package com.boresjo.play.api.google.adexchangebuyer2

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
		"""https://www.googleapis.com/auth/adexchange.buyer""" /* Manage your Ad Exchange buyer account configuration */
	)

	private val BASE_URL = "https://adexchangebuyer.googleapis.com/"

	object accounts {
		object finalizedProposals {
			/** List finalized proposals, regardless if a proposal is being renegotiated. A filter expression (PQL query) may be specified to filter the results. The notes will not be returned. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListProposalsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** An optional PQL filter query used to query for proposals. Nested repeated fields, such as proposal.deals.targetingCriterion, cannot be filtered. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListProposalsResponse])
			}
			object list {
				def apply(accountId: String, pageSize: Int, pageToken: String, filterSyntax: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/finalizedProposals").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filterSyntax" -> filterSyntax.toString))
				given Conversion[list, Future[Schema.ListProposalsResponse]] = (fun: list) => fun.apply()
			}
			/** Update given deals to pause serving. This method will set the `DealServingMetadata.DealPauseStatus.has_buyer_paused` bit to true for all listed deals in the request. Currently, this method only applies to PG and PD deals. For PA deals, call accounts.proposals.pause endpoint. It is a no-op to pause already-paused deals. It is an error to call PauseProposalDeals for deals which are not part of the proposal of proposal_id or which are not finalized or renegotiating. */
			class pause(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** Perform the request */
				def withPauseProposalDealsRequest(body: Schema.PauseProposalDealsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Proposal])
			}
			object pause {
				def apply(accountId: String, proposalId: String)(using signer: RequestSigner, ec: ExecutionContext): pause = new pause(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/finalizedProposals/${proposalId}:pause").addQueryStringParameters())
			}
			/** Update given deals to resume serving. This method will set the `DealServingMetadata.DealPauseStatus.has_buyer_paused` bit to false for all listed deals in the request. Currently, this method only applies to PG and PD deals. For PA deals, call accounts.proposals.resume endpoint. It is a no-op to resume running deals or deals paused by the other party. It is an error to call ResumeProposalDeals for deals which are not part of the proposal of proposal_id or which are not finalized or renegotiating. */
			class resume(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** Perform the request */
				def withResumeProposalDealsRequest(body: Schema.ResumeProposalDealsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Proposal])
			}
			object resume {
				def apply(accountId: String, proposalId: String)(using signer: RequestSigner, ec: ExecutionContext): resume = new resume(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/finalizedProposals/${proposalId}:resume").addQueryStringParameters())
			}
		}
		object clients {
			/** Creates a new client buyer. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** Perform the request */
				def withClient(body: Schema.Client) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Client])
			}
			object create {
				def apply(accountId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/clients").addQueryStringParameters())
			}
			/** Gets a client buyer with a given client account ID. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Client]) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Client])
			}
			object get {
				def apply(accountId: String, clientAccountId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/clients/${clientAccountId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Client]] = (fun: get) => fun.apply()
			}
			/** Updates an existing client buyer. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** Perform the request */
				def withClient(body: Schema.Client) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Client])
			}
			object update {
				def apply(accountId: String, clientAccountId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/clients/${clientAccountId}").addQueryStringParameters())
			}
			/** Lists all the clients for the current sponsor buyer. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListClientsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** Optional unique identifier (from the standpoint of an Ad Exchange sponsor buyer partner) of the client to return. If specified, at most one client will be returned in the response. */
				def withPartnerClientId(partnerClientId: String) = new list(req.addQueryStringParameters("partnerClientId" -> partnerClientId.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListClientsResponse])
			}
			object list {
				def apply(accountId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/clients").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListClientsResponse]] = (fun: list) => fun.apply()
			}
			object users {
				/** Lists all the known client users for a specified sponsor buyer account ID. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListClientUsersResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListClientUsersResponse])
				}
				object list {
					def apply(accountId: String, clientAccountId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/clients/${clientAccountId}/users").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListClientUsersResponse]] = (fun: list) => fun.apply()
				}
				/** Updates an existing client user. Only the user status can be changed on update. */
				class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
					/** Perform the request */
					def withClientUser(body: Schema.ClientUser) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.ClientUser])
				}
				object update {
					def apply(accountId: String, clientAccountId: String, userId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/clients/${clientAccountId}/users/${userId}").addQueryStringParameters())
				}
				/** Retrieves an existing client user. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ClientUser]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ClientUser])
				}
				object get {
					def apply(accountId: String, clientAccountId: String, userId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/clients/${clientAccountId}/users/${userId}").addQueryStringParameters())
					given Conversion[get, Future[Schema.ClientUser]] = (fun: get) => fun.apply()
				}
			}
			object invitations {
				/** Creates and sends out an email invitation to access an Ad Exchange client buyer account. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
					/** Perform the request */
					def withClientUserInvitation(body: Schema.ClientUserInvitation) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ClientUserInvitation])
				}
				object create {
					def apply(accountId: String, clientAccountId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/clients/${clientAccountId}/invitations").addQueryStringParameters())
				}
				/** Retrieves an existing client user invitation. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ClientUserInvitation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ClientUserInvitation])
				}
				object get {
					def apply(accountId: String, clientAccountId: String, invitationId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/clients/${clientAccountId}/invitations/${invitationId}").addQueryStringParameters())
					given Conversion[get, Future[Schema.ClientUserInvitation]] = (fun: get) => fun.apply()
				}
				/** Lists all the client users invitations for a client with a given account ID. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListClientUserInvitationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListClientUserInvitationsResponse])
				}
				object list {
					def apply(accountId: String, clientAccountId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/clients/${clientAccountId}/invitations").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListClientUserInvitationsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object products {
			/** Gets the requested product by ID. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Product]) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Product])
			}
			object get {
				def apply(accountId: String, productId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/products/${productId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Product]] = (fun: get) => fun.apply()
			}
			/** List all products visible to the buyer (optionally filtered by the specified PQL query). */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListProductsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** An optional PQL query used to query for products. See https://developers.google.com/ad-manager/docs/pqlreference for documentation about PQL and examples. Nested repeated fields, such as product.targetingCriterion.inclusions, cannot be filtered. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListProductsResponse])
			}
			object list {
				def apply(accountId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/products").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListProductsResponse]] = (fun: list) => fun.apply()
			}
		}
		object creatives {
			/** Stops watching a creative. Will stop push notifications being sent to the topics when the creative changes status. */
			class stopWatching(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** Perform the request */
				def withStopWatchingCreativeRequest(body: Schema.StopWatchingCreativeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object stopWatching {
				def apply(accountId: String, creativeId: String)(using signer: RequestSigner, ec: ExecutionContext): stopWatching = new stopWatching(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/creatives/${creativeId}:stopWatching").addQueryStringParameters())
			}
			/** Updates a creative. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** Perform the request */
				def withCreative(body: Schema.Creative) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Creative])
			}
			object update {
				def apply(accountId: String, creativeId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/creatives/${creativeId}").addQueryStringParameters())
			}
			/** Lists creatives. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCreativesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** An optional query string to filter creatives. If no filter is specified, all active creatives will be returned. Supported queries are: - accountId=&#42;account_id_string&#42; - creativeId=&#42;creative_id_string&#42; - dealsStatus: {approved, conditionally_approved, disapproved, not_checked} - openAuctionStatus: {approved, conditionally_approved, disapproved, not_checked} - attribute: {a numeric attribute from the list of attributes} - disapprovalReason: {a reason from DisapprovalReason} Example: 'accountId=12345 AND (dealsStatus:disapproved AND disapprovalReason:unacceptable_content) OR attribute:47' */
				def withQuery(query: String) = new list(req.addQueryStringParameters("query" -> query.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCreativesResponse])
			}
			object list {
				def apply(accountId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/creatives").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListCreativesResponse]] = (fun: list) => fun.apply()
			}
			/** Watches a creative. Will result in push notifications being sent to the topic when the creative changes status. */
			class watch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** Perform the request */
				def withWatchCreativeRequest(body: Schema.WatchCreativeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object watch {
				def apply(accountId: String, creativeId: String)(using signer: RequestSigner, ec: ExecutionContext): watch = new watch(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/creatives/${creativeId}:watch").addQueryStringParameters())
			}
			/** Creates a creative. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** Perform the request */
				def withCreative(body: Schema.Creative) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Creative])
			}
			object create {
				def apply(accountId: String, duplicateIdMode: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/creatives").addQueryStringParameters("duplicateIdMode" -> duplicateIdMode.toString))
			}
			/** Gets a creative. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Creative]) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Creative])
			}
			object get {
				def apply(accountId: String, creativeId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/creatives/${creativeId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Creative]] = (fun: get) => fun.apply()
			}
			object dealAssociations {
				/** Associate an existing deal with a creative. */
				class add(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
					/** Perform the request */
					def withAddDealAssociationRequest(body: Schema.AddDealAssociationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object add {
					def apply(accountId: String, creativeId: String)(using signer: RequestSigner, ec: ExecutionContext): add = new add(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/creatives/${creativeId}/dealAssociations:add").addQueryStringParameters())
				}
				/** Remove the association between a deal and a creative. */
				class remove(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
					/** Perform the request */
					def withRemoveDealAssociationRequest(body: Schema.RemoveDealAssociationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Empty])
				}
				object remove {
					def apply(accountId: String, creativeId: String)(using signer: RequestSigner, ec: ExecutionContext): remove = new remove(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/creatives/${creativeId}/dealAssociations:remove").addQueryStringParameters())
				}
				/** List all creative-deal associations. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDealAssociationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
					/** An optional query string to filter deal associations. If no filter is specified, all associations will be returned. Supported queries are: - accountId=&#42;account_id_string&#42; - creativeId=&#42;creative_id_string&#42; - dealsId=&#42;deals_id_string&#42; - dealsStatus:{approved, conditionally_approved, disapproved, not_checked} - openAuctionStatus:{approved, conditionally_approved, disapproved, not_checked} Example: 'dealsId=12345 AND dealsStatus:disapproved' */
					def withQuery(query: String) = new list(req.addQueryStringParameters("query" -> query.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDealAssociationsResponse])
				}
				object list {
					def apply(accountId: String, creativeId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/creatives/${creativeId}/dealAssociations").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListDealAssociationsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object publisherProfiles {
			/** Gets the requested publisher profile by id. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PublisherProfile]) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PublisherProfile])
			}
			object get {
				def apply(accountId: String, publisherProfileId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/publisherProfiles/${publisherProfileId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.PublisherProfile]] = (fun: get) => fun.apply()
			}
			/** List all publisher profiles visible to the buyer */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListPublisherProfilesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListPublisherProfilesResponse])
			}
			object list {
				def apply(accountId: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/publisherProfiles").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListPublisherProfilesResponse]] = (fun: list) => fun.apply()
			}
		}
		object proposals {
			/** Cancel an ongoing negotiation on a proposal. This does not cancel or end serving for the deals if the proposal has been finalized, but only cancels a negotiation unilaterally. */
			class cancelNegotiation(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** Perform the request */
				def withCancelNegotiationRequest(body: Schema.CancelNegotiationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Proposal])
			}
			object cancelNegotiation {
				def apply(accountId: String, proposalId: String)(using signer: RequestSigner, ec: ExecutionContext): cancelNegotiation = new cancelNegotiation(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/proposals/${proposalId}:cancelNegotiation").addQueryStringParameters())
			}
			/** You can opt-in to manually update proposals to indicate that setup is complete. By default, proposal setup is automatically completed after their deals are finalized. Contact your Technical Account Manager to opt in. Buyers can call this method when the proposal has been finalized, and all the required creatives have been uploaded using the Creatives API. This call updates the `is_setup_completed` field on the deals in the proposal, and notifies the seller. The server then advances the revision number of the most recent proposal. To mark an individual deal as ready to serve, call `buyers.finalizedDeals.setReadyToServe` in the Marketplace API. */
			class completeSetup(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** Perform the request */
				def withCompleteSetupRequest(body: Schema.CompleteSetupRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Proposal])
			}
			object completeSetup {
				def apply(accountId: String, proposalId: String)(using signer: RequestSigner, ec: ExecutionContext): completeSetup = new completeSetup(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/proposals/${proposalId}:completeSetup").addQueryStringParameters())
			}
			/** Gets a proposal given its ID. The proposal is returned at its head revision. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Proposal]) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Proposal])
			}
			object get {
				def apply(accountId: String, proposalId: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/proposals/${proposalId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.Proposal]] = (fun: get) => fun.apply()
			}
			/** Update the given proposal at the client known revision number. If the server revision has advanced since the passed-in `proposal.proposal_revision`, an `ABORTED` error message will be returned. Only the buyer-modifiable fields of the proposal will be updated. Note that the deals in the proposal will be updated to match the passed-in copy. If a passed-in deal does not have a `deal_id`, the server will assign a new unique ID and create the deal. If passed-in deal has a `deal_id`, it will be updated to match the passed-in copy. Any existing deals not present in the passed-in proposal will be deleted. It is an error to pass in a deal with a `deal_id` not present at head. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** Perform the request */
				def withProposal(body: Schema.Proposal) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.Proposal])
			}
			object update {
				def apply(accountId: String, proposalId: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/proposals/${proposalId}").addQueryStringParameters())
			}
			/** Update the given proposal to resume serving. This method will set the `DealServingMetadata.DealPauseStatus.has_buyer_paused` bit to false for all deals in the proposal. Note that if the `has_seller_paused` bit is also set, serving will not resume until the seller also resumes. It is a no-op to resume an already-running proposal. It is an error to call ResumeProposal for a proposal that is not finalized or renegotiating. */
			class resume(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** Perform the request */
				def withResumeProposalRequest(body: Schema.ResumeProposalRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Proposal])
			}
			object resume {
				def apply(accountId: String, proposalId: String)(using signer: RequestSigner, ec: ExecutionContext): resume = new resume(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/proposals/${proposalId}:resume").addQueryStringParameters())
			}
			/** Mark the proposal as accepted at the given revision number. If the number does not match the server's revision number an `ABORTED` error message will be returned. This call updates the proposal_state from `PROPOSED` to `BUYER_ACCEPTED`, or from `SELLER_ACCEPTED` to `FINALIZED`. Upon calling this endpoint, the buyer implicitly agrees to the terms and conditions optionally set within the proposal by the publisher. */
			class accept(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** Perform the request */
				def withAcceptProposalRequest(body: Schema.AcceptProposalRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Proposal])
			}
			object accept {
				def apply(accountId: String, proposalId: String)(using signer: RequestSigner, ec: ExecutionContext): accept = new accept(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/proposals/${proposalId}:accept").addQueryStringParameters())
			}
			/** Update the given proposal to pause serving. This method will set the `DealServingMetadata.DealPauseStatus.has_buyer_paused` bit to true for all deals in the proposal. It is a no-op to pause an already-paused proposal. It is an error to call PauseProposal for a proposal that is not finalized or renegotiating. */
			class pause(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** Perform the request */
				def withPauseProposalRequest(body: Schema.PauseProposalRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Proposal])
			}
			object pause {
				def apply(accountId: String, proposalId: String)(using signer: RequestSigner, ec: ExecutionContext): pause = new pause(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/proposals/${proposalId}:pause").addQueryStringParameters())
			}
			/** Create the given proposal. Each created proposal and any deals it contains are assigned a unique ID by the server. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** Perform the request */
				def withProposal(body: Schema.Proposal) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Proposal])
			}
			object create {
				def apply(accountId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/proposals").addQueryStringParameters())
			}
			/** List proposals. A filter expression (PQL query) may be specified to filter the results. To retrieve all finalized proposals, regardless if a proposal is being renegotiated, see the FinalizedProposals resource. Note that Bidder/ChildSeat relationships differ from the usual behavior. A Bidder account can only see its child seats' proposals by specifying the ChildSeat's accountId in the request path. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListProposalsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** An optional PQL filter query used to query for proposals. Nested repeated fields, such as proposal.deals.targetingCriterion, cannot be filtered. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListProposalsResponse])
			}
			object list {
				def apply(accountId: String, pageSize: Int, pageToken: String, filterSyntax: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/proposals").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filterSyntax" -> filterSyntax.toString))
				given Conversion[list, Future[Schema.ListProposalsResponse]] = (fun: list) => fun.apply()
			}
			/** Create a new note and attach it to the proposal. The note is assigned a unique ID by the server. The proposal revision number will not increase when associated with a new note. */
			class addNote(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** Perform the request */
				def withAddNoteRequest(body: Schema.AddNoteRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Note])
			}
			object addNote {
				def apply(accountId: String, proposalId: String)(using signer: RequestSigner, ec: ExecutionContext): addNote = new addNote(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/proposals/${proposalId}:addNote").addQueryStringParameters())
			}
		}
	}
	object bidders {
		object accounts {
			object filterSets {
				/** Retrieves the requested filter set for the account with the given account ID. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.FilterSet]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.FilterSet])
				}
				object get {
					def apply(biddersId :PlayApi, accountsId :PlayApi, filterSetsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/accounts/${accountsId}/filterSets/${filterSetsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.FilterSet]] = (fun: get) => fun.apply()
				}
				/** Lists all filter sets for the account with the given account ID. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListFilterSetsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListFilterSetsResponse])
				}
				object list {
					def apply(biddersId :PlayApi, accountsId :PlayApi, ownerName: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/accounts/${accountsId}/filterSets").addQueryStringParameters("ownerName" -> ownerName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListFilterSetsResponse]] = (fun: list) => fun.apply()
				}
				/** Creates the specified filter set for the account with the given account ID. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
					/** Perform the request */
					def withFilterSet(body: Schema.FilterSet) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.FilterSet])
				}
				object create {
					def apply(biddersId :PlayApi, accountsId :PlayApi, ownerName: String, isTransient: Boolean)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/accounts/${accountsId}/filterSets").addQueryStringParameters("ownerName" -> ownerName.toString, "isTransient" -> isTransient.toString))
				}
				/** Deletes the requested filter set from the account with the given account ID. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(biddersId :PlayApi, accountsId :PlayApi, filterSetsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/accounts/${accountsId}/filterSets/${filterSetsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				object bidResponseErrors {
					/** List all errors that occurred in bid responses, with the number of bid responses affected for each reason. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListBidResponseErrorsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListBidResponseErrorsResponse])
					}
					object list {
						def apply(biddersId :PlayApi, accountsId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/accounts/${accountsId}/filterSets/${filterSetsId}/bidResponseErrors").addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListBidResponseErrorsResponse]] = (fun: list) => fun.apply()
					}
				}
				object bidMetrics {
					/** Lists all metrics that are measured in terms of number of bids. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListBidMetricsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListBidMetricsResponse])
					}
					object list {
						def apply(biddersId :PlayApi, accountsId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/accounts/${accountsId}/filterSets/${filterSetsId}/bidMetrics").addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListBidMetricsResponse]] = (fun: list) => fun.apply()
					}
				}
				object filteredBids {
					/** List all reasons for which bids were filtered, with the number of bids filtered for each reason. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListFilteredBidsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListFilteredBidsResponse])
					}
					object list {
						def apply(biddersId :PlayApi, accountsId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/accounts/${accountsId}/filterSets/${filterSetsId}/filteredBids").addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListFilteredBidsResponse]] = (fun: list) => fun.apply()
					}
					object details {
						/** List all details associated with a specific reason for which bids were filtered, with the number of bids filtered for each detail. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCreativeStatusBreakdownByDetailResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCreativeStatusBreakdownByDetailResponse])
						}
						object list {
							def apply(biddersId :PlayApi, accountsId :PlayApi, filterSetsId :PlayApi, filterSetName: String, creativeStatusId: Int, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/accounts/${accountsId}/filterSets/${filterSetsId}/filteredBids/${creativeStatusId}/details").addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.ListCreativeStatusBreakdownByDetailResponse]] = (fun: list) => fun.apply()
						}
					}
					object creatives {
						/** List all creatives associated with a specific reason for which bids were filtered, with the number of bids filtered for each creative. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCreativeStatusBreakdownByCreativeResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCreativeStatusBreakdownByCreativeResponse])
						}
						object list {
							def apply(biddersId :PlayApi, accountsId :PlayApi, filterSetsId :PlayApi, filterSetName: String, creativeStatusId: Int, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/accounts/${accountsId}/filterSets/${filterSetsId}/filteredBids/${creativeStatusId}/creatives").addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.ListCreativeStatusBreakdownByCreativeResponse]] = (fun: list) => fun.apply()
						}
					}
				}
				object bidResponsesWithoutBids {
					/** List all reasons for which bid responses were considered to have no applicable bids, with the number of bid responses affected for each reason. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListBidResponsesWithoutBidsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListBidResponsesWithoutBidsResponse])
					}
					object list {
						def apply(biddersId :PlayApi, accountsId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/accounts/${accountsId}/filterSets/${filterSetsId}/bidResponsesWithoutBids").addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListBidResponsesWithoutBidsResponse]] = (fun: list) => fun.apply()
					}
				}
				object filteredBidRequests {
					/** List all reasons that caused a bid request not to be sent for an impression, with the number of bid requests not sent for each reason. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListFilteredBidRequestsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListFilteredBidRequestsResponse])
					}
					object list {
						def apply(biddersId :PlayApi, accountsId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/accounts/${accountsId}/filterSets/${filterSetsId}/filteredBidRequests").addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListFilteredBidRequestsResponse]] = (fun: list) => fun.apply()
					}
				}
				object impressionMetrics {
					/** Lists all metrics that are measured in terms of number of impressions. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListImpressionMetricsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListImpressionMetricsResponse])
					}
					object list {
						def apply(biddersId :PlayApi, accountsId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/accounts/${accountsId}/filterSets/${filterSetsId}/impressionMetrics").addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListImpressionMetricsResponse]] = (fun: list) => fun.apply()
					}
				}
				object losingBids {
					/** List all reasons for which bids lost in the auction, with the number of bids that lost for each reason. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLosingBidsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLosingBidsResponse])
					}
					object list {
						def apply(biddersId :PlayApi, accountsId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/accounts/${accountsId}/filterSets/${filterSetsId}/losingBids").addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListLosingBidsResponse]] = (fun: list) => fun.apply()
					}
				}
				object nonBillableWinningBids {
					/** List all reasons for which winning bids were not billable, with the number of bids not billed for each reason. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListNonBillableWinningBidsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListNonBillableWinningBidsResponse])
					}
					object list {
						def apply(biddersId :PlayApi, accountsId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/accounts/${accountsId}/filterSets/${filterSetsId}/nonBillableWinningBids").addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListNonBillableWinningBidsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
		object filterSets {
			/** Retrieves the requested filter set for the account with the given account ID. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.FilterSet]) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.FilterSet])
			}
			object get {
				def apply(biddersId :PlayApi, filterSetsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/filterSets/${filterSetsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.FilterSet]] = (fun: get) => fun.apply()
			}
			/** Lists all filter sets for the account with the given account ID. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListFilterSetsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListFilterSetsResponse])
			}
			object list {
				def apply(biddersId :PlayApi, ownerName: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/filterSets").addQueryStringParameters("ownerName" -> ownerName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListFilterSetsResponse]] = (fun: list) => fun.apply()
			}
			/** Creates the specified filter set for the account with the given account ID. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** Perform the request */
				def withFilterSet(body: Schema.FilterSet) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.FilterSet])
			}
			object create {
				def apply(biddersId :PlayApi, ownerName: String, isTransient: Boolean)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/filterSets").addQueryStringParameters("ownerName" -> ownerName.toString, "isTransient" -> isTransient.toString))
			}
			/** Deletes the requested filter set from the account with the given account ID. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(biddersId :PlayApi, filterSetsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/filterSets/${filterSetsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			object bidResponseErrors {
				/** List all errors that occurred in bid responses, with the number of bid responses affected for each reason. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListBidResponseErrorsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListBidResponseErrorsResponse])
				}
				object list {
					def apply(biddersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/filterSets/${filterSetsId}/bidResponseErrors").addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListBidResponseErrorsResponse]] = (fun: list) => fun.apply()
				}
			}
			object bidMetrics {
				/** Lists all metrics that are measured in terms of number of bids. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListBidMetricsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListBidMetricsResponse])
				}
				object list {
					def apply(biddersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/filterSets/${filterSetsId}/bidMetrics").addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListBidMetricsResponse]] = (fun: list) => fun.apply()
				}
			}
			object filteredBids {
				/** List all reasons for which bids were filtered, with the number of bids filtered for each reason. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListFilteredBidsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListFilteredBidsResponse])
				}
				object list {
					def apply(biddersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/filterSets/${filterSetsId}/filteredBids").addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListFilteredBidsResponse]] = (fun: list) => fun.apply()
				}
				object details {
					/** List all details associated with a specific reason for which bids were filtered, with the number of bids filtered for each detail. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCreativeStatusBreakdownByDetailResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCreativeStatusBreakdownByDetailResponse])
					}
					object list {
						def apply(biddersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, creativeStatusId: Int, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/filterSets/${filterSetsId}/filteredBids/${creativeStatusId}/details").addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListCreativeStatusBreakdownByDetailResponse]] = (fun: list) => fun.apply()
					}
				}
				object creatives {
					/** List all creatives associated with a specific reason for which bids were filtered, with the number of bids filtered for each creative. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCreativeStatusBreakdownByCreativeResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCreativeStatusBreakdownByCreativeResponse])
					}
					object list {
						def apply(biddersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, creativeStatusId: Int, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/filterSets/${filterSetsId}/filteredBids/${creativeStatusId}/creatives").addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListCreativeStatusBreakdownByCreativeResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object bidResponsesWithoutBids {
				/** List all reasons for which bid responses were considered to have no applicable bids, with the number of bid responses affected for each reason. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListBidResponsesWithoutBidsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListBidResponsesWithoutBidsResponse])
				}
				object list {
					def apply(biddersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/filterSets/${filterSetsId}/bidResponsesWithoutBids").addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListBidResponsesWithoutBidsResponse]] = (fun: list) => fun.apply()
				}
			}
			object filteredBidRequests {
				/** List all reasons that caused a bid request not to be sent for an impression, with the number of bid requests not sent for each reason. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListFilteredBidRequestsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListFilteredBidRequestsResponse])
				}
				object list {
					def apply(biddersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/filterSets/${filterSetsId}/filteredBidRequests").addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListFilteredBidRequestsResponse]] = (fun: list) => fun.apply()
				}
			}
			object impressionMetrics {
				/** Lists all metrics that are measured in terms of number of impressions. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListImpressionMetricsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListImpressionMetricsResponse])
				}
				object list {
					def apply(biddersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/filterSets/${filterSetsId}/impressionMetrics").addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListImpressionMetricsResponse]] = (fun: list) => fun.apply()
				}
			}
			object losingBids {
				/** List all reasons for which bids lost in the auction, with the number of bids that lost for each reason. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLosingBidsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLosingBidsResponse])
				}
				object list {
					def apply(biddersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/filterSets/${filterSetsId}/losingBids").addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListLosingBidsResponse]] = (fun: list) => fun.apply()
				}
			}
			object nonBillableWinningBids {
				/** List all reasons for which winning bids were not billable, with the number of bids not billed for each reason. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListNonBillableWinningBidsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListNonBillableWinningBidsResponse])
				}
				object list {
					def apply(biddersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/filterSets/${filterSetsId}/nonBillableWinningBids").addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListNonBillableWinningBidsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
	object buyers {
		object filterSets {
			/** Retrieves the requested filter set for the account with the given account ID. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.FilterSet]) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.FilterSet])
			}
			object get {
				def apply(buyersId :PlayApi, filterSetsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2beta1/buyers/${buyersId}/filterSets/${filterSetsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.FilterSet]] = (fun: get) => fun.apply()
			}
			/** Lists all filter sets for the account with the given account ID. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListFilterSetsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListFilterSetsResponse])
			}
			object list {
				def apply(buyersId :PlayApi, ownerName: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/buyers/${buyersId}/filterSets").addQueryStringParameters("ownerName" -> ownerName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListFilterSetsResponse]] = (fun: list) => fun.apply()
			}
			/** Creates the specified filter set for the account with the given account ID. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** Perform the request */
				def withFilterSet(body: Schema.FilterSet) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.FilterSet])
			}
			object create {
				def apply(buyersId :PlayApi, ownerName: String, isTransient: Boolean)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2beta1/buyers/${buyersId}/filterSets").addQueryStringParameters("ownerName" -> ownerName.toString, "isTransient" -> isTransient.toString))
			}
			/** Deletes the requested filter set from the account with the given account ID. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(buyersId :PlayApi, filterSetsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2beta1/buyers/${buyersId}/filterSets/${filterSetsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			object bidResponseErrors {
				/** List all errors that occurred in bid responses, with the number of bid responses affected for each reason. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListBidResponseErrorsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListBidResponseErrorsResponse])
				}
				object list {
					def apply(buyersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/buyers/${buyersId}/filterSets/${filterSetsId}/bidResponseErrors").addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListBidResponseErrorsResponse]] = (fun: list) => fun.apply()
				}
			}
			object bidMetrics {
				/** Lists all metrics that are measured in terms of number of bids. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListBidMetricsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListBidMetricsResponse])
				}
				object list {
					def apply(buyersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/buyers/${buyersId}/filterSets/${filterSetsId}/bidMetrics").addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListBidMetricsResponse]] = (fun: list) => fun.apply()
				}
			}
			object filteredBids {
				/** List all reasons for which bids were filtered, with the number of bids filtered for each reason. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListFilteredBidsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListFilteredBidsResponse])
				}
				object list {
					def apply(buyersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/buyers/${buyersId}/filterSets/${filterSetsId}/filteredBids").addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListFilteredBidsResponse]] = (fun: list) => fun.apply()
				}
				object details {
					/** List all details associated with a specific reason for which bids were filtered, with the number of bids filtered for each detail. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCreativeStatusBreakdownByDetailResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCreativeStatusBreakdownByDetailResponse])
					}
					object list {
						def apply(buyersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, creativeStatusId: Int, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/buyers/${buyersId}/filterSets/${filterSetsId}/filteredBids/${creativeStatusId}/details").addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListCreativeStatusBreakdownByDetailResponse]] = (fun: list) => fun.apply()
					}
				}
				object creatives {
					/** List all creatives associated with a specific reason for which bids were filtered, with the number of bids filtered for each creative. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCreativeStatusBreakdownByCreativeResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCreativeStatusBreakdownByCreativeResponse])
					}
					object list {
						def apply(buyersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, creativeStatusId: Int, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/buyers/${buyersId}/filterSets/${filterSetsId}/filteredBids/${creativeStatusId}/creatives").addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListCreativeStatusBreakdownByCreativeResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object bidResponsesWithoutBids {
				/** List all reasons for which bid responses were considered to have no applicable bids, with the number of bid responses affected for each reason. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListBidResponsesWithoutBidsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListBidResponsesWithoutBidsResponse])
				}
				object list {
					def apply(buyersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/buyers/${buyersId}/filterSets/${filterSetsId}/bidResponsesWithoutBids").addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListBidResponsesWithoutBidsResponse]] = (fun: list) => fun.apply()
				}
			}
			object filteredBidRequests {
				/** List all reasons that caused a bid request not to be sent for an impression, with the number of bid requests not sent for each reason. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListFilteredBidRequestsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListFilteredBidRequestsResponse])
				}
				object list {
					def apply(buyersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/buyers/${buyersId}/filterSets/${filterSetsId}/filteredBidRequests").addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListFilteredBidRequestsResponse]] = (fun: list) => fun.apply()
				}
			}
			object impressionMetrics {
				/** Lists all metrics that are measured in terms of number of impressions. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListImpressionMetricsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListImpressionMetricsResponse])
				}
				object list {
					def apply(buyersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/buyers/${buyersId}/filterSets/${filterSetsId}/impressionMetrics").addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListImpressionMetricsResponse]] = (fun: list) => fun.apply()
				}
			}
			object losingBids {
				/** List all reasons for which bids lost in the auction, with the number of bids that lost for each reason. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListLosingBidsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListLosingBidsResponse])
				}
				object list {
					def apply(buyersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/buyers/${buyersId}/filterSets/${filterSetsId}/losingBids").addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListLosingBidsResponse]] = (fun: list) => fun.apply()
				}
			}
			object nonBillableWinningBids {
				/** List all reasons for which winning bids were not billable, with the number of bids not billed for each reason. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListNonBillableWinningBidsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/adexchange.buyer""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListNonBillableWinningBidsResponse])
				}
				object list {
					def apply(buyersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2beta1/buyers/${buyersId}/filterSets/${filterSetsId}/nonBillableWinningBids").addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListNonBillableWinningBidsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
