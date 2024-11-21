package com.boresjo.play.api.google.mybusinessnotifications

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object NotificationSetting {
		enum NotificationTypesEnum extends Enum[NotificationTypesEnum] { case NOTIFICATION_TYPE_UNSPECIFIED, GOOGLE_UPDATE, NEW_REVIEW, UPDATED_REVIEW, NEW_CUSTOMER_MEDIA, NEW_QUESTION, UPDATED_QUESTION, NEW_ANSWER, UPDATED_ANSWER, DUPLICATE_LOCATION, LOSS_OF_VOICE_OF_MERCHANT, VOICE_OF_MERCHANT_UPDATED }
	}
	case class NotificationSetting(
	  /** Required. The resource name this setting is for. This is of the form `accounts/{account_id}/notificationSetting`. */
		name: Option[String] = None,
	  /** Optional. The Google Pub/Sub topic that will receive notifications when locations managed by this account are updated. If unset, no notifications will be posted. The account mybusiness-api-pubsub@system.gserviceaccount.com must have at least Publish permissions on the Pub/Sub topic. */
		pubsubTopic: Option[String] = None,
	  /** The types of notifications that will be sent to the Pub/Sub topic. To stop receiving notifications entirely, use NotificationSettings.UpdateNotificationSetting with an empty notification_types or set the pubsub_topic to an empty string. */
		notificationTypes: Option[List[Schema.NotificationSetting.NotificationTypesEnum]] = None
	)
}
