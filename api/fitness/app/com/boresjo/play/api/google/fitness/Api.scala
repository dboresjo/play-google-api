package com.boresjo.play.api.google.fitness

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://fitness.googleapis.com/fitness/v1/users/"

	object users {
		object dataSources {
			class create(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withDataSource(body: Schema.DataSource) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.DataSource])
			}
			object create {
				def apply(userId: String)(using auth: AuthToken, ec: ExecutionContext): create = new create(auth(ws.url(BASE_URL + s"${userId}/dataSources")).addQueryStringParameters())
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DataSource]) {
				def apply() = req.execute("DELETE").map(_.json.as[Schema.DataSource])
			}
			object delete {
				def apply(dataSourceId: String, userId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"${userId}/dataSources/${dataSourceId}")).addQueryStringParameters())
				given Conversion[delete, Future[Schema.DataSource]] = (fun: delete) => fun.apply()
			}
			class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.DataSource]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.DataSource])
			}
			object get {
				def apply(dataSourceId: String, userId: String)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"${userId}/dataSources/${dataSourceId}")).addQueryStringParameters())
				given Conversion[get, Future[Schema.DataSource]] = (fun: get) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withDataSource(body: Schema.DataSource) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.DataSource])
			}
			object update {
				def apply(userId: String, dataSourceId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"${userId}/dataSources/${dataSourceId}")).addQueryStringParameters())
			}
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDataSourcesResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListDataSourcesResponse])
			}
			object list {
				def apply(userId: String, dataTypeName: String)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"${userId}/dataSources")).addQueryStringParameters("dataTypeName" -> dataTypeName.toString))
				given Conversion[list, Future[Schema.ListDataSourcesResponse]] = (fun: list) => fun.apply()
			}
			object dataPointChanges {
				class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListDataPointChangesResponse]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.ListDataPointChangesResponse])
				}
				object list {
					def apply(userId: String, pageToken: String, dataSourceId: String, limit: Int)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"${userId}/dataSources/${dataSourceId}/dataPointChanges")).addQueryStringParameters("pageToken" -> pageToken.toString, "limit" -> limit.toString))
					given Conversion[list, Future[Schema.ListDataPointChangesResponse]] = (fun: list) => fun.apply()
				}
			}
			object datasets {
				class patch(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
					def withDataset(body: Schema.Dataset) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.Dataset])
				}
				object patch {
					def apply(datasetId: String, dataSourceId: String, userId: String)(using auth: AuthToken, ec: ExecutionContext): patch = new patch(auth(ws.url(BASE_URL + s"${userId}/dataSources/${dataSourceId}/datasets/${datasetId}")).addQueryStringParameters())
				}
				class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
					def apply() = req.execute("DELETE").map(_ => ())
				}
				object delete {
					def apply(dataSourceId: String, datasetId: String, userId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"${userId}/dataSources/${dataSourceId}/datasets/${datasetId}")).addQueryStringParameters())
					given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
				}
				class get(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Dataset]) {
					def apply() = req.execute("GET").map(_.json.as[Schema.Dataset])
				}
				object get {
					def apply(datasetId: String, pageToken: String, userId: String, dataSourceId: String, limit: Int)(using auth: AuthToken, ec: ExecutionContext): get = new get(auth(ws.url(BASE_URL + s"${userId}/dataSources/${dataSourceId}/datasets/${datasetId}")).addQueryStringParameters("pageToken" -> pageToken.toString, "limit" -> limit.toString))
					given Conversion[get, Future[Schema.Dataset]] = (fun: get) => fun.apply()
				}
			}
		}
		object sessions {
			class list(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.ListSessionsResponse]) {
				def apply() = req.execute("GET").map(_.json.as[Schema.ListSessionsResponse])
			}
			object list {
				def apply(activityType: Int, endTime: String, userId: String, startTime: String, pageToken: String, includeDeleted: Boolean)(using auth: AuthToken, ec: ExecutionContext): list = new list(auth(ws.url(BASE_URL + s"${userId}/sessions")).addQueryStringParameters("activityType" -> activityType.toString, "endTime" -> endTime.toString, "startTime" -> startTime.toString, "pageToken" -> pageToken.toString, "includeDeleted" -> includeDeleted.toString))
				given Conversion[list, Future[Schema.ListSessionsResponse]] = (fun: list) => fun.apply()
			}
			class update(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withSession(body: Schema.Session) = req.withBody(Json.toJson(body)).execute("PUT").map(_.json.as[Schema.Session])
			}
			object update {
				def apply(userId: String, sessionId: String)(using auth: AuthToken, ec: ExecutionContext): update = new update(auth(ws.url(BASE_URL + s"${userId}/sessions/${sessionId}")).addQueryStringParameters())
			}
			class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Unit]) {
				def apply() = req.execute("DELETE").map(_ => ())
			}
			object delete {
				def apply(sessionId: String, userId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"${userId}/sessions/${sessionId}")).addQueryStringParameters())
				given Conversion[delete, Future[Unit]] = (fun: delete) => fun.apply()
			}
		}
		object dataset {
			class aggregate(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
				def withAggregateRequest(body: Schema.AggregateRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.AggregateResponse])
			}
			object aggregate {
				def apply(userId: String)(using auth: AuthToken, ec: ExecutionContext): aggregate = new aggregate(auth(ws.url(BASE_URL + s"${userId}/dataset:aggregate")).addQueryStringParameters())
			}
		}
	}
}
