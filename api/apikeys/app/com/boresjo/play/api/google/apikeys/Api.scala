package com.boresjo.play.api.google.apikeys

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://apikeys.googleapis.com/"

	object operations {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Operation])
		}
		object get {
			def apply(operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
		}
	}
	object projects {
		object locations {
			object keys {
				class undelete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withV2UndeleteKeyRequest(body: Schema.V2UndeleteKeyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object undelete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, keysId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): undelete = new undelete(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/keys/${keysId}:undelete")).addQueryStringParameters("name" -> name.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** User specified key id (optional). If specified, it will become the final component of the key resource name. The id must be unique within the project, must conform with RFC-1034, is restricted to lower-cased letters, and has a maximum length of 63 characters. In another word, the id must match the regular expression: `[a-z]([a-z0-9-]{0,61}[a-z0-9])?`. The id must NOT be a UUID-like string. */
					def withKeyId(keyId: String) = new create(req.addQueryStringParameters("keyId" -> keyId.toString))
					def withV2Key(body: Schema.V2Key) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/keys")).addQueryStringParameters("parent" -> parent.toString))
				}
				class getKeyString(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.V2GetKeyStringResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.V2GetKeyStringResponse])
				}
				object getKeyString {
					def apply(projectsId :PlayApi, locationsId :PlayApi, keysId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getKeyString = new getKeyString(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/keys/${keysId}/keyString")).addQueryStringParameters("name" -> name.toString))
					given Conversion[getKeyString, Future[Schema.V2GetKeyStringResponse]] = (fun: getKeyString) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					/** Optional. The etag known to the client for the expected state of the key. This is to be used for optimistic concurrency. */
					def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, keysId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/keys/${keysId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.V2Key]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.V2Key])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, keysId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/keys/${keysId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.V2Key]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withV2Key(body: Schema.V2Key) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, keysId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/keys/${keysId}")).addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.V2ListKeysResponse]) {
					/** Optional. Requests a specific page of results. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Specifies the maximum number of results to be returned at a time.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Indicate that keys deleted in the past 30 days should also be returned. */
					def withShowDeleted(showDeleted: Boolean) = new list(req.addQueryStringParameters("showDeleted" -> showDeleted.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.V2ListKeysResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/keys")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.V2ListKeysResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
	object keys {
		class lookupKey(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.V2LookupKeyResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.V2LookupKeyResponse])
		}
		object lookupKey {
			def apply(keyString: String)(using auth: AuthToken, ec: ExecutionContext): lookupKey = new lookupKey(auth(ws.url(BASE_URL + s"v2/keys:lookupKey")).addQueryStringParameters("keyString" -> keyString.toString))
			given Conversion[lookupKey, Future[Schema.V2LookupKeyResponse]] = (fun: lookupKey) => fun.apply()
		}
	}
}
