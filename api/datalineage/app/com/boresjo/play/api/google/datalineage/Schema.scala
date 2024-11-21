package com.boresjo.play.api.google.datalineage

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleCloudDatacatalogLineageV1SearchLinksResponse(
	  /** The list of links for a given asset. Can be empty if the asset has no relations of requested type (source or target). */
		links: Option[List[Schema.GoogleCloudDatacatalogLineageV1Link]] = None,
	  /** The token to specify as `page_token` in the subsequent call to get the next page. Omitted if there are no more pages in the response. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogLineageV1ProcessLinks(
	  /** An array containing link details objects of the links provided in the original request. A single process can result in creating multiple links. If any of the links you provide in the request are created by the same process, they all are included in this array. */
		links: Option[List[Schema.GoogleCloudDatacatalogLineageV1ProcessLinkInfo]] = None,
	  /** The process name in the format of `projects/{project}/locations/{location}/processes/{process}`. */
		process: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogLineageV1EntityReference(
	  /** Required. [Fully Qualified Name (FQN)](https://cloud.google.com/data-catalog/docs/fully-qualified-names) of the entity. */
		fullyQualifiedName: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogLineageV1ProcessOpenLineageRunEventResponse(
	  /** Created run name. Format: `projects/{project}/locations/{location}/processes/{process}/runs/{run}`. */
		run: Option[String] = None,
	  /** Created process name. Format: `projects/{project}/locations/{location}/processes/{process}`. */
		process: Option[String] = None,
	  /** Created lineage event names. Format: `projects/{project}/locations/{location}/processes/{process}/runs/{run}/lineageEvents/{lineage_event}`. */
		lineageEvents: Option[List[String]] = None
	)
	
	case class GoogleLongrunningListOperationsResponse(
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None,
	  /** A list of operations that matches the specified filter in the request. */
		operations: Option[List[Schema.GoogleLongrunningOperation]] = None
	)
	
	object GoogleCloudDatacatalogLineageV1Origin {
		enum SourceTypeEnum extends Enum[SourceTypeEnum] { case SOURCE_TYPE_UNSPECIFIED, CUSTOM, BIGQUERY, DATA_FUSION, COMPOSER, LOOKER_STUDIO, DATAPROC, VERTEX_AI }
	}
	case class GoogleCloudDatacatalogLineageV1Origin(
	  /** If the source_type isn't CUSTOM, the value of this field should be a GCP resource name of the system, which reports lineage. The project and location parts of the resource name must match the project and location of the lineage resource being created. Examples: - `{source_type: COMPOSER, name: "projects/foo/locations/us/environments/bar"}` - `{source_type: BIGQUERY, name: "projects/foo/locations/eu"}` - `{source_type: CUSTOM, name: "myCustomIntegration"}` */
		name: Option[String] = None,
	  /** Type of the source. Use of a source_type other than `CUSTOM` for process creation or updating is highly discouraged. It may cause additional billing costs and be restricted in the future without notice. */
		sourceType: Option[Schema.GoogleCloudDatacatalogLineageV1Origin.SourceTypeEnum] = None
	)
	
	object GoogleCloudDatacatalogLineageV1OperationMetadata {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PENDING, RUNNING, SUCCEEDED, FAILED }
		enum OperationTypeEnum extends Enum[OperationTypeEnum] { case TYPE_UNSPECIFIED, DELETE, CREATE }
	}
	case class GoogleCloudDatacatalogLineageV1OperationMetadata(
	  /** Output only. The [relative name] (https://cloud.google.com//apis/design/resource_names#relative_resource_name) of the resource being operated on. */
		resource: Option[String] = None,
	  /** Output only. The UUID of the resource being operated on. */
		resourceUuid: Option[String] = None,
	  /** Output only. The current operation state. */
		state: Option[Schema.GoogleCloudDatacatalogLineageV1OperationMetadata.StateEnum] = None,
	  /** Output only. The timestamp of the operation termination, regardless of its success. This field is unset if the operation is still ongoing. */
		endTime: Option[String] = None,
	  /** Output only. The type of the operation being performed. */
		operationType: Option[Schema.GoogleCloudDatacatalogLineageV1OperationMetadata.OperationTypeEnum] = None,
	  /** Output only. The timestamp of the operation submission to the server. */
		createTime: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogLineageV1BatchSearchLinkProcessesRequest(
	  /** The maximum number of processes to return in a single page of the response. A page may contain fewer results than this value. */
		pageSize: Option[Int] = None,
	  /** The page token received from a previous `BatchSearchLinkProcesses` call. Use it to get the next page. When requesting subsequent pages of a response, remember that all parameters must match the values you provided in the original request. */
		pageToken: Option[String] = None,
	  /** Required. An array of links to check for their associated LineageProcesses. The maximum number of items in this array is 100. If the request contains more than 100 links, it returns the `INVALID_ARGUMENT` error. Format: `projects/{project}/locations/{location}/links/{link}`. */
		links: Option[List[String]] = None
	)
	
	case class GoogleCloudDatacatalogLineageV1SearchLinksRequest(
	  /** Optional. Send asset information in the &#42;&#42;target&#42;&#42; field to retrieve all links that lead from upstream assets to the specified asset. */
		target: Option[Schema.GoogleCloudDatacatalogLineageV1EntityReference] = None,
	  /** Optional. Send asset information in the &#42;&#42;source&#42;&#42; field to retrieve all links that lead from the specified asset to downstream assets. */
		source: Option[Schema.GoogleCloudDatacatalogLineageV1EntityReference] = None,
	  /** Optional. The page token received from a previous `SearchLinksRequest` call. Use it to get the next page. When requesting subsequent pages of a response, remember that all parameters must match the values you provided in the original request. */
		pageToken: Option[String] = None,
	  /** Optional. The maximum number of links to return in a single page of the response. A page may contain fewer links than this value. If unspecified, at most 10 links are returned. Maximum value is 100; values greater than 100 are reduced to 100. */
		pageSize: Option[Int] = None
	)
	
	case class GoogleCloudDatacatalogLineageV1EventLink(
	  /** Required. Reference to the source entity */
		source: Option[Schema.GoogleCloudDatacatalogLineageV1EntityReference] = None,
	  /** Required. Reference to the target entity */
		target: Option[Schema.GoogleCloudDatacatalogLineageV1EntityReference] = None
	)
	
	case class GoogleLongrunningOperation(
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.GoogleRpcStatus] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleRpcStatus(
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None,
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None
	)
	
	case class GoogleCloudDatacatalogLineageV1Process(
	  /** Optional. A human-readable name you can set to display in a user interface. Must be not longer than 200 characters and only contain UTF-8 letters or numbers, spaces or characters like `_-:&.` */
		displayName: Option[String] = None,
	  /** Optional. The attributes of the process. Should only be used for the purpose of non-semantic management (classifying, describing or labeling the process). Up to 100 attributes are allowed. */
		attributes: Option[Map[String, JsValue]] = None,
	  /** Immutable. The resource name of the lineage process. Format: `projects/{project}/locations/{location}/processes/{process}`. Can be specified or auto-assigned. {process} must be not longer than 200 characters and only contain characters in a set: `a-zA-Z0-9_-:.` */
		name: Option[String] = None,
	  /** Optional. The origin of this process and its runs and lineage events. */
		origin: Option[Schema.GoogleCloudDatacatalogLineageV1Origin] = None
	)
	
	case class GoogleCloudDatacatalogLineageV1LineageEvent(
	  /** Optional. List of source-target pairs. Can't contain more than 100 tuples. */
		links: Option[List[Schema.GoogleCloudDatacatalogLineageV1EventLink]] = None,
	  /** Optional. The end of the transformation which resulted in this lineage event. For streaming scenarios, it should be the end of the period from which the lineage is being reported. */
		endTime: Option[String] = None,
	  /** Immutable. The resource name of the lineage event. Format: `projects/{project}/locations/{location}/processes/{process}/runs/{run}/lineageEvents/{lineage_event}`. Can be specified or auto-assigned. {lineage_event} must be not longer than 200 characters and only contain characters in a set: `a-zA-Z0-9_-:.` */
		name: Option[String] = None,
	  /** Required. The beginning of the transformation which resulted in this lineage event. For streaming scenarios, it should be the beginning of the period from which the lineage is being reported. */
		startTime: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogLineageV1ListRunsResponse(
	  /** The token to specify as `page_token` in the next call to get the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** The runs from the specified project and location. */
		runs: Option[List[Schema.GoogleCloudDatacatalogLineageV1Run]] = None
	)
	
	case class GoogleProtobufEmpty(
	
	)
	
	case class GoogleCloudDatacatalogLineageV1Link(
	  /** Output only. Immutable. The name of the link. Format: `projects/{project}/locations/{location}/links/{link}`. */
		name: Option[String] = None,
	  /** The pointer to the entity that is the &#42;&#42;source&#42;&#42; of this link. */
		source: Option[Schema.GoogleCloudDatacatalogLineageV1EntityReference] = None,
	  /** The start of the first event establishing this link. */
		startTime: Option[String] = None,
	  /** The end of the last event establishing this link. */
		endTime: Option[String] = None,
	  /** The pointer to the entity that is the &#42;&#42;target&#42;&#42; of this link. */
		target: Option[Schema.GoogleCloudDatacatalogLineageV1EntityReference] = None
	)
	
	case class GoogleLongrunningCancelOperationRequest(
	
	)
	
	case class GoogleCloudDatacatalogLineageV1ListLineageEventsResponse(
	  /** The token to specify as `page_token` in the next call to get the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Lineage events from the specified project and location. */
		lineageEvents: Option[List[Schema.GoogleCloudDatacatalogLineageV1LineageEvent]] = None
	)
	
	case class GoogleCloudDatacatalogLineageV1BatchSearchLinkProcessesResponse(
	  /** An array of processes associated with the specified links. */
		processLinks: Option[List[Schema.GoogleCloudDatacatalogLineageV1ProcessLinks]] = None,
	  /** The token to specify as `page_token` in the subsequent call to get the next page. Omitted if there are no more pages in the response. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudDatacatalogLineageV1ProcessLinkInfo(
	  /** The end of the last event establishing this link-process tuple. */
		endTime: Option[String] = None,
	  /** The name of the link in the format of `projects/{project}/locations/{location}/links/{link}`. */
		link: Option[String] = None,
	  /** The start of the first event establishing this link-process tuple. */
		startTime: Option[String] = None
	)
	
	object GoogleCloudDatacatalogLineageV1Run {
		enum StateEnum extends Enum[StateEnum] { case UNKNOWN, STARTED, COMPLETED, FAILED, ABORTED }
	}
	case class GoogleCloudDatacatalogLineageV1Run(
	  /** Immutable. The resource name of the run. Format: `projects/{project}/locations/{location}/processes/{process}/runs/{run}`. Can be specified or auto-assigned. {run} must be not longer than 200 characters and only contain characters in a set: `a-zA-Z0-9_-:.` */
		name: Option[String] = None,
	  /** Optional. The attributes of the run. Should only be used for the purpose of non-semantic management (classifying, describing or labeling the run). Up to 100 attributes are allowed. */
		attributes: Option[Map[String, JsValue]] = None,
	  /** Optional. The timestamp of the end of the run. */
		endTime: Option[String] = None,
	  /** Required. The timestamp of the start of the run. */
		startTime: Option[String] = None,
	  /** Optional. A human-readable name you can set to display in a user interface. Must be not longer than 1024 characters and only contain UTF-8 letters or numbers, spaces or characters like `_-:&.` */
		displayName: Option[String] = None,
	  /** Required. The state of the run. */
		state: Option[Schema.GoogleCloudDatacatalogLineageV1Run.StateEnum] = None
	)
	
	case class GoogleCloudDatacatalogLineageV1ListProcessesResponse(
	  /** The token to specify as `page_token` in the next call to get the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** The processes from the specified project and location. */
		processes: Option[List[Schema.GoogleCloudDatacatalogLineageV1Process]] = None
	)
}
