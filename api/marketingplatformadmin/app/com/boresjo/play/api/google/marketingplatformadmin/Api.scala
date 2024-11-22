package com.boresjo.play.api.google.marketingplatformadmin

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
		"""https://www.googleapis.com/auth/marketingplatformadmin.analytics.read""" /* View your Google Analytics product account data in GMP home */,
		"""https://www.googleapis.com/auth/marketingplatformadmin.analytics.update""" /* Manage your Google Analytics product account data in GMP home */
	)

	private val BASE_URL = "https://marketingplatformadmin.googleapis.com/"

	object organizations {
		/** Lookup for a single organization. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Organization]) {
			val scopes = Seq("""https://www.googleapis.com/auth/marketingplatformadmin.analytics.read""", """https://www.googleapis.com/auth/marketingplatformadmin.analytics.update""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Organization])
		}
		object get {
			def apply(organizationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1alpha/organizations/${organizationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Organization]] = (fun: get) => fun.apply()
		}
		object analyticsAccountLinks {
			/** Creates the link between the Analytics account and the Google Marketing Platform organization. User needs to be an org user, and admin on the Analytics account to create the link. If the account is already linked to an organization, user needs to unlink the account from the current organization, then try link again. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/marketingplatformadmin.analytics.update""")
				/** Perform the request */
				def withAnalyticsAccountLink(body: Schema.AnalyticsAccountLink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AnalyticsAccountLink])
			}
			object create {
				def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1alpha/organizations/${organizationsId}/analyticsAccountLinks").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Lists the Google Analytics accounts link to the specified Google Marketing Platform organization. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAnalyticsAccountLinksResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/marketingplatformadmin.analytics.read""", """https://www.googleapis.com/auth/marketingplatformadmin.analytics.update""")
				/** Optional. The maximum number of Analytics account links to return in one call. The service may return fewer than this value. If unspecified, at most 50 Analytics account links will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A page token, received from a previous ListAnalyticsAccountLinks call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListAnalyticsAccountLinks` must match the call that provided the page token. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAnalyticsAccountLinksResponse])
			}
			object list {
				def apply(organizationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1alpha/organizations/${organizationsId}/analyticsAccountLinks").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListAnalyticsAccountLinksResponse]] = (fun: list) => fun.apply()
			}
			/** Deletes the AnalyticsAccountLink, which detaches the Analytics account from the Google Marketing Platform organization. User needs to be an org user, and admin on the Analytics account in order to delete the link. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/marketingplatformadmin.analytics.update""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(organizationsId :PlayApi, analyticsAccountLinksId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1alpha/organizations/${organizationsId}/analyticsAccountLinks/${analyticsAccountLinksId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			/** Updates the service level for an Analytics property. */
			class setPropertyServiceLevel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/marketingplatformadmin.analytics.update""")
				/** Perform the request */
				def withSetPropertyServiceLevelRequest(body: Schema.SetPropertyServiceLevelRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SetPropertyServiceLevelResponse])
			}
			object setPropertyServiceLevel {
				def apply(organizationsId :PlayApi, analyticsAccountLinksId :PlayApi, analyticsAccountLink: String)(using signer: RequestSigner, ec: ExecutionContext): setPropertyServiceLevel = new setPropertyServiceLevel(ws.url(BASE_URL + s"v1alpha/organizations/${organizationsId}/analyticsAccountLinks/${analyticsAccountLinksId}:setPropertyServiceLevel").addQueryStringParameters("analyticsAccountLink" -> analyticsAccountLink.toString))
			}
		}
	}
}
