package com.boresjo.play.api.google.authorizedbuyersmarketplace

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://authorizedbuyersmarketplace.googleapis.com/"

	object buyers {
		object clients {
			class deactivate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withDeactivateClientRequest(body: Schema.DeactivateClientRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Client])
			}
			object deactivate {
				def apply(buyersId :PlayApi, clientsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): deactivate = new deactivate(ws.url(BASE_URL + s"v1/buyers/${buyersId}/clients/${clientsId}:deactivate").addQueryStringParameters("name" -> name.toString))
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withClient(body: Schema.Client) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Client])
			}
			object create {
				def apply(buyersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/buyers/${buyersId}/clients").addQueryStringParameters("parent" -> parent.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Client]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Client])
			}
			object get {
				def apply(buyersId :PlayApi, clientsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/buyers/${buyersId}/clients/${clientsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Client]] = (fun: get) => fun.apply()
			}
			class activate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withActivateClientRequest(body: Schema.ActivateClientRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Client])
			}
			object activate {
				def apply(buyersId :PlayApi, clientsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): activate = new activate(ws.url(BASE_URL + s"v1/buyers/${buyersId}/clients/${clientsId}:activate").addQueryStringParameters("name" -> name.toString))
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withClient(body: Schema.Client) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Client])
			}
			object patch {
				def apply(buyersId :PlayApi, clientsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/buyers/${buyersId}/clients/${clientsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListClientsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListClientsResponse])
			}
			object list {
				def apply(buyersId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/buyers/${buyersId}/clients").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
				given Conversion[list, Future[Schema.ListClientsResponse]] = (fun: list) => fun.apply()
			}
			object users {
				class deactivate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withDeactivateClientUserRequest(body: Schema.DeactivateClientUserRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ClientUser])
				}
				object deactivate {
					def apply(buyersId :PlayApi, clientsId :PlayApi, usersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): deactivate = new deactivate(ws.url(BASE_URL + s"v1/buyers/${buyersId}/clients/${clientsId}/users/${usersId}:deactivate").addQueryStringParameters("name" -> name.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withClientUser(body: Schema.ClientUser) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ClientUser])
				}
				object create {
					def apply(buyersId :PlayApi, clientsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/buyers/${buyersId}/clients/${clientsId}/users").addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(buyersId :PlayApi, clientsId :PlayApi, usersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/buyers/${buyersId}/clients/${clientsId}/users/${usersId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ClientUser]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ClientUser])
				}
				object get {
					def apply(buyersId :PlayApi, clientsId :PlayApi, usersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/buyers/${buyersId}/clients/${clientsId}/users/${usersId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.ClientUser]] = (fun: get) => fun.apply()
				}
				class activate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withActivateClientUserRequest(body: Schema.ActivateClientUserRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ClientUser])
				}
				object activate {
					def apply(buyersId :PlayApi, clientsId :PlayApi, usersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): activate = new activate(ws.url(BASE_URL + s"v1/buyers/${buyersId}/clients/${clientsId}/users/${usersId}:activate").addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListClientUsersResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListClientUsersResponse])
				}
				object list {
					def apply(buyersId :PlayApi, clientsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/buyers/${buyersId}/clients/${clientsId}/users").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListClientUsersResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object finalizedDeals {
			class resume(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withResumeFinalizedDealRequest(body: Schema.ResumeFinalizedDealRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.FinalizedDeal])
			}
			object resume {
				def apply(buyersId :PlayApi, finalizedDealsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): resume = new resume(ws.url(BASE_URL + s"v1/buyers/${buyersId}/finalizedDeals/${finalizedDealsId}:resume").addQueryStringParameters("name" -> name.toString))
			}
			class setReadyToServe(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSetReadyToServeRequest(body: Schema.SetReadyToServeRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.FinalizedDeal])
			}
			object setReadyToServe {
				def apply(buyersId :PlayApi, finalizedDealsId :PlayApi, deal: String)(using auth: AuthToken, ec: ExecutionContext): setReadyToServe = new setReadyToServe(ws.url(BASE_URL + s"v1/buyers/${buyersId}/finalizedDeals/${finalizedDealsId}:setReadyToServe").addQueryStringParameters("deal" -> deal.toString))
			}
			class pause(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withPauseFinalizedDealRequest(body: Schema.PauseFinalizedDealRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.FinalizedDeal])
			}
			object pause {
				def apply(buyersId :PlayApi, finalizedDealsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): pause = new pause(ws.url(BASE_URL + s"v1/buyers/${buyersId}/finalizedDeals/${finalizedDealsId}:pause").addQueryStringParameters("name" -> name.toString))
			}
			class addCreative(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAddCreativeRequest(body: Schema.AddCreativeRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.FinalizedDeal])
			}
			object addCreative {
				def apply(buyersId :PlayApi, finalizedDealsId :PlayApi, deal: String)(using auth: AuthToken, ec: ExecutionContext): addCreative = new addCreative(ws.url(BASE_URL + s"v1/buyers/${buyersId}/finalizedDeals/${finalizedDealsId}:addCreative").addQueryStringParameters("deal" -> deal.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.FinalizedDeal]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.FinalizedDeal])
			}
			object get {
				def apply(buyersId :PlayApi, finalizedDealsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/buyers/${buyersId}/finalizedDeals/${finalizedDealsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.FinalizedDeal]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListFinalizedDealsResponse]) {
				/** Optional query string using the [Cloud API list filtering syntax](https://developers.google.com/authorized-buyers/apis/guides/list-filters) Supported columns for filtering are: &#42; deal.displayName &#42; deal.dealType &#42; deal.createTime &#42; deal.updateTime &#42; deal.flightStartTime &#42; deal.flightEndTime &#42; deal.eligibleSeatIds &#42; dealServingStatus */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** An optional query string to sort finalized deals using the [Cloud API sorting syntax](https://cloud.google.com/apis/design/design_patterns#sorting_order). If no sort order is specified, results will be returned in an arbitrary order. Supported columns for sorting are: &#42; deal.displayName &#42; deal.createTime &#42; deal.updateTime &#42; deal.flightStartTime &#42; deal.flightEndTime &#42; rtbMetrics.bidRequests7Days &#42; rtbMetrics.bids7Days &#42; rtbMetrics.adImpressions7Days &#42; rtbMetrics.bidRate7Days &#42; rtbMetrics.filteredBidRate7Days &#42; rtbMetrics.mustBidRateCurrentMonth */
				def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListFinalizedDealsResponse])
			}
			object list {
				def apply(buyersId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/buyers/${buyersId}/finalizedDeals").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListFinalizedDealsResponse]] = (fun: list) => fun.apply()
			}
		}
		object publisherProfiles {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PublisherProfile]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.PublisherProfile])
			}
			object get {
				def apply(buyersId :PlayApi, publisherProfilesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/buyers/${buyersId}/publisherProfiles/${publisherProfilesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.PublisherProfile]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListPublisherProfilesResponse]) {
				/** Optional query string using the [Cloud API list filtering] (https://developers.google.com/authorized-buyers/apis/guides/list-filters) syntax. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListPublisherProfilesResponse])
			}
			object list {
				def apply(buyersId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/buyers/${buyersId}/publisherProfiles").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListPublisherProfilesResponse]] = (fun: list) => fun.apply()
			}
		}
		object auctionPackages {
			class subscribe(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSubscribeAuctionPackageRequest(body: Schema.SubscribeAuctionPackageRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AuctionPackage])
			}
			object subscribe {
				def apply(buyersId :PlayApi, auctionPackagesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): subscribe = new subscribe(ws.url(BASE_URL + s"v1/buyers/${buyersId}/auctionPackages/${auctionPackagesId}:subscribe").addQueryStringParameters("name" -> name.toString))
			}
			class subscribeClients(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSubscribeClientsRequest(body: Schema.SubscribeClientsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AuctionPackage])
			}
			object subscribeClients {
				def apply(buyersId :PlayApi, auctionPackagesId :PlayApi, auctionPackage: String)(using auth: AuthToken, ec: ExecutionContext): subscribeClients = new subscribeClients(ws.url(BASE_URL + s"v1/buyers/${buyersId}/auctionPackages/${auctionPackagesId}:subscribeClients").addQueryStringParameters("auctionPackage" -> auctionPackage.toString))
			}
			class unsubscribe(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withUnsubscribeAuctionPackageRequest(body: Schema.UnsubscribeAuctionPackageRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AuctionPackage])
			}
			object unsubscribe {
				def apply(buyersId :PlayApi, auctionPackagesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): unsubscribe = new unsubscribe(ws.url(BASE_URL + s"v1/buyers/${buyersId}/auctionPackages/${auctionPackagesId}:unsubscribe").addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAuctionPackagesResponse]) {
				/** Optional. Optional query string using the [Cloud API list filtering syntax](/authorized-buyers/apis/guides/list-filters). Only supported when parent is bidder. Supported columns for filtering are: &#42; displayName &#42; createTime &#42; updateTime &#42; eligibleSeatIds */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Optional. An optional query string to sort auction packages using the [Cloud API sorting syntax](https://cloud.google.com/apis/design/design_patterns#sorting_order). If no sort order is specified, results will be returned in an arbitrary order. Only supported when parent is bidder. Supported columns for sorting are: &#42; displayName &#42; createTime &#42; updateTime */
				def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAuctionPackagesResponse])
			}
			object list {
				def apply(buyersId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/buyers/${buyersId}/auctionPackages").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListAuctionPackagesResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.AuctionPackage]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.AuctionPackage])
			}
			object get {
				def apply(buyersId :PlayApi, auctionPackagesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/buyers/${buyersId}/auctionPackages/${auctionPackagesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.AuctionPackage]] = (fun: get) => fun.apply()
			}
			class unsubscribeClients(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withUnsubscribeClientsRequest(body: Schema.UnsubscribeClientsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AuctionPackage])
			}
			object unsubscribeClients {
				def apply(buyersId :PlayApi, auctionPackagesId :PlayApi, auctionPackage: String)(using auth: AuthToken, ec: ExecutionContext): unsubscribeClients = new unsubscribeClients(ws.url(BASE_URL + s"v1/buyers/${buyersId}/auctionPackages/${auctionPackagesId}:unsubscribeClients").addQueryStringParameters("auctionPackage" -> auctionPackage.toString))
			}
		}
		object proposals {
			class accept(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAcceptProposalRequest(body: Schema.AcceptProposalRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Proposal])
			}
			object accept {
				def apply(buyersId :PlayApi, proposalsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): accept = new accept(ws.url(BASE_URL + s"v1/buyers/${buyersId}/proposals/${proposalsId}:accept").addQueryStringParameters("name" -> name.toString))
			}
			class cancelNegotiation(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCancelNegotiationRequest(body: Schema.CancelNegotiationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Proposal])
			}
			object cancelNegotiation {
				def apply(buyersId :PlayApi, proposalsId :PlayApi, proposal: String)(using auth: AuthToken, ec: ExecutionContext): cancelNegotiation = new cancelNegotiation(ws.url(BASE_URL + s"v1/buyers/${buyersId}/proposals/${proposalsId}:cancelNegotiation").addQueryStringParameters("proposal" -> proposal.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Proposal]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Proposal])
			}
			object get {
				def apply(buyersId :PlayApi, proposalsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/buyers/${buyersId}/proposals/${proposalsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Proposal]] = (fun: get) => fun.apply()
			}
			class sendRfp(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSendRfpRequest(body: Schema.SendRfpRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Proposal])
			}
			object sendRfp {
				def apply(buyersId :PlayApi, buyer: String)(using auth: AuthToken, ec: ExecutionContext): sendRfp = new sendRfp(ws.url(BASE_URL + s"v1/buyers/${buyersId}/proposals:sendRfp").addQueryStringParameters("buyer" -> buyer.toString))
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withProposal(body: Schema.Proposal) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Proposal])
			}
			object patch {
				def apply(buyersId :PlayApi, proposalsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/buyers/${buyersId}/proposals/${proposalsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListProposalsResponse]) {
				/** Optional query string using the [Cloud API list filtering syntax](https://developers.google.com/authorized-buyers/apis/guides/list-filters) Supported columns for filtering are: &#42; displayName &#42; dealType &#42; updateTime &#42; state */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListProposalsResponse])
			}
			object list {
				def apply(buyersId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/buyers/${buyersId}/proposals").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListProposalsResponse]] = (fun: list) => fun.apply()
			}
			class addNote(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAddNoteRequest(body: Schema.AddNoteRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Proposal])
			}
			object addNote {
				def apply(buyersId :PlayApi, proposalsId :PlayApi, proposal: String)(using auth: AuthToken, ec: ExecutionContext): addNote = new addNote(ws.url(BASE_URL + s"v1/buyers/${buyersId}/proposals/${proposalsId}:addNote").addQueryStringParameters("proposal" -> proposal.toString))
			}
			object deals {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Deal]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Deal])
				}
				object get {
					def apply(buyersId :PlayApi, proposalsId :PlayApi, dealsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/buyers/${buyersId}/proposals/${proposalsId}/deals/${dealsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Deal]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withDeal(body: Schema.Deal) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Deal])
				}
				object patch {
					def apply(buyersId :PlayApi, proposalsId :PlayApi, dealsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/buyers/${buyersId}/proposals/${proposalsId}/deals/${dealsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class batchUpdate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withBatchUpdateDealsRequest(body: Schema.BatchUpdateDealsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchUpdateDealsResponse])
				}
				object batchUpdate {
					def apply(buyersId :PlayApi, proposalsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchUpdate = new batchUpdate(ws.url(BASE_URL + s"v1/buyers/${buyersId}/proposals/${proposalsId}/deals:batchUpdate").addQueryStringParameters("parent" -> parent.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDealsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListDealsResponse])
				}
				object list {
					def apply(buyersId :PlayApi, proposalsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/buyers/${buyersId}/proposals/${proposalsId}/deals").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListDealsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
	object bidders {
		object auctionPackages {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListAuctionPackagesResponse]) {
				/** Optional. Optional query string using the [Cloud API list filtering syntax](/authorized-buyers/apis/guides/list-filters). Only supported when parent is bidder. Supported columns for filtering are: &#42; displayName &#42; createTime &#42; updateTime &#42; eligibleSeatIds */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Optional. An optional query string to sort auction packages using the [Cloud API sorting syntax](https://cloud.google.com/apis/design/design_patterns#sorting_order). If no sort order is specified, results will be returned in an arbitrary order. Only supported when parent is bidder. Supported columns for sorting are: &#42; displayName &#42; createTime &#42; updateTime */
				def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListAuctionPackagesResponse])
			}
			object list {
				def apply(biddersId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/bidders/${biddersId}/auctionPackages").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListAuctionPackagesResponse]] = (fun: list) => fun.apply()
			}
		}
		object finalizedDeals {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListFinalizedDealsResponse]) {
				/** Optional query string using the [Cloud API list filtering syntax](https://developers.google.com/authorized-buyers/apis/guides/list-filters) Supported columns for filtering are: &#42; deal.displayName &#42; deal.dealType &#42; deal.createTime &#42; deal.updateTime &#42; deal.flightStartTime &#42; deal.flightEndTime &#42; deal.eligibleSeatIds &#42; dealServingStatus */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** An optional query string to sort finalized deals using the [Cloud API sorting syntax](https://cloud.google.com/apis/design/design_patterns#sorting_order). If no sort order is specified, results will be returned in an arbitrary order. Supported columns for sorting are: &#42; deal.displayName &#42; deal.createTime &#42; deal.updateTime &#42; deal.flightStartTime &#42; deal.flightEndTime &#42; rtbMetrics.bidRequests7Days &#42; rtbMetrics.bids7Days &#42; rtbMetrics.adImpressions7Days &#42; rtbMetrics.bidRate7Days &#42; rtbMetrics.filteredBidRate7Days &#42; rtbMetrics.mustBidRateCurrentMonth */
				def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListFinalizedDealsResponse])
			}
			object list {
				def apply(biddersId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/bidders/${biddersId}/finalizedDeals").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListFinalizedDealsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
