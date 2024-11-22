package com.boresjo.play.api.google.secretmanager

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
	
	case class ListSecretsResponse(
	  /** The list of Secrets sorted in reverse by create_time (newest first). */
		secrets: Option[List[Schema.Secret]] = None,
	  /** A token to retrieve the next page of results. Pass this value in ListSecretsRequest.page_token to retrieve the next page. */
		nextPageToken: Option[String] = None,
	  /** The total number of Secrets but 0 when the ListSecretsRequest.filter field is set. */
		totalSize: Option[Int] = None
	)
	
	case class Secret(
	  /** Output only. The resource name of the Secret in the format `projects/&#42;/secrets/&#42;`. */
		name: Option[String] = None,
	  /** Optional. Immutable. The replication policy of the secret data attached to the Secret. The replication policy cannot be changed after the Secret has been created. */
		replication: Option[Schema.Replication] = None,
	  /** Output only. The time at which the Secret was created. */
		createTime: Option[String] = None,
	  /** The labels assigned to this Secret. Label keys must be between 1 and 63 characters long, have a UTF-8 encoding of maximum 128 bytes, and must conform to the following PCRE regular expression: `\p{Ll}\p{Lo}{0,62}` Label values must be between 0 and 63 characters long, have a UTF-8 encoding of maximum 128 bytes, and must conform to the following PCRE regular expression: `[\p{Ll}\p{Lo}\p{N}_-]{0,63}` No more than 64 labels can be assigned to a given resource. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. A list of up to 10 Pub/Sub topics to which messages are published when control plane operations are called on the secret or its versions. */
		topics: Option[List[Schema.Topic]] = None,
	  /** Optional. Timestamp in UTC when the Secret is scheduled to expire. This is always provided on output, regardless of what was sent on input. */
		expireTime: Option[String] = None,
	  /** Input only. The TTL for the Secret. */
		ttl: Option[String] = None,
	  /** Optional. Etag of the currently stored Secret. */
		etag: Option[String] = None,
	  /** Optional. Rotation policy attached to the Secret. May be excluded if there is no rotation policy. */
		rotation: Option[Schema.Rotation] = None,
	  /** Optional. Mapping from version alias to version name. A version alias is a string with a maximum length of 63 characters and can contain uppercase and lowercase letters, numerals, and the hyphen (`-`) and underscore ('_') characters. An alias string must start with a letter and cannot be the string 'latest' or 'NEW'. No more than 50 aliases can be assigned to a given secret. Version-Alias pairs will be viewable via GetSecret and modifiable via UpdateSecret. Access by alias is only be supported on GetSecretVersion and AccessSecretVersion. */
		versionAliases: Option[Map[String, String]] = None,
	  /** Optional. Custom metadata about the secret. Annotations are distinct from various forms of labels. Annotations exist to allow client tools to store their own state information without requiring a database. Annotation keys must be between 1 and 63 characters long, have a UTF-8 encoding of maximum 128 bytes, begin and end with an alphanumeric character ([a-z0-9A-Z]), and may have dashes (-), underscores (_), dots (.), and alphanumerics in between these symbols. The total size of annotation keys and values must be less than 16KiB. */
		annotations: Option[Map[String, String]] = None,
	  /** Optional. Secret Version TTL after destruction request This is a part of the Delayed secret version destroy feature. For secret with TTL>0, version destruction doesn't happen immediately on calling destroy instead the version goes to a disabled state and destruction happens after the TTL expires. */
		versionDestroyTtl: Option[String] = None,
	  /** Optional. The customer-managed encryption configuration of the regionalized secrets. If no configuration is provided, Google-managed default encryption is used. Updates to the Secret encryption configuration only apply to SecretVersions added afterwards. They do not apply retroactively to existing SecretVersions. */
		customerManagedEncryption: Option[Schema.CustomerManagedEncryption] = None
	)
	
	case class Replication(
	  /** The Secret will automatically be replicated without any restrictions. */
		automatic: Option[Schema.Automatic] = None,
	  /** The Secret will only be replicated into the locations specified. */
		userManaged: Option[Schema.UserManaged] = None
	)
	
	case class Automatic(
	  /** Optional. The customer-managed encryption configuration of the Secret. If no configuration is provided, Google-managed default encryption is used. Updates to the Secret encryption configuration only apply to SecretVersions added afterwards. They do not apply retroactively to existing SecretVersions. */
		customerManagedEncryption: Option[Schema.CustomerManagedEncryption] = None
	)
	
	case class CustomerManagedEncryption(
	  /** Required. The resource name of the Cloud KMS CryptoKey used to encrypt secret payloads. For secrets using the UserManaged replication policy type, Cloud KMS CryptoKeys must reside in the same location as the replica location. For secrets using the Automatic replication policy type, Cloud KMS CryptoKeys must reside in `global`. The expected format is `projects/&#42;/locations/&#42;/keyRings/&#42;/cryptoKeys/&#42;`. */
		kmsKeyName: Option[String] = None
	)
	
	case class UserManaged(
	  /** Required. The list of Replicas for this Secret. Cannot be empty. */
		replicas: Option[List[Schema.Replica]] = None
	)
	
	case class Replica(
	  /** The canonical IDs of the location to replicate data. For example: `"us-east1"`. */
		location: Option[String] = None,
	  /** Optional. The customer-managed encryption configuration of the User-Managed Replica. If no configuration is provided, Google-managed default encryption is used. Updates to the Secret encryption configuration only apply to SecretVersions added afterwards. They do not apply retroactively to existing SecretVersions. */
		customerManagedEncryption: Option[Schema.CustomerManagedEncryption] = None
	)
	
	case class Topic(
	  /** Identifier. The resource name of the Pub/Sub topic that will be published to, in the following format: `projects/&#42;/topics/&#42;`. For publication to succeed, the Secret Manager service agent must have the `pubsub.topic.publish` permission on the topic. The Pub/Sub Publisher role (`roles/pubsub.publisher`) includes this permission. */
		name: Option[String] = None
	)
	
	case class Rotation(
	  /** Optional. Timestamp in UTC at which the Secret is scheduled to rotate. Cannot be set to less than 300s (5 min) in the future and at most 3153600000s (100 years). next_rotation_time MUST be set if rotation_period is set. */
		nextRotationTime: Option[String] = None,
	  /** Input only. The Duration between rotation notifications. Must be in seconds and at least 3600s (1h) and at most 3153600000s (100 years). If rotation_period is set, next_rotation_time must be set. next_rotation_time will be advanced by this period when the service automatically sends rotation notifications. */
		rotationPeriod: Option[String] = None
	)
	
	case class AddSecretVersionRequest(
	  /** Required. The secret payload of the SecretVersion. */
		payload: Option[Schema.SecretPayload] = None
	)
	
	case class SecretPayload(
	  /** The secret data. Must be no larger than 64KiB. */
		data: Option[String] = None,
	  /** Optional. If specified, SecretManagerService will verify the integrity of the received data on SecretManagerService.AddSecretVersion calls using the crc32c checksum and store it to include in future SecretManagerService.AccessSecretVersion responses. If a checksum is not provided in the SecretManagerService.AddSecretVersion request, the SecretManagerService will generate and store one for you. The CRC32C value is encoded as a Int64 for compatibility, and can be safely downconverted to uint32 in languages that support this type. https://cloud.google.com/apis/design/design_patterns#integer_types */
		dataCrc32c: Option[String] = None
	)
	
	object SecretVersion {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ENABLED, DISABLED, DESTROYED }
	}
	case class SecretVersion(
	  /** Output only. The resource name of the SecretVersion in the format `projects/&#42;/secrets/&#42;/versions/&#42;`. SecretVersion IDs in a Secret start at 1 and are incremented for each subsequent version of the secret. */
		name: Option[String] = None,
	  /** Output only. The time at which the SecretVersion was created. */
		createTime: Option[String] = None,
	  /** Output only. The time this SecretVersion was destroyed. Only present if state is DESTROYED. */
		destroyTime: Option[String] = None,
	  /** Output only. The current state of the SecretVersion. */
		state: Option[Schema.SecretVersion.StateEnum] = None,
	  /** The replication status of the SecretVersion. */
		replicationStatus: Option[Schema.ReplicationStatus] = None,
	  /** Output only. Etag of the currently stored SecretVersion. */
		etag: Option[String] = None,
	  /** Output only. True if payload checksum specified in SecretPayload object has been received by SecretManagerService on SecretManagerService.AddSecretVersion. */
		clientSpecifiedPayloadChecksum: Option[Boolean] = None,
	  /** Optional. Output only. Scheduled destroy time for secret version. This is a part of the Delayed secret version destroy feature. For a Secret with a valid version destroy TTL, when a secert version is destroyed, version is moved to disabled state and it is scheduled for destruction Version is destroyed only after the scheduled_destroy_time. */
		scheduledDestroyTime: Option[String] = None,
	  /** Output only. The customer-managed encryption status of the SecretVersion. Only populated if customer-managed encryption is used and Secret is a regionalized secret. */
		customerManagedEncryption: Option[Schema.CustomerManagedEncryptionStatus] = None
	)
	
	case class ReplicationStatus(
	  /** Describes the replication status of a SecretVersion with automatic replication. Only populated if the parent Secret has an automatic replication policy. */
		automatic: Option[Schema.AutomaticStatus] = None,
	  /** Describes the replication status of a SecretVersion with user-managed replication. Only populated if the parent Secret has a user-managed replication policy. */
		userManaged: Option[Schema.UserManagedStatus] = None
	)
	
	case class AutomaticStatus(
	  /** Output only. The customer-managed encryption status of the SecretVersion. Only populated if customer-managed encryption is used. */
		customerManagedEncryption: Option[Schema.CustomerManagedEncryptionStatus] = None
	)
	
	case class CustomerManagedEncryptionStatus(
	  /** Required. The resource name of the Cloud KMS CryptoKeyVersion used to encrypt the secret payload, in the following format: `projects/&#42;/locations/&#42;/keyRings/&#42;/cryptoKeys/&#42;/versions/&#42;`. */
		kmsKeyVersionName: Option[String] = None
	)
	
	case class UserManagedStatus(
	  /** Output only. The list of replica statuses for the SecretVersion. */
		replicas: Option[List[Schema.ReplicaStatus]] = None
	)
	
	case class ReplicaStatus(
	  /** Output only. The canonical ID of the replica location. For example: `"us-east1"`. */
		location: Option[String] = None,
	  /** Output only. The customer-managed encryption status of the SecretVersion. Only populated if customer-managed encryption is used. */
		customerManagedEncryption: Option[Schema.CustomerManagedEncryptionStatus] = None
	)
	
	case class Empty(
	
	)
	
	case class ListSecretVersionsResponse(
	  /** The list of SecretVersions sorted in reverse by create_time (newest first). */
		versions: Option[List[Schema.SecretVersion]] = None,
	  /** A token to retrieve the next page of results. Pass this value in ListSecretVersionsRequest.page_token to retrieve the next page. */
		nextPageToken: Option[String] = None,
	  /** The total number of SecretVersions but 0 when the ListSecretsRequest.filter field is set. */
		totalSize: Option[Int] = None
	)
	
	case class AccessSecretVersionResponse(
	  /** The resource name of the SecretVersion in the format `projects/&#42;/secrets/&#42;/versions/&#42;` or `projects/&#42;/locations/&#42;/secrets/&#42;/versions/&#42;`. */
		name: Option[String] = None,
	  /** Secret payload */
		payload: Option[Schema.SecretPayload] = None
	)
	
	case class DisableSecretVersionRequest(
	  /** Optional. Etag of the SecretVersion. The request succeeds if it matches the etag of the currently stored secret version object. If the etag is omitted, the request succeeds. */
		etag: Option[String] = None
	)
	
	case class EnableSecretVersionRequest(
	  /** Optional. Etag of the SecretVersion. The request succeeds if it matches the etag of the currently stored secret version object. If the etag is omitted, the request succeeds. */
		etag: Option[String] = None
	)
	
	case class DestroySecretVersionRequest(
	  /** Optional. Etag of the SecretVersion. The request succeeds if it matches the etag of the currently stored secret version object. If the etag is omitted, the request succeeds. */
		etag: Option[String] = None
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
}
