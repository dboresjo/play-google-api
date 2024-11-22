package com.boresjo.play.api.google.discoveryengine

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://discoveryengine.googleapis.com/"

	object projects {
		class provision(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleCloudDiscoveryengineV1ProvisionProjectRequest(body: Schema.GoogleCloudDiscoveryengineV1ProvisionProjectRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object provision {
			def apply(projectsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): provision = new provision(ws.url(BASE_URL + s"v1/projects/${projectsId}:provision").addQueryStringParameters("name" -> name.toString))
		}
		object operations {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object get {
				def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
			}
			class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleLongrunningCancelOperationRequest(body: Schema.GoogleLongrunningCancelOperationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object cancel {
				def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
			}
		}
		object locations {
			object operations {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
				}
			}
			object userEvents {
				class write(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDiscoveryengineV1UserEvent(body: Schema.GoogleCloudDiscoveryengineV1UserEvent) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1UserEvent])
				}
				object write {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, writeAsync: Boolean)(using auth: AuthToken, ec: ExecutionContext): write = new write(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/userEvents:write").addQueryStringParameters("parent" -> parent.toString, "writeAsync" -> writeAsync.toString))
				}
				class collect(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleApiHttpBody]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleApiHttpBody])
				}
				object collect {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, userEvent: String, uri: String, ets: String)(using auth: AuthToken, ec: ExecutionContext): collect = new collect(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/userEvents:collect").addQueryStringParameters("parent" -> parent.toString, "userEvent" -> userEvent.toString, "uri" -> uri.toString, "ets" -> ets.toString))
					given Conversion[collect, Future[Schema.GoogleApiHttpBody]] = (fun: collect) => fun.apply()
				}
				class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDiscoveryengineV1ImportUserEventsRequest(body: Schema.GoogleCloudDiscoveryengineV1ImportUserEventsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object `import` {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/userEvents:import").addQueryStringParameters("parent" -> parent.toString))
				}
			}
			object collections {
				object dataConnector {
					object operations {
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataConnector/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataConnector/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
						}
					}
				}
				object operations {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
					}
				}
				object dataStores {
					class getSiteSearchEngine(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1SiteSearchEngine]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1SiteSearchEngine])
					}
					object getSiteSearchEngine {
						def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getSiteSearchEngine = new getSiteSearchEngine(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/siteSearchEngine").addQueryStringParameters("name" -> name.toString))
						given Conversion[getSiteSearchEngine, Future[Schema.GoogleCloudDiscoveryengineV1SiteSearchEngine]] = (fun: getSiteSearchEngine) => fun.apply()
					}
					class completeQuery(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1CompleteQueryResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1CompleteQueryResponse])
					}
					object completeQuery {
						def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, dataStore: String, query: String, queryModel: String, userPseudoId: String, includeTailSuggestions: Boolean)(using auth: AuthToken, ec: ExecutionContext): completeQuery = new completeQuery(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}:completeQuery").addQueryStringParameters("dataStore" -> dataStore.toString, "query" -> query.toString, "queryModel" -> queryModel.toString, "userPseudoId" -> userPseudoId.toString, "includeTailSuggestions" -> includeTailSuggestions.toString))
						given Conversion[completeQuery, Future[Schema.GoogleCloudDiscoveryengineV1CompleteQueryResponse]] = (fun: completeQuery) => fun.apply()
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDiscoveryengineV1DataStore(body: Schema.GoogleCloudDiscoveryengineV1DataStore) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, parent: String, cmekConfigName: String, disableCmek: Boolean, dataStoreId: String, createAdvancedSiteSearch: Boolean, skipDefaultSchemaCreation: Boolean)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores").addQueryStringParameters("parent" -> parent.toString, "cmekConfigName" -> cmekConfigName.toString, "disableCmek" -> disableCmek.toString, "dataStoreId" -> dataStoreId.toString, "createAdvancedSiteSearch" -> createAdvancedSiteSearch.toString, "skipDefaultSchemaCreation" -> skipDefaultSchemaCreation.toString))
					}
					class trainCustomModel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDiscoveryengineV1TrainCustomModelRequest(body: Schema.GoogleCloudDiscoveryengineV1TrainCustomModelRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object trainCustomModel {
						def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, dataStore: String)(using auth: AuthToken, ec: ExecutionContext): trainCustomModel = new trainCustomModel(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}:trainCustomModel").addQueryStringParameters("dataStore" -> dataStore.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1DataStore]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1DataStore])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1DataStore]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDiscoveryengineV1DataStore(body: Schema.GoogleCloudDiscoveryengineV1DataStore) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1DataStore])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListDataStoresResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListDataStoresResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListDataStoresResponse]] = (fun: list) => fun.apply()
					}
					object conversations {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1Conversation(body: Schema.GoogleCloudDiscoveryengineV1Conversation) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Conversation])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/conversations").addQueryStringParameters("parent" -> parent.toString))
						}
						class converse(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1ConverseConversationRequest(body: Schema.GoogleCloudDiscoveryengineV1ConverseConversationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ConverseConversationResponse])
						}
						object converse {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, conversationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): converse = new converse(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/conversations/${conversationsId}:converse").addQueryStringParameters("name" -> name.toString))
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, conversationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/conversations/${conversationsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1Conversation]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Conversation])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, conversationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/conversations/${conversationsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1Conversation]] = (fun: get) => fun.apply()
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1Conversation(body: Schema.GoogleCloudDiscoveryengineV1Conversation) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Conversation])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, conversationsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/conversations/${conversationsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListConversationsResponse]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListConversationsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/conversations").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
							given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListConversationsResponse]] = (fun: list) => fun.apply()
						}
					}
					object operations {
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
						}
					}
					object userEvents {
						class write(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1UserEvent(body: Schema.GoogleCloudDiscoveryengineV1UserEvent) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1UserEvent])
						}
						object write {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String, writeAsync: Boolean)(using auth: AuthToken, ec: ExecutionContext): write = new write(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/userEvents:write").addQueryStringParameters("parent" -> parent.toString, "writeAsync" -> writeAsync.toString))
						}
						class collect(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleApiHttpBody]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleApiHttpBody])
						}
						object collect {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String, userEvent: String, uri: String, ets: String)(using auth: AuthToken, ec: ExecutionContext): collect = new collect(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/userEvents:collect").addQueryStringParameters("parent" -> parent.toString, "userEvent" -> userEvent.toString, "uri" -> uri.toString, "ets" -> ets.toString))
							given Conversion[collect, Future[Schema.GoogleApiHttpBody]] = (fun: collect) => fun.apply()
						}
						class purge(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1PurgeUserEventsRequest(body: Schema.GoogleCloudDiscoveryengineV1PurgeUserEventsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object purge {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): purge = new purge(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/userEvents:purge").addQueryStringParameters("parent" -> parent.toString))
						}
						class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1ImportUserEventsRequest(body: Schema.GoogleCloudDiscoveryengineV1ImportUserEventsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object `import` {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/userEvents:import").addQueryStringParameters("parent" -> parent.toString))
						}
					}
					object sessions {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1Session(body: Schema.GoogleCloudDiscoveryengineV1Session) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Session])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/sessions").addQueryStringParameters("parent" -> parent.toString))
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, sessionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/sessions/${sessionsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1Session]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Session])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, sessionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/sessions/${sessionsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1Session]] = (fun: get) => fun.apply()
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1Session(body: Schema.GoogleCloudDiscoveryengineV1Session) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Session])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, sessionsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/sessions/${sessionsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListSessionsResponse]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListSessionsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/sessions").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
							given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListSessionsResponse]] = (fun: list) => fun.apply()
						}
						object answers {
							class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1Answer]) {
								def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Answer])
							}
							object get {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, sessionsId :PlayApi, answersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/sessions/${sessionsId}/answers/${answersId}").addQueryStringParameters("name" -> name.toString))
								given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1Answer]] = (fun: get) => fun.apply()
							}
						}
					}
					object siteSearchEngine {
						class fetchDomainVerificationStatus(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1FetchDomainVerificationStatusResponse]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1FetchDomainVerificationStatusResponse])
						}
						object fetchDomainVerificationStatus {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, siteSearchEngine: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): fetchDomainVerificationStatus = new fetchDomainVerificationStatus(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/siteSearchEngine:fetchDomainVerificationStatus").addQueryStringParameters("siteSearchEngine" -> siteSearchEngine.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[fetchDomainVerificationStatus, Future[Schema.GoogleCloudDiscoveryengineV1FetchDomainVerificationStatusResponse]] = (fun: fetchDomainVerificationStatus) => fun.apply()
						}
						class batchVerifyTargetSites(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1BatchVerifyTargetSitesRequest(body: Schema.GoogleCloudDiscoveryengineV1BatchVerifyTargetSitesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object batchVerifyTargetSites {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchVerifyTargetSites = new batchVerifyTargetSites(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/siteSearchEngine:batchVerifyTargetSites").addQueryStringParameters("parent" -> parent.toString))
						}
						class enableAdvancedSiteSearch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1EnableAdvancedSiteSearchRequest(body: Schema.GoogleCloudDiscoveryengineV1EnableAdvancedSiteSearchRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object enableAdvancedSiteSearch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, siteSearchEngine: String)(using auth: AuthToken, ec: ExecutionContext): enableAdvancedSiteSearch = new enableAdvancedSiteSearch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/siteSearchEngine:enableAdvancedSiteSearch").addQueryStringParameters("siteSearchEngine" -> siteSearchEngine.toString))
						}
						class disableAdvancedSiteSearch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1DisableAdvancedSiteSearchRequest(body: Schema.GoogleCloudDiscoveryengineV1DisableAdvancedSiteSearchRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object disableAdvancedSiteSearch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, siteSearchEngine: String)(using auth: AuthToken, ec: ExecutionContext): disableAdvancedSiteSearch = new disableAdvancedSiteSearch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/siteSearchEngine:disableAdvancedSiteSearch").addQueryStringParameters("siteSearchEngine" -> siteSearchEngine.toString))
						}
						class recrawlUris(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1RecrawlUrisRequest(body: Schema.GoogleCloudDiscoveryengineV1RecrawlUrisRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object recrawlUris {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, siteSearchEngine: String)(using auth: AuthToken, ec: ExecutionContext): recrawlUris = new recrawlUris(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/siteSearchEngine:recrawlUris").addQueryStringParameters("siteSearchEngine" -> siteSearchEngine.toString))
						}
						object operations {
							class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
								def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
							}
							object list {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/siteSearchEngine/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
								given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
							}
							class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
								def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
							}
							object get {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/siteSearchEngine/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
								given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
							}
						}
						object targetSites {
							class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
								def withGoogleCloudDiscoveryengineV1TargetSite(body: Schema.GoogleCloudDiscoveryengineV1TargetSite) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
							}
							object create {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/siteSearchEngine/targetSites").addQueryStringParameters("parent" -> parent.toString))
							}
							class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
								def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
							}
							object delete {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, targetSitesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/siteSearchEngine/targetSites/${targetSitesId}").addQueryStringParameters("name" -> name.toString))
								given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
							}
							class batchCreate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
								def withGoogleCloudDiscoveryengineV1BatchCreateTargetSitesRequest(body: Schema.GoogleCloudDiscoveryengineV1BatchCreateTargetSitesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
							}
							object batchCreate {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchCreate = new batchCreate(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/siteSearchEngine/targetSites:batchCreate").addQueryStringParameters("parent" -> parent.toString))
							}
							class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1TargetSite]) {
								def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1TargetSite])
							}
							object get {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, targetSitesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/siteSearchEngine/targetSites/${targetSitesId}").addQueryStringParameters("name" -> name.toString))
								given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1TargetSite]] = (fun: get) => fun.apply()
							}
							class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
								def withGoogleCloudDiscoveryengineV1TargetSite(body: Schema.GoogleCloudDiscoveryengineV1TargetSite) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleLongrunningOperation])
							}
							object patch {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, targetSitesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/siteSearchEngine/targetSites/${targetSitesId}").addQueryStringParameters("name" -> name.toString))
							}
							class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListTargetSitesResponse]) {
								def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListTargetSitesResponse])
							}
							object list {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/siteSearchEngine/targetSites").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
								given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListTargetSitesResponse]] = (fun: list) => fun.apply()
							}
							object operations {
								class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
									def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
								}
								object list {
									def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/siteSearchEngine/targetSites/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
									given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
								}
								class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
									def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
								}
								object get {
									def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/siteSearchEngine/targetSites/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
									given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
								}
							}
						}
					}
					object models {
						object operations {
							class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
								def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
							}
							object list {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, modelsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/models/${modelsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
								given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
							}
							class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
								def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
							}
							object get {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, modelsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/models/${modelsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
								given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
							}
						}
					}
					object suggestionDenyListEntries {
						class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1ImportSuggestionDenyListEntriesRequest(body: Schema.GoogleCloudDiscoveryengineV1ImportSuggestionDenyListEntriesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object `import` {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/suggestionDenyListEntries:import").addQueryStringParameters("parent" -> parent.toString))
						}
						class purge(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1PurgeSuggestionDenyListEntriesRequest(body: Schema.GoogleCloudDiscoveryengineV1PurgeSuggestionDenyListEntriesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object purge {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): purge = new purge(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/suggestionDenyListEntries:purge").addQueryStringParameters("parent" -> parent.toString))
						}
					}
					object schemas {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1Schema(body: Schema.GoogleCloudDiscoveryengineV1Schema) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String, schemaId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/schemas").addQueryStringParameters("parent" -> parent.toString, "schemaId" -> schemaId.toString))
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
							def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, schemasId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/schemas/${schemasId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1Schema]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Schema])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, schemasId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/schemas/${schemasId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1Schema]] = (fun: get) => fun.apply()
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1Schema(body: Schema.GoogleCloudDiscoveryengineV1Schema) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, schemasId :PlayApi, name: String, allowMissing: Boolean)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/schemas/${schemasId}").addQueryStringParameters("name" -> name.toString, "allowMissing" -> allowMissing.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListSchemasResponse]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListSchemasResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/schemas").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListSchemasResponse]] = (fun: list) => fun.apply()
						}
						object operations {
							class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
								def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
							}
							object list {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, schemasId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/schemas/${schemasId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
								given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
							}
							class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
								def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
							}
							object get {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, schemasId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/schemas/${schemasId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
								given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
							}
						}
					}
					object servingConfigs {
						class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1SearchRequest(body: Schema.GoogleCloudDiscoveryengineV1SearchRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1SearchResponse])
						}
						object search {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, servingConfigsId :PlayApi, servingConfig: String)(using auth: AuthToken, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/servingConfigs/${servingConfigsId}:search").addQueryStringParameters("servingConfig" -> servingConfig.toString))
						}
						class searchLite(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1SearchRequest(body: Schema.GoogleCloudDiscoveryengineV1SearchRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1SearchResponse])
						}
						object searchLite {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, servingConfigsId :PlayApi, servingConfig: String)(using auth: AuthToken, ec: ExecutionContext): searchLite = new searchLite(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/servingConfigs/${servingConfigsId}:searchLite").addQueryStringParameters("servingConfig" -> servingConfig.toString))
						}
						class answer(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1AnswerQueryRequest(body: Schema.GoogleCloudDiscoveryengineV1AnswerQueryRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1AnswerQueryResponse])
						}
						object answer {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, servingConfigsId :PlayApi, servingConfig: String)(using auth: AuthToken, ec: ExecutionContext): answer = new answer(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/servingConfigs/${servingConfigsId}:answer").addQueryStringParameters("servingConfig" -> servingConfig.toString))
						}
						class recommend(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1RecommendRequest(body: Schema.GoogleCloudDiscoveryengineV1RecommendRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1RecommendResponse])
						}
						object recommend {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, servingConfigsId :PlayApi, servingConfig: String)(using auth: AuthToken, ec: ExecutionContext): recommend = new recommend(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/servingConfigs/${servingConfigsId}:recommend").addQueryStringParameters("servingConfig" -> servingConfig.toString))
						}
					}
					object branches {
						class batchGetDocumentsMetadata(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1BatchGetDocumentsMetadataResponse]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1BatchGetDocumentsMetadataResponse])
						}
						object batchGetDocumentsMetadata {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, parent: String, matcherUrisMatcherUris: String, matcherFhirMatcherFhirResources: String)(using auth: AuthToken, ec: ExecutionContext): batchGetDocumentsMetadata = new batchGetDocumentsMetadata(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/branches/${branchesId}/batchGetDocumentsMetadata").addQueryStringParameters("parent" -> parent.toString, "matcher.urisMatcher.uris" -> matcherUrisMatcherUris.toString, "matcher.fhirMatcher.fhirResources" -> matcherFhirMatcherFhirResources.toString))
							given Conversion[batchGetDocumentsMetadata, Future[Schema.GoogleCloudDiscoveryengineV1BatchGetDocumentsMetadataResponse]] = (fun: batchGetDocumentsMetadata) => fun.apply()
						}
						object operations {
							class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
								def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
							}
							object list {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/branches/${branchesId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
								given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
							}
							class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
								def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
							}
							object get {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/branches/${branchesId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
								given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
							}
							class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
								def withGoogleLongrunningCancelOperationRequest(body: Schema.GoogleLongrunningCancelOperationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
							}
							object cancel {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/branches/${branchesId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
							}
						}
						object documents {
							class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
								def withGoogleCloudDiscoveryengineV1Document(body: Schema.GoogleCloudDiscoveryengineV1Document) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Document])
							}
							object create {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, parent: String, documentId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/branches/${branchesId}/documents").addQueryStringParameters("parent" -> parent.toString, "documentId" -> documentId.toString))
							}
							class purge(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
								def withGoogleCloudDiscoveryengineV1PurgeDocumentsRequest(body: Schema.GoogleCloudDiscoveryengineV1PurgeDocumentsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
							}
							object purge {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): purge = new purge(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/branches/${branchesId}/documents:purge").addQueryStringParameters("parent" -> parent.toString))
							}
							class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
								def withGoogleCloudDiscoveryengineV1ImportDocumentsRequest(body: Schema.GoogleCloudDiscoveryengineV1ImportDocumentsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
							}
							object `import` {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/branches/${branchesId}/documents:import").addQueryStringParameters("parent" -> parent.toString))
							}
							class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
								def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
							}
							object delete {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, documentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/branches/${branchesId}/documents/${documentsId}").addQueryStringParameters("name" -> name.toString))
								given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
							}
							class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1Document]) {
								def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Document])
							}
							object get {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, documentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/branches/${branchesId}/documents/${documentsId}").addQueryStringParameters("name" -> name.toString))
								given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1Document]] = (fun: get) => fun.apply()
							}
							class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
								def withGoogleCloudDiscoveryengineV1Document(body: Schema.GoogleCloudDiscoveryengineV1Document) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Document])
							}
							object patch {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, documentsId :PlayApi, name: String, allowMissing: Boolean, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/branches/${branchesId}/documents/${documentsId}").addQueryStringParameters("name" -> name.toString, "allowMissing" -> allowMissing.toString, "updateMask" -> updateMask.toString))
							}
							class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListDocumentsResponse]) {
								def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListDocumentsResponse])
							}
							object list {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/branches/${branchesId}/documents").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
								given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListDocumentsResponse]] = (fun: list) => fun.apply()
							}
						}
					}
					object completionSuggestions {
						class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1ImportCompletionSuggestionsRequest(body: Schema.GoogleCloudDiscoveryengineV1ImportCompletionSuggestionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object `import` {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/completionSuggestions:import").addQueryStringParameters("parent" -> parent.toString))
						}
						class purge(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1PurgeCompletionSuggestionsRequest(body: Schema.GoogleCloudDiscoveryengineV1PurgeCompletionSuggestionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object purge {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): purge = new purge(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/completionSuggestions:purge").addQueryStringParameters("parent" -> parent.toString))
						}
					}
					object customModels {
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListCustomModelsResponse]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListCustomModelsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, dataStore: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/customModels").addQueryStringParameters("dataStore" -> dataStore.toString))
							given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListCustomModelsResponse]] = (fun: list) => fun.apply()
						}
					}
					object controls {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1Control(body: Schema.GoogleCloudDiscoveryengineV1Control) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Control])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String, controlId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/controls").addQueryStringParameters("parent" -> parent.toString, "controlId" -> controlId.toString))
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, controlsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/controls/${controlsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1Control]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Control])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, controlsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/controls/${controlsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1Control]] = (fun: get) => fun.apply()
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							/** Optional. Indicates which fields in the provided Control to update. The following are NOT supported: &#42; Control.name &#42; Control.solution_type If not set or empty, all supported fields are updated.<br>Format: google-fieldmask */
							def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
							def withGoogleCloudDiscoveryengineV1Control(body: Schema.GoogleCloudDiscoveryengineV1Control) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Control])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, controlsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/controls/${controlsId}").addQueryStringParameters("name" -> name.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListControlsResponse]) {
							/** Optional. Maximum number of results to return. If unspecified, defaults to 50. Max allowed value is 1000.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. A page token, received from a previous `ListControls` call. Provide this to retrieve the subsequent page. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Optional. A filter to apply on the list results. Supported features: &#42; List all the products under the parent branch if filter is unset. Currently this field is unsupported. */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListControlsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/controls").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListControlsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
				object engines {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDiscoveryengineV1Engine(body: Schema.GoogleCloudDiscoveryengineV1Engine) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, parent: String, engineId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines").addQueryStringParameters("parent" -> parent.toString, "engineId" -> engineId.toString))
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1Engine]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Engine])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1Engine]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDiscoveryengineV1Engine(body: Schema.GoogleCloudDiscoveryengineV1Engine) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Engine])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListEnginesResponse]) {
						/** Optional. Not supported.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Not supported. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. Filter by solution type. For example: solution_type=SOLUTION_TYPE_SEARCH */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListEnginesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListEnginesResponse]] = (fun: list) => fun.apply()
					}
					object conversations {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1Conversation(body: Schema.GoogleCloudDiscoveryengineV1Conversation) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Conversation])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/conversations").addQueryStringParameters("parent" -> parent.toString))
						}
						class converse(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1ConverseConversationRequest(body: Schema.GoogleCloudDiscoveryengineV1ConverseConversationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ConverseConversationResponse])
						}
						object converse {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, conversationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): converse = new converse(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/conversations/${conversationsId}:converse").addQueryStringParameters("name" -> name.toString))
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, conversationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/conversations/${conversationsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1Conversation]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Conversation])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, conversationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/conversations/${conversationsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1Conversation]] = (fun: get) => fun.apply()
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1Conversation(body: Schema.GoogleCloudDiscoveryengineV1Conversation) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Conversation])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, conversationsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/conversations/${conversationsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListConversationsResponse]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListConversationsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/conversations").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
							given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListConversationsResponse]] = (fun: list) => fun.apply()
						}
					}
					object operations {
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
						}
					}
					object sessions {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1Session(body: Schema.GoogleCloudDiscoveryengineV1Session) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Session])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/sessions").addQueryStringParameters("parent" -> parent.toString))
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, sessionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/sessions/${sessionsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1Session]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Session])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, sessionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/sessions/${sessionsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1Session]] = (fun: get) => fun.apply()
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1Session(body: Schema.GoogleCloudDiscoveryengineV1Session) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Session])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, sessionsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/sessions/${sessionsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListSessionsResponse]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListSessionsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/sessions").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
							given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListSessionsResponse]] = (fun: list) => fun.apply()
						}
						object answers {
							class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1Answer]) {
								def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Answer])
							}
							object get {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, sessionsId :PlayApi, answersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/sessions/${sessionsId}/answers/${answersId}").addQueryStringParameters("name" -> name.toString))
								given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1Answer]] = (fun: get) => fun.apply()
							}
						}
					}
					object servingConfigs {
						class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1SearchRequest(body: Schema.GoogleCloudDiscoveryengineV1SearchRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1SearchResponse])
						}
						object search {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, servingConfigsId :PlayApi, servingConfig: String)(using auth: AuthToken, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/servingConfigs/${servingConfigsId}:search").addQueryStringParameters("servingConfig" -> servingConfig.toString))
						}
						class searchLite(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1SearchRequest(body: Schema.GoogleCloudDiscoveryengineV1SearchRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1SearchResponse])
						}
						object searchLite {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, servingConfigsId :PlayApi, servingConfig: String)(using auth: AuthToken, ec: ExecutionContext): searchLite = new searchLite(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/servingConfigs/${servingConfigsId}:searchLite").addQueryStringParameters("servingConfig" -> servingConfig.toString))
						}
						class answer(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1AnswerQueryRequest(body: Schema.GoogleCloudDiscoveryengineV1AnswerQueryRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1AnswerQueryResponse])
						}
						object answer {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, servingConfigsId :PlayApi, servingConfig: String)(using auth: AuthToken, ec: ExecutionContext): answer = new answer(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/servingConfigs/${servingConfigsId}:answer").addQueryStringParameters("servingConfig" -> servingConfig.toString))
						}
						class recommend(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1RecommendRequest(body: Schema.GoogleCloudDiscoveryengineV1RecommendRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1RecommendResponse])
						}
						object recommend {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, servingConfigsId :PlayApi, servingConfig: String)(using auth: AuthToken, ec: ExecutionContext): recommend = new recommend(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/servingConfigs/${servingConfigsId}:recommend").addQueryStringParameters("servingConfig" -> servingConfig.toString))
						}
					}
					object controls {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1Control(body: Schema.GoogleCloudDiscoveryengineV1Control) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Control])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, parent: String, controlId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/controls").addQueryStringParameters("parent" -> parent.toString, "controlId" -> controlId.toString))
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, controlsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/controls/${controlsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1Control]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Control])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, controlsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/controls/${controlsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1Control]] = (fun: get) => fun.apply()
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							/** Optional. Indicates which fields in the provided Control to update. The following are NOT supported: &#42; Control.name &#42; Control.solution_type If not set or empty, all supported fields are updated.<br>Format: google-fieldmask */
							def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
							def withGoogleCloudDiscoveryengineV1Control(body: Schema.GoogleCloudDiscoveryengineV1Control) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Control])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, controlsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/controls/${controlsId}").addQueryStringParameters("name" -> name.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListControlsResponse]) {
							/** Optional. Maximum number of results to return. If unspecified, defaults to 50. Max allowed value is 1000.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. A page token, received from a previous `ListControls` call. Provide this to retrieve the subsequent page. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Optional. A filter to apply on the list results. Supported features: &#42; List all the products under the parent branch if filter is unset. Currently this field is unsupported. */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListControlsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/controls").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListControlsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
			object rankingConfigs {
				class rank(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDiscoveryengineV1RankRequest(body: Schema.GoogleCloudDiscoveryengineV1RankRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1RankResponse])
				}
				object rank {
					def apply(projectsId :PlayApi, locationsId :PlayApi, rankingConfigsId :PlayApi, rankingConfig: String)(using auth: AuthToken, ec: ExecutionContext): rank = new rank(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/rankingConfigs/${rankingConfigsId}:rank").addQueryStringParameters("rankingConfig" -> rankingConfig.toString))
				}
			}
			object dataStores {
				class getSiteSearchEngine(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1SiteSearchEngine]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1SiteSearchEngine])
				}
				object getSiteSearchEngine {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getSiteSearchEngine = new getSiteSearchEngine(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/siteSearchEngine").addQueryStringParameters("name" -> name.toString))
					given Conversion[getSiteSearchEngine, Future[Schema.GoogleCloudDiscoveryengineV1SiteSearchEngine]] = (fun: getSiteSearchEngine) => fun.apply()
				}
				class completeQuery(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1CompleteQueryResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1CompleteQueryResponse])
				}
				object completeQuery {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, dataStore: String, query: String, queryModel: String, userPseudoId: String, includeTailSuggestions: Boolean)(using auth: AuthToken, ec: ExecutionContext): completeQuery = new completeQuery(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}:completeQuery").addQueryStringParameters("dataStore" -> dataStore.toString, "query" -> query.toString, "queryModel" -> queryModel.toString, "userPseudoId" -> userPseudoId.toString, "includeTailSuggestions" -> includeTailSuggestions.toString))
					given Conversion[completeQuery, Future[Schema.GoogleCloudDiscoveryengineV1CompleteQueryResponse]] = (fun: completeQuery) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDiscoveryengineV1DataStore(body: Schema.GoogleCloudDiscoveryengineV1DataStore) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, cmekConfigName: String, disableCmek: Boolean, dataStoreId: String, createAdvancedSiteSearch: Boolean, skipDefaultSchemaCreation: Boolean)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores").addQueryStringParameters("parent" -> parent.toString, "cmekConfigName" -> cmekConfigName.toString, "disableCmek" -> disableCmek.toString, "dataStoreId" -> dataStoreId.toString, "createAdvancedSiteSearch" -> createAdvancedSiteSearch.toString, "skipDefaultSchemaCreation" -> skipDefaultSchemaCreation.toString))
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDiscoveryengineV1DataStore(body: Schema.GoogleCloudDiscoveryengineV1DataStore) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1DataStore])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1DataStore]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1DataStore])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1DataStore]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListDataStoresResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListDataStoresResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListDataStoresResponse]] = (fun: list) => fun.apply()
				}
				object conversations {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDiscoveryengineV1Conversation(body: Schema.GoogleCloudDiscoveryengineV1Conversation) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Conversation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/conversations").addQueryStringParameters("parent" -> parent.toString))
					}
					class converse(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDiscoveryengineV1ConverseConversationRequest(body: Schema.GoogleCloudDiscoveryengineV1ConverseConversationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ConverseConversationResponse])
					}
					object converse {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, conversationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): converse = new converse(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/conversations/${conversationsId}:converse").addQueryStringParameters("name" -> name.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, conversationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/conversations/${conversationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1Conversation]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Conversation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, conversationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/conversations/${conversationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1Conversation]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDiscoveryengineV1Conversation(body: Schema.GoogleCloudDiscoveryengineV1Conversation) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Conversation])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, conversationsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/conversations/${conversationsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListConversationsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListConversationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/conversations").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
						given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListConversationsResponse]] = (fun: list) => fun.apply()
					}
				}
				object operations {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
					}
				}
				object userEvents {
					class write(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDiscoveryengineV1UserEvent(body: Schema.GoogleCloudDiscoveryengineV1UserEvent) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1UserEvent])
					}
					object write {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String, writeAsync: Boolean)(using auth: AuthToken, ec: ExecutionContext): write = new write(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/userEvents:write").addQueryStringParameters("parent" -> parent.toString, "writeAsync" -> writeAsync.toString))
					}
					class collect(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleApiHttpBody]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleApiHttpBody])
					}
					object collect {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String, userEvent: String, uri: String, ets: String)(using auth: AuthToken, ec: ExecutionContext): collect = new collect(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/userEvents:collect").addQueryStringParameters("parent" -> parent.toString, "userEvent" -> userEvent.toString, "uri" -> uri.toString, "ets" -> ets.toString))
						given Conversion[collect, Future[Schema.GoogleApiHttpBody]] = (fun: collect) => fun.apply()
					}
					class purge(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDiscoveryengineV1PurgeUserEventsRequest(body: Schema.GoogleCloudDiscoveryengineV1PurgeUserEventsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object purge {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): purge = new purge(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/userEvents:purge").addQueryStringParameters("parent" -> parent.toString))
					}
					class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDiscoveryengineV1ImportUserEventsRequest(body: Schema.GoogleCloudDiscoveryengineV1ImportUserEventsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/userEvents:import").addQueryStringParameters("parent" -> parent.toString))
					}
				}
				object sessions {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDiscoveryengineV1Session(body: Schema.GoogleCloudDiscoveryengineV1Session) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Session])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/sessions").addQueryStringParameters("parent" -> parent.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, sessionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/sessions/${sessionsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1Session]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Session])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, sessionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/sessions/${sessionsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1Session]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDiscoveryengineV1Session(body: Schema.GoogleCloudDiscoveryengineV1Session) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Session])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, sessionsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/sessions/${sessionsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListSessionsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListSessionsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/sessions").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
						given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListSessionsResponse]] = (fun: list) => fun.apply()
					}
					object answers {
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1Answer]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Answer])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, sessionsId :PlayApi, answersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/sessions/${sessionsId}/answers/${answersId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1Answer]] = (fun: get) => fun.apply()
						}
					}
				}
				object siteSearchEngine {
					class enableAdvancedSiteSearch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDiscoveryengineV1EnableAdvancedSiteSearchRequest(body: Schema.GoogleCloudDiscoveryengineV1EnableAdvancedSiteSearchRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object enableAdvancedSiteSearch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, siteSearchEngine: String)(using auth: AuthToken, ec: ExecutionContext): enableAdvancedSiteSearch = new enableAdvancedSiteSearch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/siteSearchEngine:enableAdvancedSiteSearch").addQueryStringParameters("siteSearchEngine" -> siteSearchEngine.toString))
					}
					class disableAdvancedSiteSearch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDiscoveryengineV1DisableAdvancedSiteSearchRequest(body: Schema.GoogleCloudDiscoveryengineV1DisableAdvancedSiteSearchRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object disableAdvancedSiteSearch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, siteSearchEngine: String)(using auth: AuthToken, ec: ExecutionContext): disableAdvancedSiteSearch = new disableAdvancedSiteSearch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/siteSearchEngine:disableAdvancedSiteSearch").addQueryStringParameters("siteSearchEngine" -> siteSearchEngine.toString))
					}
					class recrawlUris(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDiscoveryengineV1RecrawlUrisRequest(body: Schema.GoogleCloudDiscoveryengineV1RecrawlUrisRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object recrawlUris {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, siteSearchEngine: String)(using auth: AuthToken, ec: ExecutionContext): recrawlUris = new recrawlUris(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/siteSearchEngine:recrawlUris").addQueryStringParameters("siteSearchEngine" -> siteSearchEngine.toString))
					}
					object targetSites {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1TargetSite(body: Schema.GoogleCloudDiscoveryengineV1TargetSite) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/siteSearchEngine/targetSites").addQueryStringParameters("parent" -> parent.toString))
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
							def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, targetSitesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/siteSearchEngine/targetSites/${targetSitesId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
						}
						class batchCreate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1BatchCreateTargetSitesRequest(body: Schema.GoogleCloudDiscoveryengineV1BatchCreateTargetSitesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object batchCreate {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchCreate = new batchCreate(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/siteSearchEngine/targetSites:batchCreate").addQueryStringParameters("parent" -> parent.toString))
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1TargetSite]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1TargetSite])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, targetSitesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/siteSearchEngine/targetSites/${targetSitesId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1TargetSite]] = (fun: get) => fun.apply()
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1TargetSite(body: Schema.GoogleCloudDiscoveryengineV1TargetSite) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, targetSitesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/siteSearchEngine/targetSites/${targetSitesId}").addQueryStringParameters("name" -> name.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListTargetSitesResponse]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListTargetSitesResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/siteSearchEngine/targetSites").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListTargetSitesResponse]] = (fun: list) => fun.apply()
						}
					}
				}
				object models {
					object operations {
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, modelsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/models/${modelsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, modelsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/models/${modelsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
						}
					}
				}
				object suggestionDenyListEntries {
					class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDiscoveryengineV1ImportSuggestionDenyListEntriesRequest(body: Schema.GoogleCloudDiscoveryengineV1ImportSuggestionDenyListEntriesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/suggestionDenyListEntries:import").addQueryStringParameters("parent" -> parent.toString))
					}
					class purge(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDiscoveryengineV1PurgeSuggestionDenyListEntriesRequest(body: Schema.GoogleCloudDiscoveryengineV1PurgeSuggestionDenyListEntriesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object purge {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): purge = new purge(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/suggestionDenyListEntries:purge").addQueryStringParameters("parent" -> parent.toString))
					}
				}
				object schemas {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDiscoveryengineV1Schema(body: Schema.GoogleCloudDiscoveryengineV1Schema) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String, schemaId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/schemas").addQueryStringParameters("parent" -> parent.toString, "schemaId" -> schemaId.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, schemasId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/schemas/${schemasId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1Schema]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Schema])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, schemasId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/schemas/${schemasId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1Schema]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDiscoveryengineV1Schema(body: Schema.GoogleCloudDiscoveryengineV1Schema) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, schemasId :PlayApi, name: String, allowMissing: Boolean)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/schemas/${schemasId}").addQueryStringParameters("name" -> name.toString, "allowMissing" -> allowMissing.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListSchemasResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListSchemasResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/schemas").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListSchemasResponse]] = (fun: list) => fun.apply()
					}
				}
				object servingConfigs {
					class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDiscoveryengineV1SearchRequest(body: Schema.GoogleCloudDiscoveryengineV1SearchRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1SearchResponse])
					}
					object search {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, servingConfigsId :PlayApi, servingConfig: String)(using auth: AuthToken, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/servingConfigs/${servingConfigsId}:search").addQueryStringParameters("servingConfig" -> servingConfig.toString))
					}
					class searchLite(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDiscoveryengineV1SearchRequest(body: Schema.GoogleCloudDiscoveryengineV1SearchRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1SearchResponse])
					}
					object searchLite {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, servingConfigsId :PlayApi, servingConfig: String)(using auth: AuthToken, ec: ExecutionContext): searchLite = new searchLite(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/servingConfigs/${servingConfigsId}:searchLite").addQueryStringParameters("servingConfig" -> servingConfig.toString))
					}
					class answer(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDiscoveryengineV1AnswerQueryRequest(body: Schema.GoogleCloudDiscoveryengineV1AnswerQueryRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1AnswerQueryResponse])
					}
					object answer {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, servingConfigsId :PlayApi, servingConfig: String)(using auth: AuthToken, ec: ExecutionContext): answer = new answer(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/servingConfigs/${servingConfigsId}:answer").addQueryStringParameters("servingConfig" -> servingConfig.toString))
					}
					class recommend(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDiscoveryengineV1RecommendRequest(body: Schema.GoogleCloudDiscoveryengineV1RecommendRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1RecommendResponse])
					}
					object recommend {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, servingConfigsId :PlayApi, servingConfig: String)(using auth: AuthToken, ec: ExecutionContext): recommend = new recommend(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/servingConfigs/${servingConfigsId}:recommend").addQueryStringParameters("servingConfig" -> servingConfig.toString))
					}
				}
				object branches {
					class batchGetDocumentsMetadata(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1BatchGetDocumentsMetadataResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1BatchGetDocumentsMetadataResponse])
					}
					object batchGetDocumentsMetadata {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, parent: String, matcherUrisMatcherUris: String, matcherFhirMatcherFhirResources: String)(using auth: AuthToken, ec: ExecutionContext): batchGetDocumentsMetadata = new batchGetDocumentsMetadata(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/branches/${branchesId}/batchGetDocumentsMetadata").addQueryStringParameters("parent" -> parent.toString, "matcher.urisMatcher.uris" -> matcherUrisMatcherUris.toString, "matcher.fhirMatcher.fhirResources" -> matcherFhirMatcherFhirResources.toString))
						given Conversion[batchGetDocumentsMetadata, Future[Schema.GoogleCloudDiscoveryengineV1BatchGetDocumentsMetadataResponse]] = (fun: batchGetDocumentsMetadata) => fun.apply()
					}
					object operations {
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/branches/${branchesId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/branches/${branchesId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
						}
						class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleLongrunningCancelOperationRequest(body: Schema.GoogleLongrunningCancelOperationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object cancel {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/branches/${branchesId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
						}
					}
					object documents {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1Document(body: Schema.GoogleCloudDiscoveryengineV1Document) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Document])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, parent: String, documentId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/branches/${branchesId}/documents").addQueryStringParameters("parent" -> parent.toString, "documentId" -> documentId.toString))
						}
						class purge(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1PurgeDocumentsRequest(body: Schema.GoogleCloudDiscoveryengineV1PurgeDocumentsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object purge {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): purge = new purge(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/branches/${branchesId}/documents:purge").addQueryStringParameters("parent" -> parent.toString))
						}
						class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1ImportDocumentsRequest(body: Schema.GoogleCloudDiscoveryengineV1ImportDocumentsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object `import` {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/branches/${branchesId}/documents:import").addQueryStringParameters("parent" -> parent.toString))
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, documentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/branches/${branchesId}/documents/${documentsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1Document]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Document])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, documentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/branches/${branchesId}/documents/${documentsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1Document]] = (fun: get) => fun.apply()
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDiscoveryengineV1Document(body: Schema.GoogleCloudDiscoveryengineV1Document) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Document])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, documentsId :PlayApi, name: String, allowMissing: Boolean, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/branches/${branchesId}/documents/${documentsId}").addQueryStringParameters("name" -> name.toString, "allowMissing" -> allowMissing.toString, "updateMask" -> updateMask.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListDocumentsResponse]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListDocumentsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/branches/${branchesId}/documents").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListDocumentsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
				object completionSuggestions {
					class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDiscoveryengineV1ImportCompletionSuggestionsRequest(body: Schema.GoogleCloudDiscoveryengineV1ImportCompletionSuggestionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/completionSuggestions:import").addQueryStringParameters("parent" -> parent.toString))
					}
					class purge(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDiscoveryengineV1PurgeCompletionSuggestionsRequest(body: Schema.GoogleCloudDiscoveryengineV1PurgeCompletionSuggestionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object purge {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): purge = new purge(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/completionSuggestions:purge").addQueryStringParameters("parent" -> parent.toString))
					}
				}
				object controls {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDiscoveryengineV1Control(body: Schema.GoogleCloudDiscoveryengineV1Control) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Control])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String, controlId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/controls").addQueryStringParameters("parent" -> parent.toString, "controlId" -> controlId.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, controlsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/controls/${controlsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1Control]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Control])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, controlsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/controls/${controlsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1Control]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						/** Optional. Indicates which fields in the provided Control to update. The following are NOT supported: &#42; Control.name &#42; Control.solution_type If not set or empty, all supported fields are updated.<br>Format: google-fieldmask */
						def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
						def withGoogleCloudDiscoveryengineV1Control(body: Schema.GoogleCloudDiscoveryengineV1Control) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Control])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, controlsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/controls/${controlsId}").addQueryStringParameters("name" -> name.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListControlsResponse]) {
						/** Optional. Maximum number of results to return. If unspecified, defaults to 50. Max allowed value is 1000.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A page token, received from a previous `ListControls` call. Provide this to retrieve the subsequent page. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. A filter to apply on the list results. Supported features: &#42; List all the products under the parent branch if filter is unset. Currently this field is unsupported. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListControlsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/controls").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListControlsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object identityMappingStores {
				object operations {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, identityMappingStoresId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/identityMappingStores/${identityMappingStoresId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, identityMappingStoresId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/identityMappingStores/${identityMappingStoresId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
					}
				}
			}
			object groundingConfigs {
				class check(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDiscoveryengineV1CheckGroundingRequest(body: Schema.GoogleCloudDiscoveryengineV1CheckGroundingRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1CheckGroundingResponse])
				}
				object check {
					def apply(projectsId :PlayApi, locationsId :PlayApi, groundingConfigsId :PlayApi, groundingConfig: String)(using auth: AuthToken, ec: ExecutionContext): check = new check(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/groundingConfigs/${groundingConfigsId}:check").addQueryStringParameters("groundingConfig" -> groundingConfig.toString))
				}
			}
		}
	}
}
