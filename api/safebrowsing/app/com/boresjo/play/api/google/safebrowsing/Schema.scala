package com.boresjo.play.api.google.safebrowsing

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object GoogleSecuritySafebrowsingV5FullHashFullHashDetail {
		enum ThreatTypeEnum extends Enum[ThreatTypeEnum] { case THREAT_TYPE_UNSPECIFIED, MALWARE, SOCIAL_ENGINEERING, UNWANTED_SOFTWARE, POTENTIALLY_HARMFUL_APPLICATION }
		enum AttributesEnum extends Enum[AttributesEnum] { case THREAT_ATTRIBUTE_UNSPECIFIED, CANARY, FRAME_ONLY }
	}
	case class GoogleSecuritySafebrowsingV5FullHashFullHashDetail(
	  /** The type of threat. This field will never be empty. */
		threatType: Option[Schema.GoogleSecuritySafebrowsingV5FullHashFullHashDetail.ThreatTypeEnum] = None,
	  /** Unordered list. Additional attributes about those full hashes. This may be empty. */
		attributes: Option[List[Schema.GoogleSecuritySafebrowsingV5FullHashFullHashDetail.AttributesEnum]] = None
	)
	
	case class GoogleSecuritySafebrowsingV5SearchHashesResponse(
	  /** The client-side cache duration. The client MUST add this duration to the current time to determine the expiration time. The expiration time then applies to every hash prefix queried by the client in the request, regardless of how many full hashes are returned in the response. Even if the server returns no full hashes for a particular hash prefix, this fact MUST also be cached by the client. If and only if the field `full_hashes` is empty, the client MAY increase the `cache_duration` to determine a new expiration that is later than that specified by the server. In any case, the increased cache duration must not be longer than 24 hours. Important: the client MUST NOT assume that the server will return the same cache duration for all responses. The server MAY choose different cache durations for different responses depending on the situation. */
		cacheDuration: Option[String] = None,
	  /** Unordered list. The unordered list of full hashes found. */
		fullHashes: Option[List[Schema.GoogleSecuritySafebrowsingV5FullHash]] = None
	)
	
	case class GoogleSecuritySafebrowsingV5FullHash(
	  /** The matching full hash. This is the SHA256 hash. The length will be exactly 32 bytes. */
		fullHash: Option[String] = None,
	  /** Unordered list. A repeated field identifying the details relevant to this full hash. */
		fullHashDetails: Option[List[Schema.GoogleSecuritySafebrowsingV5FullHashFullHashDetail]] = None
	)
}
