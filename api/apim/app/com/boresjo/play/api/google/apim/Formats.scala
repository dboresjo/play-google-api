package com.boresjo.play.api.google.apim

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListOperationsResponse: Format[Schema.ListOperationsResponse] = Json.format[Schema.ListOperationsResponse]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtCancelOperationRequest: Format[Schema.CancelOperationRequest] = Json.format[Schema.CancelOperationRequest]
	given fmtObservationSource: Format[Schema.ObservationSource] = Json.format[Schema.ObservationSource]
	given fmtGclbObservationSource: Format[Schema.GclbObservationSource] = Json.format[Schema.GclbObservationSource]
	given fmtObservationSourceStateEnum: Format[Schema.ObservationSource.StateEnum] = JsonEnumFormat[Schema.ObservationSource.StateEnum]
	given fmtGclbObservationSourcePscNetworkConfig: Format[Schema.GclbObservationSourcePscNetworkConfig] = Json.format[Schema.GclbObservationSourcePscNetworkConfig]
	given fmtListObservationSourcesResponse: Format[Schema.ListObservationSourcesResponse] = Json.format[Schema.ListObservationSourcesResponse]
	given fmtObservationJob: Format[Schema.ObservationJob] = Json.format[Schema.ObservationJob]
	given fmtObservationJobStateEnum: Format[Schema.ObservationJob.StateEnum] = JsonEnumFormat[Schema.ObservationJob.StateEnum]
	given fmtListObservationJobsResponse: Format[Schema.ListObservationJobsResponse] = Json.format[Schema.ListObservationJobsResponse]
	given fmtEnableObservationJobRequest: Format[Schema.EnableObservationJobRequest] = Json.format[Schema.EnableObservationJobRequest]
	given fmtDisableObservationJobRequest: Format[Schema.DisableObservationJobRequest] = Json.format[Schema.DisableObservationJobRequest]
	given fmtApiObservation: Format[Schema.ApiObservation] = Json.format[Schema.ApiObservation]
	given fmtApiObservationStyleEnum: Format[Schema.ApiObservation.StyleEnum] = JsonEnumFormat[Schema.ApiObservation.StyleEnum]
	given fmtListApiObservationsResponse: Format[Schema.ListApiObservationsResponse] = Json.format[Schema.ListApiObservationsResponse]
	given fmtApiOperation: Format[Schema.ApiOperation] = Json.format[Schema.ApiOperation]
	given fmtHttpOperation: Format[Schema.HttpOperation] = Json.format[Schema.HttpOperation]
	given fmtHttpOperationPathParam: Format[Schema.HttpOperationPathParam] = Json.format[Schema.HttpOperationPathParam]
	given fmtHttpOperationQueryParam: Format[Schema.HttpOperationQueryParam] = Json.format[Schema.HttpOperationQueryParam]
	given fmtHttpOperationMethodEnum: Format[Schema.HttpOperation.MethodEnum] = JsonEnumFormat[Schema.HttpOperation.MethodEnum]
	given fmtHttpOperationHttpRequest: Format[Schema.HttpOperationHttpRequest] = Json.format[Schema.HttpOperationHttpRequest]
	given fmtHttpOperationHttpResponse: Format[Schema.HttpOperationHttpResponse] = Json.format[Schema.HttpOperationHttpResponse]
	given fmtHttpOperationPathParamDataTypeEnum: Format[Schema.HttpOperationPathParam.DataTypeEnum] = JsonEnumFormat[Schema.HttpOperationPathParam.DataTypeEnum]
	given fmtHttpOperationQueryParamDataTypeEnum: Format[Schema.HttpOperationQueryParam.DataTypeEnum] = JsonEnumFormat[Schema.HttpOperationQueryParam.DataTypeEnum]
	given fmtHttpOperationHeader: Format[Schema.HttpOperationHeader] = Json.format[Schema.HttpOperationHeader]
	given fmtHttpOperationHeaderDataTypeEnum: Format[Schema.HttpOperationHeader.DataTypeEnum] = JsonEnumFormat[Schema.HttpOperationHeader.DataTypeEnum]
	given fmtListApiOperationsResponse: Format[Schema.ListApiOperationsResponse] = Json.format[Schema.ListApiOperationsResponse]
	given fmtBatchEditTagsApiObservationsRequest: Format[Schema.BatchEditTagsApiObservationsRequest] = Json.format[Schema.BatchEditTagsApiObservationsRequest]
	given fmtEditTagsApiObservationsRequest: Format[Schema.EditTagsApiObservationsRequest] = Json.format[Schema.EditTagsApiObservationsRequest]
	given fmtTagAction: Format[Schema.TagAction] = Json.format[Schema.TagAction]
	given fmtTagActionActionEnum: Format[Schema.TagAction.ActionEnum] = JsonEnumFormat[Schema.TagAction.ActionEnum]
	given fmtBatchEditTagsApiObservationsResponse: Format[Schema.BatchEditTagsApiObservationsResponse] = Json.format[Schema.BatchEditTagsApiObservationsResponse]
	given fmtListApiObservationTagsResponse: Format[Schema.ListApiObservationTagsResponse] = Json.format[Schema.ListApiObservationTagsResponse]
	given fmtListLocationsResponse: Format[Schema.ListLocationsResponse] = Json.format[Schema.ListLocationsResponse]
	given fmtLocation: Format[Schema.Location] = Json.format[Schema.Location]
	given fmtOperationMetadata: Format[Schema.OperationMetadata] = Json.format[Schema.OperationMetadata]
}
