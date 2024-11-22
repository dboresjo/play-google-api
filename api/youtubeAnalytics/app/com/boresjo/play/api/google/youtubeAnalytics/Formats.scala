package com.boresjo.play.api.google.youtubeAnalytics

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListGroupItemsResponse: Format[Schema.ListGroupItemsResponse] = Json.format[Schema.ListGroupItemsResponse]
	given fmtGroupItem: Format[Schema.GroupItem] = Json.format[Schema.GroupItem]
	given fmtErrors: Format[Schema.Errors] = Json.format[Schema.Errors]
	given fmtGroupItemResource: Format[Schema.GroupItemResource] = Json.format[Schema.GroupItemResource]
	given fmtGroupSnippet: Format[Schema.GroupSnippet] = Json.format[Schema.GroupSnippet]
	given fmtEmptyResponse: Format[Schema.EmptyResponse] = Json.format[Schema.EmptyResponse]
	given fmtResultTableColumnHeader: Format[Schema.ResultTableColumnHeader] = Json.format[Schema.ResultTableColumnHeader]
	given fmtErrorProto: Format[Schema.ErrorProto] = Json.format[Schema.ErrorProto]
	given fmtErrorsCodeEnum: Format[Schema.Errors.CodeEnum] = JsonEnumFormat[Schema.Errors.CodeEnum]
	given fmtQueryResponse: Format[Schema.QueryResponse] = Json.format[Schema.QueryResponse]
	given fmtGroup: Format[Schema.Group] = Json.format[Schema.Group]
	given fmtGroupContentDetails: Format[Schema.GroupContentDetails] = Json.format[Schema.GroupContentDetails]
	given fmtErrorProtoLocationTypeEnum: Format[Schema.ErrorProto.LocationTypeEnum] = JsonEnumFormat[Schema.ErrorProto.LocationTypeEnum]
	given fmtListGroupsResponse: Format[Schema.ListGroupsResponse] = Json.format[Schema.ListGroupsResponse]
}
