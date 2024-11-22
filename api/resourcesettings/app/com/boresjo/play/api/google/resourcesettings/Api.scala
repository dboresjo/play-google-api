package com.boresjo.play.api.google.resourcesettings

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
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */
	)

	private val BASE_URL = "https://resourcesettings.googleapis.com/"

	object folders {
		object settings {
			/** Returns a specified setting. Returns a `google.rpc.Status` with `google.rpc.Code.NOT_FOUND` if the setting does not exist. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudResourcesettingsV1Setting]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudResourcesettingsV1Setting])
			}
			object get {
				def apply(foldersId :PlayApi, settingsId :PlayApi, view: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/folders/${foldersId}/settings/${settingsId}").addQueryStringParameters("view" -> view.toString, "name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudResourcesettingsV1Setting]] = (fun: get) => fun.apply()
			}
			/** Lists all the settings that are available on the Cloud resource `parent`. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudResourcesettingsV1ListSettingsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudResourcesettingsV1ListSettingsResponse])
			}
			object list {
				def apply(foldersId :PlayApi, pageSize: Int, view: String, pageToken: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/folders/${foldersId}/settings").addQueryStringParameters("pageSize" -> pageSize.toString, "view" -> view.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudResourcesettingsV1ListSettingsResponse]] = (fun: list) => fun.apply()
			}
			/** Updates a specified setting. Returns a `google.rpc.Status` with `google.rpc.Code.NOT_FOUND` if the setting does not exist. Returns a `google.rpc.Status` with `google.rpc.Code.FAILED_PRECONDITION` if the setting is flagged as read only. Returns a `google.rpc.Status` with `google.rpc.Code.ABORTED` if the etag supplied in the request does not match the persisted etag of the setting value. On success, the response will contain only `name`, `local_value` and `etag`. The `metadata` and `effective_value` cannot be updated through this API. Note: the supplied setting will perform a full overwrite of the `local_value` field. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudResourcesettingsV1Setting(body: Schema.GoogleCloudResourcesettingsV1Setting) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudResourcesettingsV1Setting])
			}
			object patch {
				def apply(foldersId :PlayApi, settingsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/folders/${foldersId}/settings/${settingsId}").addQueryStringParameters("name" -> name.toString))
			}
		}
	}
	object projects {
		object settings {
			/** Lists all the settings that are available on the Cloud resource `parent`. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudResourcesettingsV1ListSettingsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudResourcesettingsV1ListSettingsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, pageSize: Int, parent: String, pageToken: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/settings").addQueryStringParameters("pageSize" -> pageSize.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString, "view" -> view.toString))
				given Conversion[list, Future[Schema.GoogleCloudResourcesettingsV1ListSettingsResponse]] = (fun: list) => fun.apply()
			}
			/** Returns a specified setting. Returns a `google.rpc.Status` with `google.rpc.Code.NOT_FOUND` if the setting does not exist. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudResourcesettingsV1Setting]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudResourcesettingsV1Setting])
			}
			object get {
				def apply(projectsId :PlayApi, settingsId :PlayApi, name: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/settings/${settingsId}").addQueryStringParameters("name" -> name.toString, "view" -> view.toString))
				given Conversion[get, Future[Schema.GoogleCloudResourcesettingsV1Setting]] = (fun: get) => fun.apply()
			}
			/** Updates a specified setting. Returns a `google.rpc.Status` with `google.rpc.Code.NOT_FOUND` if the setting does not exist. Returns a `google.rpc.Status` with `google.rpc.Code.FAILED_PRECONDITION` if the setting is flagged as read only. Returns a `google.rpc.Status` with `google.rpc.Code.ABORTED` if the etag supplied in the request does not match the persisted etag of the setting value. On success, the response will contain only `name`, `local_value` and `etag`. The `metadata` and `effective_value` cannot be updated through this API. Note: the supplied setting will perform a full overwrite of the `local_value` field. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudResourcesettingsV1Setting(body: Schema.GoogleCloudResourcesettingsV1Setting) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudResourcesettingsV1Setting])
			}
			object patch {
				def apply(projectsId :PlayApi, settingsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/settings/${settingsId}").addQueryStringParameters("name" -> name.toString))
			}
		}
	}
	object organizations {
		object settings {
			/** Returns a specified setting. Returns a `google.rpc.Status` with `google.rpc.Code.NOT_FOUND` if the setting does not exist. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudResourcesettingsV1Setting]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudResourcesettingsV1Setting])
			}
			object get {
				def apply(organizationsId :PlayApi, settingsId :PlayApi, view: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/settings/${settingsId}").addQueryStringParameters("view" -> view.toString, "name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudResourcesettingsV1Setting]] = (fun: get) => fun.apply()
			}
			/** Lists all the settings that are available on the Cloud resource `parent`. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudResourcesettingsV1ListSettingsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudResourcesettingsV1ListSettingsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, pageSize: Int, parent: String, pageToken: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/settings").addQueryStringParameters("pageSize" -> pageSize.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString, "view" -> view.toString))
				given Conversion[list, Future[Schema.GoogleCloudResourcesettingsV1ListSettingsResponse]] = (fun: list) => fun.apply()
			}
			/** Updates a specified setting. Returns a `google.rpc.Status` with `google.rpc.Code.NOT_FOUND` if the setting does not exist. Returns a `google.rpc.Status` with `google.rpc.Code.FAILED_PRECONDITION` if the setting is flagged as read only. Returns a `google.rpc.Status` with `google.rpc.Code.ABORTED` if the etag supplied in the request does not match the persisted etag of the setting value. On success, the response will contain only `name`, `local_value` and `etag`. The `metadata` and `effective_value` cannot be updated through this API. Note: the supplied setting will perform a full overwrite of the `local_value` field. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudResourcesettingsV1Setting(body: Schema.GoogleCloudResourcesettingsV1Setting) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudResourcesettingsV1Setting])
			}
			object patch {
				def apply(organizationsId :PlayApi, settingsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/settings/${settingsId}").addQueryStringParameters("name" -> name.toString))
			}
		}
	}
}
