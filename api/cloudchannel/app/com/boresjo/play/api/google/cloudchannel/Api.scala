package com.boresjo.play.api.google.cloudchannel

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
		"""https://www.googleapis.com/auth/apps.reports.usage.readonly""" /* View usage reports for your G Suite domain */
	)

	private val BASE_URL = "https://cloudchannel.googleapis.com/"

	object operations {
		/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
		}
		object list {
			def apply(name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
		}
		/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object get {
			def apply(operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
		}
		/** Deletes a long-running operation. This method indicates that the client is no longer interested in the operation result. It does not cancel the operation. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
		}
		object delete {
			def apply(operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
		}
		/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
			/** Perform the request */
			def withGoogleLongrunningCancelOperationRequest(body: Schema.GoogleLongrunningCancelOperationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
		}
		object cancel {
			def apply(operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
		}
	}
	object accounts {
		/** Registers a service account with subscriber privileges on the Pub/Sub topic for this Channel Services account or integrator. After you create a subscriber, you get the events through SubscriberEvent Possible error codes: &#42; PERMISSION_DENIED: The reseller account making the request and the provided reseller account are different, or the impersonated user is not a super admin. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. &#42; INTERNAL: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. &#42; UNKNOWN: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. Return value: The topic name with the registered service email address. */
		class register(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
			/** Optional. Resource name of the account. Required if integrator is not provided. Otherwise, leave this field empty/unset. */
			def withAccount(account: String) = new register(req.addQueryStringParameters("account" -> account.toString))
			/** Perform the request */
			def withGoogleCloudChannelV1RegisterSubscriberRequest(body: Schema.GoogleCloudChannelV1RegisterSubscriberRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudChannelV1RegisterSubscriberResponse])
		}
		object register {
			def apply(accountsId :PlayApi)(using signer: RequestSigner, ec: ExecutionContext): register = new register(ws.url(BASE_URL + s"v1/accounts/${accountsId}:register").addQueryStringParameters())
		}
		/** List TransferableOffers of a customer based on Cloud Identity ID or Customer Name in the request. Use this method when a reseller gets the entitlement information of an unowned customer. The reseller should provide the customer's Cloud Identity ID or Customer Name. Possible error codes: &#42; PERMISSION_DENIED: &#42; The customer doesn't belong to the reseller and has no auth token. &#42; The customer provided incorrect reseller information when generating auth token. &#42; The reseller account making the request is different from the reseller account in the query. &#42; The reseller is not authorized to transact on this Product. See https://support.google.com/channelservices/answer/9759265 &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. Return value: List of TransferableOffer for the given customer and SKU. */
		class listTransferableOffers(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
			/** Perform the request */
			def withGoogleCloudChannelV1ListTransferableOffersRequest(body: Schema.GoogleCloudChannelV1ListTransferableOffersRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudChannelV1ListTransferableOffersResponse])
		}
		object listTransferableOffers {
			def apply(accountsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): listTransferableOffers = new listTransferableOffers(ws.url(BASE_URL + s"v1/accounts/${accountsId}:listTransferableOffers").addQueryStringParameters("parent" -> parent.toString))
		}
		/** List TransferableSkus of a customer based on the Cloud Identity ID or Customer Name in the request. Use this method to list the entitlements information of an unowned customer. You should provide the customer's Cloud Identity ID or Customer Name. Possible error codes: &#42; PERMISSION_DENIED: &#42; The customer doesn't belong to the reseller and has no auth token. &#42; The supplied auth token is invalid. &#42; The reseller account making the request is different from the reseller account in the query. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. Return value: A list of the customer's TransferableSku. */
		class listTransferableSkus(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
			/** Perform the request */
			def withGoogleCloudChannelV1ListTransferableSkusRequest(body: Schema.GoogleCloudChannelV1ListTransferableSkusRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudChannelV1ListTransferableSkusResponse])
		}
		object listTransferableSkus {
			def apply(accountsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): listTransferableSkus = new listTransferableSkus(ws.url(BASE_URL + s"v1/accounts/${accountsId}:listTransferableSkus").addQueryStringParameters("parent" -> parent.toString))
		}
		/** Unregisters a service account with subscriber privileges on the Pub/Sub topic created for this Channel Services account or integrator. If there are no service accounts left with subscriber privileges, this deletes the topic. You can call ListSubscribers to check for these accounts. Possible error codes: &#42; PERMISSION_DENIED: The reseller account making the request and the provided reseller account are different, or the impersonated user is not a super admin. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. &#42; NOT_FOUND: The topic resource doesn't exist. &#42; INTERNAL: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. &#42; UNKNOWN: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. Return value: The topic name that unregistered the service email address. Returns a success response if the service email address wasn't registered with the topic. */
		class unregister(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
			/** Optional. Resource name of the account. Required if integrator is not provided. Otherwise, leave this field empty/unset. */
			def withAccount(account: String) = new unregister(req.addQueryStringParameters("account" -> account.toString))
			/** Perform the request */
			def withGoogleCloudChannelV1UnregisterSubscriberRequest(body: Schema.GoogleCloudChannelV1UnregisterSubscriberRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudChannelV1UnregisterSubscriberResponse])
		}
		object unregister {
			def apply(accountsId :PlayApi)(using signer: RequestSigner, ec: ExecutionContext): unregister = new unregister(ws.url(BASE_URL + s"v1/accounts/${accountsId}:unregister").addQueryStringParameters())
		}
		/** Confirms the existence of Cloud Identity accounts based on the domain and if the Cloud Identity accounts are owned by the reseller. Possible error codes: &#42; PERMISSION_DENIED: The reseller account making the request is different from the reseller account in the API request. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. &#42; INVALID_VALUE: Invalid domain value in the request. Return value: A list of CloudIdentityCustomerAccount resources for the domain (may be empty) Note: in the v1alpha1 version of the API, a NOT_FOUND error returns if no CloudIdentityCustomerAccount resources match the domain. */
		class checkCloudIdentityAccountsExist(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
			/** Perform the request */
			def withGoogleCloudChannelV1CheckCloudIdentityAccountsExistRequest(body: Schema.GoogleCloudChannelV1CheckCloudIdentityAccountsExistRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudChannelV1CheckCloudIdentityAccountsExistResponse])
		}
		object checkCloudIdentityAccountsExist {
			def apply(accountsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): checkCloudIdentityAccountsExist = new checkCloudIdentityAccountsExist(ws.url(BASE_URL + s"v1/accounts/${accountsId}:checkCloudIdentityAccountsExist").addQueryStringParameters("parent" -> parent.toString))
		}
		/** Lists service accounts with subscriber privileges on the Pub/Sub topic created for this Channel Services account or integrator. Possible error codes: &#42; PERMISSION_DENIED: The reseller account making the request and the provided reseller account are different, or the impersonated user is not a super admin. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. &#42; NOT_FOUND: The topic resource doesn't exist. &#42; INTERNAL: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. &#42; UNKNOWN: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. Return value: A list of service email addresses. */
		class listSubscribers(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ListSubscribersResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
			/** Optional. Resource name of the account. Required if integrator is not provided. Otherwise, leave this field empty/unset. */
			def withAccount(account: String) = new listSubscribers(req.addQueryStringParameters("account" -> account.toString))
			/** Optional. The maximum number of service accounts to return. The service may return fewer than this value. If unspecified, returns at most 100 service accounts. The maximum value is 1000; the server will coerce values above 1000.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new listSubscribers(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A page token, received from a previous `ListSubscribers` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListSubscribers` must match the call that provided the page token. */
			def withPageToken(pageToken: String) = new listSubscribers(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Optional. Resource name of the integrator. Required if account is not provided. Otherwise, leave this field empty/unset. */
			def withIntegrator(integrator: String) = new listSubscribers(req.addQueryStringParameters("integrator" -> integrator.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ListSubscribersResponse])
		}
		object listSubscribers {
			def apply(accountsId :PlayApi)(using signer: RequestSigner, ec: ExecutionContext): listSubscribers = new listSubscribers(ws.url(BASE_URL + s"v1/accounts/${accountsId}:listSubscribers").addQueryStringParameters())
			given Conversion[listSubscribers, Future[Schema.GoogleCloudChannelV1ListSubscribersResponse]] = (fun: listSubscribers) => fun.apply()
		}
		object skuGroups {
			/** Lists the Rebilling supported SKU groups the account is authorized to sell. Reference: https://cloud.google.com/skus/sku-groups Possible Error Codes: &#42; PERMISSION_DENIED: If the account making the request and the account being queried are different, or the account doesn't exist. &#42; INTERNAL: Any non-user error related to technical issues in the backend. In this case, contact Cloud Channel support. Return Value: If successful, the SkuGroup resources. The data for each resource is displayed in the alphabetical order of SKU group display name. The data for each resource is displayed in the ascending order of SkuGroup.display_name If unsuccessful, returns an error. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ListSkuGroupsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
				/** Optional. The maximum number of SKU groups to return. The service may return fewer than this value. If unspecified, returns a maximum of 1000 SKU groups. The maximum value is 1000; values above 1000 will be coerced to 1000.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A token identifying a page of results beyond the first page. Obtained through ListSkuGroupsResponse.next_page_token of the previous CloudChannelService.ListSkuGroups call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ListSkuGroupsResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts/${accountsId}/skuGroups").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudChannelV1ListSkuGroupsResponse]] = (fun: list) => fun.apply()
			}
			object billableSkus {
				/** Lists the Billable SKUs in a given SKU group. Possible error codes: PERMISSION_DENIED: If the account making the request and the account being queried for are different, or the account doesn't exist. INVALID_ARGUMENT: Missing or invalid required parameters in the request. INTERNAL: Any non-user error related to technical issue in the backend. In this case, contact cloud channel support. Return Value: If successful, the BillableSku resources. The data for each resource is displayed in the ascending order of: &#42; BillableSku.service_display_name &#42; BillableSku.sku_display_name If unsuccessful, returns an error. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ListSkuGroupBillableSkusResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
					/** Optional. The maximum number of SKUs to return. The service may return fewer than this value. If unspecified, returns a maximum of 100000 SKUs. The maximum value is 100000; values above 100000 will be coerced to 100000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A token identifying a page of results beyond the first page. Obtained through ListSkuGroupBillableSkusResponse.next_page_token of the previous CloudChannelService.ListSkuGroupBillableSkus call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ListSkuGroupBillableSkusResponse])
				}
				object list {
					def apply(accountsId :PlayApi, skuGroupsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts/${accountsId}/skuGroups/${skuGroupsId}/billableSkus").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudChannelV1ListSkuGroupBillableSkusResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object offers {
			/** Lists the Offers the reseller can sell. Possible error codes: &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ListOffersResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
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
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ListOffersResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts/${accountsId}/offers").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudChannelV1ListOffersResponse]] = (fun: list) => fun.apply()
			}
		}
		object reportJobs {
			/** Retrieves data generated by CloudChannelReportsService.RunReportJob. Deprecated: Please use [Export Channel Services data to BigQuery](https://cloud.google.com/channel/docs/rebilling/export-data-to-bigquery) instead. */
			class fetchReportResults(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/apps.reports.usage.readonly""")
				/** Perform the request */
				def withGoogleCloudChannelV1FetchReportResultsRequest(body: Schema.GoogleCloudChannelV1FetchReportResultsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudChannelV1FetchReportResultsResponse])
			}
			object fetchReportResults {
				def apply(accountsId :PlayApi, reportJobsId :PlayApi, reportJob: String)(using signer: RequestSigner, ec: ExecutionContext): fetchReportResults = new fetchReportResults(ws.url(BASE_URL + s"v1/accounts/${accountsId}/reportJobs/${reportJobsId}:fetchReportResults").addQueryStringParameters("reportJob" -> reportJob.toString))
			}
		}
		object channelPartnerLinks {
			/** Initiates a channel partner link between a distributor and a reseller, or between resellers in an n-tier reseller channel. Invited partners need to follow the invite_link_uri provided in the response to accept. After accepting the invitation, a link is set up between the two parties. You must be a distributor to call this method. Possible error codes: &#42; PERMISSION_DENIED: The reseller account making the request is different from the reseller account in the API request. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. &#42; ALREADY_EXISTS: The ChannelPartnerLink sent in the request already exists. &#42; NOT_FOUND: No Cloud Identity customer exists for provided domain. &#42; INTERNAL: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. &#42; UNKNOWN: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. Return value: The new ChannelPartnerLink resource. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
				/** Perform the request */
				def withGoogleCloudChannelV1ChannelPartnerLink(body: Schema.GoogleCloudChannelV1ChannelPartnerLink) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudChannelV1ChannelPartnerLink])
			}
			object create {
				def apply(accountsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/accounts/${accountsId}/channelPartnerLinks").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Returns the requested ChannelPartnerLink resource. You must be a distributor to call this method. Possible error codes: &#42; PERMISSION_DENIED: The reseller account making the request is different from the reseller account in the API request. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. &#42; NOT_FOUND: ChannelPartnerLink resource not found because of an invalid channel partner link name. Return value: The ChannelPartnerLink resource. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ChannelPartnerLink]) {
				val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
				/** Optional. The level of granularity the ChannelPartnerLink will display.<br>Possible values:<br>UNSPECIFIED: The default / unset value. The API will default to the BASIC view.<br>BASIC: Includes all fields except the ChannelPartnerLink.channel_partner_cloud_identity_info.<br>FULL: Includes all fields. */
				def withView(view: String) = new get(req.addQueryStringParameters("view" -> view.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ChannelPartnerLink])
			}
			object get {
				def apply(accountsId :PlayApi, channelPartnerLinksId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/accounts/${accountsId}/channelPartnerLinks/${channelPartnerLinksId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudChannelV1ChannelPartnerLink]] = (fun: get) => fun.apply()
			}
			/** Updates a channel partner link. Distributors call this method to change a link's status. For example, to suspend a partner link. You must be a distributor to call this method. Possible error codes: &#42; PERMISSION_DENIED: The reseller account making the request is different from the reseller account in the API request. &#42; INVALID_ARGUMENT: &#42; Required request parameters are missing or invalid. &#42; Link state cannot change from invited to active or suspended. &#42; Cannot send reseller_cloud_identity_id, invite_url, or name in update mask. &#42; NOT_FOUND: ChannelPartnerLink resource not found. &#42; INTERNAL: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. &#42; UNKNOWN: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. Return value: The updated ChannelPartnerLink resource. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
				/** Perform the request */
				def withGoogleCloudChannelV1UpdateChannelPartnerLinkRequest(body: Schema.GoogleCloudChannelV1UpdateChannelPartnerLinkRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudChannelV1ChannelPartnerLink])
			}
			object patch {
				def apply(accountsId :PlayApi, channelPartnerLinksId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/accounts/${accountsId}/channelPartnerLinks/${channelPartnerLinksId}").addQueryStringParameters("name" -> name.toString))
			}
			/** List ChannelPartnerLinks belonging to a distributor. You must be a distributor to call this method. Possible error codes: &#42; PERMISSION_DENIED: The reseller account making the request is different from the reseller account in the API request. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. Return value: The list of the distributor account's ChannelPartnerLink resources. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ListChannelPartnerLinksResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
				/** Optional. Requested page size. Server might return fewer results than requested. If unspecified, server will pick a default size (25). The maximum value is 200; the server will coerce values above 200.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A token for a page of results other than the first page. Obtained using ListChannelPartnerLinksResponse.next_page_token of the previous CloudChannelService.ListChannelPartnerLinks call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The level of granularity the ChannelPartnerLink will display.<br>Possible values:<br>UNSPECIFIED: The default / unset value. The API will default to the BASIC view.<br>BASIC: Includes all fields except the ChannelPartnerLink.channel_partner_cloud_identity_info.<br>FULL: Includes all fields. */
				def withView(view: String) = new list(req.addQueryStringParameters("view" -> view.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ListChannelPartnerLinksResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts/${accountsId}/channelPartnerLinks").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudChannelV1ListChannelPartnerLinksResponse]] = (fun: list) => fun.apply()
			}
			object channelPartnerRepricingConfigs {
				/** Creates a ChannelPartnerRepricingConfig. Call this method to set modifications for a specific ChannelPartner's bill. You can only create configs if the RepricingConfig.effective_invoice_month is a future month. If needed, you can create a config for the current month, with some restrictions. When creating a config for a future month, make sure there are no existing configs for that RepricingConfig.effective_invoice_month. The following restrictions are for creating configs in the current month. &#42; This functionality is reserved for recovering from an erroneous config, and should not be used for regular business cases. &#42; The new config will not modify exports used with other configs. Changes to the config may be immediate, but may take up to 24 hours. &#42; There is a limit of ten configs for any ChannelPartner or RepricingConfig.EntitlementGranularity.entitlement, for any RepricingConfig.effective_invoice_month. &#42; The contained ChannelPartnerRepricingConfig.repricing_config value must be different from the value used in the current config for a ChannelPartner. Possible Error Codes: &#42; PERMISSION_DENIED: If the account making the request and the account being queried are different. &#42; INVALID_ARGUMENT: Missing or invalid required parameters in the request. Also displays if the updated config is for the current month or past months. &#42; NOT_FOUND: The ChannelPartnerRepricingConfig specified does not exist or is not associated with the given account. &#42; INTERNAL: Any non-user error related to technical issues in the backend. In this case, contact Cloud Channel support. Return Value: If successful, the updated ChannelPartnerRepricingConfig resource, otherwise returns an error. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
					/** Perform the request */
					def withGoogleCloudChannelV1ChannelPartnerRepricingConfig(body: Schema.GoogleCloudChannelV1ChannelPartnerRepricingConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudChannelV1ChannelPartnerRepricingConfig])
				}
				object create {
					def apply(accountsId :PlayApi, channelPartnerLinksId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/accounts/${accountsId}/channelPartnerLinks/${channelPartnerLinksId}/channelPartnerRepricingConfigs").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes the given ChannelPartnerRepricingConfig permanently. You can only delete configs if their RepricingConfig.effective_invoice_month is set to a date after the current month. Possible error codes: &#42; PERMISSION_DENIED: The account making the request does not own this customer. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. &#42; FAILED_PRECONDITION: The ChannelPartnerRepricingConfig is active or in the past. &#42; NOT_FOUND: No ChannelPartnerRepricingConfig found for the name in the request. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(accountsId :PlayApi, channelPartnerLinksId :PlayApi, channelPartnerRepricingConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/accounts/${accountsId}/channelPartnerLinks/${channelPartnerLinksId}/channelPartnerRepricingConfigs/${channelPartnerRepricingConfigsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Gets information about how a Distributor modifies their bill before sending it to a ChannelPartner. Possible Error Codes: &#42; PERMISSION_DENIED: If the account making the request and the account being queried are different. &#42; NOT_FOUND: The ChannelPartnerRepricingConfig was not found. &#42; INTERNAL: Any non-user error related to technical issues in the backend. In this case, contact Cloud Channel support. Return Value: If successful, the ChannelPartnerRepricingConfig resource, otherwise returns an error. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ChannelPartnerRepricingConfig]) {
					val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ChannelPartnerRepricingConfig])
				}
				object get {
					def apply(accountsId :PlayApi, channelPartnerLinksId :PlayApi, channelPartnerRepricingConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/accounts/${accountsId}/channelPartnerLinks/${channelPartnerLinksId}/channelPartnerRepricingConfigs/${channelPartnerRepricingConfigsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudChannelV1ChannelPartnerRepricingConfig]] = (fun: get) => fun.apply()
				}
				/** Updates a ChannelPartnerRepricingConfig. Call this method to set modifications for a specific ChannelPartner's bill. This method overwrites the existing CustomerRepricingConfig. You can only update configs if the RepricingConfig.effective_invoice_month is a future month. To make changes to configs for the current month, use CreateChannelPartnerRepricingConfig, taking note of its restrictions. You cannot update the RepricingConfig.effective_invoice_month. When updating a config in the future: &#42; This config must already exist. Possible Error Codes: &#42; PERMISSION_DENIED: If the account making the request and the account being queried are different. &#42; INVALID_ARGUMENT: Missing or invalid required parameters in the request. Also displays if the updated config is for the current month or past months. &#42; NOT_FOUND: The ChannelPartnerRepricingConfig specified does not exist or is not associated with the given account. &#42; INTERNAL: Any non-user error related to technical issues in the backend. In this case, contact Cloud Channel support. Return Value: If successful, the updated ChannelPartnerRepricingConfig resource, otherwise returns an error. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
					/** Perform the request */
					def withGoogleCloudChannelV1ChannelPartnerRepricingConfig(body: Schema.GoogleCloudChannelV1ChannelPartnerRepricingConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudChannelV1ChannelPartnerRepricingConfig])
				}
				object patch {
					def apply(accountsId :PlayApi, channelPartnerLinksId :PlayApi, channelPartnerRepricingConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/accounts/${accountsId}/channelPartnerLinks/${channelPartnerLinksId}/channelPartnerRepricingConfigs/${channelPartnerRepricingConfigsId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists information about how a Reseller modifies their bill before sending it to a ChannelPartner. Possible Error Codes: &#42; PERMISSION_DENIED: If the account making the request and the account being queried are different. &#42; NOT_FOUND: The ChannelPartnerRepricingConfig specified does not exist or is not associated with the given account. &#42; INTERNAL: Any non-user error related to technical issues in the backend. In this case, contact Cloud Channel support. Return Value: If successful, the ChannelPartnerRepricingConfig resources. The data for each resource is displayed in the ascending order of: &#42; Channel Partner ID &#42; RepricingConfig.effective_invoice_month &#42; ChannelPartnerRepricingConfig.update_time If unsuccessful, returns an error. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ListChannelPartnerRepricingConfigsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
					/** Optional. The maximum number of repricing configs to return. The service may return fewer than this value. If unspecified, returns a maximum of 50 rules. The maximum value is 100; values above 100 will be coerced to 100.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A token identifying a page of results beyond the first page. Obtained through ListChannelPartnerRepricingConfigsResponse.next_page_token of the previous CloudChannelService.ListChannelPartnerRepricingConfigs call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. A filter for [CloudChannelService.ListChannelPartnerRepricingConfigs] results (channel_partner_link only). You can use this filter when you support a BatchGet-like query. To use the filter, you must set `parent=accounts/{account_id}/channelPartnerLinks/-`. Example: `channel_partner_link = accounts/account_id/channelPartnerLinks/c1` OR `channel_partner_link = accounts/account_id/channelPartnerLinks/c2`. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ListChannelPartnerRepricingConfigsResponse])
				}
				object list {
					def apply(accountsId :PlayApi, channelPartnerLinksId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts/${accountsId}/channelPartnerLinks/${channelPartnerLinksId}/channelPartnerRepricingConfigs").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudChannelV1ListChannelPartnerRepricingConfigsResponse]] = (fun: list) => fun.apply()
				}
			}
			object customers {
				/** Creates a new Customer resource under the reseller or distributor account. Possible error codes: &#42; PERMISSION_DENIED: &#42; The reseller account making the request is different from the reseller account in the API request. &#42; You are not authorized to create a customer. See https://support.google.com/channelservices/answer/9759265 &#42; INVALID_ARGUMENT: &#42; Required request parameters are missing or invalid. &#42; Domain field value doesn't match the primary email domain. Return value: The newly created Customer resource. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
					/** Perform the request */
					def withGoogleCloudChannelV1Customer(body: Schema.GoogleCloudChannelV1Customer) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudChannelV1Customer])
				}
				object create {
					def apply(accountsId :PlayApi, channelPartnerLinksId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/accounts/${accountsId}/channelPartnerLinks/${channelPartnerLinksId}/customers").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Imports a Customer from the Cloud Identity associated with the provided Cloud Identity ID or domain before a TransferEntitlements call. If a linked Customer already exists and overwrite_if_exists is true, it will update that Customer's data. Possible error codes: &#42; PERMISSION_DENIED: &#42; The reseller account making the request is different from the reseller account in the API request. &#42; You are not authorized to import the customer. See https://support.google.com/channelservices/answer/9759265 &#42; NOT_FOUND: Cloud Identity doesn't exist or was deleted. &#42; INVALID_ARGUMENT: Required parameters are missing, or the auth_token is expired or invalid. &#42; ALREADY_EXISTS: A customer already exists and has conflicting critical fields. Requires an overwrite. Return value: The Customer. */
				class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
					/** Perform the request */
					def withGoogleCloudChannelV1ImportCustomerRequest(body: Schema.GoogleCloudChannelV1ImportCustomerRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudChannelV1Customer])
				}
				object `import` {
					def apply(accountsId :PlayApi, channelPartnerLinksId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/accounts/${accountsId}/channelPartnerLinks/${channelPartnerLinksId}/customers:import").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes the given Customer permanently. Possible error codes: &#42; PERMISSION_DENIED: The account making the request does not own this customer. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. &#42; FAILED_PRECONDITION: The customer has existing entitlements. &#42; NOT_FOUND: No Customer resource found for the name in the request. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(accountsId :PlayApi, channelPartnerLinksId :PlayApi, customersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/accounts/${accountsId}/channelPartnerLinks/${channelPartnerLinksId}/customers/${customersId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Returns the requested Customer resource. Possible error codes: &#42; PERMISSION_DENIED: The reseller account making the request is different from the reseller account in the API request. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. &#42; NOT_FOUND: The customer resource doesn't exist. Usually the result of an invalid name parameter. Return value: The Customer resource. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1Customer]) {
					val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1Customer])
				}
				object get {
					def apply(accountsId :PlayApi, channelPartnerLinksId :PlayApi, customersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/accounts/${accountsId}/channelPartnerLinks/${channelPartnerLinksId}/customers/${customersId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudChannelV1Customer]] = (fun: get) => fun.apply()
				}
				/** Updates an existing Customer resource for the reseller or distributor. Possible error codes: &#42; PERMISSION_DENIED: The reseller account making the request is different from the reseller account in the API request. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. &#42; NOT_FOUND: No Customer resource found for the name in the request. Return value: The updated Customer resource. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
					/** The update mask that applies to the resource. Optional.<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					/** Perform the request */
					def withGoogleCloudChannelV1Customer(body: Schema.GoogleCloudChannelV1Customer) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudChannelV1Customer])
				}
				object patch {
					def apply(accountsId :PlayApi, channelPartnerLinksId :PlayApi, customersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/accounts/${accountsId}/channelPartnerLinks/${channelPartnerLinksId}/customers/${customersId}").addQueryStringParameters("name" -> name.toString))
				}
				/** List Customers. Possible error codes: &#42; PERMISSION_DENIED: The reseller account making the request is different from the reseller account in the API request. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. Return value: List of Customers, or an empty list if there are no customers. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ListCustomersResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
					/** Optional. The maximum number of customers to return. The service may return fewer than this value. If unspecified, returns at most 10 customers. The maximum value is 50.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A token identifying a page of results other than the first page. Obtained through ListCustomersResponse.next_page_token of the previous CloudChannelService.ListCustomers call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Filters applied to the [CloudChannelService.ListCustomers] results. See https://cloud.google.com/channel/docs/concepts/google-cloud/filter-customers for more information. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ListCustomersResponse])
				}
				object list {
					def apply(accountsId :PlayApi, channelPartnerLinksId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts/${accountsId}/channelPartnerLinks/${channelPartnerLinksId}/customers").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudChannelV1ListCustomersResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object customers {
			/** Lists the billing accounts that are eligible to purchase particular SKUs for a given customer. Possible error codes: &#42; PERMISSION_DENIED: The customer doesn't belong to the reseller. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. Return value: Based on the provided list of SKUs, returns a list of SKU groups that must be purchased using the same billing account and the billing accounts eligible to purchase each SKU group. */
			class queryEligibleBillingAccounts(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1QueryEligibleBillingAccountsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1QueryEligibleBillingAccountsResponse])
			}
			object queryEligibleBillingAccounts {
				def apply(accountsId :PlayApi, customersId :PlayApi, customer: String, skus: String)(using signer: RequestSigner, ec: ExecutionContext): queryEligibleBillingAccounts = new queryEligibleBillingAccounts(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}:queryEligibleBillingAccounts").addQueryStringParameters("customer" -> customer.toString, "skus" -> skus.toString))
				given Conversion[queryEligibleBillingAccounts, Future[Schema.GoogleCloudChannelV1QueryEligibleBillingAccountsResponse]] = (fun: queryEligibleBillingAccounts) => fun.apply()
			}
			/** Transfers customer entitlements to new reseller. Possible error codes: &#42; PERMISSION_DENIED: &#42; The customer doesn't belong to the reseller. &#42; The reseller is not authorized to transact on this Product. See https://support.google.com/channelservices/answer/9759265 &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. &#42; NOT_FOUND: The customer or offer resource was not found. &#42; ALREADY_EXISTS: The SKU was already transferred for the customer. &#42; CONDITION_NOT_MET or FAILED_PRECONDITION: &#42; The SKU requires domain verification to transfer, but the domain is not verified. &#42; An Add-On SKU (example, Vault or Drive) is missing the pre-requisite SKU (example, G Suite Basic). &#42; (Developer accounts only) Reseller and resold domain must meet the following naming requirements: &#42; Domain names must start with goog-test. &#42; Domain names must include the reseller domain. &#42; Specify all transferring entitlements. &#42; INTERNAL: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. &#42; UNKNOWN: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. Return value: The ID of a long-running operation. To get the results of the operation, call the GetOperation method of CloudChannelOperationsService. The Operation metadata will contain an instance of OperationMetadata. */
			class transferEntitlements(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
				/** Perform the request */
				def withGoogleCloudChannelV1TransferEntitlementsRequest(body: Schema.GoogleCloudChannelV1TransferEntitlementsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object transferEntitlements {
				def apply(accountsId :PlayApi, customersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): transferEntitlements = new transferEntitlements(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}:transferEntitlements").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Lists the following: &#42; SKUs that you can purchase for a customer &#42; SKUs that you can upgrade or downgrade for an entitlement. Possible error codes: &#42; PERMISSION_DENIED: The customer doesn't belong to the reseller. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. */
			class listPurchasableSkus(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ListPurchasableSkusResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
				/** Optional. Requested page size. Server might return fewer results than requested. If unspecified, returns at most 100 SKUs. The maximum value is 1000; the server will coerce values above 1000.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new listPurchasableSkus(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A token for a page of results other than the first page. */
				def withPageToken(pageToken: String) = new listPurchasableSkus(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The BCP-47 language code. For example, "en-US". The response will localize in the corresponding language code, if specified. The default value is "en-US". */
				def withLanguageCode(languageCode: String) = new listPurchasableSkus(req.addQueryStringParameters("languageCode" -> languageCode.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ListPurchasableSkusResponse])
			}
			object listPurchasableSkus {
				def apply(accountsId :PlayApi, customersId :PlayApi, customer: String, createEntitlementPurchaseProduct: String, changeOfferPurchaseEntitlement: String, changeOfferPurchaseChangeType: String)(using signer: RequestSigner, ec: ExecutionContext): listPurchasableSkus = new listPurchasableSkus(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}:listPurchasableSkus").addQueryStringParameters("customer" -> customer.toString, "createEntitlementPurchase.product" -> createEntitlementPurchaseProduct.toString, "changeOfferPurchase.entitlement" -> changeOfferPurchaseEntitlement.toString, "changeOfferPurchase.changeType" -> changeOfferPurchaseChangeType.toString))
				given Conversion[listPurchasableSkus, Future[Schema.GoogleCloudChannelV1ListPurchasableSkusResponse]] = (fun: listPurchasableSkus) => fun.apply()
			}
			/** Creates a new Customer resource under the reseller or distributor account. Possible error codes: &#42; PERMISSION_DENIED: &#42; The reseller account making the request is different from the reseller account in the API request. &#42; You are not authorized to create a customer. See https://support.google.com/channelservices/answer/9759265 &#42; INVALID_ARGUMENT: &#42; Required request parameters are missing or invalid. &#42; Domain field value doesn't match the primary email domain. Return value: The newly created Customer resource. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
				/** Perform the request */
				def withGoogleCloudChannelV1Customer(body: Schema.GoogleCloudChannelV1Customer) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudChannelV1Customer])
			}
			object create {
				def apply(accountsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Imports a Customer from the Cloud Identity associated with the provided Cloud Identity ID or domain before a TransferEntitlements call. If a linked Customer already exists and overwrite_if_exists is true, it will update that Customer's data. Possible error codes: &#42; PERMISSION_DENIED: &#42; The reseller account making the request is different from the reseller account in the API request. &#42; You are not authorized to import the customer. See https://support.google.com/channelservices/answer/9759265 &#42; NOT_FOUND: Cloud Identity doesn't exist or was deleted. &#42; INVALID_ARGUMENT: Required parameters are missing, or the auth_token is expired or invalid. &#42; ALREADY_EXISTS: A customer already exists and has conflicting critical fields. Requires an overwrite. Return value: The Customer. */
			class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
				/** Perform the request */
				def withGoogleCloudChannelV1ImportCustomerRequest(body: Schema.GoogleCloudChannelV1ImportCustomerRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudChannelV1Customer])
			}
			object `import` {
				def apply(accountsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers:import").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Returns the requested Customer resource. Possible error codes: &#42; PERMISSION_DENIED: The reseller account making the request is different from the reseller account in the API request. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. &#42; NOT_FOUND: The customer resource doesn't exist. Usually the result of an invalid name parameter. Return value: The Customer resource. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1Customer]) {
				val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1Customer])
			}
			object get {
				def apply(accountsId :PlayApi, customersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudChannelV1Customer]] = (fun: get) => fun.apply()
			}
			/** Lists the following: &#42; Offers that you can purchase for a customer. &#42; Offers that you can change for an entitlement. Possible error codes: &#42; PERMISSION_DENIED: &#42; The customer doesn't belong to the reseller &#42; The reseller is not authorized to transact on this Product. See https://support.google.com/channelservices/answer/9759265 &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. */
			class listPurchasableOffers(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ListPurchasableOffersResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
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
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ListPurchasableOffersResponse])
			}
			object listPurchasableOffers {
				def apply(accountsId :PlayApi, customersId :PlayApi, customer: String, createEntitlementPurchaseSku: String, changeOfferPurchaseEntitlement: String)(using signer: RequestSigner, ec: ExecutionContext): listPurchasableOffers = new listPurchasableOffers(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}:listPurchasableOffers").addQueryStringParameters("customer" -> customer.toString, "createEntitlementPurchase.sku" -> createEntitlementPurchaseSku.toString, "changeOfferPurchase.entitlement" -> changeOfferPurchaseEntitlement.toString))
				given Conversion[listPurchasableOffers, Future[Schema.GoogleCloudChannelV1ListPurchasableOffersResponse]] = (fun: listPurchasableOffers) => fun.apply()
			}
			/** Deletes the given Customer permanently. Possible error codes: &#42; PERMISSION_DENIED: The account making the request does not own this customer. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. &#42; FAILED_PRECONDITION: The customer has existing entitlements. &#42; NOT_FOUND: No Customer resource found for the name in the request. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(accountsId :PlayApi, customersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			/** Updates an existing Customer resource for the reseller or distributor. Possible error codes: &#42; PERMISSION_DENIED: The reseller account making the request is different from the reseller account in the API request. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. &#42; NOT_FOUND: No Customer resource found for the name in the request. Return value: The updated Customer resource. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
				/** The update mask that applies to the resource. Optional.<br>Format: google-fieldmask */
				def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
				/** Perform the request */
				def withGoogleCloudChannelV1Customer(body: Schema.GoogleCloudChannelV1Customer) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudChannelV1Customer])
			}
			object patch {
				def apply(accountsId :PlayApi, customersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}").addQueryStringParameters("name" -> name.toString))
			}
			/** Transfers customer entitlements from their current reseller to Google. Possible error codes: &#42; PERMISSION_DENIED: The customer doesn't belong to the reseller. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. &#42; NOT_FOUND: The customer or offer resource was not found. &#42; ALREADY_EXISTS: The SKU was already transferred for the customer. &#42; CONDITION_NOT_MET or FAILED_PRECONDITION: &#42; The SKU requires domain verification to transfer, but the domain is not verified. &#42; An Add-On SKU (example, Vault or Drive) is missing the pre-requisite SKU (example, G Suite Basic). &#42; (Developer accounts only) Reseller and resold domain must meet the following naming requirements: &#42; Domain names must start with goog-test. &#42; Domain names must include the reseller domain. &#42; INTERNAL: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. &#42; UNKNOWN: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. Return value: The ID of a long-running operation. To get the results of the operation, call the GetOperation method of CloudChannelOperationsService. The response will contain google.protobuf.Empty on success. The Operation metadata will contain an instance of OperationMetadata. */
			class transferEntitlementsToGoogle(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
				/** Perform the request */
				def withGoogleCloudChannelV1TransferEntitlementsToGoogleRequest(body: Schema.GoogleCloudChannelV1TransferEntitlementsToGoogleRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object transferEntitlementsToGoogle {
				def apply(accountsId :PlayApi, customersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): transferEntitlementsToGoogle = new transferEntitlementsToGoogle(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}:transferEntitlementsToGoogle").addQueryStringParameters("parent" -> parent.toString))
			}
			/** List Customers. Possible error codes: &#42; PERMISSION_DENIED: The reseller account making the request is different from the reseller account in the API request. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. Return value: List of Customers, or an empty list if there are no customers. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ListCustomersResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
				/** Optional. The maximum number of customers to return. The service may return fewer than this value. If unspecified, returns at most 10 customers. The maximum value is 50.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A token identifying a page of results other than the first page. Obtained through ListCustomersResponse.next_page_token of the previous CloudChannelService.ListCustomers call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. Filters applied to the [CloudChannelService.ListCustomers] results. See https://cloud.google.com/channel/docs/concepts/google-cloud/filter-customers for more information. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ListCustomersResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudChannelV1ListCustomersResponse]] = (fun: list) => fun.apply()
			}
			/** Creates a Cloud Identity for the given customer using the customer's information, or the information provided here. Possible error codes: &#42; PERMISSION_DENIED: &#42; The customer doesn't belong to the reseller. &#42; You are not authorized to provision cloud identity id. See https://support.google.com/channelservices/answer/9759265 &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. &#42; NOT_FOUND: The customer was not found. &#42; ALREADY_EXISTS: The customer's primary email already exists. Retry after changing the customer's primary contact email. &#42; INTERNAL: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. &#42; UNKNOWN: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. Return value: The ID of a long-running operation. To get the results of the operation, call the GetOperation method of CloudChannelOperationsService. The Operation metadata contains an instance of OperationMetadata. */
			class provisionCloudIdentity(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
				/** Perform the request */
				def withGoogleCloudChannelV1ProvisionCloudIdentityRequest(body: Schema.GoogleCloudChannelV1ProvisionCloudIdentityRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object provisionCloudIdentity {
				def apply(accountsId :PlayApi, customersId :PlayApi, customer: String)(using signer: RequestSigner, ec: ExecutionContext): provisionCloudIdentity = new provisionCloudIdentity(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}:provisionCloudIdentity").addQueryStringParameters("customer" -> customer.toString))
			}
			object entitlements {
				/** Cancels a previously fulfilled entitlement. An entitlement cancellation is a long-running operation. Possible error codes: &#42; PERMISSION_DENIED: The reseller account making the request is different from the reseller account in the API request. &#42; FAILED_PRECONDITION: There are Google Cloud projects linked to the Google Cloud entitlement's Cloud Billing subaccount. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. &#42; NOT_FOUND: Entitlement resource not found. &#42; DELETION_TYPE_NOT_ALLOWED: Cancel is only allowed for Google Workspace add-ons, or entitlements for Google Cloud's development platform. &#42; INTERNAL: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. &#42; UNKNOWN: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. Return value: The ID of a long-running operation. To get the results of the operation, call the GetOperation method of CloudChannelOperationsService. The response will contain google.protobuf.Empty on success. The Operation metadata will contain an instance of OperationMetadata. */
				class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
					/** Perform the request */
					def withGoogleCloudChannelV1CancelEntitlementRequest(body: Schema.GoogleCloudChannelV1CancelEntitlementRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object cancel {
					def apply(accountsId :PlayApi, customersId :PlayApi, entitlementsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}/entitlements/${entitlementsId}:cancel").addQueryStringParameters("name" -> name.toString))
				}
				/** Updates the Offer for an existing customer entitlement. An entitlement update is a long-running operation and it updates the entitlement as a result of fulfillment. Possible error codes: &#42; PERMISSION_DENIED: The customer doesn't belong to the reseller. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. &#42; NOT_FOUND: Offer or Entitlement resource not found. &#42; INTERNAL: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. &#42; UNKNOWN: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. Return value: The ID of a long-running operation. To get the results of the operation, call the GetOperation method of CloudChannelOperationsService. The Operation metadata will contain an instance of OperationMetadata. */
				class changeOffer(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
					/** Perform the request */
					def withGoogleCloudChannelV1ChangeOfferRequest(body: Schema.GoogleCloudChannelV1ChangeOfferRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object changeOffer {
					def apply(accountsId :PlayApi, customersId :PlayApi, entitlementsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): changeOffer = new changeOffer(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}/entitlements/${entitlementsId}:changeOffer").addQueryStringParameters("name" -> name.toString))
				}
				/** Creates an entitlement for a customer. Possible error codes: &#42; PERMISSION_DENIED: &#42; The customer doesn't belong to the reseller. &#42; The reseller is not authorized to transact on this Product. See https://support.google.com/channelservices/answer/9759265 &#42; INVALID_ARGUMENT: &#42; Required request parameters are missing or invalid. &#42; There is already a customer entitlement for a SKU from the same product family. &#42; INVALID_VALUE: Make sure the OfferId is valid. If it is, contact Google Channel support for further troubleshooting. &#42; NOT_FOUND: The customer or offer resource was not found. &#42; ALREADY_EXISTS: &#42; The SKU was already purchased for the customer. &#42; The customer's primary email already exists. Retry after changing the customer's primary contact email. &#42; CONDITION_NOT_MET or FAILED_PRECONDITION: &#42; The domain required for purchasing a SKU has not been verified. &#42; A pre-requisite SKU required to purchase an Add-On SKU is missing. For example, Google Workspace Business Starter is required to purchase Vault or Drive. &#42; (Developer accounts only) Reseller and resold domain must meet the following naming requirements: &#42; Domain names must start with goog-test. &#42; Domain names must include the reseller domain. &#42; INTERNAL: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. &#42; UNKNOWN: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. Return value: The ID of a long-running operation. To get the results of the operation, call the GetOperation method of CloudChannelOperationsService. The Operation metadata will contain an instance of OperationMetadata. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
					/** Perform the request */
					def withGoogleCloudChannelV1CreateEntitlementRequest(body: Schema.GoogleCloudChannelV1CreateEntitlementRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object create {
					def apply(accountsId :PlayApi, customersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}/entitlements").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Suspends a previously fulfilled entitlement. An entitlement suspension is a long-running operation. Possible error codes: &#42; PERMISSION_DENIED: The customer doesn't belong to the reseller. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. &#42; NOT_FOUND: Entitlement resource not found. &#42; NOT_ACTIVE: Entitlement is not active. &#42; INTERNAL: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. &#42; UNKNOWN: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. Return value: The ID of a long-running operation. To get the results of the operation, call the GetOperation method of CloudChannelOperationsService. The Operation metadata will contain an instance of OperationMetadata. */
				class suspend(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
					/** Perform the request */
					def withGoogleCloudChannelV1SuspendEntitlementRequest(body: Schema.GoogleCloudChannelV1SuspendEntitlementRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object suspend {
					def apply(accountsId :PlayApi, customersId :PlayApi, entitlementsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): suspend = new suspend(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}/entitlements/${entitlementsId}:suspend").addQueryStringParameters("name" -> name.toString))
				}
				/** List entitlement history. Possible error codes: &#42; PERMISSION_DENIED: The reseller account making the request and the provided reseller account are different. &#42; INVALID_ARGUMENT: Missing or invalid required fields in the request. &#42; NOT_FOUND: The parent resource doesn't exist. Usually the result of an invalid name parameter. &#42; INTERNAL: Any non-user error related to a technical issue in the backend. In this case, contact CloudChannel support. &#42; UNKNOWN: Any non-user error related to a technical issue in the backend. In this case, contact Cloud Channel support. Return value: List of EntitlementChanges. */
				class listEntitlementChanges(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ListEntitlementChangesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
					/** Optional. The maximum number of entitlement changes to return. The service may return fewer than this value. If unspecified, returns at most 10 entitlement changes. The maximum value is 50; the server will coerce values above 50.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new listEntitlementChanges(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A page token, received from a previous CloudChannelService.ListEntitlementChanges call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to CloudChannelService.ListEntitlementChanges must match the call that provided the page token. */
					def withPageToken(pageToken: String) = new listEntitlementChanges(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. Filters applied to the list results. */
					def withFilter(filter: String) = new listEntitlementChanges(req.addQueryStringParameters("filter" -> filter.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ListEntitlementChangesResponse])
				}
				object listEntitlementChanges {
					def apply(accountsId :PlayApi, customersId :PlayApi, entitlementsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): listEntitlementChanges = new listEntitlementChanges(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}/entitlements/${entitlementsId}:listEntitlementChanges").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[listEntitlementChanges, Future[Schema.GoogleCloudChannelV1ListEntitlementChangesResponse]] = (fun: listEntitlementChanges) => fun.apply()
				}
				/** Change parameters of the entitlement. An entitlement update is a long-running operation and it updates the entitlement as a result of fulfillment. Possible error codes: &#42; PERMISSION_DENIED: The customer doesn't belong to the reseller. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. For example, the number of seats being changed is greater than the allowed number of max seats, or decreasing seats for a commitment based plan. &#42; NOT_FOUND: Entitlement resource not found. &#42; INTERNAL: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. &#42; UNKNOWN: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. Return value: The ID of a long-running operation. To get the results of the operation, call the GetOperation method of CloudChannelOperationsService. The Operation metadata will contain an instance of OperationMetadata. */
				class changeParameters(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
					/** Perform the request */
					def withGoogleCloudChannelV1ChangeParametersRequest(body: Schema.GoogleCloudChannelV1ChangeParametersRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object changeParameters {
					def apply(accountsId :PlayApi, customersId :PlayApi, entitlementsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): changeParameters = new changeParameters(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}/entitlements/${entitlementsId}:changeParameters").addQueryStringParameters("name" -> name.toString))
				}
				/** Returns the requested Offer resource. Possible error codes: &#42; PERMISSION_DENIED: The entitlement doesn't belong to the reseller. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. &#42; NOT_FOUND: Entitlement or offer was not found. Return value: The Offer resource. */
				class lookupOffer(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1Offer]) {
					val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1Offer])
				}
				object lookupOffer {
					def apply(accountsId :PlayApi, customersId :PlayApi, entitlementsId :PlayApi, entitlement: String)(using signer: RequestSigner, ec: ExecutionContext): lookupOffer = new lookupOffer(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}/entitlements/${entitlementsId}:lookupOffer").addQueryStringParameters("entitlement" -> entitlement.toString))
					given Conversion[lookupOffer, Future[Schema.GoogleCloudChannelV1Offer]] = (fun: lookupOffer) => fun.apply()
				}
				/** Starts paid service for a trial entitlement. Starts paid service for a trial entitlement immediately. This method is only applicable if a plan is set up for a trial entitlement but has some trial days remaining. Possible error codes: &#42; PERMISSION_DENIED: The customer doesn't belong to the reseller. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. &#42; NOT_FOUND: Entitlement resource not found. &#42; FAILED_PRECONDITION/NOT_IN_TRIAL: This method only works for entitlement on trial plans. &#42; INTERNAL: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. &#42; UNKNOWN: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. Return value: The ID of a long-running operation. To get the results of the operation, call the GetOperation method of CloudChannelOperationsService. The Operation metadata will contain an instance of OperationMetadata. */
				class startPaidService(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
					/** Perform the request */
					def withGoogleCloudChannelV1StartPaidServiceRequest(body: Schema.GoogleCloudChannelV1StartPaidServiceRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object startPaidService {
					def apply(accountsId :PlayApi, customersId :PlayApi, entitlementsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): startPaidService = new startPaidService(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}/entitlements/${entitlementsId}:startPaidService").addQueryStringParameters("name" -> name.toString))
				}
				/** Returns the requested Entitlement resource. Possible error codes: &#42; PERMISSION_DENIED: The customer doesn't belong to the reseller. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. &#42; NOT_FOUND: The customer entitlement was not found. Return value: The requested Entitlement resource. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1Entitlement]) {
					val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1Entitlement])
				}
				object get {
					def apply(accountsId :PlayApi, customersId :PlayApi, entitlementsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}/entitlements/${entitlementsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudChannelV1Entitlement]] = (fun: get) => fun.apply()
				}
				/** Activates a previously suspended entitlement. Entitlements suspended for pending ToS acceptance can't be activated using this method. An entitlement activation is a long-running operation and it updates the state of the customer entitlement. Possible error codes: &#42; PERMISSION_DENIED: The reseller account making the request is different from the reseller account in the API request. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. &#42; NOT_FOUND: Entitlement resource not found. &#42; SUSPENSION_NOT_RESELLER_INITIATED: Can only activate reseller-initiated suspensions and entitlements that have accepted the TOS. &#42; NOT_SUSPENDED: Can only activate suspended entitlements not in an ACTIVE state. &#42; INTERNAL: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. &#42; UNKNOWN: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. Return value: The ID of a long-running operation. To get the results of the operation, call the GetOperation method of CloudChannelOperationsService. The Operation metadata will contain an instance of OperationMetadata. */
				class activate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
					/** Perform the request */
					def withGoogleCloudChannelV1ActivateEntitlementRequest(body: Schema.GoogleCloudChannelV1ActivateEntitlementRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object activate {
					def apply(accountsId :PlayApi, customersId :PlayApi, entitlementsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): activate = new activate(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}/entitlements/${entitlementsId}:activate").addQueryStringParameters("name" -> name.toString))
				}
				/** Updates the renewal settings for an existing customer entitlement. An entitlement update is a long-running operation and it updates the entitlement as a result of fulfillment. Possible error codes: &#42; PERMISSION_DENIED: The customer doesn't belong to the reseller. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. &#42; NOT_FOUND: Entitlement resource not found. &#42; NOT_COMMITMENT_PLAN: Renewal Settings are only applicable for a commitment plan. Can't enable or disable renewals for non-commitment plans. &#42; INTERNAL: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. &#42; UNKNOWN: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. Return value: The ID of a long-running operation. To get the results of the operation, call the GetOperation method of CloudChannelOperationsService. The Operation metadata will contain an instance of OperationMetadata. */
				class changeRenewalSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
					/** Perform the request */
					def withGoogleCloudChannelV1ChangeRenewalSettingsRequest(body: Schema.GoogleCloudChannelV1ChangeRenewalSettingsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object changeRenewalSettings {
					def apply(accountsId :PlayApi, customersId :PlayApi, entitlementsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): changeRenewalSettings = new changeRenewalSettings(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}/entitlements/${entitlementsId}:changeRenewalSettings").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists Entitlements belonging to a customer. Possible error codes: &#42; PERMISSION_DENIED: The customer doesn't belong to the reseller. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. Return value: A list of the customer's Entitlements. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ListEntitlementsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
					/** Optional. Requested page size. Server might return fewer results than requested. If unspecified, return at most 50 entitlements. The maximum value is 100; the server will coerce values above 100.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A token for a page of results other than the first page. Obtained using ListEntitlementsResponse.next_page_token of the previous CloudChannelService.ListEntitlements call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ListEntitlementsResponse])
				}
				object list {
					def apply(accountsId :PlayApi, customersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}/entitlements").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudChannelV1ListEntitlementsResponse]] = (fun: list) => fun.apply()
				}
			}
			object customerRepricingConfigs {
				/** Creates a CustomerRepricingConfig. Call this method to set modifications for a specific customer's bill. You can only create configs if the RepricingConfig.effective_invoice_month is a future month. If needed, you can create a config for the current month, with some restrictions. When creating a config for a future month, make sure there are no existing configs for that RepricingConfig.effective_invoice_month. The following restrictions are for creating configs in the current month. &#42; This functionality is reserved for recovering from an erroneous config, and should not be used for regular business cases. &#42; The new config will not modify exports used with other configs. Changes to the config may be immediate, but may take up to 24 hours. &#42; There is a limit of ten configs for any RepricingConfig.EntitlementGranularity.entitlement, for any RepricingConfig.effective_invoice_month. &#42; The contained CustomerRepricingConfig.repricing_config value must be different from the value used in the current config for a RepricingConfig.EntitlementGranularity.entitlement. Possible Error Codes: &#42; PERMISSION_DENIED: If the account making the request and the account being queried are different. &#42; INVALID_ARGUMENT: Missing or invalid required parameters in the request. Also displays if the updated config is for the current month or past months. &#42; NOT_FOUND: The CustomerRepricingConfig specified does not exist or is not associated with the given account. &#42; INTERNAL: Any non-user error related to technical issues in the backend. In this case, contact Cloud Channel support. Return Value: If successful, the updated CustomerRepricingConfig resource, otherwise returns an error. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
					/** Perform the request */
					def withGoogleCloudChannelV1CustomerRepricingConfig(body: Schema.GoogleCloudChannelV1CustomerRepricingConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudChannelV1CustomerRepricingConfig])
				}
				object create {
					def apply(accountsId :PlayApi, customersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}/customerRepricingConfigs").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes the given CustomerRepricingConfig permanently. You can only delete configs if their RepricingConfig.effective_invoice_month is set to a date after the current month. Possible error codes: &#42; PERMISSION_DENIED: The account making the request does not own this customer. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. &#42; FAILED_PRECONDITION: The CustomerRepricingConfig is active or in the past. &#42; NOT_FOUND: No CustomerRepricingConfig found for the name in the request. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(accountsId :PlayApi, customersId :PlayApi, customerRepricingConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}/customerRepricingConfigs/${customerRepricingConfigsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Gets information about how a Reseller modifies their bill before sending it to a Customer. Possible Error Codes: &#42; PERMISSION_DENIED: If the account making the request and the account being queried are different. &#42; NOT_FOUND: The CustomerRepricingConfig was not found. &#42; INTERNAL: Any non-user error related to technical issues in the backend. In this case, contact Cloud Channel support. Return Value: If successful, the CustomerRepricingConfig resource, otherwise returns an error. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1CustomerRepricingConfig]) {
					val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1CustomerRepricingConfig])
				}
				object get {
					def apply(accountsId :PlayApi, customersId :PlayApi, customerRepricingConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}/customerRepricingConfigs/${customerRepricingConfigsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudChannelV1CustomerRepricingConfig]] = (fun: get) => fun.apply()
				}
				/** Updates a CustomerRepricingConfig. Call this method to set modifications for a specific customer's bill. This method overwrites the existing CustomerRepricingConfig. You can only update configs if the RepricingConfig.effective_invoice_month is a future month. To make changes to configs for the current month, use CreateCustomerRepricingConfig, taking note of its restrictions. You cannot update the RepricingConfig.effective_invoice_month. When updating a config in the future: &#42; This config must already exist. Possible Error Codes: &#42; PERMISSION_DENIED: If the account making the request and the account being queried are different. &#42; INVALID_ARGUMENT: Missing or invalid required parameters in the request. Also displays if the updated config is for the current month or past months. &#42; NOT_FOUND: The CustomerRepricingConfig specified does not exist or is not associated with the given account. &#42; INTERNAL: Any non-user error related to technical issues in the backend. In this case, contact Cloud Channel support. Return Value: If successful, the updated CustomerRepricingConfig resource, otherwise returns an error. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
					/** Perform the request */
					def withGoogleCloudChannelV1CustomerRepricingConfig(body: Schema.GoogleCloudChannelV1CustomerRepricingConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudChannelV1CustomerRepricingConfig])
				}
				object patch {
					def apply(accountsId :PlayApi, customersId :PlayApi, customerRepricingConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}/customerRepricingConfigs/${customerRepricingConfigsId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists information about how a Reseller modifies their bill before sending it to a Customer. Possible Error Codes: &#42; PERMISSION_DENIED: If the account making the request and the account being queried are different. &#42; NOT_FOUND: The CustomerRepricingConfig specified does not exist or is not associated with the given account. &#42; INTERNAL: Any non-user error related to technical issues in the backend. In this case, contact Cloud Channel support. Return Value: If successful, the CustomerRepricingConfig resources. The data for each resource is displayed in the ascending order of: &#42; Customer ID &#42; RepricingConfig.EntitlementGranularity.entitlement &#42; RepricingConfig.effective_invoice_month &#42; CustomerRepricingConfig.update_time If unsuccessful, returns an error. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ListCustomerRepricingConfigsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
					/** Optional. The maximum number of repricing configs to return. The service may return fewer than this value. If unspecified, returns a maximum of 50 rules. The maximum value is 100; values above 100 will be coerced to 100.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A token identifying a page of results beyond the first page. Obtained through ListCustomerRepricingConfigsResponse.next_page_token of the previous CloudChannelService.ListCustomerRepricingConfigs call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. A filter for [CloudChannelService.ListCustomerRepricingConfigs] results (customer only). You can use this filter when you support a BatchGet-like query. To use the filter, you must set `parent=accounts/{account_id}/customers/-`. Example: customer = accounts/account_id/customers/c1 OR customer = accounts/account_id/customers/c2. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ListCustomerRepricingConfigsResponse])
				}
				object list {
					def apply(accountsId :PlayApi, customersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts/${accountsId}/customers/${customersId}/customerRepricingConfigs").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudChannelV1ListCustomerRepricingConfigsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object reports {
			/** Begins generation of data for a given report. The report identifier is a UID (for example, `613bf59q`). Possible error codes: &#42; PERMISSION_DENIED: The user doesn't have access to this report. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. &#42; NOT_FOUND: The report identifier was not found. &#42; INTERNAL: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. &#42; UNKNOWN: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. Return value: The ID of a long-running operation. To get the results of the operation, call the GetOperation method of CloudChannelOperationsService. The Operation metadata contains an instance of OperationMetadata. To get the results of report generation, call CloudChannelReportsService.FetchReportResults with the RunReportJobResponse.report_job. Deprecated: Please use [Export Channel Services data to BigQuery](https://cloud.google.com/channel/docs/rebilling/export-data-to-bigquery) instead. */
			class run(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/apps.reports.usage.readonly""")
				/** Perform the request */
				def withGoogleCloudChannelV1RunReportJobRequest(body: Schema.GoogleCloudChannelV1RunReportJobRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object run {
				def apply(accountsId :PlayApi, reportsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): run = new run(ws.url(BASE_URL + s"v1/accounts/${accountsId}/reports/${reportsId}:run").addQueryStringParameters("name" -> name.toString))
			}
			/** Lists the reports that RunReportJob can run. These reports include an ID, a description, and the list of columns that will be in the result. Deprecated: Please use [Export Channel Services data to BigQuery](https://cloud.google.com/channel/docs/rebilling/export-data-to-bigquery) instead. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ListReportsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/apps.reports.usage.readonly""")
				/** Optional. Requested page size of the report. The server might return fewer results than requested. If unspecified, returns 20 reports. The maximum value is 100.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A token that specifies a page of results beyond the first page. Obtained through ListReportsResponse.next_page_token of the previous CloudChannelReportsService.ListReports call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The BCP-47 language code, such as "en-US". If specified, the response is localized to the corresponding language code if the original data sources support it. Default is "en-US". */
				def withLanguageCode(languageCode: String) = new list(req.addQueryStringParameters("languageCode" -> languageCode.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ListReportsResponse])
			}
			object list {
				def apply(accountsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/accounts/${accountsId}/reports").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudChannelV1ListReportsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object products {
		/** Lists the Products the reseller is authorized to sell. Possible error codes: &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ListProductsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
			/** Optional. Requested page size. Server might return fewer results than requested. If unspecified, returns at most 100 Products. The maximum value is 1000; the server will coerce values above 1000.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A token for a page of results other than the first page. */
			def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Optional. The BCP-47 language code. For example, "en-US". The response will localize in the corresponding language code, if specified. The default value is "en-US". */
			def withLanguageCode(languageCode: String) = new list(req.addQueryStringParameters("languageCode" -> languageCode.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ListProductsResponse])
		}
		object list {
			def apply(account: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/products").addQueryStringParameters("account" -> account.toString))
			given Conversion[list, Future[Schema.GoogleCloudChannelV1ListProductsResponse]] = (fun: list) => fun.apply()
		}
		object skus {
			/** Lists the SKUs for a product the reseller is authorized to sell. Possible error codes: &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ListSkusResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
				/** Optional. Requested page size. Server might return fewer results than requested. If unspecified, returns at most 100 SKUs. The maximum value is 1000; the server will coerce values above 1000.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. A token for a page of results other than the first page. Optional. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The BCP-47 language code. For example, "en-US". The response will localize in the corresponding language code, if specified. The default value is "en-US". */
				def withLanguageCode(languageCode: String) = new list(req.addQueryStringParameters("languageCode" -> languageCode.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ListSkusResponse])
			}
			object list {
				def apply(productsId :PlayApi, parent: String, account: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/products/${productsId}/skus").addQueryStringParameters("parent" -> parent.toString, "account" -> account.toString))
				given Conversion[list, Future[Schema.GoogleCloudChannelV1ListSkusResponse]] = (fun: list) => fun.apply()
			}
		}
	}
	object integrators {
		/** Registers a service account with subscriber privileges on the Pub/Sub topic for this Channel Services account or integrator. After you create a subscriber, you get the events through SubscriberEvent Possible error codes: &#42; PERMISSION_DENIED: The reseller account making the request and the provided reseller account are different, or the impersonated user is not a super admin. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. &#42; INTERNAL: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. &#42; UNKNOWN: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. Return value: The topic name with the registered service email address. */
		class registerSubscriber(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
			/** Optional. Resource name of the integrator. Required if account is not provided. Otherwise, leave this field empty/unset. */
			def withIntegrator(integrator: String) = new registerSubscriber(req.addQueryStringParameters("integrator" -> integrator.toString))
			/** Perform the request */
			def withGoogleCloudChannelV1RegisterSubscriberRequest(body: Schema.GoogleCloudChannelV1RegisterSubscriberRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudChannelV1RegisterSubscriberResponse])
		}
		object registerSubscriber {
			def apply(integratorsId :PlayApi)(using signer: RequestSigner, ec: ExecutionContext): registerSubscriber = new registerSubscriber(ws.url(BASE_URL + s"v1/integrators/${integratorsId}:registerSubscriber").addQueryStringParameters())
		}
		/** Unregisters a service account with subscriber privileges on the Pub/Sub topic created for this Channel Services account or integrator. If there are no service accounts left with subscriber privileges, this deletes the topic. You can call ListSubscribers to check for these accounts. Possible error codes: &#42; PERMISSION_DENIED: The reseller account making the request and the provided reseller account are different, or the impersonated user is not a super admin. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. &#42; NOT_FOUND: The topic resource doesn't exist. &#42; INTERNAL: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. &#42; UNKNOWN: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. Return value: The topic name that unregistered the service email address. Returns a success response if the service email address wasn't registered with the topic. */
		class unregisterSubscriber(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
			/** Optional. Resource name of the integrator. Required if account is not provided. Otherwise, leave this field empty/unset. */
			def withIntegrator(integrator: String) = new unregisterSubscriber(req.addQueryStringParameters("integrator" -> integrator.toString))
			/** Perform the request */
			def withGoogleCloudChannelV1UnregisterSubscriberRequest(body: Schema.GoogleCloudChannelV1UnregisterSubscriberRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudChannelV1UnregisterSubscriberResponse])
		}
		object unregisterSubscriber {
			def apply(integratorsId :PlayApi)(using signer: RequestSigner, ec: ExecutionContext): unregisterSubscriber = new unregisterSubscriber(ws.url(BASE_URL + s"v1/integrators/${integratorsId}:unregisterSubscriber").addQueryStringParameters())
		}
		/** Lists service accounts with subscriber privileges on the Pub/Sub topic created for this Channel Services account or integrator. Possible error codes: &#42; PERMISSION_DENIED: The reseller account making the request and the provided reseller account are different, or the impersonated user is not a super admin. &#42; INVALID_ARGUMENT: Required request parameters are missing or invalid. &#42; NOT_FOUND: The topic resource doesn't exist. &#42; INTERNAL: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. &#42; UNKNOWN: Any non-user error related to a technical issue in the backend. Contact Cloud Channel support. Return value: A list of service email addresses. */
		class listSubscribers(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudChannelV1ListSubscribersResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/apps.order""")
			/** Optional. Resource name of the integrator. Required if account is not provided. Otherwise, leave this field empty/unset. */
			def withIntegrator(integrator: String) = new listSubscribers(req.addQueryStringParameters("integrator" -> integrator.toString))
			/** Optional. Resource name of the account. Required if integrator is not provided. Otherwise, leave this field empty/unset. */
			def withAccount(account: String) = new listSubscribers(req.addQueryStringParameters("account" -> account.toString))
			/** Optional. The maximum number of service accounts to return. The service may return fewer than this value. If unspecified, returns at most 100 service accounts. The maximum value is 1000; the server will coerce values above 1000.<br>Format: int32 */
			def withPageSize(pageSize: Int) = new listSubscribers(req.addQueryStringParameters("pageSize" -> pageSize.toString))
			/** Optional. A page token, received from a previous `ListSubscribers` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `ListSubscribers` must match the call that provided the page token. */
			def withPageToken(pageToken: String) = new listSubscribers(req.addQueryStringParameters("pageToken" -> pageToken.toString))
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudChannelV1ListSubscribersResponse])
		}
		object listSubscribers {
			def apply(integratorsId :PlayApi)(using signer: RequestSigner, ec: ExecutionContext): listSubscribers = new listSubscribers(ws.url(BASE_URL + s"v1/integrators/${integratorsId}:listSubscribers").addQueryStringParameters())
			given Conversion[listSubscribers, Future[Schema.GoogleCloudChannelV1ListSubscribersResponse]] = (fun: listSubscribers) => fun.apply()
		}
	}
}
