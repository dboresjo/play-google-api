package com.boresjo.play.api.google.logging

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class SetIamPolicyRequest(
	  /** REQUIRED: The complete policy to be applied to the resource. The size of the policy is limited to a few 10s of KB. An empty policy is a valid policy but certain Google Cloud services (such as Projects) might reject them. */
		policy: Option[Schema.Policy] = None,
	  /** OPTIONAL: A FieldMask specifying which fields of the policy to modify. Only the fields in the mask will be modified. If no mask is provided, the following default mask is used:paths: "bindings, etag" */
		updateMask: Option[String] = None
	)
	
	case class Policy(
	  /** Specifies the format of the policy.Valid values are 0, 1, and 3. Requests that specify an invalid value are rejected.Any operation that affects conditional role bindings must specify version 3. This requirement applies to the following operations: Getting a policy that includes a conditional role binding Adding a conditional role binding to a policy Changing a conditional role binding in a policy Removing any role binding, with or without a condition, from a policy that includes conditionsImportant: If you use IAM Conditions, you must include the etag field whenever you call setIamPolicy. If you omit this field, then IAM allows you to overwrite a version 3 policy with a version 1 policy, and all of the conditions in the version 3 policy are lost.If a policy does not include any conditions, operations on that policy may specify any valid version or leave the field unset.To learn which resources support conditions in their IAM policies, see the IAM documentation (https://cloud.google.com/iam/help/conditions/resource-policies). */
		version: Option[Int] = None,
	  /** Associates a list of members, or principals, with a role. Optionally, may specify a condition that determines how and when the bindings are applied. Each of the bindings must contain at least one principal.The bindings in a Policy can refer to up to 1,500 principals; up to 250 of these principals can be Google groups. Each occurrence of a principal counts towards these limits. For example, if the bindings grant 50 different roles to user:alice@example.com, and not to any other principal, then you can add another 1,450 principals to the bindings in the Policy. */
		bindings: Option[List[Schema.Binding]] = None,
	  /** Specifies cloud audit logging configuration for this policy. */
		auditConfigs: Option[List[Schema.AuditConfig]] = None,
	  /** etag is used for optimistic concurrency control as a way to help prevent simultaneous updates of a policy from overwriting each other. It is strongly suggested that systems make use of the etag in the read-modify-write cycle to perform policy updates in order to avoid race conditions: An etag is returned in the response to getIamPolicy, and systems are expected to put that etag in the request to setIamPolicy to ensure that their change will be applied to the same version of the policy.Important: If you use IAM Conditions, you must include the etag field whenever you call setIamPolicy. If you omit this field, then IAM allows you to overwrite a version 3 policy with a version 1 policy, and all of the conditions in the version 3 policy are lost. */
		etag: Option[String] = None
	)
	
	case class Binding(
	  /** Role that is assigned to the list of members, or principals. For example, roles/viewer, roles/editor, or roles/owner.For an overview of the IAM roles and permissions, see the IAM documentation (https://cloud.google.com/iam/docs/roles-overview). For a list of the available pre-defined roles, see here (https://cloud.google.com/iam/docs/understanding-roles). */
		role: Option[String] = None,
	  /** Specifies the principals requesting access for a Google Cloud resource. members can have the following values: allUsers: A special identifier that represents anyone who is on the internet; with or without a Google account. allAuthenticatedUsers: A special identifier that represents anyone who is authenticated with a Google account or a service account. Does not include identities that come from external identity providers (IdPs) through identity federation. user:{emailid}: An email address that represents a specific Google account. For example, alice@example.com . serviceAccount:{emailid}: An email address that represents a Google service account. For example, my-other-app@appspot.gserviceaccount.com. serviceAccount:{projectid}.svc.id.goog[{namespace}/{kubernetes-sa}]: An identifier for a Kubernetes service account (https://cloud.google.com/kubernetes-engine/docs/how-to/kubernetes-service-accounts). For example, my-project.svc.id.goog[my-namespace/my-kubernetes-sa]. group:{emailid}: An email address that represents a Google group. For example, admins@example.com. domain:{domain}: The G Suite domain (primary) that represents all the users of that domain. For example, google.com or example.com. principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}: A single identity in a workforce identity pool. principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/group/{group_id}: All workforce identities in a group. principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/attribute.{attribute_name}/{attribute_value}: All workforce identities with a specific attribute value. principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/&#42;: All identities in a workforce identity pool. principal://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/subject/{subject_attribute_value}: A single identity in a workload identity pool. principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/group/{group_id}: A workload identity pool group. principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/attribute.{attribute_name}/{attribute_value}: All identities in a workload identity pool with a certain attribute. principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/&#42;: All identities in a workload identity pool. deleted:user:{emailid}?uid={uniqueid}: An email address (plus unique identifier) representing a user that has been recently deleted. For example, alice@example.com?uid=123456789012345678901. If the user is recovered, this value reverts to user:{emailid} and the recovered user retains the role in the binding. deleted:serviceAccount:{emailid}?uid={uniqueid}: An email address (plus unique identifier) representing a service account that has been recently deleted. For example, my-other-app@appspot.gserviceaccount.com?uid=123456789012345678901. If the service account is undeleted, this value reverts to serviceAccount:{emailid} and the undeleted service account retains the role in the binding. deleted:group:{emailid}?uid={uniqueid}: An email address (plus unique identifier) representing a Google group that has been recently deleted. For example, admins@example.com?uid=123456789012345678901. If the group is recovered, this value reverts to group:{emailid} and the recovered group retains the role in the binding. deleted:principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}: Deleted single identity in a workforce identity pool. For example, deleted:principal://iam.googleapis.com/locations/global/workforcePools/my-pool-id/subject/my-subject-attribute-value. */
		members: Option[List[String]] = None,
	  /** The condition that is associated with this binding.If the condition evaluates to true, then this binding applies to the current request.If the condition evaluates to false, then this binding does not apply to the current request. However, a different role binding might grant the same role to one or more of the principals in this binding.To learn which resources support conditions in their IAM policies, see the IAM documentation (https://cloud.google.com/iam/help/conditions/resource-policies). */
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
	  /** Specifies a service that will be enabled for audit logging. For example, storage.googleapis.com, cloudsql.googleapis.com. allServices is a special value that covers all services. */
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
	  /** OPTIONAL: A GetPolicyOptions object for specifying options to GetIamPolicy. */
		options: Option[Schema.GetPolicyOptions] = None
	)
	
	case class GetPolicyOptions(
	  /** Optional. The maximum policy version that will be used to format the policy.Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected.Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset.The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1.To learn which resources support conditions in their IAM policies, see the IAM documentation (https://cloud.google.com/iam/help/conditions/resource-policies). */
		requestedPolicyVersion: Option[Int] = None
	)
	
	case class TestIamPermissionsRequest(
	  /** The set of permissions to check for the resource. Permissions with wildcards (such as &#42; or storage.&#42;) are not allowed. For more information see IAM Overview (https://cloud.google.com/iam/docs/overview#permissions). */
		permissions: Option[List[String]] = None
	)
	
	case class TestIamPermissionsResponse(
	  /** A subset of TestPermissionsRequest.permissions that the caller is allowed. */
		permissions: Option[List[String]] = None
	)
	
	case class ListOperationsResponse(
	  /** A list of operations that matches the specified filter in the request. */
		operations: Option[List[Schema.Operation]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class Operation(
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the name should be a resource name ending with operations/{unique_id}. */
		name: Option[String] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** If the value is false, it means the operation is still in progress. If true, the operation is completed, and either error or response is available. */
		done: Option[Boolean] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.Status] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as Delete, the response is google.protobuf.Empty. If the original method is standard Get/Create/Update, the response should be the resource. For other methods, the response should have the type XxxResponse, where Xxx is the original method name. For example, if the original method name is TakeSnapshot(), the inferred response type is TakeSnapshotResponse. */
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
	
	case class CancelOperationRequest(
	
	)
	
	case class Empty(
	
	)
	
	case class ListLocationsResponse(
	  /** A list of locations that matches the specified filter in the request. */
		locations: Option[List[Schema.Location]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class Location(
	  /** Resource name for the location, which may vary between implementations. For example: "projects/example-project/locations/us-east1" */
		name: Option[String] = None,
	  /** The canonical id for this location. For example: "us-east1". */
		locationId: Option[String] = None,
	  /** The friendly name for this location, typically a nearby city name. For example, "Tokyo". */
		displayName: Option[String] = None,
	  /** Cross-service attributes for the location. For example {"cloud.googleapis.com/region": "us-east1"}  */
		labels: Option[Map[String, String]] = None,
	  /** Service-specific metadata. For example the available capacity at the given location. */
		metadata: Option[Map[String, JsValue]] = None
	)
	
	case class ListBucketsResponse(
	  /** A list of buckets. */
		buckets: Option[List[Schema.LogBucket]] = None,
	  /** If there might be more results than appear in this response, then nextPageToken is included. To get the next set of results, call the same method again using the value of nextPageToken as pageToken. */
		nextPageToken: Option[String] = None
	)
	
	object LogBucket {
		enum LifecycleStateEnum extends Enum[LifecycleStateEnum] { case LIFECYCLE_STATE_UNSPECIFIED, ACTIVE, DELETE_REQUESTED, UPDATING, CREATING, FAILED }
	}
	case class LogBucket(
	  /** Output only. The resource name of the bucket.For example:projects/my-project/locations/global/buckets/my-bucketFor a list of supported locations, see Supported Regions (https://cloud.google.com/logging/docs/region-support)For the location of global it is unspecified where log entries are actually stored.After a bucket has been created, the location cannot be changed. */
		name: Option[String] = None,
	  /** Optional. Describes this bucket. */
		description: Option[String] = None,
	  /** Output only. The creation timestamp of the bucket. This is not set for any of the default buckets. */
		createTime: Option[String] = None,
	  /** Output only. The last update timestamp of the bucket. */
		updateTime: Option[String] = None,
	  /** Optional. Logs will be retained by default for this amount of time, after which they will automatically be deleted. The minimum retention period is 1 day. If this value is set to zero at bucket creation time, the default time of 30 days will be used. */
		retentionDays: Option[Int] = None,
	  /** Optional. Whether the bucket is locked.The retention period on a locked bucket cannot be changed. Locked buckets may only be deleted if they are empty. */
		locked: Option[Boolean] = None,
	  /** Output only. The bucket lifecycle state. */
		lifecycleState: Option[Schema.LogBucket.LifecycleStateEnum] = None,
	  /** Optional. Whether log analytics is enabled for this bucket.Once enabled, log analytics features cannot be disabled. */
		analyticsEnabled: Option[Boolean] = None,
	  /** Optional. Log entry field paths that are denied access in this bucket.The following fields and their children are eligible: textPayload, jsonPayload, protoPayload, httpRequest, labels, sourceLocation.Restricting a repeated field will restrict all values. Adding a parent will block all child fields. (e.g. foo.bar will block foo.bar.baz) */
		restrictedFields: Option[List[String]] = None,
	  /** Optional. A list of indexed fields and related configuration data. */
		indexConfigs: Option[List[Schema.IndexConfig]] = None,
	  /** Optional. The CMEK settings of the log bucket. If present, new log entries written to this log bucket are encrypted using the CMEK key provided in this configuration. If a log bucket has CMEK settings, the CMEK settings cannot be disabled later by updating the log bucket. Changing the KMS key is allowed. */
		cmekSettings: Option[Schema.CmekSettings] = None
	)
	
	object IndexConfig {
		enum TypeEnum extends Enum[TypeEnum] { case INDEX_TYPE_UNSPECIFIED, INDEX_TYPE_STRING, INDEX_TYPE_INTEGER }
	}
	case class IndexConfig(
	  /** Required. The LogEntry field path to index.Note that some paths are automatically indexed, and other paths are not eligible for indexing. See indexing documentation( https://cloud.google.com/logging/docs/analyze/custom-index) for details.For example: jsonPayload.request.status */
		fieldPath: Option[String] = None,
	  /** Required. The type of data in this index. */
		`type`: Option[Schema.IndexConfig.TypeEnum] = None,
	  /** Output only. The timestamp when the index was last modified.This is used to return the timestamp, and will be ignored if supplied during update. */
		createTime: Option[String] = None
	)
	
	case class CmekSettings(
	  /** Output only. The resource name of the CMEK settings. */
		name: Option[String] = None,
	  /** Optional. The resource name for the configured Cloud KMS key.KMS key name format: "projects/[PROJECT_ID]/locations/[LOCATION]/keyRings/[KEYRING]/cryptoKeys/[KEY]" For example:"projects/my-project/locations/us-central1/keyRings/my-ring/cryptoKeys/my-key"To enable CMEK for the Log Router, set this field to a valid kms_key_name for which the associated service account has the needed cloudkms.cryptoKeyEncrypterDecrypter roles assigned for the key.The Cloud KMS key used by the Log Router can be updated by changing the kms_key_name to a new valid key name or disabled by setting the key name to an empty string. Encryption operations that are in progress will be completed with the key that was in use when they started. Decryption operations will be completed using the key that was used at the time of encryption unless access to that key has been revoked.To disable CMEK for the Log Router, set this field to an empty string.See Enabling CMEK for Log Router (https://cloud.google.com/logging/docs/routing/managed-encryption) for more information. */
		kmsKeyName: Option[String] = None,
	  /** Output only. The CryptoKeyVersion resource name for the configured Cloud KMS key.KMS key name format: "projects/[PROJECT_ID]/locations/[LOCATION]/keyRings/[KEYRING]/cryptoKeys/[KEY]/cryptoKeyVersions/[VERSION]" For example:"projects/my-project/locations/us-central1/keyRings/my-ring/cryptoKeys/my-key/cryptoKeyVersions/1"This is a read-only field used to convey the specific configured CryptoKeyVersion of kms_key that has been configured. It will be populated in cases where the CMEK settings are bound to a single key version.If this field is populated, the kms_key is tied to a specific CryptoKeyVersion. */
		kmsKeyVersionName: Option[String] = None,
	  /** Output only. The service account that will be used by the Log Router to access your Cloud KMS key.Before enabling CMEK for Log Router, you must first assign the cloudkms.cryptoKeyEncrypterDecrypter role to the service account that the Log Router will use to access your Cloud KMS key. Use GetCmekSettings to obtain the service account ID.See Enabling CMEK for Log Router (https://cloud.google.com/logging/docs/routing/managed-encryption) for more information. */
		serviceAccountId: Option[String] = None
	)
	
	case class UndeleteBucketRequest(
	
	)
	
	case class ListViewsResponse(
	  /** A list of views. */
		views: Option[List[Schema.LogView]] = None,
	  /** If there might be more results than appear in this response, then nextPageToken is included. To get the next set of results, call the same method again using the value of nextPageToken as pageToken. */
		nextPageToken: Option[String] = None
	)
	
	case class LogView(
	  /** Output only. The resource name of the view.For example:projects/my-project/locations/global/buckets/my-bucket/views/my-view */
		name: Option[String] = None,
	  /** Optional. Describes this view. */
		description: Option[String] = None,
	  /** Output only. The creation timestamp of the view. */
		createTime: Option[String] = None,
	  /** Output only. The last update timestamp of the view. */
		updateTime: Option[String] = None,
	  /** Optional. Filter that restricts which log entries in a bucket are visible in this view.Filters must be logical conjunctions that use the AND operator, and they can use any of the following qualifiers: SOURCE(), which specifies a project, folder, organization, or billing account of origin. resource.type, which specifies the resource type. LOG_ID(), which identifies the log.They can also use the negations of these qualifiers with the NOT operator.For example:SOURCE("projects/myproject") AND resource.type = "gce_instance" AND NOT LOG_ID("stdout") */
		filter: Option[String] = None
	)
	
	case class ListLogScopesResponse(
	  /** A list of log scopes. */
		logScopes: Option[List[Schema.LogScope]] = None,
	  /** If there might be more results than appear in this response, then nextPageToken is included. To get the next set of results, call the same method again using the value of nextPageToken as pageToken. */
		nextPageToken: Option[String] = None
	)
	
	case class LogScope(
	  /** Output only. The resource name of the log scope.Log scopes are only available in the global location. For example:projects/my-project/locations/global/logScopes/my-log-scope */
		name: Option[String] = None,
	  /** Required. Names of one or more parent resources: projects/[PROJECT_ID]May alternatively be one or more views: projects/[PROJECT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID]A log scope can include a maximum of 5 projects and a maximum of 100 resources in total. */
		resourceNames: Option[List[String]] = None,
	  /** Optional. Describes this log scope.The maximum length of the description is 8000 characters. */
		description: Option[String] = None,
	  /** Output only. The creation timestamp of the log scope. */
		createTime: Option[String] = None,
	  /** Output only. The last update timestamp of the log scope. */
		updateTime: Option[String] = None
	)
	
	case class ListExclusionsResponse(
	  /** A list of exclusions. */
		exclusions: Option[List[Schema.LogExclusion]] = None,
	  /** If there might be more results than appear in this response, then nextPageToken is included. To get the next set of results, call the same method again using the value of nextPageToken as pageToken. */
		nextPageToken: Option[String] = None
	)
	
	case class LogExclusion(
	  /** Output only. A client-assigned identifier, such as "load-balancer-exclusion". Identifiers are limited to 100 characters and can include only letters, digits, underscores, hyphens, and periods. First character has to be alphanumeric. */
		name: Option[String] = None,
	  /** Optional. A description of this exclusion. */
		description: Option[String] = None,
	  /** Required. An advanced logs filter (https://cloud.google.com/logging/docs/view/advanced-queries) that matches the log entries to be excluded. By using the sample function (https://cloud.google.com/logging/docs/view/advanced-queries#sample), you can exclude less than 100% of the matching log entries.For example, the following query matches 99% of low-severity log entries from Google Cloud Storage buckets:resource.type=gcs_bucket severity<ERROR sample(insertId, 0.99) */
		filter: Option[String] = None,
	  /** Optional. If set to True, then this exclusion is disabled and it does not exclude any log entries. You can update an exclusion to change the value of this field. */
		disabled: Option[Boolean] = None,
	  /** Output only. The creation timestamp of the exclusion.This field may not be present for older exclusions. */
		createTime: Option[String] = None,
	  /** Output only. The last update timestamp of the exclusion.This field may not be present for older exclusions. */
		updateTime: Option[String] = None
	)
	
	case class ListSinksResponse(
	  /** A list of sinks. */
		sinks: Option[List[Schema.LogSink]] = None,
	  /** If there might be more results than appear in this response, then nextPageToken is included. To get the next set of results, call the same method again using the value of nextPageToken as pageToken. */
		nextPageToken: Option[String] = None
	)
	
	object LogSink {
		enum OutputVersionFormatEnum extends Enum[OutputVersionFormatEnum] { case VERSION_FORMAT_UNSPECIFIED, V2, V1 }
	}
	case class LogSink(
	  /** Output only. The client-assigned sink identifier, unique within the project.For example: "my-syslog-errors-to-pubsub".Sink identifiers are limited to 100 characters and can include only the following characters: upper and lower-case alphanumeric characters, underscores, hyphens, periods.First character has to be alphanumeric. */
		name: Option[String] = None,
	  /** Output only. The resource name of the sink. "projects/[PROJECT_ID]/sinks/[SINK_NAME] "organizations/[ORGANIZATION_ID]/sinks/[SINK_NAME] "billingAccounts/[BILLING_ACCOUNT_ID]/sinks/[SINK_NAME] "folders/[FOLDER_ID]/sinks/[SINK_NAME] For example: projects/my_project/sinks/SINK_NAME */
		resourceName: Option[String] = None,
	  /** Required. The export destination: "storage.googleapis.com/[GCS_BUCKET]" "bigquery.googleapis.com/projects/[PROJECT_ID]/datasets/[DATASET]" "pubsub.googleapis.com/projects/[PROJECT_ID]/topics/[TOPIC_ID]" "logging.googleapis.com/projects/[PROJECT_ID]" "logging.googleapis.com/projects/[PROJECT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]" The sink's writer_identity, set when the sink is created, must have permission to write to the destination or else the log entries are not exported. For more information, see Exporting Logs with Sinks (https://cloud.google.com/logging/docs/api/tasks/exporting-logs). */
		destination: Option[String] = None,
	  /** Optional. An advanced logs filter (https://cloud.google.com/logging/docs/view/advanced-queries). The only exported log entries are those that are in the resource owning the sink and that match the filter.For example:logName="projects/[PROJECT_ID]/logs/[LOG_ID]" AND severity>=ERROR */
		filter: Option[String] = None,
	  /** Optional. A description of this sink.The maximum length of the description is 8000 characters. */
		description: Option[String] = None,
	  /** Optional. If set to true, then this sink is disabled and it does not export any log entries. */
		disabled: Option[Boolean] = None,
	  /** Optional. Log entries that match any of these exclusion filters will not be exported.If a log entry is matched by both filter and one of exclusion_filters it will not be exported. */
		exclusions: Option[List[Schema.LogExclusion]] = None,
	  /** Deprecated. This field is unused. */
		outputVersionFormat: Option[Schema.LogSink.OutputVersionFormatEnum] = None,
	  /** Output only. An IAM identity—a service account or group—under which Cloud Logging writes the exported log entries to the sink's destination. This field is either set by specifying custom_writer_identity or set automatically by sinks.create and sinks.update based on the value of unique_writer_identity in those methods.Until you grant this identity write-access to the destination, log entry exports from this sink will fail. For more information, see Granting Access for a Resource (https://cloud.google.com/iam/docs/granting-roles-to-service-accounts#granting_access_to_a_service_account_for_a_resource). Consult the destination service's documentation to determine the appropriate IAM roles to assign to the identity.Sinks that have a destination that is a log bucket in the same project as the sink cannot have a writer_identity and no additional permissions are required. */
		writerIdentity: Option[String] = None,
	  /** Optional. This field applies only to sinks owned by organizations and folders. If the field is false, the default, only the logs owned by the sink's parent resource are available for export. If the field is true, then log entries from all the projects, folders, and billing accounts contained in the sink's parent resource are also available for export. Whether a particular log entry from the children is exported depends on the sink's filter expression.For example, if this field is true, then the filter resource.type=gce_instance would export all Compute Engine VM instance log entries from all projects in the sink's parent.To only export entries from certain child projects, filter on the project part of the log name:logName:("projects/test-project1/" OR "projects/test-project2/") AND resource.type=gce_instance */
		includeChildren: Option[Boolean] = None,
	  /** Optional. This field applies only to sinks owned by organizations and folders.When the value of 'intercept_children' is true, the following restrictions apply: The sink must have the include_children flag set to true. The sink destination must be a Cloud project.Also, the following behaviors apply: Any logs matched by the sink won't be included by non-_Required sinks owned by child resources. The sink appears in the results of a ListSinks call from a child resource if the value of the filter field in its request is either 'in_scope("ALL")' or 'in_scope("ANCESTOR")'. */
		interceptChildren: Option[Boolean] = None,
	  /** Optional. Options that affect sinks exporting data to BigQuery. */
		bigqueryOptions: Option[Schema.BigQueryOptions] = None,
	  /** Output only. The creation timestamp of the sink.This field may not be present for older sinks. */
		createTime: Option[String] = None,
	  /** Output only. The last update timestamp of the sink.This field may not be present for older sinks. */
		updateTime: Option[String] = None
	)
	
	case class BigQueryOptions(
	  /** Optional. Whether to use BigQuery's partition tables (https://cloud.google.com/bigquery/docs/partitioned-tables). By default, Cloud Logging creates dated tables based on the log entries' timestamps, e.g. syslog_20170523. With partitioned tables the date suffix is no longer present and special query syntax (https://cloud.google.com/bigquery/docs/querying-partitioned-tables) has to be used instead. In both cases, tables are sharded based on UTC timezone. */
		usePartitionedTables: Option[Boolean] = None,
	  /** Output only. True if new timestamp column based partitioning is in use, false if legacy ingress-time partitioning is in use.All new sinks will have this field set true and will use timestamp column based partitioning. If use_partitioned_tables is false, this value has no meaning and will be false. Legacy sinks using partitioned tables will have this field set to false. */
		usesTimestampColumnPartitioning: Option[Boolean] = None
	)
	
	case class ListLinksResponse(
	  /** A list of links. */
		links: Option[List[Schema.Link]] = None,
	  /** If there might be more results than those appearing in this response, then nextPageToken is included. To get the next set of results, call the same method again using the value of nextPageToken as pageToken. */
		nextPageToken: Option[String] = None
	)
	
	object Link {
		enum LifecycleStateEnum extends Enum[LifecycleStateEnum] { case LIFECYCLE_STATE_UNSPECIFIED, ACTIVE, DELETE_REQUESTED, UPDATING, CREATING, FAILED }
	}
	case class Link(
	  /** Output only. The resource name of the link. The name can have up to 100 characters. A valid link id (at the end of the link name) must only have alphanumeric characters and underscores within it. "projects/[PROJECT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/links/[LINK_ID]" "organizations/[ORGANIZATION_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/links/[LINK_ID]" "billingAccounts/[BILLING_ACCOUNT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/links/[LINK_ID]" "folders/[FOLDER_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/links/[LINK_ID]" For example:`projects/my-project/locations/global/buckets/my-bucket/links/my_link */
		name: Option[String] = None,
	  /** Optional. Describes this link.The maximum length of the description is 8000 characters. */
		description: Option[String] = None,
	  /** Output only. The creation timestamp of the link. */
		createTime: Option[String] = None,
	  /** Output only. The resource lifecycle state. */
		lifecycleState: Option[Schema.Link.LifecycleStateEnum] = None,
	  /** Optional. The information of a BigQuery Dataset. When a link is created, a BigQuery dataset is created along with it, in the same project as the LogBucket it's linked to. This dataset will also have BigQuery Views corresponding to the LogViews in the bucket. */
		bigqueryDataset: Option[Schema.BigQueryDataset] = None
	)
	
	case class BigQueryDataset(
	  /** Output only. The full resource name of the BigQuery dataset. The DATASET_ID will match the ID of the link, so the link must match the naming restrictions of BigQuery datasets (alphanumeric characters and underscores only).The dataset will have a resource path of "bigquery.googleapis.com/projects/PROJECT_ID/datasets/DATASET_ID" */
		datasetId: Option[String] = None
	)
	
	case class Settings(
	  /** Output only. The resource name of the settings. */
		name: Option[String] = None,
	  /** Optional. The resource name for the configured Cloud KMS key.KMS key name format: "projects/[PROJECT_ID]/locations/[LOCATION]/keyRings/[KEYRING]/cryptoKeys/[KEY]" For example:"projects/my-project/locations/us-central1/keyRings/my-ring/cryptoKeys/my-key"To enable CMEK, set this field to a valid kms_key_name for which the associated service account has the required roles/cloudkms.cryptoKeyEncrypterDecrypter role assigned for the key.The Cloud KMS key used by the Log Router can be updated by changing the kms_key_name to a new valid key name.To disable CMEK for the Log Router, set this field to an empty string.See Enabling CMEK for Log Router (https://cloud.google.com/logging/docs/routing/managed-encryption) for more information. */
		kmsKeyName: Option[String] = None,
	  /** Output only. The service account that will be used by the Log Router to access your Cloud KMS key.Before enabling CMEK, you must first assign the role roles/cloudkms.cryptoKeyEncrypterDecrypter to the service account that will be used to access your Cloud KMS key. Use GetSettings to obtain the service account ID.See Enabling CMEK for Log Router (https://cloud.google.com/logging/docs/routing/managed-encryption) for more information. */
		kmsServiceAccountId: Option[String] = None,
	  /** Optional. The storage location for the _Default and _Required log buckets of newly created projects and folders, unless the storage location is explicitly provided.Example value: europe-west1.Note: this setting does not affect the location of resources where a location is explicitly provided when created, such as custom log buckets. */
		storageLocation: Option[String] = None,
	  /** Optional. If set to true, the _Default sink in newly created projects and folders will created in a disabled state. This can be used to automatically disable log storage if there is already an aggregated sink configured in the hierarchy. The _Default sink can be re-enabled manually if needed. */
		disableDefaultSink: Option[Boolean] = None,
	  /** Optional. Overrides the built-in configuration for _Default sink. */
		defaultSinkConfig: Option[Schema.DefaultSinkConfig] = None,
	  /** Output only. The service account for the given resource container, such as project or folder. Log sinks use this service account as their writer_identity if no custom service account is provided in the request when calling the create sink method. */
		loggingServiceAccountId: Option[String] = None
	)
	
	object DefaultSinkConfig {
		enum ModeEnum extends Enum[ModeEnum] { case FILTER_WRITE_MODE_UNSPECIFIED, APPEND, OVERWRITE }
	}
	case class DefaultSinkConfig(
	  /** Optional. An advanced logs filter (https://cloud.google.com/logging/docs/view/advanced-queries). The only exported log entries are those that are in the resource owning the sink and that match the filter.For example:logName="projects/[PROJECT_ID]/logs/[LOG_ID]" AND severity>=ERRORTo match all logs, don't add exclusions and use the following line as the value of filter:logName:&#42;Cannot be empty or unset when the value of mode is OVERWRITE. */
		filter: Option[String] = None,
	  /** Optional. Specifies the set of exclusions to be added to the _Default sink in newly created resource containers. */
		exclusions: Option[List[Schema.LogExclusion]] = None,
	  /** Required. Determines the behavior to apply to the built-in _Default sink inclusion filter.Exclusions are always appended, as built-in _Default sinks have no exclusions. */
		mode: Option[Schema.DefaultSinkConfig.ModeEnum] = None
	)
	
	case class ListSavedQueriesResponse(
	  /** A list of saved queries. */
		savedQueries: Option[List[Schema.SavedQuery]] = None,
	  /** If there might be more results than appear in this response, then nextPageToken is included. To get the next set of results, call the same method again using the value of nextPageToken as pageToken. */
		nextPageToken: Option[String] = None,
	  /** The unreachable resources. It can be either 1) a saved query if a specific query is unreachable or 2) a location if a specific location is unreachabe. "projects/[PROJECT_ID]/locations/[LOCATION_ID]/savedQueries/[QUERY_ID]" "projects/[PROJECT_ID]/locations/[LOCATION_ID]" For example: "projects/my-project/locations/global/savedQueries/12345678" "projects/my-project/locations/global" If there are unreachable resources, the response will first return pages that contain saved queries, and then return pages that contain the unreachable resources. */
		unreachable: Option[List[String]] = None
	)
	
	object SavedQuery {
		enum VisibilityEnum extends Enum[VisibilityEnum] { case VISIBILITY_UNSPECIFIED, PRIVATE, SHARED }
	}
	case class SavedQuery(
	  /** Output only. Resource name of the saved query.In the format: "projects/[PROJECT_ID]/locations/[LOCATION_ID]/savedQueries/[QUERY_ID]" For a list of supported locations, see Supported Regions (https://cloud.google.com/logging/docs/region-support#bucket-regions)After the saved query is created, the location cannot be changed.If the user doesn't provide a QUERY_ID, the system will generate an alphanumeric ID. */
		name: Option[String] = None,
	  /** Required. The user specified title for the SavedQuery. */
		displayName: Option[String] = None,
	  /** Optional. A human readable description of the saved query. */
		description: Option[String] = None,
	  /** Logging query that can be executed in Logs Explorer or via Logging API. */
		loggingQuery: Option[Schema.LoggingQuery] = None,
	  /** Analytics query that can be executed in Log Analytics. */
		opsAnalyticsQuery: Option[Schema.OpsAnalyticsQuery] = None,
	  /** Output only. The timestamp when the saved query was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp when the saved query was last updated. */
		updateTime: Option[String] = None,
	  /** Required. The visibility status of this query, which determines its ownership. */
		visibility: Option[Schema.SavedQuery.VisibilityEnum] = None
	)
	
	case class LoggingQuery(
	  /** Required. An advanced query using the Logging Query Language (https://cloud.google.com/logging/docs/view/logging-query-language). The maximum length of the filter is 20000 characters. */
		filter: Option[String] = None,
	  /** Optional. The set of summary fields to display for this saved query. */
		summaryFields: Option[List[Schema.SummaryField]] = None,
	  /** Characters will be counted from the start of the string. */
		summaryFieldStart: Option[Int] = None,
	  /** Characters will be counted from the end of the string. */
		summaryFieldEnd: Option[Int] = None
	)
	
	case class SummaryField(
	  /** Optional. The field from the LogEntry to include in the summary line, for example resource.type or jsonPayload.name. */
		field: Option[String] = None
	)
	
	case class OpsAnalyticsQuery(
	  /** Required. A logs analytics SQL query, which generally follows BigQuery format.This is the SQL query that appears in the Log Analytics UI's query editor. */
		sqlQueryText: Option[String] = None
	)
	
	case class ListRecentQueriesResponse(
	  /** A list of recent queries. */
		recentQueries: Option[List[Schema.RecentQuery]] = None,
	  /** If there might be more results than appear in this response, then nextPageToken is included. To get the next set of results, call the same method again using the value of nextPageToken as pageToken. */
		nextPageToken: Option[String] = None,
	  /** The unreachable resources. Each resource can be either 1) a saved query if a specific query is unreachable or 2) a location if a specific location is unreachable. "projects/[PROJECT_ID]/locations/[LOCATION_ID]/recentQueries/[QUERY_ID]" "projects/[PROJECT_ID]/locations/[LOCATION_ID]" For example:"projects/my-project/locations/global/recentQueries/12345678" "projects/my-project/locations/global"If there are unreachable resources, the response will first return pages that contain recent queries, and then return pages that contain the unreachable resources. */
		unreachable: Option[List[String]] = None
	)
	
	case class RecentQuery(
	  /** Output only. Resource name of the recent query.In the format: "projects/[PROJECT_ID]/locations/[LOCATION_ID]/recentQueries/[QUERY_ID]" For a list of supported locations, see Supported Regions (https://cloud.google.com/logging/docs/region-support)The QUERY_ID is a system generated alphanumeric ID. */
		name: Option[String] = None,
	  /** Logging query that can be executed in Logs Explorer or via Logging API. */
		loggingQuery: Option[Schema.LoggingQuery] = None,
	  /** Analytics query that can be executed in Log Analytics. */
		opsAnalyticsQuery: Option[Schema.OpsAnalyticsQuery] = None,
	  /** Output only. The timestamp when this query was last run. */
		lastRunTime: Option[String] = None
	)
	
	case class CopyLogEntriesRequest(
	  /** Required. Log bucket from which to copy log entries.For example:"projects/my-project/locations/global/buckets/my-source-bucket" */
		name: Option[String] = None,
	  /** Optional. A filter specifying which log entries to copy. The filter must be no more than 20k characters. An empty filter matches all log entries. */
		filter: Option[String] = None,
	  /** Required. Destination to which to copy log entries. */
		destination: Option[String] = None
	)
	
	case class WriteLogEntriesRequest(
	  /** Optional. A default log resource name that is assigned to all log entries in entries that do not specify a value for log_name: projects/[PROJECT_ID]/logs/[LOG_ID] organizations/[ORGANIZATION_ID]/logs/[LOG_ID] billingAccounts/[BILLING_ACCOUNT_ID]/logs/[LOG_ID] folders/[FOLDER_ID]/logs/[LOG_ID][LOG_ID] must be URL-encoded. For example: "projects/my-project-id/logs/syslog" "organizations/123/logs/cloudaudit.googleapis.com%2Factivity" The permission logging.logEntries.create is needed on each project, organization, billing account, or folder that is receiving new log entries, whether the resource is specified in logName or in an individual log entry. */
		logName: Option[String] = None,
	  /** Optional. A default monitored resource object that is assigned to all log entries in entries that do not specify a value for resource. Example: { "type": "gce_instance", "labels": { "zone": "us-central1-a", "instance_id": "00000000000000000000" }} See LogEntry. */
		resource: Option[Schema.MonitoredResource] = None,
	  /** Optional. Default labels that are added to the labels field of all log entries in entries. If a log entry already has a label with the same key as a label in this parameter, then the log entry's label is not changed. See LogEntry. */
		labels: Option[Map[String, String]] = None,
	  /** Required. The log entries to send to Logging. The order of log entries in this list does not matter. Values supplied in this method's log_name, resource, and labels fields are copied into those log entries in this list that do not include values for their corresponding fields. For more information, see the LogEntry type.If the timestamp or insert_id fields are missing in log entries, then this method supplies the current time or a unique identifier, respectively. The supplied values are chosen so that, among the log entries that did not supply their own values, the entries earlier in the list will sort before the entries later in the list. See the entries.list method.Log entries with timestamps that are more than the logs retention period (https://cloud.google.com/logging/quotas) in the past or more than 24 hours in the future will not be available when calling entries.list. However, those log entries can still be exported with LogSinks (https://cloud.google.com/logging/docs/api/tasks/exporting-logs).To improve throughput and to avoid exceeding the quota limit (https://cloud.google.com/logging/quotas) for calls to entries.write, you should try to include several log entries in this list, rather than calling this method for each individual log entry. */
		entries: Option[List[Schema.LogEntry]] = None,
	  /** Optional. Whether a batch's valid entries should be written even if some other entry failed due to a permanent error such as INVALID_ARGUMENT or PERMISSION_DENIED. If any entry failed, then the response status is the response status of one of the failed entries. The response will include error details in WriteLogEntriesPartialErrors.log_entry_errors keyed by the entries' zero-based index in the entries. Failed requests for which no entries are written will not include per-entry errors. */
		partialSuccess: Option[Boolean] = None,
	  /** Optional. If true, the request should expect normal response, but the entries won't be persisted nor exported. Useful for checking whether the logging API endpoints are working properly before sending valuable data. */
		dryRun: Option[Boolean] = None
	)
	
	case class MonitoredResource(
	  /** Required. The monitored resource type. This field must match the type field of a MonitoredResourceDescriptor object. For example, the type of a Compute Engine VM instance is gce_instance. Some descriptors include the service name in the type; for example, the type of a Datastream stream is datastream.googleapis.com/Stream. */
		`type`: Option[String] = None,
	  /** Required. Values for all of the labels listed in the associated monitored resource descriptor. For example, Compute Engine VM instances use the labels "project_id", "instance_id", and "zone". */
		labels: Option[Map[String, String]] = None
	)
	
	object LogEntry {
		enum SeverityEnum extends Enum[SeverityEnum] { case DEFAULT, DEBUG, INFO, NOTICE, WARNING, ERROR, CRITICAL, ALERT, EMERGENCY }
	}
	case class LogEntry(
	  /** Required. The resource name of the log to which this log entry belongs: "projects/[PROJECT_ID]/logs/[LOG_ID]" "organizations/[ORGANIZATION_ID]/logs/[LOG_ID]" "billingAccounts/[BILLING_ACCOUNT_ID]/logs/[LOG_ID]" "folders/[FOLDER_ID]/logs/[LOG_ID]" A project number may be used in place of PROJECT_ID. The project number is translated to its corresponding PROJECT_ID internally and the log_name field will contain PROJECT_ID in queries and exports.[LOG_ID] must be URL-encoded within log_name. Example: "organizations/1234567890/logs/cloudresourcemanager.googleapis.com%2Factivity".[LOG_ID] must be less than 512 characters long and can only include the following characters: upper and lower case alphanumeric characters, forward-slash, underscore, hyphen, and period.For backward compatibility, if log_name begins with a forward-slash, such as /projects/..., then the log entry is processed as usual, but the forward-slash is removed. Listing the log entry will not show the leading slash and filtering for a log name with a leading slash will never return any results. */
		logName: Option[String] = None,
	  /** Required. The monitored resource that produced this log entry.Example: a log entry that reports a database error would be associated with the monitored resource designating the particular database that reported the error. */
		resource: Option[Schema.MonitoredResource] = None,
	  /** The log entry payload, represented as a protocol buffer. Some Google Cloud Platform services use this field for their log entry payloads.The following protocol buffer types are supported; user-defined types are not supported:"type.googleapis.com/google.cloud.audit.AuditLog" "type.googleapis.com/google.appengine.logging.v1.RequestLog" */
		protoPayload: Option[Map[String, JsValue]] = None,
	  /** The log entry payload, represented as a Unicode string (UTF-8). */
		textPayload: Option[String] = None,
	  /** The log entry payload, represented as a structure that is expressed as a JSON object. */
		jsonPayload: Option[Map[String, JsValue]] = None,
	  /** Optional. The time the event described by the log entry occurred. This time is used to compute the log entry's age and to enforce the logs retention period. If this field is omitted in a new log entry, then Logging assigns it the current time. Timestamps have nanosecond accuracy, but trailing zeros in the fractional seconds might be omitted when the timestamp is displayed.Incoming log entries must have timestamps that don't exceed the logs retention period (https://cloud.google.com/logging/quotas#logs_retention_periods) in the past, and that don't exceed 24 hours in the future. Log entries outside those time boundaries are rejected by Logging. */
		timestamp: Option[String] = None,
	  /** Output only. The time the log entry was received by Logging. */
		receiveTimestamp: Option[String] = None,
	  /** Optional. The severity of the log entry. The default value is LogSeverity.DEFAULT. */
		severity: Option[Schema.LogEntry.SeverityEnum] = None,
	  /** Optional. A unique identifier for the log entry. If you provide a value, then Logging considers other log entries in the same project, with the same timestamp, and with the same insert_id to be duplicates which are removed in a single query result. However, there are no guarantees of de-duplication in the export of logs.If the insert_id is omitted when writing a log entry, the Logging API assigns its own unique identifier in this field.In queries, the insert_id is also used to order log entries that have the same log_name and timestamp values. */
		insertId: Option[String] = None,
	  /** Optional. Information about the HTTP request associated with this log entry, if applicable. */
		httpRequest: Option[Schema.HttpRequest] = None,
	  /** Optional. A map of key, value pairs that provides additional information about the log entry. The labels can be user-defined or system-defined.User-defined labels are arbitrary key, value pairs that you can use to classify logs.System-defined labels are defined by GCP services for platform logs. They have two components - a service namespace component and the attribute name. For example: compute.googleapis.com/resource_name.Cloud Logging truncates label keys that exceed 512 B and label values that exceed 64 KB upon their associated log entry being written. The truncation is indicated by an ellipsis at the end of the character string. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. Deprecated. This field is not used by Logging. Any value written to it is cleared. */
		metadata: Option[Schema.MonitoredResourceMetadata] = None,
	  /** Optional. Information about an operation associated with the log entry, if applicable. */
		operation: Option[Schema.LogEntryOperation] = None,
	  /** Optional. The REST resource name of the trace being written to Cloud Trace (https://cloud.google.com/trace) in association with this log entry. For example, if your trace data is stored in the Cloud project "my-trace-project" and if the service that is creating the log entry receives a trace header that includes the trace ID "12345", then the service should use "projects/my-trace-project/traces/12345".The trace field provides the link between logs and traces. By using this field, you can navigate from a log entry to a trace. */
		trace: Option[String] = None,
	  /** Optional. The ID of the Cloud Trace (https://cloud.google.com/trace) span associated with the current operation in which the log is being written. For example, if a span has the REST resource name of "projects/some-project/traces/some-trace/spans/some-span-id", then the span_id field is "some-span-id".A Span (https://cloud.google.com/trace/docs/reference/v2/rest/v2/projects.traces/batchWrite#Span) represents a single operation within a trace. Whereas a trace may involve multiple different microservices running on multiple different machines, a span generally corresponds to a single logical operation being performed in a single instance of a microservice on one specific machine. Spans are the nodes within the tree that is a trace.Applications that are instrumented for tracing (https://cloud.google.com/trace/docs/setup) will generally assign a new, unique span ID on each incoming request. It is also common to create and record additional spans corresponding to internal processing elements as well as issuing requests to dependencies.The span ID is expected to be a 16-character, hexadecimal encoding of an 8-byte array and should not be zero. It should be unique within the trace and should, ideally, be generated in a manner that is uniformly random.Example values: 000000000000004a 7a2190356c3fc94b 0000f00300090021 d39223e101960076 */
		spanId: Option[String] = None,
	  /** Optional. The sampling decision of the span associated with the log entry at the time the log entry was created. This field corresponds to the sampled flag in the W3C trace-context specification (https://www.w3.org/TR/trace-context/#sampled-flag). A non-sampled trace value is still useful as a request correlation identifier. The default is False. */
		traceSampled: Option[Boolean] = None,
	  /** Optional. Source code location information associated with the log entry, if any. */
		sourceLocation: Option[Schema.LogEntrySourceLocation] = None,
	  /** Optional. Information indicating this LogEntry is part of a sequence of multiple log entries split from a single LogEntry. */
		split: Option[Schema.LogSplit] = None,
	  /** Output only. The Error Reporting (https://cloud.google.com/error-reporting) error groups associated with this LogEntry. Error Reporting sets the values for this field during error group creation.For more information, see View error details( https://cloud.google.com/error-reporting/docs/viewing-errors#view_error_details)This field isn't available during log routing (https://cloud.google.com/logging/docs/routing/overview) */
		errorGroups: Option[List[Schema.LogErrorGroup]] = None
	)
	
	case class HttpRequest(
	  /** The request method. Examples: "GET", "HEAD", "PUT", "POST". */
		requestMethod: Option[String] = None,
	  /** The scheme (http, https), the host name, the path and the query portion of the URL that was requested. Example: "http://example.com/some/info?color=red". */
		requestUrl: Option[String] = None,
	  /** The size of the HTTP request message in bytes, including the request headers and the request body. */
		requestSize: Option[String] = None,
	  /** The response code indicating the status of response. Examples: 200, 404. */
		status: Option[Int] = None,
	  /** The size of the HTTP response message sent back to the client, in bytes, including the response headers and the response body. */
		responseSize: Option[String] = None,
	  /** The user agent sent by the client. Example: "Mozilla/4.0 (compatible; MSIE 6.0; Windows 98; Q312461; .NET CLR 1.0.3705)". */
		userAgent: Option[String] = None,
	  /** The IP address (IPv4 or IPv6) of the client that issued the HTTP request. This field can include port information. Examples: "192.168.1.1", "10.0.0.1:80", "FE80::0202:B3FF:FE1E:8329". */
		remoteIp: Option[String] = None,
	  /** The IP address (IPv4 or IPv6) of the origin server that the request was sent to. This field can include port information. Examples: "192.168.1.1", "10.0.0.1:80", "FE80::0202:B3FF:FE1E:8329". */
		serverIp: Option[String] = None,
	  /** The referer URL of the request, as defined in HTTP/1.1 Header Field Definitions (https://datatracker.ietf.org/doc/html/rfc2616#section-14.36). */
		referer: Option[String] = None,
	  /** The request processing latency on the server, from the time the request was received until the response was sent. For WebSocket connections, this field refers to the entire time duration of the connection. */
		latency: Option[String] = None,
	  /** Whether or not a cache lookup was attempted. */
		cacheLookup: Option[Boolean] = None,
	  /** Whether or not an entity was served from cache (with or without validation). */
		cacheHit: Option[Boolean] = None,
	  /** Whether or not the response was validated with the origin server before being served from cache. This field is only meaningful if cache_hit is True. */
		cacheValidatedWithOriginServer: Option[Boolean] = None,
	  /** The number of HTTP response bytes inserted into cache. Set only when a cache fill was attempted. */
		cacheFillBytes: Option[String] = None,
	  /** Protocol used for the request. Examples: "HTTP/1.1", "HTTP/2" */
		protocol: Option[String] = None
	)
	
	case class MonitoredResourceMetadata(
	  /** Output only. Values for predefined system metadata labels. System labels are a kind of metadata extracted by Google, including "machine_image", "vpc", "subnet_id", "security_group", "name", etc. System label values can be only strings, Boolean values, or a list of strings. For example: { "name": "my-test-instance", "security_group": ["a", "b", "c"], "spot_instance": false }  */
		systemLabels: Option[Map[String, JsValue]] = None,
	  /** Output only. A map of user-defined metadata labels. */
		userLabels: Option[Map[String, String]] = None
	)
	
	case class LogEntryOperation(
	  /** Optional. An arbitrary operation identifier. Log entries with the same identifier are assumed to be part of the same operation. */
		id: Option[String] = None,
	  /** Optional. An arbitrary producer identifier. The combination of id and producer must be globally unique. Examples for producer: "MyDivision.MyBigCompany.com", "github.com/MyProject/MyApplication". */
		producer: Option[String] = None,
	  /** Optional. Set this to True if this is the first log entry in the operation. */
		first: Option[Boolean] = None,
	  /** Optional. Set this to True if this is the last log entry in the operation. */
		last: Option[Boolean] = None
	)
	
	case class LogEntrySourceLocation(
	  /** Optional. Source file name. Depending on the runtime environment, this might be a simple name or a fully-qualified name. */
		file: Option[String] = None,
	  /** Optional. Line within the source file. 1-based; 0 indicates no line number available. */
		line: Option[String] = None,
	  /** Optional. Human-readable name of the function or method being invoked, with optional context such as the class or package name. This information may be used in contexts such as the logs viewer, where a file and line number are less meaningful. The format can vary by language. For example: qual.if.ied.Class.method (Java), dir/package.func (Go), function (Python). */
		function: Option[String] = None
	)
	
	case class LogSplit(
	  /** A globally unique identifier for all log entries in a sequence of split log entries. All log entries with the same |LogSplit.uid| are assumed to be part of the same sequence of split log entries. */
		uid: Option[String] = None,
	  /** The index of this LogEntry in the sequence of split log entries. Log entries are given |index| values 0, 1, ..., n-1 for a sequence of n log entries. */
		index: Option[Int] = None,
	  /** The total number of log entries that the original LogEntry was split into. */
		totalSplits: Option[Int] = None
	)
	
	case class LogErrorGroup(
	  /** The id is a unique identifier for a particular error group; it is the last part of the error group resource name: /project/[PROJECT_ID]/errors/[ERROR_GROUP_ID]. Example: COShysOX0r_51QE. The id is derived from key parts of the error-log content and is treated as Service Data. For information about how Service Data is handled, see Google Cloud Privacy Notice (https://cloud.google.com/terms/cloud-privacy-notice). */
		id: Option[String] = None
	)
	
	case class WriteLogEntriesResponse(
	
	)
	
	case class ListLogEntriesRequest(
	  /** Optional. Deprecated. Use resource_names instead. One or more project identifiers or project numbers from which to retrieve log entries. Example: "my-project-1A". */
		projectIds: Option[List[String]] = None,
	  /** Required. Names of one or more parent resources from which to retrieve log entries. Resources may either be resource containers or specific LogViews. For the case of resource containers, all logs ingested into that container will be returned regardless of which LogBuckets they are actually stored in - i.e. these queries may fan out to multiple regions. In the event of region unavailability, specify a specific set of LogViews that do not include the unavailable region. projects/[PROJECT_ID] organizations/[ORGANIZATION_ID] billingAccounts/[BILLING_ACCOUNT_ID] folders/[FOLDER_ID] projects/[PROJECT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] organizations/[ORGANIZATION_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] billingAccounts/[BILLING_ACCOUNT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] folders/[FOLDER_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID]Projects listed in the project_ids field are added to this list. A maximum of 100 resources may be specified in a single request. */
		resourceNames: Option[List[String]] = None,
	  /** Optional. A filter that chooses which log entries to return. For more information, see Logging query language (https://cloud.google.com/logging/docs/view/logging-query-language).Only log entries that match the filter are returned. An empty filter matches all log entries in the resources listed in resource_names. Referencing a parent resource that is not listed in resource_names will cause the filter to return no results. The maximum length of a filter is 20,000 characters.To make queries faster, you can make the filter more selective by using restrictions on indexed fields (https://cloud.google.com/logging/docs/view/logging-query-language#indexed-fields) as well as limit the time range of the query by adding range restrictions on the timestamp field. */
		filter: Option[String] = None,
	  /** Optional. How the results should be sorted. Presently, the only permitted values are "timestamp asc" (default) and "timestamp desc". The first option returns entries in order of increasing values of LogEntry.timestamp (oldest first), and the second option returns entries in order of decreasing timestamps (newest first). Entries with equal timestamps are returned in order of their insert_id values.We recommend setting the order_by field to "timestamp desc" when listing recently ingested log entries. If not set, the default value of "timestamp asc" may take a long time to fetch matching logs that are only recently ingested. */
		orderBy: Option[String] = None,
	  /** Optional. The maximum number of results to return from this request. Default is 50. If the value is negative, the request is rejected.The presence of next_page_token in the response indicates that more results might be available. */
		pageSize: Option[Int] = None,
	  /** Optional. If present, then retrieve the next batch of results from the preceding call to this method. page_token must be the value of next_page_token from the previous response. The values of other method parameters should be identical to those in the previous call. */
		pageToken: Option[String] = None
	)
	
	case class ListLogEntriesResponse(
	  /** A list of log entries. If entries is empty, nextPageToken may still be returned, indicating that more entries may exist. See nextPageToken for more information. */
		entries: Option[List[Schema.LogEntry]] = None,
	  /** If there might be more results than those appearing in this response, then nextPageToken is included. To get the next set of results, call this method again using the value of nextPageToken as pageToken.If a value for next_page_token appears and the entries field is empty, it means that the search found no log entries so far but it did not have time to search all the possible log entries. Retry the method with this value for page_token to continue the search. Alternatively, consider speeding up the search by changing your filter to specify a single log name or resource type, or to narrow the time range of the search. */
		nextPageToken: Option[String] = None
	)
	
	case class ListMonitoredResourceDescriptorsResponse(
	  /** A list of resource descriptors. */
		resourceDescriptors: Option[List[Schema.MonitoredResourceDescriptor]] = None,
	  /** If there might be more results than those appearing in this response, then nextPageToken is included. To get the next set of results, call this method again using the value of nextPageToken as pageToken. */
		nextPageToken: Option[String] = None
	)
	
	object MonitoredResourceDescriptor {
		enum LaunchStageEnum extends Enum[LaunchStageEnum] { case LAUNCH_STAGE_UNSPECIFIED, UNIMPLEMENTED, PRELAUNCH, EARLY_ACCESS, ALPHA, BETA, GA, DEPRECATED }
	}
	case class MonitoredResourceDescriptor(
	  /** Optional. The resource name of the monitored resource descriptor: "projects/{project_id}/monitoredResourceDescriptors/{type}" where {type} is the value of the type field in this object and {project_id} is a project ID that provides API-specific context for accessing the type. APIs that do not use project information can use the resource name format "monitoredResourceDescriptors/{type}". */
		name: Option[String] = None,
	  /** Required. The monitored resource type. For example, the type "cloudsql_database" represents databases in Google Cloud SQL. For a list of types, see Monitored resource types (https://cloud.google.com/monitoring/api/resources) and Logging resource types (https://cloud.google.com/logging/docs/api/v2/resource-list). */
		`type`: Option[String] = None,
	  /** Optional. A concise name for the monitored resource type that might be displayed in user interfaces. It should be a Title Cased Noun Phrase, without any article or other determiners. For example, "Google Cloud SQL Database". */
		displayName: Option[String] = None,
	  /** Optional. A detailed description of the monitored resource type that might be used in documentation. */
		description: Option[String] = None,
	  /** Required. A set of labels used to describe instances of this monitored resource type. For example, an individual Google Cloud SQL database is identified by values for the labels "database_id" and "zone". */
		labels: Option[List[Schema.LabelDescriptor]] = None,
	  /** Optional. The launch stage of the monitored resource definition. */
		launchStage: Option[Schema.MonitoredResourceDescriptor.LaunchStageEnum] = None
	)
	
	object LabelDescriptor {
		enum ValueTypeEnum extends Enum[ValueTypeEnum] { case STRING, BOOL, INT64 }
	}
	case class LabelDescriptor(
	  /** The label key. */
		key: Option[String] = None,
	  /** The type of data that can be assigned to the label. */
		valueType: Option[Schema.LabelDescriptor.ValueTypeEnum] = None,
	  /** A human-readable description for the label. */
		description: Option[String] = None
	)
	
	case class ListLogsResponse(
	  /** A list of log names. For example, "projects/my-project/logs/syslog" or "organizations/123/logs/cloudresourcemanager.googleapis.com%2Factivity". */
		logNames: Option[List[String]] = None,
	  /** If there might be more results than those appearing in this response, then nextPageToken is included. To get the next set of results, call this method again using the value of nextPageToken as pageToken. */
		nextPageToken: Option[String] = None
	)
	
	case class TailLogEntriesRequest(
	  /** Required. Name of a parent resource from which to retrieve log entries: projects/[PROJECT_ID] organizations/[ORGANIZATION_ID] billingAccounts/[BILLING_ACCOUNT_ID] folders/[FOLDER_ID]May alternatively be one or more views: projects/[PROJECT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] organizations/[ORGANIZATION_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] billingAccounts/[BILLING_ACCOUNT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] folders/[FOLDER_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/views/[VIEW_ID] */
		resourceNames: Option[List[String]] = None,
	  /** Optional. Only log entries that match the filter are returned. An empty filter matches all log entries in the resources listed in resource_names. Referencing a parent resource that is not listed in resource_names will cause the filter to return no results. The maximum length of a filter is 20,000 characters. */
		filter: Option[String] = None,
	  /** Optional. The amount of time to buffer log entries at the server before being returned to prevent out of order results due to late arriving log entries. Valid values are between 0-60000 milliseconds. Defaults to 2000 milliseconds. */
		bufferWindow: Option[String] = None
	)
	
	case class TailLogEntriesResponse(
	  /** A list of log entries. Each response in the stream will order entries with increasing values of LogEntry.timestamp. Ordering is not guaranteed between separate responses. */
		entries: Option[List[Schema.LogEntry]] = None,
	  /** If entries that otherwise would have been included in the session were not sent back to the client, counts of relevant entries omitted from the session with the reason that they were not included. There will be at most one of each reason per response. The counts represent the number of suppressed entries since the last streamed response. */
		suppressionInfo: Option[List[Schema.SuppressionInfo]] = None
	)
	
	object SuppressionInfo {
		enum ReasonEnum extends Enum[ReasonEnum] { case REASON_UNSPECIFIED, RATE_LIMIT, NOT_CONSUMED }
	}
	case class SuppressionInfo(
	  /** The reason that entries were omitted from the session. */
		reason: Option[Schema.SuppressionInfo.ReasonEnum] = None,
	  /** A lower bound on the count of entries omitted due to reason. */
		suppressedCount: Option[Int] = None
	)
	
	case class ListLogMetricsResponse(
	  /** A list of logs-based metrics. */
		metrics: Option[List[Schema.LogMetric]] = None,
	  /** If there might be more results than appear in this response, then nextPageToken is included. To get the next set of results, call this method again using the value of nextPageToken as pageToken. */
		nextPageToken: Option[String] = None
	)
	
	object LogMetric {
		enum VersionEnum extends Enum[VersionEnum] { case V2, V1 }
	}
	case class LogMetric(
	  /** Required. The client-assigned metric identifier. Examples: "error_count", "nginx/requests".Metric identifiers are limited to 100 characters and can include only the following characters: A-Z, a-z, 0-9, and the special characters _-.,+!&#42;',()%/. The forward-slash character (/) denotes a hierarchy of name pieces, and it cannot be the first character of the name.This field is the [METRIC_ID] part of a metric resource name in the format "projects/PROJECT_ID/metrics/METRIC_ID". Example: If the resource name of a metric is "projects/my-project/metrics/nginx%2Frequests", this field's value is "nginx/requests". */
		name: Option[String] = None,
	  /** Output only. The resource name of the metric: "projects/[PROJECT_ID]/metrics/[METRIC_ID]"  */
		resourceName: Option[String] = None,
	  /** Optional. A description of this metric, which is used in documentation. The maximum length of the description is 8000 characters. */
		description: Option[String] = None,
	  /** Required. An advanced logs filter (https://cloud.google.com/logging/docs/view/advanced_filters) which is used to match log entries. Example: "resource.type=gae_app AND severity>=ERROR" The maximum length of the filter is 20000 characters. */
		filter: Option[String] = None,
	  /** Optional. The resource name of the Log Bucket that owns the Log Metric. Only Log Buckets in projects are supported. The bucket has to be in the same project as the metric.For example:projects/my-project/locations/global/buckets/my-bucketIf empty, then the Log Metric is considered a non-Bucket Log Metric. */
		bucketName: Option[String] = None,
	  /** Optional. If set to True, then this metric is disabled and it does not generate any points. */
		disabled: Option[Boolean] = None,
	  /** Optional. The metric descriptor associated with the logs-based metric. If unspecified, it uses a default metric descriptor with a DELTA metric kind, INT64 value type, with no labels and a unit of "1". Such a metric counts the number of log entries matching the filter expression.The name, type, and description fields in the metric_descriptor are output only, and is constructed using the name and description field in the LogMetric.To create a logs-based metric that records a distribution of log values, a DELTA metric kind with a DISTRIBUTION value type must be used along with a value_extractor expression in the LogMetric.Each label in the metric descriptor must have a matching label name as the key and an extractor expression as the value in the label_extractors map.The metric_kind and value_type fields in the metric_descriptor cannot be updated once initially configured. New labels can be added in the metric_descriptor, but existing labels cannot be modified except for their description. */
		metricDescriptor: Option[Schema.MetricDescriptor] = None,
	  /** Optional. A value_extractor is required when using a distribution logs-based metric to extract the values to record from a log entry. Two functions are supported for value extraction: EXTRACT(field) or REGEXP_EXTRACT(field, regex). The arguments are: field: The name of the log entry field from which the value is to be extracted. regex: A regular expression using the Google RE2 syntax (https://github.com/google/re2/wiki/Syntax) with a single capture group to extract data from the specified log entry field. The value of the field is converted to a string before applying the regex. It is an error to specify a regex that does not include exactly one capture group.The result of the extraction must be convertible to a double type, as the distribution always records double values. If either the extraction or the conversion to double fails, then those values are not recorded in the distribution.Example: REGEXP_EXTRACT(jsonPayload.request, ".&#42;quantity=(\d+).&#42;") */
		valueExtractor: Option[String] = None,
	  /** Optional. A map from a label key string to an extractor expression which is used to extract data from a log entry field and assign as the label value. Each label key specified in the LabelDescriptor must have an associated extractor expression in this map. The syntax of the extractor expression is the same as for the value_extractor field.The extracted value is converted to the type defined in the label descriptor. If either the extraction or the type conversion fails, the label will have a default value. The default value for a string label is an empty string, for an integer label its 0, and for a boolean label its false.Note that there are upper bounds on the maximum number of labels and the number of active time series that are allowed in a project. */
		labelExtractors: Option[Map[String, String]] = None,
	  /** Optional. The bucket_options are required when the logs-based metric is using a DISTRIBUTION value type and it describes the bucket boundaries used to create a histogram of the extracted values. */
		bucketOptions: Option[Schema.BucketOptions] = None,
	  /** Output only. The creation timestamp of the metric.This field may not be present for older metrics. */
		createTime: Option[String] = None,
	  /** Output only. The last update timestamp of the metric.This field may not be present for older metrics. */
		updateTime: Option[String] = None,
	  /** Deprecated. The API version that created or updated this metric. The v2 format is used by default and cannot be changed. */
		version: Option[Schema.LogMetric.VersionEnum] = None
	)
	
	object MetricDescriptor {
		enum MetricKindEnum extends Enum[MetricKindEnum] { case METRIC_KIND_UNSPECIFIED, GAUGE, DELTA, CUMULATIVE }
		enum ValueTypeEnum extends Enum[ValueTypeEnum] { case VALUE_TYPE_UNSPECIFIED, BOOL, INT64, DOUBLE, STRING, DISTRIBUTION, MONEY }
		enum LaunchStageEnum extends Enum[LaunchStageEnum] { case LAUNCH_STAGE_UNSPECIFIED, UNIMPLEMENTED, PRELAUNCH, EARLY_ACCESS, ALPHA, BETA, GA, DEPRECATED }
	}
	case class MetricDescriptor(
	  /** The resource name of the metric descriptor. */
		name: Option[String] = None,
	  /** The metric type, including its DNS name prefix. The type is not URL-encoded. All user-defined metric types have the DNS name custom.googleapis.com or external.googleapis.com. Metric types should use a natural hierarchical grouping. For example: "custom.googleapis.com/invoice/paid/amount" "external.googleapis.com/prometheus/up" "appengine.googleapis.com/http/server/response_latencies"  */
		`type`: Option[String] = None,
	  /** The set of labels that can be used to describe a specific instance of this metric type. For example, the appengine.googleapis.com/http/server/response_latencies metric type has a label for the HTTP response code, response_code, so you can look at latencies for successful responses or just for responses that failed. */
		labels: Option[List[Schema.LabelDescriptor]] = None,
	  /** Whether the metric records instantaneous values, changes to a value, etc. Some combinations of metric_kind and value_type might not be supported. */
		metricKind: Option[Schema.MetricDescriptor.MetricKindEnum] = None,
	  /** Whether the measurement is an integer, a floating-point number, etc. Some combinations of metric_kind and value_type might not be supported. */
		valueType: Option[Schema.MetricDescriptor.ValueTypeEnum] = None,
	  /** The units in which the metric value is reported. It is only applicable if the value_type is INT64, DOUBLE, or DISTRIBUTION. The unit defines the representation of the stored metric values.Different systems might scale the values to be more easily displayed (so a value of 0.02kBy might be displayed as 20By, and a value of 3523kBy might be displayed as 3.5MBy). However, if the unit is kBy, then the value of the metric is always in thousands of bytes, no matter how it might be displayed.If you want a custom metric to record the exact number of CPU-seconds used by a job, you can create an INT64 CUMULATIVE metric whose unit is s{CPU} (or equivalently 1s{CPU} or just s). If the job uses 12,005 CPU-seconds, then the value is written as 12005.Alternatively, if you want a custom metric to record data in a more granular way, you can create a DOUBLE CUMULATIVE metric whose unit is ks{CPU}, and then write the value 12.005 (which is 12005/1000), or use Kis{CPU} and write 11.723 (which is 12005/1024).The supported units are a subset of The Unified Code for Units of Measure (https://unitsofmeasure.org/ucum.html) standard:Basic units (UNIT) bit bit By byte s second min minute h hour d day 1 dimensionlessPrefixes (PREFIX) k kilo (10^3) M mega (10^6) G giga (10^9) T tera (10^12) P peta (10^15) E exa (10^18) Z zetta (10^21) Y yotta (10^24) m milli (10^-3) u micro (10^-6) n nano (10^-9) p pico (10^-12) f femto (10^-15) a atto (10^-18) z zepto (10^-21) y yocto (10^-24) Ki kibi (2^10) Mi mebi (2^20) Gi gibi (2^30) Ti tebi (2^40) Pi pebi (2^50)GrammarThe grammar also includes these connectors: / division or ratio (as an infix operator). For examples, kBy/{email} or MiBy/10ms (although you should almost never have /s in a metric unit; rates should always be computed at query time from the underlying cumulative or delta value). . multiplication or composition (as an infix operator). For examples, GBy.d or k{watt}.h.The grammar for a unit is as follows: Expression = Component { "." Component } { "/" Component } ; Component = ( [ PREFIX ] UNIT | "%" ) [ Annotation ] | Annotation | "1" ; Annotation = "{" NAME "}" ; Notes: Annotation is just a comment if it follows a UNIT. If the annotation is used alone, then the unit is equivalent to 1. For examples, {request}/s == 1/s, By{transmitted}/s == By/s. NAME is a sequence of non-blank printable ASCII characters not containing { or }. 1 represents a unitary dimensionless unit (https://en.wikipedia.org/wiki/Dimensionless_quantity) of 1, such as in 1/s. It is typically used when none of the basic units are appropriate. For example, "new users per day" can be represented as 1/d or {new-users}/d (and a metric value 5 would mean "5 new users). Alternatively, "thousands of page views per day" would be represented as 1000/d or k1/d or k{page_views}/d (and a metric value of 5.3 would mean "5300 page views per day"). % represents dimensionless value of 1/100, and annotates values giving a percentage (so the metric values are typically in the range of 0..100, and a metric value 3 means "3 percent"). 10^2.% indicates a metric contains a ratio, typically in the range 0..1, that will be multiplied by 100 and displayed as a percentage (so a metric value 0.03 means "3 percent"). */
		unit: Option[String] = None,
	  /** A detailed description of the metric, which can be used in documentation. */
		description: Option[String] = None,
	  /** A concise name for the metric, which can be displayed in user interfaces. Use sentence case without an ending period, for example "Request count". This field is optional but it is recommended to be set for any metrics associated with user-visible concepts, such as Quota. */
		displayName: Option[String] = None,
	  /** Optional. Metadata which can be used to guide usage of the metric. */
		metadata: Option[Schema.MetricDescriptorMetadata] = None,
	  /** Optional. The launch stage of the metric definition. */
		launchStage: Option[Schema.MetricDescriptor.LaunchStageEnum] = None,
	  /** Read-only. If present, then a time series, which is identified partially by a metric type and a MonitoredResourceDescriptor, that is associated with this metric type can only be associated with one of the monitored resource types listed here. */
		monitoredResourceTypes: Option[List[String]] = None
	)
	
	object MetricDescriptorMetadata {
		enum LaunchStageEnum extends Enum[LaunchStageEnum] { case LAUNCH_STAGE_UNSPECIFIED, UNIMPLEMENTED, PRELAUNCH, EARLY_ACCESS, ALPHA, BETA, GA, DEPRECATED }
		enum TimeSeriesResourceHierarchyLevelEnum extends Enum[TimeSeriesResourceHierarchyLevelEnum] { case TIME_SERIES_RESOURCE_HIERARCHY_LEVEL_UNSPECIFIED, PROJECT, ORGANIZATION, FOLDER }
	}
	case class MetricDescriptorMetadata(
	  /** Deprecated. Must use the MetricDescriptor.launch_stage instead. */
		launchStage: Option[Schema.MetricDescriptorMetadata.LaunchStageEnum] = None,
	  /** The sampling period of metric data points. For metrics which are written periodically, consecutive data points are stored at this time interval, excluding data loss due to errors. Metrics with a higher granularity have a smaller sampling period. */
		samplePeriod: Option[String] = None,
	  /** The delay of data points caused by ingestion. Data points older than this age are guaranteed to be ingested and available to be read, excluding data loss due to errors. */
		ingestDelay: Option[String] = None,
	  /** The scope of the timeseries data of the metric. */
		timeSeriesResourceHierarchyLevel: Option[List[Schema.MetricDescriptorMetadata.TimeSeriesResourceHierarchyLevelEnum]] = None
	)
	
	case class BucketOptions(
	  /** The linear bucket. */
		linearBuckets: Option[Schema.Linear] = None,
	  /** The exponential buckets. */
		exponentialBuckets: Option[Schema.Exponential] = None,
	  /** The explicit buckets. */
		explicitBuckets: Option[Schema.Explicit] = None
	)
	
	case class Linear(
	  /** Must be greater than 0. */
		numFiniteBuckets: Option[Int] = None,
	  /** Must be greater than 0. */
		width: Option[BigDecimal] = None,
	  /** Lower bound of the first bucket. */
		offset: Option[BigDecimal] = None
	)
	
	case class Exponential(
	  /** Must be greater than 0. */
		numFiniteBuckets: Option[Int] = None,
	  /** Must be greater than 1. */
		growthFactor: Option[BigDecimal] = None,
	  /** Must be greater than 0. */
		scale: Option[BigDecimal] = None
	)
	
	case class Explicit(
	  /** The values must be monotonically increasing. */
		bounds: Option[List[BigDecimal]] = None
	)
	
	case class RequestLog(
	  /** Application that handled this request. */
		appId: Option[String] = None,
	  /** Module of the application that handled this request. */
		moduleId: Option[String] = None,
	  /** Version of the application that handled this request. */
		versionId: Option[String] = None,
	  /** Globally unique identifier for a request, which is based on the request start time. Request IDs for requests which started later will compare greater as strings than those for requests which started earlier. */
		requestId: Option[String] = None,
	  /** Origin IP address. */
		ip: Option[String] = None,
	  /** Time when the request started. */
		startTime: Option[String] = None,
	  /** Time when the request finished. */
		endTime: Option[String] = None,
	  /** Latency of the request. */
		latency: Option[String] = None,
	  /** Number of CPU megacycles used to process request. */
		megaCycles: Option[String] = None,
	  /** Request method. Example: "GET", "HEAD", "PUT", "POST", "DELETE". */
		method: Option[String] = None,
	  /** Contains the path and query portion of the URL that was requested. For example, if the URL was "http://example.com/app?name=val", the resource would be "/app?name=val". The fragment identifier, which is identified by the # character, is not included. */
		resource: Option[String] = None,
	  /** HTTP version of request. Example: "HTTP/1.1". */
		httpVersion: Option[String] = None,
	  /** HTTP response status code. Example: 200, 404. */
		status: Option[Int] = None,
	  /** Size in bytes sent back to client by request. */
		responseSize: Option[String] = None,
	  /** Referrer URL of request. */
		referrer: Option[String] = None,
	  /** User agent that made the request. */
		userAgent: Option[String] = None,
	  /** The logged-in user who made the request.Most likely, this is the part of the user's email before the @ sign. The field value is the same for different requests from the same user, but different users can have similar names. This information is also available to the application via the App Engine Users API.This field will be populated starting with App Engine 1.9.21. */
		nickname: Option[String] = None,
	  /** File or class that handled the request. */
		urlMapEntry: Option[String] = None,
	  /** Internet host and port number of the resource being requested. */
		host: Option[String] = None,
	  /** An indication of the relative cost of serving this request. */
		cost: Option[BigDecimal] = None,
	  /** Queue name of the request, in the case of an offline request. */
		taskQueueName: Option[String] = None,
	  /** Task name of the request, in the case of an offline request. */
		taskName: Option[String] = None,
	  /** Whether this was a loading request for the instance. */
		wasLoadingRequest: Option[Boolean] = None,
	  /** Time this request spent in the pending request queue. */
		pendingTime: Option[String] = None,
	  /** If the instance processing this request belongs to a manually scaled module, then this is the 0-based index of the instance. Otherwise, this value is -1. */
		instanceIndex: Option[Int] = None,
	  /** Whether this request is finished or active. */
		finished: Option[Boolean] = None,
	  /** Whether this is the first RequestLog entry for this request. If an active request has several RequestLog entries written to Stackdriver Logging, then this field will be set for one of them. */
		first: Option[Boolean] = None,
	  /** An identifier for the instance that handled the request. */
		instanceId: Option[String] = None,
	  /** A list of log lines emitted by the application while serving this request. */
		line: Option[List[Schema.LogLine]] = None,
	  /** App Engine release version. */
		appEngineRelease: Option[String] = None,
	  /** Stackdriver Trace identifier for this request. */
		traceId: Option[String] = None,
	  /** Stackdriver Trace span identifier for this request. */
		spanId: Option[String] = None,
	  /** If true, the value in the 'trace_id' field was sampled for storage in a trace backend. */
		traceSampled: Option[Boolean] = None,
	  /** Source code for the application that handled this request. There can be more than one source reference per deployed application if source code is distributed among multiple repositories. */
		sourceReference: Option[List[Schema.SourceReference]] = None
	)
	
	object LogLine {
		enum SeverityEnum extends Enum[SeverityEnum] { case DEFAULT, DEBUG, INFO, NOTICE, WARNING, ERROR, CRITICAL, ALERT, EMERGENCY }
	}
	case class LogLine(
	  /** Approximate time when this log entry was made. */
		time: Option[String] = None,
	  /** Severity of this log entry. */
		severity: Option[Schema.LogLine.SeverityEnum] = None,
	  /** App-provided log message. */
		logMessage: Option[String] = None,
	  /** Where in the source code this log message was written. */
		sourceLocation: Option[Schema.SourceLocation] = None
	)
	
	case class SourceLocation(
	  /** Source file name. Depending on the runtime environment, this might be a simple name or a fully-qualified name. */
		file: Option[String] = None,
	  /** Line within the source file. */
		line: Option[String] = None,
	  /** Human-readable name of the function or method being invoked, with optional context such as the class or package name. This information is used in contexts such as the logs viewer, where a file and line number are less meaningful. The format can vary by language. For example: qual.if.ied.Class.method (Java), dir/package.func (Go), function (Python). */
		functionName: Option[String] = None
	)
	
	case class SourceReference(
	  /** Optional. A URI string identifying the repository. Example: "https://github.com/GoogleCloudPlatform/kubernetes.git" */
		repository: Option[String] = None,
	  /** The canonical and persistent identifier of the deployed revision. Example (git): "0035781c50ec7aa23385dc841529ce8a4b70db1b" */
		revisionId: Option[String] = None
	)
	
	object CopyLogEntriesMetadata {
		enum StateEnum extends Enum[StateEnum] { case OPERATION_STATE_UNSPECIFIED, OPERATION_STATE_SCHEDULED, OPERATION_STATE_WAITING_FOR_PERMISSIONS, OPERATION_STATE_RUNNING, OPERATION_STATE_SUCCEEDED, OPERATION_STATE_FAILED, OPERATION_STATE_CANCELLED, OPERATION_STATE_PENDING }
	}
	case class CopyLogEntriesMetadata(
	  /** The create time of an operation. */
		startTime: Option[String] = None,
	  /** The end time of an operation. */
		endTime: Option[String] = None,
	  /** Output only. State of an operation. */
		state: Option[Schema.CopyLogEntriesMetadata.StateEnum] = None,
	  /** Identifies whether the user has requested cancellation of the operation. */
		cancellationRequested: Option[Boolean] = None,
	  /** CopyLogEntries RPC request. This field is deprecated and not used. */
		request: Option[Schema.CopyLogEntriesRequest] = None,
	  /** Estimated progress of the operation (0 - 100%). */
		progress: Option[Int] = None,
	  /** The IAM identity of a service account that must be granted access to the destination.If the service account is not granted permission to the destination within an hour, the operation will be cancelled.For example: "serviceAccount:foo@bar.com" */
		writerIdentity: Option[String] = None,
	  /** Source from which to copy log entries.For example, a log bucket:"projects/my-project/locations/global/buckets/my-source-bucket" */
		source: Option[String] = None,
	  /** Destination to which to copy log entries.For example, a Cloud Storage bucket:"storage.googleapis.com/my-cloud-storage-bucket" */
		destination: Option[String] = None,
	  /** Name of the verb executed by the operation.For example,"copy" */
		verb: Option[String] = None
	)
	
	case class CopyLogEntriesResponse(
	  /** Number of log entries copied. */
		logEntriesCopiedCount: Option[String] = None
	)
	
	object BucketMetadata {
		enum StateEnum extends Enum[StateEnum] { case OPERATION_STATE_UNSPECIFIED, OPERATION_STATE_SCHEDULED, OPERATION_STATE_WAITING_FOR_PERMISSIONS, OPERATION_STATE_RUNNING, OPERATION_STATE_SUCCEEDED, OPERATION_STATE_FAILED, OPERATION_STATE_CANCELLED, OPERATION_STATE_PENDING }
	}
	case class BucketMetadata(
	  /** The create time of an operation. */
		startTime: Option[String] = None,
	  /** The end time of an operation. */
		endTime: Option[String] = None,
	  /** Output only. State of an operation. */
		state: Option[Schema.BucketMetadata.StateEnum] = None,
	  /** LongRunningCreateBucket RPC request. */
		createBucketRequest: Option[Schema.CreateBucketRequest] = None,
	  /** LongRunningUpdateBucket RPC request. */
		updateBucketRequest: Option[Schema.UpdateBucketRequest] = None
	)
	
	case class CreateBucketRequest(
	  /** Required. The resource in which to create the log bucket: "projects/[PROJECT_ID]/locations/[LOCATION_ID]" For example:"projects/my-project/locations/global" */
		parent: Option[String] = None,
	  /** Required. A client-assigned identifier such as "my-bucket". Identifiers are limited to 100 characters and can include only letters, digits, underscores, hyphens, and periods. Bucket identifiers must start with an alphanumeric character. */
		bucketId: Option[String] = None,
	  /** Required. The new bucket. The region specified in the new bucket must be compliant with any Location Restriction Org Policy. The name field in the bucket is ignored. */
		bucket: Option[Schema.LogBucket] = None
	)
	
	case class UpdateBucketRequest(
	  /** Required. The full resource name of the bucket to update. "projects/[PROJECT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]" "organizations/[ORGANIZATION_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]" "billingAccounts/[BILLING_ACCOUNT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]" "folders/[FOLDER_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]" For example:"projects/my-project/locations/global/buckets/my-bucket" */
		name: Option[String] = None,
	  /** Required. The updated bucket. */
		bucket: Option[Schema.LogBucket] = None,
	  /** Required. Field mask that specifies the fields in bucket that need an update. A bucket field will be overwritten if, and only if, it is in the update mask. name and output only fields cannot be updated.For a detailed FieldMask definition, see: https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMaskFor example: updateMask=retention_days */
		updateMask: Option[String] = None
	)
	
	object LinkMetadata {
		enum StateEnum extends Enum[StateEnum] { case OPERATION_STATE_UNSPECIFIED, OPERATION_STATE_SCHEDULED, OPERATION_STATE_WAITING_FOR_PERMISSIONS, OPERATION_STATE_RUNNING, OPERATION_STATE_SUCCEEDED, OPERATION_STATE_FAILED, OPERATION_STATE_CANCELLED, OPERATION_STATE_PENDING }
	}
	case class LinkMetadata(
	  /** The start time of an operation. */
		startTime: Option[String] = None,
	  /** The end time of an operation. */
		endTime: Option[String] = None,
	  /** Output only. State of an operation. */
		state: Option[Schema.LinkMetadata.StateEnum] = None,
	  /** CreateLink RPC request. */
		createLinkRequest: Option[Schema.CreateLinkRequest] = None,
	  /** DeleteLink RPC request. */
		deleteLinkRequest: Option[Schema.DeleteLinkRequest] = None
	)
	
	case class CreateLinkRequest(
	  /** Required. The full resource name of the bucket to create a link for. "projects/[PROJECT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]" "organizations/[ORGANIZATION_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]" "billingAccounts/[BILLING_ACCOUNT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]" "folders/[FOLDER_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]"  */
		parent: Option[String] = None,
	  /** Required. The new link. */
		link: Option[Schema.Link] = None,
	  /** Required. The ID to use for the link. The link_id can have up to 100 characters. A valid link_id must only have alphanumeric characters and underscores within it. */
		linkId: Option[String] = None
	)
	
	case class DeleteLinkRequest(
	  /** Required. The full resource name of the link to delete. "projects/[PROJECT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/links/[LINK_ID]" "organizations/[ORGANIZATION_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/links/[LINK_ID]" "billingAccounts/[BILLING_ACCOUNT_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/links/[LINK_ID]" "folders/[FOLDER_ID]/locations/[LOCATION_ID]/buckets/[BUCKET_ID]/links/[LINK_ID]"  */
		name: Option[String] = None
	)
	
	case class LocationMetadata(
	  /** Indicates whether or not Log Analytics features are supported in the given location. */
		logAnalyticsEnabled: Option[Boolean] = None
	)
}
