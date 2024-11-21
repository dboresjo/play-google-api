package com.boresjo.play.api.google.essentialcontacts

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleProtobufEmpty(
	
	)
	
	object GoogleCloudEssentialcontactsV1SendTestMessageRequest {
		enum NotificationCategoryEnum extends Enum[NotificationCategoryEnum] { case NOTIFICATION_CATEGORY_UNSPECIFIED, ALL, SUSPENSION, SECURITY, TECHNICAL, BILLING, LEGAL, PRODUCT_UPDATES, TECHNICAL_INCIDENTS }
	}
	case class GoogleCloudEssentialcontactsV1SendTestMessageRequest(
	  /** Required. The notification category to send the test message for. All contacts must be subscribed to this category. */
		notificationCategory: Option[Schema.GoogleCloudEssentialcontactsV1SendTestMessageRequest.NotificationCategoryEnum] = None,
	  /** Required. The list of names of the contacts to send a test message to. Format: organizations/{organization_id}/contacts/{contact_id}, folders/{folder_id}/contacts/{contact_id} or projects/{project_id}/contacts/{contact_id} */
		contacts: Option[List[String]] = None
	)
	
	case class GoogleCloudEssentialcontactsV1ListContactsResponse(
	  /** If there are more results than those appearing in this response, then `next_page_token` is included. To get the next set of results, call this method again using the value of `next_page_token` as `page_token` and the rest of the parameters the same as the original request. */
		nextPageToken: Option[String] = None,
	  /** The contacts for the specified resource. */
		contacts: Option[List[Schema.GoogleCloudEssentialcontactsV1Contact]] = None
	)
	
	case class GoogleCloudEssentialcontactsV1ComputeContactsResponse(
	  /** All contacts for the resource that are subscribed to the specified notification categories, including contacts inherited from any parent resources. */
		contacts: Option[List[Schema.GoogleCloudEssentialcontactsV1Contact]] = None,
	  /** If there are more results than those appearing in this response, then `next_page_token` is included. To get the next set of results, call this method again using the value of `next_page_token` as `page_token` and the rest of the parameters the same as the original request. */
		nextPageToken: Option[String] = None
	)
	
	object GoogleCloudEssentialcontactsV1Contact {
		enum NotificationCategorySubscriptionsEnum extends Enum[NotificationCategorySubscriptionsEnum] { case NOTIFICATION_CATEGORY_UNSPECIFIED, ALL, SUSPENSION, SECURITY, TECHNICAL, BILLING, LEGAL, PRODUCT_UPDATES, TECHNICAL_INCIDENTS }
		enum ValidationStateEnum extends Enum[ValidationStateEnum] { case VALIDATION_STATE_UNSPECIFIED, VALID, INVALID }
	}
	case class GoogleCloudEssentialcontactsV1Contact(
	  /** Required. The preferred language for notifications, as a ISO 639-1 language code. See [Supported languages](https://cloud.google.com/resource-manager/docs/managing-notification-contacts#supported-languages) for a list of supported languages. */
		languageTag: Option[String] = None,
	  /** Output only. The identifier for the contact. Format: {resource_type}/{resource_id}/contacts/{contact_id} */
		name: Option[String] = None,
	  /** The last time the validation_state was updated, either manually or automatically. A contact is considered stale if its validation state was updated more than 1 year ago. */
		validateTime: Option[String] = None,
	  /** Required. The email address to send notifications to. The email address does not need to be a Google Account. */
		email: Option[String] = None,
	  /** Required. The categories of notifications that the contact will receive communications for. */
		notificationCategorySubscriptions: Option[List[Schema.GoogleCloudEssentialcontactsV1Contact.NotificationCategorySubscriptionsEnum]] = None,
	  /** Output only. The validity of the contact. A contact is considered valid if it is the correct recipient for notifications for a particular resource. */
		validationState: Option[Schema.GoogleCloudEssentialcontactsV1Contact.ValidationStateEnum] = None
	)
}
