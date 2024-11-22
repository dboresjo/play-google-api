package com.boresjo.play.api.google.reseller

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
		"""https://www.googleapis.com/auth/apps.order""" /* Manage users on your domain */,
		"""https://www.googleapis.com/auth/apps.order.readonly""" /* Manage users on your domain */
	)

	private val BASE_URL = "https://reseller.googleapis.com/"

	object customers {
		/** Gets a customer account. Use this operation to see a customer account already in your reseller management, or to see the minimal account information for an existing customer that you do not manage. For more information about the API response for existing customers, see [retrieving a customer account](/admin-sdk/reseller/v1/how-tos/manage_customers#get_customer). */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Customer]) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.order""", """https://www.googleapis.com/auth/apps.order.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Customer])
		}
		object get {
			def apply(customerId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"apps/reseller/v1/customers/${customerId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Customer]] = (fun: get) => fun.apply()
		}
		/** Orders a new customer's account. Before ordering a new customer account, establish whether the customer account already exists using the [`customers.get`](/admin-sdk/reseller/v1/reference/customers/get) If the customer account exists as a direct Google account or as a resold customer account from another reseller, use the `customerAuthToken\` as described in [order a resold account for an existing customer](/admin-sdk/reseller/v1/how-tos/manage_customers#create_existing_customer). For more information about ordering a new customer account, see [order a new customer account](/admin-sdk/reseller/v1/how-tos/manage_customers#create_customer). After creating a new customer account, you must provision a user as an administrator. The customer's administrator is required to sign in to the Admin console and sign the G Suite via Reseller agreement to activate the account. Resellers are prohibited from signing the G Suite via Reseller agreement on the customer's behalf. For more information, see [order a new customer account](/admin-sdk/reseller/v1/how-tos/manage_customers#tos). */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
			/** Perform the request */
			def withCustomer(body: Schema.Customer) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Customer])
		}
		object insert {
			def apply(customerAuthToken: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"apps/reseller/v1/customers").addQueryStringParameters("customerAuthToken" -> customerAuthToken.toString))
		}
		/** Updates a customer account's settings. You cannot update `customerType` via the Reseller API, but a `"team"` customer can verify their domain and become `customerType = "domain"`. For more information, see [update a customer's settings](/admin-sdk/reseller/v1/how-tos/manage_customers#update_customer). */
		class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
			/** Perform the request */
			def withCustomer(body: Schema.Customer) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Customer])
		}
		object update {
			def apply(customerId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"apps/reseller/v1/customers/${customerId}").addQueryStringParameters())
		}
		/** Updates a customer account's settings. This method supports patch semantics. You cannot update `customerType` via the Reseller API, but a `"team"` customer can verify their domain and become `customerType = "domain"`. For more information, see [Verify your domain to unlock Essentials features](https://support.google.com/a/answer/9122284). */
		class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
			/** Perform the request */
			def withCustomer(body: Schema.Customer) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Customer])
		}
		object patch {
			def apply(customerId: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"apps/reseller/v1/customers/${customerId}").addQueryStringParameters())
		}
	}
	object resellernotify {
		/** Returns all the details of the watch corresponding to the reseller. */
		class getwatchdetails(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ResellernotifyGetwatchdetailsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.order""", """https://www.googleapis.com/auth/apps.order.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ResellernotifyGetwatchdetailsResponse])
		}
		object getwatchdetails {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): getwatchdetails = new getwatchdetails(ws.url(BASE_URL + s"apps/reseller/v1/resellernotify/getwatchdetails").addQueryStringParameters())
			given Conversion[getwatchdetails, Future[Schema.ResellernotifyGetwatchdetailsResponse]] = (fun: getwatchdetails) => fun.apply()
		}
		/** Registers a Reseller for receiving notifications. */
		class register(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ResellernotifyResource]) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.ResellernotifyResource])
		}
		object register {
			def apply(serviceAccountEmailAddress: String)(using signer: RequestSigner, ec: ExecutionContext): register = new register(ws.url(BASE_URL + s"apps/reseller/v1/resellernotify/register").addQueryStringParameters("serviceAccountEmailAddress" -> serviceAccountEmailAddress.toString))
			given Conversion[register, Future[Schema.ResellernotifyResource]] = (fun: register) => fun.apply()
		}
		/** Unregisters a Reseller for receiving notifications. */
		class unregister(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ResellernotifyResource]) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.ResellernotifyResource])
		}
		object unregister {
			def apply(serviceAccountEmailAddress: String)(using signer: RequestSigner, ec: ExecutionContext): unregister = new unregister(ws.url(BASE_URL + s"apps/reseller/v1/resellernotify/unregister").addQueryStringParameters("serviceAccountEmailAddress" -> serviceAccountEmailAddress.toString))
			given Conversion[unregister, Future[Schema.ResellernotifyResource]] = (fun: unregister) => fun.apply()
		}
	}
	object subscriptions {
		/** Updates a subscription's user license settings. For more information about updating an annual commitment plan or a flexible plan subscription’s licenses, see [Manage Subscriptions](/admin-sdk/reseller/v1/how-tos/manage_subscriptions#update_subscription_seat). */
		class changeSeats(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
			/** Perform the request */
			def withSeats(body: Schema.Seats) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Subscription])
		}
		object changeSeats {
			def apply(customerId: String, subscriptionId: String)(using signer: RequestSigner, ec: ExecutionContext): changeSeats = new changeSeats(ws.url(BASE_URL + s"apps/reseller/v1/customers/${customerId}/subscriptions/${subscriptionId}/changeSeats").addQueryStringParameters())
		}
		/** Creates or transfer a subscription. Create a subscription for a customer's account that you ordered using the [Order a new customer account](/admin-sdk/reseller/v1/reference/customers/insert.html) method. For more information about creating a subscription for different payment plans, see [manage subscriptions](/admin-sdk/reseller/v1/how-tos/manage_subscriptions#create_subscription).\ If you did not order the customer's account using the customer insert method, use the customer's `customerAuthToken` when creating a subscription for that customer. If transferring a G Suite subscription with an associated Google Drive or Google Vault subscription, use the [batch operation](/admin-sdk/reseller/v1/how-tos/batch.html) to transfer all of these subscriptions. For more information, see how to [transfer subscriptions](/admin-sdk/reseller/v1/how-tos/manage_subscriptions#transfer_a_subscription). */
		class insert(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
			/** Perform the request */
			def withSubscription(body: Schema.Subscription) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Subscription])
		}
		object insert {
			def apply(customerId: String, customerAuthToken: String, action: String, sourceSkuId: String)(using signer: RequestSigner, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"apps/reseller/v1/customers/${customerId}/subscriptions").addQueryStringParameters("customerAuthToken" -> customerAuthToken.toString, "action" -> action.toString, "sourceSkuId" -> sourceSkuId.toString))
		}
		/** Suspends an active subscription. You can use this method to suspend a paid subscription that is currently in the `ACTIVE` state. &#42; For `FLEXIBLE` subscriptions, billing is paused. &#42; For `ANNUAL_MONTHLY_PAY` or `ANNUAL_YEARLY_PAY` subscriptions: &#42; Suspending the subscription does not change the renewal date that was originally committed to. &#42; A suspended subscription does not renew. If you activate the subscription after the original renewal date, a new annual subscription will be created, starting on the day of activation. We strongly encourage you to suspend subscriptions only for short periods of time as suspensions over 60 days may result in the subscription being cancelled. */
		class suspend(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Subscription]) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Subscription])
		}
		object suspend {
			def apply(customerId: String, subscriptionId: String)(using signer: RequestSigner, ec: ExecutionContext): suspend = new suspend(ws.url(BASE_URL + s"apps/reseller/v1/customers/${customerId}/subscriptions/${subscriptionId}/suspend").addQueryStringParameters())
			given Conversion[suspend, Future[Schema.Subscription]] = (fun: suspend) => fun.apply()
		}
		/** Updates a subscription plan. Use this method to update a plan for a 30-day trial or a flexible plan subscription to an annual commitment plan with monthly or yearly payments. How a plan is updated differs depending on the plan and the products. For more information, see the description in [manage subscriptions](/admin-sdk/reseller/v1/how-tos/manage_subscriptions#update_subscription_plan). */
		class changePlan(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
			/** Perform the request */
			def withChangePlanRequest(body: Schema.ChangePlanRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Subscription])
		}
		object changePlan {
			def apply(customerId: String, subscriptionId: String)(using signer: RequestSigner, ec: ExecutionContext): changePlan = new changePlan(ws.url(BASE_URL + s"apps/reseller/v1/customers/${customerId}/subscriptions/${subscriptionId}/changePlan").addQueryStringParameters())
		}
		/** Cancels, suspends, or transfers a subscription to direct. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Unit]) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(customerId: String, subscriptionId: String, deletionType: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"apps/reseller/v1/customers/${customerId}/subscriptions/${subscriptionId}").addQueryStringParameters("deletionType" -> deletionType.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		/** Immediately move a 30-day free trial subscription to a paid service subscription. This method is only applicable if a payment plan has already been set up for the 30-day trial subscription. For more information, see [manage subscriptions](/admin-sdk/reseller/v1/how-tos/manage_subscriptions#paid_service). */
		class startPaidService(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Subscription]) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Subscription])
		}
		object startPaidService {
			def apply(customerId: String, subscriptionId: String)(using signer: RequestSigner, ec: ExecutionContext): startPaidService = new startPaidService(ws.url(BASE_URL + s"apps/reseller/v1/customers/${customerId}/subscriptions/${subscriptionId}/startPaidService").addQueryStringParameters())
			given Conversion[startPaidService, Future[Schema.Subscription]] = (fun: startPaidService) => fun.apply()
		}
		/** Gets a specific subscription. The `subscriptionId` can be found using the [Retrieve all reseller subscriptions](/admin-sdk/reseller/v1/how-tos/manage_subscriptions#get_all_subscriptions) method. For more information about retrieving a specific subscription, see the information descrived in [manage subscriptions](/admin-sdk/reseller/v1/how-tos/manage_subscriptions#get_subscription). */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Subscription]) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.order""", """https://www.googleapis.com/auth/apps.order.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Subscription])
		}
		object get {
			def apply(customerId: String, subscriptionId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"apps/reseller/v1/customers/${customerId}/subscriptions/${subscriptionId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Subscription]] = (fun: get) => fun.apply()
		}
		/** Activates a subscription previously suspended by the reseller. If you did not suspend the customer subscription and it is suspended for any other reason, such as for abuse or a pending ToS acceptance, this call will not reactivate the customer subscription. */
		class activate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Subscription]) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.Subscription])
		}
		object activate {
			def apply(customerId: String, subscriptionId: String)(using signer: RequestSigner, ec: ExecutionContext): activate = new activate(ws.url(BASE_URL + s"apps/reseller/v1/customers/${customerId}/subscriptions/${subscriptionId}/activate").addQueryStringParameters())
			given Conversion[activate, Future[Schema.Subscription]] = (fun: activate) => fun.apply()
		}
		/** Updates a user license's renewal settings. This is applicable for accounts with annual commitment plans only. For more information, see the description in [manage subscriptions](/admin-sdk/reseller/v1/how-tos/manage_subscriptions#update_renewal). */
		class changeRenewalSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
			/** Perform the request */
			def withRenewalSettings(body: Schema.RenewalSettings) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Subscription])
		}
		object changeRenewalSettings {
			def apply(customerId: String, subscriptionId: String)(using signer: RequestSigner, ec: ExecutionContext): changeRenewalSettings = new changeRenewalSettings(ws.url(BASE_URL + s"apps/reseller/v1/customers/${customerId}/subscriptions/${subscriptionId}/changeRenewalSettings").addQueryStringParameters())
		}
		/** Lists of subscriptions managed by the reseller. The list can be all subscriptions, all of a customer's subscriptions, or all of a customer's transferable subscriptions. Optionally, this method can filter the response by a `customerNamePrefix`. For more information, see [manage subscriptions](/admin-sdk/reseller/v1/how-tos/manage_subscriptions). */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Subscriptions]) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.order""", """https://www.googleapis.com/auth/apps.order.readonly""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Subscriptions])
		}
		object list {
			def apply(customerAuthToken: String, customerId: String, customerNamePrefix: String, maxResults: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"apps/reseller/v1/subscriptions").addQueryStringParameters("customerAuthToken" -> customerAuthToken.toString, "customerId" -> customerId.toString, "customerNamePrefix" -> customerNamePrefix.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.Subscriptions]] = (fun: list) => fun.apply()
		}
	}
}
