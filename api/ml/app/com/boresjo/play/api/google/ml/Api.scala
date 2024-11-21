package com.boresjo.play.api.google.ml

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://ml.googleapis.com/"

	object projects {
		class explain(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleCloudMlV1__ExplainRequest(body: Schema.GoogleCloudMlV1__ExplainRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleApi__HttpBody])
		}
		object explain {
			def apply(projectsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): explain = new explain(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}:explain")).addQueryStringParameters("name" -> name.toString))
		}
		class predict(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleCloudMlV1__PredictRequest(body: Schema.GoogleCloudMlV1__PredictRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleApi__HttpBody])
		}
		object predict {
			def apply(projectsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): predict = new predict(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}:predict")).addQueryStringParameters("name" -> name.toString))
		}
		class getConfig(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudMlV1__GetConfigResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudMlV1__GetConfigResponse])
		}
		object getConfig {
			def apply(projectsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getConfig = new getConfig(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}:getConfig")).addQueryStringParameters("name" -> name.toString))
			given Conversion[getConfig, Future[Schema.GoogleCloudMlV1__GetConfigResponse]] = (fun: getConfig) => fun.apply()
		}
		object locations {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudMlV1__Location]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudMlV1__Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudMlV1__Location]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudMlV1__ListLocationsResponse]) {
				/** Optional. A page token to request the next page of results. You get the token from the `next_page_token` field of the response from the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The number of locations to retrieve per "page" of results. If there are more remaining results than this number, the response message will contain a valid value in the `next_page_token` field. The default value is 20, and the maximum page size is 100.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudMlV1__ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations")).addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudMlV1__ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			object operations {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunning__Operation]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunning__Operation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleLongrunning__Operation]] = (fun: get) => fun.apply()
				}
				class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobuf__Empty]) {
					def apply() = req.execute("POST").map(_.json.as[Schema.GoogleProtobuf__Empty])
				}
				object cancel {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}:cancel")).addQueryStringParameters("name" -> name.toString))
					given Conversion[cancel, Future[Schema.GoogleProtobuf__Empty]] = (fun: cancel) => fun.apply()
				}
			}
			object studies {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudMlV1__Study(body: Schema.GoogleCloudMlV1__Study) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudMlV1__Study])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, studyId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/studies")).addQueryStringParameters("parent" -> parent.toString, "studyId" -> studyId.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobuf__Empty]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobuf__Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, studiesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/studies/${studiesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobuf__Empty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudMlV1__Study]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudMlV1__Study])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, studiesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/studies/${studiesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudMlV1__Study]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudMlV1__ListStudiesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudMlV1__ListStudiesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/studies")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudMlV1__ListStudiesResponse]] = (fun: list) => fun.apply()
				}
				object trials {
					class addMeasurement(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudMlV1__AddTrialMeasurementRequest(body: Schema.GoogleCloudMlV1__AddTrialMeasurementRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudMlV1__Trial])
					}
					object addMeasurement {
						def apply(projectsId :PlayApi, locationsId :PlayApi, studiesId :PlayApi, trialsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): addMeasurement = new addMeasurement(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/studies/${studiesId}/trials/${trialsId}:addMeasurement")).addQueryStringParameters("name" -> name.toString))
					}
					class checkEarlyStoppingState(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudMlV1__CheckTrialEarlyStoppingStateRequest(body: Schema.GoogleCloudMlV1__CheckTrialEarlyStoppingStateRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunning__Operation])
					}
					object checkEarlyStoppingState {
						def apply(projectsId :PlayApi, locationsId :PlayApi, studiesId :PlayApi, trialsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): checkEarlyStoppingState = new checkEarlyStoppingState(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/studies/${studiesId}/trials/${trialsId}:checkEarlyStoppingState")).addQueryStringParameters("name" -> name.toString))
					}
					class suggest(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudMlV1__SuggestTrialsRequest(body: Schema.GoogleCloudMlV1__SuggestTrialsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunning__Operation])
					}
					object suggest {
						def apply(projectsId :PlayApi, locationsId :PlayApi, studiesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): suggest = new suggest(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/studies/${studiesId}/trials:suggest")).addQueryStringParameters("parent" -> parent.toString))
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudMlV1__Trial(body: Schema.GoogleCloudMlV1__Trial) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudMlV1__Trial])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, studiesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/studies/${studiesId}/trials")).addQueryStringParameters("parent" -> parent.toString))
					}
					class stop(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudMlV1__StopTrialRequest(body: Schema.GoogleCloudMlV1__StopTrialRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudMlV1__Trial])
					}
					object stop {
						def apply(projectsId :PlayApi, locationsId :PlayApi, studiesId :PlayApi, trialsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): stop = new stop(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/studies/${studiesId}/trials/${trialsId}:stop")).addQueryStringParameters("name" -> name.toString))
					}
					class listOptimalTrials(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudMlV1__ListOptimalTrialsRequest(body: Schema.GoogleCloudMlV1__ListOptimalTrialsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudMlV1__ListOptimalTrialsResponse])
					}
					object listOptimalTrials {
						def apply(projectsId :PlayApi, locationsId :PlayApi, studiesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): listOptimalTrials = new listOptimalTrials(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/studies/${studiesId}/trials:listOptimalTrials")).addQueryStringParameters("parent" -> parent.toString))
					}
					class complete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudMlV1__CompleteTrialRequest(body: Schema.GoogleCloudMlV1__CompleteTrialRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudMlV1__Trial])
					}
					object complete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, studiesId :PlayApi, trialsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): complete = new complete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/studies/${studiesId}/trials/${trialsId}:complete")).addQueryStringParameters("name" -> name.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobuf__Empty]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobuf__Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, studiesId :PlayApi, trialsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/studies/${studiesId}/trials/${trialsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobuf__Empty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudMlV1__Trial]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudMlV1__Trial])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, studiesId :PlayApi, trialsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/studies/${studiesId}/trials/${trialsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudMlV1__Trial]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudMlV1__ListTrialsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudMlV1__ListTrialsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, studiesId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/studies/${studiesId}/trials")).addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudMlV1__ListTrialsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
		object operations {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunning__ListOperationsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunning__ListOperationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/operations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.GoogleLongrunning__ListOperationsResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunning__Operation]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunning__Operation])
			}
			object get {
				def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleLongrunning__Operation]] = (fun: get) => fun.apply()
			}
			class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobuf__Empty]) {
				def apply() = req.execute("POST").map(_.json.as[Schema.GoogleProtobuf__Empty])
			}
			object cancel {
				def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/operations/${operationsId}:cancel")).addQueryStringParameters("name" -> name.toString))
				given Conversion[cancel, Future[Schema.GoogleProtobuf__Empty]] = (fun: cancel) => fun.apply()
			}
		}
		object jobs {
			class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleIamV1__TestIamPermissionsRequest(body: Schema.GoogleIamV1__TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleIamV1__TestIamPermissionsResponse])
			}
			object testIamPermissions {
				def apply(projectsId :PlayApi, jobsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/jobs/${jobsId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
			}
			class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudMlV1__CancelJobRequest(body: Schema.GoogleCloudMlV1__CancelJobRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleProtobuf__Empty])
			}
			object cancel {
				def apply(projectsId :PlayApi, jobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/jobs/${jobsId}:cancel")).addQueryStringParameters("name" -> name.toString))
			}
			class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleIamV1__Policy]) {
				/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
				def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleIamV1__Policy])
			}
			object getIamPolicy {
				def apply(projectsId :PlayApi, jobsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/jobs/${jobsId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				given Conversion[getIamPolicy, Future[Schema.GoogleIamV1__Policy]] = (fun: getIamPolicy) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudMlV1__Job]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudMlV1__Job])
			}
			object get {
				def apply(projectsId :PlayApi, jobsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/jobs/${jobsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudMlV1__Job]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudMlV1__Job(body: Schema.GoogleCloudMlV1__Job) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleCloudMlV1__Job])
			}
			object patch {
				def apply(projectsId :PlayApi, jobsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/jobs/${jobsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudMlV1__ListJobsResponse]) {
				/** Optional. Specifies the subset of jobs to retrieve. You can filter on the value of one or more attributes of the job object. For example, retrieve jobs with a job identifier that starts with 'census': gcloud ai-platform jobs list --filter='jobId:census&#42;' List all failed jobs with names that start with 'rnn': gcloud ai-platform jobs list --filter='jobId:rnn&#42; AND state:FAILED' For more examples, see the guide to monitoring jobs. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Optional. A page token to request the next page of results. You get the token from the `next_page_token` field of the response from the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The number of jobs to retrieve per "page" of results. If there are more remaining results than this number, the response message will contain a valid value in the `next_page_token` field. The default value is 20, and the maximum page size is 100.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudMlV1__ListJobsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/jobs")).addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudMlV1__ListJobsResponse]] = (fun: list) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudMlV1__Job(body: Schema.GoogleCloudMlV1__Job) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudMlV1__Job])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/jobs")).addQueryStringParameters("parent" -> parent.toString))
			}
			class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleIamV1__SetIamPolicyRequest(body: Schema.GoogleIamV1__SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleIamV1__Policy])
			}
			object setIamPolicy {
				def apply(projectsId :PlayApi, jobsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/jobs/${jobsId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
			}
		}
		object models {
			class testIamPermissions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleIamV1__TestIamPermissionsRequest(body: Schema.GoogleIamV1__TestIamPermissionsRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleIamV1__TestIamPermissionsResponse])
			}
			object testIamPermissions {
				def apply(projectsId :PlayApi, modelsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): testIamPermissions = new testIamPermissions(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/models/${modelsId}:testIamPermissions")).addQueryStringParameters("resource" -> resource.toString))
			}
			class getIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleIamV1__Policy]) {
				/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
				def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleIamV1__Policy])
			}
			object getIamPolicy {
				def apply(projectsId :PlayApi, modelsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): getIamPolicy = new getIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/models/${modelsId}:getIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
				given Conversion[getIamPolicy, Future[Schema.GoogleIamV1__Policy]] = (fun: getIamPolicy) => fun.apply()
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunning__Operation]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleLongrunning__Operation])
			}
			object delete {
				def apply(projectsId :PlayApi, modelsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/models/${modelsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleLongrunning__Operation]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudMlV1__Model]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudMlV1__Model])
			}
			object get {
				def apply(projectsId :PlayApi, modelsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/models/${modelsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudMlV1__Model]] = (fun: get) => fun.apply()
			}
			class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudMlV1__Model(body: Schema.GoogleCloudMlV1__Model) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleLongrunning__Operation])
			}
			object patch {
				def apply(projectsId :PlayApi, modelsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/models/${modelsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudMlV1__ListModelsResponse]) {
				/** Optional. Specifies the subset of models to retrieve. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Optional. A page token to request the next page of results. You get the token from the `next_page_token` field of the response from the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The number of models to retrieve per "page" of results. If there are more remaining results than this number, the response message will contain a valid value in the `next_page_token` field. The default value is 20, and the maximum page size is 100.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudMlV1__ListModelsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/models")).addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudMlV1__ListModelsResponse]] = (fun: list) => fun.apply()
			}
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudMlV1__Model(body: Schema.GoogleCloudMlV1__Model) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudMlV1__Model])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/models")).addQueryStringParameters("parent" -> parent.toString))
			}
			class setIamPolicy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleIamV1__SetIamPolicyRequest(body: Schema.GoogleIamV1__SetIamPolicyRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleIamV1__Policy])
			}
			object setIamPolicy {
				def apply(projectsId :PlayApi, modelsId :PlayApi, resource: String)(using auth: AuthToken, ec: ExecutionContext): setIamPolicy = new setIamPolicy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/models/${modelsId}:setIamPolicy")).addQueryStringParameters("resource" -> resource.toString))
			}
			object versions {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudMlV1__Version(body: Schema.GoogleCloudMlV1__Version) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunning__Operation])
				}
				object create {
					def apply(projectsId :PlayApi, modelsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/models/${modelsId}/versions")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunning__Operation]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleLongrunning__Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, modelsId :PlayApi, versionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/models/${modelsId}/versions/${versionsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleLongrunning__Operation]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudMlV1__Version]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudMlV1__Version])
				}
				object get {
					def apply(projectsId :PlayApi, modelsId :PlayApi, versionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/models/${modelsId}/versions/${versionsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudMlV1__Version]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudMlV1__Version(body: Schema.GoogleCloudMlV1__Version) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.GoogleLongrunning__Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, modelsId :PlayApi, versionsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/models/${modelsId}/versions/${versionsId}")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class setDefault(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudMlV1__SetDefaultVersionRequest(body: Schema.GoogleCloudMlV1__SetDefaultVersionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudMlV1__Version])
				}
				object setDefault {
					def apply(projectsId :PlayApi, modelsId :PlayApi, versionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): setDefault = new setDefault(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/models/${modelsId}/versions/${versionsId}:setDefault")).addQueryStringParameters("name" -> name.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudMlV1__ListVersionsResponse]) {
					/** Optional. Specifies the subset of versions to retrieve. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. A page token to request the next page of results. You get the token from the `next_page_token` field of the response from the previous call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The number of versions to retrieve per "page" of results. If there are more remaining results than this number, the response message will contain a valid value in the `next_page_token` field. The default value is 20, and the maximum page size is 100.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudMlV1__ListVersionsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, modelsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/models/${modelsId}/versions")).addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudMlV1__ListVersionsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
