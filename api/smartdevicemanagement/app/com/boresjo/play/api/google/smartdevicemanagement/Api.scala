package com.boresjo.play.api.google.smartdevicemanagement

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://smartdevicemanagement.googleapis.com/"

	object enterprises {
		object devices {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleHomeEnterpriseSdmV1Device]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleHomeEnterpriseSdmV1Device])
			}
			object get {
				def apply(enterprisesId :PlayApi, devicesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/devices/${devicesId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleHomeEnterpriseSdmV1Device]] = (fun: get) => fun.apply()
			}
			class executeCommand(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleHomeEnterpriseSdmV1ExecuteDeviceCommandRequest(body: Schema.GoogleHomeEnterpriseSdmV1ExecuteDeviceCommandRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleHomeEnterpriseSdmV1ExecuteDeviceCommandResponse])
			}
			object executeCommand {
				def apply(enterprisesId :PlayApi, devicesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): executeCommand = new executeCommand(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/devices/${devicesId}:executeCommand")).addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleHomeEnterpriseSdmV1ListDevicesResponse]) {
				/** Optional filter to list devices. Filters can be done on: Device custom name (substring match): 'customName=wing' */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleHomeEnterpriseSdmV1ListDevicesResponse])
			}
			object list {
				def apply(enterprisesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/devices")).addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleHomeEnterpriseSdmV1ListDevicesResponse]] = (fun: list) => fun.apply()
			}
		}
		object structures {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleHomeEnterpriseSdmV1ListStructuresResponse]) {
				/** Optional filter to list structures. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleHomeEnterpriseSdmV1ListStructuresResponse])
			}
			object list {
				def apply(enterprisesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/structures")).addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleHomeEnterpriseSdmV1ListStructuresResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleHomeEnterpriseSdmV1Structure]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleHomeEnterpriseSdmV1Structure])
			}
			object get {
				def apply(enterprisesId :PlayApi, structuresId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/structures/${structuresId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleHomeEnterpriseSdmV1Structure]] = (fun: get) => fun.apply()
			}
			object rooms {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleHomeEnterpriseSdmV1Room]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleHomeEnterpriseSdmV1Room])
				}
				object get {
					def apply(enterprisesId :PlayApi, structuresId :PlayApi, roomsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/structures/${structuresId}/rooms/${roomsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleHomeEnterpriseSdmV1Room]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleHomeEnterpriseSdmV1ListRoomsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleHomeEnterpriseSdmV1ListRoomsResponse])
				}
				object list {
					def apply(enterprisesId :PlayApi, structuresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/structures/${structuresId}/rooms")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleHomeEnterpriseSdmV1ListRoomsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
