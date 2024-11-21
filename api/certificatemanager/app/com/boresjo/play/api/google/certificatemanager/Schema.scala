package com.boresjo.play.api.google.certificatemanager

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
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
	
	case class ListCertificatesResponse(
	  /** A list of certificates for the parent resource. */
		certificates: Option[List[Schema.Certificate]] = None,
	  /** If there might be more results than those appearing in this response, then `next_page_token` is included. To get the next set of results, call this method again using the value of `next_page_token` as `page_token`. */
		nextPageToken: Option[String] = None,
	  /** A list of locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Certificate {
		enum ScopeEnum extends Enum[ScopeEnum] { case DEFAULT, EDGE_CACHE, ALL_REGIONS }
	}
	case class Certificate(
	  /** Identifier. A user-defined name of the certificate. Certificate names must be unique globally and match pattern `projects/&#42;/locations/&#42;/certificates/&#42;`. */
		name: Option[String] = None,
	  /** Optional. One or more paragraphs of text description of a certificate. */
		description: Option[String] = None,
	  /** Output only. The creation timestamp of a Certificate. */
		createTime: Option[String] = None,
	  /** Output only. The last update timestamp of a Certificate. */
		updateTime: Option[String] = None,
	  /** Optional. Set of labels associated with a Certificate. */
		labels: Option[Map[String, String]] = None,
	  /** If set, defines data of a self-managed certificate. */
		selfManaged: Option[Schema.SelfManagedCertificate] = None,
	  /** If set, contains configuration and state of a managed certificate. */
		managed: Option[Schema.ManagedCertificate] = None,
	  /** Output only. The list of Subject Alternative Names of dnsName type defined in the certificate (see RFC 5280 4.2.1.6). Managed certificates that haven't been provisioned yet have this field populated with a value of the managed.domains field. */
		sanDnsnames: Option[List[String]] = None,
	  /** Output only. The PEM-encoded certificate chain. */
		pemCertificate: Option[String] = None,
	  /** Output only. The expiry timestamp of a Certificate. */
		expireTime: Option[String] = None,
	  /** Optional. Immutable. The scope of the certificate. */
		scope: Option[Schema.Certificate.ScopeEnum] = None
	)
	
	case class SelfManagedCertificate(
	  /** Optional. Input only. The PEM-encoded certificate chain. Leaf certificate comes first, followed by intermediate ones if any. */
		pemCertificate: Option[String] = None,
	  /** Optional. Input only. The PEM-encoded private key of the leaf certificate. */
		pemPrivateKey: Option[String] = None
	)
	
	object ManagedCertificate {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PROVISIONING, FAILED, ACTIVE }
	}
	case class ManagedCertificate(
	  /** Optional. Immutable. The domains for which a managed SSL certificate will be generated. Wildcard domains are only supported with DNS challenge resolution. */
		domains: Option[List[String]] = None,
	  /** Optional. Immutable. Authorizations that will be used for performing domain authorization. */
		dnsAuthorizations: Option[List[String]] = None,
	  /** Optional. Immutable. The resource name for a CertificateIssuanceConfig used to configure private PKI certificates in the format `projects/&#42;/locations/&#42;/certificateIssuanceConfigs/&#42;`. If this field is not set, the certificates will instead be publicly signed as documented at https://cloud.google.com/load-balancing/docs/ssl-certificates/google-managed-certs#caa. */
		issuanceConfig: Option[String] = None,
	  /** Output only. State of the managed certificate resource. */
		state: Option[Schema.ManagedCertificate.StateEnum] = None,
	  /** Output only. Information about issues with provisioning a Managed Certificate. */
		provisioningIssue: Option[Schema.ProvisioningIssue] = None,
	  /** Output only. Detailed state of the latest authorization attempt for each domain specified for managed certificate resource. */
		authorizationAttemptInfo: Option[List[Schema.AuthorizationAttemptInfo]] = None
	)
	
	object ProvisioningIssue {
		enum ReasonEnum extends Enum[ReasonEnum] { case REASON_UNSPECIFIED, AUTHORIZATION_ISSUE, RATE_LIMITED }
	}
	case class ProvisioningIssue(
	  /** Output only. Reason for provisioning failures. */
		reason: Option[Schema.ProvisioningIssue.ReasonEnum] = None,
	  /** Output only. Human readable explanation about the issue. Provided to help address the configuration issues. Not guaranteed to be stable. For programmatic access use Reason enum. */
		details: Option[String] = None
	)
	
	object AuthorizationAttemptInfo {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, AUTHORIZING, AUTHORIZED, FAILED }
		enum FailureReasonEnum extends Enum[FailureReasonEnum] { case FAILURE_REASON_UNSPECIFIED, CONFIG, CAA, RATE_LIMITED }
	}
	case class AuthorizationAttemptInfo(
	  /** Output only. Domain name of the authorization attempt. */
		domain: Option[String] = None,
	  /** Output only. State of the domain for managed certificate issuance. */
		state: Option[Schema.AuthorizationAttemptInfo.StateEnum] = None,
	  /** Output only. Reason for failure of the authorization attempt for the domain. */
		failureReason: Option[Schema.AuthorizationAttemptInfo.FailureReasonEnum] = None,
	  /** Output only. Human readable explanation for reaching the state. Provided to help address the configuration issues. Not guaranteed to be stable. For programmatic access use FailureReason enum. */
		details: Option[String] = None
	)
	
	case class ListCertificateMapsResponse(
	  /** A list of certificate maps for the parent resource. */
		certificateMaps: Option[List[Schema.CertificateMap]] = None,
	  /** If there might be more results than those appearing in this response, then `next_page_token` is included. To get the next set of results, call this method again using the value of `next_page_token` as `page_token`. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class CertificateMap(
	  /** Identifier. A user-defined name of the Certificate Map. Certificate Map names must be unique globally and match pattern `projects/&#42;/locations/&#42;/certificateMaps/&#42;`. */
		name: Option[String] = None,
	  /** Optional. One or more paragraphs of text description of a certificate map. */
		description: Option[String] = None,
	  /** Output only. The creation timestamp of a Certificate Map. */
		createTime: Option[String] = None,
	  /** Output only. The update timestamp of a Certificate Map. */
		updateTime: Option[String] = None,
	  /** Optional. Set of labels associated with a Certificate Map. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. A list of GCLB targets that use this Certificate Map. A Target Proxy is only present on this list if it's attached to a Forwarding Rule. */
		gclbTargets: Option[List[Schema.GclbTarget]] = None
	)
	
	case class GclbTarget(
	  /** Output only. This field returns the resource name in the following format: `//compute.googleapis.com/projects/&#42;/global/targetHttpsProxies/&#42;`. */
		targetHttpsProxy: Option[String] = None,
	  /** Output only. This field returns the resource name in the following format: `//compute.googleapis.com/projects/&#42;/global/targetSslProxies/&#42;`. */
		targetSslProxy: Option[String] = None,
	  /** Output only. IP configurations for this Target Proxy where the Certificate Map is serving. */
		ipConfigs: Option[List[Schema.IpConfig]] = None
	)
	
	case class IpConfig(
	  /** Output only. An external IP address. */
		ipAddress: Option[String] = None,
	  /** Output only. Ports. */
		ports: Option[List[Int]] = None
	)
	
	case class ListCertificateMapEntriesResponse(
	  /** A list of certificate map entries for the parent resource. */
		certificateMapEntries: Option[List[Schema.CertificateMapEntry]] = None,
	  /** If there might be more results than those appearing in this response, then `next_page_token` is included. To get the next set of results, call this method again using the value of `next_page_token` as `page_token`. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object CertificateMapEntry {
		enum MatcherEnum extends Enum[MatcherEnum] { case MATCHER_UNSPECIFIED, PRIMARY }
		enum StateEnum extends Enum[StateEnum] { case SERVING_STATE_UNSPECIFIED, ACTIVE, PENDING }
	}
	case class CertificateMapEntry(
	  /** Identifier. A user-defined name of the Certificate Map Entry. Certificate Map Entry names must be unique globally and match pattern `projects/&#42;/locations/&#42;/certificateMaps/&#42;/certificateMapEntries/&#42;`. */
		name: Option[String] = None,
	  /** Optional. One or more paragraphs of text description of a certificate map entry. */
		description: Option[String] = None,
	  /** Output only. The creation timestamp of a Certificate Map Entry. */
		createTime: Option[String] = None,
	  /** Output only. The update timestamp of a Certificate Map Entry. */
		updateTime: Option[String] = None,
	  /** Optional. Set of labels associated with a Certificate Map Entry. */
		labels: Option[Map[String, String]] = None,
	  /** A Hostname (FQDN, e.g. `example.com`) or a wildcard hostname expression (`&#42;.example.com`) for a set of hostnames with common suffix. Used as Server Name Indication (SNI) for selecting a proper certificate. */
		hostname: Option[String] = None,
	  /** A predefined matcher for particular cases, other than SNI selection. */
		matcher: Option[Schema.CertificateMapEntry.MatcherEnum] = None,
	  /** Optional. A set of Certificates defines for the given `hostname`. There can be defined up to four certificates in each Certificate Map Entry. Each certificate must match pattern `projects/&#42;/locations/&#42;/certificates/&#42;`. */
		certificates: Option[List[String]] = None,
	  /** Output only. A serving state of this Certificate Map Entry. */
		state: Option[Schema.CertificateMapEntry.StateEnum] = None
	)
	
	case class ListDnsAuthorizationsResponse(
	  /** A list of dns authorizations for the parent resource. */
		dnsAuthorizations: Option[List[Schema.DnsAuthorization]] = None,
	  /** If there might be more results than those appearing in this response, then `next_page_token` is included. To get the next set of results, call this method again using the value of `next_page_token` as `page_token`. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object DnsAuthorization {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, FIXED_RECORD, PER_PROJECT_RECORD }
	}
	case class DnsAuthorization(
	  /** Identifier. A user-defined name of the dns authorization. DnsAuthorization names must be unique globally and match pattern `projects/&#42;/locations/&#42;/dnsAuthorizations/&#42;`. */
		name: Option[String] = None,
	  /** Output only. The creation timestamp of a DnsAuthorization. */
		createTime: Option[String] = None,
	  /** Output only. The last update timestamp of a DnsAuthorization. */
		updateTime: Option[String] = None,
	  /** Optional. Set of labels associated with a DnsAuthorization. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. One or more paragraphs of text description of a DnsAuthorization. */
		description: Option[String] = None,
	  /** Required. Immutable. A domain that is being authorized. A DnsAuthorization resource covers a single domain and its wildcard, e.g. authorization for `example.com` can be used to issue certificates for `example.com` and `&#42;.example.com`. */
		domain: Option[String] = None,
	  /** Output only. DNS Resource Record that needs to be added to DNS configuration. */
		dnsResourceRecord: Option[Schema.DnsResourceRecord] = None,
	  /** Optional. Immutable. Type of DnsAuthorization. If unset during resource creation the following default will be used: - in location `global`: FIXED_RECORD, - in other locations: PER_PROJECT_RECORD. */
		`type`: Option[Schema.DnsAuthorization.TypeEnum] = None
	)
	
	case class DnsResourceRecord(
	  /** Output only. Fully qualified name of the DNS Resource Record. e.g. `_acme-challenge.example.com` */
		name: Option[String] = None,
	  /** Output only. Type of the DNS Resource Record. Currently always set to "CNAME". */
		`type`: Option[String] = None,
	  /** Output only. Data of the DNS Resource Record. */
		data: Option[String] = None
	)
	
	case class ListCertificateIssuanceConfigsResponse(
	  /** A list of certificate configs for the parent resource. */
		certificateIssuanceConfigs: Option[List[Schema.CertificateIssuanceConfig]] = None,
	  /** If there might be more results than those appearing in this response, then `next_page_token` is included. To get the next set of results, call this method again using the value of `next_page_token` as `page_token`. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object CertificateIssuanceConfig {
		enum KeyAlgorithmEnum extends Enum[KeyAlgorithmEnum] { case KEY_ALGORITHM_UNSPECIFIED, RSA_2048, ECDSA_P256 }
	}
	case class CertificateIssuanceConfig(
	  /** Identifier. A user-defined name of the certificate issuance config. CertificateIssuanceConfig names must be unique globally and match pattern `projects/&#42;/locations/&#42;/certificateIssuanceConfigs/&#42;`. */
		name: Option[String] = None,
	  /** Output only. The creation timestamp of a CertificateIssuanceConfig. */
		createTime: Option[String] = None,
	  /** Output only. The last update timestamp of a CertificateIssuanceConfig. */
		updateTime: Option[String] = None,
	  /** Optional. Set of labels associated with a CertificateIssuanceConfig. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. One or more paragraphs of text description of a CertificateIssuanceConfig. */
		description: Option[String] = None,
	  /** Required. The CA that issues the workload certificate. It includes the CA address, type, authentication to CA service, etc. */
		certificateAuthorityConfig: Option[Schema.CertificateAuthorityConfig] = None,
	  /** Required. Workload certificate lifetime requested. */
		lifetime: Option[String] = None,
	  /** Required. Specifies the percentage of elapsed time of the certificate lifetime to wait before renewing the certificate. Must be a number between 1-99, inclusive. */
		rotationWindowPercentage: Option[Int] = None,
	  /** Required. The key algorithm to use when generating the private key. */
		keyAlgorithm: Option[Schema.CertificateIssuanceConfig.KeyAlgorithmEnum] = None
	)
	
	case class CertificateAuthorityConfig(
	  /** Defines a CertificateAuthorityServiceConfig. */
		certificateAuthorityServiceConfig: Option[Schema.CertificateAuthorityServiceConfig] = None
	)
	
	case class CertificateAuthorityServiceConfig(
	  /** Required. A CA pool resource used to issue a certificate. The CA pool string has a relative resource path following the form "projects/{project}/locations/{location}/caPools/{ca_pool}". */
		caPool: Option[String] = None
	)
	
	case class ListTrustConfigsResponse(
	  /** A list of TrustConfigs for the parent resource. */
		trustConfigs: Option[List[Schema.TrustConfig]] = None,
	  /** If there might be more results than those appearing in this response, then `next_page_token` is included. To get the next set of results, call this method again using the value of `next_page_token` as `page_token`. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class TrustConfig(
	  /** Identifier. A user-defined name of the trust config. TrustConfig names must be unique globally and match pattern `projects/&#42;/locations/&#42;/trustConfigs/&#42;`. */
		name: Option[String] = None,
	  /** Output only. The creation timestamp of a TrustConfig. */
		createTime: Option[String] = None,
	  /** Output only. The last update timestamp of a TrustConfig. */
		updateTime: Option[String] = None,
	  /** Optional. Set of labels associated with a TrustConfig. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. One or more paragraphs of text description of a TrustConfig. */
		description: Option[String] = None,
	  /** This checksum is computed by the server based on the value of other fields, and may be sent on update and delete requests to ensure the client has an up-to-date value before proceeding. */
		etag: Option[String] = None,
	  /** Optional. Set of trust stores to perform validation against. This field is supported when TrustConfig is configured with Load Balancers, currently not supported for SPIFFE certificate validation. Only one TrustStore specified is currently allowed. */
		trustStores: Option[List[Schema.TrustStore]] = None,
	  /** Optional. A certificate matching an allowlisted certificate is always considered valid as long as the certificate is parseable, proof of private key possession is established, and constraints on the certificate's SAN field are met. */
		allowlistedCertificates: Option[List[Schema.AllowlistedCertificate]] = None
	)
	
	case class TrustStore(
	  /** Optional. List of Trust Anchors to be used while performing validation against a given TrustStore. */
		trustAnchors: Option[List[Schema.TrustAnchor]] = None,
	  /** Optional. Set of intermediate CA certificates used for the path building phase of chain validation. The field is currently not supported if TrustConfig is used for the workload certificate feature. */
		intermediateCas: Option[List[Schema.IntermediateCA]] = None
	)
	
	case class TrustAnchor(
	  /** PEM root certificate of the PKI used for validation. Each certificate provided in PEM format may occupy up to 5kB. */
		pemCertificate: Option[String] = None
	)
	
	case class IntermediateCA(
	  /** PEM intermediate certificate used for building up paths for validation. Each certificate provided in PEM format may occupy up to 5kB. */
		pemCertificate: Option[String] = None
	)
	
	case class AllowlistedCertificate(
	  /** Required. PEM certificate that is allowlisted. The certificate can be up to 5k bytes, and must be a parseable X.509 certificate. */
		pemCertificate: Option[String] = None
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
	
	case class OperationMetadata(
	  /** The time the operation was created. */
		createTime: Option[String] = None,
	  /** The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Server-defined resource path for the target of the operation. */
		target: Option[String] = None,
	  /** Name of the verb executed by the operation. */
		verb: Option[String] = None,
	  /** Human-readable status of the operation, if any. */
		statusMessage: Option[String] = None,
	  /** Identifies whether the user has requested cancellation of the operation. Operations that have successfully been cancelled have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		requestedCancellation: Option[Boolean] = None,
	  /** API version used to start the operation. */
		apiVersion: Option[String] = None
	)
}
