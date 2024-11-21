package com.boresjo.play.api.google.retail

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://retail.googleapis.com/"

	object projects {
		object locations {
			object operations {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/operations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
				}
			}
			object catalogs {
				class updateGenerativeQuestion(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. Indicates which fields in the provided GenerativeQuestionConfig to update. The following are NOT supported: &#42; GenerativeQuestionConfig.frequency If not set or empty, all supported fields are updated.<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new updateGenerativeQuestion(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					def withGoogleCloudRetailV2GenerativeQuestionConfig(body: Schema.GoogleCloudRetailV2GenerativeQuestionConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudRetailV2GenerativeQuestionConfig])
				}
				object updateGenerativeQuestion {
					def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, catalog: String)(using auth: AuthToken, ec: ExecutionContext): updateGenerativeQuestion = new updateGenerativeQuestion(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/generativeQuestion")).addQueryStringParameters("catalog" -> catalog.toString))
				}
				class getDefaultBranch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRetailV2GetDefaultBranchResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRetailV2GetDefaultBranchResponse])
				}
				object getDefaultBranch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, catalog: String)(using auth: AuthToken, ec: ExecutionContext): getDefaultBranch = new getDefaultBranch(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}:getDefaultBranch")).addQueryStringParameters("catalog" -> catalog.toString))
					given Conversion[getDefaultBranch, Future[Schema.GoogleCloudRetailV2GetDefaultBranchResponse]] = (fun: getDefaultBranch) => fun.apply()
				}
				class getCompletionConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRetailV2CompletionConfig]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRetailV2CompletionConfig])
				}
				object getCompletionConfig {
					def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getCompletionConfig = new getCompletionConfig(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/completionConfig")).addQueryStringParameters("name" -> name.toString))
					given Conversion[getCompletionConfig, Future[Schema.GoogleCloudRetailV2CompletionConfig]] = (fun: getCompletionConfig) => fun.apply()
				}
				class updateGenerativeQuestionFeature(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. Indicates which fields in the provided GenerativeQuestionsFeatureConfig to update. If not set or empty, all supported fields are updated.<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new updateGenerativeQuestionFeature(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					def withGoogleCloudRetailV2GenerativeQuestionsFeatureConfig(body: Schema.GoogleCloudRetailV2GenerativeQuestionsFeatureConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudRetailV2GenerativeQuestionsFeatureConfig])
				}
				object updateGenerativeQuestionFeature {
					def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, catalog: String)(using auth: AuthToken, ec: ExecutionContext): updateGenerativeQuestionFeature = new updateGenerativeQuestionFeature(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/generativeQuestionFeature")).addQueryStringParameters("catalog" -> catalog.toString))
				}
				class completeQuery(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRetailV2CompleteQueryResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRetailV2CompleteQueryResponse])
				}
				object completeQuery {
					def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, catalog: String, query: String, visitorId: String, languageCodes: String, deviceType: String, dataset: String, maxSuggestions: Int, enableAttributeSuggestions: Boolean, entity: String)(using auth: AuthToken, ec: ExecutionContext): completeQuery = new completeQuery(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}:completeQuery")).addQueryStringParameters("catalog" -> catalog.toString, "query" -> query.toString, "visitorId" -> visitorId.toString, "languageCodes" -> languageCodes.toString, "deviceType" -> deviceType.toString, "dataset" -> dataset.toString, "maxSuggestions" -> maxSuggestions.toString, "enableAttributeSuggestions" -> enableAttributeSuggestions.toString, "entity" -> entity.toString))
					given Conversion[completeQuery, Future[Schema.GoogleCloudRetailV2CompleteQueryResponse]] = (fun: completeQuery) => fun.apply()
				}
				class getGenerativeQuestionFeature(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRetailV2GenerativeQuestionsFeatureConfig]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRetailV2GenerativeQuestionsFeatureConfig])
				}
				object getGenerativeQuestionFeature {
					def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, catalog: String)(using auth: AuthToken, ec: ExecutionContext): getGenerativeQuestionFeature = new getGenerativeQuestionFeature(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/generativeQuestionFeature")).addQueryStringParameters("catalog" -> catalog.toString))
					given Conversion[getGenerativeQuestionFeature, Future[Schema.GoogleCloudRetailV2GenerativeQuestionsFeatureConfig]] = (fun: getGenerativeQuestionFeature) => fun.apply()
				}
				class updateAttributesConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudRetailV2AttributesConfig(body: Schema.GoogleCloudRetailV2AttributesConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudRetailV2AttributesConfig])
				}
				object updateAttributesConfig {
					def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): updateAttributesConfig = new updateAttributesConfig(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/attributesConfig")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class getAttributesConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRetailV2AttributesConfig]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRetailV2AttributesConfig])
				}
				object getAttributesConfig {
					def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getAttributesConfig = new getAttributesConfig(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/attributesConfig")).addQueryStringParameters("name" -> name.toString))
					given Conversion[getAttributesConfig, Future[Schema.GoogleCloudRetailV2AttributesConfig]] = (fun: getAttributesConfig) => fun.apply()
				}
				class updateCompletionConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudRetailV2CompletionConfig(body: Schema.GoogleCloudRetailV2CompletionConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudRetailV2CompletionConfig])
				}
				object updateCompletionConfig {
					def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): updateCompletionConfig = new updateCompletionConfig(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/completionConfig")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class exportAnalyticsMetrics(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudRetailV2ExportAnalyticsMetricsRequest(body: Schema.GoogleCloudRetailV2ExportAnalyticsMetricsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object exportAnalyticsMetrics {
					def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, catalog: String)(using auth: AuthToken, ec: ExecutionContext): exportAnalyticsMetrics = new exportAnalyticsMetrics(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}:exportAnalyticsMetrics")).addQueryStringParameters("catalog" -> catalog.toString))
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudRetailV2Catalog(body: Schema.GoogleCloudRetailV2Catalog) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudRetailV2Catalog])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class setDefaultBranch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudRetailV2SetDefaultBranchRequest(body: Schema.GoogleCloudRetailV2SetDefaultBranchRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object setDefaultBranch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, catalog: String)(using auth: AuthToken, ec: ExecutionContext): setDefaultBranch = new setDefaultBranch(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}:setDefaultBranch")).addQueryStringParameters("catalog" -> catalog.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRetailV2ListCatalogsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRetailV2ListCatalogsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleCloudRetailV2ListCatalogsResponse]] = (fun: list) => fun.apply()
				}
				object userEvents {
					class rejoin(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRetailV2RejoinUserEventsRequest(body: Schema.GoogleCloudRetailV2RejoinUserEventsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object rejoin {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): rejoin = new rejoin(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/userEvents:rejoin")).addQueryStringParameters("parent" -> parent.toString))
					}
					class purge(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRetailV2PurgeUserEventsRequest(body: Schema.GoogleCloudRetailV2PurgeUserEventsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object purge {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): purge = new purge(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/userEvents:purge")).addQueryStringParameters("parent" -> parent.toString))
					}
					class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRetailV2ImportUserEventsRequest(body: Schema.GoogleCloudRetailV2ImportUserEventsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/userEvents:import")).addQueryStringParameters("parent" -> parent.toString))
					}
					class collect(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleApiHttpBody]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleApiHttpBody])
					}
					object collect {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String, prebuiltRule: String, userEvent: String, uri: String, ets: String, rawJson: String)(using auth: AuthToken, ec: ExecutionContext): collect = new collect(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/userEvents:collect")).addQueryStringParameters("parent" -> parent.toString, "prebuiltRule" -> prebuiltRule.toString, "userEvent" -> userEvent.toString, "uri" -> uri.toString, "ets" -> ets.toString, "rawJson" -> rawJson.toString))
						given Conversion[collect, Future[Schema.GoogleApiHttpBody]] = (fun: collect) => fun.apply()
					}
					class write(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRetailV2UserEvent(body: Schema.GoogleCloudRetailV2UserEvent) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRetailV2UserEvent])
					}
					object write {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String, writeAsync: Boolean)(using auth: AuthToken, ec: ExecutionContext): write = new write(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/userEvents:write")).addQueryStringParameters("parent" -> parent.toString, "writeAsync" -> writeAsync.toString))
					}
				}
				object servingConfigs {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRetailV2ServingConfig(body: Schema.GoogleCloudRetailV2ServingConfig) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRetailV2ServingConfig])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String, servingConfigId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/servingConfigs")).addQueryStringParameters("parent" -> parent.toString, "servingConfigId" -> servingConfigId.toString))
					}
					class addControl(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRetailV2AddControlRequest(body: Schema.GoogleCloudRetailV2AddControlRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRetailV2ServingConfig])
					}
					object addControl {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, servingConfigsId :PlayApi, servingConfig: String)(using auth: AuthToken, ec: ExecutionContext): addControl = new addControl(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/servingConfigs/${servingConfigsId}:addControl")).addQueryStringParameters("servingConfig" -> servingConfig.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, servingConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/servingConfigs/${servingConfigsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					class removeControl(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRetailV2RemoveControlRequest(body: Schema.GoogleCloudRetailV2RemoveControlRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRetailV2ServingConfig])
					}
					object removeControl {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, servingConfigsId :PlayApi, servingConfig: String)(using auth: AuthToken, ec: ExecutionContext): removeControl = new removeControl(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/servingConfigs/${servingConfigsId}:removeControl")).addQueryStringParameters("servingConfig" -> servingConfig.toString))
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRetailV2ServingConfig(body: Schema.GoogleCloudRetailV2ServingConfig) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudRetailV2ServingConfig])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, servingConfigsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/servingConfigs/${servingConfigsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRetailV2ServingConfig]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRetailV2ServingConfig])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, servingConfigsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/servingConfigs/${servingConfigsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudRetailV2ServingConfig]] = (fun: get) => fun.apply()
					}
					class predict(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRetailV2PredictRequest(body: Schema.GoogleCloudRetailV2PredictRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRetailV2PredictResponse])
					}
					object predict {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, servingConfigsId :PlayApi, placement: String)(using auth: AuthToken, ec: ExecutionContext): predict = new predict(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/servingConfigs/${servingConfigsId}:predict")).addQueryStringParameters("placement" -> placement.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRetailV2ListServingConfigsResponse]) {
						/** Optional. Maximum number of results to return. If unspecified, defaults to 100. If a value greater than 100 is provided, at most 100 results are returned.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A page token, received from a previous `ListServingConfigs` call. Provide this to retrieve the subsequent page. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRetailV2ListServingConfigsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/servingConfigs")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudRetailV2ListServingConfigsResponse]] = (fun: list) => fun.apply()
					}
					class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRetailV2SearchRequest(body: Schema.GoogleCloudRetailV2SearchRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRetailV2SearchResponse])
					}
					object search {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, servingConfigsId :PlayApi, placement: String)(using auth: AuthToken, ec: ExecutionContext): search = new search(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/servingConfigs/${servingConfigsId}:search")).addQueryStringParameters("placement" -> placement.toString))
					}
				}
				object models {
					class tune(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRetailV2TuneModelRequest(body: Schema.GoogleCloudRetailV2TuneModelRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object tune {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, modelsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): tune = new tune(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/models/${modelsId}:tune")).addQueryStringParameters("name" -> name.toString))
					}
					class resume(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRetailV2ResumeModelRequest(body: Schema.GoogleCloudRetailV2ResumeModelRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRetailV2Model])
					}
					object resume {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, modelsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): resume = new resume(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/models/${modelsId}:resume")).addQueryStringParameters("name" -> name.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, modelsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/models/${modelsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRetailV2Model]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRetailV2Model])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, modelsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/models/${modelsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudRetailV2Model]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						/** Optional. Indicates which fields in the provided 'model' to update. If not set, by default updates all fields.<br>Format: google-fieldmask */
						def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
						def withGoogleCloudRetailV2Model(body: Schema.GoogleCloudRetailV2Model) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudRetailV2Model])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, modelsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/models/${modelsId}")).addQueryStringParameters("name" -> name.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRetailV2ListModelsResponse]) {
						/** Optional. Maximum number of results to return. If unspecified, defaults to 50. Max allowed value is 1000.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A page token, received from a previous `ListModels` call. Provide this to retrieve the subsequent page. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRetailV2ListModelsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/models")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudRetailV2ListModelsResponse]] = (fun: list) => fun.apply()
					}
					class pause(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRetailV2PauseModelRequest(body: Schema.GoogleCloudRetailV2PauseModelRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRetailV2Model])
					}
					object pause {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, modelsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): pause = new pause(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/models/${modelsId}:pause")).addQueryStringParameters("name" -> name.toString))
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						/** Optional. Whether to run a dry run to validate the request (without actually creating the model). */
						def withDryRun(dryRun: Boolean) = new create(req.addQueryStringParameters("dryRun" -> dryRun.toString))
						def withGoogleCloudRetailV2Model(body: Schema.GoogleCloudRetailV2Model) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/models")).addQueryStringParameters("parent" -> parent.toString))
					}
				}
				object operations {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/operations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
					}
				}
				object placements {
					class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRetailV2SearchRequest(body: Schema.GoogleCloudRetailV2SearchRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRetailV2SearchResponse])
					}
					object search {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, placementsId :PlayApi, placement: String)(using auth: AuthToken, ec: ExecutionContext): search = new search(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/placements/${placementsId}:search")).addQueryStringParameters("placement" -> placement.toString))
					}
					class predict(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRetailV2PredictRequest(body: Schema.GoogleCloudRetailV2PredictRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRetailV2PredictResponse])
					}
					object predict {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, placementsId :PlayApi, placement: String)(using auth: AuthToken, ec: ExecutionContext): predict = new predict(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/placements/${placementsId}:predict")).addQueryStringParameters("placement" -> placement.toString))
					}
				}
				object attributesConfig {
					class addCatalogAttribute(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRetailV2AddCatalogAttributeRequest(body: Schema.GoogleCloudRetailV2AddCatalogAttributeRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRetailV2AttributesConfig])
					}
					object addCatalogAttribute {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, attributesConfig: String)(using auth: AuthToken, ec: ExecutionContext): addCatalogAttribute = new addCatalogAttribute(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/attributesConfig:addCatalogAttribute")).addQueryStringParameters("attributesConfig" -> attributesConfig.toString))
					}
					class removeCatalogAttribute(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRetailV2RemoveCatalogAttributeRequest(body: Schema.GoogleCloudRetailV2RemoveCatalogAttributeRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRetailV2AttributesConfig])
					}
					object removeCatalogAttribute {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, attributesConfig: String)(using auth: AuthToken, ec: ExecutionContext): removeCatalogAttribute = new removeCatalogAttribute(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/attributesConfig:removeCatalogAttribute")).addQueryStringParameters("attributesConfig" -> attributesConfig.toString))
					}
					class replaceCatalogAttribute(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRetailV2ReplaceCatalogAttributeRequest(body: Schema.GoogleCloudRetailV2ReplaceCatalogAttributeRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRetailV2AttributesConfig])
					}
					object replaceCatalogAttribute {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, attributesConfig: String)(using auth: AuthToken, ec: ExecutionContext): replaceCatalogAttribute = new replaceCatalogAttribute(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/attributesConfig:replaceCatalogAttribute")).addQueryStringParameters("attributesConfig" -> attributesConfig.toString))
					}
				}
				object generativeQuestion {
					class batchUpdate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						/** Optional. Resource name of the parent catalog. Format: projects/{project}/locations/{location}/catalogs/{catalog} */
						def withParent(parent: String) = new batchUpdate(req.addQueryStringParameters("parent" -> parent.toString))
						def withGoogleCloudRetailV2BatchUpdateGenerativeQuestionConfigsRequest(body: Schema.GoogleCloudRetailV2BatchUpdateGenerativeQuestionConfigsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRetailV2BatchUpdateGenerativeQuestionConfigsResponse])
					}
					object batchUpdate {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi)(using auth: AuthToken, ec: ExecutionContext): batchUpdate = new batchUpdate(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/generativeQuestion:batchUpdate")).addQueryStringParameters())
					}
				}
				object branches {
					object operations {
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, branchesId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/branches/${branchesId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
						}
					}
					object products {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudRetailV2Product(body: Schema.GoogleCloudRetailV2Product) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRetailV2Product])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, branchesId :PlayApi, parent: String, productId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/branches/${branchesId}/products")).addQueryStringParameters("parent" -> parent.toString, "productId" -> productId.toString))
						}
						class purge(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudRetailV2PurgeProductsRequest(body: Schema.GoogleCloudRetailV2PurgeProductsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object purge {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, branchesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): purge = new purge(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/branches/${branchesId}/products:purge")).addQueryStringParameters("parent" -> parent.toString))
						}
						class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudRetailV2ImportProductsRequest(body: Schema.GoogleCloudRetailV2ImportProductsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object `import` {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, branchesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/branches/${branchesId}/products:import")).addQueryStringParameters("parent" -> parent.toString))
						}
						class addFulfillmentPlaces(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudRetailV2AddFulfillmentPlacesRequest(body: Schema.GoogleCloudRetailV2AddFulfillmentPlacesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object addFulfillmentPlaces {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, branchesId :PlayApi, productsId :PlayApi, product: String)(using auth: AuthToken, ec: ExecutionContext): addFulfillmentPlaces = new addFulfillmentPlaces(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/branches/${branchesId}/products/${productsId}:addFulfillmentPlaces")).addQueryStringParameters("product" -> product.toString))
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, branchesId :PlayApi, productsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/branches/${branchesId}/products/${productsId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						class addLocalInventories(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudRetailV2AddLocalInventoriesRequest(body: Schema.GoogleCloudRetailV2AddLocalInventoriesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object addLocalInventories {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, branchesId :PlayApi, productsId :PlayApi, product: String)(using auth: AuthToken, ec: ExecutionContext): addLocalInventories = new addLocalInventories(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/branches/${branchesId}/products/${productsId}:addLocalInventories")).addQueryStringParameters("product" -> product.toString))
						}
						class setInventory(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudRetailV2SetInventoryRequest(body: Schema.GoogleCloudRetailV2SetInventoryRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object setInventory {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, branchesId :PlayApi, productsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): setInventory = new setInventory(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/branches/${branchesId}/products/${productsId}:setInventory")).addQueryStringParameters("name" -> name.toString))
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRetailV2Product]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRetailV2Product])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, branchesId :PlayApi, productsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/branches/${branchesId}/products/${productsId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudRetailV2Product]] = (fun: get) => fun.apply()
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudRetailV2Product(body: Schema.GoogleCloudRetailV2Product) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudRetailV2Product])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, branchesId :PlayApi, productsId :PlayApi, name: String, updateMask: String, allowMissing: Boolean)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/branches/${branchesId}/products/${productsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "allowMissing" -> allowMissing.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRetailV2ListProductsResponse]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRetailV2ListProductsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, branchesId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String, readMask: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/branches/${branchesId}/products")).addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "readMask" -> readMask.toString))
							given Conversion[list, Future[Schema.GoogleCloudRetailV2ListProductsResponse]] = (fun: list) => fun.apply()
						}
						class removeFulfillmentPlaces(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudRetailV2RemoveFulfillmentPlacesRequest(body: Schema.GoogleCloudRetailV2RemoveFulfillmentPlacesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object removeFulfillmentPlaces {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, branchesId :PlayApi, productsId :PlayApi, product: String)(using auth: AuthToken, ec: ExecutionContext): removeFulfillmentPlaces = new removeFulfillmentPlaces(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/branches/${branchesId}/products/${productsId}:removeFulfillmentPlaces")).addQueryStringParameters("product" -> product.toString))
						}
						class removeLocalInventories(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudRetailV2RemoveLocalInventoriesRequest(body: Schema.GoogleCloudRetailV2RemoveLocalInventoriesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object removeLocalInventories {
							def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, branchesId :PlayApi, productsId :PlayApi, product: String)(using auth: AuthToken, ec: ExecutionContext): removeLocalInventories = new removeLocalInventories(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/branches/${branchesId}/products/${productsId}:removeLocalInventories")).addQueryStringParameters("product" -> product.toString))
						}
					}
				}
				object generativeQuestions {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRetailV2ListGenerativeQuestionConfigsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRetailV2ListGenerativeQuestionConfigsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/generativeQuestions")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudRetailV2ListGenerativeQuestionConfigsResponse]] = (fun: list) => fun.apply()
					}
				}
				object completionData {
					class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRetailV2ImportCompletionDataRequest(body: Schema.GoogleCloudRetailV2ImportCompletionDataRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/completionData:import")).addQueryStringParameters("parent" -> parent.toString))
					}
				}
				object controls {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRetailV2Control(body: Schema.GoogleCloudRetailV2Control) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudRetailV2Control])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String, controlId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/controls")).addQueryStringParameters("parent" -> parent.toString, "controlId" -> controlId.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, controlsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/controls/${controlsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRetailV2Control]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRetailV2Control])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, controlsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/controls/${controlsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudRetailV2Control]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudRetailV2Control(body: Schema.GoogleCloudRetailV2Control) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudRetailV2Control])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, controlsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/controls/${controlsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudRetailV2ListControlsResponse]) {
						/** Optional. Maximum number of results to return. If unspecified, defaults to 50. Max allowed value is 1000.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A page token, received from a previous `ListControls` call. Provide this to retrieve the subsequent page. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. A filter to apply on the list results. Supported features: &#42; List all the products under the parent branch if filter is unset. &#42; List controls that are used in a single ServingConfig: 'serving_config = "boosted_home_page_cvr"' */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudRetailV2ListControlsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, catalogsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/locations/${locationsId}/catalogs/${catalogsId}/controls")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudRetailV2ListControlsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
		object operations {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/operations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object get {
				def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v2/projects/${projectsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
			}
		}
	}
}
