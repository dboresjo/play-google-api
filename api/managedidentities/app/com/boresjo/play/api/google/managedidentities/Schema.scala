package com.boresjo.play.api.google.managedidentities

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

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
	
	object Domain {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, READY, UPDATING, DELETING, REPAIRING, PERFORMING_MAINTENANCE, UNAVAILABLE }
	}
	case class Domain(
	  /** Required. The unique name of the domain using the form: `projects/{project_id}/locations/global/domains/{domain_name}`. */
		name: Option[String] = None,
	  /** Optional. Resource labels that can contain user-provided metadata. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. The full names of the Google Compute Engine [networks](/compute/docs/networks-and-firewalls#networks) the domain instance is connected to. Networks can be added using UpdateDomain. The domain is only available on networks listed in `authorized_networks`. If CIDR subnets overlap between networks, domain creation will fail. */
		authorizedNetworks: Option[List[String]] = None,
	  /** Required. The CIDR range of internal addresses that are reserved for this domain. Reserved networks must be /24 or larger. Ranges must be unique and non-overlapping with existing subnets in [Domain].[authorized_networks]. */
		reservedIpRange: Option[String] = None,
	  /** Required. Locations where domain needs to be provisioned. The locations can be specified according to https://cloud.google.com/compute/docs/regions-zones, such as `us-west1` or `us-east4`. Each domain supports up to 4 locations, separated by commas. Each location will use a /26 block. */
		locations: Option[List[String]] = None,
	  /** Optional. The name of delegated administrator account used to perform Active Directory operations. If not specified, `setupadmin` will be used. */
		admin: Option[String] = None,
	  /** Output only. The fully-qualified domain name of the exposed domain used by clients to connect to the service. Similar to what would be chosen for an Active Directory set up on an internal network. */
		fqdn: Option[String] = None,
	  /** Output only. The time the instance was created. */
		createTime: Option[String] = None,
	  /** Output only. The last update time. */
		updateTime: Option[String] = None,
	  /** Output only. The current state of this domain. */
		state: Option[Schema.Domain.StateEnum] = None,
	  /** Output only. Additional information about the current status of this domain, if available. */
		statusMessage: Option[String] = None,
	  /** Output only. The current trusts associated with the domain. */
		trusts: Option[List[Schema.Trust]] = None,
	  /** Optional. Configuration for audit logs. True if audit logs are enabled, else false. Default is audit logs disabled. */
		auditLogsEnabled: Option[Boolean] = None
	)
	
	object Trust {
		enum TrustTypeEnum extends Enum[TrustTypeEnum] { case TRUST_TYPE_UNSPECIFIED, FOREST, EXTERNAL }
		enum TrustDirectionEnum extends Enum[TrustDirectionEnum] { case TRUST_DIRECTION_UNSPECIFIED, INBOUND, OUTBOUND, BIDIRECTIONAL }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, UPDATING, DELETING, CONNECTED, DISCONNECTED }
	}
	case class Trust(
	  /** Required. The fully qualified target domain name which will be in trust with the current domain. */
		targetDomainName: Option[String] = None,
	  /** Required. The type of trust represented by the trust resource. */
		trustType: Option[Schema.Trust.TrustTypeEnum] = None,
	  /** Required. The trust direction, which decides if the current domain is trusted, trusting, or both. */
		trustDirection: Option[Schema.Trust.TrustDirectionEnum] = None,
	  /** Optional. The trust authentication type, which decides whether the trusted side has forest/domain wide access or selective access to an approved set of resources. */
		selectiveAuthentication: Option[Boolean] = None,
	  /** Required. The target DNS server IP addresses which can resolve the remote domain involved in the trust. */
		targetDnsIpAddresses: Option[List[String]] = None,
	  /** Required. The trust secret used for the handshake with the target domain. This will not be stored. */
		trustHandshakeSecret: Option[String] = None,
	  /** Output only. The time the instance was created. */
		createTime: Option[String] = None,
	  /** Output only. The last update time. */
		updateTime: Option[String] = None,
	  /** Output only. The current state of the trust. */
		state: Option[Schema.Trust.StateEnum] = None,
	  /** Output only. Additional information about the current state of the trust, if available. */
		stateDescription: Option[String] = None,
	  /** Output only. The last heartbeat time when the trust was known to be connected. */
		lastTrustHeartbeatTime: Option[String] = None
	)
	
	case class ResetAdminPasswordRequest(
	
	)
	
	case class ResetAdminPasswordResponse(
	  /** A random password. See admin for more information. */
		password: Option[String] = None
	)
	
	case class ListDomainsResponse(
	  /** A list of Managed Identities Service domains in the project. */
		domains: Option[List[Schema.Domain]] = None,
	  /** A token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None,
	  /** A list of locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class DomainJoinMachineRequest(
	  /** Required. Full instance id token of compute engine VM to verify instance identity. More about this: https://cloud.google.com/compute/docs/instances/verifying-instance-identity#request_signature */
		vmIdToken: Option[String] = None,
	  /** Optional. OU name where the VM needs to be domain joined */
		ouName: Option[String] = None,
	  /** Optional. force if True, forces domain join even if the computer account already exists. */
		force: Option[Boolean] = None
	)
	
	case class DomainJoinMachineResponse(
	  /** Offline domain join blob as the response */
		domainJoinBlob: Option[String] = None
	)
	
	case class RestoreDomainRequest(
	  /** Required. ID of the backup to be restored */
		backupId: Option[String] = None
	)
	
	case class AttachTrustRequest(
	  /** Required. The domain trust resource. */
		trust: Option[Schema.Trust] = None
	)
	
	case class ReconfigureTrustRequest(
	  /** Required. The fully-qualified target domain name which will be in trust with current domain. */
		targetDomainName: Option[String] = None,
	  /** Required. The target DNS server IP addresses to resolve the remote domain involved in the trust. */
		targetDnsIpAddresses: Option[List[String]] = None
	)
	
	case class DetachTrustRequest(
	  /** Required. The domain trust resource to removed. */
		trust: Option[Schema.Trust] = None
	)
	
	case class ValidateTrustRequest(
	  /** Required. The domain trust to validate trust state for. */
		trust: Option[Schema.Trust] = None
	)
	
	object LDAPSSettings {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, UPDATING, ACTIVE, FAILED }
	}
	case class LDAPSSettings(
	  /** The resource name of the LDAPS settings. Uses the form: `projects/{project}/locations/{location}/domains/{domain}`. */
		name: Option[String] = None,
	  /** Output only. The certificate used to configure LDAPS. Certificates can be chained with a maximum length of 15. */
		certificate: Option[Schema.Certificate] = None,
	  /** Output only. The current state of this LDAPS settings. */
		state: Option[Schema.LDAPSSettings.StateEnum] = None,
	  /** Input only. The uploaded PKCS12-formatted certificate to configure LDAPS with. It will enable the domain controllers in this domain to accept LDAPS connections (either LDAP over SSL/TLS or the StartTLS operation). A valid certificate chain must form a valid x.509 certificate chain (or be comprised of a single self-signed certificate. It must be encrypted with either: 1) PBES2 + PBKDF2 + AES256 encryption and SHA256 PRF; or 2) pbeWithSHA1And3-KeyTripleDES-CBC Private key must be included for the leaf / single self-signed certificate. Note: For a fqdn your-example-domain.com, the wildcard fqdn is &#42;.your-example-domain.com. Specifically the leaf certificate must have: - Either a blank subject or a subject with CN matching the wildcard fqdn. - Exactly two SANs - the fqdn and wildcard fqdn. - Encipherment and digital key signature key usages. - Server authentication extended key usage (OID=1.3.6.1.5.5.7.3.1) - Private key must be in one of the following formats: RSA, ECDSA, ED25519. - Private key must have appropriate key length: 2048 for RSA, 256 for ECDSA - Signature algorithm of the leaf certificate cannot be MD2, MD5 or SHA1. */
		certificatePfx: Option[String] = None,
	  /** Input only. The password used to encrypt the uploaded PFX certificate. */
		certificatePassword: Option[String] = None,
	  /** Output only. Last update time. */
		updateTime: Option[String] = None
	)
	
	case class Certificate(
	  /** The certificate subject. */
		subject: Option[String] = None,
	  /** The certificate thumbprint which uniquely identifies the certificate. */
		thumbprint: Option[String] = None,
	  /** The additional hostnames for the domain. */
		subjectAlternativeName: Option[List[String]] = None,
	  /** The issuer of this certificate. */
		issuingCertificate: Option[Schema.Certificate] = None,
	  /** The certificate expire time. */
		expireTime: Option[String] = None
	)
	
	object Peering {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, CONNECTED, DISCONNECTED, DELETING }
	}
	case class Peering(
	  /** Output only. Unique name of the peering in this scope including projects and location using the form: `projects/{project_id}/locations/global/peerings/{peering_id}`. */
		name: Option[String] = None,
	  /** Optional. Resource labels to represent user-provided metadata. */
		labels: Option[Map[String, String]] = None,
	  /** Required. The full names of the Google Compute Engine [networks](/compute/docs/networks-and-firewalls#networks) to which the instance is connected. Caller needs to make sure that CIDR subnets do not overlap between networks, else peering creation will fail. */
		authorizedNetwork: Option[String] = None,
	  /** Required. Full domain resource path for the Managed AD Domain involved in peering. The resource path should be in the form: `projects/{project_id}/locations/global/domains/{domain_name}` */
		domainResource: Option[String] = None,
	  /** Output only. The time the instance was created. */
		createTime: Option[String] = None,
	  /** Output only. Last update time. */
		updateTime: Option[String] = None,
	  /** Output only. The current state of this Peering. */
		state: Option[Schema.Peering.StateEnum] = None,
	  /** Output only. Additional information about the current status of this peering, if available. */
		statusMessage: Option[String] = None
	)
	
	case class ListPeeringsResponse(
	  /** A list of Managed Identities Service Peerings in the project. */
		peerings: Option[List[Schema.Peering]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Backup {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, ON_DEMAND, SCHEDULED }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, ACTIVE, FAILED, DELETING }
	}
	case class Backup(
	  /** Output only. The unique name of the Backup in the form of `projects/{project_id}/locations/global/domains/{domain_name}/backups/{name}` */
		name: Option[String] = None,
	  /** Optional. Resource labels to represent user provided metadata. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. The time the backups was created. */
		createTime: Option[String] = None,
	  /** Output only. Last update time. */
		updateTime: Option[String] = None,
	  /** Output only. Indicates whether it’s an on-demand backup or scheduled. */
		`type`: Option[Schema.Backup.TypeEnum] = None,
	  /** Output only. The current state of the backup. */
		state: Option[Schema.Backup.StateEnum] = None,
	  /** Output only. Additional information about the current status of this backup, if available. */
		statusMessage: Option[String] = None
	)
	
	case class ListBackupsResponse(
	  /** A list of Cloud AD backups in the domain. */
		backups: Option[List[Schema.Backup]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class ListSqlIntegrationsResponse(
	  /** A list of SQLIntegrations of a domain. */
		sqlIntegrations: Option[List[Schema.SqlIntegration]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None,
	  /** A list of locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object SqlIntegration {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, DELETING, READY }
	}
	case class SqlIntegration(
	  /** The unique name of the SQL integration in the form of `projects/{project_id}/locations/global/domains/{domain_name}/sqlIntegrations/{sql_integration}` */
		name: Option[String] = None,
	  /** The full resource name of an integrated SQL instance */
		sqlInstance: Option[String] = None,
	  /** Output only. The time the SQL integration was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the SQL integration was updated. */
		updateTime: Option[String] = None,
	  /** Output only. The current state of the SQL integration. */
		state: Option[Schema.SqlIntegration.StateEnum] = None
	)
	
	case class ExtendSchemaRequest(
	  /** Required. Description for Schema Change. */
		description: Option[String] = None,
	  /** File stored in Cloud Storage bucket and represented in the form projects/{project_id}/buckets/{bucket_name}/objects/{object_name} File should be in the same project as the domain. */
		gcsPath: Option[String] = None,
	  /** File uploaded as a byte stream input. */
		fileContents: Option[String] = None
	)
	
	case class EnableMigrationRequest(
	  /** Required. List of the on-prem domains to be migrated. */
		migratingDomains: Option[List[Schema.OnPremDomainDetails]] = None
	)
	
	case class OnPremDomainDetails(
	  /** Required. FQDN of the on-prem domain being migrated. */
		domainName: Option[String] = None,
	  /** Optional. Option to disable SID filtering. */
		disableSidFiltering: Option[Boolean] = None
	)
	
	case class DisableMigrationRequest(
	
	)
	
	case class CheckMigrationPermissionRequest(
	
	)
	
	object CheckMigrationPermissionResponse {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, DISABLED, ENABLED, NEEDS_MAINTENANCE }
	}
	case class CheckMigrationPermissionResponse(
	  /** The state of DomainMigration. */
		state: Option[Schema.CheckMigrationPermissionResponse.StateEnum] = None,
	  /** The state of SID filtering of all the domains which has trust established. */
		onpremDomains: Option[List[Schema.OnPremDomainSIDDetails]] = None
	)
	
	object OnPremDomainSIDDetails {
		enum SidFilteringStateEnum extends Enum[SidFilteringStateEnum] { case SID_FILTERING_STATE_UNSPECIFIED, ENABLED, DISABLED }
	}
	case class OnPremDomainSIDDetails(
	  /** FQDN of the on-prem domain being migrated. */
		name: Option[String] = None,
	  /** Current SID filtering state. */
		sidFilteringState: Option[Schema.OnPremDomainSIDDetails.SidFilteringStateEnum] = None
	)
	
	case class SetIamPolicyRequest(
	  /** REQUIRED: The complete policy to be applied to the `resource`. The size of the policy is limited to a few 10s of KB. An empty policy is a valid policy but certain Google Cloud services (such as Projects) might reject them. */
		policy: Option[Schema.Policy] = None
	)
	
	case class Policy(
	  /** Specifies the format of the policy. Valid values are `0`, `1`, and `3`. Requests that specify an invalid value are rejected. Any operation that affects conditional role bindings must specify version `3`. This requirement applies to the following operations: &#42; Getting a policy that includes a conditional role binding &#42; Adding a conditional role binding to a policy &#42; Changing a conditional role binding in a policy &#42; Removing any role binding, with or without a condition, from a policy that includes conditions &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. If a policy does not include any conditions, operations on that policy may specify any valid version or leave the field unset. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		version: Option[Int] = None,
	  /** Associates a list of `members`, or principals, with a `role`. Optionally, may specify a `condition` that determines how and when the `bindings` are applied. Each of the `bindings` must contain at least one principal. The `bindings` in a `Policy` can refer to up to 1,500 principals; up to 250 of these principals can be Google groups. Each occurrence of a principal counts towards these limits. For example, if the `bindings` grant 50 different roles to `user:alice@example.com`, and not to any other principal, then you can add another 1,450 principals to the `bindings` in the `Policy`. */
		bindings: Option[List[Schema.Binding]] = None,
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
		statusDetail: Option[String] = None,
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have been cancelled successfully have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		cancelRequested: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None
	)
	
	case class GoogleCloudManagedidentitiesV1OpMetadata(
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Output only. Server-defined resource path for the target of the operation. */
		target: Option[String] = None,
	  /** Output only. Name of the verb executed by the operation. */
		verb: Option[String] = None,
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have successfully been cancelled have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		requestedCancellation: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None
	)
	
	case class GoogleCloudManagedidentitiesV1alpha1OpMetadata(
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Output only. Server-defined resource path for the target of the operation. */
		target: Option[String] = None,
	  /** Output only. Name of the verb executed by the operation. */
		verb: Option[String] = None,
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have successfully been cancelled have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		requestedCancellation: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None
	)
	
	case class GoogleCloudManagedidentitiesV1beta1OpMetadata(
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Output only. Server-defined resource path for the target of the operation. */
		target: Option[String] = None,
	  /** Output only. Name of the verb executed by the operation. */
		verb: Option[String] = None,
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have successfully been cancelled have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		requestedCancellation: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None
	)
	
	object GoogleCloudSaasacceleratorManagementProvidersV1Instance {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, READY, UPDATING, REPAIRING, DELETING, ERROR }
	}
	case class GoogleCloudSaasacceleratorManagementProvidersV1Instance(
	  /** Unique name of the resource. It uses the form: `projects/{project_number}/locations/{location_id}/instances/{instance_id}` Note: This name is passed, stored and logged across the rollout system. So use of consumer project_id or any other consumer PII in the name is strongly discouraged for wipeout (go/wipeout) compliance. See go/elysium/project_ids#storage-guidance for more details. */
		name: Option[String] = None,
	  /** Output only. Timestamp when the resource was created. */
		createTime: Option[String] = None,
	  /** Output only. Timestamp when the resource was last modified. */
		updateTime: Option[String] = None,
	  /** Optional. Resource labels to represent user provided metadata. Each label is a key-value pair, where both the key and the value are arbitrary strings provided by the user. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. Current lifecycle state of the resource (e.g. if it's being created or ready to use). */
		state: Option[Schema.GoogleCloudSaasacceleratorManagementProvidersV1Instance.StateEnum] = None,
	  /** Software versions that are used to deploy this instance. This can be mutated by rollout services. */
		softwareVersions: Option[Map[String, String]] = None,
	  /** Optional. The MaintenancePolicies that have been attached to the instance. The key must be of the type name of the oneof policy name defined in MaintenancePolicy, and the referenced policy must define the same policy type. For details, please refer to go/mr-user-guide. Should not be set if maintenance_settings.maintenance_policies is set. */
		maintenancePolicyNames: Option[Map[String, String]] = None,
	  /** Output only. ID of the associated GCP tenant project. See go/get-instance-metadata. */
		tenantProjectId: Option[String] = None,
	  /** Output only. Custom string attributes used primarily to expose producer-specific information in monitoring dashboards. See go/get-instance-metadata. */
		producerMetadata: Option[Map[String, String]] = None,
	  /** Output only. The list of data plane resources provisioned for this instance, e.g. compute VMs. See go/get-instance-metadata. */
		provisionedResources: Option[List[Schema.GoogleCloudSaasacceleratorManagementProvidersV1ProvisionedResource]] = None,
	  /** Output only. SLO metadata for instance classification in the Standardized dataplane SLO platform. See go/cloud-ssa-standard-slo for feature description. */
		sloMetadata: Option[Schema.GoogleCloudSaasacceleratorManagementProvidersV1SloMetadata] = None,
	  /** The MaintenanceSchedule contains the scheduling information of published maintenance schedule with same key as software_versions. */
		maintenanceSchedules: Option[Map[String, Schema.GoogleCloudSaasacceleratorManagementProvidersV1MaintenanceSchedule]] = None,
	  /** consumer_defined_name is the name of the instance set by the service consumers. Generally this is different from the `name` field which reperesents the system-assigned id of the instance which the service consumers do not recognize. This is a required field for tenants onboarding to Maintenance Window notifications (go/slm-rollout-maintenance-policies#prerequisites). */
		consumerDefinedName: Option[String] = None,
	  /** Link to the SLM instance template. Only populated when updating SLM instances via SSA's Actuation service adaptor. Service producers with custom control plane (e.g. Cloud SQL) doesn't need to populate this field. Instead they should use software_versions. */
		slmInstanceTemplate: Option[String] = None,
	  /** Optional. The MaintenanceSettings associated with instance. */
		maintenanceSettings: Option[Schema.GoogleCloudSaasacceleratorManagementProvidersV1MaintenanceSettings] = None,
	  /** Optional. The instance_type of this instance of format: projects/{project_number}/locations/{location_id}/instanceTypes/{instance_type_id}. Instance Type represents a high-level tier or SKU of the service that this instance belong to. When enabled(eg: Maintenance Rollout), Rollout uses 'instance_type' along with 'software_versions' to determine whether instance needs an update or not. */
		instanceType: Option[String] = None,
	  /** Optional. notification_parameter are information that service producers may like to include that is not relevant to Rollout. This parameter will only be passed to Gamma and Cloud Logging for notification/logging purpose. */
		notificationParameters: Option[Map[String, Schema.GoogleCloudSaasacceleratorManagementProvidersV1NotificationParameter]] = None
	)
	
	case class GoogleCloudSaasacceleratorManagementProvidersV1ProvisionedResource(
	  /** Type of the resource. This can be either a GCP resource or a custom one (e.g. another cloud provider's VM). For GCP compute resources use singular form of the names listed in GCP compute API documentation (https://cloud.google.com/compute/docs/reference/rest/v1/), prefixed with 'compute-', for example: 'compute-instance', 'compute-disk', 'compute-autoscaler'. */
		resourceType: Option[String] = None,
	  /** URL identifying the resource, e.g. "https://www.googleapis.com/compute/v1/projects/...)". */
		resourceUrl: Option[String] = None
	)
	
	case class GoogleCloudSaasacceleratorManagementProvidersV1SloMetadata(
	  /** Name of the SLO tier the Instance belongs to. This name will be expected to match the tiers specified in the service SLO configuration. Field is mandatory and must not be empty. */
		tier: Option[String] = None,
	  /** Optional. List of nodes. Some producers need to use per-node metadata to calculate SLO. This field allows such producers to publish per-node SLO meta data, which will be consumed by SSA Eligibility Exporter and published in the form of per node metric to Monarch. */
		nodes: Option[List[Schema.GoogleCloudSaasacceleratorManagementProvidersV1NodeSloMetadata]] = None,
	  /** Optional. Multiple per-instance SLI eligibilities which apply for individual SLIs. */
		perSliEligibility: Option[Schema.GoogleCloudSaasacceleratorManagementProvidersV1PerSliSloEligibility] = None
	)
	
	case class GoogleCloudSaasacceleratorManagementProvidersV1NodeSloMetadata(
	  /** The id of the node. This should be equal to SaasInstanceNode.node_id. */
		nodeId: Option[String] = None,
	  /** The location of the node, if different from instance location. */
		location: Option[String] = None,
	  /** If present, this will override eligibility for the node coming from instance or exclusions for specified SLIs. */
		perSliEligibility: Option[Schema.GoogleCloudSaasacceleratorManagementProvidersV1PerSliSloEligibility] = None
	)
	
	case class GoogleCloudSaasacceleratorManagementProvidersV1PerSliSloEligibility(
	  /** An entry in the eligibilities map specifies an eligibility for a particular SLI for the given instance. The SLI key in the name must be a valid SLI name specified in the Eligibility Exporter binary flags otherwise an error will be emitted by Eligibility Exporter and the oncaller will be alerted. If an SLI has been defined in the binary flags but the eligibilities map does not contain it, the corresponding SLI time series will not be emitted by the Eligibility Exporter. This ensures a smooth rollout and compatibility between the data produced by different versions of the Eligibility Exporters. If eligibilities map contains a key for an SLI which has not been declared in the binary flags, there will be an error message emitted in the Eligibility Exporter log and the metric for the SLI in question will not be emitted. */
		eligibilities: Option[Map[String, Schema.GoogleCloudSaasacceleratorManagementProvidersV1SloEligibility]] = None
	)
	
	case class GoogleCloudSaasacceleratorManagementProvidersV1SloEligibility(
	  /** Whether an instance is eligible or ineligible. */
		eligible: Option[Boolean] = None,
	  /** User-defined reason for the current value of instance eligibility. Usually, this can be directly mapped to the internal state. An empty reason is allowed. */
		reason: Option[String] = None
	)
	
	case class GoogleCloudSaasacceleratorManagementProvidersV1MaintenanceSchedule(
	  /** The scheduled start time for the maintenance. */
		startTime: Option[String] = None,
	  /** The scheduled end time for the maintenance. */
		endTime: Option[String] = None,
	  /** This field is deprecated, and will be always set to true since reschedule can happen multiple times now. This field should not be removed until all service producers remove this for their customers. */
		canReschedule: Option[Boolean] = None,
	  /** The rollout management policy this maintenance schedule is associated with. When doing reschedule update request, the reschedule should be against this given policy. */
		rolloutManagementPolicy: Option[String] = None,
	  /** schedule_deadline_time is the time deadline any schedule start time cannot go beyond, including reschedule. It's normally the initial schedule start time plus maintenance window length (1 day or 1 week). Maintenance cannot be scheduled to start beyond this deadline. */
		scheduleDeadlineTime: Option[String] = None
	)
	
	case class GoogleCloudSaasacceleratorManagementProvidersV1MaintenanceSettings(
	  /** Optional. Exclude instance from maintenance. When true, rollout service will not attempt maintenance on the instance. Rollout service will include the instance in reported rollout progress as not attempted. */
		exclude: Option[Boolean] = None,
	  /** Optional. The MaintenancePolicies that have been attached to the instance. The key must be of the type name of the oneof policy name defined in MaintenancePolicy, and the embedded policy must define the same policy type. For details, please refer to go/mr-user-guide. Should not be set if maintenance_policy_names is set. If only the name is needed, then only populate MaintenancePolicy.name. */
		maintenancePolicies: Option[Map[String, Schema.MaintenancePolicy]] = None,
	  /** Optional. If the update call is triggered from rollback, set the value as true. */
		isRollback: Option[Boolean] = None
	)
	
	object MaintenancePolicy {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, READY, DELETING }
	}
	case class MaintenancePolicy(
	  /** Required. MaintenancePolicy name using the form: `projects/{project_id}/locations/{location_id}/maintenancePolicies/{maintenance_policy_id}` where {project_id} refers to a GCP consumer project ID, {location_id} refers to a GCP region/zone, {maintenance_policy_id} must be 1-63 characters long and match the regular expression `[a-z0-9]([-a-z0-9]&#42;[a-z0-9])?`. */
		name: Option[String] = None,
	  /** Output only. The time when the resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the resource was updated. */
		updateTime: Option[String] = None,
	  /** Optional. Description of what this policy is for. Create/Update methods return INVALID_ARGUMENT if the length is greater than 512. */
		description: Option[String] = None,
	  /** Optional. Resource labels to represent user provided metadata. Each label is a key-value pair, where both the key and the value are arbitrary strings provided by the user. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. The state of the policy. */
		state: Option[Schema.MaintenancePolicy.StateEnum] = None,
	  /** Maintenance policy applicable to instance update. */
		updatePolicy: Option[Schema.UpdatePolicy] = None
	)
	
	object UpdatePolicy {
		enum ChannelEnum extends Enum[ChannelEnum] { case UPDATE_CHANNEL_UNSPECIFIED, EARLIER, LATER, WEEK1, WEEK2, WEEK5 }
	}
	case class UpdatePolicy(
	  /** Optional. Maintenance window that is applied to resources covered by this policy. */
		window: Option[Schema.MaintenanceWindow] = None,
	  /** Optional. Relative scheduling channel applied to resource. */
		channel: Option[Schema.UpdatePolicy.ChannelEnum] = None,
	  /** Deny Maintenance Period that is applied to resource to indicate when maintenance is forbidden. The protocol supports zero-to-many such periods, but the current SLM Rollout implementation only supports zero-to-one. */
		denyMaintenancePeriods: Option[List[Schema.DenyMaintenancePeriod]] = None
	)
	
	case class MaintenanceWindow(
	  /** Daily cycle. */
		dailyCycle: Option[Schema.DailyCycle] = None,
	  /** Weekly cycle. */
		weeklyCycle: Option[Schema.WeeklyCycle] = None
	)
	
	case class DailyCycle(
	  /** Time within the day to start the operations. */
		startTime: Option[Schema.TimeOfDay] = None,
	  /** Output only. Duration of the time window, set by service producer. */
		duration: Option[String] = None
	)
	
	case class TimeOfDay(
	  /** Hours of day in 24 hour format. Should be from 0 to 23. An API may choose to allow the value "24:00:00" for scenarios like business closing time. */
		hours: Option[Int] = None,
	  /** Minutes of hour of day. Must be from 0 to 59. */
		minutes: Option[Int] = None,
	  /** Seconds of minutes of the time. Must normally be from 0 to 59. An API may allow the value 60 if it allows leap-seconds. */
		seconds: Option[Int] = None,
	  /** Fractions of seconds in nanoseconds. Must be from 0 to 999,999,999. */
		nanos: Option[Int] = None
	)
	
	case class WeeklyCycle(
	  /** User can specify multiple windows in a week. Minimum of 1 window. */
		schedule: Option[List[Schema.Schedule]] = None
	)
	
	object Schedule {
		enum DayEnum extends Enum[DayEnum] { case DAY_OF_WEEK_UNSPECIFIED, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
	}
	case class Schedule(
	  /** Allows to define schedule that runs specified day of the week. */
		day: Option[Schema.Schedule.DayEnum] = None,
	  /** Time within the window to start the operations. */
		startTime: Option[Schema.TimeOfDay] = None,
	  /** Output only. Duration of the time window, set by service producer. */
		duration: Option[String] = None
	)
	
	case class DenyMaintenancePeriod(
	  /** Deny period start date. This can be: &#42; A full date, with non-zero year, month and day values. &#42; A month and day value, with a zero year. Allows recurring deny periods each year. Date matching this period will have to be the same or after the start. */
		startDate: Option[Schema.Date] = None,
	  /** Deny period end date. This can be: &#42; A full date, with non-zero year, month and day values. &#42; A month and day value, with a zero year. Allows recurring deny periods each year. Date matching this period will have to be before the end. */
		endDate: Option[Schema.Date] = None,
	  /** Time in UTC when the Blackout period starts on start_date and ends on end_date. This can be: &#42; Full time. &#42; All zeros for 00:00:00 UTC */
		time: Option[Schema.TimeOfDay] = None
	)
	
	case class Date(
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None,
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None,
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None
	)
	
	case class GoogleCloudSaasacceleratorManagementProvidersV1NotificationParameter(
	  /** Optional. Array of string values. e.g. instance's replica information. */
		values: Option[List[String]] = None
	)
}
