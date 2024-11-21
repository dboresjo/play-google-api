package com.boresjo.play.api.google.streetviewpublish

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class BatchUpdatePhotosResponse(
	  /** List of results for each individual Photo updated, in the same order as the request. */
		results: Option[List[Schema.PhotoResponse]] = None
	)
	
	case class BatchGetPhotosResponse(
	  /** List of results for each individual Photo requested, in the same order as the requests in BatchGetPhotos. */
		results: Option[List[Schema.PhotoResponse]] = None
	)
	
	case class LatLngBounds(
	  /** The northeast corner of these bounds. */
		northeast: Option[Schema.LatLng] = None,
	  /** The southwest corner of these bounds. */
		southwest: Option[Schema.LatLng] = None
	)
	
	case class UploadRef(
	  /** An upload reference should be unique for each user. It follows the form: "https://streetviewpublish.googleapis.com/media/user/{account_id}/photo/{upload_reference}" */
		uploadUrl: Option[String] = None
	)
	
	case class BatchUpdatePhotosRequest(
	  /** Required. List of UpdatePhotoRequests. */
		updatePhotoRequests: Option[List[Schema.UpdatePhotoRequest]] = None
	)
	
	case class ListPhotoSequencesResponse(
	  /** List of photo sequences via Operation interface. The maximum number of items returned is based on the pageSize field in the request. Each item in the list can have three possible states, &#42; `Operation.done` = false, if the processing of PhotoSequence is not finished yet. &#42; `Operation.done` = true and `Operation.error` is populated, if there was an error in processing. &#42; `Operation.done` = true and `Operation.response` contains a PhotoSequence message, In each sequence, only Id is populated. */
		photoSequences: Option[List[Schema.Operation]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class BatchDeletePhotosResponse(
	  /** The status for the operation to delete a single Photo in the batch request. */
		status: Option[List[Schema.Status]] = None
	)
	
	case class NoOverlapGpsFailureDetails(
	  /** Time of first recorded GPS point. */
		gpsStartTime: Option[String] = None,
	  /** End time of video. */
		videoEndTime: Option[String] = None,
	  /** Start time of video. */
		videoStartTime: Option[String] = None,
	  /** Time of last recorded GPS point. */
		gpsEndTime: Option[String] = None
	)
	
	case class GpsDataGapFailureDetails(
	  /** The duration of the gap in GPS data that was found. */
		gapDuration: Option[String] = None,
	  /** Relative time (from the start of the video stream) when the gap started. */
		gapStartTime: Option[String] = None
	)
	
	case class Status(
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None,
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None
	)
	
	case class PhotoResponse(
	  /** The status for the operation to get or update a single photo in the batch request. */
		status: Option[Schema.Status] = None,
	  /** The Photo resource, if the request was successful. */
		photo: Option[Schema.Photo] = None
	)
	
	case class ListPhotosResponse(
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None,
	  /** List of photos. The pageSize field in the request determines the number of items returned. */
		photos: Option[List[Schema.Photo]] = None
	)
	
	case class Level(
	  /** Optional. Floor number, used for ordering. 0 indicates the ground level, 1 indicates the first level above ground level, -1 indicates the first level under ground level. Non-integer values are OK. */
		number: Option[BigDecimal] = None,
	  /** Required. A name assigned to this Level, restricted to 3 characters. Consider how the elevator buttons would be labeled for this level if there was an elevator. */
		name: Option[String] = None
	)
	
	case class ImuDataGapFailureDetails(
	  /** The duration of the gap in IMU data that was found. */
		gapDuration: Option[String] = None,
	  /** Relative time (from the start of the video stream) when the gap started. */
		gapStartTime: Option[String] = None
	)
	
	case class InsufficientGpsFailureDetails(
	  /** The number of GPS points that were found in the video. */
		gpsPointsFound: Option[Int] = None
	)
	
	case class BatchDeletePhotosRequest(
	  /** Required. IDs of the Photos. HTTP GET requests require the following syntax for the URL query parameter: `photoIds=&photoIds=&...`. */
		photoIds: Option[List[String]] = None
	)
	
	case class Imu(
	  /** The magnetometer measurements of the magnetic field in microtesla (uT) with increasing timestamps from devices. */
		magUt: Option[List[Schema.Measurement3d]] = None,
	  /** The accelerometer measurements in meters/sec^2 with increasing timestamps from devices. */
		accelMpsps: Option[List[Schema.Measurement3d]] = None,
	  /** The gyroscope measurements in radians/sec with increasing timestamps from devices. */
		gyroRps: Option[List[Schema.Measurement3d]] = None
	)
	
	case class ProcessingFailureDetails(
	  /** See NotOutdoorsFailureDetails. */
		notOutdoorsDetails: Option[Schema.NotOutdoorsFailureDetails] = None,
	  /** See ImuDataGapFailureDetails. */
		imuDataGapDetails: Option[Schema.ImuDataGapFailureDetails] = None,
	  /** See NoOverlapGpsFailureDetails. */
		noOverlapGpsDetails: Option[Schema.NoOverlapGpsFailureDetails] = None,
	  /** See InsufficientGpsFailureDetails. */
		insufficientGpsDetails: Option[Schema.InsufficientGpsFailureDetails] = None,
	  /** See GpsDataGapFailureDetails. */
		gpsDataGapDetails: Option[Schema.GpsDataGapFailureDetails] = None
	)
	
	object Photo {
		enum TransferStatusEnum extends Enum[TransferStatusEnum] { case TRANSFER_STATUS_UNKNOWN, NEVER_TRANSFERRED, PENDING, COMPLETED, REJECTED, EXPIRED, CANCELLED, RECEIVED_VIA_TRANSFER }
		enum MapsPublishStatusEnum extends Enum[MapsPublishStatusEnum] { case UNSPECIFIED_MAPS_PUBLISH_STATUS, PUBLISHED, REJECTED_UNKNOWN }
	}
	case class Photo(
	  /** Optional. Pose of the photo. */
		pose: Option[Schema.Pose] = None,
	  /** Output only. The thumbnail URL for showing a preview of the given photo. */
		thumbnailUrl: Option[String] = None,
	  /** Required. Output only. Required when updating a photo. Output only when creating a photo. Identifier for the photo, which is unique among all photos in Google. */
		photoId: Option[Schema.PhotoId] = None,
	  /** Output only. View count of the photo. */
		viewCount: Option[String] = None,
	  /** Output only. The download URL for the photo bytes. This field is set only when GetPhotoRequest.view is set to PhotoView.INCLUDE_DOWNLOAD_URL. */
		downloadUrl: Option[String] = None,
	  /** Output only. Status of rights transfer on this photo. */
		transferStatus: Option[Schema.Photo.TransferStatusEnum] = None,
	  /** Output only. Status in Google Maps, whether this photo was published or rejected. */
		mapsPublishStatus: Option[Schema.Photo.MapsPublishStatusEnum] = None,
	  /** Optional. Connections to other photos. A connection represents the link from this photo to another photo. */
		connections: Option[List[Schema.Connection]] = None,
	  /** Output only. Time when the image was uploaded. */
		uploadTime: Option[String] = None,
	  /** Input only. Required when creating a photo. Input only. The resource URL where the photo bytes are uploaded to. */
		uploadReference: Option[Schema.UploadRef] = None,
	  /** Optional. Places where this photo belongs. */
		places: Option[List[Schema.Place]] = None,
	  /** Output only. The share link for the photo. */
		shareLink: Option[String] = None,
	  /** Optional. Absolute time when the photo was captured. When the photo has no exif timestamp, this is used to set a timestamp in the photo metadata. */
		captureTime: Option[String] = None
	)
	
	case class NotOutdoorsFailureDetails(
	  /** Relative time (from the start of the video stream) when an indoor frame was found. */
		startTime: Option[String] = None
	)
	
	case class UpdatePhotoRequest(
	  /** Required. Photo object containing the new metadata. */
		photo: Option[Schema.Photo] = None,
	  /** Required. Mask that identifies fields on the photo metadata to update. If not present, the old Photo metadata is entirely replaced with the new Photo metadata in this request. The update fails if invalid fields are specified. Multiple fields can be specified in a comma-delimited list. The following fields are valid: &#42; `pose.heading` &#42; `pose.lat_lng_pair` &#42; `pose.pitch` &#42; `pose.roll` &#42; `pose.level` &#42; `pose.altitude` &#42; `connections` &#42; `places` > Note: When updateMask contains repeated fields, the entire set of repeated values get replaced with the new contents. For example, if updateMask contains `connections` and `UpdatePhotoRequest.photo.connections` is empty, all connections are removed. */
		updateMask: Option[String] = None
	)
	
	case class LatLng(
	  /** The longitude in degrees. It must be in the range [-180.0, +180.0]. */
		longitude: Option[BigDecimal] = None,
	  /** The latitude in degrees. It must be in the range [-90.0, +90.0]. */
		latitude: Option[BigDecimal] = None
	)
	
	case class Connection(
	  /** Required. The destination of the connection from the containing photo to another photo. */
		target: Option[Schema.PhotoId] = None
	)
	
	case class PhotoId(
	  /** A unique identifier for a photo. */
		id: Option[String] = None
	)
	
	case class Operation(
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None,
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.Status] = None
	)
	
	case class Place(
	  /** Output only. The name of the place, localized to the language_code. */
		name: Option[String] = None,
	  /** Place identifier, as described in https://developers.google.com/places/place-id. */
		placeId: Option[String] = None,
	  /** Output only. The language_code that the name is localized with. This should be the language_code specified in the request, but may be a fallback. */
		languageCode: Option[String] = None
	)
	
	case class Pose(
	  /** Roll, measured in degrees. Value must be >= 0 and <360. A value of 0 means level with the horizon. NaN indicates an unmeasured quantity. */
		roll: Option[BigDecimal] = None,
	  /** Pitch, measured at the center of the photo in degrees. Value must be >=-90 and <= 90. A value of -90 means looking directly down, and a value of 90 means looking directly up. NaN indicates an unmeasured quantity. */
		pitch: Option[BigDecimal] = None,
	  /** The following pose parameters pertain to the center of the photo. They match https://developers.google.com/streetview/spherical-metadata. Compass heading, measured at the center of the photo in degrees clockwise from North. Value must be >=0 and <360. NaN indicates an unmeasured quantity. */
		heading: Option[BigDecimal] = None,
	  /** The estimated horizontal accuracy of this pose in meters with 68% confidence (one standard deviation). For example, on Android, this value is available from this method: https://developer.android.com/reference/android/location/Location#getAccuracy(). Other platforms have different methods of obtaining similar accuracy estimations. */
		accuracyMeters: Option[BigDecimal] = None,
	  /** Level (the floor in a building) used to configure vertical navigation. */
		level: Option[Schema.Level] = None,
	  /** Altitude of the pose in meters above WGS84 ellipsoid. NaN indicates an unmeasured quantity. */
		altitude: Option[BigDecimal] = None,
	  /** Time of the GPS record since UTC epoch. */
		gpsRecordTimestampUnixEpoch: Option[String] = None,
	  /** Latitude and longitude pair of the pose, as explained here: https://cloud.google.com/datastore/docs/reference/rest/Shared.Types/LatLng When creating a Photo, if the latitude and longitude pair are not provided, the geolocation from the exif header is used. A latitude and longitude pair not provided in the photo or exif header causes the photo process to fail. */
		latLngPair: Option[Schema.LatLng] = None
	)
	
	case class Empty(
	
	)
	
	object PhotoSequence {
		enum ProcessingStateEnum extends Enum[ProcessingStateEnum] { case PROCESSING_STATE_UNSPECIFIED, PENDING, PROCESSING, PROCESSED, FAILED }
		enum GpsSourceEnum extends Enum[GpsSourceEnum] { case PHOTO_SEQUENCE, CAMERA_MOTION_METADATA_TRACK }
		enum FailureReasonEnum extends Enum[FailureReasonEnum] { case PROCESSING_FAILURE_REASON_UNSPECIFIED, LOW_RESOLUTION, DUPLICATE, INSUFFICIENT_GPS, NO_OVERLAP_GPS, INVALID_GPS, FAILED_TO_REFINE_POSITIONS, TAKEDOWN, CORRUPT_VIDEO, INTERNAL, INVALID_VIDEO_FORMAT, INVALID_VIDEO_DIMENSIONS, INVALID_CAPTURE_TIME, GPS_DATA_GAP, JUMPY_GPS, INVALID_IMU, INSUFFICIENT_IMU, INSUFFICIENT_OVERLAP_TIME_SERIES, IMU_DATA_GAP, UNSUPPORTED_CAMERA, NOT_OUTDOORS, INSUFFICIENT_VIDEO_FRAMES, INSUFFICIENT_MOVEMENT, MAST_DOWN, CAMERA_COVERED }
	}
	case class PhotoSequence(
	  /** Output only. The filename of the upload. Does not include the directory path. Only available if the sequence was uploaded on a platform that provides the filename. */
		filename: Option[String] = None,
	  /** Output only. The processing state of this sequence. */
		processingState: Option[Schema.PhotoSequence.ProcessingStateEnum] = None,
	  /** Input only. Required when creating photo sequence. The resource name where the bytes of the photo sequence (in the form of video) are uploaded. */
		uploadReference: Option[Schema.UploadRef] = None,
	  /** Output only. The computed distance of the photo sequence in meters. */
		distanceMeters: Option[BigDecimal] = None,
	  /** Optional. Absolute time when the photo sequence starts to be captured. If the photo sequence is a video, this is the start time of the video. If this field is populated in input, it overrides the capture time in the video or XDM file. */
		captureTimeOverride: Option[String] = None,
	  /** Output only. Photos with increasing timestamps. */
		photos: Option[List[Schema.Photo]] = None,
	  /** Output only. If this sequence has `failure_reason` set, this may contain additional details about the failure. */
		failureDetails: Option[Schema.ProcessingFailureDetails] = None,
	  /** Output only. The total number of views that all the published images in this PhotoSequence have received. */
		viewCount: Option[String] = None,
	  /** Input only. Three axis IMU data for the collection. If this data is too large to put in the request, then it should be put in the CAMM track for the video. This data always takes precedence over the equivalent CAMM data, if it exists. */
		imu: Option[Schema.Imu] = None,
	  /** Output only. Unique identifier for the photo sequence. This also acts as a long running operation ID if uploading is performed asynchronously. */
		id: Option[String] = None,
	  /** Input only. If both raw_gps_timeline and the Camera Motion Metadata Track (CAMM) contain GPS measurements, indicate which takes precedence. */
		gpsSource: Option[Schema.PhotoSequence.GpsSourceEnum] = None,
	  /** Output only. If this sequence has processing_state = FAILED, this will contain the reason why it failed. If the processing_state is any other value, this field will be unset. */
		failureReason: Option[Schema.PhotoSequence.FailureReasonEnum] = None,
	  /** Output only. A rectangular box that encapsulates every image in this photo sequence. */
		sequenceBounds: Option[Schema.LatLngBounds] = None,
	  /** Output only. The time this photo sequence was created in uSV Store service. */
		uploadTime: Option[String] = None,
	  /** Input only. Raw GPS measurements with increasing timestamps from the device that aren't time synced with each photo. These raw measurements will be used to infer the pose of each frame. Required in input when InputType is VIDEO and raw GPS measurements are not in Camera Motion Metadata Track (CAMM). User can indicate which takes precedence using gps_source if raw GPS measurements are provided in both raw_gps_timeline and Camera Motion Metadata Track (CAMM). */
		rawGpsTimeline: Option[List[Schema.Pose]] = None
	)
	
	case class Measurement3d(
	  /** The timestamp of the IMU measurement. */
		captureTime: Option[String] = None,
	  /** The sensor measurement in the y axis. */
		y: Option[BigDecimal] = None,
	  /** The sensor measurement in the z axis. */
		z: Option[BigDecimal] = None,
	  /** The sensor measurement in the x axis. */
		x: Option[BigDecimal] = None
	)
}
