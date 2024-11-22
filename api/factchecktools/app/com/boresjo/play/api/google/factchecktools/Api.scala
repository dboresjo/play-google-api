package com.boresjo.play.api.google.factchecktools

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
		"""https://www.googleapis.com/auth/factchecktools""" /* Read, create, update, and delete your ClaimReview data. */
	)

	private val BASE_URL = "https://factchecktools.googleapis.com/"

	object pages {
		/** Create `ClaimReview` markup on a page. */
		class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/factchecktools""")
			/** Perform the request */
			def withGoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkupPage(body: Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkupPage) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkupPage])
		}
		object create {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1alpha1/pages").addQueryStringParameters())
		}
		/** Delete all `ClaimReview` markup on a page. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/factchecktools""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
		}
		object delete {
			def apply(pagesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1alpha1/pages/${pagesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
		}
		/** Get all `ClaimReview` markup on a page. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkupPage]) {
			val scopes = Seq("""https://www.googleapis.com/auth/factchecktools""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkupPage])
		}
		object get {
			def apply(pagesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1alpha1/pages/${pagesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkupPage]] = (fun: get) => fun.apply()
		}
		/** Update for all `ClaimReview` markup on a page Note that this is a full update. To retain the existing `ClaimReview` markup on a page, first perform a Get operation, then modify the returned markup, and finally call Update with the entire `ClaimReview` markup as the body. */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/factchecktools""")
			/** Perform the request */
			def withGoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkupPage(body: Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkupPage) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkupPage])
		}
		object update {
			def apply(pagesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1alpha1/pages/${pagesId}").addQueryStringParameters("name" -> name.toString))
		}
		/** List the `ClaimReview` markup pages for a specific URL or for an organization. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ListClaimReviewMarkupPagesResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/factchecktools""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ListClaimReviewMarkupPagesResponse])
		}
		object list {
			def apply(url: String, offset: Int, pageSize: Int, pageToken: String, organization: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1alpha1/pages").addQueryStringParameters("url" -> url.toString, "offset" -> offset.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "organization" -> organization.toString))
			given Conversion[list, Future[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ListClaimReviewMarkupPagesResponse]] = (fun: list) => fun.apply()
		}
	}
	object claims {
		/** Search through fact-checked claims using an image as the query. */
		class imageSearch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFactcheckingFactchecktoolsV1alpha1FactCheckedClaimImageSearchResponse]) {
			val scopes = Seq()
			/** Optional. The BCP-47 language code, such as "en-US" or "sr-Latn". Can be used to restrict results by language, though we do not currently consider the region. */
			def withLanguageCode(languageCode: String) = new imageSearch(req.addQueryStringParameters("languageCode" -> languageCode.toString))
			/** Optional. The pagination token. You may provide the `next_page_token` returned from a previous List request, if any, in order to get the next page. All other fields must have the same values as in the previous request. */
			def withPageToken(pageToken: String) = new imageSearch(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Optional. An integer that specifies the current offset (that is, starting result location) in search results. This field is only considered if `page_token` is unset. For example, 0 means to return results starting from the first matching result, and 10 means to return from the 11th result.<br>Format: int32 */
			def withOffset(offset: Int) = new imageSearch(req.addQueryStringParameters("offset" -> offset.toString))
			/** Optional. The pagination size. We will return up to that many results. Defaults to 10 if not set.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new imageSearch(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFactcheckingFactchecktoolsV1alpha1FactCheckedClaimImageSearchResponse])
		}
		object imageSearch {
			def apply(imageUri: String)(using signer: RequestSigner, ec: ExecutionContext): imageSearch = new imageSearch(ws.url(BASE_URL + s"v1alpha1/claims:imageSearch").addQueryStringParameters("imageUri" -> imageUri.toString))
			given Conversion[imageSearch, Future[Schema.GoogleFactcheckingFactchecktoolsV1alpha1FactCheckedClaimImageSearchResponse]] = (fun: imageSearch) => fun.apply()
		}
		/** Search through fact-checked claims. */
		class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleFactcheckingFactchecktoolsV1alpha1FactCheckedClaimSearchResponse]) {
			val scopes = Seq()
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleFactcheckingFactchecktoolsV1alpha1FactCheckedClaimSearchResponse])
		}
		object search {
			def apply(query: String, pageToken: String, maxAgeDays: Int, offset: Int, pageSize: Int, languageCode: String, reviewPublisherSiteFilter: String)(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1alpha1/claims:search").addQueryStringParameters("query" -> query.toString, "pageToken" -> pageToken.toString, "maxAgeDays" -> maxAgeDays.toString, "offset" -> offset.toString, "pageSize" -> pageSize.toString, "languageCode" -> languageCode.toString, "reviewPublisherSiteFilter" -> reviewPublisherSiteFilter.toString))
			given Conversion[search, Future[Schema.GoogleFactcheckingFactchecktoolsV1alpha1FactCheckedClaimSearchResponse]] = (fun: search) => fun.apply()
		}
	}
}
