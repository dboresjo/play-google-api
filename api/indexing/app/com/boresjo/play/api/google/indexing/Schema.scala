package com.boresjo.play.api.google.indexing

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object UrlNotification {
		enum TypeEnum extends Enum[TypeEnum] { case URL_NOTIFICATION_TYPE_UNSPECIFIED, URL_UPDATED, URL_DELETED }
	}
	case class UrlNotification(
	  /** The object of this notification. The URL must be owned by the publisher of this notification and, in case of `URL_UPDATED` notifications, it _must_ be crawlable by Google. */
		url: Option[String] = None,
	  /** The URL life cycle event that Google is being notified about. */
		`type`: Option[Schema.UrlNotification.TypeEnum] = None,
	  /** Creation timestamp for this notification. Users should _not_ specify it, the field is ignored at the request time. */
		notifyTime: Option[String] = None
	)
	
	case class PublishUrlNotificationResponse(
	  /** Description of the notification events received for this URL. */
		urlNotificationMetadata: Option[Schema.UrlNotificationMetadata] = None
	)
	
	case class UrlNotificationMetadata(
	  /** URL to which this metadata refers. */
		url: Option[String] = None,
	  /** Latest notification received with type `URL_UPDATED`. */
		latestUpdate: Option[Schema.UrlNotification] = None,
	  /** Latest notification received with type `URL_REMOVED`. */
		latestRemove: Option[Schema.UrlNotification] = None
	)
}
