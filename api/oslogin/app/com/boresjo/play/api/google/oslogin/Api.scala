package com.boresjo.play.api.google.oslogin

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
		"""https://www.googleapis.com/auth/cloud-platform.read-only""" /* View your data across Google Cloud services and see the email address of your Google Account */,
		"""https://www.googleapis.com/auth/compute""" /* View and manage your Google Compute Engine resources */,
		"""https://www.googleapis.com/auth/compute.readonly""" /* View your Google Compute Engine resources */
	)

	private val BASE_URL = "https://oslogin.googleapis.com/"

	object users {
		/** Retrieves the profile information used for logging in to a virtual machine on Google Compute Engine. */
		class getLoginProfile(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LoginProfile]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""", """https://www.googleapis.com/auth/compute""", """https://www.googleapis.com/auth/compute.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LoginProfile])
		}
		object getLoginProfile {
			def apply(usersId :PlayApi, name: String, projectId: String, systemId: String)(using signer: RequestSigner, ec: ExecutionContext): getLoginProfile = new getLoginProfile(ws.url(BASE_URL + s"v1/users/${usersId}/loginProfile").addQueryStringParameters("name" -> name.toString, "projectId" -> projectId.toString, "systemId" -> systemId.toString))
			given Conversion[getLoginProfile, Future[Schema.LoginProfile]] = (fun: getLoginProfile) => fun.apply()
		}
		/** Adds an SSH public key and returns the profile information. Default POSIX account information is set when no username and UID exist as part of the login profile. */
		class importSshPublicKey(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
			/** Optional. The regions to which to assert that the key was written. If unspecified, defaults to all regions. Regions are listed at https://cloud.google.com/about/locations#region. */
			def withRegions(regions: String) = new importSshPublicKey(req.addQueryStringParameters("regions" -> regions.toString))
			/** Perform the request */
			def withSshPublicKey(body: Schema.SshPublicKey) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ImportSshPublicKeyResponse])
		}
		object importSshPublicKey {
			def apply(usersId :PlayApi, parent: String, projectId: String)(using signer: RequestSigner, ec: ExecutionContext): importSshPublicKey = new importSshPublicKey(ws.url(BASE_URL + s"v1/users/${usersId}:importSshPublicKey").addQueryStringParameters("parent" -> parent.toString, "projectId" -> projectId.toString))
		}
		object sshPublicKeys {
			/** Create an SSH public key */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
				/** Perform the request */
				def withSshPublicKey(body: Schema.SshPublicKey) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SshPublicKey])
			}
			object create {
				def apply(usersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/users/${usersId}/sshPublicKeys").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Deletes an SSH public key. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(usersId :PlayApi, sshPublicKeysId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/users/${usersId}/sshPublicKeys/${sshPublicKeysId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Retrieves an SSH public key. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.SshPublicKey]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.SshPublicKey])
			}
			object get {
				def apply(usersId :PlayApi, sshPublicKeysId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/users/${usersId}/sshPublicKeys/${sshPublicKeysId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.SshPublicKey]] = (fun: get) => fun.apply()
			}
			/** Updates an SSH public key and returns the profile information. This method supports patch semantics. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
				/** Perform the request */
				def withSshPublicKey(body: Schema.SshPublicKey) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.SshPublicKey])
			}
			object patch {
				def apply(usersId :PlayApi, sshPublicKeysId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/users/${usersId}/sshPublicKeys/${sshPublicKeysId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
		}
		object projects {
			/** Deletes a POSIX account. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/compute""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(usersId :PlayApi, projectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/users/${usersId}/projects/${projectsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
		}
	}
}
