package com.boresjo.play.api.google.mybusinessnotifications

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtNotificationSetting: Format[Schema.NotificationSetting] = Json.format[Schema.NotificationSetting]
	given fmtNotificationSettingNotificationTypesEnum: Format[Schema.NotificationSetting.NotificationTypesEnum] = JsonEnumFormat[Schema.NotificationSetting.NotificationTypesEnum]
}
