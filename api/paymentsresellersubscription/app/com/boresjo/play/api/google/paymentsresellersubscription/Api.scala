package com.boresjo.play.api.google.paymentsresellersubscription

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
		"""openid""" /* Associate you with your personal info on Google */
	)

	private val BASE_URL = "https://paymentsresellersubscription.googleapis.com/"

	object partners {
		object products {
			/** To retrieve the products that can be resold by the partner. It should be autenticated with a service account. - This API doesn't apply to YouTube products currently. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudPaymentsResellerSubscriptionV1ListProductsResponse]) {
				val scopes = Seq("""openid""")
				/** Optional. Specifies the filters for the product results. The syntax is defined in https://google.aip.dev/160 with the following caveats: 1. Only the following features are supported: - Logical operator `AND` - Comparison operator `=` (no wildcards `&#42;`) - Traversal operator `.` - Has operator `:` (no wildcards `&#42;`) 2. Only the following fields are supported: - `regionCodes` - `youtubePayload.partnerEligibilityId` - `youtubePayload.postalCode` 3. Unless explicitly mentioned above, other features are not supported. Example: `regionCodes:US AND youtubePayload.postalCode=94043 AND youtubePayload.partnerEligibilityId=eligibility-id` */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Optional. The maximum number of products to return. The service may return fewer than this value. If unspecified, at most 50 products will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A page token, received from a previous `ListProducts` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListProducts` must match the call that provided the page token. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudPaymentsResellerSubscriptionV1ListProductsResponse])
			}
			object list {
				def apply(partnersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/partners/${partnersId}/products").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudPaymentsResellerSubscriptionV1ListProductsResponse]] = (fun: list) => fun.apply()
			}
		}
		object userSessions {
			/** This API replaces user authorized OAuth consent based APIs (Create, Entitle). Generates a short-lived token for a user session based on the user intent. You can use the session token to redirect the user to Google to finish the signup flow. You can re-generate new session token repeatedly for the same request if necessary, regardless of the previous tokens being expired or not. */
			class generate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""openid""")
				/** Perform the request */
				def withGoogleCloudPaymentsResellerSubscriptionV1GenerateUserSessionRequest(body: Schema.GoogleCloudPaymentsResellerSubscriptionV1GenerateUserSessionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudPaymentsResellerSubscriptionV1GenerateUserSessionResponse])
			}
			object generate {
				def apply(partnersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): generate = new generate(ws.url(BASE_URL + s"v1/partners/${partnersId}/userSessions:generate").addQueryStringParameters("parent" -> parent.toString))
			}
		}
		object promotions {
			/** Retrieves the promotions, such as free trial, that can be used by the partner. - This API doesn't apply to YouTube promotions currently. It should be autenticated with a service account. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudPaymentsResellerSubscriptionV1ListPromotionsResponse]) {
				val scopes = Seq("""openid""")
				/** Optional. Specifies the filters for the promotion results. The syntax is defined in https://google.aip.dev/160 with the following caveats: 1. Only the following features are supported: - Logical operator `AND` - Comparison operator `=` (no wildcards `&#42;`) - Traversal operator `.` - Has operator `:` (no wildcards `&#42;`) 2. Only the following fields are supported: - `applicableProducts` - `regionCodes` - `youtubePayload.partnerEligibilityId` - `youtubePayload.postalCode` 3. Unless explicitly mentioned above, other features are not supported. Example: `applicableProducts:partners/partner1/products/product1 AND regionCodes:US AND youtubePayload.postalCode=94043 AND youtubePayload.partnerEligibilityId=eligibility-id` */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Optional. A page token, received from a previous `ListPromotions` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListPromotions` must match the call that provided the page token. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The maximum number of promotions to return. The service may return fewer than this value. If unspecified, at most 50 products will be returned. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudPaymentsResellerSubscriptionV1ListPromotionsResponse])
			}
			object list {
				def apply(partnersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/partners/${partnersId}/promotions").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudPaymentsResellerSubscriptionV1ListPromotionsResponse]] = (fun: list) => fun.apply()
			}
			/** To find eligible promotions for the current user. The API requires user authorization via OAuth. The bare minimum oauth scope `openid` is sufficient, which will skip the consent screen. */
			class findEligible(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""openid""")
				/** Perform the request */
				def withGoogleCloudPaymentsResellerSubscriptionV1FindEligiblePromotionsRequest(body: Schema.GoogleCloudPaymentsResellerSubscriptionV1FindEligiblePromotionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudPaymentsResellerSubscriptionV1FindEligiblePromotionsResponse])
			}
			object findEligible {
				def apply(partnersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): findEligible = new findEligible(ws.url(BASE_URL + s"v1/partners/${partnersId}/promotions:findEligible").addQueryStringParameters("parent" -> parent.toString))
			}
		}
		object subscriptions {
			/** Used by partners to cancel a subscription service either immediately or by the end of the current billing cycle for their customers. It should be called directly by the partner using service accounts. */
			class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""openid""")
				/** Perform the request */
				def withGoogleCloudPaymentsResellerSubscriptionV1CancelSubscriptionRequest(body: Schema.GoogleCloudPaymentsResellerSubscriptionV1CancelSubscriptionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudPaymentsResellerSubscriptionV1CancelSubscriptionResponse])
			}
			object cancel {
				def apply(partnersId :PlayApi, subscriptionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/partners/${partnersId}/subscriptions/${subscriptionsId}:cancel").addQueryStringParameters("name" -> name.toString))
			}
			/** Used by partners to create a subscription for their customers. The created subscription is associated with the end user inferred from the end user credentials. This API must be authorized by the end user using OAuth. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""openid""")
				/** Perform the request */
				def withGoogleCloudPaymentsResellerSubscriptionV1Subscription(body: Schema.GoogleCloudPaymentsResellerSubscriptionV1Subscription) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudPaymentsResellerSubscriptionV1Subscription])
			}
			object create {
				def apply(partnersId :PlayApi, subscriptionId: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/partners/${partnersId}/subscriptions").addQueryStringParameters("subscriptionId" -> subscriptionId.toString, "parent" -> parent.toString))
			}
			/** [Opt-in only] Most partners should be on auto-extend by default. Used by partners to extend a subscription service for their customers on an ongoing basis for the subscription to remain active and renewable. It should be called directly by the partner using service accounts. */
			class extend(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""openid""")
				/** Perform the request */
				def withGoogleCloudPaymentsResellerSubscriptionV1ExtendSubscriptionRequest(body: Schema.GoogleCloudPaymentsResellerSubscriptionV1ExtendSubscriptionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudPaymentsResellerSubscriptionV1ExtendSubscriptionResponse])
			}
			object extend {
				def apply(partnersId :PlayApi, subscriptionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): extend = new extend(ws.url(BASE_URL + s"v1/partners/${partnersId}/subscriptions/${subscriptionsId}:extend").addQueryStringParameters("name" -> name.toString))
			}
			/** Revokes the pending cancellation of a subscription, which is currently in `STATE_CANCEL_AT_END_OF_CYCLE` state. If the subscription is already cancelled, the request will fail. - &#42;&#42;This API doesn't apply to YouTube subscriptions.&#42;&#42; It should be called directly by the partner using service accounts. */
			class undoCancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""openid""")
				/** Perform the request */
				def withGoogleCloudPaymentsResellerSubscriptionV1UndoCancelSubscriptionRequest(body: Schema.GoogleCloudPaymentsResellerSubscriptionV1UndoCancelSubscriptionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudPaymentsResellerSubscriptionV1UndoCancelSubscriptionResponse])
			}
			object undoCancel {
				def apply(partnersId :PlayApi, subscriptionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): undoCancel = new undoCancel(ws.url(BASE_URL + s"v1/partners/${partnersId}/subscriptions/${subscriptionsId}:undoCancel").addQueryStringParameters("name" -> name.toString))
			}
			/** Used by partners to get a subscription by id. It should be called directly by the partner using service accounts. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudPaymentsResellerSubscriptionV1Subscription]) {
				val scopes = Seq("""openid""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudPaymentsResellerSubscriptionV1Subscription])
			}
			object get {
				def apply(partnersId :PlayApi, subscriptionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/partners/${partnersId}/subscriptions/${subscriptionsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudPaymentsResellerSubscriptionV1Subscription]] = (fun: get) => fun.apply()
			}
			/** Used by partners to provision a subscription for their customers. This creates a subscription without associating it with the end user account. EntitleSubscription must be called separately using OAuth in order for the end user account to be associated with the subscription. It should be called directly by the partner using service accounts. */
			class provision(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""openid""")
				/** Perform the request */
				def withGoogleCloudPaymentsResellerSubscriptionV1Subscription(body: Schema.GoogleCloudPaymentsResellerSubscriptionV1Subscription) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudPaymentsResellerSubscriptionV1Subscription])
			}
			object provision {
				def apply(partnersId :PlayApi, subscriptionId: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): provision = new provision(ws.url(BASE_URL + s"v1/partners/${partnersId}/subscriptions:provision").addQueryStringParameters("subscriptionId" -> subscriptionId.toString, "parent" -> parent.toString))
			}
			/** Used by partners to entitle a previously provisioned subscription to the current end user. The end user identity is inferred from the authorized credential of the request. This API must be authorized by the end user using OAuth. */
			class entitle(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""openid""")
				/** Perform the request */
				def withGoogleCloudPaymentsResellerSubscriptionV1EntitleSubscriptionRequest(body: Schema.GoogleCloudPaymentsResellerSubscriptionV1EntitleSubscriptionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudPaymentsResellerSubscriptionV1EntitleSubscriptionResponse])
			}
			object entitle {
				def apply(partnersId :PlayApi, subscriptionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): entitle = new entitle(ws.url(BASE_URL + s"v1/partners/${partnersId}/subscriptions/${subscriptionsId}:entitle").addQueryStringParameters("name" -> name.toString))
			}
		}
	}
}
