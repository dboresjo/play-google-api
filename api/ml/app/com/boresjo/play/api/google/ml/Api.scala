package com.boresjo.play.api.google.ml

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
		"""https://www.googleapis.com/auth/cloud-platform""" /* See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account. */,
		"""https://www.googleapis.com/auth/cloud-platform.read-only""" /* View your data across Google Cloud services and see the email address of your Google Account */
	)

	private val BASE_URL = "https://ml.googleapis.com/"

	object projects {
		/** Performs explanation on the data in the request. {% dynamic include "/ai-platform/includes/___explain-request" %}  */
		class explain(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withGoogleCloudMlV1__ExplainRequest(body: Schema.GoogleCloudMlV1__ExplainRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleApi__HttpBody])
		}
		object explain {
			def apply(projectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): explain = new explain(ws.url(BASE_URL + s"v1/projects/${projectsId}:explain").addQueryStringParameters("name" -> name.toString))
		}
		/** Performs online prediction on the data in the request. {% dynamic include "/ai-platform/includes/___predict-request" %}  */
		class predict(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def withGoogleCloudMlV1__PredictRequest(body: Schema.GoogleCloudMlV1__PredictRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleApi__HttpBody])
		}
		object predict {
			def apply(projectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): predict = new predict(ws.url(BASE_URL + s"v1/projects/${projectsId}:predict").addQueryStringParameters("name" -> name.toString))
		}
		/** Get the service account information associated with your project. You need this information in order to grant the service account permissions for the Google Cloud Storage location where you put your model training code for training the model with Google Cloud Machine Learning. */
		class getConfig(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudMlV1__GetConfigResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudMlV1__GetConfigResponse])
		}
		object getConfig {
			def apply(projectsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getConfig = new getConfig(ws.url(BASE_URL + s"v1/projects/${projectsId}:getConfig").addQueryStringParameters("name" -> name.toString))
			given Conversion[getConfig, Future[Schema.GoogleCloudMlV1__GetConfigResponse]] = (fun: getConfig) => fun.apply()
		}
		object locations {
			/** Get the complete list of CMLE capabilities in a location, along with their location-specific properties. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudMlV1__Location]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudMlV1__Location])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudMlV1__Location]] = (fun: get) => fun.apply()
			}
			/** List all locations that provides at least one type of CMLE capability. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudMlV1__ListLocationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
				/** Optional. A page token to request the next page of results. You get the token from the `next_page_token` field of the response from the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The number of locations to retrieve per "page" of results. If there are more remaining results than this number, the response message will contain a valid value in the `next_page_token` field. The default value is 20, and the maximum page size is 100.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudMlV1__ListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudMlV1__ListLocationsResponse]] = (fun: list) => fun.apply()
			}
			object operations {
				/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunning__Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunning__Operation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleLongrunning__Operation]] = (fun: get) => fun.apply()
				}
				/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
				class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobuf__Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.GoogleProtobuf__Empty])
				}
				object cancel {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
					given Conversion[cancel, Future[Schema.GoogleProtobuf__Empty]] = (fun: cancel) => fun.apply()
				}
			}
			object studies {
				/** Creates a study. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudMlV1__Study(body: Schema.GoogleCloudMlV1__Study) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudMlV1__Study])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, studyId: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/studies").addQueryStringParameters("parent" -> parent.toString, "studyId" -> studyId.toString))
				}
				/** Deletes a study. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobuf__Empty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobuf__Empty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, studiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/studies/${studiesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobuf__Empty]] = (fun: delete) => fun.apply()
				}
				/** Gets a study. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudMlV1__Study]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudMlV1__Study])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, studiesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/studies/${studiesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudMlV1__Study]] = (fun: get) => fun.apply()
				}
				/** Lists all the studies in a region for an associated project. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudMlV1__ListStudiesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudMlV1__ListStudiesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/studies").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudMlV1__ListStudiesResponse]] = (fun: list) => fun.apply()
				}
				object trials {
					/** Adds a measurement of the objective metrics to a trial. This measurement is assumed to have been taken before the trial is complete. */
					class addMeasurement(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudMlV1__AddTrialMeasurementRequest(body: Schema.GoogleCloudMlV1__AddTrialMeasurementRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudMlV1__Trial])
					}
					object addMeasurement {
						def apply(projectsId :PlayApi, locationsId :PlayApi, studiesId :PlayApi, trialsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): addMeasurement = new addMeasurement(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/studies/${studiesId}/trials/${trialsId}:addMeasurement").addQueryStringParameters("name" -> name.toString))
					}
					/** Checks whether a trial should stop or not. Returns a long-running operation. When the operation is successful, it will contain a CheckTrialEarlyStoppingStateResponse. */
					class checkEarlyStoppingState(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudMlV1__CheckTrialEarlyStoppingStateRequest(body: Schema.GoogleCloudMlV1__CheckTrialEarlyStoppingStateRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunning__Operation])
					}
					object checkEarlyStoppingState {
						def apply(projectsId :PlayApi, locationsId :PlayApi, studiesId :PlayApi, trialsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): checkEarlyStoppingState = new checkEarlyStoppingState(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/studies/${studiesId}/trials/${trialsId}:checkEarlyStoppingState").addQueryStringParameters("name" -> name.toString))
					}
					/** Adds one or more trials to a study, with parameter values suggested by AI Platform Vizier. Returns a long-running operation associated with the generation of trial suggestions. When this long-running operation succeeds, it will contain a SuggestTrialsResponse. */
					class suggest(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudMlV1__SuggestTrialsRequest(body: Schema.GoogleCloudMlV1__SuggestTrialsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunning__Operation])
					}
					object suggest {
						def apply(projectsId :PlayApi, locationsId :PlayApi, studiesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): suggest = new suggest(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/studies/${studiesId}/trials:suggest").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Adds a user provided trial to a study. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudMlV1__Trial(body: Schema.GoogleCloudMlV1__Trial) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudMlV1__Trial])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, studiesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/studies/${studiesId}/trials").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Stops a trial. */
					class stop(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudMlV1__StopTrialRequest(body: Schema.GoogleCloudMlV1__StopTrialRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudMlV1__Trial])
					}
					object stop {
						def apply(projectsId :PlayApi, locationsId :PlayApi, studiesId :PlayApi, trialsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): stop = new stop(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/studies/${studiesId}/trials/${trialsId}:stop").addQueryStringParameters("name" -> name.toString))
					}
					/** Lists the pareto-optimal trials for multi-objective study or the optimal trials for single-objective study. The definition of pareto-optimal can be checked in wiki page. https://en.wikipedia.org/wiki/Pareto_efficiency */
					class listOptimalTrials(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudMlV1__ListOptimalTrialsRequest(body: Schema.GoogleCloudMlV1__ListOptimalTrialsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudMlV1__ListOptimalTrialsResponse])
					}
					object listOptimalTrials {
						def apply(projectsId :PlayApi, locationsId :PlayApi, studiesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): listOptimalTrials = new listOptimalTrials(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/studies/${studiesId}/trials:listOptimalTrials").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Marks a trial as complete. */
					class complete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudMlV1__CompleteTrialRequest(body: Schema.GoogleCloudMlV1__CompleteTrialRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudMlV1__Trial])
					}
					object complete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, studiesId :PlayApi, trialsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): complete = new complete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/studies/${studiesId}/trials/${trialsId}:complete").addQueryStringParameters("name" -> name.toString))
					}
					/** Deletes a trial. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobuf__Empty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobuf__Empty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, studiesId :PlayApi, trialsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/studies/${studiesId}/trials/${trialsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobuf__Empty]] = (fun: delete) => fun.apply()
					}
					/** Gets a trial. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudMlV1__Trial]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudMlV1__Trial])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, studiesId :PlayApi, trialsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/studies/${studiesId}/trials/${trialsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudMlV1__Trial]] = (fun: get) => fun.apply()
					}
					/** Lists the trials associated with a study. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudMlV1__ListTrialsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudMlV1__ListTrialsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, studiesId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/studies/${studiesId}/trials").addQueryStringParameters("parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudMlV1__ListTrialsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
		object operations {
			/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunning__ListOperationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunning__ListOperationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.GoogleLongrunning__ListOperationsResponse]] = (fun: list) => fun.apply()
			}
			/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunning__Operation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunning__Operation])
			}
			object get {
				def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleLongrunning__Operation]] = (fun: get) => fun.apply()
			}
			/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
			class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobuf__Empty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.GoogleProtobuf__Empty])
			}
			object cancel {
				def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
				given Conversion[cancel, Future[Schema.GoogleProtobuf__Empty]] = (fun: cancel) => fun.apply()
			}
		}
		object jobs {
			/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
			class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleIamV1__TestIamPermissionsRequest(body: Schema.GoogleIamV1__TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleIamV1__TestIamPermissionsResponse])
			}
			object testIamPermissions {
				def apply(projectsId :PlayApi, jobsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/jobs/${jobsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
			}
			/** Cancels a running job. */
			class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudMlV1__CancelJobRequest(body: Schema.GoogleCloudMlV1__CancelJobRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobuf__Empty])
			}
			object cancel {
				def apply(projectsId :PlayApi, jobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/jobs/${jobsId}:cancel").addQueryStringParameters("name" -> name.toString))
			}
			/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
			class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleIamV1__Policy]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
				def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleIamV1__Policy])
			}
			object getIamPolicy {
				def apply(projectsId :PlayApi, jobsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/jobs/${jobsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				given Conversion[getIamPolicy, Future[Schema.GoogleIamV1__Policy]] = (fun: getIamPolicy) => fun.apply()
			}
			/** Describes a job. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudMlV1__Job]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudMlV1__Job])
			}
			object get {
				def apply(projectsId :PlayApi, jobsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/jobs/${jobsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudMlV1__Job]] = (fun: get) => fun.apply()
			}
			/** Updates a specific job resource. Currently the only supported fields to update are `labels`. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudMlV1__Job(body: Schema.GoogleCloudMlV1__Job) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudMlV1__Job])
			}
			object patch {
				def apply(projectsId :PlayApi, jobsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/jobs/${jobsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists the jobs in the project. If there are no jobs that match the request parameters, the list request returns an empty response body: {}. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudMlV1__ListJobsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
				/** Optional. Specifies the subset of jobs to retrieve. You can filter on the value of one or more attributes of the job object. For example, retrieve jobs with a job identifier that starts with 'census': gcloud ai-platform jobs list --filter='jobId:census&#42;' List all failed jobs with names that start with 'rnn': gcloud ai-platform jobs list --filter='jobId:rnn&#42; AND state:FAILED' For more examples, see the guide to monitoring jobs. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Optional. A page token to request the next page of results. You get the token from the `next_page_token` field of the response from the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The number of jobs to retrieve per "page" of results. If there are more remaining results than this number, the response message will contain a valid value in the `next_page_token` field. The default value is 20, and the maximum page size is 100.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudMlV1__ListJobsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/jobs").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudMlV1__ListJobsResponse]] = (fun: list) => fun.apply()
			}
			/** Creates a training or a batch prediction job. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudMlV1__Job(body: Schema.GoogleCloudMlV1__Job) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudMlV1__Job])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/jobs").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
			class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleIamV1__SetIamPolicyRequest(body: Schema.GoogleIamV1__SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleIamV1__Policy])
			}
			object setIamPolicy {
				def apply(projectsId :PlayApi, jobsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/jobs/${jobsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
			}
		}
		object models {
			/** Returns permissions that a caller has on the specified resource. If the resource does not exist, this will return an empty set of permissions, not a `NOT_FOUND` error. Note: This operation is designed to be used for building permission-aware UIs and command-line tools, not for authorization checking. This operation may "fail open" without warning. */
			class testIamPermissions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleIamV1__TestIamPermissionsRequest(body: Schema.GoogleIamV1__TestIamPermissionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleIamV1__TestIamPermissionsResponse])
			}
			object testIamPermissions {
				def apply(projectsId :PlayApi, modelsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): testIamPermissions = new testIamPermissions(ws.url(BASE_URL + s"v1/projects/${projectsId}/models/${modelsId}:testIamPermissions").addQueryStringParameters("resource" -> resource.toString))
			}
			/** Gets the access control policy for a resource. Returns an empty policy if the resource exists and does not have a policy set. */
			class getIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleIamV1__Policy]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies).<br>Format: int32 */
				def withOptionsRequestedPolicyVersion(optionsRequestedPolicyVersion: Int) = new getIamPolicy(req.addQueryStringParameters("options.requestedPolicyVersion" -> optionsRequestedPolicyVersion.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleIamV1__Policy])
			}
			object getIamPolicy {
				def apply(projectsId :PlayApi, modelsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): getIamPolicy = new getIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/models/${modelsId}:getIamPolicy").addQueryStringParameters("resource" -> resource.toString))
				given Conversion[getIamPolicy, Future[Schema.GoogleIamV1__Policy]] = (fun: getIamPolicy) => fun.apply()
			}
			/** Deletes a model. You can only delete a model if there are no versions in it. You can delete versions by calling projects.models.versions.delete. */
			class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunning__Operation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunning__Operation])
			}
			object delete {
				def apply(projectsId :PlayApi, modelsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/models/${modelsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleLongrunning__Operation]] = (fun: delete) => fun.apply()
			}
			/** Gets information about a model, including its name, the description (if set), and the default version (if at least one version of the model has been deployed). */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudMlV1__Model]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudMlV1__Model])
			}
			object get {
				def apply(projectsId :PlayApi, modelsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/models/${modelsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudMlV1__Model]] = (fun: get) => fun.apply()
			}
			/** Updates a specific model resource. Currently the only supported fields to update are `description` and `default_version.name`. */
			class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudMlV1__Model(body: Schema.GoogleCloudMlV1__Model) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleLongrunning__Operation])
			}
			object patch {
				def apply(projectsId :PlayApi, modelsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/models/${modelsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
			}
			/** Lists the models in a project. Each project can contain multiple models, and each model can have multiple versions. If there are no models that match the request parameters, the list request returns an empty response body: {}. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudMlV1__ListModelsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
				/** Optional. Specifies the subset of models to retrieve. */
				def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
				/** Optional. A page token to request the next page of results. You get the token from the `next_page_token` field of the response from the previous call. */
				def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
				/** Optional. The number of models to retrieve per "page" of results. If there are more remaining results than this number, the response message will contain a valid value in the `next_page_token` field. The default value is 20, and the maximum page size is 100.<br>Format: int32 */
				def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudMlV1__ListModelsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/models").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[list, Future[Schema.GoogleCloudMlV1__ListModelsResponse]] = (fun: list) => fun.apply()
			}
			/** Creates a model which will later contain one or more versions. You must add at least one version before you can request predictions from the model. Add versions by calling projects.models.versions.create. */
			class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleCloudMlV1__Model(body: Schema.GoogleCloudMlV1__Model) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudMlV1__Model])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/models").addQueryStringParameters("parent" -> parent.toString))
			}
			/** Sets the access control policy on the specified resource. Replaces any existing policy. Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors. */
			class setIamPolicy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def withGoogleIamV1__SetIamPolicyRequest(body: Schema.GoogleIamV1__SetIamPolicyRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleIamV1__Policy])
			}
			object setIamPolicy {
				def apply(projectsId :PlayApi, modelsId :PlayApi, resource: String)(using signer: RequestSigner, ec: ExecutionContext): setIamPolicy = new setIamPolicy(ws.url(BASE_URL + s"v1/projects/${projectsId}/models/${modelsId}:setIamPolicy").addQueryStringParameters("resource" -> resource.toString))
			}
			object versions {
				/** Creates a new version of a model from a trained TensorFlow model. If the version created in the cloud by this call is the first deployed version of the specified model, it will be made the default version of the model. When you add a version to a model that already has one or more versions, the default version does not automatically change. If you want a new version to be the default, you must call projects.models.versions.setDefault. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudMlV1__Version(body: Schema.GoogleCloudMlV1__Version) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunning__Operation])
				}
				object create {
					def apply(projectsId :PlayApi, modelsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/models/${modelsId}/versions").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes a model version. Each model can have multiple versions deployed and in use at any given time. Use this method to remove a single version. Note: You cannot delete the version that is set as the default version of the model unless it is the only remaining version. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunning__Operation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunning__Operation])
				}
				object delete {
					def apply(projectsId :PlayApi, modelsId :PlayApi, versionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/models/${modelsId}/versions/${versionsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleLongrunning__Operation]] = (fun: delete) => fun.apply()
				}
				/** Gets information about a model version. Models can have multiple versions. You can call projects.models.versions.list to get the same information that this method returns for all of the versions of a model. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudMlV1__Version]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudMlV1__Version])
				}
				object get {
					def apply(projectsId :PlayApi, modelsId :PlayApi, versionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/models/${modelsId}/versions/${versionsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudMlV1__Version]] = (fun: get) => fun.apply()
				}
				/** Updates the specified Version resource. Currently the only update-able fields are `description`, `requestLoggingConfig`, `autoScaling.minNodes`, and `manualScaling.nodes`. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudMlV1__Version(body: Schema.GoogleCloudMlV1__Version) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleLongrunning__Operation])
				}
				object patch {
					def apply(projectsId :PlayApi, modelsId :PlayApi, versionsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v1/projects/${projectsId}/models/${modelsId}/versions/${versionsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Designates a version to be the default for the model. The default version is used for prediction requests made against the model that don't specify a version. The first version to be created for a model is automatically set as the default. You must make any subsequent changes to the default version setting manually using this method. */
				class setDefault(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudMlV1__SetDefaultVersionRequest(body: Schema.GoogleCloudMlV1__SetDefaultVersionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudMlV1__Version])
				}
				object setDefault {
					def apply(projectsId :PlayApi, modelsId :PlayApi, versionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): setDefault = new setDefault(ws.url(BASE_URL + s"v1/projects/${projectsId}/models/${modelsId}/versions/${versionsId}:setDefault").addQueryStringParameters("name" -> name.toString))
				}
				/** Gets basic information about all the versions of a model. If you expect that a model has many versions, or if you need to handle only a limited number of results at a time, you can request that the list be retrieved in batches (called pages). If there are no versions that match the request parameters, the list request returns an empty response body: {}. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudMlV1__ListVersionsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/cloud-platform.read-only""")
					/** Optional. Specifies the subset of versions to retrieve. */
					def withFilter(filter: String) = new list(req.addQueryStringParameters("filter" -> filter.toString))
					/** Optional. A page token to request the next page of results. You get the token from the `next_page_token` field of the response from the previous call. */
					def withPageToken(pageToken: String) = new list(req.addQueryStringParameters("pageToken" -> pageToken.toString))
					/** Optional. The number of versions to retrieve per "page" of results. If there are more remaining results than this number, the response message will contain a valid value in the `next_page_token` field. The default value is 20, and the maximum page size is 100.<br>Format: int32 */
					def withPageSize(pageSize: Int) = new list(req.addQueryStringParameters("pageSize" -> pageSize.toString))
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudMlV1__ListVersionsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, modelsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/models/${modelsId}/versions").addQueryStringParameters("parent" -> parent.toString))
					given Conversion[list, Future[Schema.GoogleCloudMlV1__ListVersionsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
