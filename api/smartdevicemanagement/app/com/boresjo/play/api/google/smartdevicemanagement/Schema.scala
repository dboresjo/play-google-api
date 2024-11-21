package com.boresjo.play.api.google.smartdevicemanagement

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleHomeEnterpriseSdmV1ExecuteDeviceCommandResponse(
	  /** The results of executing the command. */
		results: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleHomeEnterpriseSdmV1Device(
	  /** Required. The resource name of the device. For example: "enterprises/XYZ/devices/123". */
		name: Option[String] = None,
	  /** Output only. Type of the device for general display purposes. For example: "THERMOSTAT". The device type should not be used to deduce or infer functionality of the actual device it is assigned to. Instead, use the returned traits for the device. */
		`type`: Option[String] = None,
	  /** Output only. Device traits. */
		traits: Option[Map[String, JsValue]] = None,
	  /** Assignee details of the device. */
		parentRelations: Option[List[Schema.GoogleHomeEnterpriseSdmV1ParentRelation]] = None
	)
	
	case class GoogleHomeEnterpriseSdmV1ListRoomsResponse(
	  /** The list of rooms. */
		rooms: Option[List[Schema.GoogleHomeEnterpriseSdmV1Room]] = None
	)
	
	case class GoogleHomeEnterpriseSdmV1ParentRelation(
	  /** Output only. The custom name of the relation -- e.g., structure/room where the device is assigned to. */
		displayName: Option[String] = None,
	  /** Output only. The name of the relation -- e.g., structure/room where the device is assigned to. For example: "enterprises/XYZ/structures/ABC" or "enterprises/XYZ/structures/ABC/rooms/123" */
		parent: Option[String] = None
	)
	
	case class GoogleHomeEnterpriseSdmV1ListDevicesResponse(
	  /** The list of devices. */
		devices: Option[List[Schema.GoogleHomeEnterpriseSdmV1Device]] = None
	)
	
	case class GoogleHomeEnterpriseSdmV1Room(
	  /** Output only. The resource name of the room. For example: "enterprises/XYZ/structures/ABC/rooms/123". */
		name: Option[String] = None,
	  /** Room traits. */
		traits: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleHomeEnterpriseSdmV1ListStructuresResponse(
	  /** The list of structures. */
		structures: Option[List[Schema.GoogleHomeEnterpriseSdmV1Structure]] = None
	)
	
	case class GoogleHomeEnterpriseSdmV1ExecuteDeviceCommandRequest(
	  /** The command message to execute, represented as a Struct. */
		params: Option[Map[String, JsValue]] = None,
	  /** The command name to execute, represented by the fully qualified protobuf message name. */
		command: Option[String] = None
	)
	
	case class GoogleHomeEnterpriseSdmV1Structure(
	  /** Output only. The resource name of the structure. For example: "enterprises/XYZ/structures/ABC". */
		name: Option[String] = None,
	  /** Structure traits. */
		traits: Option[Map[String, JsValue]] = None
	)
}
