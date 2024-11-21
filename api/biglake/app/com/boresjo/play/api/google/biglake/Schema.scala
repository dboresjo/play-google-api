package com.boresjo.play.api.google.biglake

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class Catalog(
	  /** Output only. The resource name. Format: projects/{project_id_or_number}/locations/{location_id}/catalogs/{catalog_id} */
		name: Option[String] = None,
	  /** Output only. The creation time of the catalog. */
		createTime: Option[String] = None,
	  /** Output only. The last modification time of the catalog. */
		updateTime: Option[String] = None,
	  /** Output only. The deletion time of the catalog. Only set after the catalog is deleted. */
		deleteTime: Option[String] = None,
	  /** Output only. The time when this catalog is considered expired. Only set after the catalog is deleted. */
		expireTime: Option[String] = None
	)
	
	case class ListCatalogsResponse(
	  /** The catalogs from the specified project. */
		catalogs: Option[List[Schema.Catalog]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	object Database {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, HIVE }
	}
	case class Database(
	  /** Options of a Hive database. */
		hiveOptions: Option[Schema.HiveDatabaseOptions] = None,
	  /** Output only. The resource name. Format: projects/{project_id_or_number}/locations/{location_id}/catalogs/{catalog_id}/databases/{database_id} */
		name: Option[String] = None,
	  /** Output only. The creation time of the database. */
		createTime: Option[String] = None,
	  /** Output only. The last modification time of the database. */
		updateTime: Option[String] = None,
	  /** Output only. The deletion time of the database. Only set after the database is deleted. */
		deleteTime: Option[String] = None,
	  /** Output only. The time when this database is considered expired. Only set after the database is deleted. */
		expireTime: Option[String] = None,
	  /** The database type. */
		`type`: Option[Schema.Database.TypeEnum] = None
	)
	
	case class HiveDatabaseOptions(
	  /** Cloud Storage folder URI where the database data is stored, starting with "gs://". */
		locationUri: Option[String] = None,
	  /** Stores user supplied Hive database parameters. */
		parameters: Option[Map[String, String]] = None
	)
	
	case class ListDatabasesResponse(
	  /** The databases from the specified catalog. */
		databases: Option[List[Schema.Database]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	object Table {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, HIVE }
	}
	case class Table(
	  /** Options of a Hive table. */
		hiveOptions: Option[Schema.HiveTableOptions] = None,
	  /** Output only. The resource name. Format: projects/{project_id_or_number}/locations/{location_id}/catalogs/{catalog_id}/databases/{database_id}/tables/{table_id} */
		name: Option[String] = None,
	  /** Output only. The creation time of the table. */
		createTime: Option[String] = None,
	  /** Output only. The last modification time of the table. */
		updateTime: Option[String] = None,
	  /** Output only. The deletion time of the table. Only set after the table is deleted. */
		deleteTime: Option[String] = None,
	  /** Output only. The time when this table is considered expired. Only set after the table is deleted. */
		expireTime: Option[String] = None,
	  /** The table type. */
		`type`: Option[Schema.Table.TypeEnum] = None,
	  /** The checksum of a table object computed by the server based on the value of other fields. It may be sent on update requests to ensure the client has an up-to-date value before proceeding. It is only checked for update table operations. */
		etag: Option[String] = None
	)
	
	case class HiveTableOptions(
	  /** Stores user supplied Hive table parameters. */
		parameters: Option[Map[String, String]] = None,
	  /** Hive table type. For example, MANAGED_TABLE, EXTERNAL_TABLE. */
		tableType: Option[String] = None,
	  /** Stores physical storage information of the data. */
		storageDescriptor: Option[Schema.StorageDescriptor] = None
	)
	
	case class StorageDescriptor(
	  /** Cloud Storage folder URI where the table data is stored, starting with "gs://". */
		locationUri: Option[String] = None,
	  /** The fully qualified Java class name of the input format. */
		inputFormat: Option[String] = None,
	  /** The fully qualified Java class name of the output format. */
		outputFormat: Option[String] = None,
	  /** Serializer and deserializer information. */
		serdeInfo: Option[Schema.SerDeInfo] = None
	)
	
	case class SerDeInfo(
	  /** The fully qualified Java class name of the serialization library. */
		serializationLib: Option[String] = None
	)
	
	case class RenameTableRequest(
	  /** Required. The new `name` for the specified table, must be in the same database. Format: projects/{project_id_or_number}/locations/{location_id}/catalogs/{catalog_id}/databases/{database_id}/tables/{table_id} */
		newName: Option[String] = None
	)
	
	case class ListTablesResponse(
	  /** The tables from the specified database. */
		tables: Option[List[Schema.Table]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
}
