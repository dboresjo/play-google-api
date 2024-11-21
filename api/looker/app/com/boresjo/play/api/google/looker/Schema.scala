package com.boresjo.play.api.google.looker

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
	
	case class ListInstancesResponse(
	  /** The list of instances matching the request filters, up to the requested ListInstancesRequest.pageSize. */
		instances: Option[List[Schema.Instance]] = None,
	  /** If provided, a page token that can look up the next ListInstancesRequest.pageSize results. If empty, the results list is exhausted. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	object Instance {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, CREATING, FAILED, SUSPENDED, UPDATING, DELETING, EXPORTING, IMPORTING }
		enum PlatformEditionEnum extends Enum[PlatformEditionEnum] { case PLATFORM_EDITION_UNSPECIFIED, LOOKER_CORE_TRIAL, LOOKER_CORE_STANDARD, LOOKER_CORE_STANDARD_ANNUAL, LOOKER_CORE_ENTERPRISE_ANNUAL, LOOKER_CORE_EMBED_ANNUAL }
	}
	case class Instance(
	  /** Output only. Format: `projects/{project}/locations/{location}/instances/{instance}`. */
		name: Option[String] = None,
	  /** Output only. The time when the Looker instance provisioning was first requested. */
		createTime: Option[String] = None,
	  /** Output only. The time when the Looker instance was last updated. */
		updateTime: Option[String] = None,
	  /** Output only. The state of the instance. */
		state: Option[Schema.Instance.StateEnum] = None,
	  /** Platform edition. */
		platformEdition: Option[Schema.Instance.PlatformEditionEnum] = None,
	  /** Whether public IP is enabled on the Looker instance. */
		publicIpEnabled: Option[Boolean] = None,
	  /** Whether private IP is enabled on the Looker instance. */
		privateIpEnabled: Option[Boolean] = None,
	  /** Output only. The Looker version that the instance is using. */
		lookerVersion: Option[String] = None,
	  /** Output only. Public Egress IP (IPv4). */
		egressPublicIp: Option[String] = None,
	  /** Output only. Private Ingress IP (IPv4). */
		ingressPrivateIp: Option[String] = None,
	  /** Output only. Public Ingress IP (IPv4). */
		ingressPublicIp: Option[String] = None,
	  /** Output only. Looker instance URI which can be used to access the Looker Instance UI. */
		lookerUri: Option[String] = None,
	  /** Optional. Whether to use Private Service Connect (PSC) for private IP connectivity. If true, neither `public_ip_enabled` nor `private_ip_enabled` can be true. */
		pscEnabled: Option[Boolean] = None,
	  /** Optional. PSC configuration. Used when `psc_enabled` is true. */
		pscConfig: Option[Schema.PscConfig] = None,
	  /** Network name in the consumer project. Format: `projects/{project}/global/networks/{network}`. Note that the consumer network may be in a different GCP project than the consumer project that is hosting the Looker Instance. */
		consumerNetwork: Option[String] = None,
	  /** Name of a reserved IP address range within the Instance.consumer_network, to be used for private services access connection. May or may not be specified in a create request. */
		reservedRange: Option[String] = None,
	  /** Maintenance window for this instance. */
		maintenanceWindow: Option[Schema.MaintenanceWindow] = None,
	  /** Maintenance denial period for this instance. */
		denyMaintenancePeriod: Option[Schema.DenyMaintenancePeriod] = None,
	  /** Output only. Last computed maintenance denial period for this instance. */
		lastDenyMaintenancePeriod: Option[Schema.DenyMaintenancePeriod] = None,
	  /** Maintenance schedule for this instance. */
		maintenanceSchedule: Option[Schema.MaintenanceSchedule] = None,
	  /** Optional. User metadata. */
		userMetadata: Option[Schema.UserMetadata] = None,
		customDomain: Option[Schema.CustomDomain] = None,
	  /** Encryption configuration (CMEK). Only set if CMEK has been enabled on the instance. */
		encryptionConfig: Option[Schema.EncryptionConfig] = None,
	  /** Looker Instance Admin settings. */
		adminSettings: Option[Schema.AdminSettings] = None,
	  /** Looker instance OAuth login settings. */
		oauthConfig: Option[Schema.OAuthConfig] = None,
	  /** Optional. Linked Google Cloud Project Number for Looker Studio Pro. */
		linkedLspProjectNumber: Option[String] = None,
	  /** Optional. Whether FIPS is enabled on the Looker instance. */
		fipsEnabled: Option[Boolean] = None,
	  /** Optional. Whether Gemini feature is enabled on the Looker instance or not. */
		geminiEnabled: Option[Boolean] = None
	)
	
	case class PscConfig(
	  /** Optional. List of VPCs that are allowed ingress into looker. Format: projects/{project}/global/networks/{network} */
		allowedVpcs: Option[List[String]] = None,
	  /** Optional. List of egress service attachment configurations. */
		serviceAttachments: Option[List[Schema.ServiceAttachment]] = None,
	  /** Output only. URI of the Looker service attachment. */
		lookerServiceAttachmentUri: Option[String] = None
	)
	
	object ServiceAttachment {
		enum ConnectionStatusEnum extends Enum[ConnectionStatusEnum] { case UNKNOWN, ACCEPTED, PENDING, REJECTED, NEEDS_ATTENTION, CLOSED }
	}
	case class ServiceAttachment(
	  /** Required. Fully qualified domain name that will be used in the private DNS record created for the service attachment. */
		localFqdn: Option[String] = None,
	  /** Required. URI of the service attachment to connect to. Format: projects/{project}/regions/{region}/serviceAttachments/{service_attachment} */
		targetServiceAttachmentUri: Option[String] = None,
	  /** Output only. Connection status. */
		connectionStatus: Option[Schema.ServiceAttachment.ConnectionStatusEnum] = None
	)
	
	object MaintenanceWindow {
		enum DayOfWeekEnum extends Enum[DayOfWeekEnum] { case DAY_OF_WEEK_UNSPECIFIED, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
	}
	case class MaintenanceWindow(
	  /** Required. Day of the week for this MaintenanceWindow (in UTC). */
		dayOfWeek: Option[Schema.MaintenanceWindow.DayOfWeekEnum] = None,
	  /** Required. Time in UTC when the period starts. Maintenance will be scheduled within 60 minutes. */
		startTime: Option[Schema.TimeOfDay] = None
	)
	
	case class TimeOfDay(
	  /** Hours of a day in 24 hour format. Must be greater than or equal to 0 and typically must be less than or equal to 23. An API may choose to allow the value "24:00:00" for scenarios like business closing time. */
		hours: Option[Int] = None,
	  /** Minutes of an hour. Must be greater than or equal to 0 and less than or equal to 59. */
		minutes: Option[Int] = None,
	  /** Seconds of a minute. Must be greater than or equal to 0 and typically must be less than or equal to 59. An API may allow the value 60 if it allows leap-seconds. */
		seconds: Option[Int] = None,
	  /** Fractions of seconds, in nanoseconds. Must be greater than or equal to 0 and less than or equal to 999,999,999. */
		nanos: Option[Int] = None
	)
	
	case class DenyMaintenancePeriod(
	  /** Required. Start date of the deny maintenance period. */
		startDate: Option[Schema.Date] = None,
	  /** Required. End date of the deny maintenance period. */
		endDate: Option[Schema.Date] = None,
	  /** Required. Time in UTC when the period starts and ends. */
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
	
	case class MaintenanceSchedule(
	  /** The scheduled start time for the maintenance. */
		startTime: Option[String] = None,
	  /** The scheduled end time for the maintenance. */
		endTime: Option[String] = None
	)
	
	case class UserMetadata(
	  /** Optional. The number of additional viewer users the instance owner has purchased. */
		additionalViewerUserCount: Option[Int] = None,
	  /** Optional. The number of additional standard users the instance owner has purchased. */
		additionalStandardUserCount: Option[Int] = None,
	  /** Optional. The number of additional developer users the instance owner has purchased. */
		additionalDeveloperUserCount: Option[Int] = None
	)
	
	object CustomDomain {
		enum StateEnum extends Enum[StateEnum] { case CUSTOM_DOMAIN_STATE_UNSPECIFIED, UNVERIFIED, VERIFIED, MODIFYING, AVAILABLE, UNAVAILABLE, UNKNOWN }
	}
	case class CustomDomain(
	  /** Domain name. */
		domain: Option[String] = None,
	  /** Domain state. */
		state: Option[Schema.CustomDomain.StateEnum] = None
	)
	
	object EncryptionConfig {
		enum KmsKeyStateEnum extends Enum[KmsKeyStateEnum] { case KMS_KEY_STATE_UNSPECIFIED, VALID, REVOKED }
	}
	case class EncryptionConfig(
	  /** Name of the CMEK key in KMS (input parameter). */
		kmsKeyName: Option[String] = None,
	  /** Output only. Status of the CMEK key. */
		kmsKeyState: Option[Schema.EncryptionConfig.KmsKeyStateEnum] = None,
	  /** Output only. Full name and version of the CMEK key currently in use to encrypt Looker data. Format: `projects/{project}/locations/{location}/keyRings/{ring}/cryptoKeys/{key}/cryptoKeyVersions/{version}`. Empty if CMEK is not configured in this instance. */
		kmsKeyNameVersion: Option[String] = None
	)
	
	case class AdminSettings(
	  /** Email domain allowlist for the instance. */
		allowedEmailDomains: Option[List[String]] = None
	)
	
	case class OAuthConfig(
	  /** Input only. Client ID from an external OAuth application. This is an input-only field, and thus will not be set in any responses. */
		clientId: Option[String] = None,
	  /** Input only. Client secret from an external OAuth application. This is an input-only field, and thus will not be set in any responses. */
		clientSecret: Option[String] = None
	)
	
	case class RestartInstanceRequest(
	
	)
	
	case class ImportInstanceRequest(
	  /** Path to the import folder in Google Cloud Storage, in the form `gs://bucketName/folderName`. */
		gcsUri: Option[String] = None
	)
	
	case class ExportInstanceRequest(
	  /** The path to the folder in Google Cloud Storage where the export will be stored. The URI is in the form `gs://bucketName/folderName`. */
		gcsUri: Option[String] = None,
	  /** Required. Encryption configuration (CMEK). For CMEK enabled instances it should be same as looker CMEK. */
		encryptionConfig: Option[Schema.ExportEncryptionConfig] = None
	)
	
	case class ExportEncryptionConfig(
	  /** Required. Name of the CMEK key in KMS. */
		kmsKeyName: Option[String] = None
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
	
	object ExportMetadata {
		enum SourceEnum extends Enum[SourceEnum] { case SOURCE_UNSPECIFIED, LOOKER_CORE, LOOKER_ORIGINAL }
	}
	case class ExportMetadata(
	  /** Name of the exported instance. Format: projects/{project}/locations/{location}/instances/{instance} */
		lookerInstance: Option[String] = None,
	  /** Version of instance when the export was created. */
		lookerVersion: Option[String] = None,
	  /** Platform edition of the exported instance. */
		lookerPlatformEdition: Option[String] = None,
	  /** Encryption key that was used to encrypt the export artifacts. */
		exportEncryptionKey: Option[Schema.ExportMetadataEncryptionKey] = None,
	  /** Looker encryption key, encrypted with the provided export encryption key. This value will only be populated if the looker instance uses Looker managed encryption instead of CMEK. */
		lookerEncryptionKey: Option[String] = None,
	  /** List of files created as part of export artifact (excluding the metadata). The paths are relative to the folder containing the metadata. */
		filePaths: Option[List[String]] = None,
	  /** The source type of the migration. */
		source: Option[Schema.ExportMetadata.SourceEnum] = None
	)
	
	case class ExportMetadataEncryptionKey(
	  /** Name of the CMEK. */
		cmek: Option[String] = None,
	  /** Version of the CMEK. */
		version: Option[String] = None
	)
}
