package com.boresjo.play.api.google.servicecontrol

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class CheckRequest(
	  /** Specifies the version of the service configuration that should be used to process the request. Must not be empty. Set this field to 'latest' to specify using the latest configuration. */
		serviceConfigId: Option[String] = None,
	  /** Describes attributes about the operation being executed by the service. */
		attributes: Option[Schema.AttributeContext] = None,
	  /** Describes the resources and the policies applied to each resource. */
		resources: Option[List[Schema.ResourceInfo]] = None,
	  /** Optional. Contains a comma-separated list of flags. */
		flags: Option[String] = None
	)
	
	case class AttributeContext(
	  /** The origin of a network activity. In a multi hop network activity, the origin represents the sender of the first hop. For the first hop, the `source` and the `origin` must have the same content. */
		origin: Option[Schema.Peer] = None,
	  /** The source of a network activity, such as starting a TCP connection. In a multi hop network activity, the source represents the sender of the last hop. */
		source: Option[Schema.Peer] = None,
	  /** The destination of a network activity, such as accepting a TCP connection. In a multi hop network activity, the destination represents the receiver of the last hop. */
		destination: Option[Schema.Peer] = None,
	  /** Represents a network request, such as an HTTP request. */
		request: Option[Schema.Request] = None,
	  /** Represents a network response, such as an HTTP response. */
		response: Option[Schema.Response] = None,
	  /** Represents a target resource that is involved with a network activity. If multiple resources are involved with an activity, this must be the primary one. */
		resource: Option[Schema.Resource] = None,
	  /** Represents an API operation that is involved to a network activity. */
		api: Option[Schema.Api] = None,
	  /** Supports extensions for advanced use cases, such as logs and metrics. */
		extensions: Option[List[Map[String, JsValue]]] = None
	)
	
	case class Peer(
	  /** The IP address of the peer. */
		ip: Option[String] = None,
	  /** The network port of the peer. */
		port: Option[String] = None,
	  /** The labels associated with the peer. */
		labels: Option[Map[String, String]] = None,
	  /** The identity of this peer. Similar to `Request.auth.principal`, but relative to the peer instead of the request. For example, the identity associated with a load balancer that forwarded the request. */
		principal: Option[String] = None,
	  /** The CLDR country/region code associated with the above IP address. If the IP address is private, the `region_code` should reflect the physical location where this peer is running. */
		regionCode: Option[String] = None
	)
	
	case class Request(
	  /** The unique ID for a request, which can be propagated to downstream systems. The ID should have low probability of collision within a single day for a specific service. */
		id: Option[String] = None,
	  /** The HTTP request method, such as `GET`, `POST`. */
		method: Option[String] = None,
	  /** The HTTP request headers. If multiple headers share the same key, they must be merged according to the HTTP spec. All header keys must be lowercased, because HTTP header keys are case-insensitive. */
		headers: Option[Map[String, String]] = None,
	  /** The HTTP URL path, excluding the query parameters. */
		path: Option[String] = None,
	  /** The HTTP request `Host` header value. */
		host: Option[String] = None,
	  /** The HTTP URL scheme, such as `http` and `https`. */
		scheme: Option[String] = None,
	  /** The HTTP URL query in the format of `name1=value1&name2=value2`, as it appears in the first line of the HTTP request. No decoding is performed. */
		query: Option[String] = None,
	  /** The timestamp when the `destination` service receives the last byte of the request. */
		time: Option[String] = None,
	  /** The HTTP request size in bytes. If unknown, it must be -1. */
		size: Option[String] = None,
	  /** The network protocol used with the request, such as "http/1.1", "spdy/3", "h2", "h2c", "webrtc", "tcp", "udp", "quic". See https://www.iana.org/assignments/tls-extensiontype-values/tls-extensiontype-values.xhtml#alpn-protocol-ids for details. */
		protocol: Option[String] = None,
	  /** A special parameter for request reason. It is used by security systems to associate auditing information with a request. */
		reason: Option[String] = None,
	  /** The request authentication. May be absent for unauthenticated requests. Derived from the HTTP request `Authorization` header or equivalent. */
		auth: Option[Schema.Auth] = None
	)
	
	case class Auth(
	  /** The authenticated principal. Reflects the issuer (`iss`) and subject (`sub`) claims within a JWT. The issuer and subject should be `/` delimited, with `/` percent-encoded within the subject fragment. For Google accounts, the principal format is: "https://accounts.google.com/{id}" */
		principal: Option[String] = None,
	  /** The intended audience(s) for this authentication information. Reflects the audience (`aud`) claim within a JWT. The audience value(s) depends on the `issuer`, but typically include one or more of the following pieces of information: &#42; The services intended to receive the credential. For example, ["https://pubsub.googleapis.com/", "https://storage.googleapis.com/"]. &#42; A set of service-based scopes. For example, ["https://www.googleapis.com/auth/cloud-platform"]. &#42; The client id of an app, such as the Firebase project id for JWTs from Firebase Auth. Consult the documentation for the credential issuer to determine the information provided. */
		audiences: Option[List[String]] = None,
	  /** The authorized presenter of the credential. Reflects the optional Authorized Presenter (`azp`) claim within a JWT or the OAuth client id. For example, a Google Cloud Platform client id looks as follows: "123456789012.apps.googleusercontent.com". */
		presenter: Option[String] = None,
	  /** Structured claims presented with the credential. JWTs include `{key: value}` pairs for standard and private claims. The following is a subset of the standard required and optional claims that would typically be presented for a Google-based JWT: {'iss': 'accounts.google.com', 'sub': '113289723416554971153', 'aud': ['123456789012', 'pubsub.googleapis.com'], 'azp': '123456789012.apps.googleusercontent.com', 'email': 'jsmith@example.com', 'iat': 1353601026, 'exp': 1353604926} SAML assertions are similarly specified, but with an identity provider dependent structure. */
		claims: Option[Map[String, JsValue]] = None,
	  /** A list of access level resource names that allow resources to be accessed by authenticated requester. It is part of Secure GCP processing for the incoming request. An access level string has the format: "//{api_service_name}/accessPolicies/{policy_id}/accessLevels/{short_name}" Example: "//accesscontextmanager.googleapis.com/accessPolicies/MY_POLICY_ID/accessLevels/MY_LEVEL" */
		accessLevels: Option[List[String]] = None,
	  /** Identifies the client credential id used for authentication. credential_id is in the format of AUTH_METHOD:IDENTIFIER, e.g. "serviceaccount:XXXXX, apikey:XXXXX" where the format of the IDENTIFIER can vary for different AUTH_METHODs. */
		credentialId: Option[String] = None
	)
	
	case class Response(
	  /** The HTTP response status code, such as `200` and `404`. */
		code: Option[String] = None,
	  /** The HTTP response size in bytes. If unknown, it must be -1. */
		size: Option[String] = None,
	  /** The HTTP response headers. If multiple headers share the same key, they must be merged according to HTTP spec. All header keys must be lowercased, because HTTP header keys are case-insensitive. */
		headers: Option[Map[String, String]] = None,
	  /** The timestamp when the `destination` service sends the last byte of the response. */
		time: Option[String] = None,
	  /** The amount of time it takes the backend service to fully respond to a request. Measured from when the destination service starts to send the request to the backend until when the destination service receives the complete response from the backend. */
		backendLatency: Option[String] = None
	)
	
	case class Resource(
	  /** The name of the service that this resource belongs to, such as `pubsub.googleapis.com`. The service may be different from the DNS hostname that actually serves the request. */
		service: Option[String] = None,
	  /** The stable identifier (name) of a resource on the `service`. A resource can be logically identified as "//{resource.service}/{resource.name}". The differences between a resource name and a URI are: &#42; Resource name is a logical identifier, independent of network protocol and API version. For example, `//pubsub.googleapis.com/projects/123/topics/news-feed`. &#42; URI often includes protocol and version information, so it can be used directly by applications. For example, `https://pubsub.googleapis.com/v1/projects/123/topics/news-feed`. See https://cloud.google.com/apis/design/resource_names for details. */
		name: Option[String] = None,
	  /** The type of the resource. The syntax is platform-specific because different platforms define their resources differently. For Google APIs, the type format must be "{service}/{kind}", such as "pubsub.googleapis.com/Topic". */
		`type`: Option[String] = None,
	  /** The labels or tags on the resource, such as AWS resource tags and Kubernetes resource labels. */
		labels: Option[Map[String, String]] = None,
	  /** The unique identifier of the resource. UID is unique in the time and space for this resource within the scope of the service. It is typically generated by the server on successful creation of a resource and must not be changed. UID is used to uniquely identify resources with resource name reuses. This should be a UUID4. */
		uid: Option[String] = None,
	  /** Annotations is an unstructured key-value map stored with a resource that may be set by external tools to store and retrieve arbitrary metadata. They are not queryable and should be preserved when modifying objects. More info: https://kubernetes.io/docs/concepts/overview/working-with-objects/annotations/ */
		annotations: Option[Map[String, String]] = None,
	  /** Mutable. The display name set by clients. Must be <= 63 characters. */
		displayName: Option[String] = None,
	  /** Output only. The timestamp when the resource was created. This may be either the time creation was initiated or when it was completed. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when the resource was last updated. Any change to the resource made by users must refresh this value. Changes to a resource made by the service should refresh this value. */
		updateTime: Option[String] = None,
	  /** Output only. The timestamp when the resource was deleted. If the resource is not deleted, this must be empty. */
		deleteTime: Option[String] = None,
	  /** Output only. An opaque value that uniquely identifies a version or generation of a resource. It can be used to confirm that the client and server agree on the ordering of a resource being written. */
		etag: Option[String] = None,
	  /** Immutable. The location of the resource. The location encoding is specific to the service provider, and new encoding may be introduced as the service evolves. For Google Cloud products, the encoding is what is used by Google Cloud APIs, such as `us-east1`, `aws-us-east-1`, and `azure-eastus2`. The semantics of `location` is identical to the `cloud.googleapis.com/location` label used by some Google Cloud APIs. */
		location: Option[String] = None
	)
	
	case class Api(
	  /** The API service name. It is a logical identifier for a networked API, such as "pubsub.googleapis.com". The naming syntax depends on the API management system being used for handling the request. */
		service: Option[String] = None,
	  /** The API operation name. For gRPC requests, it is the fully qualified API method name, such as "google.pubsub.v1.Publisher.Publish". For OpenAPI requests, it is the `operationId`, such as "getPet". */
		operation: Option[String] = None,
	  /** The API protocol used for sending the request, such as "http", "https", "grpc", or "internal". */
		protocol: Option[String] = None,
	  /** The API version associated with the API operation above, such as "v1" or "v1alpha1". */
		version: Option[String] = None
	)
	
	case class ResourceInfo(
	  /** The name of the resource referenced in the request. */
		name: Option[String] = None,
	  /** The resource type in the format of "{service}/{kind}". */
		`type`: Option[String] = None,
	  /** The resource permission needed for this request. The format must be "{service}/{plural}.{verb}". */
		permission: Option[String] = None,
	  /** Optional. The identifier of the container of this resource. For Google Cloud APIs, the resource container must be one of the following formats: - `projects/` - `folders/` - `organizations/` Required for the policy enforcement on the container level (e.g. VPCSC, Location Policy check, Org Policy check). */
		container: Option[String] = None,
	  /** Optional. The location of the resource, it must be a valid zone, region or multiregion, for example: "europe-west4", "northamerica-northeast1-a". Required for location policy check. */
		location: Option[String] = None
	)
	
	case class CheckResponse(
	  /** Operation is allowed when this field is not set. Any non-'OK' status indicates a denial; google.rpc.Status.details would contain additional details about the denial. */
		status: Option[Schema.Status] = None,
	  /** Returns a set of request contexts generated from the `CheckRequest`. */
		headers: Option[Map[String, String]] = None
	)
	
	case class Status(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class ReportRequest(
	  /** Specifies the version of the service configuration that should be used to process the request. Must not be empty. Set this field to 'latest' to specify using the latest configuration. */
		serviceConfigId: Option[String] = None,
	  /** Describes the list of operations to be reported. Each operation is represented as an AttributeContext, and contains all attributes around an API access. */
		operations: Option[List[Schema.AttributeContext]] = None
	)
	
	case class ReportResponse(
	  /** The extension field to store serialized OTel responses. e.g. ExportLogsServiceResponse, ExportMetricsServiceResponse. */
		extensions: Option[Map[String, JsValue]] = None
	)
	
	case class AuditLog(
	  /** The name of the API service performing the operation. For example, `"compute.googleapis.com"`. */
		serviceName: Option[String] = None,
	  /** The name of the service method or operation. For API calls, this should be the name of the API method. For example, "google.cloud.bigquery.v2.TableService.InsertTable" "google.logging.v2.ConfigServiceV2.CreateSink" */
		methodName: Option[String] = None,
	  /** The resource or collection that is the target of the operation. The name is a scheme-less URI, not including the API service name. For example: "projects/PROJECT_ID/zones/us-central1-a/instances" "projects/PROJECT_ID/datasets/DATASET_ID" */
		resourceName: Option[String] = None,
	  /** The resource location information. */
		resourceLocation: Option[Schema.ResourceLocation] = None,
	  /** The resource's original state before mutation. Present only for operations which have successfully modified the targeted resource(s). In general, this field should contain all changed fields, except those that are already been included in `request`, `response`, `metadata` or `service_data` fields. When the JSON object represented here has a proto equivalent, the proto name will be indicated in the `@type` property. */
		resourceOriginalState: Option[Map[String, JsValue]] = None,
	  /** The number of items returned from a List or Query API method, if applicable. */
		numResponseItems: Option[String] = None,
	  /** The status of the overall operation. */
		status: Option[Schema.Status] = None,
	  /** Authentication information. */
		authenticationInfo: Option[Schema.AuthenticationInfo] = None,
	  /** Authorization information. If there are multiple resources or permissions involved, then there is one AuthorizationInfo element for each {resource, permission} tuple. */
		authorizationInfo: Option[List[Schema.AuthorizationInfo]] = None,
	  /** Indicates the policy violations for this request. If the request is denied by the policy, violation information will be logged here. */
		policyViolationInfo: Option[Schema.PolicyViolationInfo] = None,
	  /** Metadata about the operation. */
		requestMetadata: Option[Schema.RequestMetadata] = None,
	  /** The operation request. This may not include all request parameters, such as those that are too large, privacy-sensitive, or duplicated elsewhere in the log record. It should never include user-generated data, such as file contents. When the JSON object represented here has a proto equivalent, the proto name will be indicated in the `@type` property. */
		request: Option[Map[String, JsValue]] = None,
	  /** The operation response. This may not include all response elements, such as those that are too large, privacy-sensitive, or duplicated elsewhere in the log record. It should never include user-generated data, such as file contents. When the JSON object represented here has a proto equivalent, the proto name will be indicated in the `@type` property. */
		response: Option[Map[String, JsValue]] = None,
	  /** Other service-specific data about the request, response, and other information associated with the current audited event. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** Deprecated. Use the `metadata` field instead. Other service-specific data about the request, response, and other activities. */
		serviceData: Option[Map[String, JsValue]] = None
	)
	
	case class ResourceLocation(
	  /** The locations of a resource after the execution of the operation. Requests to create or delete a location based resource must populate the 'current_locations' field and not the 'original_locations' field. For example: "europe-west1-a" "us-east1" "nam3" */
		currentLocations: Option[List[String]] = None,
	  /** The locations of a resource prior to the execution of the operation. Requests that mutate the resource's location must populate both the 'original_locations' as well as the 'current_locations' fields. For example: "europe-west1-a" "us-east1" "nam3" */
		originalLocations: Option[List[String]] = None
	)
	
	case class AuthenticationInfo(
	  /** The email address of the authenticated user (or service account on behalf of third party principal) making the request. For third party identity callers, the `principal_subject` field is populated instead of this field. For privacy reasons, the principal email address is sometimes redacted. For more information, see [Caller identities in audit logs](https://cloud.google.com/logging/docs/audit#user-id). */
		principalEmail: Option[String] = None,
	  /** The authority selector specified by the requestor, if any. It is not guaranteed that the principal was allowed to use this authority. */
		authoritySelector: Option[String] = None,
	  /** The third party identification (if any) of the authenticated user making the request. When the JSON object represented here has a proto equivalent, the proto name will be indicated in the `@type` property. */
		thirdPartyPrincipal: Option[Map[String, JsValue]] = None,
	  /** The name of the service account key used to create or exchange credentials for authenticating the service account making the request. This is a scheme-less URI full resource name. For example: "//iam.googleapis.com/projects/{PROJECT_ID}/serviceAccounts/{ACCOUNT}/keys/{key}" */
		serviceAccountKeyName: Option[String] = None,
	  /** Identity delegation history of an authenticated service account that makes the request. It contains information on the real authorities that try to access GCP resources by delegating on a service account. When multiple authorities present, they are guaranteed to be sorted based on the original ordering of the identity delegation events. */
		serviceAccountDelegationInfo: Option[List[Schema.ServiceAccountDelegationInfo]] = None,
	  /** String representation of identity of requesting party. Populated for both first and third party identities. */
		principalSubject: Option[String] = None,
	  /** Records the history of delegated resource access across Google services. */
		serviceDelegationHistory: Option[Schema.ServiceDelegationHistory] = None
	)
	
	case class ServiceAccountDelegationInfo(
	  /** A string representing the principal_subject associated with the identity. For most identities, the format will be `principal://iam.googleapis.com/{identity pool name}/subject/{subject)` except for some GKE identities (GKE_WORKLOAD, FREEFORM, GKE_HUB_WORKLOAD) that are still in the legacy format `serviceAccount:{identity pool name}[{subject}]` */
		principalSubject: Option[String] = None,
	  /** First party (Google) identity as the real authority. */
		firstPartyPrincipal: Option[Schema.FirstPartyPrincipal] = None,
	  /** Third party identity as the real authority. */
		thirdPartyPrincipal: Option[Schema.ThirdPartyPrincipal] = None
	)
	
	case class FirstPartyPrincipal(
	  /** The email address of a Google account. . */
		principalEmail: Option[String] = None,
	  /** Metadata about the service that uses the service account. . */
		serviceMetadata: Option[Map[String, JsValue]] = None
	)
	
	case class ThirdPartyPrincipal(
	  /** Metadata about third party identity. */
		thirdPartyClaims: Option[Map[String, JsValue]] = None
	)
	
	case class ServiceDelegationHistory(
	  /** The original end user who initiated the request to GCP. */
		originalPrincipal: Option[String] = None,
	  /** Data identifying the service specific jobs or units of work that were involved in a chain of service calls. */
		serviceMetadata: Option[List[Schema.ServiceMetadata]] = None
	)
	
	case class ServiceMetadata(
	  /** A string representing the principal_subject associated with the identity. For most identities, the format will be `principal://iam.googleapis.com/{identity pool name}/subject/{subject)` except for some GKE identities (GKE_WORKLOAD, FREEFORM, GKE_HUB_WORKLOAD) that are still in the legacy format `serviceAccount:{identity pool name}[{subject}]` If the identity is a Google account (e.g. workspace user account or service account), this will be the email of the prefixed by `serviceAccount:`. For example: `serviceAccount:my-service-account@project-1.iam.gserviceaccount.com`. If the identity is an individual user, the identity will be formatted as: `user:user_ABC@email.com`. */
		principalSubject: Option[String] = None,
	  /** The service's fully qualified domain name, e.g. "dataproc.googleapis.com". */
		serviceDomain: Option[String] = None,
	  /** Additional metadata provided by service teams to describe service specific job information that was triggered by the original principal. */
		jobMetadata: Option[Map[String, JsValue]] = None
	)
	
	object AuthorizationInfo {
		enum PermissionTypeEnum extends Enum[PermissionTypeEnum] { case PERMISSION_TYPE_UNSPECIFIED, ADMIN_READ, ADMIN_WRITE, DATA_READ, DATA_WRITE }
	}
	case class AuthorizationInfo(
	  /** The resource being accessed, as a REST-style or cloud resource string. For example: bigquery.googleapis.com/projects/PROJECTID/datasets/DATASETID or projects/PROJECTID/datasets/DATASETID */
		resource: Option[String] = None,
	  /** The required IAM permission. */
		permission: Option[String] = None,
	  /** Whether or not authorization for `resource` and `permission` was granted. */
		granted: Option[Boolean] = None,
	  /** Resource attributes used in IAM condition evaluation. This field contains resource attributes like resource type and resource name. To get the whole view of the attributes used in IAM condition evaluation, the user must also look into `AuditLog.request_metadata.request_attributes`. */
		resourceAttributes: Option[Schema.Resource] = None,
	  /** The type of the permission that was checked. For data access audit logs this corresponds with the permission type that must be enabled in the project/folder/organization IAM policy in order for the log to be written. */
		permissionType: Option[Schema.AuthorizationInfo.PermissionTypeEnum] = None
	)
	
	case class PolicyViolationInfo(
	  /** Indicates the orgpolicy violations for this resource. */
		orgPolicyViolationInfo: Option[Schema.OrgPolicyViolationInfo] = None
	)
	
	case class OrgPolicyViolationInfo(
	  /** Optional. Deprecated. Resource payload that is currently in scope and is subjected to orgpolicy conditions. This payload may be the subset of the actual Resource that may come in the request. */
		payload: Option[Map[String, JsValue]] = None,
	  /** Optional. Resource type that the orgpolicy is checked against. Example: compute.googleapis.com/Instance, store.googleapis.com/bucket */
		resourceType: Option[String] = None,
	  /** Optional. Deprecated. Tags referenced on the resource at the time of evaluation. */
		resourceTags: Option[Map[String, String]] = None,
	  /** Optional. Policy violations */
		violationInfo: Option[List[Schema.ViolationInfo]] = None
	)
	
	object ViolationInfo {
		enum PolicyTypeEnum extends Enum[PolicyTypeEnum] { case POLICY_TYPE_UNSPECIFIED, BOOLEAN_CONSTRAINT, LIST_CONSTRAINT, CUSTOM_CONSTRAINT }
	}
	case class ViolationInfo(
	  /** Optional. Constraint name */
		constraint: Option[String] = None,
	  /** Optional. Error message that policy is indicating. */
		errorMessage: Option[String] = None,
	  /** Optional. Value that is being checked for the policy. This could be in encrypted form (if pii sensitive). This field will only be emitted in LIST_POLICY types */
		checkedValue: Option[String] = None,
	  /** Optional. Indicates the type of the policy. */
		policyType: Option[Schema.ViolationInfo.PolicyTypeEnum] = None
	)
	
	case class RequestMetadata(
	  /** The IP address of the caller. For a caller from the internet, this will be the public IPv4 or IPv6 address. For calls made from inside Google's internal production network from one GCP service to another, `caller_ip` will be redacted to "private". For a caller from a Compute Engine VM with a external IP address, `caller_ip` will be the VM's external IP address. For a caller from a Compute Engine VM without a external IP address, if the VM is in the same organization (or project) as the accessed resource, `caller_ip` will be the VM's internal IPv4 address, otherwise `caller_ip` will be redacted to "gce-internal-ip". See https://cloud.google.com/compute/docs/vpc/ for more information. */
		callerIp: Option[String] = None,
	  /** The user agent of the caller. This information is not authenticated and should be treated accordingly. For example: + `google-api-python-client/1.4.0`: The request was made by the Google API client for Python. + `Cloud SDK Command Line Tool apitools-client/1.0 gcloud/0.9.62`: The request was made by the Google Cloud SDK CLI (gcloud). + `AppEngine-Google; (+http://code.google.com/appengine; appid: s~my-project`: The request was made from the `my-project` App Engine app. */
		callerSuppliedUserAgent: Option[String] = None,
	  /** The network of the caller. Set only if the network host project is part of the same GCP organization (or project) as the accessed resource. See https://cloud.google.com/compute/docs/vpc/ for more information. This is a scheme-less URI full resource name. For example: "//compute.googleapis.com/projects/PROJECT_ID/global/networks/NETWORK_ID" */
		callerNetwork: Option[String] = None,
	  /** Request attributes used in IAM condition evaluation. This field contains request attributes like request time and access levels associated with the request. To get the whole view of the attributes used in IAM condition evaluation, the user must also look into `AuditLog.authentication_info.resource_attributes`. */
		requestAttributes: Option[Schema.Request] = None,
	  /** The destination of a network activity, such as accepting a TCP connection. In a multi hop network activity, the destination represents the receiver of the last hop. Only two fields are used in this message, Peer.port and Peer.ip. These fields are optionally populated by those services utilizing the IAM condition feature. */
		destinationAttributes: Option[Schema.Peer] = None
	)
	
	case class SpanContext(
	  /** The resource name of the span. The format is: projects/[PROJECT_ID_OR_NUMBER]/traces/[TRACE_ID]/spans/[SPAN_ID] `[TRACE_ID]` is a unique identifier for a trace within a project; it is a 32-character hexadecimal encoding of a 16-byte array. `[SPAN_ID]` is a unique identifier for a span within a trace; it is a 16-character hexadecimal encoding of an 8-byte array. */
		spanName: Option[String] = None
	)
	
	object V2LogEntry {
		enum SeverityEnum extends Enum[SeverityEnum] { case DEFAULT, DEBUG, INFO, NOTICE, WARNING, ERROR, CRITICAL, ALERT, EMERGENCY }
	}
	case class V2LogEntry(
	  /** Required. The log to which this log entry belongs. Examples: `"syslog"`, `"book_log"`. */
		name: Option[String] = None,
	  /** The time the event described by the log entry occurred. If omitted, defaults to operation start time. */
		timestamp: Option[String] = None,
	  /** The severity of the log entry. The default value is `LogSeverity.DEFAULT`. */
		severity: Option[Schema.V2LogEntry.SeverityEnum] = None,
	  /** Optional. Information about the HTTP request associated with this log entry, if applicable. */
		httpRequest: Option[Schema.V2HttpRequest] = None,
	  /** Optional. Resource name of the trace associated with the log entry, if any. If this field contains a relative resource name, you can assume the name is relative to `//tracing.googleapis.com`. Example: `projects/my-projectid/traces/06796866738c859f2f19b7cfb3214824` */
		trace: Option[String] = None,
	  /** A unique ID for the log entry used for deduplication. If omitted, the implementation will generate one based on operation_id. */
		insertId: Option[String] = None,
	  /** A set of user-defined (key, value) data that provides additional information about the log entry. */
		labels: Option[Map[String, String]] = None,
	  /** A set of user-defined (key, value) data that provides additional information about the moniotored resource that the log entry belongs to. */
		monitoredResourceLabels: Option[Map[String, String]] = None,
	  /** The log entry payload, represented as a protocol buffer that is expressed as a JSON object. The only accepted type currently is AuditLog. */
		protoPayload: Option[Map[String, JsValue]] = None,
	  /** The log entry payload, represented as a Unicode string (UTF-8). */
		textPayload: Option[String] = None,
	  /** The log entry payload, represented as a structure that is expressed as a JSON object. */
		structPayload: Option[Map[String, JsValue]] = None,
	  /** Optional. Information about an operation associated with the log entry, if applicable. */
		operation: Option[Schema.V2LogEntryOperation] = None,
	  /** Optional. Source code location information associated with the log entry, if any. */
		sourceLocation: Option[Schema.V2LogEntrySourceLocation] = None
	)
	
	case class V2HttpRequest(
	  /** The request method. Examples: `"GET"`, `"HEAD"`, `"PUT"`, `"POST"`. */
		requestMethod: Option[String] = None,
	  /** The scheme (http, https), the host name, the path, and the query portion of the URL that was requested. Example: `"http://example.com/some/info?color=red"`. */
		requestUrl: Option[String] = None,
	  /** The size of the HTTP request message in bytes, including the request headers and the request body. */
		requestSize: Option[String] = None,
	  /** The response code indicating the status of the response. Examples: 200, 404. */
		status: Option[Int] = None,
	  /** The size of the HTTP response message sent back to the client, in bytes, including the response headers and the response body. */
		responseSize: Option[String] = None,
	  /** The user agent sent by the client. Example: `"Mozilla/4.0 (compatible; MSIE 6.0; Windows 98; Q312461; .NET CLR 1.0.3705)"`. */
		userAgent: Option[String] = None,
	  /** The IP address (IPv4 or IPv6) of the client that issued the HTTP request. Examples: `"192.168.1.1"`, `"FE80::0202:B3FF:FE1E:8329"`. */
		remoteIp: Option[String] = None,
	  /** The IP address (IPv4 or IPv6) of the origin server that the request was sent to. */
		serverIp: Option[String] = None,
	  /** The referer URL of the request, as defined in [HTTP/1.1 Header Field Definitions](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html). */
		referer: Option[String] = None,
	  /** The request processing latency on the server, from the time the request was received until the response was sent. */
		latency: Option[String] = None,
	  /** Whether or not a cache lookup was attempted. */
		cacheLookup: Option[Boolean] = None,
	  /** Whether or not an entity was served from cache (with or without validation). */
		cacheHit: Option[Boolean] = None,
	  /** Whether or not the response was validated with the origin server before being served from cache. This field is only meaningful if `cache_hit` is True. */
		cacheValidatedWithOriginServer: Option[Boolean] = None,
	  /** The number of HTTP response bytes inserted into cache. Set only when a cache fill was attempted. */
		cacheFillBytes: Option[String] = None,
	  /** Protocol used for the request. Examples: "HTTP/1.1", "HTTP/2", "websocket" */
		protocol: Option[String] = None
	)
	
	case class V2LogEntryOperation(
	  /** Optional. An arbitrary operation identifier. Log entries with the same identifier are assumed to be part of the same operation. */
		id: Option[String] = None,
	  /** Optional. An arbitrary producer identifier. The combination of `id` and `producer` must be globally unique. Examples for `producer`: `"MyDivision.MyBigCompany.com"`, `"github.com/MyProject/MyApplication"`. */
		producer: Option[String] = None,
	  /** Optional. Set this to True if this is the first log entry in the operation. */
		first: Option[Boolean] = None,
	  /** Optional. Set this to True if this is the last log entry in the operation. */
		last: Option[Boolean] = None
	)
	
	case class V2LogEntrySourceLocation(
	  /** Optional. Source file name. Depending on the runtime environment, this might be a simple name or a fully-qualified name. */
		file: Option[String] = None,
	  /** Optional. Line within the source file. 1-based; 0 indicates no line number available. */
		line: Option[String] = None,
	  /** Optional. Human-readable name of the function or method being invoked, with optional context such as the class or package name. This information may be used in contexts such as the logs viewer, where a file and line number are less meaningful. The format can vary by language. For example: `qual.if.ied.Class.method` (Java), `dir/package.func` (Go), `function` (Python). */
		function: Option[String] = None
	)
	
	object V2ResourceEvent {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, CREATE, UPDATE, DELETE, UNDELETE }
		enum PathEnum extends Enum[PathEnum] { case API_PATH_UNSPECIFIED, REQUEST, RESPONSE }
	}
	case class V2ResourceEvent(
	  /** The payload contains metadata associated with the resource event. A ResourceEventPayloadStatus is provided instead if the original payload cannot be returned due to a limitation (e.g. size limit). */
		payload: Option[Map[String, JsValue]] = None,
	  /** The resource event type determines how the backend service should process the event. */
		`type`: Option[Schema.V2ResourceEvent.TypeEnum] = None,
	  /** The destinations field determines which backend services should handle the event. This should be specified as a comma-delimited string. */
		destinations: Option[String] = None,
	  /** The resource associated with the event. */
		resource: Option[Schema.Resource] = None,
	  /** The parent resource for the resource. */
		parent: Option[Schema.Resource] = None,
	  /** The api path the resource event was created in. This should match the source of the `payload` field. For direct integrations with Chemist, this should generally be the RESPONSE. go/resource-event-pipeline-type */
		path: Option[Schema.V2ResourceEvent.PathEnum] = None,
	  /** The ESF unique context id of the api request, from which this resource event originated. This field is only needed for CAIS integration via api annotation. See go/cais-lro-delete for more details. */
		contextId: Option[String] = None
	)
}
