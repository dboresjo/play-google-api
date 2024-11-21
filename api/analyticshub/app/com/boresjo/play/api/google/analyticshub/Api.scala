package com.boresjo.play.api.google.analyticshub

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://analyticshub.googleapis.com/"

	object projects {
		object locations {
			object dataExchanges {
				class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
				}
				class listSubscriptions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSharedResourceSubscriptionsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListSharedResourceSubscriptionsResponse])
				}
				object listSubscriptions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, resource: String, includeDeletedSubscriptions: Boolean, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): listSubscriptions = new listSubscriptions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}:listSubscriptions")).addQueryStringParameters("resource" -> resource.toString, "includeDeletedSubscriptions" -> includeDeletedSubscriptions.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[listSubscriptions, Future[Schema.ListSharedResourceSubscriptionsResponse]] = (fun: listSubscriptions) => fun.apply()
				}
				class subscribe(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSubscribeDataExchangeRequest(body: Schema.SubscribeDataExchangeRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object subscribe {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): subscribe = new subscribe(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}:subscribe")).addQueryStringParameters("name" -> name.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DataExchange]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.DataExchange])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.DataExchange]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withDataExchange(body: Schema.DataExchange) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.DataExchange])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDataExchangesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListDataExchangesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListDataExchangesResponse]] = (fun: list) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withDataExchange(body: Schema.DataExchange) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.DataExchange])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, dataExchangeId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges")).addQueryStringParameters("parent" -> parent.toString, "dataExchangeId" -> dataExchangeId.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				}
				object listings {
					class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, listingsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}/listings/${listingsId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
					}
					class listSubscriptions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSharedResourceSubscriptionsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListSharedResourceSubscriptionsResponse])
					}
					object listSubscriptions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, listingsId :PlayApi, resource: String, includeDeletedSubscriptions: Boolean, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): listSubscriptions = new listSubscriptions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}/listings/${listingsId}:listSubscriptions")).addQueryStringParameters("resource" -> resource.toString, "includeDeletedSubscriptions" -> includeDeletedSubscriptions.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[listSubscriptions, Future[Schema.ListSharedResourceSubscriptionsResponse]] = (fun: listSubscriptions) => fun.apply()
					}
					class subscribe(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSubscribeListingRequest(body: Schema.SubscribeListingRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SubscribeListingResponse])
					}
					object subscribe {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, listingsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): subscribe = new subscribe(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}/listings/${listingsId}:subscribe")).addQueryStringParameters("name" -> name.toString))
					}
					class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, listingsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}/listings/${listingsId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, listingsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}/listings/${listingsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Listing]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.Listing])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, listingsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}/listings/${listingsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Listing]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withListing(body: Schema.Listing) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Listing])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, listingsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}/listings/${listingsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListListingsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.ListListingsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}/listings")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListListingsResponse]] = (fun: list) => fun.apply()
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withListing(body: Schema.Listing) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Listing])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, parent: String, listingId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}/listings")).addQueryStringParameters("parent" -> parent.toString, "listingId" -> listingId.toString))
					}
					class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, listingsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}/listings/${listingsId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
					}
				}
			}
			object subscriptions {
				class refresh(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withRefreshSubscriptionRequest(body: Schema.RefreshSubscriptionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Operation])
				}
				object refresh {
					def apply(projectsId :PlayApi, locationsId :PlayApi, subscriptionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): refresh = new refresh(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/subscriptions/${subscriptionsId}:refresh")).addQueryStringParameters("name" -> name.toString))
				}
				class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, subscriptionsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/subscriptions/${subscriptionsId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				}
				class revoke(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withRevokeSubscriptionRequest(body: Schema.RevokeSubscriptionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.RevokeSubscriptionResponse])
				}
				object revoke {
					def apply(projectsId :PlayApi, locationsId :PlayApi, subscriptionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): revoke = new revoke(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/subscriptions/${subscriptionsId}:revoke")).addQueryStringParameters("name" -> name.toString))
				}
				class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, subscriptionsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/subscriptions/${subscriptionsId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, subscriptionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/subscriptions/${subscriptionsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Subscription]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.Subscription])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, subscriptionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/subscriptions/${subscriptionsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Subscription]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSubscriptionsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListSubscriptionsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/subscriptions")).addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListSubscriptionsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
	object organizations {
		object locations {
			object dataExchanges {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListOrgDataExchangesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListOrgDataExchangesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, organization: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/dataExchanges")).addQueryStringParameters("organization" -> organization.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOrgDataExchangesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
