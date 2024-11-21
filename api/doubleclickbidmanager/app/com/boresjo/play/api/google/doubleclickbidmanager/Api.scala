package com.boresjo.play.api.google.doubleclickbidmanager

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://doubleclickbidmanager.googleapis.com/v2/"

	object queries {
		class run(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withRunQueryRequest(body: Schema.RunQueryRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Report])
		}
		object run {
			def apply(queryId: String, synchronous: Boolean)(using auth: AuthToken, ec: ExecutionContext): run = new run(auth(ws.url(BASE_URL + s"queries/${queryId}:run")).addQueryStringParameters("synchronous" -> synchronous.toString))
		}
		class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withQuery(body: Schema.Query) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.Query])
		}
		object create {
			def apply()(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"queries")).addQueryStringParameters())
		}
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
			def apply() = req.execute("DELETE").map(_ => ())
		}
		object delete {
			def apply(queryId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"queries/${queryId}")).addQueryStringParameters())
			given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
		}
		class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Query]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.Query])
		}
		object get {
			def apply(queryId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"queries/${queryId}")).addQueryStringParameters())
			given Conversion[get, Future[Schema.Query]] = (fun: get) => fun.apply()
		}
		class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListQueriesResponse]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.ListQueriesResponse])
		}
		object list {
			def apply(pageSize: Int, pageToken: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"queries")).addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString))
			given Conversion[list, Future[Schema.ListQueriesResponse]] = (fun: list) => fun.apply()
		}
		object reports {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListReportsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListReportsResponse])
			}
			object list {
				def apply(queryId: String, pageSize: Int, pageToken: String, orderBy: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"queries/${queryId}/reports")).addQueryStringParameters("pageSize" -> pageSize.toString, "pageToken" -> pageToken.toString, "orderBy" -> orderBy.toString))
				given Conversion[list, Future[Schema.ListReportsResponse]] = (fun: list) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Report]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.Report])
			}
			object get {
				def apply(queryId: String, reportId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"queries/${queryId}/reports/${reportId}")).addQueryStringParameters())
				given Conversion[get, Future[Schema.Report]] = (fun: get) => fun.apply()
			}
		}
	}
}
