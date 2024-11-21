package com.boresjo.play.api.google.marketingplatformadmin

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://marketingplatformadmin.googleapis.com/"

	object organizations {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Organization]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Organization])
		}
		object get {
			def apply(organizationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1alpha/organizations/${organizationsId}")).addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Organization]] = (fun: get) => fun.apply()
		}
		object analyticsAccountLinks {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAnalyticsAccountLink(body: Schema.AnalyticsAccountLink) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.AnalyticsAccountLink])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1alpha/organizations/${organizationsId}/analyticsAccountLinks")).addQueryStringParameters("parent" -> parent.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAnalyticsAccountLinksResponse]) {
				/** Optional. The maximum number of Analytics account links to return in one call. The service may return fewer than this value. If unspecified, at most 50 Analytics account links will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A page token, received from a previous ListAnalyticsAccountLinks call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListAnalyticsAccountLinks` must match the call that provided the page token. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.ListAnalyticsAccountLinksResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1alpha/organizations/${organizationsId}/analyticsAccountLinks")).addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListAnalyticsAccountLinksResponse]] = (fun: list) => fun.apply()
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(organizationsId :PlayApi, analyticsAccountLinksId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1alpha/organizations/${organizationsId}/analyticsAccountLinks/${analyticsAccountLinksId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class setPropertyServiceLevel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSetPropertyServiceLevelRequest(body: Schema.SetPropertyServiceLevelRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SetPropertyServiceLevelResponse])
			}
			object setPropertyServiceLevel {
				def apply(organizationsId :PlayApi, analyticsAccountLinksId :PlayApi, analyticsAccountLink: String)(using auth: AuthToken, ec: ExecutionContext): setPropertyServiceLevel = new setPropertyServiceLevel(auth(ws.url(BASE_URL + s"v1alpha/organizations/${organizationsId}/analyticsAccountLinks/${analyticsAccountLinksId}:setPropertyServiceLevel")).addQueryStringParameters("analyticsAccountLink" -> analyticsAccountLink.toString))
			}
		}
	}
}
