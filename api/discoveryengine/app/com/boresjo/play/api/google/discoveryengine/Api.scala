package com.boresjo.play.api.google.discoveryengine

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

	private val BASE_URL = "https://discoveryengine.googleapis.com/"

	object projects {
		/** Provisions the project resource. During the process, related systems will get prepared and initialized. Caller must read the [Terms for data use](https://cloud.google.com/retail/data-use-terms), and optionally specify in request to provide consent to that service terms. */
		class provision(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withGoogleCloudDiscoveryengineV1ProvisionProjectRequest(body: Schema.GoogleCloudDiscoveryengineV1ProvisionProjectRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object provision {
			def apply(projectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): provision = new provision(ws.url(BASE_URL + s"v1/projects/${projectsId}:provision").addQueryStringParameters("name" -> name.toString))
		}
		object operations {
			/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
			}
			/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object get {
				def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
			}
			/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
			class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleLongrunningCancelOperationRequest(body: Schema.GoogleLongrunningCancelOperationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object cancel {
				def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
			}
		}
		object locations {
			object operations {
				/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
				}
				/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
				}
			}
			object userEvents {
				/** Writes a single user event. */
				class write(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudDiscoveryengineV1UserEvent(body: Schema.GoogleCloudDiscoveryengineV1UserEvent) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1UserEvent])
				}
				object write {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, writeAsync: Boolean)(using signer: RequestSigner, ec: ExecutionContext): write = new write(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/userEvents:write").addQueryStringParameters("parent" -> parent.toString, "writeAsync" -> writeAsync.toString))
				}
				/** Writes a single user event from the browser. This uses a GET request to due to browser restriction of POST-ing to a third-party domain. This method is used only by the Discovery Engine API JavaScript pixel and Google Tag Manager. Users should not call this method directly. */
				class collect(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleApiHttpBody]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleApiHttpBody])
				}
				object collect {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, userEvent: String, uri: String, ets: String)(using signer: RequestSigner, ec: ExecutionContext): collect = new collect(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/userEvents:collect").addQueryStringParameters("parent" -> parent.toString, "userEvent" -> userEvent.toString, "uri" -> uri.toString, "ets" -> ets.toString))
					given Conversion[collect, Future[Schema.GoogleApiHttpBody]] = (fun: collect) => fun.apply()
				}
				/** Bulk import of user events. Request processing might be synchronous. Events that already exist are skipped. Use this method for backfilling historical user events. Operation.response is of type ImportResponse. Note that it is possible for a subset of the items to be successfully inserted. Operation.metadata is of type ImportMetadata. */
				class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudDiscoveryengineV1ImportUserEventsRequest(body: Schema.GoogleCloudDiscoveryengineV1ImportUserEventsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object `import` {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/userEvents:import").addQueryStringParameters("parent" -> parent.toString))
				}
			}
			object collections {
				object dataConnector {
					object operations {
						/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataConnector/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
						}
						/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataConnector/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
						}
					}
				}
				object operations {
					/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
					}
					/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
					}
				}
				object dataStores {
					/** Gets the SiteSearchEngine. */
					class getSiteSearchEngine(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1SiteSearchEngine]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1SiteSearchEngine])
					}
					object getSiteSearchEngine {
						def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getSiteSearchEngine = new getSiteSearchEngine(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/siteSearchEngine").addQueryStringParameters("name" -> name.toString))
						given Conversion[getSiteSearchEngine, Future[Schema.GoogleCloudDiscoveryengineV1SiteSearchEngine]] = (fun: getSiteSearchEngine) => fun.apply()
					}
					/** Completes the specified user input with keyword suggestions. */
					class completeQuery(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1CompleteQueryResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1CompleteQueryResponse])
					}
					object completeQuery {
						def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, dataStore: String, query: String, queryModel: String, userPseudoId: String, includeTailSuggestions: Boolean)(using signer: RequestSigner, ec: ExecutionContext): completeQuery = new completeQuery(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}:completeQuery").addQueryStringParameters("dataStore" -> dataStore.toString, "query" -> query.toString, "queryModel" -> queryModel.toString, "userPseudoId" -> userPseudoId.toString, "includeTailSuggestions" -> includeTailSuggestions.toString))
						given Conversion[completeQuery, Future[Schema.GoogleCloudDiscoveryengineV1CompleteQueryResponse]] = (fun: completeQuery) => fun.apply()
					}
					/** Creates a DataStore. DataStore is for storing Documents. To serve these documents for Search, or Recommendation use case, an Engine needs to be created separately. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDiscoveryengineV1DataStore(body: Schema.GoogleCloudDiscoveryengineV1DataStore) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, parent: String, cmekConfigName: String, disableCmek: Boolean, dataStoreId: String, createAdvancedSiteSearch: Boolean, skipDefaultSchemaCreation: Boolean)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores").addQueryStringParameters("parent" -> parent.toString, "cmekConfigName" -> cmekConfigName.toString, "disableCmek" -> disableCmek.toString, "dataStoreId" -> dataStoreId.toString, "createAdvancedSiteSearch" -> createAdvancedSiteSearch.toString, "skipDefaultSchemaCreation" -> skipDefaultSchemaCreation.toString))
					}
					/** Trains a custom model. */
					class trainCustomModel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDiscoveryengineV1TrainCustomModelRequest(body: Schema.GoogleCloudDiscoveryengineV1TrainCustomModelRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object trainCustomModel {
						def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, dataStore: String)(using signer: RequestSigner, ec: ExecutionContext): trainCustomModel = new trainCustomModel(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}:trainCustomModel").addQueryStringParameters("dataStore" -> dataStore.toString))
					}
					/** Deletes a DataStore. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
					}
					/** Gets a DataStore. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1DataStore]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1DataStore])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1DataStore]] = (fun: get) => fun.apply()
					}
					/** Updates a DataStore */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDiscoveryengineV1DataStore(body: Schema.GoogleCloudDiscoveryengineV1DataStore) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1DataStore])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Lists all the DataStores associated with the project. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListDataStoresResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListDataStoresResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListDataStoresResponse]] = (fun: list) => fun.apply()
					}
					object conversations {
						/** Creates a Conversation. If the Conversation to create already exists, an ALREADY_EXISTS error is returned. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1Conversation(body: Schema.GoogleCloudDiscoveryengineV1Conversation) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Conversation])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/conversations").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Converses a conversation. */
						class converse(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1ConverseConversationRequest(body: Schema.GoogleCloudDiscoveryengineV1ConverseConversationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ConverseConversationResponse])
						}
						object converse {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, conversationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): converse = new converse(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/conversations/${conversationsId}:converse").addQueryStringParameters("name" -> name.toString))
						}
						/** Deletes a Conversation. If the Conversation to delete does not exist, a NOT_FOUND error is returned. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, conversationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/conversations/${conversationsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						/** Gets a Conversation. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1Conversation]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Conversation])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, conversationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/conversations/${conversationsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1Conversation]] = (fun: get) => fun.apply()
						}
						/** Updates a Conversation. Conversation action type cannot be changed. If the Conversation to update does not exist, a NOT_FOUND error is returned. */
						class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1Conversation(body: Schema.GoogleCloudDiscoveryengineV1Conversation) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Conversation])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, conversationsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/conversations/${conversationsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						/** Lists all Conversations by their parent DataStore. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListConversationsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListConversationsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/conversations").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
							given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListConversationsResponse]] = (fun: list) => fun.apply()
						}
					}
					object operations {
						/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
						}
						/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
						}
					}
					object userEvents {
						/** Writes a single user event. */
						class write(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1UserEvent(body: Schema.GoogleCloudDiscoveryengineV1UserEvent) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1UserEvent])
						}
						object write {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String, writeAsync: Boolean)(using signer: RequestSigner, ec: ExecutionContext): write = new write(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/userEvents:write").addQueryStringParameters("parent" -> parent.toString, "writeAsync" -> writeAsync.toString))
						}
						/** Writes a single user event from the browser. This uses a GET request to due to browser restriction of POST-ing to a third-party domain. This method is used only by the Discovery Engine API JavaScript pixel and Google Tag Manager. Users should not call this method directly. */
						class collect(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleApiHttpBody]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleApiHttpBody])
						}
						object collect {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String, userEvent: String, uri: String, ets: String)(using signer: RequestSigner, ec: ExecutionContext): collect = new collect(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/userEvents:collect").addQueryStringParameters("parent" -> parent.toString, "userEvent" -> userEvent.toString, "uri" -> uri.toString, "ets" -> ets.toString))
							given Conversion[collect, Future[Schema.GoogleApiHttpBody]] = (fun: collect) => fun.apply()
						}
						/** Deletes permanently all user events specified by the filter provided. Depending on the number of events specified by the filter, this operation could take hours or days to complete. To test a filter, use the list command first. */
						class purge(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1PurgeUserEventsRequest(body: Schema.GoogleCloudDiscoveryengineV1PurgeUserEventsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object purge {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): purge = new purge(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/userEvents:purge").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Bulk import of user events. Request processing might be synchronous. Events that already exist are skipped. Use this method for backfilling historical user events. Operation.response is of type ImportResponse. Note that it is possible for a subset of the items to be successfully inserted. Operation.metadata is of type ImportMetadata. */
						class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1ImportUserEventsRequest(body: Schema.GoogleCloudDiscoveryengineV1ImportUserEventsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object `import` {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/userEvents:import").addQueryStringParameters("parent" -> parent.toString))
						}
					}
					object sessions {
						/** Creates a Session. If the Session to create already exists, an ALREADY_EXISTS error is returned. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1Session(body: Schema.GoogleCloudDiscoveryengineV1Session) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Session])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/sessions").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Deletes a Session. If the Session to delete does not exist, a NOT_FOUND error is returned. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, sessionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/sessions/${sessionsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						/** Gets a Session. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1Session]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Session])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, sessionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/sessions/${sessionsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1Session]] = (fun: get) => fun.apply()
						}
						/** Updates a Session. Session action type cannot be changed. If the Session to update does not exist, a NOT_FOUND error is returned. */
						class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1Session(body: Schema.GoogleCloudDiscoveryengineV1Session) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Session])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, sessionsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/sessions/${sessionsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						/** Lists all Sessions by their parent DataStore. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListSessionsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListSessionsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/sessions").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
							given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListSessionsResponse]] = (fun: list) => fun.apply()
						}
						object answers {
							/** Gets a Answer. */
							class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1Answer]) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Answer])
							}
							object get {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, sessionsId :PlayApi, answersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/sessions/${sessionsId}/answers/${answersId}").addQueryStringParameters("name" -> name.toString))
								given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1Answer]] = (fun: get) => fun.apply()
							}
						}
					}
					object siteSearchEngine {
						/** Returns list of target sites with its domain verification status. This method can only be called under data store with BASIC_SITE_SEARCH state at the moment. */
						class fetchDomainVerificationStatus(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1FetchDomainVerificationStatusResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1FetchDomainVerificationStatusResponse])
						}
						object fetchDomainVerificationStatus {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, siteSearchEngine: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): fetchDomainVerificationStatus = new fetchDomainVerificationStatus(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/siteSearchEngine:fetchDomainVerificationStatus").addQueryStringParameters("siteSearchEngine" -> siteSearchEngine.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[fetchDomainVerificationStatus, Future[Schema.GoogleCloudDiscoveryengineV1FetchDomainVerificationStatusResponse]] = (fun: fetchDomainVerificationStatus) => fun.apply()
						}
						/** Verify target sites' ownership and validity. This API sends all the target sites under site search engine for verification. */
						class batchVerifyTargetSites(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1BatchVerifyTargetSitesRequest(body: Schema.GoogleCloudDiscoveryengineV1BatchVerifyTargetSitesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object batchVerifyTargetSites {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): batchVerifyTargetSites = new batchVerifyTargetSites(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/siteSearchEngine:batchVerifyTargetSites").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Upgrade from basic site search to advanced site search. */
						class enableAdvancedSiteSearch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1EnableAdvancedSiteSearchRequest(body: Schema.GoogleCloudDiscoveryengineV1EnableAdvancedSiteSearchRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object enableAdvancedSiteSearch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, siteSearchEngine: String)(using signer: RequestSigner, ec: ExecutionContext): enableAdvancedSiteSearch = new enableAdvancedSiteSearch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/siteSearchEngine:enableAdvancedSiteSearch").addQueryStringParameters("siteSearchEngine" -> siteSearchEngine.toString))
						}
						/** Downgrade from advanced site search to basic site search. */
						class disableAdvancedSiteSearch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1DisableAdvancedSiteSearchRequest(body: Schema.GoogleCloudDiscoveryengineV1DisableAdvancedSiteSearchRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object disableAdvancedSiteSearch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, siteSearchEngine: String)(using signer: RequestSigner, ec: ExecutionContext): disableAdvancedSiteSearch = new disableAdvancedSiteSearch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/siteSearchEngine:disableAdvancedSiteSearch").addQueryStringParameters("siteSearchEngine" -> siteSearchEngine.toString))
						}
						/** Request on-demand recrawl for a list of URIs. */
						class recrawlUris(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1RecrawlUrisRequest(body: Schema.GoogleCloudDiscoveryengineV1RecrawlUrisRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object recrawlUris {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, siteSearchEngine: String)(using signer: RequestSigner, ec: ExecutionContext): recrawlUris = new recrawlUris(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/siteSearchEngine:recrawlUris").addQueryStringParameters("siteSearchEngine" -> siteSearchEngine.toString))
						}
						object operations {
							/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
							class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
							}
							object list {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/siteSearchEngine/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
								given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
							}
							/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
							class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
							}
							object get {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/siteSearchEngine/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
								given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
							}
						}
						object targetSites {
							/** Creates a TargetSite. */
							class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def withGoogleCloudDiscoveryengineV1TargetSite(body: Schema.GoogleCloudDiscoveryengineV1TargetSite) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
							}
							object create {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/siteSearchEngine/targetSites").addQueryStringParameters("parent" -> parent.toString))
							}
							/** Deletes a TargetSite. */
							class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
							}
							object delete {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, targetSitesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/siteSearchEngine/targetSites/${targetSitesId}").addQueryStringParameters("name" -> name.toString))
								given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
							}
							/** Creates TargetSite in a batch. */
							class batchCreate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def withGoogleCloudDiscoveryengineV1BatchCreateTargetSitesRequest(body: Schema.GoogleCloudDiscoveryengineV1BatchCreateTargetSitesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
							}
							object batchCreate {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): batchCreate = new batchCreate(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/siteSearchEngine/targetSites:batchCreate").addQueryStringParameters("parent" -> parent.toString))
							}
							/** Gets a TargetSite. */
							class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1TargetSite]) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1TargetSite])
							}
							object get {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, targetSitesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/siteSearchEngine/targetSites/${targetSitesId}").addQueryStringParameters("name" -> name.toString))
								given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1TargetSite]] = (fun: get) => fun.apply()
							}
							/** Updates a TargetSite. */
							class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def withGoogleCloudDiscoveryengineV1TargetSite(body: Schema.GoogleCloudDiscoveryengineV1TargetSite) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleLongrunningOperation])
							}
							object patch {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, targetSitesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/siteSearchEngine/targetSites/${targetSitesId}").addQueryStringParameters("name" -> name.toString))
							}
							/** Gets a list of TargetSites. */
							class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListTargetSitesResponse]) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListTargetSitesResponse])
							}
							object list {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/siteSearchEngine/targetSites").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
								given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListTargetSitesResponse]] = (fun: list) => fun.apply()
							}
							object operations {
								/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
								class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
									val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
									/** Perform the request */
									def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
								}
								object list {
									def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/siteSearchEngine/targetSites/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
									given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
								}
								/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
								class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
									val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
									/** Perform the request */
									def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
								}
								object get {
									def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/siteSearchEngine/targetSites/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
									given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
								}
							}
						}
					}
					object models {
						object operations {
							/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
							class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
							}
							object list {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, modelsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/models/${modelsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
								given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
							}
							/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
							class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
							}
							object get {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, modelsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/models/${modelsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
								given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
							}
						}
					}
					object suggestionDenyListEntries {
						/** Imports all SuggestionDenyListEntry for a DataStore. */
						class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1ImportSuggestionDenyListEntriesRequest(body: Schema.GoogleCloudDiscoveryengineV1ImportSuggestionDenyListEntriesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object `import` {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/suggestionDenyListEntries:import").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Permanently deletes all SuggestionDenyListEntry for a DataStore. */
						class purge(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1PurgeSuggestionDenyListEntriesRequest(body: Schema.GoogleCloudDiscoveryengineV1PurgeSuggestionDenyListEntriesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object purge {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): purge = new purge(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/suggestionDenyListEntries:purge").addQueryStringParameters("parent" -> parent.toString))
						}
					}
					object schemas {
						/** Creates a Schema. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1Schema(body: Schema.GoogleCloudDiscoveryengineV1Schema) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String, schemaId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/schemas").addQueryStringParameters("parent" -> parent.toString, "schemaId" -> schemaId.toString))
						}
						/** Deletes a Schema. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, schemasId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/schemas/${schemasId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
						}
						/** Gets a Schema. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1Schema]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Schema])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, schemasId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/schemas/${schemasId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1Schema]] = (fun: get) => fun.apply()
						}
						/** Updates a Schema. */
						class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1Schema(body: Schema.GoogleCloudDiscoveryengineV1Schema) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, schemasId :PlayApi, name: String, allowMissing: Boolean)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/schemas/${schemasId}").addQueryStringParameters("name" -> name.toString, "allowMissing" -> allowMissing.toString))
						}
						/** Gets a list of Schemas. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListSchemasResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListSchemasResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/schemas").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListSchemasResponse]] = (fun: list) => fun.apply()
						}
						object operations {
							/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
							class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
							}
							object list {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, schemasId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/schemas/${schemasId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
								given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
							}
							/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
							class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
							}
							object get {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, schemasId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/schemas/${schemasId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
								given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
							}
						}
					}
					object servingConfigs {
						/** Performs a search. */
						class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1SearchRequest(body: Schema.GoogleCloudDiscoveryengineV1SearchRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1SearchResponse])
						}
						object search {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, servingConfigsId :PlayApi, servingConfig: String)(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/servingConfigs/${servingConfigsId}:search").addQueryStringParameters("servingConfig" -> servingConfig.toString))
						}
						/** Performs a search. Similar to the SearchService.Search method, but a lite version that allows API key for authentication, where OAuth and IAM checks are not required. Only public website search is supported by this method. If data stores and engines not associated with public website search are specified, a `FAILED_PRECONDITION` error is returned. This method can be used for easy onboarding without having to implement an authentication backend. However, it is strongly recommended to use SearchService.Search instead with required OAuth and IAM checks to provide better data security. */
						class searchLite(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1SearchRequest(body: Schema.GoogleCloudDiscoveryengineV1SearchRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1SearchResponse])
						}
						object searchLite {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, servingConfigsId :PlayApi, servingConfig: String)(using signer: RequestSigner, ec: ExecutionContext): searchLite = new searchLite(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/servingConfigs/${servingConfigsId}:searchLite").addQueryStringParameters("servingConfig" -> servingConfig.toString))
						}
						/** Answer query method. */
						class answer(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1AnswerQueryRequest(body: Schema.GoogleCloudDiscoveryengineV1AnswerQueryRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1AnswerQueryResponse])
						}
						object answer {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, servingConfigsId :PlayApi, servingConfig: String)(using signer: RequestSigner, ec: ExecutionContext): answer = new answer(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/servingConfigs/${servingConfigsId}:answer").addQueryStringParameters("servingConfig" -> servingConfig.toString))
						}
						/** Makes a recommendation, which requires a contextual user event. */
						class recommend(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1RecommendRequest(body: Schema.GoogleCloudDiscoveryengineV1RecommendRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1RecommendResponse])
						}
						object recommend {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, servingConfigsId :PlayApi, servingConfig: String)(using signer: RequestSigner, ec: ExecutionContext): recommend = new recommend(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/servingConfigs/${servingConfigsId}:recommend").addQueryStringParameters("servingConfig" -> servingConfig.toString))
						}
					}
					object branches {
						/** Gets index freshness metadata for Documents. Supported for website search only. */
						class batchGetDocumentsMetadata(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1BatchGetDocumentsMetadataResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1BatchGetDocumentsMetadataResponse])
						}
						object batchGetDocumentsMetadata {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, parent: String, matcherUrisMatcherUris: String, matcherFhirMatcherFhirResources: String)(using signer: RequestSigner, ec: ExecutionContext): batchGetDocumentsMetadata = new batchGetDocumentsMetadata(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/branches/${branchesId}/batchGetDocumentsMetadata").addQueryStringParameters("parent" -> parent.toString, "matcher.urisMatcher.uris" -> matcherUrisMatcherUris.toString, "matcher.fhirMatcher.fhirResources" -> matcherFhirMatcherFhirResources.toString))
							given Conversion[batchGetDocumentsMetadata, Future[Schema.GoogleCloudDiscoveryengineV1BatchGetDocumentsMetadataResponse]] = (fun: batchGetDocumentsMetadata) => fun.apply()
						}
						object operations {
							/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
							class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
							}
							object list {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/branches/${branchesId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
								given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
							}
							/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
							class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
							}
							object get {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/branches/${branchesId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
								given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
							}
							/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
							class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def withGoogleLongrunningCancelOperationRequest(body: Schema.GoogleLongrunningCancelOperationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
							}
							object cancel {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/branches/${branchesId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
							}
						}
						object documents {
							/** Creates a Document. */
							class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def withGoogleCloudDiscoveryengineV1Document(body: Schema.GoogleCloudDiscoveryengineV1Document) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Document])
							}
							object create {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, parent: String, documentId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/branches/${branchesId}/documents").addQueryStringParameters("parent" -> parent.toString, "documentId" -> documentId.toString))
							}
							/** Permanently deletes all selected Documents in a branch. This process is asynchronous. Depending on the number of Documents to be deleted, this operation can take hours to complete. Before the delete operation completes, some Documents might still be returned by DocumentService.GetDocument or DocumentService.ListDocuments. To get a list of the Documents to be deleted, set PurgeDocumentsRequest.force to false. */
							class purge(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def withGoogleCloudDiscoveryengineV1PurgeDocumentsRequest(body: Schema.GoogleCloudDiscoveryengineV1PurgeDocumentsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
							}
							object purge {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): purge = new purge(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/branches/${branchesId}/documents:purge").addQueryStringParameters("parent" -> parent.toString))
							}
							/** Bulk import of multiple Documents. Request processing may be synchronous. Non-existing items are created. Note: It is possible for a subset of the Documents to be successfully updated. */
							class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def withGoogleCloudDiscoveryengineV1ImportDocumentsRequest(body: Schema.GoogleCloudDiscoveryengineV1ImportDocumentsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
							}
							object `import` {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/branches/${branchesId}/documents:import").addQueryStringParameters("parent" -> parent.toString))
							}
							/** Deletes a Document. */
							class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
							}
							object delete {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, documentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/branches/${branchesId}/documents/${documentsId}").addQueryStringParameters("name" -> name.toString))
								given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
							}
							/** Gets a Document. */
							class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1Document]) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Document])
							}
							object get {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, documentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/branches/${branchesId}/documents/${documentsId}").addQueryStringParameters("name" -> name.toString))
								given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1Document]] = (fun: get) => fun.apply()
							}
							/** Updates a Document. */
							class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def withGoogleCloudDiscoveryengineV1Document(body: Schema.GoogleCloudDiscoveryengineV1Document) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Document])
							}
							object patch {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, documentsId :PlayApi, name: String, allowMissing: Boolean, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/branches/${branchesId}/documents/${documentsId}").addQueryStringParameters("name" -> name.toString, "allowMissing" -> allowMissing.toString, "updateMask" -> updateMask.toString))
							}
							/** Gets a list of Documents. */
							class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListDocumentsResponse]) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListDocumentsResponse])
							}
							object list {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/branches/${branchesId}/documents").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
								given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListDocumentsResponse]] = (fun: list) => fun.apply()
							}
						}
					}
					object completionSuggestions {
						/** Imports CompletionSuggestions for a DataStore. */
						class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1ImportCompletionSuggestionsRequest(body: Schema.GoogleCloudDiscoveryengineV1ImportCompletionSuggestionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object `import` {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/completionSuggestions:import").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Permanently deletes all CompletionSuggestions for a DataStore. */
						class purge(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1PurgeCompletionSuggestionsRequest(body: Schema.GoogleCloudDiscoveryengineV1PurgeCompletionSuggestionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object purge {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): purge = new purge(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/completionSuggestions:purge").addQueryStringParameters("parent" -> parent.toString))
						}
					}
					object customModels {
						/** Gets a list of all the custom models. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListCustomModelsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListCustomModelsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, dataStore: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/customModels").addQueryStringParameters("dataStore" -> dataStore.toString))
							given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListCustomModelsResponse]] = (fun: list) => fun.apply()
						}
					}
					object controls {
						/** Creates a Control. By default 1000 controls are allowed for a data store. A request can be submitted to adjust this limit. If the Control to create already exists, an ALREADY_EXISTS error is returned. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1Control(body: Schema.GoogleCloudDiscoveryengineV1Control) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Control])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String, controlId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/controls").addQueryStringParameters("parent" -> parent.toString, "controlId" -> controlId.toString))
						}
						/** Deletes a Control. If the Control to delete does not exist, a NOT_FOUND error is returned. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, controlsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/controls/${controlsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						/** Gets a Control. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1Control]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Control])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, controlsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/controls/${controlsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1Control]] = (fun: get) => fun.apply()
						}
						/** Updates a Control. Control action type cannot be changed. If the Control to update does not exist, a NOT_FOUND error is returned. */
						class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. Indicates which fields in the provided Control to update. The following are NOT supported: &#42; Control.name &#42; Control.solution_type If not set or empty, all supported fields are updated.<br>Format: google-fieldmask */
							def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1Control(body: Schema.GoogleCloudDiscoveryengineV1Control) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Control])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, controlsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/controls/${controlsId}").addQueryStringParameters("name" -> name.toString))
						}
						/** Lists all Controls by their parent DataStore. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListControlsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. Maximum number of results to return. If unspecified, defaults to 50. Max allowed value is 1000.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. A page token, received from a previous `ListControls` call. Provide this to retrieve the subsequent page. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Optional. A filter to apply on the list results. Supported features: &#42; List all the products under the parent branch if filter is unset. Currently this field is unsupported. */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListControlsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, dataStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/dataStores/${dataStoresId}/controls").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListControlsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
				object engines {
					/** Creates a Engine. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDiscoveryengineV1Engine(body: Schema.GoogleCloudDiscoveryengineV1Engine) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, parent: String, engineId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines").addQueryStringParameters("parent" -> parent.toString, "engineId" -> engineId.toString))
					}
					/** Gets a Engine. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1Engine]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Engine])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1Engine]] = (fun: get) => fun.apply()
					}
					/** Updates an Engine */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDiscoveryengineV1Engine(body: Schema.GoogleCloudDiscoveryengineV1Engine) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Engine])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Deletes a Engine. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
					}
					/** Lists all the Engines associated with the project. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListEnginesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Not supported.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. Not supported. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. Filter by solution type. For example: solution_type=SOLUTION_TYPE_SEARCH */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListEnginesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListEnginesResponse]] = (fun: list) => fun.apply()
					}
					object conversations {
						/** Creates a Conversation. If the Conversation to create already exists, an ALREADY_EXISTS error is returned. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1Conversation(body: Schema.GoogleCloudDiscoveryengineV1Conversation) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Conversation])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/conversations").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Converses a conversation. */
						class converse(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1ConverseConversationRequest(body: Schema.GoogleCloudDiscoveryengineV1ConverseConversationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ConverseConversationResponse])
						}
						object converse {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, conversationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): converse = new converse(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/conversations/${conversationsId}:converse").addQueryStringParameters("name" -> name.toString))
						}
						/** Deletes a Conversation. If the Conversation to delete does not exist, a NOT_FOUND error is returned. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, conversationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/conversations/${conversationsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						/** Gets a Conversation. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1Conversation]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Conversation])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, conversationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/conversations/${conversationsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1Conversation]] = (fun: get) => fun.apply()
						}
						/** Updates a Conversation. Conversation action type cannot be changed. If the Conversation to update does not exist, a NOT_FOUND error is returned. */
						class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1Conversation(body: Schema.GoogleCloudDiscoveryengineV1Conversation) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Conversation])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, conversationsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/conversations/${conversationsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						/** Lists all Conversations by their parent DataStore. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListConversationsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListConversationsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/conversations").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
							given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListConversationsResponse]] = (fun: list) => fun.apply()
						}
					}
					object operations {
						/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
						}
						/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
						}
					}
					object sessions {
						/** Creates a Session. If the Session to create already exists, an ALREADY_EXISTS error is returned. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1Session(body: Schema.GoogleCloudDiscoveryengineV1Session) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Session])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/sessions").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Deletes a Session. If the Session to delete does not exist, a NOT_FOUND error is returned. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, sessionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/sessions/${sessionsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						/** Gets a Session. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1Session]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Session])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, sessionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/sessions/${sessionsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1Session]] = (fun: get) => fun.apply()
						}
						/** Updates a Session. Session action type cannot be changed. If the Session to update does not exist, a NOT_FOUND error is returned. */
						class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1Session(body: Schema.GoogleCloudDiscoveryengineV1Session) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Session])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, sessionsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/sessions/${sessionsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						/** Lists all Sessions by their parent DataStore. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListSessionsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListSessionsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/sessions").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
							given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListSessionsResponse]] = (fun: list) => fun.apply()
						}
						object answers {
							/** Gets a Answer. */
							class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1Answer]) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
								/** Perform the request */
								def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Answer])
							}
							object get {
								def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, sessionsId :PlayApi, answersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/sessions/${sessionsId}/answers/${answersId}").addQueryStringParameters("name" -> name.toString))
								given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1Answer]] = (fun: get) => fun.apply()
							}
						}
					}
					object servingConfigs {
						/** Performs a search. */
						class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1SearchRequest(body: Schema.GoogleCloudDiscoveryengineV1SearchRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1SearchResponse])
						}
						object search {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, servingConfigsId :PlayApi, servingConfig: String)(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/servingConfigs/${servingConfigsId}:search").addQueryStringParameters("servingConfig" -> servingConfig.toString))
						}
						/** Performs a search. Similar to the SearchService.Search method, but a lite version that allows API key for authentication, where OAuth and IAM checks are not required. Only public website search is supported by this method. If data stores and engines not associated with public website search are specified, a `FAILED_PRECONDITION` error is returned. This method can be used for easy onboarding without having to implement an authentication backend. However, it is strongly recommended to use SearchService.Search instead with required OAuth and IAM checks to provide better data security. */
						class searchLite(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1SearchRequest(body: Schema.GoogleCloudDiscoveryengineV1SearchRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1SearchResponse])
						}
						object searchLite {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, servingConfigsId :PlayApi, servingConfig: String)(using signer: RequestSigner, ec: ExecutionContext): searchLite = new searchLite(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/servingConfigs/${servingConfigsId}:searchLite").addQueryStringParameters("servingConfig" -> servingConfig.toString))
						}
						/** Answer query method. */
						class answer(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1AnswerQueryRequest(body: Schema.GoogleCloudDiscoveryengineV1AnswerQueryRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1AnswerQueryResponse])
						}
						object answer {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, servingConfigsId :PlayApi, servingConfig: String)(using signer: RequestSigner, ec: ExecutionContext): answer = new answer(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/servingConfigs/${servingConfigsId}:answer").addQueryStringParameters("servingConfig" -> servingConfig.toString))
						}
						/** Makes a recommendation, which requires a contextual user event. */
						class recommend(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1RecommendRequest(body: Schema.GoogleCloudDiscoveryengineV1RecommendRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1RecommendResponse])
						}
						object recommend {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, servingConfigsId :PlayApi, servingConfig: String)(using signer: RequestSigner, ec: ExecutionContext): recommend = new recommend(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/servingConfigs/${servingConfigsId}:recommend").addQueryStringParameters("servingConfig" -> servingConfig.toString))
						}
					}
					object controls {
						/** Creates a Control. By default 1000 controls are allowed for a data store. A request can be submitted to adjust this limit. If the Control to create already exists, an ALREADY_EXISTS error is returned. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1Control(body: Schema.GoogleCloudDiscoveryengineV1Control) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Control])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, parent: String, controlId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/controls").addQueryStringParameters("parent" -> parent.toString, "controlId" -> controlId.toString))
						}
						/** Deletes a Control. If the Control to delete does not exist, a NOT_FOUND error is returned. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, controlsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/controls/${controlsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						/** Gets a Control. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1Control]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Control])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, controlsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/controls/${controlsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1Control]] = (fun: get) => fun.apply()
						}
						/** Updates a Control. Control action type cannot be changed. If the Control to update does not exist, a NOT_FOUND error is returned. */
						class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. Indicates which fields in the provided Control to update. The following are NOT supported: &#42; Control.name &#42; Control.solution_type If not set or empty, all supported fields are updated.<br>Format: google-fieldmask */
							def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1Control(body: Schema.GoogleCloudDiscoveryengineV1Control) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Control])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, controlsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/controls/${controlsId}").addQueryStringParameters("name" -> name.toString))
						}
						/** Lists all Controls by their parent DataStore. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListControlsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. Maximum number of results to return. If unspecified, defaults to 50. Max allowed value is 1000.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. A page token, received from a previous `ListControls` call. Provide this to retrieve the subsequent page. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Optional. A filter to apply on the list results. Supported features: &#42; List all the products under the parent branch if filter is unset. Currently this field is unsupported. */
							def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListControlsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, collectionsId :PlayApi, enginesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/collections/${collectionsId}/engines/${enginesId}/controls").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListControlsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
			object rankingConfigs {
				/** Ranks a list of text records based on the given input query. */
				class rank(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudDiscoveryengineV1RankRequest(body: Schema.GoogleCloudDiscoveryengineV1RankRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1RankResponse])
				}
				object rank {
					def apply(projectsId :PlayApi, locationsId :PlayApi, rankingConfigsId :PlayApi, rankingConfig: String)(using signer: RequestSigner, ec: ExecutionContext): rank = new rank(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/rankingConfigs/${rankingConfigsId}:rank").addQueryStringParameters("rankingConfig" -> rankingConfig.toString))
				}
			}
			object dataStores {
				/** Gets the SiteSearchEngine. */
				class getSiteSearchEngine(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1SiteSearchEngine]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1SiteSearchEngine])
				}
				object getSiteSearchEngine {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getSiteSearchEngine = new getSiteSearchEngine(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/siteSearchEngine").addQueryStringParameters("name" -> name.toString))
					given Conversion[getSiteSearchEngine, Future[Schema.GoogleCloudDiscoveryengineV1SiteSearchEngine]] = (fun: getSiteSearchEngine) => fun.apply()
				}
				/** Completes the specified user input with keyword suggestions. */
				class completeQuery(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1CompleteQueryResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1CompleteQueryResponse])
				}
				object completeQuery {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, dataStore: String, query: String, queryModel: String, userPseudoId: String, includeTailSuggestions: Boolean)(using signer: RequestSigner, ec: ExecutionContext): completeQuery = new completeQuery(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}:completeQuery").addQueryStringParameters("dataStore" -> dataStore.toString, "query" -> query.toString, "queryModel" -> queryModel.toString, "userPseudoId" -> userPseudoId.toString, "includeTailSuggestions" -> includeTailSuggestions.toString))
					given Conversion[completeQuery, Future[Schema.GoogleCloudDiscoveryengineV1CompleteQueryResponse]] = (fun: completeQuery) => fun.apply()
				}
				/** Creates a DataStore. DataStore is for storing Documents. To serve these documents for Search, or Recommendation use case, an Engine needs to be created separately. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudDiscoveryengineV1DataStore(body: Schema.GoogleCloudDiscoveryengineV1DataStore) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, cmekConfigName: String, disableCmek: Boolean, dataStoreId: String, createAdvancedSiteSearch: Boolean, skipDefaultSchemaCreation: Boolean)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores").addQueryStringParameters("parent" -> parent.toString, "cmekConfigName" -> cmekConfigName.toString, "disableCmek" -> disableCmek.toString, "dataStoreId" -> dataStoreId.toString, "createAdvancedSiteSearch" -> createAdvancedSiteSearch.toString, "skipDefaultSchemaCreation" -> skipDefaultSchemaCreation.toString))
				}
				/** Updates a DataStore */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudDiscoveryengineV1DataStore(body: Schema.GoogleCloudDiscoveryengineV1DataStore) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1DataStore])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Deletes a DataStore. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
				}
				/** Gets a DataStore. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1DataStore]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1DataStore])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1DataStore]] = (fun: get) => fun.apply()
				}
				/** Lists all the DataStores associated with the project. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListDataStoresResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListDataStoresResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
					given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListDataStoresResponse]] = (fun: list) => fun.apply()
				}
				object conversations {
					/** Creates a Conversation. If the Conversation to create already exists, an ALREADY_EXISTS error is returned. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDiscoveryengineV1Conversation(body: Schema.GoogleCloudDiscoveryengineV1Conversation) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Conversation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/conversations").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Converses a conversation. */
					class converse(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDiscoveryengineV1ConverseConversationRequest(body: Schema.GoogleCloudDiscoveryengineV1ConverseConversationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ConverseConversationResponse])
					}
					object converse {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, conversationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): converse = new converse(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/conversations/${conversationsId}:converse").addQueryStringParameters("name" -> name.toString))
					}
					/** Deletes a Conversation. If the Conversation to delete does not exist, a NOT_FOUND error is returned. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, conversationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/conversations/${conversationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					/** Gets a Conversation. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1Conversation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Conversation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, conversationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/conversations/${conversationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1Conversation]] = (fun: get) => fun.apply()
					}
					/** Updates a Conversation. Conversation action type cannot be changed. If the Conversation to update does not exist, a NOT_FOUND error is returned. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDiscoveryengineV1Conversation(body: Schema.GoogleCloudDiscoveryengineV1Conversation) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Conversation])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, conversationsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/conversations/${conversationsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Lists all Conversations by their parent DataStore. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListConversationsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListConversationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/conversations").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
						given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListConversationsResponse]] = (fun: list) => fun.apply()
					}
				}
				object operations {
					/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
					}
					/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
					}
				}
				object userEvents {
					/** Writes a single user event. */
					class write(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDiscoveryengineV1UserEvent(body: Schema.GoogleCloudDiscoveryengineV1UserEvent) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1UserEvent])
					}
					object write {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String, writeAsync: Boolean)(using signer: RequestSigner, ec: ExecutionContext): write = new write(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/userEvents:write").addQueryStringParameters("parent" -> parent.toString, "writeAsync" -> writeAsync.toString))
					}
					/** Writes a single user event from the browser. This uses a GET request to due to browser restriction of POST-ing to a third-party domain. This method is used only by the Discovery Engine API JavaScript pixel and Google Tag Manager. Users should not call this method directly. */
					class collect(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleApiHttpBody]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleApiHttpBody])
					}
					object collect {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String, userEvent: String, uri: String, ets: String)(using signer: RequestSigner, ec: ExecutionContext): collect = new collect(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/userEvents:collect").addQueryStringParameters("parent" -> parent.toString, "userEvent" -> userEvent.toString, "uri" -> uri.toString, "ets" -> ets.toString))
						given Conversion[collect, Future[Schema.GoogleApiHttpBody]] = (fun: collect) => fun.apply()
					}
					/** Deletes permanently all user events specified by the filter provided. Depending on the number of events specified by the filter, this operation could take hours or days to complete. To test a filter, use the list command first. */
					class purge(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDiscoveryengineV1PurgeUserEventsRequest(body: Schema.GoogleCloudDiscoveryengineV1PurgeUserEventsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object purge {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): purge = new purge(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/userEvents:purge").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Bulk import of user events. Request processing might be synchronous. Events that already exist are skipped. Use this method for backfilling historical user events. Operation.response is of type ImportResponse. Note that it is possible for a subset of the items to be successfully inserted. Operation.metadata is of type ImportMetadata. */
					class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDiscoveryengineV1ImportUserEventsRequest(body: Schema.GoogleCloudDiscoveryengineV1ImportUserEventsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/userEvents:import").addQueryStringParameters("parent" -> parent.toString))
					}
				}
				object sessions {
					/** Creates a Session. If the Session to create already exists, an ALREADY_EXISTS error is returned. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDiscoveryengineV1Session(body: Schema.GoogleCloudDiscoveryengineV1Session) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Session])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/sessions").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Deletes a Session. If the Session to delete does not exist, a NOT_FOUND error is returned. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, sessionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/sessions/${sessionsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					/** Gets a Session. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1Session]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Session])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, sessionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/sessions/${sessionsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1Session]] = (fun: get) => fun.apply()
					}
					/** Updates a Session. Session action type cannot be changed. If the Session to update does not exist, a NOT_FOUND error is returned. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDiscoveryengineV1Session(body: Schema.GoogleCloudDiscoveryengineV1Session) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Session])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, sessionsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/sessions/${sessionsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Lists all Sessions by their parent DataStore. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListSessionsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListSessionsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, orderBy: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/sessions").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "orderBy" -> orderBy.toString))
						given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListSessionsResponse]] = (fun: list) => fun.apply()
					}
					object answers {
						/** Gets a Answer. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1Answer]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Answer])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, sessionsId :PlayApi, answersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/sessions/${sessionsId}/answers/${answersId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1Answer]] = (fun: get) => fun.apply()
						}
					}
				}
				object siteSearchEngine {
					/** Upgrade from basic site search to advanced site search. */
					class enableAdvancedSiteSearch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDiscoveryengineV1EnableAdvancedSiteSearchRequest(body: Schema.GoogleCloudDiscoveryengineV1EnableAdvancedSiteSearchRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object enableAdvancedSiteSearch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, siteSearchEngine: String)(using signer: RequestSigner, ec: ExecutionContext): enableAdvancedSiteSearch = new enableAdvancedSiteSearch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/siteSearchEngine:enableAdvancedSiteSearch").addQueryStringParameters("siteSearchEngine" -> siteSearchEngine.toString))
					}
					/** Downgrade from advanced site search to basic site search. */
					class disableAdvancedSiteSearch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDiscoveryengineV1DisableAdvancedSiteSearchRequest(body: Schema.GoogleCloudDiscoveryengineV1DisableAdvancedSiteSearchRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object disableAdvancedSiteSearch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, siteSearchEngine: String)(using signer: RequestSigner, ec: ExecutionContext): disableAdvancedSiteSearch = new disableAdvancedSiteSearch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/siteSearchEngine:disableAdvancedSiteSearch").addQueryStringParameters("siteSearchEngine" -> siteSearchEngine.toString))
					}
					/** Request on-demand recrawl for a list of URIs. */
					class recrawlUris(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDiscoveryengineV1RecrawlUrisRequest(body: Schema.GoogleCloudDiscoveryengineV1RecrawlUrisRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object recrawlUris {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, siteSearchEngine: String)(using signer: RequestSigner, ec: ExecutionContext): recrawlUris = new recrawlUris(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/siteSearchEngine:recrawlUris").addQueryStringParameters("siteSearchEngine" -> siteSearchEngine.toString))
					}
					object targetSites {
						/** Creates a TargetSite. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1TargetSite(body: Schema.GoogleCloudDiscoveryengineV1TargetSite) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/siteSearchEngine/targetSites").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Deletes a TargetSite. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, targetSitesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/siteSearchEngine/targetSites/${targetSitesId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
						}
						/** Creates TargetSite in a batch. */
						class batchCreate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1BatchCreateTargetSitesRequest(body: Schema.GoogleCloudDiscoveryengineV1BatchCreateTargetSitesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object batchCreate {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): batchCreate = new batchCreate(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/siteSearchEngine/targetSites:batchCreate").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Gets a TargetSite. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1TargetSite]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1TargetSite])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, targetSitesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/siteSearchEngine/targetSites/${targetSitesId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1TargetSite]] = (fun: get) => fun.apply()
						}
						/** Updates a TargetSite. */
						class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1TargetSite(body: Schema.GoogleCloudDiscoveryengineV1TargetSite) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, targetSitesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/siteSearchEngine/targetSites/${targetSitesId}").addQueryStringParameters("name" -> name.toString))
						}
						/** Gets a list of TargetSites. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListTargetSitesResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListTargetSitesResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/siteSearchEngine/targetSites").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListTargetSitesResponse]] = (fun: list) => fun.apply()
						}
					}
				}
				object models {
					object operations {
						/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, modelsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/models/${modelsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
						}
						/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, modelsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/models/${modelsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
						}
					}
				}
				object suggestionDenyListEntries {
					/** Imports all SuggestionDenyListEntry for a DataStore. */
					class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDiscoveryengineV1ImportSuggestionDenyListEntriesRequest(body: Schema.GoogleCloudDiscoveryengineV1ImportSuggestionDenyListEntriesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/suggestionDenyListEntries:import").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Permanently deletes all SuggestionDenyListEntry for a DataStore. */
					class purge(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDiscoveryengineV1PurgeSuggestionDenyListEntriesRequest(body: Schema.GoogleCloudDiscoveryengineV1PurgeSuggestionDenyListEntriesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object purge {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): purge = new purge(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/suggestionDenyListEntries:purge").addQueryStringParameters("parent" -> parent.toString))
					}
				}
				object schemas {
					/** Creates a Schema. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDiscoveryengineV1Schema(body: Schema.GoogleCloudDiscoveryengineV1Schema) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String, schemaId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/schemas").addQueryStringParameters("parent" -> parent.toString, "schemaId" -> schemaId.toString))
					}
					/** Deletes a Schema. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, schemasId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/schemas/${schemasId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
					}
					/** Gets a Schema. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1Schema]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Schema])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, schemasId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/schemas/${schemasId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1Schema]] = (fun: get) => fun.apply()
					}
					/** Updates a Schema. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDiscoveryengineV1Schema(body: Schema.GoogleCloudDiscoveryengineV1Schema) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, schemasId :PlayApi, name: String, allowMissing: Boolean)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/schemas/${schemasId}").addQueryStringParameters("name" -> name.toString, "allowMissing" -> allowMissing.toString))
					}
					/** Gets a list of Schemas. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListSchemasResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListSchemasResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/schemas").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListSchemasResponse]] = (fun: list) => fun.apply()
					}
				}
				object servingConfigs {
					/** Performs a search. */
					class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDiscoveryengineV1SearchRequest(body: Schema.GoogleCloudDiscoveryengineV1SearchRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1SearchResponse])
					}
					object search {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, servingConfigsId :PlayApi, servingConfig: String)(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/servingConfigs/${servingConfigsId}:search").addQueryStringParameters("servingConfig" -> servingConfig.toString))
					}
					/** Performs a search. Similar to the SearchService.Search method, but a lite version that allows API key for authentication, where OAuth and IAM checks are not required. Only public website search is supported by this method. If data stores and engines not associated with public website search are specified, a `FAILED_PRECONDITION` error is returned. This method can be used for easy onboarding without having to implement an authentication backend. However, it is strongly recommended to use SearchService.Search instead with required OAuth and IAM checks to provide better data security. */
					class searchLite(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDiscoveryengineV1SearchRequest(body: Schema.GoogleCloudDiscoveryengineV1SearchRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1SearchResponse])
					}
					object searchLite {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, servingConfigsId :PlayApi, servingConfig: String)(using signer: RequestSigner, ec: ExecutionContext): searchLite = new searchLite(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/servingConfigs/${servingConfigsId}:searchLite").addQueryStringParameters("servingConfig" -> servingConfig.toString))
					}
					/** Answer query method. */
					class answer(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDiscoveryengineV1AnswerQueryRequest(body: Schema.GoogleCloudDiscoveryengineV1AnswerQueryRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1AnswerQueryResponse])
					}
					object answer {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, servingConfigsId :PlayApi, servingConfig: String)(using signer: RequestSigner, ec: ExecutionContext): answer = new answer(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/servingConfigs/${servingConfigsId}:answer").addQueryStringParameters("servingConfig" -> servingConfig.toString))
					}
					/** Makes a recommendation, which requires a contextual user event. */
					class recommend(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDiscoveryengineV1RecommendRequest(body: Schema.GoogleCloudDiscoveryengineV1RecommendRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1RecommendResponse])
					}
					object recommend {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, servingConfigsId :PlayApi, servingConfig: String)(using signer: RequestSigner, ec: ExecutionContext): recommend = new recommend(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/servingConfigs/${servingConfigsId}:recommend").addQueryStringParameters("servingConfig" -> servingConfig.toString))
					}
				}
				object branches {
					/** Gets index freshness metadata for Documents. Supported for website search only. */
					class batchGetDocumentsMetadata(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1BatchGetDocumentsMetadataResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1BatchGetDocumentsMetadataResponse])
					}
					object batchGetDocumentsMetadata {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, parent: String, matcherUrisMatcherUris: String, matcherFhirMatcherFhirResources: String)(using signer: RequestSigner, ec: ExecutionContext): batchGetDocumentsMetadata = new batchGetDocumentsMetadata(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/branches/${branchesId}/batchGetDocumentsMetadata").addQueryStringParameters("parent" -> parent.toString, "matcher.urisMatcher.uris" -> matcherUrisMatcherUris.toString, "matcher.fhirMatcher.fhirResources" -> matcherFhirMatcherFhirResources.toString))
						given Conversion[batchGetDocumentsMetadata, Future[Schema.GoogleCloudDiscoveryengineV1BatchGetDocumentsMetadataResponse]] = (fun: batchGetDocumentsMetadata) => fun.apply()
					}
					object operations {
						/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/branches/${branchesId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
						}
						/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/branches/${branchesId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
						}
						/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
						class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleLongrunningCancelOperationRequest(body: Schema.GoogleLongrunningCancelOperationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object cancel {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/branches/${branchesId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
						}
					}
					object documents {
						/** Creates a Document. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1Document(body: Schema.GoogleCloudDiscoveryengineV1Document) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Document])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, parent: String, documentId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/branches/${branchesId}/documents").addQueryStringParameters("parent" -> parent.toString, "documentId" -> documentId.toString))
						}
						/** Permanently deletes all selected Documents in a branch. This process is asynchronous. Depending on the number of Documents to be deleted, this operation can take hours to complete. Before the delete operation completes, some Documents might still be returned by DocumentService.GetDocument or DocumentService.ListDocuments. To get a list of the Documents to be deleted, set PurgeDocumentsRequest.force to false. */
						class purge(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1PurgeDocumentsRequest(body: Schema.GoogleCloudDiscoveryengineV1PurgeDocumentsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object purge {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): purge = new purge(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/branches/${branchesId}/documents:purge").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Bulk import of multiple Documents. Request processing may be synchronous. Non-existing items are created. Note: It is possible for a subset of the Documents to be successfully updated. */
						class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1ImportDocumentsRequest(body: Schema.GoogleCloudDiscoveryengineV1ImportDocumentsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object `import` {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/branches/${branchesId}/documents:import").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Deletes a Document. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, documentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/branches/${branchesId}/documents/${documentsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						/** Gets a Document. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1Document]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Document])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, documentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/branches/${branchesId}/documents/${documentsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1Document]] = (fun: get) => fun.apply()
						}
						/** Updates a Document. */
						class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudDiscoveryengineV1Document(body: Schema.GoogleCloudDiscoveryengineV1Document) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Document])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, documentsId :PlayApi, name: String, allowMissing: Boolean, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/branches/${branchesId}/documents/${documentsId}").addQueryStringParameters("name" -> name.toString, "allowMissing" -> allowMissing.toString, "updateMask" -> updateMask.toString))
						}
						/** Gets a list of Documents. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListDocumentsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListDocumentsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, branchesId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/branches/${branchesId}/documents").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListDocumentsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
				object completionSuggestions {
					/** Imports CompletionSuggestions for a DataStore. */
					class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDiscoveryengineV1ImportCompletionSuggestionsRequest(body: Schema.GoogleCloudDiscoveryengineV1ImportCompletionSuggestionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/completionSuggestions:import").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Permanently deletes all CompletionSuggestions for a DataStore. */
					class purge(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDiscoveryengineV1PurgeCompletionSuggestionsRequest(body: Schema.GoogleCloudDiscoveryengineV1PurgeCompletionSuggestionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object purge {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): purge = new purge(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/completionSuggestions:purge").addQueryStringParameters("parent" -> parent.toString))
					}
				}
				object controls {
					/** Creates a Control. By default 1000 controls are allowed for a data store. A request can be submitted to adjust this limit. If the Control to create already exists, an ALREADY_EXISTS error is returned. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDiscoveryengineV1Control(body: Schema.GoogleCloudDiscoveryengineV1Control) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Control])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String, controlId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/controls").addQueryStringParameters("parent" -> parent.toString, "controlId" -> controlId.toString))
					}
					/** Deletes a Control. If the Control to delete does not exist, a NOT_FOUND error is returned. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, controlsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/controls/${controlsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					/** Gets a Control. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1Control]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Control])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, controlsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/controls/${controlsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudDiscoveryengineV1Control]] = (fun: get) => fun.apply()
					}
					/** Updates a Control. Control action type cannot be changed. If the Control to update does not exist, a NOT_FOUND error is returned. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Indicates which fields in the provided Control to update. The following are NOT supported: &#42; Control.name &#42; Control.solution_type If not set or empty, all supported fields are updated.<br>Format: google-fieldmask */
						def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
						/** Perform the request */
						def withGoogleCloudDiscoveryengineV1Control(body: Schema.GoogleCloudDiscoveryengineV1Control) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1Control])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, controlsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/controls/${controlsId}").addQueryStringParameters("name" -> name.toString))
					}
					/** Lists all Controls by their parent DataStore. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDiscoveryengineV1ListControlsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Maximum number of results to return. If unspecified, defaults to 50. Max allowed value is 1000.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A page token, received from a previous `ListControls` call. Provide this to retrieve the subsequent page. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. A filter to apply on the list results. Supported features: &#42; List all the products under the parent branch if filter is unset. Currently this field is unsupported. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1ListControlsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, dataStoresId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/dataStores/${dataStoresId}/controls").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudDiscoveryengineV1ListControlsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object identityMappingStores {
				object operations {
					/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, identityMappingStoresId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/identityMappingStores/${identityMappingStoresId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
					}
					/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, identityMappingStoresId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/identityMappingStores/${identityMappingStoresId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
					}
				}
			}
			object groundingConfigs {
				/** Performs a grounding check. */
				class check(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudDiscoveryengineV1CheckGroundingRequest(body: Schema.GoogleCloudDiscoveryengineV1CheckGroundingRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDiscoveryengineV1CheckGroundingResponse])
				}
				object check {
					def apply(projectsId :PlayApi, locationsId :PlayApi, groundingConfigsId :PlayApi, groundingConfig: String)(using signer: RequestSigner, ec: ExecutionContext): check = new check(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/groundingConfigs/${groundingConfigsId}:check").addQueryStringParameters("groundingConfig" -> groundingConfig.toString))
				}
			}
		}
	}
}
