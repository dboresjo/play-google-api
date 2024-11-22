package com.boresjo.play.api.google.realtimebidding

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://realtimebidding.googleapis.com/"

	object bidders {
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Bidder]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Bidder])
		}
		object get {
			def apply(biddersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/bidders/${biddersId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Bidder]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBiddersResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListBiddersResponse])
		}
		object list {
			def apply(pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/bidders").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListBiddersResponse]] = (fun: list) => fun.apply()
		}
		object publisherConnections {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListPublisherConnectionsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListPublisherConnectionsResponse])
			}
			object list {
				def apply(biddersId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/bidders/${biddersId}/publisherConnections").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
				given Conversion[list, Future[Schema.ListPublisherConnectionsResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PublisherConnection]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.PublisherConnection])
			}
			object get {
				def apply(biddersId :PlayApi, publisherConnectionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/bidders/${biddersId}/publisherConnections/${publisherConnectionsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.PublisherConnection]] = (fun: get) => fun.apply()
			}
			class batchApprove(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withBatchApprovePublisherConnectionsRequest(body: Schema.BatchApprovePublisherConnectionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchApprovePublisherConnectionsResponse])
			}
			object batchApprove {
				def apply(biddersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchApprove = new batchApprove(ws.url(BASE_URL + s"v1/bidders/${biddersId}/publisherConnections:batchApprove").addQueryStringParameters("parent" -> parent.toString))
			}
			class batchReject(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withBatchRejectPublisherConnectionsRequest(body: Schema.BatchRejectPublisherConnectionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BatchRejectPublisherConnectionsResponse])
			}
			object batchReject {
				def apply(biddersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchReject = new batchReject(ws.url(BASE_URL + s"v1/bidders/${biddersId}/publisherConnections:batchReject").addQueryStringParameters("parent" -> parent.toString))
			}
		}
		object endpoints {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Endpoint]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Endpoint])
			}
			object get {
				def apply(biddersId :PlayApi, endpointsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/bidders/${biddersId}/endpoints/${endpointsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.Endpoint]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListEndpointsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListEndpointsResponse])
			}
			object list {
				def apply(biddersId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/bidders/${biddersId}/endpoints").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListEndpointsResponse]] = (fun: list) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withEndpoint(body: Schema.Endpoint) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Endpoint])
			}
			object patch {
				def apply(biddersId :PlayApi, endpointsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/bidders/${biddersId}/endpoints/${endpointsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
		}
		object pretargetingConfigs {
			class addTargetedSites(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAddTargetedSitesRequest(body: Schema.AddTargetedSitesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PretargetingConfig])
			}
			object addTargetedSites {
				def apply(biddersId :PlayApi, pretargetingConfigsId :PlayApi, pretargetingConfig: String)(using auth: AuthToken, ec: ExecutionContext): addTargetedSites = new addTargetedSites(ws.url(BASE_URL + s"v1/bidders/${biddersId}/pretargetingConfigs/${pretargetingConfigsId}:addTargetedSites").addQueryStringParameters("pretargetingConfig" -> pretargetingConfig.toString))
			}
			class addTargetedApps(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAddTargetedAppsRequest(body: Schema.AddTargetedAppsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PretargetingConfig])
			}
			object addTargetedApps {
				def apply(biddersId :PlayApi, pretargetingConfigsId :PlayApi, pretargetingConfig: String)(using auth: AuthToken, ec: ExecutionContext): addTargetedApps = new addTargetedApps(ws.url(BASE_URL + s"v1/bidders/${biddersId}/pretargetingConfigs/${pretargetingConfigsId}:addTargetedApps").addQueryStringParameters("pretargetingConfig" -> pretargetingConfig.toString))
			}
			class removeTargetedApps(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withRemoveTargetedAppsRequest(body: Schema.RemoveTargetedAppsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PretargetingConfig])
			}
			object removeTargetedApps {
				def apply(biddersId :PlayApi, pretargetingConfigsId :PlayApi, pretargetingConfig: String)(using auth: AuthToken, ec: ExecutionContext): removeTargetedApps = new removeTargetedApps(ws.url(BASE_URL + s"v1/bidders/${biddersId}/pretargetingConfigs/${pretargetingConfigsId}:removeTargetedApps").addQueryStringParameters("pretargetingConfig" -> pretargetingConfig.toString))
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withPretargetingConfig(body: Schema.PretargetingConfig) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PretargetingConfig])
			}
			object create {
				def apply(biddersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/bidders/${biddersId}/pretargetingConfigs").addQueryStringParameters("parent" -> parent.toString))
			}
			class addTargetedPublishers(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAddTargetedPublishersRequest(body: Schema.AddTargetedPublishersRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PretargetingConfig])
			}
			object addTargetedPublishers {
				def apply(biddersId :PlayApi, pretargetingConfigsId :PlayApi, pretargetingConfig: String)(using auth: AuthToken, ec: ExecutionContext): addTargetedPublishers = new addTargetedPublishers(ws.url(BASE_URL + s"v1/bidders/${biddersId}/pretargetingConfigs/${pretargetingConfigsId}:addTargetedPublishers").addQueryStringParameters("pretargetingConfig" -> pretargetingConfig.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.PretargetingConfig]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.PretargetingConfig])
			}
			object get {
				def apply(biddersId :PlayApi, pretargetingConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/bidders/${biddersId}/pretargetingConfigs/${pretargetingConfigsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.PretargetingConfig]] = (fun: get) => fun.apply()
			}
			class activate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withActivatePretargetingConfigRequest(body: Schema.ActivatePretargetingConfigRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PretargetingConfig])
			}
			object activate {
				def apply(biddersId :PlayApi, pretargetingConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): activate = new activate(ws.url(BASE_URL + s"v1/bidders/${biddersId}/pretargetingConfigs/${pretargetingConfigsId}:activate").addQueryStringParameters("name" -> name.toString))
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withPretargetingConfig(body: Schema.PretargetingConfig) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.PretargetingConfig])
			}
			object patch {
				def apply(biddersId :PlayApi, pretargetingConfigsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/bidders/${biddersId}/pretargetingConfigs/${pretargetingConfigsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListPretargetingConfigsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListPretargetingConfigsResponse])
			}
			object list {
				def apply(biddersId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/bidders/${biddersId}/pretargetingConfigs").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListPretargetingConfigsResponse]] = (fun: list) => fun.apply()
			}
			class suspend(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSuspendPretargetingConfigRequest(body: Schema.SuspendPretargetingConfigRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PretargetingConfig])
			}
			object suspend {
				def apply(biddersId :PlayApi, pretargetingConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): suspend = new suspend(ws.url(BASE_URL + s"v1/bidders/${biddersId}/pretargetingConfigs/${pretargetingConfigsId}:suspend").addQueryStringParameters("name" -> name.toString))
			}
			class removeTargetedPublishers(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withRemoveTargetedPublishersRequest(body: Schema.RemoveTargetedPublishersRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PretargetingConfig])
			}
			object removeTargetedPublishers {
				def apply(biddersId :PlayApi, pretargetingConfigsId :PlayApi, pretargetingConfig: String)(using auth: AuthToken, ec: ExecutionContext): removeTargetedPublishers = new removeTargetedPublishers(ws.url(BASE_URL + s"v1/bidders/${biddersId}/pretargetingConfigs/${pretargetingConfigsId}:removeTargetedPublishers").addQueryStringParameters("pretargetingConfig" -> pretargetingConfig.toString))
			}
			class removeTargetedSites(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withRemoveTargetedSitesRequest(body: Schema.RemoveTargetedSitesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.PretargetingConfig])
			}
			object removeTargetedSites {
				def apply(biddersId :PlayApi, pretargetingConfigsId :PlayApi, pretargetingConfig: String)(using auth: AuthToken, ec: ExecutionContext): removeTargetedSites = new removeTargetedSites(ws.url(BASE_URL + s"v1/bidders/${biddersId}/pretargetingConfigs/${pretargetingConfigsId}:removeTargetedSites").addQueryStringParameters("pretargetingConfig" -> pretargetingConfig.toString))
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(biddersId :PlayApi, pretargetingConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/bidders/${biddersId}/pretargetingConfigs/${pretargetingConfigsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
		}
		object creatives {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCreativesResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListCreativesResponse])
			}
			object list {
				def apply(biddersId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, view: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/bidders/${biddersId}/creatives").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "view" -> view.toString))
				given Conversion[list, Future[Schema.ListCreativesResponse]] = (fun: list) => fun.apply()
			}
			class watch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withWatchCreativesRequest(body: Schema.WatchCreativesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.WatchCreativesResponse])
			}
			object watch {
				def apply(biddersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): watch = new watch(ws.url(BASE_URL + s"v1/bidders/${biddersId}/creatives:watch").addQueryStringParameters("parent" -> parent.toString))
			}
		}
	}
	object buyers {
		class getRemarketingTag(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GetRemarketingTagResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GetRemarketingTagResponse])
		}
		object getRemarketingTag {
			def apply(buyersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getRemarketingTag = new getRemarketingTag(ws.url(BASE_URL + s"v1/buyers/${buyersId}:getRemarketingTag").addQueryStringParameters("name" -> name.toString))
			given Conversion[getRemarketingTag, Future[Schema.GetRemarketingTagResponse]] = (fun: getRemarketingTag) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Buyer]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Buyer])
		}
		object get {
			def apply(buyersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/buyers/${buyersId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.Buyer]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListBuyersResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListBuyersResponse])
		}
		object list {
			def apply(pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/buyers").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
			given Conversion[list, Future[Schema.ListBuyersResponse]] = (fun: list) => fun.apply()
		}
		object userLists {
			class getRemarketingTag(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GetRemarketingTagResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GetRemarketingTagResponse])
			}
			object getRemarketingTag {
				def apply(buyersId :PlayApi, userListsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getRemarketingTag = new getRemarketingTag(ws.url(BASE_URL + s"v1/buyers/${buyersId}/userLists/${userListsId}:getRemarketingTag").addQueryStringParameters("name" -> name.toString))
				given Conversion[getRemarketingTag, Future[Schema.GetRemarketingTagResponse]] = (fun: getRemarketingTag) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withUserList(body: Schema.UserList) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UserList])
			}
			object create {
				def apply(buyersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/buyers/${buyersId}/userLists").addQueryStringParameters("parent" -> parent.toString))
			}
			class open(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withOpenUserListRequest(body: Schema.OpenUserListRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UserList])
			}
			object open {
				def apply(buyersId :PlayApi, userListsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): open = new open(ws.url(BASE_URL + s"v1/buyers/${buyersId}/userLists/${userListsId}:open").addQueryStringParameters("name" -> name.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.UserList]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.UserList])
			}
			object get {
				def apply(buyersId :PlayApi, userListsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/buyers/${buyersId}/userLists/${userListsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.UserList]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withUserList(body: Schema.UserList) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PUT")).map(_.json.as[Schema.UserList])
			}
			object update {
				def apply(buyersId :PlayApi, userListsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(ws.url(BASE_URL + s"v1/buyers/${buyersId}/userLists/${userListsId}").addQueryStringParameters("name" -> name.toString))
			}
			class close(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCloseUserListRequest(body: Schema.CloseUserListRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.UserList])
			}
			object close {
				def apply(buyersId :PlayApi, userListsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): close = new close(ws.url(BASE_URL + s"v1/buyers/${buyersId}/userLists/${userListsId}:close").addQueryStringParameters("name" -> name.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListUserListsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListUserListsResponse])
			}
			object list {
				def apply(buyersId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/buyers/${buyersId}/userLists").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.ListUserListsResponse]] = (fun: list) => fun.apply()
			}
		}
		object creatives {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListCreativesResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.ListCreativesResponse])
			}
			object list {
				def apply(buyersId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, view: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/buyers/${buyersId}/creatives").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "view" -> view.toString))
				given Conversion[list, Future[Schema.ListCreativesResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Creative]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.Creative])
			}
			object get {
				def apply(buyersId :PlayApi, creativesId :PlayApi, name: String, view: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/buyers/${buyersId}/creatives/${creativesId}").addQueryStringParameters("name" -> name.toString, "view" -> view.toString))
				given Conversion[get, Future[Schema.Creative]] = (fun: get) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCreative(body: Schema.Creative) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Creative])
			}
			object create {
				def apply(buyersId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/buyers/${buyersId}/creatives").addQueryStringParameters("parent" -> parent.toString))
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withCreative(body: Schema.Creative) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Creative])
			}
			object patch {
				def apply(buyersId :PlayApi, creativesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/buyers/${buyersId}/creatives/${creativesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
		}
	}
}
