package com.boresjo.play.api.google.mybusinessnotifications

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	val scopes = Seq()

	private val BASE_URL = "https://mybusinessnotifications.googleapis.com/"

	object accounts {
		/** Returns the pubsub notification settings for the account. */
		class getNotificationSetting(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) extends (() => Future[Schema.NotificationSetting]) {
			val scopes = Seq()
			/** Perform the request */
			def apply() = signer.exec(scopes:_*)(req,_.execute("GET")).map(_.json.as[Schema.NotificationSetting])
		}
		object getNotificationSetting {
			def apply(accountsId :PlayApi, name: String)(using signer: RequestSigner, ec: ExecutionContext): getNotificationSetting = new getNotificationSetting(ws.url(BASE_URL + s"v1/accounts/${accountsId}/notificationSetting").addQueryStringParameters("name" -> name.toString))
			given Conversion[getNotificationSetting, Future[Schema.NotificationSetting]] = (fun: getNotificationSetting) => fun.apply()
		}
		/** Sets the pubsub notification setting for the account informing Google which topic to send pubsub notifications for. Use the notification_types field within notification_setting to manipulate the events an account wants to subscribe to. An account will only have one notification setting resource, and only one pubsub topic can be set. To delete the setting, update with an empty notification_types */
		class updateNotificationSetting(private val req: WSRequest)(using signer: RequestSigner, ec: ExecutionContext) {
			val scopes = Seq()
			/** Perform the request */
			def withNotificationSetting(body: Schema.NotificationSetting) = signer.exec(scopes:_*)(req.withBody(Json.toJson(body)),_.execute("PATCH")).map(_.json.as[Schema.NotificationSetting])
		}
		object updateNotificationSetting {
			def apply(accountsId :PlayApi, name: String, updateMask: String)(using signer: RequestSigner, ec: ExecutionContext): updateNotificationSetting = new updateNotificationSetting(ws.url(BASE_URL + s"v1/accounts/${accountsId}/notificationSetting").addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
	}
}
