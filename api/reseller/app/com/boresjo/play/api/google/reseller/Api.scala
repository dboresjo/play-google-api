package com.boresjo.play.api.google.reseller

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://reseller.googleapis.com/"

	object customers {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Customer]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Customer])
		}
		object get {
			def apply(customerId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"apps/reseller/v1/customers/${customerId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Customer]] = (fun: get) => fun.apply()
		}
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCustomer(body: Schema.Customer) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Customer])
		}
		object insert {
			def apply(customerAuthToken: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"apps/reseller/v1/customers").addQueryStringParameters("customerAuthToken" -> customerAuthToken.toString))
		}
		class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCustomer(body: Schema.Customer) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Customer])
		}
		object update {
			def apply(customerId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"apps/reseller/v1/customers/${customerId}").addQueryStringParameters())
		}
		class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCustomer(body: Schema.Customer) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Customer])
		}
		object patch {
			def apply(customerId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"apps/reseller/v1/customers/${customerId}").addQueryStringParameters())
		}
	}
	object resellernotify {
		class getwatchdetails(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ResellernotifyGetwatchdetailsResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ResellernotifyGetwatchdetailsResponse])
		}
		object getwatchdetails {
			def apply()(using auth: AuthToken, ec: ExecutionContext): getwatchdetails = new getwatchdetails(ws.url(BASE_URL + s"apps/reseller/v1/resellernotify/getwatchdetails").addQueryStringParameters())
			given Conversion[getwatchdetails, Future[Schema.ResellernotifyGetwatchdetailsResponse]] = (fun: getwatchdetails) => fun.apply()
		}
		class register(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ResellernotifyResource]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.ResellernotifyResource])
		}
		object register {
			def apply(serviceAccountEmailAddress: String)(using auth: AuthToken, ec: ExecutionContext): register = new register(ws.url(BASE_URL + s"apps/reseller/v1/resellernotify/register").addQueryStringParameters("serviceAccountEmailAddress" -> serviceAccountEmailAddress.toString))
			given Conversion[register, Future[Schema.ResellernotifyResource]] = (fun: register) => fun.apply()
		}
		class unregister(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ResellernotifyResource]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.ResellernotifyResource])
		}
		object unregister {
			def apply(serviceAccountEmailAddress: String)(using auth: AuthToken, ec: ExecutionContext): unregister = new unregister(ws.url(BASE_URL + s"apps/reseller/v1/resellernotify/unregister").addQueryStringParameters("serviceAccountEmailAddress" -> serviceAccountEmailAddress.toString))
			given Conversion[unregister, Future[Schema.ResellernotifyResource]] = (fun: unregister) => fun.apply()
		}
	}
	object subscriptions {
		class changeSeats(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSeats(body: Schema.Seats) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Subscription])
		}
		object changeSeats {
			def apply(customerId: String, subscriptionId: String)(using auth: AuthToken, ec: ExecutionContext): changeSeats = new changeSeats(ws.url(BASE_URL + s"apps/reseller/v1/customers/${customerId}/subscriptions/${subscriptionId}/changeSeats").addQueryStringParameters())
		}
		class insert(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSubscription(body: Schema.Subscription) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Subscription])
		}
		object insert {
			def apply(customerId: String, customerAuthToken: String, action: String, sourceSkuId: String)(using auth: AuthToken, ec: ExecutionContext): insert = new insert(ws.url(BASE_URL + s"apps/reseller/v1/customers/${customerId}/subscriptions").addQueryStringParameters("customerAuthToken" -> customerAuthToken.toString, "action" -> action.toString, "sourceSkuId" -> sourceSkuId.toString))
		}
		class suspend(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Subscription]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Subscription])
		}
		object suspend {
			def apply(customerId: String, subscriptionId: String)(using auth: AuthToken, ec: ExecutionContext): suspend = new suspend(ws.url(BASE_URL + s"apps/reseller/v1/customers/${customerId}/subscriptions/${subscriptionId}/suspend").addQueryStringParameters())
			given Conversion[suspend, Future[Schema.Subscription]] = (fun: suspend) => fun.apply()
		}
		class changePlan(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withChangePlanRequest(body: Schema.ChangePlanRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Subscription])
		}
		object changePlan {
			def apply(customerId: String, subscriptionId: String)(using auth: AuthToken, ec: ExecutionContext): changePlan = new changePlan(ws.url(BASE_URL + s"apps/reseller/v1/customers/${customerId}/subscriptions/${subscriptionId}/changePlan").addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_ => ())
		}
		object delete {
			def apply(customerId: String, subscriptionId: String, deletionType: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"apps/reseller/v1/customers/${customerId}/subscriptions/${subscriptionId}").addQueryStringParameters("deletionType" -> deletionType.toString))
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class startPaidService(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Subscription]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Subscription])
		}
		object startPaidService {
			def apply(customerId: String, subscriptionId: String)(using auth: AuthToken, ec: ExecutionContext): startPaidService = new startPaidService(ws.url(BASE_URL + s"apps/reseller/v1/customers/${customerId}/subscriptions/${subscriptionId}/startPaidService").addQueryStringParameters())
			given Conversion[startPaidService, Future[Schema.Subscription]] = (fun: startPaidService) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Subscription]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Subscription])
		}
		object get {
			def apply(customerId: String, subscriptionId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"apps/reseller/v1/customers/${customerId}/subscriptions/${subscriptionId}").addQueryStringParameters())
			given Conversion[get, Future[Schema.Subscription]] = (fun: get) => fun.apply()
		}
		class activate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Subscription]) {
			def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Subscription])
		}
		object activate {
			def apply(customerId: String, subscriptionId: String)(using auth: AuthToken, ec: ExecutionContext): activate = new activate(ws.url(BASE_URL + s"apps/reseller/v1/customers/${customerId}/subscriptions/${subscriptionId}/activate").addQueryStringParameters())
			given Conversion[activate, Future[Schema.Subscription]] = (fun: activate) => fun.apply()
		}
		class changeRenewalSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withRenewalSettings(body: Schema.RenewalSettings) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Subscription])
		}
		object changeRenewalSettings {
			def apply(customerId: String, subscriptionId: String)(using auth: AuthToken, ec: ExecutionContext): changeRenewalSettings = new changeRenewalSettings(ws.url(BASE_URL + s"apps/reseller/v1/customers/${customerId}/subscriptions/${subscriptionId}/changeRenewalSettings").addQueryStringParameters())
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Subscriptions]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Subscriptions])
		}
		object list {
			def apply(customerAuthToken: String, customerId: String, customerNamePrefix: String, maxResults: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"apps/reseller/v1/subscriptions").addQueryStringParameters("customerAuthToken" -> customerAuthToken.toString, "customerId" -> customerId.toString, "customerNamePrefix" -> customerNamePrefix.toString, "maxResults" -> maxResults.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.Subscriptions]] = (fun: list) => fun.apply()
		}
	}
}
