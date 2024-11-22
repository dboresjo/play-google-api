package com.boresjo.play.api.google.sheets

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	val scopes = Seq(
		"""https://www.googleapis.com/auth/drive""" /* See, edit, create, and delete all of your Google Drive files */,
		"""https://www.googleapis.com/auth/drive.file""" /* See, edit, create, and delete only the specific Google Drive files you use with this app */,
		"""https://www.googleapis.com/auth/drive.readonly""" /* See and download all your Google Drive files */,
		"""https://www.googleapis.com/auth/spreadsheets""" /* See, edit, create, and delete all your Google Sheets spreadsheets */,
		"""https://www.googleapis.com/auth/spreadsheets.readonly""" /* See all your Google Sheets spreadsheets */
	)

	private val BASE_URL = "https://sheets.googleapis.com/"

	object spreadsheets {
		/** Returns the spreadsheet at the given ID. The caller must specify the spreadsheet ID. This method differs from GetSpreadsheet in that it allows selecting which subsets of spreadsheet data to return by specifying a dataFilters parameter. Multiple DataFilters can be specified. Specifying one or more data filters returns the portions of the spreadsheet that intersect ranges matched by any of the filters. By default, data within grids is not returned. You can include grid data one of 2 ways: &#42; Specify a [field mask](https://developers.google.com/sheets/api/guides/field-masks) listing your desired fields using the `fields` URL parameter in HTTP &#42; Set the includeGridData parameter to true. If a field mask is set, the `includeGridData` parameter is ignored For large spreadsheets, as a best practice, retrieve only the specific spreadsheet fields that you want. */
		class getByDataFilter(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/spreadsheets""")
			/** Perform the request */
			def withGetSpreadsheetByDataFilterRequest(body: Schema.GetSpreadsheetByDataFilterRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Spreadsheet])
		}
		object getByDataFilter {
			def apply(spreadsheetId: String)(using signer: RequestSigner, ec: ExecutionContext): getByDataFilter = new getByDataFilter(ws.url(BASE_URL + s"v4/spreadsheets/${spreadsheetId}:getByDataFilter").addQueryStringParameters())
		}
		/** Creates a spreadsheet, returning the newly created spreadsheet. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/spreadsheets""")
			/** Perform the request */
			def withSpreadsheet(body: Schema.Spreadsheet) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Spreadsheet])
		}
		object create {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v4/spreadsheets").addQueryStringParameters())
		}
		/** Applies one or more updates to the spreadsheet. Each request is validated before being applied. If any request is not valid then the entire request will fail and nothing will be applied. Some requests have replies to give you some information about how they are applied. The replies will mirror the requests. For example, if you applied 4 updates and the 3rd one had a reply, then the response will have 2 empty replies, the actual reply, and another empty reply, in that order. Due to the collaborative nature of spreadsheets, it is not guaranteed that the spreadsheet will reflect exactly your changes after this completes, however it is guaranteed that the updates in the request will be applied together atomically. Your changes may be altered with respect to collaborator changes. If there are no collaborators, the spreadsheet should reflect your changes. */
		class batchUpdate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/spreadsheets""")
			/** Perform the request */
			def withBatchUpdateSpreadsheetRequest(body: Schema.BatchUpdateSpreadsheetRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchUpdateSpreadsheetResponse])
		}
		object batchUpdate {
			def apply(spreadsheetId: String)(using signer: RequestSigner, ec: ExecutionContext): batchUpdate = new batchUpdate(ws.url(BASE_URL + s"v4/spreadsheets/${spreadsheetId}:batchUpdate").addQueryStringParameters())
		}
		/** Returns the spreadsheet at the given ID. The caller must specify the spreadsheet ID. By default, data within grids is not returned. You can include grid data in one of 2 ways: &#42; Specify a [field mask](https://developers.google.com/sheets/api/guides/field-masks) listing your desired fields using the `fields` URL parameter in HTTP &#42; Set the includeGridData URL parameter to true. If a field mask is set, the `includeGridData` parameter is ignored For large spreadsheets, as a best practice, retrieve only the specific spreadsheet fields that you want. To retrieve only subsets of spreadsheet data, use the ranges URL parameter. Ranges are specified using [A1 notation](/sheets/api/guides/concepts#cell). You can define a single cell (for example, `A1`) or multiple cells (for example, `A1:D5`). You can also get cells from other sheets within the same spreadsheet (for example, `Sheet2!A1:C4`) or retrieve multiple ranges at once (for example, `?ranges=A1:D5&ranges=Sheet2!A1:C4`). Limiting the range returns only the portions of the spreadsheet that intersect the requested ranges. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Spreadsheet]) {
			val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.readonly""", """https://www.googleapis.com/auth/spreadsheets""", """https://www.googleapis.com/auth/spreadsheets.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Spreadsheet])
		}
		object get {
			def apply(spreadsheetId: String, ranges: String, includeGridData: Boolean)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v4/spreadsheets/${spreadsheetId}").addQueryStringParameters("ranges" -> ranges.toString, "includeGridData" -> includeGridData.toString))
			given Conversion[get, Future[Schema.Spreadsheet]] = (fun: get) => fun.apply()
		}
		object sheets {
			/** Copies a single sheet from a spreadsheet to another spreadsheet. Returns the properties of the newly created sheet. */
			class copyTo(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/spreadsheets""")
				/** Perform the request */
				def withCopySheetToAnotherSpreadsheetRequest(body: Schema.CopySheetToAnotherSpreadsheetRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SheetProperties])
			}
			object copyTo {
				def apply(spreadsheetId: String, sheetId: Int)(using signer: RequestSigner, ec: ExecutionContext): copyTo = new copyTo(ws.url(BASE_URL + s"v4/spreadsheets/${spreadsheetId}/sheets/${sheetId}:copyTo").addQueryStringParameters())
			}
		}
		object values {
			/** Clears values from a spreadsheet. The caller must specify the spreadsheet ID and range. Only values are cleared -- all other properties of the cell (such as formatting, data validation, etc..) are kept. */
			class clear(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/spreadsheets""")
				/** Perform the request */
				def withClearValuesRequest(body: Schema.ClearValuesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ClearValuesResponse])
			}
			object clear {
				def apply(spreadsheetId: String, range: String)(using signer: RequestSigner, ec: ExecutionContext): clear = new clear(ws.url(BASE_URL + s"v4/spreadsheets/${spreadsheetId}/values/${range}:clear").addQueryStringParameters())
			}
			/** Sets values in one or more ranges of a spreadsheet. The caller must specify the spreadsheet ID, a valueInputOption, and one or more DataFilterValueRanges. */
			class batchUpdateByDataFilter(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/spreadsheets""")
				/** Perform the request */
				def withBatchUpdateValuesByDataFilterRequest(body: Schema.BatchUpdateValuesByDataFilterRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchUpdateValuesByDataFilterResponse])
			}
			object batchUpdateByDataFilter {
				def apply(spreadsheetId: String)(using signer: RequestSigner, ec: ExecutionContext): batchUpdateByDataFilter = new batchUpdateByDataFilter(ws.url(BASE_URL + s"v4/spreadsheets/${spreadsheetId}/values:batchUpdateByDataFilter").addQueryStringParameters())
			}
			/** Sets values in one or more ranges of a spreadsheet. The caller must specify the spreadsheet ID, a valueInputOption, and one or more ValueRanges. */
			class batchUpdate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/spreadsheets""")
				/** Perform the request */
				def withBatchUpdateValuesRequest(body: Schema.BatchUpdateValuesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchUpdateValuesResponse])
			}
			object batchUpdate {
				def apply(spreadsheetId: String)(using signer: RequestSigner, ec: ExecutionContext): batchUpdate = new batchUpdate(ws.url(BASE_URL + s"v4/spreadsheets/${spreadsheetId}/values:batchUpdate").addQueryStringParameters())
			}
			/** Returns one or more ranges of values that match the specified data filters. The caller must specify the spreadsheet ID and one or more DataFilters. Ranges that match any of the data filters in the request will be returned. */
			class batchGetByDataFilter(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/spreadsheets""")
				/** Perform the request */
				def withBatchGetValuesByDataFilterRequest(body: Schema.BatchGetValuesByDataFilterRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchGetValuesByDataFilterResponse])
			}
			object batchGetByDataFilter {
				def apply(spreadsheetId: String)(using signer: RequestSigner, ec: ExecutionContext): batchGetByDataFilter = new batchGetByDataFilter(ws.url(BASE_URL + s"v4/spreadsheets/${spreadsheetId}/values:batchGetByDataFilter").addQueryStringParameters())
			}
			/** Returns a range of values from a spreadsheet. The caller must specify the spreadsheet ID and a range. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ValueRange]) {
				val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.readonly""", """https://www.googleapis.com/auth/spreadsheets""", """https://www.googleapis.com/auth/spreadsheets.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ValueRange])
			}
			object get {
				def apply(spreadsheetId: String, range: String, majorDimension: String, valueRenderOption: String, dateTimeRenderOption: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v4/spreadsheets/${spreadsheetId}/values/${range}").addQueryStringParameters("majorDimension" -> majorDimension.toString, "valueRenderOption" -> valueRenderOption.toString, "dateTimeRenderOption" -> dateTimeRenderOption.toString))
				given Conversion[get, Future[Schema.ValueRange]] = (fun: get) => fun.apply()
			}
			/** Sets values in a range of a spreadsheet. The caller must specify the spreadsheet ID, range, and a valueInputOption. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/spreadsheets""")
				/** Perform the request */
				def withValueRange(body: Schema.ValueRange) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.UpdateValuesResponse])
			}
			object update {
				def apply(spreadsheetId: String, range: String, valueInputOption: String, includeValuesInResponse: Boolean, responseValueRenderOption: String, responseDateTimeRenderOption: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v4/spreadsheets/${spreadsheetId}/values/${range}").addQueryStringParameters("valueInputOption" -> valueInputOption.toString, "includeValuesInResponse" -> includeValuesInResponse.toString, "responseValueRenderOption" -> responseValueRenderOption.toString, "responseDateTimeRenderOption" -> responseDateTimeRenderOption.toString))
			}
			/** Returns one or more ranges of values from a spreadsheet. The caller must specify the spreadsheet ID and one or more ranges. */
			class batchGet(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.BatchGetValuesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/drive.readonly""", """https://www.googleapis.com/auth/spreadsheets""", """https://www.googleapis.com/auth/spreadsheets.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.BatchGetValuesResponse])
			}
			object batchGet {
				def apply(spreadsheetId: String, ranges: String, majorDimension: String, valueRenderOption: String, dateTimeRenderOption: String)(using signer: RequestSigner, ec: ExecutionContext): batchGet = new batchGet(ws.url(BASE_URL + s"v4/spreadsheets/${spreadsheetId}/values:batchGet").addQueryStringParameters("ranges" -> ranges.toString, "majorDimension" -> majorDimension.toString, "valueRenderOption" -> valueRenderOption.toString, "dateTimeRenderOption" -> dateTimeRenderOption.toString))
				given Conversion[batchGet, Future[Schema.BatchGetValuesResponse]] = (fun: batchGet) => fun.apply()
			}
			/** Appends values to a spreadsheet. The input range is used to search for existing data and find a "table" within that range. Values will be appended to the next row of the table, starting with the first column of the table. See the [guide](/sheets/api/guides/values#appending_values) and [sample code](/sheets/api/samples/writing#append_values) for specific details of how tables are detected and data is appended. The caller must specify the spreadsheet ID, range, and a valueInputOption. The `valueInputOption` only controls how the input data will be added to the sheet (column-wise or row-wise), it does not influence what cell the data starts being written to. */
			class append(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/spreadsheets""")
				/** Perform the request */
				def withValueRange(body: Schema.ValueRange) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AppendValuesResponse])
			}
			object append {
				def apply(spreadsheetId: String, range: String, valueInputOption: String, insertDataOption: String, includeValuesInResponse: Boolean, responseValueRenderOption: String, responseDateTimeRenderOption: String)(using signer: RequestSigner, ec: ExecutionContext): append = new append(ws.url(BASE_URL + s"v4/spreadsheets/${spreadsheetId}/values/${range}:append").addQueryStringParameters("valueInputOption" -> valueInputOption.toString, "insertDataOption" -> insertDataOption.toString, "includeValuesInResponse" -> includeValuesInResponse.toString, "responseValueRenderOption" -> responseValueRenderOption.toString, "responseDateTimeRenderOption" -> responseDateTimeRenderOption.toString))
			}
			/** Clears one or more ranges of values from a spreadsheet. The caller must specify the spreadsheet ID and one or more DataFilters. Ranges matching any of the specified data filters will be cleared. Only values are cleared -- all other properties of the cell (such as formatting, data validation, etc..) are kept. */
			class batchClearByDataFilter(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/spreadsheets""")
				/** Perform the request */
				def withBatchClearValuesByDataFilterRequest(body: Schema.BatchClearValuesByDataFilterRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchClearValuesByDataFilterResponse])
			}
			object batchClearByDataFilter {
				def apply(spreadsheetId: String)(using signer: RequestSigner, ec: ExecutionContext): batchClearByDataFilter = new batchClearByDataFilter(ws.url(BASE_URL + s"v4/spreadsheets/${spreadsheetId}/values:batchClearByDataFilter").addQueryStringParameters())
			}
			/** Clears one or more ranges of values from a spreadsheet. The caller must specify the spreadsheet ID and one or more ranges. Only values are cleared -- all other properties of the cell (such as formatting and data validation) are kept. */
			class batchClear(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/spreadsheets""")
				/** Perform the request */
				def withBatchClearValuesRequest(body: Schema.BatchClearValuesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchClearValuesResponse])
			}
			object batchClear {
				def apply(spreadsheetId: String)(using signer: RequestSigner, ec: ExecutionContext): batchClear = new batchClear(ws.url(BASE_URL + s"v4/spreadsheets/${spreadsheetId}/values:batchClear").addQueryStringParameters())
			}
		}
		object developerMetadata {
			/** Returns the developer metadata with the specified ID. The caller must specify the spreadsheet ID and the developer metadata's unique metadataId. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DeveloperMetadata]) {
				val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/spreadsheets""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.DeveloperMetadata])
			}
			object get {
				def apply(spreadsheetId: String, metadataId: Int)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v4/spreadsheets/${spreadsheetId}/developerMetadata/${metadataId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.DeveloperMetadata]] = (fun: get) => fun.apply()
			}
			/** Returns all developer metadata matching the specified DataFilter. If the provided DataFilter represents a DeveloperMetadataLookup object, this will return all DeveloperMetadata entries selected by it. If the DataFilter represents a location in a spreadsheet, this will return all developer metadata associated with locations intersecting that region. */
			class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/drive""", """https://www.googleapis.com/auth/drive.file""", """https://www.googleapis.com/auth/spreadsheets""")
				/** Perform the request */
				def withSearchDeveloperMetadataRequest(body: Schema.SearchDeveloperMetadataRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SearchDeveloperMetadataResponse])
			}
			object search {
				def apply(spreadsheetId: String)(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v4/spreadsheets/${spreadsheetId}/developerMetadata:search").addQueryStringParameters())
			}
		}
	}
}
