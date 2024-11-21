package com.boresjo.play.api.google.recommendationengine

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://recommendationengine.googleapis.com/"

	object projects {
		object locations {
			object catalogs {
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. Indicates which fields in the provided 'catalog' to update. If not set, will only update the catalog_item_level_config field. Currently only fields that can be updated are catalog_item_level_config.<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					def withGoogleCloudRecommendationengineV1beta1Catalog(body: Schema.GoogleCloudRecommendationengineV1beta1Catalog) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudRecommendationengineV1beta1Catalog])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}")).addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommendationengineV1beta1ListCatalogsResponse]) {
					/** Optional. Maximum number of results to return. If unspecified, defaults to 50. Max allowed value is 1000.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. A page token, received from a previous `ListCatalogs` call. Provide this to retrieve the subsequent page. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRecommendationengineV1beta1ListCatalogsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudRecommendationengineV1beta1ListCatalogsResponse]] = (fun: list) => fun.apply()
				}
				object operations {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/operations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
					}
				}
				object catalogItems {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRecommendationengineV1beta1CatalogItem(body: Schema.GoogleCloudRecommendationengineV1beta1CatalogItem) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRecommendationengineV1beta1CatalogItem])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/catalogItems")).addQueryStringParameters("parent" -> parent.toString))
					}
					class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRecommendationengineV1beta1ImportCatalogItemsRequest(body: Schema.GoogleCloudRecommendationengineV1beta1ImportCatalogItemsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/catalogItems:import")).addQueryStringParameters("parent" -> parent.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, catalogItemsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/catalogItems/${catalogItemsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommendationengineV1beta1CatalogItem]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRecommendationengineV1beta1CatalogItem])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, catalogItemsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/catalogItems/${catalogItemsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudRecommendationengineV1beta1CatalogItem]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						/** Optional. Indicates which fields in the provided 'item' to update. If not set, will by default update all fields.<br>Format: google-fieldmask */
						def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
						def withGoogleCloudRecommendationengineV1beta1CatalogItem(body: Schema.GoogleCloudRecommendationengineV1beta1CatalogItem) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudRecommendationengineV1beta1CatalogItem])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, catalogItemsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/catalogItems/${catalogItemsId}")).addQueryStringParameters("name" -> name.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommendationengineV1beta1ListCatalogItemsResponse]) {
						/** Optional. Maximum number of results to return per page. If zero, the service will choose a reasonable default.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. The previous ListCatalogItemsResponse.next_page_token. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. Use of this field is not supported by version v1beta1. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRecommendationengineV1beta1ListCatalogItemsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/catalogItems")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudRecommendationengineV1beta1ListCatalogItemsResponse]] = (fun: list) => fun.apply()
					}
				}
				object eventStores {
					object operations {
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, eventStoresId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/eventStores/${eventStoresId}/operations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, eventStoresId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/eventStores/${eventStoresId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
						}
					}
					object predictionApiKeyRegistrations {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudRecommendationengineV1beta1CreatePredictionApiKeyRegistrationRequest(body: Schema.GoogleCloudRecommendationengineV1beta1CreatePredictionApiKeyRegistrationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRecommendationengineV1beta1PredictionApiKeyRegistration])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, eventStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/eventStores/${eventStoresId}/predictionApiKeyRegistrations")).addQueryStringParameters("parent" -> parent.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommendationengineV1beta1ListPredictionApiKeyRegistrationsResponse]) {
							/** Optional. Maximum number of results to return per page. If unset, the service will choose a reasonable default.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. The previous `ListPredictionApiKeyRegistration.nextPageToken`. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRecommendationengineV1beta1ListPredictionApiKeyRegistrationsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, eventStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/eventStores/${eventStoresId}/predictionApiKeyRegistrations")).addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.GoogleCloudRecommendationengineV1beta1ListPredictionApiKeyRegistrationsResponse]] = (fun: list) => fun.apply()
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, eventStoresId :PlayApi, predictionApiKeyRegistrationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/eventStores/${eventStoresId}/predictionApiKeyRegistrations/${predictionApiKeyRegistrationsId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
					}
					object placements {
						class predict(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudRecommendationengineV1beta1PredictRequest(body: Schema.GoogleCloudRecommendationengineV1beta1PredictRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRecommendationengineV1beta1PredictResponse])
						}
						object predict {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, eventStoresId :PlayApi, placementsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): predict = new predict(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/eventStores/${eventStoresId}/placements/${placementsId}:predict")).addQueryStringParameters("name" -> name.toString))
						}
					}
					object userEvents {
						class rejoin(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudRecommendationengineV1beta1RejoinUserEventsRequest(body: Schema.GoogleCloudRecommendationengineV1beta1RejoinUserEventsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object rejoin {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, eventStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): rejoin = new rejoin(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/eventStores/${eventStoresId}/userEvents:rejoin")).addQueryStringParameters("parent" -> parent.toString))
						}
						class purge(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudRecommendationengineV1beta1PurgeUserEventsRequest(body: Schema.GoogleCloudRecommendationengineV1beta1PurgeUserEventsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object purge {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, eventStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): purge = new purge(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/eventStores/${eventStoresId}/userEvents:purge")).addQueryStringParameters("parent" -> parent.toString))
						}
						class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudRecommendationengineV1beta1ImportUserEventsRequest(body: Schema.GoogleCloudRecommendationengineV1beta1ImportUserEventsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object `import` {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, eventStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/eventStores/${eventStoresId}/userEvents:import")).addQueryStringParameters("parent" -> parent.toString))
						}
						class collect(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleApiHttpBody]) {
							/** Optional. The url including cgi-parameters but excluding the hash fragment. The URL must be truncated to 1.5K bytes to conservatively be under the 2K bytes. This is often more useful than the referer url, because many browsers only send the domain for 3rd party requests. */
							def withUri(uri: String) = new collect(req.addQueryStringParameters("uri" -> uri.toString))
							/** Optional. The event timestamp in milliseconds. This prevents browser caching of otherwise identical get requests. The name is abbreviated to reduce the payload bytes.<br>Format: int64 */
							def withEts(ets: String) = new collect(req.addQueryStringParameters("ets" -> ets.toString))
							def apply() = req.execute("GET").map(_.json.as[Schema.GoogleApiHttpBody])
						}
						object collect {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, eventStoresId :PlayApi, parent: String, userEvent: String)(using auth: AuthToken, ec: ExecutionContext): collect = new collect(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/eventStores/${eventStoresId}/userEvents:collect")).addQueryStringParameters("parent" -> parent.toString, "userEvent" -> userEvent.toString))
							given Conversion[collect, Future[Schema.GoogleApiHttpBody]] = (fun: collect) => fun.apply()
						}
						class write(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudRecommendationengineV1beta1UserEvent(body: Schema.GoogleCloudRecommendationengineV1beta1UserEvent) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRecommendationengineV1beta1UserEvent])
						}
						object write {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, eventStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): write = new write(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/eventStores/${eventStoresId}/userEvents:write")).addQueryStringParameters("parent" -> parent.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRecommendationengineV1beta1ListUserEventsResponse]) {
							/** Optional. Maximum number of results to return per page. If zero, the service will choose a reasonable default.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. The previous ListUserEventsResponse.next_page_token. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Optional. Filtering expression to specify restrictions over returned events. This is a sequence of terms, where each term applies some kind of a restriction to the returned user events. Use this expression to restrict results to a specific time range, or filter events by eventType. eg: eventTime > "2012-04-23T18:25:43.511Z" eventsMissingCatalogItems eventTime<"2012-04-23T18:25:43.511Z" eventType=search We expect only 3 types of fields: &#42; eventTime: this can be specified a maximum of 2 times, once with a less than operator and once with a greater than operator. The eventTime restrict should result in one contiguous valid eventTime range. &#42; eventType: only 1 eventType restriction can be specified. &#42; eventsMissingCatalogItems: specififying this will restrict results to events for which catalog items were not found in the catalog. The default behavior is to return only those events for which catalog items were found. Some examples of valid filters expressions: &#42; Example 1: eventTime > "2012-04-23T18:25:43.511Z" eventTime < "2012-04-23T18:30:43.511Z" &#42; Example 2: eventTime > "2012-04-23T18:25:43.511Z" eventType = detail-page-view &#42; Example 3: eventsMissingCatalogItems eventType = search eventTime < "2018-04-23T18:30:43.511Z" &#42; Example 4: eventTime > "2012-04-23T18:25:43.511Z" &#42; Example 5: eventType = search &#42; Example 6: eventsMissingCatalogItems */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRecommendationengineV1beta1ListUserEventsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, eventStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1beta1/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/eventStores/${eventStoresId}/userEvents")).addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.GoogleCloudRecommendationengineV1beta1ListUserEventsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
		}
	}
}
