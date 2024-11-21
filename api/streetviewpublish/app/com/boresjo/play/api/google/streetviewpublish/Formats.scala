package com.boresjo.play.api.google.streetviewpublish

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtBatchUpdatePhotosResponse: Format[Schema.BatchUpdatePhotosResponse] = Json.format[Schema.BatchUpdatePhotosResponse]
	given fmtPhotoResponse: Format[Schema.PhotoResponse] = Json.format[Schema.PhotoResponse]
	given fmtBatchGetPhotosResponse: Format[Schema.BatchGetPhotosResponse] = Json.format[Schema.BatchGetPhotosResponse]
	given fmtLatLngBounds: Format[Schema.LatLngBounds] = Json.format[Schema.LatLngBounds]
	given fmtLatLng: Format[Schema.LatLng] = Json.format[Schema.LatLng]
	given fmtUploadRef: Format[Schema.UploadRef] = Json.format[Schema.UploadRef]
	given fmtBatchUpdatePhotosRequest: Format[Schema.BatchUpdatePhotosRequest] = Json.format[Schema.BatchUpdatePhotosRequest]
	given fmtUpdatePhotoRequest: Format[Schema.UpdatePhotoRequest] = Json.format[Schema.UpdatePhotoRequest]
	given fmtListPhotoSequencesResponse: Format[Schema.ListPhotoSequencesResponse] = Json.format[Schema.ListPhotoSequencesResponse]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtBatchDeletePhotosResponse: Format[Schema.BatchDeletePhotosResponse] = Json.format[Schema.BatchDeletePhotosResponse]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtNoOverlapGpsFailureDetails: Format[Schema.NoOverlapGpsFailureDetails] = Json.format[Schema.NoOverlapGpsFailureDetails]
	given fmtGpsDataGapFailureDetails: Format[Schema.GpsDataGapFailureDetails] = Json.format[Schema.GpsDataGapFailureDetails]
	given fmtPhoto: Format[Schema.Photo] = Json.format[Schema.Photo]
	given fmtListPhotosResponse: Format[Schema.ListPhotosResponse] = Json.format[Schema.ListPhotosResponse]
	given fmtLevel: Format[Schema.Level] = Json.format[Schema.Level]
	given fmtImuDataGapFailureDetails: Format[Schema.ImuDataGapFailureDetails] = Json.format[Schema.ImuDataGapFailureDetails]
	given fmtInsufficientGpsFailureDetails: Format[Schema.InsufficientGpsFailureDetails] = Json.format[Schema.InsufficientGpsFailureDetails]
	given fmtBatchDeletePhotosRequest: Format[Schema.BatchDeletePhotosRequest] = Json.format[Schema.BatchDeletePhotosRequest]
	given fmtImu: Format[Schema.Imu] = Json.format[Schema.Imu]
	given fmtMeasurement3d: Format[Schema.Measurement3d] = Json.format[Schema.Measurement3d]
	given fmtProcessingFailureDetails: Format[Schema.ProcessingFailureDetails] = Json.format[Schema.ProcessingFailureDetails]
	given fmtNotOutdoorsFailureDetails: Format[Schema.NotOutdoorsFailureDetails] = Json.format[Schema.NotOutdoorsFailureDetails]
	given fmtPose: Format[Schema.Pose] = Json.format[Schema.Pose]
	given fmtPhotoId: Format[Schema.PhotoId] = Json.format[Schema.PhotoId]
	given fmtPhotoTransferStatusEnum: Format[Schema.Photo.TransferStatusEnum] = JsonEnumFormat[Schema.Photo.TransferStatusEnum]
	given fmtPhotoMapsPublishStatusEnum: Format[Schema.Photo.MapsPublishStatusEnum] = JsonEnumFormat[Schema.Photo.MapsPublishStatusEnum]
	given fmtConnection: Format[Schema.Connection] = Json.format[Schema.Connection]
	given fmtPlace: Format[Schema.Place] = Json.format[Schema.Place]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtPhotoSequence: Format[Schema.PhotoSequence] = Json.format[Schema.PhotoSequence]
	given fmtPhotoSequenceProcessingStateEnum: Format[Schema.PhotoSequence.ProcessingStateEnum] = JsonEnumFormat[Schema.PhotoSequence.ProcessingStateEnum]
	given fmtPhotoSequenceGpsSourceEnum: Format[Schema.PhotoSequence.GpsSourceEnum] = JsonEnumFormat[Schema.PhotoSequence.GpsSourceEnum]
	given fmtPhotoSequenceFailureReasonEnum: Format[Schema.PhotoSequence.FailureReasonEnum] = JsonEnumFormat[Schema.PhotoSequence.FailureReasonEnum]
}
