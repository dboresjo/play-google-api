package com.boresjo.play.api.google.resourcesettings

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://resourcesettings.googleapis.com/"

	object folders {
		object settings {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudResourcesettingsV1Setting]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudResourcesettingsV1Setting])
			}
			object get {
				def apply(foldersId :PlayApi, settingsId :PlayApi, view: String, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/settings/${settingsId}")).addQueryStringParameters("view" -> view.toString, "name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudResourcesettingsV1Setting]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudResourcesettingsV1ListSettingsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudResourcesettingsV1ListSettingsResponse])
			}
			object list {
				def apply(foldersId :PlayApi, pageSize: Int, view: String, pageToken: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/settings")).addQueryStringParameters("pageSize" -> pageSize.toString, "view" -> view.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudResourcesettingsV1ListSettingsResponse]] = (fun: list) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudResourcesettingsV1Setting(body: Schema.GoogleCloudResourcesettingsV1Setting) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudResourcesettingsV1Setting])
			}
			object patch {
				def apply(foldersId :PlayApi, settingsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/settings/${settingsId}")).addQueryStringParameters("name" -> name.toString))
			}
		}
	}
	object projects {
		object settings {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudResourcesettingsV1ListSettingsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudResourcesettingsV1ListSettingsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, pageSize: Int, parent: String, pageToken: String, view: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/settings")).addQueryStringParameters("pageSize" -> pageSize.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString, "view" -> view.toString))
				given Conversion[list, Future[Schema.GoogleCloudResourcesettingsV1ListSettingsResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudResourcesettingsV1Setting]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudResourcesettingsV1Setting])
			}
			object get {
				def apply(projectsId :PlayApi, settingsId :PlayApi, name: String, view: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/settings/${settingsId}")).addQueryStringParameters("name" -> name.toString, "view" -> view.toString))
				given Conversion[get, Future[Schema.GoogleCloudResourcesettingsV1Setting]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudResourcesettingsV1Setting(body: Schema.GoogleCloudResourcesettingsV1Setting) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudResourcesettingsV1Setting])
			}
			object patch {
				def apply(projectsId :PlayApi, settingsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/settings/${settingsId}")).addQueryStringParameters("name" -> name.toString))
			}
		}
	}
	object organizations {
		object settings {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudResourcesettingsV1Setting]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudResourcesettingsV1Setting])
			}
			object get {
				def apply(organizationsId :PlayApi, settingsId :PlayApi, view: String, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/settings/${settingsId}")).addQueryStringParameters("view" -> view.toString, "name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudResourcesettingsV1Setting]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudResourcesettingsV1ListSettingsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudResourcesettingsV1ListSettingsResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, pageSize: Int, parent: String, pageToken: String, view: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/settings")).addQueryStringParameters("pageSize" -> pageSize.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString, "view" -> view.toString))
				given Conversion[list, Future[Schema.GoogleCloudResourcesettingsV1ListSettingsResponse]] = (fun: list) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudResourcesettingsV1Setting(body: Schema.GoogleCloudResourcesettingsV1Setting) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudResourcesettingsV1Setting])
			}
			object patch {
				def apply(organizationsId :PlayApi, settingsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/settings/${settingsId}")).addQueryStringParameters("name" -> name.toString))
			}
		}
	}
}
