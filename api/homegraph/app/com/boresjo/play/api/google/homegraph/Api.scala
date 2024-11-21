package com.boresjo.play.api.google.homegraph

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://homegraph.googleapis.com/"

	object agentUsers {
		class delete(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			def apply() = req.execute("DELETE").map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(agentUsersId :PlayApi, requestId: String, agentUserId: String)(using auth: AuthToken, ec: ExecutionContext): delete = new delete(auth(ws.url(BASE_URL + s"v1/agentUsers/${agentUsersId}")).addQueryStringParameters("requestId" -> requestId.toString, "agentUserId" -> agentUserId.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
	}
	object devices {
		class requestSync(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withRequestSyncDevicesRequest(body: Schema.RequestSyncDevicesRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.RequestSyncDevicesResponse])
		}
		object requestSync {
			def apply()(using auth: AuthToken, ec: ExecutionContext): requestSync = new requestSync(auth(ws.url(BASE_URL + s"v1/devices:requestSync")).addQueryStringParameters())
		}
		class reportStateAndNotification(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withReportStateAndNotificationRequest(body: Schema.ReportStateAndNotificationRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.ReportStateAndNotificationResponse])
		}
		object reportStateAndNotification {
			def apply()(using auth: AuthToken, ec: ExecutionContext): reportStateAndNotification = new reportStateAndNotification(auth(ws.url(BASE_URL + s"v1/devices:reportStateAndNotification")).addQueryStringParameters())
		}
		class query(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withQueryRequest(body: Schema.QueryRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.QueryResponse])
		}
		object query {
			def apply()(using auth: AuthToken, ec: ExecutionContext): query = new query(auth(ws.url(BASE_URL + s"v1/devices:query")).addQueryStringParameters())
		}
		class sync(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withSyncRequest(body: Schema.SyncRequest) = req.withBody(Json.toJson(body)).execute("POST").map(_.json.as[Schema.SyncResponse])
		}
		object sync {
			def apply()(using auth: AuthToken, ec: ExecutionContext): sync = new sync(auth(ws.url(BASE_URL + s"v1/devices:sync")).addQueryStringParameters())
		}
	}
}
