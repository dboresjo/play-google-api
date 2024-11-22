package com.boresjo.play.api.google.readerrevenuesubscriptionlinking

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class Entitlement(
	  /** The detail field can carry a description of the SKU that corresponds to what the user has been granted access to. This description, which is opaque to Google, can be displayed in the Google user subscription console for users who linked the subscription to a Google Account. Max 80 character limit. */
		detail: Option[String] = None,
	  /** Required. Expiration time of the entitlement. Entitlements that have expired over 30 days will be purged. The max expire_time is 398 days from now(). */
		expireTime: Option[String] = None,
	  /** A source-specific subscription token. This is an opaque string that the publisher provides to Google. This token is opaque and has no meaning to Google. */
		subscriptionToken: Option[String] = None,
	  /** Required. The publication's product ID that the user has access to. This is the same product ID as can be found in Schema.org markup (http://schema.org/productID). E.g. "dailybugle.com:basic" */
		productId: Option[String] = None
	)
	
	case class DeleteReaderResponse(
	
	)
	
	case class ReaderEntitlements(
	  /** All of the entitlements for a publication reader. */
		entitlements: Option[List[Schema.Entitlement]] = None,
	  /** Output only. The resource name of the singleton. */
		name: Option[String] = None
	)
	
	case class Reader(
	  /** Output only. The resource name of the reader. The last part of ppid in the resource name is the publisher provided id. */
		name: Option[String] = None,
	  /** Output only. Time the publication reader was created and associated with a Google user. */
		createTime: Option[String] = None
	)
}
