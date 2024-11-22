package com.boresjo.play.api.google.advisorynotifications

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleCloudAdvisorynotificationsV1Csv(
	  /** The list of data rows in a CSV file, as string arrays rather than as a single comma-separated string. */
		dataRows: Option[List[Schema.GoogleCloudAdvisorynotificationsV1CsvCsvRow]] = None,
	  /** The list of headers for data columns in a CSV file. */
		headers: Option[List[String]] = None
	)
	
	case class GoogleCloudAdvisorynotificationsV1ListNotificationsResponse(
	  /** Estimation of a total number of notifications. */
		totalSize: Option[Int] = None,
	  /** List of notifications under a given parent. */
		notifications: Option[List[Schema.GoogleCloudAdvisorynotificationsV1Notification]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudAdvisorynotificationsV1NotificationSettings(
	  /** Whether the associated NotificationType is enabled. */
		enabled: Option[Boolean] = None
	)
	
	case class GoogleCloudAdvisorynotificationsV1CsvCsvRow(
	  /** The data entries in a CSV file row, as a string array rather than a single comma-separated string. */
		entries: Option[List[String]] = None
	)
	
	case class GoogleCloudAdvisorynotificationsV1Attachment(
	  /** The title of the attachment. */
		displayName: Option[String] = None,
	  /** A CSV file attachment. Max size is 10 MB. */
		csv: Option[Schema.GoogleCloudAdvisorynotificationsV1Csv] = None
	)
	
	case class GoogleCloudAdvisorynotificationsV1Message(
	  /** Time when Message was localized */
		localizationTime: Option[String] = None,
	  /** The attachments to download. */
		attachments: Option[List[Schema.GoogleCloudAdvisorynotificationsV1Attachment]] = None,
	  /** The message content. */
		body: Option[Schema.GoogleCloudAdvisorynotificationsV1MessageBody] = None,
	  /** The Message creation timestamp. */
		createTime: Option[String] = None
	)
	
	object GoogleCloudAdvisorynotificationsV1Notification {
		enum NotificationTypeEnum extends Enum[NotificationTypeEnum] { case NOTIFICATION_TYPE_UNSPECIFIED, NOTIFICATION_TYPE_SECURITY_PRIVACY_ADVISORY, NOTIFICATION_TYPE_SENSITIVE_ACTIONS, NOTIFICATION_TYPE_SECURITY_MSA, NOTIFICATION_TYPE_THREAT_HORIZONS }
	}
	case class GoogleCloudAdvisorynotificationsV1Notification(
	  /** The resource name of the notification. Format: organizations/{organization}/locations/{location}/notifications/{notification} or projects/{project}/locations/{location}/notifications/{notification}. */
		name: Option[String] = None,
	  /** The subject line of the notification. */
		subject: Option[Schema.GoogleCloudAdvisorynotificationsV1Subject] = None,
	  /** Output only. Time the notification was created. */
		createTime: Option[String] = None,
	  /** Type of notification */
		notificationType: Option[Schema.GoogleCloudAdvisorynotificationsV1Notification.NotificationTypeEnum] = None,
	  /** A list of messages in the notification. */
		messages: Option[List[Schema.GoogleCloudAdvisorynotificationsV1Message]] = None
	)
	
	case class GoogleCloudAdvisorynotificationsV1Settings(
	  /** Required. Fingerprint for optimistic concurrency returned in Get requests. Must be provided for Update requests. If the value provided does not match the value known to the server, ABORTED will be thrown, and the client should retry the read-modify-write cycle. */
		etag: Option[String] = None,
	  /** Required. Map of each notification type and its settings to get/set all settings at once. The server will validate the value for each notification type. */
		notificationSettings: Option[Map[String, Schema.GoogleCloudAdvisorynotificationsV1NotificationSettings]] = None,
	  /** Identifier. The resource name of the settings to retrieve. Format: organizations/{organization}/locations/{location}/settings or projects/{projects}/locations/{location}/settings. */
		name: Option[String] = None
	)
	
	case class GoogleCloudAdvisorynotificationsV1MessageBody(
	  /** The text content of the message body. */
		text: Option[Schema.GoogleCloudAdvisorynotificationsV1Text] = None
	)
	
	case class GoogleCloudAdvisorynotificationsV1Subject(
	  /** The text content. */
		text: Option[Schema.GoogleCloudAdvisorynotificationsV1Text] = None
	)
	
	object GoogleCloudAdvisorynotificationsV1Text {
		enum LocalizationStateEnum extends Enum[LocalizationStateEnum] { case LOCALIZATION_STATE_UNSPECIFIED, LOCALIZATION_STATE_NOT_APPLICABLE, LOCALIZATION_STATE_PENDING, LOCALIZATION_STATE_COMPLETED }
	}
	case class GoogleCloudAdvisorynotificationsV1Text(
	  /** Status of the localization. */
		localizationState: Option[Schema.GoogleCloudAdvisorynotificationsV1Text.LocalizationStateEnum] = None,
	  /** The requested localized copy (if applicable). */
		localizedText: Option[String] = None,
	  /** The English copy. */
		enText: Option[String] = None
	)
}
