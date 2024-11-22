package com.boresjo.play.api.google.homegraph

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ReportStateAndNotificationResponse(
	  /** Request ID copied from ReportStateAndNotificationRequest. */
		requestId: Option[String] = None
	)
	
	case class ReportStateAndNotificationDevice(
	  /** States of devices to update. See the &#42;&#42;Device STATES&#42;&#42; section of the individual trait [reference guides](https://developers.home.google.com/cloud-to-cloud/traits). */
		states: Option[Map[String, JsValue]] = None,
	  /** Notifications metadata for devices. See the &#42;&#42;Device NOTIFICATIONS&#42;&#42; section of the individual trait [reference guides](https://developers.home.google.com/cloud-to-cloud/traits). */
		notifications: Option[Map[String, JsValue]] = None
	)
	
	case class AgentOtherDeviceId(
	  /** Unique third-party device ID. */
		deviceId: Option[String] = None,
	  /** Project ID for your smart home Action. */
		agentId: Option[String] = None
	)
	
	case class QueryRequestInput(
	  /** Payload containing third-party device IDs. */
		payload: Option[Schema.QueryRequestPayload] = None
	)
	
	case class AgentDeviceId(
	  /** Third-party device ID. */
		id: Option[String] = None
	)
	
	case class RequestSyncDevicesRequest(
	  /** Required. Third-party user ID. */
		agentUserId: Option[String] = None,
	  /** Optional. If set, the request will be added to a queue and a response will be returned immediately. This enables concurrent requests for the given `agent_user_id`, but the caller will not receive any error responses. */
		async: Option[Boolean] = None
	)
	
	case class QueryResponse(
	  /** Request ID used for debugging. Copied from the request. */
		requestId: Option[String] = None,
	  /** Device states for the devices given in the request. */
		payload: Option[Schema.QueryResponsePayload] = None
	)
	
	case class StateAndNotificationPayload(
	  /** The devices for updating state and sending notifications. */
		devices: Option[Schema.ReportStateAndNotificationDevice] = None
	)
	
	case class RequestSyncDevicesResponse(
	
	)
	
	case class SyncRequest(
	  /** Required. Third-party user ID. */
		agentUserId: Option[String] = None,
	  /** Request ID used for debugging. */
		requestId: Option[String] = None
	)
	
	case class SyncResponse(
	  /** Devices associated with the third-party user. */
		payload: Option[Schema.SyncResponsePayload] = None,
	  /** Request ID used for debugging. Copied from the request. */
		requestId: Option[String] = None
	)
	
	case class SyncResponsePayload(
	  /** Devices associated with the third-party user. */
		devices: Option[List[Schema.Device]] = None,
	  /** Third-party user ID */
		agentUserId: Option[String] = None
	)
	
	case class QueryRequest(
	  /** Required. Third-party user ID. */
		agentUserId: Option[String] = None,
	  /** Request ID used for debugging. */
		requestId: Option[String] = None,
	  /** Required. Inputs containing third-party device IDs for which to get the device states. */
		inputs: Option[List[Schema.QueryRequestInput]] = None
	)
	
	case class QueryRequestPayload(
	  /** Third-party device IDs for which to get the device states. */
		devices: Option[List[Schema.AgentDeviceId]] = None
	)
	
	case class QueryResponsePayload(
	  /** States of the devices. Map of third-party device ID to struct of device states. */
		devices: Option[Map[String, Map[String, JsValue]]] = None
	)
	
	case class Empty(
	
	)
	
	case class ReportStateAndNotificationRequest(
	  /** Required. Third-party user ID. */
		agentUserId: Option[String] = None,
	  /** Required. State of devices to update and notification metadata for devices. */
		payload: Option[Schema.StateAndNotificationPayload] = None,
	  /** Deprecated. */
		followUpToken: Option[String] = None,
	  /** Unique identifier per event (for example, a doorbell press). */
		eventId: Option[String] = None,
	  /** Request ID used for debugging. */
		requestId: Option[String] = None
	)
	
	case class DeviceNames(
	  /** List of names provided by the manufacturer rather than the user, such as serial numbers, SKUs, etc. */
		defaultNames: Option[List[String]] = None,
	  /** Primary name of the device, generally provided by the user. */
		name: Option[String] = None,
	  /** Additional names provided by the user for the device. */
		nicknames: Option[List[String]] = None
	)
	
	case class Device(
	  /** Suggested name for the structure where this device is installed. Google attempts to use this value during user setup. */
		structureHint: Option[String] = None,
	  /** Traits supported by the device. See [device traits](https://developers.home.google.com/cloud-to-cloud/traits). */
		traits: Option[List[String]] = None,
	  /** Custom device attributes stored in Home Graph and provided to your smart home Action in each [QUERY](https://developers.home.google.com/cloud-to-cloud/intents/query) and [EXECUTE](https://developers.home.google.com/cloud-to-cloud/intents/execute) intent. Data in this object has a few constraints: No sensitive information, including but not limited to Personally Identifiable Information. */
		customData: Option[Map[String, JsValue]] = None,
	  /** Attributes for the traits supported by the device. */
		attributes: Option[Map[String, JsValue]] = None,
	  /** Device manufacturer, model, hardware version, and software version. */
		deviceInfo: Option[Schema.DeviceInfo] = None,
	  /** Suggested name for the room where this device is installed. Google attempts to use this value during user setup. */
		roomHint: Option[String] = None,
	  /** Names given to this device by your smart home Action. */
		name: Option[Schema.DeviceNames] = None,
	  /** Indicates whether your smart home Action will report notifications to Google for this device via ReportStateAndNotification. If your smart home Action enables users to control device notifications, you should update this field and call RequestSyncDevices. */
		notificationSupportedByAgent: Option[Boolean] = None,
	  /** Hardware type of the device. See [device types](https://developers.home.google.com/cloud-to-cloud/guides). */
		`type`: Option[String] = None,
	  /** Alternate IDs associated with this device. This is used to identify cloud synced devices enabled for [local fulfillment](https://developers.home.google.com/local-home/overview). */
		otherDeviceIds: Option[List[Schema.AgentOtherDeviceId]] = None,
	  /** Indicates whether your smart home Action will report state of this device to Google via ReportStateAndNotification. */
		willReportState: Option[Boolean] = None,
	  /** Third-party device ID. */
		id: Option[String] = None
	)
	
	case class DeviceInfo(
	  /** Device software version. */
		swVersion: Option[String] = None,
	  /** Device manufacturer. */
		manufacturer: Option[String] = None,
	  /** Device model. */
		model: Option[String] = None,
	  /** Device hardware version. */
		hwVersion: Option[String] = None
	)
}
