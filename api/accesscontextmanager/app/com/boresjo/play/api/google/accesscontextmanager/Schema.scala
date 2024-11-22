package com.boresjo.play.api.google.accesscontextmanager

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

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
	
	case class ListAccessPoliciesResponse(
	  /** List of the AccessPolicy instances. */
		accessPolicies: Option[List[Schema.AccessPolicy]] = None,
	  /** The pagination token to retrieve the next page of results. If the value is empty, no further results remain. */
		nextPageToken: Option[String] = None
	)
	
	case class AccessPolicy(
	  /** Output only. Identifier. Resource name of the `AccessPolicy`. Format: `accessPolicies/{access_policy}` */
		name: Option[String] = None,
	  /** Required. The parent of this `AccessPolicy` in the Cloud Resource Hierarchy. Currently immutable once created. Format: `organizations/{organization_id}` */
		parent: Option[String] = None,
	  /** Required. Human readable title. Does not affect behavior. */
		title: Option[String] = None,
	  /** The scopes of the AccessPolicy. Scopes define which resources a policy can restrict and where its resources can be referenced. For example, policy A with `scopes=["folders/123"]` has the following behavior: - ServicePerimeter can only restrict projects within `folders/123`. - ServicePerimeter within policy A can only reference access levels defined within policy A. - Only one policy can include a given scope; thus, attempting to create a second policy which includes `folders/123` will result in an error. If no scopes are provided, then any resource within the organization can be restricted. Scopes cannot be modified after a policy is created. Policies can only have a single scope. Format: list of `folders/{folder_number}` or `projects/{project_number}` */
		scopes: Option[List[String]] = None,
	  /** Output only. An opaque identifier for the current version of the `AccessPolicy`. This will always be a strongly validated etag, meaning that two Access Policies will be identical if and only if their etags are identical. Clients should not expect this to be in any specific format. */
		etag: Option[String] = None
	)
	
	case class ListAccessLevelsResponse(
	  /** List of the Access Level instances. */
		accessLevels: Option[List[Schema.AccessLevel]] = None,
	  /** The pagination token to retrieve the next page of results. If the value is empty, no further results remain. */
		nextPageToken: Option[String] = None
	)
	
	case class AccessLevel(
	  /** Identifier. Resource name for the `AccessLevel`. Format: `accessPolicies/{access_policy}/accessLevels/{access_level}`. The `access_level` component must begin with a letter, followed by alphanumeric characters or `_`. Its maximum length is 50 characters. After you create an `AccessLevel`, you cannot change its `name`. */
		name: Option[String] = None,
	  /** Human readable title. Must be unique within the Policy. */
		title: Option[String] = None,
	  /** Description of the `AccessLevel` and its use. Does not affect behavior. */
		description: Option[String] = None,
	  /** A `BasicLevel` composed of `Conditions`. */
		basic: Option[Schema.BasicLevel] = None,
	  /** A `CustomLevel` written in the Common Expression Language. */
		custom: Option[Schema.CustomLevel] = None
	)
	
	object BasicLevel {
		enum CombiningFunctionEnum extends Enum[CombiningFunctionEnum] { case AND, OR }
	}
	case class BasicLevel(
	  /** Required. A list of requirements for the `AccessLevel` to be granted. */
		conditions: Option[List[Schema.Condition]] = None,
	  /** How the `conditions` list should be combined to determine if a request is granted this `AccessLevel`. If AND is used, each `Condition` in `conditions` must be satisfied for the `AccessLevel` to be applied. If OR is used, at least one `Condition` in `conditions` must be satisfied for the `AccessLevel` to be applied. Default behavior is AND. */
		combiningFunction: Option[Schema.BasicLevel.CombiningFunctionEnum] = None
	)
	
	case class Condition(
	  /** CIDR block IP subnetwork specification. May be IPv4 or IPv6. Note that for a CIDR IP address block, the specified IP address portion must be properly truncated (i.e. all the host bits must be zero) or the input is considered malformed. For example, "192.0.2.0/24" is accepted but "192.0.2.1/24" is not. Similarly, for IPv6, "2001:db8::/32" is accepted whereas "2001:db8::1/32" is not. The originating IP of a request must be in one of the listed subnets in order for this Condition to be true. If empty, all IP addresses are allowed. */
		ipSubnetworks: Option[List[String]] = None,
	  /** Device specific restrictions, all restrictions must hold for the Condition to be true. If not specified, all devices are allowed. */
		devicePolicy: Option[Schema.DevicePolicy] = None,
	  /** A list of other access levels defined in the same `Policy`, referenced by resource name. Referencing an `AccessLevel` which does not exist is an error. All access levels listed must be granted for the Condition to be true. Example: "`accessPolicies/MY_POLICY/accessLevels/LEVEL_NAME"` */
		requiredAccessLevels: Option[List[String]] = None,
	  /** Whether to negate the Condition. If true, the Condition becomes a NAND over its non-empty fields. Any non-empty field criteria evaluating to false will result in the Condition to be satisfied. Defaults to false. */
		negate: Option[Boolean] = None,
	  /** The request must be made by one of the provided user or service accounts. Groups are not supported. Syntax: `user:{emailid}` `serviceAccount:{emailid}` If not specified, a request may come from any user. */
		members: Option[List[String]] = None,
	  /** The request must originate from one of the provided countries/regions. Must be valid ISO 3166-1 alpha-2 codes. */
		regions: Option[List[String]] = None,
	  /** The request must originate from one of the provided VPC networks in Google Cloud. Cannot specify this field together with `ip_subnetworks`. */
		vpcNetworkSources: Option[List[Schema.VpcNetworkSource]] = None
	)
	
	object DevicePolicy {
		enum AllowedEncryptionStatusesEnum extends Enum[AllowedEncryptionStatusesEnum] { case ENCRYPTION_UNSPECIFIED, ENCRYPTION_UNSUPPORTED, UNENCRYPTED, ENCRYPTED }
		enum AllowedDeviceManagementLevelsEnum extends Enum[AllowedDeviceManagementLevelsEnum] { case MANAGEMENT_UNSPECIFIED, NONE, BASIC, COMPLETE }
	}
	case class DevicePolicy(
	  /** Whether or not screenlock is required for the DevicePolicy to be true. Defaults to `false`. */
		requireScreenlock: Option[Boolean] = None,
	  /** Allowed encryptions statuses, an empty list allows all statuses. */
		allowedEncryptionStatuses: Option[List[Schema.DevicePolicy.AllowedEncryptionStatusesEnum]] = None,
	  /** Allowed OS versions, an empty list allows all types and all versions. */
		osConstraints: Option[List[Schema.OsConstraint]] = None,
	  /** Allowed device management levels, an empty list allows all management levels. */
		allowedDeviceManagementLevels: Option[List[Schema.DevicePolicy.AllowedDeviceManagementLevelsEnum]] = None,
	  /** Whether the device needs to be approved by the customer admin. */
		requireAdminApproval: Option[Boolean] = None,
	  /** Whether the device needs to be corp owned. */
		requireCorpOwned: Option[Boolean] = None
	)
	
	object OsConstraint {
		enum OsTypeEnum extends Enum[OsTypeEnum] { case OS_UNSPECIFIED, DESKTOP_MAC, DESKTOP_WINDOWS, DESKTOP_LINUX, DESKTOP_CHROME_OS, ANDROID, IOS }
	}
	case class OsConstraint(
	  /** Required. The allowed OS type. */
		osType: Option[Schema.OsConstraint.OsTypeEnum] = None,
	  /** The minimum allowed OS version. If not set, any version of this OS satisfies the constraint. Format: `"major.minor.patch"`. Examples: `"10.5.301"`, `"9.2.1"`. */
		minimumVersion: Option[String] = None,
	  /** Only allows requests from devices with a verified Chrome OS. Verifications includes requirements that the device is enterprise-managed, conformant to domain policies, and the caller has permission to call the API targeted by the request. */
		requireVerifiedChromeOs: Option[Boolean] = None
	)
	
	case class VpcNetworkSource(
	  /** Sub-segment ranges of a VPC network. */
		vpcSubnetwork: Option[Schema.VpcSubNetwork] = None
	)
	
	case class VpcSubNetwork(
	  /** Required. Network name. If the network is not part of the organization, the `compute.network.get` permission must be granted to the caller. Format: `//compute.googleapis.com/projects/{PROJECT_ID}/global/networks/{NETWORK_NAME}` Example: `//compute.googleapis.com/projects/my-project/global/networks/network-1` */
		network: Option[String] = None,
	  /** CIDR block IP subnetwork specification. The IP address must be an IPv4 address and can be a public or private IP address. Note that for a CIDR IP address block, the specified IP address portion must be properly truncated (i.e. all the host bits must be zero) or the input is considered malformed. For example, "192.0.2.0/24" is accepted but "192.0.2.1/24" is not. If empty, all IP addresses are allowed. */
		vpcIpSubnetworks: Option[List[String]] = None
	)
	
	case class CustomLevel(
	  /** Required. A Cloud CEL expression evaluating to a boolean. */
		expr: Option[Schema.Expr] = None
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
	
	case class ReplaceAccessLevelsRequest(
	  /** Required. The desired Access Levels that should replace all existing Access Levels in the Access Policy. */
		accessLevels: Option[List[Schema.AccessLevel]] = None,
	  /** Optional. The etag for the version of the Access Policy that this replace operation is to be performed on. If, at the time of replace, the etag for the Access Policy stored in Access Context Manager is different from the specified etag, then the replace operation will not be performed and the call will fail. This field is not required. If etag is not provided, the operation will be performed as if a valid etag is provided. */
		etag: Option[String] = None
	)
	
	case class ListServicePerimetersResponse(
	  /** List of the Service Perimeter instances. */
		servicePerimeters: Option[List[Schema.ServicePerimeter]] = None,
	  /** The pagination token to retrieve the next page of results. If the value is empty, no further results remain. */
		nextPageToken: Option[String] = None
	)
	
	object ServicePerimeter {
		enum PerimeterTypeEnum extends Enum[PerimeterTypeEnum] { case PERIMETER_TYPE_REGULAR, PERIMETER_TYPE_BRIDGE }
	}
	case class ServicePerimeter(
	  /** Identifier. Resource name for the `ServicePerimeter`. Format: `accessPolicies/{access_policy}/servicePerimeters/{service_perimeter}`. The `service_perimeter` component must begin with a letter, followed by alphanumeric characters or `_`. After you create a `ServicePerimeter`, you cannot change its `name`. */
		name: Option[String] = None,
	  /** Human readable title. Must be unique within the Policy. */
		title: Option[String] = None,
	  /** Description of the `ServicePerimeter` and its use. Does not affect behavior. */
		description: Option[String] = None,
	  /** Perimeter type indicator. A single project or VPC network is allowed to be a member of single regular perimeter, but multiple service perimeter bridges. A project cannot be a included in a perimeter bridge without being included in regular perimeter. For perimeter bridges, the restricted service list as well as access level lists must be empty. */
		perimeterType: Option[Schema.ServicePerimeter.PerimeterTypeEnum] = None,
	  /** Current ServicePerimeter configuration. Specifies sets of resources, restricted services and access levels that determine perimeter content and boundaries. */
		status: Option[Schema.ServicePerimeterConfig] = None,
	  /** Proposed (or dry run) ServicePerimeter configuration. This configuration allows to specify and test ServicePerimeter configuration without enforcing actual access restrictions. Only allowed to be set when the "use_explicit_dry_run_spec" flag is set. */
		spec: Option[Schema.ServicePerimeterConfig] = None,
	  /** Use explicit dry run spec flag. Ordinarily, a dry-run spec implicitly exists for all Service Perimeters, and that spec is identical to the status for those Service Perimeters. When this flag is set, it inhibits the generation of the implicit spec, thereby allowing the user to explicitly provide a configuration ("spec") to use in a dry-run version of the Service Perimeter. This allows the user to test changes to the enforced config ("status") without actually enforcing them. This testing is done through analyzing the differences between currently enforced and suggested restrictions. use_explicit_dry_run_spec must bet set to True if any of the fields in the spec are set to non-default values. */
		useExplicitDryRunSpec: Option[Boolean] = None,
	  /** Optional. An opaque identifier for the current version of the `ServicePerimeter`. Clients should not expect this to be in any specific format. If etag is not provided, the operation will be performed as if a valid etag is provided. */
		etag: Option[String] = None
	)
	
	case class ServicePerimeterConfig(
	  /** A list of Google Cloud resources that are inside of the service perimeter. Currently only projects and VPCs are allowed. Project format: `projects/{project_number}` VPC network format: `//compute.googleapis.com/projects/{PROJECT_ID}/global/networks/{NAME}`. */
		resources: Option[List[String]] = None,
	  /** A list of `AccessLevel` resource names that allow resources within the `ServicePerimeter` to be accessed from the internet. `AccessLevels` listed must be in the same policy as this `ServicePerimeter`. Referencing a nonexistent `AccessLevel` is a syntax error. If no `AccessLevel` names are listed, resources within the perimeter can only be accessed via Google Cloud calls with request origins within the perimeter. Example: `"accessPolicies/MY_POLICY/accessLevels/MY_LEVEL"`. For Service Perimeter Bridge, must be empty. */
		accessLevels: Option[List[String]] = None,
	  /** Google Cloud services that are subject to the Service Perimeter restrictions. For example, if `storage.googleapis.com` is specified, access to the storage buckets inside the perimeter must meet the perimeter's access restrictions. */
		restrictedServices: Option[List[String]] = None,
	  /** Configuration for APIs allowed within Perimeter. */
		vpcAccessibleServices: Option[Schema.VpcAccessibleServices] = None,
	  /** List of IngressPolicies to apply to the perimeter. A perimeter may have multiple IngressPolicies, each of which is evaluated separately. Access is granted if any Ingress Policy grants it. Must be empty for a perimeter bridge. */
		ingressPolicies: Option[List[Schema.IngressPolicy]] = None,
	  /** List of EgressPolicies to apply to the perimeter. A perimeter may have multiple EgressPolicies, each of which is evaluated separately. Access is granted if any EgressPolicy grants it. Must be empty for a perimeter bridge. */
		egressPolicies: Option[List[Schema.EgressPolicy]] = None
	)
	
	case class VpcAccessibleServices(
	  /** Whether to restrict API calls within the Service Perimeter to the list of APIs specified in 'allowed_services'. */
		enableRestriction: Option[Boolean] = None,
	  /** The list of APIs usable within the Service Perimeter. Must be empty unless 'enable_restriction' is True. You can specify a list of individual services, as well as include the 'RESTRICTED-SERVICES' value, which automatically includes all of the services protected by the perimeter. */
		allowedServices: Option[List[String]] = None
	)
	
	case class IngressPolicy(
	  /** Defines the conditions on the source of a request causing this IngressPolicy to apply. */
		ingressFrom: Option[Schema.IngressFrom] = None,
	  /** Defines the conditions on the ApiOperation and request destination that cause this IngressPolicy to apply. */
		ingressTo: Option[Schema.IngressTo] = None
	)
	
	object IngressFrom {
		enum IdentityTypeEnum extends Enum[IdentityTypeEnum] { case IDENTITY_TYPE_UNSPECIFIED, ANY_IDENTITY, ANY_USER_ACCOUNT, ANY_SERVICE_ACCOUNT }
	}
	case class IngressFrom(
	  /** Sources that this IngressPolicy authorizes access from. */
		sources: Option[List[Schema.IngressSource]] = None,
	  /** A list of identities that are allowed access through [IngressPolicy]. Identities can be an individual user, service account, Google group, or third-party identity. For third-party identity, only single identities are supported and other identity types are not supported. The `v1` identities that have the prefix `user`, `group`, `serviceAccount`, and `principal` in https://cloud.google.com/iam/docs/principal-identifiers#v1 are supported. */
		identities: Option[List[String]] = None,
	  /** Specifies the type of identities that are allowed access from outside the perimeter. If left unspecified, then members of `identities` field will be allowed access. */
		identityType: Option[Schema.IngressFrom.IdentityTypeEnum] = None
	)
	
	case class IngressSource(
	  /** An AccessLevel resource name that allow resources within the ServicePerimeters to be accessed from the internet. AccessLevels listed must be in the same policy as this ServicePerimeter. Referencing a nonexistent AccessLevel will cause an error. If no AccessLevel names are listed, resources within the perimeter can only be accessed via Google Cloud calls with request origins within the perimeter. Example: `accessPolicies/MY_POLICY/accessLevels/MY_LEVEL`. If a single `&#42;` is specified for `access_level`, then all IngressSources will be allowed. */
		accessLevel: Option[String] = None,
	  /** A Google Cloud resource that is allowed to ingress the perimeter. Requests from these resources will be allowed to access perimeter data. Currently only projects and VPCs are allowed. Project format: `projects/{project_number}` VPC network format: `//compute.googleapis.com/projects/{PROJECT_ID}/global/networks/{NAME}`. The project may be in any Google Cloud organization, not just the organization that the perimeter is defined in. `&#42;` is not allowed, the case of allowing all Google Cloud resources only is not supported. */
		resource: Option[String] = None
	)
	
	case class IngressTo(
	  /** A list of ApiOperations allowed to be performed by the sources specified in corresponding IngressFrom in this ServicePerimeter. */
		operations: Option[List[Schema.ApiOperation]] = None,
	  /** A list of resources, currently only projects in the form `projects/`, protected by this ServicePerimeter that are allowed to be accessed by sources defined in the corresponding IngressFrom. If a single `&#42;` is specified, then access to all resources inside the perimeter are allowed. */
		resources: Option[List[String]] = None
	)
	
	case class ApiOperation(
	  /** The name of the API whose methods or permissions the IngressPolicy or EgressPolicy want to allow. A single ApiOperation with `service_name` field set to `&#42;` will allow all methods AND permissions for all services. */
		serviceName: Option[String] = None,
	  /** API methods or permissions to allow. Method or permission must belong to the service specified by `service_name` field. A single MethodSelector entry with `&#42;` specified for the `method` field will allow all methods AND permissions for the service specified in `service_name`. */
		methodSelectors: Option[List[Schema.MethodSelector]] = None
	)
	
	case class MethodSelector(
	  /** A valid method name for the corresponding `service_name` in ApiOperation. If `&#42;` is used as the value for the `method`, then ALL methods and permissions are allowed. */
		method: Option[String] = None,
	  /** A valid Cloud IAM permission for the corresponding `service_name` in ApiOperation. */
		permission: Option[String] = None
	)
	
	case class EgressPolicy(
	  /** Defines conditions on the source of a request causing this EgressPolicy to apply. */
		egressFrom: Option[Schema.EgressFrom] = None,
	  /** Defines the conditions on the ApiOperation and destination resources that cause this EgressPolicy to apply. */
		egressTo: Option[Schema.EgressTo] = None
	)
	
	object EgressFrom {
		enum IdentityTypeEnum extends Enum[IdentityTypeEnum] { case IDENTITY_TYPE_UNSPECIFIED, ANY_IDENTITY, ANY_USER_ACCOUNT, ANY_SERVICE_ACCOUNT }
		enum SourceRestrictionEnum extends Enum[SourceRestrictionEnum] { case SOURCE_RESTRICTION_UNSPECIFIED, SOURCE_RESTRICTION_ENABLED, SOURCE_RESTRICTION_DISABLED }
	}
	case class EgressFrom(
	  /** A list of identities that are allowed access through [EgressPolicy]. Identities can be an individual user, service account, Google group, or third-party identity. For third-party identity, only single identities are supported and other identity types are not supported. The `v1` identities that have the prefix `user`, `group`, `serviceAccount`, and `principal` in https://cloud.google.com/iam/docs/principal-identifiers#v1 are supported. */
		identities: Option[List[String]] = None,
	  /** Specifies the type of identities that are allowed access to outside the perimeter. If left unspecified, then members of `identities` field will be allowed access. */
		identityType: Option[Schema.EgressFrom.IdentityTypeEnum] = None,
	  /** Sources that this EgressPolicy authorizes access from. If this field is not empty, then `source_restriction` must be set to `SOURCE_RESTRICTION_ENABLED`. */
		sources: Option[List[Schema.EgressSource]] = None,
	  /** Whether to enforce traffic restrictions based on `sources` field. If the `sources` fields is non-empty, then this field must be set to `SOURCE_RESTRICTION_ENABLED`. */
		sourceRestriction: Option[Schema.EgressFrom.SourceRestrictionEnum] = None
	)
	
	case class EgressSource(
	  /** An AccessLevel resource name that allows protected resources inside the ServicePerimeters to access outside the ServicePerimeter boundaries. AccessLevels listed must be in the same policy as this ServicePerimeter. Referencing a nonexistent AccessLevel will cause an error. If an AccessLevel name is not specified, only resources within the perimeter can be accessed through Google Cloud calls with request origins within the perimeter. Example: `accessPolicies/MY_POLICY/accessLevels/MY_LEVEL`. If a single `&#42;` is specified for `access_level`, then all EgressSources will be allowed. */
		accessLevel: Option[String] = None
	)
	
	case class EgressTo(
	  /** A list of resources, currently only projects in the form `projects/`, that are allowed to be accessed by sources defined in the corresponding EgressFrom. A request matches if it contains a resource in this list. If `&#42;` is specified for `resources`, then this EgressTo rule will authorize access to all resources outside the perimeter. */
		resources: Option[List[String]] = None,
	  /** A list of ApiOperations allowed to be performed by the sources specified in the corresponding EgressFrom. A request matches if it uses an operation/service in this list. */
		operations: Option[List[Schema.ApiOperation]] = None,
	  /** A list of external resources that are allowed to be accessed. Only AWS and Azure resources are supported. For Amazon S3, the supported formats are s3://BUCKET_NAME, s3a://BUCKET_NAME, and s3n://BUCKET_NAME. For Azure Storage, the supported format is azure://myaccount.blob.core.windows.net/CONTAINER_NAME. A request matches if it contains an external resource in this list (Example: s3://bucket/path). Currently '&#42;' is not allowed. */
		externalResources: Option[List[String]] = None
	)
	
	case class ReplaceServicePerimetersRequest(
	  /** Required. The desired Service Perimeters that should replace all existing Service Perimeters in the Access Policy. */
		servicePerimeters: Option[List[Schema.ServicePerimeter]] = None,
	  /** Optional. The etag for the version of the Access Policy that this replace operation is to be performed on. If, at the time of replace, the etag for the Access Policy stored in Access Context Manager is different from the specified etag, then the replace operation will not be performed and the call will fail. This field is not required. If etag is not provided, the operation will be performed as if a valid etag is provided. */
		etag: Option[String] = None
	)
	
	case class CommitServicePerimetersRequest(
	  /** Optional. The etag for the version of the Access Policy that this commit operation is to be performed on. If, at the time of commit, the etag for the Access Policy stored in Access Context Manager is different from the specified etag, then the commit operation will not be performed and the call will fail. This field is not required. If etag is not provided, the operation will be performed as if a valid etag is provided. */
		etag: Option[String] = None
	)
	
	case class ListSupportedServicesResponse(
	  /** List of services supported by VPC Service Controls instances. */
		supportedServices: Option[List[Schema.SupportedService]] = None,
	  /** The pagination token to retrieve the next page of results. If the value is empty, no further results remain. */
		nextPageToken: Option[String] = None
	)
	
	object SupportedService {
		enum SupportStageEnum extends Enum[SupportStageEnum] { case LAUNCH_STAGE_UNSPECIFIED, UNIMPLEMENTED, PRELAUNCH, EARLY_ACCESS, ALPHA, BETA, GA, DEPRECATED }
		enum ServiceSupportStageEnum extends Enum[ServiceSupportStageEnum] { case SERVICE_SUPPORT_STAGE_UNSPECIFIED, GA, PREVIEW, DEPRECATED }
	}
	case class SupportedService(
	  /** The service name or address of the supported service, such as `service.googleapis.com`. */
		name: Option[String] = None,
	  /** The support stage of the service. */
		supportStage: Option[Schema.SupportedService.SupportStageEnum] = None,
	  /** True if the service is available on the restricted VIP. Services on the restricted VIP typically either support VPC Service Controls or are core infrastructure services required for the functioning of Google Cloud. */
		availableOnRestrictedVip: Option[Boolean] = None,
	  /** The name of the supported product, such as 'Cloud Product API'. */
		title: Option[String] = None,
	  /** The list of the supported methods. This field exists only in response to GetSupportedService */
		supportedMethods: Option[List[Schema.MethodSelector]] = None,
	  /** True if the service is supported with some limitations. Check [documentation](https://cloud.google.com/vpc-service-controls/docs/supported-products) for details. */
		knownLimitations: Option[Boolean] = None,
	  /** The support stage of the service. */
		serviceSupportStage: Option[Schema.SupportedService.ServiceSupportStageEnum] = None
	)
	
	case class ListGcpUserAccessBindingsResponse(
	  /** GcpUserAccessBinding */
		gcpUserAccessBindings: Option[List[Schema.GcpUserAccessBinding]] = None,
	  /** Token to get the next page of items. If blank, there are no more items. */
		nextPageToken: Option[String] = None
	)
	
	case class GcpUserAccessBinding(
	  /** Immutable. Assigned by the server during creation. The last segment has an arbitrary length and has only URI unreserved characters (as defined by [RFC 3986 Section 2.3](https://tools.ietf.org/html/rfc3986#section-2.3)). Should not be specified by the client during creation. Example: "organizations/256/gcpUserAccessBindings/b3-BhcX_Ud5N" */
		name: Option[String] = None,
	  /** Required. Immutable. Google Group id whose members are subject to this binding's restrictions. See "id" in the [G Suite Directory API's Groups resource] (https://developers.google.com/admin-sdk/directory/v1/reference/groups#resource). If a group's email address/alias is changed, this resource will continue to point at the changed group. This field does not accept group email addresses or aliases. Example: "01d520gv4vjcrht" */
		groupKey: Option[String] = None,
	  /** Optional. Access level that a user must have to be granted access. Only one access level is supported, not multiple. This repeated field must have exactly one element. Example: "accessPolicies/9522/accessLevels/device_trusted" */
		accessLevels: Option[List[String]] = None,
	  /** Optional. Dry run access level that will be evaluated but will not be enforced. The access denial based on dry run policy will be logged. Only one access level is supported, not multiple. This list must have exactly one element. Example: "accessPolicies/9522/accessLevels/device_trusted" */
		dryRunAccessLevels: Option[List[String]] = None,
	  /** Optional. GCSL policy for the group key. */
		sessionSettings: Option[Schema.SessionSettings] = None,
	  /** Optional. A list of applications that are subject to this binding's restrictions. If the list is empty, the binding restrictions will universally apply to all applications. */
		restrictedClientApplications: Option[List[Schema.Application]] = None,
	  /** Optional. A list of scoped access settings that set this binding's restrictions on a subset of applications. This field cannot be set if restricted_client_applications is set. */
		scopedAccessSettings: Option[List[Schema.ScopedAccessSettings]] = None
	)
	
	object SessionSettings {
		enum SessionReauthMethodEnum extends Enum[SessionReauthMethodEnum] { case SESSION_REAUTH_METHOD_UNSPECIFIED, LOGIN, SECURITY_KEY, PASSWORD }
	}
	case class SessionSettings(
	  /** Optional. Session method when users GCP session is up. */
		sessionReauthMethod: Option[Schema.SessionSettings.SessionReauthMethodEnum] = None,
	  /** Optional. The session length. Setting this field to zero is equal to disabling. Session. Also can set infinite session by flipping the enabled bit to false below. If use_oidc_max_age is true, for OIDC apps, the session length will be the minimum of this field and OIDC max_age param. */
		sessionLength: Option[String] = None,
	  /** Optional. How long a user is allowed to take between actions before a new access token must be issued. Presently only set for Cloud Apps. */
		maxInactivity: Option[String] = None,
	  /** Optional. Only useful for OIDC apps. When false, the OIDC max_age param, if passed in the authentication request will be ignored. When true, the re-auth period will be the minimum of the session_length field and the max_age OIDC param. */
		useOidcMaxAge: Option[Boolean] = None,
	  /** Optional. Big red button to turn off GCSL. When false, all fields set above will be disregarded and the session length is basically infinite. */
		sessionLengthEnabled: Option[Boolean] = None
	)
	
	case class Application(
	  /** The OAuth client ID of the application. */
		clientId: Option[String] = None,
	  /** The name of the application. Example: "Cloud Console" */
		name: Option[String] = None
	)
	
	case class ScopedAccessSettings(
	  /** Optional. Application, etc. to which the access settings will be applied to. Implicitly, this is the scoped access settings key; as such, it must be unique and non-empty. */
		scope: Option[Schema.AccessScope] = None,
	  /** Optional. Access settings for this scoped access settings. This field may be empty if dry_run_settings is set. */
		activeSettings: Option[Schema.AccessSettings] = None,
	  /** Optional. Dry-run access settings for this scoped access settings. This field may be empty if active_settings is set. */
		dryRunSettings: Option[Schema.AccessSettings] = None
	)
	
	case class AccessScope(
	  /** Optional. Client scope for this access scope. */
		clientScope: Option[Schema.ClientScope] = None
	)
	
	case class ClientScope(
	  /** Optional. The application that is subject to this binding's scope. */
		restrictedClientApplication: Option[Schema.Application] = None
	)
	
	case class AccessSettings(
	  /** Optional. Access level that a user must have to be granted access. Only one access level is supported, not multiple. This repeated field must have exactly one element. Example: "accessPolicies/9522/accessLevels/device_trusted" */
		accessLevels: Option[List[String]] = None,
	  /** Optional. Session settings applied to user access on a given AccessScope. */
		sessionSettings: Option[Schema.SessionSettings] = None
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
	
	case class GetIamPolicyRequest(
	  /** OPTIONAL: A `GetPolicyOptions` object for specifying options to `GetIamPolicy`. */
		options: Option[Schema.GetPolicyOptions] = None
	)
	
	case class GetPolicyOptions(
	  /** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		requestedPolicyVersion: Option[Int] = None
	)
	
	case class TestIamPermissionsRequest(
	  /** The set of permissions to check for the `resource`. Permissions with wildcards (such as `&#42;` or `storage.&#42;`) are not allowed. For more information see [IAM Overview](https://cloud.google.com/iam/docs/overview#permissions). */
		permissions: Option[List[String]] = None
	)
	
	case class TestIamPermissionsResponse(
	  /** A subset of `TestPermissionsRequest.permissions` that the caller is allowed. */
		permissions: Option[List[String]] = None
	)
	
	case class ListAuthorizedOrgsDescsResponse(
	  /** List of all the Authorized Orgs Desc instances. */
		authorizedOrgsDescs: Option[List[Schema.AuthorizedOrgsDesc]] = None,
	  /** The pagination token to retrieve the next page of results. If the value is empty, no further results remain. */
		nextPageToken: Option[String] = None
	)
	
	object AuthorizedOrgsDesc {
		enum AuthorizationTypeEnum extends Enum[AuthorizationTypeEnum] { case AUTHORIZATION_TYPE_UNSPECIFIED, AUTHORIZATION_TYPE_TRUST }
		enum AssetTypeEnum extends Enum[AssetTypeEnum] { case ASSET_TYPE_UNSPECIFIED, ASSET_TYPE_DEVICE, ASSET_TYPE_CREDENTIAL_STRENGTH }
		enum AuthorizationDirectionEnum extends Enum[AuthorizationDirectionEnum] { case AUTHORIZATION_DIRECTION_UNSPECIFIED, AUTHORIZATION_DIRECTION_TO, AUTHORIZATION_DIRECTION_FROM }
	}
	case class AuthorizedOrgsDesc(
	  /** Identifier. Resource name for the `AuthorizedOrgsDesc`. Format: `accessPolicies/{access_policy}/authorizedOrgsDescs/{authorized_orgs_desc}`. The `authorized_orgs_desc` component must begin with a letter, followed by alphanumeric characters or `_`. After you create an `AuthorizedOrgsDesc`, you cannot change its `name`. */
		name: Option[String] = None,
	  /** A granular control type for authorization levels. Valid value is `AUTHORIZATION_TYPE_TRUST`. */
		authorizationType: Option[Schema.AuthorizedOrgsDesc.AuthorizationTypeEnum] = None,
	  /** The asset type of this authorized orgs desc. Valid values are `ASSET_TYPE_DEVICE`, and `ASSET_TYPE_CREDENTIAL_STRENGTH`. */
		assetType: Option[Schema.AuthorizedOrgsDesc.AssetTypeEnum] = None,
	  /** The direction of the authorization relationship between this organization and the organizations listed in the `orgs` field. The valid values for this field include the following: `AUTHORIZATION_DIRECTION_FROM`: Allows this organization to evaluate traffic in the organizations listed in the `orgs` field. `AUTHORIZATION_DIRECTION_TO`: Allows the organizations listed in the `orgs` field to evaluate the traffic in this organization. For the authorization relationship to take effect, all of the organizations must authorize and specify the appropriate relationship direction. For example, if organization A authorized organization B and C to evaluate its traffic, by specifying `AUTHORIZATION_DIRECTION_TO` as the authorization direction, organizations B and C must specify `AUTHORIZATION_DIRECTION_FROM` as the authorization direction in their `AuthorizedOrgsDesc` resource. */
		authorizationDirection: Option[Schema.AuthorizedOrgsDesc.AuthorizationDirectionEnum] = None,
	  /** The list of organization ids in this AuthorizedOrgsDesc. Format: `organizations/` Example: `organizations/123456` */
		orgs: Option[List[String]] = None
	)
	
	case class ReplaceAccessLevelsResponse(
	  /** List of the Access Level instances. */
		accessLevels: Option[List[Schema.AccessLevel]] = None
	)
	
	case class ReplaceServicePerimetersResponse(
	  /** List of the Service Perimeter instances. */
		servicePerimeters: Option[List[Schema.ServicePerimeter]] = None
	)
	
	case class CommitServicePerimetersResponse(
	  /** List of all the Service Perimeter instances in the Access Policy. */
		servicePerimeters: Option[List[Schema.ServicePerimeter]] = None
	)
	
	case class GcpUserAccessBindingOperationMetadata(
	
	)
	
	case class AccessContextManagerOperationMetadata(
	
	)
}
