package com.boresjo.play.api.google.homegraph

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
		"""https://www.googleapis.com/auth/homegraph""" /* Private Service: https://www.googleapis.com/auth/homegraph */
	)

	private val BASE_URL = "https://homegraph.googleapis.com/"

	object agentUsers {
		/** Unlinks the given third-party user from your smart home Action. All data related to this user will be deleted. For more details on how users link their accounts, see [fulfillment and authentication](https://developers.home.google.com/cloud-to-cloud/primer/fulfillment). The third-party user's identity is passed in via the `agent_user_id` (see DeleteAgentUserRequest). This request must be authorized using service account credentials from your Actions console project. */
		class delete(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.Empty]) {
			val scopes = Seq("""https://www.googleapis.com/auth/homegraph""")
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("DELETE")).map(_.json.as[Schema.Empty])
		}
		object delete {
			def apply(agentUsersId :PlayApi, requestId: String, agentUserId: String)(using signer: RequestSigner, ec: ExecutionContext): delete = new delete(ws.url(BASE_URL + s"v1/agentUsers/${agentUsersId}").addQueryStringParameters("requestId" -> requestId.toString, "agentUserId" -> agentUserId.toString))
			given Conversion[delete, Future[Schema.Empty]] = (fun: delete) => fun.apply()
		}
	}
	object devices {
		/** Requests Google to send an `action.devices.SYNC` [intent](https://developers.home.google.com/cloud-to-cloud/intents/sync) to your smart home Action to update device metadata for the given user. The third-party user's identity is passed via the `agent_user_id` (see RequestSyncDevicesRequest). This request must be authorized using service account credentials from your Actions console project. */
		class requestSync(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/homegraph""")
			/** Perform the request */
			def withRequestSyncDevicesRequest(body: Schema.RequestSyncDevicesRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.RequestSyncDevicesResponse])
		}
		object requestSync {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): requestSync = new requestSync(ws.url(BASE_URL + s"v1/devices:requestSync").addQueryStringParameters())
		}
		/** Reports device state and optionally sends device notifications. Called by your smart home Action when the state of a third-party device changes or you need to send a notification about the device. See [Implement Report State](https://developers.home.google.com/cloud-to-cloud/integration/report-state) for more information. This method updates the device state according to its declared [traits](https://developers.home.google.com/cloud-to-cloud/primer/device-types-and-traits). Publishing a new state value outside of these traits will result in an `INVALID_ARGUMENT` error response. The third-party user's identity is passed in via the `agent_user_id` (see ReportStateAndNotificationRequest). This request must be authorized using service account credentials from your Actions console project. */
		class reportStateAndNotification(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/homegraph""")
			/** Perform the request */
			def withReportStateAndNotificationRequest(body: Schema.ReportStateAndNotificationRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.ReportStateAndNotificationResponse])
		}
		object reportStateAndNotification {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): reportStateAndNotification = new reportStateAndNotification(ws.url(BASE_URL + s"v1/devices:reportStateAndNotification").addQueryStringParameters())
		}
		/** Gets the current states in Home Graph for the given set of the third-party user's devices. The third-party user's identity is passed in via the `agent_user_id` (see QueryRequest). This request must be authorized using service account credentials from your Actions console project. */
		class query(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/homegraph""")
			/** Perform the request */
			def withQueryRequest(body: Schema.QueryRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.QueryResponse])
		}
		object query {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): query = new query(ws.url(BASE_URL + s"v1/devices:query").addQueryStringParameters())
		}
		/** Gets all the devices associated with the given third-party user. The third-party user's identity is passed in via the `agent_user_id` (see SyncRequest). This request must be authorized using service account credentials from your Actions console project. */
		class sync(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq("""https://www.googleapis.com/auth/homegraph""")
			/** Perform the request */
			def withSyncRequest(body: Schema.SyncRequest) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("POST")).map(_.json.as[Schema.SyncResponse])
		}
		object sync {
			def apply()(using signer: RequestSigner, ec: ExecutionContext): sync = new sync(ws.url(BASE_URL + s"v1/devices:sync").addQueryStringParameters())
		}
	}
}
