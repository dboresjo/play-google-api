package com.boresjo.play.api.google.factchecktools

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://factchecktools.googleapis.com/"

	object pages {
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkupPage(body: Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkupPage) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkupPage])
		}
		object create {
			def apply()(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1alpha1/pages").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
		}
		object delete {
			def apply(pagesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1alpha1/pages/${pagesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkupPage]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkupPage])
		}
		object get {
			def apply(pagesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1alpha1/pages/${pagesId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkupPage]] = (fun: get) => fun.apply()
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkupPage(body: Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkupPage) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ClaimReviewMarkupPage])
		}
		object update {
			def apply(pagesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1alpha1/pages/${pagesId}").addQueryStringParameters("name" -> name.toString))
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ListClaimReviewMarkupPagesResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ListClaimReviewMarkupPagesResponse])
		}
		object list {
			def apply(url: String, offset: Int, pageSize: Int, pageToken: String, organization: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1alpha1/pages").addQueryStringParameters("url" -> url.toString, "offset" -> offset.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "organization" -> organization.toString))
			given Conversion[list, Future[Schema.GoogleFactcheckingFactchecktoolsV1alpha1ListClaimReviewMarkupPagesResponse]] = (fun: list) => fun.apply()
		}
	}
	object claims {
		class imageSearch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFactcheckingFactchecktoolsV1alpha1FactCheckedClaimImageSearchResponse]) {
			/** Optional. The BCP-47 language code, such as "en-US" or "sr-Latn". Can be used to restrict results by language, though we do not currently consider the region. */
			def withLanguageCode(languageCode: String) = new imageSearch(req.addQueryStringParameters("languageCode" -> languageCode.toString))
			/** Optional. The pagination token. You may provide the `next_page_token` returned from a previous List request, if any, in order to get the next page. All other fields must have the same values as in the previous request. */
			def withPageToken(pageToken: String) = new imageSearch(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Optional. An integer that specifies the current offset (that is, starting result location) in search results. This field is only considered if `page_token` is unset. For example, 0 means to return results starting from the first matching result, and 10 means to return from the 11th result.<br>Format: int32 */
			def withOffset(offset: Int) = new imageSearch(req.addQueryStringParameters("offset" -> offset.toString))
			/** Optional. The pagination size. We will return up to that many results. Defaults to 10 if not set.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new imageSearch(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleFactcheckingFactchecktoolsV1alpha1FactCheckedClaimImageSearchResponse])
		}
		object imageSearch {
			def apply(imageUri: String)(using auth: AuthToken, ec: ExecutionContext): imageSearch = new imageSearch(ws.url(BASE_URL + s"v1alpha1/claims:imageSearch").addQueryStringParameters("imageUri" -> imageUri.toString))
			given Conversion[imageSearch, Future[Schema.GoogleFactcheckingFactchecktoolsV1alpha1FactCheckedClaimImageSearchResponse]] = (fun: imageSearch) => fun.apply()
		}
		class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleFactcheckingFactchecktoolsV1alpha1FactCheckedClaimSearchResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleFactcheckingFactchecktoolsV1alpha1FactCheckedClaimSearchResponse])
		}
		object search {
			def apply(query: String, pageToken: String, maxAgeDays: Int, offset: Int, pageSize: Int, languageCode: String, reviewPublisherSiteFilter: String)(using auth: AuthToken, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1alpha1/claims:search").addQueryStringParameters("query" -> query.toString, "pageToken" -> pageToken.toString, "maxAgeDays" -> maxAgeDays.toString, "offset" -> offset.toString, "pageSize" -> pageSize.toString, "languageCode" -> languageCode.toString, "reviewPublisherSiteFilter" -> reviewPublisherSiteFilter.toString))
			given Conversion[search, Future[Schema.GoogleFactcheckingFactchecktoolsV1alpha1FactCheckedClaimSearchResponse]] = (fun: search) => fun.apply()
		}
	}
}
