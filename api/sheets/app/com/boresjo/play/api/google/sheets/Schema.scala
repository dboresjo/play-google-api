package com.boresjo.play.api.google.sheets

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object ValueRange {
		enum MajorDimensionEnum extends Enum[MajorDimensionEnum] { case DIMENSION_UNSPECIFIED, ROWS, COLUMNS }
	}
	case class ValueRange(
	  /** The range the values cover, in [A1 notation](/sheets/api/guides/concepts#cell). For output, this range indicates the entire requested range, even though the values will exclude trailing rows and columns. When appending values, this field represents the range to search for a table, after which values will be appended. */
		range: Option[String] = None,
	  /** The major dimension of the values. For output, if the spreadsheet data is: `A1=1,B1=2,A2=3,B2=4`, then requesting `range=A1:B2,majorDimension=ROWS` will return `[[1,2],[3,4]]`, whereas requesting `range=A1:B2,majorDimension=COLUMNS` will return `[[1,3],[2,4]]`. For input, with `range=A1:B2,majorDimension=ROWS` then `[[1,2],[3,4]]` will set `A1=1,B1=2,A2=3,B2=4`. With `range=A1:B2,majorDimension=COLUMNS` then `[[1,2],[3,4]]` will set `A1=1,B1=3,A2=2,B2=4`. When writing, if this field is not set, it defaults to ROWS. */
		majorDimension: Option[Schema.ValueRange.MajorDimensionEnum] = None,
	  /** The data that was read or to be written. This is an array of arrays, the outer array representing all the data and each inner array representing a major dimension. Each item in the inner array corresponds with one cell. For output, empty trailing rows and columns will not be included. For input, supported value types are: bool, string, and double. Null values will be skipped. To set a cell to an empty value, set the string value to an empty string. */
		values: Option[List[List[JsValue]]] = None
	)
	
	case class UpdateValuesResponse(
	  /** The spreadsheet the updates were applied to. */
		spreadsheetId: Option[String] = None,
	  /** The range (in A1 notation) that updates were applied to. */
		updatedRange: Option[String] = None,
	  /** The number of rows where at least one cell in the row was updated. */
		updatedRows: Option[Int] = None,
	  /** The number of columns where at least one cell in the column was updated. */
		updatedColumns: Option[Int] = None,
	  /** The number of cells updated. */
		updatedCells: Option[Int] = None,
	  /** The values of the cells after updates were applied. This is only included if the request's `includeValuesInResponse` field was `true`. */
		updatedData: Option[Schema.ValueRange] = None
	)
	
	case class AppendValuesResponse(
	  /** The spreadsheet the updates were applied to. */
		spreadsheetId: Option[String] = None,
	  /** The range (in A1 notation) of the table that values are being appended to (before the values were appended). Empty if no table was found. */
		tableRange: Option[String] = None,
	  /** Information about the updates that were applied. */
		updates: Option[Schema.UpdateValuesResponse] = None
	)
	
	case class ClearValuesRequest(
	
	)
	
	case class ClearValuesResponse(
	  /** The spreadsheet the updates were applied to. */
		spreadsheetId: Option[String] = None,
	  /** The range (in A1 notation) that was cleared. (If the request was for an unbounded range or a ranger larger than the bounds of the sheet, this will be the actual range that was cleared, bounded to the sheet's limits.) */
		clearedRange: Option[String] = None
	)
	
	case class BatchGetValuesResponse(
	  /** The ID of the spreadsheet the data was retrieved from. */
		spreadsheetId: Option[String] = None,
	  /** The requested values. The order of the ValueRanges is the same as the order of the requested ranges. */
		valueRanges: Option[List[Schema.ValueRange]] = None
	)
	
	object BatchUpdateValuesRequest {
		enum ValueInputOptionEnum extends Enum[ValueInputOptionEnum] { case INPUT_VALUE_OPTION_UNSPECIFIED, RAW, USER_ENTERED }
		enum ResponseValueRenderOptionEnum extends Enum[ResponseValueRenderOptionEnum] { case FORMATTED_VALUE, UNFORMATTED_VALUE, FORMULA }
		enum ResponseDateTimeRenderOptionEnum extends Enum[ResponseDateTimeRenderOptionEnum] { case SERIAL_NUMBER, FORMATTED_STRING }
	}
	case class BatchUpdateValuesRequest(
	  /** How the input data should be interpreted. */
		valueInputOption: Option[Schema.BatchUpdateValuesRequest.ValueInputOptionEnum] = None,
	  /** The new values to apply to the spreadsheet. */
		data: Option[List[Schema.ValueRange]] = None,
	  /** Determines if the update response should include the values of the cells that were updated. By default, responses do not include the updated values. The `updatedData` field within each of the BatchUpdateValuesResponse.responses contains the updated values. If the range to write was larger than the range actually written, the response includes all values in the requested range (excluding trailing empty rows and columns). */
		includeValuesInResponse: Option[Boolean] = None,
	  /** Determines how values in the response should be rendered. The default render option is FORMATTED_VALUE. */
		responseValueRenderOption: Option[Schema.BatchUpdateValuesRequest.ResponseValueRenderOptionEnum] = None,
	  /** Determines how dates, times, and durations in the response should be rendered. This is ignored if response_value_render_option is FORMATTED_VALUE. The default dateTime render option is SERIAL_NUMBER. */
		responseDateTimeRenderOption: Option[Schema.BatchUpdateValuesRequest.ResponseDateTimeRenderOptionEnum] = None
	)
	
	case class BatchUpdateValuesResponse(
	  /** The spreadsheet the updates were applied to. */
		spreadsheetId: Option[String] = None,
	  /** The total number of rows where at least one cell in the row was updated. */
		totalUpdatedRows: Option[Int] = None,
	  /** The total number of columns where at least one cell in the column was updated. */
		totalUpdatedColumns: Option[Int] = None,
	  /** The total number of cells updated. */
		totalUpdatedCells: Option[Int] = None,
	  /** The total number of sheets where at least one cell in the sheet was updated. */
		totalUpdatedSheets: Option[Int] = None,
	  /** One UpdateValuesResponse per requested range, in the same order as the requests appeared. */
		responses: Option[List[Schema.UpdateValuesResponse]] = None
	)
	
	case class BatchClearValuesRequest(
	  /** The ranges to clear, in [A1 notation or R1C1 notation](/sheets/api/guides/concepts#cell). */
		ranges: Option[List[String]] = None
	)
	
	case class BatchClearValuesResponse(
	  /** The spreadsheet the updates were applied to. */
		spreadsheetId: Option[String] = None,
	  /** The ranges that were cleared, in A1 notation. If the requests are for an unbounded range or a ranger larger than the bounds of the sheet, this is the actual ranges that were cleared, bounded to the sheet's limits. */
		clearedRanges: Option[List[String]] = None
	)
	
	object BatchGetValuesByDataFilterRequest {
		enum MajorDimensionEnum extends Enum[MajorDimensionEnum] { case DIMENSION_UNSPECIFIED, ROWS, COLUMNS }
		enum ValueRenderOptionEnum extends Enum[ValueRenderOptionEnum] { case FORMATTED_VALUE, UNFORMATTED_VALUE, FORMULA }
		enum DateTimeRenderOptionEnum extends Enum[DateTimeRenderOptionEnum] { case SERIAL_NUMBER, FORMATTED_STRING }
	}
	case class BatchGetValuesByDataFilterRequest(
	  /** The data filters used to match the ranges of values to retrieve. Ranges that match any of the specified data filters are included in the response. */
		dataFilters: Option[List[Schema.DataFilter]] = None,
	  /** The major dimension that results should use. For example, if the spreadsheet data is: `A1=1,B1=2,A2=3,B2=4`, then a request that selects that range and sets `majorDimension=ROWS` returns `[[1,2],[3,4]]`, whereas a request that sets `majorDimension=COLUMNS` returns `[[1,3],[2,4]]`. */
		majorDimension: Option[Schema.BatchGetValuesByDataFilterRequest.MajorDimensionEnum] = None,
	  /** How values should be represented in the output. The default render option is FORMATTED_VALUE. */
		valueRenderOption: Option[Schema.BatchGetValuesByDataFilterRequest.ValueRenderOptionEnum] = None,
	  /** How dates, times, and durations should be represented in the output. This is ignored if value_render_option is FORMATTED_VALUE. The default dateTime render option is SERIAL_NUMBER. */
		dateTimeRenderOption: Option[Schema.BatchGetValuesByDataFilterRequest.DateTimeRenderOptionEnum] = None
	)
	
	case class DataFilter(
	  /** Selects data associated with the developer metadata matching the criteria described by this DeveloperMetadataLookup. */
		developerMetadataLookup: Option[Schema.DeveloperMetadataLookup] = None,
	  /** Selects data that matches the specified A1 range. */
		a1Range: Option[String] = None,
	  /** Selects data that matches the range described by the GridRange. */
		gridRange: Option[Schema.GridRange] = None
	)
	
	object DeveloperMetadataLookup {
		enum LocationTypeEnum extends Enum[LocationTypeEnum] { case DEVELOPER_METADATA_LOCATION_TYPE_UNSPECIFIED, ROW, COLUMN, SHEET, SPREADSHEET }
		enum LocationMatchingStrategyEnum extends Enum[LocationMatchingStrategyEnum] { case DEVELOPER_METADATA_LOCATION_MATCHING_STRATEGY_UNSPECIFIED, EXACT_LOCATION, INTERSECTING_LOCATION }
		enum VisibilityEnum extends Enum[VisibilityEnum] { case DEVELOPER_METADATA_VISIBILITY_UNSPECIFIED, DOCUMENT, PROJECT }
	}
	case class DeveloperMetadataLookup(
	  /** Limits the selected developer metadata to those entries which are associated with locations of the specified type. For example, when this field is specified as ROW this lookup only considers developer metadata associated on rows. If the field is left unspecified, all location types are considered. This field cannot be specified as SPREADSHEET when the locationMatchingStrategy is specified as INTERSECTING or when the metadataLocation is specified as a non-spreadsheet location: spreadsheet metadata cannot intersect any other developer metadata location. This field also must be left unspecified when the locationMatchingStrategy is specified as EXACT. */
		locationType: Option[Schema.DeveloperMetadataLookup.LocationTypeEnum] = None,
	  /** Limits the selected developer metadata to those entries associated with the specified location. This field either matches exact locations or all intersecting locations according the specified locationMatchingStrategy. */
		metadataLocation: Option[Schema.DeveloperMetadataLocation] = None,
	  /** Determines how this lookup matches the location. If this field is specified as EXACT, only developer metadata associated on the exact location specified is matched. If this field is specified to INTERSECTING, developer metadata associated on intersecting locations is also matched. If left unspecified, this field assumes a default value of INTERSECTING. If this field is specified, a metadataLocation must also be specified. */
		locationMatchingStrategy: Option[Schema.DeveloperMetadataLookup.LocationMatchingStrategyEnum] = None,
	  /** Limits the selected developer metadata to that which has a matching DeveloperMetadata.metadata_id. */
		metadataId: Option[Int] = None,
	  /** Limits the selected developer metadata to that which has a matching DeveloperMetadata.metadata_key. */
		metadataKey: Option[String] = None,
	  /** Limits the selected developer metadata to that which has a matching DeveloperMetadata.metadata_value. */
		metadataValue: Option[String] = None,
	  /** Limits the selected developer metadata to that which has a matching DeveloperMetadata.visibility. If left unspecified, all developer metadata visible to the requesting project is considered. */
		visibility: Option[Schema.DeveloperMetadataLookup.VisibilityEnum] = None
	)
	
	object DeveloperMetadataLocation {
		enum LocationTypeEnum extends Enum[LocationTypeEnum] { case DEVELOPER_METADATA_LOCATION_TYPE_UNSPECIFIED, ROW, COLUMN, SHEET, SPREADSHEET }
	}
	case class DeveloperMetadataLocation(
	  /** The type of location this object represents. This field is read-only. */
		locationType: Option[Schema.DeveloperMetadataLocation.LocationTypeEnum] = None,
	  /** True when metadata is associated with an entire spreadsheet. */
		spreadsheet: Option[Boolean] = None,
	  /** The ID of the sheet when metadata is associated with an entire sheet. */
		sheetId: Option[Int] = None,
	  /** Represents the row or column when metadata is associated with a dimension. The specified DimensionRange must represent a single row or column; it cannot be unbounded or span multiple rows or columns. */
		dimensionRange: Option[Schema.DimensionRange] = None
	)
	
	object DimensionRange {
		enum DimensionEnum extends Enum[DimensionEnum] { case DIMENSION_UNSPECIFIED, ROWS, COLUMNS }
	}
	case class DimensionRange(
	  /** The sheet this span is on. */
		sheetId: Option[Int] = None,
	  /** The dimension of the span. */
		dimension: Option[Schema.DimensionRange.DimensionEnum] = None,
	  /** The start (inclusive) of the span, or not set if unbounded. */
		startIndex: Option[Int] = None,
	  /** The end (exclusive) of the span, or not set if unbounded. */
		endIndex: Option[Int] = None
	)
	
	case class GridRange(
	  /** The sheet this range is on. */
		sheetId: Option[Int] = None,
	  /** The start row (inclusive) of the range, or not set if unbounded. */
		startRowIndex: Option[Int] = None,
	  /** The end row (exclusive) of the range, or not set if unbounded. */
		endRowIndex: Option[Int] = None,
	  /** The start column (inclusive) of the range, or not set if unbounded. */
		startColumnIndex: Option[Int] = None,
	  /** The end column (exclusive) of the range, or not set if unbounded. */
		endColumnIndex: Option[Int] = None
	)
	
	case class BatchGetValuesByDataFilterResponse(
	  /** The ID of the spreadsheet the data was retrieved from. */
		spreadsheetId: Option[String] = None,
	  /** The requested values with the list of data filters that matched them. */
		valueRanges: Option[List[Schema.MatchedValueRange]] = None
	)
	
	case class MatchedValueRange(
	  /** The values matched by the DataFilter. */
		valueRange: Option[Schema.ValueRange] = None,
	  /** The DataFilters from the request that matched the range of values. */
		dataFilters: Option[List[Schema.DataFilter]] = None
	)
	
	object BatchUpdateValuesByDataFilterRequest {
		enum ValueInputOptionEnum extends Enum[ValueInputOptionEnum] { case INPUT_VALUE_OPTION_UNSPECIFIED, RAW, USER_ENTERED }
		enum ResponseValueRenderOptionEnum extends Enum[ResponseValueRenderOptionEnum] { case FORMATTED_VALUE, UNFORMATTED_VALUE, FORMULA }
		enum ResponseDateTimeRenderOptionEnum extends Enum[ResponseDateTimeRenderOptionEnum] { case SERIAL_NUMBER, FORMATTED_STRING }
	}
	case class BatchUpdateValuesByDataFilterRequest(
	  /** How the input data should be interpreted. */
		valueInputOption: Option[Schema.BatchUpdateValuesByDataFilterRequest.ValueInputOptionEnum] = None,
	  /** The new values to apply to the spreadsheet. If more than one range is matched by the specified DataFilter the specified values are applied to all of those ranges. */
		data: Option[List[Schema.DataFilterValueRange]] = None,
	  /** Determines if the update response should include the values of the cells that were updated. By default, responses do not include the updated values. The `updatedData` field within each of the BatchUpdateValuesResponse.responses contains the updated values. If the range to write was larger than the range actually written, the response includes all values in the requested range (excluding trailing empty rows and columns). */
		includeValuesInResponse: Option[Boolean] = None,
	  /** Determines how values in the response should be rendered. The default render option is FORMATTED_VALUE. */
		responseValueRenderOption: Option[Schema.BatchUpdateValuesByDataFilterRequest.ResponseValueRenderOptionEnum] = None,
	  /** Determines how dates, times, and durations in the response should be rendered. This is ignored if response_value_render_option is FORMATTED_VALUE. The default dateTime render option is SERIAL_NUMBER. */
		responseDateTimeRenderOption: Option[Schema.BatchUpdateValuesByDataFilterRequest.ResponseDateTimeRenderOptionEnum] = None
	)
	
	object DataFilterValueRange {
		enum MajorDimensionEnum extends Enum[MajorDimensionEnum] { case DIMENSION_UNSPECIFIED, ROWS, COLUMNS }
	}
	case class DataFilterValueRange(
	  /** The data filter describing the location of the values in the spreadsheet. */
		dataFilter: Option[Schema.DataFilter] = None,
	  /** The major dimension of the values. */
		majorDimension: Option[Schema.DataFilterValueRange.MajorDimensionEnum] = None,
	  /** The data to be written. If the provided values exceed any of the ranges matched by the data filter then the request fails. If the provided values are less than the matched ranges only the specified values are written, existing values in the matched ranges remain unaffected. */
		values: Option[List[List[JsValue]]] = None
	)
	
	case class BatchUpdateValuesByDataFilterResponse(
	  /** The spreadsheet the updates were applied to. */
		spreadsheetId: Option[String] = None,
	  /** The total number of rows where at least one cell in the row was updated. */
		totalUpdatedRows: Option[Int] = None,
	  /** The total number of columns where at least one cell in the column was updated. */
		totalUpdatedColumns: Option[Int] = None,
	  /** The total number of cells updated. */
		totalUpdatedCells: Option[Int] = None,
	  /** The total number of sheets where at least one cell in the sheet was updated. */
		totalUpdatedSheets: Option[Int] = None,
	  /** The response for each range updated. */
		responses: Option[List[Schema.UpdateValuesByDataFilterResponse]] = None
	)
	
	case class UpdateValuesByDataFilterResponse(
	  /** The range (in [A1 notation](/sheets/api/guides/concepts#cell)) that updates were applied to. */
		updatedRange: Option[String] = None,
	  /** The number of rows where at least one cell in the row was updated. */
		updatedRows: Option[Int] = None,
	  /** The number of columns where at least one cell in the column was updated. */
		updatedColumns: Option[Int] = None,
	  /** The number of cells updated. */
		updatedCells: Option[Int] = None,
	  /** The data filter that selected the range that was updated. */
		dataFilter: Option[Schema.DataFilter] = None,
	  /** The values of the cells in the range matched by the dataFilter after all updates were applied. This is only included if the request's `includeValuesInResponse` field was `true`. */
		updatedData: Option[Schema.ValueRange] = None
	)
	
	case class BatchClearValuesByDataFilterRequest(
	  /** The DataFilters used to determine which ranges to clear. */
		dataFilters: Option[List[Schema.DataFilter]] = None
	)
	
	case class BatchClearValuesByDataFilterResponse(
	  /** The spreadsheet the updates were applied to. */
		spreadsheetId: Option[String] = None,
	  /** The ranges that were cleared, in [A1 notation](/sheets/api/guides/concepts#cell). If the requests are for an unbounded range or a ranger larger than the bounds of the sheet, this is the actual ranges that were cleared, bounded to the sheet's limits. */
		clearedRanges: Option[List[String]] = None
	)
	
	object DeveloperMetadata {
		enum VisibilityEnum extends Enum[VisibilityEnum] { case DEVELOPER_METADATA_VISIBILITY_UNSPECIFIED, DOCUMENT, PROJECT }
	}
	case class DeveloperMetadata(
	  /** The spreadsheet-scoped unique ID that identifies the metadata. IDs may be specified when metadata is created, otherwise one will be randomly generated and assigned. Must be positive. */
		metadataId: Option[Int] = None,
	  /** The metadata key. There may be multiple metadata in a spreadsheet with the same key. Developer metadata must always have a key specified. */
		metadataKey: Option[String] = None,
	  /** Data associated with the metadata's key. */
		metadataValue: Option[String] = None,
	  /** The location where the metadata is associated. */
		location: Option[Schema.DeveloperMetadataLocation] = None,
	  /** The metadata visibility. Developer metadata must always have a visibility specified. */
		visibility: Option[Schema.DeveloperMetadata.VisibilityEnum] = None
	)
	
	case class SearchDeveloperMetadataRequest(
	  /** The data filters describing the criteria used to determine which DeveloperMetadata entries to return. DeveloperMetadata matching any of the specified filters are included in the response. */
		dataFilters: Option[List[Schema.DataFilter]] = None
	)
	
	case class SearchDeveloperMetadataResponse(
	  /** The metadata matching the criteria of the search request. */
		matchedDeveloperMetadata: Option[List[Schema.MatchedDeveloperMetadata]] = None
	)
	
	case class MatchedDeveloperMetadata(
	  /** The developer metadata matching the specified filters. */
		developerMetadata: Option[Schema.DeveloperMetadata] = None,
	  /** All filters matching the returned developer metadata. */
		dataFilters: Option[List[Schema.DataFilter]] = None
	)
	
	case class Spreadsheet(
	  /** The ID of the spreadsheet. This field is read-only. */
		spreadsheetId: Option[String] = None,
	  /** Overall properties of a spreadsheet. */
		properties: Option[Schema.SpreadsheetProperties] = None,
	  /** The sheets that are part of a spreadsheet. */
		sheets: Option[List[Schema.Sheet]] = None,
	  /** The named ranges defined in a spreadsheet. */
		namedRanges: Option[List[Schema.NamedRange]] = None,
	  /** The url of the spreadsheet. This field is read-only. */
		spreadsheetUrl: Option[String] = None,
	  /** The developer metadata associated with a spreadsheet. */
		developerMetadata: Option[List[Schema.DeveloperMetadata]] = None,
	  /** A list of external data sources connected with the spreadsheet. */
		dataSources: Option[List[Schema.DataSource]] = None,
	  /** Output only. A list of data source refresh schedules. */
		dataSourceSchedules: Option[List[Schema.DataSourceRefreshSchedule]] = None
	)
	
	object SpreadsheetProperties {
		enum AutoRecalcEnum extends Enum[AutoRecalcEnum] { case RECALCULATION_INTERVAL_UNSPECIFIED, ON_CHANGE, MINUTE, HOUR }
	}
	case class SpreadsheetProperties(
	  /** The title of the spreadsheet. */
		title: Option[String] = None,
	  /** The locale of the spreadsheet in one of the following formats: &#42; an ISO 639-1 language code such as `en` &#42; an ISO 639-2 language code such as `fil`, if no 639-1 code exists &#42; a combination of the ISO language code and country code, such as `en_US` Note: when updating this field, not all locales/languages are supported. */
		locale: Option[String] = None,
	  /** The amount of time to wait before volatile functions are recalculated. */
		autoRecalc: Option[Schema.SpreadsheetProperties.AutoRecalcEnum] = None,
	  /** The time zone of the spreadsheet, in CLDR format such as `America/New_York`. If the time zone isn't recognized, this may be a custom time zone such as `GMT-07:00`. */
		timeZone: Option[String] = None,
	  /** The default format of all cells in the spreadsheet. CellData.effectiveFormat will not be set if the cell's format is equal to this default format. This field is read-only. */
		defaultFormat: Option[Schema.CellFormat] = None,
	  /** Determines whether and how circular references are resolved with iterative calculation. Absence of this field means that circular references result in calculation errors. */
		iterativeCalculationSettings: Option[Schema.IterativeCalculationSettings] = None,
	  /** Theme applied to the spreadsheet. */
		spreadsheetTheme: Option[Schema.SpreadsheetTheme] = None,
	  /** Whether to allow external URL access for image and import functions. Read only when true. When false, you can set to true. This value will be bypassed and always return true if the admin has enabled the [allowlisting feature](https://support.google.com/a?p=url_allowlist). */
		importFunctionsExternalUrlAccessAllowed: Option[Boolean] = None
	)
	
	object CellFormat {
		enum HorizontalAlignmentEnum extends Enum[HorizontalAlignmentEnum] { case HORIZONTAL_ALIGN_UNSPECIFIED, LEFT, CENTER, RIGHT }
		enum VerticalAlignmentEnum extends Enum[VerticalAlignmentEnum] { case VERTICAL_ALIGN_UNSPECIFIED, TOP, MIDDLE, BOTTOM }
		enum WrapStrategyEnum extends Enum[WrapStrategyEnum] { case WRAP_STRATEGY_UNSPECIFIED, OVERFLOW_CELL, LEGACY_WRAP, CLIP, WRAP }
		enum TextDirectionEnum extends Enum[TextDirectionEnum] { case TEXT_DIRECTION_UNSPECIFIED, LEFT_TO_RIGHT, RIGHT_TO_LEFT }
		enum HyperlinkDisplayTypeEnum extends Enum[HyperlinkDisplayTypeEnum] { case HYPERLINK_DISPLAY_TYPE_UNSPECIFIED, LINKED, PLAIN_TEXT }
	}
	case class CellFormat(
	  /** A format describing how number values should be represented to the user. */
		numberFormat: Option[Schema.NumberFormat] = None,
	  /** The background color of the cell. Deprecated: Use background_color_style. */
		backgroundColor: Option[Schema.Color] = None,
	  /** The background color of the cell. If background_color is also set, this field takes precedence. */
		backgroundColorStyle: Option[Schema.ColorStyle] = None,
	  /** The borders of the cell. */
		borders: Option[Schema.Borders] = None,
	  /** The padding of the cell. */
		padding: Option[Schema.Padding] = None,
	  /** The horizontal alignment of the value in the cell. */
		horizontalAlignment: Option[Schema.CellFormat.HorizontalAlignmentEnum] = None,
	  /** The vertical alignment of the value in the cell. */
		verticalAlignment: Option[Schema.CellFormat.VerticalAlignmentEnum] = None,
	  /** The wrap strategy for the value in the cell. */
		wrapStrategy: Option[Schema.CellFormat.WrapStrategyEnum] = None,
	  /** The direction of the text in the cell. */
		textDirection: Option[Schema.CellFormat.TextDirectionEnum] = None,
	  /** The format of the text in the cell (unless overridden by a format run). Setting a cell-level link here clears the cell's existing links. Setting the link field in a TextFormatRun takes precedence over the cell-level link. */
		textFormat: Option[Schema.TextFormat] = None,
	  /** If one exists, how a hyperlink should be displayed in the cell. */
		hyperlinkDisplayType: Option[Schema.CellFormat.HyperlinkDisplayTypeEnum] = None,
	  /** The rotation applied to text in the cell. */
		textRotation: Option[Schema.TextRotation] = None
	)
	
	object NumberFormat {
		enum TypeEnum extends Enum[TypeEnum] { case NUMBER_FORMAT_TYPE_UNSPECIFIED, TEXT, NUMBER, PERCENT, CURRENCY, DATE, TIME, DATE_TIME, SCIENTIFIC }
	}
	case class NumberFormat(
	  /** The type of the number format. When writing, this field must be set. */
		`type`: Option[Schema.NumberFormat.TypeEnum] = None,
	  /** Pattern string used for formatting. If not set, a default pattern based on the user's locale will be used if necessary for the given type. See the [Date and Number Formats guide](/sheets/api/guides/formats) for more information about the supported patterns. */
		pattern: Option[String] = None
	)
	
	case class Color(
	  /** The amount of red in the color as a value in the interval [0, 1]. */
		red: Option[BigDecimal] = None,
	  /** The amount of green in the color as a value in the interval [0, 1]. */
		green: Option[BigDecimal] = None,
	  /** The amount of blue in the color as a value in the interval [0, 1]. */
		blue: Option[BigDecimal] = None,
	  /** The fraction of this color that should be applied to the pixel. That is, the final pixel color is defined by the equation: `pixel color = alpha &#42; (this color) + (1.0 - alpha) &#42; (background color)` This means that a value of 1.0 corresponds to a solid color, whereas a value of 0.0 corresponds to a completely transparent color. This uses a wrapper message rather than a simple float scalar so that it is possible to distinguish between a default value and the value being unset. If omitted, this color object is rendered as a solid color (as if the alpha value had been explicitly given a value of 1.0). */
		alpha: Option[BigDecimal] = None
	)
	
	object ColorStyle {
		enum ThemeColorEnum extends Enum[ThemeColorEnum] { case THEME_COLOR_TYPE_UNSPECIFIED, TEXT, BACKGROUND, ACCENT1, ACCENT2, ACCENT3, ACCENT4, ACCENT5, ACCENT6, LINK }
	}
	case class ColorStyle(
	  /** RGB color. The [`alpha`](/sheets/api/reference/rest/v4/spreadsheets/other#Color.FIELDS.alpha) value in the [`Color`](/sheets/api/reference/rest/v4/spreadsheets/other#color) object isn't generally supported. */
		rgbColor: Option[Schema.Color] = None,
	  /** Theme color. */
		themeColor: Option[Schema.ColorStyle.ThemeColorEnum] = None
	)
	
	case class Borders(
	  /** The top border of the cell. */
		top: Option[Schema.Border] = None,
	  /** The bottom border of the cell. */
		bottom: Option[Schema.Border] = None,
	  /** The left border of the cell. */
		left: Option[Schema.Border] = None,
	  /** The right border of the cell. */
		right: Option[Schema.Border] = None
	)
	
	object Border {
		enum StyleEnum extends Enum[StyleEnum] { case STYLE_UNSPECIFIED, DOTTED, DASHED, SOLID, SOLID_MEDIUM, SOLID_THICK, NONE, DOUBLE }
	}
	case class Border(
	  /** The style of the border. */
		style: Option[Schema.Border.StyleEnum] = None,
	  /** The width of the border, in pixels. Deprecated; the width is determined by the "style" field. */
		width: Option[Int] = None,
	  /** The color of the border. Deprecated: Use color_style. */
		color: Option[Schema.Color] = None,
	  /** The color of the border. If color is also set, this field takes precedence. */
		colorStyle: Option[Schema.ColorStyle] = None
	)
	
	case class Padding(
	  /** The top padding of the cell. */
		top: Option[Int] = None,
	  /** The right padding of the cell. */
		right: Option[Int] = None,
	  /** The bottom padding of the cell. */
		bottom: Option[Int] = None,
	  /** The left padding of the cell. */
		left: Option[Int] = None
	)
	
	case class TextFormat(
	  /** The foreground color of the text. Deprecated: Use foreground_color_style. */
		foregroundColor: Option[Schema.Color] = None,
	  /** The foreground color of the text. If foreground_color is also set, this field takes precedence. */
		foregroundColorStyle: Option[Schema.ColorStyle] = None,
	  /** The font family. */
		fontFamily: Option[String] = None,
	  /** The size of the font. */
		fontSize: Option[Int] = None,
	  /** True if the text is bold. */
		bold: Option[Boolean] = None,
	  /** True if the text is italicized. */
		italic: Option[Boolean] = None,
	  /** True if the text has a strikethrough. */
		strikethrough: Option[Boolean] = None,
	  /** True if the text is underlined. */
		underline: Option[Boolean] = None,
	  /** The link destination of the text, if any. Setting the link field in a TextFormatRun will clear the cell's existing links or a cell-level link set in the same request. When a link is set, the text foreground color will be set to the default link color and the text will be underlined. If these fields are modified in the same request, those values will be used instead of the link defaults. */
		link: Option[Schema.Link] = None
	)
	
	case class Link(
	  /** The link identifier. */
		uri: Option[String] = None
	)
	
	case class TextRotation(
	  /** The angle between the standard orientation and the desired orientation. Measured in degrees. Valid values are between -90 and 90. Positive angles are angled upwards, negative are angled downwards. Note: For LTR text direction positive angles are in the counterclockwise direction, whereas for RTL they are in the clockwise direction */
		angle: Option[Int] = None,
	  /** If true, text reads top to bottom, but the orientation of individual characters is unchanged. For example: | V | | e | | r | | t | | i | | c | | a | | l | */
		vertical: Option[Boolean] = None
	)
	
	case class IterativeCalculationSettings(
	  /** When iterative calculation is enabled, the maximum number of calculation rounds to perform. */
		maxIterations: Option[Int] = None,
	  /** When iterative calculation is enabled and successive results differ by less than this threshold value, the calculation rounds stop. */
		convergenceThreshold: Option[BigDecimal] = None
	)
	
	case class SpreadsheetTheme(
	  /** Name of the primary font family. */
		primaryFontFamily: Option[String] = None,
	  /** The spreadsheet theme color pairs. To update you must provide all theme color pairs. */
		themeColors: Option[List[Schema.ThemeColorPair]] = None
	)
	
	object ThemeColorPair {
		enum ColorTypeEnum extends Enum[ColorTypeEnum] { case THEME_COLOR_TYPE_UNSPECIFIED, TEXT, BACKGROUND, ACCENT1, ACCENT2, ACCENT3, ACCENT4, ACCENT5, ACCENT6, LINK }
	}
	case class ThemeColorPair(
	  /** The type of the spreadsheet theme color. */
		colorType: Option[Schema.ThemeColorPair.ColorTypeEnum] = None,
	  /** The concrete color corresponding to the theme color type. */
		color: Option[Schema.ColorStyle] = None
	)
	
	case class Sheet(
	  /** The properties of the sheet. */
		properties: Option[Schema.SheetProperties] = None,
	  /** Data in the grid, if this is a grid sheet. The number of GridData objects returned is dependent on the number of ranges requested on this sheet. For example, if this is representing `Sheet1`, and the spreadsheet was requested with ranges `Sheet1!A1:C10` and `Sheet1!D15:E20`, then the first GridData will have a startRow/startColumn of `0`, while the second one will have `startRow 14` (zero-based row 15), and `startColumn 3` (zero-based column D). For a DATA_SOURCE sheet, you can not request a specific range, the GridData contains all the values. */
		data: Option[List[Schema.GridData]] = None,
	  /** The ranges that are merged together. */
		merges: Option[List[Schema.GridRange]] = None,
	  /** The conditional format rules in this sheet. */
		conditionalFormats: Option[List[Schema.ConditionalFormatRule]] = None,
	  /** The filter views in this sheet. */
		filterViews: Option[List[Schema.FilterView]] = None,
	  /** The protected ranges in this sheet. */
		protectedRanges: Option[List[Schema.ProtectedRange]] = None,
	  /** The filter on this sheet, if any. */
		basicFilter: Option[Schema.BasicFilter] = None,
	  /** The specifications of every chart on this sheet. */
		charts: Option[List[Schema.EmbeddedChart]] = None,
	  /** The banded (alternating colors) ranges on this sheet. */
		bandedRanges: Option[List[Schema.BandedRange]] = None,
	  /** The developer metadata associated with a sheet. */
		developerMetadata: Option[List[Schema.DeveloperMetadata]] = None,
	  /** All row groups on this sheet, ordered by increasing range start index, then by group depth. */
		rowGroups: Option[List[Schema.DimensionGroup]] = None,
	  /** All column groups on this sheet, ordered by increasing range start index, then by group depth. */
		columnGroups: Option[List[Schema.DimensionGroup]] = None,
	  /** The slicers on this sheet. */
		slicers: Option[List[Schema.Slicer]] = None
	)
	
	object SheetProperties {
		enum SheetTypeEnum extends Enum[SheetTypeEnum] { case SHEET_TYPE_UNSPECIFIED, GRID, OBJECT, DATA_SOURCE }
	}
	case class SheetProperties(
	  /** The ID of the sheet. Must be non-negative. This field cannot be changed once set. */
		sheetId: Option[Int] = None,
	  /** The name of the sheet. */
		title: Option[String] = None,
	  /** The index of the sheet within the spreadsheet. When adding or updating sheet properties, if this field is excluded then the sheet is added or moved to the end of the sheet list. When updating sheet indices or inserting sheets, movement is considered in "before the move" indexes. For example, if there were three sheets (S1, S2, S3) in order to move S1 ahead of S2 the index would have to be set to 2. A sheet index update request is ignored if the requested index is identical to the sheets current index or if the requested new index is equal to the current sheet index + 1. */
		index: Option[Int] = None,
	  /** The type of sheet. Defaults to GRID. This field cannot be changed once set. */
		sheetType: Option[Schema.SheetProperties.SheetTypeEnum] = None,
	  /** Additional properties of the sheet if this sheet is a grid. (If the sheet is an object sheet, containing a chart or image, then this field will be absent.) When writing it is an error to set any grid properties on non-grid sheets. If this sheet is a DATA_SOURCE sheet, this field is output only but contains the properties that reflect how a data source sheet is rendered in the UI, e.g. row_count. */
		gridProperties: Option[Schema.GridProperties] = None,
	  /** True if the sheet is hidden in the UI, false if it's visible. */
		hidden: Option[Boolean] = None,
	  /** The color of the tab in the UI. Deprecated: Use tab_color_style. */
		tabColor: Option[Schema.Color] = None,
	  /** The color of the tab in the UI. If tab_color is also set, this field takes precedence. */
		tabColorStyle: Option[Schema.ColorStyle] = None,
	  /** True if the sheet is an RTL sheet instead of an LTR sheet. */
		rightToLeft: Option[Boolean] = None,
	  /** Output only. If present, the field contains DATA_SOURCE sheet specific properties. */
		dataSourceSheetProperties: Option[Schema.DataSourceSheetProperties] = None
	)
	
	case class GridProperties(
	  /** The number of rows in the grid. */
		rowCount: Option[Int] = None,
	  /** The number of columns in the grid. */
		columnCount: Option[Int] = None,
	  /** The number of rows that are frozen in the grid. */
		frozenRowCount: Option[Int] = None,
	  /** The number of columns that are frozen in the grid. */
		frozenColumnCount: Option[Int] = None,
	  /** True if the grid isn't showing gridlines in the UI. */
		hideGridlines: Option[Boolean] = None,
	  /** True if the row grouping control toggle is shown after the group. */
		rowGroupControlAfter: Option[Boolean] = None,
	  /** True if the column grouping control toggle is shown after the group. */
		columnGroupControlAfter: Option[Boolean] = None
	)
	
	case class DataSourceSheetProperties(
	  /** ID of the DataSource the sheet is connected to. */
		dataSourceId: Option[String] = None,
	  /** The columns displayed on the sheet, corresponding to the values in RowData. */
		columns: Option[List[Schema.DataSourceColumn]] = None,
	  /** The data execution status. */
		dataExecutionStatus: Option[Schema.DataExecutionStatus] = None
	)
	
	case class DataSourceColumn(
	  /** The column reference. */
		reference: Option[Schema.DataSourceColumnReference] = None,
	  /** The formula of the calculated column. */
		formula: Option[String] = None
	)
	
	case class DataSourceColumnReference(
	  /** The display name of the column. It should be unique within a data source. */
		name: Option[String] = None
	)
	
	object DataExecutionStatus {
		enum StateEnum extends Enum[StateEnum] { case DATA_EXECUTION_STATE_UNSPECIFIED, NOT_STARTED, RUNNING, CANCELLING, SUCCEEDED, FAILED }
		enum ErrorCodeEnum extends Enum[ErrorCodeEnum] { case DATA_EXECUTION_ERROR_CODE_UNSPECIFIED, TIMED_OUT, TOO_MANY_ROWS, TOO_MANY_COLUMNS, TOO_MANY_CELLS, ENGINE, PARAMETER_INVALID, UNSUPPORTED_DATA_TYPE, DUPLICATE_COLUMN_NAMES, INTERRUPTED, CONCURRENT_QUERY, OTHER, TOO_MANY_CHARS_PER_CELL, DATA_NOT_FOUND, PERMISSION_DENIED, MISSING_COLUMN_ALIAS, OBJECT_NOT_FOUND, OBJECT_IN_ERROR_STATE, OBJECT_SPEC_INVALID, DATA_EXECUTION_CANCELLED }
	}
	case class DataExecutionStatus(
	  /** The state of the data execution. */
		state: Option[Schema.DataExecutionStatus.StateEnum] = None,
	  /** The error code. */
		errorCode: Option[Schema.DataExecutionStatus.ErrorCodeEnum] = None,
	  /** The error message, which may be empty. */
		errorMessage: Option[String] = None,
	  /** Gets the time the data last successfully refreshed. */
		lastRefreshTime: Option[String] = None
	)
	
	case class GridData(
	  /** The first row this GridData refers to, zero-based. */
		startRow: Option[Int] = None,
	  /** The first column this GridData refers to, zero-based. */
		startColumn: Option[Int] = None,
	  /** The data in the grid, one entry per row, starting with the row in startRow. The values in RowData will correspond to columns starting at start_column. */
		rowData: Option[List[Schema.RowData]] = None,
	  /** Metadata about the requested rows in the grid, starting with the row in start_row. */
		rowMetadata: Option[List[Schema.DimensionProperties]] = None,
	  /** Metadata about the requested columns in the grid, starting with the column in start_column. */
		columnMetadata: Option[List[Schema.DimensionProperties]] = None
	)
	
	case class RowData(
	  /** The values in the row, one per column. */
		values: Option[List[Schema.CellData]] = None
	)
	
	case class CellData(
	  /** The value the user entered in the cell. e.g., `1234`, `'Hello'`, or `=NOW()` Note: Dates, Times and DateTimes are represented as doubles in serial number format. */
		userEnteredValue: Option[Schema.ExtendedValue] = None,
	  /** The effective value of the cell. For cells with formulas, this is the calculated value. For cells with literals, this is the same as the user_entered_value. This field is read-only. */
		effectiveValue: Option[Schema.ExtendedValue] = None,
	  /** The formatted value of the cell. This is the value as it's shown to the user. This field is read-only. */
		formattedValue: Option[String] = None,
	  /** The format the user entered for the cell. When writing, the new format will be merged with the existing format. */
		userEnteredFormat: Option[Schema.CellFormat] = None,
	  /** The effective format being used by the cell. This includes the results of applying any conditional formatting and, if the cell contains a formula, the computed number format. If the effective format is the default format, effective format will not be written. This field is read-only. */
		effectiveFormat: Option[Schema.CellFormat] = None,
	  /** A hyperlink this cell points to, if any. If the cell contains multiple hyperlinks, this field will be empty. This field is read-only. To set it, use a `=HYPERLINK` formula in the userEnteredValue.formulaValue field. A cell-level link can also be set from the userEnteredFormat.textFormat field. Alternatively, set a hyperlink in the textFormatRun.format.link field that spans the entire cell. */
		hyperlink: Option[String] = None,
	  /** Any note on the cell. */
		note: Option[String] = None,
	  /** Runs of rich text applied to subsections of the cell. Runs are only valid on user entered strings, not formulas, bools, or numbers. Properties of a run start at a specific index in the text and continue until the next run. Runs will inherit the properties of the cell unless explicitly changed. When writing, the new runs will overwrite any prior runs. When writing a new user_entered_value, previous runs are erased. */
		textFormatRuns: Option[List[Schema.TextFormatRun]] = None,
	  /** A data validation rule on the cell, if any. When writing, the new data validation rule will overwrite any prior rule. */
		dataValidation: Option[Schema.DataValidationRule] = None,
	  /** A pivot table anchored at this cell. The size of pivot table itself is computed dynamically based on its data, grouping, filters, values, etc. Only the top-left cell of the pivot table contains the pivot table definition. The other cells will contain the calculated values of the results of the pivot in their effective_value fields. */
		pivotTable: Option[Schema.PivotTable] = None,
	  /** A data source table anchored at this cell. The size of data source table itself is computed dynamically based on its configuration. Only the first cell of the data source table contains the data source table definition. The other cells will contain the display values of the data source table result in their effective_value fields. */
		dataSourceTable: Option[Schema.DataSourceTable] = None,
	  /** Output only. Information about a data source formula on the cell. The field is set if user_entered_value is a formula referencing some DATA_SOURCE sheet, e.g. `=SUM(DataSheet!Column)`. */
		dataSourceFormula: Option[Schema.DataSourceFormula] = None
	)
	
	case class ExtendedValue(
	  /** Represents a double value. Note: Dates, Times and DateTimes are represented as doubles in SERIAL_NUMBER format. */
		numberValue: Option[BigDecimal] = None,
	  /** Represents a string value. Leading single quotes are not included. For example, if the user typed `'123` into the UI, this would be represented as a `stringValue` of `"123"`. */
		stringValue: Option[String] = None,
	  /** Represents a boolean value. */
		boolValue: Option[Boolean] = None,
	  /** Represents a formula. */
		formulaValue: Option[String] = None,
	  /** Represents an error. This field is read-only. */
		errorValue: Option[Schema.ErrorValue] = None
	)
	
	object ErrorValue {
		enum TypeEnum extends Enum[TypeEnum] { case ERROR_TYPE_UNSPECIFIED, ERROR, NULL_VALUE, DIVIDE_BY_ZERO, VALUE, REF, NAME, NUM, N_A, LOADING }
	}
	case class ErrorValue(
	  /** The type of error. */
		`type`: Option[Schema.ErrorValue.TypeEnum] = None,
	  /** A message with more information about the error (in the spreadsheet's locale). */
		message: Option[String] = None
	)
	
	case class TextFormatRun(
	  /** The zero-based character index where this run starts, in UTF-16 code units. */
		startIndex: Option[Int] = None,
	  /** The format of this run. Absent values inherit the cell's format. */
		format: Option[Schema.TextFormat] = None
	)
	
	case class DataValidationRule(
	  /** The condition that data in the cell must match. */
		condition: Option[Schema.BooleanCondition] = None,
	  /** A message to show the user when adding data to the cell. */
		inputMessage: Option[String] = None,
	  /** True if invalid data should be rejected. */
		strict: Option[Boolean] = None,
	  /** True if the UI should be customized based on the kind of condition. If true, "List" conditions will show a dropdown. */
		showCustomUi: Option[Boolean] = None
	)
	
	object BooleanCondition {
		enum TypeEnum extends Enum[TypeEnum] { case CONDITION_TYPE_UNSPECIFIED, NUMBER_GREATER, NUMBER_GREATER_THAN_EQ, NUMBER_LESS, NUMBER_LESS_THAN_EQ, NUMBER_EQ, NUMBER_NOT_EQ, NUMBER_BETWEEN, NUMBER_NOT_BETWEEN, TEXT_CONTAINS, TEXT_NOT_CONTAINS, TEXT_STARTS_WITH, TEXT_ENDS_WITH, TEXT_EQ, TEXT_IS_EMAIL, TEXT_IS_URL, DATE_EQ, DATE_BEFORE, DATE_AFTER, DATE_ON_OR_BEFORE, DATE_ON_OR_AFTER, DATE_BETWEEN, DATE_NOT_BETWEEN, DATE_IS_VALID, ONE_OF_RANGE, ONE_OF_LIST, BLANK, NOT_BLANK, CUSTOM_FORMULA, BOOLEAN, TEXT_NOT_EQ, DATE_NOT_EQ, FILTER_EXPRESSION }
	}
	case class BooleanCondition(
	  /** The type of condition. */
		`type`: Option[Schema.BooleanCondition.TypeEnum] = None,
	  /** The values of the condition. The number of supported values depends on the condition type. Some support zero values, others one or two values, and ConditionType.ONE_OF_LIST supports an arbitrary number of values. */
		values: Option[List[Schema.ConditionValue]] = None
	)
	
	object ConditionValue {
		enum RelativeDateEnum extends Enum[RelativeDateEnum] { case RELATIVE_DATE_UNSPECIFIED, PAST_YEAR, PAST_MONTH, PAST_WEEK, YESTERDAY, TODAY, TOMORROW }
	}
	case class ConditionValue(
	  /** A relative date (based on the current date). Valid only if the type is DATE_BEFORE, DATE_AFTER, DATE_ON_OR_BEFORE or DATE_ON_OR_AFTER. Relative dates are not supported in data validation. They are supported only in conditional formatting and conditional filters. */
		relativeDate: Option[Schema.ConditionValue.RelativeDateEnum] = None,
	  /** A value the condition is based on. The value is parsed as if the user typed into a cell. Formulas are supported (and must begin with an `=` or a '+'). */
		userEnteredValue: Option[String] = None
	)
	
	object PivotTable {
		enum ValueLayoutEnum extends Enum[ValueLayoutEnum] { case HORIZONTAL, VERTICAL }
	}
	case class PivotTable(
	  /** The range the pivot table is reading data from. */
		source: Option[Schema.GridRange] = None,
	  /** The ID of the data source the pivot table is reading data from. */
		dataSourceId: Option[String] = None,
	  /** Each row grouping in the pivot table. */
		rows: Option[List[Schema.PivotGroup]] = None,
	  /** Each column grouping in the pivot table. */
		columns: Option[List[Schema.PivotGroup]] = None,
	  /** An optional mapping of filters per source column offset. The filters are applied before aggregating data into the pivot table. The map's key is the column offset of the source range that you want to filter, and the value is the criteria for that column. For example, if the source was `C10:E15`, a key of `0` will have the filter for column `C`, whereas the key `1` is for column `D`. This field is deprecated in favor of filter_specs. */
		criteria: Option[Map[String, Schema.PivotFilterCriteria]] = None,
	  /** The filters applied to the source columns before aggregating data for the pivot table. Both criteria and filter_specs are populated in responses. If both fields are specified in an update request, this field takes precedence. */
		filterSpecs: Option[List[Schema.PivotFilterSpec]] = None,
	  /** A list of values to include in the pivot table. */
		values: Option[List[Schema.PivotValue]] = None,
	  /** Whether values should be listed horizontally (as columns) or vertically (as rows). */
		valueLayout: Option[Schema.PivotTable.ValueLayoutEnum] = None,
	  /** Output only. The data execution status for data source pivot tables. */
		dataExecutionStatus: Option[Schema.DataExecutionStatus] = None
	)
	
	object PivotGroup {
		enum SortOrderEnum extends Enum[SortOrderEnum] { case SORT_ORDER_UNSPECIFIED, ASCENDING, DESCENDING }
	}
	case class PivotGroup(
	  /** The column offset of the source range that this grouping is based on. For example, if the source was `C10:E15`, a `sourceColumnOffset` of `0` means this group refers to column `C`, whereas the offset `1` would refer to column `D`. */
		sourceColumnOffset: Option[Int] = None,
	  /** The reference to the data source column this grouping is based on. */
		dataSourceColumnReference: Option[Schema.DataSourceColumnReference] = None,
	  /** True if the pivot table should include the totals for this grouping. */
		showTotals: Option[Boolean] = None,
	  /** Metadata about values in the grouping. */
		valueMetadata: Option[List[Schema.PivotGroupValueMetadata]] = None,
	  /** The order the values in this group should be sorted. */
		sortOrder: Option[Schema.PivotGroup.SortOrderEnum] = None,
	  /** The bucket of the opposite pivot group to sort by. If not specified, sorting is alphabetical by this group's values. */
		valueBucket: Option[Schema.PivotGroupSortValueBucket] = None,
	  /** True if the headings in this pivot group should be repeated. This is only valid for row groupings and is ignored by columns. By default, we minimize repetition of headings by not showing higher level headings where they are the same. For example, even though the third row below corresponds to "Q1 Mar", "Q1" is not shown because it is redundant with previous rows. Setting repeat_headings to true would cause "Q1" to be repeated for "Feb" and "Mar". +--------------+ | Q1 | Jan | | | Feb | | | Mar | +--------+-----+ | Q1 Total | +--------------+ */
		repeatHeadings: Option[Boolean] = None,
	  /** The labels to use for the row/column groups which can be customized. For example, in the following pivot table, the row label is `Region` (which could be renamed to `State`) and the column label is `Product` (which could be renamed `Item`). Pivot tables created before December 2017 do not have header labels. If you'd like to add header labels to an existing pivot table, please delete the existing pivot table and then create a new pivot table with same parameters. +--------------+---------+-------+ | SUM of Units | Product | | | Region | Pen | Paper | +--------------+---------+-------+ | New York | 345 | 98 | | Oregon | 234 | 123 | | Tennessee | 531 | 415 | +--------------+---------+-------+ | Grand Total | 1110 | 636 | +--------------+---------+-------+ */
		label: Option[String] = None,
	  /** The group rule to apply to this row/column group. */
		groupRule: Option[Schema.PivotGroupRule] = None,
	  /** The count limit on rows or columns to apply to this pivot group. */
		groupLimit: Option[Schema.PivotGroupLimit] = None
	)
	
	case class PivotGroupValueMetadata(
	  /** The calculated value the metadata corresponds to. (Note that formulaValue is not valid, because the values will be calculated.) */
		value: Option[Schema.ExtendedValue] = None,
	  /** True if the data corresponding to the value is collapsed. */
		collapsed: Option[Boolean] = None
	)
	
	case class PivotGroupSortValueBucket(
	  /** The offset in the PivotTable.values list which the values in this grouping should be sorted by. */
		valuesIndex: Option[Int] = None,
	  /** Determines the bucket from which values are chosen to sort. For example, in a pivot table with one row group & two column groups, the row group can list up to two values. The first value corresponds to a value within the first column group, and the second value corresponds to a value in the second column group. If no values are listed, this would indicate that the row should be sorted according to the "Grand Total" over the column groups. If a single value is listed, this would correspond to using the "Total" of that bucket. */
		buckets: Option[List[Schema.ExtendedValue]] = None
	)
	
	case class PivotGroupRule(
	  /** A ManualRule. */
		manualRule: Option[Schema.ManualRule] = None,
	  /** A HistogramRule. */
		histogramRule: Option[Schema.HistogramRule] = None,
	  /** A DateTimeRule. */
		dateTimeRule: Option[Schema.DateTimeRule] = None
	)
	
	case class ManualRule(
	  /** The list of group names and the corresponding items from the source data that map to each group name. */
		groups: Option[List[Schema.ManualRuleGroup]] = None
	)
	
	case class ManualRuleGroup(
	  /** The group name, which must be a string. Each group in a given ManualRule must have a unique group name. */
		groupName: Option[Schema.ExtendedValue] = None,
	  /** The items in the source data that should be placed into this group. Each item may be a string, number, or boolean. Items may appear in at most one group within a given ManualRule. Items that do not appear in any group will appear on their own. */
		items: Option[List[Schema.ExtendedValue]] = None
	)
	
	case class HistogramRule(
	  /** The size of the buckets that are created. Must be positive. */
		interval: Option[BigDecimal] = None,
	  /** The minimum value at which items are placed into buckets of constant size. Values below start are lumped into a single bucket. This field is optional. */
		start: Option[BigDecimal] = None,
	  /** The maximum value at which items are placed into buckets of constant size. Values above end are lumped into a single bucket. This field is optional. */
		end: Option[BigDecimal] = None
	)
	
	object DateTimeRule {
		enum TypeEnum extends Enum[TypeEnum] { case DATE_TIME_RULE_TYPE_UNSPECIFIED, SECOND, MINUTE, HOUR, HOUR_MINUTE, HOUR_MINUTE_AMPM, DAY_OF_WEEK, DAY_OF_YEAR, DAY_OF_MONTH, DAY_MONTH, MONTH, QUARTER, YEAR, YEAR_MONTH, YEAR_QUARTER, YEAR_MONTH_DAY }
	}
	case class DateTimeRule(
	  /** The type of date-time grouping to apply. */
		`type`: Option[Schema.DateTimeRule.TypeEnum] = None
	)
	
	case class PivotGroupLimit(
	  /** The count limit. */
		countLimit: Option[Int] = None,
	  /** The order in which the group limit is applied to the pivot table. Pivot group limits are applied from lower to higher order number. Order numbers are normalized to consecutive integers from 0. For write request, to fully customize the applying orders, all pivot group limits should have this field set with an unique number. Otherwise, the order is determined by the index in the PivotTable.rows list and then the PivotTable.columns list. */
		applyOrder: Option[Int] = None
	)
	
	case class PivotFilterCriteria(
	  /** Values that should be included. Values not listed here are excluded. */
		visibleValues: Option[List[String]] = None,
	  /** A condition that must be true for values to be shown. (`visibleValues` does not override this -- even if a value is listed there, it is still hidden if it does not meet the condition.) Condition values that refer to ranges in A1-notation are evaluated relative to the pivot table sheet. References are treated absolutely, so are not filled down the pivot table. For example, a condition value of `=A1` on "Pivot Table 1" is treated as `'Pivot Table 1'!$A$1`. The source data of the pivot table can be referenced by column header name. For example, if the source data has columns named "Revenue" and "Cost" and a condition is applied to the "Revenue" column with type `NUMBER_GREATER` and value `=Cost`, then only columns where "Revenue" > "Cost" are included. */
		condition: Option[Schema.BooleanCondition] = None,
	  /** Whether values are visible by default. If true, the visible_values are ignored, all values that meet condition (if specified) are shown. If false, values that are both in visible_values and meet condition are shown. */
		visibleByDefault: Option[Boolean] = None
	)
	
	case class PivotFilterSpec(
	  /** The zero-based column offset of the source range. */
		columnOffsetIndex: Option[Int] = None,
	  /** The reference to the data source column. */
		dataSourceColumnReference: Option[Schema.DataSourceColumnReference] = None,
	  /** The criteria for the column. */
		filterCriteria: Option[Schema.PivotFilterCriteria] = None
	)
	
	object PivotValue {
		enum SummarizeFunctionEnum extends Enum[SummarizeFunctionEnum] { case PIVOT_STANDARD_VALUE_FUNCTION_UNSPECIFIED, SUM, COUNTA, COUNT, COUNTUNIQUE, AVERAGE, MAX, MIN, MEDIAN, PRODUCT, STDEV, STDEVP, VAR, VARP, CUSTOM, NONE }
		enum CalculatedDisplayTypeEnum extends Enum[CalculatedDisplayTypeEnum] { case PIVOT_VALUE_CALCULATED_DISPLAY_TYPE_UNSPECIFIED, PERCENT_OF_ROW_TOTAL, PERCENT_OF_COLUMN_TOTAL, PERCENT_OF_GRAND_TOTAL }
	}
	case class PivotValue(
	  /** The column offset of the source range that this value reads from. For example, if the source was `C10:E15`, a `sourceColumnOffset` of `0` means this value refers to column `C`, whereas the offset `1` would refer to column `D`. */
		sourceColumnOffset: Option[Int] = None,
	  /** A custom formula to calculate the value. The formula must start with an `=` character. */
		formula: Option[String] = None,
	  /** The reference to the data source column that this value reads from. */
		dataSourceColumnReference: Option[Schema.DataSourceColumnReference] = None,
	  /** A function to summarize the value. If formula is set, the only supported values are SUM and CUSTOM. If sourceColumnOffset is set, then `CUSTOM` is not supported. */
		summarizeFunction: Option[Schema.PivotValue.SummarizeFunctionEnum] = None,
	  /** A name to use for the value. */
		name: Option[String] = None,
	  /** If specified, indicates that pivot values should be displayed as the result of a calculation with another pivot value. For example, if calculated_display_type is specified as PERCENT_OF_GRAND_TOTAL, all the pivot values are displayed as the percentage of the grand total. In the Sheets editor, this is referred to as "Show As" in the value section of a pivot table. */
		calculatedDisplayType: Option[Schema.PivotValue.CalculatedDisplayTypeEnum] = None
	)
	
	object DataSourceTable {
		enum ColumnSelectionTypeEnum extends Enum[ColumnSelectionTypeEnum] { case DATA_SOURCE_TABLE_COLUMN_SELECTION_TYPE_UNSPECIFIED, SELECTED, SYNC_ALL }
	}
	case class DataSourceTable(
	  /** The ID of the data source the data source table is associated with. */
		dataSourceId: Option[String] = None,
	  /** The type to select columns for the data source table. Defaults to SELECTED. */
		columnSelectionType: Option[Schema.DataSourceTable.ColumnSelectionTypeEnum] = None,
	  /** Columns selected for the data source table. The column_selection_type must be SELECTED. */
		columns: Option[List[Schema.DataSourceColumnReference]] = None,
	  /** Filter specifications in the data source table. */
		filterSpecs: Option[List[Schema.FilterSpec]] = None,
	  /** Sort specifications in the data source table. The result of the data source table is sorted based on the sort specifications in order. */
		sortSpecs: Option[List[Schema.SortSpec]] = None,
	  /** The limit of rows to return. If not set, a default limit is applied. Please refer to the Sheets editor for the default and max limit. */
		rowLimit: Option[Int] = None,
	  /** Output only. The data execution status. */
		dataExecutionStatus: Option[Schema.DataExecutionStatus] = None
	)
	
	case class FilterSpec(
	  /** The zero-based column index. */
		columnIndex: Option[Int] = None,
	  /** Reference to a data source column. */
		dataSourceColumnReference: Option[Schema.DataSourceColumnReference] = None,
	  /** The criteria for the column. */
		filterCriteria: Option[Schema.FilterCriteria] = None
	)
	
	case class FilterCriteria(
	  /** Values that should be hidden. */
		hiddenValues: Option[List[String]] = None,
	  /** A condition that must be true for values to be shown. (This does not override hidden_values -- if a value is listed there, it will still be hidden.) */
		condition: Option[Schema.BooleanCondition] = None,
	  /** The background fill color to filter by; only cells with this fill color are shown. Mutually exclusive with visible_foreground_color. Deprecated: Use visible_background_color_style. */
		visibleBackgroundColor: Option[Schema.Color] = None,
	  /** The background fill color to filter by; only cells with this fill color are shown. This field is mutually exclusive with visible_foreground_color, and must be set to an RGB-type color. If visible_background_color is also set, this field takes precedence. */
		visibleBackgroundColorStyle: Option[Schema.ColorStyle] = None,
	  /** The foreground color to filter by; only cells with this foreground color are shown. Mutually exclusive with visible_background_color. Deprecated: Use visible_foreground_color_style. */
		visibleForegroundColor: Option[Schema.Color] = None,
	  /** The foreground color to filter by; only cells with this foreground color are shown. This field is mutually exclusive with visible_background_color, and must be set to an RGB-type color. If visible_foreground_color is also set, this field takes precedence. */
		visibleForegroundColorStyle: Option[Schema.ColorStyle] = None
	)
	
	object SortSpec {
		enum SortOrderEnum extends Enum[SortOrderEnum] { case SORT_ORDER_UNSPECIFIED, ASCENDING, DESCENDING }
	}
	case class SortSpec(
	  /** The dimension the sort should be applied to. */
		dimensionIndex: Option[Int] = None,
	  /** Reference to a data source column. */
		dataSourceColumnReference: Option[Schema.DataSourceColumnReference] = None,
	  /** The order data should be sorted. */
		sortOrder: Option[Schema.SortSpec.SortOrderEnum] = None,
	  /** The foreground color to sort by; cells with this foreground color are sorted to the top. Mutually exclusive with background_color. Deprecated: Use foreground_color_style. */
		foregroundColor: Option[Schema.Color] = None,
	  /** The foreground color to sort by; cells with this foreground color are sorted to the top. Mutually exclusive with background_color, and must be an RGB-type color. If foreground_color is also set, this field takes precedence. */
		foregroundColorStyle: Option[Schema.ColorStyle] = None,
	  /** The background fill color to sort by; cells with this fill color are sorted to the top. Mutually exclusive with foreground_color. Deprecated: Use background_color_style. */
		backgroundColor: Option[Schema.Color] = None,
	  /** The background fill color to sort by; cells with this fill color are sorted to the top. Mutually exclusive with foreground_color, and must be an RGB-type color. If background_color is also set, this field takes precedence. */
		backgroundColorStyle: Option[Schema.ColorStyle] = None
	)
	
	case class DataSourceFormula(
	  /** The ID of the data source the formula is associated with. */
		dataSourceId: Option[String] = None,
	  /** Output only. The data execution status. */
		dataExecutionStatus: Option[Schema.DataExecutionStatus] = None
	)
	
	case class DimensionProperties(
	  /** True if this dimension is being filtered. This field is read-only. */
		hiddenByFilter: Option[Boolean] = None,
	  /** True if this dimension is explicitly hidden. */
		hiddenByUser: Option[Boolean] = None,
	  /** The height (if a row) or width (if a column) of the dimension in pixels. */
		pixelSize: Option[Int] = None,
	  /** The developer metadata associated with a single row or column. */
		developerMetadata: Option[List[Schema.DeveloperMetadata]] = None,
	  /** Output only. If set, this is a column in a data source sheet. */
		dataSourceColumnReference: Option[Schema.DataSourceColumnReference] = None
	)
	
	case class ConditionalFormatRule(
	  /** The ranges that are formatted if the condition is true. All the ranges must be on the same grid. */
		ranges: Option[List[Schema.GridRange]] = None,
	  /** The formatting is either "on" or "off" according to the rule. */
		booleanRule: Option[Schema.BooleanRule] = None,
	  /** The formatting will vary based on the gradients in the rule. */
		gradientRule: Option[Schema.GradientRule] = None
	)
	
	case class BooleanRule(
	  /** The condition of the rule. If the condition evaluates to true, the format is applied. */
		condition: Option[Schema.BooleanCondition] = None,
	  /** The format to apply. Conditional formatting can only apply a subset of formatting: bold, italic, strikethrough, foreground color and, background color. */
		format: Option[Schema.CellFormat] = None
	)
	
	case class GradientRule(
	  /** The starting interpolation point. */
		minpoint: Option[Schema.InterpolationPoint] = None,
	  /** An optional midway interpolation point. */
		midpoint: Option[Schema.InterpolationPoint] = None,
	  /** The final interpolation point. */
		maxpoint: Option[Schema.InterpolationPoint] = None
	)
	
	object InterpolationPoint {
		enum TypeEnum extends Enum[TypeEnum] { case INTERPOLATION_POINT_TYPE_UNSPECIFIED, MIN, MAX, NUMBER, PERCENT, PERCENTILE }
	}
	case class InterpolationPoint(
	  /** The color this interpolation point should use. Deprecated: Use color_style. */
		color: Option[Schema.Color] = None,
	  /** The color this interpolation point should use. If color is also set, this field takes precedence. */
		colorStyle: Option[Schema.ColorStyle] = None,
	  /** How the value should be interpreted. */
		`type`: Option[Schema.InterpolationPoint.TypeEnum] = None,
	  /** The value this interpolation point uses. May be a formula. Unused if type is MIN or MAX. */
		value: Option[String] = None
	)
	
	case class FilterView(
	  /** The ID of the filter view. */
		filterViewId: Option[Int] = None,
	  /** The name of the filter view. */
		title: Option[String] = None,
	  /** The range this filter view covers. When writing, only one of range or named_range_id may be set. */
		range: Option[Schema.GridRange] = None,
	  /** The named range this filter view is backed by, if any. When writing, only one of range or named_range_id may be set. */
		namedRangeId: Option[String] = None,
	  /** The sort order per column. Later specifications are used when values are equal in the earlier specifications. */
		sortSpecs: Option[List[Schema.SortSpec]] = None,
	  /** The criteria for showing/hiding values per column. The map's key is the column index, and the value is the criteria for that column. This field is deprecated in favor of filter_specs. */
		criteria: Option[Map[String, Schema.FilterCriteria]] = None,
	  /** The filter criteria for showing/hiding values per column. Both criteria and filter_specs are populated in responses. If both fields are specified in an update request, this field takes precedence. */
		filterSpecs: Option[List[Schema.FilterSpec]] = None
	)
	
	case class ProtectedRange(
	  /** The ID of the protected range. This field is read-only. */
		protectedRangeId: Option[Int] = None,
	  /** The range that is being protected. The range may be fully unbounded, in which case this is considered a protected sheet. When writing, only one of range or named_range_id may be set. */
		range: Option[Schema.GridRange] = None,
	  /** The named range this protected range is backed by, if any. When writing, only one of range or named_range_id may be set. */
		namedRangeId: Option[String] = None,
	  /** The description of this protected range. */
		description: Option[String] = None,
	  /** True if this protected range will show a warning when editing. Warning-based protection means that every user can edit data in the protected range, except editing will prompt a warning asking the user to confirm the edit. When writing: if this field is true, then editors are ignored. Additionally, if this field is changed from true to false and the `editors` field is not set (nor included in the field mask), then the editors will be set to all the editors in the document. */
		warningOnly: Option[Boolean] = None,
	  /** True if the user who requested this protected range can edit the protected area. This field is read-only. */
		requestingUserCanEdit: Option[Boolean] = None,
	  /** The list of unprotected ranges within a protected sheet. Unprotected ranges are only supported on protected sheets. */
		unprotectedRanges: Option[List[Schema.GridRange]] = None,
	  /** The users and groups with edit access to the protected range. This field is only visible to users with edit access to the protected range and the document. Editors are not supported with warning_only protection. */
		editors: Option[Schema.Editors] = None
	)
	
	case class Editors(
	  /** The email addresses of users with edit access to the protected range. */
		users: Option[List[String]] = None,
	  /** The email addresses of groups with edit access to the protected range. */
		groups: Option[List[String]] = None,
	  /** True if anyone in the document's domain has edit access to the protected range. Domain protection is only supported on documents within a domain. */
		domainUsersCanEdit: Option[Boolean] = None
	)
	
	case class BasicFilter(
	  /** The range the filter covers. */
		range: Option[Schema.GridRange] = None,
	  /** The sort order per column. Later specifications are used when values are equal in the earlier specifications. */
		sortSpecs: Option[List[Schema.SortSpec]] = None,
	  /** The criteria for showing/hiding values per column. The map's key is the column index, and the value is the criteria for that column. This field is deprecated in favor of filter_specs. */
		criteria: Option[Map[String, Schema.FilterCriteria]] = None,
	  /** The filter criteria per column. Both criteria and filter_specs are populated in responses. If both fields are specified in an update request, this field takes precedence. */
		filterSpecs: Option[List[Schema.FilterSpec]] = None
	)
	
	case class EmbeddedChart(
	  /** The ID of the chart. */
		chartId: Option[Int] = None,
	  /** The specification of the chart. */
		spec: Option[Schema.ChartSpec] = None,
	  /** The position of the chart. */
		position: Option[Schema.EmbeddedObjectPosition] = None,
	  /** The border of the chart. */
		border: Option[Schema.EmbeddedObjectBorder] = None
	)
	
	object ChartSpec {
		enum HiddenDimensionStrategyEnum extends Enum[HiddenDimensionStrategyEnum] { case CHART_HIDDEN_DIMENSION_STRATEGY_UNSPECIFIED, SKIP_HIDDEN_ROWS_AND_COLUMNS, SKIP_HIDDEN_ROWS, SKIP_HIDDEN_COLUMNS, SHOW_ALL }
	}
	case class ChartSpec(
	  /** The title of the chart. */
		title: Option[String] = None,
	  /** The alternative text that describes the chart. This is often used for accessibility. */
		altText: Option[String] = None,
	  /** The title text format. Strikethrough, underline, and link are not supported. */
		titleTextFormat: Option[Schema.TextFormat] = None,
	  /** The title text position. This field is optional. */
		titleTextPosition: Option[Schema.TextPosition] = None,
	  /** The subtitle of the chart. */
		subtitle: Option[String] = None,
	  /** The subtitle text format. Strikethrough, underline, and link are not supported. */
		subtitleTextFormat: Option[Schema.TextFormat] = None,
	  /** The subtitle text position. This field is optional. */
		subtitleTextPosition: Option[Schema.TextPosition] = None,
	  /** The name of the font to use by default for all chart text (e.g. title, axis labels, legend). If a font is specified for a specific part of the chart it will override this font name. */
		fontName: Option[String] = None,
	  /** True to make a chart fill the entire space in which it's rendered with minimum padding. False to use the default padding. (Not applicable to Geo and Org charts.) */
		maximized: Option[Boolean] = None,
	  /** The background color of the entire chart. Not applicable to Org charts. Deprecated: Use background_color_style. */
		backgroundColor: Option[Schema.Color] = None,
	  /** The background color of the entire chart. Not applicable to Org charts. If background_color is also set, this field takes precedence. */
		backgroundColorStyle: Option[Schema.ColorStyle] = None,
	  /** If present, the field contains data source chart specific properties. */
		dataSourceChartProperties: Option[Schema.DataSourceChartProperties] = None,
	  /** The filters applied to the source data of the chart. Only supported for data source charts. */
		filterSpecs: Option[List[Schema.FilterSpec]] = None,
	  /** The order to sort the chart data by. Only a single sort spec is supported. Only supported for data source charts. */
		sortSpecs: Option[List[Schema.SortSpec]] = None,
	  /** A basic chart specification, can be one of many kinds of charts. See BasicChartType for the list of all charts this supports. */
		basicChart: Option[Schema.BasicChartSpec] = None,
	  /** A pie chart specification. */
		pieChart: Option[Schema.PieChartSpec] = None,
	  /** A bubble chart specification. */
		bubbleChart: Option[Schema.BubbleChartSpec] = None,
	  /** A candlestick chart specification. */
		candlestickChart: Option[Schema.CandlestickChartSpec] = None,
	  /** An org chart specification. */
		orgChart: Option[Schema.OrgChartSpec] = None,
	  /** A histogram chart specification. */
		histogramChart: Option[Schema.HistogramChartSpec] = None,
	  /** A waterfall chart specification. */
		waterfallChart: Option[Schema.WaterfallChartSpec] = None,
	  /** A treemap chart specification. */
		treemapChart: Option[Schema.TreemapChartSpec] = None,
	  /** A scorecard chart specification. */
		scorecardChart: Option[Schema.ScorecardChartSpec] = None,
	  /** Determines how the charts will use hidden rows or columns. */
		hiddenDimensionStrategy: Option[Schema.ChartSpec.HiddenDimensionStrategyEnum] = None
	)
	
	object TextPosition {
		enum HorizontalAlignmentEnum extends Enum[HorizontalAlignmentEnum] { case HORIZONTAL_ALIGN_UNSPECIFIED, LEFT, CENTER, RIGHT }
	}
	case class TextPosition(
	  /** Horizontal alignment setting for the piece of text. */
		horizontalAlignment: Option[Schema.TextPosition.HorizontalAlignmentEnum] = None
	)
	
	case class DataSourceChartProperties(
	  /** ID of the data source that the chart is associated with. */
		dataSourceId: Option[String] = None,
	  /** Output only. The data execution status. */
		dataExecutionStatus: Option[Schema.DataExecutionStatus] = None
	)
	
	object BasicChartSpec {
		enum ChartTypeEnum extends Enum[ChartTypeEnum] { case BASIC_CHART_TYPE_UNSPECIFIED, BAR, LINE, AREA, COLUMN, SCATTER, COMBO, STEPPED_AREA }
		enum LegendPositionEnum extends Enum[LegendPositionEnum] { case BASIC_CHART_LEGEND_POSITION_UNSPECIFIED, BOTTOM_LEGEND, LEFT_LEGEND, RIGHT_LEGEND, TOP_LEGEND, NO_LEGEND }
		enum StackedTypeEnum extends Enum[StackedTypeEnum] { case BASIC_CHART_STACKED_TYPE_UNSPECIFIED, NOT_STACKED, STACKED, PERCENT_STACKED }
		enum CompareModeEnum extends Enum[CompareModeEnum] { case BASIC_CHART_COMPARE_MODE_UNSPECIFIED, DATUM, CATEGORY }
	}
	case class BasicChartSpec(
	  /** The type of the chart. */
		chartType: Option[Schema.BasicChartSpec.ChartTypeEnum] = None,
	  /** The position of the chart legend. */
		legendPosition: Option[Schema.BasicChartSpec.LegendPositionEnum] = None,
	  /** The axis on the chart. */
		axis: Option[List[Schema.BasicChartAxis]] = None,
	  /** The domain of data this is charting. Only a single domain is supported. */
		domains: Option[List[Schema.BasicChartDomain]] = None,
	  /** The data this chart is visualizing. */
		series: Option[List[Schema.BasicChartSeries]] = None,
	  /** The number of rows or columns in the data that are "headers". If not set, Google Sheets will guess how many rows are headers based on the data. (Note that BasicChartAxis.title may override the axis title inferred from the header values.) */
		headerCount: Option[Int] = None,
	  /** True to make the chart 3D. Applies to Bar and Column charts. */
		threeDimensional: Option[Boolean] = None,
	  /** If some values in a series are missing, gaps may appear in the chart (e.g, segments of lines in a line chart will be missing). To eliminate these gaps set this to true. Applies to Line, Area, and Combo charts. */
		interpolateNulls: Option[Boolean] = None,
	  /** The stacked type for charts that support vertical stacking. Applies to Area, Bar, Column, Combo, and Stepped Area charts. */
		stackedType: Option[Schema.BasicChartSpec.StackedTypeEnum] = None,
	  /** Gets whether all lines should be rendered smooth or straight by default. Applies to Line charts. */
		lineSmoothing: Option[Boolean] = None,
	  /** The behavior of tooltips and data highlighting when hovering on data and chart area. */
		compareMode: Option[Schema.BasicChartSpec.CompareModeEnum] = None,
	  /** Controls whether to display additional data labels on stacked charts which sum the total value of all stacked values at each value along the domain axis. These data labels can only be set when chart_type is one of AREA, BAR, COLUMN, COMBO or STEPPED_AREA and stacked_type is either STACKED or PERCENT_STACKED. In addition, for COMBO, this will only be supported if there is only one type of stackable series type or one type has more series than the others and each of the other types have no more than one series. For example, if a chart has two stacked bar series and one area series, the total data labels will be supported. If it has three bar series and two area series, total data labels are not allowed. Neither CUSTOM nor placement can be set on the total_data_label. */
		totalDataLabel: Option[Schema.DataLabel] = None
	)
	
	object BasicChartAxis {
		enum PositionEnum extends Enum[PositionEnum] { case BASIC_CHART_AXIS_POSITION_UNSPECIFIED, BOTTOM_AXIS, LEFT_AXIS, RIGHT_AXIS }
	}
	case class BasicChartAxis(
	  /** The position of this axis. */
		position: Option[Schema.BasicChartAxis.PositionEnum] = None,
	  /** The title of this axis. If set, this overrides any title inferred from headers of the data. */
		title: Option[String] = None,
	  /** The format of the title. Only valid if the axis is not associated with the domain. The link field is not supported. */
		format: Option[Schema.TextFormat] = None,
	  /** The axis title text position. */
		titleTextPosition: Option[Schema.TextPosition] = None,
	  /** The view window options for this axis. */
		viewWindowOptions: Option[Schema.ChartAxisViewWindowOptions] = None
	)
	
	object ChartAxisViewWindowOptions {
		enum ViewWindowModeEnum extends Enum[ViewWindowModeEnum] { case DEFAULT_VIEW_WINDOW_MODE, VIEW_WINDOW_MODE_UNSUPPORTED, EXPLICIT, PRETTY }
	}
	case class ChartAxisViewWindowOptions(
	  /** The minimum numeric value to be shown in this view window. If unset, will automatically determine a minimum value that looks good for the data. */
		viewWindowMin: Option[BigDecimal] = None,
	  /** The maximum numeric value to be shown in this view window. If unset, will automatically determine a maximum value that looks good for the data. */
		viewWindowMax: Option[BigDecimal] = None,
	  /** The view window's mode. */
		viewWindowMode: Option[Schema.ChartAxisViewWindowOptions.ViewWindowModeEnum] = None
	)
	
	case class BasicChartDomain(
	  /** The data of the domain. For example, if charting stock prices over time, this is the data representing the dates. */
		domain: Option[Schema.ChartData] = None,
	  /** True to reverse the order of the domain values (horizontal axis). */
		reversed: Option[Boolean] = None
	)
	
	object ChartData {
		enum AggregateTypeEnum extends Enum[AggregateTypeEnum] { case CHART_AGGREGATE_TYPE_UNSPECIFIED, AVERAGE, COUNT, MAX, MEDIAN, MIN, SUM }
	}
	case class ChartData(
	  /** The source ranges of the data. */
		sourceRange: Option[Schema.ChartSourceRange] = None,
	  /** The reference to the data source column that the data reads from. */
		columnReference: Option[Schema.DataSourceColumnReference] = None,
	  /** The rule to group the data by if the ChartData backs the domain of a data source chart. Only supported for data source charts. */
		groupRule: Option[Schema.ChartGroupRule] = None,
	  /** The aggregation type for the series of a data source chart. Only supported for data source charts. */
		aggregateType: Option[Schema.ChartData.AggregateTypeEnum] = None
	)
	
	case class ChartSourceRange(
	  /** The ranges of data for a series or domain. Exactly one dimension must have a length of 1, and all sources in the list must have the same dimension with length 1. The domain (if it exists) & all series must have the same number of source ranges. If using more than one source range, then the source range at a given offset must be in order and contiguous across the domain and series. For example, these are valid configurations: domain sources: A1:A5 series1 sources: B1:B5 series2 sources: D6:D10 domain sources: A1:A5, C10:C12 series1 sources: B1:B5, D10:D12 series2 sources: C1:C5, E10:E12 */
		sources: Option[List[Schema.GridRange]] = None
	)
	
	case class ChartGroupRule(
	  /** A ChartDateTimeRule. */
		dateTimeRule: Option[Schema.ChartDateTimeRule] = None,
	  /** A ChartHistogramRule */
		histogramRule: Option[Schema.ChartHistogramRule] = None
	)
	
	object ChartDateTimeRule {
		enum TypeEnum extends Enum[TypeEnum] { case CHART_DATE_TIME_RULE_TYPE_UNSPECIFIED, SECOND, MINUTE, HOUR, HOUR_MINUTE, HOUR_MINUTE_AMPM, DAY_OF_WEEK, DAY_OF_YEAR, DAY_OF_MONTH, DAY_MONTH, MONTH, QUARTER, YEAR, YEAR_MONTH, YEAR_QUARTER, YEAR_MONTH_DAY }
	}
	case class ChartDateTimeRule(
	  /** The type of date-time grouping to apply. */
		`type`: Option[Schema.ChartDateTimeRule.TypeEnum] = None
	)
	
	case class ChartHistogramRule(
	  /** The minimum value at which items are placed into buckets. Values that are less than the minimum are grouped into a single bucket. If omitted, it is determined by the minimum item value. */
		minValue: Option[BigDecimal] = None,
	  /** The maximum value at which items are placed into buckets. Values greater than the maximum are grouped into a single bucket. If omitted, it is determined by the maximum item value. */
		maxValue: Option[BigDecimal] = None,
	  /** The size of the buckets that are created. Must be positive. */
		intervalSize: Option[BigDecimal] = None
	)
	
	object BasicChartSeries {
		enum TargetAxisEnum extends Enum[TargetAxisEnum] { case BASIC_CHART_AXIS_POSITION_UNSPECIFIED, BOTTOM_AXIS, LEFT_AXIS, RIGHT_AXIS }
		enum TypeEnum extends Enum[TypeEnum] { case BASIC_CHART_TYPE_UNSPECIFIED, BAR, LINE, AREA, COLUMN, SCATTER, COMBO, STEPPED_AREA }
	}
	case class BasicChartSeries(
	  /** The data being visualized in this chart series. */
		series: Option[Schema.ChartData] = None,
	  /** The minor axis that will specify the range of values for this series. For example, if charting stocks over time, the "Volume" series may want to be pinned to the right with the prices pinned to the left, because the scale of trading volume is different than the scale of prices. It is an error to specify an axis that isn't a valid minor axis for the chart's type. */
		targetAxis: Option[Schema.BasicChartSeries.TargetAxisEnum] = None,
	  /** The type of this series. Valid only if the chartType is COMBO. Different types will change the way the series is visualized. Only LINE, AREA, and COLUMN are supported. */
		`type`: Option[Schema.BasicChartSeries.TypeEnum] = None,
	  /** The line style of this series. Valid only if the chartType is AREA, LINE, or SCATTER. COMBO charts are also supported if the series chart type is AREA or LINE. */
		lineStyle: Option[Schema.LineStyle] = None,
	  /** Information about the data labels for this series. */
		dataLabel: Option[Schema.DataLabel] = None,
	  /** The color for elements (such as bars, lines, and points) associated with this series. If empty, a default color is used. Deprecated: Use color_style. */
		color: Option[Schema.Color] = None,
	  /** The color for elements (such as bars, lines, and points) associated with this series. If empty, a default color is used. If color is also set, this field takes precedence. */
		colorStyle: Option[Schema.ColorStyle] = None,
	  /** The style for points associated with this series. Valid only if the chartType is AREA, LINE, or SCATTER. COMBO charts are also supported if the series chart type is AREA, LINE, or SCATTER. If empty, a default point style is used. */
		pointStyle: Option[Schema.PointStyle] = None,
	  /** Style override settings for series data points. */
		styleOverrides: Option[List[Schema.BasicSeriesDataPointStyleOverride]] = None
	)
	
	object LineStyle {
		enum TypeEnum extends Enum[TypeEnum] { case LINE_DASH_TYPE_UNSPECIFIED, INVISIBLE, CUSTOM, SOLID, DOTTED, MEDIUM_DASHED, MEDIUM_DASHED_DOTTED, LONG_DASHED, LONG_DASHED_DOTTED }
	}
	case class LineStyle(
	  /** The thickness of the line, in px. */
		width: Option[Int] = None,
	  /** The dash type of the line. */
		`type`: Option[Schema.LineStyle.TypeEnum] = None
	)
	
	object DataLabel {
		enum TypeEnum extends Enum[TypeEnum] { case DATA_LABEL_TYPE_UNSPECIFIED, NONE, DATA, CUSTOM }
		enum PlacementEnum extends Enum[PlacementEnum] { case DATA_LABEL_PLACEMENT_UNSPECIFIED, CENTER, LEFT, RIGHT, ABOVE, BELOW, INSIDE_END, INSIDE_BASE, OUTSIDE_END }
	}
	case class DataLabel(
	  /** The type of the data label. */
		`type`: Option[Schema.DataLabel.TypeEnum] = None,
	  /** The text format used for the data label. The link field is not supported. */
		textFormat: Option[Schema.TextFormat] = None,
	  /** The placement of the data label relative to the labeled data. */
		placement: Option[Schema.DataLabel.PlacementEnum] = None,
	  /** Data to use for custom labels. Only used if type is set to CUSTOM. This data must be the same length as the series or other element this data label is applied to. In addition, if the series is split into multiple source ranges, this source data must come from the next column in the source data. For example, if the series is B2:B4,E6:E8 then this data must come from C2:C4,F6:F8. */
		customLabelData: Option[Schema.ChartData] = None
	)
	
	object PointStyle {
		enum ShapeEnum extends Enum[ShapeEnum] { case POINT_SHAPE_UNSPECIFIED, CIRCLE, DIAMOND, HEXAGON, PENTAGON, SQUARE, STAR, TRIANGLE, X_MARK }
	}
	case class PointStyle(
	  /** The point size. If empty, a default size is used. */
		size: Option[BigDecimal] = None,
	  /** The point shape. If empty or unspecified, a default shape is used. */
		shape: Option[Schema.PointStyle.ShapeEnum] = None
	)
	
	case class BasicSeriesDataPointStyleOverride(
	  /** The zero-based index of the series data point. */
		index: Option[Int] = None,
	  /** Color of the series data point. If empty, the series default is used. Deprecated: Use color_style. */
		color: Option[Schema.Color] = None,
	  /** Color of the series data point. If empty, the series default is used. If color is also set, this field takes precedence. */
		colorStyle: Option[Schema.ColorStyle] = None,
	  /** Point style of the series data point. Valid only if the chartType is AREA, LINE, or SCATTER. COMBO charts are also supported if the series chart type is AREA, LINE, or SCATTER. If empty, the series default is used. */
		pointStyle: Option[Schema.PointStyle] = None
	)
	
	object PieChartSpec {
		enum LegendPositionEnum extends Enum[LegendPositionEnum] { case PIE_CHART_LEGEND_POSITION_UNSPECIFIED, BOTTOM_LEGEND, LEFT_LEGEND, RIGHT_LEGEND, TOP_LEGEND, NO_LEGEND, LABELED_LEGEND }
	}
	case class PieChartSpec(
	  /** Where the legend of the pie chart should be drawn. */
		legendPosition: Option[Schema.PieChartSpec.LegendPositionEnum] = None,
	  /** The data that covers the domain of the pie chart. */
		domain: Option[Schema.ChartData] = None,
	  /** The data that covers the one and only series of the pie chart. */
		series: Option[Schema.ChartData] = None,
	  /** True if the pie is three dimensional. */
		threeDimensional: Option[Boolean] = None,
	  /** The size of the hole in the pie chart. */
		pieHole: Option[BigDecimal] = None
	)
	
	object BubbleChartSpec {
		enum LegendPositionEnum extends Enum[LegendPositionEnum] { case BUBBLE_CHART_LEGEND_POSITION_UNSPECIFIED, BOTTOM_LEGEND, LEFT_LEGEND, RIGHT_LEGEND, TOP_LEGEND, NO_LEGEND, INSIDE_LEGEND }
	}
	case class BubbleChartSpec(
	  /** Where the legend of the chart should be drawn. */
		legendPosition: Option[Schema.BubbleChartSpec.LegendPositionEnum] = None,
	  /** The data containing the bubble labels. These do not need to be unique. */
		bubbleLabels: Option[Schema.ChartData] = None,
	  /** The data containing the bubble x-values. These values locate the bubbles in the chart horizontally. */
		domain: Option[Schema.ChartData] = None,
	  /** The data containing the bubble y-values. These values locate the bubbles in the chart vertically. */
		series: Option[Schema.ChartData] = None,
	  /** The data containing the bubble group IDs. All bubbles with the same group ID are drawn in the same color. If bubble_sizes is specified then this field must also be specified but may contain blank values. This field is optional. */
		groupIds: Option[Schema.ChartData] = None,
	  /** The data containing the bubble sizes. Bubble sizes are used to draw the bubbles at different sizes relative to each other. If specified, group_ids must also be specified. This field is optional. */
		bubbleSizes: Option[Schema.ChartData] = None,
	  /** The opacity of the bubbles between 0 and 1.0. 0 is fully transparent and 1 is fully opaque. */
		bubbleOpacity: Option[BigDecimal] = None,
	  /** The bubble border color. Deprecated: Use bubble_border_color_style. */
		bubbleBorderColor: Option[Schema.Color] = None,
	  /** The bubble border color. If bubble_border_color is also set, this field takes precedence. */
		bubbleBorderColorStyle: Option[Schema.ColorStyle] = None,
	  /** The max radius size of the bubbles, in pixels. If specified, the field must be a positive value. */
		bubbleMaxRadiusSize: Option[Int] = None,
	  /** The minimum radius size of the bubbles, in pixels. If specific, the field must be a positive value. */
		bubbleMinRadiusSize: Option[Int] = None,
	  /** The format of the text inside the bubbles. Strikethrough, underline, and link are not supported. */
		bubbleTextStyle: Option[Schema.TextFormat] = None
	)
	
	case class CandlestickChartSpec(
	  /** The domain data (horizontal axis) for the candlestick chart. String data will be treated as discrete labels, other data will be treated as continuous values. */
		domain: Option[Schema.CandlestickDomain] = None,
	  /** The Candlestick chart data. Only one CandlestickData is supported. */
		data: Option[List[Schema.CandlestickData]] = None
	)
	
	case class CandlestickDomain(
	  /** The data of the CandlestickDomain. */
		data: Option[Schema.ChartData] = None,
	  /** True to reverse the order of the domain values (horizontal axis). */
		reversed: Option[Boolean] = None
	)
	
	case class CandlestickData(
	  /** The range data (vertical axis) for the low/minimum value for each candle. This is the bottom of the candle's center line. */
		lowSeries: Option[Schema.CandlestickSeries] = None,
	  /** The range data (vertical axis) for the open/initial value for each candle. This is the bottom of the candle body. If less than the close value the candle will be filled. Otherwise the candle will be hollow. */
		openSeries: Option[Schema.CandlestickSeries] = None,
	  /** The range data (vertical axis) for the close/final value for each candle. This is the top of the candle body. If greater than the open value the candle will be filled. Otherwise the candle will be hollow. */
		closeSeries: Option[Schema.CandlestickSeries] = None,
	  /** The range data (vertical axis) for the high/maximum value for each candle. This is the top of the candle's center line. */
		highSeries: Option[Schema.CandlestickSeries] = None
	)
	
	case class CandlestickSeries(
	  /** The data of the CandlestickSeries. */
		data: Option[Schema.ChartData] = None
	)
	
	object OrgChartSpec {
		enum NodeSizeEnum extends Enum[NodeSizeEnum] { case ORG_CHART_LABEL_SIZE_UNSPECIFIED, SMALL, MEDIUM, LARGE }
	}
	case class OrgChartSpec(
	  /** The size of the org chart nodes. */
		nodeSize: Option[Schema.OrgChartSpec.NodeSizeEnum] = None,
	  /** The color of the org chart nodes. Deprecated: Use node_color_style. */
		nodeColor: Option[Schema.Color] = None,
	  /** The color of the org chart nodes. If node_color is also set, this field takes precedence. */
		nodeColorStyle: Option[Schema.ColorStyle] = None,
	  /** The color of the selected org chart nodes. Deprecated: Use selected_node_color_style. */
		selectedNodeColor: Option[Schema.Color] = None,
	  /** The color of the selected org chart nodes. If selected_node_color is also set, this field takes precedence. */
		selectedNodeColorStyle: Option[Schema.ColorStyle] = None,
	  /** The data containing the labels for all the nodes in the chart. Labels must be unique. */
		labels: Option[Schema.ChartData] = None,
	  /** The data containing the label of the parent for the corresponding node. A blank value indicates that the node has no parent and is a top-level node. This field is optional. */
		parentLabels: Option[Schema.ChartData] = None,
	  /** The data containing the tooltip for the corresponding node. A blank value results in no tooltip being displayed for the node. This field is optional. */
		tooltips: Option[Schema.ChartData] = None
	)
	
	object HistogramChartSpec {
		enum LegendPositionEnum extends Enum[LegendPositionEnum] { case HISTOGRAM_CHART_LEGEND_POSITION_UNSPECIFIED, BOTTOM_LEGEND, LEFT_LEGEND, RIGHT_LEGEND, TOP_LEGEND, NO_LEGEND, INSIDE_LEGEND }
	}
	case class HistogramChartSpec(
	  /** The series for a histogram may be either a single series of values to be bucketed or multiple series, each of the same length, containing the name of the series followed by the values to be bucketed for that series. */
		series: Option[List[Schema.HistogramSeries]] = None,
	  /** The position of the chart legend. */
		legendPosition: Option[Schema.HistogramChartSpec.LegendPositionEnum] = None,
	  /** Whether horizontal divider lines should be displayed between items in each column. */
		showItemDividers: Option[Boolean] = None,
	  /** By default the bucket size (the range of values stacked in a single column) is chosen automatically, but it may be overridden here. E.g., A bucket size of 1.5 results in buckets from 0 - 1.5, 1.5 - 3.0, etc. Cannot be negative. This field is optional. */
		bucketSize: Option[BigDecimal] = None,
	  /** The outlier percentile is used to ensure that outliers do not adversely affect the calculation of bucket sizes. For example, setting an outlier percentile of 0.05 indicates that the top and bottom 5% of values when calculating buckets. The values are still included in the chart, they will be added to the first or last buckets instead of their own buckets. Must be between 0.0 and 0.5. */
		outlierPercentile: Option[BigDecimal] = None
	)
	
	case class HistogramSeries(
	  /** The color of the column representing this series in each bucket. This field is optional. Deprecated: Use bar_color_style. */
		barColor: Option[Schema.Color] = None,
	  /** The color of the column representing this series in each bucket. This field is optional. If bar_color is also set, this field takes precedence. */
		barColorStyle: Option[Schema.ColorStyle] = None,
	  /** The data for this histogram series. */
		data: Option[Schema.ChartData] = None
	)
	
	object WaterfallChartSpec {
		enum StackedTypeEnum extends Enum[StackedTypeEnum] { case WATERFALL_STACKED_TYPE_UNSPECIFIED, STACKED, SEQUENTIAL }
	}
	case class WaterfallChartSpec(
	  /** The domain data (horizontal axis) for the waterfall chart. */
		domain: Option[Schema.WaterfallChartDomain] = None,
	  /** The data this waterfall chart is visualizing. */
		series: Option[List[Schema.WaterfallChartSeries]] = None,
	  /** The stacked type. */
		stackedType: Option[Schema.WaterfallChartSpec.StackedTypeEnum] = None,
	  /** True to interpret the first value as a total. */
		firstValueIsTotal: Option[Boolean] = None,
	  /** True to hide connector lines between columns. */
		hideConnectorLines: Option[Boolean] = None,
	  /** The line style for the connector lines. */
		connectorLineStyle: Option[Schema.LineStyle] = None,
	  /** Controls whether to display additional data labels on stacked charts which sum the total value of all stacked values at each value along the domain axis. stacked_type must be STACKED and neither CUSTOM nor placement can be set on the total_data_label. */
		totalDataLabel: Option[Schema.DataLabel] = None
	)
	
	case class WaterfallChartDomain(
	  /** The data of the WaterfallChartDomain. */
		data: Option[Schema.ChartData] = None,
	  /** True to reverse the order of the domain values (horizontal axis). */
		reversed: Option[Boolean] = None
	)
	
	case class WaterfallChartSeries(
	  /** The data being visualized in this series. */
		data: Option[Schema.ChartData] = None,
	  /** Styles for all columns in this series with positive values. */
		positiveColumnsStyle: Option[Schema.WaterfallChartColumnStyle] = None,
	  /** Styles for all columns in this series with negative values. */
		negativeColumnsStyle: Option[Schema.WaterfallChartColumnStyle] = None,
	  /** Styles for all subtotal columns in this series. */
		subtotalColumnsStyle: Option[Schema.WaterfallChartColumnStyle] = None,
	  /** True to hide the subtotal column from the end of the series. By default, a subtotal column will appear at the end of each series. Setting this field to true will hide that subtotal column for this series. */
		hideTrailingSubtotal: Option[Boolean] = None,
	  /** Custom subtotal columns appearing in this series. The order in which subtotals are defined is not significant. Only one subtotal may be defined for each data point. */
		customSubtotals: Option[List[Schema.WaterfallChartCustomSubtotal]] = None,
	  /** Information about the data labels for this series. */
		dataLabel: Option[Schema.DataLabel] = None
	)
	
	case class WaterfallChartColumnStyle(
	  /** The label of the column's legend. */
		label: Option[String] = None,
	  /** The color of the column. Deprecated: Use color_style. */
		color: Option[Schema.Color] = None,
	  /** The color of the column. If color is also set, this field takes precedence. */
		colorStyle: Option[Schema.ColorStyle] = None
	)
	
	case class WaterfallChartCustomSubtotal(
	  /** The zero-based index of a data point within the series. If data_is_subtotal is true, the data point at this index is the subtotal. Otherwise, the subtotal appears after the data point with this index. A series can have multiple subtotals at arbitrary indices, but subtotals do not affect the indices of the data points. For example, if a series has three data points, their indices will always be 0, 1, and 2, regardless of how many subtotals exist on the series or what data points they are associated with. */
		subtotalIndex: Option[Int] = None,
	  /** A label for the subtotal column. */
		label: Option[String] = None,
	  /** True if the data point at subtotal_index is the subtotal. If false, the subtotal will be computed and appear after the data point. */
		dataIsSubtotal: Option[Boolean] = None
	)
	
	case class TreemapChartSpec(
	  /** The data that contains the treemap cell labels. */
		labels: Option[Schema.ChartData] = None,
	  /** The data the contains the treemap cells' parent labels. */
		parentLabels: Option[Schema.ChartData] = None,
	  /** The data that determines the size of each treemap data cell. This data is expected to be numeric. The cells corresponding to non-numeric or missing data will not be rendered. If color_data is not specified, this data is used to determine data cell background colors as well. */
		sizeData: Option[Schema.ChartData] = None,
	  /** The data that determines the background color of each treemap data cell. This field is optional. If not specified, size_data is used to determine background colors. If specified, the data is expected to be numeric. color_scale will determine how the values in this data map to data cell background colors. */
		colorData: Option[Schema.ChartData] = None,
	  /** The text format for all labels on the chart. The link field is not supported. */
		textFormat: Option[Schema.TextFormat] = None,
	  /** The number of data levels to show on the treemap chart. These levels are interactive and are shown with their labels. Defaults to 2 if not specified. */
		levels: Option[Int] = None,
	  /** The number of additional data levels beyond the labeled levels to be shown on the treemap chart. These levels are not interactive and are shown without their labels. Defaults to 0 if not specified. */
		hintedLevels: Option[Int] = None,
	  /** The minimum possible data value. Cells with values less than this will have the same color as cells with this value. If not specified, defaults to the actual minimum value from color_data, or the minimum value from size_data if color_data is not specified. */
		minValue: Option[BigDecimal] = None,
	  /** The maximum possible data value. Cells with values greater than this will have the same color as cells with this value. If not specified, defaults to the actual maximum value from color_data, or the maximum value from size_data if color_data is not specified. */
		maxValue: Option[BigDecimal] = None,
	  /** The background color for header cells. Deprecated: Use header_color_style. */
		headerColor: Option[Schema.Color] = None,
	  /** The background color for header cells. If header_color is also set, this field takes precedence. */
		headerColorStyle: Option[Schema.ColorStyle] = None,
	  /** The color scale for data cells in the treemap chart. Data cells are assigned colors based on their color values. These color values come from color_data, or from size_data if color_data is not specified. Cells with color values less than or equal to min_value will have minValueColor as their background color. Cells with color values greater than or equal to max_value will have maxValueColor as their background color. Cells with color values between min_value and max_value will have background colors on a gradient between minValueColor and maxValueColor, the midpoint of the gradient being midValueColor. Cells with missing or non-numeric color values will have noDataColor as their background color. */
		colorScale: Option[Schema.TreemapChartColorScale] = None,
	  /** True to hide tooltips. */
		hideTooltips: Option[Boolean] = None
	)
	
	case class TreemapChartColorScale(
	  /** The background color for cells with a color value less than or equal to minValue. Defaults to #dc3912 if not specified. Deprecated: Use min_value_color_style. */
		minValueColor: Option[Schema.Color] = None,
	  /** The background color for cells with a color value less than or equal to minValue. Defaults to #dc3912 if not specified. If min_value_color is also set, this field takes precedence. */
		minValueColorStyle: Option[Schema.ColorStyle] = None,
	  /** The background color for cells with a color value at the midpoint between minValue and maxValue. Defaults to #efe6dc if not specified. Deprecated: Use mid_value_color_style. */
		midValueColor: Option[Schema.Color] = None,
	  /** The background color for cells with a color value at the midpoint between minValue and maxValue. Defaults to #efe6dc if not specified. If mid_value_color is also set, this field takes precedence. */
		midValueColorStyle: Option[Schema.ColorStyle] = None,
	  /** The background color for cells with a color value greater than or equal to maxValue. Defaults to #109618 if not specified. Deprecated: Use max_value_color_style. */
		maxValueColor: Option[Schema.Color] = None,
	  /** The background color for cells with a color value greater than or equal to maxValue. Defaults to #109618 if not specified. If max_value_color is also set, this field takes precedence. */
		maxValueColorStyle: Option[Schema.ColorStyle] = None,
	  /** The background color for cells that have no color data associated with them. Defaults to #000000 if not specified. Deprecated: Use no_data_color_style. */
		noDataColor: Option[Schema.Color] = None,
	  /** The background color for cells that have no color data associated with them. Defaults to #000000 if not specified. If no_data_color is also set, this field takes precedence. */
		noDataColorStyle: Option[Schema.ColorStyle] = None
	)
	
	object ScorecardChartSpec {
		enum AggregateTypeEnum extends Enum[AggregateTypeEnum] { case CHART_AGGREGATE_TYPE_UNSPECIFIED, AVERAGE, COUNT, MAX, MEDIAN, MIN, SUM }
		enum NumberFormatSourceEnum extends Enum[NumberFormatSourceEnum] { case CHART_NUMBER_FORMAT_SOURCE_UNDEFINED, FROM_DATA, CUSTOM }
	}
	case class ScorecardChartSpec(
	  /** The data for scorecard key value. */
		keyValueData: Option[Schema.ChartData] = None,
	  /** The data for scorecard baseline value. This field is optional. */
		baselineValueData: Option[Schema.ChartData] = None,
	  /** The aggregation type for key and baseline chart data in scorecard chart. This field is not supported for data source charts. Use the ChartData.aggregateType field of the key_value_data or baseline_value_data instead for data source charts. This field is optional. */
		aggregateType: Option[Schema.ScorecardChartSpec.AggregateTypeEnum] = None,
	  /** Formatting options for key value. */
		keyValueFormat: Option[Schema.KeyValueFormat] = None,
	  /** Formatting options for baseline value. This field is needed only if baseline_value_data is specified. */
		baselineValueFormat: Option[Schema.BaselineValueFormat] = None,
	  /** Value to scale scorecard key and baseline value. For example, a factor of 10 can be used to divide all values in the chart by 10. This field is optional. */
		scaleFactor: Option[BigDecimal] = None,
	  /** The number format source used in the scorecard chart. This field is optional. */
		numberFormatSource: Option[Schema.ScorecardChartSpec.NumberFormatSourceEnum] = None,
	  /** Custom formatting options for numeric key/baseline values in scorecard chart. This field is used only when number_format_source is set to CUSTOM. This field is optional. */
		customFormatOptions: Option[Schema.ChartCustomNumberFormatOptions] = None
	)
	
	case class KeyValueFormat(
	  /** Text formatting options for key value. The link field is not supported. */
		textFormat: Option[Schema.TextFormat] = None,
	  /** Specifies the horizontal text positioning of key value. This field is optional. If not specified, default positioning is used. */
		position: Option[Schema.TextPosition] = None
	)
	
	object BaselineValueFormat {
		enum ComparisonTypeEnum extends Enum[ComparisonTypeEnum] { case COMPARISON_TYPE_UNDEFINED, ABSOLUTE_DIFFERENCE, PERCENTAGE_DIFFERENCE }
	}
	case class BaselineValueFormat(
	  /** The comparison type of key value with baseline value. */
		comparisonType: Option[Schema.BaselineValueFormat.ComparisonTypeEnum] = None,
	  /** Text formatting options for baseline value. The link field is not supported. */
		textFormat: Option[Schema.TextFormat] = None,
	  /** Specifies the horizontal text positioning of baseline value. This field is optional. If not specified, default positioning is used. */
		position: Option[Schema.TextPosition] = None,
	  /** Description which is appended after the baseline value. This field is optional. */
		description: Option[String] = None,
	  /** Color to be used, in case baseline value represents a positive change for key value. This field is optional. Deprecated: Use positive_color_style. */
		positiveColor: Option[Schema.Color] = None,
	  /** Color to be used, in case baseline value represents a positive change for key value. This field is optional. If positive_color is also set, this field takes precedence. */
		positiveColorStyle: Option[Schema.ColorStyle] = None,
	  /** Color to be used, in case baseline value represents a negative change for key value. This field is optional. Deprecated: Use negative_color_style. */
		negativeColor: Option[Schema.Color] = None,
	  /** Color to be used, in case baseline value represents a negative change for key value. This field is optional. If negative_color is also set, this field takes precedence. */
		negativeColorStyle: Option[Schema.ColorStyle] = None
	)
	
	case class ChartCustomNumberFormatOptions(
	  /** Custom prefix to be prepended to the chart attribute. This field is optional. */
		prefix: Option[String] = None,
	  /** Custom suffix to be appended to the chart attribute. This field is optional. */
		suffix: Option[String] = None
	)
	
	case class EmbeddedObjectPosition(
	  /** The sheet this is on. Set only if the embedded object is on its own sheet. Must be non-negative. */
		sheetId: Option[Int] = None,
	  /** The position at which the object is overlaid on top of a grid. */
		overlayPosition: Option[Schema.OverlayPosition] = None,
	  /** If true, the embedded object is put on a new sheet whose ID is chosen for you. Used only when writing. */
		newSheet: Option[Boolean] = None
	)
	
	case class OverlayPosition(
	  /** The cell the object is anchored to. */
		anchorCell: Option[Schema.GridCoordinate] = None,
	  /** The horizontal offset, in pixels, that the object is offset from the anchor cell. */
		offsetXPixels: Option[Int] = None,
	  /** The vertical offset, in pixels, that the object is offset from the anchor cell. */
		offsetYPixels: Option[Int] = None,
	  /** The width of the object, in pixels. Defaults to 600. */
		widthPixels: Option[Int] = None,
	  /** The height of the object, in pixels. Defaults to 371. */
		heightPixels: Option[Int] = None
	)
	
	case class GridCoordinate(
	  /** The sheet this coordinate is on. */
		sheetId: Option[Int] = None,
	  /** The row index of the coordinate. */
		rowIndex: Option[Int] = None,
	  /** The column index of the coordinate. */
		columnIndex: Option[Int] = None
	)
	
	case class EmbeddedObjectBorder(
	  /** The color of the border. Deprecated: Use color_style. */
		color: Option[Schema.Color] = None,
	  /** The color of the border. If color is also set, this field takes precedence. */
		colorStyle: Option[Schema.ColorStyle] = None
	)
	
	case class BandedRange(
	  /** The ID of the banded range. */
		bandedRangeId: Option[Int] = None,
	  /** The range over which these properties are applied. */
		range: Option[Schema.GridRange] = None,
	  /** Properties for row bands. These properties are applied on a row-by-row basis throughout all the rows in the range. At least one of row_properties or column_properties must be specified. */
		rowProperties: Option[Schema.BandingProperties] = None,
	  /** Properties for column bands. These properties are applied on a column- by-column basis throughout all the columns in the range. At least one of row_properties or column_properties must be specified. */
		columnProperties: Option[Schema.BandingProperties] = None
	)
	
	case class BandingProperties(
	  /** The color of the first row or column. If this field is set, the first row or column is filled with this color and the colors alternate between first_band_color and second_band_color starting from the second row or column. Otherwise, the first row or column is filled with first_band_color and the colors proceed to alternate as they normally would. Deprecated: Use header_color_style. */
		headerColor: Option[Schema.Color] = None,
	  /** The color of the first row or column. If this field is set, the first row or column is filled with this color and the colors alternate between first_band_color and second_band_color starting from the second row or column. Otherwise, the first row or column is filled with first_band_color and the colors proceed to alternate as they normally would. If header_color is also set, this field takes precedence. */
		headerColorStyle: Option[Schema.ColorStyle] = None,
	  /** The first color that is alternating. (Required) Deprecated: Use first_band_color_style. */
		firstBandColor: Option[Schema.Color] = None,
	  /** The first color that is alternating. (Required) If first_band_color is also set, this field takes precedence. */
		firstBandColorStyle: Option[Schema.ColorStyle] = None,
	  /** The second color that is alternating. (Required) Deprecated: Use second_band_color_style. */
		secondBandColor: Option[Schema.Color] = None,
	  /** The second color that is alternating. (Required) If second_band_color is also set, this field takes precedence. */
		secondBandColorStyle: Option[Schema.ColorStyle] = None,
	  /** The color of the last row or column. If this field is not set, the last row or column is filled with either first_band_color or second_band_color, depending on the color of the previous row or column. Deprecated: Use footer_color_style. */
		footerColor: Option[Schema.Color] = None,
	  /** The color of the last row or column. If this field is not set, the last row or column is filled with either first_band_color or second_band_color, depending on the color of the previous row or column. If footer_color is also set, this field takes precedence. */
		footerColorStyle: Option[Schema.ColorStyle] = None
	)
	
	case class DimensionGroup(
	  /** The range over which this group exists. */
		range: Option[Schema.DimensionRange] = None,
	  /** The depth of the group, representing how many groups have a range that wholly contains the range of this group. */
		depth: Option[Int] = None,
	  /** This field is true if this group is collapsed. A collapsed group remains collapsed if an overlapping group at a shallower depth is expanded. A true value does not imply that all dimensions within the group are hidden, since a dimension's visibility can change independently from this group property. However, when this property is updated, all dimensions within it are set to hidden if this field is true, or set to visible if this field is false. */
		collapsed: Option[Boolean] = None
	)
	
	case class Slicer(
	  /** The ID of the slicer. */
		slicerId: Option[Int] = None,
	  /** The specification of the slicer. */
		spec: Option[Schema.SlicerSpec] = None,
	  /** The position of the slicer. Note that slicer can be positioned only on existing sheet. Also, width and height of slicer can be automatically adjusted to keep it within permitted limits. */
		position: Option[Schema.EmbeddedObjectPosition] = None
	)
	
	object SlicerSpec {
		enum HorizontalAlignmentEnum extends Enum[HorizontalAlignmentEnum] { case HORIZONTAL_ALIGN_UNSPECIFIED, LEFT, CENTER, RIGHT }
	}
	case class SlicerSpec(
	  /** The data range of the slicer. */
		dataRange: Option[Schema.GridRange] = None,
	  /** The filtering criteria of the slicer. */
		filterCriteria: Option[Schema.FilterCriteria] = None,
	  /** The zero-based column index in the data table on which the filter is applied to. */
		columnIndex: Option[Int] = None,
	  /** True if the filter should apply to pivot tables. If not set, default to `True`. */
		applyToPivotTables: Option[Boolean] = None,
	  /** The title of the slicer. */
		title: Option[String] = None,
	  /** The text format of title in the slicer. The link field is not supported. */
		textFormat: Option[Schema.TextFormat] = None,
	  /** The background color of the slicer. Deprecated: Use background_color_style. */
		backgroundColor: Option[Schema.Color] = None,
	  /** The background color of the slicer. If background_color is also set, this field takes precedence. */
		backgroundColorStyle: Option[Schema.ColorStyle] = None,
	  /** The horizontal alignment of title in the slicer. If unspecified, defaults to `LEFT` */
		horizontalAlignment: Option[Schema.SlicerSpec.HorizontalAlignmentEnum] = None
	)
	
	case class NamedRange(
	  /** The ID of the named range. */
		namedRangeId: Option[String] = None,
	  /** The name of the named range. */
		name: Option[String] = None,
	  /** The range this represents. */
		range: Option[Schema.GridRange] = None
	)
	
	case class DataSource(
	  /** The spreadsheet-scoped unique ID that identifies the data source. Example: 1080547365. */
		dataSourceId: Option[String] = None,
	  /** The DataSourceSpec for the data source connected with this spreadsheet. */
		spec: Option[Schema.DataSourceSpec] = None,
	  /** All calculated columns in the data source. */
		calculatedColumns: Option[List[Schema.DataSourceColumn]] = None,
	  /** The ID of the Sheet connected with the data source. The field cannot be changed once set. When creating a data source, an associated DATA_SOURCE sheet is also created, if the field is not specified, the ID of the created sheet will be randomly generated. */
		sheetId: Option[Int] = None
	)
	
	case class DataSourceSpec(
	  /** A BigQueryDataSourceSpec. */
		bigQuery: Option[Schema.BigQueryDataSourceSpec] = None,
	  /** A LookerDatasourceSpec. */
		looker: Option[Schema.LookerDataSourceSpec] = None,
	  /** The parameters of the data source, used when querying the data source. */
		parameters: Option[List[Schema.DataSourceParameter]] = None
	)
	
	case class BigQueryDataSourceSpec(
	  /** The ID of a BigQuery enabled Google Cloud project with a billing account attached. For any queries executed against the data source, the project is charged. */
		projectId: Option[String] = None,
	  /** A BigQueryQuerySpec. */
		querySpec: Option[Schema.BigQueryQuerySpec] = None,
	  /** A BigQueryTableSpec. */
		tableSpec: Option[Schema.BigQueryTableSpec] = None
	)
	
	case class BigQueryQuerySpec(
	  /** The raw query string. */
		rawQuery: Option[String] = None
	)
	
	case class BigQueryTableSpec(
	  /** The ID of a BigQuery project the table belongs to. If not specified, the project_id is assumed. */
		tableProjectId: Option[String] = None,
	  /** The BigQuery table id. */
		tableId: Option[String] = None,
	  /** The BigQuery dataset id. */
		datasetId: Option[String] = None
	)
	
	case class LookerDataSourceSpec(
	  /** A Looker instance URL. */
		instanceUri: Option[String] = None,
	  /** Name of a Looker model. */
		model: Option[String] = None,
	  /** Name of a Looker model explore. */
		explore: Option[String] = None
	)
	
	case class DataSourceParameter(
	  /** Named parameter. Must be a legitimate identifier for the DataSource that supports it. For example, [BigQuery identifier](https://cloud.google.com/bigquery/docs/reference/standard-sql/lexical#identifiers). */
		name: Option[String] = None,
	  /** ID of a NamedRange. Its size must be 1x1. */
		namedRangeId: Option[String] = None,
	  /** A range that contains the value of the parameter. Its size must be 1x1. */
		range: Option[Schema.GridRange] = None
	)
	
	object DataSourceRefreshSchedule {
		enum RefreshScopeEnum extends Enum[RefreshScopeEnum] { case DATA_SOURCE_REFRESH_SCOPE_UNSPECIFIED, ALL_DATA_SOURCES }
	}
	case class DataSourceRefreshSchedule(
	  /** True if the refresh schedule is enabled, or false otherwise. */
		enabled: Option[Boolean] = None,
	  /** The scope of the refresh. Must be ALL_DATA_SOURCES. */
		refreshScope: Option[Schema.DataSourceRefreshSchedule.RefreshScopeEnum] = None,
	  /** Daily refresh schedule. */
		dailySchedule: Option[Schema.DataSourceRefreshDailySchedule] = None,
	  /** Weekly refresh schedule. */
		weeklySchedule: Option[Schema.DataSourceRefreshWeeklySchedule] = None,
	  /** Monthly refresh schedule. */
		monthlySchedule: Option[Schema.DataSourceRefreshMonthlySchedule] = None,
	  /** Output only. The time interval of the next run. */
		nextRun: Option[Schema.Interval] = None
	)
	
	case class DataSourceRefreshDailySchedule(
	  /** The start time of a time interval in which a data source refresh is scheduled. Only `hours` part is used. The time interval size defaults to that in the Sheets editor. */
		startTime: Option[Schema.TimeOfDay] = None
	)
	
	case class TimeOfDay(
	  /** Hours of a day in 24 hour format. Must be greater than or equal to 0 and typically must be less than or equal to 23. An API may choose to allow the value "24:00:00" for scenarios like business closing time. */
		hours: Option[Int] = None,
	  /** Minutes of an hour. Must be greater than or equal to 0 and less than or equal to 59. */
		minutes: Option[Int] = None,
	  /** Seconds of a minute. Must be greater than or equal to 0 and typically must be less than or equal to 59. An API may allow the value 60 if it allows leap-seconds. */
		seconds: Option[Int] = None,
	  /** Fractions of seconds, in nanoseconds. Must be greater than or equal to 0 and less than or equal to 999,999,999. */
		nanos: Option[Int] = None
	)
	
	object DataSourceRefreshWeeklySchedule {
		enum DaysOfWeekEnum extends Enum[DaysOfWeekEnum] { case DAY_OF_WEEK_UNSPECIFIED, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
	}
	case class DataSourceRefreshWeeklySchedule(
	  /** The start time of a time interval in which a data source refresh is scheduled. Only `hours` part is used. The time interval size defaults to that in the Sheets editor. */
		startTime: Option[Schema.TimeOfDay] = None,
	  /** Days of the week to refresh. At least one day must be specified. */
		daysOfWeek: Option[List[Schema.DataSourceRefreshWeeklySchedule.DaysOfWeekEnum]] = None
	)
	
	case class DataSourceRefreshMonthlySchedule(
	  /** The start time of a time interval in which a data source refresh is scheduled. Only `hours` part is used. The time interval size defaults to that in the Sheets editor. */
		startTime: Option[Schema.TimeOfDay] = None,
	  /** Days of the month to refresh. Only 1-28 are supported, mapping to the 1st to the 28th day. At least one day must be specified. */
		daysOfMonth: Option[List[Int]] = None
	)
	
	case class Interval(
	  /** Optional. Inclusive start of the interval. If specified, a Timestamp matching this interval will have to be the same or after the start. */
		startTime: Option[String] = None,
	  /** Optional. Exclusive end of the interval. If specified, a Timestamp matching this interval will have to be before the end. */
		endTime: Option[String] = None
	)
	
	case class GetSpreadsheetByDataFilterRequest(
	  /** The DataFilters used to select which ranges to retrieve from the spreadsheet. */
		dataFilters: Option[List[Schema.DataFilter]] = None,
	  /** True if grid data should be returned. This parameter is ignored if a field mask was set in the request. */
		includeGridData: Option[Boolean] = None
	)
	
	case class BatchUpdateSpreadsheetRequest(
	  /** A list of updates to apply to the spreadsheet. Requests will be applied in the order they are specified. If any request is not valid, no requests will be applied. */
		requests: Option[List[Schema.Request]] = None,
	  /** Determines if the update response should include the spreadsheet resource. */
		includeSpreadsheetInResponse: Option[Boolean] = None,
	  /** Limits the ranges included in the response spreadsheet. Meaningful only if include_spreadsheet_in_response is 'true'. */
		responseRanges: Option[List[String]] = None,
	  /** True if grid data should be returned. Meaningful only if include_spreadsheet_in_response is 'true'. This parameter is ignored if a field mask was set in the request. */
		responseIncludeGridData: Option[Boolean] = None
	)
	
	case class Request(
	  /** Updates the spreadsheet's properties. */
		updateSpreadsheetProperties: Option[Schema.UpdateSpreadsheetPropertiesRequest] = None,
	  /** Updates a sheet's properties. */
		updateSheetProperties: Option[Schema.UpdateSheetPropertiesRequest] = None,
	  /** Updates dimensions' properties. */
		updateDimensionProperties: Option[Schema.UpdateDimensionPropertiesRequest] = None,
	  /** Updates a named range. */
		updateNamedRange: Option[Schema.UpdateNamedRangeRequest] = None,
	  /** Repeats a single cell across a range. */
		repeatCell: Option[Schema.RepeatCellRequest] = None,
	  /** Adds a named range. */
		addNamedRange: Option[Schema.AddNamedRangeRequest] = None,
	  /** Deletes a named range. */
		deleteNamedRange: Option[Schema.DeleteNamedRangeRequest] = None,
	  /** Adds a sheet. */
		addSheet: Option[Schema.AddSheetRequest] = None,
	  /** Deletes a sheet. */
		deleteSheet: Option[Schema.DeleteSheetRequest] = None,
	  /** Automatically fills in more data based on existing data. */
		autoFill: Option[Schema.AutoFillRequest] = None,
	  /** Cuts data from one area and pastes it to another. */
		cutPaste: Option[Schema.CutPasteRequest] = None,
	  /** Copies data from one area and pastes it to another. */
		copyPaste: Option[Schema.CopyPasteRequest] = None,
	  /** Merges cells together. */
		mergeCells: Option[Schema.MergeCellsRequest] = None,
	  /** Unmerges merged cells. */
		unmergeCells: Option[Schema.UnmergeCellsRequest] = None,
	  /** Updates the borders in a range of cells. */
		updateBorders: Option[Schema.UpdateBordersRequest] = None,
	  /** Updates many cells at once. */
		updateCells: Option[Schema.UpdateCellsRequest] = None,
	  /** Adds a filter view. */
		addFilterView: Option[Schema.AddFilterViewRequest] = None,
	  /** Appends cells after the last row with data in a sheet. */
		appendCells: Option[Schema.AppendCellsRequest] = None,
	  /** Clears the basic filter on a sheet. */
		clearBasicFilter: Option[Schema.ClearBasicFilterRequest] = None,
	  /** Deletes rows or columns in a sheet. */
		deleteDimension: Option[Schema.DeleteDimensionRequest] = None,
	  /** Deletes an embedded object (e.g, chart, image) in a sheet. */
		deleteEmbeddedObject: Option[Schema.DeleteEmbeddedObjectRequest] = None,
	  /** Deletes a filter view from a sheet. */
		deleteFilterView: Option[Schema.DeleteFilterViewRequest] = None,
	  /** Duplicates a filter view. */
		duplicateFilterView: Option[Schema.DuplicateFilterViewRequest] = None,
	  /** Duplicates a sheet. */
		duplicateSheet: Option[Schema.DuplicateSheetRequest] = None,
	  /** Finds and replaces occurrences of some text with other text. */
		findReplace: Option[Schema.FindReplaceRequest] = None,
	  /** Inserts new rows or columns in a sheet. */
		insertDimension: Option[Schema.InsertDimensionRequest] = None,
	  /** Inserts new cells in a sheet, shifting the existing cells. */
		insertRange: Option[Schema.InsertRangeRequest] = None,
	  /** Moves rows or columns to another location in a sheet. */
		moveDimension: Option[Schema.MoveDimensionRequest] = None,
	  /** Updates an embedded object's (e.g. chart, image) position. */
		updateEmbeddedObjectPosition: Option[Schema.UpdateEmbeddedObjectPositionRequest] = None,
	  /** Pastes data (HTML or delimited) into a sheet. */
		pasteData: Option[Schema.PasteDataRequest] = None,
	  /** Converts a column of text into many columns of text. */
		textToColumns: Option[Schema.TextToColumnsRequest] = None,
	  /** Updates the properties of a filter view. */
		updateFilterView: Option[Schema.UpdateFilterViewRequest] = None,
	  /** Deletes a range of cells from a sheet, shifting the remaining cells. */
		deleteRange: Option[Schema.DeleteRangeRequest] = None,
	  /** Appends dimensions to the end of a sheet. */
		appendDimension: Option[Schema.AppendDimensionRequest] = None,
	  /** Adds a new conditional format rule. */
		addConditionalFormatRule: Option[Schema.AddConditionalFormatRuleRequest] = None,
	  /** Updates an existing conditional format rule. */
		updateConditionalFormatRule: Option[Schema.UpdateConditionalFormatRuleRequest] = None,
	  /** Deletes an existing conditional format rule. */
		deleteConditionalFormatRule: Option[Schema.DeleteConditionalFormatRuleRequest] = None,
	  /** Sorts data in a range. */
		sortRange: Option[Schema.SortRangeRequest] = None,
	  /** Sets data validation for one or more cells. */
		setDataValidation: Option[Schema.SetDataValidationRequest] = None,
	  /** Sets the basic filter on a sheet. */
		setBasicFilter: Option[Schema.SetBasicFilterRequest] = None,
	  /** Adds a protected range. */
		addProtectedRange: Option[Schema.AddProtectedRangeRequest] = None,
	  /** Updates a protected range. */
		updateProtectedRange: Option[Schema.UpdateProtectedRangeRequest] = None,
	  /** Deletes a protected range. */
		deleteProtectedRange: Option[Schema.DeleteProtectedRangeRequest] = None,
	  /** Automatically resizes one or more dimensions based on the contents of the cells in that dimension. */
		autoResizeDimensions: Option[Schema.AutoResizeDimensionsRequest] = None,
	  /** Adds a chart. */
		addChart: Option[Schema.AddChartRequest] = None,
	  /** Updates a chart's specifications. */
		updateChartSpec: Option[Schema.UpdateChartSpecRequest] = None,
	  /** Updates a banded range */
		updateBanding: Option[Schema.UpdateBandingRequest] = None,
	  /** Adds a new banded range */
		addBanding: Option[Schema.AddBandingRequest] = None,
	  /** Removes a banded range */
		deleteBanding: Option[Schema.DeleteBandingRequest] = None,
	  /** Creates new developer metadata */
		createDeveloperMetadata: Option[Schema.CreateDeveloperMetadataRequest] = None,
	  /** Updates an existing developer metadata entry */
		updateDeveloperMetadata: Option[Schema.UpdateDeveloperMetadataRequest] = None,
	  /** Deletes developer metadata */
		deleteDeveloperMetadata: Option[Schema.DeleteDeveloperMetadataRequest] = None,
	  /** Randomizes the order of the rows in a range. */
		randomizeRange: Option[Schema.RandomizeRangeRequest] = None,
	  /** Creates a group over the specified range. */
		addDimensionGroup: Option[Schema.AddDimensionGroupRequest] = None,
	  /** Deletes a group over the specified range. */
		deleteDimensionGroup: Option[Schema.DeleteDimensionGroupRequest] = None,
	  /** Updates the state of the specified group. */
		updateDimensionGroup: Option[Schema.UpdateDimensionGroupRequest] = None,
	  /** Trims cells of whitespace (such as spaces, tabs, or new lines). */
		trimWhitespace: Option[Schema.TrimWhitespaceRequest] = None,
	  /** Removes rows containing duplicate values in specified columns of a cell range. */
		deleteDuplicates: Option[Schema.DeleteDuplicatesRequest] = None,
	  /** Updates an embedded object's border. */
		updateEmbeddedObjectBorder: Option[Schema.UpdateEmbeddedObjectBorderRequest] = None,
	  /** Adds a slicer. */
		addSlicer: Option[Schema.AddSlicerRequest] = None,
	  /** Updates a slicer's specifications. */
		updateSlicerSpec: Option[Schema.UpdateSlicerSpecRequest] = None,
	  /** Adds a data source. */
		addDataSource: Option[Schema.AddDataSourceRequest] = None,
	  /** Updates a data source. */
		updateDataSource: Option[Schema.UpdateDataSourceRequest] = None,
	  /** Deletes a data source. */
		deleteDataSource: Option[Schema.DeleteDataSourceRequest] = None,
	  /** Refreshes one or multiple data sources and associated dbobjects. */
		refreshDataSource: Option[Schema.RefreshDataSourceRequest] = None,
	  /** Cancels refreshes of one or multiple data sources and associated dbobjects. */
		cancelDataSourceRefresh: Option[Schema.CancelDataSourceRefreshRequest] = None
	)
	
	case class UpdateSpreadsheetPropertiesRequest(
	  /** The properties to update. */
		properties: Option[Schema.SpreadsheetProperties] = None,
	  /** The fields that should be updated. At least one field must be specified. The root 'properties' is implied and should not be specified. A single `"&#42;"` can be used as short-hand for listing every field. */
		fields: Option[String] = None
	)
	
	case class UpdateSheetPropertiesRequest(
	  /** The properties to update. */
		properties: Option[Schema.SheetProperties] = None,
	  /** The fields that should be updated. At least one field must be specified. The root `properties` is implied and should not be specified. A single `"&#42;"` can be used as short-hand for listing every field. */
		fields: Option[String] = None
	)
	
	case class UpdateDimensionPropertiesRequest(
	  /** The rows or columns to update. */
		range: Option[Schema.DimensionRange] = None,
	  /** The columns on a data source sheet to update. */
		dataSourceSheetRange: Option[Schema.DataSourceSheetDimensionRange] = None,
	  /** Properties to update. */
		properties: Option[Schema.DimensionProperties] = None,
	  /** The fields that should be updated. At least one field must be specified. The root `properties` is implied and should not be specified. A single `"&#42;"` can be used as short-hand for listing every field. */
		fields: Option[String] = None
	)
	
	case class DataSourceSheetDimensionRange(
	  /** The ID of the data source sheet the range is on. */
		sheetId: Option[Int] = None,
	  /** The columns on the data source sheet. */
		columnReferences: Option[List[Schema.DataSourceColumnReference]] = None
	)
	
	case class UpdateNamedRangeRequest(
	  /** The named range to update with the new properties. */
		namedRange: Option[Schema.NamedRange] = None,
	  /** The fields that should be updated. At least one field must be specified. The root `namedRange` is implied and should not be specified. A single `"&#42;"` can be used as short-hand for listing every field. */
		fields: Option[String] = None
	)
	
	case class RepeatCellRequest(
	  /** The range to repeat the cell in. */
		range: Option[Schema.GridRange] = None,
	  /** The data to write. */
		cell: Option[Schema.CellData] = None,
	  /** The fields that should be updated. At least one field must be specified. The root `cell` is implied and should not be specified. A single `"&#42;"` can be used as short-hand for listing every field. */
		fields: Option[String] = None
	)
	
	case class AddNamedRangeRequest(
	  /** The named range to add. The namedRangeId field is optional; if one is not set, an id will be randomly generated. (It is an error to specify the ID of a range that already exists.) */
		namedRange: Option[Schema.NamedRange] = None
	)
	
	case class DeleteNamedRangeRequest(
	  /** The ID of the named range to delete. */
		namedRangeId: Option[String] = None
	)
	
	case class AddSheetRequest(
	  /** The properties the new sheet should have. All properties are optional. The sheetId field is optional; if one is not set, an id will be randomly generated. (It is an error to specify the ID of a sheet that already exists.) */
		properties: Option[Schema.SheetProperties] = None
	)
	
	case class DeleteSheetRequest(
	  /** The ID of the sheet to delete. If the sheet is of DATA_SOURCE type, the associated DataSource is also deleted. */
		sheetId: Option[Int] = None
	)
	
	case class AutoFillRequest(
	  /** The range to autofill. This will examine the range and detect the location that has data and automatically fill that data in to the rest of the range. */
		range: Option[Schema.GridRange] = None,
	  /** The source and destination areas to autofill. This explicitly lists the source of the autofill and where to extend that data. */
		sourceAndDestination: Option[Schema.SourceAndDestination] = None,
	  /** True if we should generate data with the "alternate" series. This differs based on the type and amount of source data. */
		useAlternateSeries: Option[Boolean] = None
	)
	
	object SourceAndDestination {
		enum DimensionEnum extends Enum[DimensionEnum] { case DIMENSION_UNSPECIFIED, ROWS, COLUMNS }
	}
	case class SourceAndDestination(
	  /** The location of the data to use as the source of the autofill. */
		source: Option[Schema.GridRange] = None,
	  /** The dimension that data should be filled into. */
		dimension: Option[Schema.SourceAndDestination.DimensionEnum] = None,
	  /** The number of rows or columns that data should be filled into. Positive numbers expand beyond the last row or last column of the source. Negative numbers expand before the first row or first column of the source. */
		fillLength: Option[Int] = None
	)
	
	object CutPasteRequest {
		enum PasteTypeEnum extends Enum[PasteTypeEnum] { case PASTE_NORMAL, PASTE_VALUES, PASTE_FORMAT, PASTE_NO_BORDERS, PASTE_FORMULA, PASTE_DATA_VALIDATION, PASTE_CONDITIONAL_FORMATTING }
	}
	case class CutPasteRequest(
	  /** The source data to cut. */
		source: Option[Schema.GridRange] = None,
	  /** The top-left coordinate where the data should be pasted. */
		destination: Option[Schema.GridCoordinate] = None,
	  /** What kind of data to paste. All the source data will be cut, regardless of what is pasted. */
		pasteType: Option[Schema.CutPasteRequest.PasteTypeEnum] = None
	)
	
	object CopyPasteRequest {
		enum PasteTypeEnum extends Enum[PasteTypeEnum] { case PASTE_NORMAL, PASTE_VALUES, PASTE_FORMAT, PASTE_NO_BORDERS, PASTE_FORMULA, PASTE_DATA_VALIDATION, PASTE_CONDITIONAL_FORMATTING }
		enum PasteOrientationEnum extends Enum[PasteOrientationEnum] { case NORMAL, TRANSPOSE }
	}
	case class CopyPasteRequest(
	  /** The source range to copy. */
		source: Option[Schema.GridRange] = None,
	  /** The location to paste to. If the range covers a span that's a multiple of the source's height or width, then the data will be repeated to fill in the destination range. If the range is smaller than the source range, the entire source data will still be copied (beyond the end of the destination range). */
		destination: Option[Schema.GridRange] = None,
	  /** What kind of data to paste. */
		pasteType: Option[Schema.CopyPasteRequest.PasteTypeEnum] = None,
	  /** How that data should be oriented when pasting. */
		pasteOrientation: Option[Schema.CopyPasteRequest.PasteOrientationEnum] = None
	)
	
	object MergeCellsRequest {
		enum MergeTypeEnum extends Enum[MergeTypeEnum] { case MERGE_ALL, MERGE_COLUMNS, MERGE_ROWS }
	}
	case class MergeCellsRequest(
	  /** The range of cells to merge. */
		range: Option[Schema.GridRange] = None,
	  /** How the cells should be merged. */
		mergeType: Option[Schema.MergeCellsRequest.MergeTypeEnum] = None
	)
	
	case class UnmergeCellsRequest(
	  /** The range within which all cells should be unmerged. If the range spans multiple merges, all will be unmerged. The range must not partially span any merge. */
		range: Option[Schema.GridRange] = None
	)
	
	case class UpdateBordersRequest(
	  /** The range whose borders should be updated. */
		range: Option[Schema.GridRange] = None,
	  /** The border to put at the top of the range. */
		top: Option[Schema.Border] = None,
	  /** The border to put at the bottom of the range. */
		bottom: Option[Schema.Border] = None,
	  /** The border to put at the left of the range. */
		left: Option[Schema.Border] = None,
	  /** The border to put at the right of the range. */
		right: Option[Schema.Border] = None,
	  /** The horizontal border to put within the range. */
		innerHorizontal: Option[Schema.Border] = None,
	  /** The vertical border to put within the range. */
		innerVertical: Option[Schema.Border] = None
	)
	
	case class UpdateCellsRequest(
	  /** The coordinate to start writing data at. Any number of rows and columns (including a different number of columns per row) may be written. */
		start: Option[Schema.GridCoordinate] = None,
	  /** The range to write data to. If the data in rows does not cover the entire requested range, the fields matching those set in fields will be cleared. */
		range: Option[Schema.GridRange] = None,
	  /** The data to write. */
		rows: Option[List[Schema.RowData]] = None,
	  /** The fields of CellData that should be updated. At least one field must be specified. The root is the CellData; 'row.values.' should not be specified. A single `"&#42;"` can be used as short-hand for listing every field. */
		fields: Option[String] = None
	)
	
	case class AddFilterViewRequest(
	  /** The filter to add. The filterViewId field is optional; if one is not set, an id will be randomly generated. (It is an error to specify the ID of a filter that already exists.) */
		filter: Option[Schema.FilterView] = None
	)
	
	case class AppendCellsRequest(
	  /** The sheet ID to append the data to. */
		sheetId: Option[Int] = None,
	  /** The data to append. */
		rows: Option[List[Schema.RowData]] = None,
	  /** The fields of CellData that should be updated. At least one field must be specified. The root is the CellData; 'row.values.' should not be specified. A single `"&#42;"` can be used as short-hand for listing every field. */
		fields: Option[String] = None
	)
	
	case class ClearBasicFilterRequest(
	  /** The sheet ID on which the basic filter should be cleared. */
		sheetId: Option[Int] = None
	)
	
	case class DeleteDimensionRequest(
	  /** The dimensions to delete from the sheet. */
		range: Option[Schema.DimensionRange] = None
	)
	
	case class DeleteEmbeddedObjectRequest(
	  /** The ID of the embedded object to delete. */
		objectId: Option[Int] = None
	)
	
	case class DeleteFilterViewRequest(
	  /** The ID of the filter to delete. */
		filterId: Option[Int] = None
	)
	
	case class DuplicateFilterViewRequest(
	  /** The ID of the filter being duplicated. */
		filterId: Option[Int] = None
	)
	
	case class DuplicateSheetRequest(
	  /** The sheet to duplicate. If the source sheet is of DATA_SOURCE type, its backing DataSource is also duplicated and associated with the new copy of the sheet. No data execution is triggered, the grid data of this sheet is also copied over but only available after the batch request completes. */
		sourceSheetId: Option[Int] = None,
	  /** The zero-based index where the new sheet should be inserted. The index of all sheets after this are incremented. */
		insertSheetIndex: Option[Int] = None,
	  /** If set, the ID of the new sheet. If not set, an ID is chosen. If set, the ID must not conflict with any existing sheet ID. If set, it must be non-negative. */
		newSheetId: Option[Int] = None,
	  /** The name of the new sheet. If empty, a new name is chosen for you. */
		newSheetName: Option[String] = None
	)
	
	case class FindReplaceRequest(
	  /** The value to search. */
		find: Option[String] = None,
	  /** The value to use as the replacement. */
		replacement: Option[String] = None,
	  /** The range to find/replace over. */
		range: Option[Schema.GridRange] = None,
	  /** The sheet to find/replace over. */
		sheetId: Option[Int] = None,
	  /** True to find/replace over all sheets. */
		allSheets: Option[Boolean] = None,
	  /** True if the search is case sensitive. */
		matchCase: Option[Boolean] = None,
	  /** True if the find value should match the entire cell. */
		matchEntireCell: Option[Boolean] = None,
	  /** True if the find value is a regex. The regular expression and replacement should follow Java regex rules at https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html. The replacement string is allowed to refer to capturing groups. For example, if one cell has the contents `"Google Sheets"` and another has `"Google Docs"`, then searching for `"o.&#42; (.&#42;)"` with a replacement of `"$1 Rocks"` would change the contents of the cells to `"GSheets Rocks"` and `"GDocs Rocks"` respectively. */
		searchByRegex: Option[Boolean] = None,
	  /** True if the search should include cells with formulas. False to skip cells with formulas. */
		includeFormulas: Option[Boolean] = None
	)
	
	case class InsertDimensionRequest(
	  /** The dimensions to insert. Both the start and end indexes must be bounded. */
		range: Option[Schema.DimensionRange] = None,
	  /** Whether dimension properties should be extended from the dimensions before or after the newly inserted dimensions. True to inherit from the dimensions before (in which case the start index must be greater than 0), and false to inherit from the dimensions after. For example, if row index 0 has red background and row index 1 has a green background, then inserting 2 rows at index 1 can inherit either the green or red background. If `inheritFromBefore` is true, the two new rows will be red (because the row before the insertion point was red), whereas if `inheritFromBefore` is false, the two new rows will be green (because the row after the insertion point was green). */
		inheritFromBefore: Option[Boolean] = None
	)
	
	object InsertRangeRequest {
		enum ShiftDimensionEnum extends Enum[ShiftDimensionEnum] { case DIMENSION_UNSPECIFIED, ROWS, COLUMNS }
	}
	case class InsertRangeRequest(
	  /** The range to insert new cells into. */
		range: Option[Schema.GridRange] = None,
	  /** The dimension which will be shifted when inserting cells. If ROWS, existing cells will be shifted down. If COLUMNS, existing cells will be shifted right. */
		shiftDimension: Option[Schema.InsertRangeRequest.ShiftDimensionEnum] = None
	)
	
	case class MoveDimensionRequest(
	  /** The source dimensions to move. */
		source: Option[Schema.DimensionRange] = None,
	  /** The zero-based start index of where to move the source data to, based on the coordinates &#42;before&#42; the source data is removed from the grid. Existing data will be shifted down or right (depending on the dimension) to make room for the moved dimensions. The source dimensions are removed from the grid, so the the data may end up in a different index than specified. For example, given `A1..A5` of `0, 1, 2, 3, 4` and wanting to move `"1"` and `"2"` to between `"3"` and `"4"`, the source would be `ROWS [1..3)`,and the destination index would be `"4"` (the zero-based index of row 5). The end result would be `A1..A5` of `0, 3, 1, 2, 4`. */
		destinationIndex: Option[Int] = None
	)
	
	case class UpdateEmbeddedObjectPositionRequest(
	  /** The ID of the object to moved. */
		objectId: Option[Int] = None,
	  /** An explicit position to move the embedded object to. If newPosition.sheetId is set, a new sheet with that ID will be created. If newPosition.newSheet is set to true, a new sheet will be created with an ID that will be chosen for you. */
		newPosition: Option[Schema.EmbeddedObjectPosition] = None,
	  /** The fields of OverlayPosition that should be updated when setting a new position. Used only if newPosition.overlayPosition is set, in which case at least one field must be specified. The root `newPosition.overlayPosition` is implied and should not be specified. A single `"&#42;"` can be used as short-hand for listing every field. */
		fields: Option[String] = None
	)
	
	object PasteDataRequest {
		enum TypeEnum extends Enum[TypeEnum] { case PASTE_NORMAL, PASTE_VALUES, PASTE_FORMAT, PASTE_NO_BORDERS, PASTE_FORMULA, PASTE_DATA_VALIDATION, PASTE_CONDITIONAL_FORMATTING }
	}
	case class PasteDataRequest(
	  /** The coordinate at which the data should start being inserted. */
		coordinate: Option[Schema.GridCoordinate] = None,
	  /** The data to insert. */
		data: Option[String] = None,
	  /** The delimiter in the data. */
		delimiter: Option[String] = None,
	  /** True if the data is HTML. */
		html: Option[Boolean] = None,
	  /** How the data should be pasted. */
		`type`: Option[Schema.PasteDataRequest.TypeEnum] = None
	)
	
	object TextToColumnsRequest {
		enum DelimiterTypeEnum extends Enum[DelimiterTypeEnum] { case DELIMITER_TYPE_UNSPECIFIED, COMMA, SEMICOLON, PERIOD, SPACE, CUSTOM, AUTODETECT }
	}
	case class TextToColumnsRequest(
	  /** The source data range. This must span exactly one column. */
		source: Option[Schema.GridRange] = None,
	  /** The delimiter to use. Used only if delimiterType is CUSTOM. */
		delimiter: Option[String] = None,
	  /** The delimiter type to use. */
		delimiterType: Option[Schema.TextToColumnsRequest.DelimiterTypeEnum] = None
	)
	
	case class UpdateFilterViewRequest(
	  /** The new properties of the filter view. */
		filter: Option[Schema.FilterView] = None,
	  /** The fields that should be updated. At least one field must be specified. The root `filter` is implied and should not be specified. A single `"&#42;"` can be used as short-hand for listing every field. */
		fields: Option[String] = None
	)
	
	object DeleteRangeRequest {
		enum ShiftDimensionEnum extends Enum[ShiftDimensionEnum] { case DIMENSION_UNSPECIFIED, ROWS, COLUMNS }
	}
	case class DeleteRangeRequest(
	  /** The range of cells to delete. */
		range: Option[Schema.GridRange] = None,
	  /** The dimension from which deleted cells will be replaced with. If ROWS, existing cells will be shifted upward to replace the deleted cells. If COLUMNS, existing cells will be shifted left to replace the deleted cells. */
		shiftDimension: Option[Schema.DeleteRangeRequest.ShiftDimensionEnum] = None
	)
	
	object AppendDimensionRequest {
		enum DimensionEnum extends Enum[DimensionEnum] { case DIMENSION_UNSPECIFIED, ROWS, COLUMNS }
	}
	case class AppendDimensionRequest(
	  /** The sheet to append rows or columns to. */
		sheetId: Option[Int] = None,
	  /** Whether rows or columns should be appended. */
		dimension: Option[Schema.AppendDimensionRequest.DimensionEnum] = None,
	  /** The number of rows or columns to append. */
		length: Option[Int] = None
	)
	
	case class AddConditionalFormatRuleRequest(
	  /** The rule to add. */
		rule: Option[Schema.ConditionalFormatRule] = None,
	  /** The zero-based index where the rule should be inserted. */
		index: Option[Int] = None
	)
	
	case class UpdateConditionalFormatRuleRequest(
	  /** The rule that should replace the rule at the given index. */
		rule: Option[Schema.ConditionalFormatRule] = None,
	  /** The zero-based new index the rule should end up at. */
		newIndex: Option[Int] = None,
	  /** The zero-based index of the rule that should be replaced or moved. */
		index: Option[Int] = None,
	  /** The sheet of the rule to move. Required if new_index is set, unused otherwise. */
		sheetId: Option[Int] = None
	)
	
	case class DeleteConditionalFormatRuleRequest(
	  /** The zero-based index of the rule to be deleted. */
		index: Option[Int] = None,
	  /** The sheet the rule is being deleted from. */
		sheetId: Option[Int] = None
	)
	
	case class SortRangeRequest(
	  /** The range to sort. */
		range: Option[Schema.GridRange] = None,
	  /** The sort order per column. Later specifications are used when values are equal in the earlier specifications. */
		sortSpecs: Option[List[Schema.SortSpec]] = None
	)
	
	case class SetDataValidationRequest(
	  /** The range the data validation rule should apply to. */
		range: Option[Schema.GridRange] = None,
	  /** The data validation rule to set on each cell in the range, or empty to clear the data validation in the range. */
		rule: Option[Schema.DataValidationRule] = None
	)
	
	case class SetBasicFilterRequest(
	  /** The filter to set. */
		filter: Option[Schema.BasicFilter] = None
	)
	
	case class AddProtectedRangeRequest(
	  /** The protected range to be added. The protectedRangeId field is optional; if one is not set, an id will be randomly generated. (It is an error to specify the ID of a range that already exists.) */
		protectedRange: Option[Schema.ProtectedRange] = None
	)
	
	case class UpdateProtectedRangeRequest(
	  /** The protected range to update with the new properties. */
		protectedRange: Option[Schema.ProtectedRange] = None,
	  /** The fields that should be updated. At least one field must be specified. The root `protectedRange` is implied and should not be specified. A single `"&#42;"` can be used as short-hand for listing every field. */
		fields: Option[String] = None
	)
	
	case class DeleteProtectedRangeRequest(
	  /** The ID of the protected range to delete. */
		protectedRangeId: Option[Int] = None
	)
	
	case class AutoResizeDimensionsRequest(
	  /** The dimensions to automatically resize. */
		dimensions: Option[Schema.DimensionRange] = None,
	  /** The dimensions on a data source sheet to automatically resize. */
		dataSourceSheetDimensions: Option[Schema.DataSourceSheetDimensionRange] = None
	)
	
	case class AddChartRequest(
	  /** The chart that should be added to the spreadsheet, including the position where it should be placed. The chartId field is optional; if one is not set, an id will be randomly generated. (It is an error to specify the ID of an embedded object that already exists.) */
		chart: Option[Schema.EmbeddedChart] = None
	)
	
	case class UpdateChartSpecRequest(
	  /** The ID of the chart to update. */
		chartId: Option[Int] = None,
	  /** The specification to apply to the chart. */
		spec: Option[Schema.ChartSpec] = None
	)
	
	case class UpdateBandingRequest(
	  /** The banded range to update with the new properties. */
		bandedRange: Option[Schema.BandedRange] = None,
	  /** The fields that should be updated. At least one field must be specified. The root `bandedRange` is implied and should not be specified. A single `"&#42;"` can be used as short-hand for listing every field. */
		fields: Option[String] = None
	)
	
	case class AddBandingRequest(
	  /** The banded range to add. The bandedRangeId field is optional; if one is not set, an id will be randomly generated. (It is an error to specify the ID of a range that already exists.) */
		bandedRange: Option[Schema.BandedRange] = None
	)
	
	case class DeleteBandingRequest(
	  /** The ID of the banded range to delete. */
		bandedRangeId: Option[Int] = None
	)
	
	case class CreateDeveloperMetadataRequest(
	  /** The developer metadata to create. */
		developerMetadata: Option[Schema.DeveloperMetadata] = None
	)
	
	case class UpdateDeveloperMetadataRequest(
	  /** The filters matching the developer metadata entries to update. */
		dataFilters: Option[List[Schema.DataFilter]] = None,
	  /** The value that all metadata matched by the data filters will be updated to. */
		developerMetadata: Option[Schema.DeveloperMetadata] = None,
	  /** The fields that should be updated. At least one field must be specified. The root `developerMetadata` is implied and should not be specified. A single `"&#42;"` can be used as short-hand for listing every field. */
		fields: Option[String] = None
	)
	
	case class DeleteDeveloperMetadataRequest(
	  /** The data filter describing the criteria used to select which developer metadata entry to delete. */
		dataFilter: Option[Schema.DataFilter] = None
	)
	
	case class RandomizeRangeRequest(
	  /** The range to randomize. */
		range: Option[Schema.GridRange] = None
	)
	
	case class AddDimensionGroupRequest(
	  /** The range over which to create a group. */
		range: Option[Schema.DimensionRange] = None
	)
	
	case class DeleteDimensionGroupRequest(
	  /** The range of the group to be deleted. */
		range: Option[Schema.DimensionRange] = None
	)
	
	case class UpdateDimensionGroupRequest(
	  /** The group whose state should be updated. The range and depth of the group should specify a valid group on the sheet, and all other fields updated. */
		dimensionGroup: Option[Schema.DimensionGroup] = None,
	  /** The fields that should be updated. At least one field must be specified. The root `dimensionGroup` is implied and should not be specified. A single `"&#42;"` can be used as short-hand for listing every field. */
		fields: Option[String] = None
	)
	
	case class TrimWhitespaceRequest(
	  /** The range whose cells to trim. */
		range: Option[Schema.GridRange] = None
	)
	
	case class DeleteDuplicatesRequest(
	  /** The range to remove duplicates rows from. */
		range: Option[Schema.GridRange] = None,
	  /** The columns in the range to analyze for duplicate values. If no columns are selected then all columns are analyzed for duplicates. */
		comparisonColumns: Option[List[Schema.DimensionRange]] = None
	)
	
	case class UpdateEmbeddedObjectBorderRequest(
	  /** The ID of the embedded object to update. */
		objectId: Option[Int] = None,
	  /** The border that applies to the embedded object. */
		border: Option[Schema.EmbeddedObjectBorder] = None,
	  /** The fields that should be updated. At least one field must be specified. The root `border` is implied and should not be specified. A single `"&#42;"` can be used as short-hand for listing every field. */
		fields: Option[String] = None
	)
	
	case class AddSlicerRequest(
	  /** The slicer that should be added to the spreadsheet, including the position where it should be placed. The slicerId field is optional; if one is not set, an id will be randomly generated. (It is an error to specify the ID of a slicer that already exists.) */
		slicer: Option[Schema.Slicer] = None
	)
	
	case class UpdateSlicerSpecRequest(
	  /** The id of the slicer to update. */
		slicerId: Option[Int] = None,
	  /** The specification to apply to the slicer. */
		spec: Option[Schema.SlicerSpec] = None,
	  /** The fields that should be updated. At least one field must be specified. The root `SlicerSpec` is implied and should not be specified. A single "&#42;"` can be used as short-hand for listing every field. */
		fields: Option[String] = None
	)
	
	case class AddDataSourceRequest(
	  /** The data source to add. */
		dataSource: Option[Schema.DataSource] = None
	)
	
	case class UpdateDataSourceRequest(
	  /** The data source to update. */
		dataSource: Option[Schema.DataSource] = None,
	  /** The fields that should be updated. At least one field must be specified. The root `dataSource` is implied and should not be specified. A single `"&#42;"` can be used as short-hand for listing every field. */
		fields: Option[String] = None
	)
	
	case class DeleteDataSourceRequest(
	  /** The ID of the data source to delete. */
		dataSourceId: Option[String] = None
	)
	
	case class RefreshDataSourceRequest(
	  /** References to data source objects to refresh. */
		references: Option[Schema.DataSourceObjectReferences] = None,
	  /** Reference to a DataSource. If specified, refreshes all associated data source objects for the data source. */
		dataSourceId: Option[String] = None,
	  /** Refreshes all existing data source objects in the spreadsheet. */
		isAll: Option[Boolean] = None,
	  /** Refreshes the data source objects regardless of the current state. If not set and a referenced data source object was in error state, the refresh will fail immediately. */
		force: Option[Boolean] = None
	)
	
	case class DataSourceObjectReferences(
	  /** The references. */
		references: Option[List[Schema.DataSourceObjectReference]] = None
	)
	
	case class DataSourceObjectReference(
	  /** References to a DATA_SOURCE sheet. */
		sheetId: Option[String] = None,
	  /** References to a data source chart. */
		chartId: Option[Int] = None,
	  /** References to a DataSourceTable anchored at the cell. */
		dataSourceTableAnchorCell: Option[Schema.GridCoordinate] = None,
	  /** References to a data source PivotTable anchored at the cell. */
		dataSourcePivotTableAnchorCell: Option[Schema.GridCoordinate] = None,
	  /** References to a cell containing DataSourceFormula. */
		dataSourceFormulaCell: Option[Schema.GridCoordinate] = None
	)
	
	case class CancelDataSourceRefreshRequest(
	  /** References to data source objects whose refreshes are to be cancelled. */
		references: Option[Schema.DataSourceObjectReferences] = None,
	  /** Reference to a DataSource. If specified, cancels all associated data source object refreshes for this data source. */
		dataSourceId: Option[String] = None,
	  /** Cancels all existing data source object refreshes for all data sources in the spreadsheet. */
		isAll: Option[Boolean] = None
	)
	
	case class BatchUpdateSpreadsheetResponse(
	  /** The spreadsheet the updates were applied to. */
		spreadsheetId: Option[String] = None,
	  /** The reply of the updates. This maps 1:1 with the updates, although replies to some requests may be empty. */
		replies: Option[List[Schema.Response]] = None,
	  /** The spreadsheet after updates were applied. This is only set if BatchUpdateSpreadsheetRequest.include_spreadsheet_in_response is `true`. */
		updatedSpreadsheet: Option[Schema.Spreadsheet] = None
	)
	
	case class Response(
	  /** A reply from adding a named range. */
		addNamedRange: Option[Schema.AddNamedRangeResponse] = None,
	  /** A reply from adding a sheet. */
		addSheet: Option[Schema.AddSheetResponse] = None,
	  /** A reply from adding a filter view. */
		addFilterView: Option[Schema.AddFilterViewResponse] = None,
	  /** A reply from duplicating a filter view. */
		duplicateFilterView: Option[Schema.DuplicateFilterViewResponse] = None,
	  /** A reply from duplicating a sheet. */
		duplicateSheet: Option[Schema.DuplicateSheetResponse] = None,
	  /** A reply from doing a find/replace. */
		findReplace: Option[Schema.FindReplaceResponse] = None,
	  /** A reply from updating an embedded object's position. */
		updateEmbeddedObjectPosition: Option[Schema.UpdateEmbeddedObjectPositionResponse] = None,
	  /** A reply from updating a conditional format rule. */
		updateConditionalFormatRule: Option[Schema.UpdateConditionalFormatRuleResponse] = None,
	  /** A reply from deleting a conditional format rule. */
		deleteConditionalFormatRule: Option[Schema.DeleteConditionalFormatRuleResponse] = None,
	  /** A reply from adding a protected range. */
		addProtectedRange: Option[Schema.AddProtectedRangeResponse] = None,
	  /** A reply from adding a chart. */
		addChart: Option[Schema.AddChartResponse] = None,
	  /** A reply from adding a banded range. */
		addBanding: Option[Schema.AddBandingResponse] = None,
	  /** A reply from creating a developer metadata entry. */
		createDeveloperMetadata: Option[Schema.CreateDeveloperMetadataResponse] = None,
	  /** A reply from updating a developer metadata entry. */
		updateDeveloperMetadata: Option[Schema.UpdateDeveloperMetadataResponse] = None,
	  /** A reply from deleting a developer metadata entry. */
		deleteDeveloperMetadata: Option[Schema.DeleteDeveloperMetadataResponse] = None,
	  /** A reply from adding a dimension group. */
		addDimensionGroup: Option[Schema.AddDimensionGroupResponse] = None,
	  /** A reply from deleting a dimension group. */
		deleteDimensionGroup: Option[Schema.DeleteDimensionGroupResponse] = None,
	  /** A reply from trimming whitespace. */
		trimWhitespace: Option[Schema.TrimWhitespaceResponse] = None,
	  /** A reply from removing rows containing duplicate values. */
		deleteDuplicates: Option[Schema.DeleteDuplicatesResponse] = None,
	  /** A reply from adding a slicer. */
		addSlicer: Option[Schema.AddSlicerResponse] = None,
	  /** A reply from adding a data source. */
		addDataSource: Option[Schema.AddDataSourceResponse] = None,
	  /** A reply from updating a data source. */
		updateDataSource: Option[Schema.UpdateDataSourceResponse] = None,
	  /** A reply from refreshing data source objects. */
		refreshDataSource: Option[Schema.RefreshDataSourceResponse] = None,
	  /** A reply from cancelling data source object refreshes. */
		cancelDataSourceRefresh: Option[Schema.CancelDataSourceRefreshResponse] = None
	)
	
	case class AddNamedRangeResponse(
	  /** The named range to add. */
		namedRange: Option[Schema.NamedRange] = None
	)
	
	case class AddSheetResponse(
	  /** The properties of the newly added sheet. */
		properties: Option[Schema.SheetProperties] = None
	)
	
	case class AddFilterViewResponse(
	  /** The newly added filter view. */
		filter: Option[Schema.FilterView] = None
	)
	
	case class DuplicateFilterViewResponse(
	  /** The newly created filter. */
		filter: Option[Schema.FilterView] = None
	)
	
	case class DuplicateSheetResponse(
	  /** The properties of the duplicate sheet. */
		properties: Option[Schema.SheetProperties] = None
	)
	
	case class FindReplaceResponse(
	  /** The number of non-formula cells changed. */
		valuesChanged: Option[Int] = None,
	  /** The number of formula cells changed. */
		formulasChanged: Option[Int] = None,
	  /** The number of rows changed. */
		rowsChanged: Option[Int] = None,
	  /** The number of sheets changed. */
		sheetsChanged: Option[Int] = None,
	  /** The number of occurrences (possibly multiple within a cell) changed. For example, if replacing `"e"` with `"o"` in `"Google Sheets"`, this would be `"3"` because `"Google Sheets"` -> `"Googlo Shoots"`. */
		occurrencesChanged: Option[Int] = None
	)
	
	case class UpdateEmbeddedObjectPositionResponse(
	  /** The new position of the embedded object. */
		position: Option[Schema.EmbeddedObjectPosition] = None
	)
	
	case class UpdateConditionalFormatRuleResponse(
	  /** The old (deleted) rule. Not set if a rule was moved (because it is the same as new_rule). */
		oldRule: Option[Schema.ConditionalFormatRule] = None,
	  /** The old index of the rule. Not set if a rule was replaced (because it is the same as new_index). */
		oldIndex: Option[Int] = None,
	  /** The new rule that replaced the old rule (if replacing), or the rule that was moved (if moved) */
		newRule: Option[Schema.ConditionalFormatRule] = None,
	  /** The index of the new rule. */
		newIndex: Option[Int] = None
	)
	
	case class DeleteConditionalFormatRuleResponse(
	  /** The rule that was deleted. */
		rule: Option[Schema.ConditionalFormatRule] = None
	)
	
	case class AddProtectedRangeResponse(
	  /** The newly added protected range. */
		protectedRange: Option[Schema.ProtectedRange] = None
	)
	
	case class AddChartResponse(
	  /** The newly added chart. */
		chart: Option[Schema.EmbeddedChart] = None
	)
	
	case class AddBandingResponse(
	  /** The banded range that was added. */
		bandedRange: Option[Schema.BandedRange] = None
	)
	
	case class CreateDeveloperMetadataResponse(
	  /** The developer metadata that was created. */
		developerMetadata: Option[Schema.DeveloperMetadata] = None
	)
	
	case class UpdateDeveloperMetadataResponse(
	  /** The updated developer metadata. */
		developerMetadata: Option[List[Schema.DeveloperMetadata]] = None
	)
	
	case class DeleteDeveloperMetadataResponse(
	  /** The metadata that was deleted. */
		deletedDeveloperMetadata: Option[List[Schema.DeveloperMetadata]] = None
	)
	
	case class AddDimensionGroupResponse(
	  /** All groups of a dimension after adding a group to that dimension. */
		dimensionGroups: Option[List[Schema.DimensionGroup]] = None
	)
	
	case class DeleteDimensionGroupResponse(
	  /** All groups of a dimension after deleting a group from that dimension. */
		dimensionGroups: Option[List[Schema.DimensionGroup]] = None
	)
	
	case class TrimWhitespaceResponse(
	  /** The number of cells that were trimmed of whitespace. */
		cellsChangedCount: Option[Int] = None
	)
	
	case class DeleteDuplicatesResponse(
	  /** The number of duplicate rows removed. */
		duplicatesRemovedCount: Option[Int] = None
	)
	
	case class AddSlicerResponse(
	  /** The newly added slicer. */
		slicer: Option[Schema.Slicer] = None
	)
	
	case class AddDataSourceResponse(
	  /** The data source that was created. */
		dataSource: Option[Schema.DataSource] = None,
	  /** The data execution status. */
		dataExecutionStatus: Option[Schema.DataExecutionStatus] = None
	)
	
	case class UpdateDataSourceResponse(
	  /** The updated data source. */
		dataSource: Option[Schema.DataSource] = None,
	  /** The data execution status. */
		dataExecutionStatus: Option[Schema.DataExecutionStatus] = None
	)
	
	case class RefreshDataSourceResponse(
	  /** All the refresh status for the data source object references specified in the request. If is_all is specified, the field contains only those in failure status. */
		statuses: Option[List[Schema.RefreshDataSourceObjectExecutionStatus]] = None
	)
	
	case class RefreshDataSourceObjectExecutionStatus(
	  /** Reference to a data source object being refreshed. */
		reference: Option[Schema.DataSourceObjectReference] = None,
	  /** The data execution status. */
		dataExecutionStatus: Option[Schema.DataExecutionStatus] = None
	)
	
	case class CancelDataSourceRefreshResponse(
	  /** The cancellation statuses of refreshes of all data source objects specified in the request. If is_all is specified, the field contains only those in failure status. Refreshing and canceling refresh the same data source object is also not allowed in the same `batchUpdate`. */
		statuses: Option[List[Schema.CancelDataSourceRefreshStatus]] = None
	)
	
	case class CancelDataSourceRefreshStatus(
	  /** Reference to the data source object whose refresh is being cancelled. */
		reference: Option[Schema.DataSourceObjectReference] = None,
	  /** The cancellation status. */
		refreshCancellationStatus: Option[Schema.RefreshCancellationStatus] = None
	)
	
	object RefreshCancellationStatus {
		enum StateEnum extends Enum[StateEnum] { case REFRESH_CANCELLATION_STATE_UNSPECIFIED, CANCEL_SUCCEEDED, CANCEL_FAILED }
		enum ErrorCodeEnum extends Enum[ErrorCodeEnum] { case REFRESH_CANCELLATION_ERROR_CODE_UNSPECIFIED, EXECUTION_NOT_FOUND, CANCEL_PERMISSION_DENIED, QUERY_EXECUTION_COMPLETED, CONCURRENT_CANCELLATION, CANCEL_OTHER_ERROR }
	}
	case class RefreshCancellationStatus(
	  /** The state of a call to cancel a refresh in Sheets. */
		state: Option[Schema.RefreshCancellationStatus.StateEnum] = None,
	  /** The error code. */
		errorCode: Option[Schema.RefreshCancellationStatus.ErrorCodeEnum] = None
	)
	
	case class CopySheetToAnotherSpreadsheetRequest(
	  /** The ID of the spreadsheet to copy the sheet to. */
		destinationSpreadsheetId: Option[String] = None
	)
}
