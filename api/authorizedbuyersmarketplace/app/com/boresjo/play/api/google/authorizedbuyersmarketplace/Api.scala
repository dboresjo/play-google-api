package com.boresjo.play.api.google.authorizedbuyersmarketplace

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
		"""https://www.googleapis.com/auth/authorized-buyers-marketplace""" /* See, create, edit, and delete your Authorized Buyers Marketplace entities. */
	)

	private val BASE_URL = "https://authorizedbuyersmarketplace.googleapis.com/"

	object buyers {
		object clients {
			/** Deactivates an existing client. The state of the client will be updated to "INACTIVE". This method has no effect if the client is already in "INACTIVE" state. */
			class deactivate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
				/** Perform the request */
				def withDeactivateClientRequest(body: Schema.DeactivateClientRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Client])
			}
			object deactivate {
				def apply(buyersId :PlayApi, clientsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): deactivate = new deactivate(ws.url(BASE_URL + s"v1/buyers/${buyersId}/clients/${clientsId}:deactivate").addQueryStringParameters("name" -> name.toString))
			}
			/** Creates a new client. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
				/** Perform the request */
				def withClient(body: Schema.Client) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Client])
			}
			object create {
				def apply(buyersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/buyers/${buyersId}/clients").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Gets a client with a given resource name. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Client]) {
				val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Client])
			}
			object get {
				def apply(buyersId :PlayApi, clientsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/buyers/${buyersId}/clients/${clientsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Client]] = (fun: get) => fun.apply()
			}
			/** Activates an existing client. The state of the client will be updated to "ACTIVE". This method has no effect if the client is already in "ACTIVE" state. */
			class activate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
				/** Perform the request */
				def withActivateClientRequest(body: Schema.ActivateClientRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Client])
			}
			object activate {
				def apply(buyersId :PlayApi, clientsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): activate = new activate(ws.url(BASE_URL + s"v1/buyers/${buyersId}/clients/${clientsId}:activate").addQueryStringParameters("name" -> name.toString))
			}
			/** Updates an existing client. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
				/** Perform the request */
				def withClient(body: Schema.Client) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Client])
			}
			object patch {
				def apply(buyersId :PlayApi, clientsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/buyers/${buyersId}/clients/${clientsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists all the clients for the current buyer. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListClientsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListClientsResponse])
			}
			object list {
				def apply(buyersId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/buyers/${buyersId}/clients").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
				given Conversion[list, Future[Schema.ListClientsResponse]] = (fun: list) => fun.apply()
			}
			object users {
				/** Deactivates an existing client user. The state of the client user will be updated from "ACTIVE" to "INACTIVE". This method has no effect if the client user is already in "INACTIVE" state. An error will be returned if the client user to deactivate is still in "INVITED" state. */
				class deactivate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
					/** Perform the request */
					def withDeactivateClientUserRequest(body: Schema.DeactivateClientUserRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ClientUser])
				}
				object deactivate {
					def apply(buyersId :PlayApi, clientsId :PlayApi, usersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): deactivate = new deactivate(ws.url(BASE_URL + s"v1/buyers/${buyersId}/clients/${clientsId}/users/${usersId}:deactivate").addQueryStringParameters("name" -> name.toString))
				}
				/** Creates a new client user in "INVITED" state. An email invitation will be sent to the new user, once accepted the user will become active. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
					/** Perform the request */
					def withClientUser(body: Schema.ClientUser) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ClientUser])
				}
				object create {
					def apply(buyersId :PlayApi, clientsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/buyers/${buyersId}/clients/${clientsId}/users").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes an existing client user. The client user will lose access to the Authorized Buyers UI. Note that if a client user is deleted, the user's access to the UI can't be restored unless a new client user is created and activated. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(buyersId :PlayApi, clientsId :PlayApi, usersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/buyers/${buyersId}/clients/${clientsId}/users/${usersId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Retrieves an existing client user. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ClientUser]) {
					val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ClientUser])
				}
				object get {
					def apply(buyersId :PlayApi, clientsId :PlayApi, usersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/buyers/${buyersId}/clients/${clientsId}/users/${usersId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.ClientUser]] = (fun: get) => fun.apply()
				}
				/** Activates an existing client user. The state of the client user will be updated from "INACTIVE" to "ACTIVE". This method has no effect if the client user is already in "ACTIVE" state. An error will be returned if the client user to activate is still in "INVITED" state. */
				class activate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
					/** Perform the request */
					def withActivateClientUserRequest(body: Schema.ActivateClientUserRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ClientUser])
				}
				object activate {
					def apply(buyersId :PlayApi, clientsId :PlayApi, usersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): activate = new activate(ws.url(BASE_URL + s"v1/buyers/${buyersId}/clients/${clientsId}/users/${usersId}:activate").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists all client users for a specified client. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListClientUsersResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListClientUsersResponse])
				}
				object list {
					def apply(buyersId :PlayApi, clientsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/buyers/${buyersId}/clients/${clientsId}/users").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListClientUsersResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object finalizedDeals {
			/** Resumes serving of the given finalized deal. Calling this method for an running deal has no effect. If a deal is initially paused by the seller, calling this method will not resume serving of the deal until the seller also resumes the deal. This method only applies to programmatic guaranteed deals and preferred deals. */
			class resume(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
				/** Perform the request */
				def withResumeFinalizedDealRequest(body: Schema.ResumeFinalizedDealRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.FinalizedDeal])
			}
			object resume {
				def apply(buyersId :PlayApi, finalizedDealsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): resume = new resume(ws.url(BASE_URL + s"v1/buyers/${buyersId}/finalizedDeals/${finalizedDealsId}:resume").addQueryStringParameters("name" -> name.toString))
			}
			/** Sets the given finalized deal as ready to serve. By default, deals are set as ready to serve as soon as they're finalized. If you want to opt out of the default behavior, and manually indicate that deals are ready to serve, ask your Technical Account Manager to add you to the allowlist. If you choose to use this method, finalized deals belonging to the bidder and its child seats don't start serving until after you call `setReadyToServe`, and after the deals become active. For example, you can use this method to delay receiving bid requests until your creative is ready. This method only applies to programmatic guaranteed deals. */
			class setReadyToServe(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
				/** Perform the request */
				def withSetReadyToServeRequest(body: Schema.SetReadyToServeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.FinalizedDeal])
			}
			object setReadyToServe {
				def apply(buyersId :PlayApi, finalizedDealsId :PlayApi, deal: String)(using signer: RequestSigner, ec: ExecutionContext): setReadyToServe = new setReadyToServe(ws.url(BASE_URL + s"v1/buyers/${buyersId}/finalizedDeals/${finalizedDealsId}:setReadyToServe").addQueryStringParameters("deal" -> deal.toString))
			}
			/** Pauses serving of the given finalized deal. This call only pauses the serving status, and does not affect other fields of the finalized deal. Calling this method for an already paused deal has no effect. This method only applies to programmatic guaranteed deals and preferred deals. */
			class pause(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
				/** Perform the request */
				def withPauseFinalizedDealRequest(body: Schema.PauseFinalizedDealRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.FinalizedDeal])
			}
			object pause {
				def apply(buyersId :PlayApi, finalizedDealsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): pause = new pause(ws.url(BASE_URL + s"v1/buyers/${buyersId}/finalizedDeals/${finalizedDealsId}:pause").addQueryStringParameters("name" -> name.toString))
			}
			/** Add creative to be used in the bidding process for a finalized deal. For programmatic guaranteed deals, it's recommended that you associate at least one approved creative with the deal before calling SetReadyToServe, to help reduce the number of bid responses filtered because they don't contain approved creatives. Creatives successfully added to a deal can be found in the Realtime-bidding Creatives API creative.deal_ids. This method only applies to programmatic guaranteed deals. Maximum number of 1000 creatives can be added to a finalized deal. */
			class addCreative(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
				/** Perform the request */
				def withAddCreativeRequest(body: Schema.AddCreativeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.FinalizedDeal])
			}
			object addCreative {
				def apply(buyersId :PlayApi, finalizedDealsId :PlayApi, deal: String)(using signer: RequestSigner, ec: ExecutionContext): addCreative = new addCreative(ws.url(BASE_URL + s"v1/buyers/${buyersId}/finalizedDeals/${finalizedDealsId}:addCreative").addQueryStringParameters("deal" -> deal.toString))
			}
			/** Gets a finalized deal given its name. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.FinalizedDeal]) {
				val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.FinalizedDeal])
			}
			object get {
				def apply(buyersId :PlayApi, finalizedDealsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/buyers/${buyersId}/finalizedDeals/${finalizedDealsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.FinalizedDeal]] = (fun: get) => fun.apply()
			}
			/** Lists finalized deals. Use the URL path "/v1/buyers/{accountId}/finalizedDeals" to list finalized deals for the current buyer and its clients. Bidders can use the URL path "/v1/bidders/{accountId}/finalizedDeals" to list finalized deals for the bidder, its buyers and all their clients. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListFinalizedDealsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
				/** Optional query string using the [Cloud API list filtering syntax](https://developers.google.com/authorized-buyers/apis/guides/list-filters) Supported columns for filtering are: &#42; deal.displayName &#42; deal.dealType &#42; deal.createTime &#42; deal.updateTime &#42; deal.flightStartTime &#42; deal.flightEndTime &#42; deal.eligibleSeatIds &#42; dealServingStatus */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** An optional query string to sort finalized deals using the [Cloud API sorting syntax](https://cloud.google.com/apis/design/design_patterns#sorting_order). If no sort order is specified, results will be returned in an arbitrary order. Supported columns for sorting are: &#42; deal.displayName &#42; deal.createTime &#42; deal.updateTime &#42; deal.flightStartTime &#42; deal.flightEndTime &#42; rtbMetrics.bidRequests7Days &#42; rtbMetrics.bids7Days &#42; rtbMetrics.adImpressions7Days &#42; rtbMetrics.bidRate7Days &#42; rtbMetrics.filteredBidRate7Days &#42; rtbMetrics.mustBidRateCurrentMonth */
				def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListFinalizedDealsResponse])
			}
			object list {
				def apply(buyersId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/buyers/${buyersId}/finalizedDeals").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListFinalizedDealsResponse]] = (fun: list) => fun.apply()
			}
		}
		object publisherProfiles {
			/** Gets the requested publisher profile by name. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PublisherProfile]) {
				val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PublisherProfile])
			}
			object get {
				def apply(buyersId :PlayApi, publisherProfilesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/buyers/${buyersId}/publisherProfiles/${publisherProfilesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.PublisherProfile]] = (fun: get) => fun.apply()
			}
			/** Lists publisher profiles. The returned publisher profiles aren't in any defined order. The order of the results might change. A new publisher profile can appear in any place in the list of returned results. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListPublisherProfilesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
				/** Optional query string using the [Cloud API list filtering] (https://developers.google.com/authorized-buyers/apis/guides/list-filters) syntax. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListPublisherProfilesResponse])
			}
			object list {
				def apply(buyersId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/buyers/${buyersId}/publisherProfiles").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListPublisherProfilesResponse]] = (fun: list) => fun.apply()
			}
		}
		object auctionPackages {
			/** Subscribe to the auction package for the specified buyer. Once subscribed, the bidder will receive a call out for inventory matching the auction package targeting criteria with the auction package deal ID and the specified buyer. */
			class subscribe(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
				/** Perform the request */
				def withSubscribeAuctionPackageRequest(body: Schema.SubscribeAuctionPackageRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AuctionPackage])
			}
			object subscribe {
				def apply(buyersId :PlayApi, auctionPackagesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): subscribe = new subscribe(ws.url(BASE_URL + s"v1/buyers/${buyersId}/auctionPackages/${auctionPackagesId}:subscribe").addQueryStringParameters("name" -> name.toString))
			}
			/** Subscribe the specified clients of the buyer to the auction package. If a client in the list does not belong to the buyer, an error response will be returned, and all of the following clients in the list will not be subscribed. Subscribing an already subscribed client will have no effect. */
			class subscribeClients(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
				/** Perform the request */
				def withSubscribeClientsRequest(body: Schema.SubscribeClientsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AuctionPackage])
			}
			object subscribeClients {
				def apply(buyersId :PlayApi, auctionPackagesId :PlayApi, auctionPackage: String)(using signer: RequestSigner, ec: ExecutionContext): subscribeClients = new subscribeClients(ws.url(BASE_URL + s"v1/buyers/${buyersId}/auctionPackages/${auctionPackagesId}:subscribeClients").addQueryStringParameters("auctionPackage" -> auctionPackage.toString))
			}
			/** Unsubscribe from the auction package for the specified buyer. Once unsubscribed, the bidder will no longer receive a call out for the auction package deal ID and the specified buyer. */
			class unsubscribe(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
				/** Perform the request */
				def withUnsubscribeAuctionPackageRequest(body: Schema.UnsubscribeAuctionPackageRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AuctionPackage])
			}
			object unsubscribe {
				def apply(buyersId :PlayApi, auctionPackagesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): unsubscribe = new unsubscribe(ws.url(BASE_URL + s"v1/buyers/${buyersId}/auctionPackages/${auctionPackagesId}:unsubscribe").addQueryStringParameters("name" -> name.toString))
			}
			/** List the auction packages. Buyers can use the URL path "/v1/buyers/{accountId}/auctionPackages" to list auction packages for the current buyer and its clients. Bidders can use the URL path "/v1/bidders/{accountId}/auctionPackages" to list auction packages for the bidder, its media planners, its buyers, and all their clients. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAuctionPackagesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
				/** Optional. Optional query string using the [Cloud API list filtering syntax](/authorized-buyers/apis/guides/list-filters). Only supported when parent is bidder. Supported columns for filtering are: &#42; displayName &#42; createTime &#42; updateTime &#42; eligibleSeatIds */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Optional. An optional query string to sort auction packages using the [Cloud API sorting syntax](https://cloud.google.com/apis/design/design_patterns#sorting_order). If no sort order is specified, results will be returned in an arbitrary order. Only supported when parent is bidder. Supported columns for sorting are: &#42; displayName &#42; createTime &#42; updateTime */
				def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAuctionPackagesResponse])
			}
			object list {
				def apply(buyersId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/buyers/${buyersId}/auctionPackages").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListAuctionPackagesResponse]] = (fun: list) => fun.apply()
			}
			/** Gets an auction package given its name. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.AuctionPackage]) {
				val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.AuctionPackage])
			}
			object get {
				def apply(buyersId :PlayApi, auctionPackagesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/buyers/${buyersId}/auctionPackages/${auctionPackagesId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.AuctionPackage]] = (fun: get) => fun.apply()
			}
			/** Unsubscribe from the auction package for the specified clients of the buyer. Unsubscribing a client that is not subscribed will have no effect. */
			class unsubscribeClients(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
				/** Perform the request */
				def withUnsubscribeClientsRequest(body: Schema.UnsubscribeClientsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AuctionPackage])
			}
			object unsubscribeClients {
				def apply(buyersId :PlayApi, auctionPackagesId :PlayApi, auctionPackage: String)(using signer: RequestSigner, ec: ExecutionContext): unsubscribeClients = new unsubscribeClients(ws.url(BASE_URL + s"v1/buyers/${buyersId}/auctionPackages/${auctionPackagesId}:unsubscribeClients").addQueryStringParameters("auctionPackage" -> auctionPackage.toString))
			}
		}
		object proposals {
			/** Accepts the proposal at the given revision number. If the revision number in the request is behind the latest from the server, an error message will be returned. This call updates the Proposal.state from `BUYER_ACCEPTANCE_REQUESTED` to `FINALIZED`; it has no side effect if the Proposal.state is already `FINALIZED` and throws exception if the Proposal.state is not either `BUYER_ACCEPTANCE_REQUESTED` or `FINALIZED`. Accepting a proposal means the buyer understands and accepts the Proposal.terms_and_conditions proposed by the seller. */
			class accept(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
				/** Perform the request */
				def withAcceptProposalRequest(body: Schema.AcceptProposalRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Proposal])
			}
			object accept {
				def apply(buyersId :PlayApi, proposalsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): accept = new accept(ws.url(BASE_URL + s"v1/buyers/${buyersId}/proposals/${proposalsId}:accept").addQueryStringParameters("name" -> name.toString))
			}
			/** Cancels an ongoing negotiation on a proposal. This does not cancel or end serving for the deals if the proposal has been finalized. If the proposal has not been finalized before, calling this method will set the Proposal.state to `TERMINATED` and increment the Proposal.proposal_revision. If the proposal has been finalized before and is under renegotiation now, calling this method will reset the Proposal.state to `FINALIZED` and increment the Proposal.proposal_revision. This method does not support private auction proposals whose Proposal.deal_type is 'PRIVATE_AUCTION'. */
			class cancelNegotiation(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
				/** Perform the request */
				def withCancelNegotiationRequest(body: Schema.CancelNegotiationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Proposal])
			}
			object cancelNegotiation {
				def apply(buyersId :PlayApi, proposalsId :PlayApi, proposal: String)(using signer: RequestSigner, ec: ExecutionContext): cancelNegotiation = new cancelNegotiation(ws.url(BASE_URL + s"v1/buyers/${buyersId}/proposals/${proposalsId}:cancelNegotiation").addQueryStringParameters("proposal" -> proposal.toString))
			}
			/** Gets a proposal using its resource name. The proposal is returned at the latest revision. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Proposal]) {
				val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Proposal])
			}
			object get {
				def apply(buyersId :PlayApi, proposalsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/buyers/${buyersId}/proposals/${proposalsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Proposal]] = (fun: get) => fun.apply()
			}
			/** Sends a request for proposal (RFP) to a publisher to initiate the negotiation regarding certain inventory. In the RFP, buyers can specify the deal type, deal terms, start and end dates, targeting, and a message to the publisher. Once the RFP is sent, a proposal in `SELLER_REVIEW_REQUESTED` state will be created and returned in the response. The publisher may review your request and respond with detailed deals in the proposal. */
			class sendRfp(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
				/** Perform the request */
				def withSendRfpRequest(body: Schema.SendRfpRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Proposal])
			}
			object sendRfp {
				def apply(buyersId :PlayApi, buyer: String)(using signer: RequestSigner, ec: ExecutionContext): sendRfp = new sendRfp(ws.url(BASE_URL + s"v1/buyers/${buyersId}/proposals:sendRfp").addQueryStringParameters("buyer" -> buyer.toString))
			}
			/** Updates the proposal at the given revision number. If the revision number in the request is behind the latest one kept in the server, an error message will be returned. See FieldMask for how to use FieldMask. Only fields specified in the UpdateProposalRequest.update_mask will be updated; Fields noted as 'Immutable' or 'Output only' yet specified in the UpdateProposalRequest.update_mask will be ignored and left unchanged. Updating a private auction proposal is not allowed and will result in an error. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
				/** Perform the request */
				def withProposal(body: Schema.Proposal) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Proposal])
			}
			object patch {
				def apply(buyersId :PlayApi, proposalsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/buyers/${buyersId}/proposals/${proposalsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists proposals. A filter expression using [Cloud API list filtering syntax](https://developers.google.com/authorized-buyers/apis/guides/list-filters) may be specified to filter the results. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListProposalsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
				/** Optional query string using the [Cloud API list filtering syntax](https://developers.google.com/authorized-buyers/apis/guides/list-filters) Supported columns for filtering are: &#42; displayName &#42; dealType &#42; updateTime &#42; state */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListProposalsResponse])
			}
			object list {
				def apply(buyersId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/buyers/${buyersId}/proposals").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListProposalsResponse]] = (fun: list) => fun.apply()
			}
			/** Creates a note for this proposal and sends to the seller. This method is not supported for proposals with DealType set to 'PRIVATE_AUCTION'. */
			class addNote(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
				/** Perform the request */
				def withAddNoteRequest(body: Schema.AddNoteRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Proposal])
			}
			object addNote {
				def apply(buyersId :PlayApi, proposalsId :PlayApi, proposal: String)(using signer: RequestSigner, ec: ExecutionContext): addNote = new addNote(ws.url(BASE_URL + s"v1/buyers/${buyersId}/proposals/${proposalsId}:addNote").addQueryStringParameters("proposal" -> proposal.toString))
			}
			object deals {
				/** Gets a deal given its name. The deal is returned at its head revision. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Deal]) {
					val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Deal])
				}
				object get {
					def apply(buyersId :PlayApi, proposalsId :PlayApi, dealsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/buyers/${buyersId}/proposals/${proposalsId}/deals/${dealsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Deal]] = (fun: get) => fun.apply()
				}
				/** Updates the given deal at the buyer known revision number. If the server revision has advanced since the passed-in proposal.proposal_revision an ABORTED error message will be returned. The revision number is incremented by the server whenever the proposal or its constituent deals are updated. Note: The revision number is kept at a proposal level. The buyer of the API is expected to keep track of the revision number after the last update operation and send it in as part of the next update request. This way, if there are further changes on the server (for example, seller making new updates), then the server can detect conflicts and reject the proposed changes. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
					/** Perform the request */
					def withDeal(body: Schema.Deal) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Deal])
				}
				object patch {
					def apply(buyersId :PlayApi, proposalsId :PlayApi, dealsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/buyers/${buyersId}/proposals/${proposalsId}/deals/${dealsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Batch updates multiple deals in the same proposal. */
				class batchUpdate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
					/** Perform the request */
					def withBatchUpdateDealsRequest(body: Schema.BatchUpdateDealsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchUpdateDealsResponse])
				}
				object batchUpdate {
					def apply(buyersId :PlayApi, proposalsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): batchUpdate = new batchUpdate(ws.url(BASE_URL + s"v1/buyers/${buyersId}/proposals/${proposalsId}/deals:batchUpdate").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Lists all deals in a proposal. To retrieve only the finalized revision deals regardless if a deal is being renegotiated, see the FinalizedDeals resource. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDealsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDealsResponse])
				}
				object list {
					def apply(buyersId :PlayApi, proposalsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/buyers/${buyersId}/proposals/${proposalsId}/deals").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListDealsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
	object bidders {
		object auctionPackages {
			/** List the auction packages. Buyers can use the URL path "/v1/buyers/{accountId}/auctionPackages" to list auction packages for the current buyer and its clients. Bidders can use the URL path "/v1/bidders/{accountId}/auctionPackages" to list auction packages for the bidder, its media planners, its buyers, and all their clients. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListAuctionPackagesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
				/** Optional. Optional query string using the [Cloud API list filtering syntax](/authorized-buyers/apis/guides/list-filters). Only supported when parent is bidder. Supported columns for filtering are: &#42; displayName &#42; createTime &#42; updateTime &#42; eligibleSeatIds */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Optional. An optional query string to sort auction packages using the [Cloud API sorting syntax](https://cloud.google.com/apis/design/design_patterns#sorting_order). If no sort order is specified, results will be returned in an arbitrary order. Only supported when parent is bidder. Supported columns for sorting are: &#42; displayName &#42; createTime &#42; updateTime */
				def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListAuctionPackagesResponse])
			}
			object list {
				def apply(biddersId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/bidders/${biddersId}/auctionPackages").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListAuctionPackagesResponse]] = (fun: list) => fun.apply()
			}
		}
		object finalizedDeals {
			/** Lists finalized deals. Use the URL path "/v1/buyers/{accountId}/finalizedDeals" to list finalized deals for the current buyer and its clients. Bidders can use the URL path "/v1/bidders/{accountId}/finalizedDeals" to list finalized deals for the bidder, its buyers and all their clients. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListFinalizedDealsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/authorized-buyers-marketplace""")
				/** Optional query string using the [Cloud API list filtering syntax](https://developers.google.com/authorized-buyers/apis/guides/list-filters) Supported columns for filtering are: &#42; deal.displayName &#42; deal.dealType &#42; deal.createTime &#42; deal.updateTime &#42; deal.flightStartTime &#42; deal.flightEndTime &#42; deal.eligibleSeatIds &#42; dealServingStatus */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** An optional query string to sort finalized deals using the [Cloud API sorting syntax](https://cloud.google.com/apis/design/design_patterns#sorting_order). If no sort order is specified, results will be returned in an arbitrary order. Supported columns for sorting are: &#42; deal.displayName &#42; deal.createTime &#42; deal.updateTime &#42; deal.flightStartTime &#42; deal.flightEndTime &#42; rtbMetrics.bidRequests7Days &#42; rtbMetrics.bids7Days &#42; rtbMetrics.adImpressions7Days &#42; rtbMetrics.bidRate7Days &#42; rtbMetrics.filteredBidRate7Days &#42; rtbMetrics.mustBidRateCurrentMonth */
				def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListFinalizedDealsResponse])
			}
			object list {
				def apply(biddersId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/bidders/${biddersId}/finalizedDeals").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListFinalizedDealsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
