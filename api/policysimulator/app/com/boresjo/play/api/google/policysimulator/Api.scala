package com.boresjo.play.api.google.policysimulator

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

	private val BASE_URL = "https://policysimulator.googleapis.com/"

	object folders {
		object locations {
			object orgPolicyViolationsPreviews {
				object operations {
					/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object get {
						def apply(foldersId :PlayApi, locationsId :PlayApi, orgPolicyViolationsPreviewsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/orgPolicyViolationsPreviews/${orgPolicyViolationsPreviewsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
					}
				}
			}
			object replays {
				/** Creates and starts a Replay using the given ReplayConfig. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudPolicysimulatorV1Replay(body: Schema.GoogleCloudPolicysimulatorV1Replay) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object create {
					def apply(foldersId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/replays").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Gets the specified Replay. Each `Replay` is available for at least 7 days. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudPolicysimulatorV1Replay]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudPolicysimulatorV1Replay])
				}
				object get {
					def apply(foldersId :PlayApi, locationsId :PlayApi, replaysId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/replays/${replaysId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudPolicysimulatorV1Replay]] = (fun: get) => fun.apply()
				}
				object results {
					/** Lists the results of running a Replay. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudPolicysimulatorV1ListReplayResultsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudPolicysimulatorV1ListReplayResultsResponse])
					}
					object list {
						def apply(foldersId :PlayApi, locationsId :PlayApi, replaysId :PlayApi, pageSize: Int, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/replays/${replaysId}/results").addQueryStringParameters("pageSize" -> pageSize.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleCloudPolicysimulatorV1ListReplayResultsResponse]] = (fun: list) => fun.apply()
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
						def apply(foldersId :PlayApi, locationsId :PlayApi, replaysId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/replays/${replaysId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
					}
					/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
					}
					object list {
						def apply(foldersId :PlayApi, locationsId :PlayApi, replaysId :PlayApi, pageSize: Int, name: String, pageToken: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/replays/${replaysId}/operations").addQueryStringParameters("pageSize" -> pageSize.toString, "name" -> name.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
	}
	object organizations {
		object locations {
			object replays {
				/** Gets the specified Replay. Each `Replay` is available for at least 7 days. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudPolicysimulatorV1Replay]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudPolicysimulatorV1Replay])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, replaysId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/replays/${replaysId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudPolicysimulatorV1Replay]] = (fun: get) => fun.apply()
				}
				/** Creates and starts a Replay using the given ReplayConfig. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudPolicysimulatorV1Replay(body: Schema.GoogleCloudPolicysimulatorV1Replay) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object create {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/replays").addQueryStringParameters("parent" -> parent.toString))
				}
				object results {
					/** Lists the results of running a Replay. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudPolicysimulatorV1ListReplayResultsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudPolicysimulatorV1ListReplayResultsResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, replaysId :PlayApi, pageToken: String, parent: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/replays/${replaysId}/results").addQueryStringParameters("pageToken" -> pageToken.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
						given Conversion[list, Future[Schema.GoogleCloudPolicysimulatorV1ListReplayResultsResponse]] = (fun: list) => fun.apply()
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
						def apply(organizationsId :PlayApi, locationsId :PlayApi, replaysId :PlayApi, pageSize: Int, name: String, pageToken: String, filter: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/replays/${replaysId}/operations").addQueryStringParameters("pageSize" -> pageSize.toString, "name" -> name.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
					}
					/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object get {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, replaysId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/replays/${replaysId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
					}
				}
			}
			object orgPolicyViolationsPreviews {
				object operations {
					/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object get {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, orgPolicyViolationsPreviewsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/orgPolicyViolationsPreviews/${orgPolicyViolationsPreviewsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
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
			def apply(operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
		}
		/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
		class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
			val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
		}
		object list {
			def apply(name: String, pageToken: String, filter: String, pageSize: Int)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/operations").addQueryStringParameters("name" -> name.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString))
			given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
		}
	}
	object projects {
		object locations {
			object replays {
				/** Creates and starts a Replay using the given ReplayConfig. */
				class create(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def withGoogleCloudPolicysimulatorV1Replay(body: Schema.GoogleCloudPolicysimulatorV1Replay) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using signer: RequestSigner, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/replays").addQueryStringParameters("parent" -> parent.toString))
				}
				/** Gets the specified Replay. Each `Replay` is available for at least 7 days. */
				class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudPolicysimulatorV1Replay]) {
					val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
					/** Perform the request */
					def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudPolicysimulatorV1Replay])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, replaysId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/replays/${replaysId}").addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudPolicysimulatorV1Replay]] = (fun: get) => fun.apply()
				}
				object results {
					/** Lists the results of running a Replay. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudPolicysimulatorV1ListReplayResultsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudPolicysimulatorV1ListReplayResultsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, replaysId :PlayApi, pageSize: Int, parent: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/replays/${replaysId}/results").addQueryStringParameters("pageSize" -> pageSize.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleCloudPolicysimulatorV1ListReplayResultsResponse]] = (fun: list) => fun.apply()
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
						def apply(projectsId :PlayApi, locationsId :PlayApi, replaysId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/replays/${replaysId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
					}
					/** Lists operations that match the specified filter in the request. If the server doesn't support this method, it returns `UNIMPLEMENTED`. */
					class list(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, replaysId :PlayApi, pageSize: Int, name: String, filter: String, pageToken: String)(using signer: RequestSigner, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/replays/${replaysId}/operations").addQueryStringParameters("pageSize" -> pageSize.toString, "name" -> name.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object orgPolicyViolationsPreviews {
				object operations {
					/** Gets the latest state of a long-running operation. Clients can use this method to poll the operation result at intervals as recommended by the API service. */
					class get(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						val scopes = Seq("""https://www.googleapis.com/auth/cloud-platform""")
						/** Perform the request */
						def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, orgPolicyViolationsPreviewsId :PlayApi, operationsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/orgPolicyViolationsPreviews/${orgPolicyViolationsPreviewsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
					}
				}
			}
		}
	}
}
