package com.boresjo.play.api.google.abusiveexperiencereport

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object SiteSummaryResponse {
		enum AbusiveStatusEnum extends Enum[AbusiveStatusEnum] { case UNKNOWN, PASSING, FAILING }
		enum FilterStatusEnum extends Enum[FilterStatusEnum] { case UNKNOWN, ON, OFF, PAUSED, PENDING }
	}
	case class SiteSummaryResponse(
	  /** The name of the reviewed site, e.g. `google.com`. */
		reviewedSite: Option[String] = None,
	  /** The time at which the site's status last changed. */
		lastChangeTime: Option[String] = None,
	  /** The site's Abusive Experience Report status. */
		abusiveStatus: Option[Schema.SiteSummaryResponse.AbusiveStatusEnum] = None,
	  /** Whether the site is currently under review. */
		underReview: Option[Boolean] = None,
	  /** The time at which [enforcement](https://support.google.com/webtools/answer/7538608) against the site began or will begin. Not set when the filter_status is OFF. */
		enforcementTime: Option[String] = None,
	  /** A link to the full Abusive Experience Report for the site. Not set in ViolatingSitesResponse. Note that you must complete the [Search Console verification process](https://support.google.com/webmasters/answer/9008080) for the site before you can access the full report. */
		reportUrl: Option[String] = None,
	  /** The site's [enforcement status](https://support.google.com/webtools/answer/7538608). */
		filterStatus: Option[Schema.SiteSummaryResponse.FilterStatusEnum] = None
	)
	
	case class ViolatingSitesResponse(
	  /** The list of violating sites. */
		violatingSites: Option[List[Schema.SiteSummaryResponse]] = None
	)
}
