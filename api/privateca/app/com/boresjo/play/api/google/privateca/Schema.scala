package com.boresjo.play.api.google.privateca

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
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
	
	case class ListOperationsResponse(
	  /** A list of operations that matches the specified filter in the request. */
		operations: Option[List[Schema.Operation]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
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
	
	case class Empty(
	
	)
	
	case class CancelOperationRequest(
	
	)
	
	object Certificate {
		enum SubjectModeEnum extends Enum[SubjectModeEnum] { case SUBJECT_REQUEST_MODE_UNSPECIFIED, DEFAULT, REFLECTED_SPIFFE }
	}
	case class Certificate(
	  /** Output only. The resource name for this Certificate in the format `projects/&#42;/locations/&#42;/caPools/&#42;/certificates/&#42;`. */
		name: Option[String] = None,
	  /** Immutable. A pem-encoded X.509 certificate signing request (CSR). */
		pemCsr: Option[String] = None,
	  /** Immutable. A description of the certificate and key that does not require X.509 or ASN.1. */
		config: Option[Schema.CertificateConfig] = None,
	  /** Output only. The resource name of the issuing CertificateAuthority in the format `projects/&#42;/locations/&#42;/caPools/&#42;/certificateAuthorities/&#42;`. */
		issuerCertificateAuthority: Option[String] = None,
	  /** Required. Immutable. The desired lifetime of a certificate. Used to create the "not_before_time" and "not_after_time" fields inside an X.509 certificate. Note that the lifetime may be truncated if it would extend past the life of any certificate authority in the issuing chain. */
		lifetime: Option[String] = None,
	  /** Immutable. The resource name for a CertificateTemplate used to issue this certificate, in the format `projects/&#42;/locations/&#42;/certificateTemplates/&#42;`. If this is specified, the caller must have the necessary permission to use this template. If this is omitted, no template will be used. This template must be in the same location as the Certificate. */
		certificateTemplate: Option[String] = None,
	  /** Immutable. Specifies how the Certificate's identity fields are to be decided. If this is omitted, the `DEFAULT` subject mode will be used. */
		subjectMode: Option[Schema.Certificate.SubjectModeEnum] = None,
	  /** Output only. Details regarding the revocation of this Certificate. This Certificate is considered revoked if and only if this field is present. */
		revocationDetails: Option[Schema.RevocationDetails] = None,
	  /** Output only. The pem-encoded, signed X.509 certificate. */
		pemCertificate: Option[String] = None,
	  /** Output only. A structured description of the issued X.509 certificate. */
		certificateDescription: Option[Schema.CertificateDescription] = None,
	  /** Output only. The chain that may be used to verify the X.509 certificate. Expected to be in issuer-to-root order according to RFC 5246. */
		pemCertificateChain: Option[List[String]] = None,
	  /** Output only. The time at which this Certificate was created. */
		createTime: Option[String] = None,
	  /** Output only. The time at which this Certificate was updated. */
		updateTime: Option[String] = None,
	  /** Optional. Labels with user-defined metadata. */
		labels: Option[Map[String, String]] = None
	)
	
	case class CertificateConfig(
	  /** Required. Specifies some of the values in a certificate that are related to the subject. */
		subjectConfig: Option[Schema.SubjectConfig] = None,
	  /** Required. Describes how some of the technical X.509 fields in a certificate should be populated. */
		x509Config: Option[Schema.X509Parameters] = None,
	  /** Optional. The public key that corresponds to this config. This is, for example, used when issuing Certificates, but not when creating a self-signed CertificateAuthority or CertificateAuthority CSR. */
		publicKey: Option[Schema.PublicKey] = None,
	  /** Optional. When specified this provides a custom SKI to be used in the certificate. This should only be used to maintain a SKI of an existing CA originally created outside CA service, which was not generated using method (1) described in RFC 5280 section 4.2.1.2. */
		subjectKeyId: Option[Schema.CertificateConfigKeyId] = None
	)
	
	case class SubjectConfig(
	  /** Optional. Contains distinguished name fields such as the common name, location and organization. */
		subject: Option[Schema.Subject] = None,
	  /** Optional. The subject alternative name fields. */
		subjectAltName: Option[Schema.SubjectAltNames] = None
	)
	
	case class Subject(
	  /** The "common name" of the subject. */
		commonName: Option[String] = None,
	  /** The country code of the subject. */
		countryCode: Option[String] = None,
	  /** The organization of the subject. */
		organization: Option[String] = None,
	  /** The organizational_unit of the subject. */
		organizationalUnit: Option[String] = None,
	  /** The locality or city of the subject. */
		locality: Option[String] = None,
	  /** The province, territory, or regional state of the subject. */
		province: Option[String] = None,
	  /** The street address of the subject. */
		streetAddress: Option[String] = None,
	  /** The postal code of the subject. */
		postalCode: Option[String] = None
	)
	
	case class SubjectAltNames(
	  /** Contains only valid, fully-qualified host names. */
		dnsNames: Option[List[String]] = None,
	  /** Contains only valid RFC 3986 URIs. */
		uris: Option[List[String]] = None,
	  /** Contains only valid RFC 2822 E-mail addresses. */
		emailAddresses: Option[List[String]] = None,
	  /** Contains only valid 32-bit IPv4 addresses or RFC 4291 IPv6 addresses. */
		ipAddresses: Option[List[String]] = None,
	  /** Contains additional subject alternative name values. For each custom_san, the `value` field must contain an ASN.1 encoded UTF8String. */
		customSans: Option[List[Schema.X509Extension]] = None
	)
	
	case class X509Extension(
	  /** Required. The OID for this X.509 extension. */
		objectId: Option[Schema.ObjectId] = None,
	  /** Optional. Indicates whether or not this extension is critical (i.e., if the client does not know how to handle this extension, the client should consider this to be an error). */
		critical: Option[Boolean] = None,
	  /** Required. The value of this X.509 extension. */
		value: Option[String] = None
	)
	
	case class ObjectId(
	  /** Required. The parts of an OID path. The most significant parts of the path come first. */
		objectIdPath: Option[List[Int]] = None
	)
	
	case class X509Parameters(
	  /** Optional. Indicates the intended use for keys that correspond to a certificate. */
		keyUsage: Option[Schema.KeyUsage] = None,
	  /** Optional. Describes options in this X509Parameters that are relevant in a CA certificate. If not specified, a default basic constraints extension with `is_ca=false` will be added for leaf certificates. */
		caOptions: Option[Schema.CaOptions] = None,
	  /** Optional. Describes the X.509 certificate policy object identifiers, per https://tools.ietf.org/html/rfc5280#section-4.2.1.4. */
		policyIds: Option[List[Schema.ObjectId]] = None,
	  /** Optional. Describes Online Certificate Status Protocol (OCSP) endpoint addresses that appear in the "Authority Information Access" extension in the certificate. */
		aiaOcspServers: Option[List[String]] = None,
	  /** Optional. Describes the X.509 name constraints extension. */
		nameConstraints: Option[Schema.NameConstraints] = None,
	  /** Optional. Describes custom X.509 extensions. */
		additionalExtensions: Option[List[Schema.X509Extension]] = None
	)
	
	case class KeyUsage(
	  /** Describes high-level ways in which a key may be used. */
		baseKeyUsage: Option[Schema.KeyUsageOptions] = None,
	  /** Detailed scenarios in which a key may be used. */
		extendedKeyUsage: Option[Schema.ExtendedKeyUsageOptions] = None,
	  /** Used to describe extended key usages that are not listed in the KeyUsage.ExtendedKeyUsageOptions message. */
		unknownExtendedKeyUsages: Option[List[Schema.ObjectId]] = None
	)
	
	case class KeyUsageOptions(
	  /** The key may be used for digital signatures. */
		digitalSignature: Option[Boolean] = None,
	  /** The key may be used for cryptographic commitments. Note that this may also be referred to as "non-repudiation". */
		contentCommitment: Option[Boolean] = None,
	  /** The key may be used to encipher other keys. */
		keyEncipherment: Option[Boolean] = None,
	  /** The key may be used to encipher data. */
		dataEncipherment: Option[Boolean] = None,
	  /** The key may be used in a key agreement protocol. */
		keyAgreement: Option[Boolean] = None,
	  /** The key may be used to sign certificates. */
		certSign: Option[Boolean] = None,
	  /** The key may be used sign certificate revocation lists. */
		crlSign: Option[Boolean] = None,
	  /** The key may be used to encipher only. */
		encipherOnly: Option[Boolean] = None,
	  /** The key may be used to decipher only. */
		decipherOnly: Option[Boolean] = None
	)
	
	case class ExtendedKeyUsageOptions(
	  /** Corresponds to OID 1.3.6.1.5.5.7.3.1. Officially described as "TLS WWW server authentication", though regularly used for non-WWW TLS. */
		serverAuth: Option[Boolean] = None,
	  /** Corresponds to OID 1.3.6.1.5.5.7.3.2. Officially described as "TLS WWW client authentication", though regularly used for non-WWW TLS. */
		clientAuth: Option[Boolean] = None,
	  /** Corresponds to OID 1.3.6.1.5.5.7.3.3. Officially described as "Signing of downloadable executable code client authentication". */
		codeSigning: Option[Boolean] = None,
	  /** Corresponds to OID 1.3.6.1.5.5.7.3.4. Officially described as "Email protection". */
		emailProtection: Option[Boolean] = None,
	  /** Corresponds to OID 1.3.6.1.5.5.7.3.8. Officially described as "Binding the hash of an object to a time". */
		timeStamping: Option[Boolean] = None,
	  /** Corresponds to OID 1.3.6.1.5.5.7.3.9. Officially described as "Signing OCSP responses". */
		ocspSigning: Option[Boolean] = None
	)
	
	case class CaOptions(
	  /** Optional. Refers to the "CA" boolean field in the X.509 extension. When this value is missing, the basic constraints extension will be omitted from the certificate. */
		isCa: Option[Boolean] = None,
	  /** Optional. Refers to the path length constraint field in the X.509 extension. For a CA certificate, this value describes the depth of subordinate CA certificates that are allowed. If this value is less than 0, the request will fail. If this value is missing, the max path length will be omitted from the certificate. */
		maxIssuerPathLength: Option[Int] = None
	)
	
	case class NameConstraints(
	  /** Indicates whether or not the name constraints are marked critical. */
		critical: Option[Boolean] = None,
	  /** Contains permitted DNS names. Any DNS name that can be constructed by simply adding zero or more labels to the left-hand side of the name satisfies the name constraint. For example, `example.com`, `www.example.com`, `www.sub.example.com` would satisfy `example.com` while `example1.com` does not. */
		permittedDnsNames: Option[List[String]] = None,
	  /** Contains excluded DNS names. Any DNS name that can be constructed by simply adding zero or more labels to the left-hand side of the name satisfies the name constraint. For example, `example.com`, `www.example.com`, `www.sub.example.com` would satisfy `example.com` while `example1.com` does not. */
		excludedDnsNames: Option[List[String]] = None,
	  /** Contains the permitted IP ranges. For IPv4 addresses, the ranges are expressed using CIDR notation as specified in RFC 4632. For IPv6 addresses, the ranges are expressed in similar encoding as IPv4 addresses. */
		permittedIpRanges: Option[List[String]] = None,
	  /** Contains the excluded IP ranges. For IPv4 addresses, the ranges are expressed using CIDR notation as specified in RFC 4632. For IPv6 addresses, the ranges are expressed in similar encoding as IPv4 addresses. */
		excludedIpRanges: Option[List[String]] = None,
	  /** Contains the permitted email addresses. The value can be a particular email address, a hostname to indicate all email addresses on that host or a domain with a leading period (e.g. `.example.com`) to indicate all email addresses in that domain. */
		permittedEmailAddresses: Option[List[String]] = None,
	  /** Contains the excluded email addresses. The value can be a particular email address, a hostname to indicate all email addresses on that host or a domain with a leading period (e.g. `.example.com`) to indicate all email addresses in that domain. */
		excludedEmailAddresses: Option[List[String]] = None,
	  /** Contains the permitted URIs that apply to the host part of the name. The value can be a hostname or a domain with a leading period (like `.example.com`) */
		permittedUris: Option[List[String]] = None,
	  /** Contains the excluded URIs that apply to the host part of the name. The value can be a hostname or a domain with a leading period (like `.example.com`) */
		excludedUris: Option[List[String]] = None
	)
	
	object PublicKey {
		enum FormatEnum extends Enum[FormatEnum] { case KEY_FORMAT_UNSPECIFIED, PEM }
	}
	case class PublicKey(
	  /** Required. A public key. The padding and encoding must match with the `KeyFormat` value specified for the `format` field. */
		key: Option[String] = None,
	  /** Required. The format of the public key. */
		format: Option[Schema.PublicKey.FormatEnum] = None
	)
	
	case class CertificateConfigKeyId(
	  /** Required. The value of this KeyId encoded in lowercase hexadecimal. This is most likely the 160 bit SHA-1 hash of the public key. */
		keyId: Option[String] = None
	)
	
	object RevocationDetails {
		enum RevocationStateEnum extends Enum[RevocationStateEnum] { case REVOCATION_REASON_UNSPECIFIED, KEY_COMPROMISE, CERTIFICATE_AUTHORITY_COMPROMISE, AFFILIATION_CHANGED, SUPERSEDED, CESSATION_OF_OPERATION, CERTIFICATE_HOLD, PRIVILEGE_WITHDRAWN, ATTRIBUTE_AUTHORITY_COMPROMISE }
	}
	case class RevocationDetails(
	  /** Indicates why a Certificate was revoked. */
		revocationState: Option[Schema.RevocationDetails.RevocationStateEnum] = None,
	  /** The time at which this Certificate was revoked. */
		revocationTime: Option[String] = None
	)
	
	case class CertificateDescription(
	  /** Describes some of the values in a certificate that are related to the subject and lifetime. */
		subjectDescription: Option[Schema.SubjectDescription] = None,
	  /** Describes some of the technical X.509 fields in a certificate. */
		x509Description: Option[Schema.X509Parameters] = None,
	  /** The public key that corresponds to an issued certificate. */
		publicKey: Option[Schema.PublicKey] = None,
	  /** Provides a means of identifiying certificates that contain a particular public key, per https://tools.ietf.org/html/rfc5280#section-4.2.1.2. */
		subjectKeyId: Option[Schema.KeyId] = None,
	  /** Identifies the subject_key_id of the parent certificate, per https://tools.ietf.org/html/rfc5280#section-4.2.1.1 */
		authorityKeyId: Option[Schema.KeyId] = None,
	  /** Describes a list of locations to obtain CRL information, i.e. the DistributionPoint.fullName described by https://tools.ietf.org/html/rfc5280#section-4.2.1.13 */
		crlDistributionPoints: Option[List[String]] = None,
	  /** Describes lists of issuer CA certificate URLs that appear in the "Authority Information Access" extension in the certificate. */
		aiaIssuingCertificateUrls: Option[List[String]] = None,
	  /** The hash of the x.509 certificate. */
		certFingerprint: Option[Schema.CertificateFingerprint] = None,
	  /** The hash of the pre-signed certificate, which will be signed by the CA. Corresponds to the TBS Certificate in https://tools.ietf.org/html/rfc5280#section-4.1.2. The field will always be populated. */
		tbsCertificateDigest: Option[String] = None
	)
	
	case class SubjectDescription(
	  /** Contains distinguished name fields such as the common name, location and / organization. */
		subject: Option[Schema.Subject] = None,
	  /** The subject alternative name fields. */
		subjectAltName: Option[Schema.SubjectAltNames] = None,
	  /** The serial number encoded in lowercase hexadecimal. */
		hexSerialNumber: Option[String] = None,
	  /** For convenience, the actual lifetime of an issued certificate. */
		lifetime: Option[String] = None,
	  /** The time at which the certificate becomes valid. */
		notBeforeTime: Option[String] = None,
	  /** The time after which the certificate is expired. Per RFC 5280, the validity period for a certificate is the period of time from not_before_time through not_after_time, inclusive. Corresponds to 'not_before_time' + 'lifetime' - 1 second. */
		notAfterTime: Option[String] = None
	)
	
	case class KeyId(
	  /** Optional. The value of this KeyId encoded in lowercase hexadecimal. This is most likely the 160 bit SHA-1 hash of the public key. */
		keyId: Option[String] = None
	)
	
	case class CertificateFingerprint(
	  /** The SHA 256 hash, encoded in hexadecimal, of the DER x509 certificate. */
		sha256Hash: Option[String] = None
	)
	
	case class ListCertificatesResponse(
	  /** The list of Certificates. */
		certificates: Option[List[Schema.Certificate]] = None,
	  /** A token to retrieve next page of results. Pass this value in ListCertificatesRequest.page_token to retrieve the next page of results. */
		nextPageToken: Option[String] = None,
	  /** A list of locations (e.g. "us-west1") that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object RevokeCertificateRequest {
		enum ReasonEnum extends Enum[ReasonEnum] { case REVOCATION_REASON_UNSPECIFIED, KEY_COMPROMISE, CERTIFICATE_AUTHORITY_COMPROMISE, AFFILIATION_CHANGED, SUPERSEDED, CESSATION_OF_OPERATION, CERTIFICATE_HOLD, PRIVILEGE_WITHDRAWN, ATTRIBUTE_AUTHORITY_COMPROMISE }
	}
	case class RevokeCertificateRequest(
	  /** Required. The RevocationReason for revoking this certificate. */
		reason: Option[Schema.RevokeCertificateRequest.ReasonEnum] = None,
	  /** Optional. An ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None
	)
	
	case class ActivateCertificateAuthorityRequest(
	  /** Required. The signed CA certificate issued from FetchCertificateAuthorityCsrResponse.pem_csr. */
		pemCaCertificate: Option[String] = None,
	  /** Required. Must include information about the issuer of 'pem_ca_certificate', and any further issuers until the self-signed CA. */
		subordinateConfig: Option[Schema.SubordinateConfig] = None,
	  /** Optional. An ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None
	)
	
	case class SubordinateConfig(
	  /** Required. This can refer to a CertificateAuthority that was used to create a subordinate CertificateAuthority. This field is used for information and usability purposes only. The resource name is in the format `projects/&#42;/locations/&#42;/caPools/&#42;/certificateAuthorities/&#42;`. */
		certificateAuthority: Option[String] = None,
	  /** Required. Contains the PEM certificate chain for the issuers of this CertificateAuthority, but not pem certificate for this CA itself. */
		pemIssuerChain: Option[Schema.SubordinateConfigChain] = None
	)
	
	case class SubordinateConfigChain(
	  /** Required. Expected to be in leaf-to-root order according to RFC 5246. */
		pemCertificates: Option[List[String]] = None
	)
	
	object CertificateAuthority {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, SELF_SIGNED, SUBORDINATE }
		enum TierEnum extends Enum[TierEnum] { case TIER_UNSPECIFIED, ENTERPRISE, DEVOPS }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ENABLED, DISABLED, STAGED, AWAITING_USER_ACTIVATION, DELETED }
	}
	case class CertificateAuthority(
	  /** Output only. Identifier. The resource name for this CertificateAuthority in the format `projects/&#42;/locations/&#42;/caPools/&#42;/certificateAuthorities/&#42;`. */
		name: Option[String] = None,
	  /** Required. Immutable. The Type of this CertificateAuthority. */
		`type`: Option[Schema.CertificateAuthority.TypeEnum] = None,
	  /** Required. Immutable. The config used to create a self-signed X.509 certificate or CSR. */
		config: Option[Schema.CertificateConfig] = None,
	  /** Required. Immutable. The desired lifetime of the CA certificate. Used to create the "not_before_time" and "not_after_time" fields inside an X.509 certificate. */
		lifetime: Option[String] = None,
	  /** Required. Immutable. Used when issuing certificates for this CertificateAuthority. If this CertificateAuthority is a self-signed CertificateAuthority, this key is also used to sign the self-signed CA certificate. Otherwise, it is used to sign a CSR. */
		keySpec: Option[Schema.KeyVersionSpec] = None,
	  /** Optional. If this is a subordinate CertificateAuthority, this field will be set with the subordinate configuration, which describes its issuers. This may be updated, but this CertificateAuthority must continue to validate. */
		subordinateConfig: Option[Schema.SubordinateConfig] = None,
	  /** Output only. The CaPool.Tier of the CaPool that includes this CertificateAuthority. */
		tier: Option[Schema.CertificateAuthority.TierEnum] = None,
	  /** Output only. The State for this CertificateAuthority. */
		state: Option[Schema.CertificateAuthority.StateEnum] = None,
	  /** Output only. This CertificateAuthority's certificate chain, including the current CertificateAuthority's certificate. Ordered such that the root issuer is the final element (consistent with RFC 5246). For a self-signed CA, this will only list the current CertificateAuthority's certificate. */
		pemCaCertificates: Option[List[String]] = None,
	  /** Output only. A structured description of this CertificateAuthority's CA certificate and its issuers. Ordered as self-to-root. */
		caCertificateDescriptions: Option[List[Schema.CertificateDescription]] = None,
	  /** Immutable. The name of a Cloud Storage bucket where this CertificateAuthority will publish content, such as the CA certificate and CRLs. This must be a bucket name, without any prefixes (such as `gs://`) or suffixes (such as `.googleapis.com`). For example, to use a bucket named `my-bucket`, you would simply specify `my-bucket`. If not specified, a managed bucket will be created. */
		gcsBucket: Option[String] = None,
	  /** Output only. URLs for accessing content published by this CA, such as the CA certificate and CRLs. */
		accessUrls: Option[Schema.AccessUrls] = None,
	  /** Output only. The time at which this CertificateAuthority was created. */
		createTime: Option[String] = None,
	  /** Output only. The time at which this CertificateAuthority was last updated. */
		updateTime: Option[String] = None,
	  /** Output only. The time at which this CertificateAuthority was soft deleted, if it is in the DELETED state. */
		deleteTime: Option[String] = None,
	  /** Output only. The time at which this CertificateAuthority will be permanently purged, if it is in the DELETED state. */
		expireTime: Option[String] = None,
	  /** Optional. Labels with user-defined metadata. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. Reserved for future use. */
		satisfiesPzs: Option[Boolean] = None,
	  /** Output only. Reserved for future use. */
		satisfiesPzi: Option[Boolean] = None
	)
	
	object KeyVersionSpec {
		enum AlgorithmEnum extends Enum[AlgorithmEnum] { case SIGN_HASH_ALGORITHM_UNSPECIFIED, RSA_PSS_2048_SHA256, RSA_PSS_3072_SHA256, RSA_PSS_4096_SHA256, RSA_PKCS1_2048_SHA256, RSA_PKCS1_3072_SHA256, RSA_PKCS1_4096_SHA256, EC_P256_SHA256, EC_P384_SHA384 }
	}
	case class KeyVersionSpec(
	  /** The resource name for an existing Cloud KMS CryptoKeyVersion in the format `projects/&#42;/locations/&#42;/keyRings/&#42;/cryptoKeys/&#42;/cryptoKeyVersions/&#42;`. This option enables full flexibility in the key's capabilities and properties. */
		cloudKmsKeyVersion: Option[String] = None,
	  /** The algorithm to use for creating a managed Cloud KMS key for a for a simplified experience. All managed keys will be have their ProtectionLevel as `HSM`. */
		algorithm: Option[Schema.KeyVersionSpec.AlgorithmEnum] = None
	)
	
	case class AccessUrls(
	  /** The URL where this CertificateAuthority's CA certificate is published. This will only be set for CAs that have been activated. */
		caCertificateAccessUrl: Option[String] = None,
	  /** The URLs where this CertificateAuthority's CRLs are published. This will only be set for CAs that have been activated. */
		crlAccessUrls: Option[List[String]] = None
	)
	
	case class DisableCertificateAuthorityRequest(
	  /** Optional. An ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None,
	  /** Optional. This field allows this CA to be disabled even if it's being depended on by another resource. However, doing so may result in unintended and unrecoverable effects on any dependent resources since the CA will no longer be able to issue certificates. */
		ignoreDependentResources: Option[Boolean] = None
	)
	
	case class EnableCertificateAuthorityRequest(
	  /** Optional. An ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None
	)
	
	case class FetchCertificateAuthorityCsrResponse(
	  /** Output only. The PEM-encoded signed certificate signing request (CSR). */
		pemCsr: Option[String] = None
	)
	
	case class ListCertificateAuthoritiesResponse(
	  /** The list of CertificateAuthorities. */
		certificateAuthorities: Option[List[Schema.CertificateAuthority]] = None,
	  /** A token to retrieve next page of results. Pass this value in ListCertificateAuthoritiesRequest.page_token to retrieve the next page of results. */
		nextPageToken: Option[String] = None,
	  /** A list of locations (e.g. "us-west1") that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class UndeleteCertificateAuthorityRequest(
	  /** Optional. An ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None
	)
	
	object CaPool {
		enum TierEnum extends Enum[TierEnum] { case TIER_UNSPECIFIED, ENTERPRISE, DEVOPS }
	}
	case class CaPool(
	  /** Output only. Identifier. The resource name for this CaPool in the format `projects/&#42;/locations/&#42;/caPools/&#42;`. */
		name: Option[String] = None,
	  /** Required. Immutable. The Tier of this CaPool. */
		tier: Option[Schema.CaPool.TierEnum] = None,
	  /** Optional. The IssuancePolicy to control how Certificates will be issued from this CaPool. */
		issuancePolicy: Option[Schema.IssuancePolicy] = None,
	  /** Optional. The PublishingOptions to follow when issuing Certificates from any CertificateAuthority in this CaPool. */
		publishingOptions: Option[Schema.PublishingOptions] = None,
	  /** Optional. Labels with user-defined metadata. */
		labels: Option[Map[String, String]] = None
	)
	
	case class IssuancePolicy(
	  /** Optional. If any AllowedKeyType is specified, then the certificate request's public key must match one of the key types listed here. Otherwise, any key may be used. */
		allowedKeyTypes: Option[List[Schema.AllowedKeyType]] = None,
	  /** Optional. The maximum lifetime allowed for issued Certificates. Note that if the issuing CertificateAuthority expires before a Certificate resource's requested maximum_lifetime, the effective lifetime will be explicitly truncated to match it. */
		maximumLifetime: Option[String] = None,
	  /** Optional. If specified, then only methods allowed in the IssuanceModes may be used to issue Certificates. */
		allowedIssuanceModes: Option[Schema.IssuanceModes] = None,
	  /** Optional. A set of X.509 values that will be applied to all certificates issued through this CaPool. If a certificate request includes conflicting values for the same properties, they will be overwritten by the values defined here. If a certificate request uses a CertificateTemplate that defines conflicting predefined_values for the same properties, the certificate issuance request will fail. */
		baselineValues: Option[Schema.X509Parameters] = None,
	  /** Optional. Describes constraints on identities that may appear in Certificates issued through this CaPool. If this is omitted, then this CaPool will not add restrictions on a certificate's identity. */
		identityConstraints: Option[Schema.CertificateIdentityConstraints] = None,
	  /** Optional. Describes the set of X.509 extensions that may appear in a Certificate issued through this CaPool. If a certificate request sets extensions that don't appear in the passthrough_extensions, those extensions will be dropped. If a certificate request uses a CertificateTemplate with predefined_values that don't appear here, the certificate issuance request will fail. If this is omitted, then this CaPool will not add restrictions on a certificate's X.509 extensions. These constraints do not apply to X.509 extensions set in this CaPool's baseline_values. */
		passthroughExtensions: Option[Schema.CertificateExtensionConstraints] = None
	)
	
	case class AllowedKeyType(
	  /** Represents an allowed RSA key type. */
		rsa: Option[Schema.RsaKeyType] = None,
	  /** Represents an allowed Elliptic Curve key type. */
		ellipticCurve: Option[Schema.EcKeyType] = None
	)
	
	case class RsaKeyType(
	  /** Optional. The minimum allowed RSA modulus size (inclusive), in bits. If this is not set, or if set to zero, the service-level min RSA modulus size will continue to apply. */
		minModulusSize: Option[String] = None,
	  /** Optional. The maximum allowed RSA modulus size (inclusive), in bits. If this is not set, or if set to zero, the service will not enforce an explicit upper bound on RSA modulus sizes. */
		maxModulusSize: Option[String] = None
	)
	
	object EcKeyType {
		enum SignatureAlgorithmEnum extends Enum[SignatureAlgorithmEnum] { case EC_SIGNATURE_ALGORITHM_UNSPECIFIED, ECDSA_P256, ECDSA_P384, EDDSA_25519 }
	}
	case class EcKeyType(
	  /** Optional. A signature algorithm that must be used. If this is omitted, any EC-based signature algorithm will be allowed. */
		signatureAlgorithm: Option[Schema.EcKeyType.SignatureAlgorithmEnum] = None
	)
	
	case class IssuanceModes(
	  /** Optional. When true, allows callers to create Certificates by specifying a CSR. */
		allowCsrBasedIssuance: Option[Boolean] = None,
	  /** Optional. When true, allows callers to create Certificates by specifying a CertificateConfig. */
		allowConfigBasedIssuance: Option[Boolean] = None
	)
	
	case class CertificateIdentityConstraints(
	  /** Optional. A CEL expression that may be used to validate the resolved X.509 Subject and/or Subject Alternative Name before a certificate is signed. To see the full allowed syntax and some examples, see https://cloud.google.com/certificate-authority-service/docs/using-cel */
		celExpression: Option[Schema.Expr] = None,
	  /** Required. If this is true, the Subject field may be copied from a certificate request into the signed certificate. Otherwise, the requested Subject will be discarded. */
		allowSubjectPassthrough: Option[Boolean] = None,
	  /** Required. If this is true, the SubjectAltNames extension may be copied from a certificate request into the signed certificate. Otherwise, the requested SubjectAltNames will be discarded. */
		allowSubjectAltNamesPassthrough: Option[Boolean] = None
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
	
	object CertificateExtensionConstraints {
		enum KnownExtensionsEnum extends Enum[KnownExtensionsEnum] { case KNOWN_CERTIFICATE_EXTENSION_UNSPECIFIED, BASE_KEY_USAGE, EXTENDED_KEY_USAGE, CA_OPTIONS, POLICY_IDS, AIA_OCSP_SERVERS, NAME_CONSTRAINTS }
	}
	case class CertificateExtensionConstraints(
	  /** Optional. A set of named X.509 extensions. Will be combined with additional_extensions to determine the full set of X.509 extensions. */
		knownExtensions: Option[List[Schema.CertificateExtensionConstraints.KnownExtensionsEnum]] = None,
	  /** Optional. A set of ObjectIds identifying custom X.509 extensions. Will be combined with known_extensions to determine the full set of X.509 extensions. */
		additionalExtensions: Option[List[Schema.ObjectId]] = None
	)
	
	object PublishingOptions {
		enum EncodingFormatEnum extends Enum[EncodingFormatEnum] { case ENCODING_FORMAT_UNSPECIFIED, PEM, DER }
	}
	case class PublishingOptions(
	  /** Optional. When true, publishes each CertificateAuthority's CA certificate and includes its URL in the "Authority Information Access" X.509 extension in all issued Certificates. If this is false, the CA certificate will not be published and the corresponding X.509 extension will not be written in issued certificates. */
		publishCaCert: Option[Boolean] = None,
	  /** Optional. When true, publishes each CertificateAuthority's CRL and includes its URL in the "CRL Distribution Points" X.509 extension in all issued Certificates. If this is false, CRLs will not be published and the corresponding X.509 extension will not be written in issued certificates. CRLs will expire 7 days from their creation. However, we will rebuild daily. CRLs are also rebuilt shortly after a certificate is revoked. */
		publishCrl: Option[Boolean] = None,
	  /** Optional. Specifies the encoding format of each CertificateAuthority resource's CA certificate and CRLs. If this is omitted, CA certificates and CRLs will be published in PEM. */
		encodingFormat: Option[Schema.PublishingOptions.EncodingFormatEnum] = None
	)
	
	case class ListCaPoolsResponse(
	  /** The list of CaPools. */
		caPools: Option[List[Schema.CaPool]] = None,
	  /** A token to retrieve next page of results. Pass this value in ListCertificateAuthoritiesRequest.page_token to retrieve the next page of results. */
		nextPageToken: Option[String] = None,
	  /** A list of locations (e.g. "us-west1") that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class FetchCaCertsRequest(
	  /** Optional. An ID to identify requests. Specify a unique request ID so that if you must retry your request, the server will know to ignore the request if it has already been completed. The server will guarantee that for at least 60 minutes since the first request. For example, consider a situation where you make an initial request and the request times out. If you make the request again with the same request ID, the server can check if original operation with the same request ID was received, and if so, will ignore the second request. This prevents clients from accidentally creating duplicate commitments. The request ID must be a valid UUID with the exception that zero UUID is not supported (00000000-0000-0000-0000-000000000000). */
		requestId: Option[String] = None
	)
	
	case class FetchCaCertsResponse(
	  /** The PEM encoded CA certificate chains of all certificate authorities in this CaPool in the ENABLED, DISABLED, or STAGED states. */
		caCerts: Option[List[Schema.CertChain]] = None
	)
	
	case class CertChain(
	  /** The certificates that form the CA chain, from leaf to root order. */
		certificates: Option[List[String]] = None
	)
	
	object CertificateRevocationList {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, SUPERSEDED }
	}
	case class CertificateRevocationList(
	  /** Output only. The resource name for this CertificateRevocationList in the format `projects/&#42;/locations/&#42;/caPools/&#42;certificateAuthorities/&#42;/ certificateRevocationLists/&#42;`. */
		name: Option[String] = None,
	  /** Output only. The CRL sequence number that appears in pem_crl. */
		sequenceNumber: Option[String] = None,
	  /** Output only. The revoked serial numbers that appear in pem_crl. */
		revokedCertificates: Option[List[Schema.RevokedCertificate]] = None,
	  /** Output only. The PEM-encoded X.509 CRL. */
		pemCrl: Option[String] = None,
	  /** Output only. The location where 'pem_crl' can be accessed. */
		accessUrl: Option[String] = None,
	  /** Output only. The State for this CertificateRevocationList. */
		state: Option[Schema.CertificateRevocationList.StateEnum] = None,
	  /** Output only. The time at which this CertificateRevocationList was created. */
		createTime: Option[String] = None,
	  /** Output only. The time at which this CertificateRevocationList was updated. */
		updateTime: Option[String] = None,
	  /** Output only. The revision ID of this CertificateRevocationList. A new revision is committed whenever a new CRL is published. The format is an 8-character hexadecimal string. */
		revisionId: Option[String] = None,
	  /** Optional. Labels with user-defined metadata. */
		labels: Option[Map[String, String]] = None
	)
	
	object RevokedCertificate {
		enum RevocationReasonEnum extends Enum[RevocationReasonEnum] { case REVOCATION_REASON_UNSPECIFIED, KEY_COMPROMISE, CERTIFICATE_AUTHORITY_COMPROMISE, AFFILIATION_CHANGED, SUPERSEDED, CESSATION_OF_OPERATION, CERTIFICATE_HOLD, PRIVILEGE_WITHDRAWN, ATTRIBUTE_AUTHORITY_COMPROMISE }
	}
	case class RevokedCertificate(
	  /** The resource name for the Certificate in the format `projects/&#42;/locations/&#42;/caPools/&#42;/certificates/&#42;`. */
		certificate: Option[String] = None,
	  /** The serial number of the Certificate. */
		hexSerialNumber: Option[String] = None,
	  /** The reason the Certificate was revoked. */
		revocationReason: Option[Schema.RevokedCertificate.RevocationReasonEnum] = None
	)
	
	case class ListCertificateRevocationListsResponse(
	  /** The list of CertificateRevocationLists. */
		certificateRevocationLists: Option[List[Schema.CertificateRevocationList]] = None,
	  /** A token to retrieve next page of results. Pass this value in ListCertificateRevocationListsRequest.page_token to retrieve the next page of results. */
		nextPageToken: Option[String] = None,
	  /** A list of locations (e.g. "us-west1") that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class CertificateTemplate(
	  /** Output only. The resource name for this CertificateTemplate in the format `projects/&#42;/locations/&#42;/certificateTemplates/&#42;`. */
		name: Option[String] = None,
	  /** Optional. The maximum lifetime allowed for issued Certificates that use this template. If the issuing CaPool resource's IssuancePolicy specifies a maximum_lifetime the minimum of the two durations will be the maximum lifetime for issued Certificates. Note that if the issuing CertificateAuthority expires before a Certificate's requested maximum_lifetime, the effective lifetime will be explicitly truncated to match it. */
		maximumLifetime: Option[String] = None,
	  /** Optional. A set of X.509 values that will be applied to all issued certificates that use this template. If the certificate request includes conflicting values for the same properties, they will be overwritten by the values defined here. If the issuing CaPool's IssuancePolicy defines conflicting baseline_values for the same properties, the certificate issuance request will fail. */
		predefinedValues: Option[Schema.X509Parameters] = None,
	  /** Optional. Describes constraints on identities that may be appear in Certificates issued using this template. If this is omitted, then this template will not add restrictions on a certificate's identity. */
		identityConstraints: Option[Schema.CertificateIdentityConstraints] = None,
	  /** Optional. Describes the set of X.509 extensions that may appear in a Certificate issued using this CertificateTemplate. If a certificate request sets extensions that don't appear in the passthrough_extensions, those extensions will be dropped. If the issuing CaPool's IssuancePolicy defines baseline_values that don't appear here, the certificate issuance request will fail. If this is omitted, then this template will not add restrictions on a certificate's X.509 extensions. These constraints do not apply to X.509 extensions set in this CertificateTemplate's predefined_values. */
		passthroughExtensions: Option[Schema.CertificateExtensionConstraints] = None,
	  /** Optional. A human-readable description of scenarios this template is intended for. */
		description: Option[String] = None,
	  /** Output only. The time at which this CertificateTemplate was created. */
		createTime: Option[String] = None,
	  /** Output only. The time at which this CertificateTemplate was updated. */
		updateTime: Option[String] = None,
	  /** Optional. Labels with user-defined metadata. */
		labels: Option[Map[String, String]] = None
	)
	
	case class ListCertificateTemplatesResponse(
	  /** The list of CertificateTemplates. */
		certificateTemplates: Option[List[Schema.CertificateTemplate]] = None,
	  /** A token to retrieve next page of results. Pass this value in ListCertificateTemplatesRequest.page_token to retrieve the next page of results. */
		nextPageToken: Option[String] = None,
	  /** A list of locations (e.g. "us-west1") that could not be reached. */
		unreachable: Option[List[String]] = None
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
	
	case class OperationMetadata(
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Output only. Server-defined resource path for the target of the operation. */
		target: Option[String] = None,
	  /** Output only. Name of the verb executed by the operation. */
		verb: Option[String] = None,
	  /** Output only. Human-readable status of the operation, if any. */
		statusMessage: Option[String] = None,
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have successfully been cancelled have google.longrunning.Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		requestedCancellation: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None
	)
	
	object ReconciliationOperationMetadata {
		enum ExclusiveActionEnum extends Enum[ExclusiveActionEnum] { case UNKNOWN_REPAIR_ACTION, DELETE, RETRY }
	}
	case class ReconciliationOperationMetadata(
	  /** DEPRECATED. Use exclusive_action instead. */
		deleteResource: Option[Boolean] = None,
	  /** Excluisive action returned by the CLH. */
		exclusiveAction: Option[Schema.ReconciliationOperationMetadata.ExclusiveActionEnum] = None
	)
}
