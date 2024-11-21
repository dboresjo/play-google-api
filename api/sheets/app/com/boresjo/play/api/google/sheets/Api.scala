package com.boresjo.play.api.google.sheets

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://sheets.googleapis.com/"

	object spreadsheets {
		class getByDataFilter(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGetSpreadsheetByDataFilterRequest(body: Schema.GetSpreadsheetByDataFilterRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Spreadsheet])
		}
		object getByDataFilter {
			def apply(spreadsheetId: String)(using auth: AuthToken, ec: ExecutionContext): getByDataFilter = new getByDataFilter(auth(ws.url(BASE_URL + s"v4/spreadsheets/${spreadsheetId}:getByDataFilter")).addQueryStringParameters())
		}
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSpreadsheet(body: Schema.Spreadsheet) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Spreadsheet])
		}
		object create {
			def apply()(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v4/spreadsheets")).addQueryStringParameters())
		}
		class batchUpdate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withBatchUpdateSpreadsheetRequest(body: Schema.BatchUpdateSpreadsheetRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.BatchUpdateSpreadsheetResponse])
		}
		object batchUpdate {
			def apply(spreadsheetId: String)(using auth: AuthToken, ec: ExecutionContext): batchUpdate = new batchUpdate(auth(ws.url(BASE_URL + s"v4/spreadsheets/${spreadsheetId}:batchUpdate")).addQueryStringParameters())
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Spreadsheet]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Spreadsheet])
		}
		object get {
			def apply(spreadsheetId: String, ranges: String, includeGridData: Boolean)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v4/spreadsheets/${spreadsheetId}")).addQueryStringParameters("ranges" -> ranges.toString, "includeGridData" -> includeGridData.toString))
			given Conversion[get, Future[Schema.Spreadsheet]] = (fun: get) => fun.apply()
		}
		object sheets {
			class copyTo(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCopySheetToAnotherSpreadsheetRequest(body: Schema.CopySheetToAnotherSpreadsheetRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SheetProperties])
			}
			object copyTo {
				def apply(spreadsheetId: String, sheetId: Int)(using auth: AuthToken, ec: ExecutionContext): copyTo = new copyTo(auth(ws.url(BASE_URL + s"v4/spreadsheets/${spreadsheetId}/sheets/${sheetId}:copyTo")).addQueryStringParameters())
			}
		}
		object values {
			class clear(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withClearValuesRequest(body: Schema.ClearValuesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ClearValuesResponse])
			}
			object clear {
				def apply(spreadsheetId: String, range: String)(using auth: AuthToken, ec: ExecutionContext): clear = new clear(auth(ws.url(BASE_URL + s"v4/spreadsheets/${spreadsheetId}/values/${range}:clear")).addQueryStringParameters())
			}
			class batchUpdateByDataFilter(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withBatchUpdateValuesByDataFilterRequest(body: Schema.BatchUpdateValuesByDataFilterRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.BatchUpdateValuesByDataFilterResponse])
			}
			object batchUpdateByDataFilter {
				def apply(spreadsheetId: String)(using auth: AuthToken, ec: ExecutionContext): batchUpdateByDataFilter = new batchUpdateByDataFilter(auth(ws.url(BASE_URL + s"v4/spreadsheets/${spreadsheetId}/values:batchUpdateByDataFilter")).addQueryStringParameters())
			}
			class batchUpdate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withBatchUpdateValuesRequest(body: Schema.BatchUpdateValuesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.BatchUpdateValuesResponse])
			}
			object batchUpdate {
				def apply(spreadsheetId: String)(using auth: AuthToken, ec: ExecutionContext): batchUpdate = new batchUpdate(auth(ws.url(BASE_URL + s"v4/spreadsheets/${spreadsheetId}/values:batchUpdate")).addQueryStringParameters())
			}
			class batchGetByDataFilter(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withBatchGetValuesByDataFilterRequest(body: Schema.BatchGetValuesByDataFilterRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.BatchGetValuesByDataFilterResponse])
			}
			object batchGetByDataFilter {
				def apply(spreadsheetId: String)(using auth: AuthToken, ec: ExecutionContext): batchGetByDataFilter = new batchGetByDataFilter(auth(ws.url(BASE_URL + s"v4/spreadsheets/${spreadsheetId}/values:batchGetByDataFilter")).addQueryStringParameters())
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ValueRange]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ValueRange])
			}
			object get {
				def apply(spreadsheetId: String, range: String, majorDimension: String, valueRenderOption: String, dateTimeRenderOption: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v4/spreadsheets/${spreadsheetId}/values/${range}")).addQueryStringParameters("majorDimension" -> majorDimension.toString, "valueRenderOption" -> valueRenderOption.toString, "dateTimeRenderOption" -> dateTimeRenderOption.toString))
				given Conversion[get, Future[Schema.ValueRange]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withValueRange(body: Schema.ValueRange) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.UpdateValuesResponse])
			}
			object update {
				def apply(spreadsheetId: String, range: String, valueInputOption: String, includeValuesInResponse: Boolean, responseValueRenderOption: String, responseDateTimeRenderOption: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"v4/spreadsheets/${spreadsheetId}/values/${range}")).addQueryStringParameters("valueInputOption" -> valueInputOption.toString, "includeValuesInResponse" -> includeValuesInResponse.toString, "responseValueRenderOption" -> responseValueRenderOption.toString, "responseDateTimeRenderOption" -> responseDateTimeRenderOption.toString))
			}
			class batchGet(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.BatchGetValuesResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.BatchGetValuesResponse])
			}
			object batchGet {
				def apply(spreadsheetId: String, ranges: String, majorDimension: String, valueRenderOption: String, dateTimeRenderOption: String)(using auth: AuthToken, ec: ExecutionContext): batchGet = new batchGet(auth(ws.url(BASE_URL + s"v4/spreadsheets/${spreadsheetId}/values:batchGet")).addQueryStringParameters("ranges" -> ranges.toString, "majorDimension" -> majorDimension.toString, "valueRenderOption" -> valueRenderOption.toString, "dateTimeRenderOption" -> dateTimeRenderOption.toString))
				given Conversion[batchGet, Future[Schema.BatchGetValuesResponse]] = (fun: batchGet) => fun.apply()
			}
			class append(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withValueRange(body: Schema.ValueRange) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.AppendValuesResponse])
			}
			object append {
				def apply(spreadsheetId: String, range: String, valueInputOption: String, insertDataOption: String, includeValuesInResponse: Boolean, responseValueRenderOption: String, responseDateTimeRenderOption: String)(using auth: AuthToken, ec: ExecutionContext): append = new append(auth(ws.url(BASE_URL + s"v4/spreadsheets/${spreadsheetId}/values/${range}:append")).addQueryStringParameters("valueInputOption" -> valueInputOption.toString, "insertDataOption" -> insertDataOption.toString, "includeValuesInResponse" -> includeValuesInResponse.toString, "responseValueRenderOption" -> responseValueRenderOption.toString, "responseDateTimeRenderOption" -> responseDateTimeRenderOption.toString))
			}
			class batchClearByDataFilter(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withBatchClearValuesByDataFilterRequest(body: Schema.BatchClearValuesByDataFilterRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.BatchClearValuesByDataFilterResponse])
			}
			object batchClearByDataFilter {
				def apply(spreadsheetId: String)(using auth: AuthToken, ec: ExecutionContext): batchClearByDataFilter = new batchClearByDataFilter(auth(ws.url(BASE_URL + s"v4/spreadsheets/${spreadsheetId}/values:batchClearByDataFilter")).addQueryStringParameters())
			}
			class batchClear(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withBatchClearValuesRequest(body: Schema.BatchClearValuesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.BatchClearValuesResponse])
			}
			object batchClear {
				def apply(spreadsheetId: String)(using auth: AuthToken, ec: ExecutionContext): batchClear = new batchClear(auth(ws.url(BASE_URL + s"v4/spreadsheets/${spreadsheetId}/values:batchClear")).addQueryStringParameters())
			}
		}
		object developerMetadata {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DeveloperMetadata]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.DeveloperMetadata])
			}
			object get {
				def apply(spreadsheetId: String, metadataId: Int)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v4/spreadsheets/${spreadsheetId}/developerMetadata/${metadataId}")).addQueryStringParameters())
				given Conversion[get, Future[Schema.DeveloperMetadata]] = (fun: get) => fun.apply()
			}
			class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSearchDeveloperMetadataRequest(body: Schema.SearchDeveloperMetadataRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SearchDeveloperMetadataResponse])
			}
			object search {
				def apply(spreadsheetId: String)(using auth: AuthToken, ec: ExecutionContext): search = new search(auth(ws.url(BASE_URL + s"v4/spreadsheets/${spreadsheetId}/developerMetadata:search")).addQueryStringParameters())
			}
		}
	}
}
