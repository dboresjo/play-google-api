package com.boresjo.play.api.google.readerrevenuesubscriptionlinking

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://readerrevenuesubscriptionlinking.googleapis.com/"

	object publications {
		object readers {
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DeleteReaderResponse]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.DeleteReaderResponse])
			}
			object delete {
				def apply(publicationsId :PlayApi, readersId :PlayApi, name: String, force: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/publications/${publicationsId}/readers/${readersId}").addQueryStringParameters("name" -> name.toString, "force" -> force.toString))
				given Conversion[delete, Future[Schema.DeleteReaderResponse]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Reader]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Reader])
			}
			object get {
				def apply(publicationsId :PlayApi, readersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/publications/${publicationsId}/readers/${readersId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Reader]] = (fun: get) => fun.apply()
			}
			class updateEntitlements(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** Optional. The list of fields to update. Defaults to all fields.<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new updateEntitlements(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				def withReaderEntitlements(body: Schema.ReaderEntitlements) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.ReaderEntitlements])
			}
			object updateEntitlements {
				def apply(publicationsId :PlayApi, readersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): updateEntitlements = new updateEntitlements(ws.url(BASE_URL + s"v1/publications/${publicationsId}/readers/${readersId}/entitlements").addQueryStringParameters("name" -> name.toString))
			}
			class getEntitlements(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ReaderEntitlements]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ReaderEntitlements])
			}
			object getEntitlements {
				def apply(publicationsId :PlayApi, readersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getEntitlements = new getEntitlements(ws.url(BASE_URL + s"v1/publications/${publicationsId}/readers/${readersId}/entitlements").addQueryStringParameters("name" -> name.toString))
				given Conversion[getEntitlements, Future[Schema.ReaderEntitlements]] = (fun: getEntitlements) => fun.apply()
			}
		}
	}
}
