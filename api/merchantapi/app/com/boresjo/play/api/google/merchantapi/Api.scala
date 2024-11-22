package com.boresjo.play.api.google.merchantapi

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://merchantapi.googleapis.com/"

	object accounts {
		object merchantReviews {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.MerchantReview]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.MerchantReview])
			}
			object get {
				def apply(accountsId :PlayApi, merchantReviewsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"reviews/v1beta/accounts/${accountsId}/merchantReviews/${merchantReviewsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.MerchantReview]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListMerchantReviewsResponse]) {
				/** Optional. The maximum number of merchant reviews to return. The service can return fewer than this value. The maximum value is 1000; values above 1000 are coerced to 1000. If unspecified, the maximum number of reviews is returned.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A page token, received from a previous `ListMerchantReviews` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListMerchantReviews` must match the call that provided the page token. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListMerchantReviewsResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"reviews/v1beta/accounts/${accountsId}/merchantReviews").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListMerchantReviewsResponse]] = (fun: list) => fun.apply()
			}
			class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withMerchantReview(body: Schema.MerchantReview) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.MerchantReview])
			}
			object insert {
				def apply(accountsId :PlayApi, parent: String, dataSource: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"reviews/v1beta/accounts/${accountsId}/merchantReviews:insert").addQueryStringParameters("parent" -> parent.toString, "dataSource" -> dataSource.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(accountsId :PlayApi, merchantReviewsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"reviews/v1beta/accounts/${accountsId}/merchantReviews/${merchantReviewsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
		}
		object productReviews {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ProductReview]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ProductReview])
			}
			object get {
				def apply(accountsId :PlayApi, productReviewsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"reviews/v1beta/accounts/${accountsId}/productReviews/${productReviewsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.ProductReview]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListProductReviewsResponse]) {
				/** Optional. The maximum number of products to return. The service may return fewer than this value.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A page token, received from a previous `ListProductReviews` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListProductReviews` must match the call that provided the page token. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListProductReviewsResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"reviews/v1beta/accounts/${accountsId}/productReviews").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.ListProductReviewsResponse]] = (fun: list) => fun.apply()
			}
			class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withProductReview(body: Schema.ProductReview) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ProductReview])
			}
			object insert {
				def apply(accountsId :PlayApi, parent: String, dataSource: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"reviews/v1beta/accounts/${accountsId}/productReviews:insert").addQueryStringParameters("parent" -> parent.toString, "dataSource" -> dataSource.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(accountsId :PlayApi, productReviewsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"reviews/v1beta/accounts/${accountsId}/productReviews/${productReviewsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
		}
	}
}
