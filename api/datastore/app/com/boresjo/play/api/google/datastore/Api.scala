package com.boresjo.play.api.google.datastore

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://datastore.googleapis.com/"

	object projects {
		class lookup(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withLookupRequest(body: Schema.LookupRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.LookupResponse])
		}
		object lookup {
			def apply(projectId: String)(using auth: AuthToken, ec: ExecutionContext): lookup = new lookup(ws.url(BASE_URL + s"v1/projects/${projectId}:lookup").addQueryStringParameters())
		}
		class allocateIds(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withAllocateIdsRequest(body: Schema.AllocateIdsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.AllocateIdsResponse])
		}
		object allocateIds {
			def apply(projectId: String)(using auth: AuthToken, ec: ExecutionContext): allocateIds = new allocateIds(ws.url(BASE_URL + s"v1/projects/${projectId}:allocateIds").addQueryStringParameters())
		}
		class beginTransaction(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withBeginTransactionRequest(body: Schema.BeginTransactionRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.BeginTransactionResponse])
		}
		object beginTransaction {
			def apply(projectId: String)(using auth: AuthToken, ec: ExecutionContext): beginTransaction = new beginTransaction(ws.url(BASE_URL + s"v1/projects/${projectId}:beginTransaction").addQueryStringParameters())
		}
		class runQuery(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withRunQueryRequest(body: Schema.RunQueryRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RunQueryResponse])
		}
		object runQuery {
			def apply(projectId: String)(using auth: AuthToken, ec: ExecutionContext): runQuery = new runQuery(ws.url(BASE_URL + s"v1/projects/${projectId}:runQuery").addQueryStringParameters())
		}
		class `export`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleDatastoreAdminV1ExportEntitiesRequest(body: Schema.GoogleDatastoreAdminV1ExportEntitiesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object `export` {
			def apply(projectId: String)(using auth: AuthToken, ec: ExecutionContext): `export` = new `export`(ws.url(BASE_URL + s"v1/projects/${projectId}:export").addQueryStringParameters())
		}
		class rollback(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withRollbackRequest(body: Schema.RollbackRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RollbackResponse])
		}
		object rollback {
			def apply(projectId: String)(using auth: AuthToken, ec: ExecutionContext): rollback = new rollback(ws.url(BASE_URL + s"v1/projects/${projectId}:rollback").addQueryStringParameters())
		}
		class `import`(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withGoogleDatastoreAdminV1ImportEntitiesRequest(body: Schema.GoogleDatastoreAdminV1ImportEntitiesRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
		}
		object `import` {
			def apply(projectId: String)(using auth: AuthToken, ec: ExecutionContext): `import` = new `import`(ws.url(BASE_URL + s"v1/projects/${projectId}:import").addQueryStringParameters())
		}
		class reserveIds(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withReserveIdsRequest(body: Schema.ReserveIdsRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ReserveIdsResponse])
		}
		object reserveIds {
			def apply(projectId: String)(using auth: AuthToken, ec: ExecutionContext): reserveIds = new reserveIds(ws.url(BASE_URL + s"v1/projects/${projectId}:reserveIds").addQueryStringParameters())
		}
		class commit(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withCommitRequest(body: Schema.CommitRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.CommitResponse])
		}
		object commit {
			def apply(projectId: String)(using auth: AuthToken, ec: ExecutionContext): commit = new commit(ws.url(BASE_URL + s"v1/projects/${projectId}:commit").addQueryStringParameters())
		}
		class runAggregationQuery(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withRunAggregationQueryRequest(body: Schema.RunAggregationQueryRequest) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RunAggregationQueryResponse])
		}
		object runAggregationQuery {
			def apply(projectId: String)(using auth: AuthToken, ec: ExecutionContext): runAggregationQuery = new runAggregationQuery(ws.url(BASE_URL + s"v1/projects/${projectId}:runAggregationQuery").addQueryStringParameters())
		}
		object operations {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningListOperationsResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningListOperationsResponse])
			}
			object list {
				def apply(projectsId :PlayApi, name: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectsId}/operations").addQueryStringParameters("name" -> name.toString, "filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.GoogleLongrunningListOperationsResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object get {
				def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[get, Future[Schema.GoogleLongrunningOperation]] = (fun: get) => fun.apply()
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
			}
			object delete {
				def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectsId}/operations/${operationsId}").addQueryStringParameters("name" -> name.toString))
				given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
			}
			class cancel(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
				def apply() = auth.exec(req,_.execute("POST")).map(_.json.as[Schema.Empty])
			}
			object cancel {
				def apply(projectsId :PlayApi, operationsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): cancel = new cancel(ws.url(BASE_URL + s"v1/projects/${projectsId}/operations/${operationsId}:cancel").addQueryStringParameters("name" -> name.toString))
				given Conversion[cancel, Future[Schema.Empty]] = (fun: cancel) => fun.apply()
			}
		}
		object indexes {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withGoogleDatastoreAdminV1Index(body: Schema.GoogleDatastoreAdminV1Index) = auth.exec(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object create {
				def apply(projectId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(ws.url(BASE_URL + s"v1/projects/${projectId}/indexes").addQueryStringParameters())
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleLongrunningOperation]) {
				def apply() = auth.exec(req,_.execute("DELETE")).map(_.json.as[Schema.GoogleLongrunningOperation])
			}
			object delete {
				def apply(projectId: String, indexId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/projects/${projectId}/indexes/${indexId}").addQueryStringParameters())
				given Conversion[delete, Future[Schema.GoogleLongrunningOperation]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleDatastoreAdminV1Index]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleDatastoreAdminV1Index])
			}
			object get {
				def apply(projectId: String, indexId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(ws.url(BASE_URL + s"v1/projects/${projectId}/indexes/${indexId}").addQueryStringParameters())
				given Conversion[get, Future[Schema.GoogleDatastoreAdminV1Index]] = (fun: get) => fun.apply()
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.GoogleDatastoreAdminV1ListIndexesResponse]) {
				def apply() = auth.exec(req,_.execute("GET")).map(_.json.as[Schema.GoogleDatastoreAdminV1ListIndexesResponse])
			}
			object list {
				def apply(projectId: String, filter: String, pageSize: Int, pageToken: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(ws.url(BASE_URL + s"v1/projects/${projectId}/indexes").addQueryStringParameters("filter" -> filter.toString, "pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString))
				given Conversion[list, Future[Schema.GoogleDatastoreAdminV1ListIndexesResponse]] = (fun: list) => fun.apply()
			}
		}
	}
}
