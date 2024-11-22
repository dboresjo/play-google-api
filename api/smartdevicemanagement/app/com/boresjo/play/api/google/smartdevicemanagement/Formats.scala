package com.boresjo.play.api.google.smartdevicemanagement

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtGoogleHomeEnterpriseSdmV1ExecuteDeviceCommandResponse: Format[Schema.GoogleHomeEnterpriseSdmV1ExecuteDeviceCommandResponse] = Json.format[Schema.GoogleHomeEnterpriseSdmV1ExecuteDeviceCommandResponse]
	given fmtGoogleHomeEnterpriseSdmV1Device: Format[Schema.GoogleHomeEnterpriseSdmV1Device] = Json.format[Schema.GoogleHomeEnterpriseSdmV1Device]
	given fmtGoogleHomeEnterpriseSdmV1ParentRelation: Format[Schema.GoogleHomeEnterpriseSdmV1ParentRelation] = Json.format[Schema.GoogleHomeEnterpriseSdmV1ParentRelation]
	given fmtGoogleHomeEnterpriseSdmV1ListRoomsResponse: Format[Schema.GoogleHomeEnterpriseSdmV1ListRoomsResponse] = Json.format[Schema.GoogleHomeEnterpriseSdmV1ListRoomsResponse]
	given fmtGoogleHomeEnterpriseSdmV1Room: Format[Schema.GoogleHomeEnterpriseSdmV1Room] = Json.format[Schema.GoogleHomeEnterpriseSdmV1Room]
	given fmtGoogleHomeEnterpriseSdmV1ListDevicesResponse: Format[Schema.GoogleHomeEnterpriseSdmV1ListDevicesResponse] = Json.format[Schema.GoogleHomeEnterpriseSdmV1ListDevicesResponse]
	given fmtGoogleHomeEnterpriseSdmV1ListStructuresResponse: Format[Schema.GoogleHomeEnterpriseSdmV1ListStructuresResponse] = Json.format[Schema.GoogleHomeEnterpriseSdmV1ListStructuresResponse]
	given fmtGoogleHomeEnterpriseSdmV1Structure: Format[Schema.GoogleHomeEnterpriseSdmV1Structure] = Json.format[Schema.GoogleHomeEnterpriseSdmV1Structure]
	given fmtGoogleHomeEnterpriseSdmV1ExecuteDeviceCommandRequest: Format[Schema.GoogleHomeEnterpriseSdmV1ExecuteDeviceCommandRequest] = Json.format[Schema.GoogleHomeEnterpriseSdmV1ExecuteDeviceCommandRequest]
}
