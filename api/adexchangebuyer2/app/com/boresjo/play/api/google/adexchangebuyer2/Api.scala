package com.boresjo.play.api.google.adexchangebuyer2

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://adexchangebuyer.googleapis.com/"

	object accounts {
		object finalizedProposals {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListProposalsResponse]) {
				/** An optional PQL filter query used to query for proposals. Nested repeated fields, such as proposal.deals.targetingCriterion, cannot be filtered. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.ListProposalsResponse])
			}
			object list {
				def apply(accountId: String, pageSize: Int, pageToken: String, filterSyntax: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/finalizedProposals")).addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filterSyntax" -> filterSyntax.toString))
				given Conversion[list, Future[Schema.ListProposalsResponse]] = (fun: list) => fun.apply()
			}
			class pause(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withPauseProposalDealsRequest(body: Schema.PauseProposalDealsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Proposal])
			}
			object pause {
				def apply(accountId: String, proposalId: String)(using auth: AuthToken, ec: ExecutionContext): pause = new pause(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/finalizedProposals/${proposalId}:pause")).addQueryStringParameters())
			}
			class resume(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withResumeProposalDealsRequest(body: Schema.ResumeProposalDealsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Proposal])
			}
			object resume {
				def apply(accountId: String, proposalId: String)(using auth: AuthToken, ec: ExecutionContext): resume = new resume(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/finalizedProposals/${proposalId}:resume")).addQueryStringParameters())
			}
		}
		object clients {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withClient(body: Schema.Client) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Client])
			}
			object create {
				def apply(accountId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/clients")).addQueryStringParameters())
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Client]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Client])
			}
			object get {
				def apply(accountId: String, clientAccountId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/clients/${clientAccountId}")).addQueryStringParameters())
				given Conversion[get, Future[Schema.Client]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withClient(body: Schema.Client) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.Client])
			}
			object update {
				def apply(accountId: String, clientAccountId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/clients/${clientAccountId}")).addQueryStringParameters())
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListClientsResponse]) {
				/** Optional unique identifier (from the standpoint of an Ad Exchange sponsor buyer partner) of the client to return. If specified, at most one client will be returned in the response. */
				def withPartnerClientId(partnerClientId: String) = new list(req.addQueryStringParameters("partnerClientId" -> partnerClientId.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.ListClientsResponse])
			}
			object list {
				def apply(accountId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/clients")).addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListClientsResponse]] = (fun: list) => fun.apply()
			}
			object users {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListClientUsersResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListClientUsersResponse])
				}
				object list {
					def apply(accountId: String, clientAccountId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/clients/${clientAccountId}/users")).addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListClientUsersResponse]] = (fun: list) => fun.apply()
				}
				class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withClientUser(body: Schema.ClientUser) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.ClientUser])
				}
				object update {
					def apply(accountId: String, clientAccountId: String, userId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/clients/${clientAccountId}/users/${userId}")).addQueryStringParameters())
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ClientUser]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ClientUser])
				}
				object get {
					def apply(accountId: String, clientAccountId: String, userId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/clients/${clientAccountId}/users/${userId}")).addQueryStringParameters())
					given Conversion[get, Future[Schema.ClientUser]] = (fun: get) => fun.apply()
				}
			}
			object invitations {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withClientUserInvitation(body: Schema.ClientUserInvitation) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ClientUserInvitation])
				}
				object create {
					def apply(accountId: String, clientAccountId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/clients/${clientAccountId}/invitations")).addQueryStringParameters())
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ClientUserInvitation]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ClientUserInvitation])
				}
				object get {
					def apply(accountId: String, clientAccountId: String, invitationId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/clients/${clientAccountId}/invitations/${invitationId}")).addQueryStringParameters())
					given Conversion[get, Future[Schema.ClientUserInvitation]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListClientUserInvitationsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListClientUserInvitationsResponse])
				}
				object list {
					def apply(accountId: String, clientAccountId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/clients/${clientAccountId}/invitations")).addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListClientUserInvitationsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object products {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Product]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Product])
			}
			object get {
				def apply(accountId: String, productId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/products/${productId}")).addQueryStringParameters())
				given Conversion[get, Future[Schema.Product]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListProductsResponse]) {
				/** An optional PQL query used to query for products. See https://developers.google.com/ad-manager/docs/pqlreference for documentation about PQL and examples. Nested repeated fields, such as product.targetingCriterion.inclusions, cannot be filtered. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.ListProductsResponse])
			}
			object list {
				def apply(accountId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/products")).addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListProductsResponse]] = (fun: list) => fun.apply()
			}
		}
		object creatives {
			class stopWatching(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withStopWatchingCreativeRequest(body: Schema.StopWatchingCreativeRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Empty])
			}
			object stopWatching {
				def apply(accountId: String, creativeId: String)(using auth: AuthToken, ec: ExecutionContext): stopWatching = new stopWatching(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/creatives/${creativeId}:stopWatching")).addQueryStringParameters())
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCreative(body: Schema.Creative) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.Creative])
			}
			object update {
				def apply(accountId: String, creativeId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/creatives/${creativeId}")).addQueryStringParameters())
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCreativesResponse]) {
				/** An optional query string to filter creatives. If no filter is specified, all active creatives will be returned. Supported queries are: - accountId=&#42;account_id_string&#42; - creativeId=&#42;creative_id_string&#42; - dealsStatus: {approved, conditionally_approved, disapproved, not_checked} - openAuctionStatus: {approved, conditionally_approved, disapproved, not_checked} - attribute: {a numeric attribute from the list of attributes} - disapprovalReason: {a reason from DisapprovalReason} Example: 'accountId=12345 AND (dealsStatus:disapproved AND disapprovalReason:unacceptable_content) OR attribute:47' */
				def withQuery(query: String) = new list(req.addQueryStringParameters("query" -> query.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.ListCreativesResponse])
			}
			object list {
				def apply(accountId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/creatives")).addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListCreativesResponse]] = (fun: list) => fun.apply()
			}
			class watch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withWatchCreativeRequest(body: Schema.WatchCreativeRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Empty])
			}
			object watch {
				def apply(accountId: String, creativeId: String)(using auth: AuthToken, ec: ExecutionContext): watch = new watch(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/creatives/${creativeId}:watch")).addQueryStringParameters())
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCreative(body: Schema.Creative) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Creative])
			}
			object create {
				def apply(accountId: String, duplicateIdMode: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/creatives")).addQueryStringParameters("duplicateIdMode" -> duplicateIdMode.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Creative]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Creative])
			}
			object get {
				def apply(accountId: String, creativeId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/creatives/${creativeId}")).addQueryStringParameters())
				given Conversion[get, Future[Schema.Creative]] = (fun: get) => fun.apply()
			}
			object dealAssociations {
				class add(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withAddDealAssociationRequest(body: Schema.AddDealAssociationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Empty])
				}
				object add {
					def apply(accountId: String, creativeId: String)(using auth: AuthToken, ec: ExecutionContext): add = new add(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/creatives/${creativeId}/dealAssociations:add")).addQueryStringParameters())
				}
				class remove(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withRemoveDealAssociationRequest(body: Schema.RemoveDealAssociationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Empty])
				}
				object remove {
					def apply(accountId: String, creativeId: String)(using auth: AuthToken, ec: ExecutionContext): remove = new remove(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/creatives/${creativeId}/dealAssociations:remove")).addQueryStringParameters())
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDealAssociationsResponse]) {
					/** An optional query string to filter deal associations. If no filter is specified, all associations will be returned. Supported queries are: - accountId=&#42;account_id_string&#42; - creativeId=&#42;creative_id_string&#42; - dealsId=&#42;deals_id_string&#42; - dealsStatus:{approved, conditionally_approved, disapproved, not_checked} - openAuctionStatus:{approved, conditionally_approved, disapproved, not_checked} Example: 'dealsId=12345 AND dealsStatus:disapproved' */
					def withQuery(query: String) = new list(req.addQueryStringParameters("query" -> query.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.ListDealAssociationsResponse])
				}
				object list {
					def apply(accountId: String, creativeId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/creatives/${creativeId}/dealAssociations")).addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListDealAssociationsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
		object publisherProfiles {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PublisherProfile]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.PublisherProfile])
			}
			object get {
				def apply(accountId: String, publisherProfileId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/publisherProfiles/${publisherProfileId}")).addQueryStringParameters())
				given Conversion[get, Future[Schema.PublisherProfile]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListPublisherProfilesResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListPublisherProfilesResponse])
			}
			object list {
				def apply(accountId: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/publisherProfiles")).addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListPublisherProfilesResponse]] = (fun: list) => fun.apply()
			}
		}
		object proposals {
			class cancelNegotiation(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCancelNegotiationRequest(body: Schema.CancelNegotiationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Proposal])
			}
			object cancelNegotiation {
				def apply(accountId: String, proposalId: String)(using auth: AuthToken, ec: ExecutionContext): cancelNegotiation = new cancelNegotiation(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/proposals/${proposalId}:cancelNegotiation")).addQueryStringParameters())
			}
			class completeSetup(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCompleteSetupRequest(body: Schema.CompleteSetupRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Proposal])
			}
			object completeSetup {
				def apply(accountId: String, proposalId: String)(using auth: AuthToken, ec: ExecutionContext): completeSetup = new completeSetup(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/proposals/${proposalId}:completeSetup")).addQueryStringParameters())
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Proposal]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Proposal])
			}
			object get {
				def apply(accountId: String, proposalId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/proposals/${proposalId}")).addQueryStringParameters())
				given Conversion[get, Future[Schema.Proposal]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withProposal(body: Schema.Proposal) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.Proposal])
			}
			object update {
				def apply(accountId: String, proposalId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/proposals/${proposalId}")).addQueryStringParameters())
			}
			class resume(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withResumeProposalRequest(body: Schema.ResumeProposalRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Proposal])
			}
			object resume {
				def apply(accountId: String, proposalId: String)(using auth: AuthToken, ec: ExecutionContext): resume = new resume(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/proposals/${proposalId}:resume")).addQueryStringParameters())
			}
			class accept(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAcceptProposalRequest(body: Schema.AcceptProposalRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Proposal])
			}
			object accept {
				def apply(accountId: String, proposalId: String)(using auth: AuthToken, ec: ExecutionContext): accept = new accept(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/proposals/${proposalId}:accept")).addQueryStringParameters())
			}
			class pause(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withPauseProposalRequest(body: Schema.PauseProposalRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Proposal])
			}
			object pause {
				def apply(accountId: String, proposalId: String)(using auth: AuthToken, ec: ExecutionContext): pause = new pause(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/proposals/${proposalId}:pause")).addQueryStringParameters())
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withProposal(body: Schema.Proposal) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Proposal])
			}
			object create {
				def apply(accountId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/proposals")).addQueryStringParameters())
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListProposalsResponse]) {
				/** An optional PQL filter query used to query for proposals. Nested repeated fields, such as proposal.deals.targetingCriterion, cannot be filtered. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.ListProposalsResponse])
			}
			object list {
				def apply(accountId: String, pageSize: Int, pageToken: String, filterSyntax: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/proposals")).addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filterSyntax" -> filterSyntax.toString))
				given Conversion[list, Future[Schema.ListProposalsResponse]] = (fun: list) => fun.apply()
			}
			class addNote(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAddNoteRequest(body: Schema.AddNoteRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Note])
			}
			object addNote {
				def apply(accountId: String, proposalId: String)(using auth: AuthToken, ec: ExecutionContext): addNote = new addNote(auth(ws.url(BASE_URL + s"v2beta1/accounts/${accountId}/proposals/${proposalId}:addNote")).addQueryStringParameters())
			}
		}
	}
	object bidders {
		object accounts {
			object filterSets {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.FilterSet]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.FilterSet])
				}
				object get {
					def apply(biddersId :PlayApi, accountsId :PlayApi, filterSetsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/accounts/${accountsId}/filterSets/${filterSetsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.FilterSet]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListFilterSetsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListFilterSetsResponse])
				}
				object list {
					def apply(biddersId :PlayApi, accountsId :PlayApi, ownerName: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/accounts/${accountsId}/filterSets")).addQueryStringParameters("ownerName" -> ownerName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListFilterSetsResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withFilterSet(body: Schema.FilterSet) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.FilterSet])
				}
				object create {
					def apply(biddersId :PlayApi, accountsId :PlayApi, ownerName: String, isTransient: Boolean)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/accounts/${accountsId}/filterSets")).addQueryStringParameters("ownerName" -> ownerName.toString, "isTransient" -> isTransient.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(biddersId :PlayApi, accountsId :PlayApi, filterSetsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/accounts/${accountsId}/filterSets/${filterSetsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				object bidResponseErrors {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBidResponseErrorsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListBidResponseErrorsResponse])
					}
					object list {
						def apply(biddersId :PlayApi, accountsId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/accounts/${accountsId}/filterSets/${filterSetsId}/bidResponseErrors")).addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListBidResponseErrorsResponse]] = (fun: list) => fun.apply()
					}
				}
				object bidMetrics {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBidMetricsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListBidMetricsResponse])
					}
					object list {
						def apply(biddersId :PlayApi, accountsId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/accounts/${accountsId}/filterSets/${filterSetsId}/bidMetrics")).addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListBidMetricsResponse]] = (fun: list) => fun.apply()
					}
				}
				object filteredBids {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListFilteredBidsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListFilteredBidsResponse])
					}
					object list {
						def apply(biddersId :PlayApi, accountsId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/accounts/${accountsId}/filterSets/${filterSetsId}/filteredBids")).addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListFilteredBidsResponse]] = (fun: list) => fun.apply()
					}
					object details {
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCreativeStatusBreakdownByDetailResponse]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.ListCreativeStatusBreakdownByDetailResponse])
						}
						object list {
							def apply(biddersId :PlayApi, accountsId :PlayApi, filterSetsId :PlayApi, filterSetName: String, creativeStatusId: Int, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/accounts/${accountsId}/filterSets/${filterSetsId}/filteredBids/${creativeStatusId}/details")).addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.ListCreativeStatusBreakdownByDetailResponse]] = (fun: list) => fun.apply()
						}
					}
					object creatives {
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCreativeStatusBreakdownByCreativeResponse]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.ListCreativeStatusBreakdownByCreativeResponse])
						}
						object list {
							def apply(biddersId :PlayApi, accountsId :PlayApi, filterSetsId :PlayApi, filterSetName: String, creativeStatusId: Int, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/accounts/${accountsId}/filterSets/${filterSetsId}/filteredBids/${creativeStatusId}/creatives")).addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.ListCreativeStatusBreakdownByCreativeResponse]] = (fun: list) => fun.apply()
						}
					}
				}
				object bidResponsesWithoutBids {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBidResponsesWithoutBidsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListBidResponsesWithoutBidsResponse])
					}
					object list {
						def apply(biddersId :PlayApi, accountsId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/accounts/${accountsId}/filterSets/${filterSetsId}/bidResponsesWithoutBids")).addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListBidResponsesWithoutBidsResponse]] = (fun: list) => fun.apply()
					}
				}
				object filteredBidRequests {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListFilteredBidRequestsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListFilteredBidRequestsResponse])
					}
					object list {
						def apply(biddersId :PlayApi, accountsId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/accounts/${accountsId}/filterSets/${filterSetsId}/filteredBidRequests")).addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListFilteredBidRequestsResponse]] = (fun: list) => fun.apply()
					}
				}
				object impressionMetrics {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListImpressionMetricsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListImpressionMetricsResponse])
					}
					object list {
						def apply(biddersId :PlayApi, accountsId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/accounts/${accountsId}/filterSets/${filterSetsId}/impressionMetrics")).addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListImpressionMetricsResponse]] = (fun: list) => fun.apply()
					}
				}
				object losingBids {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLosingBidsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListLosingBidsResponse])
					}
					object list {
						def apply(biddersId :PlayApi, accountsId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/accounts/${accountsId}/filterSets/${filterSetsId}/losingBids")).addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListLosingBidsResponse]] = (fun: list) => fun.apply()
					}
				}
				object nonBillableWinningBids {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListNonBillableWinningBidsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListNonBillableWinningBidsResponse])
					}
					object list {
						def apply(biddersId :PlayApi, accountsId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/accounts/${accountsId}/filterSets/${filterSetsId}/nonBillableWinningBids")).addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListNonBillableWinningBidsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
		object filterSets {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.FilterSet]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.FilterSet])
			}
			object get {
				def apply(biddersId :PlayApi, filterSetsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/filterSets/${filterSetsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.FilterSet]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListFilterSetsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListFilterSetsResponse])
			}
			object list {
				def apply(biddersId :PlayApi, ownerName: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/filterSets")).addQueryStringParameters("ownerName" -> ownerName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListFilterSetsResponse]] = (fun: list) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withFilterSet(body: Schema.FilterSet) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.FilterSet])
			}
			object create {
				def apply(biddersId :PlayApi, ownerName: String, isTransient: Boolean)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/filterSets")).addQueryStringParameters("ownerName" -> ownerName.toString, "isTransient" -> isTransient.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(biddersId :PlayApi, filterSetsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/filterSets/${filterSetsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			object bidResponseErrors {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBidResponseErrorsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListBidResponseErrorsResponse])
				}
				object list {
					def apply(biddersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/filterSets/${filterSetsId}/bidResponseErrors")).addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListBidResponseErrorsResponse]] = (fun: list) => fun.apply()
				}
			}
			object bidMetrics {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBidMetricsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListBidMetricsResponse])
				}
				object list {
					def apply(biddersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/filterSets/${filterSetsId}/bidMetrics")).addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListBidMetricsResponse]] = (fun: list) => fun.apply()
				}
			}
			object filteredBids {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListFilteredBidsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListFilteredBidsResponse])
				}
				object list {
					def apply(biddersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/filterSets/${filterSetsId}/filteredBids")).addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListFilteredBidsResponse]] = (fun: list) => fun.apply()
				}
				object details {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCreativeStatusBreakdownByDetailResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListCreativeStatusBreakdownByDetailResponse])
					}
					object list {
						def apply(biddersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, creativeStatusId: Int, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/filterSets/${filterSetsId}/filteredBids/${creativeStatusId}/details")).addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListCreativeStatusBreakdownByDetailResponse]] = (fun: list) => fun.apply()
					}
				}
				object creatives {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCreativeStatusBreakdownByCreativeResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListCreativeStatusBreakdownByCreativeResponse])
					}
					object list {
						def apply(biddersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, creativeStatusId: Int, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/filterSets/${filterSetsId}/filteredBids/${creativeStatusId}/creatives")).addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListCreativeStatusBreakdownByCreativeResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object bidResponsesWithoutBids {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBidResponsesWithoutBidsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListBidResponsesWithoutBidsResponse])
				}
				object list {
					def apply(biddersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/filterSets/${filterSetsId}/bidResponsesWithoutBids")).addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListBidResponsesWithoutBidsResponse]] = (fun: list) => fun.apply()
				}
			}
			object filteredBidRequests {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListFilteredBidRequestsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListFilteredBidRequestsResponse])
				}
				object list {
					def apply(biddersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/filterSets/${filterSetsId}/filteredBidRequests")).addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListFilteredBidRequestsResponse]] = (fun: list) => fun.apply()
				}
			}
			object impressionMetrics {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListImpressionMetricsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListImpressionMetricsResponse])
				}
				object list {
					def apply(biddersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/filterSets/${filterSetsId}/impressionMetrics")).addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListImpressionMetricsResponse]] = (fun: list) => fun.apply()
				}
			}
			object losingBids {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLosingBidsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListLosingBidsResponse])
				}
				object list {
					def apply(biddersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/filterSets/${filterSetsId}/losingBids")).addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListLosingBidsResponse]] = (fun: list) => fun.apply()
				}
			}
			object nonBillableWinningBids {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListNonBillableWinningBidsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListNonBillableWinningBidsResponse])
				}
				object list {
					def apply(biddersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/bidders/${biddersId}/filterSets/${filterSetsId}/nonBillableWinningBids")).addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListNonBillableWinningBidsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
	object buyers {
		object filterSets {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.FilterSet]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.FilterSet])
			}
			object get {
				def apply(buyersId :PlayApi, filterSetsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2beta1/buyers/${buyersId}/filterSets/${filterSetsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.FilterSet]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListFilterSetsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListFilterSetsResponse])
			}
			object list {
				def apply(buyersId :PlayApi, ownerName: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/buyers/${buyersId}/filterSets")).addQueryStringParameters("ownerName" -> ownerName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListFilterSetsResponse]] = (fun: list) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withFilterSet(body: Schema.FilterSet) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.FilterSet])
			}
			object create {
				def apply(buyersId :PlayApi, ownerName: String, isTransient: Boolean)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2beta1/buyers/${buyersId}/filterSets")).addQueryStringParameters("ownerName" -> ownerName.toString, "isTransient" -> isTransient.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(buyersId :PlayApi, filterSetsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2beta1/buyers/${buyersId}/filterSets/${filterSetsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			object bidResponseErrors {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBidResponseErrorsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListBidResponseErrorsResponse])
				}
				object list {
					def apply(buyersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/buyers/${buyersId}/filterSets/${filterSetsId}/bidResponseErrors")).addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListBidResponseErrorsResponse]] = (fun: list) => fun.apply()
				}
			}
			object bidMetrics {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBidMetricsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListBidMetricsResponse])
				}
				object list {
					def apply(buyersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/buyers/${buyersId}/filterSets/${filterSetsId}/bidMetrics")).addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListBidMetricsResponse]] = (fun: list) => fun.apply()
				}
			}
			object filteredBids {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListFilteredBidsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListFilteredBidsResponse])
				}
				object list {
					def apply(buyersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/buyers/${buyersId}/filterSets/${filterSetsId}/filteredBids")).addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListFilteredBidsResponse]] = (fun: list) => fun.apply()
				}
				object details {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCreativeStatusBreakdownByDetailResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListCreativeStatusBreakdownByDetailResponse])
					}
					object list {
						def apply(buyersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, creativeStatusId: Int, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/buyers/${buyersId}/filterSets/${filterSetsId}/filteredBids/${creativeStatusId}/details")).addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListCreativeStatusBreakdownByDetailResponse]] = (fun: list) => fun.apply()
					}
				}
				object creatives {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCreativeStatusBreakdownByCreativeResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListCreativeStatusBreakdownByCreativeResponse])
					}
					object list {
						def apply(buyersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, creativeStatusId: Int, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/buyers/${buyersId}/filterSets/${filterSetsId}/filteredBids/${creativeStatusId}/creatives")).addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListCreativeStatusBreakdownByCreativeResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object bidResponsesWithoutBids {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBidResponsesWithoutBidsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListBidResponsesWithoutBidsResponse])
				}
				object list {
					def apply(buyersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/buyers/${buyersId}/filterSets/${filterSetsId}/bidResponsesWithoutBids")).addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListBidResponsesWithoutBidsResponse]] = (fun: list) => fun.apply()
				}
			}
			object filteredBidRequests {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListFilteredBidRequestsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListFilteredBidRequestsResponse])
				}
				object list {
					def apply(buyersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/buyers/${buyersId}/filterSets/${filterSetsId}/filteredBidRequests")).addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListFilteredBidRequestsResponse]] = (fun: list) => fun.apply()
				}
			}
			object impressionMetrics {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListImpressionMetricsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListImpressionMetricsResponse])
				}
				object list {
					def apply(buyersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/buyers/${buyersId}/filterSets/${filterSetsId}/impressionMetrics")).addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListImpressionMetricsResponse]] = (fun: list) => fun.apply()
				}
			}
			object losingBids {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListLosingBidsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListLosingBidsResponse])
				}
				object list {
					def apply(buyersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/buyers/${buyersId}/filterSets/${filterSetsId}/losingBids")).addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListLosingBidsResponse]] = (fun: list) => fun.apply()
				}
			}
			object nonBillableWinningBids {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListNonBillableWinningBidsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListNonBillableWinningBidsResponse])
				}
				object list {
					def apply(buyersId :PlayApi, filterSetsId :PlayApi, filterSetName: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2beta1/buyers/${buyersId}/filterSets/${filterSetsId}/nonBillableWinningBids")).addQueryStringParameters("filterSetName" -> filterSetName.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListNonBillableWinningBidsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
