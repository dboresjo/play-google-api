package com.boresjo.play.api.google.dialogflow

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://dialogflow.googleapis.com/"

	object projects {
		object operations {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object get {
				def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
			}
			class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object cancel {
				def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v3/projects/${projectsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
				given Conversion[cancel, Future[Schema.GoogleProtobufEmpty]] = (fun: cancel) => fun.apply()
			}
		}
		object locations {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudLocationLocation]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudLocationLocation])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudLocationLocation]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudLocationListLocationsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudLocationListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.GoogleCloudLocationListLocationsResponse]] = (fun: list) => fun.apply()
			}
			object operations {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
				}
				class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object cancel {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
					given Conversion[cancel, Future[Schema.GoogleProtobufEmpty]] = (fun: cancel) => fun.apply()
				}
			}
			object agents {
				class updateGenerativeSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					/** Optional. The mask to control which fields get updated. If the mask is not present, all fields will be updated.<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new updateGenerativeSettings(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					def withGoogleCloudDialogflowCxV3GenerativeSettings(body: Schema.GoogleCloudDialogflowCxV3GenerativeSettings) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3GenerativeSettings])
				}
				object updateGenerativeSettings {
					def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): updateGenerativeSettings = new updateGenerativeSettings(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/generativeSettings").addQueryStringParameters("name" -> name.toString))
				}
				class restore(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDialogflowCxV3RestoreAgentRequest(body: Schema.GoogleCloudDialogflowCxV3RestoreAgentRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object restore {
					def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): restore = new restore(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}:restore").addQueryStringParameters("name" -> name.toString))
				}
				class validate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDialogflowCxV3ValidateAgentRequest(body: Schema.GoogleCloudDialogflowCxV3ValidateAgentRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3AgentValidationResult])
				}
				object validate {
					def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): validate = new validate(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}:validate").addQueryStringParameters("name" -> name.toString))
				}
				class getGenerativeSettings(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3GenerativeSettings]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3GenerativeSettings])
				}
				object getGenerativeSettings {
					def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, name: String, languageCode: String)(using auth: AuthToken, ec: ExecutionContext): getGenerativeSettings = new getGenerativeSettings(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/generativeSettings").addQueryStringParameters("name" -> name.toString, "languageCode" -> languageCode.toString))
					given Conversion[getGenerativeSettings, Future[Schema.GoogleCloudDialogflowCxV3GenerativeSettings]] = (fun: getGenerativeSettings) => fun.apply()
				}
				class `export`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDialogflowCxV3ExportAgentRequest(body: Schema.GoogleCloudDialogflowCxV3ExportAgentRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object `export` {
					def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): `export` = new `export`(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}:export").addQueryStringParameters("name" -> name.toString))
				}
				class getValidationResult(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3AgentValidationResult]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3AgentValidationResult])
				}
				object getValidationResult {
					def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, name: String, languageCode: String)(using auth: AuthToken, ec: ExecutionContext): getValidationResult = new getValidationResult(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/validationResult").addQueryStringParameters("name" -> name.toString, "languageCode" -> languageCode.toString))
					given Conversion[getValidationResult, Future[Schema.GoogleCloudDialogflowCxV3AgentValidationResult]] = (fun: getValidationResult) => fun.apply()
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDialogflowCxV3Agent(body: Schema.GoogleCloudDialogflowCxV3Agent) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Agent])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDialogflowCxV3Agent(body: Schema.GoogleCloudDialogflowCxV3Agent) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Agent])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents").addQueryStringParameters("parent" -> parent.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3Agent]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Agent])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3Agent]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListAgentsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListAgentsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListAgentsResponse]] = (fun: list) => fun.apply()
				}
				object environments {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3Environment(body: Schema.GoogleCloudDialogflowCxV3Environment) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments").addQueryStringParameters("parent" -> parent.toString))
					}
					class runContinuousTest(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3RunContinuousTestRequest(body: Schema.GoogleCloudDialogflowCxV3RunContinuousTestRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object runContinuousTest {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, environment: String)(using auth: AuthToken, ec: ExecutionContext): runContinuousTest = new runContinuousTest(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}:runContinuousTest").addQueryStringParameters("environment" -> environment.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3Environment]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Environment])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3Environment]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3Environment(body: Schema.GoogleCloudDialogflowCxV3Environment) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListEnvironmentsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListEnvironmentsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListEnvironmentsResponse]] = (fun: list) => fun.apply()
					}
					class deployFlow(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3DeployFlowRequest(body: Schema.GoogleCloudDialogflowCxV3DeployFlowRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object deployFlow {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, environment: String)(using auth: AuthToken, ec: ExecutionContext): deployFlow = new deployFlow(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}:deployFlow").addQueryStringParameters("environment" -> environment.toString))
					}
					class lookupEnvironmentHistory(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3LookupEnvironmentHistoryResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3LookupEnvironmentHistoryResponse])
					}
					object lookupEnvironmentHistory {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, name: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): lookupEnvironmentHistory = new lookupEnvironmentHistory(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}:lookupEnvironmentHistory").addQueryStringParameters("name" -> name.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[lookupEnvironmentHistory, Future[Schema.GoogleCloudDialogflowCxV3LookupEnvironmentHistoryResponse]] = (fun: lookupEnvironmentHistory) => fun.apply()
					}
					object experiments {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDialogflowCxV3Experiment(body: Schema.GoogleCloudDialogflowCxV3Experiment) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Experiment])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/experiments").addQueryStringParameters("parent" -> parent.toString))
						}
						class stop(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDialogflowCxV3StopExperimentRequest(body: Schema.GoogleCloudDialogflowCxV3StopExperimentRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Experiment])
						}
						object stop {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, experimentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): stop = new stop(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/experiments/${experimentsId}:stop").addQueryStringParameters("name" -> name.toString))
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, experimentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/experiments/${experimentsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3Experiment]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Experiment])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, experimentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/experiments/${experimentsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3Experiment]] = (fun: get) => fun.apply()
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListExperimentsResponse]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListExperimentsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/experiments").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListExperimentsResponse]] = (fun: list) => fun.apply()
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDialogflowCxV3Experiment(body: Schema.GoogleCloudDialogflowCxV3Experiment) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Experiment])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, experimentsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/experiments/${experimentsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						class start(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDialogflowCxV3StartExperimentRequest(body: Schema.GoogleCloudDialogflowCxV3StartExperimentRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Experiment])
						}
						object start {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, experimentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): start = new start(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/experiments/${experimentsId}:start").addQueryStringParameters("name" -> name.toString))
						}
					}
					object continuousTestResults {
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListContinuousTestResultsResponse]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListContinuousTestResultsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/continuousTestResults").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListContinuousTestResultsResponse]] = (fun: list) => fun.apply()
						}
					}
					object sessions {
						class matchIntent(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDialogflowCxV3MatchIntentRequest(body: Schema.GoogleCloudDialogflowCxV3MatchIntentRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3MatchIntentResponse])
						}
						object matchIntent {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, sessionsId :PlayApi, session: String)(using auth: AuthToken, ec: ExecutionContext): matchIntent = new matchIntent(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/sessions/${sessionsId}:matchIntent").addQueryStringParameters("session" -> session.toString))
						}
						class serverStreamingDetectIntent(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDialogflowCxV3DetectIntentRequest(body: Schema.GoogleCloudDialogflowCxV3DetectIntentRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3DetectIntentResponse])
						}
						object serverStreamingDetectIntent {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, sessionsId :PlayApi, session: String)(using auth: AuthToken, ec: ExecutionContext): serverStreamingDetectIntent = new serverStreamingDetectIntent(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/sessions/${sessionsId}:serverStreamingDetectIntent").addQueryStringParameters("session" -> session.toString))
						}
						class detectIntent(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDialogflowCxV3DetectIntentRequest(body: Schema.GoogleCloudDialogflowCxV3DetectIntentRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3DetectIntentResponse])
						}
						object detectIntent {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, sessionsId :PlayApi, session: String)(using auth: AuthToken, ec: ExecutionContext): detectIntent = new detectIntent(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/sessions/${sessionsId}:detectIntent").addQueryStringParameters("session" -> session.toString))
						}
						class fulfillIntent(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDialogflowCxV3FulfillIntentRequest(body: Schema.GoogleCloudDialogflowCxV3FulfillIntentRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3FulfillIntentResponse])
						}
						object fulfillIntent {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, sessionsId :PlayApi, session: String)(using auth: AuthToken, ec: ExecutionContext): fulfillIntent = new fulfillIntent(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/sessions/${sessionsId}:fulfillIntent").addQueryStringParameters("session" -> session.toString))
						}
						object entityTypes {
							class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
								def withGoogleCloudDialogflowCxV3SessionEntityType(body: Schema.GoogleCloudDialogflowCxV3SessionEntityType) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3SessionEntityType])
							}
							object create {
								def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, sessionsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/sessions/${sessionsId}/entityTypes").addQueryStringParameters("parent" -> parent.toString))
							}
							class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
								def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
							}
							object delete {
								def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, sessionsId :PlayApi, entityTypesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/sessions/${sessionsId}/entityTypes/${entityTypesId}").addQueryStringParameters("name" -> name.toString))
								given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
							}
							class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3SessionEntityType]) {
								def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3SessionEntityType])
							}
							object get {
								def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, sessionsId :PlayApi, entityTypesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/sessions/${sessionsId}/entityTypes/${entityTypesId}").addQueryStringParameters("name" -> name.toString))
								given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3SessionEntityType]] = (fun: get) => fun.apply()
							}
							class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
								def withGoogleCloudDialogflowCxV3SessionEntityType(body: Schema.GoogleCloudDialogflowCxV3SessionEntityType) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3SessionEntityType])
							}
							object patch {
								def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, sessionsId :PlayApi, entityTypesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/sessions/${sessionsId}/entityTypes/${entityTypesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
							}
							class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListSessionEntityTypesResponse]) {
								def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListSessionEntityTypesResponse])
							}
							object list {
								def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, sessionsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/sessions/${sessionsId}/entityTypes").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
								given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListSessionEntityTypesResponse]] = (fun: list) => fun.apply()
							}
						}
					}
					object deployments {
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListDeploymentsResponse]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListDeploymentsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/deployments").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListDeploymentsResponse]] = (fun: list) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3Deployment]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Deployment])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, deploymentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/deployments/${deploymentsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3Deployment]] = (fun: get) => fun.apply()
						}
					}
				}
				object changelogs {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListChangelogsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListChangelogsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/changelogs").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListChangelogsResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3Changelog]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Changelog])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, changelogsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/changelogs/${changelogsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3Changelog]] = (fun: get) => fun.apply()
					}
				}
				object webhooks {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3Webhook(body: Schema.GoogleCloudDialogflowCxV3Webhook) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Webhook])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/webhooks").addQueryStringParameters("parent" -> parent.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, webhooksId :PlayApi, name: String, force: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/webhooks/${webhooksId}").addQueryStringParameters("name" -> name.toString, "force" -> force.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3Webhook]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Webhook])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, webhooksId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/webhooks/${webhooksId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3Webhook]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3Webhook(body: Schema.GoogleCloudDialogflowCxV3Webhook) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Webhook])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, webhooksId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/webhooks/${webhooksId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListWebhooksResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListWebhooksResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/webhooks").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListWebhooksResponse]] = (fun: list) => fun.apply()
					}
				}
				object generators {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3Generator(body: Schema.GoogleCloudDialogflowCxV3Generator) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Generator])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String, languageCode: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/generators").addQueryStringParameters("parent" -> parent.toString, "languageCode" -> languageCode.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, generatorsId :PlayApi, name: String, force: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/generators/${generatorsId}").addQueryStringParameters("name" -> name.toString, "force" -> force.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3Generator]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Generator])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, generatorsId :PlayApi, name: String, languageCode: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/generators/${generatorsId}").addQueryStringParameters("name" -> name.toString, "languageCode" -> languageCode.toString))
						given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3Generator]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3Generator(body: Schema.GoogleCloudDialogflowCxV3Generator) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Generator])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, generatorsId :PlayApi, name: String, languageCode: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/generators/${generatorsId}").addQueryStringParameters("name" -> name.toString, "languageCode" -> languageCode.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListGeneratorsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListGeneratorsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String, languageCode: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/generators").addQueryStringParameters("parent" -> parent.toString, "languageCode" -> languageCode.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListGeneratorsResponse]] = (fun: list) => fun.apply()
					}
				}
				object intents {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3Intent(body: Schema.GoogleCloudDialogflowCxV3Intent) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Intent])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String, languageCode: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/intents").addQueryStringParameters("parent" -> parent.toString, "languageCode" -> languageCode.toString))
					}
					class `export`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3ExportIntentsRequest(body: Schema.GoogleCloudDialogflowCxV3ExportIntentsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object `export` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `export` = new `export`(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/intents:export").addQueryStringParameters("parent" -> parent.toString))
					}
					class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3ImportIntentsRequest(body: Schema.GoogleCloudDialogflowCxV3ImportIntentsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/intents:import").addQueryStringParameters("parent" -> parent.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, intentsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/intents/${intentsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3Intent]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Intent])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, intentsId :PlayApi, name: String, languageCode: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/intents/${intentsId}").addQueryStringParameters("name" -> name.toString, "languageCode" -> languageCode.toString))
						given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3Intent]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3Intent(body: Schema.GoogleCloudDialogflowCxV3Intent) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Intent])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, intentsId :PlayApi, name: String, languageCode: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/intents/${intentsId}").addQueryStringParameters("name" -> name.toString, "languageCode" -> languageCode.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListIntentsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListIntentsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String, languageCode: String, intentView: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/intents").addQueryStringParameters("parent" -> parent.toString, "languageCode" -> languageCode.toString, "intentView" -> intentView.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListIntentsResponse]] = (fun: list) => fun.apply()
					}
				}
				object transitionRouteGroups {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3TransitionRouteGroup(body: Schema.GoogleCloudDialogflowCxV3TransitionRouteGroup) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3TransitionRouteGroup])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String, languageCode: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/transitionRouteGroups").addQueryStringParameters("parent" -> parent.toString, "languageCode" -> languageCode.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, transitionRouteGroupsId :PlayApi, name: String, force: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/transitionRouteGroups/${transitionRouteGroupsId}").addQueryStringParameters("name" -> name.toString, "force" -> force.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3TransitionRouteGroup]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3TransitionRouteGroup])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, transitionRouteGroupsId :PlayApi, name: String, languageCode: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/transitionRouteGroups/${transitionRouteGroupsId}").addQueryStringParameters("name" -> name.toString, "languageCode" -> languageCode.toString))
						given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3TransitionRouteGroup]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3TransitionRouteGroup(body: Schema.GoogleCloudDialogflowCxV3TransitionRouteGroup) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3TransitionRouteGroup])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, transitionRouteGroupsId :PlayApi, name: String, updateMask: String, languageCode: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/transitionRouteGroups/${transitionRouteGroupsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "languageCode" -> languageCode.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListTransitionRouteGroupsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListTransitionRouteGroupsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String, pageSize: Int, pageToken: String, languageCode: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/transitionRouteGroups").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "languageCode" -> languageCode.toString))
						given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListTransitionRouteGroupsResponse]] = (fun: list) => fun.apply()
					}
				}
				object flows {
					class validate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3ValidateFlowRequest(body: Schema.GoogleCloudDialogflowCxV3ValidateFlowRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3FlowValidationResult])
					}
					object validate {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): validate = new validate(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}:validate").addQueryStringParameters("name" -> name.toString))
					}
					class `export`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3ExportFlowRequest(body: Schema.GoogleCloudDialogflowCxV3ExportFlowRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object `export` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): `export` = new `export`(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}:export").addQueryStringParameters("name" -> name.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, name: String, force: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}").addQueryStringParameters("name" -> name.toString, "force" -> force.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3Flow(body: Schema.GoogleCloudDialogflowCxV3Flow) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Flow])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, name: String, updateMask: String, languageCode: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "languageCode" -> languageCode.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListFlowsResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListFlowsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String, pageSize: Int, pageToken: String, languageCode: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "languageCode" -> languageCode.toString))
						given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListFlowsResponse]] = (fun: list) => fun.apply()
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3Flow(body: Schema.GoogleCloudDialogflowCxV3Flow) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Flow])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String, languageCode: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows").addQueryStringParameters("parent" -> parent.toString, "languageCode" -> languageCode.toString))
					}
					class getValidationResult(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3FlowValidationResult]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3FlowValidationResult])
					}
					object getValidationResult {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, name: String, languageCode: String)(using auth: AuthToken, ec: ExecutionContext): getValidationResult = new getValidationResult(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/validationResult").addQueryStringParameters("name" -> name.toString, "languageCode" -> languageCode.toString))
						given Conversion[getValidationResult, Future[Schema.GoogleCloudDialogflowCxV3FlowValidationResult]] = (fun: getValidationResult) => fun.apply()
					}
					class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3ImportFlowRequest(body: Schema.GoogleCloudDialogflowCxV3ImportFlowRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows:import").addQueryStringParameters("parent" -> parent.toString))
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3Flow]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Flow])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, name: String, languageCode: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}").addQueryStringParameters("name" -> name.toString, "languageCode" -> languageCode.toString))
						given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3Flow]] = (fun: get) => fun.apply()
					}
					class train(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3TrainFlowRequest(body: Schema.GoogleCloudDialogflowCxV3TrainFlowRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object train {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): train = new train(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}:train").addQueryStringParameters("name" -> name.toString))
					}
					object versions {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDialogflowCxV3Version(body: Schema.GoogleCloudDialogflowCxV3Version) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/versions").addQueryStringParameters("parent" -> parent.toString))
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, versionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/versions/${versionsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3Version]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Version])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, versionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/versions/${versionsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3Version]] = (fun: get) => fun.apply()
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDialogflowCxV3Version(body: Schema.GoogleCloudDialogflowCxV3Version) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Version])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, versionsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/versions/${versionsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListVersionsResponse]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListVersionsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/versions").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListVersionsResponse]] = (fun: list) => fun.apply()
						}
						class load(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDialogflowCxV3LoadVersionRequest(body: Schema.GoogleCloudDialogflowCxV3LoadVersionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object load {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, versionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): load = new load(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/versions/${versionsId}:load").addQueryStringParameters("name" -> name.toString))
						}
						class compareVersions(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDialogflowCxV3CompareVersionsRequest(body: Schema.GoogleCloudDialogflowCxV3CompareVersionsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3CompareVersionsResponse])
						}
						object compareVersions {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, versionsId :PlayApi, baseVersion: String)(using auth: AuthToken, ec: ExecutionContext): compareVersions = new compareVersions(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/versions/${versionsId}:compareVersions").addQueryStringParameters("baseVersion" -> baseVersion.toString))
						}
					}
					object transitionRouteGroups {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDialogflowCxV3TransitionRouteGroup(body: Schema.GoogleCloudDialogflowCxV3TransitionRouteGroup) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3TransitionRouteGroup])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, parent: String, languageCode: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/transitionRouteGroups").addQueryStringParameters("parent" -> parent.toString, "languageCode" -> languageCode.toString))
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, transitionRouteGroupsId :PlayApi, name: String, force: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/transitionRouteGroups/${transitionRouteGroupsId}").addQueryStringParameters("name" -> name.toString, "force" -> force.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3TransitionRouteGroup]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3TransitionRouteGroup])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, transitionRouteGroupsId :PlayApi, name: String, languageCode: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/transitionRouteGroups/${transitionRouteGroupsId}").addQueryStringParameters("name" -> name.toString, "languageCode" -> languageCode.toString))
							given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3TransitionRouteGroup]] = (fun: get) => fun.apply()
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDialogflowCxV3TransitionRouteGroup(body: Schema.GoogleCloudDialogflowCxV3TransitionRouteGroup) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3TransitionRouteGroup])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, transitionRouteGroupsId :PlayApi, name: String, updateMask: String, languageCode: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/transitionRouteGroups/${transitionRouteGroupsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "languageCode" -> languageCode.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListTransitionRouteGroupsResponse]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListTransitionRouteGroupsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, parent: String, pageSize: Int, pageToken: String, languageCode: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/transitionRouteGroups").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "languageCode" -> languageCode.toString))
							given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListTransitionRouteGroupsResponse]] = (fun: list) => fun.apply()
						}
					}
					object pages {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDialogflowCxV3Page(body: Schema.GoogleCloudDialogflowCxV3Page) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Page])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, parent: String, languageCode: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/pages").addQueryStringParameters("parent" -> parent.toString, "languageCode" -> languageCode.toString))
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, pagesId :PlayApi, name: String, force: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/pages/${pagesId}").addQueryStringParameters("name" -> name.toString, "force" -> force.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3Page]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Page])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, pagesId :PlayApi, name: String, languageCode: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/pages/${pagesId}").addQueryStringParameters("name" -> name.toString, "languageCode" -> languageCode.toString))
							given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3Page]] = (fun: get) => fun.apply()
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDialogflowCxV3Page(body: Schema.GoogleCloudDialogflowCxV3Page) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Page])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, pagesId :PlayApi, name: String, languageCode: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/pages/${pagesId}").addQueryStringParameters("name" -> name.toString, "languageCode" -> languageCode.toString, "updateMask" -> updateMask.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListPagesResponse]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListPagesResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, parent: String, languageCode: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/pages").addQueryStringParameters("parent" -> parent.toString, "languageCode" -> languageCode.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListPagesResponse]] = (fun: list) => fun.apply()
						}
					}
				}
				object sessions {
					class matchIntent(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3MatchIntentRequest(body: Schema.GoogleCloudDialogflowCxV3MatchIntentRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3MatchIntentResponse])
					}
					object matchIntent {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, sessionsId :PlayApi, session: String)(using auth: AuthToken, ec: ExecutionContext): matchIntent = new matchIntent(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/sessions/${sessionsId}:matchIntent").addQueryStringParameters("session" -> session.toString))
					}
					class submitAnswerFeedback(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3SubmitAnswerFeedbackRequest(body: Schema.GoogleCloudDialogflowCxV3SubmitAnswerFeedbackRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3AnswerFeedback])
					}
					object submitAnswerFeedback {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, sessionsId :PlayApi, session: String)(using auth: AuthToken, ec: ExecutionContext): submitAnswerFeedback = new submitAnswerFeedback(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/sessions/${sessionsId}:submitAnswerFeedback").addQueryStringParameters("session" -> session.toString))
					}
					class serverStreamingDetectIntent(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3DetectIntentRequest(body: Schema.GoogleCloudDialogflowCxV3DetectIntentRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3DetectIntentResponse])
					}
					object serverStreamingDetectIntent {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, sessionsId :PlayApi, session: String)(using auth: AuthToken, ec: ExecutionContext): serverStreamingDetectIntent = new serverStreamingDetectIntent(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/sessions/${sessionsId}:serverStreamingDetectIntent").addQueryStringParameters("session" -> session.toString))
					}
					class detectIntent(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3DetectIntentRequest(body: Schema.GoogleCloudDialogflowCxV3DetectIntentRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3DetectIntentResponse])
					}
					object detectIntent {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, sessionsId :PlayApi, session: String)(using auth: AuthToken, ec: ExecutionContext): detectIntent = new detectIntent(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/sessions/${sessionsId}:detectIntent").addQueryStringParameters("session" -> session.toString))
					}
					class fulfillIntent(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3FulfillIntentRequest(body: Schema.GoogleCloudDialogflowCxV3FulfillIntentRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3FulfillIntentResponse])
					}
					object fulfillIntent {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, sessionsId :PlayApi, session: String)(using auth: AuthToken, ec: ExecutionContext): fulfillIntent = new fulfillIntent(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/sessions/${sessionsId}:fulfillIntent").addQueryStringParameters("session" -> session.toString))
					}
					object entityTypes {
						class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDialogflowCxV3SessionEntityType(body: Schema.GoogleCloudDialogflowCxV3SessionEntityType) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3SessionEntityType])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, sessionsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/sessions/${sessionsId}/entityTypes").addQueryStringParameters("parent" -> parent.toString))
						}
						class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, sessionsId :PlayApi, entityTypesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/sessions/${sessionsId}/entityTypes/${entityTypesId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3SessionEntityType]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3SessionEntityType])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, sessionsId :PlayApi, entityTypesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/sessions/${sessionsId}/entityTypes/${entityTypesId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3SessionEntityType]] = (fun: get) => fun.apply()
						}
						class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
							def withGoogleCloudDialogflowCxV3SessionEntityType(body: Schema.GoogleCloudDialogflowCxV3SessionEntityType) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3SessionEntityType])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, sessionsId :PlayApi, entityTypesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/sessions/${sessionsId}/entityTypes/${entityTypesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListSessionEntityTypesResponse]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListSessionEntityTypesResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, sessionsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/sessions/${sessionsId}/entityTypes").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListSessionEntityTypesResponse]] = (fun: list) => fun.apply()
						}
					}
				}
				object testCases {
					class run(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3RunTestCaseRequest(body: Schema.GoogleCloudDialogflowCxV3RunTestCaseRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object run {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, testCasesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): run = new run(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/testCases/${testCasesId}:run").addQueryStringParameters("name" -> name.toString))
					}
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3TestCase(body: Schema.GoogleCloudDialogflowCxV3TestCase) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3TestCase])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/testCases").addQueryStringParameters("parent" -> parent.toString))
					}
					class `export`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3ExportTestCasesRequest(body: Schema.GoogleCloudDialogflowCxV3ExportTestCasesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object `export` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `export` = new `export`(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/testCases:export").addQueryStringParameters("parent" -> parent.toString))
					}
					class batchDelete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3BatchDeleteTestCasesRequest(body: Schema.GoogleCloudDialogflowCxV3BatchDeleteTestCasesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object batchDelete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchDelete = new batchDelete(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/testCases:batchDelete").addQueryStringParameters("parent" -> parent.toString))
					}
					class batchRun(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3BatchRunTestCasesRequest(body: Schema.GoogleCloudDialogflowCxV3BatchRunTestCasesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object batchRun {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): batchRun = new batchRun(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/testCases:batchRun").addQueryStringParameters("parent" -> parent.toString))
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3TestCase]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3TestCase])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, testCasesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/testCases/${testCasesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3TestCase]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListTestCasesResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListTestCasesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String, pageSize: Int, pageToken: String, view: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/testCases").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "view" -> view.toString))
						given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListTestCasesResponse]] = (fun: list) => fun.apply()
					}
					class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3ImportTestCasesRequest(body: Schema.GoogleCloudDialogflowCxV3ImportTestCasesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/testCases:import").addQueryStringParameters("parent" -> parent.toString))
					}
					class calculateCoverage(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3CalculateCoverageResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3CalculateCoverageResponse])
					}
					object calculateCoverage {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, agent: String, `type`: String)(using auth: AuthToken, ec: ExecutionContext): calculateCoverage = new calculateCoverage(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/testCases:calculateCoverage").addQueryStringParameters("agent" -> agent.toString, "type" -> `type`.toString))
						given Conversion[calculateCoverage, Future[Schema.GoogleCloudDialogflowCxV3CalculateCoverageResponse]] = (fun: calculateCoverage) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3TestCase(body: Schema.GoogleCloudDialogflowCxV3TestCase) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3TestCase])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, testCasesId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/testCases/${testCasesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					object results {
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListTestCaseResultsResponse]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListTestCaseResultsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, testCasesId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/testCases/${testCasesId}/results").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
							given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListTestCaseResultsResponse]] = (fun: list) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3TestCaseResult]) {
							def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3TestCaseResult])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, testCasesId :PlayApi, resultsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/testCases/${testCasesId}/results/${resultsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3TestCaseResult]] = (fun: get) => fun.apply()
						}
					}
				}
				object entityTypes {
					class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3EntityType(body: Schema.GoogleCloudDialogflowCxV3EntityType) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3EntityType])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String, languageCode: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/entityTypes").addQueryStringParameters("parent" -> parent.toString, "languageCode" -> languageCode.toString))
					}
					class `export`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3ExportEntityTypesRequest(body: Schema.GoogleCloudDialogflowCxV3ExportEntityTypesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object `export` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `export` = new `export`(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/entityTypes:export").addQueryStringParameters("parent" -> parent.toString))
					}
					class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3ImportEntityTypesRequest(body: Schema.GoogleCloudDialogflowCxV3ImportEntityTypesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/entityTypes:import").addQueryStringParameters("parent" -> parent.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, entityTypesId :PlayApi, name: String, force: Boolean)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/entityTypes/${entityTypesId}").addQueryStringParameters("name" -> name.toString, "force" -> force.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3EntityType]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3EntityType])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, entityTypesId :PlayApi, name: String, languageCode: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/entityTypes/${entityTypesId}").addQueryStringParameters("name" -> name.toString, "languageCode" -> languageCode.toString))
						given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3EntityType]] = (fun: get) => fun.apply()
					}
					class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDialogflowCxV3EntityType(body: Schema.GoogleCloudDialogflowCxV3EntityType) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3EntityType])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, entityTypesId :PlayApi, name: String, languageCode: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/entityTypes/${entityTypesId}").addQueryStringParameters("name" -> name.toString, "languageCode" -> languageCode.toString, "updateMask" -> updateMask.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListEntityTypesResponse]) {
						def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListEntityTypesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String, languageCode: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/entityTypes").addQueryStringParameters("parent" -> parent.toString, "languageCode" -> languageCode.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListEntityTypesResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object securitySettings {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDialogflowCxV3SecuritySettings(body: Schema.GoogleCloudDialogflowCxV3SecuritySettings) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3SecuritySettings])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/securitySettings").addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, securitySettingsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/securitySettings/${securitySettingsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3SecuritySettings]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3SecuritySettings])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, securitySettingsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/securitySettings/${securitySettingsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3SecuritySettings]] = (fun: get) => fun.apply()
				}
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDialogflowCxV3SecuritySettings(body: Schema.GoogleCloudDialogflowCxV3SecuritySettings) = auth.exec(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3SecuritySettings])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, securitySettingsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/securitySettings/${securitySettingsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListSecuritySettingsResponse]) {
					def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListSecuritySettingsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/securitySettings").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListSecuritySettingsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
