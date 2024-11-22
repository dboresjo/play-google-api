package com.boresjo.play.api.google.advisorynotifications

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

	private val BASE_URL = "https://advisorynotifications.googleapis.com/"

	object projects {
		object locations {
			/** Get notification settings. */
			class getSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudAdvisorynotificationsV1Settings]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudAdvisorynotificationsV1Settings])
			}
			object getSettings {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getSettings = new getSettings(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/settings").addQueryStringParameters("name" -> name.toString))
				given Conversion[getSettings, Future[Schema.GoogleCloudAdvisorynotificationsV1Settings]] = (fun: getSettings) => fun.apply()
			}
			/** Update notification settings. */
			class updateSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudAdvisorynotificationsV1Settings(body: Schema.GoogleCloudAdvisorynotificationsV1Settings) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudAdvisorynotificationsV1Settings])
			}
			object updateSettings {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): updateSettings = new updateSettings(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/settings").addQueryStringParameters("name" -> name.toString))
			}
			object notifications {
				/** Gets a notification. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudAdvisorynotificationsV1Notification]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudAdvisorynotificationsV1Notification])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, notificationsId :PlayApi, languageCode: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/notifications/${notificationsId}").addQueryStringParameters("languageCode" -> languageCode.toString, "name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudAdvisorynotificationsV1Notification]] = (fun: get) => fun.apply()
				}
				/** Lists notifications under a given parent. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudAdvisorynotificationsV1ListNotificationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudAdvisorynotificationsV1ListNotificationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, pageToken: String, pageSize: Int, parent: String, languageCode: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/notifications").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString, "languageCode" -> languageCode.toString, "view" -> view.toString))
					given Conversion[list, Future[Schema.GoogleCloudAdvisorynotificationsV1ListNotificationsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
	object organizations {
		object locations {
			/** Update notification settings. */
			class updateSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudAdvisorynotificationsV1Settings(body: Schema.GoogleCloudAdvisorynotificationsV1Settings) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudAdvisorynotificationsV1Settings])
			}
			object updateSettings {
				def apply(organizationsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): updateSettings = new updateSettings(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/settings").addQueryStringParameters("name" -> name.toString))
			}
			/** Get notification settings. */
			class getSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudAdvisorynotificationsV1Settings]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudAdvisorynotificationsV1Settings])
			}
			object getSettings {
				def apply(organizationsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getSettings = new getSettings(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/settings").addQueryStringParameters("name" -> name.toString))
				given Conversion[getSettings, Future[Schema.GoogleCloudAdvisorynotificationsV1Settings]] = (fun: getSettings) => fun.apply()
			}
			object notifications {
				/** Gets a notification. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudAdvisorynotificationsV1Notification]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudAdvisorynotificationsV1Notification])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, notificationsId :PlayApi, languageCode: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/notifications/${notificationsId}").addQueryStringParameters("languageCode" -> languageCode.toString, "name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudAdvisorynotificationsV1Notification]] = (fun: get) => fun.apply()
				}
				/** Lists notifications under a given parent. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudAdvisorynotificationsV1ListNotificationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudAdvisorynotificationsV1ListNotificationsResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, pageSize: Int, view: String, pageToken: String, parent: String, languageCode: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/notifications").addQueryStringParameters("pageSize" -> pageSize.toString, "view" -> view.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString, "languageCode" -> languageCode.toString))
					given Conversion[list, Future[Schema.GoogleCloudAdvisorynotificationsV1ListNotificationsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
