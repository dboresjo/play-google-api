package com.boresjo.play.api.google.advisorynotifications

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaGoogleCloudAdvisorynotificationsV1CsvCsvRow: Conversion[List[Schema.GoogleCloudAdvisorynotificationsV1CsvCsvRow], Option[List[Schema.GoogleCloudAdvisorynotificationsV1CsvCsvRow]]] = (fun: List[Schema.GoogleCloudAdvisorynotificationsV1CsvCsvRow]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListSchemaGoogleCloudAdvisorynotificationsV1Notification: Conversion[List[Schema.GoogleCloudAdvisorynotificationsV1Notification], Option[List[Schema.GoogleCloudAdvisorynotificationsV1Notification]]] = (fun: List[Schema.GoogleCloudAdvisorynotificationsV1Notification]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaGoogleCloudAdvisorynotificationsV1Csv: Conversion[Schema.GoogleCloudAdvisorynotificationsV1Csv, Option[Schema.GoogleCloudAdvisorynotificationsV1Csv]] = (fun: Schema.GoogleCloudAdvisorynotificationsV1Csv) => Option(fun)
		given putListSchemaGoogleCloudAdvisorynotificationsV1Attachment: Conversion[List[Schema.GoogleCloudAdvisorynotificationsV1Attachment], Option[List[Schema.GoogleCloudAdvisorynotificationsV1Attachment]]] = (fun: List[Schema.GoogleCloudAdvisorynotificationsV1Attachment]) => Option(fun)
		given putSchemaGoogleCloudAdvisorynotificationsV1MessageBody: Conversion[Schema.GoogleCloudAdvisorynotificationsV1MessageBody, Option[Schema.GoogleCloudAdvisorynotificationsV1MessageBody]] = (fun: Schema.GoogleCloudAdvisorynotificationsV1MessageBody) => Option(fun)
		given putSchemaGoogleCloudAdvisorynotificationsV1Subject: Conversion[Schema.GoogleCloudAdvisorynotificationsV1Subject, Option[Schema.GoogleCloudAdvisorynotificationsV1Subject]] = (fun: Schema.GoogleCloudAdvisorynotificationsV1Subject) => Option(fun)
		given putSchemaGoogleCloudAdvisorynotificationsV1NotificationNotificationTypeEnum: Conversion[Schema.GoogleCloudAdvisorynotificationsV1Notification.NotificationTypeEnum, Option[Schema.GoogleCloudAdvisorynotificationsV1Notification.NotificationTypeEnum]] = (fun: Schema.GoogleCloudAdvisorynotificationsV1Notification.NotificationTypeEnum) => Option(fun)
		given putListSchemaGoogleCloudAdvisorynotificationsV1Message: Conversion[List[Schema.GoogleCloudAdvisorynotificationsV1Message], Option[List[Schema.GoogleCloudAdvisorynotificationsV1Message]]] = (fun: List[Schema.GoogleCloudAdvisorynotificationsV1Message]) => Option(fun)
		given putMapStringSchemaGoogleCloudAdvisorynotificationsV1NotificationSettings: Conversion[Map[String, Schema.GoogleCloudAdvisorynotificationsV1NotificationSettings], Option[Map[String, Schema.GoogleCloudAdvisorynotificationsV1NotificationSettings]]] = (fun: Map[String, Schema.GoogleCloudAdvisorynotificationsV1NotificationSettings]) => Option(fun)
		given putSchemaGoogleCloudAdvisorynotificationsV1Text: Conversion[Schema.GoogleCloudAdvisorynotificationsV1Text, Option[Schema.GoogleCloudAdvisorynotificationsV1Text]] = (fun: Schema.GoogleCloudAdvisorynotificationsV1Text) => Option(fun)
		given putSchemaGoogleCloudAdvisorynotificationsV1TextLocalizationStateEnum: Conversion[Schema.GoogleCloudAdvisorynotificationsV1Text.LocalizationStateEnum, Option[Schema.GoogleCloudAdvisorynotificationsV1Text.LocalizationStateEnum]] = (fun: Schema.GoogleCloudAdvisorynotificationsV1Text.LocalizationStateEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
