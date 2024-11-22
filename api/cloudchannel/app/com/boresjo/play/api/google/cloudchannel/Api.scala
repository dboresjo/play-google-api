package com.boresjo.play.api.google.cloudchannel

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://cloudchannel.googleapis.com/"

	object operations {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
		}
		object list {
			def apply(name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object get {
			def apply(operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
			def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
		}
		object delete {
			def apply(operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
		}
		class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleLongrunningCancelOperationRequest(body: Schema.GoogleLongrunningCancelOperationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
		}
		object cancel {
			def apply(operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
		}
	}
	object accounts {
		class register(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. Resource name of the account. Required if integrator is not provided. Otherwise, leave this field empty/unset. */
			def withAccount(account: String) = new register(req.addQueryStringParameters("account" -> account.toString))
			def withGoogleCloudChannelV1RegisterSubscriberRequest(body: Schema.GoogleCloudChannelV1RegisterSubscriberRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudChannelV1RegisterSubscriberResponse])
		}
		object register {
			def apply(accountsId :PlayApi)(using auth: AuthToken, ec: ExecutionContext): register = new register(ws.url(BASE_URL + s"v1/accounts/${accountsId}:register").addQueryStringParameters())
		}
		class listTransferableOffers(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleCloudChannelV1ListTransferableOffersRequest(body: Schema.GoogleCloudChannelV1ListTransferableOffersRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudChannelV1ListTransferableOffersResponse])
		}
		object listTransferableOffers {
			def apply(accountsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): listTransferableOffers = new listTransferableOffers(ws.url(BASE_URL + s"v1/accounts/${accountsId}:listTransferableOffers").addQueryStringParameters("parent" -> parent.toString))
		}
		class listTransferableSkus(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleCloudChannelV1ListTransferableSkusRequest(body: Schema.GoogleCloudChannelV1ListTransferableSkusRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudChannelV1ListTransferableSkusResponse])
		}
		object listTransferableSkus {
			def apply(accountsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): listTransferableSkus = new listTransferableSkus(ws.url(BASE_URL + s"v1/accounts/${accountsId}:listTransferableSkus").addQueryStringParameters("parent" -> parent.toString))
		}
		class unregister(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. Resource name of the account. Required if integrator is not provided. Otherwise, leave this field empty/unset. */
			def withAccount(account: String) = new unregister(req.addQueryStringParameters("account" -> account.toString))
			def withGoogleCloudChannelV1UnregisterSubscriberRequest(body: Schema.GoogleCloudChannelV1UnregisterSubscriberRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudChannelV1UnregisterSubscriberResponse])
		}
		object unregister {
			def apply(accountsId :PlayApi)(using auth: AuthToken, ec: ExecutionContext): unregister = new unregister(ws.url(BASE_URL + s"v1/accounts/${accountsId}:unregister").addQueryStringParameters())
		}
		class checkCloudIdentityAccountsExist(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleCloudChannelV1CheckCloudIdentityAccountsExistRequest(body: Schema.GoogleCloudChannelV1CheckCloudIdentityAccountsExistRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudChannelV1CheckCloudIdentityAccountsExistResponse])
		}
		object checkCloudIdentityAccountsExist {
			def apply(accountsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): checkCloudIdentityAccountsExist = new checkCloudIdentityAccountsExist(ws.url(BASE_URL + s"v1/accounts/${accountsId}:checkCloudIdentityAccountsExist").addQueryStringParameters("parent" -> parent.toString))
		}
		class listSubscribers(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ListSubscribersResponse]) {
			/** Optional. Resource name of the account. Required if integrator is not provided. Otherwise, leave this field empty/unset. */
			def withAccount(account: String) = new listSubscribers(req.addQueryStringParameters("account" -> account.toString))
			/** Optional. The maximum number of service accounts to return. The service may return fewer than this value. If unspecified, returns at most 100 service accounts. The maximum value is 1000; the server will coerce values above 1000.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new listSubscribers(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A page token, received from a previous `ListSubscribers` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListSubscribers` must match the call that provided the page token. */
			def withPageToken(pageToken: String) = new listSubscribers(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Optional. Resource name of the integrator. Required if account is not provided. Otherwise, leave this field empty/unset. */
			def withIntegrator(integrator: String) = new listSubscribers(req.addQueryStringParameters("integrator" -> integrator.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ListSubscribersResponse])
		}
		object listSubscribers {
			def apply(accountsId :PlayApi)(using auth: AuthToken, ec: ExecutionContext): listSubscribers = new listSubscribers(ws.url(BASE_URL + s"v1/accounts/${accountsId}:listSubscribers").addQueryStringParameters())
			given Conversion[listSubscribers, Future[Schema.GoogleCloudChannelV1ListSubscribersResponse]] = (fun: listSubscribers) => fun.apply()
		}
		object skuGroups {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ListSkuGroupsResponse]) {
				/** Optional. The maximum number of SKU groups to return. The service may return fewer than this value. If unspecified, returns a maximum of 1000 SKU groups. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A token identifying a page of results beyond the first page. Obtained through ListSkuGroupsResponse.next_page_token of the previous CloudChannelService.ListSkuGroups call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ListSkuGroupsResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts/${accountsId}/skuGroups").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudChannelV1ListSkuGroupsResponse]] = (fun: list) => fun.apply()
			}
			object billableSkus {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ListSkuGroupBillableSkusResponse]) {
					/** Optional. The maximum number of SKUs to return. The service may return fewer than this value. If unspecified, returns a maximum of 100000 SKUs. The maximum value is 100000; values above 100000 will be coerced to 100000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A token identifying a page of results beyond the first page. Obtained through ListSkuGroupBillableSkusResponse.next_page_token of the previous CloudChannelService.ListSkuGroupBillableSkus call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ListSkuGroupBillableSkusResponse])
				}
				object list {
					def apply(accountsId :PlayApi, skuGroupsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts/${accountsId}/skuGroups/${skuGroupsId}/billableSkus").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudChannelV1ListSkuGroupBillableSkusResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object offers {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ListOffersResponse]) {
				/** Optional. Requested page size. Server might return fewer results than requested. If unspecified, returns at most 500 Offers. The maximum value is 1000; the server will coerce values above 1000.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A token for a page of results other than the first page. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The expression to filter results by name (name of the Offer), sku.name (name of the SKU), or sku.product.name (name of the Product). Example 1: sku.product.name=products/p1 AND sku.name!=products/p1/skus/s1 Example 2: name=accounts/a1/offers/o1 */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Optional. The BCP-47 language code. For example, "en-US". The response will localize in the corresponding language code, if specified. The default value is "en-US". */
				def withLanguageCode(languageCode: String) = new list(req.addQueryStringParameters("languageCode" -> languageCode.toString))
				/** Optional. A boolean flag that determines if a response returns future offers 30 days from now. If the show_future_offers is true, the response will only contain offers that are scheduled to be available 30 days from now. */
				def withShowFutureOffers(showFutureOffers: Boolean) = new list(req.addQueryStringParameters("showFutureOffers" -> showFutureOffers.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ListOffersResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts/${accountsId}/offers").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudChannelV1ListOffersResponse]] = (fun: list) => fun.apply()
			}
		}
		object reportJobs {
			class fetchReportResults(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudChannelV1FetchReportResultsRequest(body: Schema.GoogleCloudChannelV1FetchReportResultsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudChannelV1FetchReportResultsResponse])
			}
			object fetchReportResults {
				def apply(accountsId :PlayApi, reportJobsId :PlayApi, reportJob: String)(using auth: AuthToken, ec: ExecutionContext): fetchReportResults = new fetchReportResults(ws.url(BASE_URL + s"v1/accounts/${accountsId}/reportJobs/${reportJobsId}:fetchReportResults").addQueryStringParameters("reportJob" -> reportJob.toString))
			}
		}
		object channelPartnerLinks {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudChannelV1ChannelPartnerLink(body: Schema.GoogleCloudChannelV1ChannelPartnerLink) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudChannelV1ChannelPartnerLink])
			}
			object create {
				def apply(accountsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/accounts/${accountsId}/channelPartnerLinks").addQueryStringParameters("parent" -> parent.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ChannelPartnerLink]) {
				/** Optional. The level of granularity the ChannelPartnerLink will display.<br>Possible values:<br>UNSPECIFIED: The default / unset value. The API will default to the BASIC view.<br>BASIC: Includes all fields except the ChannelPartnerLink.channel_partner_cloud_identity_info.<br>FULL: Includes all fields. */
				def withView(view: String) = new get(req.addQueryStringParameters("view" -> view.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ChannelPartnerLink])
			}
			object get {
				def apply(accountsId :PlayApi, channelPartnerLinksId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/accounts/${accountsId}/channelPartnerLinks/${channelPartnerLinksId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudChannelV1ChannelPartnerLink]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudChannelV1UpdateChannelPartnerLinkRequest(body: Schema.GoogleCloudChannelV1UpdateChannelPartnerLinkRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudChannelV1ChannelPartnerLink])
			}
			object patch {
				def apply(accountsId :PlayApi, channelPartnerLinksId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/accounts/${accountsId}/channelPartnerLinks/${channelPartnerLinksId}").addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ListChannelPartnerLinksResponse]) {
				/** Optional. Requested page size. Server might return fewer results than requested. If unspecified, server will pick a default size (25). The maximum value is 200; the server will coerce values above 200.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A token for a page of results other than the first page. Obtained using ListChannelPartnerLinksResponse.next_page_token of the previous CloudChannelService.ListChannelPartnerLinks call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The level of granularity the ChannelPartnerLink will display.<br>Possible values:<br>UNSPECIFIED: The default / unset value. The API will default to the BASIC view.<br>BASIC: Includes all fields except the ChannelPartnerLink.channel_partner_cloud_identity_info.<br>FULL: Includes all fields. */
				def withView(view: String) = new list(req.addQueryStringParameters("view" -> view.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ListChannelPartnerLinksResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts/${accountsId}/channelPartnerLinks").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudChannelV1ListChannelPartnerLinksResponse]] = (fun: list) => fun.apply()
			}
			object channelPartnerRepricingConfigs {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudChannelV1ChannelPartnerRepricingConfig(body: Schema.GoogleCloudChannelV1ChannelPartnerRepricingConfig) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudChannelV1ChannelPartnerRepricingConfig])
				}
				object create {
					def apply(accountsId :PlayApi, channelPartnerLinksId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/accounts/${accountsId}/channelPartnerLinks/${channelPartnerLinksId}/channelPartnerRepricingConfigs").addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(accountsId :PlayApi, channelPartnerLinksId :PlayApi, channelPartnerRepricingConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/accounts/${accountsId}/channelPartnerLinks/${channelPartnerLinksId}/channelPartnerRepricingConfigs/${channelPartnerRepricingConfigsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ChannelPartnerRepricingConfig]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ChannelPartnerRepricingConfig])
				}
				object get {
					def apply(accountsId :PlayApi, channelPartnerLinksId :PlayApi, channelPartnerRepricingConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/accounts/${accountsId}/channelPartnerLinks/${channelPartnerLinksId}/channelPartnerRepricingConfigs/${channelPartnerRepricingConfigsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudChannelV1ChannelPartnerRepricingConfig]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudChannelV1ChannelPartnerRepricingConfig(body: Schema.GoogleCloudChannelV1ChannelPartnerRepricingConfig) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudChannelV1ChannelPartnerRepricingConfig])
				}
				object patch {
					def apply(accountsId :PlayApi, channelPartnerLinksId :PlayApi, channelPartnerRepricingConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/accounts/${accountsId}/channelPartnerLinks/${channelPartnerLinksId}/channelPartnerRepricingConfigs/${channelPartnerRepricingConfigsId}").addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ListChannelPartnerRepricingConfigsResponse]) {
					/** Optional. The maximum number of repricing configs to return. The service may return fewer than this value. If unspecified, returns a maximum of 50 rules. The maximum value is 100; values above 100 will be coerced to 100.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A token identifying a page of results beyond the first page. Obtained through ListChannelPartnerRepricingConfigsResponse.next_page_token of the previous CloudChannelService.ListChannelPartnerRepricingConfigs call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. A filter for [CloudChannelService.ListChannelPartnerRepricingConfigs] results (channel_partner_link only). You can use this filter when you support a BatchGet-like query. To use the filter, you must set `parent=accounts/{account_id}/channelPartnerLinks/-`. Example: `channel_partner_link = accounts/account_id/channelPartnerLinks/c1` OR `channel_partner_link = accounts/account_id/channelPartnerLinks/c2`. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ListChannelPartnerRepricingConfigsResponse])
				}
				object list {
					def apply(accountsId :PlayApi, channelPartnerLinksId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts/${accountsId}/channelPartnerLinks/${channelPartnerLinksId}/channelPartnerRepricingConfigs").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudChannelV1ListChannelPartnerRepricingConfigsResponse]] = (fun: list) => fun.apply()
				}
			}
			object customers {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudChannelV1Customer(body: Schema.GoogleCloudChannelV1Customer) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudChannelV1Customer])
				}
				object create {
					def apply(accountsId :PlayApi, channelPartnerLinksId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/accounts/${accountsId}/channelPartnerLinks/${channelPartnerLinksId}/customers").addQueryStringParameters("parent" -> parent.toString))
				}
				class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudChannelV1ImportCustomerRequest(body: Schema.GoogleCloudChannelV1ImportCustomerRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudChannelV1Customer])
				}
				object `import` {
					def apply(accountsId :PlayApi, channelPartnerLinksId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/accounts/${accountsId}/channelPartnerLinks/${channelPartnerLinksId}/customers:import").addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(accountsId :PlayApi, channelPartnerLinksId :PlayApi, customersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/accounts/${accountsId}/channelPartnerLinks/${channelPartnerLinksId}/customers/${customersId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1Customer]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1Customer])
				}
				object get {
					def apply(accountsId :PlayApi, channelPartnerLinksId :PlayApi, customersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/accounts/${accountsId}/channelPartnerLinks/${channelPartnerLinksId}/customers/${customersId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudChannelV1Customer]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** The update mask that applies to the resource. Optional.<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					def withGoogleCloudChannelV1Customer(body: Schema.GoogleCloudChannelV1Customer) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudChannelV1Customer])
				}
				object patch {
					def apply(accountsId :PlayApi, channelPartnerLinksId :PlayApi, customersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/accounts/${accountsId}/channelPartnerLinks/${channelPartnerLinksId}/customers/${customersId}").addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ListCustomersResponse]) {
					/** Optional. The maximum number of customers to return. The service may return fewer than this value. If unspecified, returns at most 10 customers. The maximum value is 50.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A token identifying a page of results other than the first page. Obtained through ListCustomersResponse.next_page_token of the previous CloudChannelService.ListCustomers call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Filters applied to the [CloudChannelService.ListCustomers] results. See https://cloud.google.com/channel/docs/concepts/google-cloud/filter-customers for more information. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ListCustomersResponse])
				}
				object list {
					def apply(accountsId :PlayApi, channelPartnerLinksId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts/${accountsId}/channelPartnerLinks/${channelPartnerLinksId}/customers").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudChannelV1ListCustomersResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object customers {
			class queryEligibleBillingAccounts(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1QueryEligibleBillingAccountsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1QueryEligibleBillingAccountsResponse])
			}
			object queryEligibleBillingAccounts {
				def apply(accountsId :PlayApi, customersId :PlayApi, customer: String, skus: String)(using auth: AuthToken, ec: ExecutionContext): queryEligibleBillingAccounts = new queryEligibleBillingAccounts(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}:queryEligibleBillingAccounts").addQueryStringParameters("customer" -> customer.toString, "skus" -> skus.toString))
				given Conversion[queryEligibleBillingAccounts, Future[Schema.GoogleCloudChannelV1QueryEligibleBillingAccountsResponse]] = (fun: queryEligibleBillingAccounts) => fun.apply()
			}
			class transferEntitlements(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudChannelV1TransferEntitlementsRequest(body: Schema.GoogleCloudChannelV1TransferEntitlementsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object transferEntitlements {
				def apply(accountsId :PlayApi, customersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): transferEntitlements = new transferEntitlements(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}:transferEntitlements").addQueryStringParameters("parent" -> parent.toString))
			}
			class listPurchasableSkus(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ListPurchasableSkusResponse]) {
				/** Optional. Requested page size. Server might return fewer results than requested. If unspecified, returns at most 100 SKUs. The maximum value is 1000; the server will coerce values above 1000.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new listPurchasableSkus(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A token for a page of results other than the first page. */
				def withPageToken(pageToken: String) = new listPurchasableSkus(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The BCP-47 language code. For example, "en-US". The response will localize in the corresponding language code, if specified. The default value is "en-US". */
				def withLanguageCode(languageCode: String) = new listPurchasableSkus(req.addQueryStringParameters("languageCode" -> languageCode.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ListPurchasableSkusResponse])
			}
			object listPurchasableSkus {
				def apply(accountsId :PlayApi, customersId :PlayApi, customer: String, createEntitlementPurchaseProduct: String, changeOfferPurchaseEntitlement: String, changeOfferPurchaseChangeType: String)(using auth: AuthToken, ec: ExecutionContext): listPurchasableSkus = new listPurchasableSkus(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}:listPurchasableSkus").addQueryStringParameters("customer" -> customer.toString, "createEntitlementPurchase.product" -> createEntitlementPurchaseProduct.toString, "changeOfferPurchase.entitlement" -> changeOfferPurchaseEntitlement.toString, "changeOfferPurchase.changeType" -> changeOfferPurchaseChangeType.toString))
				given Conversion[listPurchasableSkus, Future[Schema.GoogleCloudChannelV1ListPurchasableSkusResponse]] = (fun: listPurchasableSkus) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudChannelV1Customer(body: Schema.GoogleCloudChannelV1Customer) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudChannelV1Customer])
			}
			object create {
				def apply(accountsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers").addQueryStringParameters("parent" -> parent.toString))
			}
			class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudChannelV1ImportCustomerRequest(body: Schema.GoogleCloudChannelV1ImportCustomerRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudChannelV1Customer])
			}
			object `import` {
				def apply(accountsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers:import").addQueryStringParameters("parent" -> parent.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1Customer]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1Customer])
			}
			object get {
				def apply(accountsId :PlayApi, customersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudChannelV1Customer]] = (fun: get) => fun.apply()
			}
			class listPurchasableOffers(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ListPurchasableOffersResponse]) {
				/** Optional. Billing account that the result should be restricted to. Format: accounts/{account_id}/billingAccounts/{billing_account_id}. */
				def withCreateEntitlementPurchaseBillingAccount(createEntitlementPurchaseBillingAccount: String) = new listPurchasableOffers(req.addQueryStringParameters("createEntitlementPurchase.billingAccount" -> createEntitlementPurchaseBillingAccount.toString))
				/** Optional. Resource name of the new target SKU. Provide this SKU when upgrading or downgrading an entitlement. Format: products/{product_id}/skus/{sku_id} */
				def withChangeOfferPurchaseNewSku(changeOfferPurchaseNewSku: String) = new listPurchasableOffers(req.addQueryStringParameters("changeOfferPurchase.newSku" -> changeOfferPurchaseNewSku.toString))
				/** Optional. Resource name of the new target Billing Account. Provide this Billing Account when setting up billing for a trial subscription. Format: accounts/{account_id}/billingAccounts/{billing_account_id}. This field is only relevant for multi-currency accounts. It should be left empty for single currency accounts. */
				def withChangeOfferPurchaseBillingAccount(changeOfferPurchaseBillingAccount: String) = new listPurchasableOffers(req.addQueryStringParameters("changeOfferPurchase.billingAccount" -> changeOfferPurchaseBillingAccount.toString))
				/** Optional. Requested page size. Server might return fewer results than requested. If unspecified, returns at most 100 Offers. The maximum value is 1000; the server will coerce values above 1000.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new listPurchasableOffers(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A token for a page of results other than the first page. */
				def withPageToken(pageToken: String) = new listPurchasableOffers(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The BCP-47 language code. For example, "en-US". The response will localize in the corresponding language code, if specified. The default value is "en-US". */
				def withLanguageCode(languageCode: String) = new listPurchasableOffers(req.addQueryStringParameters("languageCode" -> languageCode.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ListPurchasableOffersResponse])
			}
			object listPurchasableOffers {
				def apply(accountsId :PlayApi, customersId :PlayApi, customer: String, createEntitlementPurchaseSku: String, changeOfferPurchaseEntitlement: String)(using auth: AuthToken, ec: ExecutionContext): listPurchasableOffers = new listPurchasableOffers(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}:listPurchasableOffers").addQueryStringParameters("customer" -> customer.toString, "createEntitlementPurchase.sku" -> createEntitlementPurchaseSku.toString, "changeOfferPurchase.entitlement" -> changeOfferPurchaseEntitlement.toString))
				given Conversion[listPurchasableOffers, Future[Schema.GoogleCloudChannelV1ListPurchasableOffersResponse]] = (fun: listPurchasableOffers) => fun.apply()
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(accountsId :PlayApi, customersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				/** The update mask that applies to the resource. Optional.<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				def withGoogleCloudChannelV1Customer(body: Schema.GoogleCloudChannelV1Customer) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudChannelV1Customer])
			}
			object patch {
				def apply(accountsId :PlayApi, customersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}").addQueryStringParameters("name" -> name.toString))
			}
			class transferEntitlementsToGoogle(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudChannelV1TransferEntitlementsToGoogleRequest(body: Schema.GoogleCloudChannelV1TransferEntitlementsToGoogleRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object transferEntitlementsToGoogle {
				def apply(accountsId :PlayApi, customersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): transferEntitlementsToGoogle = new transferEntitlementsToGoogle(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}:transferEntitlementsToGoogle").addQueryStringParameters("parent" -> parent.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ListCustomersResponse]) {
				/** Optional. The maximum number of customers to return. The service may return fewer than this value. If unspecified, returns at most 10 customers. The maximum value is 50.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A token identifying a page of results other than the first page. Obtained through ListCustomersResponse.next_page_token of the previous CloudChannelService.ListCustomers call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. Filters applied to the [CloudChannelService.ListCustomers] results. See https://cloud.google.com/channel/docs/concepts/google-cloud/filter-customers for more information. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ListCustomersResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudChannelV1ListCustomersResponse]] = (fun: list) => fun.apply()
			}
			class provisionCloudIdentity(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudChannelV1ProvisionCloudIdentityRequest(body: Schema.GoogleCloudChannelV1ProvisionCloudIdentityRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object provisionCloudIdentity {
				def apply(accountsId :PlayApi, customersId :PlayApi, customer: String)(using auth: AuthToken, ec: ExecutionContext): provisionCloudIdentity = new provisionCloudIdentity(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}:provisionCloudIdentity").addQueryStringParameters("customer" -> customer.toString))
			}
			object entitlements {
				class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudChannelV1CancelEntitlementRequest(body: Schema.GoogleCloudChannelV1CancelEntitlementRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object cancel {
					def apply(accountsId :PlayApi, customersId :PlayApi, entitlementsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}/entitlements/${entitlementsId}:cancel").addQueryStringParameters("name" -> name.toString))
				}
				class changeOffer(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudChannelV1ChangeOfferRequest(body: Schema.GoogleCloudChannelV1ChangeOfferRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object changeOffer {
					def apply(accountsId :PlayApi, customersId :PlayApi, entitlementsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): changeOffer = new changeOffer(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}/entitlements/${entitlementsId}:changeOffer").addQueryStringParameters("name" -> name.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudChannelV1CreateEntitlementRequest(body: Schema.GoogleCloudChannelV1CreateEntitlementRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object create {
					def apply(accountsId :PlayApi, customersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}/entitlements").addQueryStringParameters("parent" -> parent.toString))
				}
				class suspend(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudChannelV1SuspendEntitlementRequest(body: Schema.GoogleCloudChannelV1SuspendEntitlementRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object suspend {
					def apply(accountsId :PlayApi, customersId :PlayApi, entitlementsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): suspend = new suspend(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}/entitlements/${entitlementsId}:suspend").addQueryStringParameters("name" -> name.toString))
				}
				class listEntitlementChanges(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ListEntitlementChangesResponse]) {
					/** Optional. The maximum number of entitlement changes to return. The service may return fewer than this value. If unspecified, returns at most 10 entitlement changes. The maximum value is 50; the server will coerce values above 50.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new listEntitlementChanges(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A page token, received from a previous CloudChannelService.ListEntitlementChanges call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to CloudChannelService.ListEntitlementChanges must match the call that provided the page token. */
					def withPageToken(pageToken: String) = new listEntitlementChanges(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Filters applied to the list results. */
					def withFilter(filter: String) = new listEntitlementChanges(req.addQueryStringParameters("filter" -> filter.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ListEntitlementChangesResponse])
				}
				object listEntitlementChanges {
					def apply(accountsId :PlayApi, customersId :PlayApi, entitlementsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): listEntitlementChanges = new listEntitlementChanges(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}/entitlements/${entitlementsId}:listEntitlementChanges").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[listEntitlementChanges, Future[Schema.GoogleCloudChannelV1ListEntitlementChangesResponse]] = (fun: listEntitlementChanges) => fun.apply()
				}
				class changeParameters(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudChannelV1ChangeParametersRequest(body: Schema.GoogleCloudChannelV1ChangeParametersRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object changeParameters {
					def apply(accountsId :PlayApi, customersId :PlayApi, entitlementsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): changeParameters = new changeParameters(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}/entitlements/${entitlementsId}:changeParameters").addQueryStringParameters("name" -> name.toString))
				}
				class lookupOffer(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1Offer]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1Offer])
				}
				object lookupOffer {
					def apply(accountsId :PlayApi, customersId :PlayApi, entitlementsId :PlayApi, entitlement: String)(using auth: AuthToken, ec: ExecutionContext): lookupOffer = new lookupOffer(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}/entitlements/${entitlementsId}:lookupOffer").addQueryStringParameters("entitlement" -> entitlement.toString))
					given Conversion[lookupOffer, Future[Schema.GoogleCloudChannelV1Offer]] = (fun: lookupOffer) => fun.apply()
				}
				class startPaidService(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudChannelV1StartPaidServiceRequest(body: Schema.GoogleCloudChannelV1StartPaidServiceRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object startPaidService {
					def apply(accountsId :PlayApi, customersId :PlayApi, entitlementsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): startPaidService = new startPaidService(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}/entitlements/${entitlementsId}:startPaidService").addQueryStringParameters("name" -> name.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1Entitlement]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1Entitlement])
				}
				object get {
					def apply(accountsId :PlayApi, customersId :PlayApi, entitlementsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}/entitlements/${entitlementsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudChannelV1Entitlement]] = (fun: get) => fun.apply()
				}
				class activate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudChannelV1ActivateEntitlementRequest(body: Schema.GoogleCloudChannelV1ActivateEntitlementRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object activate {
					def apply(accountsId :PlayApi, customersId :PlayApi, entitlementsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): activate = new activate(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}/entitlements/${entitlementsId}:activate").addQueryStringParameters("name" -> name.toString))
				}
				class changeRenewalSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudChannelV1ChangeRenewalSettingsRequest(body: Schema.GoogleCloudChannelV1ChangeRenewalSettingsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object changeRenewalSettings {
					def apply(accountsId :PlayApi, customersId :PlayApi, entitlementsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): changeRenewalSettings = new changeRenewalSettings(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}/entitlements/${entitlementsId}:changeRenewalSettings").addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ListEntitlementsResponse]) {
					/** Optional. Requested page size. Server might return fewer results than requested. If unspecified, return at most 50 entitlements. The maximum value is 100; the server will coerce values above 100.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A token for a page of results other than the first page. Obtained using ListEntitlementsResponse.next_page_token of the previous CloudChannelService.ListEntitlements call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ListEntitlementsResponse])
				}
				object list {
					def apply(accountsId :PlayApi, customersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}/entitlements").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudChannelV1ListEntitlementsResponse]] = (fun: list) => fun.apply()
				}
			}
			object customerRepricingConfigs {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudChannelV1CustomerRepricingConfig(body: Schema.GoogleCloudChannelV1CustomerRepricingConfig) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudChannelV1CustomerRepricingConfig])
				}
				object create {
					def apply(accountsId :PlayApi, customersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}/customerRepricingConfigs").addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(accountsId :PlayApi, customersId :PlayApi, customerRepricingConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}/customerRepricingConfigs/${customerRepricingConfigsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1CustomerRepricingConfig]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1CustomerRepricingConfig])
				}
				object get {
					def apply(accountsId :PlayApi, customersId :PlayApi, customerRepricingConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}/customerRepricingConfigs/${customerRepricingConfigsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudChannelV1CustomerRepricingConfig]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudChannelV1CustomerRepricingConfig(body: Schema.GoogleCloudChannelV1CustomerRepricingConfig) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudChannelV1CustomerRepricingConfig])
				}
				object patch {
					def apply(accountsId :PlayApi, customersId :PlayApi, customerRepricingConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}/customerRepricingConfigs/${customerRepricingConfigsId}").addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ListCustomerRepricingConfigsResponse]) {
					/** Optional. The maximum number of repricing configs to return. The service may return fewer than this value. If unspecified, returns a maximum of 50 rules. The maximum value is 100; values above 100 will be coerced to 100.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A token identifying a page of results beyond the first page. Obtained through ListCustomerRepricingConfigsResponse.next_page_token of the previous CloudChannelService.ListCustomerRepricingConfigs call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. A filter for [CloudChannelService.ListCustomerRepricingConfigs] results (customer only). You can use this filter when you support a BatchGet-like query. To use the filter, you must set `parent=accounts/{account_id}/customers/-`. Example: customer = accounts/account_id/customers/c1 OR customer = accounts/account_id/customers/c2. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ListCustomerRepricingConfigsResponse])
				}
				object list {
					def apply(accountsId :PlayApi, customersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}/customerRepricingConfigs").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudChannelV1ListCustomerRepricingConfigsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object reports {
			class run(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudChannelV1RunReportJobRequest(body: Schema.GoogleCloudChannelV1RunReportJobRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object run {
				def apply(accountsId :PlayApi, reportsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): run = new run(ws.url(BASE_URL + s"v1/accounts/${accountsId}/reports/${reportsId}:run").addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ListReportsResponse]) {
				/** Optional. Requested page size of the report. The server might return fewer results than requested. If unspecified, returns 20 reports. The maximum value is 100.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A token that specifies a page of results beyond the first page. Obtained through ListReportsResponse.next_page_token of the previous CloudChannelReportsService.ListReports call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The BCP-47 language code, such as "en-US". If specified, the response is localized to the corresponding language code if the original data sources support it. Default is "en-US". */
				def withLanguageCode(languageCode: String) = new list(req.addQueryStringParameters("languageCode" -> languageCode.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ListReportsResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts/${accountsId}/reports").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudChannelV1ListReportsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object products {
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ListProductsResponse]) {
			/** Optional. Requested page size. Server might return fewer results than requested. If unspecified, returns at most 100 Products. The maximum value is 1000; the server will coerce values above 1000.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A token for a page of results other than the first page. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Optional. The BCP-47 language code. For example, "en-US". The response will localize in the corresponding language code, if specified. The default value is "en-US". */
			def withLanguageCode(languageCode: String) = new list(req.addQueryStringParameters("languageCode" -> languageCode.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ListProductsResponse])
		}
		object list {
			def apply(account: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/products").addQueryStringParameters("account" -> account.toString))
			given Conversion[list, Future[Schema.GoogleCloudChannelV1ListProductsResponse]] = (fun: list) => fun.apply()
		}
		object skus {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ListSkusResponse]) {
				/** Optional. Requested page size. Server might return fewer results than requested. If unspecified, returns at most 100 SKUs. The maximum value is 1000; the server will coerce values above 1000.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A token for a page of results other than the first page. Optional. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The BCP-47 language code. For example, "en-US". The response will localize in the corresponding language code, if specified. The default value is "en-US". */
				def withLanguageCode(languageCode: String) = new list(req.addQueryStringParameters("languageCode" -> languageCode.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ListSkusResponse])
			}
			object list {
				def apply(productsId :PlayApi, parent: String, account: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/products/${productsId}/skus").addQueryStringParameters("parent" -> parent.toString, "account" -> account.toString))
				given Conversion[list, Future[Schema.GoogleCloudChannelV1ListSkusResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object integrators {
		class registerSubscriber(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. Resource name of the integrator. Required if account is not provided. Otherwise, leave this field empty/unset. */
			def withIntegrator(integrator: String) = new registerSubscriber(req.addQueryStringParameters("integrator" -> integrator.toString))
			def withGoogleCloudChannelV1RegisterSubscriberRequest(body: Schema.GoogleCloudChannelV1RegisterSubscriberRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudChannelV1RegisterSubscriberResponse])
		}
		object registerSubscriber {
			def apply(integratorsId :PlayApi)(using auth: AuthToken, ec: ExecutionContext): registerSubscriber = new registerSubscriber(ws.url(BASE_URL + s"v1/integrators/${integratorsId}:registerSubscriber").addQueryStringParameters())
		}
		class unregisterSubscriber(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			/** Optional. Resource name of the integrator. Required if account is not provided. Otherwise, leave this field empty/unset. */
			def withIntegrator(integrator: String) = new unregisterSubscriber(req.addQueryStringParameters("integrator" -> integrator.toString))
			def withGoogleCloudChannelV1UnregisterSubscriberRequest(body: Schema.GoogleCloudChannelV1UnregisterSubscriberRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudChannelV1UnregisterSubscriberResponse])
		}
		object unregisterSubscriber {
			def apply(integratorsId :PlayApi)(using auth: AuthToken, ec: ExecutionContext): unregisterSubscriber = new unregisterSubscriber(ws.url(BASE_URL + s"v1/integrators/${integratorsId}:unregisterSubscriber").addQueryStringParameters())
		}
		class listSubscribers(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ListSubscribersResponse]) {
			/** Optional. Resource name of the integrator. Required if account is not provided. Otherwise, leave this field empty/unset. */
			def withIntegrator(integrator: String) = new listSubscribers(req.addQueryStringParameters("integrator" -> integrator.toString))
			/** Optional. Resource name of the account. Required if integrator is not provided. Otherwise, leave this field empty/unset. */
			def withAccount(account: String) = new listSubscribers(req.addQueryStringParameters("account" -> account.toString))
			/** Optional. The maximum number of service accounts to return. The service may return fewer than this value. If unspecified, returns at most 100 service accounts. The maximum value is 1000; the server will coerce values above 1000.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new listSubscribers(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A page token, received from a previous `ListSubscribers` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListSubscribers` must match the call that provided the page token. */
			def withPageToken(pageToken: String) = new listSubscribers(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ListSubscribersResponse])
		}
		object listSubscribers {
			def apply(integratorsId :PlayApi)(using auth: AuthToken, ec: ExecutionContext): listSubscribers = new listSubscribers(ws.url(BASE_URL + s"v1/integrators/${integratorsId}:listSubscribers").addQueryStringParameters())
			given Conversion[listSubscribers, Future[Schema.GoogleCloudChannelV1ListSubscribersResponse]] = (fun: listSubscribers) => fun.apply()
		}
	}
}
