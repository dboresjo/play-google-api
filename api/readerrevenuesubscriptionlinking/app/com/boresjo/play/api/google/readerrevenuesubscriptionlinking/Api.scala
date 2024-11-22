package com.boresjo.play.api.google.readerrevenuesubscriptionlinking

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	val scopes = Seq()

	private val BASE_URL = "https://readerrevenuesubscriptionlinking.googleapis.com/"

	object publications {
		object readers {
			/** Removes a publication reader, effectively severing the association with a Google user. If `force` is set to true, any entitlements for this reader will also be deleted. (Otherwise, the request will only work if the reader has no entitlements.) - If the reader does not exist, return NOT_FOUND. - Return FAILED_PRECONDITION if the force field is false (or unset) and entitlements are present. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DeleteReaderResponse]) {
				val scopes = Seq()
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.DeleteReaderResponse])
			}
			object delete {
				def apply(publicationsId :PlayApi, readersId :PlayApi, name: String, force: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/publications/${publicationsId}/readers/${readersId}").addQueryStringParameters("name" -> name.toString, "force" -> force.toString))
				given Conversion[delete, Future[Schema.DeleteReaderResponse]] = (fun: delete) => fun.apply()
			}
			/** Gets a reader of a publication. Returns NOT_FOUND if the reader does not exist. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Reader]) {
				val scopes = Seq()
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Reader])
			}
			object get {
				def apply(publicationsId :PlayApi, readersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/publications/${publicationsId}/readers/${readersId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Reader]] = (fun: get) => fun.apply()
			}
			/** Updates the reader entitlements for a publication reader. The entire reader entitlements will be overwritten by the new reader entitlements in the payload, like a PUT. - Returns PERMISSION_DENIED if the caller does not have access. - Returns NOT_FOUND if the reader does not exist. */
			class updateEntitlements(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq()
				/** Optional. The list of fields to update. Defaults to all fields.<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new updateEntitlements(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				/** Perform the request */
				def withReaderEntitlements(body: Schema.ReaderEntitlements) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.ReaderEntitlements])
			}
			object updateEntitlements {
				def apply(publicationsId :PlayApi, readersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): updateEntitlements = new updateEntitlements(ws.url(BASE_URL + s"v1/publications/${publicationsId}/readers/${readersId}/entitlements").addQueryStringParameters("name" -> name.toString))
			}
			/** Gets the reader entitlements for a publication reader. - Returns PERMISSION_DENIED if the caller does not have access. - Returns NOT_FOUND if the reader does not exist. */
			class getEntitlements(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ReaderEntitlements]) {
				val scopes = Seq()
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ReaderEntitlements])
			}
			object getEntitlements {
				def apply(publicationsId :PlayApi, readersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getEntitlements = new getEntitlements(ws.url(BASE_URL + s"v1/publications/${publicationsId}/readers/${readersId}/entitlements").addQueryStringParameters("name" -> name.toString))
				given Conversion[getEntitlements, Future[Schema.ReaderEntitlements]] = (fun: getEntitlements) => fun.apply()
			}
		}
	}
}
