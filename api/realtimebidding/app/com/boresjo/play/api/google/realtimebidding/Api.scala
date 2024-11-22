package com.boresjo.play.api.google.realtimebidding

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
		"""https://www.googleapis.com/auth/realtime-bidding""" /* See, create, edit, and delete your Authorized Buyers and Open Bidding account entities */
	)

	private val BASE_URL = "https://realtimebidding.googleapis.com/"

	object bidders {
		/** Gets a bidder account by its name. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Bidder]) {
			val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Bidder])
		}
		object get {
			def apply(biddersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/bidders/${biddersId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Bidder]] = (fun: get) => fun.apply()
		}
		/** Lists all the bidder accounts that belong to the caller. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListBiddersResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListBiddersResponse])
		}
		object list {
			def apply(pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/bidders").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListBiddersResponse]] = (fun: list) => fun.apply()
		}
		object publisherConnections {
			/** Lists publisher connections for a given bidder. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListPublisherConnectionsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListPublisherConnectionsResponse])
			}
			object list {
				def apply(biddersId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/bidders/${biddersId}/publisherConnections").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
				given Conversion[list, Future[Schema.ListPublisherConnectionsResponse]] = (fun: list) => fun.apply()
			}
			/** Gets a publisher connection. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PublisherConnection]) {
				val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PublisherConnection])
			}
			object get {
				def apply(biddersId :PlayApi, publisherConnectionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/bidders/${biddersId}/publisherConnections/${publisherConnectionsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.PublisherConnection]] = (fun: get) => fun.apply()
			}
			/** Batch approves multiple publisher connections. */
			class batchApprove(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
				/** Perform the request */
				def withBatchApprovePublisherConnectionsRequest(body: Schema.BatchApprovePublisherConnectionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchApprovePublisherConnectionsResponse])
			}
			object batchApprove {
				def apply(biddersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): batchApprove = new batchApprove(ws.url(BASE_URL + s"v1/bidders/${biddersId}/publisherConnections:batchApprove").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Batch rejects multiple publisher connections. */
			class batchReject(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
				/** Perform the request */
				def withBatchRejectPublisherConnectionsRequest(body: Schema.BatchRejectPublisherConnectionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchRejectPublisherConnectionsResponse])
			}
			object batchReject {
				def apply(biddersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): batchReject = new batchReject(ws.url(BASE_URL + s"v1/bidders/${biddersId}/publisherConnections:batchReject").addQueryStringParameters("parent" -> parent.toString))
			}
		}
		object endpoints {
			/** Gets a bidder endpoint by its name. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Endpoint]) {
				val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Endpoint])
			}
			object get {
				def apply(biddersId :PlayApi, endpointsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/bidders/${biddersId}/endpoints/${endpointsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Endpoint]] = (fun: get) => fun.apply()
			}
			/** Lists all the bidder's endpoints. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListEndpointsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListEndpointsResponse])
			}
			object list {
				def apply(biddersId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/bidders/${biddersId}/endpoints").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListEndpointsResponse]] = (fun: list) => fun.apply()
			}
			/** Updates a bidder's endpoint. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
				/** Perform the request */
				def withEndpoint(body: Schema.Endpoint) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Endpoint])
			}
			object patch {
				def apply(biddersId :PlayApi, endpointsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/bidders/${biddersId}/endpoints/${endpointsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
		}
		object pretargetingConfigs {
			/** Adds targeted sites to the pretargeting configuration. */
			class addTargetedSites(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
				/** Perform the request */
				def withAddTargetedSitesRequest(body: Schema.AddTargetedSitesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PretargetingConfig])
			}
			object addTargetedSites {
				def apply(biddersId :PlayApi, pretargetingConfigsId :PlayApi, pretargetingConfig: String)(using signer: RequestSigner, ec: ExecutionContext): addTargetedSites = new addTargetedSites(ws.url(BASE_URL + s"v1/bidders/${biddersId}/pretargetingConfigs/${pretargetingConfigsId}:addTargetedSites").addQueryStringParameters("pretargetingConfig" -> pretargetingConfig.toString))
			}
			/** Adds targeted apps to the pretargeting configuration. */
			class addTargetedApps(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
				/** Perform the request */
				def withAddTargetedAppsRequest(body: Schema.AddTargetedAppsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PretargetingConfig])
			}
			object addTargetedApps {
				def apply(biddersId :PlayApi, pretargetingConfigsId :PlayApi, pretargetingConfig: String)(using signer: RequestSigner, ec: ExecutionContext): addTargetedApps = new addTargetedApps(ws.url(BASE_URL + s"v1/bidders/${biddersId}/pretargetingConfigs/${pretargetingConfigsId}:addTargetedApps").addQueryStringParameters("pretargetingConfig" -> pretargetingConfig.toString))
			}
			/** Removes targeted apps from the pretargeting configuration. */
			class removeTargetedApps(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
				/** Perform the request */
				def withRemoveTargetedAppsRequest(body: Schema.RemoveTargetedAppsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PretargetingConfig])
			}
			object removeTargetedApps {
				def apply(biddersId :PlayApi, pretargetingConfigsId :PlayApi, pretargetingConfig: String)(using signer: RequestSigner, ec: ExecutionContext): removeTargetedApps = new removeTargetedApps(ws.url(BASE_URL + s"v1/bidders/${biddersId}/pretargetingConfigs/${pretargetingConfigsId}:removeTargetedApps").addQueryStringParameters("pretargetingConfig" -> pretargetingConfig.toString))
			}
			/** Creates a pretargeting configuration. A pretargeting configuration's state (PretargetingConfig.state) is active upon creation, and it will start to affect traffic shortly after. A bidder may create a maximum of 10 pretargeting configurations. Attempts to exceed this maximum results in a 400 bad request error. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
				/** Perform the request */
				def withPretargetingConfig(body: Schema.PretargetingConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PretargetingConfig])
			}
			object create {
				def apply(biddersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/bidders/${biddersId}/pretargetingConfigs").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Adds targeted publishers to the pretargeting config. */
			class addTargetedPublishers(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
				/** Perform the request */
				def withAddTargetedPublishersRequest(body: Schema.AddTargetedPublishersRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PretargetingConfig])
			}
			object addTargetedPublishers {
				def apply(biddersId :PlayApi, pretargetingConfigsId :PlayApi, pretargetingConfig: String)(using signer: RequestSigner, ec: ExecutionContext): addTargetedPublishers = new addTargetedPublishers(ws.url(BASE_URL + s"v1/bidders/${biddersId}/pretargetingConfigs/${pretargetingConfigsId}:addTargetedPublishers").addQueryStringParameters("pretargetingConfig" -> pretargetingConfig.toString))
			}
			/** Gets a pretargeting configuration. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.PretargetingConfig]) {
				val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.PretargetingConfig])
			}
			object get {
				def apply(biddersId :PlayApi, pretargetingConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/bidders/${biddersId}/pretargetingConfigs/${pretargetingConfigsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.PretargetingConfig]] = (fun: get) => fun.apply()
			}
			/** Activates a pretargeting configuration. */
			class activate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
				/** Perform the request */
				def withActivatePretargetingConfigRequest(body: Schema.ActivatePretargetingConfigRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PretargetingConfig])
			}
			object activate {
				def apply(biddersId :PlayApi, pretargetingConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): activate = new activate(ws.url(BASE_URL + s"v1/bidders/${biddersId}/pretargetingConfigs/${pretargetingConfigsId}:activate").addQueryStringParameters("name" -> name.toString))
			}
			/** Updates a pretargeting configuration. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
				/** Perform the request */
				def withPretargetingConfig(body: Schema.PretargetingConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.PretargetingConfig])
			}
			object patch {
				def apply(biddersId :PlayApi, pretargetingConfigsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/bidders/${biddersId}/pretargetingConfigs/${pretargetingConfigsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists all pretargeting configurations for a single bidder. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListPretargetingConfigsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListPretargetingConfigsResponse])
			}
			object list {
				def apply(biddersId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/bidders/${biddersId}/pretargetingConfigs").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListPretargetingConfigsResponse]] = (fun: list) => fun.apply()
			}
			/** Suspends a pretargeting configuration. */
			class suspend(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
				/** Perform the request */
				def withSuspendPretargetingConfigRequest(body: Schema.SuspendPretargetingConfigRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PretargetingConfig])
			}
			object suspend {
				def apply(biddersId :PlayApi, pretargetingConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): suspend = new suspend(ws.url(BASE_URL + s"v1/bidders/${biddersId}/pretargetingConfigs/${pretargetingConfigsId}:suspend").addQueryStringParameters("name" -> name.toString))
			}
			/** Removes targeted publishers from the pretargeting config. */
			class removeTargetedPublishers(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
				/** Perform the request */
				def withRemoveTargetedPublishersRequest(body: Schema.RemoveTargetedPublishersRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PretargetingConfig])
			}
			object removeTargetedPublishers {
				def apply(biddersId :PlayApi, pretargetingConfigsId :PlayApi, pretargetingConfig: String)(using signer: RequestSigner, ec: ExecutionContext): removeTargetedPublishers = new removeTargetedPublishers(ws.url(BASE_URL + s"v1/bidders/${biddersId}/pretargetingConfigs/${pretargetingConfigsId}:removeTargetedPublishers").addQueryStringParameters("pretargetingConfig" -> pretargetingConfig.toString))
			}
			/** Removes targeted sites from the pretargeting configuration. */
			class removeTargetedSites(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
				/** Perform the request */
				def withRemoveTargetedSitesRequest(body: Schema.RemoveTargetedSitesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PretargetingConfig])
			}
			object removeTargetedSites {
				def apply(biddersId :PlayApi, pretargetingConfigsId :PlayApi, pretargetingConfig: String)(using signer: RequestSigner, ec: ExecutionContext): removeTargetedSites = new removeTargetedSites(ws.url(BASE_URL + s"v1/bidders/${biddersId}/pretargetingConfigs/${pretargetingConfigsId}:removeTargetedSites").addQueryStringParameters("pretargetingConfig" -> pretargetingConfig.toString))
			}
			/** Deletes a pretargeting configuration. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(biddersId :PlayApi, pretargetingConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/bidders/${biddersId}/pretargetingConfigs/${pretargetingConfigsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
		}
		object creatives {
			/** Lists creatives as they are at the time of the initial request. This call may take multiple hours to complete. For large, paginated requests, this method returns a snapshot of creatives at the time of request for the first page. `lastStatusUpdate` and `creativeServingDecision` may be outdated for creatives on sequential pages. We recommend [Google Cloud Pub/Sub](//cloud.google.com/pubsub/docs/overview) to view the latest status. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCreativesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCreativesResponse])
			}
			object list {
				def apply(biddersId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/bidders/${biddersId}/creatives").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "view" -> view.toString))
				given Conversion[list, Future[Schema.ListCreativesResponse]] = (fun: list) => fun.apply()
			}
			/** Watches all creatives pertaining to a bidder. It is sufficient to invoke this endpoint once per bidder. A Pub/Sub topic will be created and notifications will be pushed to the topic when any of the bidder's creatives change status. All of the bidder's service accounts will have access to read from the topic. Subsequent invocations of this method will return the existing Pub/Sub configuration. */
			class watch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
				/** Perform the request */
				def withWatchCreativesRequest(body: Schema.WatchCreativesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.WatchCreativesResponse])
			}
			object watch {
				def apply(biddersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): watch = new watch(ws.url(BASE_URL + s"v1/bidders/${biddersId}/creatives:watch").addQueryStringParameters("parent" -> parent.toString))
			}
		}
	}
	object buyers {
		/** This has been sunset as of October 2023, and will return an error response if called. For more information, see the release notes: https://developers.google.com/authorized-buyers/apis/relnotes#real-time-bidding-api Gets remarketing tag for a buyer. A remarketing tag is a piece of JavaScript code that can be placed on a web page. When a user visits a page containing a remarketing tag, Google adds the user to a user list. */
		class getRemarketingTag(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GetRemarketingTagResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GetRemarketingTagResponse])
		}
		object getRemarketingTag {
			def apply(buyersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getRemarketingTag = new getRemarketingTag(ws.url(BASE_URL + s"v1/buyers/${buyersId}:getRemarketingTag").addQueryStringParameters("name" -> name.toString))
			given Conversion[getRemarketingTag, Future[Schema.GetRemarketingTagResponse]] = (fun: getRemarketingTag) => fun.apply()
		}
		/** Gets a buyer account by its name. */
		class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Buyer]) {
			val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Buyer])
		}
		object get {
			def apply(buyersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/buyers/${buyersId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Buyer]] = (fun: get) => fun.apply()
		}
		/** Lists all buyer account information the calling buyer user or service account is permissioned to manage. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListBuyersResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListBuyersResponse])
		}
		object list {
			def apply(pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/buyers").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListBuyersResponse]] = (fun: list) => fun.apply()
		}
		object userLists {
			/** This has been sunset as of October 2023, and will return an error response if called. For more information, see the release notes: https://developers.google.com/authorized-buyers/apis/relnotes#real-time-bidding-api Gets remarketing tag for a buyer. A remarketing tag is a piece of JavaScript code that can be placed on a web page. When a user visits a page containing a remarketing tag, Google adds the user to a user list. */
			class getRemarketingTag(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GetRemarketingTagResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GetRemarketingTagResponse])
			}
			object getRemarketingTag {
				def apply(buyersId :PlayApi, userListsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getRemarketingTag = new getRemarketingTag(ws.url(BASE_URL + s"v1/buyers/${buyersId}/userLists/${userListsId}:getRemarketingTag").addQueryStringParameters("name" -> name.toString))
				given Conversion[getRemarketingTag, Future[Schema.GetRemarketingTagResponse]] = (fun: getRemarketingTag) => fun.apply()
			}
			/** Creates a new user list. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
				/** Perform the request */
				def withUserList(body: Schema.UserList) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UserList])
			}
			object create {
				def apply(buyersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/buyers/${buyersId}/userLists").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Changes the status of a user list to OPEN. This allows new users to be added to the user list. */
			class open(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
				/** Perform the request */
				def withOpenUserListRequest(body: Schema.OpenUserListRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UserList])
			}
			object open {
				def apply(buyersId :PlayApi, userListsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): open = new open(ws.url(BASE_URL + s"v1/buyers/${buyersId}/userLists/${userListsId}:open").addQueryStringParameters("name" -> name.toString))
			}
			/** Gets a user list by its name. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.UserList]) {
				val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.UserList])
			}
			object get {
				def apply(buyersId :PlayApi, userListsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/buyers/${buyersId}/userLists/${userListsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.UserList]] = (fun: get) => fun.apply()
			}
			/** Updates the given user list. Only user lists with URLRestrictions can be updated. */
			class update(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
				/** Perform the request */
				def withUserList(body: Schema.UserList) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.UserList])
			}
			object update {
				def apply(buyersId :PlayApi, userListsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/buyers/${buyersId}/userLists/${userListsId}").addQueryStringParameters("name" -> name.toString))
			}
			/** Changes the status of a user list to CLOSED. This prevents new users from being added to the user list. */
			class close(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
				/** Perform the request */
				def withCloseUserListRequest(body: Schema.CloseUserListRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UserList])
			}
			object close {
				def apply(buyersId :PlayApi, userListsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): close = new close(ws.url(BASE_URL + s"v1/buyers/${buyersId}/userLists/${userListsId}:close").addQueryStringParameters("name" -> name.toString))
			}
			/** Lists the user lists visible to the current user. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListUserListsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListUserListsResponse])
			}
			object list {
				def apply(buyersId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/buyers/${buyersId}/userLists").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListUserListsResponse]] = (fun: list) => fun.apply()
			}
		}
		object creatives {
			/** Lists creatives as they are at the time of the initial request. This call may take multiple hours to complete. For large, paginated requests, this method returns a snapshot of creatives at the time of request for the first page. `lastStatusUpdate` and `creativeServingDecision` may be outdated for creatives on sequential pages. We recommend [Google Cloud Pub/Sub](//cloud.google.com/pubsub/docs/overview) to view the latest status. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListCreativesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListCreativesResponse])
			}
			object list {
				def apply(buyersId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/buyers/${buyersId}/creatives").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "view" -> view.toString))
				given Conversion[list, Future[Schema.ListCreativesResponse]] = (fun: list) => fun.apply()
			}
			/** Gets a creative. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Creative]) {
				val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Creative])
			}
			object get {
				def apply(buyersId :PlayApi, creativesId :PlayApi, name: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/buyers/${buyersId}/creatives/${creativesId}").addQueryStringParameters("name" -> name.toString, "view" -> view.toString))
				given Conversion[get, Future[Schema.Creative]] = (fun: get) => fun.apply()
			}
			/** Creates a creative. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
				/** Perform the request */
				def withCreative(body: Schema.Creative) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Creative])
			}
			object create {
				def apply(buyersId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/buyers/${buyersId}/creatives").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Updates a creative. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/realtime-bidding""")
				/** Perform the request */
				def withCreative(body: Schema.Creative) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Creative])
			}
			object patch {
				def apply(buyersId :PlayApi, creativesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/buyers/${buyersId}/creatives/${creativesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
		}
	}
}
