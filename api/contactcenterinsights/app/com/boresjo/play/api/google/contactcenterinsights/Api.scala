package com.boresjo.play.api.google.contactcenterinsights

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

	private val BASE_URL = "https://contactcenterinsights.googleapis.com/"

	object projects {
		object locations {
			/** Gets location-level encryption key specification. */
			class getEncryptionSpec(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1EncryptionSpec]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1EncryptionSpec])
			}
			object getEncryptionSpec {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getEncryptionSpec = new getEncryptionSpec(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/encryptionSpec").addQueryStringParameters("name" -> name.toString))
				given Conversion[getEncryptionSpec, Future[Schema.GoogleCloudContactcenterinsightsV1EncryptionSpec]] = (fun: getEncryptionSpec) => fun.apply()
			}
			/** Updates project-level settings. */
			class updateSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudContactcenterinsightsV1Settings(body: Schema.GoogleCloudContactcenterinsightsV1Settings) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1Settings])
			}
			object updateSettings {
				def apply(projectsId :PlayApi, locationsId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): updateSettings = new updateSettings(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/settings").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
			}
			/** Query metrics. */
			class queryMetrics(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudContactcenterinsightsV1QueryMetricsRequest(body: Schema.GoogleCloudContactcenterinsightsV1QueryMetricsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object queryMetrics {
				def apply(projectsId :PlayApi, locationsId :PlayApi, location: String)(using signer: RequestSigner, ec: ExecutionContext): queryMetrics = new queryMetrics(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:queryMetrics").addQueryStringParameters("location" -> location.toString))
			}
			/** List all feedback labels by project number. */
			class listAllFeedbackLabels(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1ListAllFeedbackLabelsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. The maximum number of feedback labels to return in the response. A valid page size ranges from 0 to 100,000 inclusive. If the page size is zero or unspecified, a default page size of 100 will be chosen. Note that a call might return fewer results than the requested page size.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new listAllFeedbackLabels(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Optional. The value returned by the last `ListAllFeedbackLabelsResponse`. This value indicates that this is a continuation of a prior `ListAllFeedbackLabels` call and that the system should return the next page of data. */
				def withPageToken(pageToken: String) = new listAllFeedbackLabels(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. A filter to reduce results to a specific subset in the entire project. Supports disjunctions (OR) and conjunctions (AND). Supported fields: &#42; `issue_model_id` &#42; `qa_question_id` &#42; `min_create_time` &#42; `max_create_time` &#42; `min_update_time` &#42; `max_update_time` &#42; `feedback_label_type`: QUALITY_AI, TOPIC_MODELING */
				def withFilter(filter: String) = new listAllFeedbackLabels(req.addQueryStringParameters("filter" -> filter.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1ListAllFeedbackLabelsResponse])
			}
			object listAllFeedbackLabels {
				def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): listAllFeedbackLabels = new listAllFeedbackLabels(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:listAllFeedbackLabels").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[listAllFeedbackLabels, Future[Schema.GoogleCloudContactcenterinsightsV1ListAllFeedbackLabelsResponse]] = (fun: listAllFeedbackLabels) => fun.apply()
			}
			/** Upload feedback labels in bulk. */
			class bulkUploadFeedbackLabels(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudContactcenterinsightsV1BulkUploadFeedbackLabelsRequest(body: Schema.GoogleCloudContactcenterinsightsV1BulkUploadFeedbackLabelsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object bulkUploadFeedbackLabels {
				def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): bulkUploadFeedbackLabels = new bulkUploadFeedbackLabels(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:bulkUploadFeedbackLabels").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Gets project-level settings. */
			class getSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1Settings]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1Settings])
			}
			object getSettings {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getSettings = new getSettings(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/settings").addQueryStringParameters("name" -> name.toString))
				given Conversion[getSettings, Future[Schema.GoogleCloudContactcenterinsightsV1Settings]] = (fun: getSettings) => fun.apply()
			}
			/** Download feedback labels in bulk. */
			class bulkDownloadFeedbackLabels(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudContactcenterinsightsV1BulkDownloadFeedbackLabelsRequest(body: Schema.GoogleCloudContactcenterinsightsV1BulkDownloadFeedbackLabelsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object bulkDownloadFeedbackLabels {
				def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): bulkDownloadFeedbackLabels = new bulkDownloadFeedbackLabels(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:bulkDownloadFeedbackLabels").addQueryStringParameters("parent" -> parent.toString))
			}
			object operations {
				/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
				}
				/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
				class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object cancel {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
					given Conversion[cancel, Future[Schema.GoogleProtobufEmpty]] = (fun: cancel) => fun.apply()
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
			object phraseMatchers {
				/** Creates a phrase matcher. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContactcenterinsightsV1PhraseMatcher(body: Schema.GoogleCloudContactcenterinsightsV1PhraseMatcher) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1PhraseMatcher])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/phraseMatchers").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes a phrase matcher. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, phraseMatchersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/phraseMatchers/${phraseMatchersId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Gets a phrase matcher. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1PhraseMatcher]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1PhraseMatcher])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, phraseMatchersId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/phraseMatchers/${phraseMatchersId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudContactcenterinsightsV1PhraseMatcher]] = (fun: get) => fun.apply()
				}
				/** Updates a phrase matcher. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContactcenterinsightsV1PhraseMatcher(body: Schema.GoogleCloudContactcenterinsightsV1PhraseMatcher) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1PhraseMatcher])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, phraseMatchersId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/phraseMatchers/${phraseMatchersId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
				}
				/** Lists phrase matchers. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1ListPhraseMatchersResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1ListPhraseMatchersResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, filter: String, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/phraseMatchers").addQueryStringParameters("filter" -> filter.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleCloudContactcenterinsightsV1ListPhraseMatchersResponse]] = (fun: list) => fun.apply()
				}
			}
			object qaScorecards {
				/** Create a QaScorecard. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. A unique ID for the new QaScorecard. This ID will become the final component of the QaScorecard's resource name. If no ID is specified, a server-generated ID will be used. This value should be 4-64 characters and must match the regular expression `^[a-z0-9-]{4,64}$`. Valid characters are `a-z-`. */
					def withQaScorecardId(qaScorecardId: String) = new create(req.addQueryStringParameters("qaScorecardId" -> qaScorecardId.toString))
					/** Perform the request */
					def withGoogleCloudContactcenterinsightsV1QaScorecard(body: Schema.GoogleCloudContactcenterinsightsV1QaScorecard) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1QaScorecard])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/qaScorecards").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes a QaScorecard. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. If set to true, all of this QaScorecard's child resources will also be deleted. Otherwise, the request will only succeed if it has none. */
					def withForce(force: Boolean) = new delete(req.addQueryStringParameters("force" -> force.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, qaScorecardsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/qaScorecards/${qaScorecardsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Gets a QaScorecard. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1QaScorecard]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1QaScorecard])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, qaScorecardsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/qaScorecards/${qaScorecardsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudContactcenterinsightsV1QaScorecard]] = (fun: get) => fun.apply()
				}
				/** Updates a QaScorecard. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContactcenterinsightsV1QaScorecard(body: Schema.GoogleCloudContactcenterinsightsV1QaScorecard) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1QaScorecard])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, qaScorecardsId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/qaScorecards/${qaScorecardsId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
				}
				/** Lists QaScorecards. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1ListQaScorecardsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The value returned by the last `ListQaScorecardsResponse`. This value indicates that this is a continuation of a prior `ListQaScorecards` call and that the system should return the next page of data. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The maximum number of scorecards to return in the response. If the value is zero, the service will select a default size. A call might return fewer objects than requested. A non-empty `next_page_token` in the response indicates that more data is available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1ListQaScorecardsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/qaScorecards").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudContactcenterinsightsV1ListQaScorecardsResponse]] = (fun: list) => fun.apply()
				}
				object revisions {
					/** Undeploy a QaScorecardRevision. */
					class undeploy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudContactcenterinsightsV1UndeployQaScorecardRevisionRequest(body: Schema.GoogleCloudContactcenterinsightsV1UndeployQaScorecardRevisionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1QaScorecardRevision])
					}
					object undeploy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, qaScorecardsId :PlayApi, revisionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): undeploy = new undeploy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/qaScorecards/${qaScorecardsId}/revisions/${revisionsId}:undeploy").addQueryStringParameters("name" -> name.toString))
					}
					/** Creates a QaScorecardRevision. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. A unique ID for the new QaScorecardRevision. This ID will become the final component of the QaScorecardRevision's resource name. If no ID is specified, a server-generated ID will be used. This value should be 4-64 characters and must match the regular expression `^[a-z0-9-]{4,64}$`. Valid characters are `a-z-`. */
						def withQaScorecardRevisionId(qaScorecardRevisionId: String) = new create(req.addQueryStringParameters("qaScorecardRevisionId" -> qaScorecardRevisionId.toString))
						/** Perform the request */
						def withGoogleCloudContactcenterinsightsV1QaScorecardRevision(body: Schema.GoogleCloudContactcenterinsightsV1QaScorecardRevision) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1QaScorecardRevision])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, qaScorecardsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/qaScorecards/${qaScorecardsId}/revisions").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Deletes a QaScorecardRevision. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. If set to true, all of this QaScorecardRevision's child resources will also be deleted. Otherwise, the request will only succeed if it has none. */
						def withForce(force: Boolean) = new delete(req.addQueryStringParameters("force" -> force.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, qaScorecardsId :PlayApi, revisionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/qaScorecards/${qaScorecardsId}/revisions/${revisionsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					/** Deploy a QaScorecardRevision. */
					class deploy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudContactcenterinsightsV1DeployQaScorecardRevisionRequest(body: Schema.GoogleCloudContactcenterinsightsV1DeployQaScorecardRevisionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1QaScorecardRevision])
					}
					object deploy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, qaScorecardsId :PlayApi, revisionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): deploy = new deploy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/qaScorecards/${qaScorecardsId}/revisions/${revisionsId}:deploy").addQueryStringParameters("name" -> name.toString))
					}
					/** Fine tune one or more QaModels. */
					class tuneQaScorecardRevision(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudContactcenterinsightsV1TuneQaScorecardRevisionRequest(body: Schema.GoogleCloudContactcenterinsightsV1TuneQaScorecardRevisionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object tuneQaScorecardRevision {
						def apply(projectsId :PlayApi, locationsId :PlayApi, qaScorecardsId :PlayApi, revisionsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): tuneQaScorecardRevision = new tuneQaScorecardRevision(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/qaScorecards/${qaScorecardsId}/revisions/${revisionsId}:tuneQaScorecardRevision").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Lists all revisions under the parent QaScorecard. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1ListQaScorecardRevisionsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. The value returned by the last `ListQaScorecardRevisionsResponse`. This value indicates that this is a continuation of a prior `ListQaScorecardRevisions` call and that the system should return the next page of data. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The maximum number of scorecard revisions to return in the response. If the value is zero, the service will select a default size. A call might return fewer objects than requested. A non-empty `next_page_token` in the response indicates that more data is available.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A filter to reduce results to a specific subset. Useful for querying scorecard revisions with specific properties. */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1ListQaScorecardRevisionsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, qaScorecardsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/qaScorecards/${qaScorecardsId}/revisions").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudContactcenterinsightsV1ListQaScorecardRevisionsResponse]] = (fun: list) => fun.apply()
					}
					/** Gets a QaScorecardRevision. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1QaScorecardRevision]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1QaScorecardRevision])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, qaScorecardsId :PlayApi, revisionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/qaScorecards/${qaScorecardsId}/revisions/${revisionsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudContactcenterinsightsV1QaScorecardRevision]] = (fun: get) => fun.apply()
					}
					object qaQuestions {
						/** Create a QaQuestion. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. A unique ID for the new question. This ID will become the final component of the question's resource name. If no ID is specified, a server-generated ID will be used. This value should be 4-64 characters and must match the regular expression `^[a-z0-9-]{4,64}$`. Valid characters are `a-z-`. */
							def withQaQuestionId(qaQuestionId: String) = new create(req.addQueryStringParameters("qaQuestionId" -> qaQuestionId.toString))
							/** Perform the request */
							def withGoogleCloudContactcenterinsightsV1QaQuestion(body: Schema.GoogleCloudContactcenterinsightsV1QaQuestion) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1QaQuestion])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, qaScorecardsId :PlayApi, revisionsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/qaScorecards/${qaScorecardsId}/revisions/${revisionsId}/qaQuestions").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Deletes a QaQuestion. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, qaScorecardsId :PlayApi, revisionsId :PlayApi, qaQuestionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/qaScorecards/${qaScorecardsId}/revisions/${revisionsId}/qaQuestions/${qaQuestionsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						/** Gets a QaQuestion. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1QaQuestion]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1QaQuestion])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, qaScorecardsId :PlayApi, revisionsId :PlayApi, qaQuestionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/qaScorecards/${qaScorecardsId}/revisions/${revisionsId}/qaQuestions/${qaQuestionsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudContactcenterinsightsV1QaQuestion]] = (fun: get) => fun.apply()
						}
						/** Updates a QaQuestion. */
						class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def withGoogleCloudContactcenterinsightsV1QaQuestion(body: Schema.GoogleCloudContactcenterinsightsV1QaQuestion) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1QaQuestion])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, qaScorecardsId :PlayApi, revisionsId :PlayApi, qaQuestionsId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/qaScorecards/${qaScorecardsId}/revisions/${revisionsId}/qaQuestions/${qaQuestionsId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
						}
						/** Lists QaQuestions. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1ListQaQuestionsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Optional. The maximum number of questions to return in the response. If the value is zero, the service will select a default size. A call might return fewer objects than requested. A non-empty `next_page_token` in the response indicates that more data is available.<br>Format: int32 */
							def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
							/** Optional. The value returned by the last `ListQaQuestionsResponse`. This value indicates that this is a continuation of a prior `ListQaQuestions` call and that the system should return the next page of data. */
							def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1ListQaQuestionsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, qaScorecardsId :PlayApi, revisionsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/qaScorecards/${qaScorecardsId}/revisions/${revisionsId}/qaQuestions").addQueryStringParameters("parent" -> parent.toString))
							given Conversion[list, Future[Schema.GoogleCloudContactcenterinsightsV1ListQaQuestionsResponse]] = (fun: list) => fun.apply()
						}
					}
				}
			}
			object encryptionSpec {
				/** Initializes a location-level encryption key specification. An error will result if the location has resources already created before the initialization. After the encryption specification is initialized at a location, it is immutable and all newly created resources under the location will be encrypted with the existing specification. */
				class initialize(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContactcenterinsightsV1InitializeEncryptionSpecRequest(body: Schema.GoogleCloudContactcenterinsightsV1InitializeEncryptionSpecRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object initialize {
					def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): initialize = new initialize(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/encryptionSpec:initialize").addQueryStringParameters("name" -> name.toString))
				}
			}
			object issueModels {
				/** Undeploys an issue model. An issue model can not be used in analysis after it has been undeployed. */
				class undeploy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContactcenterinsightsV1UndeployIssueModelRequest(body: Schema.GoogleCloudContactcenterinsightsV1UndeployIssueModelRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object undeploy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, issueModelsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): undeploy = new undeploy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/issueModels/${issueModelsId}:undeploy").addQueryStringParameters("name" -> name.toString))
				}
				/** Creates an issue model. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContactcenterinsightsV1IssueModel(body: Schema.GoogleCloudContactcenterinsightsV1IssueModel) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/issueModels").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Exports an issue model to the provided destination. */
				class `export`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContactcenterinsightsV1ExportIssueModelRequest(body: Schema.GoogleCloudContactcenterinsightsV1ExportIssueModelRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object `export` {
					def apply(projectsId :PlayApi, locationsId :PlayApi, issueModelsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): `export` = new `export`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/issueModels/${issueModelsId}:export").addQueryStringParameters("name" -> name.toString))
				}
				/** Imports an issue model from a Cloud Storage bucket. */
				class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContactcenterinsightsV1ImportIssueModelRequest(body: Schema.GoogleCloudContactcenterinsightsV1ImportIssueModelRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object `import` {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/issueModels:import").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes an issue model. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, issueModelsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/issueModels/${issueModelsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
				}
				/** Deploys an issue model. Returns an error if a model is already deployed. An issue model can only be used in analysis after it has been deployed. */
				class deploy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContactcenterinsightsV1DeployIssueModelRequest(body: Schema.GoogleCloudContactcenterinsightsV1DeployIssueModelRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object deploy {
					def apply(projectsId :PlayApi, locationsId :PlayApi, issueModelsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): deploy = new deploy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/issueModels/${issueModelsId}:deploy").addQueryStringParameters("name" -> name.toString))
				}
				/** Gets an issue model. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1IssueModel]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1IssueModel])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, issueModelsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/issueModels/${issueModelsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudContactcenterinsightsV1IssueModel]] = (fun: get) => fun.apply()
				}
				/** Gets an issue model's statistics. */
				class calculateIssueModelStats(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1CalculateIssueModelStatsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1CalculateIssueModelStatsResponse])
				}
				object calculateIssueModelStats {
					def apply(projectsId :PlayApi, locationsId :PlayApi, issueModelsId :PlayApi, issueModel: String)(using signer: RequestSigner, ec: ExecutionContext): calculateIssueModelStats = new calculateIssueModelStats(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/issueModels/${issueModelsId}:calculateIssueModelStats").addQueryStringParameters("issueModel" -> issueModel.toString))
					given Conversion[calculateIssueModelStats, Future[Schema.GoogleCloudContactcenterinsightsV1CalculateIssueModelStatsResponse]] = (fun: calculateIssueModelStats) => fun.apply()
				}
				/** Updates an issue model. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContactcenterinsightsV1IssueModel(body: Schema.GoogleCloudContactcenterinsightsV1IssueModel) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1IssueModel])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, issueModelsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/issueModels/${issueModelsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists issue models. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1ListIssueModelsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1ListIssueModelsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/issueModels").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudContactcenterinsightsV1ListIssueModelsResponse]] = (fun: list) => fun.apply()
				}
				object issues {
					/** Gets an issue. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1Issue]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1Issue])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, issueModelsId :PlayApi, issuesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/issueModels/${issueModelsId}/issues/${issuesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudContactcenterinsightsV1Issue]] = (fun: get) => fun.apply()
					}
					/** Updates an issue. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudContactcenterinsightsV1Issue(body: Schema.GoogleCloudContactcenterinsightsV1Issue) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1Issue])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, issueModelsId :PlayApi, issuesId :PlayApi, updateMask: String, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/issueModels/${issueModelsId}/issues/${issuesId}").addQueryStringParameters("updateMask" -> updateMask.toString, "name" -> name.toString))
					}
					/** Deletes an issue. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, issueModelsId :PlayApi, issuesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/issueModels/${issueModelsId}/issues/${issuesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					/** Lists issues. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1ListIssuesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1ListIssuesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, issueModelsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/issueModels/${issueModelsId}/issues").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudContactcenterinsightsV1ListIssuesResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object views {
				/** Creates a view. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContactcenterinsightsV1View(body: Schema.GoogleCloudContactcenterinsightsV1View) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1View])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/views").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes a view. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, viewsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Gets a view. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1View]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1View])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, viewsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudContactcenterinsightsV1View]] = (fun: get) => fun.apply()
				}
				/** Updates a view. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContactcenterinsightsV1View(body: Schema.GoogleCloudContactcenterinsightsV1View) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1View])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, viewsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/views/${viewsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists views. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1ListViewsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1ListViewsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, pageSize: Int, pageToken: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/views").addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudContactcenterinsightsV1ListViewsResponse]] = (fun: list) => fun.apply()
				}
			}
			object analysisRules {
				/** Creates a analysis rule. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContactcenterinsightsV1AnalysisRule(body: Schema.GoogleCloudContactcenterinsightsV1AnalysisRule) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1AnalysisRule])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/analysisRules").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes a analysis rule. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, analysisRulesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/analysisRules/${analysisRulesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Get a analysis rule. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1AnalysisRule]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1AnalysisRule])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, analysisRulesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/analysisRules/${analysisRulesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudContactcenterinsightsV1AnalysisRule]] = (fun: get) => fun.apply()
				}
				/** Updates a analysis rule. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The list of fields to be updated. If the update_mask is not provided, the update will be applied to all fields.<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new patch(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					/** Perform the request */
					def withGoogleCloudContactcenterinsightsV1AnalysisRule(body: Schema.GoogleCloudContactcenterinsightsV1AnalysisRule) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1AnalysisRule])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, analysisRulesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/analysisRules/${analysisRulesId}").addQueryStringParameters("name" -> name.toString))
				}
				/** Lists analysis rules. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1ListAnalysisRulesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The maximum number of analysis rule to return in the response. If this value is zero, the service will select a default size. A call may return fewer objects than requested. A non-empty `next_page_token` in the response indicates that more data is available.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Optional. The value returned by the last `ListAnalysisRulesResponse`; indicates that this is a continuation of a prior `ListAnalysisRules` call and the system should return the next page of data. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1ListAnalysisRulesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/analysisRules").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudContactcenterinsightsV1ListAnalysisRulesResponse]] = (fun: list) => fun.apply()
				}
			}
			object conversations {
				/** Gets conversation statistics. */
				class calculateStats(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1CalculateStatsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1CalculateStatsResponse])
				}
				object calculateStats {
					def apply(projectsId :PlayApi, locationsId :PlayApi, filter: String, location: String)(using signer: RequestSigner, ec: ExecutionContext): calculateStats = new calculateStats(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations:calculateStats").addQueryStringParameters("filter" -> filter.toString, "location" -> location.toString))
					given Conversion[calculateStats, Future[Schema.GoogleCloudContactcenterinsightsV1CalculateStatsResponse]] = (fun: calculateStats) => fun.apply()
				}
				/** Deletes multiple conversations in a single request. */
				class bulkDelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContactcenterinsightsV1BulkDeleteConversationsRequest(body: Schema.GoogleCloudContactcenterinsightsV1BulkDeleteConversationsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object bulkDelete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): bulkDelete = new bulkDelete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations:bulkDelete").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Creates a conversation. Note that this method does not support audio transcription or redaction. Use `conversations.upload` instead. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContactcenterinsightsV1Conversation(body: Schema.GoogleCloudContactcenterinsightsV1Conversation) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1Conversation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, conversationId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations").addQueryStringParameters("parent" -> parent.toString, "conversationId" -> conversationId.toString))
				}
				/** Imports conversations and processes them according to the user's configuration. */
				class ingest(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContactcenterinsightsV1IngestConversationsRequest(body: Schema.GoogleCloudContactcenterinsightsV1IngestConversationsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object ingest {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): ingest = new ingest(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations:ingest").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Gets a conversation. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1Conversation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1Conversation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, conversationsId :PlayApi, name: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations/${conversationsId}").addQueryStringParameters("name" -> name.toString, "view" -> view.toString))
					given Conversion[get, Future[Schema.GoogleCloudContactcenterinsightsV1Conversation]] = (fun: get) => fun.apply()
				}
				/** Analyzes multiple conversations in a single request. */
				class bulkAnalyze(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContactcenterinsightsV1BulkAnalyzeConversationsRequest(body: Schema.GoogleCloudContactcenterinsightsV1BulkAnalyzeConversationsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object bulkAnalyze {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): bulkAnalyze = new bulkAnalyze(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations:bulkAnalyze").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Updates a conversation. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContactcenterinsightsV1Conversation(body: Schema.GoogleCloudContactcenterinsightsV1Conversation) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1Conversation])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, conversationsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations/${conversationsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Lists conversations. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1ListConversationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Optional. The attribute by which to order conversations in the response. If empty, conversations will be ordered by descending creation time. Supported values are one of the following: &#42; create_time &#42; customer_satisfaction_rating &#42; duration &#42; latest_analysis &#42; start_time &#42; turn_count The default sort order is ascending. To specify order, append `asc` or `desc` (`create_time desc`). For more details, see [Google AIPs Ordering](https://google.aip.dev/132#ordering). */
					def withOrderBy(orderBy: String) = new list(req.addQueryStringParameters("orderBy" -> orderBy.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1ListConversationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, filter: String, pageToken: String, pageSize: Int, view: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "view" -> view.toString))
					given Conversion[list, Future[Schema.GoogleCloudContactcenterinsightsV1ListConversationsResponse]] = (fun: list) => fun.apply()
				}
				/** Create a long-running conversation upload operation. This method differs from `CreateConversation` by allowing audio transcription and optional DLP redaction. */
				class upload(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContactcenterinsightsV1UploadConversationRequest(body: Schema.GoogleCloudContactcenterinsightsV1UploadConversationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object upload {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): upload = new upload(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations:upload").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes a conversation. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, conversationsId :PlayApi, force: Boolean, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations/${conversationsId}").addQueryStringParameters("force" -> force.toString, "name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				object feedbackLabels {
					/** Create feedback label. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. The ID of the feedback label to create. If one is not specified it will be generated by the server. */
						def withFeedbackLabelId(feedbackLabelId: String) = new create(req.addQueryStringParameters("feedbackLabelId" -> feedbackLabelId.toString))
						/** Perform the request */
						def withGoogleCloudContactcenterinsightsV1FeedbackLabel(body: Schema.GoogleCloudContactcenterinsightsV1FeedbackLabel) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1FeedbackLabel])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, conversationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations/${conversationsId}/feedbackLabels").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Delete feedback label. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, conversationsId :PlayApi, feedbackLabelsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations/${conversationsId}/feedbackLabels/${feedbackLabelsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					/** Get feedback label. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1FeedbackLabel]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1FeedbackLabel])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, conversationsId :PlayApi, feedbackLabelsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations/${conversationsId}/feedbackLabels/${feedbackLabelsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudContactcenterinsightsV1FeedbackLabel]] = (fun: get) => fun.apply()
					}
					/** Update feedback label. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudContactcenterinsightsV1FeedbackLabel(body: Schema.GoogleCloudContactcenterinsightsV1FeedbackLabel) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1FeedbackLabel])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, conversationsId :PlayApi, feedbackLabelsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations/${conversationsId}/feedbackLabels/${feedbackLabelsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** List feedback labels. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1ListFeedbackLabelsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Optional. The value returned by the last `ListFeedbackLabelsResponse`. This value indicates that this is a continuation of a prior `ListFeedbackLabels` call and that the system should return the next page of data. */
						def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
						/** Optional. The maximum number of feedback labels to return in the response. A valid page size ranges from 0 to 100,000 inclusive. If the page size is zero or unspecified, a default page size of 100 will be chosen. Note that a call might return fewer results than the requested page size.<br>Format: int32 */
						def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
						/** Optional. A filter to reduce results to a specific subset. Supports disjunctions (OR) and conjunctions (AND). Automatically sorts by conversation ID. To sort by all feedback labels in a project see ListAllFeedbackLabels. Supported fields: &#42; `issue_model_id` &#42; `qa_question_id` &#42; `qa_scorecard_id` &#42; `min_create_time` &#42; `max_create_time` &#42; `min_update_time` &#42; `max_update_time` &#42; `feedback_label_type`: QUALITY_AI, TOPIC_MODELING */
						def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1ListFeedbackLabelsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, conversationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations/${conversationsId}/feedbackLabels").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudContactcenterinsightsV1ListFeedbackLabelsResponse]] = (fun: list) => fun.apply()
					}
				}
				object analyses {
					/** Creates an analysis. The long running operation is done when the analysis has completed. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudContactcenterinsightsV1Analysis(body: Schema.GoogleCloudContactcenterinsightsV1Analysis) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, conversationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations/${conversationsId}/analyses").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Lists analyses. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1ListAnalysesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1ListAnalysesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, conversationsId :PlayApi, filter: String, pageSize: Int, pageToken: String, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations/${conversationsId}/analyses").addQueryStringParameters("filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudContactcenterinsightsV1ListAnalysesResponse]] = (fun: list) => fun.apply()
					}
					/** Gets an analysis. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudContactcenterinsightsV1Analysis]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudContactcenterinsightsV1Analysis])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, conversationsId :PlayApi, analysesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations/${conversationsId}/analyses/${analysesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudContactcenterinsightsV1Analysis]] = (fun: get) => fun.apply()
					}
					/** Deletes an analysis. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, conversationsId :PlayApi, analysesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/conversations/${conversationsId}/analyses/${analysesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
				}
			}
			object insightsdata {
				/** Export insights data to a destination defined in the request body. */
				class `export`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudContactcenterinsightsV1ExportInsightsDataRequest(body: Schema.GoogleCloudContactcenterinsightsV1ExportInsightsDataRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object `export` {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): `export` = new `export`(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/insightsdata:export").addQueryStringParameters("parent" -> parent.toString))
				}
			}
		}
	}
}
