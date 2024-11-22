package com.boresjo.play.api.google.smartdevicemanagement

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	val scopes = Seq(
		"""https://www.googleapis.com/auth/sdm.service""" /* See and/or control the devices that you selected */
	)

	private val BASE_URL = "https://smartdevicemanagement.googleapis.com/"

	object enterprises {
		object devices {
			/** Gets a device managed by the enterprise. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleHomeEnterpriseSdmV1Device]) {
				val scopes = Seq("""https://www.googleapis.com/auth/sdm.service""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleHomeEnterpriseSdmV1Device])
			}
			object get {
				def apply(enterprisesId :PlayApi, devicesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/devices/${devicesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleHomeEnterpriseSdmV1Device]] = (fun: get) => fun.apply()
			}
			/** Executes a command to device managed by the enterprise. */
			class executeCommand(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/sdm.service""")
				/** Perform the request */
				def withGoogleHomeEnterpriseSdmV1ExecuteDeviceCommandRequest(body: Schema.GoogleHomeEnterpriseSdmV1ExecuteDeviceCommandRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleHomeEnterpriseSdmV1ExecuteDeviceCommandResponse])
			}
			object executeCommand {
				def apply(enterprisesId :PlayApi, devicesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): executeCommand = new executeCommand(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/devices/${devicesId}:executeCommand").addQueryStringParameters("name" -> name.toString))
			}
			/** Lists devices managed by the enterprise. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleHomeEnterpriseSdmV1ListDevicesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/sdm.service""")
				/** Optional filter to list devices. Filters can be done on: Device custom name (substring match): 'customName=wing' */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleHomeEnterpriseSdmV1ListDevicesResponse])
			}
			object list {
				def apply(enterprisesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/devices").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleHomeEnterpriseSdmV1ListDevicesResponse]] = (fun: list) => fun.apply()
			}
		}
		object structures {
			/** Lists structures managed by the enterprise. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleHomeEnterpriseSdmV1ListStructuresResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/sdm.service""")
				/** Optional filter to list structures. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleHomeEnterpriseSdmV1ListStructuresResponse])
			}
			object list {
				def apply(enterprisesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/structures").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleHomeEnterpriseSdmV1ListStructuresResponse]] = (fun: list) => fun.apply()
			}
			/** Gets a structure managed by the enterprise. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleHomeEnterpriseSdmV1Structure]) {
				val scopes = Seq("""https://www.googleapis.com/auth/sdm.service""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleHomeEnterpriseSdmV1Structure])
			}
			object get {
				def apply(enterprisesId :PlayApi, structuresId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/structures/${structuresId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleHomeEnterpriseSdmV1Structure]] = (fun: get) => fun.apply()
			}
			object rooms {
				/** Gets a room managed by the enterprise. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleHomeEnterpriseSdmV1Room]) {
					val scopes = Seq("""https://www.googleapis.com/auth/sdm.service""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleHomeEnterpriseSdmV1Room])
				}
				object get {
					def apply(enterprisesId :PlayApi, structuresId :PlayApi, roomsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/structures/${structuresId}/rooms/${roomsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleHomeEnterpriseSdmV1Room]] = (fun: get) => fun.apply()
				}
				/** Lists rooms managed by the enterprise. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleHomeEnterpriseSdmV1ListRoomsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/sdm.service""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleHomeEnterpriseSdmV1ListRoomsResponse])
				}
				object list {
					def apply(enterprisesId :PlayApi, structuresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/enterprises/${enterprisesId}/structures/${structuresId}/rooms").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleHomeEnterpriseSdmV1ListRoomsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
