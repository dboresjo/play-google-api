package com.boresjo.play.api.google.bigtableadmin

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
	
	case class CreateInstanceRequest(
	  /** Required. The unique name of the project in which to create the new instance. Values are of the form `projects/{project}`. */
		parent: Option[String] = None,
	  /** Required. The ID to be used when referring to the new instance within its project, e.g., just `myinstance` rather than `projects/myproject/instances/myinstance`. */
		instanceId: Option[String] = None,
	  /** Required. The instance to create. Fields marked `OutputOnly` must be left blank. */
		instance: Option[Schema.Instance] = None,
	  /** Required. The clusters to be created within the instance, mapped by desired cluster ID, e.g., just `mycluster` rather than `projects/myproject/instances/myinstance/clusters/mycluster`. Fields marked `OutputOnly` must be left blank. */
		clusters: Option[Map[String, Schema.Cluster]] = None
	)
	
	object Instance {
		enum StateEnum extends Enum[StateEnum] { case STATE_NOT_KNOWN, READY, CREATING }
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, PRODUCTION, DEVELOPMENT }
	}
	case class Instance(
	  /** The unique name of the instance. Values are of the form `projects/{project}/instances/a-z+[a-z0-9]`. */
		name: Option[String] = None,
	  /** Required. The descriptive name for this instance as it appears in UIs. Can be changed at any time, but should be kept globally unique to avoid confusion. */
		displayName: Option[String] = None,
	  /** Output only. The current state of the instance. */
		state: Option[Schema.Instance.StateEnum] = None,
	  /** The type of the instance. Defaults to `PRODUCTION`. */
		`type`: Option[Schema.Instance.TypeEnum] = None,
	  /** Labels are a flexible and lightweight mechanism for organizing cloud resources into groups that reflect a customer's organizational needs and deployment strategies. They can be used to filter resources and aggregate metrics. &#42; Label keys must be between 1 and 63 characters long and must conform to the regular expression: `\p{Ll}\p{Lo}{0,62}`. &#42; Label values must be between 0 and 63 characters long and must conform to the regular expression: `[\p{Ll}\p{Lo}\p{N}_-]{0,63}`. &#42; No more than 64 labels can be associated with a given resource. &#42; Keys and values must both be under 128 bytes. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. A commit timestamp representing when this Instance was created. For instances created before this field was added (August 2021), this value is `seconds: 0, nanos: 1`. */
		createTime: Option[String] = None,
	  /** Output only. Reserved for future use. */
		satisfiesPzs: Option[Boolean] = None,
	  /** Output only. Reserved for future use. */
		satisfiesPzi: Option[Boolean] = None
	)
	
	object Cluster {
		enum StateEnum extends Enum[StateEnum] { case STATE_NOT_KNOWN, READY, CREATING, RESIZING, DISABLED }
		enum NodeScalingFactorEnum extends Enum[NodeScalingFactorEnum] { case NODE_SCALING_FACTOR_UNSPECIFIED, NODE_SCALING_FACTOR_1X, NODE_SCALING_FACTOR_2X }
		enum DefaultStorageTypeEnum extends Enum[DefaultStorageTypeEnum] { case STORAGE_TYPE_UNSPECIFIED, SSD, HDD }
	}
	case class Cluster(
	  /** The unique name of the cluster. Values are of the form `projects/{project}/instances/{instance}/clusters/a-z&#42;`. */
		name: Option[String] = None,
	  /** Immutable. The location where this cluster's nodes and storage reside. For best performance, clients should be located as close as possible to this cluster. Currently only zones are supported, so values should be of the form `projects/{project}/locations/{zone}`. */
		location: Option[String] = None,
	  /** Output only. The current state of the cluster. */
		state: Option[Schema.Cluster.StateEnum] = None,
	  /** The number of nodes in the cluster. If no value is set, Cloud Bigtable automatically allocates nodes based on your data footprint and optimized for 50% storage utilization. */
		serveNodes: Option[Int] = None,
	  /** Immutable. The node scaling factor of this cluster. */
		nodeScalingFactor: Option[Schema.Cluster.NodeScalingFactorEnum] = None,
	  /** Configuration for this cluster. */
		clusterConfig: Option[Schema.ClusterConfig] = None,
	  /** Immutable. The type of storage used by this cluster to serve its parent instance's tables, unless explicitly overridden. */
		defaultStorageType: Option[Schema.Cluster.DefaultStorageTypeEnum] = None,
	  /** Immutable. The encryption configuration for CMEK-protected clusters. */
		encryptionConfig: Option[Schema.EncryptionConfig] = None
	)
	
	case class ClusterConfig(
	  /** Autoscaling configuration for this cluster. */
		clusterAutoscalingConfig: Option[Schema.ClusterAutoscalingConfig] = None
	)
	
	case class ClusterAutoscalingConfig(
	  /** Required. Autoscaling limits for this cluster. */
		autoscalingLimits: Option[Schema.AutoscalingLimits] = None,
	  /** Required. Autoscaling targets for this cluster. */
		autoscalingTargets: Option[Schema.AutoscalingTargets] = None
	)
	
	case class AutoscalingLimits(
	  /** Required. Minimum number of nodes to scale down to. */
		minServeNodes: Option[Int] = None,
	  /** Required. Maximum number of nodes to scale up to. */
		maxServeNodes: Option[Int] = None
	)
	
	case class AutoscalingTargets(
	  /** The cpu utilization that the Autoscaler should be trying to achieve. This number is on a scale from 0 (no utilization) to 100 (total utilization), and is limited between 10 and 80, otherwise it will return INVALID_ARGUMENT error. */
		cpuUtilizationPercent: Option[Int] = None,
	  /** The storage utilization that the Autoscaler should be trying to achieve. This number is limited between 2560 (2.5TiB) and 5120 (5TiB) for a SSD cluster and between 8192 (8TiB) and 16384 (16TiB) for an HDD cluster, otherwise it will return INVALID_ARGUMENT error. If this value is set to 0, it will be treated as if it were set to the default value: 2560 for SSD, 8192 for HDD. */
		storageUtilizationGibPerNode: Option[Int] = None
	)
	
	case class EncryptionConfig(
	  /** Describes the Cloud KMS encryption key that will be used to protect the destination Bigtable cluster. The requirements for this key are: 1) The Cloud Bigtable service account associated with the project that contains this cluster must be granted the `cloudkms.cryptoKeyEncrypterDecrypter` role on the CMEK key. 2) Only regional keys can be used and the region of the CMEK key must match the region of the cluster. Values are of the form `projects/{project}/locations/{location}/keyRings/{keyring}/cryptoKeys/{key}` */
		kmsKeyName: Option[String] = None
	)
	
	case class ListInstancesResponse(
	  /** The list of requested instances. */
		instances: Option[List[Schema.Instance]] = None,
	  /** Locations from which Instance information could not be retrieved, due to an outage or some other transient condition. Instances whose Clusters are all in one of the failed locations may be missing from `instances`, and Instances with at least one Cluster in a failed location may only have partial information returned. Values are of the form `projects//locations/` */
		failedLocations: Option[List[String]] = None,
	  /** DEPRECATED: This field is unused and ignored. */
		nextPageToken: Option[String] = None
	)
	
	case class Empty(
	
	)
	
	case class ListClustersResponse(
	  /** The list of requested clusters. */
		clusters: Option[List[Schema.Cluster]] = None,
	  /** Locations from which Cluster information could not be retrieved, due to an outage or some other transient condition. Clusters from these locations may be missing from `clusters`, or may only have partial information returned. Values are of the form `projects//locations/` */
		failedLocations: Option[List[String]] = None,
	  /** DEPRECATED: This field is unused and ignored. */
		nextPageToken: Option[String] = None
	)
	
	object AppProfile {
		enum PriorityEnum extends Enum[PriorityEnum] { case PRIORITY_UNSPECIFIED, PRIORITY_LOW, PRIORITY_MEDIUM, PRIORITY_HIGH }
	}
	case class AppProfile(
	  /** The unique name of the app profile. Values are of the form `projects/{project}/instances/{instance}/appProfiles/_a-zA-Z0-9&#42;`. */
		name: Option[String] = None,
	  /** Strongly validated etag for optimistic concurrency control. Preserve the value returned from `GetAppProfile` when calling `UpdateAppProfile` to fail the request if there has been a modification in the mean time. The `update_mask` of the request need not include `etag` for this protection to apply. See [Wikipedia](https://en.wikipedia.org/wiki/HTTP_ETag) and [RFC 7232](https://tools.ietf.org/html/rfc7232#section-2.3) for more details. */
		etag: Option[String] = None,
	  /** Long form description of the use case for this AppProfile. */
		description: Option[String] = None,
	  /** Use a multi-cluster routing policy. */
		multiClusterRoutingUseAny: Option[Schema.MultiClusterRoutingUseAny] = None,
	  /** Use a single-cluster routing policy. */
		singleClusterRouting: Option[Schema.SingleClusterRouting] = None,
	  /** This field has been deprecated in favor of `standard_isolation.priority`. If you set this field, `standard_isolation.priority` will be set instead. The priority of requests sent using this app profile. */
		priority: Option[Schema.AppProfile.PriorityEnum] = None,
	  /** The standard options used for isolating this app profile's traffic from other use cases. */
		standardIsolation: Option[Schema.StandardIsolation] = None,
	  /** Specifies that this app profile is intended for read-only usage via the Data Boost feature. */
		dataBoostIsolationReadOnly: Option[Schema.DataBoostIsolationReadOnly] = None
	)
	
	case class MultiClusterRoutingUseAny(
	  /** The set of clusters to route to. The order is ignored; clusters will be tried in order of distance. If left empty, all clusters are eligible. */
		clusterIds: Option[List[String]] = None,
	  /** Row affinity sticky routing based on the row key of the request. Requests that span multiple rows are routed non-deterministically. */
		rowAffinity: Option[Schema.RowAffinity] = None
	)
	
	case class RowAffinity(
	
	)
	
	case class SingleClusterRouting(
	  /** The cluster to which read/write requests should be routed. */
		clusterId: Option[String] = None,
	  /** Whether or not `CheckAndMutateRow` and `ReadModifyWriteRow` requests are allowed by this app profile. It is unsafe to send these requests to the same table/row/column in multiple clusters. */
		allowTransactionalWrites: Option[Boolean] = None
	)
	
	object StandardIsolation {
		enum PriorityEnum extends Enum[PriorityEnum] { case PRIORITY_UNSPECIFIED, PRIORITY_LOW, PRIORITY_MEDIUM, PRIORITY_HIGH }
	}
	case class StandardIsolation(
	  /** The priority of requests sent using this app profile. */
		priority: Option[Schema.StandardIsolation.PriorityEnum] = None
	)
	
	object DataBoostIsolationReadOnly {
		enum ComputeBillingOwnerEnum extends Enum[ComputeBillingOwnerEnum] { case COMPUTE_BILLING_OWNER_UNSPECIFIED, HOST_PAYS }
	}
	case class DataBoostIsolationReadOnly(
	  /** The Compute Billing Owner for this Data Boost App Profile. */
		computeBillingOwner: Option[Schema.DataBoostIsolationReadOnly.ComputeBillingOwnerEnum] = None
	)
	
	case class ListAppProfilesResponse(
	  /** The list of requested app profiles. */
		appProfiles: Option[List[Schema.AppProfile]] = None,
	  /** Set if not all app profiles could be returned in a single response. Pass this value to `page_token` in another request to get the next page of results. */
		nextPageToken: Option[String] = None,
	  /** Locations from which AppProfile information could not be retrieved, due to an outage or some other transient condition. AppProfiles from these locations may be missing from `app_profiles`. Values are of the form `projects//locations/` */
		failedLocations: Option[List[String]] = None
	)
	
	case class GetIamPolicyRequest(
	  /** OPTIONAL: A `GetPolicyOptions` object for specifying options to `GetIamPolicy`. */
		options: Option[Schema.GetPolicyOptions] = None
	)
	
	case class GetPolicyOptions(
	  /** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		requestedPolicyVersion: Option[Int] = None
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
	
	case class SetIamPolicyRequest(
	  /** REQUIRED: The complete policy to be applied to the `resource`. The size of the policy is limited to a few 10s of KB. An empty policy is a valid policy but certain Google Cloud services (such as Projects) might reject them. */
		policy: Option[Schema.Policy] = None,
	  /** OPTIONAL: A FieldMask specifying which fields of the policy to modify. Only the fields in the mask will be modified. If no mask is provided, the following default mask is used: `paths: "bindings, etag"` */
		updateMask: Option[String] = None
	)
	
	case class TestIamPermissionsRequest(
	  /** The set of permissions to check for the `resource`. Permissions with wildcards (such as `&#42;` or `storage.&#42;`) are not allowed. For more information see [IAM Overview](https://cloud.google.com/iam/docs/overview#permissions). */
		permissions: Option[List[String]] = None
	)
	
	case class TestIamPermissionsResponse(
	  /** A subset of `TestPermissionsRequest.permissions` that the caller is allowed. */
		permissions: Option[List[String]] = None
	)
	
	case class ListHotTabletsResponse(
	  /** List of hot tablets in the tables of the requested cluster that fall within the requested time range. Hot tablets are ordered by node cpu usage percent. If there are multiple hot tablets that correspond to the same tablet within a 15-minute interval, only the hot tablet with the highest node cpu usage will be included in the response. */
		hotTablets: Option[List[Schema.HotTablet]] = None,
	  /** Set if not all hot tablets could be returned in a single response. Pass this value to `page_token` in another request to get the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class HotTablet(
	  /** The unique name of the hot tablet. Values are of the form `projects/{project}/instances/{instance}/clusters/{cluster}/hotTablets/[a-zA-Z0-9_-]&#42;`. */
		name: Option[String] = None,
	  /** Name of the table that contains the tablet. Values are of the form `projects/{project}/instances/{instance}/tables/_a-zA-Z0-9&#42;`. */
		tableName: Option[String] = None,
	  /** Output only. The start time of the hot tablet. */
		startTime: Option[String] = None,
	  /** Output only. The end time of the hot tablet. */
		endTime: Option[String] = None,
	  /** Tablet Start Key (inclusive). */
		startKey: Option[String] = None,
	  /** Tablet End Key (inclusive). */
		endKey: Option[String] = None,
	  /** Output only. The average CPU usage spent by a node on this tablet over the start_time to end_time time range. The percentage is the amount of CPU used by the node to serve the tablet, from 0% (tablet was not interacted with) to 100% (the node spent all cycles serving the hot tablet). */
		nodeCpuUsagePercent: Option[BigDecimal] = None
	)
	
	case class CreateTableRequest(
	  /** Required. The name by which the new table should be referred to within the parent instance, e.g., `foobar` rather than `{parent}/tables/foobar`. Maximum 50 characters. */
		tableId: Option[String] = None,
	  /** Required. The Table to create. */
		table: Option[Schema.Table] = None,
	  /** The optional list of row keys that will be used to initially split the table into several tablets (tablets are similar to HBase regions). Given two split keys, `s1` and `s2`, three tablets will be created, spanning the key ranges: `[, s1), [s1, s2), [s2, )`. Example: &#42; Row keys := `["a", "apple", "custom", "customer_1", "customer_2",` `"other", "zz"]` &#42; initial_split_keys := `["apple", "customer_1", "customer_2", "other"]` &#42; Key assignment: - Tablet 1 `[, apple) => {"a"}.` - Tablet 2 `[apple, customer_1) => {"apple", "custom"}.` - Tablet 3 `[customer_1, customer_2) => {"customer_1"}.` - Tablet 4 `[customer_2, other) => {"customer_2"}.` - Tablet 5 `[other, ) => {"other", "zz"}.` */
		initialSplits: Option[List[Schema.Split]] = None
	)
	
	object Table {
		enum GranularityEnum extends Enum[GranularityEnum] { case TIMESTAMP_GRANULARITY_UNSPECIFIED, MILLIS }
	}
	case class Table(
	  /** The unique name of the table. Values are of the form `projects/{project}/instances/{instance}/tables/_a-zA-Z0-9&#42;`. Views: `NAME_ONLY`, `SCHEMA_VIEW`, `REPLICATION_VIEW`, `STATS_VIEW`, `FULL` */
		name: Option[String] = None,
	  /** Output only. Map from cluster ID to per-cluster table state. If it could not be determined whether or not the table has data in a particular cluster (for example, if its zone is unavailable), then there will be an entry for the cluster with UNKNOWN `replication_status`. Views: `REPLICATION_VIEW`, `ENCRYPTION_VIEW`, `FULL` */
		clusterStates: Option[Map[String, Schema.ClusterState]] = None,
	  /** The column families configured for this table, mapped by column family ID. Views: `SCHEMA_VIEW`, `STATS_VIEW`, `FULL` */
		columnFamilies: Option[Map[String, Schema.ColumnFamily]] = None,
	  /** Immutable. The granularity (i.e. `MILLIS`) at which timestamps are stored in this table. Timestamps not matching the granularity will be rejected. If unspecified at creation time, the value will be set to `MILLIS`. Views: `SCHEMA_VIEW`, `FULL`. */
		granularity: Option[Schema.Table.GranularityEnum] = None,
	  /** Output only. If this table was restored from another data source (e.g. a backup), this field will be populated with information about the restore. */
		restoreInfo: Option[Schema.RestoreInfo] = None,
	  /** If specified, enable the change stream on this table. Otherwise, the change stream is disabled and the change stream is not retained. */
		changeStreamConfig: Option[Schema.ChangeStreamConfig] = None,
	  /** Set to true to make the table protected against data loss. i.e. deleting the following resources through Admin APIs are prohibited: &#42; The table. &#42; The column families in the table. &#42; The instance containing the table. Note one can still delete the data stored in the table through Data APIs. */
		deletionProtection: Option[Boolean] = None,
	  /** Output only. Only available with STATS_VIEW, this includes summary statistics about the entire table contents. For statistics about a specific column family, see ColumnFamilyStats in the mapped ColumnFamily collection above. */
		stats: Option[Schema.TableStats] = None,
	  /** If specified, automated backups are enabled for this table. Otherwise, automated backups are disabled. */
		automatedBackupPolicy: Option[Schema.AutomatedBackupPolicy] = None
	)
	
	object ClusterState {
		enum ReplicationStateEnum extends Enum[ReplicationStateEnum] { case STATE_NOT_KNOWN, INITIALIZING, PLANNED_MAINTENANCE, UNPLANNED_MAINTENANCE, READY, READY_OPTIMIZING }
	}
	case class ClusterState(
	  /** Output only. The state of replication for the table in this cluster. */
		replicationState: Option[Schema.ClusterState.ReplicationStateEnum] = None,
	  /** Output only. The encryption information for the table in this cluster. If the encryption key protecting this resource is customer managed, then its version can be rotated in Cloud Key Management Service (Cloud KMS). The primary version of the key and its status will be reflected here when changes propagate from Cloud KMS. */
		encryptionInfo: Option[List[Schema.EncryptionInfo]] = None
	)
	
	object EncryptionInfo {
		enum EncryptionTypeEnum extends Enum[EncryptionTypeEnum] { case ENCRYPTION_TYPE_UNSPECIFIED, GOOGLE_DEFAULT_ENCRYPTION, CUSTOMER_MANAGED_ENCRYPTION }
	}
	case class EncryptionInfo(
	  /** Output only. The type of encryption used to protect this resource. */
		encryptionType: Option[Schema.EncryptionInfo.EncryptionTypeEnum] = None,
	  /** Output only. The status of encrypt/decrypt calls on underlying data for this resource. Regardless of status, the existing data is always encrypted at rest. */
		encryptionStatus: Option[Schema.Status] = None,
	  /** Output only. The version of the Cloud KMS key specified in the parent cluster that is in use for the data underlying this table. */
		kmsKeyVersion: Option[String] = None
	)
	
	case class ColumnFamily(
	  /** Garbage collection rule specified as a protobuf. Must serialize to at most 500 bytes. NOTE: Garbage collection executes opportunistically in the background, and so it's possible for reads to return a cell even if it matches the active GC expression for its family. */
		gcRule: Option[Schema.GcRule] = None,
	  /** Output only. Only available with STATS_VIEW, this includes summary statistics about column family contents. For statistics over an entire table, see TableStats above. */
		stats: Option[Schema.ColumnFamilyStats] = None,
	  /** The type of data stored in each of this family's cell values, including its full encoding. If omitted, the family only serves raw untyped bytes. For now, only the `Aggregate` type is supported. `Aggregate` can only be set at family creation and is immutable afterwards. If `value_type` is `Aggregate`, written data must be compatible with: &#42; `value_type.input_type` for `AddInput` mutations */
		valueType: Option[Schema.Type] = None
	)
	
	case class GcRule(
	  /** Delete all cells in a column except the most recent N. */
		maxNumVersions: Option[Int] = None,
	  /** Delete cells in a column older than the given age. Values must be at least one millisecond, and will be truncated to microsecond granularity. */
		maxAge: Option[String] = None,
	  /** Delete cells that would be deleted by every nested rule. */
		intersection: Option[Schema.Intersection] = None,
	  /** Delete cells that would be deleted by any nested rule. */
		union: Option[Schema.Union] = None
	)
	
	case class Intersection(
	  /** Only delete cells which would be deleted by every element of `rules`. */
		rules: Option[List[Schema.GcRule]] = None
	)
	
	case class Union(
	  /** Delete cells which would be deleted by any element of `rules`. */
		rules: Option[List[Schema.GcRule]] = None
	)
	
	case class ColumnFamilyStats(
	  /** How many column qualifiers are present in this column family, averaged over all rows in the table. e.g. For column family "family" in a table with 3 rows: &#42; A row with cells in "family:col" and "other:col" (1 column in "family") &#42; A row with cells in "family:col", "family:other_col", and "other:data" (2 columns in "family") &#42; A row with cells in "other:col" (0 columns in "family", "family" not present) would report (1 + 2 + 0)/3 = 1.5 in this field. */
		averageColumnsPerRow: Option[BigDecimal] = None,
	  /** How many cells are present per column qualifier in this column family, averaged over all rows containing any column in the column family. e.g. For column family "family" in a table with 3 rows: &#42; A row with 3 cells in "family:col" and 1 cell in "other:col" (3 cells / 1 column in "family") &#42; A row with 1 cell in "family:col", 7 cells in "family:other_col", and 7 cells in "other:data" (8 cells / 2 columns in "family") &#42; A row with 3 cells in "other:col" (0 columns in "family", "family" not present) would report (3 + 8 + 0)/(1 + 2 + 0) = 3.66 in this field. */
		averageCellsPerColumn: Option[BigDecimal] = None,
	  /** How much space the data in the column family occupies. This is roughly how many bytes would be needed to read the contents of the entire column family (e.g. by streaming all contents out). */
		logicalDataBytes: Option[String] = None
	)
	
	case class Type(
	  /** Bytes */
		bytesType: Option[Schema.GoogleBigtableAdminV2TypeBytes] = None,
	  /** String */
		stringType: Option[Schema.GoogleBigtableAdminV2TypeString] = None,
	  /** Int64 */
		int64Type: Option[Schema.GoogleBigtableAdminV2TypeInt64] = None,
	  /** Float32 */
		float32Type: Option[Schema.GoogleBigtableAdminV2TypeFloat32] = None,
	  /** Float64 */
		float64Type: Option[Schema.GoogleBigtableAdminV2TypeFloat64] = None,
	  /** Bool */
		boolType: Option[Schema.GoogleBigtableAdminV2TypeBool] = None,
	  /** Timestamp */
		timestampType: Option[Schema.GoogleBigtableAdminV2TypeTimestamp] = None,
	  /** Date */
		dateType: Option[Schema.GoogleBigtableAdminV2TypeDate] = None,
	  /** Aggregate */
		aggregateType: Option[Schema.GoogleBigtableAdminV2TypeAggregate] = None,
	  /** Struct */
		structType: Option[Schema.GoogleBigtableAdminV2TypeStruct] = None,
	  /** Array */
		arrayType: Option[Schema.GoogleBigtableAdminV2TypeArray] = None,
	  /** Map */
		mapType: Option[Schema.GoogleBigtableAdminV2TypeMap] = None
	)
	
	case class GoogleBigtableAdminV2TypeBytes(
	  /** The encoding to use when converting to or from lower level types. */
		encoding: Option[Schema.GoogleBigtableAdminV2TypeBytesEncoding] = None
	)
	
	case class GoogleBigtableAdminV2TypeBytesEncoding(
	  /** Use `Raw` encoding. */
		raw: Option[Schema.GoogleBigtableAdminV2TypeBytesEncodingRaw] = None
	)
	
	case class GoogleBigtableAdminV2TypeBytesEncodingRaw(
	
	)
	
	case class GoogleBigtableAdminV2TypeString(
	  /** The encoding to use when converting to or from lower level types. */
		encoding: Option[Schema.GoogleBigtableAdminV2TypeStringEncoding] = None
	)
	
	case class GoogleBigtableAdminV2TypeStringEncoding(
	  /** Deprecated: if set, converts to an empty `utf8_bytes`. */
		utf8Raw: Option[Schema.GoogleBigtableAdminV2TypeStringEncodingUtf8Raw] = None,
	  /** Use `Utf8Bytes` encoding. */
		utf8Bytes: Option[Schema.GoogleBigtableAdminV2TypeStringEncodingUtf8Bytes] = None
	)
	
	case class GoogleBigtableAdminV2TypeStringEncodingUtf8Raw(
	
	)
	
	case class GoogleBigtableAdminV2TypeStringEncodingUtf8Bytes(
	
	)
	
	case class GoogleBigtableAdminV2TypeInt64(
	  /** The encoding to use when converting to or from lower level types. */
		encoding: Option[Schema.GoogleBigtableAdminV2TypeInt64Encoding] = None
	)
	
	case class GoogleBigtableAdminV2TypeInt64Encoding(
	  /** Use `BigEndianBytes` encoding. */
		bigEndianBytes: Option[Schema.GoogleBigtableAdminV2TypeInt64EncodingBigEndianBytes] = None
	)
	
	case class GoogleBigtableAdminV2TypeInt64EncodingBigEndianBytes(
	  /** Deprecated: ignored if set. */
		bytesType: Option[Schema.GoogleBigtableAdminV2TypeBytes] = None
	)
	
	case class GoogleBigtableAdminV2TypeFloat32(
	
	)
	
	case class GoogleBigtableAdminV2TypeFloat64(
	
	)
	
	case class GoogleBigtableAdminV2TypeBool(
	
	)
	
	case class GoogleBigtableAdminV2TypeTimestamp(
	
	)
	
	case class GoogleBigtableAdminV2TypeDate(
	
	)
	
	case class GoogleBigtableAdminV2TypeAggregate(
	  /** Type of the inputs that are accumulated by this `Aggregate`. Use `AddInput` mutations to accumulate new inputs. */
		inputType: Option[Schema.Type] = None,
	  /** Output only. Type that holds the internal accumulator state for the `Aggregate`. This is a function of the `input_type` and `aggregator` chosen. */
		stateType: Option[Schema.Type] = None,
	  /** Sum aggregator. */
		sum: Option[Schema.GoogleBigtableAdminV2TypeAggregateSum] = None,
	  /** HyperLogLogPlusPlusUniqueCount aggregator. */
		hllppUniqueCount: Option[Schema.GoogleBigtableAdminV2TypeAggregateHyperLogLogPlusPlusUniqueCount] = None,
	  /** Max aggregator. */
		max: Option[Schema.GoogleBigtableAdminV2TypeAggregateMax] = None,
	  /** Min aggregator. */
		min: Option[Schema.GoogleBigtableAdminV2TypeAggregateMin] = None
	)
	
	case class GoogleBigtableAdminV2TypeAggregateSum(
	
	)
	
	case class GoogleBigtableAdminV2TypeAggregateHyperLogLogPlusPlusUniqueCount(
	
	)
	
	case class GoogleBigtableAdminV2TypeAggregateMax(
	
	)
	
	case class GoogleBigtableAdminV2TypeAggregateMin(
	
	)
	
	case class GoogleBigtableAdminV2TypeStruct(
	  /** The names and types of the fields in this struct. */
		fields: Option[List[Schema.GoogleBigtableAdminV2TypeStructField]] = None
	)
	
	case class GoogleBigtableAdminV2TypeStructField(
	  /** The field name (optional). Fields without a `field_name` are considered anonymous and cannot be referenced by name. */
		fieldName: Option[String] = None,
	  /** The type of values in this field. */
		`type`: Option[Schema.Type] = None
	)
	
	case class GoogleBigtableAdminV2TypeArray(
	  /** The type of the elements in the array. This must not be `Array`. */
		elementType: Option[Schema.Type] = None
	)
	
	case class GoogleBigtableAdminV2TypeMap(
	  /** The type of a map key. Only `Bytes`, `String`, and `Int64` are allowed as key types. */
		keyType: Option[Schema.Type] = None,
	  /** The type of the values in a map. */
		valueType: Option[Schema.Type] = None
	)
	
	object RestoreInfo {
		enum SourceTypeEnum extends Enum[SourceTypeEnum] { case RESTORE_SOURCE_TYPE_UNSPECIFIED, BACKUP }
	}
	case class RestoreInfo(
	  /** The type of the restore source. */
		sourceType: Option[Schema.RestoreInfo.SourceTypeEnum] = None,
	  /** Information about the backup used to restore the table. The backup may no longer exist. */
		backupInfo: Option[Schema.BackupInfo] = None
	)
	
	case class BackupInfo(
	  /** Output only. Name of the backup. */
		backup: Option[String] = None,
	  /** Output only. The time that the backup was started. Row data in the backup will be no older than this timestamp. */
		startTime: Option[String] = None,
	  /** Output only. This time that the backup was finished. Row data in the backup will be no newer than this timestamp. */
		endTime: Option[String] = None,
	  /** Output only. Name of the table the backup was created from. */
		sourceTable: Option[String] = None,
	  /** Output only. Name of the backup from which this backup was copied. If a backup is not created by copying a backup, this field will be empty. Values are of the form: projects//instances//clusters//backups/ */
		sourceBackup: Option[String] = None
	)
	
	case class ChangeStreamConfig(
	  /** How long the change stream should be retained. Change stream data older than the retention period will not be returned when reading the change stream from the table. Values must be at least 1 day and at most 7 days, and will be truncated to microsecond granularity. */
		retentionPeriod: Option[String] = None
	)
	
	case class TableStats(
	  /** How many rows are in the table. */
		rowCount: Option[String] = None,
	  /** How many (column family, column qualifier) combinations are present per row in the table, averaged over all rows in the table. e.g. A table with 2 rows: &#42; A row with cells in "family:col" and "other:col" (2 distinct columns) &#42; A row with cells in "family:col", "family:other_col", and "other:data" (3 distinct columns) would report (2 + 3)/2 = 2.5 in this field. */
		averageColumnsPerRow: Option[BigDecimal] = None,
	  /** How many cells are present per column (column family, column qualifier) combinations, averaged over all columns in all rows in the table. e.g. A table with 2 rows: &#42; A row with 3 cells in "family:col" and 1 cell in "other:col" (4 cells / 2 columns) &#42; A row with 1 cell in "family:col", 7 cells in "family:other_col", and 7 cells in "other:data" (15 cells / 3 columns) would report (4 + 15)/(2 + 3) = 3.8 in this field. */
		averageCellsPerColumn: Option[BigDecimal] = None,
	  /** This is roughly how many bytes would be needed to read the entire table (e.g. by streaming all contents out). */
		logicalDataBytes: Option[String] = None
	)
	
	case class AutomatedBackupPolicy(
	  /** Required. How long the automated backups should be retained. The only supported value at this time is 3 days. */
		retentionPeriod: Option[String] = None,
	  /** How frequently automated backups should occur. The only supported value at this time is 24 hours. An undefined frequency is treated as 24 hours. */
		frequency: Option[String] = None
	)
	
	case class Split(
	  /** Row key to use as an initial tablet boundary. */
		key: Option[String] = None
	)
	
	case class ListTablesResponse(
	  /** The tables present in the requested instance. */
		tables: Option[List[Schema.Table]] = None,
	  /** Set if not all tables could be returned in a single response. Pass this value to `page_token` in another request to get the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class UndeleteTableRequest(
	
	)
	
	case class AuthorizedView(
	  /** Identifier. The name of this AuthorizedView. Values are of the form `projects/{project}/instances/{instance}/tables/{table}/authorizedViews/{authorized_view}` */
		name: Option[String] = None,
	  /** An AuthorizedView permitting access to an explicit subset of a Table. */
		subsetView: Option[Schema.GoogleBigtableAdminV2AuthorizedViewSubsetView] = None,
	  /** The etag for this AuthorizedView. If this is provided on update, it must match the server's etag. The server returns ABORTED error on a mismatched etag. */
		etag: Option[String] = None,
	  /** Set to true to make the AuthorizedView protected against deletion. The parent Table and containing Instance cannot be deleted if an AuthorizedView has this bit set. */
		deletionProtection: Option[Boolean] = None
	)
	
	case class GoogleBigtableAdminV2AuthorizedViewSubsetView(
	  /** Row prefixes to be included in the AuthorizedView. To provide access to all rows, include the empty string as a prefix (""). */
		rowPrefixes: Option[List[String]] = None,
	  /** Map from column family name to the columns in this family to be included in the AuthorizedView. */
		familySubsets: Option[Map[String, Schema.GoogleBigtableAdminV2AuthorizedViewFamilySubsets]] = None
	)
	
	case class GoogleBigtableAdminV2AuthorizedViewFamilySubsets(
	  /** Individual exact column qualifiers to be included in the AuthorizedView. */
		qualifiers: Option[List[String]] = None,
	  /** Prefixes for qualifiers to be included in the AuthorizedView. Every qualifier starting with one of these prefixes is included in the AuthorizedView. To provide access to all qualifiers, include the empty string as a prefix (""). */
		qualifierPrefixes: Option[List[String]] = None
	)
	
	case class ListAuthorizedViewsResponse(
	  /** The AuthorizedViews present in the requested table. */
		authorizedViews: Option[List[Schema.AuthorizedView]] = None,
	  /** Set if not all tables could be returned in a single response. Pass this value to `page_token` in another request to get the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class ModifyColumnFamiliesRequest(
	  /** Required. Modifications to be atomically applied to the specified table's families. Entries are applied in order, meaning that earlier modifications can be masked by later ones (in the case of repeated updates to the same family, for example). */
		modifications: Option[List[Schema.Modification]] = None,
	  /** Optional. If true, ignore safety checks when modifying the column families. */
		ignoreWarnings: Option[Boolean] = None
	)
	
	case class Modification(
	  /** The ID of the column family to be modified. */
		id: Option[String] = None,
	  /** Create a new column family with the specified schema, or fail if one already exists with the given ID. */
		create: Option[Schema.ColumnFamily] = None,
	  /** Update an existing column family to the specified schema, or fail if no column family exists with the given ID. */
		update: Option[Schema.ColumnFamily] = None,
	  /** Drop (delete) the column family with the given ID, or fail if no such family exists. */
		drop: Option[Boolean] = None,
	  /** Optional. A mask specifying which fields (e.g. `gc_rule`) in the `update` mod should be updated, ignored for other modification types. If unset or empty, we treat it as updating `gc_rule` to be backward compatible. */
		updateMask: Option[String] = None
	)
	
	case class DropRowRangeRequest(
	  /** Delete all rows that start with this row key prefix. Prefix cannot be zero length. */
		rowKeyPrefix: Option[String] = None,
	  /** Delete all rows in the table. Setting this to false is a no-op. */
		deleteAllDataFromTable: Option[Boolean] = None
	)
	
	case class GenerateConsistencyTokenRequest(
	
	)
	
	case class GenerateConsistencyTokenResponse(
	  /** The generated consistency token. */
		consistencyToken: Option[String] = None
	)
	
	case class CheckConsistencyRequest(
	  /** Required. The token created using GenerateConsistencyToken for the Table. */
		consistencyToken: Option[String] = None,
	  /** Checks that reads using an app profile with `StandardIsolation` can see all writes committed before the token was created, even if the read and write target different clusters. */
		standardReadRemoteWrites: Option[Schema.StandardReadRemoteWrites] = None,
	  /** Checks that reads using an app profile with `DataBoostIsolationReadOnly` can see all writes committed before the token was created, but only if the read and write target the same cluster. */
		dataBoostReadLocalWrites: Option[Schema.DataBoostReadLocalWrites] = None
	)
	
	case class StandardReadRemoteWrites(
	
	)
	
	case class DataBoostReadLocalWrites(
	
	)
	
	case class CheckConsistencyResponse(
	  /** True only if the token is consistent. A token is consistent if replication has caught up with the restrictions specified in the request. */
		consistent: Option[Boolean] = None
	)
	
	object Backup {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, CREATING, READY }
		enum BackupTypeEnum extends Enum[BackupTypeEnum] { case BACKUP_TYPE_UNSPECIFIED, STANDARD, HOT }
	}
	case class Backup(
	  /** A globally unique identifier for the backup which cannot be changed. Values are of the form `projects/{project}/instances/{instance}/clusters/{cluster}/ backups/_a-zA-Z0-9&#42;` The final segment of the name must be between 1 and 50 characters in length. The backup is stored in the cluster identified by the prefix of the backup name of the form `projects/{project}/instances/{instance}/clusters/{cluster}`. */
		name: Option[String] = None,
	  /** Required. Immutable. Name of the table from which this backup was created. This needs to be in the same instance as the backup. Values are of the form `projects/{project}/instances/{instance}/tables/{source_table}`. */
		sourceTable: Option[String] = None,
	  /** Output only. Name of the backup from which this backup was copied. If a backup is not created by copying a backup, this field will be empty. Values are of the form: projects//instances//clusters//backups/ */
		sourceBackup: Option[String] = None,
	  /** Required. The expiration time of the backup. When creating a backup or updating its `expire_time`, the value must be greater than the backup creation time by: - At least 6 hours - At most 90 days Once the `expire_time` has passed, Cloud Bigtable will delete the backup. */
		expireTime: Option[String] = None,
	  /** Output only. `start_time` is the time that the backup was started (i.e. approximately the time the CreateBackup request is received). The row data in this backup will be no older than this timestamp. */
		startTime: Option[String] = None,
	  /** Output only. `end_time` is the time that the backup was finished. The row data in the backup will be no newer than this timestamp. */
		endTime: Option[String] = None,
	  /** Output only. Size of the backup in bytes. */
		sizeBytes: Option[String] = None,
	  /** Output only. The current state of the backup. */
		state: Option[Schema.Backup.StateEnum] = None,
	  /** Output only. The encryption information for the backup. */
		encryptionInfo: Option[Schema.EncryptionInfo] = None,
	  /** Indicates the backup type of the backup. */
		backupType: Option[Schema.Backup.BackupTypeEnum] = None,
	  /** The time at which the hot backup will be converted to a standard backup. Once the `hot_to_standard_time` has passed, Cloud Bigtable will convert the hot backup to a standard backup. This value must be greater than the backup creation time by: - At least 24 hours This field only applies for hot backups. When creating or updating a standard backup, attempting to set this field will fail the request. */
		hotToStandardTime: Option[String] = None
	)
	
	case class ListBackupsResponse(
	  /** The list of matching backups. */
		backups: Option[List[Schema.Backup]] = None,
	  /** `next_page_token` can be sent in a subsequent ListBackups call to fetch more of the matching backups. */
		nextPageToken: Option[String] = None
	)
	
	case class RestoreTableRequest(
	  /** Required. The id of the table to create and restore to. This table must not already exist. The `table_id` appended to `parent` forms the full table name of the form `projects//instances//tables/`. */
		tableId: Option[String] = None,
	  /** Name of the backup from which to restore. Values are of the form `projects//instances//clusters//backups/`. */
		backup: Option[String] = None
	)
	
	case class CopyBackupRequest(
	  /** Required. The id of the new backup. The `backup_id` along with `parent` are combined as {parent}/backups/{backup_id} to create the full backup name, of the form: `projects/{project}/instances/{instance}/clusters/{cluster}/backups/{backup_id}`. This string must be between 1 and 50 characters in length and match the regex _a-zA-Z0-9&#42;. */
		backupId: Option[String] = None,
	  /** Required. The source backup to be copied from. The source backup needs to be in READY state for it to be copied. Copying a copied backup is not allowed. Once CopyBackup is in progress, the source backup cannot be deleted or cleaned up on expiration until CopyBackup is finished. Values are of the form: `projects//instances//clusters//backups/`. */
		sourceBackup: Option[String] = None,
	  /** Required. Required. The expiration time of the copied backup with microsecond granularity that must be at least 6 hours and at most 30 days from the time the request is received. Once the `expire_time` has passed, Cloud Bigtable will delete the backup and free the resources used by the backup. */
		expireTime: Option[String] = None
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
	
	case class CreateInstanceMetadata(
	  /** The request that prompted the initiation of this CreateInstance operation. */
		originalRequest: Option[Schema.CreateInstanceRequest] = None,
	  /** The time at which the original request was received. */
		requestTime: Option[String] = None,
	  /** The time at which the operation failed or was completed successfully. */
		finishTime: Option[String] = None
	)
	
	case class UpdateInstanceMetadata(
	  /** The request that prompted the initiation of this UpdateInstance operation. */
		originalRequest: Option[Schema.PartialUpdateInstanceRequest] = None,
	  /** The time at which the original request was received. */
		requestTime: Option[String] = None,
	  /** The time at which the operation failed or was completed successfully. */
		finishTime: Option[String] = None
	)
	
	case class PartialUpdateInstanceRequest(
	  /** Required. The Instance which will (partially) replace the current value. */
		instance: Option[Schema.Instance] = None,
	  /** Required. The subset of Instance fields which should be replaced. Must be explicitly set. */
		updateMask: Option[String] = None
	)
	
	case class CreateClusterMetadata(
	  /** The request that prompted the initiation of this CreateCluster operation. */
		originalRequest: Option[Schema.CreateClusterRequest] = None,
	  /** The time at which the original request was received. */
		requestTime: Option[String] = None,
	  /** The time at which the operation failed or was completed successfully. */
		finishTime: Option[String] = None,
	  /** Keys: the full `name` of each table that existed in the instance when CreateCluster was first called, i.e. `projects//instances//tables/`. Any table added to the instance by a later API call will be created in the new cluster by that API call, not this one. Values: information on how much of a table's data has been copied to the newly-created cluster so far. */
		tables: Option[Map[String, Schema.TableProgress]] = None
	)
	
	case class CreateClusterRequest(
	  /** Required. The unique name of the instance in which to create the new cluster. Values are of the form `projects/{project}/instances/{instance}`. */
		parent: Option[String] = None,
	  /** Required. The ID to be used when referring to the new cluster within its instance, e.g., just `mycluster` rather than `projects/myproject/instances/myinstance/clusters/mycluster`. */
		clusterId: Option[String] = None,
	  /** Required. The cluster to be created. Fields marked `OutputOnly` must be left blank. */
		cluster: Option[Schema.Cluster] = None
	)
	
	object TableProgress {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PENDING, COPYING, COMPLETED, CANCELLED }
	}
	case class TableProgress(
	  /** Estimate of the size of the table to be copied. */
		estimatedSizeBytes: Option[String] = None,
	  /** Estimate of the number of bytes copied so far for this table. This will eventually reach 'estimated_size_bytes' unless the table copy is CANCELLED. */
		estimatedCopiedBytes: Option[String] = None,
		state: Option[Schema.TableProgress.StateEnum] = None
	)
	
	case class PartialUpdateClusterMetadata(
	  /** The time at which the original request was received. */
		requestTime: Option[String] = None,
	  /** The time at which the operation failed or was completed successfully. */
		finishTime: Option[String] = None,
	  /** The original request for PartialUpdateCluster. */
		originalRequest: Option[Schema.PartialUpdateClusterRequest] = None
	)
	
	case class PartialUpdateClusterRequest(
	  /** Required. The Cluster which contains the partial updates to be applied, subject to the update_mask. */
		cluster: Option[Schema.Cluster] = None,
	  /** Required. The subset of Cluster fields which should be replaced. */
		updateMask: Option[String] = None
	)
	
	case class UpdateClusterMetadata(
	  /** The request that prompted the initiation of this UpdateCluster operation. */
		originalRequest: Option[Schema.Cluster] = None,
	  /** The time at which the original request was received. */
		requestTime: Option[String] = None,
	  /** The time at which the operation failed or was completed successfully. */
		finishTime: Option[String] = None
	)
	
	case class UpdateAppProfileMetadata(
	
	)
	
	case class CreateBackupMetadata(
	  /** The name of the backup being created. */
		name: Option[String] = None,
	  /** The name of the table the backup is created from. */
		sourceTable: Option[String] = None,
	  /** The time at which this operation started. */
		startTime: Option[String] = None,
	  /** If set, the time at which this operation finished or was cancelled. */
		endTime: Option[String] = None
	)
	
	case class CreateAuthorizedViewMetadata(
	  /** The request that prompted the initiation of this CreateAuthorizedView operation. */
		originalRequest: Option[Schema.CreateAuthorizedViewRequest] = None,
	  /** The time at which the original request was received. */
		requestTime: Option[String] = None,
	  /** The time at which the operation failed or was completed successfully. */
		finishTime: Option[String] = None
	)
	
	case class CreateAuthorizedViewRequest(
	  /** Required. This is the name of the table the AuthorizedView belongs to. Values are of the form `projects/{project}/instances/{instance}/tables/{table}`. */
		parent: Option[String] = None,
	  /** Required. The id of the AuthorizedView to create. This AuthorizedView must not already exist. The `authorized_view_id` appended to `parent` forms the full AuthorizedView name of the form `projects/{project}/instances/{instance}/tables/{table}/authorizedView/{authorized_view}`. */
		authorizedViewId: Option[String] = None,
	  /** Required. The AuthorizedView to create. */
		authorizedView: Option[Schema.AuthorizedView] = None
	)
	
	case class CopyBackupMetadata(
	  /** The name of the backup being created through the copy operation. Values are of the form `projects//instances//clusters//backups/`. */
		name: Option[String] = None,
	  /** Information about the source backup that is being copied from. */
		sourceBackupInfo: Option[Schema.BackupInfo] = None,
	  /** The progress of the CopyBackup operation. */
		progress: Option[Schema.OperationProgress] = None
	)
	
	case class OperationProgress(
	  /** Percent completion of the operation. Values are between 0 and 100 inclusive. */
		progressPercent: Option[Int] = None,
	  /** Time the request was received. */
		startTime: Option[String] = None,
	  /** If set, the time at which this operation failed or was completed successfully. */
		endTime: Option[String] = None
	)
	
	object RestoreTableMetadata {
		enum SourceTypeEnum extends Enum[SourceTypeEnum] { case RESTORE_SOURCE_TYPE_UNSPECIFIED, BACKUP }
	}
	case class RestoreTableMetadata(
	  /** Name of the table being created and restored to. */
		name: Option[String] = None,
	  /** The type of the restore source. */
		sourceType: Option[Schema.RestoreTableMetadata.SourceTypeEnum] = None,
		backupInfo: Option[Schema.BackupInfo] = None,
	  /** If exists, the name of the long-running operation that will be used to track the post-restore optimization process to optimize the performance of the restored table. The metadata type of the long-running operation is OptimizeRestoreTableMetadata. The response type is Empty. This long-running operation may be automatically created by the system if applicable after the RestoreTable long-running operation completes successfully. This operation may not be created if the table is already optimized or the restore was not successful. */
		optimizeTableOperationName: Option[String] = None,
	  /** The progress of the RestoreTable operation. */
		progress: Option[Schema.OperationProgress] = None
	)
	
	case class OptimizeRestoredTableMetadata(
	  /** Name of the restored table being optimized. */
		name: Option[String] = None,
	  /** The progress of the post-restore optimizations. */
		progress: Option[Schema.OperationProgress] = None
	)
	
	case class UndeleteTableMetadata(
	  /** The name of the table being restored. */
		name: Option[String] = None,
	  /** The time at which this operation started. */
		startTime: Option[String] = None,
	  /** If set, the time at which this operation finished or was cancelled. */
		endTime: Option[String] = None
	)
	
	case class UpdateTableMetadata(
	  /** The name of the table being updated. */
		name: Option[String] = None,
	  /** The time at which this operation started. */
		startTime: Option[String] = None,
	  /** If set, the time at which this operation finished or was canceled. */
		endTime: Option[String] = None
	)
	
	case class UpdateAuthorizedViewMetadata(
	  /** The request that prompted the initiation of this UpdateAuthorizedView operation. */
		originalRequest: Option[Schema.UpdateAuthorizedViewRequest] = None,
	  /** The time at which the original request was received. */
		requestTime: Option[String] = None,
	  /** The time at which the operation failed or was completed successfully. */
		finishTime: Option[String] = None
	)
	
	case class UpdateAuthorizedViewRequest(
	  /** Required. The AuthorizedView to update. The `name` in `authorized_view` is used to identify the AuthorizedView. AuthorizedView name must in this format: `projects/{project}/instances/{instance}/tables/{table}/authorizedViews/{authorized_view}`. */
		authorizedView: Option[Schema.AuthorizedView] = None,
	  /** Optional. The list of fields to update. A mask specifying which fields in the AuthorizedView resource should be updated. This mask is relative to the AuthorizedView resource, not to the request message. A field will be overwritten if it is in the mask. If empty, all fields set in the request will be overwritten. A special value `&#42;` means to overwrite all fields (including fields not set in the request). */
		updateMask: Option[String] = None,
	  /** Optional. If true, ignore the safety checks when updating the AuthorizedView. */
		ignoreWarnings: Option[Boolean] = None
	)
}
