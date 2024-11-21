package com.boresjo.play.api.google.area120tables

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListWorkspacesResponse: Format[Schema.ListWorkspacesResponse] = Json.format[Schema.ListWorkspacesResponse]
	given fmtWorkspace: Format[Schema.Workspace] = Json.format[Schema.Workspace]
	given fmtCreateRowRequest: Format[Schema.CreateRowRequest] = Json.format[Schema.CreateRowRequest]
	given fmtRow: Format[Schema.Row] = Json.format[Schema.Row]
	given fmtCreateRowRequestViewEnum: Format[Schema.CreateRowRequest.ViewEnum] = JsonEnumFormat[Schema.CreateRowRequest.ViewEnum]
	given fmtSavedView: Format[Schema.SavedView] = Json.format[Schema.SavedView]
	given fmtLookupDetails: Format[Schema.LookupDetails] = Json.format[Schema.LookupDetails]
	given fmtBatchDeleteRowsRequest: Format[Schema.BatchDeleteRowsRequest] = Json.format[Schema.BatchDeleteRowsRequest]
	given fmtBatchCreateRowsRequest: Format[Schema.BatchCreateRowsRequest] = Json.format[Schema.BatchCreateRowsRequest]
	given fmtColumnDescription: Format[Schema.ColumnDescription] = Json.format[Schema.ColumnDescription]
	given fmtLabeledItem: Format[Schema.LabeledItem] = Json.format[Schema.LabeledItem]
	given fmtDateDetails: Format[Schema.DateDetails] = Json.format[Schema.DateDetails]
	given fmtRelationshipDetails: Format[Schema.RelationshipDetails] = Json.format[Schema.RelationshipDetails]
	given fmtUpdateRowRequest: Format[Schema.UpdateRowRequest] = Json.format[Schema.UpdateRowRequest]
	given fmtUpdateRowRequestViewEnum: Format[Schema.UpdateRowRequest.ViewEnum] = JsonEnumFormat[Schema.UpdateRowRequest.ViewEnum]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtBatchCreateRowsResponse: Format[Schema.BatchCreateRowsResponse] = Json.format[Schema.BatchCreateRowsResponse]
	given fmtTable: Format[Schema.Table] = Json.format[Schema.Table]
	given fmtListTablesResponse: Format[Schema.ListTablesResponse] = Json.format[Schema.ListTablesResponse]
	given fmtBatchUpdateRowsResponse: Format[Schema.BatchUpdateRowsResponse] = Json.format[Schema.BatchUpdateRowsResponse]
	given fmtBatchUpdateRowsRequest: Format[Schema.BatchUpdateRowsRequest] = Json.format[Schema.BatchUpdateRowsRequest]
	given fmtListRowsResponse: Format[Schema.ListRowsResponse] = Json.format[Schema.ListRowsResponse]
}
