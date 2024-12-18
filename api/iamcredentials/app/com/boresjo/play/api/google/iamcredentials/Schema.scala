package com.boresjo.play.api.google.iamcredentials

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GenerateAccessTokenRequest(
	  /** The sequence of service accounts in a delegation chain. This field is required for [delegated requests](https://cloud.google.com/iam/help/credentials/delegated-request). For [direct requests](https://cloud.google.com/iam/help/credentials/direct-request), which are more common, do not specify this field. Each service account must be granted the `roles/iam.serviceAccountTokenCreator` role on its next service account in the chain. The last service account in the chain must be granted the `roles/iam.serviceAccountTokenCreator` role on the service account that is specified in the `name` field of the request. The delegates must have the following format: `projects/-/serviceAccounts/{ACCOUNT_EMAIL_OR_UNIQUEID}`. The `-` wildcard character is required; replacing it with a project ID is invalid. */
		delegates: Option[List[String]] = None,
	  /** Required. Code to identify the scopes to be included in the OAuth 2.0 access token. See https://developers.google.com/identity/protocols/googlescopes for more information. At least one value required. */
		scope: Option[List[String]] = None,
	  /** The desired lifetime duration of the access token in seconds. By default, the maximum allowed value is 1 hour. To set a lifetime of up to 12 hours, you can add the service account as an allowed value in an Organization Policy that enforces the `constraints/iam.allowServiceAccountCredentialLifetimeExtension` constraint. See detailed instructions at https://cloud.google.com/iam/help/credentials/lifetime If a value is not specified, the token's lifetime will be set to a default value of 1 hour. */
		lifetime: Option[String] = None
	)
	
	case class GenerateAccessTokenResponse(
	  /** The OAuth 2.0 access token. */
		accessToken: Option[String] = None,
	  /** Token expiration time. The expiration time is always set. */
		expireTime: Option[String] = None
	)
	
	case class GenerateIdTokenRequest(
	  /** The sequence of service accounts in a delegation chain. Each service account must be granted the `roles/iam.serviceAccountTokenCreator` role on its next service account in the chain. The last service account in the chain must be granted the `roles/iam.serviceAccountTokenCreator` role on the service account that is specified in the `name` field of the request. The delegates must have the following format: `projects/-/serviceAccounts/{ACCOUNT_EMAIL_OR_UNIQUEID}`. The `-` wildcard character is required; replacing it with a project ID is invalid. */
		delegates: Option[List[String]] = None,
	  /** Required. The audience for the token, such as the API or account that this token grants access to. */
		audience: Option[String] = None,
	  /** Include the service account email in the token. If set to `true`, the token will contain `email` and `email_verified` claims. */
		includeEmail: Option[Boolean] = None
	)
	
	case class GenerateIdTokenResponse(
	  /** The OpenId Connect ID token. */
		token: Option[String] = None
	)
	
	case class SignBlobRequest(
	  /** The sequence of service accounts in a delegation chain. Each service account must be granted the `roles/iam.serviceAccountTokenCreator` role on its next service account in the chain. The last service account in the chain must be granted the `roles/iam.serviceAccountTokenCreator` role on the service account that is specified in the `name` field of the request. The delegates must have the following format: `projects/-/serviceAccounts/{ACCOUNT_EMAIL_OR_UNIQUEID}`. The `-` wildcard character is required; replacing it with a project ID is invalid. */
		delegates: Option[List[String]] = None,
	  /** Required. The bytes to sign. */
		payload: Option[String] = None
	)
	
	case class SignBlobResponse(
	  /** The ID of the key used to sign the blob. The key used for signing will remain valid for at least 12 hours after the blob is signed. To verify the signature, you can retrieve the public key in several formats from the following endpoints: - RSA public key wrapped in an X.509 v3 certificate: `https://www.googleapis.com/service_accounts/v1/metadata/x509/{ACCOUNT_EMAIL}` - Raw key in JSON format: `https://www.googleapis.com/service_accounts/v1/metadata/raw/{ACCOUNT_EMAIL}` - JSON Web Key (JWK): `https://www.googleapis.com/service_accounts/v1/metadata/jwk/{ACCOUNT_EMAIL}` */
		keyId: Option[String] = None,
	  /** The signature for the blob. Does not include the original blob. After the key pair referenced by the `key_id` response field expires, Google no longer exposes the public key that can be used to verify the blob. As a result, the receiver can no longer verify the signature. */
		signedBlob: Option[String] = None
	)
	
	case class SignJwtRequest(
	  /** The sequence of service accounts in a delegation chain. Each service account must be granted the `roles/iam.serviceAccountTokenCreator` role on its next service account in the chain. The last service account in the chain must be granted the `roles/iam.serviceAccountTokenCreator` role on the service account that is specified in the `name` field of the request. The delegates must have the following format: `projects/-/serviceAccounts/{ACCOUNT_EMAIL_OR_UNIQUEID}`. The `-` wildcard character is required; replacing it with a project ID is invalid. */
		delegates: Option[List[String]] = None,
	  /** Required. The JWT payload to sign. Must be a serialized JSON object that contains a JWT Claims Set. For example: `{"sub": "user@example.com", "iat": 313435}` If the JWT Claims Set contains an expiration time (`exp`) claim, it must be an integer timestamp that is not in the past and no more than 12 hours in the future. */
		payload: Option[String] = None
	)
	
	case class SignJwtResponse(
	  /** The ID of the key used to sign the JWT. The key used for signing will remain valid for at least 12 hours after the JWT is signed. To verify the signature, you can retrieve the public key in several formats from the following endpoints: - RSA public key wrapped in an X.509 v3 certificate: `https://www.googleapis.com/service_accounts/v1/metadata/x509/{ACCOUNT_EMAIL}` - Raw key in JSON format: `https://www.googleapis.com/service_accounts/v1/metadata/raw/{ACCOUNT_EMAIL}` - JSON Web Key (JWK): `https://www.googleapis.com/service_accounts/v1/metadata/jwk/{ACCOUNT_EMAIL}` */
		keyId: Option[String] = None,
	  /** The signed JWT. Contains the automatically generated header; the client-supplied payload; and the signature, which is generated using the key referenced by the `kid` field in the header. After the key pair referenced by the `key_id` response field expires, Google no longer exposes the public key that can be used to verify the JWT. As a result, the receiver can no longer verify the signature. */
		signedJwt: Option[String] = None
	)
	
	case class ServiceAccountAllowedLocations(
	  /** Output only. The human readable trust boundary locations. For example, ["us-central1", "europe-west1"] */
		locations: Option[List[String]] = None,
	  /** Output only. The hex encoded bitmap of the trust boundary locations */
		encodedLocations: Option[String] = None
	)
}
