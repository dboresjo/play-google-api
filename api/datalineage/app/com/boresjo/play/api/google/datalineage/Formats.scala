package com.boresjo.play.api.google.datalineage

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtGoogleCloudDatacatalogLineageV1SearchLinksResponse: Format[Schema.GoogleCloudDatacatalogLineageV1SearchLinksResponse] = Json.format[Schema.GoogleCloudDatacatalogLineageV1SearchLinksResponse]
	given fmtGoogleCloudDatacatalogLineageV1Link: Format[Schema.GoogleCloudDatacatalogLineageV1Link] = Json.format[Schema.GoogleCloudDatacatalogLineageV1Link]
	given fmtGoogleCloudDatacatalogLineageV1ProcessLinks: Format[Schema.GoogleCloudDatacatalogLineageV1ProcessLinks] = Json.format[Schema.GoogleCloudDatacatalogLineageV1ProcessLinks]
	given fmtGoogleCloudDatacatalogLineageV1ProcessLinkInfo: Format[Schema.GoogleCloudDatacatalogLineageV1ProcessLinkInfo] = Json.format[Schema.GoogleCloudDatacatalogLineageV1ProcessLinkInfo]
	given fmtGoogleCloudDatacatalogLineageV1EntityReference: Format[Schema.GoogleCloudDatacatalogLineageV1EntityReference] = Json.format[Schema.GoogleCloudDatacatalogLineageV1EntityReference]
	given fmtGoogleCloudDatacatalogLineageV1ProcessOpenLineageRunEventResponse: Format[Schema.GoogleCloudDatacatalogLineageV1ProcessOpenLineageRunEventResponse] = Json.format[Schema.GoogleCloudDatacatalogLineageV1ProcessOpenLineageRunEventResponse]
	given fmtGoogleLongrunningListOperationsResponse: Format[Schema.GoogleLongrunningListOperationsResponse] = Json.format[Schema.GoogleLongrunningListOperationsResponse]
	given fmtGoogleLongrunningOperation: Format[Schema.GoogleLongrunningOperation] = Json.format[Schema.GoogleLongrunningOperation]
	given fmtGoogleCloudDatacatalogLineageV1Origin: Format[Schema.GoogleCloudDatacatalogLineageV1Origin] = Json.format[Schema.GoogleCloudDatacatalogLineageV1Origin]
	given fmtGoogleCloudDatacatalogLineageV1OriginSourceTypeEnum: Format[Schema.GoogleCloudDatacatalogLineageV1Origin.SourceTypeEnum] = JsonEnumFormat[Schema.GoogleCloudDatacatalogLineageV1Origin.SourceTypeEnum]
	given fmtGoogleCloudDatacatalogLineageV1OperationMetadata: Format[Schema.GoogleCloudDatacatalogLineageV1OperationMetadata] = Json.format[Schema.GoogleCloudDatacatalogLineageV1OperationMetadata]
	given fmtGoogleCloudDatacatalogLineageV1OperationMetadataStateEnum: Format[Schema.GoogleCloudDatacatalogLineageV1OperationMetadata.StateEnum] = JsonEnumFormat[Schema.GoogleCloudDatacatalogLineageV1OperationMetadata.StateEnum]
	given fmtGoogleCloudDatacatalogLineageV1OperationMetadataOperationTypeEnum: Format[Schema.GoogleCloudDatacatalogLineageV1OperationMetadata.OperationTypeEnum] = JsonEnumFormat[Schema.GoogleCloudDatacatalogLineageV1OperationMetadata.OperationTypeEnum]
	given fmtGoogleCloudDatacatalogLineageV1BatchSearchLinkProcessesRequest: Format[Schema.GoogleCloudDatacatalogLineageV1BatchSearchLinkProcessesRequest] = Json.format[Schema.GoogleCloudDatacatalogLineageV1BatchSearchLinkProcessesRequest]
	given fmtGoogleCloudDatacatalogLineageV1SearchLinksRequest: Format[Schema.GoogleCloudDatacatalogLineageV1SearchLinksRequest] = Json.format[Schema.GoogleCloudDatacatalogLineageV1SearchLinksRequest]
	given fmtGoogleCloudDatacatalogLineageV1EventLink: Format[Schema.GoogleCloudDatacatalogLineageV1EventLink] = Json.format[Schema.GoogleCloudDatacatalogLineageV1EventLink]
	given fmtGoogleRpcStatus: Format[Schema.GoogleRpcStatus] = Json.format[Schema.GoogleRpcStatus]
	given fmtGoogleCloudDatacatalogLineageV1Process: Format[Schema.GoogleCloudDatacatalogLineageV1Process] = Json.format[Schema.GoogleCloudDatacatalogLineageV1Process]
	given fmtGoogleCloudDatacatalogLineageV1LineageEvent: Format[Schema.GoogleCloudDatacatalogLineageV1LineageEvent] = Json.format[Schema.GoogleCloudDatacatalogLineageV1LineageEvent]
	given fmtGoogleCloudDatacatalogLineageV1ListRunsResponse: Format[Schema.GoogleCloudDatacatalogLineageV1ListRunsResponse] = Json.format[Schema.GoogleCloudDatacatalogLineageV1ListRunsResponse]
	given fmtGoogleCloudDatacatalogLineageV1Run: Format[Schema.GoogleCloudDatacatalogLineageV1Run] = Json.format[Schema.GoogleCloudDatacatalogLineageV1Run]
	given fmtGoogleProtobufEmpty: Format[Schema.GoogleProtobufEmpty] = Json.format[Schema.GoogleProtobufEmpty]
	given fmtGoogleLongrunningCancelOperationRequest: Format[Schema.GoogleLongrunningCancelOperationRequest] = Json.format[Schema.GoogleLongrunningCancelOperationRequest]
	given fmtGoogleCloudDatacatalogLineageV1ListLineageEventsResponse: Format[Schema.GoogleCloudDatacatalogLineageV1ListLineageEventsResponse] = Json.format[Schema.GoogleCloudDatacatalogLineageV1ListLineageEventsResponse]
	given fmtGoogleCloudDatacatalogLineageV1BatchSearchLinkProcessesResponse: Format[Schema.GoogleCloudDatacatalogLineageV1BatchSearchLinkProcessesResponse] = Json.format[Schema.GoogleCloudDatacatalogLineageV1BatchSearchLinkProcessesResponse]
	given fmtGoogleCloudDatacatalogLineageV1RunStateEnum: Format[Schema.GoogleCloudDatacatalogLineageV1Run.StateEnum] = JsonEnumFormat[Schema.GoogleCloudDatacatalogLineageV1Run.StateEnum]
	given fmtGoogleCloudDatacatalogLineageV1ListProcessesResponse: Format[Schema.GoogleCloudDatacatalogLineageV1ListProcessesResponse] = Json.format[Schema.GoogleCloudDatacatalogLineageV1ListProcessesResponse]
}
