package com.boresjo.play.api.google.policysimulator

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://policysimulator.googleapis.com/"

	object folders {
		object locations {
			object orgPolicyViolationsPreviews {
				object operations {
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object get {
						def apply(foldersId :PlayApi, locationsId :PlayApi, orgPolicyViolationsPreviewsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/orgPolicyViolationsPreviews/${orgPolicyViolationsPreviewsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
					}
				}
			}
			object replays {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudPolicysimulatorV1Replay(body: Schema.GoogleCloudPolicysimulatorV1Replay) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object create {
					def apply(foldersId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/replays")).addQueryStringParameters("parent" -> parent.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudPolicysimulatorV1Replay]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudPolicysimulatorV1Replay])
				}
				object get {
					def apply(foldersId :PlayApi, locationsId :PlayApi, replaysId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/replays/${replaysId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudPolicysimulatorV1Replay]] = (fun: get) => fun.apply()
				}
				object results {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudPolicysimulatorV1ListReplayResultsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudPolicysimulatorV1ListReplayResultsResponse])
					}
					object list {
						def apply(foldersId :PlayApi, locationsId :PlayApi, replaysId :PlayApi, pageSize: Int, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/replays/${replaysId}/results")).addQueryStringParameters("pageSize" -> pageSize.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleCloudPolicysimulatorV1ListReplayResultsResponse]] = (fun: list) => fun.apply()
					}
				}
				object operations {
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object get {
						def apply(foldersId :PlayApi, locationsId :PlayApi, replaysId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/replays/${replaysId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
					}
					object list {
						def apply(foldersId :PlayApi, locationsId :PlayApi, replaysId :PlayApi, pageSize: Int, name: String, pageToken: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/folders/${foldersId}/locations/${locationsId}/replays/${replaysId}/operations")).addQueryStringParameters("pageSize" -> pageSize.toString, "name" -> name.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
		}
	}
	object organizations {
		object locations {
			object replays {
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudPolicysimulatorV1Replay]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudPolicysimulatorV1Replay])
				}
				object get {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, replaysId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/replays/${replaysId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudPolicysimulatorV1Replay]] = (fun: get) => fun.apply()
				}
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudPolicysimulatorV1Replay(body: Schema.GoogleCloudPolicysimulatorV1Replay) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object create {
					def apply(organizationsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/replays")).addQueryStringParameters("parent" -> parent.toString))
				}
				object results {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudPolicysimulatorV1ListReplayResultsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudPolicysimulatorV1ListReplayResultsResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, replaysId :PlayApi, pageToken: String, parent: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/replays/${replaysId}/results")).addQueryStringParameters("pageToken" -> pageToken.toString, "parent" -> parent.toString, "pageSize" -> pageSize.toString))
						given Conversion[list, Future[Schema.GoogleCloudPolicysimulatorV1ListReplayResultsResponse]] = (fun: list) => fun.apply()
					}
				}
				object operations {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
					}
					object list {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, replaysId :PlayApi, pageSize: Int, name: String, pageToken: String, filter: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/replays/${replaysId}/operations")).addQueryStringParameters("pageSize" -> pageSize.toString, "name" -> name.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString))
						given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
					}
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object get {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, replaysId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/replays/${replaysId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
					}
				}
			}
			object orgPolicyViolationsPreviews {
				object operations {
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object get {
						def apply(organizationsId :PlayApi, locationsId :PlayApi, orgPolicyViolationsPreviewsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/organizations/${organizationsId}/locations/${locationsId}/orgPolicyViolationsPreviews/${orgPolicyViolationsPreviewsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
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
			def apply(operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
			given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
		}
		object list {
			def apply(name: String, pageToken: String, filter: String, pageSize: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/operations")).addQueryStringParameters("name" -> name.toString, "pageToken" -> pageToken.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString))
			given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
		}
	}
	object projects {
		object locations {
			object replays {
				class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withGoogleCloudPolicysimulatorV1Replay(body: Schema.GoogleCloudPolicysimulatorV1Replay) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.GoogleLongrunningOperation])
				}
				object create {
					def apply(projectsId :PlayApi, locationsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/replays")).addQueryStringParameters("parent" -> parent.toString))
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudPolicysimulatorV1Replay]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudPolicysimulatorV1Replay])
				}
				object get {
					def apply(projectsId :PlayApi, locationsId :PlayApi, replaysId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/replays/${replaysId}")).addQueryStringParameters("name" -> name.toString))
					given Conversion[get, Future[Schema.GoogleCloudPolicysimulatorV1Replay]] = (fun: get) => fun.apply()
				}
				object results {
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudPolicysimulatorV1ListReplayResultsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleCloudPolicysimulatorV1ListReplayResultsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, replaysId :PlayApi, pageSize: Int, parent: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/replays/${replaysId}/results")).addQueryStringParameters("pageSize" -> pageSize.toString, "parent" -> parent.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleCloudPolicysimulatorV1ListReplayResultsResponse]] = (fun: list) => fun.apply()
					}
				}
				object operations {
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, replaysId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/replays/${replaysId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
					}
					class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
					}
					object list {
						def apply(projectsId :PlayApi, locationsId :PlayApi, replaysId :PlayApi, pageSize: Int, name: String, filter: String, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/replays/${replaysId}/operations")).addQueryStringParameters("pageSize" -> pageSize.toString, "name" -> name.toString, "filter" -> filter.toString, "pageToken" -> pageToken.toString))
						given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
					}
				}
			}
			object orgPolicyViolationsPreviews {
				object operations {
					class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
						def apply() = req.execute("GET").map(_.json.as[Schema.GoogleLongrunningOperation])
					}
					object get {
						def apply(projectsId :PlayApi, locationsId :PlayApi, orgPolicyViolationsPreviewsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"v1/projects/${projectsId}/locations/${locationsId}/orgPolicyViolationsPreviews/${orgPolicyViolationsPreviewsId}/operations/${operationsId}")).addQueryStringParameters("name" -> name.toString))
						given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
					}
				}
			}
		}
	}
}
