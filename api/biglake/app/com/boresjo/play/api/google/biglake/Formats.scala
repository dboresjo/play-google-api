package com.boresjo.play.api.google.biglake

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtCatalog: Format[Schema.Catalog] = Json.format[Schema.Catalog]
	given fmtListCatalogsResponse: Format[Schema.ListCatalogsResponse] = Json.format[Schema.ListCatalogsResponse]
	given fmtDatabase: Format[Schema.Database] = Json.format[Schema.Database]
	given fmtHiveDatabaseOptions: Format[Schema.HiveDatabaseOptions] = Json.format[Schema.HiveDatabaseOptions]
	given fmtDatabaseTypeEnum: Format[Schema.Database.TypeEnum] = JsonEnumFormat[Schema.Database.TypeEnum]
	given fmtListDatabasesResponse: Format[Schema.ListDatabasesResponse] = Json.format[Schema.ListDatabasesResponse]
	given fmtTable: Format[Schema.Table] = Json.format[Schema.Table]
	given fmtHiveTableOptions: Format[Schema.HiveTableOptions] = Json.format[Schema.HiveTableOptions]
	given fmtTableTypeEnum: Format[Schema.Table.TypeEnum] = JsonEnumFormat[Schema.Table.TypeEnum]
	given fmtStorageDescriptor: Format[Schema.StorageDescriptor] = Json.format[Schema.StorageDescriptor]
	given fmtSerDeInfo: Format[Schema.SerDeInfo] = Json.format[Schema.SerDeInfo]
	given fmtRenameTableRequest: Format[Schema.RenameTableRequest] = Json.format[Schema.RenameTableRequest]
	given fmtListTablesResponse: Format[Schema.ListTablesResponse] = Json.format[Schema.ListTablesResponse]
}
