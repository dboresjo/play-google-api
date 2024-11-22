package com.boresjo.play.api.google.recommendationengine

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
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */
	)

	private val BASE_URL = "https://recommendationengine.googleapis.com/"

	object projects {
		object locations {
			object catalogs {
				/** Updates the catalog configuration. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Indicates which fields in the provided 'catalog' to update. If not set, will only update the catalog_item_level_config field. Currently only fields that can be updated are catalog_item_level_config.<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					/** Perform the request */
					def withGoogleCloudRecommendationengineV1beta1Catalog(body: Schema.GoogleCloudRecommendationengineV1beta1Catalog) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudRecommendationengineV1beta1Catalog])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists all the catalog configurations associated with the project. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommendationengineV1beta1ListCatalogsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Maximum number of results to return. If unspecified, defaults to 50. Max allowed value is 1000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A page token, received from a previous `ListCatalogs` call. Provide this to retrieve the subsequent page. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecommendationengineV1beta1ListCatalogsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudRecommendationengineV1beta1ListCatalogsResponse]] = (fun: list) => fun.apply()
				}
				object operations {
					/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
					}
					/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
					}
				}
				object catalogItems {
					/** Creates a catalog item. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRecommendationengineV1beta1CatalogItem(body: Schema.GoogleCloudRecommendationengineV1beta1CatalogItem) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecommendationengineV1beta1CatalogItem])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/catalogItems").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Bulk import of multiple catalog items. Request processing may be synchronous. No partial updating supported. Non-existing items will be created. Operation.response is of type ImportResponse. Note that it is possible for a subset of the items to be successfully updated. */
					class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRecommendationengineV1beta1ImportCatalogItemsRequest(body: Schema.GoogleCloudRecommendationengineV1beta1ImportCatalogItemsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/catalogItems:import").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Deletes a catalog item. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, catalogItemsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/catalogItems/${catalogItemsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					/** Gets a specific catalog item. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommendationengineV1beta1CatalogItem]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecommendationengineV1beta1CatalogItem])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, catalogItemsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/catalogItems/${catalogItemsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudRecommendationengineV1beta1CatalogItem]] = (fun: get) => fun.apply()
					}
					/** Updates a catalog item. Partial updating is supported. Non-existing items will be created. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Indicates which fields in the provided 'item' to update. If not set, will by default update all fields.<br>Format: google-fieldmask */
						def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
						/** Perform the request */
						def withGoogleCloudRecommendationengineV1beta1CatalogItem(body: Schema.GoogleCloudRecommendationengineV1beta1CatalogItem) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudRecommendationengineV1beta1CatalogItem])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, catalogItemsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/catalogItems/${catalogItemsId}").addQueryStringParameters("name" -> name.toString))
					}
					/** Gets a list of catalog items. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommendationengineV1beta1ListCatalogItemsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Maximum number of results to return per page. If zero, the service will choose a reasonable default.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. The previous ListCatalogItemsResponse.next_page_token. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. Use of this field is not supported by version v1beta1. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecommendationengineV1beta1ListCatalogItemsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/catalogItems").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudRecommendationengineV1beta1ListCatalogItemsResponse]] = (fun: list) => fun.apply()
					}
				}
				object eventStores {
					object operations {
						/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, eventStoresId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/eventStores/${eventStoresId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
						}
						/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, eventStoresId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/eventStores/${eventStoresId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
						}
					}
					object predictionApiKeyRegistrations {
						/** Register an API key for use with predict method. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudRecommendationengineV1beta1CreatePredictionApiKeyRegistrationRequest(body: Schema.GoogleCloudRecommendationengineV1beta1CreatePredictionApiKeyRegistrationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecommendationengineV1beta1PredictionApiKeyRegistration])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, eventStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/eventStores/${eventStoresId}/predictionApiKeyRegistrations").addQueryStringParameters("parent" -> parent.toString))
						}
						/** List the registered apiKeys for use with predict method. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommendationengineV1beta1ListPredictionApiKeyRegistrationsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. Maximum number of results to return per page. If unset, the service will choose a reasonable default.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. The previous `ListPredictionApiKeyRegistration.nextPageToken`. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecommendationengineV1beta1ListPredictionApiKeyRegistrationsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, eventStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/eventStores/${eventStoresId}/predictionApiKeyRegistrations").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.GoogleCloudRecommendationengineV1beta1ListPredictionApiKeyRegistrationsResponse]] = (fun: list) => fun.apply()
						}
						/** Unregister an apiKey from using for predict method. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, eventStoresId :PlayApi, predictionApiKeyRegistrationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/eventStores/${eventStoresId}/predictionApiKeyRegistrations/${predictionApiKeyRegistrationsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
					}
					object placements {
						/** Makes a recommendation prediction. If using API Key based authentication, the API Key must be registered using the PredictionApiKeyRegistry service. [Learn more](https://cloud.google.com/recommendations-ai/docs/setting-up#register-key). */
						class predict(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudRecommendationengineV1beta1PredictRequest(body: Schema.GoogleCloudRecommendationengineV1beta1PredictRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecommendationengineV1beta1PredictResponse])
						}
						object predict {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, eventStoresId :PlayApi, placementsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): predict = new predict(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/eventStores/${eventStoresId}/placements/${placementsId}:predict").addQueryStringParameters("name" -> name.toString))
						}
					}
					object userEvents {
						/** Triggers a user event rejoin operation with latest catalog data. Events will not be annotated with detailed catalog information if catalog item is missing at the time the user event is ingested, and these events are stored as unjoined events with a limited usage on training and serving. This API can be used to trigger a 'join' operation on specified events with latest version of catalog items. It can also be used to correct events joined with wrong catalog items. */
						class rejoin(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudRecommendationengineV1beta1RejoinUserEventsRequest(body: Schema.GoogleCloudRecommendationengineV1beta1RejoinUserEventsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object rejoin {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, eventStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): rejoin = new rejoin(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/eventStores/${eventStoresId}/userEvents:rejoin").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Deletes permanently all user events specified by the filter provided. Depending on the number of events specified by the filter, this operation could take hours or days to complete. To test a filter, use the list command first. */
						class purge(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudRecommendationengineV1beta1PurgeUserEventsRequest(body: Schema.GoogleCloudRecommendationengineV1beta1PurgeUserEventsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object purge {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, eventStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): purge = new purge(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/eventStores/${eventStoresId}/userEvents:purge").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Bulk import of User events. Request processing might be synchronous. Events that already exist are skipped. Use this method for backfilling historical user events. Operation.response is of type ImportResponse. Note that it is possible for a subset of the items to be successfully inserted. Operation.metadata is of type ImportMetadata. */
						class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudRecommendationengineV1beta1ImportUserEventsRequest(body: Schema.GoogleCloudRecommendationengineV1beta1ImportUserEventsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object `import` {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, eventStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/eventStores/${eventStoresId}/userEvents:import").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Writes a single user event from the browser. This uses a GET request to due to browser restriction of POST-ing to a 3rd party domain. This method is used only by the Recommendations AI JavaScript pixel. Users should not call this method directly. */
						class collect(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleApiHttpBody]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. The url including cgi-parameters but excluding the hash fragment. The URL must be truncated to 1.5K bytes to conservatively be under the 2K bytes. This is often more useful than the referer url, because many browsers only send the domain for 3rd party requests. */
							def withUri(uri: String) = new collect(req.addQueryStringParameters("uri" -> uri.toString))
							/** Optional. The event timestamp in milliseconds. This prevents browser caching of otherwise identical get requests. The name is abbreviated to reduce the payload bytes.<br>Format: int64 */
							def withEts(ets: String) = new collect(req.addQueryStringParameters("ets" -> ets.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleApiHttpBody])
						}
						object collect {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, eventStoresId :PlayApi, parent: String, userEvent: String)(using signer: RequestSigner, ec: ExecutionContext): collect = new collect(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/eventStores/${eventStoresId}/userEvents:collect").addQueryStringParameters("parent" -> parent.toString, "userEvent" -> userEvent.toString))
							given Conversion[collect, Future[Schema.GoogleApiHttpBody]] = (fun: collect) => fun.apply()
						}
						/** Writes a single user event. */
						class write(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudRecommendationengineV1beta1UserEvent(body: Schema.GoogleCloudRecommendationengineV1beta1UserEvent) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRecommendationengineV1beta1UserEvent])
						}
						object write {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, eventStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): write = new write(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/eventStores/${eventStoresId}/userEvents:write").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Gets a list of user events within a time range, with potential filtering. The method does not list unjoined user events. Unjoined user event definition: when a user event is ingested from Recommendations AI User Event APIs, the catalog item included in the user event is connected with the current catalog. If a catalog item of the ingested event is not in the current catalog, it could lead to degraded model quality. This is called an unjoined event. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommendationengineV1beta1ListUserEventsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. Maximum number of results to return per page. If zero, the service will choose a reasonable default.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. The previous ListUserEventsResponse.next_page_token. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Optional. Filtering expression to specify restrictions over returned events. This is a sequence of terms, where each term applies some kind of a restriction to the returned user events. Use this expression to restrict results to a specific time range, or filter events by eventType. eg: eventTime > "2012-04-23T18:25:43.511Z" eventsMissingCatalogItems eventTime<"2012-04-23T18:25:43.511Z" eventType=search We expect only 3 types of fields: &#42; eventTime: this can be specified a maximum of 2 times, once with a less than operator and once with a greater than operator. The eventTime restrict should result in one contiguous valid eventTime range. &#42; eventType: only 1 eventType restriction can be specified. &#42; eventsMissingCatalogItems: specififying this will restrict results to events for which catalog items were not found in the catalog. The default behavior is to return only those events for which catalog items were found. Some examples of valid filters expressions: &#42; Example 1: eventTime > "2012-04-23T18:25:43.511Z" eventTime < "2012-04-23T18:30:43.511Z" &#42; Example 2: eventTime > "2012-04-23T18:25:43.511Z" eventType = detail-page-view &#42; Example 3: eventsMissingCatalogItems eventType = search eventTime < "2018-04-23T18:30:43.511Z" &#42; Example 4: eventTime > "2012-04-23T18:25:43.511Z" &#42; Example 5: eventType = search &#42; Example 6: eventsMissingCatalogItems */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRecommendationengineV1beta1ListUserEventsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, eventStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/eventStores/${eventStoresId}/userEvents").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.GoogleCloudRecommendationengineV1beta1ListUserEventsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
		}
	}
}
