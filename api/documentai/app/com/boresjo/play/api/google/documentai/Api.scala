package com.boresjo.play.api.google.documentai

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

	private val BASE_URL = "https://documentai.googleapis.com/"

	object projects {
		object locations {
			/** Gets information about a location. */
			class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudLocationLocation]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudLocationLocation])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudLocationLocation]] = (fun: get) => fun.apply()
			}
			/** Fetches processor types. Note that we don't use ListProcessorTypes here, because it isn't paginated. */
			class fetchProcessorTypes(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDocumentaiV1FetchProcessorTypesResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDocumentaiV1FetchProcessorTypesResponse])
			}
			object fetchProcessorTypes {
				def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): fetchProcessorTypes = new fetchProcessorTypes(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:fetchProcessorTypes").addQueryStringParameters("parent" -> parent.toString))
				given Conversion[fetchProcessorTypes, Future[Schema.GoogleCloudDocumentaiV1FetchProcessorTypesResponse]] = (fun: fetchProcessorTypes) => fun.apply()
			}
			/** Lists information about the supported locations for this service. */
			class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudLocationListLocationsResponse]) {
				val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
				/** Perform the request */
				def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudLocationListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.GoogleCloudLocationListLocationsResponse]] = (fun: list) => fun.apply()
			}
			object processorTypes {
				/** Lists the processor types that exist. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDocumentaiV1ListProcessorTypesResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDocumentaiV1ListProcessorTypesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, pageSize: Int, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processorTypes").addQueryStringParameters("pageSize" -> pageSize.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleCloudDocumentaiV1ListProcessorTypesResponse]] = (fun: list) => fun.apply()
				}
				/** Gets a processor type detail. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDocumentaiV1ProcessorType]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDocumentaiV1ProcessorType])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, processorTypesId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processorTypes/${processorTypesId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudDocumentaiV1ProcessorType]] = (fun: get) => fun.apply()
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
			object processors {
				/** Enables a processor */
				class enable(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudDocumentaiV1EnableProcessorRequest(body: Schema.GoogleCloudDocumentaiV1EnableProcessorRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object enable {
					def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): enable = new enable(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}:enable").addQueryStringParameters("name" -> name.toString))
				}
				/** Set the default (active) version of a Processor that will be used in ProcessDocument and BatchProcessDocuments. */
				class setDefaultProcessorVersion(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudDocumentaiV1SetDefaultProcessorVersionRequest(body: Schema.GoogleCloudDocumentaiV1SetDefaultProcessorVersionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object setDefaultProcessorVersion {
					def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, processor: String)(using signer: RequestSigner, ec: ExecutionContext): setDefaultProcessorVersion = new setDefaultProcessorVersion(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}:setDefaultProcessorVersion").addQueryStringParameters("processor" -> processor.toString))
				}
				/** LRO endpoint to batch process many documents. The output is written to Cloud Storage as JSON in the [Document] format. */
				class batchProcess(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudDocumentaiV1BatchProcessRequest(body: Schema.GoogleCloudDocumentaiV1BatchProcessRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object batchProcess {
					def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): batchProcess = new batchProcess(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}:batchProcess").addQueryStringParameters("name" -> name.toString))
				}
				/** Creates a processor from the ProcessorType provided. The processor will be at `ENABLED` state by default after its creation. Note that this method requires the `documentai.processors.create` permission on the project, which is highly privileged. A user or service account with this permission can create new processors that can interact with any gcs bucket in your project. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudDocumentaiV1Processor(body: Schema.GoogleCloudDocumentaiV1Processor) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDocumentaiV1Processor])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Deletes the processor, unloads all deployed model artifacts if it was enabled and then deletes all artifacts associated with this processor. */
				class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
				}
				/** Disables a processor */
				class disable(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudDocumentaiV1DisableProcessorRequest(body: Schema.GoogleCloudDocumentaiV1DisableProcessorRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object disable {
					def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): disable = new disable(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}:disable").addQueryStringParameters("name" -> name.toString))
				}
				/** Gets a processor detail. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDocumentaiV1Processor]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDocumentaiV1Processor])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudDocumentaiV1Processor]] = (fun: get) => fun.apply()
				}
				/** Lists all processors which belong to this project. */
				class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDocumentaiV1ListProcessorsResponse]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDocumentaiV1ListProcessorsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors").addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.GoogleCloudDocumentaiV1ListProcessorsResponse]] = (fun: list) => fun.apply()
				}
				/** Processes a single document. */
				class process(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudDocumentaiV1ProcessRequest(body: Schema.GoogleCloudDocumentaiV1ProcessRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDocumentaiV1ProcessResponse])
				}
				object process {
					def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): process = new process(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}:process").addQueryStringParameters("name" -> name.toString))
				}
				object humanReviewConfig {
					/** Send a document for Human Review. The input document should be processed by the specified processor. */
					class reviewDocument(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDocumentaiV1ReviewDocumentRequest(body: Schema.GoogleCloudDocumentaiV1ReviewDocumentRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object reviewDocument {
						def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, humanReviewConfig: String)(using signer: RequestSigner, ec: ExecutionContext): reviewDocument = new reviewDocument(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}/humanReviewConfig:reviewDocument").addQueryStringParameters("humanReviewConfig" -> humanReviewConfig.toString))
					}
				}
				object processorVersions {
					/** Processes a single document. */
					class process(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDocumentaiV1ProcessRequest(body: Schema.GoogleCloudDocumentaiV1ProcessRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudDocumentaiV1ProcessResponse])
					}
					object process {
						def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, processorVersionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): process = new process(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}/processorVersions/${processorVersionsId}:process").addQueryStringParameters("name" -> name.toString))
					}
					/** Undeploys the processor version. */
					class undeploy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDocumentaiV1UndeployProcessorVersionRequest(body: Schema.GoogleCloudDocumentaiV1UndeployProcessorVersionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object undeploy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, processorVersionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): undeploy = new undeploy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}/processorVersions/${processorVersionsId}:undeploy").addQueryStringParameters("name" -> name.toString))
					}
					/** LRO endpoint to batch process many documents. The output is written to Cloud Storage as JSON in the [Document] format. */
					class batchProcess(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDocumentaiV1BatchProcessRequest(body: Schema.GoogleCloudDocumentaiV1BatchProcessRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object batchProcess {
						def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, processorVersionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): batchProcess = new batchProcess(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}/processorVersions/${processorVersionsId}:batchProcess").addQueryStringParameters("name" -> name.toString))
					}
					/** Evaluates a ProcessorVersion against annotated documents, producing an Evaluation. */
					class evaluateProcessorVersion(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDocumentaiV1EvaluateProcessorVersionRequest(body: Schema.GoogleCloudDocumentaiV1EvaluateProcessorVersionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object evaluateProcessorVersion {
						def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, processorVersionsId :PlayApi, processorVersion: String)(using signer: RequestSigner, ec: ExecutionContext): evaluateProcessorVersion = new evaluateProcessorVersion(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}/processorVersions/${processorVersionsId}:evaluateProcessorVersion").addQueryStringParameters("processorVersion" -> processorVersion.toString))
					}
					/** Deletes the processor version, all artifacts under the processor version will be deleted. */
					class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, processorVersionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}/processorVersions/${processorVersionsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
					}
					/** Deploys the processor version. */
					class deploy(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDocumentaiV1DeployProcessorVersionRequest(body: Schema.GoogleCloudDocumentaiV1DeployProcessorVersionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object deploy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, processorVersionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): deploy = new deploy(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}/processorVersions/${processorVersionsId}:deploy").addQueryStringParameters("name" -> name.toString))
					}
					/** Lists all versions of a processor. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDocumentaiV1ListProcessorVersionsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDocumentaiV1ListProcessorVersionsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, pageToken: String, pageSize: Int, parent: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}/processorVersions").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudDocumentaiV1ListProcessorVersionsResponse]] = (fun: list) => fun.apply()
					}
					/** Gets a processor version detail. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDocumentaiV1ProcessorVersion]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDocumentaiV1ProcessorVersion])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, processorVersionsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}/processorVersions/${processorVersionsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudDocumentaiV1ProcessorVersion]] = (fun: get) => fun.apply()
					}
					/** Trains a new processor version. Operation metadata is returned as TrainProcessorVersionMetadata. */
					class train(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def withGoogleCloudDocumentaiV1TrainProcessorVersionRequest(body: Schema.GoogleCloudDocumentaiV1TrainProcessorVersionRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object train {
						def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): train = new train(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}/processorVersions:train").addQueryStringParameters("parent" -> parent.toString))
					}
					object evaluations {
						/** Retrieves a set of evaluations for a given processor version. */
						class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDocumentaiV1ListEvaluationsResponse]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDocumentaiV1ListEvaluationsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, processorVersionsId :PlayApi, pageSize: Int, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}/processorVersions/${processorVersionsId}/evaluations").addQueryStringParameters("pageSize" -> pageSize.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleCloudDocumentaiV1ListEvaluationsResponse]] = (fun: list) => fun.apply()
						}
						/** Retrieves a specific evaluation. */
						class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDocumentaiV1Evaluation]) {
							val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
							/** Perform the request */
							def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudDocumentaiV1Evaluation])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, processorVersionsId :PlayApi, evaluationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}/processorVersions/${processorVersionsId}/evaluations/${evaluationsId}").addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDocumentaiV1Evaluation]] = (fun: get) => fun.apply()
						}
					}
				}
			}
		}
		object operations {
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
		}
	}
	object operations {
		/** Deletes a long-running operation. This method indicates that the client is no longer interested in the operation result. It does not cancel the operation. If the server doesn't support this method, it returns `google.rpc.Code.UNIMPLEMENTED`. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
		}
		object delete {
			def apply(operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
		}
	}
}
