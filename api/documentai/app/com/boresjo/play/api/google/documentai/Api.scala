package com.boresjo.play.api.google.documentai

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://documentai.googleapis.com/"

	object projects {
		object locations {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudLocationLocation]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudLocationLocation])
			}
			object get {
				def apply(projectsId :PlayApi, locationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleCloudLocationLocation]] = (fun: get) => fun.apply()
			}
			class fetchProcessorTypes(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDocumentaiV1FetchProcessorTypesResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudDocumentaiV1FetchProcessorTypesResponse])
			}
			object fetchProcessorTypes {
				def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): fetchProcessorTypes = new fetchProcessorTypes(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}:fetchProcessorTypes")).addQueryStringParameters("parent" -> parent.toString))
				given Conversion[fetchProcessorTypes, Future[Schema.GoogleCloudDocumentaiV1FetchProcessorTypesResponse]] = (fun: fetchProcessorTypes) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudLocationListLocationsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudLocationListLocationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
				given Conversion[list, Future[Schema.GoogleCloudLocationListLocationsResponse]] = (fun: list) => fun.apply()
			}
			object processorTypes {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDocumentaiV1ListProcessorTypesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudDocumentaiV1ListProcessorTypesResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, pageSize: Int, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processorTypes")).addQueryStringParameters("pageSize" -> pageSize.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString))
					given Conversion[list, Future[Schema.GoogleCloudDocumentaiV1ListProcessorTypesResponse]] = (fun: list) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDocumentaiV1ProcessorType]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudDocumentaiV1ProcessorType])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, processorTypesId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processorTypes/${processorTypesId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudDocumentaiV1ProcessorType]] = (fun: get) => fun.apply()
				}
			}
			object operations {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, name: String, filter: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations")).addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
				}
				class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
					def apply() = req.execute("POST").map(_.json.as[Schema.GoogleProtobufEmpty])
				}
				object cancel {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}:cancel")).addQueryStringParameters("name" -> name.toString))
					given Conversion[cancel, Future[Schema.GoogleProtobufEmpty]] = (fun: cancel) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
				}
			}
			object processors {
				class enable(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDocumentaiV1EnableProcessorRequest(body: Schema.GoogleCloudDocumentaiV1EnableProcessorRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object enable {
					def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): enable = new enable(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}:enable")).addQueryStringParameters("name" -> name.toString))
				}
				class setDefaultProcessorVersion(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDocumentaiV1SetDefaultProcessorVersionRequest(body: Schema.GoogleCloudDocumentaiV1SetDefaultProcessorVersionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object setDefaultProcessorVersion {
					def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, processor: String)(using auth: AuthToken, ec: ExecutionContext): setDefaultProcessorVersion = new setDefaultProcessorVersion(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}:setDefaultProcessorVersion")).addQueryStringParameters("processor" -> processor.toString))
				}
				class batchProcess(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDocumentaiV1BatchProcessRequest(body: Schema.GoogleCloudDocumentaiV1BatchProcessRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object batchProcess {
					def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): batchProcess = new batchProcess(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}:batchProcess")).addQueryStringParameters("name" -> name.toString))
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDocumentaiV1Processor(body: Schema.GoogleCloudDocumentaiV1Processor) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudDocumentaiV1Processor])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors")).addQueryStringParameters("parent" -> parent.toString))
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
					def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object delete {
					def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
				}
				class disable(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDocumentaiV1DisableProcessorRequest(body: Schema.GoogleCloudDocumentaiV1DisableProcessorRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object disable {
					def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): disable = new disable(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}:disable")).addQueryStringParameters("name" -> name.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDocumentaiV1Processor]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudDocumentaiV1Processor])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudDocumentaiV1Processor]] = (fun: get) => fun.apply()
				}
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDocumentaiV1ListProcessorsResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudDocumentaiV1ListProcessorsResponse])
				}
				object list {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String, pageToken: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors")).addQueryStringParameters("parent" -> parent.toString, "pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString))
					given Conversion[list, Future[Schema.GoogleCloudDocumentaiV1ListProcessorsResponse]] = (fun: list) => fun.apply()
				}
				class process(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudDocumentaiV1ProcessRequest(body: Schema.GoogleCloudDocumentaiV1ProcessRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudDocumentaiV1ProcessResponse])
				}
				object process {
					def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): process = new process(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}:process")).addQueryStringParameters("name" -> name.toString))
				}
				object humanReviewConfig {
					class reviewDocument(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDocumentaiV1ReviewDocumentRequest(body: Schema.GoogleCloudDocumentaiV1ReviewDocumentRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object reviewDocument {
						def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, humanReviewConfig: String)(using auth: AuthToken, ec: ExecutionContext): reviewDocument = new reviewDocument(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}/humanReviewConfig:reviewDocument")).addQueryStringParameters("humanReviewConfig" -> humanReviewConfig.toString))
					}
				}
				object processorVersions {
					class process(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDocumentaiV1ProcessRequest(body: Schema.GoogleCloudDocumentaiV1ProcessRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleCloudDocumentaiV1ProcessResponse])
					}
					object process {
						def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, processorVersionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): process = new process(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}/processorVersions/${processorVersionsId}:process")).addQueryStringParameters("name" -> name.toString))
					}
					class undeploy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDocumentaiV1UndeployProcessorVersionRequest(body: Schema.GoogleCloudDocumentaiV1UndeployProcessorVersionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object undeploy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, processorVersionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): undeploy = new undeploy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}/processorVersions/${processorVersionsId}:undeploy")).addQueryStringParameters("name" -> name.toString))
					}
					class batchProcess(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDocumentaiV1BatchProcessRequest(body: Schema.GoogleCloudDocumentaiV1BatchProcessRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object batchProcess {
						def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, processorVersionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): batchProcess = new batchProcess(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}/processorVersions/${processorVersionsId}:batchProcess")).addQueryStringParameters("name" -> name.toString))
					}
					class evaluateProcessorVersion(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDocumentaiV1EvaluateProcessorVersionRequest(body: Schema.GoogleCloudDocumentaiV1EvaluateProcessorVersionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object evaluateProcessorVersion {
						def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, processorVersionsId :PlayApi, processorVersion: String)(using auth: AuthToken, ec: ExecutionContext): evaluateProcessorVersion = new evaluateProcessorVersion(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}/processorVersions/${processorVersionsId}:evaluateProcessorVersion")).addQueryStringParameters("processorVersion" -> processorVersion.toString))
					}
					class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object delete {
						def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, processorVersionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}/processorVersions/${processorVersionsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
					}
					class deploy(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDocumentaiV1DeployProcessorVersionRequest(body: Schema.GoogleCloudDocumentaiV1DeployProcessorVersionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object deploy {
						def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, processorVersionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): deploy = new deploy(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}/processorVersions/${processorVersionsId}:deploy")).addQueryStringParameters("name" -> name.toString))
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDocumentaiV1ListProcessorVersionsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudDocumentaiV1ListProcessorVersionsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, pageToken: String, pageSize: Int, parent: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}/processorVersions")).addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "parent" -> parent.toString))
						given Conversion[list, Future[Schema.GoogleCloudDocumentaiV1ListProcessorVersionsResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDocumentaiV1ProcessorVersion]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudDocumentaiV1ProcessorVersion])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, processorVersionsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}/processorVersions/${processorVersionsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleCloudDocumentaiV1ProcessorVersion]] = (fun: get) => fun.apply()
					}
					class train(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
						def withGoogleCloudDocumentaiV1TrainProcessorVersionRequest(body: Schema.GoogleCloudDocumentaiV1TrainProcessorVersionRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object train {
						def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): train = new train(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}/processorVersions:train")).addQueryStringParameters("parent" -> parent.toString))
					}
					object evaluations {
						class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDocumentaiV1ListEvaluationsResponse]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudDocumentaiV1ListEvaluationsResponse])
						}
						object list {
							def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, processorVersionsId :PlayApi, pageSize: Int, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}/processorVersions/${processorVersionsId}/evaluations")).addQueryStringParameters("pageSize" -> pageSize.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString))
							given Conversion[list, Future[Schema.GoogleCloudDocumentaiV1ListEvaluationsResponse]] = (fun: list) => fun.apply()
						}
						class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudDocumentaiV1Evaluation]) {
							def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudDocumentaiV1Evaluation])
						}
						object get {
							def apply(projectsId :PlayApi, locationsId :PlayApi, processorsId :PlayApi, processorVersionsId :PlayApi, evaluationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/processors/${processorsId}/processorVersions/${processorVersionsId}/evaluations/${evaluationsId}")).addQueryStringParameters("name" -> name.toString))
							given Conversion[get, Future[Schema.GoogleCloudDocumentaiV1Evaluation]] = (fun: get) => fun.apply()
						}
					}
				}
			}
		}
		object operations {
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object get {
				def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
			}
		}
	}
	object operations {
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
			def apply() = req.execute("DELETE").map(_.json.as[Schema.GoogleProtobufEmpty])
		}
		object delete {
			def apply(operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
			given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
		}
	}
}
