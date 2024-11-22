package com.boresjo.play.api.google.fcmdata

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleFirebaseFcmDataV1beta1Data(
	  /** Mutually exclusive breakdown of message delivery outcomes. */
		messageOutcomePercents: Option[Schema.GoogleFirebaseFcmDataV1beta1MessageOutcomePercents] = None,
	  /** Count of notifications accepted by FCM intended for Android devices. The targeted device must have opted in to the collection of usage and diagnostic information. */
		countNotificationsAccepted: Option[String] = None,
	  /** Additional information about delivery performance for messages that were successfully delivered. */
		deliveryPerformancePercents: Option[Schema.GoogleFirebaseFcmDataV1beta1DeliveryPerformancePercents] = None,
	  /** Count of messages accepted by FCM intended for Android devices. The targeted device must have opted in to the collection of usage and diagnostic information. */
		countMessagesAccepted: Option[String] = None,
	  /** Additional insights about proxy notification delivery. */
		proxyNotificationInsightPercents: Option[Schema.GoogleFirebaseFcmDataV1beta1ProxyNotificationInsightPercents] = None,
	  /** Additional general insights about message delivery. */
		messageInsightPercents: Option[Schema.GoogleFirebaseFcmDataV1beta1MessageInsightPercents] = None
	)
	
	case class GoogleFirebaseFcmDataV1beta1DeliveryPerformancePercents(
	  /** The percentage of accepted messages that were delayed because the target device was not connected at the time of sending. These messages were eventually delivered when the device reconnected. */
		delayedDeviceOffline: Option[BigDecimal] = None,
	  /** The percentage of accepted messages that were delayed because the intended device user-profile was [stopped](https://firebase.google.com/docs/cloud-messaging/android/receive#handling_messages) on the target device at the time of the send. The messages were eventually delivered when the user-profile was started again. */
		delayedUserStopped: Option[BigDecimal] = None,
	  /** The percentage of accepted messages that were delivered to the device without delay from the FCM system. */
		deliveredNoDelay: Option[BigDecimal] = None,
	  /** The percentage of accepted messages that were delayed because the device was in doze mode. Only [normal priority messages](https://firebase.google.com/docs/cloud-messaging/concept-options#setting-the-priority-of-a-message) should be delayed due to doze mode. */
		delayedDeviceDoze: Option[BigDecimal] = None,
	  /** The percentage of accepted messages that were delayed due to message throttling, such as [collapsible message throttling](https://firebase.google.com/docs/cloud-messaging/concept-options#collapsible_throttling) or [maximum message rate throttling](https://firebase.google.com/docs/cloud-messaging/concept-options#device_throttling). */
		delayedMessageThrottled: Option[BigDecimal] = None
	)
	
	case class GoogleTypeDate(
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None,
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None,
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None
	)
	
	case class GoogleFirebaseFcmDataV1beta1ListAndroidDeliveryDataResponse(
	  /** The delivery data for the provided app. There will be one entry per combination of app, date, and analytics label. */
		androidDeliveryData: Option[List[Schema.GoogleFirebaseFcmDataV1beta1AndroidDeliveryData]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleFirebaseFcmDataV1beta1ProxyNotificationInsightPercents(
	  /** The percentage of accepted notifications that were successfully proxied by [Google Play services](https://developers.google.com/android/guides/overview). */
		proxied: Option[BigDecimal] = None,
	  /** The percentage of accepted notifications that were skipped because proxy notification is unsupported for the recipient. */
		skippedUnsupported: Option[BigDecimal] = None,
	  /** The percentage of accepted notifications that were skipped because the app disallowed these messages to be proxied. */
		skippedOptedOut: Option[BigDecimal] = None,
	  /** The percentage of accepted notifications that were skipped because the messages were not throttled. */
		skippedNotThrottled: Option[BigDecimal] = None,
	  /** The percentage of accepted notifications that were skipped because configurations required for notifications to be proxied were missing. */
		skippedUnconfigured: Option[BigDecimal] = None,
	  /** The percentage of accepted notifications that failed to be proxied. This is usually caused by exceptions that occurred while calling [notifyAsPackage](https://developer.android.com/reference/android/app/NotificationManager#notifyAsPackage%28java.lang.String,%20java.lang.String,%20int,%20android.app.Notification%29). */
		failed: Option[BigDecimal] = None
	)
	
	case class GoogleFirebaseFcmDataV1beta1MessageOutcomePercents(
	  /** The percentage of accepted messages that were dropped because the application was force stopped on the device at the time of delivery and retries were unsuccessful. */
		droppedAppForceStopped: Option[BigDecimal] = None,
	  /** The percentage of accepted messages that expired because [Time To Live (TTL)](https://firebase.google.com/docs/cloud-messaging/concept-options#ttl) elapsed before the target device reconnected. */
		droppedTtlExpired: Option[BigDecimal] = None,
	  /** The percentage of accepted messages that were dropped because the target device is inactive. FCM will drop messages if the target device is deemed inactive by our servers. If a device does reconnect, we call [OnDeletedMessages()](https://firebase.google.com/docs/cloud-messaging/android/receive#override-ondeletedmessages) in our SDK instead of delivering the messages. */
		droppedDeviceInactive: Option[BigDecimal] = None,
	  /** The percentage of accepted messages that were dropped due to [too many undelivered non-collapsible messages](https://firebase.google.com/docs/cloud-messaging/concept-options#collapsible_and_non-collapsible_messages). Specifically, each app instance can only have 100 pending messages stored on our servers for a device which is disconnected. When that device reconnects, those messages are delivered. When there are more than the maximum pending messages, we call [OnDeletedMessages()](https://firebase.google.com/docs/cloud-messaging/android/receive#override-ondeletedmessages) in our SDK instead of delivering the messages. */
		droppedTooManyPendingMessages: Option[BigDecimal] = None,
	  /** The percentage of all accepted messages that were successfully delivered to the device. */
		delivered: Option[BigDecimal] = None,
	  /** The percentage of accepted messages that were [collapsed](https://firebase.google.com/docs/cloud-messaging/concept-options#collapsible_and_non-collapsible_messages) by another message. */
		collapsed: Option[BigDecimal] = None,
	  /** The percentage of messages accepted on this day that were not dropped and not delivered, due to the device being disconnected (as of the end of the America/Los_Angeles day when the message was sent to FCM). A portion of these messages will be delivered the next day when the device connects but others may be destined to devices that ultimately never reconnect. */
		pending: Option[BigDecimal] = None
	)
	
	case class GoogleFirebaseFcmDataV1beta1MessageInsightPercents(
	  /** The percentage of accepted messages that had their priority lowered from high to normal. See [documentation for setting message priority](https://firebase.google.com/docs/cloud-messaging/android/message-priority). */
		priorityLowered: Option[BigDecimal] = None
	)
	
	case class GoogleFirebaseFcmDataV1beta1AndroidDeliveryData(
	  /** The data for the specified appId, date, and analyticsLabel. */
		data: Option[Schema.GoogleFirebaseFcmDataV1beta1Data] = None,
	  /** The date represented by this entry. */
		date: Option[Schema.GoogleTypeDate] = None,
	  /** The analytics label associated with the messages sent. All messages sent without an analytics label will be grouped together in a single entry. */
		analyticsLabel: Option[String] = None,
	  /** The app ID to which the messages were sent. */
		appId: Option[String] = None
	)
}
