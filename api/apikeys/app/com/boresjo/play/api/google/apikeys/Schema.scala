package com.boresjo.play.api.google.apikeys

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class V2Restrictions(
	  /** The IP addresses of callers that are allowed to use the key. */
		serverKeyRestrictions: Option[Schema.V2ServerKeyRestrictions] = None,
	  /** The HTTP referrers (websites) that are allowed to use the key. */
		browserKeyRestrictions: Option[Schema.V2BrowserKeyRestrictions] = None,
	  /** The iOS apps that are allowed to use the key. */
		iosKeyRestrictions: Option[Schema.V2IosKeyRestrictions] = None,
	  /** A restriction for a specific service and optionally one or more specific methods. Requests are allowed if they match any of these restrictions. If no restrictions are specified, all targets are allowed. */
		apiTargets: Option[List[Schema.V2ApiTarget]] = None,
	  /** The Android apps that are allowed to use the key. */
		androidKeyRestrictions: Option[Schema.V2AndroidKeyRestrictions] = None
	)
	
	case class V2UndeleteKeyRequest(
	
	)
	
	case class V2AndroidKeyRestrictions(
	  /** A list of Android applications that are allowed to make API calls with this key. */
		allowedApplications: Option[List[Schema.V2AndroidApplication]] = None
	)
	
	case class Status(
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None,
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None
	)
	
	case class V2GetKeyStringResponse(
	  /** An encrypted and signed value of the key. */
		keyString: Option[String] = None
	)
	
	case class V2ServerKeyRestrictions(
	  /** A list of the caller IP addresses that are allowed to make API calls with this key. */
		allowedIps: Option[List[String]] = None
	)
	
	case class V2LookupKeyResponse(
	  /** The project that owns the key with the value specified in the request. */
		parent: Option[String] = None,
	  /** The resource name of the API key. If the API key has been purged, resource name is empty. */
		name: Option[String] = None
	)
	
	case class V2ApiTarget(
	  /** The service for this restriction. It should be the canonical service name, for example: `translate.googleapis.com`. You can use [`gcloud services list`](https://cloud.google.com/sdk/gcloud/reference/services/list) to get a list of services that are enabled in the project. */
		service: Option[String] = None,
	  /** Optional. List of one or more methods that can be called. If empty, all methods for the service are allowed. A wildcard (&#42;) can be used as the last symbol. Valid examples: `google.cloud.translate.v2.TranslateService.GetSupportedLanguage` `TranslateText` `Get&#42;` `translate.googleapis.com.Get&#42;` */
		methods: Option[List[String]] = None
	)
	
	case class Operation(
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.Status] = None,
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None
	)
	
	case class V2IosKeyRestrictions(
	  /** A list of bundle IDs that are allowed when making API calls with this key. */
		allowedBundleIds: Option[List[String]] = None
	)
	
	case class V2Key(
	  /** Output only. A timestamp when this key was deleted. If the resource is not deleted, this must be empty. */
		deleteTime: Option[String] = None,
	  /** Output only. A timestamp identifying the time this key was originally created. */
		createTime: Option[String] = None,
	  /** Annotations is an unstructured key-value map stored with a policy that may be set by external tools to store and retrieve arbitrary metadata. They are not queryable and should be preserved when modifying objects. */
		annotations: Option[Map[String, String]] = None,
	  /** Optional. The email address of [the service account](https://cloud.google.com/iam/docs/service-accounts) the key is bound to. */
		serviceAccountEmail: Option[String] = None,
	  /** Output only. The resource name of the key. The `name` has the form: `projects//locations/global/keys/`. For example: `projects/123456867718/locations/global/keys/b7ff1f9f-8275-410a-94dd-3855ee9b5dd2` NOTE: Key is a global resource; hence the only supported value for location is `global`. */
		name: Option[String] = None,
	  /** Human-readable display name of this key that you can modify. The maximum length is 63 characters. */
		displayName: Option[String] = None,
	  /** Output only. A checksum computed by the server based on the current value of the Key resource. This may be sent on update and delete requests to ensure the client has an up-to-date value before proceeding. See https://google.aip.dev/154. */
		etag: Option[String] = None,
	  /** Output only. An encrypted and signed value held by this key. This field can be accessed only through the `GetKeyString` method. */
		keyString: Option[String] = None,
	  /** Key restrictions. */
		restrictions: Option[Schema.V2Restrictions] = None,
	  /** Output only. A timestamp identifying the time this key was last updated. */
		updateTime: Option[String] = None,
	  /** Output only. Unique id in UUID4 format. */
		uid: Option[String] = None
	)
	
	case class V2AndroidApplication(
	  /** The package name of the application. */
		packageName: Option[String] = None,
	  /** The SHA1 fingerprint of the application. For example, both sha1 formats are acceptable : DA:39:A3:EE:5E:6B:4B:0D:32:55:BF:EF:95:60:18:90:AF:D8:07:09 or DA39A3EE5E6B4B0D3255BFEF95601890AFD80709. Output format is the latter. */
		sha1Fingerprint: Option[String] = None
	)
	
	case class V2ListKeysResponse(
	  /** The pagination token for the next page of results. */
		nextPageToken: Option[String] = None,
	  /** A list of API keys. */
		keys: Option[List[Schema.V2Key]] = None
	)
	
	case class V2BrowserKeyRestrictions(
	  /** A list of regular expressions for the referrer URLs that are allowed to make API calls with this key. */
		allowedReferrers: Option[List[String]] = None
	)
}
