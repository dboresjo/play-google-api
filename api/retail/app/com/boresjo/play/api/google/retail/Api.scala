package com.boresjo.play.api.google.retail

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

	private val BASE_URL = "https://retail.googleapis.com/"

	object projects {
		object locations {
			object operations {
				/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
				}
				/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
				}
			}
			object catalogs {
				/** Allows management of individual questions. */
				class updateGenerativeQuestion(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Indicates which fields in the provided GenerativeQuestionConfig to update. The following are NOT supported: &#42; GenerativeQuestionConfig.frequency If not set or empty, all supported fields are updated.<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new updateGenerativeQuestion(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					/** Perform the request */
					def withGoogleCloudRetailV2GenerativeQuestionConfig(body: Schema.GoogleCloudRetailV2GenerativeQuestionConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudRetailV2GenerativeQuestionConfig])
				}
				object updateGenerativeQuestion {
					def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, catalog: String)(using signer: RequestSigner, ec: ExecutionContext): updateGenerativeQuestion = new updateGenerativeQuestion(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/generativeQuestion").addQueryStringParameters("catalog" -> catalog.toString))
				}
				/** Get which branch is currently default branch set by CatalogService.SetDefaultBranch method under a specified parent catalog. */
				class getDefaultBranch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRetailV2GetDefaultBranchResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRetailV2GetDefaultBranchResponse])
				}
				object getDefaultBranch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, catalog: String)(using signer: RequestSigner, ec: ExecutionContext): getDefaultBranch = new getDefaultBranch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}:getDefaultBranch").addQueryStringParameters("catalog" -> catalog.toString))
					given Conversion[getDefaultBranch, Future[Schema.GoogleCloudRetailV2GetDefaultBranchResponse]] = (fun: getDefaultBranch) => fun.apply()
				}
				/** Gets a CompletionConfig. */
				class getCompletionConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRetailV2CompletionConfig]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRetailV2CompletionConfig])
				}
				object getCompletionConfig {
					def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getCompletionConfig = new getCompletionConfig(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/completionConfig").addQueryStringParameters("name" -> name.toString))
					given Conversion[getCompletionConfig, Future[Schema.GoogleCloudRetailV2CompletionConfig]] = (fun: getCompletionConfig) => fun.apply()
				}
				/** Manages overal generative question feature state -- enables toggling feature on and off. */
				class updateGenerativeQuestionFeature(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. Indicates which fields in the provided GenerativeQuestionsFeatureConfig to update. If not set or empty, all supported fields are updated.<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new updateGenerativeQuestionFeature(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					/** Perform the request */
					def withGoogleCloudRetailV2GenerativeQuestionsFeatureConfig(body: Schema.GoogleCloudRetailV2GenerativeQuestionsFeatureConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudRetailV2GenerativeQuestionsFeatureConfig])
				}
				object updateGenerativeQuestionFeature {
					def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, catalog: String)(using signer: RequestSigner, ec: ExecutionContext): updateGenerativeQuestionFeature = new updateGenerativeQuestionFeature(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/generativeQuestionFeature").addQueryStringParameters("catalog" -> catalog.toString))
				}
				/** Completes the specified prefix with keyword suggestions. This feature is only available for users who have Retail Search enabled. Enable Retail Search on Cloud Console before using this feature. */
				class completeQuery(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRetailV2CompleteQueryResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRetailV2CompleteQueryResponse])
				}
				object completeQuery {
					def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, catalog: String, query: String, visitorId: String, languageCodes: String, deviceType: String, dataset: String, maxSuggestions: Int, enableAttributeSuggestions: Boolean, entity: String)(using signer: RequestSigner, ec: ExecutionContext): completeQuery = new completeQuery(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}:completeQuery").addQueryStringParameters("catalog" -> catalog.toString, "query" -> query.toString, "visitorId" -> visitorId.toString, "languageCodes" -> languageCodes.toString, "deviceType" -> deviceType.toString, "dataset" -> dataset.toString, "maxSuggestions" -> maxSuggestions.toString, "enableAttributeSuggestions" -> enableAttributeSuggestions.toString, "entity" -> entity.toString))
					given Conversion[completeQuery, Future[Schema.GoogleCloudRetailV2CompleteQueryResponse]] = (fun: completeQuery) => fun.apply()
				}
				/** Manages overal generative question feature state -- enables toggling feature on and off. */
				class getGenerativeQuestionFeature(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRetailV2GenerativeQuestionsFeatureConfig]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRetailV2GenerativeQuestionsFeatureConfig])
				}
				object getGenerativeQuestionFeature {
					def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, catalog: String)(using signer: RequestSigner, ec: ExecutionContext): getGenerativeQuestionFeature = new getGenerativeQuestionFeature(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/generativeQuestionFeature").addQueryStringParameters("catalog" -> catalog.toString))
					given Conversion[getGenerativeQuestionFeature, Future[Schema.GoogleCloudRetailV2GenerativeQuestionsFeatureConfig]] = (fun: getGenerativeQuestionFeature) => fun.apply()
				}
				/** Updates the AttributesConfig. The catalog attributes in the request will be updated in the catalog, or inserted if they do not exist. Existing catalog attributes not included in the request will remain unchanged. Attributes that are assigned to products, but do not exist at the catalog level, are always included in the response. The product attribute is assigned default values for missing catalog attribute fields, e.g., searchable and dynamic facetable options. */
				class updateAttributesConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudRetailV2AttributesConfig(body: Schema.GoogleCloudRetailV2AttributesConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudRetailV2AttributesConfig])
				}
				object updateAttributesConfig {
					def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): updateAttributesConfig = new updateAttributesConfig(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/attributesConfig").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Gets an AttributesConfig. */
				class getAttributesConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRetailV2AttributesConfig]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRetailV2AttributesConfig])
				}
				object getAttributesConfig {
					def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getAttributesConfig = new getAttributesConfig(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/attributesConfig").addQueryStringParameters("name" -> name.toString))
					given Conversion[getAttributesConfig, Future[Schema.GoogleCloudRetailV2AttributesConfig]] = (fun: getAttributesConfig) => fun.apply()
				}
				/** Updates the CompletionConfigs. */
				class updateCompletionConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudRetailV2CompletionConfig(body: Schema.GoogleCloudRetailV2CompletionConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudRetailV2CompletionConfig])
				}
				object updateCompletionConfig {
					def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): updateCompletionConfig = new updateCompletionConfig(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/completionConfig").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Exports analytics metrics. `Operation.response` is of type `ExportAnalyticsMetricsResponse`. `Operation.metadata` is of type `ExportMetadata`. */
				class exportAnalyticsMetrics(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudRetailV2ExportAnalyticsMetricsRequest(body: Schema.GoogleCloudRetailV2ExportAnalyticsMetricsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object exportAnalyticsMetrics {
					def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, catalog: String)(using signer: RequestSigner, ec: ExecutionContext): exportAnalyticsMetrics = new exportAnalyticsMetrics(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}:exportAnalyticsMetrics").addQueryStringParameters("catalog" -> catalog.toString))
				}
				/** Updates the Catalogs. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudRetailV2Catalog(body: Schema.GoogleCloudRetailV2Catalog) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudRetailV2Catalog])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Set a specified branch id as default branch. API methods such as SearchService.Search, ProductService.GetProduct, ProductService.ListProducts will treat requests using "default_branch" to the actual branch id set as default. For example, if `projects/&#42;/locations/&#42;/catalogs/&#42;/branches/1` is set as default, setting SearchRequest.branch to `projects/&#42;/locations/&#42;/catalogs/&#42;/branches/default_branch` is equivalent to setting SearchRequest.branch to `projects/&#42;/locations/&#42;/catalogs/&#42;/branches/1`. Using multiple branches can be useful when developers would like to have a staging branch to test and verify for future usage. When it becomes ready, developers switch on the staging branch using this API while keeping using `projects/&#42;/locations/&#42;/catalogs/&#42;/branches/default_branch` as SearchRequest.branch to route the traffic to this staging branch. CAUTION: If you have live predict/search traffic, switching the default branch could potentially cause outages if the ID space of the new branch is very different from the old one. More specifically: &#42; PredictionService will only return product IDs from branch {newBranch}. &#42; SearchService will only return product IDs from branch {newBranch} (if branch is not explicitly set). &#42; UserEventService will only join events with products from branch {newBranch}. */
				class setDefaultBranch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudRetailV2SetDefaultBranchRequest(body: Schema.GoogleCloudRetailV2SetDefaultBranchRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object setDefaultBranch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, catalog: String)(using signer: RequestSigner, ec: ExecutionContext): setDefaultBranch = new setDefaultBranch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}:setDefaultBranch").addQueryStringParameters("catalog" -> catalog.toString))
				}
				/** Lists all the Catalogs associated with the project. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRetailV2ListCatalogsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRetailV2ListCatalogsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleCloudRetailV2ListCatalogsResponse]] = (fun: list) => fun.apply()
				}
				object userEvents {
					/** Starts a user-event rejoin operation with latest product catalog. Events are not annotated with detailed product information for products that are missing from the catalog when the user event is ingested. These events are stored as unjoined events with limited usage on training and serving. You can use this method to start a join operation on specified events with the latest version of product catalog. You can also use this method to correct events joined with the wrong product catalog. A rejoin operation can take hours or days to complete. */
					class rejoin(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRetailV2RejoinUserEventsRequest(body: Schema.GoogleCloudRetailV2RejoinUserEventsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object rejoin {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): rejoin = new rejoin(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/userEvents:rejoin").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Deletes permanently all user events specified by the filter provided. Depending on the number of events specified by the filter, this operation could take hours or days to complete. To test a filter, use the list command first. */
					class purge(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRetailV2PurgeUserEventsRequest(body: Schema.GoogleCloudRetailV2PurgeUserEventsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object purge {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): purge = new purge(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/userEvents:purge").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Bulk import of User events. Request processing might be synchronous. Events that already exist are skipped. Use this method for backfilling historical user events. `Operation.response` is of type `ImportResponse`. Note that it is possible for a subset of the items to be successfully inserted. `Operation.metadata` is of type `ImportMetadata`. */
					class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRetailV2ImportUserEventsRequest(body: Schema.GoogleCloudRetailV2ImportUserEventsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/userEvents:import").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Writes a single user event from the browser. This uses a GET request to due to browser restriction of POST-ing to a 3rd party domain. This method is used only by the Retail API JavaScript pixel and Google Tag Manager. Users should not call this method directly. */
					class collect(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleApiHttpBody]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleApiHttpBody])
					}
					object collect {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String, prebuiltRule: String, userEvent: String, uri: String, ets: String, rawJson: String)(using signer: RequestSigner, ec: ExecutionContext): collect = new collect(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/userEvents:collect").addQueryStringParameters("parent" -> parent.toString, "prebuiltRule" -> prebuiltRule.toString, "userEvent" -> userEvent.toString, "uri" -> uri.toString, "ets" -> ets.toString, "rawJson" -> rawJson.toString))
						given Conversion[collect, Future[Schema.GoogleApiHttpBody]] = (fun: collect) => fun.apply()
					}
					/** Writes a single user event. */
					class write(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRetailV2UserEvent(body: Schema.GoogleCloudRetailV2UserEvent) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRetailV2UserEvent])
					}
					object write {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String, writeAsync: Boolean)(using signer: RequestSigner, ec: ExecutionContext): write = new write(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/userEvents:write").addQueryStringParameters("parent" -> parent.toString, "writeAsync" -> writeAsync.toString))
					}
				}
				object servingConfigs {
					/** Creates a ServingConfig. A maximum of 100 ServingConfigs are allowed in a Catalog, otherwise a FAILED_PRECONDITION error is returned. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRetailV2ServingConfig(body: Schema.GoogleCloudRetailV2ServingConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRetailV2ServingConfig])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String, servingConfigId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/servingConfigs").addQueryStringParameters("parent" -> parent.toString, "servingConfigId" -> servingConfigId.toString))
					}
					/** Enables a Control on the specified ServingConfig. The control is added in the last position of the list of controls it belongs to (e.g. if it's a facet spec control it will be applied in the last position of servingConfig.facetSpecIds) Returns a ALREADY_EXISTS error if the control has already been applied. Returns a FAILED_PRECONDITION error if the addition could exceed maximum number of control allowed for that type of control. */
					class addControl(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRetailV2AddControlRequest(body: Schema.GoogleCloudRetailV2AddControlRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRetailV2ServingConfig])
					}
					object addControl {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, servingConfigsId :PlayApi, servingConfig: String)(using signer: RequestSigner, ec: ExecutionContext): addControl = new addControl(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/servingConfigs/${servingConfigsId}:addControl").addQueryStringParameters("servingConfig" -> servingConfig.toString))
					}
					/** Deletes a ServingConfig. Returns a NotFound error if the ServingConfig does not exist. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, servingConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/servingConfigs/${servingConfigsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					/** Disables a Control on the specified ServingConfig. The control is removed from the ServingConfig. Returns a NOT_FOUND error if the Control is not enabled for the ServingConfig. */
					class removeControl(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRetailV2RemoveControlRequest(body: Schema.GoogleCloudRetailV2RemoveControlRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRetailV2ServingConfig])
					}
					object removeControl {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, servingConfigsId :PlayApi, servingConfig: String)(using signer: RequestSigner, ec: ExecutionContext): removeControl = new removeControl(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/servingConfigs/${servingConfigsId}:removeControl").addQueryStringParameters("servingConfig" -> servingConfig.toString))
					}
					/** Updates a ServingConfig. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRetailV2ServingConfig(body: Schema.GoogleCloudRetailV2ServingConfig) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudRetailV2ServingConfig])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, servingConfigsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/servingConfigs/${servingConfigsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Gets a ServingConfig. Returns a NotFound error if the ServingConfig does not exist. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRetailV2ServingConfig]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRetailV2ServingConfig])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, servingConfigsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/servingConfigs/${servingConfigsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudRetailV2ServingConfig]] = (fun: get) => fun.apply()
					}
					/** Makes a recommendation prediction. */
					class predict(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRetailV2PredictRequest(body: Schema.GoogleCloudRetailV2PredictRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRetailV2PredictResponse])
					}
					object predict {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, servingConfigsId :PlayApi, placement: String)(using signer: RequestSigner, ec: ExecutionContext): predict = new predict(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/servingConfigs/${servingConfigsId}:predict").addQueryStringParameters("placement" -> placement.toString))
					}
					/** Lists all ServingConfigs linked to this catalog. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRetailV2ListServingConfigsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Maximum number of results to return. If unspecified, defaults to 100. If a value greater than 100 is provided, at most 100 results are returned.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A page token, received from a previous `ListServingConfigs` call. Provide this to retrieve the subsequent page. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRetailV2ListServingConfigsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/servingConfigs").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudRetailV2ListServingConfigsResponse]] = (fun: list) => fun.apply()
					}
					/** Performs a search. This feature is only available for users who have Retail Search enabled. Enable Retail Search on Cloud Console before using this feature. */
					class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRetailV2SearchRequest(body: Schema.GoogleCloudRetailV2SearchRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRetailV2SearchResponse])
					}
					object search {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, servingConfigsId :PlayApi, placement: String)(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/servingConfigs/${servingConfigsId}:search").addQueryStringParameters("placement" -> placement.toString))
					}
				}
				object models {
					/** Tunes an existing model. */
					class tune(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRetailV2TuneModelRequest(body: Schema.GoogleCloudRetailV2TuneModelRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object tune {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, modelsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): tune = new tune(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/models/${modelsId}:tune").addQueryStringParameters("name" -> name.toString))
					}
					/** Resumes the training of an existing model. */
					class resume(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRetailV2ResumeModelRequest(body: Schema.GoogleCloudRetailV2ResumeModelRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRetailV2Model])
					}
					object resume {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, modelsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): resume = new resume(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/models/${modelsId}:resume").addQueryStringParameters("name" -> name.toString))
					}
					/** Deletes an existing model. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, modelsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/models/${modelsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					/** Gets a model. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRetailV2Model]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRetailV2Model])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, modelsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/models/${modelsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudRetailV2Model]] = (fun: get) => fun.apply()
					}
					/** Update of model metadata. Only fields that currently can be updated are: `filtering_option` and `periodic_tuning_state`. If other values are provided, this API method ignores them. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Indicates which fields in the provided 'model' to update. If not set, by default updates all fields.<br>Format: google-fieldmask */
						def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
						/** Perform the request */
						def withGoogleCloudRetailV2Model(body: Schema.GoogleCloudRetailV2Model) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudRetailV2Model])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, modelsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/models/${modelsId}").addQueryStringParameters("name" -> name.toString))
					}
					/** Lists all the models linked to this event store. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRetailV2ListModelsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Maximum number of results to return. If unspecified, defaults to 50. Max allowed value is 1000.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A page token, received from a previous `ListModels` call. Provide this to retrieve the subsequent page. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRetailV2ListModelsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/models").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudRetailV2ListModelsResponse]] = (fun: list) => fun.apply()
					}
					/** Pauses the training of an existing model. */
					class pause(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRetailV2PauseModelRequest(body: Schema.GoogleCloudRetailV2PauseModelRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRetailV2Model])
					}
					object pause {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, modelsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): pause = new pause(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/models/${modelsId}:pause").addQueryStringParameters("name" -> name.toString))
					}
					/** Creates a new model. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Whether to run a dry run to validate the request (without actually creating the model). */
						def withDryRun(dryRun: Boolean) = new create(req.addQueryStringParameters("dryRun" -> dryRun.toString))
						/** Perform the request */
						def withGoogleCloudRetailV2Model(body: Schema.GoogleCloudRetailV2Model) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/models").addQueryStringParameters("parent" -> parent.toString))
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
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
					}
					/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
					}
				}
				object placements {
					/** Performs a search. This feature is only available for users who have Retail Search enabled. Enable Retail Search on Cloud Console before using this feature. */
					class search(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRetailV2SearchRequest(body: Schema.GoogleCloudRetailV2SearchRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRetailV2SearchResponse])
					}
					object search {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, placementsId :PlayApi, placement: String)(using signer: RequestSigner, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/placements/${placementsId}:search").addQueryStringParameters("placement" -> placement.toString))
					}
					/** Makes a recommendation prediction. */
					class predict(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRetailV2PredictRequest(body: Schema.GoogleCloudRetailV2PredictRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRetailV2PredictResponse])
					}
					object predict {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, placementsId :PlayApi, placement: String)(using signer: RequestSigner, ec: ExecutionContext): predict = new predict(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/placements/${placementsId}:predict").addQueryStringParameters("placement" -> placement.toString))
					}
				}
				object attributesConfig {
					/** Adds the specified CatalogAttribute to the AttributesConfig. If the CatalogAttribute to add already exists, an ALREADY_EXISTS error is returned. */
					class addCatalogAttribute(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRetailV2AddCatalogAttributeRequest(body: Schema.GoogleCloudRetailV2AddCatalogAttributeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRetailV2AttributesConfig])
					}
					object addCatalogAttribute {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, attributesConfig: String)(using signer: RequestSigner, ec: ExecutionContext): addCatalogAttribute = new addCatalogAttribute(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/attributesConfig:addCatalogAttribute").addQueryStringParameters("attributesConfig" -> attributesConfig.toString))
					}
					/** Removes the specified CatalogAttribute from the AttributesConfig. If the CatalogAttribute to remove does not exist, a NOT_FOUND error is returned. */
					class removeCatalogAttribute(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRetailV2RemoveCatalogAttributeRequest(body: Schema.GoogleCloudRetailV2RemoveCatalogAttributeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRetailV2AttributesConfig])
					}
					object removeCatalogAttribute {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, attributesConfig: String)(using signer: RequestSigner, ec: ExecutionContext): removeCatalogAttribute = new removeCatalogAttribute(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/attributesConfig:removeCatalogAttribute").addQueryStringParameters("attributesConfig" -> attributesConfig.toString))
					}
					/** Replaces the specified CatalogAttribute in the AttributesConfig by updating the catalog attribute with the same CatalogAttribute.key. If the CatalogAttribute to replace does not exist, a NOT_FOUND error is returned. */
					class replaceCatalogAttribute(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRetailV2ReplaceCatalogAttributeRequest(body: Schema.GoogleCloudRetailV2ReplaceCatalogAttributeRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRetailV2AttributesConfig])
					}
					object replaceCatalogAttribute {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, attributesConfig: String)(using signer: RequestSigner, ec: ExecutionContext): replaceCatalogAttribute = new replaceCatalogAttribute(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/attributesConfig:replaceCatalogAttribute").addQueryStringParameters("attributesConfig" -> attributesConfig.toString))
					}
				}
				object generativeQuestion {
					/** Allows management of multiple questions. */
					class batchUpdate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Resource name of the parent catalog. Format: projects/{project}/locations/{location}/catalogs/{catalog} */
						def withParent(parent: String) = new batchUpdate(req.addQueryStringParameters("parent" -> parent.toString))
						/** Perform the request */
						def withGoogleCloudRetailV2BatchUpdateGenerativeQuestionConfigsRequest(body: Schema.GoogleCloudRetailV2BatchUpdateGenerativeQuestionConfigsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRetailV2BatchUpdateGenerativeQuestionConfigsResponse])
					}
					object batchUpdate {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi)(using signer: RequestSigner, ec: ExecutionContext): batchUpdate = new batchUpdate(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/generativeQuestion:batchUpdate").addQueryStringParameters())
					}
				}
				object branches {
					object operations {
						/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, branchesId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/branches/${branchesId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
						}
					}
					object products {
						/** Creates a Product. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudRetailV2Product(body: Schema.GoogleCloudRetailV2Product) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRetailV2Product])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, branchesId :PlayApi, parent: String, productId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/branches/${branchesId}/products").addQueryStringParameters("parent" -> parent.toString, "productId" -> productId.toString))
						}
						/** Permanently deletes all selected Products under a branch. This process is asynchronous. If the request is valid, the removal will be enqueued and processed offline. Depending on the number of Products, this operation could take hours to complete. Before the operation completes, some Products may still be returned by ProductService.GetProduct or ProductService.ListProducts. Depending on the number of Products, this operation could take hours to complete. To get a sample of Products that would be deleted, set PurgeProductsRequest.force to false. */
						class purge(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudRetailV2PurgeProductsRequest(body: Schema.GoogleCloudRetailV2PurgeProductsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object purge {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, branchesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): purge = new purge(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/branches/${branchesId}/products:purge").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Bulk import of multiple Products. Request processing may be synchronous. Non-existing items are created. Note that it is possible for a subset of the Products to be successfully updated. */
						class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudRetailV2ImportProductsRequest(body: Schema.GoogleCloudRetailV2ImportProductsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object `import` {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, branchesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/branches/${branchesId}/products:import").addQueryStringParameters("parent" -> parent.toString))
						}
						/** We recommend that you use the ProductService.AddLocalInventories method instead of the ProductService.AddFulfillmentPlaces method. ProductService.AddLocalInventories achieves the same results but provides more fine-grained control over ingesting local inventory data. Incrementally adds place IDs to Product.fulfillment_info.place_ids. This process is asynchronous and does not require the Product to exist before updating fulfillment information. If the request is valid, the update will be enqueued and processed downstream. As a consequence, when a response is returned, the added place IDs are not immediately manifested in the Product queried by ProductService.GetProduct or ProductService.ListProducts. The returned Operations will be obsolete after 1 day, and GetOperation API will return NOT_FOUND afterwards. If conflicting updates are issued, the Operations associated with the stale updates will not be marked as done until being obsolete. */
						class addFulfillmentPlaces(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudRetailV2AddFulfillmentPlacesRequest(body: Schema.GoogleCloudRetailV2AddFulfillmentPlacesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object addFulfillmentPlaces {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, branchesId :PlayApi, productsId :PlayApi, product: String)(using signer: RequestSigner, ec: ExecutionContext): addFulfillmentPlaces = new addFulfillmentPlaces(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/branches/${branchesId}/products/${productsId}:addFulfillmentPlaces").addQueryStringParameters("product" -> product.toString))
						}
						/** Deletes a Product. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, branchesId :PlayApi, productsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/branches/${branchesId}/products/${productsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						/** Updates local inventory information for a Product at a list of places, while respecting the last update timestamps of each inventory field. This process is asynchronous and does not require the Product to exist before updating inventory information. If the request is valid, the update will be enqueued and processed downstream. As a consequence, when a response is returned, updates are not immediately manifested in the Product queried by ProductService.GetProduct or ProductService.ListProducts. Local inventory information can only be modified using this method. ProductService.CreateProduct and ProductService.UpdateProduct has no effect on local inventories. The returned Operations will be obsolete after 1 day, and GetOperation API will return NOT_FOUND afterwards. If conflicting updates are issued, the Operations associated with the stale updates will not be marked as done until being obsolete. */
						class addLocalInventories(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudRetailV2AddLocalInventoriesRequest(body: Schema.GoogleCloudRetailV2AddLocalInventoriesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object addLocalInventories {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, branchesId :PlayApi, productsId :PlayApi, product: String)(using signer: RequestSigner, ec: ExecutionContext): addLocalInventories = new addLocalInventories(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/branches/${branchesId}/products/${productsId}:addLocalInventories").addQueryStringParameters("product" -> product.toString))
						}
						/** Updates inventory information for a Product while respecting the last update timestamps of each inventory field. This process is asynchronous and does not require the Product to exist before updating fulfillment information. If the request is valid, the update is enqueued and processed downstream. As a consequence, when a response is returned, updates are not immediately manifested in the Product queried by ProductService.GetProduct or ProductService.ListProducts. When inventory is updated with ProductService.CreateProduct and ProductService.UpdateProduct, the specified inventory field value(s) overwrite any existing value(s) while ignoring the last update time for this field. Furthermore, the last update times for the specified inventory fields are overwritten by the times of the ProductService.CreateProduct or ProductService.UpdateProduct request. If no inventory fields are set in CreateProductRequest.product, then any pre-existing inventory information for this product is used. If no inventory fields are set in SetInventoryRequest.set_mask, then any existing inventory information is preserved. Pre-existing inventory information can only be updated with ProductService.SetInventory, ProductService.AddFulfillmentPlaces, and ProductService.RemoveFulfillmentPlaces. The returned Operations is obsolete after one day, and the GetOperation API returns `NOT_FOUND` afterwards. If conflicting updates are issued, the Operations associated with the stale updates are not marked as done until they are obsolete. */
						class setInventory(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudRetailV2SetInventoryRequest(body: Schema.GoogleCloudRetailV2SetInventoryRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object setInventory {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, branchesId :PlayApi, productsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): setInventory = new setInventory(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/branches/${branchesId}/products/${productsId}:setInventory").addQueryStringParameters("name" -> name.toString))
						}
						/** Gets a Product. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRetailV2Product]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRetailV2Product])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, branchesId :PlayApi, productsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/branches/${branchesId}/products/${productsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudRetailV2Product]] = (fun: get) => fun.apply()
						}
						/** Updates a Product. */
						class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudRetailV2Product(body: Schema.GoogleCloudRetailV2Product) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudRetailV2Product])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, branchesId :PlayApi, productsId :PlayApi, name: String, updateMask: String, allowMissing: Boolean)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/branches/${branchesId}/products/${productsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "allowMissing" -> allowMissing.toString))
						}
						/** Gets a list of Products. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRetailV2ListProductsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRetailV2ListProductsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, branchesId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, readMask: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/branches/${branchesId}/products").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "readMask" -> readMask.toString))
							given Conversion[list, Future[Schema.GoogleCloudRetailV2ListProductsResponse]] = (fun: list) => fun.apply()
						}
						/** We recommend that you use the ProductService.RemoveLocalInventories method instead of the ProductService.RemoveFulfillmentPlaces method. ProductService.RemoveLocalInventories achieves the same results but provides more fine-grained control over ingesting local inventory data. Incrementally removes place IDs from a Product.fulfillment_info.place_ids. This process is asynchronous and does not require the Product to exist before updating fulfillment information. If the request is valid, the update will be enqueued and processed downstream. As a consequence, when a response is returned, the removed place IDs are not immediately manifested in the Product queried by ProductService.GetProduct or ProductService.ListProducts. The returned Operations will be obsolete after 1 day, and GetOperation API will return NOT_FOUND afterwards. If conflicting updates are issued, the Operations associated with the stale updates will not be marked as done until being obsolete. */
						class removeFulfillmentPlaces(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudRetailV2RemoveFulfillmentPlacesRequest(body: Schema.GoogleCloudRetailV2RemoveFulfillmentPlacesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object removeFulfillmentPlaces {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, branchesId :PlayApi, productsId :PlayApi, product: String)(using signer: RequestSigner, ec: ExecutionContext): removeFulfillmentPlaces = new removeFulfillmentPlaces(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/branches/${branchesId}/products/${productsId}:removeFulfillmentPlaces").addQueryStringParameters("product" -> product.toString))
						}
						/** Remove local inventory information for a Product at a list of places at a removal timestamp. This process is asynchronous. If the request is valid, the removal will be enqueued and processed downstream. As a consequence, when a response is returned, removals are not immediately manifested in the Product queried by ProductService.GetProduct or ProductService.ListProducts. Local inventory information can only be removed using this method. ProductService.CreateProduct and ProductService.UpdateProduct has no effect on local inventories. The returned Operations will be obsolete after 1 day, and GetOperation API will return NOT_FOUND afterwards. If conflicting updates are issued, the Operations associated with the stale updates will not be marked as done until being obsolete. */
						class removeLocalInventories(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudRetailV2RemoveLocalInventoriesRequest(body: Schema.GoogleCloudRetailV2RemoveLocalInventoriesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object removeLocalInventories {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, branchesId :PlayApi, productsId :PlayApi, product: String)(using signer: RequestSigner, ec: ExecutionContext): removeLocalInventories = new removeLocalInventories(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/branches/${branchesId}/products/${productsId}:removeLocalInventories").addQueryStringParameters("product" -> product.toString))
						}
					}
				}
				object generativeQuestions {
					/** Returns all questions for a given catalog. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRetailV2ListGenerativeQuestionConfigsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRetailV2ListGenerativeQuestionConfigsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/generativeQuestions").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudRetailV2ListGenerativeQuestionConfigsResponse]] = (fun: list) => fun.apply()
					}
				}
				object completionData {
					/** Bulk import of processed completion dataset. Request processing is asynchronous. Partial updating is not supported. The operation is successfully finished only after the imported suggestions are indexed successfully and ready for serving. The process takes hours. This feature is only available for users who have Retail Search enabled. Enable Retail Search on Cloud Console before using this feature. */
					class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRetailV2ImportCompletionDataRequest(body: Schema.GoogleCloudRetailV2ImportCompletionDataRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/completionData:import").addQueryStringParameters("parent" -> parent.toString))
					}
				}
				object controls {
					/** Creates a Control. If the Control to create already exists, an ALREADY_EXISTS error is returned. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRetailV2Control(body: Schema.GoogleCloudRetailV2Control) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudRetailV2Control])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String, controlId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/controls").addQueryStringParameters("parent" -> parent.toString, "controlId" -> controlId.toString))
					}
					/** Deletes a Control. If the Control to delete does not exist, a NOT_FOUND error is returned. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, controlsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/controls/${controlsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					/** Gets a Control. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRetailV2Control]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRetailV2Control])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, controlsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/controls/${controlsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudRetailV2Control]] = (fun: get) => fun.apply()
					}
					/** Updates a Control. Control cannot be set to a different oneof field, if so an INVALID_ARGUMENT is returned. If the Control to update does not exist, a NOT_FOUND error is returned. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudRetailV2Control(body: Schema.GoogleCloudRetailV2Control) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudRetailV2Control])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, controlsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/controls/${controlsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Lists all Controls by their parent Catalog. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRetailV2ListControlsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. Maximum number of results to return. If unspecified, defaults to 50. Max allowed value is 1000.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A page token, received from a previous `ListControls` call. Provide this to retrieve the subsequent page. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. A filter to apply on the list results. Supported features: &#42; List all the products under the parent branch if filter is unset. &#42; List controls that are used in a single ServingConfig: 'serving_config = "boosted_home_page_cvr"' */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudRetailV2ListControlsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/controls").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudRetailV2ListControlsResponse]] = (fun: list) => fun.apply()
					}
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
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v2/projects/${projectsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
			}
			/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object get {
				def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v2/projects/${projectsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
			}
		}
	}
}
