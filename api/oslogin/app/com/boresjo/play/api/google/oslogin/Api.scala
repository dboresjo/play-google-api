package com.boresjo.play.api.google.oslogin

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://oslogin.googleapis.com/"

	object users {
		class getLoginProfile(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LoginProfile]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.LoginProfile])
		}
		object getLoginProfile {
			def apply(usersId :PlayApi, name: String, projectId: String, systemId: String)(using auth: AuthToken, ec: ExecutionContext): getLoginProfile = new getLoginProfile(auth(ws.url(BASE_URL + s"v1/users/${usersId}/loginProfile")).addQueryStringParameters("name" -> name.toString, "projectId" -> projectId.toString, "systemId" -> systemId.toString))
			given Conversion[getLoginProfile, Future[Schema.LoginProfile]] = (fun: getLoginProfile) => fun.apply()
		}
		class importSshPublicKey(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. The regions to which to assert that the key was written. If unspecified, defaults to all regions. Regions are listed at https://cloud.google.com/about/locations#region. */
			def withRegions(regions: String) = new importSshPublicKey(req.addQueryStringParameters("regions" -> regions.toString))
			def withSshPublicKey(body: Schema.SshPublicKey) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ImportSshPublicKeyResponse])
		}
		object importSshPublicKey {
			def apply(usersId :PlayApi, parent: String, projectId: String)(using auth: AuthToken, ec: ExecutionContext): importSshPublicKey = new importSshPublicKey(auth(ws.url(BASE_URL + s"v1/users/${usersId}:importSshPublicKey")).addQueryStringParameters("parent" -> parent.toString, "projectId" -> projectId.toString))
		}
		object sshPublicKeys {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSshPublicKey(body: Schema.SshPublicKey) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SshPublicKey])
			}
			object create {
				def apply(usersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/users/${usersId}/sshPublicKeys")).addQueryStringParameters("parent" -> parent.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(usersId :PlayApi, sshPublicKeysId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/users/${usersId}/sshPublicKeys/${sshPublicKeysId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SshPublicKey]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.SshPublicKey])
			}
			object get {
				def apply(usersId :PlayApi, sshPublicKeysId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/users/${usersId}/sshPublicKeys/${sshPublicKeysId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.SshPublicKey]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSshPublicKey(body: Schema.SshPublicKey) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.SshPublicKey])
			}
			object patch {
				def apply(usersId :PlayApi, sshPublicKeysId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/users/${usersId}/sshPublicKeys/${sshPublicKeysId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
		}
		object projects {
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(usersId :PlayApi, projectsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/users/${usersId}/projects/${projectsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
		}
	}
}
