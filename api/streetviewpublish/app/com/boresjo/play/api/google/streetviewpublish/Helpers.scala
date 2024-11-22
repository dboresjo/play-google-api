package com.boresjo.play.api.google.streetviewpublish

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaPhotoResponse: Conversion[List[Schema.PhotoResponse], Option[List[Schema.PhotoResponse]]] = (fun: List[Schema.PhotoResponse]) => Option(fun)
		given putSchemaLatLng: Conversion[Schema.LatLng, Option[Schema.LatLng]] = (fun: Schema.LatLng) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListSchemaUpdatePhotoRequest: Conversion[List[Schema.UpdatePhotoRequest], Option[List[Schema.UpdatePhotoRequest]]] = (fun: List[Schema.UpdatePhotoRequest]) => Option(fun)
		given putListSchemaOperation: Conversion[List[Schema.Operation], Option[List[Schema.Operation]]] = (fun: List[Schema.Operation]) => Option(fun)
		given putListSchemaStatus: Conversion[List[Schema.Status], Option[List[Schema.Status]]] = (fun: List[Schema.Status]) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putSchemaPhoto: Conversion[Schema.Photo, Option[Schema.Photo]] = (fun: Schema.Photo) => Option(fun)
		given putListSchemaPhoto: Conversion[List[Schema.Photo], Option[List[Schema.Photo]]] = (fun: List[Schema.Photo]) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putListSchemaMeasurement3d: Conversion[List[Schema.Measurement3d], Option[List[Schema.Measurement3d]]] = (fun: List[Schema.Measurement3d]) => Option(fun)
		given putSchemaNotOutdoorsFailureDetails: Conversion[Schema.NotOutdoorsFailureDetails, Option[Schema.NotOutdoorsFailureDetails]] = (fun: Schema.NotOutdoorsFailureDetails) => Option(fun)
		given putSchemaImuDataGapFailureDetails: Conversion[Schema.ImuDataGapFailureDetails, Option[Schema.ImuDataGapFailureDetails]] = (fun: Schema.ImuDataGapFailureDetails) => Option(fun)
		given putSchemaNoOverlapGpsFailureDetails: Conversion[Schema.NoOverlapGpsFailureDetails, Option[Schema.NoOverlapGpsFailureDetails]] = (fun: Schema.NoOverlapGpsFailureDetails) => Option(fun)
		given putSchemaInsufficientGpsFailureDetails: Conversion[Schema.InsufficientGpsFailureDetails, Option[Schema.InsufficientGpsFailureDetails]] = (fun: Schema.InsufficientGpsFailureDetails) => Option(fun)
		given putSchemaGpsDataGapFailureDetails: Conversion[Schema.GpsDataGapFailureDetails, Option[Schema.GpsDataGapFailureDetails]] = (fun: Schema.GpsDataGapFailureDetails) => Option(fun)
		given putSchemaPose: Conversion[Schema.Pose, Option[Schema.Pose]] = (fun: Schema.Pose) => Option(fun)
		given putSchemaPhotoId: Conversion[Schema.PhotoId, Option[Schema.PhotoId]] = (fun: Schema.PhotoId) => Option(fun)
		given putSchemaPhotoTransferStatusEnum: Conversion[Schema.Photo.TransferStatusEnum, Option[Schema.Photo.TransferStatusEnum]] = (fun: Schema.Photo.TransferStatusEnum) => Option(fun)
		given putSchemaPhotoMapsPublishStatusEnum: Conversion[Schema.Photo.MapsPublishStatusEnum, Option[Schema.Photo.MapsPublishStatusEnum]] = (fun: Schema.Photo.MapsPublishStatusEnum) => Option(fun)
		given putListSchemaConnection: Conversion[List[Schema.Connection], Option[List[Schema.Connection]]] = (fun: List[Schema.Connection]) => Option(fun)
		given putSchemaUploadRef: Conversion[Schema.UploadRef, Option[Schema.UploadRef]] = (fun: Schema.UploadRef) => Option(fun)
		given putListSchemaPlace: Conversion[List[Schema.Place], Option[List[Schema.Place]]] = (fun: List[Schema.Place]) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaLevel: Conversion[Schema.Level, Option[Schema.Level]] = (fun: Schema.Level) => Option(fun)
		given putSchemaPhotoSequenceProcessingStateEnum: Conversion[Schema.PhotoSequence.ProcessingStateEnum, Option[Schema.PhotoSequence.ProcessingStateEnum]] = (fun: Schema.PhotoSequence.ProcessingStateEnum) => Option(fun)
		given putSchemaProcessingFailureDetails: Conversion[Schema.ProcessingFailureDetails, Option[Schema.ProcessingFailureDetails]] = (fun: Schema.ProcessingFailureDetails) => Option(fun)
		given putSchemaImu: Conversion[Schema.Imu, Option[Schema.Imu]] = (fun: Schema.Imu) => Option(fun)
		given putSchemaPhotoSequenceGpsSourceEnum: Conversion[Schema.PhotoSequence.GpsSourceEnum, Option[Schema.PhotoSequence.GpsSourceEnum]] = (fun: Schema.PhotoSequence.GpsSourceEnum) => Option(fun)
		given putSchemaPhotoSequenceFailureReasonEnum: Conversion[Schema.PhotoSequence.FailureReasonEnum, Option[Schema.PhotoSequence.FailureReasonEnum]] = (fun: Schema.PhotoSequence.FailureReasonEnum) => Option(fun)
		given putSchemaLatLngBounds: Conversion[Schema.LatLngBounds, Option[Schema.LatLngBounds]] = (fun: Schema.LatLngBounds) => Option(fun)
		given putListSchemaPose: Conversion[List[Schema.Pose], Option[List[Schema.Pose]]] = (fun: List[Schema.Pose]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
