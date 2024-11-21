package com.boresjo.play.api.google.webrisk

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleLongrunningCancelOperationRequest(
	
	)
	
	case class GoogleCloudWebriskV1RiceDeltaEncoding(
	  /** The number of entries that are delta encoded in the encoded data. If only a single integer was encoded, this will be zero and the single value will be stored in `first_value`. */
		entryCount: Option[Int] = None,
	  /** The offset of the first entry in the encoded data, or, if only a single integer was encoded, that single integer's value. If the field is empty or missing, assume zero. */
		firstValue: Option[String] = None,
	  /** The Golomb-Rice parameter, which is a number between 2 and 28. This field is missing (that is, zero) if `num_entries` is zero. */
		riceParameter: Option[Int] = None,
	  /** The encoded deltas that are encoded using the Golomb-Rice coder. */
		encodedData: Option[String] = None
	)
	
	case class GoogleCloudWebriskV1Submission(
	  /** Required. The URI that is being reported for malicious content to be analyzed. */
		uri: Option[String] = None
	)
	
	case class GoogleProtobufEmpty(
	
	)
	
	case class GoogleCloudWebriskV1ComputeThreatListDiffResponseChecksum(
	  /** The SHA256 hash of the client state; that is, of the sorted list of all hashes present in the database. */
		sha256: Option[String] = None
	)
	
	case class GoogleLongrunningListOperationsResponse(
	  /** A list of operations that matches the specified filter in the request. */
		operations: Option[List[Schema.GoogleLongrunningOperation]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudWebriskV1ThreatEntryAdditions(
	  /** The encoded 4-byte prefixes of SHA256-formatted entries, using a Golomb-Rice encoding. The hashes are converted to uint32, sorted in ascending order, then delta encoded and stored as encoded_data. */
		riceHashes: Option[Schema.GoogleCloudWebriskV1RiceDeltaEncoding] = None,
	  /** The raw SHA256-formatted entries. Repeated to allow returning sets of hashes with different prefix sizes. */
		rawHashes: Option[List[Schema.GoogleCloudWebriskV1RawHashes]] = None
	)
	
	case class GoogleLongrunningOperation(
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.GoogleRpcStatus] = None,
	  /** Contains a `SubmitUriMetadata` object. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** Matches the `/v1/{project-name}/operations/{operation-id}` pattern. */
		name: Option[String] = None
	)
	
	case class GoogleCloudWebriskV1ThreatEntryRemovals(
	  /** The raw removal indices for a local list. */
		rawIndices: Option[Schema.GoogleCloudWebriskV1RawIndices] = None,
	  /** The encoded local, lexicographically-sorted list indices, using a Golomb-Rice encoding. Used for sending compressed removal indices. The removal indices (uint32) are sorted in ascending order, then delta encoded and stored as encoded_data. */
		riceIndices: Option[Schema.GoogleCloudWebriskV1RiceDeltaEncoding] = None
	)
	
	case class GoogleCloudWebriskV1SearchUrisResponse(
	  /** The threat list matches. This might be empty if the URI is on no list. */
		threat: Option[Schema.GoogleCloudWebriskV1SearchUrisResponseThreatUri] = None
	)
	
	case class GoogleRpcStatus(
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None,
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None
	)
	
	case class GoogleCloudWebriskV1RawHashes(
	  /** The hashes, in binary format, concatenated into one long string. Hashes are sorted in lexicographic order. For JSON API users, hashes are base64-encoded. */
		rawHashes: Option[String] = None,
	  /** The number of bytes for each prefix encoded below. This field can be anywhere from 4 (shortest prefix) to 32 (full SHA256 hash). In practice this is almost always 4, except in exceptional circumstances. */
		prefixSize: Option[Int] = None
	)
	
	case class GoogleCloudWebriskV1SearchHashesResponse(
	  /** For requested entities that did not match the threat list, how long to cache the response until. */
		negativeExpireTime: Option[String] = None,
	  /** The full hashes that matched the requested prefixes. The hash will be populated in the key. */
		threats: Option[List[Schema.GoogleCloudWebriskV1SearchHashesResponseThreatHash]] = None
	)
	
	object GoogleCloudWebriskV1ComputeThreatListDiffResponse {
		enum ResponseTypeEnum extends Enum[ResponseTypeEnum] { case RESPONSE_TYPE_UNSPECIFIED, DIFF, RESET }
	}
	case class GoogleCloudWebriskV1ComputeThreatListDiffResponse(
	  /** A set of entries to add to a local threat type's list. */
		additions: Option[Schema.GoogleCloudWebriskV1ThreatEntryAdditions] = None,
	  /** The type of response. This may indicate that an action must be taken by the client when the response is received. */
		responseType: Option[Schema.GoogleCloudWebriskV1ComputeThreatListDiffResponse.ResponseTypeEnum] = None,
	  /** A set of entries to remove from a local threat type's list. This field may be empty. */
		removals: Option[Schema.GoogleCloudWebriskV1ThreatEntryRemovals] = None,
	  /** The soonest the client should wait before issuing any diff request. Querying sooner is unlikely to produce a meaningful diff. Waiting longer is acceptable considering the use case. If this field is not set clients may update as soon as they want. */
		recommendedNextDiff: Option[String] = None,
	  /** The new opaque client version token. This should be retained by the client and passed into the next call of ComputeThreatListDiff as 'version_token'. A separate version token should be stored and used for each threatList. */
		newVersionToken: Option[String] = None,
	  /** The expected SHA256 hash of the client state; that is, of the sorted list of all hashes present in the database after applying the provided diff. If the client state doesn't match the expected state, the client must discard this diff and retry later. */
		checksum: Option[Schema.GoogleCloudWebriskV1ComputeThreatListDiffResponseChecksum] = None
	)
	
	object GoogleCloudWebriskV1SearchUrisResponseThreatUri {
		enum ThreatTypesEnum extends Enum[ThreatTypesEnum] { case THREAT_TYPE_UNSPECIFIED, MALWARE, SOCIAL_ENGINEERING, UNWANTED_SOFTWARE, SOCIAL_ENGINEERING_EXTENDED_COVERAGE }
	}
	case class GoogleCloudWebriskV1SearchUrisResponseThreatUri(
	  /** The ThreatList this threat belongs to. */
		threatTypes: Option[List[Schema.GoogleCloudWebriskV1SearchUrisResponseThreatUri.ThreatTypesEnum]] = None,
	  /** The cache lifetime for the returned match. Clients must not cache this response past this timestamp to avoid false positives. */
		expireTime: Option[String] = None
	)
	
	object GoogleCloudWebriskV1SearchHashesResponseThreatHash {
		enum ThreatTypesEnum extends Enum[ThreatTypesEnum] { case THREAT_TYPE_UNSPECIFIED, MALWARE, SOCIAL_ENGINEERING, UNWANTED_SOFTWARE, SOCIAL_ENGINEERING_EXTENDED_COVERAGE }
	}
	case class GoogleCloudWebriskV1SearchHashesResponseThreatHash(
	  /** The cache lifetime for the returned match. Clients must not cache this response past this timestamp to avoid false positives. */
		expireTime: Option[String] = None,
	  /** The ThreatList this threat belongs to. This must contain at least one entry. */
		threatTypes: Option[List[Schema.GoogleCloudWebriskV1SearchHashesResponseThreatHash.ThreatTypesEnum]] = None,
	  /** A 32 byte SHA256 hash. This field is in binary format. For JSON requests, hashes are base64-encoded. */
		hash: Option[String] = None
	)
	
	case class GoogleCloudWebriskV1RawIndices(
	  /** The indices to remove from a lexicographically-sorted local list. */
		indices: Option[List[Int]] = None
	)
}
