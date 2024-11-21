package com.boresjo.play.api.google.marketingplatformadmin

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ListAnalyticsAccountLinksResponse(
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Analytics account links in this organization. */
		analyticsAccountLinks: Option[List[Schema.AnalyticsAccountLink]] = None
	)
	
	case class Empty(
	
	)
	
	object SetPropertyServiceLevelRequest {
		enum ServiceLevelEnum extends Enum[ServiceLevelEnum] { case ANALYTICS_SERVICE_LEVEL_UNSPECIFIED, ANALYTICS_SERVICE_LEVEL_STANDARD, ANALYTICS_SERVICE_LEVEL_360 }
	}
	case class SetPropertyServiceLevelRequest(
	  /** Required. The Analytics property to change the ServiceLevel setting. This field is the name of the Google Analytics Admin API property resource. Format: analyticsadmin.googleapis.com/properties/{property_id} */
		analyticsProperty: Option[String] = None,
	  /** Required. The service level to set for this property. */
		serviceLevel: Option[Schema.SetPropertyServiceLevelRequest.ServiceLevelEnum] = None
	)
	
	case class Organization(
	  /** The human-readable name for the organization. */
		displayName: Option[String] = None,
	  /** Identifier. The resource name of the GMP organization. Format: organizations/{org_id} */
		name: Option[String] = None
	)
	
	case class SetPropertyServiceLevelResponse(
	
	)
	
	object AnalyticsAccountLink {
		enum LinkVerificationStateEnum extends Enum[LinkVerificationStateEnum] { case LINK_VERIFICATION_STATE_UNSPECIFIED, LINK_VERIFICATION_STATE_VERIFIED, LINK_VERIFICATION_STATE_NOT_VERIFIED }
	}
	case class AnalyticsAccountLink(
	  /** Output only. The human-readable name for the Analytics account. */
		displayName: Option[String] = None,
	  /** Identifier. Resource name of this AnalyticsAccountLink. Note the resource ID is the same as the ID of the Analtyics account. Format: organizations/{org_id}/analyticsAccountLinks/{analytics_account_link_id} Example: "organizations/xyz/analyticsAccountLinks/1234" */
		name: Option[String] = None,
	  /** Output only. The verification state of the link between the Analytics account and the parent organization. */
		linkVerificationState: Option[Schema.AnalyticsAccountLink.LinkVerificationStateEnum] = None,
	  /** Required. Immutable. The resource name of the AnalyticsAdmin API account. The account ID will be used as the ID of this AnalyticsAccountLink resource, which will become the final component of the resource name. Format: analyticsadmin.googleapis.com/accounts/{account_id} */
		analyticsAccount: Option[String] = None
	)
}
