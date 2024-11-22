package com.boresjo.play.api.google.advisorynotifications

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtGoogleCloudAdvisorynotificationsV1Csv: Format[Schema.GoogleCloudAdvisorynotificationsV1Csv] = Json.format[Schema.GoogleCloudAdvisorynotificationsV1Csv]
	given fmtGoogleCloudAdvisorynotificationsV1CsvCsvRow: Format[Schema.GoogleCloudAdvisorynotificationsV1CsvCsvRow] = Json.format[Schema.GoogleCloudAdvisorynotificationsV1CsvCsvRow]
	given fmtGoogleCloudAdvisorynotificationsV1ListNotificationsResponse: Format[Schema.GoogleCloudAdvisorynotificationsV1ListNotificationsResponse] = Json.format[Schema.GoogleCloudAdvisorynotificationsV1ListNotificationsResponse]
	given fmtGoogleCloudAdvisorynotificationsV1Notification: Format[Schema.GoogleCloudAdvisorynotificationsV1Notification] = Json.format[Schema.GoogleCloudAdvisorynotificationsV1Notification]
	given fmtGoogleCloudAdvisorynotificationsV1NotificationSettings: Format[Schema.GoogleCloudAdvisorynotificationsV1NotificationSettings] = Json.format[Schema.GoogleCloudAdvisorynotificationsV1NotificationSettings]
	given fmtGoogleCloudAdvisorynotificationsV1Attachment: Format[Schema.GoogleCloudAdvisorynotificationsV1Attachment] = Json.format[Schema.GoogleCloudAdvisorynotificationsV1Attachment]
	given fmtGoogleCloudAdvisorynotificationsV1Message: Format[Schema.GoogleCloudAdvisorynotificationsV1Message] = Json.format[Schema.GoogleCloudAdvisorynotificationsV1Message]
	given fmtGoogleCloudAdvisorynotificationsV1MessageBody: Format[Schema.GoogleCloudAdvisorynotificationsV1MessageBody] = Json.format[Schema.GoogleCloudAdvisorynotificationsV1MessageBody]
	given fmtGoogleCloudAdvisorynotificationsV1Subject: Format[Schema.GoogleCloudAdvisorynotificationsV1Subject] = Json.format[Schema.GoogleCloudAdvisorynotificationsV1Subject]
	given fmtGoogleCloudAdvisorynotificationsV1NotificationNotificationTypeEnum: Format[Schema.GoogleCloudAdvisorynotificationsV1Notification.NotificationTypeEnum] = JsonEnumFormat[Schema.GoogleCloudAdvisorynotificationsV1Notification.NotificationTypeEnum]
	given fmtGoogleCloudAdvisorynotificationsV1Settings: Format[Schema.GoogleCloudAdvisorynotificationsV1Settings] = Json.format[Schema.GoogleCloudAdvisorynotificationsV1Settings]
	given fmtGoogleCloudAdvisorynotificationsV1Text: Format[Schema.GoogleCloudAdvisorynotificationsV1Text] = Json.format[Schema.GoogleCloudAdvisorynotificationsV1Text]
	given fmtGoogleCloudAdvisorynotificationsV1TextLocalizationStateEnum: Format[Schema.GoogleCloudAdvisorynotificationsV1Text.LocalizationStateEnum] = JsonEnumFormat[Schema.GoogleCloudAdvisorynotificationsV1Text.LocalizationStateEnum]
}
