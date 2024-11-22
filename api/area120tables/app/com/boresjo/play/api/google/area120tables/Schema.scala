package com.boresjo.play.api.google.area120tables

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ListWorkspacesResponse(
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is empty, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** The list of workspaces. */
		workspaces: Option[List[Schema.Workspace]] = None
	)
	
	object CreateRowRequest {
		enum ViewEnum extends Enum[ViewEnum] { case VIEW_UNSPECIFIED, COLUMN_ID_VIEW }
	}
	case class CreateRowRequest(
	  /** Required. The row to create. */
		row: Option[Schema.Row] = None,
	  /** Optional. Column key to use for values in the row. Defaults to user entered name. */
		view: Option[Schema.CreateRowRequest.ViewEnum] = None,
	  /** Required. The parent table where this row will be created. Format: tables/{table} */
		parent: Option[String] = None
	)
	
	case class SavedView(
	  /** Internal id associated with the saved view. */
		id: Option[String] = None,
	  /** Display name of the saved view. */
		name: Option[String] = None
	)
	
	case class LookupDetails(
	  /** The id of the relationship column. */
		relationshipColumnId: Option[String] = None,
	  /** The name of the relationship column associated with the lookup. */
		relationshipColumn: Option[String] = None
	)
	
	case class BatchDeleteRowsRequest(
	  /** Required. The names of the rows to delete. All rows must belong to the parent table or else the entire batch will fail. A maximum of 500 rows can be deleted in a batch. Format: tables/{table}/rows/{row} */
		names: Option[List[String]] = None
	)
	
	case class BatchCreateRowsRequest(
	  /** Required. The request message specifying the rows to create. A maximum of 500 rows can be created in a single batch. */
		requests: Option[List[Schema.CreateRowRequest]] = None
	)
	
	case class ColumnDescription(
	  /** Optional. Range of labeled values for the column. Some columns like tags and drop-downs limit the values to a set of possible values. We return the range of values in such cases to help clients implement better user data validation. */
		labels: Option[List[Schema.LabeledItem]] = None,
	  /** Optional. Indicates whether or not multiple values are allowed for array types where such a restriction is possible. */
		multipleValuesDisallowed: Option[Boolean] = None,
	  /** Optional. Additional details about a date column. */
		dateDetails: Option[Schema.DateDetails] = None,
	  /** Data type of the column Supported types are auto_id, boolean, boolean_list, creator, create_timestamp, date, dropdown, location, integer, integer_list, number, number_list, person, person_list, tags, check_list, text, text_list, update_timestamp, updater, relationship, file_attachment_list. These types directly map to the column types supported on Tables website. */
		dataType: Option[String] = None,
	  /** Optional. Additional details about a relationship column. Specified when data_type is relationship. */
		relationshipDetails: Option[Schema.RelationshipDetails] = None,
	  /** column name */
		name: Option[String] = None,
	  /** Optional. Indicates that values for the column cannot be set by the user. */
		readonly: Option[Boolean] = None,
	  /** Internal id for a column. */
		id: Option[String] = None,
	  /** Optional. Indicates that this is a lookup column whose value is derived from the relationship column specified in the details. Lookup columns can not be updated directly. To change the value you must update the associated relationship column. */
		lookupDetails: Option[Schema.LookupDetails] = None
	)
	
	case class Row(
	  /** The values of the row. This is a map of column key to value. Key is user entered name(default) or the internal column id based on the view in the request. */
		values: Option[Map[String, JsValue]] = None,
	  /** Time when the row was created. */
		createTime: Option[String] = None,
	  /** Time when the row was last updated. */
		updateTime: Option[String] = None,
	  /** The resource name of the row. Row names have the form `tables/{table}/rows/{row}`. The name is ignored when creating a row. */
		name: Option[String] = None
	)
	
	object UpdateRowRequest {
		enum ViewEnum extends Enum[ViewEnum] { case VIEW_UNSPECIFIED, COLUMN_ID_VIEW }
	}
	case class UpdateRowRequest(
	  /** Optional. Column key to use for values in the row. Defaults to user entered name. */
		view: Option[Schema.UpdateRowRequest.ViewEnum] = None,
	  /** The list of fields to update. */
		updateMask: Option[String] = None,
	  /** Required. The row to update. */
		row: Option[Schema.Row] = None
	)
	
	case class Empty(
	
	)
	
	case class LabeledItem(
	  /** Internal id associated with the item. */
		id: Option[String] = None,
	  /** Display string as entered by user. */
		name: Option[String] = None
	)
	
	case class BatchCreateRowsResponse(
	  /** The created rows. */
		rows: Option[List[Schema.Row]] = None
	)
	
	case class Table(
	  /** Time when the table was created. */
		createTime: Option[String] = None,
	  /** The human readable title of the table. */
		displayName: Option[String] = None,
	  /** The time zone of the table. IANA Time Zone Database time zone, e.g. "America/New_York". */
		timeZone: Option[String] = None,
	  /** Saved views for this table. */
		savedViews: Option[List[Schema.SavedView]] = None,
	  /** List of columns in this table. Order of columns matches the display order. */
		columns: Option[List[Schema.ColumnDescription]] = None,
	  /** The resource name of the table. Table names have the form `tables/{table}`. */
		name: Option[String] = None,
	  /** Time when the table was last updated excluding updates to individual rows */
		updateTime: Option[String] = None
	)
	
	case class ListTablesResponse(
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is empty, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** The list of tables. */
		tables: Option[List[Schema.Table]] = None
	)
	
	case class BatchUpdateRowsResponse(
	  /** The updated rows. */
		rows: Option[List[Schema.Row]] = None
	)
	
	case class DateDetails(
	  /** Whether the date column includes time. */
		hasTime: Option[Boolean] = None
	)
	
	case class BatchUpdateRowsRequest(
	  /** Required. The request messages specifying the rows to update. A maximum of 500 rows can be modified in a single batch. */
		requests: Option[List[Schema.UpdateRowRequest]] = None
	)
	
	case class ListRowsResponse(
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is empty, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** The rows from the specified table. */
		rows: Option[List[Schema.Row]] = None
	)
	
	case class Workspace(
	  /** The human readable title of the workspace. */
		displayName: Option[String] = None,
	  /** The resource name of the workspace. Workspace names have the form `workspaces/{workspace}`. */
		name: Option[String] = None,
	  /** Time when the workspace was created. */
		createTime: Option[String] = None,
	  /** The list of tables in the workspace. */
		tables: Option[List[Schema.Table]] = None,
	  /** Time when the workspace was last updated. */
		updateTime: Option[String] = None
	)
	
	case class RelationshipDetails(
	  /** The name of the table this relationship is linked to. */
		linkedTable: Option[String] = None
	)
}
