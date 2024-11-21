package com.boresjo.play.api.google.adsenseplatform

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://adsenseplatform.googleapis.com/"

	object platforms {
		object accounts {
			class lookup(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.LookupAccountResponse]) {
				/** Optional. The creation_request_id provided when calling createAccount. */
				def withCreationRequestId(creationRequestId: String) = new lookup(req.addQueryStringParameters("creationRequestId" -> creationRequestId.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.LookupAccountResponse])
			}
			object lookup {
				def apply(platformsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): lookup = new lookup(auth(ws.url(BASE_URL + s"v1/platforms/${platformsId}/accounts:lookup")).addQueryStringParameters("parent" -> parent.toString))
				given Conversion[lookup, Future[Schema.LookupAccountResponse]] = (fun: lookup) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAccount(body: Schema.Account) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Account])
			}
			object create {
				def apply(platformsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/platforms/${platformsId}/accounts")).addQueryStringParameters("parent" -> parent.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Account]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Account])
			}
			object get {
				def apply(platformsId :PlayApi, accountsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/platforms/${platformsId}/accounts/${accountsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Account]] = (fun: get) => fun.apply()
			}
			class close(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCloseAccountRequest(body: Schema.CloseAccountRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.CloseAccountResponse])
			}
			object close {
				def apply(platformsId :PlayApi, accountsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): close = new close(auth(ws.url(BASE_URL + s"v1/platforms/${platformsId}/accounts/${accountsId}:close")).addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAccountsResponse]) {
				/** Optional. The maximum number of accounts to include in the response, used for paging. If unspecified, at most 10000 accounts will be returned. The maximum value is 10000; values above 10000 will be coerced to 10000.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A page token, received from a previous `ListAccounts` call. Provide this to retrieve the subsequent page. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.ListAccountsResponse])
			}
			object list {
				def apply(platformsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/platforms/${platformsId}/accounts")).addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListAccountsResponse]] = (fun: list) => fun.apply()
			}
			object sites {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSite(body: Schema.Site) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Site])
				}
				object create {
					def apply(platformsId :PlayApi, accountsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/platforms/${platformsId}/accounts/${accountsId}/sites")).addQueryStringParameters("parent" -> parent.toString))
				}
				class requestReview(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.RequestSiteReviewResponse]) {
					def apply() = req.execute("POST").map(_.json.as[Schema.RequestSiteReviewResponse])
				}
				object requestReview {
					def apply(platformsId :PlayApi, accountsId :PlayApi, sitesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): requestReview = new requestReview(auth(ws.url(BASE_URL + s"v1/platforms/${platformsId}/accounts/${accountsId}/sites/${sitesId}:requestReview")).addQueryStringParameters("name" -> name.toString))
					given Conversion[requestReview, Future[Schema.RequestSiteReviewResponse]] = (fun: requestReview) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(platformsId :PlayApi, accountsId :PlayApi, sitesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/platforms/${platformsId}/accounts/${accountsId}/sites/${sitesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Site]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.Site])
				}
				object get {
					def apply(platformsId :PlayApi, accountsId :PlayApi, sitesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/platforms/${platformsId}/accounts/${accountsId}/sites/${sitesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Site]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSitesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListSitesResponse])
				}
				object list {
					def apply(platformsId :PlayApi, accountsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/platforms/${platformsId}/accounts/${accountsId}/sites")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListSitesResponse]] = (fun: list) => fun.apply()
				}
			}
			object events {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withEvent(body: Schema.Event) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Event])
				}
				object create {
					def apply(platformsId :PlayApi, accountsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/platforms/${platformsId}/accounts/${accountsId}/events")).addQueryStringParameters("parent" -> parent.toString))
				}
			}
		}
	}
}
