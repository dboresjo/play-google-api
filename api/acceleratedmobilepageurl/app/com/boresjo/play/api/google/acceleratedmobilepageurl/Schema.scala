package com.boresjo.play.api.google.acceleratedmobilepageurl

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object BatchGetAmpUrlsRequest {
		enum LookupStrategyEnum extends Enum[LookupStrategyEnum] { case FETCH_LIVE_DOC, IN_INDEX_DOC }
	}
	case class BatchGetAmpUrlsRequest(
	  /** List of URLs to look up for the paired AMP URLs. The URLs are case-sensitive. Up to 50 URLs per lookup (see [Usage Limits](/amp/cache/reference/limits)). */
		urls: Option[List[String]] = None,
	  /** The lookup_strategy being requested. */
		lookupStrategy: Option[Schema.BatchGetAmpUrlsRequest.LookupStrategyEnum] = None
	)
	
	case class BatchGetAmpUrlsResponse(
	  /** For each URL in BatchAmpUrlsRequest, the URL response. The response might not be in the same order as URLs in the batch request. If BatchAmpUrlsRequest contains duplicate URLs, AmpUrl is generated only once. */
		ampUrls: Option[List[Schema.AmpUrl]] = None,
	  /** The errors for requested URLs that have no AMP URL. */
		urlErrors: Option[List[Schema.AmpUrlError]] = None
	)
	
	case class AmpUrl(
	  /** The original non-AMP URL. */
		originalUrl: Option[String] = None,
	  /** The AMP URL pointing to the publisher's web server. */
		ampUrl: Option[String] = None,
	  /** The [AMP Cache URL](/amp/cache/overview#amp-cache-url-format) pointing to the cached document in the Google AMP Cache. */
		cdnAmpUrl: Option[String] = None
	)
	
	object AmpUrlError {
		enum ErrorCodeEnum extends Enum[ErrorCodeEnum] { case ERROR_CODE_UNSPECIFIED, INPUT_URL_NOT_FOUND, NO_AMP_URL, APPLICATION_ERROR, URL_IS_VALID_AMP, URL_IS_INVALID_AMP }
	}
	case class AmpUrlError(
	  /** The error code of an API call. */
		errorCode: Option[Schema.AmpUrlError.ErrorCodeEnum] = None,
	  /** An optional descriptive error message. */
		errorMessage: Option[String] = None,
	  /** The original non-AMP URL. */
		originalUrl: Option[String] = None
	)
}
