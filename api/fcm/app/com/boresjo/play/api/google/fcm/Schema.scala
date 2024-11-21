package com.boresjo.play.api.google.fcm

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class SendMessageRequest(
	  /** Flag for testing the request without actually delivering the message. */
		validateOnly: Option[Boolean] = None,
	  /** Required. Message to send. */
		message: Option[Schema.Message] = None
	)
	
	case class Message(
	  /** Output Only. The identifier of the message sent, in the format of `projects/&#42;/messages/{message_id}`. */
		name: Option[String] = None,
	  /** Registration token to send a message to. */
		token: Option[String] = None,
	  /** Topic name to send a message to, e.g. "weather". Note: "/topics/" prefix should not be provided. */
		topic: Option[String] = None,
	  /** Condition to send a message to, e.g. "'foo' in topics && 'bar' in topics". */
		condition: Option[String] = None,
	  /** Input only. Arbitrary key/value payload, which must be UTF-8 encoded. The key should not be a reserved word ("from", "message_type", or any word starting with "google." or "gcm.notification."). When sending payloads containing only data fields to iOS devices, only normal priority (`"apns-priority": "5"`) is allowed in [`ApnsConfig`](/docs/reference/fcm/rest/v1/projects.messages#apnsconfig). */
		data: Option[Map[String, String]] = None,
	  /** Input only. Basic notification template to use across all platforms. */
		notification: Option[Schema.Notification] = None,
	  /** Input only. Android specific options for messages sent through [FCM connection server](https://goo.gl/4GLdUl). */
		android: Option[Schema.AndroidConfig] = None,
	  /** Input only. [Webpush protocol](https://tools.ietf.org/html/rfc8030) options. */
		webpush: Option[Schema.WebpushConfig] = None,
	  /** Input only. [Apple Push Notification Service](https://goo.gl/MXRTPa) specific options. */
		apns: Option[Schema.ApnsConfig] = None,
	  /** Input only. Template for FCM SDK feature options to use across all platforms. */
		fcmOptions: Option[Schema.FcmOptions] = None
	)
	
	case class Notification(
	  /** The notification's title. */
		title: Option[String] = None,
	  /** The notification's body text. */
		body: Option[String] = None,
	  /** Contains the URL of an image that is going to be downloaded on the device and displayed in a notification. JPEG, PNG, BMP have full support across platforms. Animated GIF and video only work on iOS. WebP and HEIF have varying levels of support across platforms and platform versions. Android has 1MB image size limit. Quota usage and implications/costs for hosting image on Firebase Storage: https://firebase.google.com/pricing */
		image: Option[String] = None
	)
	
	object AndroidConfig {
		enum PriorityEnum extends Enum[PriorityEnum] { case NORMAL, HIGH }
	}
	case class AndroidConfig(
	  /** An identifier of a group of messages that can be collapsed, so that only the last message gets sent when delivery can be resumed. A maximum of 4 different collapse keys is allowed at any given time. */
		collapseKey: Option[String] = None,
	  /** Message priority. Can take "normal" and "high" values. For more information, see [Setting the priority of a message](https://goo.gl/GjONJv). */
		priority: Option[Schema.AndroidConfig.PriorityEnum] = None,
	  /** How long (in seconds) the message should be kept in FCM storage if the device is offline. The maximum time to live supported is 4 weeks, and the default value is 4 weeks if not set. Set it to 0 if want to send the message immediately. In JSON format, the Duration type is encoded as a string rather than an object, where the string ends in the suffix "s" (indicating seconds) and is preceded by the number of seconds, with nanoseconds expressed as fractional seconds. For example, 3 seconds with 0 nanoseconds should be encoded in JSON format as "3s", while 3 seconds and 1 nanosecond should be expressed in JSON format as "3.000000001s". The ttl will be rounded down to the nearest second. */
		ttl: Option[String] = None,
	  /** Package name of the application where the registration token must match in order to receive the message. */
		restrictedPackageName: Option[String] = None,
	  /** Arbitrary key/value payload. If present, it will override google.firebase.fcm.v1.Message.data. */
		data: Option[Map[String, String]] = None,
	  /** Notification to send to android devices. */
		notification: Option[Schema.AndroidNotification] = None,
	  /** Options for features provided by the FCM SDK for Android. */
		fcmOptions: Option[Schema.AndroidFcmOptions] = None,
	  /** If set to true, messages will be allowed to be delivered to the app while the device is in direct boot mode. See [Support Direct Boot mode](https://developer.android.com/training/articles/direct-boot). */
		directBootOk: Option[Boolean] = None
	)
	
	object AndroidNotification {
		enum NotificationPriorityEnum extends Enum[NotificationPriorityEnum] { case PRIORITY_UNSPECIFIED, PRIORITY_MIN, PRIORITY_LOW, PRIORITY_DEFAULT, PRIORITY_HIGH, PRIORITY_MAX }
		enum VisibilityEnum extends Enum[VisibilityEnum] { case VISIBILITY_UNSPECIFIED, PRIVATE, PUBLIC, SECRET }
		enum ProxyEnum extends Enum[ProxyEnum] { case PROXY_UNSPECIFIED, ALLOW, DENY, IF_PRIORITY_LOWERED }
	}
	case class AndroidNotification(
	  /** The notification's title. If present, it will override google.firebase.fcm.v1.Notification.title. */
		title: Option[String] = None,
	  /** The notification's body text. If present, it will override google.firebase.fcm.v1.Notification.body. */
		body: Option[String] = None,
	  /** The notification's icon. Sets the notification icon to myicon for drawable resource myicon. If you don't send this key in the request, FCM displays the launcher icon specified in your app manifest. */
		icon: Option[String] = None,
	  /** The notification's icon color, expressed in #rrggbb format. */
		color: Option[String] = None,
	  /** The sound to play when the device receives the notification. Supports "default" or the filename of a sound resource bundled in the app. Sound files must reside in /res/raw/. */
		sound: Option[String] = None,
	  /** Identifier used to replace existing notifications in the notification drawer. If not specified, each request creates a new notification. If specified and a notification with the same tag is already being shown, the new notification replaces the existing one in the notification drawer. */
		tag: Option[String] = None,
	  /** The action associated with a user click on the notification. If specified, an activity with a matching intent filter is launched when a user clicks on the notification. */
		clickAction: Option[String] = None,
	  /** The key to the body string in the app's string resources to use to localize the body text to the user's current localization. See [String Resources](https://goo.gl/NdFZGI) for more information. */
		bodyLocKey: Option[String] = None,
	  /** Variable string values to be used in place of the format specifiers in body_loc_key to use to localize the body text to the user's current localization. See [Formatting and Styling](https://goo.gl/MalYE3) for more information. */
		bodyLocArgs: Option[List[String]] = None,
	  /** The key to the title string in the app's string resources to use to localize the title text to the user's current localization. See [String Resources](https://goo.gl/NdFZGI) for more information. */
		titleLocKey: Option[String] = None,
	  /** Variable string values to be used in place of the format specifiers in title_loc_key to use to localize the title text to the user's current localization. See [Formatting and Styling](https://goo.gl/MalYE3) for more information. */
		titleLocArgs: Option[List[String]] = None,
	  /** The [notification's channel id](https://developer.android.com/guide/topics/ui/notifiers/notifications#ManageChannels) (new in Android O). The app must create a channel with this channel ID before any notification with this channel ID is received. If you don't send this channel ID in the request, or if the channel ID provided has not yet been created by the app, FCM uses the channel ID specified in the app manifest. */
		channelId: Option[String] = None,
	  /** Sets the "ticker" text, which is sent to accessibility services. Prior to API level 21 (`Lollipop`), sets the text that is displayed in the status bar when the notification first arrives. */
		ticker: Option[String] = None,
	  /** When set to false or unset, the notification is automatically dismissed when the user clicks it in the panel. When set to true, the notification persists even when the user clicks it. */
		sticky: Option[Boolean] = None,
	  /** Set the time that the event in the notification occurred. Notifications in the panel are sorted by this time. A point in time is represented using [protobuf.Timestamp](https://developers.google.com/protocol-buffers/docs/reference/java/com/google/protobuf/Timestamp). */
		eventTime: Option[String] = None,
	  /** Set whether or not this notification is relevant only to the current device. Some notifications can be bridged to other devices for remote display, such as a Wear OS watch. This hint can be set to recommend this notification not be bridged. See [Wear OS guides](https://developer.android.com/training/wearables/notifications/bridger#existing-method-of-preventing-bridging) */
		localOnly: Option[Boolean] = None,
	  /** Set the relative priority for this notification. Priority is an indication of how much of the user's attention should be consumed by this notification. Low-priority notifications may be hidden from the user in certain situations, while the user might be interrupted for a higher-priority notification. The effect of setting the same priorities may differ slightly on different platforms. Note this priority differs from `AndroidMessagePriority`. This priority is processed by the client after the message has been delivered, whereas [AndroidMessagePriority](https://firebase.google.com/docs/reference/fcm/rest/v1/projects.messages#androidmessagepriority) is an FCM concept that controls when the message is delivered. */
		notificationPriority: Option[Schema.AndroidNotification.NotificationPriorityEnum] = None,
	  /** If set to true, use the Android framework's default sound for the notification. Default values are specified in [config.xml](https://android.googlesource.com/platform/frameworks/base/+/master/core/res/res/values/config.xml). */
		defaultSound: Option[Boolean] = None,
	  /** If set to true, use the Android framework's default vibrate pattern for the notification. Default values are specified in [config.xml](https://android.googlesource.com/platform/frameworks/base/+/master/core/res/res/values/config.xml). If `default_vibrate_timings` is set to true and `vibrate_timings` is also set, the default value is used instead of the user-specified `vibrate_timings`. */
		defaultVibrateTimings: Option[Boolean] = None,
	  /** If set to true, use the Android framework's default LED light settings for the notification. Default values are specified in [config.xml](https://android.googlesource.com/platform/frameworks/base/+/master/core/res/res/values/config.xml). If `default_light_settings` is set to true and `light_settings` is also set, the user-specified `light_settings` is used instead of the default value. */
		defaultLightSettings: Option[Boolean] = None,
	  /** Set the vibration pattern to use. Pass in an array of [protobuf.Duration](https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.Duration) to turn on or off the vibrator. The first value indicates the `Duration` to wait before turning the vibrator on. The next value indicates the `Duration` to keep the vibrator on. Subsequent values alternate between `Duration` to turn the vibrator off and to turn the vibrator on. If `vibrate_timings` is set and `default_vibrate_timings` is set to `true`, the default value is used instead of the user-specified `vibrate_timings`. */
		vibrateTimings: Option[List[String]] = None,
	  /** Set the [Notification.visibility](https://developer.android.com/reference/android/app/Notification.html#visibility) of the notification. */
		visibility: Option[Schema.AndroidNotification.VisibilityEnum] = None,
	  /** Sets the number of items this notification represents. May be displayed as a badge count for launchers that support badging.See [Notification Badge](https://developer.android.com/training/notify-user/badges). For example, this might be useful if you're using just one notification to represent multiple new messages but you want the count here to represent the number of total new messages. If zero or unspecified, systems that support badging use the default, which is to increment a number displayed on the long-press menu each time a new notification arrives. */
		notificationCount: Option[Int] = None,
	  /** Settings to control the notification's LED blinking rate and color if LED is available on the device. The total blinking time is controlled by the OS. */
		lightSettings: Option[Schema.LightSettings] = None,
	  /** Contains the URL of an image that is going to be displayed in a notification. If present, it will override google.firebase.fcm.v1.Notification.image. */
		image: Option[String] = None,
	  /** If set, display notifications delivered to the device will be handled by the app instead of the proxy. */
		bypassProxyNotification: Option[Boolean] = None,
	  /** Setting to control when a notification may be proxied. */
		proxy: Option[Schema.AndroidNotification.ProxyEnum] = None
	)
	
	case class LightSettings(
	  /** Required. Set `color` of the LED with [google.type.Color](https://github.com/googleapis/googleapis/blob/master/google/type/color.proto). */
		color: Option[Schema.Color] = None,
	  /** Required. Along with `light_off_duration`, define the blink rate of LED flashes. Resolution defined by [proto.Duration](https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.Duration) */
		lightOnDuration: Option[String] = None,
	  /** Required. Along with `light_on_duration `, define the blink rate of LED flashes. Resolution defined by [proto.Duration](https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.Duration) */
		lightOffDuration: Option[String] = None
	)
	
	case class Color(
	  /** The amount of red in the color as a value in the interval [0, 1]. */
		red: Option[BigDecimal] = None,
	  /** The amount of green in the color as a value in the interval [0, 1]. */
		green: Option[BigDecimal] = None,
	  /** The amount of blue in the color as a value in the interval [0, 1]. */
		blue: Option[BigDecimal] = None,
	  /** The fraction of this color that should be applied to the pixel. That is, the final pixel color is defined by the equation: `pixel color = alpha &#42; (this color) + (1.0 - alpha) &#42; (background color)` This means that a value of 1.0 corresponds to a solid color, whereas a value of 0.0 corresponds to a completely transparent color. This uses a wrapper message rather than a simple float scalar so that it is possible to distinguish between a default value and the value being unset. If omitted, this color object is rendered as a solid color (as if the alpha value had been explicitly given a value of 1.0). */
		alpha: Option[BigDecimal] = None
	)
	
	case class AndroidFcmOptions(
	  /** Label associated with the message's analytics data. */
		analyticsLabel: Option[String] = None
	)
	
	case class WebpushConfig(
	  /** HTTP headers defined in webpush protocol. Refer to [Webpush protocol](https://tools.ietf.org/html/rfc8030#section-5) for supported headers, e.g. "TTL": "15". */
		headers: Option[Map[String, String]] = None,
	  /** Arbitrary key/value payload. If present, it will override google.firebase.fcm.v1.Message.data. */
		data: Option[Map[String, String]] = None,
	  /** Web Notification options as a JSON object. Supports Notification instance properties as defined in [Web Notification API](https://developer.mozilla.org/en-US/docs/Web/API/Notification). If present, "title" and "body" fields override [google.firebase.fcm.v1.Notification.title] and [google.firebase.fcm.v1.Notification.body]. */
		notification: Option[Map[String, JsValue]] = None,
	  /** Options for features provided by the FCM SDK for Web. */
		fcmOptions: Option[Schema.WebpushFcmOptions] = None
	)
	
	case class WebpushFcmOptions(
	  /** The link to open when the user clicks on the notification. For all URL values, HTTPS is required. */
		link: Option[String] = None,
	  /** Label associated with the message's analytics data. */
		analyticsLabel: Option[String] = None
	)
	
	case class ApnsConfig(
	  /** HTTP request headers defined in Apple Push Notification Service. Refer to [APNs request headers](https://developer.apple.com/documentation/usernotifications/setting_up_a_remote_notification_server/sending_notification_requests_to_apns) for supported headers such as `apns-expiration` and `apns-priority`. The backend sets a default value for `apns-expiration` of 30 days and a default value for `apns-priority` of 10 if not explicitly set. */
		headers: Option[Map[String, String]] = None,
	  /** APNs payload as a JSON object, including both `aps` dictionary and custom payload. See [Payload Key Reference](https://developer.apple.com/documentation/usernotifications/setting_up_a_remote_notification_server/generating_a_remote_notification). If present, it overrides google.firebase.fcm.v1.Notification.title and google.firebase.fcm.v1.Notification.body. */
		payload: Option[Map[String, JsValue]] = None,
	  /** Options for features provided by the FCM SDK for iOS. */
		fcmOptions: Option[Schema.ApnsFcmOptions] = None,
	  /** Optional. [Apple Live Activity](https://developer.apple.com/design/human-interface-guidelines/live-activities) token to send updates to. This token can either be a push token or [push-to-start](https://developer.apple.com/documentation/activitykit/activity/pushtostarttoken) token from Apple. To start, update, or end a live activity remotely using FCM, construct an [`aps payload`](https://developer.apple.com/documentation/activitykit/starting-and-updating-live-activities-with-activitykit-push-notifications#Construct-the-payload-that-starts-a-Live-Activity) and put it in the [`apns.payload`](https://firebase.google.com/docs/reference/fcm/rest/v1/projects.messages#ApnsConfig) field. */
		liveActivityToken: Option[String] = None
	)
	
	case class ApnsFcmOptions(
	  /** Label associated with the message's analytics data. */
		analyticsLabel: Option[String] = None,
	  /** Contains the URL of an image that is going to be displayed in a notification. If present, it will override google.firebase.fcm.v1.Notification.image. */
		image: Option[String] = None
	)
	
	case class FcmOptions(
	  /** Label associated with the message's analytics data. */
		analyticsLabel: Option[String] = None
	)
}
