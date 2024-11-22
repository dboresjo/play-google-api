package com.boresjo.play.api.google.analyticshub

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
		"""https://www.googleapis.com/auth/bigquery""" /* View and manage your data in Google BigQuery and see the email address for your Google Account */,
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */
	)

	private val BASE_URL = "https://analyticshub.googleapis.com/"

	object projects {
		object locations {
			object dataExchanges {
				/** Returns the permissions that a caller has. */
				class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
				}
				object testIamPermissions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Lists all subscriptions on a given Data Exchange or Listing. */
				class listSubscriptions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSharedResourceSubscriptionsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSharedResourceSubscriptionsResponse])
				}
				object listSubscriptions {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, resource: String, includeDeletedSubscriptions: Boolean, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): listSubscriptions = new listSubscriptions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}:listSubscriptions").addQueryStringParameters("resource" -> resource.toString, "includeDeletedSubscriptions" -> includeDeletedSubscriptions.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[listSubscriptions, Future[Schema.ListSharedResourceSubscriptionsResponse]] = (fun: listSubscriptions) => fun.apply()
				}
				/** Creates a Subscription to a Data Clean Room. This is a long-running operation as it will create one or more linked datasets. */
				class subscribe(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSubscribeDataExchangeRequest(body: Schema.SubscribeDataExchangeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object subscribe {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): subscribe = new subscribe(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}:subscribe").addQueryStringParameters("name" -> name.toString))
				}
				/** Gets the IAM policy. */
				class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Deletes an existing data exchange. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets the details of a data exchange. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.DataExchange]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.DataExchange])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.DataExchange]] = (fun: get) => fun.apply()
				}
				/** Updates an existing data exchange. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withDataExchange(body: Schema.DataExchange) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.DataExchange])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists all data exchanges in a given project and location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListDataExchangesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListDataExchangesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListDataExchangesResponse]] = (fun: list) => fun.apply()
				}
				/** Creates a new data exchange. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withDataExchange(body: Schema.DataExchange) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.DataExchange])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, dataExchangeId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges").addQueryStringParameters("parent" -> parent.toString, "dataExchangeId" -> dataExchangeId.toString))
				}
				/** Sets the IAM policy. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				object listings {
					/** Returns the permissions that a caller has. */
					class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withTestIamPermissionsRequest(body: Schema.TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.TestIamPermissionsResponse])
					}
					object testIamPermissions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, listingsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}/listings/${listingsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Lists all subscriptions on a given Data Exchange or Listing. */
					class listSubscriptions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSharedResourceSubscriptionsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSharedResourceSubscriptionsResponse])
					}
					object listSubscriptions {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, listingsId :PlayApi, resource: String, includeDeletedSubscriptions: Boolean, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): listSubscriptions = new listSubscriptions(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}/listings/${listingsId}:listSubscriptions").addQueryStringParameters("resource" -> resource.toString, "includeDeletedSubscriptions" -> includeDeletedSubscriptions.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[listSubscriptions, Future[Schema.ListSharedResourceSubscriptionsResponse]] = (fun: listSubscriptions) => fun.apply()
					}
					/** Subscribes to a listing. Currently, with Analytics Hub, you can create listings that reference only BigQuery datasets. Upon subscription to a listing for a BigQuery dataset, Analytics Hub creates a linked dataset in the subscriber's project. */
					class subscribe(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withSubscribeListingRequest(body: Schema.SubscribeListingRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SubscribeListingResponse])
					}
					object subscribe {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, listingsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): subscribe = new subscribe(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}/listings/${listingsId}:subscribe").addQueryStringParameters("name" -> name.toString))
					}
					/** Gets the IAM policy. */
					class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object getIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, listingsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}/listings/${listingsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
					/** Deletes a listing. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, listingsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}/listings/${listingsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
					}
					/** Gets the details of a listing. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Listing]) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Listing])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, listingsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}/listings/${listingsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.Listing]] = (fun: get) => fun.apply()
					}
					/** Updates an existing listing. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withListing(body: Schema.Listing) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.Listing])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, listingsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}/listings/${listingsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Lists all listings in a given project and location. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListListingsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListListingsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}/listings").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.ListListingsResponse]] = (fun: list) => fun.apply()
					}
					/** Creates a new listing. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withListing(body: Schema.Listing) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Listing])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, parent: String, listingId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}/listings").addQueryStringParameters("parent" -> parent.toString, "listingId" -> listingId.toString))
					}
					/** Sets the IAM policy. */
					class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
					}
					object setIamPolicy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataExchangesId :PlayApi, listingsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataExchanges/${dataExchangesId}/listings/${listingsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
					}
				}
			}
			object subscriptions {
				/** Refreshes a Subscription to a Data Exchange. A Data Exchange can become stale when a publisher adds or removes data. This is a long-running operation as it may create many linked datasets. */
				class refresh(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withRefreshSubscriptionRequest(body: Schema.RefreshSubscriptionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Operation])
				}
				object refresh {
					def apply(projectsId :PlayApi, locationsId :PlayApi, subscriptionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): refresh = new refresh(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/subscriptions/${subscriptionsId}:refresh").addQueryStringParameters("name" -> name.toString))
				}
				/** Sets the IAM policy. */
				class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withSetIamPolicyRequest(body: Schema.SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object setIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, subscriptionsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/subscriptions/${subscriptionsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Revokes a given subscription. */
				class revoke(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withRevokeSubscriptionRequest(body: Schema.RevokeSubscriptionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RevokeSubscriptionResponse])
				}
				object revoke {
					def apply(projectsId :PlayApi, locationsId :PlayApi, subscriptionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): revoke = new revoke(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/subscriptions/${subscriptionsId}:revoke").addQueryStringParameters("name" -> name.toString))
				}
				/** Gets the IAM policy. */
				class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGetIamPolicyRequest(body: Schema.GetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.Policy])
				}
				object getIamPolicy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, subscriptionsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/subscriptions/${subscriptionsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				}
				/** Deletes a subscription. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, subscriptionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/subscriptions/${subscriptionsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.Operation]] = (fun: delete) => fun.apply()
				}
				/** Gets the details of a Subscription. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Subscription]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.Subscription])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, subscriptionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/subscriptions/${subscriptionsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.Subscription]] = (fun: get) => fun.apply()
				}
				/** Lists all subscriptions in a given project and location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListSubscriptionsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListSubscriptionsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/subscriptions").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListSubscriptionsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
	object organizations {
		object locations {
			object dataExchanges {
				/** Lists all data exchanges from projects in a given organization and location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.ListOrgDataExchangesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/bigquery""", """https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.ListOrgDataExchangesResponse])
				}
				object list {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, organization: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/dataExchanges").addQueryStringParameters("organization" -> organization.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.ListOrgDataExchangesResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
