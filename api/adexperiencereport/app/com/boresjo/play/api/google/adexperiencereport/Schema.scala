package com.boresjo.play.api.google.adexperiencereport

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class SiteSummaryResponse(
	  /** The name of the reviewed site, e.g. `google.com`. */
		reviewedSite: Option[String] = None,
	  /** The site's Ad Experience Report summary on mobile. */
		mobileSummary: Option[Schema.PlatformSummary] = None,
	  /** The site's Ad Experience Report summary on desktop. */
		desktopSummary: Option[Schema.PlatformSummary] = None
	)
	
	object PlatformSummary {
		enum RegionEnum extends Enum[RegionEnum] { case REGION_UNKNOWN, REGION_A, REGION_B, REGION_C }
		enum BetterAdsStatusEnum extends Enum[BetterAdsStatusEnum] { case UNKNOWN, PASSING, WARNING, FAILING }
		enum FilterStatusEnum extends Enum[FilterStatusEnum] { case UNKNOWN, ON, OFF, PAUSED, PENDING }
	}
	case class PlatformSummary(
	  /** The time at which the site's status last changed on this platform. */
		lastChangeTime: Option[String] = None,
	  /** The site's regions on this platform. No longer populated, because there is no longer any semantic difference between sites in different regions. */
		region: Option[List[Schema.PlatformSummary.RegionEnum]] = None,
	  /** The site's Ad Experience Report status on this platform. */
		betterAdsStatus: Option[Schema.PlatformSummary.BetterAdsStatusEnum] = None,
	  /** Whether the site is currently under review on this platform. */
		underReview: Option[Boolean] = None,
	  /** The time at which [enforcement](https://support.google.com/webtools/answer/7308033) against the site began or will begin on this platform. Not set when the filter_status is OFF. */
		enforcementTime: Option[String] = None,
	  /** A link to the full Ad Experience Report for the site on this platform.. Not set in ViolatingSitesResponse. Note that you must complete the [Search Console verification process](https://support.google.com/webmasters/answer/9008080) for the site before you can access the full report. */
		reportUrl: Option[String] = None,
	  /** The site's [enforcement status](https://support.google.com/webtools/answer/7308033) on this platform. */
		filterStatus: Option[Schema.PlatformSummary.FilterStatusEnum] = None
	)
	
	case class ViolatingSitesResponse(
	  /** The list of violating sites. */
		violatingSites: Option[List[Schema.SiteSummaryResponse]] = None
	)
}
