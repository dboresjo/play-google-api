package com.boresjo.play.api.google.kmsinventory

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object GoogleCloudKmsV1CryptoKeyVersionTemplate {
		enum AlgorithmEnum extends Enum[AlgorithmEnum] { case CRYPTO_KEY_VERSION_ALGORITHM_UNSPECIFIED, GOOGLE_SYMMETRIC_ENCRYPTION, AES_128_GCM, AES_256_GCM, AES_128_CBC, AES_256_CBC, AES_128_CTR, AES_256_CTR, RSA_SIGN_PSS_2048_SHA256, RSA_SIGN_PSS_3072_SHA256, RSA_SIGN_PSS_4096_SHA256, RSA_SIGN_PSS_4096_SHA512, RSA_SIGN_PKCS1_2048_SHA256, RSA_SIGN_PKCS1_3072_SHA256, RSA_SIGN_PKCS1_4096_SHA256, RSA_SIGN_PKCS1_4096_SHA512, RSA_SIGN_RAW_PKCS1_2048, RSA_SIGN_RAW_PKCS1_3072, RSA_SIGN_RAW_PKCS1_4096, RSA_DECRYPT_OAEP_2048_SHA256, RSA_DECRYPT_OAEP_3072_SHA256, RSA_DECRYPT_OAEP_4096_SHA256, RSA_DECRYPT_OAEP_4096_SHA512, RSA_DECRYPT_OAEP_2048_SHA1, RSA_DECRYPT_OAEP_3072_SHA1, RSA_DECRYPT_OAEP_4096_SHA1, EC_SIGN_P256_SHA256, EC_SIGN_P384_SHA384, EC_SIGN_SECP256K1_SHA256, EC_SIGN_ED25519, HMAC_SHA256, HMAC_SHA1, HMAC_SHA384, HMAC_SHA512, HMAC_SHA224, EXTERNAL_SYMMETRIC_ENCRYPTION }
		enum ProtectionLevelEnum extends Enum[ProtectionLevelEnum] { case PROTECTION_LEVEL_UNSPECIFIED, SOFTWARE, HSM, EXTERNAL, EXTERNAL_VPC }
	}
	case class GoogleCloudKmsV1CryptoKeyVersionTemplate(
	  /** Required. Algorithm to use when creating a CryptoKeyVersion based on this template. For backwards compatibility, GOOGLE_SYMMETRIC_ENCRYPTION is implied if both this field is omitted and CryptoKey.purpose is ENCRYPT_DECRYPT. */
		algorithm: Option[Schema.GoogleCloudKmsV1CryptoKeyVersionTemplate.AlgorithmEnum] = None,
	  /** ProtectionLevel to use when creating a CryptoKeyVersion based on this template. Immutable. Defaults to SOFTWARE. */
		protectionLevel: Option[Schema.GoogleCloudKmsV1CryptoKeyVersionTemplate.ProtectionLevelEnum] = None
	)
	
	case class GoogleCloudKmsV1ExternalProtectionLevelOptions(
	  /** The URI for an external resource that this CryptoKeyVersion represents. */
		externalKeyUri: Option[String] = None,
	  /** The path to the external key material on the EKM when using EkmConnection e.g., "v0/my/key". Set this field instead of external_key_uri when using an EkmConnection. */
		ekmConnectionKeyPath: Option[String] = None
	)
	
	object GoogleCloudKmsV1KeyAccessJustificationsPolicy {
		enum AllowedAccessReasonsEnum extends Enum[AllowedAccessReasonsEnum] { case REASON_UNSPECIFIED, CUSTOMER_INITIATED_SUPPORT, GOOGLE_INITIATED_SERVICE, THIRD_PARTY_DATA_REQUEST, GOOGLE_INITIATED_REVIEW, CUSTOMER_INITIATED_ACCESS, GOOGLE_INITIATED_SYSTEM_OPERATION, REASON_NOT_EXPECTED, MODIFIED_CUSTOMER_INITIATED_ACCESS, MODIFIED_GOOGLE_INITIATED_SYSTEM_OPERATION, GOOGLE_RESPONSE_TO_PRODUCTION_ALERT, CUSTOMER_AUTHORIZED_WORKFLOW_SERVICING }
	}
	case class GoogleCloudKmsV1KeyAccessJustificationsPolicy(
	  /** The list of allowed reasons for access to a CryptoKey. Zero allowed access reasons means all encrypt, decrypt, and sign operations for the CryptoKey associated with this policy will fail. */
		allowedAccessReasons: Option[List[Schema.GoogleCloudKmsV1KeyAccessJustificationsPolicy.AllowedAccessReasonsEnum]] = None
	)
	
	object GoogleCloudKmsV1CryptoKeyVersion {
		enum ProtectionLevelEnum extends Enum[ProtectionLevelEnum] { case PROTECTION_LEVEL_UNSPECIFIED, SOFTWARE, HSM, EXTERNAL, EXTERNAL_VPC }
		enum AlgorithmEnum extends Enum[AlgorithmEnum] { case CRYPTO_KEY_VERSION_ALGORITHM_UNSPECIFIED, GOOGLE_SYMMETRIC_ENCRYPTION, AES_128_GCM, AES_256_GCM, AES_128_CBC, AES_256_CBC, AES_128_CTR, AES_256_CTR, RSA_SIGN_PSS_2048_SHA256, RSA_SIGN_PSS_3072_SHA256, RSA_SIGN_PSS_4096_SHA256, RSA_SIGN_PSS_4096_SHA512, RSA_SIGN_PKCS1_2048_SHA256, RSA_SIGN_PKCS1_3072_SHA256, RSA_SIGN_PKCS1_4096_SHA256, RSA_SIGN_PKCS1_4096_SHA512, RSA_SIGN_RAW_PKCS1_2048, RSA_SIGN_RAW_PKCS1_3072, RSA_SIGN_RAW_PKCS1_4096, RSA_DECRYPT_OAEP_2048_SHA256, RSA_DECRYPT_OAEP_3072_SHA256, RSA_DECRYPT_OAEP_4096_SHA256, RSA_DECRYPT_OAEP_4096_SHA512, RSA_DECRYPT_OAEP_2048_SHA1, RSA_DECRYPT_OAEP_3072_SHA1, RSA_DECRYPT_OAEP_4096_SHA1, EC_SIGN_P256_SHA256, EC_SIGN_P384_SHA384, EC_SIGN_SECP256K1_SHA256, EC_SIGN_ED25519, HMAC_SHA256, HMAC_SHA1, HMAC_SHA384, HMAC_SHA512, HMAC_SHA224, EXTERNAL_SYMMETRIC_ENCRYPTION }
		enum StateEnum extends Enum[StateEnum] { case CRYPTO_KEY_VERSION_STATE_UNSPECIFIED, PENDING_GENERATION, ENABLED, DISABLED, DESTROYED, DESTROY_SCHEDULED, PENDING_IMPORT, IMPORT_FAILED, GENERATION_FAILED, PENDING_EXTERNAL_DESTRUCTION, EXTERNAL_DESTRUCTION_FAILED }
	}
	case class GoogleCloudKmsV1CryptoKeyVersion(
	  /** Output only. The root cause of the most recent generation failure. Only present if state is GENERATION_FAILED. */
		generationFailureReason: Option[String] = None,
	  /** Output only. The time at which this CryptoKeyVersion's key material was most recently imported. */
		importTime: Option[String] = None,
	  /** Output only. The ProtectionLevel describing how crypto operations are performed with this CryptoKeyVersion. */
		protectionLevel: Option[Schema.GoogleCloudKmsV1CryptoKeyVersion.ProtectionLevelEnum] = None,
	  /** Output only. The time at which this CryptoKeyVersion was created. */
		createTime: Option[String] = None,
	  /** Output only. The resource name for this CryptoKeyVersion in the format `projects/&#42;/locations/&#42;/keyRings/&#42;/cryptoKeys/&#42;/cryptoKeyVersions/&#42;`. */
		name: Option[String] = None,
	  /** Output only. The root cause of the most recent import failure. Only present if state is IMPORT_FAILED. */
		importFailureReason: Option[String] = None,
	  /** Output only. The time this CryptoKeyVersion's key material was destroyed. Only present if state is DESTROYED. */
		destroyEventTime: Option[String] = None,
	  /** ExternalProtectionLevelOptions stores a group of additional fields for configuring a CryptoKeyVersion that are specific to the EXTERNAL protection level and EXTERNAL_VPC protection levels. */
		externalProtectionLevelOptions: Option[Schema.GoogleCloudKmsV1ExternalProtectionLevelOptions] = None,
	  /** Output only. Statement that was generated and signed by the HSM at key creation time. Use this statement to verify attributes of the key as stored on the HSM, independently of Google. Only provided for key versions with protection_level HSM. */
		attestation: Option[Schema.GoogleCloudKmsV1KeyOperationAttestation] = None,
	  /** Output only. Whether or not this key version is eligible for reimport, by being specified as a target in ImportCryptoKeyVersionRequest.crypto_key_version. */
		reimportEligible: Option[Boolean] = None,
	  /** Output only. The CryptoKeyVersionAlgorithm that this CryptoKeyVersion supports. */
		algorithm: Option[Schema.GoogleCloudKmsV1CryptoKeyVersion.AlgorithmEnum] = None,
	  /** Output only. The time this CryptoKeyVersion's key material was generated. */
		generateTime: Option[String] = None,
	  /** The current state of the CryptoKeyVersion. */
		state: Option[Schema.GoogleCloudKmsV1CryptoKeyVersion.StateEnum] = None,
	  /** Output only. The time this CryptoKeyVersion's key material is scheduled for destruction. Only present if state is DESTROY_SCHEDULED. */
		destroyTime: Option[String] = None,
	  /** Output only. The name of the ImportJob used in the most recent import of this CryptoKeyVersion. Only present if the underlying key material was imported. */
		importJob: Option[String] = None,
	  /** Output only. The root cause of the most recent external destruction failure. Only present if state is EXTERNAL_DESTRUCTION_FAILED. */
		externalDestructionFailureReason: Option[String] = None
	)
	
	case class GoogleCloudKmsInventoryV1SearchProtectedResourcesResponse(
	  /** A token that can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Protected resources for this page. */
		protectedResources: Option[List[Schema.GoogleCloudKmsInventoryV1ProtectedResource]] = None
	)
	
	case class GoogleCloudKmsInventoryV1ListCryptoKeysResponse(
	  /** The page token returned from the previous response if the next page is desired. */
		nextPageToken: Option[String] = None,
	  /** The list of CryptoKeys. */
		cryptoKeys: Option[List[Schema.GoogleCloudKmsV1CryptoKey]] = None
	)
	
	object GoogleCloudKmsV1KeyOperationAttestation {
		enum FormatEnum extends Enum[FormatEnum] { case ATTESTATION_FORMAT_UNSPECIFIED, CAVIUM_V1_COMPRESSED, CAVIUM_V2_COMPRESSED }
	}
	case class GoogleCloudKmsV1KeyOperationAttestation(
	  /** Output only. The attestation data provided by the HSM when the key operation was performed. */
		content: Option[String] = None,
	  /** Output only. The certificate chains needed to validate the attestation */
		certChains: Option[Schema.GoogleCloudKmsV1KeyOperationAttestationCertificateChains] = None,
	  /** Output only. The format of the attestation data. */
		format: Option[Schema.GoogleCloudKmsV1KeyOperationAttestation.FormatEnum] = None
	)
	
	case class GoogleCloudKmsInventoryV1ProtectedResourcesSummary(
	  /** The total number of protected resources in the same Cloud organization as the key. */
		resourceCount: Option[String] = None,
	  /** The full name of the ProtectedResourcesSummary resource. Example: projects/test-project/locations/us/keyRings/test-keyring/cryptoKeys/test-key/protectedResourcesSummary */
		name: Option[String] = None,
	  /** The number of resources protected by the key grouped by region. */
		locations: Option[Map[String, String]] = None,
	  /** The number of distinct Cloud projects in the same Cloud organization as the key that have resources protected by the key. */
		projectCount: Option[Int] = None,
	  /** The number of resources protected by the key grouped by Cloud product. */
		cloudProducts: Option[Map[String, String]] = None,
	  /** The number of resources protected by the key grouped by resource type. */
		resourceTypes: Option[Map[String, String]] = None
	)
	
	case class GoogleCloudKmsInventoryV1ProtectedResource(
	  /** The Cloud product that owns the resource. Example: `compute` */
		cloudProduct: Option[String] = None,
	  /** The name of the Cloud KMS [CryptoKeyVersion](https://cloud.google.com/kms/docs/reference/rest/v1/projects.locations.keyRings.cryptoKeys.cryptoKeyVersions?hl=en) used to protect this resource via CMEK. This field is empty if the Google Cloud product owning the resource does not provide key version data to Asset Inventory. If there are multiple key versions protecting the resource, then this is same value as the first element of crypto_key_versions. */
		cryptoKeyVersion: Option[String] = None,
	  /** Format: `projects/{PROJECT_NUMBER}`. */
		project: Option[String] = None,
	  /** The ID of the project that owns the resource. */
		projectId: Option[String] = None,
	  /** Example: `compute.googleapis.com/Disk` */
		resourceType: Option[String] = None,
	  /** Location can be `global`, regional like `us-east1`, or zonal like `us-west1-b`. */
		location: Option[String] = None,
	  /** The names of the Cloud KMS [CryptoKeyVersion](https://cloud.google.com/kms/docs/reference/rest/v1/projects.locations.keyRings.cryptoKeys.cryptoKeyVersions?hl=en) used to protect this resource via CMEK. This field is empty if the Google Cloud product owning the resource does not provide key versions data to Asset Inventory. The first element of this field is stored in crypto_key_version. */
		cryptoKeyVersions: Option[List[String]] = None,
	  /** A key-value pair of the resource's labels (v1) to their values. */
		labels: Option[Map[String, String]] = None,
	  /** The full resource name of the resource. Example: `//compute.googleapis.com/projects/my_project_123/zones/zone1/instances/instance1`. */
		name: Option[String] = None,
	  /** Output only. The time at which this resource was created. The granularity is in seconds. Timestamp.nanos will always be 0. */
		createTime: Option[String] = None
	)
	
	object GoogleCloudKmsV1CryptoKey {
		enum PurposeEnum extends Enum[PurposeEnum] { case CRYPTO_KEY_PURPOSE_UNSPECIFIED, ENCRYPT_DECRYPT, ASYMMETRIC_SIGN, ASYMMETRIC_DECRYPT, RAW_ENCRYPT_DECRYPT, MAC }
	}
	case class GoogleCloudKmsV1CryptoKey(
	  /** Output only. The resource name for this CryptoKey in the format `projects/&#42;/locations/&#42;/keyRings/&#42;/cryptoKeys/&#42;`. */
		name: Option[String] = None,
	  /** Output only. The time at which this CryptoKey was created. */
		createTime: Option[String] = None,
	  /** Labels with user-defined metadata. For more information, see [Labeling Keys](https://cloud.google.com/kms/docs/labeling-keys). */
		labels: Option[Map[String, String]] = None,
	  /** next_rotation_time will be advanced by this period when the service automatically rotates a key. Must be at least 24 hours and at most 876,000 hours. If rotation_period is set, next_rotation_time must also be set. Keys with purpose ENCRYPT_DECRYPT support automatic rotation. For other keys, this field must be omitted. */
		rotationPeriod: Option[String] = None,
	  /** Output only. A copy of the "primary" CryptoKeyVersion that will be used by Encrypt when this CryptoKey is given in EncryptRequest.name. The CryptoKey's primary version can be updated via UpdateCryptoKeyPrimaryVersion. Keys with purpose ENCRYPT_DECRYPT may have a primary. For other keys, this field will be omitted. */
		primary: Option[Schema.GoogleCloudKmsV1CryptoKeyVersion] = None,
	  /** Immutable. The period of time that versions of this key spend in the DESTROY_SCHEDULED state before transitioning to DESTROYED. If not specified at creation time, the default duration is 30 days. */
		destroyScheduledDuration: Option[String] = None,
	  /** A template describing settings for new CryptoKeyVersion instances. The properties of new CryptoKeyVersion instances created by either CreateCryptoKeyVersion or auto-rotation are controlled by this template. */
		versionTemplate: Option[Schema.GoogleCloudKmsV1CryptoKeyVersionTemplate] = None,
	  /** Immutable. Whether this key may contain imported versions only. */
		importOnly: Option[Boolean] = None,
	  /** Optional. The policy used for Key Access Justifications Policy Enforcement. If this field is present and this key is enrolled in Key Access Justifications Policy Enforcement, the policy will be evaluated in encrypt, decrypt, and sign operations, and the operation will fail if rejected by the policy. The policy is defined by specifying zero or more allowed justification codes. https://cloud.google.com/assured-workloads/key-access-justifications/docs/justification-codes By default, this field is absent, and all justification codes are allowed. */
		keyAccessJustificationsPolicy: Option[Schema.GoogleCloudKmsV1KeyAccessJustificationsPolicy] = None,
	  /** At next_rotation_time, the Key Management Service will automatically: 1. Create a new version of this CryptoKey. 2. Mark the new version as primary. Key rotations performed manually via CreateCryptoKeyVersion and UpdateCryptoKeyPrimaryVersion do not affect next_rotation_time. Keys with purpose ENCRYPT_DECRYPT support automatic rotation. For other keys, this field must be omitted. */
		nextRotationTime: Option[String] = None,
	  /** Immutable. The resource name of the backend environment where the key material for all CryptoKeyVersions associated with this CryptoKey reside and where all related cryptographic operations are performed. Only applicable if CryptoKeyVersions have a ProtectionLevel of EXTERNAL_VPC, with the resource name in the format `projects/&#42;/locations/&#42;/ekmConnections/&#42;`. Note, this list is non-exhaustive and may apply to additional ProtectionLevels in the future. */
		cryptoKeyBackend: Option[String] = None,
	  /** Immutable. The immutable purpose of this CryptoKey. */
		purpose: Option[Schema.GoogleCloudKmsV1CryptoKey.PurposeEnum] = None
	)
	
	case class GoogleCloudKmsV1KeyOperationAttestationCertificateChains(
	  /** Google partition certificate chain corresponding to the attestation. */
		googlePartitionCerts: Option[List[String]] = None,
	  /** Google card certificate chain corresponding to the attestation. */
		googleCardCerts: Option[List[String]] = None,
	  /** Cavium certificate chain corresponding to the attestation. */
		caviumCerts: Option[List[String]] = None
	)
}
