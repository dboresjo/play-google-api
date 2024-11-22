package com.boresjo.play.api.google.contactcenterinsights

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://contactcenterinsights.googleapis.com/"

	object projects {
		object locations {
			class getEncryptionSpec(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1EncryptionSpec]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1EncryptionSpec])
			}
			object getEncryptionSpec {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getEncryptionSpec = new getEncryptionSpec(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/encryptionSpec").addQueryStringParameters("name" -> name.toString))
				given Conversion[getEncryptionSpec, Future[Schema.GoogleCloudContactcenterinsightsV1EncryptionSpec]] = (fun: getEncryptionSpec) => fun.apply()
			}
			class updateSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudContactcenterinsightsV1Settings(body: Schema.GoogleCloudContactcenterinsightsV1Settings) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1Settings])
			}
			object updateSettings {
				def apply(projectsId :PlayApi, locationsId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): updateSettings = new updateSettings(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/settings").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			class queryMetrics(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudContactcenterinsightsV1QueryMetricsRequest(body: Schema.GoogleCloudContactcenterinsightsV1QueryMetricsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object queryMetrics {
				def apply(projectsId :PlayApi, locationsId :PlayApi, location: String)(using auth: AuthToken, ec: ExecutionContext): queryMetrics = new queryMetrics(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:queryMetrics").addQueryStringParameters("location" -> location.toString))
			}
			class listAllFeedbackLabels(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1ListAllFeedbackLabelsResponse]) {
				/** Optional. The maximum number of feedback labels to return in the response. A valid page size ranges from 0 to 100,000 inclusive. If the page size is zero or unspecified, a default page size of 100 will be chosen. Note that a call might return fewer results than the requested page size.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new listAllFeedbackLabels(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. The value returned by the last `ListAllFeedbackLabelsResponse`. This value indicates that this is a continuation of a prior `ListAllFeedbackLabels` call and that the system should return the next page of data. */
				def withPageToken(pageToken: String) = new listAllFeedbackLabels(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. A filter to reduce results to a specific subset in the entire project. Supports disjunctions (OR) and conjunctions (AND). Supported fields: &#42; `issue_model_id` &#42; `qa_question_id` &#42; `min_create_time` &#42; `max_create_time` &#42; `min_update_time` &#42; `max_update_time` &#42; `feedback_label_type`: QUALITY_AI, TOPIC_MODELING */
				def withFilter(filter: String) = new listAllFeedbackLabels(req.addQueryStringParameters("filter" -> filter.toString))
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1ListAllFeedbackLabelsResponse])
			}
			object listAllFeedbackLabels {
				def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): listAllFeedbackLabels = new listAllFeedbackLabels(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:listAllFeedbackLabels").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[listAllFeedbackLabels, Future[Schema.GoogleCloudContactcenterinsightsV1ListAllFeedbackLabelsResponse]] = (fun: listAllFeedbackLabels) => fun.apply()
			}
			class bulkUploadFeedbackLabels(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudContactcenterinsightsV1BulkUploadFeedbackLabelsRequest(body: Schema.GoogleCloudContactcenterinsightsV1BulkUploadFeedbackLabelsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object bulkUploadFeedbackLabels {
				def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): bulkUploadFeedbackLabels = new bulkUploadFeedbackLabels(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:bulkUploadFeedbackLabels").addQueryStringParameters("parent" -> parent.toString))
			}
			class getSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1Settings]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1Settings])
			}
			object getSettings {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getSettings = new getSettings(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/settings").addQueryStringParameters("name" -> name.toString))
				given Conversion[getSettings, Future[Schema.GoogleCloudContactcenterinsightsV1Settings]] = (fun: getSettings) => fun.apply()
			}
			class bulkDownloadFeedbackLabels(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudContactcenterinsightsV1BulkDownloadFeedbackLabelsRequest(body: Schema.GoogleCloudContactcenterinsightsV1BulkDownloadFeedbackLabelsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object bulkDownloadFeedbackLabels {
				def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): bulkDownloadFeedbackLabels = new bulkDownloadFeedbackLabels(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:bulkDownloadFeedbackLabels").addQueryStringParameters("parent" -> parent.toString))
			}
			object operations {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
				}
				class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object cancel {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
					given Conversion[cancel, Future[Schema.GoogleProtobufEmpty]] = (fun: cancel) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
				}
			}
			object phraseMatchers {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContactcenterinsightsV1PhraseMatcher(body: Schema.GoogleCloudContactcenterinsightsV1PhraseMatcher) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1PhraseMatcher])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/phraseMatchers").addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, phraseMatchersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/phraseMatchers/${phraseMatchersId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1PhraseMatcher]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1PhraseMatcher])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, phraseMatchersId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/phraseMatchers/${phraseMatchersId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudContactcenterinsightsV1PhraseMatcher]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContactcenterinsightsV1PhraseMatcher(body: Schema.GoogleCloudContactcenterinsightsV1PhraseMatcher) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1PhraseMatcher])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, phraseMatchersId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/phraseMatchers/${phraseMatchersId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1ListPhraseMatchersResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1ListPhraseMatchersResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, filter: String, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/phraseMatchers").addQueryStringParameters("filter" -> filter.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleCloudContactcenterinsightsV1ListPhraseMatchersResponse]] = (fun: list) => fun.apply()
				}
			}
			object qaScorecards {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. A unique ID for the new QaScorecard. This ID will become the final component of the QaScorecard's resource name. If no ID is specified, a server-generated ID will be used. This value should be 4-64 characters and must match the regular expression `^[a-z0-9-]{4,64}$`. Valid characters are `a-z-`. */
					def withQaScorecardId(qaScorecardId: String) = new create(req.addQueryStringParameters("qaScorecardId" -> qaScorecardId.toString))
					def withGoogleCloudContactcenterinsightsV1QaScorecard(body: Schema.GoogleCloudContactcenterinsightsV1QaScorecard) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1QaScorecard])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/qaScorecards").addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					/** Optional. If set to true, all of this QaScorecard's child resources will also be deleted. Otherwise, the request will only succeed if it has none. */
					def withForce(force: Boolean) = new delete(req.addQueryStringParameters("force" -> force.toString))
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, qaScorecardsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/qaScorecards/${qaScorecardsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1QaScorecard]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1QaScorecard])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, qaScorecardsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/qaScorecards/${qaScorecardsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudContactcenterinsightsV1QaScorecard]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContactcenterinsightsV1QaScorecard(body: Schema.GoogleCloudContactcenterinsightsV1QaScorecard) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1QaScorecard])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, qaScorecardsId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/qaScorecards/${qaScorecardsId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1ListQaScorecardsResponse]) {
					/** Optional. The value returned by the last `ListQaScorecardsResponse`. This value indicates that this is a continuation of a prior `ListQaScorecards` call and that the system should return the next page of data. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of scorecards to return in the response. If the value is zero, the service will select a default size. A call might return fewer objects than requested. A non-empty `next_page_token` in the response indicates that more data is available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1ListQaScorecardsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/qaScorecards").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudContactcenterinsightsV1ListQaScorecardsResponse]] = (fun: list) => fun.apply()
				}
				object revisions {
					class undeploy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudContactcenterinsightsV1UndeployQaScorecardRevisionRequest(body: Schema.GoogleCloudContactcenterinsightsV1UndeployQaScorecardRevisionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1QaScorecardRevision])
					}
					object undeploy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, qaScorecardsId :PlayApi, revisionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): undeploy = new undeploy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/qaScorecards/${qaScorecardsId}/revisions/${revisionsId}:undeploy").addQueryStringParameters("name" -> name.toString))
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						/** Optional. A unique ID for the new QaScorecardRevision. This ID will become the final component of the QaScorecardRevision's resource name. If no ID is specified, a server-generated ID will be used. This value should be 4-64 characters and must match the regular expression `^[a-z0-9-]{4,64}$`. Valid characters are `a-z-`. */
						def withQaScorecardRevisionId(qaScorecardRevisionId: String) = new create(req.addQueryStringParameters("qaScorecardRevisionId" -> qaScorecardRevisionId.toString))
						def withGoogleCloudContactcenterinsightsV1QaScorecardRevision(body: Schema.GoogleCloudContactcenterinsightsV1QaScorecardRevision) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1QaScorecardRevision])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, qaScorecardsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/qaScorecards/${qaScorecardsId}/revisions").addQueryStringParameters("parent" -> parent.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						/** Optional. If set to true, all of this QaScorecardRevision's child resources will also be deleted. Otherwise, the request will only succeed if it has none. */
						def withForce(force: Boolean) = new delete(req.addQueryStringParameters("force" -> force.toString))
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, qaScorecardsId :PlayApi, revisionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/qaScorecards/${qaScorecardsId}/revisions/${revisionsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					class deploy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudContactcenterinsightsV1DeployQaScorecardRevisionRequest(body: Schema.GoogleCloudContactcenterinsightsV1DeployQaScorecardRevisionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1QaScorecardRevision])
					}
					object deploy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, qaScorecardsId :PlayApi, revisionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): deploy = new deploy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/qaScorecards/${qaScorecardsId}/revisions/${revisionsId}:deploy").addQueryStringParameters("name" -> name.toString))
					}
					class tuneQaScorecardRevision(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudContactcenterinsightsV1TuneQaScorecardRevisionRequest(body: Schema.GoogleCloudContactcenterinsightsV1TuneQaScorecardRevisionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object tuneQaScorecardRevision {
						def apply(projectsId :PlayApi, locationsId :PlayApi, qaScorecardsId :PlayApi, revisionsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): tuneQaScorecardRevision = new tuneQaScorecardRevision(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/qaScorecards/${qaScorecardsId}/revisions/${revisionsId}:tuneQaScorecardRevision").addQueryStringParameters("parent" -> parent.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1ListQaScorecardRevisionsResponse]) {
						/** Optional. The value returned by the last `ListQaScorecardRevisionsResponse`. This value indicates that this is a continuation of a prior `ListQaScorecardRevisions` call and that the system should return the next page of data. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The maximum number of scorecard revisions to return in the response. If the value is zero, the service will select a default size. A call might return fewer objects than requested. A non-empty `next_page_token` in the response indicates that more data is available.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A filter to reduce results to a specific subset. Useful for querying scorecard revisions with specific properties. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1ListQaScorecardRevisionsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, qaScorecardsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/qaScorecards/${qaScorecardsId}/revisions").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudContactcenterinsightsV1ListQaScorecardRevisionsResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1QaScorecardRevision]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1QaScorecardRevision])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, qaScorecardsId :PlayApi, revisionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/qaScorecards/${qaScorecardsId}/revisions/${revisionsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudContactcenterinsightsV1QaScorecardRevision]] = (fun: get) => fun.apply()
					}
					object qaQuestions {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							/** Optional. A unique ID for the new question. This ID will become the final component of the question's resource name. If no ID is specified, a server-generated ID will be used. This value should be 4-64 characters and must match the regular expression `^[a-z0-9-]{4,64}$`. Valid characters are `a-z-`. */
							def withQaQuestionId(qaQuestionId: String) = new create(req.addQueryStringParameters("qaQuestionId" -> qaQuestionId.toString))
							def withGoogleCloudContactcenterinsightsV1QaQuestion(body: Schema.GoogleCloudContactcenterinsightsV1QaQuestion) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1QaQuestion])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, qaScorecardsId :PlayApi, revisionsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/qaScorecards/${qaScorecardsId}/revisions/${revisionsId}/qaQuestions").addQueryStringParameters("parent" -> parent.toString))
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, qaScorecardsId :PlayApi, revisionsId :PlayApi, qaQuestionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/qaScorecards/${qaScorecardsId}/revisions/${revisionsId}/qaQuestions/${qaQuestionsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1QaQuestion]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1QaQuestion])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, qaScorecardsId :PlayApi, revisionsId :PlayApi, qaQuestionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/qaScorecards/${qaScorecardsId}/revisions/${revisionsId}/qaQuestions/${qaQuestionsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudContactcenterinsightsV1QaQuestion]] = (fun: get) => fun.apply()
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudContactcenterinsightsV1QaQuestion(body: Schema.GoogleCloudContactcenterinsightsV1QaQuestion) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1QaQuestion])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, qaScorecardsId :PlayApi, revisionsId :PlayApi, qaQuestionsId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/qaScorecards/${qaScorecardsId}/revisions/${revisionsId}/qaQuestions/${qaQuestionsId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1ListQaQuestionsResponse]) {
							/** Optional. The maximum number of questions to return in the response. If the value is zero, the service will select a default size. A call might return fewer objects than requested. A non-empty `next_page_token` in the response indicates that more data is available.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. The value returned by the last `ListQaQuestionsResponse`. This value indicates that this is a continuation of a prior `ListQaQuestions` call and that the system should return the next page of data. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1ListQaQuestionsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, qaScorecardsId :PlayApi, revisionsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/qaScorecards/${qaScorecardsId}/revisions/${revisionsId}/qaQuestions").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.GoogleCloudContactcenterinsightsV1ListQaQuestionsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
			object encryptionSpec {
				class initialize(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContactcenterinsightsV1InitializeEncryptionSpecRequest(body: Schema.GoogleCloudContactcenterinsightsV1InitializeEncryptionSpecRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object initialize {
					def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): initialize = new initialize(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/encryptionSpec:initialize").addQueryStringParameters("name" -> name.toString))
				}
			}
			object issueModels {
				class undeploy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContactcenterinsightsV1UndeployIssueModelRequest(body: Schema.GoogleCloudContactcenterinsightsV1UndeployIssueModelRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object undeploy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, issueModelsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): undeploy = new undeploy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/issueModels/${issueModelsId}:undeploy").addQueryStringParameters("name" -> name.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContactcenterinsightsV1IssueModel(body: Schema.GoogleCloudContactcenterinsightsV1IssueModel) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/issueModels").addQueryStringParameters("parent" -> parent.toString))
				}
				class `export`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContactcenterinsightsV1ExportIssueModelRequest(body: Schema.GoogleCloudContactcenterinsightsV1ExportIssueModelRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object `export` {
					def apply(projectsId :PlayApi, locationsId :PlayApi, issueModelsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): `export` = new `export`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/issueModels/${issueModelsId}:export").addQueryStringParameters("name" -> name.toString))
				}
				class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContactcenterinsightsV1ImportIssueModelRequest(body: Schema.GoogleCloudContactcenterinsightsV1ImportIssueModelRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object `import` {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/issueModels:import").addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, issueModelsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/issueModels/${issueModelsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
				}
				class deploy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContactcenterinsightsV1DeployIssueModelRequest(body: Schema.GoogleCloudContactcenterinsightsV1DeployIssueModelRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object deploy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, issueModelsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): deploy = new deploy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/issueModels/${issueModelsId}:deploy").addQueryStringParameters("name" -> name.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1IssueModel]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1IssueModel])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, issueModelsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/issueModels/${issueModelsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudContactcenterinsightsV1IssueModel]] = (fun: get) => fun.apply()
				}
				class calculateIssueModelStats(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1CalculateIssueModelStatsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1CalculateIssueModelStatsResponse])
				}
				object calculateIssueModelStats {
					def apply(projectsId :PlayApi, locationsId :PlayApi, issueModelsId :PlayApi, issueModel: String)(using auth: AuthToken, ec: ExecutionContext): calculateIssueModelStats = new calculateIssueModelStats(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/issueModels/${issueModelsId}:calculateIssueModelStats").addQueryStringParameters("issueModel" -> issueModel.toString))
					given Conversion[calculateIssueModelStats, Future[Schema.GoogleCloudContactcenterinsightsV1CalculateIssueModelStatsResponse]] = (fun: calculateIssueModelStats) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContactcenterinsightsV1IssueModel(body: Schema.GoogleCloudContactcenterinsightsV1IssueModel) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1IssueModel])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, issueModelsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/issueModels/${issueModelsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1ListIssueModelsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1ListIssueModelsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/issueModels").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudContactcenterinsightsV1ListIssueModelsResponse]] = (fun: list) => fun.apply()
				}
				object issues {
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1Issue]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1Issue])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, issueModelsId :PlayApi, issuesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/issueModels/${issueModelsId}/issues/${issuesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudContactcenterinsightsV1Issue]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudContactcenterinsightsV1Issue(body: Schema.GoogleCloudContactcenterinsightsV1Issue) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1Issue])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, issueModelsId :PlayApi, issuesId :PlayApi, updateMask: String, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/issueModels/${issueModelsId}/issues/${issuesId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, issueModelsId :PlayApi, issuesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/issueModels/${issueModelsId}/issues/${issuesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1ListIssuesResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1ListIssuesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, issueModelsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/issueModels/${issueModelsId}/issues").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudContactcenterinsightsV1ListIssuesResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object views {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContactcenterinsightsV1View(body: Schema.GoogleCloudContactcenterinsightsV1View) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1View])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/views").addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, viewsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1View]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1View])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, viewsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudContactcenterinsightsV1View]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContactcenterinsightsV1View(body: Schema.GoogleCloudContactcenterinsightsV1View) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1View])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, viewsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1ListViewsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1ListViewsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, pageSize: Int, pageToken: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/views").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudContactcenterinsightsV1ListViewsResponse]] = (fun: list) => fun.apply()
				}
			}
			object analysisRules {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContactcenterinsightsV1AnalysisRule(body: Schema.GoogleCloudContactcenterinsightsV1AnalysisRule) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1AnalysisRule])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/analysisRules").addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, analysisRulesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/analysisRules/${analysisRulesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1AnalysisRule]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1AnalysisRule])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, analysisRulesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/analysisRules/${analysisRulesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudContactcenterinsightsV1AnalysisRule]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. The list of fields to be updated. If the update_mask is not provided, the update will be applied to all fields.<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					def withGoogleCloudContactcenterinsightsV1AnalysisRule(body: Schema.GoogleCloudContactcenterinsightsV1AnalysisRule) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1AnalysisRule])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, analysisRulesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/analysisRules/${analysisRulesId}").addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1ListAnalysisRulesResponse]) {
					/** Optional. The maximum number of analysis rule to return in the response. If this value is zero, the service will select a default size. A call may return fewer objects than requested. A non-empty `next_page_token` in the response indicates that more data is available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. The value returned by the last `ListAnalysisRulesResponse`; indicates that this is a continuation of a prior `ListAnalysisRules` call and the system should return the next page of data. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1ListAnalysisRulesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/analysisRules").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudContactcenterinsightsV1ListAnalysisRulesResponse]] = (fun: list) => fun.apply()
				}
			}
			object conversations {
				class calculateStats(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1CalculateStatsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1CalculateStatsResponse])
				}
				object calculateStats {
					def apply(projectsId :PlayApi, locationsId :PlayApi, filter: String, location: String)(using auth: AuthToken, ec: ExecutionContext): calculateStats = new calculateStats(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations:calculateStats").addQueryStringParameters("filter" -> filter.toString, "location" -> location.toString))
					given Conversion[calculateStats, Future[Schema.GoogleCloudContactcenterinsightsV1CalculateStatsResponse]] = (fun: calculateStats) => fun.apply()
				}
				class bulkDelete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContactcenterinsightsV1BulkDeleteConversationsRequest(body: Schema.GoogleCloudContactcenterinsightsV1BulkDeleteConversationsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object bulkDelete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): bulkDelete = new bulkDelete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations:bulkDelete").addQueryStringParameters("parent" -> parent.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContactcenterinsightsV1Conversation(body: Schema.GoogleCloudContactcenterinsightsV1Conversation) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1Conversation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, conversationId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations").addQueryStringParameters("parent" -> parent.toString, "conversationId" -> conversationId.toString))
				}
				class ingest(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContactcenterinsightsV1IngestConversationsRequest(body: Schema.GoogleCloudContactcenterinsightsV1IngestConversationsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object ingest {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): ingest = new ingest(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations:ingest").addQueryStringParameters("parent" -> parent.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1Conversation]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1Conversation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, conversationsId :PlayApi, name: String, view: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations/${conversationsId}").addQueryStringParameters("name" -> name.toString, "view" -> view.toString))
					given Conversion[get, Future[Schema.GoogleCloudContactcenterinsightsV1Conversation]] = (fun: get) => fun.apply()
				}
				class bulkAnalyze(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContactcenterinsightsV1BulkAnalyzeConversationsRequest(body: Schema.GoogleCloudContactcenterinsightsV1BulkAnalyzeConversationsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object bulkAnalyze {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): bulkAnalyze = new bulkAnalyze(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations:bulkAnalyze").addQueryStringParameters("parent" -> parent.toString))
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContactcenterinsightsV1Conversation(body: Schema.GoogleCloudContactcenterinsightsV1Conversation) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1Conversation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, conversationsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations/${conversationsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1ListConversationsResponse]) {
					/** Optional. The attribute by which to order conversations in the response. If empty, conversations will be ordered by descending creation time. Supported values are one of the following: &#42; create_time &#42; customer_satisfaction_rating &#42; duration &#42; latest_analysis &#42; start_time &#42; turn_count The default sort order is ascending. To specify order, append `asc` or `desc` (`create_time desc`). For more details, see [Google AIPs Ordering](https://google.aip.dev/132#ordering). */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1ListConversationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, filter: String, pageToken: String, pageSize: Int, view: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "view" -> view.toString))
					given Conversion[list, Future[Schema.GoogleCloudContactcenterinsightsV1ListConversationsResponse]] = (fun: list) => fun.apply()
				}
				class upload(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContactcenterinsightsV1UploadConversationRequest(body: Schema.GoogleCloudContactcenterinsightsV1UploadConversationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object upload {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations:upload").addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, conversationsId :PlayApi, force: Boolean, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations/${conversationsId}").addQueryStringParameters("force" -> force.toString, "name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				object feedbackLabels {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						/** Optional. The ID of the feedback label to create. If one is not specified it will be generated by the server. */
						def withFeedbackLabelId(feedbackLabelId: String) = new create(req.addQueryStringParameters("feedbackLabelId" -> feedbackLabelId.toString))
						def withGoogleCloudContactcenterinsightsV1FeedbackLabel(body: Schema.GoogleCloudContactcenterinsightsV1FeedbackLabel) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1FeedbackLabel])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, conversationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations/${conversationsId}/feedbackLabels").addQueryStringParameters("parent" -> parent.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, conversationsId :PlayApi, feedbackLabelsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations/${conversationsId}/feedbackLabels/${feedbackLabelsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1FeedbackLabel]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1FeedbackLabel])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, conversationsId :PlayApi, feedbackLabelsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations/${conversationsId}/feedbackLabels/${feedbackLabelsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudContactcenterinsightsV1FeedbackLabel]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudContactcenterinsightsV1FeedbackLabel(body: Schema.GoogleCloudContactcenterinsightsV1FeedbackLabel) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1FeedbackLabel])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, conversationsId :PlayApi, feedbackLabelsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations/${conversationsId}/feedbackLabels/${feedbackLabelsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1ListFeedbackLabelsResponse]) {
						/** Optional. The value returned by the last `ListFeedbackLabelsResponse`. This value indicates that this is a continuation of a prior `ListFeedbackLabels` call and that the system should return the next page of data. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The maximum number of feedback labels to return in the response. A valid page size ranges from 0 to 100,000 inclusive. If the page size is zero or unspecified, a default page size of 100 will be chosen. Note that a call might return fewer results than the requested page size.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A filter to reduce results to a specific subset. Supports disjunctions (OR) and conjunctions (AND). Automatically sorts by conversation ID. To sort by all feedback labels in a project see ListAllFeedbackLabels. Supported fields: &#42; `issue_model_id` &#42; `qa_question_id` &#42; `qa_scorecard_id` &#42; `min_create_time` &#42; `max_create_time` &#42; `min_update_time` &#42; `max_update_time` &#42; `feedback_label_type`: QUALITY_AI, TOPIC_MODELING */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1ListFeedbackLabelsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, conversationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations/${conversationsId}/feedbackLabels").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudContactcenterinsightsV1ListFeedbackLabelsResponse]] = (fun: list) => fun.apply()
					}
				}
				object analyses {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudContactcenterinsightsV1Analysis(body: Schema.GoogleCloudContactcenterinsightsV1Analysis) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, conversationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations/${conversationsId}/analyses").addQueryStringParameters("parent" -> parent.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1ListAnalysesResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1ListAnalysesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, conversationsId :PlayApi, filter: String, pageSize: Int, pageToken: String, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations/${conversationsId}/analyses").addQueryStringParameters("filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudContactcenterinsightsV1ListAnalysesResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1Analysis]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1Analysis])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, conversationsId :PlayApi, analysesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations/${conversationsId}/analyses/${analysesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudContactcenterinsightsV1Analysis]] = (fun: get) => fun.apply()
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, conversationsId :PlayApi, analysesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations/${conversationsId}/analyses/${analysesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
				}
			}
			object insightsdata {
				class `export`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudContactcenterinsightsV1ExportInsightsDataRequest(body: Schema.GoogleCloudContactcenterinsightsV1ExportInsightsDataRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object `export` {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `export` = new `export`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/insightsdata:export").addQueryStringParameters("parent" -> parent.toString))
				}
			}
		}
	}
}
