package com.boresjo.play.api.google.cloudkms

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object AutokeyConfig {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, KEY_PROJECT_DELETED, UNINITIALIZED }
	}
	case class AutokeyConfig(
	  /** Identifier. Name of the AutokeyConfig resource, e.g. `folders/{FOLDER_NUMBER}/autokeyConfig`. */
		name: Option[String] = None,
	  /** Optional. Name of the key project, e.g. `projects/{PROJECT_ID}` or `projects/{PROJECT_NUMBER}`, where Cloud KMS Autokey will provision a new CryptoKey when a KeyHandle is created. On UpdateAutokeyConfig, the caller will require `cloudkms.cryptoKeys.setIamPolicy` permission on this key project. Once configured, for Cloud KMS Autokey to function properly, this key project must have the Cloud KMS API activated and the Cloud KMS Service Agent for this key project must be granted the `cloudkms.admin` role (or pertinent permissions). A request with an empty key project field will clear the configuration. */
		keyProject: Option[String] = None,
	  /** Output only. The state for the AutokeyConfig. */
		state: Option[Schema.AutokeyConfig.StateEnum] = None
	)
	
	case class ShowEffectiveAutokeyConfigResponse(
	  /** Name of the key project configured in the resource project's folder ancestry. */
		keyProject: Option[String] = None
	)
	
	case class Operation(
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.Status] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None
	)
	
	case class Status(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class KeyHandle(
	  /** Identifier. Name of the KeyHandle resource, e.g. `projects/{PROJECT_ID}/locations/{LOCATION}/keyHandles/{KEY_HANDLE_ID}`. */
		name: Option[String] = None,
	  /** Output only. Name of a CryptoKey that has been provisioned for Customer Managed Encryption Key (CMEK) use in the KeyHandle project and location for the requested resource type. The CryptoKey project will reflect the value configured in the AutokeyConfig on the resource project's ancestor folder at the time of the KeyHandle creation. If more than one ancestor folder has a configured AutokeyConfig, the nearest of these configurations is used. */
		kmsKey: Option[String] = None,
	  /** Required. Indicates the resource type that the resulting CryptoKey is meant to protect, e.g. `{SERVICE}.googleapis.com/{TYPE}`. See documentation for supported resource types. */
		resourceTypeSelector: Option[String] = None
	)
	
	case class ListKeyHandlesResponse(
	  /** Resulting KeyHandles. */
		keyHandles: Option[List[Schema.KeyHandle]] = None,
	  /** A token to retrieve next page of results. Pass this value in ListKeyHandlesRequest.page_token to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class ListEkmConnectionsResponse(
	  /** The list of EkmConnections. */
		ekmConnections: Option[List[Schema.EkmConnection]] = None,
	  /** A token to retrieve next page of results. Pass this value in ListEkmConnectionsRequest.page_token to retrieve the next page of results. */
		nextPageToken: Option[String] = None,
	  /** The total number of EkmConnections that matched the query. */
		totalSize: Option[Int] = None
	)
	
	object EkmConnection {
		enum KeyManagementModeEnum extends Enum[KeyManagementModeEnum] { case KEY_MANAGEMENT_MODE_UNSPECIFIED, MANUAL, CLOUD_KMS }
	}
	case class EkmConnection(
	  /** Output only. The resource name for the EkmConnection in the format `projects/&#42;/locations/&#42;/ekmConnections/&#42;`. */
		name: Option[String] = None,
	  /** Output only. The time at which the EkmConnection was created. */
		createTime: Option[String] = None,
	  /** Optional. A list of ServiceResolvers where the EKM can be reached. There should be one ServiceResolver per EKM replica. Currently, only a single ServiceResolver is supported. */
		serviceResolvers: Option[List[Schema.ServiceResolver]] = None,
	  /** Optional. Etag of the currently stored EkmConnection. */
		etag: Option[String] = None,
	  /** Optional. Describes who can perform control plane operations on the EKM. If unset, this defaults to MANUAL. */
		keyManagementMode: Option[Schema.EkmConnection.KeyManagementModeEnum] = None,
	  /** Optional. Identifies the EKM Crypto Space that this EkmConnection maps to. Note: This field is required if KeyManagementMode is CLOUD_KMS. */
		cryptoSpacePath: Option[String] = None
	)
	
	case class ServiceResolver(
	  /** Required. The resource name of the Service Directory service pointing to an EKM replica, in the format `projects/&#42;/locations/&#42;/namespaces/&#42;/services/&#42;`. */
		serviceDirectoryService: Option[String] = None,
	  /** Optional. The filter applied to the endpoints of the resolved service. If no filter is specified, all endpoints will be considered. An endpoint will be chosen arbitrarily from the filtered list for each request. For endpoint filter syntax and examples, see https://cloud.google.com/service-directory/docs/reference/rpc/google.cloud.servicedirectory.v1#resolveservicerequest. */
		endpointFilter: Option[String] = None,
	  /** Required. The hostname of the EKM replica used at TLS and HTTP layers. */
		hostname: Option[String] = None,
	  /** Required. A list of leaf server certificates used to authenticate HTTPS connections to the EKM replica. Currently, a maximum of 10 Certificate is supported. */
		serverCertificates: Option[List[Schema.Certificate]] = None
	)
	
	case class Certificate(
	  /** Required. The raw certificate bytes in DER format. */
		rawDer: Option[String] = None,
	  /** Output only. True if the certificate was parsed successfully. */
		parsed: Option[Boolean] = None,
	  /** Output only. The issuer distinguished name in RFC 2253 format. Only present if parsed is true. */
		issuer: Option[String] = None,
	  /** Output only. The subject distinguished name in RFC 2253 format. Only present if parsed is true. */
		subject: Option[String] = None,
	  /** Output only. The subject Alternative DNS names. Only present if parsed is true. */
		subjectAlternativeDnsNames: Option[List[String]] = None,
	  /** Output only. The certificate is not valid before this time. Only present if parsed is true. */
		notBeforeTime: Option[String] = None,
	  /** Output only. The certificate is not valid after this time. Only present if parsed is true. */
		notAfterTime: Option[String] = None,
	  /** Output only. The certificate serial number as a hex string. Only present if parsed is true. */
		serialNumber: Option[String] = None,
	  /** Output only. The SHA-256 certificate fingerprint as a hex string. Only present if parsed is true. */
		sha256Fingerprint: Option[String] = None
	)
	
	case class EkmConfig(
	  /** Output only. The resource name for the EkmConfig in the format `projects/&#42;/locations/&#42;/ekmConfig`. */
		name: Option[String] = None,
	  /** Optional. Resource name of the default EkmConnection. Setting this field to the empty string removes the default. */
		defaultEkmConnection: Option[String] = None
	)
	
	case class VerifyConnectivityResponse(
	
	)
	
	case class ListKeyRingsResponse(
	  /** The list of KeyRings. */
		keyRings: Option[List[Schema.KeyRing]] = None,
	  /** A token to retrieve next page of results. Pass this value in ListKeyRingsRequest.page_token to retrieve the next page of results. */
		nextPageToken: Option[String] = None,
	  /** The total number of KeyRings that matched the query. */
		totalSize: Option[Int] = None
	)
	
	case class KeyRing(
	  /** Output only. The resource name for the KeyRing in the format `projects/&#42;/locations/&#42;/keyRings/&#42;`. */
		name: Option[String] = None,
	  /** Output only. The time at which this KeyRing was created. */
		createTime: Option[String] = None
	)
	
	case class ListCryptoKeysResponse(
	  /** The list of CryptoKeys. */
		cryptoKeys: Option[List[Schema.CryptoKey]] = None,
	  /** A token to retrieve next page of results. Pass this value in ListCryptoKeysRequest.page_token to retrieve the next page of results. */
		nextPageToken: Option[String] = None,
	  /** The total number of CryptoKeys that matched the query. */
		totalSize: Option[Int] = None
	)
	
	object CryptoKey {
		enum PurposeEnum extends Enum[PurposeEnum] { case CRYPTO_KEY_PURPOSE_UNSPECIFIED, ENCRYPT_DECRYPT, ASYMMETRIC_SIGN, ASYMMETRIC_DECRYPT, RAW_ENCRYPT_DECRYPT, MAC }
	}
	case class CryptoKey(
	  /** Output only. The resource name for this CryptoKey in the format `projects/&#42;/locations/&#42;/keyRings/&#42;/cryptoKeys/&#42;`. */
		name: Option[String] = None,
	  /** Output only. A copy of the "primary" CryptoKeyVersion that will be used by Encrypt when this CryptoKey is given in EncryptRequest.name. The CryptoKey's primary version can be updated via UpdateCryptoKeyPrimaryVersion. Keys with purpose ENCRYPT_DECRYPT may have a primary. For other keys, this field will be omitted. */
		primary: Option[Schema.CryptoKeyVersion] = None,
	  /** Immutable. The immutable purpose of this CryptoKey. */
		purpose: Option[Schema.CryptoKey.PurposeEnum] = None,
	  /** Output only. The time at which this CryptoKey was created. */
		createTime: Option[String] = None,
	  /** At next_rotation_time, the Key Management Service will automatically: 1. Create a new version of this CryptoKey. 2. Mark the new version as primary. Key rotations performed manually via CreateCryptoKeyVersion and UpdateCryptoKeyPrimaryVersion do not affect next_rotation_time. Keys with purpose ENCRYPT_DECRYPT support automatic rotation. For other keys, this field must be omitted. */
		nextRotationTime: Option[String] = None,
	  /** next_rotation_time will be advanced by this period when the service automatically rotates a key. Must be at least 24 hours and at most 876,000 hours. If rotation_period is set, next_rotation_time must also be set. Keys with purpose ENCRYPT_DECRYPT support automatic rotation. For other keys, this field must be omitted. */
		rotationPeriod: Option[String] = None,
	  /** A template describing settings for new CryptoKeyVersion instances. The properties of new CryptoKeyVersion instances created by either CreateCryptoKeyVersion or auto-rotation are controlled by this template. */
		versionTemplate: Option[Schema.CryptoKeyVersionTemplate] = None,
	  /** Labels with user-defined metadata. For more information, see [Labeling Keys](https://cloud.google.com/kms/docs/labeling-keys). */
		labels: Option[Map[String, String]] = None,
	  /** Immutable. Whether this key may contain imported versions only. */
		importOnly: Option[Boolean] = None,
	  /** Immutable. The period of time that versions of this key spend in the DESTROY_SCHEDULED state before transitioning to DESTROYED. If not specified at creation time, the default duration is 30 days. */
		destroyScheduledDuration: Option[String] = None,
	  /** Immutable. The resource name of the backend environment where the key material for all CryptoKeyVersions associated with this CryptoKey reside and where all related cryptographic operations are performed. Only applicable if CryptoKeyVersions have a ProtectionLevel of EXTERNAL_VPC, with the resource name in the format `projects/&#42;/locations/&#42;/ekmConnections/&#42;`. Note, this list is non-exhaustive and may apply to additional ProtectionLevels in the future. */
		cryptoKeyBackend: Option[String] = None,
	  /** Optional. The policy used for Key Access Justifications Policy Enforcement. If this field is present and this key is enrolled in Key Access Justifications Policy Enforcement, the policy will be evaluated in encrypt, decrypt, and sign operations, and the operation will fail if rejected by the policy. The policy is defined by specifying zero or more allowed justification codes. https://cloud.google.com/assured-workloads/key-access-justifications/docs/justification-codes By default, this field is absent, and all justification codes are allowed. */
		keyAccessJustificationsPolicy: Option[Schema.KeyAccessJustificationsPolicy] = None
	)
	
	object CryptoKeyVersion {
		enum StateEnum extends Enum[StateEnum] { case CRYPTO_KEY_VERSION_STATE_UNSPECIFIED, PENDING_GENERATION, ENABLED, DISABLED, DESTROYED, DESTROY_SCHEDULED, PENDING_IMPORT, IMPORT_FAILED, GENERATION_FAILED, PENDING_EXTERNAL_DESTRUCTION, EXTERNAL_DESTRUCTION_FAILED }
		enum ProtectionLevelEnum extends Enum[ProtectionLevelEnum] { case PROTECTION_LEVEL_UNSPECIFIED, SOFTWARE, HSM, EXTERNAL, EXTERNAL_VPC }
		enum AlgorithmEnum extends Enum[AlgorithmEnum] { case CRYPTO_KEY_VERSION_ALGORITHM_UNSPECIFIED, GOOGLE_SYMMETRIC_ENCRYPTION, AES_128_GCM, AES_256_GCM, AES_128_CBC, AES_256_CBC, AES_128_CTR, AES_256_CTR, RSA_SIGN_PSS_2048_SHA256, RSA_SIGN_PSS_3072_SHA256, RSA_SIGN_PSS_4096_SHA256, RSA_SIGN_PSS_4096_SHA512, RSA_SIGN_PKCS1_2048_SHA256, RSA_SIGN_PKCS1_3072_SHA256, RSA_SIGN_PKCS1_4096_SHA256, RSA_SIGN_PKCS1_4096_SHA512, RSA_SIGN_RAW_PKCS1_2048, RSA_SIGN_RAW_PKCS1_3072, RSA_SIGN_RAW_PKCS1_4096, RSA_DECRYPT_OAEP_2048_SHA256, RSA_DECRYPT_OAEP_3072_SHA256, RSA_DECRYPT_OAEP_4096_SHA256, RSA_DECRYPT_OAEP_4096_SHA512, RSA_DECRYPT_OAEP_2048_SHA1, RSA_DECRYPT_OAEP_3072_SHA1, RSA_DECRYPT_OAEP_4096_SHA1, EC_SIGN_P256_SHA256, EC_SIGN_P384_SHA384, EC_SIGN_SECP256K1_SHA256, EC_SIGN_ED25519, HMAC_SHA256, HMAC_SHA1, HMAC_SHA384, HMAC_SHA512, HMAC_SHA224, EXTERNAL_SYMMETRIC_ENCRYPTION }
	}
	case class CryptoKeyVersion(
	  /** Output only. The resource name for this CryptoKeyVersion in the format `projects/&#42;/locations/&#42;/keyRings/&#42;/cryptoKeys/&#42;/cryptoKeyVersions/&#42;`. */
		name: Option[String] = None,
	  /** The current state of the CryptoKeyVersion. */
		state: Option[Schema.CryptoKeyVersion.StateEnum] = None,
	  /** Output only. The ProtectionLevel describing how crypto operations are performed with this CryptoKeyVersion. */
		protectionLevel: Option[Schema.CryptoKeyVersion.ProtectionLevelEnum] = None,
	  /** Output only. The CryptoKeyVersionAlgorithm that this CryptoKeyVersion supports. */
		algorithm: Option[Schema.CryptoKeyVersion.AlgorithmEnum] = None,
	  /** Output only. Statement that was generated and signed by the HSM at key creation time. Use this statement to verify attributes of the key as stored on the HSM, independently of Google. Only provided for key versions with protection_level HSM. */
		attestation: Option[Schema.KeyOperationAttestation] = None,
	  /** Output only. The time at which this CryptoKeyVersion was created. */
		createTime: Option[String] = None,
	  /** Output only. The time this CryptoKeyVersion's key material was generated. */
		generateTime: Option[String] = None,
	  /** Output only. The time this CryptoKeyVersion's key material is scheduled for destruction. Only present if state is DESTROY_SCHEDULED. */
		destroyTime: Option[String] = None,
	  /** Output only. The time this CryptoKeyVersion's key material was destroyed. Only present if state is DESTROYED. */
		destroyEventTime: Option[String] = None,
	  /** Output only. The name of the ImportJob used in the most recent import of this CryptoKeyVersion. Only present if the underlying key material was imported. */
		importJob: Option[String] = None,
	  /** Output only. The time at which this CryptoKeyVersion's key material was most recently imported. */
		importTime: Option[String] = None,
	  /** Output only. The root cause of the most recent import failure. Only present if state is IMPORT_FAILED. */
		importFailureReason: Option[String] = None,
	  /** Output only. The root cause of the most recent generation failure. Only present if state is GENERATION_FAILED. */
		generationFailureReason: Option[String] = None,
	  /** Output only. The root cause of the most recent external destruction failure. Only present if state is EXTERNAL_DESTRUCTION_FAILED. */
		externalDestructionFailureReason: Option[String] = None,
	  /** ExternalProtectionLevelOptions stores a group of additional fields for configuring a CryptoKeyVersion that are specific to the EXTERNAL protection level and EXTERNAL_VPC protection levels. */
		externalProtectionLevelOptions: Option[Schema.ExternalProtectionLevelOptions] = None,
	  /** Output only. Whether or not this key version is eligible for reimport, by being specified as a target in ImportCryptoKeyVersionRequest.crypto_key_version. */
		reimportEligible: Option[Boolean] = None
	)
	
	object KeyOperationAttestation {
		enum FormatEnum extends Enum[FormatEnum] { case ATTESTATION_FORMAT_UNSPECIFIED, CAVIUM_V1_COMPRESSED, CAVIUM_V2_COMPRESSED }
	}
	case class KeyOperationAttestation(
	  /** Output only. The format of the attestation data. */
		format: Option[Schema.KeyOperationAttestation.FormatEnum] = None,
	  /** Output only. The attestation data provided by the HSM when the key operation was performed. */
		content: Option[String] = None,
	  /** Output only. The certificate chains needed to validate the attestation */
		certChains: Option[Schema.CertificateChains] = None
	)
	
	case class CertificateChains(
	  /** Cavium certificate chain corresponding to the attestation. */
		caviumCerts: Option[List[String]] = None,
	  /** Google card certificate chain corresponding to the attestation. */
		googleCardCerts: Option[List[String]] = None,
	  /** Google partition certificate chain corresponding to the attestation. */
		googlePartitionCerts: Option[List[String]] = None
	)
	
	case class ExternalProtectionLevelOptions(
	  /** The URI for an external resource that this CryptoKeyVersion represents. */
		externalKeyUri: Option[String] = None,
	  /** The path to the external key material on the EKM when using EkmConnection e.g., "v0/my/key". Set this field instead of external_key_uri when using an EkmConnection. */
		ekmConnectionKeyPath: Option[String] = None
	)
	
	object CryptoKeyVersionTemplate {
		enum ProtectionLevelEnum extends Enum[ProtectionLevelEnum] { case PROTECTION_LEVEL_UNSPECIFIED, SOFTWARE, HSM, EXTERNAL, EXTERNAL_VPC }
		enum AlgorithmEnum extends Enum[AlgorithmEnum] { case CRYPTO_KEY_VERSION_ALGORITHM_UNSPECIFIED, GOOGLE_SYMMETRIC_ENCRYPTION, AES_128_GCM, AES_256_GCM, AES_128_CBC, AES_256_CBC, AES_128_CTR, AES_256_CTR, RSA_SIGN_PSS_2048_SHA256, RSA_SIGN_PSS_3072_SHA256, RSA_SIGN_PSS_4096_SHA256, RSA_SIGN_PSS_4096_SHA512, RSA_SIGN_PKCS1_2048_SHA256, RSA_SIGN_PKCS1_3072_SHA256, RSA_SIGN_PKCS1_4096_SHA256, RSA_SIGN_PKCS1_4096_SHA512, RSA_SIGN_RAW_PKCS1_2048, RSA_SIGN_RAW_PKCS1_3072, RSA_SIGN_RAW_PKCS1_4096, RSA_DECRYPT_OAEP_2048_SHA256, RSA_DECRYPT_OAEP_3072_SHA256, RSA_DECRYPT_OAEP_4096_SHA256, RSA_DECRYPT_OAEP_4096_SHA512, RSA_DECRYPT_OAEP_2048_SHA1, RSA_DECRYPT_OAEP_3072_SHA1, RSA_DECRYPT_OAEP_4096_SHA1, EC_SIGN_P256_SHA256, EC_SIGN_P384_SHA384, EC_SIGN_SECP256K1_SHA256, EC_SIGN_ED25519, HMAC_SHA256, HMAC_SHA1, HMAC_SHA384, HMAC_SHA512, HMAC_SHA224, EXTERNAL_SYMMETRIC_ENCRYPTION }
	}
	case class CryptoKeyVersionTemplate(
	  /** ProtectionLevel to use when creating a CryptoKeyVersion based on this template. Immutable. Defaults to SOFTWARE. */
		protectionLevel: Option[Schema.CryptoKeyVersionTemplate.ProtectionLevelEnum] = None,
	  /** Required. Algorithm to use when creating a CryptoKeyVersion based on this template. For backwards compatibility, GOOGLE_SYMMETRIC_ENCRYPTION is implied if both this field is omitted and CryptoKey.purpose is ENCRYPT_DECRYPT. */
		algorithm: Option[Schema.CryptoKeyVersionTemplate.AlgorithmEnum] = None
	)
	
	object KeyAccessJustificationsPolicy {
		enum AllowedAccessReasonsEnum extends Enum[AllowedAccessReasonsEnum] { case REASON_UNSPECIFIED, CUSTOMER_INITIATED_SUPPORT, GOOGLE_INITIATED_SERVICE, THIRD_PARTY_DATA_REQUEST, GOOGLE_INITIATED_REVIEW, CUSTOMER_INITIATED_ACCESS, GOOGLE_INITIATED_SYSTEM_OPERATION, REASON_NOT_EXPECTED, MODIFIED_CUSTOMER_INITIATED_ACCESS, MODIFIED_GOOGLE_INITIATED_SYSTEM_OPERATION, GOOGLE_RESPONSE_TO_PRODUCTION_ALERT, CUSTOMER_AUTHORIZED_WORKFLOW_SERVICING }
	}
	case class KeyAccessJustificationsPolicy(
	  /** The list of allowed reasons for access to a CryptoKey. Zero allowed access reasons means all encrypt, decrypt, and sign operations for the CryptoKey associated with this policy will fail. */
		allowedAccessReasons: Option[List[Schema.KeyAccessJustificationsPolicy.AllowedAccessReasonsEnum]] = None
	)
	
	case class ListCryptoKeyVersionsResponse(
	  /** The list of CryptoKeyVersions. */
		cryptoKeyVersions: Option[List[Schema.CryptoKeyVersion]] = None,
	  /** A token to retrieve next page of results. Pass this value in ListCryptoKeyVersionsRequest.page_token to retrieve the next page of results. */
		nextPageToken: Option[String] = None,
	  /** The total number of CryptoKeyVersions that matched the query. */
		totalSize: Option[Int] = None
	)
	
	case class ListImportJobsResponse(
	  /** The list of ImportJobs. */
		importJobs: Option[List[Schema.ImportJob]] = None,
	  /** A token to retrieve next page of results. Pass this value in ListImportJobsRequest.page_token to retrieve the next page of results. */
		nextPageToken: Option[String] = None,
	  /** The total number of ImportJobs that matched the query. */
		totalSize: Option[Int] = None
	)
	
	object ImportJob {
		enum ImportMethodEnum extends Enum[ImportMethodEnum] { case IMPORT_METHOD_UNSPECIFIED, RSA_OAEP_3072_SHA1_AES_256, RSA_OAEP_4096_SHA1_AES_256, RSA_OAEP_3072_SHA256_AES_256, RSA_OAEP_4096_SHA256_AES_256, RSA_OAEP_3072_SHA256, RSA_OAEP_4096_SHA256 }
		enum ProtectionLevelEnum extends Enum[ProtectionLevelEnum] { case PROTECTION_LEVEL_UNSPECIFIED, SOFTWARE, HSM, EXTERNAL, EXTERNAL_VPC }
		enum StateEnum extends Enum[StateEnum] { case IMPORT_JOB_STATE_UNSPECIFIED, PENDING_GENERATION, ACTIVE, EXPIRED }
	}
	case class ImportJob(
	  /** Output only. The resource name for this ImportJob in the format `projects/&#42;/locations/&#42;/keyRings/&#42;/importJobs/&#42;`. */
		name: Option[String] = None,
	  /** Required. Immutable. The wrapping method to be used for incoming key material. */
		importMethod: Option[Schema.ImportJob.ImportMethodEnum] = None,
	  /** Required. Immutable. The protection level of the ImportJob. This must match the protection_level of the version_template on the CryptoKey you attempt to import into. */
		protectionLevel: Option[Schema.ImportJob.ProtectionLevelEnum] = None,
	  /** Output only. The time at which this ImportJob was created. */
		createTime: Option[String] = None,
	  /** Output only. The time this ImportJob's key material was generated. */
		generateTime: Option[String] = None,
	  /** Output only. The time at which this ImportJob is scheduled for expiration and can no longer be used to import key material. */
		expireTime: Option[String] = None,
	  /** Output only. The time this ImportJob expired. Only present if state is EXPIRED. */
		expireEventTime: Option[String] = None,
	  /** Output only. The current state of the ImportJob, indicating if it can be used. */
		state: Option[Schema.ImportJob.StateEnum] = None,
	  /** Output only. The public key with which to wrap key material prior to import. Only returned if state is ACTIVE. */
		publicKey: Option[Schema.WrappingPublicKey] = None,
	  /** Output only. Statement that was generated and signed by the key creator (for example, an HSM) at key creation time. Use this statement to verify attributes of the key as stored on the HSM, independently of Google. Only present if the chosen ImportMethod is one with a protection level of HSM. */
		attestation: Option[Schema.KeyOperationAttestation] = None
	)
	
	case class WrappingPublicKey(
	  /** The public key, encoded in PEM format. For more information, see the [RFC 7468](https://tools.ietf.org/html/rfc7468) sections for [General Considerations](https://tools.ietf.org/html/rfc7468#section-2) and [Textual Encoding of Subject Public Key Info] (https://tools.ietf.org/html/rfc7468#section-13). */
		pem: Option[String] = None
	)
	
	object PublicKey {
		enum AlgorithmEnum extends Enum[AlgorithmEnum] { case CRYPTO_KEY_VERSION_ALGORITHM_UNSPECIFIED, GOOGLE_SYMMETRIC_ENCRYPTION, AES_128_GCM, AES_256_GCM, AES_128_CBC, AES_256_CBC, AES_128_CTR, AES_256_CTR, RSA_SIGN_PSS_2048_SHA256, RSA_SIGN_PSS_3072_SHA256, RSA_SIGN_PSS_4096_SHA256, RSA_SIGN_PSS_4096_SHA512, RSA_SIGN_PKCS1_2048_SHA256, RSA_SIGN_PKCS1_3072_SHA256, RSA_SIGN_PKCS1_4096_SHA256, RSA_SIGN_PKCS1_4096_SHA512, RSA_SIGN_RAW_PKCS1_2048, RSA_SIGN_RAW_PKCS1_3072, RSA_SIGN_RAW_PKCS1_4096, RSA_DECRYPT_OAEP_2048_SHA256, RSA_DECRYPT_OAEP_3072_SHA256, RSA_DECRYPT_OAEP_4096_SHA256, RSA_DECRYPT_OAEP_4096_SHA512, RSA_DECRYPT_OAEP_2048_SHA1, RSA_DECRYPT_OAEP_3072_SHA1, RSA_DECRYPT_OAEP_4096_SHA1, EC_SIGN_P256_SHA256, EC_SIGN_P384_SHA384, EC_SIGN_SECP256K1_SHA256, EC_SIGN_ED25519, HMAC_SHA256, HMAC_SHA1, HMAC_SHA384, HMAC_SHA512, HMAC_SHA224, EXTERNAL_SYMMETRIC_ENCRYPTION }
		enum ProtectionLevelEnum extends Enum[ProtectionLevelEnum] { case PROTECTION_LEVEL_UNSPECIFIED, SOFTWARE, HSM, EXTERNAL, EXTERNAL_VPC }
	}
	case class PublicKey(
	  /** The public key, encoded in PEM format. For more information, see the [RFC 7468](https://tools.ietf.org/html/rfc7468) sections for [General Considerations](https://tools.ietf.org/html/rfc7468#section-2) and [Textual Encoding of Subject Public Key Info] (https://tools.ietf.org/html/rfc7468#section-13). */
		pem: Option[String] = None,
	  /** The Algorithm associated with this key. */
		algorithm: Option[Schema.PublicKey.AlgorithmEnum] = None,
	  /** Integrity verification field. A CRC32C checksum of the returned PublicKey.pem. An integrity check of PublicKey.pem can be performed by computing the CRC32C checksum of PublicKey.pem and comparing your results to this field. Discard the response in case of non-matching checksum values, and perform a limited number of retries. A persistent mismatch may indicate an issue in your computation of the CRC32C checksum. Note: This field is defined as int64 for reasons of compatibility across different languages. However, it is a non-negative integer, which will never exceed 2^32-1, and can be safely downconverted to uint32 in languages that support this type. NOTE: This field is in Beta. */
		pemCrc32c: Option[String] = None,
	  /** The name of the CryptoKeyVersion public key. Provided here for verification. NOTE: This field is in Beta. */
		name: Option[String] = None,
	  /** The ProtectionLevel of the CryptoKeyVersion public key. */
		protectionLevel: Option[Schema.PublicKey.ProtectionLevelEnum] = None
	)
	
	object ImportCryptoKeyVersionRequest {
		enum AlgorithmEnum extends Enum[AlgorithmEnum] { case CRYPTO_KEY_VERSION_ALGORITHM_UNSPECIFIED, GOOGLE_SYMMETRIC_ENCRYPTION, AES_128_GCM, AES_256_GCM, AES_128_CBC, AES_256_CBC, AES_128_CTR, AES_256_CTR, RSA_SIGN_PSS_2048_SHA256, RSA_SIGN_PSS_3072_SHA256, RSA_SIGN_PSS_4096_SHA256, RSA_SIGN_PSS_4096_SHA512, RSA_SIGN_PKCS1_2048_SHA256, RSA_SIGN_PKCS1_3072_SHA256, RSA_SIGN_PKCS1_4096_SHA256, RSA_SIGN_PKCS1_4096_SHA512, RSA_SIGN_RAW_PKCS1_2048, RSA_SIGN_RAW_PKCS1_3072, RSA_SIGN_RAW_PKCS1_4096, RSA_DECRYPT_OAEP_2048_SHA256, RSA_DECRYPT_OAEP_3072_SHA256, RSA_DECRYPT_OAEP_4096_SHA256, RSA_DECRYPT_OAEP_4096_SHA512, RSA_DECRYPT_OAEP_2048_SHA1, RSA_DECRYPT_OAEP_3072_SHA1, RSA_DECRYPT_OAEP_4096_SHA1, EC_SIGN_P256_SHA256, EC_SIGN_P384_SHA384, EC_SIGN_SECP256K1_SHA256, EC_SIGN_ED25519, HMAC_SHA256, HMAC_SHA1, HMAC_SHA384, HMAC_SHA512, HMAC_SHA224, EXTERNAL_SYMMETRIC_ENCRYPTION }
	}
	case class ImportCryptoKeyVersionRequest(
	  /** Optional. The optional name of an existing CryptoKeyVersion to target for an import operation. If this field is not present, a new CryptoKeyVersion containing the supplied key material is created. If this field is present, the supplied key material is imported into the existing CryptoKeyVersion. To import into an existing CryptoKeyVersion, the CryptoKeyVersion must be a child of ImportCryptoKeyVersionRequest.parent, have been previously created via ImportCryptoKeyVersion, and be in DESTROYED or IMPORT_FAILED state. The key material and algorithm must match the previous CryptoKeyVersion exactly if the CryptoKeyVersion has ever contained key material. */
		cryptoKeyVersion: Option[String] = None,
	  /** Required. The algorithm of the key being imported. This does not need to match the version_template of the CryptoKey this version imports into. */
		algorithm: Option[Schema.ImportCryptoKeyVersionRequest.AlgorithmEnum] = None,
	  /** Required. The name of the ImportJob that was used to wrap this key material. */
		importJob: Option[String] = None,
	  /** Optional. The wrapped key material to import. Before wrapping, key material must be formatted. If importing symmetric key material, the expected key material format is plain bytes. If importing asymmetric key material, the expected key material format is PKCS#8-encoded DER (the PrivateKeyInfo structure from RFC 5208). When wrapping with import methods (RSA_OAEP_3072_SHA1_AES_256 or RSA_OAEP_4096_SHA1_AES_256 or RSA_OAEP_3072_SHA256_AES_256 or RSA_OAEP_4096_SHA256_AES_256), this field must contain the concatenation of: 1. An ephemeral AES-256 wrapping key wrapped with the public_key using RSAES-OAEP with SHA-1/SHA-256, MGF1 with SHA-1/SHA-256, and an empty label. 2. The formatted key to be imported, wrapped with the ephemeral AES-256 key using AES-KWP (RFC 5649). This format is the same as the format produced by PKCS#11 mechanism CKM_RSA_AES_KEY_WRAP. When wrapping with import methods (RSA_OAEP_3072_SHA256 or RSA_OAEP_4096_SHA256), this field must contain the formatted key to be imported, wrapped with the public_key using RSAES-OAEP with SHA-256, MGF1 with SHA-256, and an empty label. */
		wrappedKey: Option[String] = None,
	  /** Optional. This field has the same meaning as wrapped_key. Prefer to use that field in new work. Either that field or this field (but not both) must be specified. */
		rsaAesWrappedKey: Option[String] = None
	)
	
	case class UpdateCryptoKeyPrimaryVersionRequest(
	  /** Required. The id of the child CryptoKeyVersion to use as primary. */
		cryptoKeyVersionId: Option[String] = None
	)
	
	case class DestroyCryptoKeyVersionRequest(
	
	)
	
	case class RestoreCryptoKeyVersionRequest(
	
	)
	
	case class EncryptRequest(
	  /** Required. The data to encrypt. Must be no larger than 64KiB. The maximum size depends on the key version's protection_level. For SOFTWARE, EXTERNAL, and EXTERNAL_VPC keys, the plaintext must be no larger than 64KiB. For HSM keys, the combined length of the plaintext and additional_authenticated_data fields must be no larger than 8KiB. */
		plaintext: Option[String] = None,
	  /** Optional. Optional data that, if specified, must also be provided during decryption through DecryptRequest.additional_authenticated_data. The maximum size depends on the key version's protection_level. For SOFTWARE, EXTERNAL, and EXTERNAL_VPC keys the AAD must be no larger than 64KiB. For HSM keys, the combined length of the plaintext and additional_authenticated_data fields must be no larger than 8KiB. */
		additionalAuthenticatedData: Option[String] = None,
	  /** Optional. An optional CRC32C checksum of the EncryptRequest.plaintext. If specified, KeyManagementService will verify the integrity of the received EncryptRequest.plaintext using this checksum. KeyManagementService will report an error if the checksum verification fails. If you receive a checksum error, your client should verify that CRC32C(EncryptRequest.plaintext) is equal to EncryptRequest.plaintext_crc32c, and if so, perform a limited number of retries. A persistent mismatch may indicate an issue in your computation of the CRC32C checksum. Note: This field is defined as int64 for reasons of compatibility across different languages. However, it is a non-negative integer, which will never exceed 2^32-1, and can be safely downconverted to uint32 in languages that support this type. */
		plaintextCrc32c: Option[String] = None,
	  /** Optional. An optional CRC32C checksum of the EncryptRequest.additional_authenticated_data. If specified, KeyManagementService will verify the integrity of the received EncryptRequest.additional_authenticated_data using this checksum. KeyManagementService will report an error if the checksum verification fails. If you receive a checksum error, your client should verify that CRC32C(EncryptRequest.additional_authenticated_data) is equal to EncryptRequest.additional_authenticated_data_crc32c, and if so, perform a limited number of retries. A persistent mismatch may indicate an issue in your computation of the CRC32C checksum. Note: This field is defined as int64 for reasons of compatibility across different languages. However, it is a non-negative integer, which will never exceed 2^32-1, and can be safely downconverted to uint32 in languages that support this type. */
		additionalAuthenticatedDataCrc32c: Option[String] = None
	)
	
	object EncryptResponse {
		enum ProtectionLevelEnum extends Enum[ProtectionLevelEnum] { case PROTECTION_LEVEL_UNSPECIFIED, SOFTWARE, HSM, EXTERNAL, EXTERNAL_VPC }
	}
	case class EncryptResponse(
	  /** The resource name of the CryptoKeyVersion used in encryption. Check this field to verify that the intended resource was used for encryption. */
		name: Option[String] = None,
	  /** The encrypted data. */
		ciphertext: Option[String] = None,
	  /** Integrity verification field. A CRC32C checksum of the returned EncryptResponse.ciphertext. An integrity check of EncryptResponse.ciphertext can be performed by computing the CRC32C checksum of EncryptResponse.ciphertext and comparing your results to this field. Discard the response in case of non-matching checksum values, and perform a limited number of retries. A persistent mismatch may indicate an issue in your computation of the CRC32C checksum. Note: This field is defined as int64 for reasons of compatibility across different languages. However, it is a non-negative integer, which will never exceed 2^32-1, and can be safely downconverted to uint32 in languages that support this type. */
		ciphertextCrc32c: Option[String] = None,
	  /** Integrity verification field. A flag indicating whether EncryptRequest.plaintext_crc32c was received by KeyManagementService and used for the integrity verification of the plaintext. A false value of this field indicates either that EncryptRequest.plaintext_crc32c was left unset or that it was not delivered to KeyManagementService. If you've set EncryptRequest.plaintext_crc32c but this field is still false, discard the response and perform a limited number of retries. */
		verifiedPlaintextCrc32c: Option[Boolean] = None,
	  /** Integrity verification field. A flag indicating whether EncryptRequest.additional_authenticated_data_crc32c was received by KeyManagementService and used for the integrity verification of the AAD. A false value of this field indicates either that EncryptRequest.additional_authenticated_data_crc32c was left unset or that it was not delivered to KeyManagementService. If you've set EncryptRequest.additional_authenticated_data_crc32c but this field is still false, discard the response and perform a limited number of retries. */
		verifiedAdditionalAuthenticatedDataCrc32c: Option[Boolean] = None,
	  /** The ProtectionLevel of the CryptoKeyVersion used in encryption. */
		protectionLevel: Option[Schema.EncryptResponse.ProtectionLevelEnum] = None
	)
	
	case class DecryptRequest(
	  /** Required. The encrypted data originally returned in EncryptResponse.ciphertext. */
		ciphertext: Option[String] = None,
	  /** Optional. Optional data that must match the data originally supplied in EncryptRequest.additional_authenticated_data. */
		additionalAuthenticatedData: Option[String] = None,
	  /** Optional. An optional CRC32C checksum of the DecryptRequest.ciphertext. If specified, KeyManagementService will verify the integrity of the received DecryptRequest.ciphertext using this checksum. KeyManagementService will report an error if the checksum verification fails. If you receive a checksum error, your client should verify that CRC32C(DecryptRequest.ciphertext) is equal to DecryptRequest.ciphertext_crc32c, and if so, perform a limited number of retries. A persistent mismatch may indicate an issue in your computation of the CRC32C checksum. Note: This field is defined as int64 for reasons of compatibility across different languages. However, it is a non-negative integer, which will never exceed 2^32-1, and can be safely downconverted to uint32 in languages that support this type. */
		ciphertextCrc32c: Option[String] = None,
	  /** Optional. An optional CRC32C checksum of the DecryptRequest.additional_authenticated_data. If specified, KeyManagementService will verify the integrity of the received DecryptRequest.additional_authenticated_data using this checksum. KeyManagementService will report an error if the checksum verification fails. If you receive a checksum error, your client should verify that CRC32C(DecryptRequest.additional_authenticated_data) is equal to DecryptRequest.additional_authenticated_data_crc32c, and if so, perform a limited number of retries. A persistent mismatch may indicate an issue in your computation of the CRC32C checksum. Note: This field is defined as int64 for reasons of compatibility across different languages. However, it is a non-negative integer, which will never exceed 2^32-1, and can be safely downconverted to uint32 in languages that support this type. */
		additionalAuthenticatedDataCrc32c: Option[String] = None
	)
	
	object DecryptResponse {
		enum ProtectionLevelEnum extends Enum[ProtectionLevelEnum] { case PROTECTION_LEVEL_UNSPECIFIED, SOFTWARE, HSM, EXTERNAL, EXTERNAL_VPC }
	}
	case class DecryptResponse(
	  /** The decrypted data originally supplied in EncryptRequest.plaintext. */
		plaintext: Option[String] = None,
	  /** Integrity verification field. A CRC32C checksum of the returned DecryptResponse.plaintext. An integrity check of DecryptResponse.plaintext can be performed by computing the CRC32C checksum of DecryptResponse.plaintext and comparing your results to this field. Discard the response in case of non-matching checksum values, and perform a limited number of retries. A persistent mismatch may indicate an issue in your computation of the CRC32C checksum. Note: receiving this response message indicates that KeyManagementService is able to successfully decrypt the ciphertext. Note: This field is defined as int64 for reasons of compatibility across different languages. However, it is a non-negative integer, which will never exceed 2^32-1, and can be safely downconverted to uint32 in languages that support this type. */
		plaintextCrc32c: Option[String] = None,
	  /** Whether the Decryption was performed using the primary key version. */
		usedPrimary: Option[Boolean] = None,
	  /** The ProtectionLevel of the CryptoKeyVersion used in decryption. */
		protectionLevel: Option[Schema.DecryptResponse.ProtectionLevelEnum] = None
	)
	
	case class RawEncryptRequest(
	  /** Required. The data to encrypt. Must be no larger than 64KiB. The maximum size depends on the key version's protection_level. For SOFTWARE keys, the plaintext must be no larger than 64KiB. For HSM keys, the combined length of the plaintext and additional_authenticated_data fields must be no larger than 8KiB. */
		plaintext: Option[String] = None,
	  /** Optional. Optional data that, if specified, must also be provided during decryption through RawDecryptRequest.additional_authenticated_data. This field may only be used in conjunction with an algorithm that accepts additional authenticated data (for example, AES-GCM). The maximum size depends on the key version's protection_level. For SOFTWARE keys, the plaintext must be no larger than 64KiB. For HSM keys, the combined length of the plaintext and additional_authenticated_data fields must be no larger than 8KiB. */
		additionalAuthenticatedData: Option[String] = None,
	  /** Optional. An optional CRC32C checksum of the RawEncryptRequest.plaintext. If specified, KeyManagementService will verify the integrity of the received plaintext using this checksum. KeyManagementService will report an error if the checksum verification fails. If you receive a checksum error, your client should verify that CRC32C(plaintext) is equal to plaintext_crc32c, and if so, perform a limited number of retries. A persistent mismatch may indicate an issue in your computation of the CRC32C checksum. Note: This field is defined as int64 for reasons of compatibility across different languages. However, it is a non-negative integer, which will never exceed 2^32-1, and can be safely downconverted to uint32 in languages that support this type. */
		plaintextCrc32c: Option[String] = None,
	  /** Optional. An optional CRC32C checksum of the RawEncryptRequest.additional_authenticated_data. If specified, KeyManagementService will verify the integrity of the received additional_authenticated_data using this checksum. KeyManagementService will report an error if the checksum verification fails. If you receive a checksum error, your client should verify that CRC32C(additional_authenticated_data) is equal to additional_authenticated_data_crc32c, and if so, perform a limited number of retries. A persistent mismatch may indicate an issue in your computation of the CRC32C checksum. Note: This field is defined as int64 for reasons of compatibility across different languages. However, it is a non-negative integer, which will never exceed 2^32-1, and can be safely downconverted to uint32 in languages that support this type. */
		additionalAuthenticatedDataCrc32c: Option[String] = None,
	  /** Optional. A customer-supplied initialization vector that will be used for encryption. If it is not provided for AES-CBC and AES-CTR, one will be generated. It will be returned in RawEncryptResponse.initialization_vector. */
		initializationVector: Option[String] = None,
	  /** Optional. An optional CRC32C checksum of the RawEncryptRequest.initialization_vector. If specified, KeyManagementService will verify the integrity of the received initialization_vector using this checksum. KeyManagementService will report an error if the checksum verification fails. If you receive a checksum error, your client should verify that CRC32C(initialization_vector) is equal to initialization_vector_crc32c, and if so, perform a limited number of retries. A persistent mismatch may indicate an issue in your computation of the CRC32C checksum. Note: This field is defined as int64 for reasons of compatibility across different languages. However, it is a non-negative integer, which will never exceed 2^32-1, and can be safely downconverted to uint32 in languages that support this type. */
		initializationVectorCrc32c: Option[String] = None
	)
	
	object RawEncryptResponse {
		enum ProtectionLevelEnum extends Enum[ProtectionLevelEnum] { case PROTECTION_LEVEL_UNSPECIFIED, SOFTWARE, HSM, EXTERNAL, EXTERNAL_VPC }
	}
	case class RawEncryptResponse(
	  /** The encrypted data. In the case of AES-GCM, the authentication tag is the tag_length bytes at the end of this field. */
		ciphertext: Option[String] = None,
	  /** The initialization vector (IV) generated by the service during encryption. This value must be stored and provided in RawDecryptRequest.initialization_vector at decryption time. */
		initializationVector: Option[String] = None,
	  /** The length of the authentication tag that is appended to the end of the ciphertext. */
		tagLength: Option[Int] = None,
	  /** Integrity verification field. A CRC32C checksum of the returned RawEncryptResponse.ciphertext. An integrity check of ciphertext can be performed by computing the CRC32C checksum of ciphertext and comparing your results to this field. Discard the response in case of non-matching checksum values, and perform a limited number of retries. A persistent mismatch may indicate an issue in your computation of the CRC32C checksum. Note: This field is defined as int64 for reasons of compatibility across different languages. However, it is a non-negative integer, which will never exceed 2^32-1, and can be safely downconverted to uint32 in languages that support this type. */
		ciphertextCrc32c: Option[String] = None,
	  /** Integrity verification field. A CRC32C checksum of the returned RawEncryptResponse.initialization_vector. An integrity check of initialization_vector can be performed by computing the CRC32C checksum of initialization_vector and comparing your results to this field. Discard the response in case of non-matching checksum values, and perform a limited number of retries. A persistent mismatch may indicate an issue in your computation of the CRC32C checksum. Note: This field is defined as int64 for reasons of compatibility across different languages. However, it is a non-negative integer, which will never exceed 2^32-1, and can be safely downconverted to uint32 in languages that support this type. */
		initializationVectorCrc32c: Option[String] = None,
	  /** Integrity verification field. A flag indicating whether RawEncryptRequest.plaintext_crc32c was received by KeyManagementService and used for the integrity verification of the plaintext. A false value of this field indicates either that RawEncryptRequest.plaintext_crc32c was left unset or that it was not delivered to KeyManagementService. If you've set RawEncryptRequest.plaintext_crc32c but this field is still false, discard the response and perform a limited number of retries. */
		verifiedPlaintextCrc32c: Option[Boolean] = None,
	  /** Integrity verification field. A flag indicating whether RawEncryptRequest.additional_authenticated_data_crc32c was received by KeyManagementService and used for the integrity verification of additional_authenticated_data. A false value of this field indicates either that // RawEncryptRequest.additional_authenticated_data_crc32c was left unset or that it was not delivered to KeyManagementService. If you've set RawEncryptRequest.additional_authenticated_data_crc32c but this field is still false, discard the response and perform a limited number of retries. */
		verifiedAdditionalAuthenticatedDataCrc32c: Option[Boolean] = None,
	  /** Integrity verification field. A flag indicating whether RawEncryptRequest.initialization_vector_crc32c was received by KeyManagementService and used for the integrity verification of initialization_vector. A false value of this field indicates either that RawEncryptRequest.initialization_vector_crc32c was left unset or that it was not delivered to KeyManagementService. If you've set RawEncryptRequest.initialization_vector_crc32c but this field is still false, discard the response and perform a limited number of retries. */
		verifiedInitializationVectorCrc32c: Option[Boolean] = None,
	  /** The resource name of the CryptoKeyVersion used in encryption. Check this field to verify that the intended resource was used for encryption. */
		name: Option[String] = None,
	  /** The ProtectionLevel of the CryptoKeyVersion used in encryption. */
		protectionLevel: Option[Schema.RawEncryptResponse.ProtectionLevelEnum] = None
	)
	
	case class RawDecryptRequest(
	  /** Required. The encrypted data originally returned in RawEncryptResponse.ciphertext. */
		ciphertext: Option[String] = None,
	  /** Optional. Optional data that must match the data originally supplied in RawEncryptRequest.additional_authenticated_data. */
		additionalAuthenticatedData: Option[String] = None,
	  /** Required. The initialization vector (IV) used during encryption, which must match the data originally provided in RawEncryptResponse.initialization_vector. */
		initializationVector: Option[String] = None,
	  /** The length of the authentication tag that is appended to the end of the ciphertext. If unspecified (0), the default value for the key's algorithm will be used (for AES-GCM, the default value is 16). */
		tagLength: Option[Int] = None,
	  /** Optional. An optional CRC32C checksum of the RawDecryptRequest.ciphertext. If specified, KeyManagementService will verify the integrity of the received ciphertext using this checksum. KeyManagementService will report an error if the checksum verification fails. If you receive a checksum error, your client should verify that CRC32C(ciphertext) is equal to ciphertext_crc32c, and if so, perform a limited number of retries. A persistent mismatch may indicate an issue in your computation of the CRC32C checksum. Note: This field is defined as int64 for reasons of compatibility across different languages. However, it is a non-negative integer, which will never exceed 2^32-1, and can be safely downconverted to uint32 in languages that support this type. */
		ciphertextCrc32c: Option[String] = None,
	  /** Optional. An optional CRC32C checksum of the RawDecryptRequest.additional_authenticated_data. If specified, KeyManagementService will verify the integrity of the received additional_authenticated_data using this checksum. KeyManagementService will report an error if the checksum verification fails. If you receive a checksum error, your client should verify that CRC32C(additional_authenticated_data) is equal to additional_authenticated_data_crc32c, and if so, perform a limited number of retries. A persistent mismatch may indicate an issue in your computation of the CRC32C checksum. Note: This field is defined as int64 for reasons of compatibility across different languages. However, it is a non-negative integer, which will never exceed 2^32-1, and can be safely downconverted to uint32 in languages that support this type. */
		additionalAuthenticatedDataCrc32c: Option[String] = None,
	  /** Optional. An optional CRC32C checksum of the RawDecryptRequest.initialization_vector. If specified, KeyManagementService will verify the integrity of the received initialization_vector using this checksum. KeyManagementService will report an error if the checksum verification fails. If you receive a checksum error, your client should verify that CRC32C(initialization_vector) is equal to initialization_vector_crc32c, and if so, perform a limited number of retries. A persistent mismatch may indicate an issue in your computation of the CRC32C checksum. Note: This field is defined as int64 for reasons of compatibility across different languages. However, it is a non-negative integer, which will never exceed 2^32-1, and can be safely downconverted to uint32 in languages that support this type. */
		initializationVectorCrc32c: Option[String] = None
	)
	
	object RawDecryptResponse {
		enum ProtectionLevelEnum extends Enum[ProtectionLevelEnum] { case PROTECTION_LEVEL_UNSPECIFIED, SOFTWARE, HSM, EXTERNAL, EXTERNAL_VPC }
	}
	case class RawDecryptResponse(
	  /** The decrypted data. */
		plaintext: Option[String] = None,
	  /** Integrity verification field. A CRC32C checksum of the returned RawDecryptResponse.plaintext. An integrity check of plaintext can be performed by computing the CRC32C checksum of plaintext and comparing your results to this field. Discard the response in case of non-matching checksum values, and perform a limited number of retries. A persistent mismatch may indicate an issue in your computation of the CRC32C checksum. Note: receiving this response message indicates that KeyManagementService is able to successfully decrypt the ciphertext. Note: This field is defined as int64 for reasons of compatibility across different languages. However, it is a non-negative integer, which will never exceed 2^32-1, and can be safely downconverted to uint32 in languages that support this type. */
		plaintextCrc32c: Option[String] = None,
	  /** The ProtectionLevel of the CryptoKeyVersion used in decryption. */
		protectionLevel: Option[Schema.RawDecryptResponse.ProtectionLevelEnum] = None,
	  /** Integrity verification field. A flag indicating whether RawDecryptRequest.ciphertext_crc32c was received by KeyManagementService and used for the integrity verification of the ciphertext. A false value of this field indicates either that RawDecryptRequest.ciphertext_crc32c was left unset or that it was not delivered to KeyManagementService. If you've set RawDecryptRequest.ciphertext_crc32c but this field is still false, discard the response and perform a limited number of retries. */
		verifiedCiphertextCrc32c: Option[Boolean] = None,
	  /** Integrity verification field. A flag indicating whether RawDecryptRequest.additional_authenticated_data_crc32c was received by KeyManagementService and used for the integrity verification of additional_authenticated_data. A false value of this field indicates either that // RawDecryptRequest.additional_authenticated_data_crc32c was left unset or that it was not delivered to KeyManagementService. If you've set RawDecryptRequest.additional_authenticated_data_crc32c but this field is still false, discard the response and perform a limited number of retries. */
		verifiedAdditionalAuthenticatedDataCrc32c: Option[Boolean] = None,
	  /** Integrity verification field. A flag indicating whether RawDecryptRequest.initialization_vector_crc32c was received by KeyManagementService and used for the integrity verification of initialization_vector. A false value of this field indicates either that RawDecryptRequest.initialization_vector_crc32c was left unset or that it was not delivered to KeyManagementService. If you've set RawDecryptRequest.initialization_vector_crc32c but this field is still false, discard the response and perform a limited number of retries. */
		verifiedInitializationVectorCrc32c: Option[Boolean] = None
	)
	
	case class AsymmetricSignRequest(
	  /** Optional. The digest of the data to sign. The digest must be produced with the same digest algorithm as specified by the key version's algorithm. This field may not be supplied if AsymmetricSignRequest.data is supplied. */
		digest: Option[Schema.Digest] = None,
	  /** Optional. An optional CRC32C checksum of the AsymmetricSignRequest.digest. If specified, KeyManagementService will verify the integrity of the received AsymmetricSignRequest.digest using this checksum. KeyManagementService will report an error if the checksum verification fails. If you receive a checksum error, your client should verify that CRC32C(AsymmetricSignRequest.digest) is equal to AsymmetricSignRequest.digest_crc32c, and if so, perform a limited number of retries. A persistent mismatch may indicate an issue in your computation of the CRC32C checksum. Note: This field is defined as int64 for reasons of compatibility across different languages. However, it is a non-negative integer, which will never exceed 2^32-1, and can be safely downconverted to uint32 in languages that support this type. */
		digestCrc32c: Option[String] = None,
	  /** Optional. The data to sign. It can't be supplied if AsymmetricSignRequest.digest is supplied. */
		data: Option[String] = None,
	  /** Optional. An optional CRC32C checksum of the AsymmetricSignRequest.data. If specified, KeyManagementService will verify the integrity of the received AsymmetricSignRequest.data using this checksum. KeyManagementService will report an error if the checksum verification fails. If you receive a checksum error, your client should verify that CRC32C(AsymmetricSignRequest.data) is equal to AsymmetricSignRequest.data_crc32c, and if so, perform a limited number of retries. A persistent mismatch may indicate an issue in your computation of the CRC32C checksum. Note: This field is defined as int64 for reasons of compatibility across different languages. However, it is a non-negative integer, which will never exceed 2^32-1, and can be safely downconverted to uint32 in languages that support this type. */
		dataCrc32c: Option[String] = None
	)
	
	case class Digest(
	  /** A message digest produced with the SHA-256 algorithm. */
		sha256: Option[String] = None,
	  /** A message digest produced with the SHA-384 algorithm. */
		sha384: Option[String] = None,
	  /** A message digest produced with the SHA-512 algorithm. */
		sha512: Option[String] = None
	)
	
	object AsymmetricSignResponse {
		enum ProtectionLevelEnum extends Enum[ProtectionLevelEnum] { case PROTECTION_LEVEL_UNSPECIFIED, SOFTWARE, HSM, EXTERNAL, EXTERNAL_VPC }
	}
	case class AsymmetricSignResponse(
	  /** The created signature. */
		signature: Option[String] = None,
	  /** Integrity verification field. A CRC32C checksum of the returned AsymmetricSignResponse.signature. An integrity check of AsymmetricSignResponse.signature can be performed by computing the CRC32C checksum of AsymmetricSignResponse.signature and comparing your results to this field. Discard the response in case of non-matching checksum values, and perform a limited number of retries. A persistent mismatch may indicate an issue in your computation of the CRC32C checksum. Note: This field is defined as int64 for reasons of compatibility across different languages. However, it is a non-negative integer, which will never exceed 2^32-1, and can be safely downconverted to uint32 in languages that support this type. */
		signatureCrc32c: Option[String] = None,
	  /** Integrity verification field. A flag indicating whether AsymmetricSignRequest.digest_crc32c was received by KeyManagementService and used for the integrity verification of the digest. A false value of this field indicates either that AsymmetricSignRequest.digest_crc32c was left unset or that it was not delivered to KeyManagementService. If you've set AsymmetricSignRequest.digest_crc32c but this field is still false, discard the response and perform a limited number of retries. */
		verifiedDigestCrc32c: Option[Boolean] = None,
	  /** The resource name of the CryptoKeyVersion used for signing. Check this field to verify that the intended resource was used for signing. */
		name: Option[String] = None,
	  /** Integrity verification field. A flag indicating whether AsymmetricSignRequest.data_crc32c was received by KeyManagementService and used for the integrity verification of the data. A false value of this field indicates either that AsymmetricSignRequest.data_crc32c was left unset or that it was not delivered to KeyManagementService. If you've set AsymmetricSignRequest.data_crc32c but this field is still false, discard the response and perform a limited number of retries. */
		verifiedDataCrc32c: Option[Boolean] = None,
	  /** The ProtectionLevel of the CryptoKeyVersion used for signing. */
		protectionLevel: Option[Schema.AsymmetricSignResponse.ProtectionLevelEnum] = None
	)
	
	case class AsymmetricDecryptRequest(
	  /** Required. The data encrypted with the named CryptoKeyVersion's public key using OAEP. */
		ciphertext: Option[String] = None,
	  /** Optional. An optional CRC32C checksum of the AsymmetricDecryptRequest.ciphertext. If specified, KeyManagementService will verify the integrity of the received AsymmetricDecryptRequest.ciphertext using this checksum. KeyManagementService will report an error if the checksum verification fails. If you receive a checksum error, your client should verify that CRC32C(AsymmetricDecryptRequest.ciphertext) is equal to AsymmetricDecryptRequest.ciphertext_crc32c, and if so, perform a limited number of retries. A persistent mismatch may indicate an issue in your computation of the CRC32C checksum. Note: This field is defined as int64 for reasons of compatibility across different languages. However, it is a non-negative integer, which will never exceed 2^32-1, and can be safely downconverted to uint32 in languages that support this type. */
		ciphertextCrc32c: Option[String] = None
	)
	
	object AsymmetricDecryptResponse {
		enum ProtectionLevelEnum extends Enum[ProtectionLevelEnum] { case PROTECTION_LEVEL_UNSPECIFIED, SOFTWARE, HSM, EXTERNAL, EXTERNAL_VPC }
	}
	case class AsymmetricDecryptResponse(
	  /** The decrypted data originally encrypted with the matching public key. */
		plaintext: Option[String] = None,
	  /** Integrity verification field. A CRC32C checksum of the returned AsymmetricDecryptResponse.plaintext. An integrity check of AsymmetricDecryptResponse.plaintext can be performed by computing the CRC32C checksum of AsymmetricDecryptResponse.plaintext and comparing your results to this field. Discard the response in case of non-matching checksum values, and perform a limited number of retries. A persistent mismatch may indicate an issue in your computation of the CRC32C checksum. Note: This field is defined as int64 for reasons of compatibility across different languages. However, it is a non-negative integer, which will never exceed 2^32-1, and can be safely downconverted to uint32 in languages that support this type. */
		plaintextCrc32c: Option[String] = None,
	  /** Integrity verification field. A flag indicating whether AsymmetricDecryptRequest.ciphertext_crc32c was received by KeyManagementService and used for the integrity verification of the ciphertext. A false value of this field indicates either that AsymmetricDecryptRequest.ciphertext_crc32c was left unset or that it was not delivered to KeyManagementService. If you've set AsymmetricDecryptRequest.ciphertext_crc32c but this field is still false, discard the response and perform a limited number of retries. */
		verifiedCiphertextCrc32c: Option[Boolean] = None,
	  /** The ProtectionLevel of the CryptoKeyVersion used in decryption. */
		protectionLevel: Option[Schema.AsymmetricDecryptResponse.ProtectionLevelEnum] = None
	)
	
	case class MacSignRequest(
	  /** Required. The data to sign. The MAC tag is computed over this data field based on the specific algorithm. */
		data: Option[String] = None,
	  /** Optional. An optional CRC32C checksum of the MacSignRequest.data. If specified, KeyManagementService will verify the integrity of the received MacSignRequest.data using this checksum. KeyManagementService will report an error if the checksum verification fails. If you receive a checksum error, your client should verify that CRC32C(MacSignRequest.data) is equal to MacSignRequest.data_crc32c, and if so, perform a limited number of retries. A persistent mismatch may indicate an issue in your computation of the CRC32C checksum. Note: This field is defined as int64 for reasons of compatibility across different languages. However, it is a non-negative integer, which will never exceed 2^32-1, and can be safely downconverted to uint32 in languages that support this type. */
		dataCrc32c: Option[String] = None
	)
	
	object MacSignResponse {
		enum ProtectionLevelEnum extends Enum[ProtectionLevelEnum] { case PROTECTION_LEVEL_UNSPECIFIED, SOFTWARE, HSM, EXTERNAL, EXTERNAL_VPC }
	}
	case class MacSignResponse(
	  /** The resource name of the CryptoKeyVersion used for signing. Check this field to verify that the intended resource was used for signing. */
		name: Option[String] = None,
	  /** The created signature. */
		mac: Option[String] = None,
	  /** Integrity verification field. A CRC32C checksum of the returned MacSignResponse.mac. An integrity check of MacSignResponse.mac can be performed by computing the CRC32C checksum of MacSignResponse.mac and comparing your results to this field. Discard the response in case of non-matching checksum values, and perform a limited number of retries. A persistent mismatch may indicate an issue in your computation of the CRC32C checksum. Note: This field is defined as int64 for reasons of compatibility across different languages. However, it is a non-negative integer, which will never exceed 2^32-1, and can be safely downconverted to uint32 in languages that support this type. */
		macCrc32c: Option[String] = None,
	  /** Integrity verification field. A flag indicating whether MacSignRequest.data_crc32c was received by KeyManagementService and used for the integrity verification of the data. A false value of this field indicates either that MacSignRequest.data_crc32c was left unset or that it was not delivered to KeyManagementService. If you've set MacSignRequest.data_crc32c but this field is still false, discard the response and perform a limited number of retries. */
		verifiedDataCrc32c: Option[Boolean] = None,
	  /** The ProtectionLevel of the CryptoKeyVersion used for signing. */
		protectionLevel: Option[Schema.MacSignResponse.ProtectionLevelEnum] = None
	)
	
	case class MacVerifyRequest(
	  /** Required. The data used previously as a MacSignRequest.data to generate the MAC tag. */
		data: Option[String] = None,
	  /** Optional. An optional CRC32C checksum of the MacVerifyRequest.data. If specified, KeyManagementService will verify the integrity of the received MacVerifyRequest.data using this checksum. KeyManagementService will report an error if the checksum verification fails. If you receive a checksum error, your client should verify that CRC32C(MacVerifyRequest.data) is equal to MacVerifyRequest.data_crc32c, and if so, perform a limited number of retries. A persistent mismatch may indicate an issue in your computation of the CRC32C checksum. Note: This field is defined as int64 for reasons of compatibility across different languages. However, it is a non-negative integer, which will never exceed 2^32-1, and can be safely downconverted to uint32 in languages that support this type. */
		dataCrc32c: Option[String] = None,
	  /** Required. The signature to verify. */
		mac: Option[String] = None,
	  /** Optional. An optional CRC32C checksum of the MacVerifyRequest.mac. If specified, KeyManagementService will verify the integrity of the received MacVerifyRequest.mac using this checksum. KeyManagementService will report an error if the checksum verification fails. If you receive a checksum error, your client should verify that CRC32C(MacVerifyRequest.tag) is equal to MacVerifyRequest.mac_crc32c, and if so, perform a limited number of retries. A persistent mismatch may indicate an issue in your computation of the CRC32C checksum. Note: This field is defined as int64 for reasons of compatibility across different languages. However, it is a non-negative integer, which will never exceed 2^32-1, and can be safely downconverted to uint32 in languages that support this type. */
		macCrc32c: Option[String] = None
	)
	
	object MacVerifyResponse {
		enum ProtectionLevelEnum extends Enum[ProtectionLevelEnum] { case PROTECTION_LEVEL_UNSPECIFIED, SOFTWARE, HSM, EXTERNAL, EXTERNAL_VPC }
	}
	case class MacVerifyResponse(
	  /** The resource name of the CryptoKeyVersion used for verification. Check this field to verify that the intended resource was used for verification. */
		name: Option[String] = None,
	  /** This field indicates whether or not the verification operation for MacVerifyRequest.mac over MacVerifyRequest.data was successful. */
		success: Option[Boolean] = None,
	  /** Integrity verification field. A flag indicating whether MacVerifyRequest.data_crc32c was received by KeyManagementService and used for the integrity verification of the data. A false value of this field indicates either that MacVerifyRequest.data_crc32c was left unset or that it was not delivered to KeyManagementService. If you've set MacVerifyRequest.data_crc32c but this field is still false, discard the response and perform a limited number of retries. */
		verifiedDataCrc32c: Option[Boolean] = None,
	  /** Integrity verification field. A flag indicating whether MacVerifyRequest.mac_crc32c was received by KeyManagementService and used for the integrity verification of the data. A false value of this field indicates either that MacVerifyRequest.mac_crc32c was left unset or that it was not delivered to KeyManagementService. If you've set MacVerifyRequest.mac_crc32c but this field is still false, discard the response and perform a limited number of retries. */
		verifiedMacCrc32c: Option[Boolean] = None,
	  /** Integrity verification field. This value is used for the integrity verification of [MacVerifyResponse.success]. If the value of this field contradicts the value of [MacVerifyResponse.success], discard the response and perform a limited number of retries. */
		verifiedSuccessIntegrity: Option[Boolean] = None,
	  /** The ProtectionLevel of the CryptoKeyVersion used for verification. */
		protectionLevel: Option[Schema.MacVerifyResponse.ProtectionLevelEnum] = None
	)
	
	object GenerateRandomBytesRequest {
		enum ProtectionLevelEnum extends Enum[ProtectionLevelEnum] { case PROTECTION_LEVEL_UNSPECIFIED, SOFTWARE, HSM, EXTERNAL, EXTERNAL_VPC }
	}
	case class GenerateRandomBytesRequest(
	  /** The length in bytes of the amount of randomness to retrieve. Minimum 8 bytes, maximum 1024 bytes. */
		lengthBytes: Option[Int] = None,
	  /** The ProtectionLevel to use when generating the random data. Currently, only HSM protection level is supported. */
		protectionLevel: Option[Schema.GenerateRandomBytesRequest.ProtectionLevelEnum] = None
	)
	
	case class GenerateRandomBytesResponse(
	  /** The generated data. */
		data: Option[String] = None,
	  /** Integrity verification field. A CRC32C checksum of the returned GenerateRandomBytesResponse.data. An integrity check of GenerateRandomBytesResponse.data can be performed by computing the CRC32C checksum of GenerateRandomBytesResponse.data and comparing your results to this field. Discard the response in case of non-matching checksum values, and perform a limited number of retries. A persistent mismatch may indicate an issue in your computation of the CRC32C checksum. Note: This field is defined as int64 for reasons of compatibility across different languages. However, it is a non-negative integer, which will never exceed 2^32-1, and can be safely downconverted to uint32 in languages that support this type. */
		dataCrc32c: Option[String] = None
	)
	
	case class ListLocationsResponse(
	  /** A list of locations that matches the specified filter in the request. */
		locations: Option[List[Schema.Location]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class Location(
	  /** Resource name for the location, which may vary between implementations. For example: `"projects/example-project/locations/us-east1"` */
		name: Option[String] = None,
	  /** The canonical id for this location. For example: `"us-east1"`. */
		locationId: Option[String] = None,
	  /** The friendly name for this location, typically a nearby city name. For example, "Tokyo". */
		displayName: Option[String] = None,
	  /** Cross-service attributes for the location. For example {"cloud.googleapis.com/region": "us-east1"} */
		labels: Option[Map[String, String]] = None,
	  /** Service-specific metadata. For example the available capacity at the given location. */
		metadata: Option[Map[String, JsValue]] = None
	)
	
	case class SetIamPolicyRequest(
	  /** REQUIRED: The complete policy to be applied to the `resource`. The size of the policy is limited to a few 10s of KB. An empty policy is a valid policy but certain Google Cloud services (such as Projects) might reject them. */
		policy: Option[Schema.Policy] = None,
	  /** OPTIONAL: A FieldMask specifying which fields of the policy to modify. Only the fields in the mask will be modified. If no mask is provided, the following default mask is used: `paths: "bindings, etag"` */
		updateMask: Option[String] = None
	)
	
	case class Policy(
	  /** Specifies the format of the policy. Valid values are `0`, `1`, and `3`. Requests that specify an invalid value are rejected. Any operation that affects conditional role bindings must specify version `3`. This requirement applies to the following operations: &#42; Getting a policy that includes a conditional role binding &#42; Adding a conditional role binding to a policy &#42; Changing a conditional role binding in a policy &#42; Removing any role binding, with or without a condition, from a policy that includes conditions &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. If a policy does not include any conditions, operations on that policy may specify any valid version or leave the field unset. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		version: Option[Int] = None,
	  /** Associates a list of `members`, or principals, with a `role`. Optionally, may specify a `condition` that determines how and when the `bindings` are applied. Each of the `bindings` must contain at least one principal. The `bindings` in a `Policy` can refer to up to 1,500 principals; up to 250 of these principals can be Google groups. Each occurrence of a principal counts towards these limits. For example, if the `bindings` grant 50 different roles to `user:alice@example.com`, and not to any other principal, then you can add another 1,450 principals to the `bindings` in the `Policy`. */
		bindings: Option[List[Schema.Binding]] = None,
	  /** Specifies cloud audit logging configuration for this policy. */
		auditConfigs: Option[List[Schema.AuditConfig]] = None,
	  /** `etag` is used for optimistic concurrency control as a way to help prevent simultaneous updates of a policy from overwriting each other. It is strongly suggested that systems make use of the `etag` in the read-modify-write cycle to perform policy updates in order to avoid race conditions: An `etag` is returned in the response to `getIamPolicy`, and systems are expected to put that etag in the request to `setIamPolicy` to ensure that their change will be applied to the same version of the policy. &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. */
		etag: Option[String] = None
	)
	
	case class Binding(
	  /** Role that is assigned to the list of `members`, or principals. For example, `roles/viewer`, `roles/editor`, or `roles/owner`. For an overview of the IAM roles and permissions, see the [IAM documentation](https://cloud.google.com/iam/docs/roles-overview). For a list of the available pre-defined roles, see [here](https://cloud.google.com/iam/docs/understanding-roles). */
		role: Option[String] = None,
	  /** Specifies the principals requesting access for a Google Cloud resource. `members` can have the following values: &#42; `allUsers`: A special identifier that represents anyone who is on the internet; with or without a Google account. &#42; `allAuthenticatedUsers`: A special identifier that represents anyone who is authenticated with a Google account or a service account. Does not include identities that come from external identity providers (IdPs) through identity federation. &#42; `user:{emailid}`: An email address that represents a specific Google account. For example, `alice@example.com` . &#42; `serviceAccount:{emailid}`: An email address that represents a Google service account. For example, `my-other-app@appspot.gserviceaccount.com`. &#42; `serviceAccount:{projectid}.svc.id.goog[{namespace}/{kubernetes-sa}]`: An identifier for a [Kubernetes service account](https://cloud.google.com/kubernetes-engine/docs/how-to/kubernetes-service-accounts). For example, `my-project.svc.id.goog[my-namespace/my-kubernetes-sa]`. &#42; `group:{emailid}`: An email address that represents a Google group. For example, `admins@example.com`. &#42; `domain:{domain}`: The G Suite domain (primary) that represents all the users of that domain. For example, `google.com` or `example.com`. &#42; `principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workforce identity pool. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/group/{group_id}`: All workforce identities in a group. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All workforce identities with a specific attribute value. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/&#42;`: All identities in a workforce identity pool. &#42; `principal://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workload identity pool. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/group/{group_id}`: A workload identity pool group. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All identities in a workload identity pool with a certain attribute. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/&#42;`: All identities in a workload identity pool. &#42; `deleted:user:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a user that has been recently deleted. For example, `alice@example.com?uid=123456789012345678901`. If the user is recovered, this value reverts to `user:{emailid}` and the recovered user retains the role in the binding. &#42; `deleted:serviceAccount:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a service account that has been recently deleted. For example, `my-other-app@appspot.gserviceaccount.com?uid=123456789012345678901`. If the service account is undeleted, this value reverts to `serviceAccount:{emailid}` and the undeleted service account retains the role in the binding. &#42; `deleted:group:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a Google group that has been recently deleted. For example, `admins@example.com?uid=123456789012345678901`. If the group is recovered, this value reverts to `group:{emailid}` and the recovered group retains the role in the binding. &#42; `deleted:principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: Deleted single identity in a workforce identity pool. For example, `deleted:principal://iam.googleapis.com/locations/global/workforcePools/my-pool-id/subject/my-subject-attribute-value`. */
		members: Option[List[String]] = None,
	  /** The condition that is associated with this binding. If the condition evaluates to `true`, then this binding applies to the current request. If the condition evaluates to `false`, then this binding does not apply to the current request. However, a different role binding might grant the same role to one or more of the principals in this binding. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		condition: Option[Schema.Expr] = None
	)
	
	case class Expr(
	  /** Textual representation of an expression in Common Expression Language syntax. */
		expression: Option[String] = None,
	  /** Optional. Title for the expression, i.e. a short string describing its purpose. This can be used e.g. in UIs which allow to enter the expression. */
		title: Option[String] = None,
	  /** Optional. Description of the expression. This is a longer text which describes the expression, e.g. when hovered over it in a UI. */
		description: Option[String] = None,
	  /** Optional. String indicating the location of the expression for error reporting, e.g. a file name and a position in the file. */
		location: Option[String] = None
	)
	
	case class AuditConfig(
	  /** Specifies a service that will be enabled for audit logging. For example, `storage.googleapis.com`, `cloudsql.googleapis.com`. `allServices` is a special value that covers all services. */
		service: Option[String] = None,
	  /** The configuration for logging of each type of permission. */
		auditLogConfigs: Option[List[Schema.AuditLogConfig]] = None
	)
	
	object AuditLogConfig {
		enum LogTypeEnum extends Enum[LogTypeEnum] { case LOG_TYPE_UNSPECIFIED, ADMIN_READ, DATA_WRITE, DATA_READ }
	}
	case class AuditLogConfig(
	  /** The log type that this config enables. */
		logType: Option[Schema.AuditLogConfig.LogTypeEnum] = None,
	  /** Specifies the identities that do not cause logging for this type of permission. Follows the same format of Binding.members. */
		exemptedMembers: Option[List[String]] = None
	)
	
	case class TestIamPermissionsRequest(
	  /** The set of permissions to check for the `resource`. Permissions with wildcards (such as `&#42;` or `storage.&#42;`) are not allowed. For more information see [IAM Overview](https://cloud.google.com/iam/docs/overview#permissions). */
		permissions: Option[List[String]] = None
	)
	
	case class TestIamPermissionsResponse(
	  /** A subset of `TestPermissionsRequest.permissions` that the caller is allowed. */
		permissions: Option[List[String]] = None
	)
	
	case class LocationMetadata(
	  /** Indicates whether CryptoKeys with protection_level HSM can be created in this location. */
		hsmAvailable: Option[Boolean] = None,
	  /** Indicates whether CryptoKeys with protection_level EXTERNAL can be created in this location. */
		ekmAvailable: Option[Boolean] = None
	)
}
