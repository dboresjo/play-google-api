package com.boresjo.play.api.google.accessapproval

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ListApprovalRequestsResponse(
	  /** Approval request details. */
		approvalRequests: Option[List[Schema.ApprovalRequest]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more. */
		nextPageToken: Option[String] = None
	)
	
	case class ApprovalRequest(
	  /** The resource name of the request. Format is "{projects|folders|organizations}/{id}/approvalRequests/{approval_request}". */
		name: Option[String] = None,
	  /** The resource for which approval is being requested. The format of the resource name is defined at https://cloud.google.com/apis/design/resource_names. The resource name here may either be a "full" resource name (e.g. "//library.googleapis.com/shelves/shelf1/books/book2") or a "relative" resource name (e.g. "shelves/shelf1/books/book2") as described in the resource name specification. */
		requestedResourceName: Option[String] = None,
	  /** This field contains the augmented information of the request. */
		requestedAugmentedInfo: Option[Schema.AugmentedInfo] = None,
	  /** Properties related to the resource represented by requested_resource_name. */
		requestedResourceProperties: Option[Schema.ResourceProperties] = None,
	  /** The justification for which approval is being requested. */
		requestedReason: Option[Schema.AccessReason] = None,
	  /** The locations for which approval is being requested. */
		requestedLocations: Option[Schema.AccessLocations] = None,
	  /** The time at which approval was requested. */
		requestTime: Option[String] = None,
	  /** The original requested expiration for the approval. Calculated by adding the requested_duration to the request_time. */
		requestedExpiration: Option[String] = None,
	  /** The requested access duration. */
		requestedDuration: Option[String] = None,
	  /** Access was approved. */
		approve: Option[Schema.ApproveDecision] = None,
	  /** The request was dismissed. */
		dismiss: Option[Schema.DismissDecision] = None
	)
	
	case class AugmentedInfo(
	  /** For command-line tools, the full command-line exactly as entered by the actor without adding any additional characters (such as quotation marks). */
		command: Option[String] = None
	)
	
	case class ResourceProperties(
	  /** Whether an approval will exclude the descendants of the resource being requested. */
		excludesDescendants: Option[Boolean] = None
	)
	
	object AccessReason {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, CUSTOMER_INITIATED_SUPPORT, GOOGLE_INITIATED_SERVICE, GOOGLE_INITIATED_REVIEW, THIRD_PARTY_DATA_REQUEST, GOOGLE_RESPONSE_TO_PRODUCTION_ALERT, CLOUD_INITIATED_ACCESS }
	}
	case class AccessReason(
	  /** Type of access justification. */
		`type`: Option[Schema.AccessReason.TypeEnum] = None,
	  /** More detail about certain reason types. See comments for each type above. */
		detail: Option[String] = None
	)
	
	case class AccessLocations(
	  /** The "home office" location of the principal. A two-letter country code (ISO 3166-1 alpha-2), such as "US", "DE" or "GB" or a region code. In some limited situations Google systems may refer refer to a region code instead of a country code. Possible Region Codes: &#42; ASI: Asia &#42; EUR: Europe &#42; OCE: Oceania &#42; AFR: Africa &#42; NAM: North America &#42; SAM: South America &#42; ANT: Antarctica &#42; ANY: Any location */
		principalOfficeCountry: Option[String] = None,
	  /** Physical location of the principal at the time of the access. A two-letter country code (ISO 3166-1 alpha-2), such as "US", "DE" or "GB" or a region code. In some limited situations Google systems may refer refer to a region code instead of a country code. Possible Region Codes: &#42; ASI: Asia &#42; EUR: Europe &#42; OCE: Oceania &#42; AFR: Africa &#42; NAM: North America &#42; SAM: South America &#42; ANT: Antarctica &#42; ANY: Any location */
		principalPhysicalLocationCountry: Option[String] = None
	)
	
	case class ApproveDecision(
	  /** The time at which approval was granted. */
		approveTime: Option[String] = None,
	  /** The time at which the approval expires. */
		expireTime: Option[String] = None,
	  /** If set, denotes the timestamp at which the approval is invalidated. */
		invalidateTime: Option[String] = None,
	  /** The signature for the ApprovalRequest and details on how it was signed. */
		signatureInfo: Option[Schema.SignatureInfo] = None,
	  /** True when the request has been auto-approved. */
		autoApproved: Option[Boolean] = None
	)
	
	object SignatureInfo {
		enum GoogleKeyAlgorithmEnum extends Enum[GoogleKeyAlgorithmEnum] { case CRYPTO_KEY_VERSION_ALGORITHM_UNSPECIFIED, GOOGLE_SYMMETRIC_ENCRYPTION, AES_128_GCM, AES_256_GCM, AES_128_CBC, AES_256_CBC, AES_128_CTR, AES_256_CTR, RSA_SIGN_PSS_2048_SHA256, RSA_SIGN_PSS_3072_SHA256, RSA_SIGN_PSS_4096_SHA256, RSA_SIGN_PSS_4096_SHA512, RSA_SIGN_PKCS1_2048_SHA256, RSA_SIGN_PKCS1_3072_SHA256, RSA_SIGN_PKCS1_4096_SHA256, RSA_SIGN_PKCS1_4096_SHA512, RSA_SIGN_RAW_PKCS1_2048, RSA_SIGN_RAW_PKCS1_3072, RSA_SIGN_RAW_PKCS1_4096, RSA_DECRYPT_OAEP_2048_SHA256, RSA_DECRYPT_OAEP_3072_SHA256, RSA_DECRYPT_OAEP_4096_SHA256, RSA_DECRYPT_OAEP_4096_SHA512, RSA_DECRYPT_OAEP_2048_SHA1, RSA_DECRYPT_OAEP_3072_SHA1, RSA_DECRYPT_OAEP_4096_SHA1, EC_SIGN_P256_SHA256, EC_SIGN_P384_SHA384, EC_SIGN_SECP256K1_SHA256, EC_SIGN_ED25519, HMAC_SHA256, HMAC_SHA1, HMAC_SHA384, HMAC_SHA512, HMAC_SHA224, EXTERNAL_SYMMETRIC_ENCRYPTION }
	}
	case class SignatureInfo(
	  /** The digital signature. */
		signature: Option[String] = None,
	  /** The public key for the Google default signing, encoded in PEM format. The signature was created using a private key which may be verified using this public key. */
		googlePublicKeyPem: Option[String] = None,
	  /** The resource name of the customer CryptoKeyVersion used for signing. */
		customerKmsKeyVersion: Option[String] = None,
	  /** The hashing algorithm used for signature verification. It will only be present in the case of Google managed keys. */
		googleKeyAlgorithm: Option[Schema.SignatureInfo.GoogleKeyAlgorithmEnum] = None,
	  /** The ApprovalRequest that is serialized without the SignatureInfo message field. This data is used with the hashing algorithm to generate the digital signature, and it can be used for signature verification. */
		serializedApprovalRequest: Option[String] = None
	)
	
	case class DismissDecision(
	  /** The time at which the approval request was dismissed. */
		dismissTime: Option[String] = None,
	  /** This field will be true if the ApprovalRequest was implicitly dismissed due to inaction by the access approval approvers (the request is not acted on by the approvers before the exiration time). */
		`implicit`: Option[Boolean] = None
	)
	
	case class ApproveApprovalRequestMessage(
	  /** The expiration time of this approval. */
		expireTime: Option[String] = None
	)
	
	case class DismissApprovalRequestMessage(
	
	)
	
	case class InvalidateApprovalRequestMessage(
	
	)
	
	object AccessApprovalSettings {
		enum RequestScopeMaxWidthPreferenceEnum extends Enum[RequestScopeMaxWidthPreferenceEnum] { case REQUEST_SCOPE_MAX_WIDTH_PREFERENCE_UNSPECIFIED, ORGANIZATION, FOLDER, PROJECT }
	}
	case class AccessApprovalSettings(
	  /** The resource name of the settings. Format is one of: &#42; "projects/{project}/accessApprovalSettings" &#42; "folders/{folder}/accessApprovalSettings" &#42; "organizations/{organization}/accessApprovalSettings" */
		name: Option[String] = None,
	  /** A list of email addresses to which notifications relating to approval requests should be sent. Notifications relating to a resource will be sent to all emails in the settings of ancestor resources of that resource. A maximum of 50 email addresses are allowed. */
		notificationEmails: Option[List[String]] = None,
	  /** A list of Google Cloud Services for which the given resource has Access Approval enrolled. Access requests for the resource given by name against any of these services contained here will be required to have explicit approval. If name refers to an organization, enrollment can be done for individual services. If name refers to a folder or project, enrollment can only be done on an all or nothing basis. If a cloud_product is repeated in this list, the first entry will be honored and all following entries will be discarded. A maximum of 10 enrolled services will be enforced, to be expanded as the set of supported services is expanded. */
		enrolledServices: Option[List[Schema.EnrolledService]] = None,
	  /** Output only. This field is read only (not settable via UpdateAccessApprovalSettings method). If the field is true, that indicates that at least one service is enrolled for Access Approval in one or more ancestors of the Project or Folder (this field will always be unset for the organization since organizations do not have ancestors). */
		enrolledAncestor: Option[Boolean] = None,
	  /** The asymmetric crypto key version to use for signing approval requests. Empty active_key_version indicates that a Google-managed key should be used for signing. This property will be ignored if set by an ancestor of this resource, and new non-empty values may not be set. */
		activeKeyVersion: Option[String] = None,
	  /** Output only. This field is read only (not settable via UpdateAccessApprovalSettings method). If the field is true, that indicates that an ancestor of this Project or Folder has set active_key_version (this field will always be unset for the organization since organizations do not have ancestors). */
		ancestorHasActiveKeyVersion: Option[Boolean] = None,
	  /** Output only. This field is read only (not settable via UpdateAccessApprovalSettings method). If the field is true, that indicates that there is some configuration issue with the active_key_version configured at this level in the resource hierarchy (e.g. it doesn't exist or the Access Approval service account doesn't have the correct permissions on it, etc.) This key version is not necessarily the effective key version at this level, as key versions are inherited top-down. */
		invalidKeyVersion: Option[Boolean] = None,
	  /** This preference is shared with Google personnel, but can be overridden if said personnel deems necessary. The approver ultimately can set the expiration at approval time. */
		preferredRequestExpirationDays: Option[Int] = None,
	  /** This preference is communicated to Google personnel when sending an approval request but can be overridden if necessary. */
		preferNoBroadApprovalRequests: Option[Boolean] = None,
	  /** Optional. A pubsub topic to which notifications relating to approval requests should be sent. */
		notificationPubsubTopic: Option[String] = None,
	  /** Optional. A setting to require approval request justifications to be customer visible. */
		requireCustomerVisibleJustification: Option[Boolean] = None,
	  /** Optional. A setting to indicate the maximum width of an Access Approval request. */
		requestScopeMaxWidthPreference: Option[Schema.AccessApprovalSettings.RequestScopeMaxWidthPreferenceEnum] = None
	)
	
	object EnrolledService {
		enum EnrollmentLevelEnum extends Enum[EnrollmentLevelEnum] { case ENROLLMENT_LEVEL_UNSPECIFIED, BLOCK_ALL }
	}
	case class EnrolledService(
	  /** The product for which Access Approval will be enrolled. Allowed values are listed below (case-sensitive): &#42; all &#42; GA &#42; App Engine &#42; Artifact Registry &#42; BigQuery &#42; Certificate Authority Service &#42; Cloud Bigtable &#42; Cloud Key Management Service &#42; Compute Engine &#42; Cloud Composer &#42; Cloud Dataflow &#42; Cloud Dataproc &#42; Cloud DLP &#42; Cloud EKM &#42; Cloud Firestore &#42; Cloud HSM &#42; Cloud Identity and Access Management &#42; Cloud Logging &#42; Cloud NAT &#42; Cloud Pub/Sub &#42; Cloud Spanner &#42; Cloud SQL &#42; Cloud Storage &#42; Eventarc &#42; Google Kubernetes Engine &#42; Organization Policy Serivice &#42; Persistent Disk &#42; Resource Manager &#42; Secret Manager &#42; Speaker ID Note: These values are supported as input for legacy purposes, but will not be returned from the API. &#42; all &#42; ga-only &#42; appengine.googleapis.com &#42; artifactregistry.googleapis.com &#42; bigquery.googleapis.com &#42; bigtable.googleapis.com &#42; container.googleapis.com &#42; cloudkms.googleapis.com &#42; cloudresourcemanager.googleapis.com &#42; cloudsql.googleapis.com &#42; compute.googleapis.com &#42; dataflow.googleapis.com &#42; dataproc.googleapis.com &#42; dlp.googleapis.com &#42; iam.googleapis.com &#42; logging.googleapis.com &#42; orgpolicy.googleapis.com &#42; pubsub.googleapis.com &#42; spanner.googleapis.com &#42; secretmanager.googleapis.com &#42; speakerid.googleapis.com &#42; storage.googleapis.com Calls to UpdateAccessApprovalSettings using 'all' or any of the XXX.googleapis.com will be translated to the associated product name ('all', 'App Engine', etc.). Note: 'all' will enroll the resource in all products supported at both 'GA' and 'Preview' levels. More information about levels of support is available at https://cloud.google.com/access-approval/docs/supported-services */
		cloudProduct: Option[String] = None,
	  /** The enrollment level of the service. */
		enrollmentLevel: Option[Schema.EnrolledService.EnrollmentLevelEnum] = None
	)
	
	case class Empty(
	
	)
	
	case class AccessApprovalServiceAccount(
	  /** The resource name of the Access Approval service account. Format is one of: &#42; "projects/{project}/serviceAccount" &#42; "folders/{folder}/serviceAccount" &#42; "organizations/{organization}/serviceAccount" */
		name: Option[String] = None,
	  /** Email address of the service account. */
		accountEmail: Option[String] = None
	)
}
