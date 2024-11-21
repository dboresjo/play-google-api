package com.boresjo.play.api.google.firebasehosting

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
	
	object CustomDomainMetadata {
		enum HostStateEnum extends Enum[HostStateEnum] { case HOST_STATE_UNSPECIFIED, HOST_UNHOSTED, HOST_UNREACHABLE, HOST_MISMATCH, HOST_CONFLICT, HOST_ACTIVE }
		enum OwnershipStateEnum extends Enum[OwnershipStateEnum] { case OWNERSHIP_STATE_UNSPECIFIED, OWNERSHIP_MISSING, OWNERSHIP_UNREACHABLE, OWNERSHIP_MISMATCH, OWNERSHIP_CONFLICT, OWNERSHIP_PENDING, OWNERSHIP_ACTIVE }
		enum CertStateEnum extends Enum[CertStateEnum] { case CERT_STATE_UNSPECIFIED, CERT_PREPARING, CERT_VALIDATING, CERT_PROPAGATING, CERT_ACTIVE, CERT_EXPIRING_SOON, CERT_EXPIRED }
	}
	case class CustomDomainMetadata(
	  /** The `HostState` of the domain name this `CustomDomain` refers to. */
		hostState: Option[Schema.CustomDomainMetadata.HostStateEnum] = None,
	  /** The `OwnershipState` of the domain name this `CustomDomain` refers to. */
		ownershipState: Option[Schema.CustomDomainMetadata.OwnershipStateEnum] = None,
	  /** The `CertState` of the domain name's SSL certificate. */
		certState: Option[Schema.CustomDomainMetadata.CertStateEnum] = None,
	  /** A set of DNS record updates and ACME challenges that allow you to transition domain names to Firebase Hosting with zero downtime. These updates allow Hosting to create an SSL certificate and establish ownership for your custom domain before Hosting begins serving traffic on it. If your domain name is already in active use with another provider, add one of the challenges and make the recommended DNS updates. After adding challenges and adjusting DNS records as necessary, wait for the `ownershipState` to be `OWNERSHIP_ACTIVE` and the `certState` to be `CERT_ACTIVE` before sending traffic to Hosting. */
		liveMigrationSteps: Option[List[Schema.LiveMigrationStep]] = None,
	  /** A set of DNS record updates that allow Hosting to serve secure content on your domain name. The record type determines the update's purpose: - `A` and `AAAA`: Updates your domain name's IP addresses so that they direct traffic to Hosting servers. - `TXT`: Updates ownership permissions on your domain name, letting Hosting know that your custom domain's project has permission to perform actions for that domain name. - `CAA`: Updates your domain name's list of authorized Certificate Authorities (CAs). Only present if you have existing `CAA` records that prohibit Hosting's CA from minting certs for your domain name. These updates include all DNS changes you'll need to get started with Hosting, but, if made all at once, can result in a brief period of downtime for your domain name--while Hosting creates and uploads an SSL cert, for example. If you'd like to add your domain name to Hosting without downtime, complete the `liveMigrationSteps` first, before making the remaining updates in this field. */
		quickSetupUpdates: Option[Schema.DnsUpdates] = None,
	  /** A list of issues that are currently preventing Hosting from completing the operation. These are generally DNS-related issues that Hosting encounters when querying a domain name's records or attempting to mint an SSL certificate. */
		issues: Option[List[Schema.Status]] = None
	)
	
	object LiveMigrationStep {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PREPARING, PENDING, INCOMPLETE, PROCESSING, COMPLETE }
	}
	case class LiveMigrationStep(
	  /** Output only. The state of the live migration step, indicates whether you should work to complete the step now, in the future, or have already completed it. */
		state: Option[Schema.LiveMigrationStep.StateEnum] = None,
	  /** Output only. A pair of ACME challenges that Hosting's Certificate Authority (CA) can use to create an SSL cert for your domain name. Use either the DNS or HTTP challenge; it's not necessary to provide both. */
		certVerification: Option[Schema.CertVerification] = None,
	  /** Output only. DNS updates to facilitate your domain's zero-downtime migration to Hosting. */
		dnsUpdates: Option[Schema.DnsUpdates] = None,
	  /** Output only. Issues that prevent the current step from completing. */
		issues: Option[List[Schema.Status]] = None
	)
	
	case class CertVerification(
	  /** Output only. A `TXT` record to add to your DNS records that confirms your intent to let Hosting create an SSL cert for your domain name. */
		dns: Option[Schema.DnsUpdates] = None,
	  /** Output only. A file to add to your existing, non-Hosting hosting service that confirms your intent to let Hosting create an SSL cert for your domain name. */
		http: Option[Schema.HttpUpdate] = None
	)
	
	case class DnsUpdates(
	  /** The set of DNS records Hosting discovered when inspecting a domain. */
		discovered: Option[List[Schema.DnsRecordSet]] = None,
	  /** The set of DNS records Hosting needs to serve secure content on the domain. */
		desired: Option[List[Schema.DnsRecordSet]] = None,
	  /** The last time Hosting checked your custom domain's DNS records. */
		checkTime: Option[String] = None
	)
	
	case class DnsRecordSet(
	  /** Output only. The domain name the record set pertains to. */
		domainName: Option[String] = None,
	  /** Output only. An error Hosting services encountered when querying your domain name's DNS records. Note: Hosting ignores `NXDOMAIN` errors, as those generally just mean that a domain name hasn't been set up yet. */
		checkError: Option[Schema.Status] = None,
	  /** Output only. Records on the domain. */
		records: Option[List[Schema.DnsRecord]] = None
	)
	
	object DnsRecord {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, A, CNAME, TXT, AAAA, CAA }
		enum RequiredActionEnum extends Enum[RequiredActionEnum] { case NONE, ADD, REMOVE }
	}
	case class DnsRecord(
	  /** Output only. The domain name the record pertains to, e.g. `foo.bar.com.`. */
		domainName: Option[String] = None,
	  /** Output only. The record's type, which determines what data the record contains. */
		`type`: Option[Schema.DnsRecord.TypeEnum] = None,
	  /** Output only. The data of the record. The meaning of the value depends on record type: - A and AAAA: IP addresses for the domain name. - CNAME: Another domain to check for records. - TXT: Arbitrary text strings associated with the domain name. Hosting uses TXT records to determine which Firebase projects have permission to act on the domain name's behalf. - CAA: The record's flags, tag, and value, e.g. `0 issue "pki.goog"`. */
		rdata: Option[String] = None,
	  /** Output only. An enum that indicates the a required action for this record. */
		requiredAction: Option[Schema.DnsRecord.RequiredActionEnum] = None
	)
	
	case class HttpUpdate(
	  /** Output only. The path to the file. */
		path: Option[String] = None,
	  /** Output only. A text string to serve at the path. */
		desired: Option[String] = None,
	  /** Output only. Whether Hosting was able to find the required file contents on the specified path during its last check. */
		discovered: Option[String] = None,
	  /** Output only. The last time Hosting systems checked for the file contents. */
		lastCheckTime: Option[String] = None,
	  /** Output only. An error encountered during the last contents check. If null, the check completed successfully. */
		checkError: Option[Schema.Status] = None
	)
}
