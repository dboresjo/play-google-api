package com.boresjo.play.api.google.adsenseplatform

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
		"""https://www.googleapis.com/auth/adsense""" /* View and manage your AdSense data */,
		"""https://www.googleapis.com/auth/adsense.readonly""" /* View your AdSense data */
	)

	private val BASE_URL = "https://adsenseplatform.googleapis.com/"

	object platforms {
		object accounts {
			/** Looks up information about a sub-account for a specified creation_request_id. If no account exists for the given creation_request_id, returns 404. */
			class lookup(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.LookupAccountResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/adsense""", """https://www.googleapis.com/auth/adsense.readonly""")
				/** Optional. The creation_request_id provided when calling createAccount. */
				def withCreationRequestId(creationRequestId: String) = new lookup(req.addQueryStringParameters("creationRequestId" -> creationRequestId.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.LookupAccountResponse])
			}
			object lookup {
				def apply(platformsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): lookup = new lookup(ws.url(BASE_URL + s"v1/platforms/${platformsId}/accounts:lookup").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[lookup, Future[Schema.LookupAccountResponse]] = (fun: lookup) => fun.apply()
			}
			/** Creates a sub-account. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/adsense""")
				/** Perform the request */
				def withAccount(body: Schema.Account) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Account])
			}
			object create {
				def apply(platformsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/platforms/${platformsId}/accounts").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Gets information about the selected sub-account. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Account]) {
				val scopes = Seq("""https://www.googleapis.com/auth/adsense""", """https://www.googleapis.com/auth/adsense.readonly""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Account])
			}
			object get {
				def apply(platformsId :PlayApi, accountsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/platforms/${platformsId}/accounts/${accountsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Account]] = (fun: get) => fun.apply()
			}
			/** Closes a sub-account. */
			class close(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/adsense""")
				/** Perform the request */
				def withCloseAccountRequest(body: Schema.CloseAccountRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CloseAccountResponse])
			}
			object close {
				def apply(platformsId :PlayApi, accountsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): close = new close(ws.url(BASE_URL + s"v1/platforms/${platformsId}/accounts/${accountsId}:close").addQueryStringParameters("name" -> name.toString))
			}
			/** Lists a partial view of sub-accounts for a specific parent account. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAccountsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/adsense""", """https://www.googleapis.com/auth/adsense.readonly""")
				/** Optional. The maximum number of accounts to include in the response, used for paging. If unspecified, at most 10000 accounts will be returned. The maximum value is 10000; values above 10000 will be coerced to 10000.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A page token, received from a previous `ListAccounts` call. Provide this to retrieve the subsequent page. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAccountsResponse])
			}
			object list {
				def apply(platformsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/platforms/${platformsId}/accounts").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListAccountsResponse]] = (fun: list) => fun.apply()
			}
			object sites {
				/** Creates a site for a specified account. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/adsense""")
					/** Perform the request */
					def withSite(body: Schema.Site) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Site])
				}
				object create {
					def apply(platformsId :PlayApi, accountsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/platforms/${platformsId}/accounts/${accountsId}/sites").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Requests the review of a site. The site should be in REQUIRES_REVIEW or NEEDS_ATTENTION state. Note: Make sure you place an [ad tag](https://developers.google.com/adsense/platforms/direct/ad-tags) on your site before requesting a review. */
				class requestReview(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.RequestSiteReviewResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adsense""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.RequestSiteReviewResponse])
				}
				object requestReview {
					def apply(platformsId :PlayApi, accountsId :PlayApi, sitesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): requestReview = new requestReview(ws.url(BASE_URL + s"v1/platforms/${platformsId}/accounts/${accountsId}/sites/${sitesId}:requestReview").addQueryStringParameters("name" -> name.toString))
					given Conversion[requestReview, Future[Schema.RequestSiteReviewResponse]] = (fun: requestReview) => fun.apply()
				}
				/** Deletes a site from a specified account. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adsense""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(platformsId :PlayApi, accountsId :PlayApi, sitesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/platforms/${platformsId}/accounts/${accountsId}/sites/${sitesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets a site from a specified sub-account. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Site]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adsense""", """https://www.googleapis.com/auth/adsense.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Site])
				}
				object get {
					def apply(platformsId :PlayApi, accountsId :PlayApi, sitesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/platforms/${platformsId}/accounts/${accountsId}/sites/${sitesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Site]] = (fun: get) => fun.apply()
				}
				/** Lists sites for a specific account. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSitesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adsense""", """https://www.googleapis.com/auth/adsense.readonly""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSitesResponse])
				}
				object list {
					def apply(platformsId :PlayApi, accountsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/platforms/${platformsId}/accounts/${accountsId}/sites").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListSitesResponse]] = (fun: list) => fun.apply()
				}
			}
			object events {
				/** Creates an account event. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/adsense""")
					/** Perform the request */
					def withEvent(body: Schema.Event) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Event])
				}
				object create {
					def apply(platformsId :PlayApi, accountsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/platforms/${platformsId}/accounts/${accountsId}/events").addQueryStringParameters("parent" -> parent.toString))
				}
			}
		}
	}
}
