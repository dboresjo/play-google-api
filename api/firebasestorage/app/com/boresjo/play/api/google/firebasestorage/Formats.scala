package com.boresjo.play.api.google.firebasestorage

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtBucket: Format[Schema.Bucket] = Json.format[Schema.Bucket]
	given fmtListBucketsResponse: Format[Schema.ListBucketsResponse] = Json.format[Schema.ListBucketsResponse]
	given fmtDefaultBucket: Format[Schema.DefaultBucket] = Json.format[Schema.DefaultBucket]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtAddFirebaseRequest: Format[Schema.AddFirebaseRequest] = Json.format[Schema.AddFirebaseRequest]
	given fmtRemoveFirebaseRequest: Format[Schema.RemoveFirebaseRequest] = Json.format[Schema.RemoveFirebaseRequest]
	given fmtGoogleFirebaseStorageControlplaneV1alphaMigrateLocationDestructivelyMetadata: Format[Schema.GoogleFirebaseStorageControlplaneV1alphaMigrateLocationDestructivelyMetadata] = Json.format[Schema.GoogleFirebaseStorageControlplaneV1alphaMigrateLocationDestructivelyMetadata]
	given fmtGoogleFirebaseStorageControlplaneV1alphaMigrateLocationDestructivelyMetadataStateEnum: Format[Schema.GoogleFirebaseStorageControlplaneV1alphaMigrateLocationDestructivelyMetadata.StateEnum] = JsonEnumFormat[Schema.GoogleFirebaseStorageControlplaneV1alphaMigrateLocationDestructivelyMetadata.StateEnum]
	given fmtGoogleFirebaseStorageControlplaneV1betaMigrateLocationDestructivelyMetadata: Format[Schema.GoogleFirebaseStorageControlplaneV1betaMigrateLocationDestructivelyMetadata] = Json.format[Schema.GoogleFirebaseStorageControlplaneV1betaMigrateLocationDestructivelyMetadata]
	given fmtGoogleFirebaseStorageControlplaneV1betaMigrateLocationDestructivelyMetadataStateEnum: Format[Schema.GoogleFirebaseStorageControlplaneV1betaMigrateLocationDestructivelyMetadata.StateEnum] = JsonEnumFormat[Schema.GoogleFirebaseStorageControlplaneV1betaMigrateLocationDestructivelyMetadata.StateEnum]
}
