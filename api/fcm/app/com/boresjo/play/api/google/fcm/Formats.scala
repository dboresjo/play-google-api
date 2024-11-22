package com.boresjo.play.api.google.fcm

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtSendMessageRequest: Format[Schema.SendMessageRequest] = Json.format[Schema.SendMessageRequest]
	given fmtMessage: Format[Schema.Message] = Json.format[Schema.Message]
	given fmtNotification: Format[Schema.Notification] = Json.format[Schema.Notification]
	given fmtAndroidConfig: Format[Schema.AndroidConfig] = Json.format[Schema.AndroidConfig]
	given fmtWebpushConfig: Format[Schema.WebpushConfig] = Json.format[Schema.WebpushConfig]
	given fmtApnsConfig: Format[Schema.ApnsConfig] = Json.format[Schema.ApnsConfig]
	given fmtFcmOptions: Format[Schema.FcmOptions] = Json.format[Schema.FcmOptions]
	given fmtAndroidConfigPriorityEnum: Format[Schema.AndroidConfig.PriorityEnum] = JsonEnumFormat[Schema.AndroidConfig.PriorityEnum]
	given fmtAndroidNotification: Format[Schema.AndroidNotification] = Json.format[Schema.AndroidNotification]
	given fmtAndroidFcmOptions: Format[Schema.AndroidFcmOptions] = Json.format[Schema.AndroidFcmOptions]
	given fmtAndroidNotificationNotificationPriorityEnum: Format[Schema.AndroidNotification.NotificationPriorityEnum] = JsonEnumFormat[Schema.AndroidNotification.NotificationPriorityEnum]
	given fmtAndroidNotificationVisibilityEnum: Format[Schema.AndroidNotification.VisibilityEnum] = JsonEnumFormat[Schema.AndroidNotification.VisibilityEnum]
	given fmtLightSettings: Format[Schema.LightSettings] = Json.format[Schema.LightSettings]
	given fmtAndroidNotificationProxyEnum: Format[Schema.AndroidNotification.ProxyEnum] = JsonEnumFormat[Schema.AndroidNotification.ProxyEnum]
	given fmtColor: Format[Schema.Color] = Json.format[Schema.Color]
	given fmtWebpushFcmOptions: Format[Schema.WebpushFcmOptions] = Json.format[Schema.WebpushFcmOptions]
	given fmtApnsFcmOptions: Format[Schema.ApnsFcmOptions] = Json.format[Schema.ApnsFcmOptions]
}
