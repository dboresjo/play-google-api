package com.boresjo.play.api.google.firebase

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://firebase.googleapis.com/"

	object operations {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Operation])
		}
		object get {
			def apply(operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Operation]] = (fun: get) => fun.apply()
		}
	}
	object projects {
		class searchApps(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.SearchFirebaseAppsResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.SearchFirebaseAppsResponse])
		}
		object searchApps {
			def apply(projectsId :PlayApi, parent: String, pageToken: String, pageSize: Int, filter: String, showDeleted: Boolean)(using auth: AuthToken, ec: ExecutionContext): searchApps = new searchApps(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}:searchApps").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "filter" -> filter.toString, "showDeleted" -> showDeleted.toString))
			given Conversion[searchApps, Future[Schema.SearchFirebaseAppsResponse]] = (fun: searchApps) => fun.apply()
		}
		class addFirebase(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAddFirebaseRequest(body: Schema.AddFirebaseRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object addFirebase {
			def apply(projectsId :PlayApi, project: String)(using auth: AuthToken, ec: ExecutionContext): addFirebase = new addFirebase(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}:addFirebase").addQueryStringParameters("project" -> project.toString))
		}
		class removeAnalytics(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withRemoveAnalyticsRequest(body: Schema.RemoveAnalyticsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
		}
		object removeAnalytics {
			def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): removeAnalytics = new removeAnalytics(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}:removeAnalytics").addQueryStringParameters("parent" -> parent.toString))
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withFirebaseProject(body: Schema.FirebaseProject) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.FirebaseProject])
		}
		object patch {
			def apply(projectsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
		class getAdminSdkConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AdminSdkConfig]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AdminSdkConfig])
		}
		object getAdminSdkConfig {
			def apply(projectsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getAdminSdkConfig = new getAdminSdkConfig(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/adminSdkConfig").addQueryStringParameters("name" -> name.toString))
			given Conversion[getAdminSdkConfig, Future[Schema.AdminSdkConfig]] = (fun: getAdminSdkConfig) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListFirebaseProjectsResponse]) {
			/** Optional. Controls whether Projects in the DELETED state should be returned in the response. If not specified, only `ACTIVE` Projects will be returned. */
			def withShowDeleted(showDeleted: Boolean) = new list(req.addQueryStringParameters("showDeleted" -> showDeleted.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListFirebaseProjectsResponse])
		}
		object list {
			def apply(pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
			given Conversion[list, Future[Schema.ListFirebaseProjectsResponse]] = (fun: list) => fun.apply()
		}
		class addGoogleAnalytics(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAddGoogleAnalyticsRequest(body: Schema.AddGoogleAnalyticsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
		}
		object addGoogleAnalytics {
			def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): addGoogleAnalytics = new addGoogleAnalytics(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}:addGoogleAnalytics").addQueryStringParameters("parent" -> parent.toString))
		}
		class getAnalyticsDetails(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AnalyticsDetails]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AnalyticsDetails])
		}
		object getAnalyticsDetails {
			def apply(projectsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getAnalyticsDetails = new getAnalyticsDetails(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/analyticsDetails").addQueryStringParameters("name" -> name.toString))
			given Conversion[getAnalyticsDetails, Future[Schema.AnalyticsDetails]] = (fun: getAnalyticsDetails) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.FirebaseProject]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.FirebaseProject])
		}
		object get {
			def apply(projectsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.FirebaseProject]] = (fun: get) => fun.apply()
		}
		object webApps {
			class undelete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withUndeleteWebAppRequest(body: Schema.UndeleteWebAppRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object undelete {
				def apply(projectsId :PlayApi, webAppsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/webApps/${webAppsId}:undelete").addQueryStringParameters("name" -> name.toString))
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withWebApp(body: Schema.WebApp) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/webApps").addQueryStringParameters("parent" -> parent.toString))
			}
			class remove(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withRemoveWebAppRequest(body: Schema.RemoveWebAppRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object remove {
				def apply(projectsId :PlayApi, webAppsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): remove = new remove(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/webApps/${webAppsId}:remove").addQueryStringParameters("name" -> name.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.WebApp]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.WebApp])
			}
			object get {
				def apply(projectsId :PlayApi, webAppsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/webApps/${webAppsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.WebApp]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withWebApp(body: Schema.WebApp) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.WebApp])
			}
			object patch {
				def apply(projectsId :PlayApi, webAppsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/webApps/${webAppsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class getConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.WebAppConfig]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.WebAppConfig])
			}
			object getConfig {
				def apply(projectsId :PlayApi, webAppsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getConfig = new getConfig(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/webApps/${webAppsId}/config").addQueryStringParameters("name" -> name.toString))
				given Conversion[getConfig, Future[Schema.WebAppConfig]] = (fun: getConfig) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListWebAppsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListWebAppsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, pageToken: String, pageSize: Int, showDeleted: Boolean)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/webApps").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "showDeleted" -> showDeleted.toString))
				given Conversion[list, Future[Schema.ListWebAppsResponse]] = (fun: list) => fun.apply()
			}
		}
		object androidApps {
			class undelete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withUndeleteAndroidAppRequest(body: Schema.UndeleteAndroidAppRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object undelete {
				def apply(projectsId :PlayApi, androidAppsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/androidApps/${androidAppsId}:undelete").addQueryStringParameters("name" -> name.toString))
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAndroidApp(body: Schema.AndroidApp) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/androidApps").addQueryStringParameters("parent" -> parent.toString))
			}
			class remove(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withRemoveAndroidAppRequest(body: Schema.RemoveAndroidAppRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object remove {
				def apply(projectsId :PlayApi, androidAppsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): remove = new remove(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/androidApps/${androidAppsId}:remove").addQueryStringParameters("name" -> name.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AndroidApp]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AndroidApp])
			}
			object get {
				def apply(projectsId :PlayApi, androidAppsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/androidApps/${androidAppsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.AndroidApp]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAndroidApp(body: Schema.AndroidApp) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.AndroidApp])
			}
			object patch {
				def apply(projectsId :PlayApi, androidAppsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/androidApps/${androidAppsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class getConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AndroidAppConfig]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AndroidAppConfig])
			}
			object getConfig {
				def apply(projectsId :PlayApi, androidAppsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getConfig = new getConfig(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/androidApps/${androidAppsId}/config").addQueryStringParameters("name" -> name.toString))
				given Conversion[getConfig, Future[Schema.AndroidAppConfig]] = (fun: getConfig) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAndroidAppsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAndroidAppsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, pageToken: String, pageSize: Int, showDeleted: Boolean)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/androidApps").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "showDeleted" -> showDeleted.toString))
				given Conversion[list, Future[Schema.ListAndroidAppsResponse]] = (fun: list) => fun.apply()
			}
			object sha {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListShaCertificatesResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListShaCertificatesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, androidAppsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/androidApps/${androidAppsId}/sha").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.ListShaCertificatesResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withShaCertificate(body: Schema.ShaCertificate) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ShaCertificate])
				}
				object create {
					def apply(projectsId :PlayApi, androidAppsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/androidApps/${androidAppsId}/sha").addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, androidAppsId :PlayApi, shaId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/androidApps/${androidAppsId}/sha/${shaId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
			}
		}
		object availableLocations {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAvailableLocationsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAvailableLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/availableLocations").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.ListAvailableLocationsResponse]] = (fun: list) => fun.apply()
			}
		}
		object defaultLocation {
			class _finalize(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withFinalizeDefaultLocationRequest(body: Schema.FinalizeDefaultLocationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object _finalize {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): _finalize = new _finalize(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/defaultLocation:finalize").addQueryStringParameters("parent" -> parent.toString))
			}
		}
		object iosApps {
			class undelete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withUndeleteIosAppRequest(body: Schema.UndeleteIosAppRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object undelete {
				def apply(projectsId :PlayApi, iosAppsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): undelete = new undelete(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/iosApps/${iosAppsId}:undelete").addQueryStringParameters("name" -> name.toString))
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withIosApp(body: Schema.IosApp) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/iosApps").addQueryStringParameters("parent" -> parent.toString))
			}
			class remove(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withRemoveIosAppRequest(body: Schema.RemoveIosAppRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
			}
			object remove {
				def apply(projectsId :PlayApi, iosAppsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): remove = new remove(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/iosApps/${iosAppsId}:remove").addQueryStringParameters("name" -> name.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.IosApp]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.IosApp])
			}
			object get {
				def apply(projectsId :PlayApi, iosAppsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/iosApps/${iosAppsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.IosApp]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withIosApp(body: Schema.IosApp) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.IosApp])
			}
			object patch {
				def apply(projectsId :PlayApi, iosAppsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/iosApps/${iosAppsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class getConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.IosAppConfig]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.IosAppConfig])
			}
			object getConfig {
				def apply(projectsId :PlayApi, iosAppsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getConfig = new getConfig(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/iosApps/${iosAppsId}/config").addQueryStringParameters("name" -> name.toString))
				given Conversion[getConfig, Future[Schema.IosAppConfig]] = (fun: getConfig) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListIosAppsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListIosAppsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String, pageToken: String, pageSize: Int, showDeleted: Boolean)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/iosApps").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "showDeleted" -> showDeleted.toString))
				given Conversion[list, Future[Schema.ListIosAppsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object availableProjects {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAvailableProjectsResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAvailableProjectsResponse])
		}
		object list {
			def apply(pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/availableProjects").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
			given Conversion[list, Future[Schema.ListAvailableProjectsResponse]] = (fun: list) => fun.apply()
		}
	}
}
