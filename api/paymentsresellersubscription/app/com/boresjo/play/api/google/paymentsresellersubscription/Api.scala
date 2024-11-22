package com.boresjo.play.api.google.paymentsresellersubscription

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://paymentsresellersubscription.googleapis.com/"

	object partners {
		object products {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudPaymentsResellerSubscriptionV1ListProductsResponse]) {
				/** Optional. Specifies the filters for the product results. The syntax is defined in https://google.aip.dev/160 with the following caveats: 1. Only the following features are supported: - Logical operator `AND` - Comparison operator `=` (no wildcards `&#42;`) - Traversal operator `.` - Has operator `:` (no wildcards `&#42;`) 2. Only the following fields are supported: - `regionCodes` - `youtubePayload.partnerEligibilityId` - `youtubePayload.postalCode` 3. Unless explicitly mentioned above, other features are not supported. Example: `regionCodes:US AND youtubePayload.postalCode=94043 AND youtubePayload.partnerEligibilityId=eligibility-id` */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Optional. The maximum number of products to return. The service may return fewer than this value. If unspecified, at most 50 products will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A page token, received from a previous `ListProducts` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListProducts` must match the call that provided the page token. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudPaymentsResellerSubscriptionV1ListProductsResponse])
			}
			object list {
				def apply(partnersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/partners/${partnersId}/products").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudPaymentsResellerSubscriptionV1ListProductsResponse]] = (fun: list) => fun.apply()
			}
		}
		object userSessions {
			class generate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudPaymentsResellerSubscriptionV1GenerateUserSessionRequest(body: Schema.GoogleCloudPaymentsResellerSubscriptionV1GenerateUserSessionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudPaymentsResellerSubscriptionV1GenerateUserSessionResponse])
			}
			object generate {
				def apply(partnersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): generate = new generate(ws.url(BASE_URL + s"v1/partners/${partnersId}/userSessions:generate").addQueryStringParameters("parent" -> parent.toString))
			}
		}
		object promotions {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudPaymentsResellerSubscriptionV1ListPromotionsResponse]) {
				/** Optional. Specifies the filters for the promotion results. The syntax is defined in https://google.aip.dev/160 with the following caveats: 1. Only the following features are supported: - Logical operator `AND` - Comparison operator `=` (no wildcards `&#42;`) - Traversal operator `.` - Has operator `:` (no wildcards `&#42;`) 2. Only the following fields are supported: - `applicableProducts` - `regionCodes` - `youtubePayload.partnerEligibilityId` - `youtubePayload.postalCode` 3. Unless explicitly mentioned above, other features are not supported. Example: `applicableProducts:partners/partner1/products/product1 AND regionCodes:US AND youtubePayload.postalCode=94043 AND youtubePayload.partnerEligibilityId=eligibility-id` */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Optional. A page token, received from a previous `ListPromotions` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListPromotions` must match the call that provided the page token. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The maximum number of promotions to return. The service may return fewer than this value. If unspecified, at most 50 products will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudPaymentsResellerSubscriptionV1ListPromotionsResponse])
			}
			object list {
				def apply(partnersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/partners/${partnersId}/promotions").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudPaymentsResellerSubscriptionV1ListPromotionsResponse]] = (fun: list) => fun.apply()
			}
			class findEligible(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudPaymentsResellerSubscriptionV1FindEligiblePromotionsRequest(body: Schema.GoogleCloudPaymentsResellerSubscriptionV1FindEligiblePromotionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudPaymentsResellerSubscriptionV1FindEligiblePromotionsResponse])
			}
			object findEligible {
				def apply(partnersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): findEligible = new findEligible(ws.url(BASE_URL + s"v1/partners/${partnersId}/promotions:findEligible").addQueryStringParameters("parent" -> parent.toString))
			}
		}
		object subscriptions {
			class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudPaymentsResellerSubscriptionV1CancelSubscriptionRequest(body: Schema.GoogleCloudPaymentsResellerSubscriptionV1CancelSubscriptionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudPaymentsResellerSubscriptionV1CancelSubscriptionResponse])
			}
			object cancel {
				def apply(partnersId :PlayApi, subscriptionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/partners/${partnersId}/subscriptions/${subscriptionsId}:cancel").addQueryStringParameters("name" -> name.toString))
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudPaymentsResellerSubscriptionV1Subscription(body: Schema.GoogleCloudPaymentsResellerSubscriptionV1Subscription) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudPaymentsResellerSubscriptionV1Subscription])
			}
			object create {
				def apply(partnersId :PlayApi, subscriptionId: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/partners/${partnersId}/subscriptions").addQueryStringParameters("subscriptionId" -> subscriptionId.toString, "parent" -> parent.toString))
			}
			class extend(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudPaymentsResellerSubscriptionV1ExtendSubscriptionRequest(body: Schema.GoogleCloudPaymentsResellerSubscriptionV1ExtendSubscriptionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudPaymentsResellerSubscriptionV1ExtendSubscriptionResponse])
			}
			object extend {
				def apply(partnersId :PlayApi, subscriptionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): extend = new extend(ws.url(BASE_URL + s"v1/partners/${partnersId}/subscriptions/${subscriptionsId}:extend").addQueryStringParameters("name" -> name.toString))
			}
			class undoCancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudPaymentsResellerSubscriptionV1UndoCancelSubscriptionRequest(body: Schema.GoogleCloudPaymentsResellerSubscriptionV1UndoCancelSubscriptionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudPaymentsResellerSubscriptionV1UndoCancelSubscriptionResponse])
			}
			object undoCancel {
				def apply(partnersId :PlayApi, subscriptionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): undoCancel = new undoCancel(ws.url(BASE_URL + s"v1/partners/${partnersId}/subscriptions/${subscriptionsId}:undoCancel").addQueryStringParameters("name" -> name.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudPaymentsResellerSubscriptionV1Subscription]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudPaymentsResellerSubscriptionV1Subscription])
			}
			object get {
				def apply(partnersId :PlayApi, subscriptionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/partners/${partnersId}/subscriptions/${subscriptionsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudPaymentsResellerSubscriptionV1Subscription]] = (fun: get) => fun.apply()
			}
			class provision(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudPaymentsResellerSubscriptionV1Subscription(body: Schema.GoogleCloudPaymentsResellerSubscriptionV1Subscription) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudPaymentsResellerSubscriptionV1Subscription])
			}
			object provision {
				def apply(partnersId :PlayApi, subscriptionId: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): provision = new provision(ws.url(BASE_URL + s"v1/partners/${partnersId}/subscriptions:provision").addQueryStringParameters("subscriptionId" -> subscriptionId.toString, "parent" -> parent.toString))
			}
			class entitle(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudPaymentsResellerSubscriptionV1EntitleSubscriptionRequest(body: Schema.GoogleCloudPaymentsResellerSubscriptionV1EntitleSubscriptionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudPaymentsResellerSubscriptionV1EntitleSubscriptionResponse])
			}
			object entitle {
				def apply(partnersId :PlayApi, subscriptionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): entitle = new entitle(ws.url(BASE_URL + s"v1/partners/${partnersId}/subscriptions/${subscriptionsId}:entitle").addQueryStringParameters("name" -> name.toString))
			}
		}
	}
}
