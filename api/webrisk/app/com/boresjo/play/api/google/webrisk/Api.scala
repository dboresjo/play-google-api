package com.boresjo.play.api.google.webrisk

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://webrisk.googleapis.com/"

	object threatLists {
		class computeDiff(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudWebriskV1ComputeThreatListDiffResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudWebriskV1ComputeThreatListDiffResponse])
		}
		object computeDiff {
			def apply(constraintsSupportedCompressions: String, constraintsMaxDiffEntries: Int, threatType: String, versionToken: String, constraintsMaxDatabaseEntries: Int)(using auth: AuthToken, ec: ExecutionContext): computeDiff = new computeDiff(ws.url(BASE_URL + s"v1/threatLists:computeDiff").addQueryStringParameters("constraints.supportedCompressions" -> constraintsSupportedCompressions.toString, "constraints.maxDiffEntries" -> constraintsMaxDiffEntries.toString, "threatType" -> threatType.toString, "versionToken" -> versionToken.toString, "constraints.maxDatabaseEntries" -> constraintsMaxDatabaseEntries.toString))
			given Conversion[computeDiff, Future[Schema.GoogleCloudWebriskV1ComputeThreatListDiffResponse]] = (fun: computeDiff) => fun.apply()
		}
	}
	object hashes {
		class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudWebriskV1SearchHashesResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudWebriskV1SearchHashesResponse])
		}
		object search {
			def apply(threatTypes: String, hashPrefix: String)(using auth: AuthToken, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1/hashes:search").addQueryStringParameters("threatTypes" -> threatTypes.toString, "hashPrefix" -> hashPrefix.toString))
			given Conversion[search, Future[Schema.GoogleCloudWebriskV1SearchHashesResponse]] = (fun: search) => fun.apply()
		}
	}
	object uris {
		class search(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleCloudWebriskV1SearchUrisResponse]) {
			def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleCloudWebriskV1SearchUrisResponse])
		}
		object search {
			def apply(threatTypes: String, uri: String)(using auth: AuthToken, ec: ExecutionContext): search = new search(ws.url(BASE_URL + s"v1/uris:search").addQueryStringParameters("threatTypes" -> threatTypes.toString, "uri" -> uri.toString))
			given Conversion[search, Future[Schema.GoogleCloudWebriskV1SearchUrisResponse]] = (fun: search) => fun.apply()
		}
	}
	object projects {
		object submissions {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleCloudWebriskV1Submission(body: Schema.GoogleCloudWebriskV1Submission) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleCloudWebriskV1Submission])
			}
			object create {
				def apply(projectsId :PlayApi, parent: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectsId}/submissions").addQueryStringParameters("parent" -> parent.toString))
			}
		}
		object operations {
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleProtobufEmpty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object delete {
				def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.GoogleProtobufEmpty]] = (fun: delete) => fun.apply()
			}
			class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleLongrunningCancelOperationRequest(body: Schema.GoogleLongrunningCancelOperationRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleProtobufEmpty])
			}
			object cancel {
				def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object get {
				def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, pageToken: String, pageSize: Int, filter: String, name: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/operations").addQueryStringParameters("pageToken" -> pageToken.toString, "pageSize" -> pageSize.toString, "filter" -> filter.toString, "name" -> name.toString))
				given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
