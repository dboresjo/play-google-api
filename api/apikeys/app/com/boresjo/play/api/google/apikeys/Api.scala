package com.boresjo.play.api.google.apikeys

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
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */,
		"""https://www.googleapis.com/auth/cloud-platform.read-only""" /* View your data across Google Cloud services and see the email address of your Google Account */
	)

	private val BASE_URL = "https://apikeys.googleapis.com/"

	object operations {
		/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Operation])
		}
		object get {
			def apply(operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
		}
	}
	object projects {
		object locations {
			object keys {
				/** Undeletes an API key which was deleted within 30 days. NOTE: Key is a global resource; hence the only supported value for location is `global`. */
				class undelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withV2UndeleteKeyRequest(body: Schema.V2UndeleteKeyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object undelete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, keysId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/keys/${keysId}:undelete").addQueryStringParameters("name" -> name.toString))
				}
				/** Creates a new API key. NOTE: Key is a global resource; hence the only supported value for location is `global`. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** User specified key id (optional). If specified, it will become the final component of the key resource name. The id must be unique within the project, must conform with RFC-1034, is restricted to lower-cased letters, and has a maximum length of 63 characters. In another word, the id must match the regular expression: `[a-z]([a-z0-9-]{0,61}[a-z0-9])?`. The id must NOT be a UUID-like string. */
					def withKeyId(keyId: String) = new create(req.addQueryStringParameters("keyId" -> keyId.toString))
					/** Perform the request */
					def withV2Key(body: Schema.V2Key) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/keys").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Get the key string for an API key. NOTE: Key is a global resource; hence the only supported value for location is `global`. */
				class getKeyString(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.V2GetKeyStringResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.V2GetKeyStringResponse])
				}
				object getKeyString {
					def apply(projectsId :PlayApi, locationsId :PlayApi, keysId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getKeyString = new getKeyString(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/keys/${keysId}/keyString").addQueryStringParameters("name" -> name.toString))
					given Conversion[getKeyString, Future[Schema.V2GetKeyStringResponse]] = (fun: getKeyString) => fun.apply()
				}
				/** Deletes an API key. Deleted key can be retrieved within 30 days of deletion. Afterward, key will be purged from the project. NOTE: Key is a global resource; hence the only supported value for location is `global`. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The etag known to the client for the expected state of the key. This is to be used for optimistic concurrency. */
					def withEtag(etag: String) = new delete(req.addQueryStringParameters("etag" -> etag.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, keysId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/keys/${keysId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				/** Gets the metadata for an API key. The key string of the API key isn't included in the response. NOTE: Key is a global resource; hence the only supported value for location is `global`. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.V2Key]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.V2Key])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, keysId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/keys/${keysId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.V2Key]] = (fun: get) => fun.apply()
				}
				/** Patches the modifiable fields of an API key. The key string of the API key isn't included in the response. NOTE: Key is a global resource; hence the only supported value for location is `global`. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withV2Key(body: Schema.V2Key) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, keysId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/keys/${keysId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
				}
				/** Lists the API keys owned by a project. The key string of the API key isn't included in the response. NOTE: Key is a global resource; hence the only supported value for location is `global`. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.V2ListKeysResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
					/** Optional. Requests a specific page of results. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Specifies the maximum number of results to be returned at a time.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. Indicate that keys deleted in the past 30 days should also be returned. */
					def withShowDeleted(showDeleted: Boolean) = new list(req.addQueryStringParameters("showDeleted" -> showDeleted.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.V2ListKeysResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/keys").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.V2ListKeysResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
	object keys {
		/** Find the parent project and resource name of the API key that matches the key string in the request. If the API key has been purged, resource name will not be set. The service account must have the `apikeys.keys.lookup` permission on the parent project. */
		class lookupKey(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.V2LookupKeyResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.V2LookupKeyResponse])
		}
		object lookupKey {
			def apply(keyString: String)(using signer: RequestSigner, ec: ExecutionContext): lookupKey = new lookupKey(ws.url(BASE_URL + s"v2/keys:lookupKey").addQueryStringParameters("keyString" -> keyString.toString))
			given Conversion[lookupKey, Future[Schema.V2LookupKeyResponse]] = (fun: lookupKey) => fun.apply()
		}
	}
}
