package com.boresjo.play.api.google.dialogflow

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
		"""https://www.googleapis.com/auth/dialogflow""" /* View, manage and query your Dialogflow agents */
	)

	private val BASE_URL = "https://dialogflow.googleapis.com/"

	object projects {
		object operations {
			/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
			}
			/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object get {
				def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
			}
			/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
			class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object cancel {
				def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v3/projects/${projectsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
				given Conversion[cancel, Future[Schema.GoogleProtobufEmpty]] = (fun: cancel) => fun.apply()
			}
		}
		object locations {
			/** Gets information about a location. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudLocationLocation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudLocationLocation])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudLocationLocation]] = (fun: get) => fun.apply()
			}
			/** Lists information about the supported locations for this service. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudLocationListLocationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudLocationListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.GoogleCloudLocationListLocationsResponse]] = (fun: list) => fun.apply()
			}
			object operations {
				/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
				}
				/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
				}
				/** Starts asynchronous cancellation on a long-running operation. The server makes a best effort to cancel the operation, but success is not guaranteed. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use Operations.GetOperation or other methods to check whether the cancellation succeeded or whether the operation completed despite cancellation. On successful cancellation, the operation is not deleted; instead, it becomes an operation with an Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
				class cancel(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object cancel {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
					given Conversion[cancel, Future[Schema.GoogleProtobufEmpty]] = (fun: cancel) => fun.apply()
				}
			}
			object agents {
				/** Updates the generative settings for the agent. */
				class updateGenerativeSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
					/** Optional. The mask to control which fields get updated. If the mask is not present, all fields will be updated.<br>Format: google-fieldmask */
					def withUpdateMask(updateMask: String) = new updateGenerativeSettings(req.addQueryStringParameters("updateMask" -> updateMask.toString))
					/** Perform the request */
					def withGoogleCloudDialogflowCxV3GenerativeSettings(body: Schema.GoogleCloudDialogflowCxV3GenerativeSettings) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3GenerativeSettings])
				}
				object updateGenerativeSettings {
					def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): updateGenerativeSettings = new updateGenerativeSettings(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/generativeSettings").addQueryStringParameters("name" -> name.toString))
				}
				/** Restores the specified agent from a binary file. Replaces the current agent with a new one. Note that all existing resources in agent (e.g. intents, entity types, flows) will be removed. This method is a [long-running operation](https://cloud.google.com/dialogflow/cx/docs/how/long-running-operation). The returned `Operation` type has the following method-specific fields: - `metadata`: An empty [Struct message](https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#struct) - `response`: An [Empty message](https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#empty) Note: You should always train flows prior to sending them queries. See the [training documentation](https://cloud.google.com/dialogflow/cx/docs/concept/training). */
				class restore(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
					/** Perform the request */
					def withGoogleCloudDialogflowCxV3RestoreAgentRequest(body: Schema.GoogleCloudDialogflowCxV3RestoreAgentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object restore {
					def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): restore = new restore(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}:restore").addQueryStringParameters("name" -> name.toString))
				}
				/** Validates the specified agent and creates or updates validation results. The agent in draft version is validated. Please call this API after the training is completed to get the complete validation results. */
				class validate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
					/** Perform the request */
					def withGoogleCloudDialogflowCxV3ValidateAgentRequest(body: Schema.GoogleCloudDialogflowCxV3ValidateAgentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3AgentValidationResult])
				}
				object validate {
					def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): validate = new validate(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}:validate").addQueryStringParameters("name" -> name.toString))
				}
				/** Gets the generative settings for the agent. */
				class getGenerativeSettings(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3GenerativeSettings]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3GenerativeSettings])
				}
				object getGenerativeSettings {
					def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, name: String, languageCode: String)(using signer: RequestSigner, ec: ExecutionContext): getGenerativeSettings = new getGenerativeSettings(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/generativeSettings").addQueryStringParameters("name" -> name.toString, "languageCode" -> languageCode.toString))
					given Conversion[getGenerativeSettings, Future[Schema.GoogleCloudDialogflowCxV3GenerativeSettings]] = (fun: getGenerativeSettings) => fun.apply()
				}
				/** Exports the specified agent to a binary file. This method is a [long-running operation](https://cloud.google.com/dialogflow/cx/docs/how/long-running-operation). The returned `Operation` type has the following method-specific fields: - `metadata`: An empty [Struct message](https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#struct) - `response`: ExportAgentResponse */
				class `export`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
					/** Perform the request */
					def withGoogleCloudDialogflowCxV3ExportAgentRequest(body: Schema.GoogleCloudDialogflowCxV3ExportAgentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object `export` {
					def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): `export` = new `export`(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}:export").addQueryStringParameters("name" -> name.toString))
				}
				/** Gets the latest agent validation result. Agent validation is performed when ValidateAgent is called. */
				class getValidationResult(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3AgentValidationResult]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3AgentValidationResult])
				}
				object getValidationResult {
					def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, name: String, languageCode: String)(using signer: RequestSigner, ec: ExecutionContext): getValidationResult = new getValidationResult(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/validationResult").addQueryStringParameters("name" -> name.toString, "languageCode" -> languageCode.toString))
					given Conversion[getValidationResult, Future[Schema.GoogleCloudDialogflowCxV3AgentValidationResult]] = (fun: getValidationResult) => fun.apply()
				}
				/** Deletes the specified agent. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Updates the specified agent. Note: You should always train flows prior to sending them queries. See the [training documentation](https://cloud.google.com/dialogflow/cx/docs/concept/training). */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
					/** Perform the request */
					def withGoogleCloudDialogflowCxV3Agent(body: Schema.GoogleCloudDialogflowCxV3Agent) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Agent])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Creates an agent in the specified location. Note: You should always train flows prior to sending them queries. See the [training documentation](https://cloud.google.com/dialogflow/cx/docs/concept/training). */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
					/** Perform the request */
					def withGoogleCloudDialogflowCxV3Agent(body: Schema.GoogleCloudDialogflowCxV3Agent) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Agent])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Retrieves the specified agent. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3Agent]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Agent])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3Agent]] = (fun: get) => fun.apply()
				}
				/** Returns the list of all agents in the specified location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListAgentsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListAgentsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListAgentsResponse]] = (fun: list) => fun.apply()
				}
				object environments {
					/** Creates an Environment in the specified Agent. This method is a [long-running operation](https://cloud.google.com/dialogflow/cx/docs/how/long-running-operation). The returned `Operation` type has the following method-specific fields: - `metadata`: An empty [Struct message](https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#struct) - `response`: Environment */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3Environment(body: Schema.GoogleCloudDialogflowCxV3Environment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Kicks off a continuous test under the specified Environment. This method is a [long-running operation](https://cloud.google.com/dialogflow/cx/docs/how/long-running-operation). The returned `Operation` type has the following method-specific fields: - `metadata`: RunContinuousTestMetadata - `response`: RunContinuousTestResponse */
					class runContinuousTest(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3RunContinuousTestRequest(body: Schema.GoogleCloudDialogflowCxV3RunContinuousTestRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object runContinuousTest {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, environment: String)(using signer: RequestSigner, ec: ExecutionContext): runContinuousTest = new runContinuousTest(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}:runContinuousTest").addQueryStringParameters("environment" -> environment.toString))
					}
					/** Deletes the specified Environment. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					/** Retrieves the specified Environment. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3Environment]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Environment])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3Environment]] = (fun: get) => fun.apply()
					}
					/** Updates the specified Environment. This method is a [long-running operation](https://cloud.google.com/dialogflow/cx/docs/how/long-running-operation). The returned `Operation` type has the following method-specific fields: - `metadata`: An empty [Struct message](https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#struct) - `response`: Environment */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3Environment(body: Schema.GoogleCloudDialogflowCxV3Environment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Returns the list of all environments in the specified Agent. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListEnvironmentsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListEnvironmentsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListEnvironmentsResponse]] = (fun: list) => fun.apply()
					}
					/** Deploys a flow to the specified Environment. This method is a [long-running operation](https://cloud.google.com/dialogflow/cx/docs/how/long-running-operation). The returned `Operation` type has the following method-specific fields: - `metadata`: DeployFlowMetadata - `response`: DeployFlowResponse */
					class deployFlow(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3DeployFlowRequest(body: Schema.GoogleCloudDialogflowCxV3DeployFlowRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object deployFlow {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, environment: String)(using signer: RequestSigner, ec: ExecutionContext): deployFlow = new deployFlow(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}:deployFlow").addQueryStringParameters("environment" -> environment.toString))
					}
					/** Looks up the history of the specified Environment. */
					class lookupEnvironmentHistory(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3LookupEnvironmentHistoryResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3LookupEnvironmentHistoryResponse])
					}
					object lookupEnvironmentHistory {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, name: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): lookupEnvironmentHistory = new lookupEnvironmentHistory(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}:lookupEnvironmentHistory").addQueryStringParameters("name" -> name.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[lookupEnvironmentHistory, Future[Schema.GoogleCloudDialogflowCxV3LookupEnvironmentHistoryResponse]] = (fun: lookupEnvironmentHistory) => fun.apply()
					}
					object experiments {
						/** Creates an Experiment in the specified Environment. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def withGoogleCloudDialogflowCxV3Experiment(body: Schema.GoogleCloudDialogflowCxV3Experiment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Experiment])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/experiments").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Stops the specified Experiment. This rpc only changes the state of experiment from RUNNING to DONE. */
						class stop(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def withGoogleCloudDialogflowCxV3StopExperimentRequest(body: Schema.GoogleCloudDialogflowCxV3StopExperimentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Experiment])
						}
						object stop {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, experimentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): stop = new stop(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/experiments/${experimentsId}:stop").addQueryStringParameters("name" -> name.toString))
						}
						/** Deletes the specified Experiment. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, experimentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/experiments/${experimentsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						/** Retrieves the specified Experiment. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3Experiment]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Experiment])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, experimentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/experiments/${experimentsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3Experiment]] = (fun: get) => fun.apply()
						}
						/** Returns the list of all experiments in the specified Environment. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListExperimentsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListExperimentsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/experiments").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListExperimentsResponse]] = (fun: list) => fun.apply()
						}
						/** Updates the specified Experiment. */
						class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def withGoogleCloudDialogflowCxV3Experiment(body: Schema.GoogleCloudDialogflowCxV3Experiment) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Experiment])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, experimentsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/experiments/${experimentsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						/** Starts the specified Experiment. This rpc only changes the state of experiment from PENDING to RUNNING. */
						class start(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def withGoogleCloudDialogflowCxV3StartExperimentRequest(body: Schema.GoogleCloudDialogflowCxV3StartExperimentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Experiment])
						}
						object start {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, experimentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): start = new start(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/experiments/${experimentsId}:start").addQueryStringParameters("name" -> name.toString))
						}
					}
					object continuousTestResults {
						/** Fetches a list of continuous test results for a given environment. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListContinuousTestResultsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListContinuousTestResultsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/continuousTestResults").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListContinuousTestResultsResponse]] = (fun: list) => fun.apply()
						}
					}
					object sessions {
						/** Returns preliminary intent match results, doesn't change the session status. */
						class matchIntent(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def withGoogleCloudDialogflowCxV3MatchIntentRequest(body: Schema.GoogleCloudDialogflowCxV3MatchIntentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3MatchIntentResponse])
						}
						object matchIntent {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, sessionsId :PlayApi, session: String)(using signer: RequestSigner, ec: ExecutionContext): matchIntent = new matchIntent(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/sessions/${sessionsId}:matchIntent").addQueryStringParameters("session" -> session.toString))
						}
						/** Processes a natural language query and returns structured, actionable data as a result through server-side streaming. Server-side streaming allows Dialogflow to send [partial responses](https://cloud.google.com/dialogflow/cx/docs/concept/fulfillment#partial-response) earlier in a single request. */
						class serverStreamingDetectIntent(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def withGoogleCloudDialogflowCxV3DetectIntentRequest(body: Schema.GoogleCloudDialogflowCxV3DetectIntentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3DetectIntentResponse])
						}
						object serverStreamingDetectIntent {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, sessionsId :PlayApi, session: String)(using signer: RequestSigner, ec: ExecutionContext): serverStreamingDetectIntent = new serverStreamingDetectIntent(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/sessions/${sessionsId}:serverStreamingDetectIntent").addQueryStringParameters("session" -> session.toString))
						}
						/** Processes a natural language query and returns structured, actionable data as a result. This method is not idempotent, because it may cause session entity types to be updated, which in turn might affect results of future queries. Note: Always use agent versions for production traffic. See [Versions and environments](https://cloud.google.com/dialogflow/cx/docs/concept/version). */
						class detectIntent(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def withGoogleCloudDialogflowCxV3DetectIntentRequest(body: Schema.GoogleCloudDialogflowCxV3DetectIntentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3DetectIntentResponse])
						}
						object detectIntent {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, sessionsId :PlayApi, session: String)(using signer: RequestSigner, ec: ExecutionContext): detectIntent = new detectIntent(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/sessions/${sessionsId}:detectIntent").addQueryStringParameters("session" -> session.toString))
						}
						/** Fulfills a matched intent returned by MatchIntent. Must be called after MatchIntent, with input from MatchIntentResponse. Otherwise, the behavior is undefined. */
						class fulfillIntent(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def withGoogleCloudDialogflowCxV3FulfillIntentRequest(body: Schema.GoogleCloudDialogflowCxV3FulfillIntentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3FulfillIntentResponse])
						}
						object fulfillIntent {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, sessionsId :PlayApi, session: String)(using signer: RequestSigner, ec: ExecutionContext): fulfillIntent = new fulfillIntent(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/sessions/${sessionsId}:fulfillIntent").addQueryStringParameters("session" -> session.toString))
						}
						object entityTypes {
							/** Creates a session entity type. */
							class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
								/** Perform the request */
								def withGoogleCloudDialogflowCxV3SessionEntityType(body: Schema.GoogleCloudDialogflowCxV3SessionEntityType) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3SessionEntityType])
							}
							object create {
								def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, sessionsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/sessions/${sessionsId}/entityTypes").addQueryStringParameters("parent" -> parent.toString))
							}
							/** Deletes the specified session entity type. */
							class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
								/** Perform the request */
								def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
							}
							object delete {
								def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, sessionsId :PlayApi, entityTypesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/sessions/${sessionsId}/entityTypes/${entityTypesId}").addQueryStringParameters("name" -> name.toString))
								given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
							}
							/** Retrieves the specified session entity type. */
							class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3SessionEntityType]) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
								/** Perform the request */
								def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3SessionEntityType])
							}
							object get {
								def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, sessionsId :PlayApi, entityTypesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/sessions/${sessionsId}/entityTypes/${entityTypesId}").addQueryStringParameters("name" -> name.toString))
								given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3SessionEntityType]] = (fun: get) => fun.apply()
							}
							/** Updates the specified session entity type. */
							class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
								/** Perform the request */
								def withGoogleCloudDialogflowCxV3SessionEntityType(body: Schema.GoogleCloudDialogflowCxV3SessionEntityType) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3SessionEntityType])
							}
							object patch {
								def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, sessionsId :PlayApi, entityTypesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/sessions/${sessionsId}/entityTypes/${entityTypesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
							}
							/** Returns the list of all session entity types in the specified session. */
							class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListSessionEntityTypesResponse]) {
								val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
								/** Perform the request */
								def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListSessionEntityTypesResponse])
							}
							object list {
								def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, sessionsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/sessions/${sessionsId}/entityTypes").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
								given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListSessionEntityTypesResponse]] = (fun: list) => fun.apply()
							}
						}
					}
					object deployments {
						/** Returns the list of all deployments in the specified Environment. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListDeploymentsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListDeploymentsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/deployments").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListDeploymentsResponse]] = (fun: list) => fun.apply()
						}
						/** Retrieves the specified Deployment. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3Deployment]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Deployment])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, environmentsId :PlayApi, deploymentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/environments/${environmentsId}/deployments/${deploymentsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3Deployment]] = (fun: get) => fun.apply()
						}
					}
				}
				object changelogs {
					/** Returns the list of Changelogs. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListChangelogsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListChangelogsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String, filter: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/changelogs").addQueryStringParameters("parent" -> parent.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListChangelogsResponse]] = (fun: list) => fun.apply()
					}
					/** Retrieves the specified Changelog. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3Changelog]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Changelog])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, changelogsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/changelogs/${changelogsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3Changelog]] = (fun: get) => fun.apply()
					}
				}
				object webhooks {
					/** Creates a webhook in the specified agent. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3Webhook(body: Schema.GoogleCloudDialogflowCxV3Webhook) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Webhook])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/webhooks").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Deletes the specified webhook. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, webhooksId :PlayApi, name: String, force: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/webhooks/${webhooksId}").addQueryStringParameters("name" -> name.toString, "force" -> force.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					/** Retrieves the specified webhook. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3Webhook]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Webhook])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, webhooksId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/webhooks/${webhooksId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3Webhook]] = (fun: get) => fun.apply()
					}
					/** Updates the specified webhook. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3Webhook(body: Schema.GoogleCloudDialogflowCxV3Webhook) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Webhook])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, webhooksId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/webhooks/${webhooksId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					/** Returns the list of all webhooks in the specified agent. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListWebhooksResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListWebhooksResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/webhooks").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListWebhooksResponse]] = (fun: list) => fun.apply()
					}
				}
				object generators {
					/** Creates a generator in the specified agent. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3Generator(body: Schema.GoogleCloudDialogflowCxV3Generator) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Generator])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String, languageCode: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/generators").addQueryStringParameters("parent" -> parent.toString, "languageCode" -> languageCode.toString))
					}
					/** Deletes the specified generators. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, generatorsId :PlayApi, name: String, force: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/generators/${generatorsId}").addQueryStringParameters("name" -> name.toString, "force" -> force.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					/** Retrieves the specified generator. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3Generator]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Generator])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, generatorsId :PlayApi, name: String, languageCode: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/generators/${generatorsId}").addQueryStringParameters("name" -> name.toString, "languageCode" -> languageCode.toString))
						given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3Generator]] = (fun: get) => fun.apply()
					}
					/** Update the specified generator. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3Generator(body: Schema.GoogleCloudDialogflowCxV3Generator) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Generator])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, generatorsId :PlayApi, name: String, languageCode: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/generators/${generatorsId}").addQueryStringParameters("name" -> name.toString, "languageCode" -> languageCode.toString, "updateMask" -> updateMask.toString))
					}
					/** Returns the list of all generators in the specified agent. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListGeneratorsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListGeneratorsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String, languageCode: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/generators").addQueryStringParameters("parent" -> parent.toString, "languageCode" -> languageCode.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListGeneratorsResponse]] = (fun: list) => fun.apply()
					}
				}
				object intents {
					/** Creates an intent in the specified agent. Note: You should always train a flow prior to sending it queries. See the [training documentation](https://cloud.google.com/dialogflow/cx/docs/concept/training). */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3Intent(body: Schema.GoogleCloudDialogflowCxV3Intent) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Intent])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String, languageCode: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/intents").addQueryStringParameters("parent" -> parent.toString, "languageCode" -> languageCode.toString))
					}
					/** Exports the selected intents. This method is a [long-running operation](https://cloud.google.com/dialogflow/cx/docs/how/long-running-operation). The returned `Operation` type has the following method-specific fields: - `metadata`: ExportIntentsMetadata - `response`: ExportIntentsResponse */
					class `export`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3ExportIntentsRequest(body: Schema.GoogleCloudDialogflowCxV3ExportIntentsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object `export` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): `export` = new `export`(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/intents:export").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Imports the specified intents into the agent. This method is a [long-running operation](https://cloud.google.com/dialogflow/cx/docs/how/long-running-operation). The returned `Operation` type has the following method-specific fields: - `metadata`: ImportIntentsMetadata - `response`: ImportIntentsResponse */
					class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3ImportIntentsRequest(body: Schema.GoogleCloudDialogflowCxV3ImportIntentsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/intents:import").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Deletes the specified intent. Note: You should always train a flow prior to sending it queries. See the [training documentation](https://cloud.google.com/dialogflow/cx/docs/concept/training). */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, intentsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/intents/${intentsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					/** Retrieves the specified intent. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3Intent]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Intent])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, intentsId :PlayApi, name: String, languageCode: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/intents/${intentsId}").addQueryStringParameters("name" -> name.toString, "languageCode" -> languageCode.toString))
						given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3Intent]] = (fun: get) => fun.apply()
					}
					/** Updates the specified intent. Note: You should always train a flow prior to sending it queries. See the [training documentation](https://cloud.google.com/dialogflow/cx/docs/concept/training). */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3Intent(body: Schema.GoogleCloudDialogflowCxV3Intent) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Intent])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, intentsId :PlayApi, name: String, languageCode: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/intents/${intentsId}").addQueryStringParameters("name" -> name.toString, "languageCode" -> languageCode.toString, "updateMask" -> updateMask.toString))
					}
					/** Returns the list of all intents in the specified agent. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListIntentsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListIntentsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String, languageCode: String, intentView: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/intents").addQueryStringParameters("parent" -> parent.toString, "languageCode" -> languageCode.toString, "intentView" -> intentView.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListIntentsResponse]] = (fun: list) => fun.apply()
					}
				}
				object transitionRouteGroups {
					/** Creates an TransitionRouteGroup in the specified flow. Note: You should always train a flow prior to sending it queries. See the [training documentation](https://cloud.google.com/dialogflow/cx/docs/concept/training). */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3TransitionRouteGroup(body: Schema.GoogleCloudDialogflowCxV3TransitionRouteGroup) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3TransitionRouteGroup])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String, languageCode: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/transitionRouteGroups").addQueryStringParameters("parent" -> parent.toString, "languageCode" -> languageCode.toString))
					}
					/** Deletes the specified TransitionRouteGroup. Note: You should always train a flow prior to sending it queries. See the [training documentation](https://cloud.google.com/dialogflow/cx/docs/concept/training). */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, transitionRouteGroupsId :PlayApi, name: String, force: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/transitionRouteGroups/${transitionRouteGroupsId}").addQueryStringParameters("name" -> name.toString, "force" -> force.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					/** Retrieves the specified TransitionRouteGroup. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3TransitionRouteGroup]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3TransitionRouteGroup])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, transitionRouteGroupsId :PlayApi, name: String, languageCode: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/transitionRouteGroups/${transitionRouteGroupsId}").addQueryStringParameters("name" -> name.toString, "languageCode" -> languageCode.toString))
						given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3TransitionRouteGroup]] = (fun: get) => fun.apply()
					}
					/** Updates the specified TransitionRouteGroup. Note: You should always train a flow prior to sending it queries. See the [training documentation](https://cloud.google.com/dialogflow/cx/docs/concept/training). */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3TransitionRouteGroup(body: Schema.GoogleCloudDialogflowCxV3TransitionRouteGroup) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3TransitionRouteGroup])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, transitionRouteGroupsId :PlayApi, name: String, updateMask: String, languageCode: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/transitionRouteGroups/${transitionRouteGroupsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "languageCode" -> languageCode.toString))
					}
					/** Returns the list of all transition route groups in the specified flow. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListTransitionRouteGroupsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListTransitionRouteGroupsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String, pageSize: Int, pageToken: String, languageCode: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/transitionRouteGroups").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "languageCode" -> languageCode.toString))
						given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListTransitionRouteGroupsResponse]] = (fun: list) => fun.apply()
					}
				}
				object flows {
					/** Validates the specified flow and creates or updates validation results. Please call this API after the training is completed to get the complete validation results. */
					class validate(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3ValidateFlowRequest(body: Schema.GoogleCloudDialogflowCxV3ValidateFlowRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3FlowValidationResult])
					}
					object validate {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): validate = new validate(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}:validate").addQueryStringParameters("name" -> name.toString))
					}
					/** Exports the specified flow to a binary file. This method is a [long-running operation](https://cloud.google.com/dialogflow/cx/docs/how/long-running-operation). The returned `Operation` type has the following method-specific fields: - `metadata`: An empty [Struct message](https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#struct) - `response`: ExportFlowResponse Note that resources (e.g. intents, entities, webhooks) that the flow references will also be exported. */
					class `export`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3ExportFlowRequest(body: Schema.GoogleCloudDialogflowCxV3ExportFlowRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object `export` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): `export` = new `export`(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}:export").addQueryStringParameters("name" -> name.toString))
					}
					/** Deletes a specified flow. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, name: String, force: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}").addQueryStringParameters("name" -> name.toString, "force" -> force.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					/** Updates the specified flow. Note: You should always train a flow prior to sending it queries. See the [training documentation](https://cloud.google.com/dialogflow/cx/docs/concept/training). */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3Flow(body: Schema.GoogleCloudDialogflowCxV3Flow) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Flow])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, name: String, updateMask: String, languageCode: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "languageCode" -> languageCode.toString))
					}
					/** Returns the list of all flows in the specified agent. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListFlowsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListFlowsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String, pageSize: Int, pageToken: String, languageCode: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "languageCode" -> languageCode.toString))
						given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListFlowsResponse]] = (fun: list) => fun.apply()
					}
					/** Creates a flow in the specified agent. Note: You should always train a flow prior to sending it queries. See the [training documentation](https://cloud.google.com/dialogflow/cx/docs/concept/training). */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3Flow(body: Schema.GoogleCloudDialogflowCxV3Flow) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Flow])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String, languageCode: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows").addQueryStringParameters("parent" -> parent.toString, "languageCode" -> languageCode.toString))
					}
					/** Gets the latest flow validation result. Flow validation is performed when ValidateFlow is called. */
					class getValidationResult(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3FlowValidationResult]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3FlowValidationResult])
					}
					object getValidationResult {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, name: String, languageCode: String)(using signer: RequestSigner, ec: ExecutionContext): getValidationResult = new getValidationResult(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/validationResult").addQueryStringParameters("name" -> name.toString, "languageCode" -> languageCode.toString))
						given Conversion[getValidationResult, Future[Schema.GoogleCloudDialogflowCxV3FlowValidationResult]] = (fun: getValidationResult) => fun.apply()
					}
					/** Imports the specified flow to the specified agent from a binary file. This method is a [long-running operation](https://cloud.google.com/dialogflow/cx/docs/how/long-running-operation). The returned `Operation` type has the following method-specific fields: - `metadata`: An empty [Struct message](https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#struct) - `response`: ImportFlowResponse Note: You should always train a flow prior to sending it queries. See the [training documentation](https://cloud.google.com/dialogflow/cx/docs/concept/training). */
					class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3ImportFlowRequest(body: Schema.GoogleCloudDialogflowCxV3ImportFlowRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows:import").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Retrieves the specified flow. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3Flow]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Flow])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, name: String, languageCode: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}").addQueryStringParameters("name" -> name.toString, "languageCode" -> languageCode.toString))
						given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3Flow]] = (fun: get) => fun.apply()
					}
					/** Trains the specified flow. Note that only the flow in 'draft' environment is trained. This method is a [long-running operation](https://cloud.google.com/dialogflow/cx/docs/how/long-running-operation). The returned `Operation` type has the following method-specific fields: - `metadata`: An empty [Struct message](https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#struct) - `response`: An [Empty message](https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#empty) Note: You should always train a flow prior to sending it queries. See the [training documentation](https://cloud.google.com/dialogflow/cx/docs/concept/training). */
					class train(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3TrainFlowRequest(body: Schema.GoogleCloudDialogflowCxV3TrainFlowRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object train {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): train = new train(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}:train").addQueryStringParameters("name" -> name.toString))
					}
					object versions {
						/** Creates a Version in the specified Flow. This method is a [long-running operation](https://cloud.google.com/dialogflow/cx/docs/how/long-running-operation). The returned `Operation` type has the following method-specific fields: - `metadata`: CreateVersionOperationMetadata - `response`: Version */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def withGoogleCloudDialogflowCxV3Version(body: Schema.GoogleCloudDialogflowCxV3Version) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/versions").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Deletes the specified Version. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, versionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/versions/${versionsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						/** Retrieves the specified Version. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3Version]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Version])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, versionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/versions/${versionsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3Version]] = (fun: get) => fun.apply()
						}
						/** Updates the specified Version. */
						class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def withGoogleCloudDialogflowCxV3Version(body: Schema.GoogleCloudDialogflowCxV3Version) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Version])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, versionsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/versions/${versionsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						/** Returns the list of all versions in the specified Flow. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListVersionsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListVersionsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/versions").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListVersionsResponse]] = (fun: list) => fun.apply()
						}
						/** Loads resources in the specified version to the draft flow. This method is a [long-running operation](https://cloud.google.com/dialogflow/cx/docs/how/long-running-operation). The returned `Operation` type has the following method-specific fields: - `metadata`: An empty [Struct message](https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#struct) - `response`: An [Empty message](https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#empty) */
						class load(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def withGoogleCloudDialogflowCxV3LoadVersionRequest(body: Schema.GoogleCloudDialogflowCxV3LoadVersionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
						}
						object load {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, versionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): load = new load(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/versions/${versionsId}:load").addQueryStringParameters("name" -> name.toString))
						}
						/** Compares the specified base version with target version. */
						class compareVersions(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def withGoogleCloudDialogflowCxV3CompareVersionsRequest(body: Schema.GoogleCloudDialogflowCxV3CompareVersionsRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3CompareVersionsResponse])
						}
						object compareVersions {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, versionsId :PlayApi, baseVersion: String)(using signer: RequestSigner, ec: ExecutionContext): compareVersions = new compareVersions(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/versions/${versionsId}:compareVersions").addQueryStringParameters("baseVersion" -> baseVersion.toString))
						}
					}
					object transitionRouteGroups {
						/** Creates an TransitionRouteGroup in the specified flow. Note: You should always train a flow prior to sending it queries. See the [training documentation](https://cloud.google.com/dialogflow/cx/docs/concept/training). */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def withGoogleCloudDialogflowCxV3TransitionRouteGroup(body: Schema.GoogleCloudDialogflowCxV3TransitionRouteGroup) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3TransitionRouteGroup])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, parent: String, languageCode: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/transitionRouteGroups").addQueryStringParameters("parent" -> parent.toString, "languageCode" -> languageCode.toString))
						}
						/** Deletes the specified TransitionRouteGroup. Note: You should always train a flow prior to sending it queries. See the [training documentation](https://cloud.google.com/dialogflow/cx/docs/concept/training). */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, transitionRouteGroupsId :PlayApi, name: String, force: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/transitionRouteGroups/${transitionRouteGroupsId}").addQueryStringParameters("name" -> name.toString, "force" -> force.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						/** Retrieves the specified TransitionRouteGroup. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3TransitionRouteGroup]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3TransitionRouteGroup])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, transitionRouteGroupsId :PlayApi, name: String, languageCode: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/transitionRouteGroups/${transitionRouteGroupsId}").addQueryStringParameters("name" -> name.toString, "languageCode" -> languageCode.toString))
							given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3TransitionRouteGroup]] = (fun: get) => fun.apply()
						}
						/** Updates the specified TransitionRouteGroup. Note: You should always train a flow prior to sending it queries. See the [training documentation](https://cloud.google.com/dialogflow/cx/docs/concept/training). */
						class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def withGoogleCloudDialogflowCxV3TransitionRouteGroup(body: Schema.GoogleCloudDialogflowCxV3TransitionRouteGroup) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3TransitionRouteGroup])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, transitionRouteGroupsId :PlayApi, name: String, updateMask: String, languageCode: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/transitionRouteGroups/${transitionRouteGroupsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString, "languageCode" -> languageCode.toString))
						}
						/** Returns the list of all transition route groups in the specified flow. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListTransitionRouteGroupsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListTransitionRouteGroupsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, parent: String, pageSize: Int, pageToken: String, languageCode: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/transitionRouteGroups").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "languageCode" -> languageCode.toString))
							given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListTransitionRouteGroupsResponse]] = (fun: list) => fun.apply()
						}
					}
					object pages {
						/** Creates a page in the specified flow. Note: You should always train a flow prior to sending it queries. See the [training documentation](https://cloud.google.com/dialogflow/cx/docs/concept/training). */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def withGoogleCloudDialogflowCxV3Page(body: Schema.GoogleCloudDialogflowCxV3Page) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Page])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, parent: String, languageCode: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/pages").addQueryStringParameters("parent" -> parent.toString, "languageCode" -> languageCode.toString))
						}
						/** Deletes the specified page. Note: You should always train a flow prior to sending it queries. See the [training documentation](https://cloud.google.com/dialogflow/cx/docs/concept/training). */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, pagesId :PlayApi, name: String, force: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/pages/${pagesId}").addQueryStringParameters("name" -> name.toString, "force" -> force.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						/** Retrieves the specified page. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3Page]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Page])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, pagesId :PlayApi, name: String, languageCode: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/pages/${pagesId}").addQueryStringParameters("name" -> name.toString, "languageCode" -> languageCode.toString))
							given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3Page]] = (fun: get) => fun.apply()
						}
						/** Updates the specified page. Note: You should always train a flow prior to sending it queries. See the [training documentation](https://cloud.google.com/dialogflow/cx/docs/concept/training). */
						class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def withGoogleCloudDialogflowCxV3Page(body: Schema.GoogleCloudDialogflowCxV3Page) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3Page])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, pagesId :PlayApi, name: String, languageCode: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/pages/${pagesId}").addQueryStringParameters("name" -> name.toString, "languageCode" -> languageCode.toString, "updateMask" -> updateMask.toString))
						}
						/** Returns the list of all pages in the specified flow. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListPagesResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListPagesResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, flowsId :PlayApi, parent: String, languageCode: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/flows/${flowsId}/pages").addQueryStringParameters("parent" -> parent.toString, "languageCode" -> languageCode.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListPagesResponse]] = (fun: list) => fun.apply()
						}
					}
				}
				object sessions {
					/** Returns preliminary intent match results, doesn't change the session status. */
					class matchIntent(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3MatchIntentRequest(body: Schema.GoogleCloudDialogflowCxV3MatchIntentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3MatchIntentResponse])
					}
					object matchIntent {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, sessionsId :PlayApi, session: String)(using signer: RequestSigner, ec: ExecutionContext): matchIntent = new matchIntent(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/sessions/${sessionsId}:matchIntent").addQueryStringParameters("session" -> session.toString))
					}
					/** Updates the feedback received from the user for a single turn of the bot response. */
					class submitAnswerFeedback(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3SubmitAnswerFeedbackRequest(body: Schema.GoogleCloudDialogflowCxV3SubmitAnswerFeedbackRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3AnswerFeedback])
					}
					object submitAnswerFeedback {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, sessionsId :PlayApi, session: String)(using signer: RequestSigner, ec: ExecutionContext): submitAnswerFeedback = new submitAnswerFeedback(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/sessions/${sessionsId}:submitAnswerFeedback").addQueryStringParameters("session" -> session.toString))
					}
					/** Processes a natural language query and returns structured, actionable data as a result through server-side streaming. Server-side streaming allows Dialogflow to send [partial responses](https://cloud.google.com/dialogflow/cx/docs/concept/fulfillment#partial-response) earlier in a single request. */
					class serverStreamingDetectIntent(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3DetectIntentRequest(body: Schema.GoogleCloudDialogflowCxV3DetectIntentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3DetectIntentResponse])
					}
					object serverStreamingDetectIntent {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, sessionsId :PlayApi, session: String)(using signer: RequestSigner, ec: ExecutionContext): serverStreamingDetectIntent = new serverStreamingDetectIntent(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/sessions/${sessionsId}:serverStreamingDetectIntent").addQueryStringParameters("session" -> session.toString))
					}
					/** Processes a natural language query and returns structured, actionable data as a result. This method is not idempotent, because it may cause session entity types to be updated, which in turn might affect results of future queries. Note: Always use agent versions for production traffic. See [Versions and environments](https://cloud.google.com/dialogflow/cx/docs/concept/version). */
					class detectIntent(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3DetectIntentRequest(body: Schema.GoogleCloudDialogflowCxV3DetectIntentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3DetectIntentResponse])
					}
					object detectIntent {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, sessionsId :PlayApi, session: String)(using signer: RequestSigner, ec: ExecutionContext): detectIntent = new detectIntent(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/sessions/${sessionsId}:detectIntent").addQueryStringParameters("session" -> session.toString))
					}
					/** Fulfills a matched intent returned by MatchIntent. Must be called after MatchIntent, with input from MatchIntentResponse. Otherwise, the behavior is undefined. */
					class fulfillIntent(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3FulfillIntentRequest(body: Schema.GoogleCloudDialogflowCxV3FulfillIntentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3FulfillIntentResponse])
					}
					object fulfillIntent {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, sessionsId :PlayApi, session: String)(using signer: RequestSigner, ec: ExecutionContext): fulfillIntent = new fulfillIntent(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/sessions/${sessionsId}:fulfillIntent").addQueryStringParameters("session" -> session.toString))
					}
					object entityTypes {
						/** Creates a session entity type. */
						class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def withGoogleCloudDialogflowCxV3SessionEntityType(body: Schema.GoogleCloudDialogflowCxV3SessionEntityType) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3SessionEntityType])
						}
						object create {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, sessionsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/sessions/${sessionsId}/entityTypes").addQueryStringParameters("parent" -> parent.toString))
						}
						/** Deletes the specified session entity type. */
						class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
						}
						object delete {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, sessionsId :PlayApi, entityTypesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/sessions/${sessionsId}/entityTypes/${entityTypesId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
						}
						/** Retrieves the specified session entity type. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3SessionEntityType]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3SessionEntityType])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, sessionsId :PlayApi, entityTypesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/sessions/${sessionsId}/entityTypes/${entityTypesId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3SessionEntityType]] = (fun: get) => fun.apply()
						}
						/** Updates the specified session entity type. */
						class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def withGoogleCloudDialogflowCxV3SessionEntityType(body: Schema.GoogleCloudDialogflowCxV3SessionEntityType) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3SessionEntityType])
						}
						object patch {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, sessionsId :PlayApi, entityTypesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/sessions/${sessionsId}/entityTypes/${entityTypesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
						}
						/** Returns the list of all session entity types in the specified session. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListSessionEntityTypesResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListSessionEntityTypesResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, sessionsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/sessions/${sessionsId}/entityTypes").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListSessionEntityTypesResponse]] = (fun: list) => fun.apply()
						}
					}
				}
				object testCases {
					/** Kicks off a test case run. This method is a [long-running operation](https://cloud.google.com/dialogflow/cx/docs/how/long-running-operation). The returned `Operation` type has the following method-specific fields: - `metadata`: RunTestCaseMetadata - `response`: RunTestCaseResponse */
					class run(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3RunTestCaseRequest(body: Schema.GoogleCloudDialogflowCxV3RunTestCaseRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object run {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, testCasesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): run = new run(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/testCases/${testCasesId}:run").addQueryStringParameters("name" -> name.toString))
					}
					/** Creates a test case for the given agent. */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3TestCase(body: Schema.GoogleCloudDialogflowCxV3TestCase) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3TestCase])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/testCases").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Exports the test cases under the agent to a Cloud Storage bucket or a local file. Filter can be applied to export a subset of test cases. This method is a [long-running operation](https://cloud.google.com/dialogflow/cx/docs/how/long-running-operation). The returned `Operation` type has the following method-specific fields: - `metadata`: ExportTestCasesMetadata - `response`: ExportTestCasesResponse */
					class `export`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3ExportTestCasesRequest(body: Schema.GoogleCloudDialogflowCxV3ExportTestCasesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object `export` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): `export` = new `export`(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/testCases:export").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Batch deletes test cases. */
					class batchDelete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3BatchDeleteTestCasesRequest(body: Schema.GoogleCloudDialogflowCxV3BatchDeleteTestCasesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object batchDelete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): batchDelete = new batchDelete(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/testCases:batchDelete").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Kicks off a batch run of test cases. This method is a [long-running operation](https://cloud.google.com/dialogflow/cx/docs/how/long-running-operation). The returned `Operation` type has the following method-specific fields: - `metadata`: BatchRunTestCasesMetadata - `response`: BatchRunTestCasesResponse */
					class batchRun(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3BatchRunTestCasesRequest(body: Schema.GoogleCloudDialogflowCxV3BatchRunTestCasesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object batchRun {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): batchRun = new batchRun(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/testCases:batchRun").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Gets a test case. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3TestCase]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3TestCase])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, testCasesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/testCases/${testCasesId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3TestCase]] = (fun: get) => fun.apply()
					}
					/** Fetches a list of test cases for a given agent. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListTestCasesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListTestCasesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String, pageSize: Int, pageToken: String, view: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/testCases").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "view" -> view.toString))
						given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListTestCasesResponse]] = (fun: list) => fun.apply()
					}
					/** Imports the test cases from a Cloud Storage bucket or a local file. It always creates new test cases and won't overwrite any existing ones. The provided ID in the imported test case is neglected. This method is a [long-running operation](https://cloud.google.com/dialogflow/cx/docs/how/long-running-operation). The returned `Operation` type has the following method-specific fields: - `metadata`: ImportTestCasesMetadata - `response`: ImportTestCasesResponse */
					class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3ImportTestCasesRequest(body: Schema.GoogleCloudDialogflowCxV3ImportTestCasesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/testCases:import").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Calculates the test coverage for an agent. */
					class calculateCoverage(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3CalculateCoverageResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3CalculateCoverageResponse])
					}
					object calculateCoverage {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, agent: String, `type`: String)(using signer: RequestSigner, ec: ExecutionContext): calculateCoverage = new calculateCoverage(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/testCases:calculateCoverage").addQueryStringParameters("agent" -> agent.toString, "type" -> `type`.toString))
						given Conversion[calculateCoverage, Future[Schema.GoogleCloudDialogflowCxV3CalculateCoverageResponse]] = (fun: calculateCoverage) => fun.apply()
					}
					/** Updates the specified test case. */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3TestCase(body: Schema.GoogleCloudDialogflowCxV3TestCase) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3TestCase])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, testCasesId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/testCases/${testCasesId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
					}
					object results {
						/** Fetches the list of run results for the given test case. A maximum of 100 results are kept for each test case. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListTestCaseResultsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListTestCaseResultsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, testCasesId :PlayApi, parent: String, pageSize: Int, pageToken: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/testCases/${testCasesId}/results").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
							given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListTestCaseResultsResponse]] = (fun: list) => fun.apply()
						}
						/** Gets a test case result. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3TestCaseResult]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3TestCaseResult])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, testCasesId :PlayApi, resultsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/testCases/${testCasesId}/results/${resultsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3TestCaseResult]] = (fun: get) => fun.apply()
						}
					}
				}
				object entityTypes {
					/** Creates an entity type in the specified agent. Note: You should always train a flow prior to sending it queries. See the [training documentation](https://cloud.google.com/dialogflow/cx/docs/concept/training). */
					class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3EntityType(body: Schema.GoogleCloudDialogflowCxV3EntityType) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3EntityType])
					}
					object create {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String, languageCode: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/entityTypes").addQueryStringParameters("parent" -> parent.toString, "languageCode" -> languageCode.toString))
					}
					/** Exports the selected entity types. */
					class `export`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3ExportEntityTypesRequest(body: Schema.GoogleCloudDialogflowCxV3ExportEntityTypesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object `export` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): `export` = new `export`(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/entityTypes:export").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Imports the specified entitytypes into the agent. */
					class `import`(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3ImportEntityTypesRequest(body: Schema.GoogleCloudDialogflowCxV3ImportEntityTypesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object `import` {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/entityTypes:import").addQueryStringParameters("parent" -> parent.toString))
					}
					/** Deletes the specified entity type. Note: You should always train a flow prior to sending it queries. See the [training documentation](https://cloud.google.com/dialogflow/cx/docs/concept/training). */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, entityTypesId :PlayApi, name: String, force: Boolean)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/entityTypes/${entityTypesId}").addQueryStringParameters("name" -> name.toString, "force" -> force.toString))
						given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
					}
					/** Retrieves the specified entity type. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3EntityType]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3EntityType])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, entityTypesId :PlayApi, name: String, languageCode: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/entityTypes/${entityTypesId}").addQueryStringParameters("name" -> name.toString, "languageCode" -> languageCode.toString))
						given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3EntityType]] = (fun: get) => fun.apply()
					}
					/** Updates the specified entity type. Note: You should always train a flow prior to sending it queries. See the [training documentation](https://cloud.google.com/dialogflow/cx/docs/concept/training). */
					class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def withGoogleCloudDialogflowCxV3EntityType(body: Schema.GoogleCloudDialogflowCxV3EntityType) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3EntityType])
					}
					object patch {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, entityTypesId :PlayApi, name: String, languageCode: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/entityTypes/${entityTypesId}").addQueryStringParameters("name" -> name.toString, "languageCode" -> languageCode.toString, "updateMask" -> updateMask.toString))
					}
					/** Returns the list of all entity types in the specified agent. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListEntityTypesResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListEntityTypesResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, agentsId :PlayApi, parent: String, languageCode: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/agents/${agentsId}/entityTypes").addQueryStringParameters("parent" -> parent.toString, "languageCode" -> languageCode.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListEntityTypesResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object securitySettings {
				/** Create security settings in the specified location. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
					/** Perform the request */
					def withGoogleCloudDialogflowCxV3SecuritySettings(body: Schema.GoogleCloudDialogflowCxV3SecuritySettings) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3SecuritySettings])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/securitySettings").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes the specified SecuritySettings. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, securitySettingsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/securitySettings/${securitySettingsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
				}
				/** Retrieves the specified SecuritySettings. The returned settings may be stale by up to 1 minute. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3SecuritySettings]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3SecuritySettings])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, securitySettingsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/securitySettings/${securitySettingsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudDialogflowCxV3SecuritySettings]] = (fun: get) => fun.apply()
				}
				/** Updates the specified SecuritySettings. */
				class patch(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
					/** Perform the request */
					def withGoogleCloudDialogflowCxV3SecuritySettings(body: Schema.GoogleCloudDialogflowCxV3SecuritySettings) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3SecuritySettings])
				}
				object patch {
					def apply(projectsId :PlayApi, locationsId :PlayApi, securitySettingsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): patch = new patch(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/securitySettings/${securitySettingsId}").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
				}
				/** Returns the list of all security settings in the specified location. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDialogflowCxV3ListSecuritySettingsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""", """https://www.googleapis.com/auth/dialogflow""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDialogflowCxV3ListSecuritySettingsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageSize: Int, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v3/projects/${projectsId}/locations/${locationsId}/securitySettings").addQueryStringParameters("parent" -> parent.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleCloudDialogflowCxV3ListSecuritySettingsResponse]] = (fun: list) => fun.apply()
				}
			}
		}
	}
}
