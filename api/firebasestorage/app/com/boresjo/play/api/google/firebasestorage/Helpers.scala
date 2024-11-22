package com.boresjo.play.api.google.firebasestorage

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListSchemaBucket: Conversion[List[Schema.Bucket], Option[List[Schema.Bucket]]] = (fun: List[Schema.Bucket]) => Option(fun)
		given putSchemaBucket: Conversion[Schema.Bucket, Option[Schema.Bucket]] = (fun: Schema.Bucket) => Option(fun)
		given putSchemaGoogleFirebaseStorageControlplaneV1alphaMigrateLocationDestructivelyMetadataStateEnum: Conversion[Schema.GoogleFirebaseStorageControlplaneV1alphaMigrateLocationDestructivelyMetadata.StateEnum, Option[Schema.GoogleFirebaseStorageControlplaneV1alphaMigrateLocationDestructivelyMetadata.StateEnum]] = (fun: Schema.GoogleFirebaseStorageControlplaneV1alphaMigrateLocationDestructivelyMetadata.StateEnum) => Option(fun)
		given putSchemaGoogleFirebaseStorageControlplaneV1betaMigrateLocationDestructivelyMetadataStateEnum: Conversion[Schema.GoogleFirebaseStorageControlplaneV1betaMigrateLocationDestructivelyMetadata.StateEnum, Option[Schema.GoogleFirebaseStorageControlplaneV1betaMigrateLocationDestructivelyMetadata.StateEnum]] = (fun: Schema.GoogleFirebaseStorageControlplaneV1betaMigrateLocationDestructivelyMetadata.StateEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
