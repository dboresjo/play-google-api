package com.boresjo.play.api.google.mybusinessnotifications

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Api @Inject() (ws: WSClient) extends PlayApi {
	import Formats.given
	import play.api.libs.ws.writeableOf_JsValue

	private val BASE_URL = "https://mybusinessnotifications.googleapis.com/"

	object accounts {
		class getNotificationSetting(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) extends (() => Future[Schema.NotificationSetting]) {
			def apply() = req.execute("GET").map(_.json.as[Schema.NotificationSetting])
		}
		object getNotificationSetting {
			def apply(accountsId :PlayApi, name: String)(using auth: AuthToken, ec: ExecutionContext): getNotificationSetting = new getNotificationSetting(auth(ws.url(BASE_URL + s"v1/accounts/${accountsId}/notificationSetting")).addQueryStringParameters("name" -> name.toString))
			given Conversion[getNotificationSetting, Future[Schema.NotificationSetting]] = (fun: getNotificationSetting) => fun.apply()
		}
		class updateNotificationSetting(private val req: WSRequest)(using auth: AuthToken, ec: ExecutionContext) {
			def withNotificationSetting(body: Schema.NotificationSetting) = req.withBody(Json.toJson(body)).execute("PATCH").map(_.json.as[Schema.NotificationSetting])
		}
		object updateNotificationSetting {
			def apply(accountsId :PlayApi, name: String, updateMask: String)(using auth: AuthToken, ec: ExecutionContext): updateNotificationSetting = new updateNotificationSetting(auth(ws.url(BASE_URL + s"v1/accounts/${accountsId}/notificationSetting")).addQueryStringParameters("name" -> name.toString, "updateMask" -> updateMask.toString))
		}
	}
}
