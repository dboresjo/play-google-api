package com.boresjo.play.api.google.siteVerification

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object SiteVerificationWebResourceGettokenRequest {
		case class SiteItem(
		  /** The site identifier. If the type is set to SITE, the identifier is a URL. If the type is set to INET_DOMAIN, the site identifier is a domain name. */
			identifier: Option[String] = None,
		  /** The type of resource to be verified. Can be SITE or INET_DOMAIN (domain name). */
			`type`: Option[String] = None
		)
	}
	case class SiteVerificationWebResourceGettokenRequest(
	  /** The site for which a verification token will be generated. */
		site: Option[Schema.SiteVerificationWebResourceGettokenRequest.SiteItem] = None,
	  /** The verification method that will be used to verify this site. For sites, 'FILE' or 'META' methods may be used. For domains, only 'DNS' may be used. */
		verificationMethod: Option[String] = None
	)
	
	case class SiteVerificationWebResourceGettokenResponse(
	  /** The verification method to use in conjunction with this token. For FILE, the token should be placed in the top-level directory of the site, stored inside a file of the same name. For META, the token should be placed in the HEAD tag of the default page that is loaded for the site. For DNS, the token should be placed in a TXT record of the domain. */
		method: Option[String] = None,
	  /** The verification token. The token must be placed appropriately in order for verification to succeed. */
		token: Option[String] = None
	)
	
	case class SiteVerificationWebResourceListResponse(
	  /** The list of sites that are owned by the authenticated user. */
		items: Option[List[Schema.SiteVerificationWebResourceResource]] = None
	)
	
	object SiteVerificationWebResourceResource {
		case class SiteItem(
		  /** The site identifier. If the type is set to SITE, the identifier is a URL. If the type is set to INET_DOMAIN, the site identifier is a domain name. */
			identifier: Option[String] = None,
		  /** The site type. Can be SITE or INET_DOMAIN (domain name). */
			`type`: Option[String] = None
		)
	}
	case class SiteVerificationWebResourceResource(
	  /** The string used to identify this site. This value should be used in the "id" portion of the REST URL for the Get, Update, and Delete operations. */
		id: Option[String] = None,
	  /** The email addresses of all verified owners. */
		owners: Option[List[String]] = None,
	  /** The address and type of a site that is verified or will be verified. */
		site: Option[Schema.SiteVerificationWebResourceResource.SiteItem] = None
	)
}
