package com.boresjo.play.api.google.homegraph

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putSchemaQueryRequestPayload: Conversion[Schema.QueryRequestPayload, Option[Schema.QueryRequestPayload]] = (fun: Schema.QueryRequestPayload) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaQueryResponsePayload: Conversion[Schema.QueryResponsePayload, Option[Schema.QueryResponsePayload]] = (fun: Schema.QueryResponsePayload) => Option(fun)
		given putSchemaReportStateAndNotificationDevice: Conversion[Schema.ReportStateAndNotificationDevice, Option[Schema.ReportStateAndNotificationDevice]] = (fun: Schema.ReportStateAndNotificationDevice) => Option(fun)
		given putSchemaSyncResponsePayload: Conversion[Schema.SyncResponsePayload, Option[Schema.SyncResponsePayload]] = (fun: Schema.SyncResponsePayload) => Option(fun)
		given putListSchemaDevice: Conversion[List[Schema.Device], Option[List[Schema.Device]]] = (fun: List[Schema.Device]) => Option(fun)
		given putListSchemaQueryRequestInput: Conversion[List[Schema.QueryRequestInput], Option[List[Schema.QueryRequestInput]]] = (fun: List[Schema.QueryRequestInput]) => Option(fun)
		given putListSchemaAgentDeviceId: Conversion[List[Schema.AgentDeviceId], Option[List[Schema.AgentDeviceId]]] = (fun: List[Schema.AgentDeviceId]) => Option(fun)
		given putMapStringMapStringJsValue: Conversion[Map[String, Map[String, JsValue]], Option[Map[String, Map[String, JsValue]]]] = (fun: Map[String, Map[String, JsValue]]) => Option(fun)
		given putSchemaStateAndNotificationPayload: Conversion[Schema.StateAndNotificationPayload, Option[Schema.StateAndNotificationPayload]] = (fun: Schema.StateAndNotificationPayload) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaDeviceInfo: Conversion[Schema.DeviceInfo, Option[Schema.DeviceInfo]] = (fun: Schema.DeviceInfo) => Option(fun)
		given putSchemaDeviceNames: Conversion[Schema.DeviceNames, Option[Schema.DeviceNames]] = (fun: Schema.DeviceNames) => Option(fun)
		given putListSchemaAgentOtherDeviceId: Conversion[List[Schema.AgentOtherDeviceId], Option[List[Schema.AgentOtherDeviceId]]] = (fun: List[Schema.AgentOtherDeviceId]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
