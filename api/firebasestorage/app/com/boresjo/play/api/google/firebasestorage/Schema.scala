package com.boresjo.play.api.google.firebasestorage

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class Bucket(
	  /** Output only. Resource name of the bucket. */
		name: Option[String] = None
	)
	
	case class ListBucketsResponse(
	  /** The list of linked buckets. */
		buckets: Option[List[Schema.Bucket]] = None,
	  /** A token that can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class DefaultBucket(
	  /** Resource name of the default bucket. */
		name: Option[String] = None,
	  /** Immutable. Location of the default bucket. */
		location: Option[String] = None,
	  /** Output only. Underlying bucket resource. */
		bucket: Option[Schema.Bucket] = None,
	  /** Immutable. Storage class of the default bucket. Supported values are available at https://cloud.google.com/storage/docs/storage-classes#classes. */
		storageClass: Option[String] = None
	)
	
	case class Empty(
	
	)
	
	case class AddFirebaseRequest(
	
	)
	
	case class RemoveFirebaseRequest(
	
	)
	
	object GoogleFirebaseStorageControlplaneV1alphaMigrateLocationDestructivelyMetadata {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PENDING, CREATING_TEMP_BUCKET, TRANSFERRING_TO_TEMP, DELETING_SOURCE_BUCKET, CREATING_DESTINATION_BUCKET, TRANSFERRING_TO_DESTINATION, DELETING_TEMP_BUCKET, SUCCEEDED, FAILED, ROLLING_BACK, ROLLED_BACK }
	}
	case class GoogleFirebaseStorageControlplaneV1alphaMigrateLocationDestructivelyMetadata(
	  /** The current state of the migration. */
		state: Option[Schema.GoogleFirebaseStorageControlplaneV1alphaMigrateLocationDestructivelyMetadata.StateEnum] = None,
	  /** The time the LRO was created. */
		createTime: Option[String] = None,
	  /** The time the LRO was last updated. */
		lastUpdateTime: Option[String] = None
	)
	
	object GoogleFirebaseStorageControlplaneV1betaMigrateLocationDestructivelyMetadata {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PENDING, CREATING_TEMP_BUCKET, TRANSFERRING_TO_TEMP, DELETING_SOURCE_BUCKET, CREATING_DESTINATION_BUCKET, TRANSFERRING_TO_DESTINATION, DELETING_TEMP_BUCKET, SUCCEEDED, FAILED, ROLLING_BACK, ROLLED_BACK }
	}
	case class GoogleFirebaseStorageControlplaneV1betaMigrateLocationDestructivelyMetadata(
	  /** The current state of the migration. */
		state: Option[Schema.GoogleFirebaseStorageControlplaneV1betaMigrateLocationDestructivelyMetadata.StateEnum] = None,
	  /** The time the LRO was created. */
		createTime: Option[String] = None,
	  /** The time the LRO was last updated. */
		lastUpdateTime: Option[String] = None
	)
}
