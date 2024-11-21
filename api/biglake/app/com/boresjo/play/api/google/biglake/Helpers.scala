package com.boresjo.play.api.google.biglake

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListSchemaCatalog: Conversion[List[Schema.Catalog], Option[List[Schema.Catalog]]] = (fun: List[Schema.Catalog]) => Option(fun)
		given putSchemaHiveDatabaseOptions: Conversion[Schema.HiveDatabaseOptions, Option[Schema.HiveDatabaseOptions]] = (fun: Schema.HiveDatabaseOptions) => Option(fun)
		given putSchemaDatabaseTypeEnum: Conversion[Schema.Database.TypeEnum, Option[Schema.Database.TypeEnum]] = (fun: Schema.Database.TypeEnum) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putListSchemaDatabase: Conversion[List[Schema.Database], Option[List[Schema.Database]]] = (fun: List[Schema.Database]) => Option(fun)
		given putSchemaHiveTableOptions: Conversion[Schema.HiveTableOptions, Option[Schema.HiveTableOptions]] = (fun: Schema.HiveTableOptions) => Option(fun)
		given putSchemaTableTypeEnum: Conversion[Schema.Table.TypeEnum, Option[Schema.Table.TypeEnum]] = (fun: Schema.Table.TypeEnum) => Option(fun)
		given putSchemaStorageDescriptor: Conversion[Schema.StorageDescriptor, Option[Schema.StorageDescriptor]] = (fun: Schema.StorageDescriptor) => Option(fun)
		given putSchemaSerDeInfo: Conversion[Schema.SerDeInfo, Option[Schema.SerDeInfo]] = (fun: Schema.SerDeInfo) => Option(fun)
		given putListSchemaTable: Conversion[List[Schema.Table], Option[List[Schema.Table]]] = (fun: List[Schema.Table]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
