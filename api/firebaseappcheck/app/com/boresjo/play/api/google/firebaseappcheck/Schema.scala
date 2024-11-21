package com.boresjo.play.api.google.firebaseappcheck

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleFirebaseAppcheckV1BatchGetDeviceCheckConfigsResponse(
	  /** DeviceCheckConfigs retrieved. */
		configs: Option[List[Schema.GoogleFirebaseAppcheckV1DeviceCheckConfig]] = None
	)
	
	case class GoogleFirebaseAppcheckV1ListDebugTokensResponse(
	  /** If the result list is too large to fit in a single response, then a token is returned. If the string is empty or omitted, then this response is the last page of results. This token can be used in a subsequent call to ListDebugTokens to find the next group of DebugTokens. Page tokens are short-lived and should not be persisted. */
		nextPageToken: Option[String] = None,
	  /** The DebugTokens retrieved. */
		debugTokens: Option[List[Schema.GoogleFirebaseAppcheckV1DebugToken]] = None
	)
	
	case class GoogleFirebaseAppcheckV1ExchangeCustomTokenRequest(
	  /** Required. A custom token signed using your project's Admin SDK service account credentials. */
		customToken: Option[String] = None,
	  /** Specifies whether this attestation is for use in a &#42;limited use&#42; (`true`) or &#42;session based&#42; (`false`) context. To enable this attestation to be used with the &#42;replay protection&#42; feature, set this to `true`. The default value is `false`. */
		limitedUse: Option[Boolean] = None
	)
	
	case class GoogleFirebaseAppcheckV1GenerateAppAttestChallengeResponse(
	  /** The duration from the time this challenge is minted until its expiration. This field is intended to ease client-side token management, since the client may have clock skew, but is still able to accurately measure a duration. */
		ttl: Option[String] = None,
	  /** A one-time use challenge for the client to pass to the App Attest API. */
		challenge: Option[String] = None
	)
	
	case class GoogleFirebaseAppcheckV1GeneratePlayIntegrityChallengeRequest(
	
	)
	
	case class GoogleFirebaseAppcheckV1ExchangeDebugTokenRequest(
	  /** Specifies whether this attestation is for use in a &#42;limited use&#42; (`true`) or &#42;session based&#42; (`false`) context. To enable this attestation to be used with the &#42;replay protection&#42; feature, set this to `true`. The default value is `false`. */
		limitedUse: Option[Boolean] = None,
	  /** Required. A debug token secret. This string must match a debug token secret previously created using CreateDebugToken. */
		debugToken: Option[String] = None
	)
	
	case class GoogleProtobufEmpty(
	
	)
	
	case class GoogleFirebaseAppcheckV1ListServicesResponse(
	  /** The Services retrieved. */
		services: Option[List[Schema.GoogleFirebaseAppcheckV1Service]] = None,
	  /** If the result list is too large to fit in a single response, then a token is returned. If the string is empty or omitted, then this response is the last page of results. This token can be used in a subsequent call to ListServices to find the next group of Services. Page tokens are short-lived and should not be persisted. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleFirebaseAppcheckV1ExchangeRecaptchaV3TokenRequest(
	  /** Required. The reCAPTCHA token as returned by the [reCAPTCHA v3 JavaScript API](https://developers.google.com/recaptcha/docs/v3). */
		recaptchaV3Token: Option[String] = None,
	  /** Specifies whether this attestation is for use in a &#42;limited use&#42; (`true`) or &#42;session based&#42; (`false`) context. To enable this attestation to be used with the &#42;replay protection&#42; feature, set this to `true`. The default value is `false`. */
		limitedUse: Option[Boolean] = None
	)
	
	case class GoogleFirebaseAppcheckV1ListResourcePoliciesResponse(
	  /** The ResourcePolicy objects retrieved. */
		resourcePolicies: Option[List[Schema.GoogleFirebaseAppcheckV1ResourcePolicy]] = None,
	  /** If the result list is too large to fit in a single response, then a token is returned. If the string is empty or omitted, then this response is the last page of results. This token can be used in a subsequent call to ListResourcePolicies to find the next group of ResourcePolicy objects. Page tokens are short-lived and should not be persisted. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleFirebaseAppcheckV1SafetyNetConfig(
	  /** Required. The relative resource name of the SafetyNet configuration object, in the format: ``` projects/{project_number}/apps/{app_id}/safetyNetConfig ``` */
		name: Option[String] = None,
	  /** Specifies the duration for which App Check tokens exchanged from SafetyNet tokens will be valid. If unset, a default value of 1 hour is assumed. Must be between 30 minutes and 7 days, inclusive. */
		tokenTtl: Option[String] = None
	)
	
	case class GoogleFirebaseAppcheckV1ExchangeAppAttestAttestationRequest(
	  /** Required. A one-time challenge returned by an immediately prior call to GenerateAppAttestChallenge. */
		challenge: Option[String] = None,
	  /** Required. The key ID generated by App Attest for the client app. */
		keyId: Option[String] = None,
	  /** Required. The App Attest statement returned by the client-side App Attest API. This is a base64url encoded CBOR object in the JSON response. */
		attestationStatement: Option[String] = None,
	  /** Specifies whether this attestation is for use in a &#42;limited use&#42; (`true`) or &#42;session based&#42; (`false`) context. To enable this attestation to be used with the &#42;replay protection&#42; feature, set this to `true`. The default value is `false`. */
		limitedUse: Option[Boolean] = None
	)
	
	case class GoogleFirebaseAppcheckV1PlayIntegrityConfig(
	  /** Specifies the duration for which App Check tokens exchanged from Play Integrity tokens will be valid. If unset, a default value of 1 hour is assumed. Must be between 30 minutes and 7 days, inclusive. */
		tokenTtl: Option[String] = None,
	  /** Required. The relative resource name of the Play Integrity configuration object, in the format: ``` projects/{project_number}/apps/{app_id}/playIntegrityConfig ``` */
		name: Option[String] = None
	)
	
	object GoogleFirebaseAppcheckV1ResourcePolicy {
		enum EnforcementModeEnum extends Enum[EnforcementModeEnum] { case OFF, UNENFORCED, ENFORCED }
	}
	case class GoogleFirebaseAppcheckV1ResourcePolicy(
	  /** This checksum is computed by the server based on the value of other fields, and may be sent on update and delete requests to ensure the client has an up-to-date value before proceeding. This etag is strongly validated as defined by RFC 7232. */
		etag: Option[String] = None,
	  /** Output only. Timestamp when this resource policy configuration object was most recently updated. */
		updateTime: Option[String] = None,
	  /** Required. Service specific name of the resource object to which this policy applies, in the format: &#42; &#42;&#42;iOS OAuth clients&#42;&#42; (Google Identity for iOS): `//oauth2.googleapis.com/projects/{project_number}/oauthClients/{oauth_client_id}` Note that the resource must belong to the service specified in the `name` and be from the same project as this policy, but the resource is allowed to be missing at the time of creation of this policy; in that case, we make a best-effort attempt at respecting this policy, but it may not have any effect until the resource is fully created. */
		targetResource: Option[String] = None,
	  /** Required. Identifier. The relative name of the resource policy object, in the format: ``` projects/{project_number}/services/{service_id}/resourcePolicies/{resource_policy_id} ``` Note that the `service_id` element must be a supported service ID. Currently, the following service IDs are supported: &#42; `oauth2.googleapis.com` (Google Identity for iOS) `resource_policy_id` is a system-generated UID. */
		name: Option[String] = None,
	  /** Required. The App Check enforcement mode for this resource. This will override the EnforcementMode setting on the service. */
		enforcementMode: Option[Schema.GoogleFirebaseAppcheckV1ResourcePolicy.EnforcementModeEnum] = None
	)
	
	case class GoogleFirebaseAppcheckV1BatchGetRecaptchaV3ConfigsResponse(
	  /** RecaptchaV3Configs retrieved. */
		configs: Option[List[Schema.GoogleFirebaseAppcheckV1RecaptchaV3Config]] = None
	)
	
	case class GoogleFirebaseAppcheckV1ExchangeRecaptchaEnterpriseTokenRequest(
	  /** Specifies whether this attestation is for use in a &#42;limited use&#42; (`true`) or &#42;session based&#42; (`false`) context. To enable this attestation to be used with the &#42;replay protection&#42; feature, set this to `true`. The default value is `false`. */
		limitedUse: Option[Boolean] = None,
	  /** Required. The reCAPTCHA token as returned by the [reCAPTCHA Enterprise JavaScript API](https://cloud.google.com/recaptcha-enterprise/docs/instrument-web-pages). */
		recaptchaEnterpriseToken: Option[String] = None
	)
	
	case class GoogleFirebaseAppcheckV1ExchangeDeviceCheckTokenRequest(
	  /** Specifies whether this attestation is for use in a &#42;limited use&#42; (`true`) or &#42;session based&#42; (`false`) context. To enable this attestation to be used with the &#42;replay protection&#42; feature, set this to `true`. The default value is `false`. */
		limitedUse: Option[Boolean] = None,
	  /** Required. The `device_token` as returned by Apple's client-side [DeviceCheck API](https://developer.apple.com/documentation/devicecheck/dcdevice). This is the base64 encoded `Data` (Swift) or `NSData` (ObjC) object. */
		deviceToken: Option[String] = None
	)
	
	case class GoogleFirebaseAppcheckV1PublicJwk(
	  /** See [section 6.3.1.1 of RFC 7518](https://tools.ietf.org/html/rfc7518#section-6.3.1.1). */
		n: Option[String] = None,
	  /** See [section 4.4 of RFC 7517](https://tools.ietf.org/html/rfc7517#section-4.4). */
		alg: Option[String] = None,
	  /** See [section 4.1 of RFC 7517](https://tools.ietf.org/html/rfc7517#section-4.1). */
		kty: Option[String] = None,
	  /** See [section 4.5 of RFC 7517](https://tools.ietf.org/html/rfc7517#section-4.5). */
		kid: Option[String] = None,
	  /** See [section 6.3.1.2 of RFC 7518](https://tools.ietf.org/html/rfc7518#section-6.3.1.2). */
		e: Option[String] = None,
	  /** See [section 4.2 of RFC 7517](https://tools.ietf.org/html/rfc7517#section-4.2). */
		use: Option[String] = None
	)
	
	case class GoogleFirebaseAppcheckV1BatchUpdateServicesRequest(
	  /** Optional. A comma-separated list of names of fields in the Services to update. Example: `display_name`. If the `update_mask` field is set in both this request and any of the UpdateServiceRequest messages, they must match or the entire batch fails and no updates will be committed. */
		updateMask: Option[String] = None,
	  /** Required. The request messages specifying the Services to update. A maximum of 100 objects can be updated in a batch. */
		requests: Option[List[Schema.GoogleFirebaseAppcheckV1UpdateServiceRequest]] = None
	)
	
	case class GoogleFirebaseAppcheckV1ExchangeAppAttestAttestationResponse(
	  /** Encapsulates an App Check token. */
		appCheckToken: Option[Schema.GoogleFirebaseAppcheckV1AppCheckToken] = None,
	  /** An artifact that can be used in future calls to ExchangeAppAttestAssertion. */
		artifact: Option[String] = None
	)
	
	case class GoogleFirebaseAppcheckV1AppCheckToken(
	  /** The App Check token. App Check tokens are signed [JWTs](https://tools.ietf.org/html/rfc7519) containing claims that identify the attested app and GCP project. This token is used to access Google services protected by App Check. These tokens can also be [verified by your own custom backends](https://firebase.google.com/docs/app-check/custom-resource-backend) using the Firebase Admin SDK or third-party libraries. */
		token: Option[String] = None,
	  /** The duration from the time this token is minted until its expiration. This field is intended to ease client-side token management, since the client may have clock skew, but is still able to accurately measure a duration. */
		ttl: Option[String] = None
	)
	
	case class GoogleFirebaseAppcheckV1GeneratePlayIntegrityChallengeResponse(
	  /** The duration from the time this challenge is minted until its expiration. This field is intended to ease client-side token management, since the client may have clock skew, but is still able to accurately measure a duration. */
		ttl: Option[String] = None,
	  /** A one-time use [challenge](https://developer.android.com/google/play/integrity/verdict#protect-against-replay-attacks) for the client to pass to the Play Integrity API. */
		challenge: Option[String] = None
	)
	
	case class GoogleFirebaseAppcheckV1BatchGetPlayIntegrityConfigsResponse(
	  /** PlayIntegrityConfigs retrieved. */
		configs: Option[List[Schema.GoogleFirebaseAppcheckV1PlayIntegrityConfig]] = None
	)
	
	case class GoogleFirebaseAppcheckV1DebugToken(
	  /** Required. Input only. Immutable. The secret token itself. Must be provided during creation, and must be a UUID4, case insensitive. This field is immutable once set, and cannot be provided during an UpdateDebugToken request. You can, however, delete this debug token using DeleteDebugToken to revoke it. For security reasons, this field will never be populated in any response. */
		token: Option[String] = None,
	  /** Required. The relative resource name of the debug token, in the format: ``` projects/{project_number}/apps/{app_id}/debugTokens/{debug_token_id} ``` */
		name: Option[String] = None,
	  /** Output only. Timestamp when this debug token was most recently updated. */
		updateTime: Option[String] = None,
	  /** Required. A human readable display name used to identify this debug token. */
		displayName: Option[String] = None
	)
	
	case class GoogleFirebaseAppcheckV1ExchangeSafetyNetTokenRequest(
	  /** Required. The [SafetyNet attestation response](https://developer.android.com/training/safetynet/attestation#request-attestation-step) issued to your app. */
		safetyNetToken: Option[String] = None
	)
	
	case class GoogleFirebaseAppcheckV1DeviceCheckConfig(
	  /** Required. The relative resource name of the DeviceCheck configuration object, in the format: ``` projects/{project_number}/apps/{app_id}/deviceCheckConfig ``` */
		name: Option[String] = None,
	  /** Required. The key identifier of a private key enabled with DeviceCheck, created in your Apple Developer account. */
		keyId: Option[String] = None,
	  /** Required. Input only. The contents of the private key (`.p8`) file associated with the key specified by `key_id`. For security reasons, this field will never be populated in any response. */
		privateKey: Option[String] = None,
	  /** Specifies the duration for which App Check tokens exchanged from DeviceCheck tokens will be valid. If unset, a default value of 1 hour is assumed. Must be between 30 minutes and 7 days, inclusive. */
		tokenTtl: Option[String] = None,
	  /** Output only. Whether the `private_key` field was previously set. Since we will never return the `private_key` field, this field is the only way to find out whether it was previously set. */
		privateKeySet: Option[Boolean] = None
	)
	
	case class GoogleFirebaseAppcheckV1RecaptchaEnterpriseConfig(
	  /** The score-based site key [created in reCAPTCHA Enterprise](https://cloud.google.com/recaptcha-enterprise/docs/create-key#creating_a_site_key) used to [invoke reCAPTCHA and generate the reCAPTCHA tokens](https://cloud.google.com/recaptcha-enterprise/docs/instrument-web-pages) for your application. Important: This is &#42;not&#42; the `site_secret` (as it is in reCAPTCHA v3), but rather your score-based reCAPTCHA Enterprise site key. */
		siteKey: Option[String] = None,
	  /** Specifies the duration for which App Check tokens exchanged from reCAPTCHA Enterprise tokens will be valid. If unset, a default value of 1 hour is assumed. Must be between 30 minutes and 7 days, inclusive. */
		tokenTtl: Option[String] = None,
	  /** Required. The relative resource name of the reCAPTCHA Enterprise configuration object, in the format: ``` projects/{project_number}/apps/{app_id}/recaptchaEnterpriseConfig ``` */
		name: Option[String] = None
	)
	
	case class GoogleFirebaseAppcheckV1BatchGetRecaptchaEnterpriseConfigsResponse(
	  /** RecaptchaEnterpriseConfigs retrieved. */
		configs: Option[List[Schema.GoogleFirebaseAppcheckV1RecaptchaEnterpriseConfig]] = None
	)
	
	case class GoogleFirebaseAppcheckV1PublicJwkSet(
	  /** The set of public keys. See [section 5.1 of RFC 7517](https://tools.ietf.org/html/rfc7517#section-5). */
		keys: Option[List[Schema.GoogleFirebaseAppcheckV1PublicJwk]] = None
	)
	
	case class GoogleFirebaseAppcheckV1AppAttestConfig(
	  /** Specifies the duration for which App Check tokens exchanged from App Attest artifacts will be valid. If unset, a default value of 1 hour is assumed. Must be between 30 minutes and 7 days, inclusive. */
		tokenTtl: Option[String] = None,
	  /** Required. The relative resource name of the App Attest configuration object, in the format: ``` projects/{project_number}/apps/{app_id}/appAttestConfig ``` */
		name: Option[String] = None
	)
	
	case class GoogleFirebaseAppcheckV1BatchUpdateServicesResponse(
	  /** Service objects after the updates have been applied. */
		services: Option[List[Schema.GoogleFirebaseAppcheckV1Service]] = None
	)
	
	case class GoogleFirebaseAppcheckV1ExchangeAppAttestAssertionRequest(
	  /** Required. A one-time challenge returned by an immediately prior call to GenerateAppAttestChallenge. */
		challenge: Option[String] = None,
	  /** Required. The CBOR-encoded assertion returned by the client-side App Attest API. */
		assertion: Option[String] = None,
	  /** Specifies whether this attestation is for use in a &#42;limited use&#42; (`true`) or &#42;session based&#42; (`false`) context. To enable this attestation to be used with the &#42;replay protection&#42; feature, set this to `true`. The default value is `false`. */
		limitedUse: Option[Boolean] = None,
	  /** Required. The artifact returned by a previous call to ExchangeAppAttestAttestation. */
		artifact: Option[String] = None
	)
	
	object GoogleFirebaseAppcheckV1Service {
		enum EnforcementModeEnum extends Enum[EnforcementModeEnum] { case OFF, UNENFORCED, ENFORCED }
	}
	case class GoogleFirebaseAppcheckV1Service(
	  /** Required. The App Check enforcement mode for this service. */
		enforcementMode: Option[Schema.GoogleFirebaseAppcheckV1Service.EnforcementModeEnum] = None,
	  /** Required. The relative resource name of the service configuration object, in the format: ``` projects/{project_number}/services/{service_id} ``` Note that the `service_id` element must be a supported service ID. Currently, the following service IDs are supported: &#42; `firebasestorage.googleapis.com` (Cloud Storage for Firebase) &#42; `firebasedatabase.googleapis.com` (Firebase Realtime Database) &#42; `firestore.googleapis.com` (Cloud Firestore) &#42; `oauth2.googleapis.com` (Google Identity for iOS) */
		name: Option[String] = None
	)
	
	case class GoogleFirebaseAppcheckV1BatchUpdateResourcePoliciesResponse(
	  /** ResourcePolicy objects after the updates have been applied. */
		resourcePolicies: Option[List[Schema.GoogleFirebaseAppcheckV1ResourcePolicy]] = None
	)
	
	case class GoogleFirebaseAppcheckV1BatchGetAppAttestConfigsResponse(
	  /** AppAttestConfigs retrieved. */
		configs: Option[List[Schema.GoogleFirebaseAppcheckV1AppAttestConfig]] = None
	)
	
	case class GoogleFirebaseAppcheckV1UpdateResourcePolicyRequest(
	  /** Required. A comma-separated list of names of fields in the ResourcePolicy to update. Example: `enforcement_mode`. */
		updateMask: Option[String] = None,
	  /** Required. The ResourcePolicy to update. The ResourcePolicy's `name` field is used to identify the ResourcePolicy to be updated, in the format: ``` projects/{project_number}/services/{service_id}/resourcePolicies/{resource_policy_id} ``` Note that the `service_id` element must be a supported service ID. Currently, the following service IDs are supported: &#42; `oauth2.googleapis.com` (Google Identity for iOS) */
		resourcePolicy: Option[Schema.GoogleFirebaseAppcheckV1ResourcePolicy] = None
	)
	
	case class GoogleFirebaseAppcheckV1ExchangePlayIntegrityTokenRequest(
	  /** Specifies whether this attestation is for use in a &#42;limited use&#42; (`true`) or &#42;session based&#42; (`false`) context. To enable this attestation to be used with the &#42;replay protection&#42; feature, set this to `true`. The default value is `false`. */
		limitedUse: Option[Boolean] = None,
	  /** Required. The [integrity verdict response token from Play Integrity](https://developer.android.com/google/play/integrity/verdict#decrypt-verify) issued to your app. */
		playIntegrityToken: Option[String] = None
	)
	
	case class GoogleFirebaseAppcheckV1BatchGetSafetyNetConfigsResponse(
	  /** SafetyNetConfigs retrieved. */
		configs: Option[List[Schema.GoogleFirebaseAppcheckV1SafetyNetConfig]] = None
	)
	
	case class GoogleFirebaseAppcheckV1BatchUpdateResourcePoliciesRequest(
	  /** Required. The request messages specifying the ResourcePolicy objects to update. A maximum of 100 objects can be updated in a batch. */
		requests: Option[List[Schema.GoogleFirebaseAppcheckV1UpdateResourcePolicyRequest]] = None,
	  /** Optional. A comma-separated list of names of fields in the ResourcePolicy objects to update. Example: `enforcement_mode`. If this field is present, the `update_mask` field in the UpdateResourcePolicyRequest messages must all match this field, or the entire batch fails and no updates will be committed. */
		updateMask: Option[String] = None
	)
	
	case class GoogleFirebaseAppcheckV1GenerateAppAttestChallengeRequest(
	
	)
	
	case class GoogleFirebaseAppcheckV1RecaptchaV3Config(
	  /** Specifies the duration for which App Check tokens exchanged from reCAPTCHA tokens will be valid. If unset, a default value of 1 day is assumed. Must be between 30 minutes and 7 days, inclusive. */
		tokenTtl: Option[String] = None,
	  /** Output only. Whether the `site_secret` field was previously set. Since we will never return the `site_secret` field, this field is the only way to find out whether it was previously set. */
		siteSecretSet: Option[Boolean] = None,
	  /** Required. The relative resource name of the reCAPTCHA v3 configuration object, in the format: ``` projects/{project_number}/apps/{app_id}/recaptchaV3Config ``` */
		name: Option[String] = None,
	  /** Required. Input only. The site secret used to identify your service for reCAPTCHA v3 verification. For security reasons, this field will never be populated in any response. */
		siteSecret: Option[String] = None
	)
	
	case class GoogleFirebaseAppcheckV1UpdateServiceRequest(
	  /** Required. A comma-separated list of names of fields in the Service to update. Example: `enforcement_mode`. */
		updateMask: Option[String] = None,
	  /** Required. The Service to update. The Service's `name` field is used to identify the Service to be updated, in the format: ``` projects/{project_number}/services/{service_id} ``` Note that the `service_id` element must be a supported service ID. Currently, the following service IDs are supported: &#42; `firebasestorage.googleapis.com` (Cloud Storage for Firebase) &#42; `firebasedatabase.googleapis.com` (Firebase Realtime Database) &#42; `firestore.googleapis.com` (Cloud Firestore) &#42; `oauth2.googleapis.com` (Google Identity for iOS) */
		service: Option[Schema.GoogleFirebaseAppcheckV1Service] = None
	)
}
