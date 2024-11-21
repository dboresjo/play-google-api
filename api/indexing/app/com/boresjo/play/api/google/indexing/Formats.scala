package com.boresjo.play.api.google.indexing

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtUrlNotification: Format[Schema.UrlNotification] = Json.format[Schema.UrlNotification]
	given fmtUrlNotificationTypeEnum: Format[Schema.UrlNotification.TypeEnum] = JsonEnumFormat[Schema.UrlNotification.TypeEnum]
	given fmtPublishUrlNotificationResponse: Format[Schema.PublishUrlNotificationResponse] = Json.format[Schema.PublishUrlNotificationResponse]
	given fmtUrlNotificationMetadata: Format[Schema.UrlNotificationMetadata] = Json.format[Schema.UrlNotificationMetadata]
}
