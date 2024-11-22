package com.boresjo.play.api.google.homegraph

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtReportStateAndNotificationResponse: Format[Schema.ReportStateAndNotificationResponse] = Json.format[Schema.ReportStateAndNotificationResponse]
	given fmtReportStateAndNotificationDevice: Format[Schema.ReportStateAndNotificationDevice] = Json.format[Schema.ReportStateAndNotificationDevice]
	given fmtAgentOtherDeviceId: Format[Schema.AgentOtherDeviceId] = Json.format[Schema.AgentOtherDeviceId]
	given fmtQueryRequestInput: Format[Schema.QueryRequestInput] = Json.format[Schema.QueryRequestInput]
	given fmtQueryRequestPayload: Format[Schema.QueryRequestPayload] = Json.format[Schema.QueryRequestPayload]
	given fmtAgentDeviceId: Format[Schema.AgentDeviceId] = Json.format[Schema.AgentDeviceId]
	given fmtRequestSyncDevicesRequest: Format[Schema.RequestSyncDevicesRequest] = Json.format[Schema.RequestSyncDevicesRequest]
	given fmtQueryResponse: Format[Schema.QueryResponse] = Json.format[Schema.QueryResponse]
	given fmtQueryResponsePayload: Format[Schema.QueryResponsePayload] = Json.format[Schema.QueryResponsePayload]
	given fmtStateAndNotificationPayload: Format[Schema.StateAndNotificationPayload] = Json.format[Schema.StateAndNotificationPayload]
	given fmtRequestSyncDevicesResponse: Format[Schema.RequestSyncDevicesResponse] = Json.format[Schema.RequestSyncDevicesResponse]
	given fmtSyncRequest: Format[Schema.SyncRequest] = Json.format[Schema.SyncRequest]
	given fmtSyncResponse: Format[Schema.SyncResponse] = Json.format[Schema.SyncResponse]
	given fmtSyncResponsePayload: Format[Schema.SyncResponsePayload] = Json.format[Schema.SyncResponsePayload]
	given fmtDevice: Format[Schema.Device] = Json.format[Schema.Device]
	given fmtQueryRequest: Format[Schema.QueryRequest] = Json.format[Schema.QueryRequest]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtReportStateAndNotificationRequest: Format[Schema.ReportStateAndNotificationRequest] = Json.format[Schema.ReportStateAndNotificationRequest]
	given fmtDeviceNames: Format[Schema.DeviceNames] = Json.format[Schema.DeviceNames]
	given fmtDeviceInfo: Format[Schema.DeviceInfo] = Json.format[Schema.DeviceInfo]
}
