package com.boresjo.play.api.google.cloudasset

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
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
	
	object ExportAssetsRequest {
		enum ContentTypeEnum extends Enum[ContentTypeEnum] { case CONTENT_TYPE_UNSPECIFIED, RESOURCE, IAM_POLICY, ORG_POLICY, ACCESS_POLICY, OS_INVENTORY, RELATIONSHIP }
	}
	case class ExportAssetsRequest(
	  /** Timestamp to take an asset snapshot. This can only be set to a timestamp between the current time and the current time minus 35 days (inclusive). If not specified, the current time will be used. Due to delays in resource data collection and indexing, there is a volatile window during which running the same query may get different results. */
		readTime: Option[String] = None,
	  /** A list of asset types to take a snapshot for. For example: "compute.googleapis.com/Disk". Regular expressions are also supported. For example: &#42; "compute.googleapis.com.&#42;" snapshots resources whose asset type starts with "compute.googleapis.com". &#42; ".&#42;Instance" snapshots resources whose asset type ends with "Instance". &#42; ".&#42;Instance.&#42;" snapshots resources whose asset type contains "Instance". See [RE2](https://github.com/google/re2/wiki/Syntax) for all supported regular expression syntax. If the regular expression does not match any supported asset type, an INVALID_ARGUMENT error will be returned. If specified, only matching assets will be returned, otherwise, it will snapshot all asset types. See [Introduction to Cloud Asset Inventory](https://cloud.google.com/asset-inventory/docs/overview) for all supported asset types. */
		assetTypes: Option[List[String]] = None,
	  /** Asset content type. If not specified, no content but the asset name will be returned. */
		contentType: Option[Schema.ExportAssetsRequest.ContentTypeEnum] = None,
	  /** Required. Output configuration indicating where the results will be output to. */
		outputConfig: Option[Schema.OutputConfig] = None,
	  /** A list of relationship types to export, for example: `INSTANCE_TO_INSTANCEGROUP`. This field should only be specified if content_type=RELATIONSHIP. &#42; If specified: it snapshots specified relationships. It returns an error if any of the [relationship_types] doesn't belong to the supported relationship types of the [asset_types] or if any of the [asset_types] doesn't belong to the source types of the [relationship_types]. &#42; Otherwise: it snapshots the supported relationships for all [asset_types] or returns an error if any of the [asset_types] has no relationship support. An unspecified asset types field means all supported asset_types. See [Introduction to Cloud Asset Inventory](https://cloud.google.com/asset-inventory/docs/overview) for all supported asset types and relationship types. */
		relationshipTypes: Option[List[String]] = None
	)
	
	case class OutputConfig(
	  /** Destination on Cloud Storage. */
		gcsDestination: Option[Schema.GcsDestination] = None,
	  /** Destination on BigQuery. The output table stores the fields in asset Protobuf as columns in BigQuery. */
		bigqueryDestination: Option[Schema.BigQueryDestination] = None
	)
	
	case class GcsDestination(
	  /** The URI of the Cloud Storage object. It's the same URI that is used by gsutil. Example: "gs://bucket_name/object_name". See [Viewing and Editing Object Metadata](https://cloud.google.com/storage/docs/viewing-editing-metadata) for more information. If the specified Cloud Storage object already exists and there is no [hold](https://cloud.google.com/storage/docs/object-holds), it will be overwritten with the exported result. */
		uri: Option[String] = None,
	  /** The URI prefix of all generated Cloud Storage objects. Example: "gs://bucket_name/object_name_prefix". Each object URI is in format: "gs://bucket_name/object_name_prefix// and only contains assets for that type. starts from 0. Example: "gs://bucket_name/object_name_prefix/compute.googleapis.com/Disk/0" is the first shard of output objects containing all compute.googleapis.com/Disk assets. An INVALID_ARGUMENT error will be returned if file with the same name "gs://bucket_name/object_name_prefix" already exists. */
		uriPrefix: Option[String] = None
	)
	
	case class BigQueryDestination(
	  /** Required. The BigQuery dataset in format "projects/projectId/datasets/datasetId", to which the snapshot result should be exported. If this dataset does not exist, the export call returns an INVALID_ARGUMENT error. Setting the `contentType` for `exportAssets` determines the [schema](/asset-inventory/docs/exporting-to-bigquery#bigquery-schema) of the BigQuery table. Setting `separateTablesPerAssetType` to `TRUE` also influences the schema. */
		dataset: Option[String] = None,
	  /** Required. The BigQuery table to which the snapshot result should be written. If this table does not exist, a new table with the given name will be created. */
		table: Option[String] = None,
	  /** If the destination table already exists and this flag is `TRUE`, the table will be overwritten by the contents of assets snapshot. If the flag is `FALSE` or unset and the destination table already exists, the export call returns an INVALID_ARGUMEMT error. */
		force: Option[Boolean] = None,
	  /** [partition_spec] determines whether to export to partitioned table(s) and how to partition the data. If [partition_spec] is unset or [partition_spec.partition_key] is unset or `PARTITION_KEY_UNSPECIFIED`, the snapshot results will be exported to non-partitioned table(s). [force] will decide whether to overwrite existing table(s). If [partition_spec] is specified. First, the snapshot results will be written to partitioned table(s) with two additional timestamp columns, readTime and requestTime, one of which will be the partition key. Secondly, in the case when any destination table already exists, it will first try to update existing table's schema as necessary by appending additional columns. Then, if [force] is `TRUE`, the corresponding partition will be overwritten by the snapshot results (data in different partitions will remain intact); if [force] is unset or `FALSE`, it will append the data. An error will be returned if the schema update or data appension fails. */
		partitionSpec: Option[Schema.PartitionSpec] = None,
	  /** If this flag is `TRUE`, the snapshot results will be written to one or multiple tables, each of which contains results of one asset type. The [force] and [partition_spec] fields will apply to each of them. Field [table] will be concatenated with "_" and the asset type names (see https://cloud.google.com/asset-inventory/docs/supported-asset-types for supported asset types) to construct per-asset-type table names, in which all non-alphanumeric characters like "." and "/" will be substituted by "_". Example: if field [table] is "mytable" and snapshot results contain "storage.googleapis.com/Bucket" assets, the corresponding table name will be "mytable_storage_googleapis_com_Bucket". If any of these tables does not exist, a new table with the concatenated name will be created. When [content_type] in the ExportAssetsRequest is `RESOURCE`, the schema of each table will include RECORD-type columns mapped to the nested fields in the Asset.resource.data field of that asset type (up to the 15 nested level BigQuery supports (https://cloud.google.com/bigquery/docs/nested-repeated#limitations)). The fields in >15 nested levels will be stored in JSON format string as a child column of its parent RECORD column. If error occurs when exporting to any table, the whole export call will return an error but the export results that already succeed will persist. Example: if exporting to table_type_A succeeds when exporting to table_type_B fails during one export call, the results in table_type_A will persist and there will not be partial results persisting in a table. */
		separateTablesPerAssetType: Option[Boolean] = None
	)
	
	object PartitionSpec {
		enum PartitionKeyEnum extends Enum[PartitionKeyEnum] { case PARTITION_KEY_UNSPECIFIED, READ_TIME, REQUEST_TIME }
	}
	case class PartitionSpec(
	  /** The partition key for BigQuery partitioned table. */
		partitionKey: Option[Schema.PartitionSpec.PartitionKeyEnum] = None
	)
	
	case class ListAssetsResponse(
	  /** Time the snapshot was taken. */
		readTime: Option[String] = None,
	  /** Assets. */
		assets: Option[List[Schema.Asset]] = None,
	  /** Token to retrieve the next page of results. It expires 72 hours after the page token for the first page is generated. Set to empty if there are no remaining results. */
		nextPageToken: Option[String] = None
	)
	
	case class Asset(
	  /** The last update timestamp of an asset. update_time is updated when create/update/delete operation is performed. */
		updateTime: Option[String] = None,
	  /** The full name of the asset. Example: `//compute.googleapis.com/projects/my_project_123/zones/zone1/instances/instance1` See [Resource names](https://cloud.google.com/apis/design/resource_names#full_resource_name) for more information. */
		name: Option[String] = None,
	  /** The type of the asset. Example: `compute.googleapis.com/Disk` See [Supported asset types](https://cloud.google.com/asset-inventory/docs/supported-asset-types) for more information. */
		assetType: Option[String] = None,
	  /** A representation of the resource. */
		resource: Option[Schema.Resource] = None,
	  /** A representation of the IAM policy set on a Google Cloud resource. There can be a maximum of one IAM policy set on any given resource. In addition, IAM policies inherit their granted access scope from any policies set on parent resources in the resource hierarchy. Therefore, the effectively policy is the union of both the policy set on this resource and each policy set on all of the resource's ancestry resource levels in the hierarchy. See [this topic](https://cloud.google.com/iam/help/allow-policies/inheritance) for more information. */
		iamPolicy: Option[Schema.Policy] = None,
	  /** A representation of an [organization policy](https://cloud.google.com/resource-manager/docs/organization-policy/overview#organization_policy). There can be more than one organization policy with different constraints set on a given resource. */
		orgPolicy: Option[List[Schema.GoogleCloudOrgpolicyV1Policy]] = None,
	  /** Also refer to the [access policy user guide](https://cloud.google.com/access-context-manager/docs/overview#access-policies). */
		accessPolicy: Option[Schema.GoogleIdentityAccesscontextmanagerV1AccessPolicy] = None,
	  /** Also refer to the [access level user guide](https://cloud.google.com/access-context-manager/docs/overview#access-levels). */
		accessLevel: Option[Schema.GoogleIdentityAccesscontextmanagerV1AccessLevel] = None,
	  /** Also refer to the [service perimeter user guide](https://cloud.google.com/vpc-service-controls/docs/overview). */
		servicePerimeter: Option[Schema.GoogleIdentityAccesscontextmanagerV1ServicePerimeter] = None,
	  /** A representation of runtime OS Inventory information. See [this topic](https://cloud.google.com/compute/docs/instances/os-inventory-management) for more information. */
		osInventory: Option[Schema.Inventory] = None,
	  /** DEPRECATED. This field only presents for the purpose of backward-compatibility. The server will never generate responses with this field. The related assets of the asset of one relationship type. One asset only represents one type of relationship. */
		relatedAssets: Option[Schema.RelatedAssets] = None,
	  /** One related asset of the current asset. */
		relatedAsset: Option[Schema.RelatedAsset] = None,
	  /** The ancestry path of an asset in Google Cloud [resource hierarchy](https://cloud.google.com/resource-manager/docs/cloud-platform-resource-hierarchy), represented as a list of relative resource names. An ancestry path starts with the closest ancestor in the hierarchy and ends at root. If the asset is a project, folder, or organization, the ancestry path starts from the asset itself. Example: `["projects/123456789", "folders/5432", "organizations/1234"]` */
		ancestors: Option[List[String]] = None
	)
	
	case class Resource(
	  /** The API version. Example: `v1` */
		version: Option[String] = None,
	  /** The URL of the discovery document containing the resource's JSON schema. Example: `https://www.googleapis.com/discovery/v1/apis/compute/v1/rest` This value is unspecified for resources that do not have an API based on a discovery document, such as Cloud Bigtable. */
		discoveryDocumentUri: Option[String] = None,
	  /** The JSON schema name listed in the discovery document. Example: `Project` This value is unspecified for resources that do not have an API based on a discovery document, such as Cloud Bigtable. */
		discoveryName: Option[String] = None,
	  /** The REST URL for accessing the resource. An HTTP `GET` request using this URL returns the resource itself. Example: `https://cloudresourcemanager.googleapis.com/v1/projects/my-project-123` This value is unspecified for resources without a REST API. */
		resourceUrl: Option[String] = None,
	  /** The full name of the immediate parent of this resource. See [Resource Names](https://cloud.google.com/apis/design/resource_names#full_resource_name) for more information. For Google Cloud assets, this value is the parent resource defined in the [IAM policy hierarchy](https://cloud.google.com/iam/docs/overview#policy_hierarchy). Example: `//cloudresourcemanager.googleapis.com/projects/my_project_123` */
		parent: Option[String] = None,
	  /** The content of the resource, in which some sensitive fields are removed and may not be present. */
		data: Option[Map[String, JsValue]] = None,
	  /** The location of the resource in Google Cloud, such as its zone and region. For more information, see https://cloud.google.com/about/locations/. */
		location: Option[String] = None
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
	
	case class GoogleCloudOrgpolicyV1Policy(
	  /** Version of the `Policy`. Default version is 0; */
		version: Option[Int] = None,
	  /** The name of the `Constraint` the `Policy` is configuring, for example, `constraints/serviceuser.services`. A [list of available constraints](/resource-manager/docs/organization-policy/org-policy-constraints) is available. Immutable after creation. */
		constraint: Option[String] = None,
	  /** An opaque tag indicating the current version of the `Policy`, used for concurrency control. When the `Policy` is returned from either a `GetPolicy` or a `ListOrgPolicy` request, this `etag` indicates the version of the current `Policy` to use when executing a read-modify-write loop. When the `Policy` is returned from a `GetEffectivePolicy` request, the `etag` will be unset. When the `Policy` is used in a `SetOrgPolicy` method, use the `etag` value that was returned from a `GetOrgPolicy` request as part of a read-modify-write loop for concurrency control. Not setting the `etag`in a `SetOrgPolicy` request will result in an unconditional write of the `Policy`. */
		etag: Option[String] = None,
	  /** The time stamp the `Policy` was previously updated. This is set by the server, not specified by the caller, and represents the last time a call to `SetOrgPolicy` was made for that `Policy`. Any value set by the client will be ignored. */
		updateTime: Option[String] = None,
	  /** List of values either allowed or disallowed. */
		listPolicy: Option[Schema.GoogleCloudOrgpolicyV1ListPolicy] = None,
	  /** For boolean `Constraints`, whether to enforce the `Constraint` or not. */
		booleanPolicy: Option[Schema.GoogleCloudOrgpolicyV1BooleanPolicy] = None,
	  /** Restores the default behavior of the constraint; independent of `Constraint` type. */
		restoreDefault: Option[Schema.GoogleCloudOrgpolicyV1RestoreDefault] = None
	)
	
	object GoogleCloudOrgpolicyV1ListPolicy {
		enum AllValuesEnum extends Enum[AllValuesEnum] { case ALL_VALUES_UNSPECIFIED, ALLOW, DENY }
	}
	case class GoogleCloudOrgpolicyV1ListPolicy(
	  /** List of values allowed at this resource. Can only be set if `all_values` is set to `ALL_VALUES_UNSPECIFIED`. */
		allowedValues: Option[List[String]] = None,
	  /** List of values denied at this resource. Can only be set if `all_values` is set to `ALL_VALUES_UNSPECIFIED`. */
		deniedValues: Option[List[String]] = None,
	  /** The policy all_values state. */
		allValues: Option[Schema.GoogleCloudOrgpolicyV1ListPolicy.AllValuesEnum] = None,
	  /** Optional. The Google Cloud Console will try to default to a configuration that matches the value specified in this `Policy`. If `suggested_value` is not set, it will inherit the value specified higher in the hierarchy, unless `inherit_from_parent` is `false`. */
		suggestedValue: Option[String] = None,
	  /** Determines the inheritance behavior for this `Policy`. By default, a `ListPolicy` set at a resource supersedes any `Policy` set anywhere up the resource hierarchy. However, if `inherit_from_parent` is set to `true`, then the values from the effective `Policy` of the parent resource are inherited, meaning the values set in this `Policy` are added to the values inherited up the hierarchy. Setting `Policy` hierarchies that inherit both allowed values and denied values isn't recommended in most circumstances to keep the configuration simple and understandable. However, it is possible to set a `Policy` with `allowed_values` set that inherits a `Policy` with `denied_values` set. In this case, the values that are allowed must be in `allowed_values` and not present in `denied_values`. For example, suppose you have a `Constraint` `constraints/serviceuser.services`, which has a `constraint_type` of `list_constraint`, and with `constraint_default` set to `ALLOW`. Suppose that at the Organization level, a `Policy` is applied that restricts the allowed API activations to {`E1`, `E2`}. Then, if a `Policy` is applied to a project below the Organization that has `inherit_from_parent` set to `false` and field all_values set to DENY, then an attempt to activate any API will be denied. The following examples demonstrate different possible layerings for `projects/bar` parented by `organizations/foo`: Example 1 (no inherited values): `organizations/foo` has a `Policy` with values: {allowed_values: "E1" allowed_values:"E2"} `projects/bar` has `inherit_from_parent` `false` and values: {allowed_values: "E3" allowed_values: "E4"} The accepted values at `organizations/foo` are `E1`, `E2`. The accepted values at `projects/bar` are `E3`, and `E4`. Example 2 (inherited values): `organizations/foo` has a `Policy` with values: {allowed_values: "E1" allowed_values:"E2"} `projects/bar` has a `Policy` with values: {value: "E3" value: "E4" inherit_from_parent: true} The accepted values at `organizations/foo` are `E1`, `E2`. The accepted values at `projects/bar` are `E1`, `E2`, `E3`, and `E4`. Example 3 (inheriting both allowed and denied values): `organizations/foo` has a `Policy` with values: {allowed_values: "E1" allowed_values: "E2"} `projects/bar` has a `Policy` with: {denied_values: "E1"} The accepted values at `organizations/foo` are `E1`, `E2`. The value accepted at `projects/bar` is `E2`. Example 4 (RestoreDefault): `organizations/foo` has a `Policy` with values: {allowed_values: "E1" allowed_values:"E2"} `projects/bar` has a `Policy` with values: {RestoreDefault: {}} The accepted values at `organizations/foo` are `E1`, `E2`. The accepted values at `projects/bar` are either all or none depending on the value of `constraint_default` (if `ALLOW`, all; if `DENY`, none). Example 5 (no policy inherits parent policy): `organizations/foo` has no `Policy` set. `projects/bar` has no `Policy` set. The accepted values at both levels are either all or none depending on the value of `constraint_default` (if `ALLOW`, all; if `DENY`, none). Example 6 (ListConstraint allowing all): `organizations/foo` has a `Policy` with values: {allowed_values: "E1" allowed_values: "E2"} `projects/bar` has a `Policy` with: {all: ALLOW} The accepted values at `organizations/foo` are `E1`, E2`. Any value is accepted at `projects/bar`. Example 7 (ListConstraint allowing none): `organizations/foo` has a `Policy` with values: {allowed_values: "E1" allowed_values: "E2"} `projects/bar` has a `Policy` with: {all: DENY} The accepted values at `organizations/foo` are `E1`, E2`. No value is accepted at `projects/bar`. Example 10 (allowed and denied subtrees of Resource Manager hierarchy): Given the following resource hierarchy O1->{F1, F2}; F1->{P1}; F2->{P2, P3}, `organizations/foo` has a `Policy` with values: {allowed_values: "under:organizations/O1"} `projects/bar` has a `Policy` with: {allowed_values: "under:projects/P3"} {denied_values: "under:folders/F2"} The accepted values at `organizations/foo` are `organizations/O1`, `folders/F1`, `folders/F2`, `projects/P1`, `projects/P2`, `projects/P3`. The accepted values at `projects/bar` are `organizations/O1`, `folders/F1`, `projects/P1`. */
		inheritFromParent: Option[Boolean] = None
	)
	
	case class GoogleCloudOrgpolicyV1BooleanPolicy(
	  /** If `true`, then the `Policy` is enforced. If `false`, then any configuration is acceptable. Suppose you have a `Constraint` `constraints/compute.disableSerialPortAccess` with `constraint_default` set to `ALLOW`. A `Policy` for that `Constraint` exhibits the following behavior: - If the `Policy` at this resource has enforced set to `false`, serial port connection attempts will be allowed. - If the `Policy` at this resource has enforced set to `true`, serial port connection attempts will be refused. - If the `Policy` at this resource is `RestoreDefault`, serial port connection attempts will be allowed. - If no `Policy` is set at this resource or anywhere higher in the resource hierarchy, serial port connection attempts will be allowed. - If no `Policy` is set at this resource, but one exists higher in the resource hierarchy, the behavior is as if the`Policy` were set at this resource. The following examples demonstrate the different possible layerings: Example 1 (nearest `Constraint` wins): `organizations/foo` has a `Policy` with: {enforced: false} `projects/bar` has no `Policy` set. The constraint at `projects/bar` and `organizations/foo` will not be enforced. Example 2 (enforcement gets replaced): `organizations/foo` has a `Policy` with: {enforced: false} `projects/bar` has a `Policy` with: {enforced: true} The constraint at `organizations/foo` is not enforced. The constraint at `projects/bar` is enforced. Example 3 (RestoreDefault): `organizations/foo` has a `Policy` with: {enforced: true} `projects/bar` has a `Policy` with: {RestoreDefault: {}} The constraint at `organizations/foo` is enforced. The constraint at `projects/bar` is not enforced, because `constraint_default` for the `Constraint` is `ALLOW`. */
		enforced: Option[Boolean] = None
	)
	
	case class GoogleCloudOrgpolicyV1RestoreDefault(
	
	)
	
	case class GoogleIdentityAccesscontextmanagerV1AccessPolicy(
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
	
	case class GoogleIdentityAccesscontextmanagerV1AccessLevel(
	  /** Identifier. Resource name for the `AccessLevel`. Format: `accessPolicies/{access_policy}/accessLevels/{access_level}`. The `access_level` component must begin with a letter, followed by alphanumeric characters or `_`. Its maximum length is 50 characters. After you create an `AccessLevel`, you cannot change its `name`. */
		name: Option[String] = None,
	  /** Human readable title. Must be unique within the Policy. */
		title: Option[String] = None,
	  /** Description of the `AccessLevel` and its use. Does not affect behavior. */
		description: Option[String] = None,
	  /** A `BasicLevel` composed of `Conditions`. */
		basic: Option[Schema.GoogleIdentityAccesscontextmanagerV1BasicLevel] = None,
	  /** A `CustomLevel` written in the Common Expression Language. */
		custom: Option[Schema.GoogleIdentityAccesscontextmanagerV1CustomLevel] = None
	)
	
	object GoogleIdentityAccesscontextmanagerV1BasicLevel {
		enum CombiningFunctionEnum extends Enum[CombiningFunctionEnum] { case AND, OR }
	}
	case class GoogleIdentityAccesscontextmanagerV1BasicLevel(
	  /** Required. A list of requirements for the `AccessLevel` to be granted. */
		conditions: Option[List[Schema.GoogleIdentityAccesscontextmanagerV1Condition]] = None,
	  /** How the `conditions` list should be combined to determine if a request is granted this `AccessLevel`. If AND is used, each `Condition` in `conditions` must be satisfied for the `AccessLevel` to be applied. If OR is used, at least one `Condition` in `conditions` must be satisfied for the `AccessLevel` to be applied. Default behavior is AND. */
		combiningFunction: Option[Schema.GoogleIdentityAccesscontextmanagerV1BasicLevel.CombiningFunctionEnum] = None
	)
	
	case class GoogleIdentityAccesscontextmanagerV1Condition(
	  /** CIDR block IP subnetwork specification. May be IPv4 or IPv6. Note that for a CIDR IP address block, the specified IP address portion must be properly truncated (i.e. all the host bits must be zero) or the input is considered malformed. For example, "192.0.2.0/24" is accepted but "192.0.2.1/24" is not. Similarly, for IPv6, "2001:db8::/32" is accepted whereas "2001:db8::1/32" is not. The originating IP of a request must be in one of the listed subnets in order for this Condition to be true. If empty, all IP addresses are allowed. */
		ipSubnetworks: Option[List[String]] = None,
	  /** Device specific restrictions, all restrictions must hold for the Condition to be true. If not specified, all devices are allowed. */
		devicePolicy: Option[Schema.GoogleIdentityAccesscontextmanagerV1DevicePolicy] = None,
	  /** A list of other access levels defined in the same `Policy`, referenced by resource name. Referencing an `AccessLevel` which does not exist is an error. All access levels listed must be granted for the Condition to be true. Example: "`accessPolicies/MY_POLICY/accessLevels/LEVEL_NAME"` */
		requiredAccessLevels: Option[List[String]] = None,
	  /** Whether to negate the Condition. If true, the Condition becomes a NAND over its non-empty fields. Any non-empty field criteria evaluating to false will result in the Condition to be satisfied. Defaults to false. */
		negate: Option[Boolean] = None,
	  /** The request must be made by one of the provided user or service accounts. Groups are not supported. Syntax: `user:{emailid}` `serviceAccount:{emailid}` If not specified, a request may come from any user. */
		members: Option[List[String]] = None,
	  /** The request must originate from one of the provided countries/regions. Must be valid ISO 3166-1 alpha-2 codes. */
		regions: Option[List[String]] = None,
	  /** The request must originate from one of the provided VPC networks in Google Cloud. Cannot specify this field together with `ip_subnetworks`. */
		vpcNetworkSources: Option[List[Schema.GoogleIdentityAccesscontextmanagerV1VpcNetworkSource]] = None
	)
	
	object GoogleIdentityAccesscontextmanagerV1DevicePolicy {
		enum AllowedEncryptionStatusesEnum extends Enum[AllowedEncryptionStatusesEnum] { case ENCRYPTION_UNSPECIFIED, ENCRYPTION_UNSUPPORTED, UNENCRYPTED, ENCRYPTED }
		enum AllowedDeviceManagementLevelsEnum extends Enum[AllowedDeviceManagementLevelsEnum] { case MANAGEMENT_UNSPECIFIED, NONE, BASIC, COMPLETE }
	}
	case class GoogleIdentityAccesscontextmanagerV1DevicePolicy(
	  /** Whether or not screenlock is required for the DevicePolicy to be true. Defaults to `false`. */
		requireScreenlock: Option[Boolean] = None,
	  /** Allowed encryptions statuses, an empty list allows all statuses. */
		allowedEncryptionStatuses: Option[List[Schema.GoogleIdentityAccesscontextmanagerV1DevicePolicy.AllowedEncryptionStatusesEnum]] = None,
	  /** Allowed OS versions, an empty list allows all types and all versions. */
		osConstraints: Option[List[Schema.GoogleIdentityAccesscontextmanagerV1OsConstraint]] = None,
	  /** Allowed device management levels, an empty list allows all management levels. */
		allowedDeviceManagementLevels: Option[List[Schema.GoogleIdentityAccesscontextmanagerV1DevicePolicy.AllowedDeviceManagementLevelsEnum]] = None,
	  /** Whether the device needs to be approved by the customer admin. */
		requireAdminApproval: Option[Boolean] = None,
	  /** Whether the device needs to be corp owned. */
		requireCorpOwned: Option[Boolean] = None
	)
	
	object GoogleIdentityAccesscontextmanagerV1OsConstraint {
		enum OsTypeEnum extends Enum[OsTypeEnum] { case OS_UNSPECIFIED, DESKTOP_MAC, DESKTOP_WINDOWS, DESKTOP_LINUX, DESKTOP_CHROME_OS, ANDROID, IOS }
	}
	case class GoogleIdentityAccesscontextmanagerV1OsConstraint(
	  /** Required. The allowed OS type. */
		osType: Option[Schema.GoogleIdentityAccesscontextmanagerV1OsConstraint.OsTypeEnum] = None,
	  /** The minimum allowed OS version. If not set, any version of this OS satisfies the constraint. Format: `"major.minor.patch"`. Examples: `"10.5.301"`, `"9.2.1"`. */
		minimumVersion: Option[String] = None,
	  /** Only allows requests from devices with a verified Chrome OS. Verifications includes requirements that the device is enterprise-managed, conformant to domain policies, and the caller has permission to call the API targeted by the request. */
		requireVerifiedChromeOs: Option[Boolean] = None
	)
	
	case class GoogleIdentityAccesscontextmanagerV1VpcNetworkSource(
	  /** Sub-segment ranges of a VPC network. */
		vpcSubnetwork: Option[Schema.GoogleIdentityAccesscontextmanagerV1VpcSubNetwork] = None
	)
	
	case class GoogleIdentityAccesscontextmanagerV1VpcSubNetwork(
	  /** Required. Network name. If the network is not part of the organization, the `compute.network.get` permission must be granted to the caller. Format: `//compute.googleapis.com/projects/{PROJECT_ID}/global/networks/{NETWORK_NAME}` Example: `//compute.googleapis.com/projects/my-project/global/networks/network-1` */
		network: Option[String] = None,
	  /** CIDR block IP subnetwork specification. The IP address must be an IPv4 address and can be a public or private IP address. Note that for a CIDR IP address block, the specified IP address portion must be properly truncated (i.e. all the host bits must be zero) or the input is considered malformed. For example, "192.0.2.0/24" is accepted but "192.0.2.1/24" is not. If empty, all IP addresses are allowed. */
		vpcIpSubnetworks: Option[List[String]] = None
	)
	
	case class GoogleIdentityAccesscontextmanagerV1CustomLevel(
	  /** Required. A Cloud CEL expression evaluating to a boolean. */
		expr: Option[Schema.Expr] = None
	)
	
	object GoogleIdentityAccesscontextmanagerV1ServicePerimeter {
		enum PerimeterTypeEnum extends Enum[PerimeterTypeEnum] { case PERIMETER_TYPE_REGULAR, PERIMETER_TYPE_BRIDGE }
	}
	case class GoogleIdentityAccesscontextmanagerV1ServicePerimeter(
	  /** Identifier. Resource name for the `ServicePerimeter`. Format: `accessPolicies/{access_policy}/servicePerimeters/{service_perimeter}`. The `service_perimeter` component must begin with a letter, followed by alphanumeric characters or `_`. After you create a `ServicePerimeter`, you cannot change its `name`. */
		name: Option[String] = None,
	  /** Human readable title. Must be unique within the Policy. */
		title: Option[String] = None,
	  /** Description of the `ServicePerimeter` and its use. Does not affect behavior. */
		description: Option[String] = None,
	  /** Perimeter type indicator. A single project or VPC network is allowed to be a member of single regular perimeter, but multiple service perimeter bridges. A project cannot be a included in a perimeter bridge without being included in regular perimeter. For perimeter bridges, the restricted service list as well as access level lists must be empty. */
		perimeterType: Option[Schema.GoogleIdentityAccesscontextmanagerV1ServicePerimeter.PerimeterTypeEnum] = None,
	  /** Current ServicePerimeter configuration. Specifies sets of resources, restricted services and access levels that determine perimeter content and boundaries. */
		status: Option[Schema.GoogleIdentityAccesscontextmanagerV1ServicePerimeterConfig] = None,
	  /** Proposed (or dry run) ServicePerimeter configuration. This configuration allows to specify and test ServicePerimeter configuration without enforcing actual access restrictions. Only allowed to be set when the "use_explicit_dry_run_spec" flag is set. */
		spec: Option[Schema.GoogleIdentityAccesscontextmanagerV1ServicePerimeterConfig] = None,
	  /** Use explicit dry run spec flag. Ordinarily, a dry-run spec implicitly exists for all Service Perimeters, and that spec is identical to the status for those Service Perimeters. When this flag is set, it inhibits the generation of the implicit spec, thereby allowing the user to explicitly provide a configuration ("spec") to use in a dry-run version of the Service Perimeter. This allows the user to test changes to the enforced config ("status") without actually enforcing them. This testing is done through analyzing the differences between currently enforced and suggested restrictions. use_explicit_dry_run_spec must bet set to True if any of the fields in the spec are set to non-default values. */
		useExplicitDryRunSpec: Option[Boolean] = None,
	  /** Optional. An opaque identifier for the current version of the `ServicePerimeter`. Clients should not expect this to be in any specific format. If etag is not provided, the operation will be performed as if a valid etag is provided. */
		etag: Option[String] = None
	)
	
	case class GoogleIdentityAccesscontextmanagerV1ServicePerimeterConfig(
	  /** A list of Google Cloud resources that are inside of the service perimeter. Currently only projects and VPCs are allowed. Project format: `projects/{project_number}` VPC network format: `//compute.googleapis.com/projects/{PROJECT_ID}/global/networks/{NAME}`. */
		resources: Option[List[String]] = None,
	  /** A list of `AccessLevel` resource names that allow resources within the `ServicePerimeter` to be accessed from the internet. `AccessLevels` listed must be in the same policy as this `ServicePerimeter`. Referencing a nonexistent `AccessLevel` is a syntax error. If no `AccessLevel` names are listed, resources within the perimeter can only be accessed via Google Cloud calls with request origins within the perimeter. Example: `"accessPolicies/MY_POLICY/accessLevels/MY_LEVEL"`. For Service Perimeter Bridge, must be empty. */
		accessLevels: Option[List[String]] = None,
	  /** Google Cloud services that are subject to the Service Perimeter restrictions. For example, if `storage.googleapis.com` is specified, access to the storage buckets inside the perimeter must meet the perimeter's access restrictions. */
		restrictedServices: Option[List[String]] = None,
	  /** Configuration for APIs allowed within Perimeter. */
		vpcAccessibleServices: Option[Schema.GoogleIdentityAccesscontextmanagerV1VpcAccessibleServices] = None,
	  /** List of IngressPolicies to apply to the perimeter. A perimeter may have multiple IngressPolicies, each of which is evaluated separately. Access is granted if any Ingress Policy grants it. Must be empty for a perimeter bridge. */
		ingressPolicies: Option[List[Schema.GoogleIdentityAccesscontextmanagerV1IngressPolicy]] = None,
	  /** List of EgressPolicies to apply to the perimeter. A perimeter may have multiple EgressPolicies, each of which is evaluated separately. Access is granted if any EgressPolicy grants it. Must be empty for a perimeter bridge. */
		egressPolicies: Option[List[Schema.GoogleIdentityAccesscontextmanagerV1EgressPolicy]] = None
	)
	
	case class GoogleIdentityAccesscontextmanagerV1VpcAccessibleServices(
	  /** Whether to restrict API calls within the Service Perimeter to the list of APIs specified in 'allowed_services'. */
		enableRestriction: Option[Boolean] = None,
	  /** The list of APIs usable within the Service Perimeter. Must be empty unless 'enable_restriction' is True. You can specify a list of individual services, as well as include the 'RESTRICTED-SERVICES' value, which automatically includes all of the services protected by the perimeter. */
		allowedServices: Option[List[String]] = None
	)
	
	case class GoogleIdentityAccesscontextmanagerV1IngressPolicy(
	  /** Defines the conditions on the source of a request causing this IngressPolicy to apply. */
		ingressFrom: Option[Schema.GoogleIdentityAccesscontextmanagerV1IngressFrom] = None,
	  /** Defines the conditions on the ApiOperation and request destination that cause this IngressPolicy to apply. */
		ingressTo: Option[Schema.GoogleIdentityAccesscontextmanagerV1IngressTo] = None
	)
	
	object GoogleIdentityAccesscontextmanagerV1IngressFrom {
		enum IdentityTypeEnum extends Enum[IdentityTypeEnum] { case IDENTITY_TYPE_UNSPECIFIED, ANY_IDENTITY, ANY_USER_ACCOUNT, ANY_SERVICE_ACCOUNT }
	}
	case class GoogleIdentityAccesscontextmanagerV1IngressFrom(
	  /** Sources that this IngressPolicy authorizes access from. */
		sources: Option[List[Schema.GoogleIdentityAccesscontextmanagerV1IngressSource]] = None,
	  /** A list of identities that are allowed access through [IngressPolicy]. Identities can be an individual user, service account, Google group, or third-party identity. For third-party identity, only single identities are supported and other identity types are not supported. The `v1` identities that have the prefix `user`, `group`, `serviceAccount`, and `principal` in https://cloud.google.com/iam/docs/principal-identifiers#v1 are supported. */
		identities: Option[List[String]] = None,
	  /** Specifies the type of identities that are allowed access from outside the perimeter. If left unspecified, then members of `identities` field will be allowed access. */
		identityType: Option[Schema.GoogleIdentityAccesscontextmanagerV1IngressFrom.IdentityTypeEnum] = None
	)
	
	case class GoogleIdentityAccesscontextmanagerV1IngressSource(
	  /** An AccessLevel resource name that allow resources within the ServicePerimeters to be accessed from the internet. AccessLevels listed must be in the same policy as this ServicePerimeter. Referencing a nonexistent AccessLevel will cause an error. If no AccessLevel names are listed, resources within the perimeter can only be accessed via Google Cloud calls with request origins within the perimeter. Example: `accessPolicies/MY_POLICY/accessLevels/MY_LEVEL`. If a single `&#42;` is specified for `access_level`, then all IngressSources will be allowed. */
		accessLevel: Option[String] = None,
	  /** A Google Cloud resource that is allowed to ingress the perimeter. Requests from these resources will be allowed to access perimeter data. Currently only projects and VPCs are allowed. Project format: `projects/{project_number}` VPC network format: `//compute.googleapis.com/projects/{PROJECT_ID}/global/networks/{NAME}`. The project may be in any Google Cloud organization, not just the organization that the perimeter is defined in. `&#42;` is not allowed, the case of allowing all Google Cloud resources only is not supported. */
		resource: Option[String] = None
	)
	
	case class GoogleIdentityAccesscontextmanagerV1IngressTo(
	  /** A list of ApiOperations allowed to be performed by the sources specified in corresponding IngressFrom in this ServicePerimeter. */
		operations: Option[List[Schema.GoogleIdentityAccesscontextmanagerV1ApiOperation]] = None,
	  /** A list of resources, currently only projects in the form `projects/`, protected by this ServicePerimeter that are allowed to be accessed by sources defined in the corresponding IngressFrom. If a single `&#42;` is specified, then access to all resources inside the perimeter are allowed. */
		resources: Option[List[String]] = None
	)
	
	case class GoogleIdentityAccesscontextmanagerV1ApiOperation(
	  /** The name of the API whose methods or permissions the IngressPolicy or EgressPolicy want to allow. A single ApiOperation with `service_name` field set to `&#42;` will allow all methods AND permissions for all services. */
		serviceName: Option[String] = None,
	  /** API methods or permissions to allow. Method or permission must belong to the service specified by `service_name` field. A single MethodSelector entry with `&#42;` specified for the `method` field will allow all methods AND permissions for the service specified in `service_name`. */
		methodSelectors: Option[List[Schema.GoogleIdentityAccesscontextmanagerV1MethodSelector]] = None
	)
	
	case class GoogleIdentityAccesscontextmanagerV1MethodSelector(
	  /** A valid method name for the corresponding `service_name` in ApiOperation. If `&#42;` is used as the value for the `method`, then ALL methods and permissions are allowed. */
		method: Option[String] = None,
	  /** A valid Cloud IAM permission for the corresponding `service_name` in ApiOperation. */
		permission: Option[String] = None
	)
	
	case class GoogleIdentityAccesscontextmanagerV1EgressPolicy(
	  /** Defines conditions on the source of a request causing this EgressPolicy to apply. */
		egressFrom: Option[Schema.GoogleIdentityAccesscontextmanagerV1EgressFrom] = None,
	  /** Defines the conditions on the ApiOperation and destination resources that cause this EgressPolicy to apply. */
		egressTo: Option[Schema.GoogleIdentityAccesscontextmanagerV1EgressTo] = None
	)
	
	object GoogleIdentityAccesscontextmanagerV1EgressFrom {
		enum IdentityTypeEnum extends Enum[IdentityTypeEnum] { case IDENTITY_TYPE_UNSPECIFIED, ANY_IDENTITY, ANY_USER_ACCOUNT, ANY_SERVICE_ACCOUNT }
		enum SourceRestrictionEnum extends Enum[SourceRestrictionEnum] { case SOURCE_RESTRICTION_UNSPECIFIED, SOURCE_RESTRICTION_ENABLED, SOURCE_RESTRICTION_DISABLED }
	}
	case class GoogleIdentityAccesscontextmanagerV1EgressFrom(
	  /** A list of identities that are allowed access through [EgressPolicy]. Identities can be an individual user, service account, Google group, or third-party identity. For third-party identity, only single identities are supported and other identity types are not supported. The `v1` identities that have the prefix `user`, `group`, `serviceAccount`, and `principal` in https://cloud.google.com/iam/docs/principal-identifiers#v1 are supported. */
		identities: Option[List[String]] = None,
	  /** Specifies the type of identities that are allowed access to outside the perimeter. If left unspecified, then members of `identities` field will be allowed access. */
		identityType: Option[Schema.GoogleIdentityAccesscontextmanagerV1EgressFrom.IdentityTypeEnum] = None,
	  /** Sources that this EgressPolicy authorizes access from. If this field is not empty, then `source_restriction` must be set to `SOURCE_RESTRICTION_ENABLED`. */
		sources: Option[List[Schema.GoogleIdentityAccesscontextmanagerV1EgressSource]] = None,
	  /** Whether to enforce traffic restrictions based on `sources` field. If the `sources` fields is non-empty, then this field must be set to `SOURCE_RESTRICTION_ENABLED`. */
		sourceRestriction: Option[Schema.GoogleIdentityAccesscontextmanagerV1EgressFrom.SourceRestrictionEnum] = None
	)
	
	case class GoogleIdentityAccesscontextmanagerV1EgressSource(
	  /** An AccessLevel resource name that allows protected resources inside the ServicePerimeters to access outside the ServicePerimeter boundaries. AccessLevels listed must be in the same policy as this ServicePerimeter. Referencing a nonexistent AccessLevel will cause an error. If an AccessLevel name is not specified, only resources within the perimeter can be accessed through Google Cloud calls with request origins within the perimeter. Example: `accessPolicies/MY_POLICY/accessLevels/MY_LEVEL`. If a single `&#42;` is specified for `access_level`, then all EgressSources will be allowed. */
		accessLevel: Option[String] = None
	)
	
	case class GoogleIdentityAccesscontextmanagerV1EgressTo(
	  /** A list of resources, currently only projects in the form `projects/`, that are allowed to be accessed by sources defined in the corresponding EgressFrom. A request matches if it contains a resource in this list. If `&#42;` is specified for `resources`, then this EgressTo rule will authorize access to all resources outside the perimeter. */
		resources: Option[List[String]] = None,
	  /** A list of ApiOperations allowed to be performed by the sources specified in the corresponding EgressFrom. A request matches if it uses an operation/service in this list. */
		operations: Option[List[Schema.GoogleIdentityAccesscontextmanagerV1ApiOperation]] = None,
	  /** A list of external resources that are allowed to be accessed. Only AWS and Azure resources are supported. For Amazon S3, the supported formats are s3://BUCKET_NAME, s3a://BUCKET_NAME, and s3n://BUCKET_NAME. For Azure Storage, the supported format is azure://myaccount.blob.core.windows.net/CONTAINER_NAME. A request matches if it contains an external resource in this list (Example: s3://bucket/path). Currently '&#42;' is not allowed. */
		externalResources: Option[List[String]] = None
	)
	
	case class Inventory(
	  /** Output only. The `Inventory` API resource name. Format: `projects/{project_number}/locations/{location}/instances/{instance_id}/inventory` */
		name: Option[String] = None,
	  /** Base level operating system information for the VM. */
		osInfo: Option[Schema.OsInfo] = None,
	  /** Inventory items related to the VM keyed by an opaque unique identifier for each inventory item. The identifier is unique to each distinct and addressable inventory item and will change, when there is a new package version. */
		items: Option[Map[String, Schema.Item]] = None,
	  /** Output only. Timestamp of the last reported inventory for the VM. */
		updateTime: Option[String] = None
	)
	
	case class OsInfo(
	  /** The VM hostname. */
		hostname: Option[String] = None,
	  /** The operating system long name. For example 'Debian GNU/Linux 9' or 'Microsoft Window Server 2019 Datacenter'. */
		longName: Option[String] = None,
	  /** The operating system short name. For example, 'windows' or 'debian'. */
		shortName: Option[String] = None,
	  /** The version of the operating system. */
		version: Option[String] = None,
	  /** The system architecture of the operating system. */
		architecture: Option[String] = None,
	  /** The kernel version of the operating system. */
		kernelVersion: Option[String] = None,
	  /** The kernel release of the operating system. */
		kernelRelease: Option[String] = None,
	  /** The current version of the OS Config agent running on the VM. */
		osconfigAgentVersion: Option[String] = None
	)
	
	object Item {
		enum OriginTypeEnum extends Enum[OriginTypeEnum] { case ORIGIN_TYPE_UNSPECIFIED, INVENTORY_REPORT }
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, INSTALLED_PACKAGE, AVAILABLE_PACKAGE }
	}
	case class Item(
	  /** Identifier for this item, unique across items for this VM. */
		id: Option[String] = None,
	  /** The origin of this inventory item. */
		originType: Option[Schema.Item.OriginTypeEnum] = None,
	  /** When this inventory item was first detected. */
		createTime: Option[String] = None,
	  /** When this inventory item was last modified. */
		updateTime: Option[String] = None,
	  /** The specific type of inventory, correlating to its specific details. */
		`type`: Option[Schema.Item.TypeEnum] = None,
	  /** Software package present on the VM instance. */
		installedPackage: Option[Schema.SoftwarePackage] = None,
	  /** Software package available to be installed on the VM instance. */
		availablePackage: Option[Schema.SoftwarePackage] = None
	)
	
	case class SoftwarePackage(
	  /** Yum package info. For details about the yum package manager, see https://access.redhat.com/documentation/en-us/red_hat_enterprise_linux/6/html/deployment_guide/ch-yum. */
		yumPackage: Option[Schema.VersionedPackage] = None,
	  /** Details of an APT package. For details about the apt package manager, see https://wiki.debian.org/Apt. */
		aptPackage: Option[Schema.VersionedPackage] = None,
	  /** Details of a Zypper package. For details about the Zypper package manager, see https://en.opensuse.org/SDB:Zypper_manual. */
		zypperPackage: Option[Schema.VersionedPackage] = None,
	  /** Details of a Googet package. For details about the googet package manager, see https://github.com/google/googet. */
		googetPackage: Option[Schema.VersionedPackage] = None,
	  /** Details of a Zypper patch. For details about the Zypper package manager, see https://en.opensuse.org/SDB:Zypper_manual. */
		zypperPatch: Option[Schema.ZypperPatch] = None,
	  /** Details of a Windows Update package. See https://docs.microsoft.com/en-us/windows/win32/api/_wua/ for information about Windows Update. */
		wuaPackage: Option[Schema.WindowsUpdatePackage] = None,
	  /** Details of a Windows Quick Fix engineering package. See https://docs.microsoft.com/en-us/windows/win32/cimwin32prov/win32-quickfixengineering for info in Windows Quick Fix Engineering. */
		qfePackage: Option[Schema.WindowsQuickFixEngineeringPackage] = None,
	  /** Details of a COS package. */
		cosPackage: Option[Schema.VersionedPackage] = None,
	  /** Details of Windows Application. */
		windowsApplication: Option[Schema.WindowsApplication] = None
	)
	
	case class VersionedPackage(
	  /** The name of the package. */
		packageName: Option[String] = None,
	  /** The system architecture this package is intended for. */
		architecture: Option[String] = None,
	  /** The version of the package. */
		version: Option[String] = None
	)
	
	case class ZypperPatch(
	  /** The name of the patch. */
		patchName: Option[String] = None,
	  /** The category of the patch. */
		category: Option[String] = None,
	  /** The severity specified for this patch */
		severity: Option[String] = None,
	  /** Any summary information provided about this patch. */
		summary: Option[String] = None
	)
	
	case class WindowsUpdatePackage(
	  /** The localized title of the update package. */
		title: Option[String] = None,
	  /** The localized description of the update package. */
		description: Option[String] = None,
	  /** The categories that are associated with this update package. */
		categories: Option[List[Schema.WindowsUpdateCategory]] = None,
	  /** A collection of Microsoft Knowledge Base article IDs that are associated with the update package. */
		kbArticleIds: Option[List[String]] = None,
	  /** A hyperlink to the language-specific support information for the update. */
		supportUrl: Option[String] = None,
	  /** A collection of URLs that provide more information about the update package. */
		moreInfoUrls: Option[List[String]] = None,
	  /** Gets the identifier of an update package. Stays the same across revisions. */
		updateId: Option[String] = None,
	  /** The revision number of this update package. */
		revisionNumber: Option[Int] = None,
	  /** The last published date of the update, in (UTC) date and time. */
		lastDeploymentChangeTime: Option[String] = None
	)
	
	case class WindowsUpdateCategory(
	  /** The identifier of the windows update category. */
		id: Option[String] = None,
	  /** The name of the windows update category. */
		name: Option[String] = None
	)
	
	case class WindowsQuickFixEngineeringPackage(
	  /** A short textual description of the QFE update. */
		caption: Option[String] = None,
	  /** A textual description of the QFE update. */
		description: Option[String] = None,
	  /** Unique identifier associated with a particular QFE update. */
		hotFixId: Option[String] = None,
	  /** Date that the QFE update was installed. Mapped from installed_on field. */
		installTime: Option[String] = None
	)
	
	case class WindowsApplication(
	  /** The name of the application or product. */
		displayName: Option[String] = None,
	  /** The version of the product or application in string format. */
		displayVersion: Option[String] = None,
	  /** The name of the manufacturer for the product or application. */
		publisher: Option[String] = None,
	  /** The last time this product received service. The value of this property is replaced each time a patch is applied or removed from the product or the command-line option is used to repair the product. */
		installDate: Option[Schema.Date] = None,
	  /** The internet address for technical support. */
		helpLink: Option[String] = None
	)
	
	case class Date(
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None,
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None,
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None
	)
	
	case class RelatedAssets(
	  /** The detailed relationship attributes. */
		relationshipAttributes: Option[Schema.RelationshipAttributes] = None,
	  /** The peer resources of the relationship. */
		assets: Option[List[Schema.RelatedAsset]] = None
	)
	
	case class RelationshipAttributes(
	  /** The unique identifier of the relationship type. Example: `INSTANCE_TO_INSTANCEGROUP` */
		`type`: Option[String] = None,
	  /** The source asset type. Example: `compute.googleapis.com/Instance` */
		sourceResourceType: Option[String] = None,
	  /** The target asset type. Example: `compute.googleapis.com/Disk` */
		targetResourceType: Option[String] = None,
	  /** The detail of the relationship, e.g. `contains`, `attaches` */
		action: Option[String] = None
	)
	
	case class RelatedAsset(
	  /** The full name of the asset. Example: `//compute.googleapis.com/projects/my_project_123/zones/zone1/instances/instance1` See [Resource names](https://cloud.google.com/apis/design/resource_names#full_resource_name) for more information. */
		asset: Option[String] = None,
	  /** The type of the asset. Example: `compute.googleapis.com/Disk` See [Supported asset types](https://cloud.google.com/asset-inventory/docs/supported-asset-types) for more information. */
		assetType: Option[String] = None,
	  /** The ancestors of an asset in Google Cloud [resource hierarchy](https://cloud.google.com/resource-manager/docs/cloud-platform-resource-hierarchy), represented as a list of relative resource names. An ancestry path starts with the closest ancestor in the hierarchy and ends at root. Example: `["projects/123456789", "folders/5432", "organizations/1234"]` */
		ancestors: Option[List[String]] = None,
	  /** The unique identifier of the relationship type. Example: `INSTANCE_TO_INSTANCEGROUP` */
		relationshipType: Option[String] = None
	)
	
	case class BatchGetAssetsHistoryResponse(
	  /** A list of assets with valid time windows. */
		assets: Option[List[Schema.TemporalAsset]] = None
	)
	
	object TemporalAsset {
		enum PriorAssetStateEnum extends Enum[PriorAssetStateEnum] { case PRIOR_ASSET_STATE_UNSPECIFIED, PRESENT, INVALID, DOES_NOT_EXIST, DELETED }
	}
	case class TemporalAsset(
	  /** The time window when the asset data and state was observed. */
		window: Option[Schema.TimeWindow] = None,
	  /** Whether the asset has been deleted or not. */
		deleted: Option[Boolean] = None,
	  /** An asset in Google Cloud. */
		asset: Option[Schema.Asset] = None,
	  /** State of prior_asset. */
		priorAssetState: Option[Schema.TemporalAsset.PriorAssetStateEnum] = None,
	  /** Prior copy of the asset. Populated if prior_asset_state is PRESENT. Currently this is only set for responses in Real-Time Feed. */
		priorAsset: Option[Schema.Asset] = None
	)
	
	case class TimeWindow(
	  /** Start time of the time window (exclusive). */
		startTime: Option[String] = None,
	  /** End time of the time window (inclusive). If not specified, the current timestamp is used instead. */
		endTime: Option[String] = None
	)
	
	case class CreateFeedRequest(
	  /** Required. This is the client-assigned asset feed identifier and it needs to be unique under a specific parent project/folder/organization. */
		feedId: Option[String] = None,
	  /** Required. The feed details. The field `name` must be empty and it will be generated in the format of: projects/project_number/feeds/feed_id folders/folder_number/feeds/feed_id organizations/organization_number/feeds/feed_id */
		feed: Option[Schema.Feed] = None
	)
	
	object Feed {
		enum ContentTypeEnum extends Enum[ContentTypeEnum] { case CONTENT_TYPE_UNSPECIFIED, RESOURCE, IAM_POLICY, ORG_POLICY, ACCESS_POLICY, OS_INVENTORY, RELATIONSHIP }
	}
	case class Feed(
	  /** Required. The format will be projects/{project_number}/feeds/{client-assigned_feed_identifier} or folders/{folder_number}/feeds/{client-assigned_feed_identifier} or organizations/{organization_number}/feeds/{client-assigned_feed_identifier} The client-assigned feed identifier must be unique within the parent project/folder/organization. */
		name: Option[String] = None,
	  /** A list of the full names of the assets to receive updates. You must specify either or both of asset_names and asset_types. Only asset updates matching specified asset_names or asset_types are exported to the feed. Example: `//compute.googleapis.com/projects/my_project_123/zones/zone1/instances/instance1`. For a list of the full names for supported asset types, see [Resource name format](/asset-inventory/docs/resource-name-format). */
		assetNames: Option[List[String]] = None,
	  /** A list of types of the assets to receive updates. You must specify either or both of asset_names and asset_types. Only asset updates matching specified asset_names or asset_types are exported to the feed. Example: `"compute.googleapis.com/Disk"` For a list of all supported asset types, see [Supported asset types](/asset-inventory/docs/supported-asset-types). */
		assetTypes: Option[List[String]] = None,
	  /** Asset content type. If not specified, no content but the asset name and type will be returned. */
		contentType: Option[Schema.Feed.ContentTypeEnum] = None,
	  /** Required. Feed output configuration defining where the asset updates are published to. */
		feedOutputConfig: Option[Schema.FeedOutputConfig] = None,
	  /** A condition which determines whether an asset update should be published. If specified, an asset will be returned only when the expression evaluates to true. When set, `expression` field in the `Expr` must be a valid [CEL expression] (https://github.com/google/cel-spec) on a TemporalAsset with name `temporal_asset`. Example: a Feed with expression ("temporal_asset.deleted == true") will only publish Asset deletions. Other fields of `Expr` are optional. See our [user guide](https://cloud.google.com/asset-inventory/docs/monitoring-asset-changes-with-condition) for detailed instructions. */
		condition: Option[Schema.Expr] = None,
	  /** A list of relationship types to output, for example: `INSTANCE_TO_INSTANCEGROUP`. This field should only be specified if content_type=RELATIONSHIP. &#42; If specified: it outputs specified relationship updates on the [asset_names] or the [asset_types]. It returns an error if any of the [relationship_types] doesn't belong to the supported relationship types of the [asset_names] or [asset_types], or any of the [asset_names] or the [asset_types] doesn't belong to the source types of the [relationship_types]. &#42; Otherwise: it outputs the supported relationships of the types of [asset_names] and [asset_types] or returns an error if any of the [asset_names] or the [asset_types] has no replationship support. See [Introduction to Cloud Asset Inventory](https://cloud.google.com/asset-inventory/docs/overview) for all supported asset types and relationship types. */
		relationshipTypes: Option[List[String]] = None
	)
	
	case class FeedOutputConfig(
	  /** Destination on Pub/Sub. */
		pubsubDestination: Option[Schema.PubsubDestination] = None
	)
	
	case class PubsubDestination(
	  /** The name of the Pub/Sub topic to publish to. Example: `projects/PROJECT_ID/topics/TOPIC_ID`. */
		topic: Option[String] = None
	)
	
	case class ListFeedsResponse(
	  /** A list of feeds. */
		feeds: Option[List[Schema.Feed]] = None
	)
	
	case class UpdateFeedRequest(
	  /** Required. The new values of feed details. It must match an existing feed and the field `name` must be in the format of: projects/project_number/feeds/feed_id or folders/folder_number/feeds/feed_id or organizations/organization_number/feeds/feed_id. */
		feed: Option[Schema.Feed] = None,
	  /** Required. Only updates the `feed` fields indicated by this mask. The field mask must not be empty, and it must not contain fields that are immutable or only set by the server. */
		updateMask: Option[String] = None
	)
	
	case class Empty(
	
	)
	
	case class SearchAllResourcesResponse(
	  /** A list of Resources that match the search query. It contains the resource standard metadata information. */
		results: Option[List[Schema.ResourceSearchResult]] = None,
	  /** If there are more results than those appearing in this response, then `next_page_token` is included. To get the next set of results, call this method again using the value of `next_page_token` as `page_token`. */
		nextPageToken: Option[String] = None
	)
	
	case class ResourceSearchResult(
	  /** The full resource name of this resource. Example: `//compute.googleapis.com/projects/my_project_123/zones/zone1/instances/instance1`. See [Cloud Asset Inventory Resource Name Format](https://cloud.google.com/asset-inventory/docs/resource-name-format) for more information. To search against the `name`: &#42; Use a field query. Example: `name:instance1` &#42; Use a free text query. Example: `instance1` */
		name: Option[String] = None,
	  /** The type of this resource. Example: `compute.googleapis.com/Disk`. To search against the `asset_type`: &#42; Specify the `asset_type` field in your search request. */
		assetType: Option[String] = None,
	  /** The project that this resource belongs to, in the form of projects/{PROJECT_NUMBER}. This field is available when the resource belongs to a project. To search against `project`: &#42; Use a field query. Example: `project:12345` &#42; Use a free text query. Example: `12345` &#42; Specify the `scope` field as this project in your search request. */
		project: Option[String] = None,
	  /** The folder(s) that this resource belongs to, in the form of folders/{FOLDER_NUMBER}. This field is available when the resource belongs to one or more folders. To search against `folders`: &#42; Use a field query. Example: `folders:(123 OR 456)` &#42; Use a free text query. Example: `123` &#42; Specify the `scope` field as this folder in your search request. */
		folders: Option[List[String]] = None,
	  /** The organization that this resource belongs to, in the form of organizations/{ORGANIZATION_NUMBER}. This field is available when the resource belongs to an organization. To search against `organization`: &#42; Use a field query. Example: `organization:123` &#42; Use a free text query. Example: `123` &#42; Specify the `scope` field as this organization in your search request. */
		organization: Option[String] = None,
	  /** The display name of this resource. This field is available only when the resource's Protobuf contains it. To search against the `display_name`: &#42; Use a field query. Example: `displayName:"My Instance"` &#42; Use a free text query. Example: `"My Instance"` */
		displayName: Option[String] = None,
	  /** One or more paragraphs of text description of this resource. Maximum length could be up to 1M bytes. This field is available only when the resource's Protobuf contains it. To search against the `description`: &#42; Use a field query. Example: `description:"important instance"` &#42; Use a free text query. Example: `"important instance"` */
		description: Option[String] = None,
	  /** Location can be `global`, regional like `us-east1`, or zonal like `us-west1-b`. This field is available only when the resource's Protobuf contains it. To search against the `location`: &#42; Use a field query. Example: `location:us-west&#42;` &#42; Use a free text query. Example: `us-west&#42;` */
		location: Option[String] = None,
	  /** User labels associated with this resource. See [Labelling and grouping Google Cloud resources](https://cloud.google.com/blog/products/gcp/labelling-and-grouping-your-google-cloud-platform-resources) for more information. This field is available only when the resource's Protobuf contains it. To search against the `labels`: &#42; Use a field query: - query on any label's key or value. Example: `labels:prod` - query by a given label. Example: `labels.env:prod` - query by a given label's existence. Example: `labels.env:&#42;` &#42; Use a free text query. Example: `prod` */
		labels: Option[Map[String, String]] = None,
	  /** Network tags associated with this resource. Like labels, network tags are a type of annotations used to group Google Cloud resources. See [Labelling Google Cloud resources](https://cloud.google.com/blog/products/gcp/labelling-and-grouping-your-google-cloud-platform-resources) for more information. This field is available only when the resource's Protobuf contains it. To search against the `network_tags`: &#42; Use a field query. Example: `networkTags:internal` &#42; Use a free text query. Example: `internal` */
		networkTags: Option[List[String]] = None,
	  /** The Cloud KMS [CryptoKey](https://cloud.google.com/kms/docs/reference/rest/v1/projects.locations.keyRings.cryptoKeys) name or [CryptoKeyVersion](https://cloud.google.com/kms/docs/reference/rest/v1/projects.locations.keyRings.cryptoKeys.cryptoKeyVersions) name. This field only presents for the purpose of backward compatibility. Use the `kms_keys` field to retrieve Cloud KMS key information. This field is available only when the resource's Protobuf contains it and will only be populated for [these resource types](https://cloud.google.com/asset-inventory/docs/legacy-field-names#resource_types_with_the_to_be_deprecated_kmskey_field) for backward compatible purposes. To search against the `kms_key`: &#42; Use a field query. Example: `kmsKey:key` &#42; Use a free text query. Example: `key` */
		kmsKey: Option[String] = None,
	  /** The Cloud KMS [CryptoKey](https://cloud.google.com/kms/docs/reference/rest/v1/projects.locations.keyRings.cryptoKeys) names or [CryptoKeyVersion](https://cloud.google.com/kms/docs/reference/rest/v1/projects.locations.keyRings.cryptoKeys.cryptoKeyVersions) names. This field is available only when the resource's Protobuf contains it. To search against the `kms_keys`: &#42; Use a field query. Example: `kmsKeys:key` &#42; Use a free text query. Example: `key` */
		kmsKeys: Option[List[String]] = None,
	  /** The create timestamp of this resource, at which the resource was created. The granularity is in seconds. Timestamp.nanos will always be 0. This field is available only when the resource's Protobuf contains it. To search against `create_time`: &#42; Use a field query. - value in seconds since unix epoch. Example: `createTime > 1609459200` - value in date string. Example: `createTime > 2021-01-01` - value in date-time string (must be quoted). Example: `createTime > "2021-01-01T00:00:00"` */
		createTime: Option[String] = None,
	  /** The last update timestamp of this resource, at which the resource was last modified or deleted. The granularity is in seconds. Timestamp.nanos will always be 0. This field is available only when the resource's Protobuf contains it. To search against `update_time`: &#42; Use a field query. - value in seconds since unix epoch. Example: `updateTime < 1609459200` - value in date string. Example: `updateTime < 2021-01-01` - value in date-time string (must be quoted). Example: `updateTime < "2021-01-01T00:00:00"` */
		updateTime: Option[String] = None,
	  /** The state of this resource. Different resources types have different state definitions that are mapped from various fields of different resource types. This field is available only when the resource's Protobuf contains it. Example: If the resource is an instance provided by Compute Engine, its state will include PROVISIONING, STAGING, RUNNING, STOPPING, SUSPENDING, SUSPENDED, REPAIRING, and TERMINATED. See `status` definition in [API Reference](https://cloud.google.com/compute/docs/reference/rest/v1/instances). If the resource is a project provided by Resource Manager, its state will include LIFECYCLE_STATE_UNSPECIFIED, ACTIVE, DELETE_REQUESTED and DELETE_IN_PROGRESS. See `lifecycleState` definition in [API Reference](https://cloud.google.com/resource-manager/reference/rest/v1/projects). To search against the `state`: &#42; Use a field query. Example: `state:RUNNING` &#42; Use a free text query. Example: `RUNNING` */
		state: Option[String] = None,
	  /** The additional searchable attributes of this resource. The attributes may vary from one resource type to another. Examples: `projectId` for Project, `dnsName` for DNS ManagedZone. This field contains a subset of the resource metadata fields that are returned by the List or Get APIs provided by the corresponding Google Cloud service (e.g., Compute Engine). see [API references and supported searchable attributes](https://cloud.google.com/asset-inventory/docs/supported-asset-types) to see which fields are included. You can search values of these fields through free text search. However, you should not consume the field programically as the field names and values may change as the Google Cloud service updates to a new incompatible API version. To search against the `additional_attributes`: &#42; Use a free text query to match the attributes values. Example: to search `additional_attributes = { dnsName: "foobar" }`, you can issue a query `foobar`. */
		additionalAttributes: Option[Map[String, JsValue]] = None,
	  /** The full resource name of this resource's parent, if it has one. To search against the `parent_full_resource_name`: &#42; Use a field query. Example: `parentFullResourceName:"project-name"` &#42; Use a free text query. Example: `project-name` */
		parentFullResourceName: Option[String] = None,
	  /** Versioned resource representations of this resource. This is repeated because there could be multiple versions of resource representations during version migration. This `versioned_resources` field is not searchable. Some attributes of the resource representations are exposed in `additional_attributes` field, so as to allow users to search on them. */
		versionedResources: Option[List[Schema.VersionedResource]] = None,
	  /** Attached resources of this resource. For example, an OSConfig Inventory is an attached resource of a Compute Instance. This field is repeated because a resource could have multiple attached resources. This `attached_resources` field is not searchable. Some attributes of the attached resources are exposed in `additional_attributes` field, so as to allow users to search on them. */
		attachedResources: Option[List[Schema.AttachedResource]] = None,
	  /** A map of related resources of this resource, keyed by the relationship type. A relationship type is in the format of {SourceType}_{ACTION}_{DestType}. Example: `DISK_TO_INSTANCE`, `DISK_TO_NETWORK`, `INSTANCE_TO_INSTANCEGROUP`. See [supported relationship types](https://cloud.google.com/asset-inventory/docs/supported-asset-types#supported_relationship_types). */
		relationships: Option[Map[String, Schema.RelatedResources]] = None,
	  /** This field is only present for the purpose of backward compatibility. Use the `tags` field instead. TagKey namespaced names, in the format of {ORG_ID}/{TAG_KEY_SHORT_NAME}. To search against the `tagKeys`: &#42; Use a field query. Example: - `tagKeys:"123456789/env&#42;"` - `tagKeys="123456789/env"` - `tagKeys:"env"` &#42; Use a free text query. Example: - `env` */
		tagKeys: Option[List[String]] = None,
	  /** This field is only present for the purpose of backward compatibility. Use the `tags` field instead. TagValue namespaced names, in the format of {ORG_ID}/{TAG_KEY_SHORT_NAME}/{TAG_VALUE_SHORT_NAME}. To search against the `tagValues`: &#42; Use a field query. Example: - `tagValues:"env"` - `tagValues:"env/prod"` - `tagValues:"123456789/env/prod&#42;"` - `tagValues="123456789/env/prod"` &#42; Use a free text query. Example: - `prod` */
		tagValues: Option[List[String]] = None,
	  /** This field is only present for the purpose of backward compatibility. Use the `tags` field instead. TagValue IDs, in the format of tagValues/{TAG_VALUE_ID}. To search against the `tagValueIds`: &#42; Use a field query. Example: - `tagValueIds="tagValues/456"` &#42; Use a free text query. Example: - `456` */
		tagValueIds: Option[List[String]] = None,
	  /** The tags directly attached to this resource. To search against the `tags`: &#42; Use a field query. Example: - `tagKeys:"123456789/env&#42;"` - `tagKeys="123456789/env"` - `tagKeys:"env"` - `tagKeyIds="tagKeys/123"` - `tagValues:"env"` - `tagValues:"env/prod"` - `tagValues:"123456789/env/prod&#42;"` - `tagValues="123456789/env/prod"` - `tagValueIds="tagValues/456"` &#42; Use a free text query. Example: - `env/prod` */
		tags: Option[List[Schema.Tag]] = None,
	  /** The effective tags on this resource. All of the tags that are both attached to and inherited by a resource are collectively called the effective tags. For more information, see [tag inheritance](https://cloud.google.com/resource-manager/docs/tags/tags-overview#inheritance). To search against the `effective_tags`: &#42; Use a field query. Example: - `effectiveTagKeys:"123456789/env&#42;"` - `effectiveTagKeys="123456789/env"` - `effectiveTagKeys:"env"` - `effectiveTagKeyIds="tagKeys/123"` - `effectiveTagValues:"env"` - `effectiveTagValues:"env/prod"` - `effectiveTagValues:"123456789/env/prod&#42;"` - `effectiveTagValues="123456789/env/prod"` - `effectiveTagValueIds="tagValues/456"` */
		effectiveTags: Option[List[Schema.EffectiveTagDetails]] = None,
	  /** Enrichments of the asset. Currently supported enrichment types with SearchAllResources API: &#42; RESOURCE_OWNERS The corresponding read masks in order to get the enrichment: &#42; enrichments.resource_owners The corresponding required permissions: &#42; cloudasset.assets.searchEnrichmentResourceOwners Example query to get resource owner enrichment: ``` scope: "projects/my-project" query: "name: my-project" assetTypes: "cloudresourcemanager.googleapis.com/Project" readMask: { paths: "asset_type" paths: "name" paths: "enrichments.resource_owners" } ``` */
		enrichments: Option[List[Schema.AssetEnrichment]] = None,
	  /** The type of this resource's immediate parent, if there is one. To search against the `parent_asset_type`: &#42; Use a field query. Example: `parentAssetType:"cloudresourcemanager.googleapis.com/Project"` &#42; Use a free text query. Example: `cloudresourcemanager.googleapis.com/Project` */
		parentAssetType: Option[String] = None,
	  /** The actual content of Security Command Center security marks associated with the asset. To search against SCC SecurityMarks field: &#42; Use a field query: - query by a given key value pair. Example: `sccSecurityMarks.foo=bar` - query by a given key's existence. Example: `sccSecurityMarks.foo:&#42;` */
		sccSecurityMarks: Option[Map[String, String]] = None
	)
	
	case class VersionedResource(
	  /** API version of the resource. Example: If the resource is an instance provided by Compute Engine v1 API as defined in `https://cloud.google.com/compute/docs/reference/rest/v1/instances`, version will be "v1". */
		version: Option[String] = None,
	  /** JSON representation of the resource as defined by the corresponding service providing this resource. Example: If the resource is an instance provided by Compute Engine, this field will contain the JSON representation of the instance as defined by Compute Engine: `https://cloud.google.com/compute/docs/reference/rest/v1/instances`. You can find the resource definition for each supported resource type in this table: `https://cloud.google.com/asset-inventory/docs/supported-asset-types` */
		resource: Option[Map[String, JsValue]] = None
	)
	
	case class AttachedResource(
	  /** The type of this attached resource. Example: `osconfig.googleapis.com/Inventory` You can find the supported attached asset types of each resource in this table: `https://cloud.google.com/asset-inventory/docs/supported-asset-types` */
		assetType: Option[String] = None,
	  /** Versioned resource representations of this attached resource. This is repeated because there could be multiple versions of the attached resource representations during version migration. */
		versionedResources: Option[List[Schema.VersionedResource]] = None
	)
	
	case class RelatedResources(
	  /** The detailed related resources of the primary resource. */
		relatedResources: Option[List[Schema.RelatedResource]] = None
	)
	
	case class RelatedResource(
	  /** The type of the asset. Example: `compute.googleapis.com/Instance` */
		assetType: Option[String] = None,
	  /** The full resource name of the related resource. Example: `//compute.googleapis.com/projects/my_proj_123/zones/instance/instance123` */
		fullResourceName: Option[String] = None
	)
	
	case class Tag(
	  /** TagKey namespaced name, in the format of {ORG_ID}/{TAG_KEY_SHORT_NAME}. */
		tagKey: Option[String] = None,
	  /** TagKey ID, in the format of tagKeys/{TAG_KEY_ID}. */
		tagKeyId: Option[String] = None,
	  /** TagValue namespaced name, in the format of {ORG_ID}/{TAG_KEY_SHORT_NAME}/{TAG_VALUE_SHORT_NAME}. */
		tagValue: Option[String] = None,
	  /** TagValue ID, in the format of tagValues/{TAG_VALUE_ID}. */
		tagValueId: Option[String] = None
	)
	
	case class EffectiveTagDetails(
	  /** The [full resource name](https://cloud.google.com/asset-inventory/docs/resource-name-format) of the ancestor from which effective_tags are inherited, according to [tag inheritance](https://cloud.google.com/resource-manager/docs/tags/tags-overview#inheritance). */
		attachedResource: Option[String] = None,
	  /** The effective tags inherited from the attached_resource. Note that tags with the same key but different values may attach to resources at a different hierarchy levels. The lower hierarchy tag value will overwrite the higher hierarchy tag value of the same tag key. In this case, the tag value at the higher hierarchy level will be removed. For more information, see [tag inheritance](https://cloud.google.com/resource-manager/docs/tags/tags-overview#inheritance). */
		effectiveTags: Option[List[Schema.Tag]] = None
	)
	
	case class AssetEnrichment(
	  /** The resource owners for a resource. Note that this field only contains the members that have "roles/owner" role in the resource's IAM Policy. */
		resourceOwners: Option[Schema.ResourceOwners] = None
	)
	
	case class ResourceOwners(
	  /** List of resource owners. */
		resourceOwners: Option[List[String]] = None
	)
	
	case class SearchAllIamPoliciesResponse(
	  /** A list of IAM policies that match the search query. Related information such as the associated resource is returned along with the policy. */
		results: Option[List[Schema.IamPolicySearchResult]] = None,
	  /** Set if there are more results than those appearing in this response; to get the next set of results, call this method again, using this value as the `page_token`. */
		nextPageToken: Option[String] = None
	)
	
	case class IamPolicySearchResult(
	  /** The full resource name of the resource associated with this IAM policy. Example: `//compute.googleapis.com/projects/my_project_123/zones/zone1/instances/instance1`. See [Cloud Asset Inventory Resource Name Format](https://cloud.google.com/asset-inventory/docs/resource-name-format) for more information. To search against the `resource`: &#42; use a field query. Example: `resource:organizations/123` */
		resource: Option[String] = None,
	  /** The type of the resource associated with this IAM policy. Example: `compute.googleapis.com/Disk`. To search against the `asset_type`: &#42; specify the `asset_types` field in your search request. */
		assetType: Option[String] = None,
	  /** The project that the associated Google Cloud resource belongs to, in the form of projects/{PROJECT_NUMBER}. If an IAM policy is set on a resource (like VM instance, Cloud Storage bucket), the project field will indicate the project that contains the resource. If an IAM policy is set on a folder or orgnization, this field will be empty. To search against the `project`: &#42; specify the `scope` field as this project in your search request. */
		project: Option[String] = None,
	  /** The folder(s) that the IAM policy belongs to, in the form of folders/{FOLDER_NUMBER}. This field is available when the IAM policy belongs to one or more folders. To search against `folders`: &#42; use a field query. Example: `folders:(123 OR 456)` &#42; use a free text query. Example: `123` &#42; specify the `scope` field as this folder in your search request. */
		folders: Option[List[String]] = None,
	  /** The organization that the IAM policy belongs to, in the form of organizations/{ORGANIZATION_NUMBER}. This field is available when the IAM policy belongs to an organization. To search against `organization`: &#42; use a field query. Example: `organization:123` &#42; use a free text query. Example: `123` &#42; specify the `scope` field as this organization in your search request. */
		organization: Option[String] = None,
	  /** The IAM policy directly set on the given resource. Note that the original IAM policy can contain multiple bindings. This only contains the bindings that match the given query. For queries that don't contain a constrain on policies (e.g., an empty query), this contains all the bindings. To search against the `policy` bindings: &#42; use a field query: - query by the policy contained members. Example: `policy:amy@gmail.com` - query by the policy contained roles. Example: `policy:roles/compute.admin` - query by the policy contained roles' included permissions. Example: `policy.role.permissions:compute.instances.create` */
		policy: Option[Schema.Policy] = None,
	  /** Explanation about the IAM policy search result. It contains additional information to explain why the search result matches the query. */
		explanation: Option[Schema.Explanation] = None
	)
	
	case class Explanation(
	  /** The map from roles to their included permissions that match the permission query (i.e., a query containing `policy.role.permissions:`). Example: if query `policy.role.permissions:compute.disk.get` matches a policy binding that contains owner role, the matched_permissions will be `{"roles/owner": ["compute.disk.get"]}`. The roles can also be found in the returned `policy` bindings. Note that the map is populated only for requests with permission queries. */
		matchedPermissions: Option[Map[String, Schema.Permissions]] = None
	)
	
	case class Permissions(
	  /** A list of permissions. A sample permission string: `compute.disk.get`. */
		permissions: Option[List[String]] = None
	)
	
	case class AnalyzeIamPolicyResponse(
	  /** The main analysis that matches the original request. */
		mainAnalysis: Option[Schema.IamPolicyAnalysis] = None,
	  /** The service account impersonation analysis if IamPolicyAnalysisQuery.Options.analyze_service_account_impersonation is enabled. */
		serviceAccountImpersonationAnalysis: Option[List[Schema.IamPolicyAnalysis]] = None,
	  /** Represents whether all entries in the main_analysis and service_account_impersonation_analysis have been fully explored to answer the query in the request. */
		fullyExplored: Option[Boolean] = None
	)
	
	case class IamPolicyAnalysis(
	  /** The analysis query. */
		analysisQuery: Option[Schema.IamPolicyAnalysisQuery] = None,
	  /** A list of IamPolicyAnalysisResult that matches the analysis query, or empty if no result is found. */
		analysisResults: Option[List[Schema.IamPolicyAnalysisResult]] = None,
	  /** Represents whether all entries in the analysis_results have been fully explored to answer the query. */
		fullyExplored: Option[Boolean] = None,
	  /** A list of non-critical errors happened during the query handling. */
		nonCriticalErrors: Option[List[Schema.IamPolicyAnalysisState]] = None
	)
	
	case class IamPolicyAnalysisQuery(
	  /** Required. The relative name of the root asset. Only resources and IAM policies within the scope will be analyzed. This can only be an organization number (such as "organizations/123"), a folder number (such as "folders/123"), a project ID (such as "projects/my-project-id"), or a project number (such as "projects/12345"). To know how to get organization ID, visit [here ](https://cloud.google.com/resource-manager/docs/creating-managing-organization#retrieving_your_organization_id). To know how to get folder or project ID, visit [here ](https://cloud.google.com/resource-manager/docs/creating-managing-folders#viewing_or_listing_folders_and_projects). */
		scope: Option[String] = None,
	  /** Optional. Specifies a resource for analysis. */
		resourceSelector: Option[Schema.ResourceSelector] = None,
	  /** Optional. Specifies an identity for analysis. */
		identitySelector: Option[Schema.IdentitySelector] = None,
	  /** Optional. Specifies roles or permissions for analysis. This is optional. */
		accessSelector: Option[Schema.AccessSelector] = None,
	  /** Optional. The query options. */
		options: Option[Schema.Options] = None,
	  /** Optional. The hypothetical context for IAM conditions evaluation. */
		conditionContext: Option[Schema.ConditionContext] = None
	)
	
	case class ResourceSelector(
	  /** Required. The [full resource name] (https://cloud.google.com/asset-inventory/docs/resource-name-format) of a resource of [supported resource types](https://cloud.google.com/asset-inventory/docs/supported-asset-types#analyzable_asset_types). */
		fullResourceName: Option[String] = None
	)
	
	case class IdentitySelector(
	  /** Required. The identity appear in the form of principals in [IAM policy binding](https://cloud.google.com/iam/reference/rest/v1/Binding). The examples of supported forms are: "user:mike@example.com", "group:admins@example.com", "domain:google.com", "serviceAccount:my-project-id@appspot.gserviceaccount.com". Notice that wildcard characters (such as &#42; and ?) are not supported. You must give a specific identity. */
		identity: Option[String] = None
	)
	
	case class AccessSelector(
	  /** Optional. The roles to appear in result. */
		roles: Option[List[String]] = None,
	  /** Optional. The permissions to appear in result. */
		permissions: Option[List[String]] = None
	)
	
	case class Options(
	  /** Optional. If true, the identities section of the result will expand any Google groups appearing in an IAM policy binding. If IamPolicyAnalysisQuery.identity_selector is specified, the identity in the result will be determined by the selector, and this flag is not allowed to set. If true, the default max expansion per group is 1000 for AssetService.AnalyzeIamPolicy][]. Default is false. */
		expandGroups: Option[Boolean] = None,
	  /** Optional. If true, the access section of result will expand any roles appearing in IAM policy bindings to include their permissions. If IamPolicyAnalysisQuery.access_selector is specified, the access section of the result will be determined by the selector, and this flag is not allowed to set. Default is false. */
		expandRoles: Option[Boolean] = None,
	  /** Optional. If true and IamPolicyAnalysisQuery.resource_selector is not specified, the resource section of the result will expand any resource attached to an IAM policy to include resources lower in the resource hierarchy. For example, if the request analyzes for which resources user A has permission P, and the results include an IAM policy with P on a Google Cloud folder, the results will also include resources in that folder with permission P. If true and IamPolicyAnalysisQuery.resource_selector is specified, the resource section of the result will expand the specified resource to include resources lower in the resource hierarchy. Only project or lower resources are supported. Folder and organization resources cannot be used together with this option. For example, if the request analyzes for which users have permission P on a Google Cloud project with this option enabled, the results will include all users who have permission P on that project or any lower resource. If true, the default max expansion per resource is 1000 for AssetService.AnalyzeIamPolicy][] and 100000 for AssetService.AnalyzeIamPolicyLongrunning][]. Default is false. */
		expandResources: Option[Boolean] = None,
	  /** Optional. If true, the result will output the relevant parent/child relationships between resources. Default is false. */
		outputResourceEdges: Option[Boolean] = None,
	  /** Optional. If true, the result will output the relevant membership relationships between groups and other groups, and between groups and principals. Default is false. */
		outputGroupEdges: Option[Boolean] = None,
	  /** Optional. If true, the response will include access analysis from identities to resources via service account impersonation. This is a very expensive operation, because many derived queries will be executed. We highly recommend you use AssetService.AnalyzeIamPolicyLongrunning RPC instead. For example, if the request analyzes for which resources user A has permission P, and there's an IAM policy states user A has iam.serviceAccounts.getAccessToken permission to a service account SA, and there's another IAM policy states service account SA has permission P to a Google Cloud folder F, then user A potentially has access to the Google Cloud folder F. And those advanced analysis results will be included in AnalyzeIamPolicyResponse.service_account_impersonation_analysis. Another example, if the request analyzes for who has permission P to a Google Cloud folder F, and there's an IAM policy states user A has iam.serviceAccounts.actAs permission to a service account SA, and there's another IAM policy states service account SA has permission P to the Google Cloud folder F, then user A potentially has access to the Google Cloud folder F. And those advanced analysis results will be included in AnalyzeIamPolicyResponse.service_account_impersonation_analysis. Only the following permissions are considered in this analysis: &#42; `iam.serviceAccounts.actAs` &#42; `iam.serviceAccounts.signBlob` &#42; `iam.serviceAccounts.signJwt` &#42; `iam.serviceAccounts.getAccessToken` &#42; `iam.serviceAccounts.getOpenIdToken` &#42; `iam.serviceAccounts.implicitDelegation` Default is false. */
		analyzeServiceAccountImpersonation: Option[Boolean] = None
	)
	
	case class ConditionContext(
	  /** The hypothetical access timestamp to evaluate IAM conditions. Note that this value must not be earlier than the current time; otherwise, an INVALID_ARGUMENT error will be returned. */
		accessTime: Option[String] = None
	)
	
	case class IamPolicyAnalysisResult(
	  /** The [full resource name](https://cloud.google.com/asset-inventory/docs/resource-name-format) of the resource to which the iam_binding policy attaches. */
		attachedResourceFullName: Option[String] = None,
	  /** The IAM policy binding under analysis. */
		iamBinding: Option[Schema.Binding] = None,
	  /** The access control lists derived from the iam_binding that match or potentially match resource and access selectors specified in the request. */
		accessControlLists: Option[List[Schema.GoogleCloudAssetV1AccessControlList]] = None,
	  /** The identity list derived from members of the iam_binding that match or potentially match identity selector specified in the request. */
		identityList: Option[Schema.GoogleCloudAssetV1IdentityList] = None,
	  /** Represents whether all analyses on the iam_binding have successfully finished. */
		fullyExplored: Option[Boolean] = None
	)
	
	case class GoogleCloudAssetV1AccessControlList(
	  /** The resources that match one of the following conditions: - The resource_selector, if it is specified in request; - Otherwise, resources reachable from the policy attached resource. */
		resources: Option[List[Schema.GoogleCloudAssetV1Resource]] = None,
	  /** The accesses that match one of the following conditions: - The access_selector, if it is specified in request; - Otherwise, access specifiers reachable from the policy binding's role. */
		accesses: Option[List[Schema.GoogleCloudAssetV1Access]] = None,
	  /** Resource edges of the graph starting from the policy attached resource to any descendant resources. The Edge.source_node contains the full resource name of a parent resource and Edge.target_node contains the full resource name of a child resource. This field is present only if the output_resource_edges option is enabled in request. */
		resourceEdges: Option[List[Schema.GoogleCloudAssetV1Edge]] = None,
	  /** Condition evaluation for this AccessControlList, if there is a condition defined in the above IAM policy binding. */
		conditionEvaluation: Option[Schema.ConditionEvaluation] = None
	)
	
	case class GoogleCloudAssetV1Resource(
	  /** The [full resource name](https://cloud.google.com/asset-inventory/docs/resource-name-format) */
		fullResourceName: Option[String] = None,
	  /** The analysis state of this resource. */
		analysisState: Option[Schema.IamPolicyAnalysisState] = None
	)
	
	object IamPolicyAnalysisState {
		enum CodeEnum extends Enum[CodeEnum] { case OK, CANCELLED, UNKNOWN, INVALID_ARGUMENT, DEADLINE_EXCEEDED, NOT_FOUND, ALREADY_EXISTS, PERMISSION_DENIED, UNAUTHENTICATED, RESOURCE_EXHAUSTED, FAILED_PRECONDITION, ABORTED, OUT_OF_RANGE, UNIMPLEMENTED, INTERNAL, UNAVAILABLE, DATA_LOSS }
	}
	case class IamPolicyAnalysisState(
	  /** The Google standard error code that best describes the state. For example: - OK means the analysis on this entity has been successfully finished; - PERMISSION_DENIED means an access denied error is encountered; - DEADLINE_EXCEEDED means the analysis on this entity hasn't been started in time; */
		code: Option[Schema.IamPolicyAnalysisState.CodeEnum] = None,
	  /** The human-readable description of the cause of failure. */
		cause: Option[String] = None
	)
	
	case class GoogleCloudAssetV1Access(
	  /** The role. */
		role: Option[String] = None,
	  /** The permission. */
		permission: Option[String] = None,
	  /** The analysis state of this access. */
		analysisState: Option[Schema.IamPolicyAnalysisState] = None
	)
	
	case class GoogleCloudAssetV1Edge(
	  /** The source node of the edge. For example, it could be a full resource name for a resource node or an email of an identity. */
		sourceNode: Option[String] = None,
	  /** The target node of the edge. For example, it could be a full resource name for a resource node or an email of an identity. */
		targetNode: Option[String] = None
	)
	
	object ConditionEvaluation {
		enum EvaluationValueEnum extends Enum[EvaluationValueEnum] { case EVALUATION_VALUE_UNSPECIFIED, TRUE, FALSE, CONDITIONAL }
	}
	case class ConditionEvaluation(
	  /** The evaluation result. */
		evaluationValue: Option[Schema.ConditionEvaluation.EvaluationValueEnum] = None
	)
	
	case class GoogleCloudAssetV1IdentityList(
	  /** Only the identities that match one of the following conditions will be presented: - The identity_selector, if it is specified in request; - Otherwise, identities reachable from the policy binding's members. */
		identities: Option[List[Schema.GoogleCloudAssetV1Identity]] = None,
	  /** Group identity edges of the graph starting from the binding's group members to any node of the identities. The Edge.source_node contains a group, such as `group:parent@google.com`. The Edge.target_node contains a member of the group, such as `group:child@google.com` or `user:foo@google.com`. This field is present only if the output_group_edges option is enabled in request. */
		groupEdges: Option[List[Schema.GoogleCloudAssetV1Edge]] = None
	)
	
	case class GoogleCloudAssetV1Identity(
	  /** The identity of members, formatted as appear in an [IAM policy binding](https://cloud.google.com/iam/reference/rest/v1/Binding). For example, they might be formatted like the following: - user:foo@google.com - group:group1@google.com - serviceAccount:s1@prj1.iam.gserviceaccount.com - projectOwner:some_project_id - domain:google.com - allUsers */
		name: Option[String] = None,
	  /** The analysis state of this identity. */
		analysisState: Option[Schema.IamPolicyAnalysisState] = None
	)
	
	case class AnalyzeIamPolicyLongrunningRequest(
	  /** Required. The request query. */
		analysisQuery: Option[Schema.IamPolicyAnalysisQuery] = None,
	  /** Optional. The name of a saved query, which must be in the format of: &#42; projects/project_number/savedQueries/saved_query_id &#42; folders/folder_number/savedQueries/saved_query_id &#42; organizations/organization_number/savedQueries/saved_query_id If both `analysis_query` and `saved_analysis_query` are provided, they will be merged together with the `saved_analysis_query` as base and the `analysis_query` as overrides. For more details of the merge behavior, refer to the [MergeFrom](https://developers.google.com/protocol-buffers/docs/reference/cpp/google.protobuf.message#Message.MergeFrom.details) doc. Note that you cannot override primitive fields with default value, such as 0 or empty string, etc., because we use proto3, which doesn't support field presence yet. */
		savedAnalysisQuery: Option[String] = None,
	  /** Required. Output configuration indicating where the results will be output to. */
		outputConfig: Option[Schema.IamPolicyAnalysisOutputConfig] = None
	)
	
	case class IamPolicyAnalysisOutputConfig(
	  /** Destination on Cloud Storage. */
		gcsDestination: Option[Schema.GoogleCloudAssetV1GcsDestination] = None,
	  /** Destination on BigQuery. */
		bigqueryDestination: Option[Schema.GoogleCloudAssetV1BigQueryDestination] = None
	)
	
	case class GoogleCloudAssetV1GcsDestination(
	  /** Required. The URI of the Cloud Storage object. It's the same URI that is used by gsutil. Example: "gs://bucket_name/object_name". See [Viewing and Editing Object Metadata](https://cloud.google.com/storage/docs/viewing-editing-metadata) for more information. If the specified Cloud Storage object already exists and there is no [hold](https://cloud.google.com/storage/docs/object-holds), it will be overwritten with the analysis result. */
		uri: Option[String] = None
	)
	
	object GoogleCloudAssetV1BigQueryDestination {
		enum PartitionKeyEnum extends Enum[PartitionKeyEnum] { case PARTITION_KEY_UNSPECIFIED, REQUEST_TIME }
	}
	case class GoogleCloudAssetV1BigQueryDestination(
	  /** Required. The BigQuery dataset in format "projects/projectId/datasets/datasetId", to which the analysis results should be exported. If this dataset does not exist, the export call will return an INVALID_ARGUMENT error. */
		dataset: Option[String] = None,
	  /** Required. The prefix of the BigQuery tables to which the analysis results will be written. Tables will be created based on this table_prefix if not exist: &#42; _analysis table will contain export operation's metadata. &#42; _analysis_result will contain all the IamPolicyAnalysisResult. When [partition_key] is specified, both tables will be partitioned based on the [partition_key]. */
		tablePrefix: Option[String] = None,
	  /** The partition key for BigQuery partitioned table. */
		partitionKey: Option[Schema.GoogleCloudAssetV1BigQueryDestination.PartitionKeyEnum] = None,
	  /** Optional. Specifies the action that occurs if the destination table or partition already exists. The following values are supported: &#42; WRITE_TRUNCATE: If the table or partition already exists, BigQuery overwrites the entire table or all the partitions data. &#42; WRITE_APPEND: If the table or partition already exists, BigQuery appends the data to the table or the latest partition. &#42; WRITE_EMPTY: If the table already exists and contains data, an error is returned. The default value is WRITE_APPEND. Each action is atomic and only occurs if BigQuery is able to complete the job successfully. Details are at https://cloud.google.com/bigquery/docs/loading-data-local#appending_to_or_overwriting_a_table_using_a_local_file. */
		writeDisposition: Option[String] = None
	)
	
	case class AnalyzeMoveResponse(
	  /** The list of analyses returned from performing the intended resource move analysis. The analysis is grouped by different Google Cloud services. */
		moveAnalysis: Option[List[Schema.MoveAnalysis]] = None
	)
	
	case class MoveAnalysis(
	  /** The user friendly display name of the analysis. E.g. IAM, organization policy etc. */
		displayName: Option[String] = None,
	  /** Analysis result of moving the target resource. */
		analysis: Option[Schema.MoveAnalysisResult] = None,
	  /** Description of error encountered when performing the analysis. */
		error: Option[Schema.Status] = None
	)
	
	case class MoveAnalysisResult(
	  /** Blocking information that would prevent the target resource from moving to the specified destination at runtime. */
		blockers: Option[List[Schema.MoveImpact]] = None,
	  /** Warning information indicating that moving the target resource to the specified destination might be unsafe. This can include important policy information and configuration changes, but will not block moves at runtime. */
		warnings: Option[List[Schema.MoveImpact]] = None
	)
	
	case class MoveImpact(
	  /** User friendly impact detail in a free form message. */
		detail: Option[String] = None
	)
	
	case class QueryAssetsRequest(
	  /** Optional. A SQL statement that's compatible with [BigQuery SQL](https://cloud.google.com/bigquery/docs/introduction-sql). */
		statement: Option[String] = None,
	  /** Optional. Reference to the query job, which is from the `QueryAssetsResponse` of previous `QueryAssets` call. */
		jobReference: Option[String] = None,
	  /** Optional. The maximum number of rows to return in the results. Responses are limited to 10 MB and 1000 rows. By default, the maximum row count is 1000. When the byte or row count limit is reached, the rest of the query results will be paginated. The field will be ignored when [output_config] is specified. */
		pageSize: Option[Int] = None,
	  /** Optional. A page token received from previous `QueryAssets`. The field will be ignored when [output_config] is specified. */
		pageToken: Option[String] = None,
	  /** Optional. Specifies the maximum amount of time that the client is willing to wait for the query to complete. By default, this limit is 5 min for the first query, and 1 minute for the following queries. If the query is complete, the `done` field in the `QueryAssetsResponse` is true, otherwise false. Like BigQuery [jobs.query API](https://cloud.google.com/bigquery/docs/reference/rest/v2/jobs/query#queryrequest) The call is not guaranteed to wait for the specified timeout; it typically returns after around 200 seconds (200,000 milliseconds), even if the query is not complete. The field will be ignored when [output_config] is specified. */
		timeout: Option[String] = None,
	  /** Optional. [start_time] is required. [start_time] must be less than [end_time] Defaults [end_time] to now if [start_time] is set and [end_time] isn't. Maximum permitted time range is 7 days. */
		readTimeWindow: Option[Schema.TimeWindow] = None,
	  /** Optional. Queries cloud assets as they appeared at the specified point in time. */
		readTime: Option[String] = None,
	  /** Optional. Destination where the query results will be saved. When this field is specified, the query results won't be saved in the [QueryAssetsResponse.query_result]. Instead [QueryAssetsResponse.output_config] will be set. Meanwhile, [QueryAssetsResponse.job_reference] will be set and can be used to check the status of the query job when passed to a following [QueryAssets] API call. */
		outputConfig: Option[Schema.QueryAssetsOutputConfig] = None
	)
	
	case class QueryAssetsOutputConfig(
	  /** BigQuery destination where the query results will be saved. */
		bigqueryDestination: Option[Schema.GoogleCloudAssetV1QueryAssetsOutputConfigBigQueryDestination] = None
	)
	
	case class GoogleCloudAssetV1QueryAssetsOutputConfigBigQueryDestination(
	  /** Required. The BigQuery dataset where the query results will be saved. It has the format of "projects/{projectId}/datasets/{datasetId}". */
		dataset: Option[String] = None,
	  /** Required. The BigQuery table where the query results will be saved. If this table does not exist, a new table with the given name will be created. */
		table: Option[String] = None,
	  /** Specifies the action that occurs if the destination table or partition already exists. The following values are supported: &#42; WRITE_TRUNCATE: If the table or partition already exists, BigQuery overwrites the entire table or all the partitions data. &#42; WRITE_APPEND: If the table or partition already exists, BigQuery appends the data to the table or the latest partition. &#42; WRITE_EMPTY: If the table already exists and contains data, a 'duplicate' error is returned in the job result. The default value is WRITE_EMPTY. */
		writeDisposition: Option[String] = None
	)
	
	case class QueryAssetsResponse(
	  /** Reference to a query job. */
		jobReference: Option[String] = None,
	  /** The query response, which can be either an `error` or a valid `response`. If `done` == `false` and the query result is being saved in an output, the output_config field will be set. If `done` == `true`, exactly one of `error`, `query_result` or `output_config` will be set. [done] is unset unless the [QueryAssetsResponse] contains a [QueryAssetsResponse.job_reference]. */
		done: Option[Boolean] = None,
	  /** Error status. */
		error: Option[Schema.Status] = None,
	  /** Result of the query. */
		queryResult: Option[Schema.QueryResult] = None,
	  /** Output configuration, which indicates that instead of being returned in an API response on the fly, the query result will be saved in a specific output. */
		outputConfig: Option[Schema.QueryAssetsOutputConfig] = None
	)
	
	case class QueryResult(
	  /** Each row hold a query result in the format of `Struct`. */
		rows: Option[List[Map[String, JsValue]]] = None,
	  /** Describes the format of the [rows]. */
		schema: Option[Schema.TableSchema] = None,
	  /** Token to retrieve the next page of the results. */
		nextPageToken: Option[String] = None,
	  /** Total rows of the whole query results. */
		totalRows: Option[String] = None
	)
	
	case class TableSchema(
	  /** Describes the fields in a table. */
		fields: Option[List[Schema.TableFieldSchema]] = None
	)
	
	case class TableFieldSchema(
	  /** The field name. The name must contain only letters (a-z, A-Z), numbers (0-9), or underscores (_), and must start with a letter or underscore. The maximum length is 128 characters. */
		field: Option[String] = None,
	  /** The field data type. Possible values include &#42; STRING &#42; BYTES &#42; INTEGER &#42; FLOAT &#42; BOOLEAN &#42; TIMESTAMP &#42; DATE &#42; TIME &#42; DATETIME &#42; GEOGRAPHY, &#42; NUMERIC, &#42; BIGNUMERIC, &#42; RECORD (where RECORD indicates that the field contains a nested schema). */
		`type`: Option[String] = None,
	  /** The field mode. Possible values include NULLABLE, REQUIRED and REPEATED. The default value is NULLABLE. */
		mode: Option[String] = None,
	  /** Describes the nested schema fields if the type property is set to RECORD. */
		fields: Option[List[Schema.TableFieldSchema]] = None
	)
	
	case class SavedQuery(
	  /** The resource name of the saved query. The format must be: &#42; projects/project_number/savedQueries/saved_query_id &#42; folders/folder_number/savedQueries/saved_query_id &#42; organizations/organization_number/savedQueries/saved_query_id */
		name: Option[String] = None,
	  /** The description of this saved query. This value should be fewer than 255 characters. */
		description: Option[String] = None,
	  /** Output only. The create time of this saved query. */
		createTime: Option[String] = None,
	  /** Output only. The account's email address who has created this saved query. */
		creator: Option[String] = None,
	  /** Output only. The last update time of this saved query. */
		lastUpdateTime: Option[String] = None,
	  /** Output only. The account's email address who has updated this saved query most recently. */
		lastUpdater: Option[String] = None,
	  /** Labels applied on the resource. This value should not contain more than 10 entries. The key and value of each entry must be non-empty and fewer than 64 characters. */
		labels: Option[Map[String, String]] = None,
	  /** The query content. */
		content: Option[Schema.QueryContent] = None
	)
	
	case class QueryContent(
	  /** An IAM Policy Analysis query, which could be used in the AssetService.AnalyzeIamPolicy RPC or the AssetService.AnalyzeIamPolicyLongrunning RPC. */
		iamPolicyAnalysisQuery: Option[Schema.IamPolicyAnalysisQuery] = None
	)
	
	case class ListSavedQueriesResponse(
	  /** A list of savedQueries. */
		savedQueries: Option[List[Schema.SavedQuery]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class BatchGetEffectiveIamPoliciesResponse(
	  /** The effective policies for a batch of resources. Note that the results order is the same as the order of BatchGetEffectiveIamPoliciesRequest.names. When a resource does not have any effective IAM policies, its corresponding policy_result will contain empty EffectiveIamPolicy.policies. */
		policyResults: Option[List[Schema.EffectiveIamPolicy]] = None
	)
	
	case class EffectiveIamPolicy(
	  /** The [full_resource_name] (https://cloud.google.com/asset-inventory/docs/resource-name-format) for which the policies are computed. This is one of the BatchGetEffectiveIamPoliciesRequest.names the caller provides in the request. */
		fullResourceName: Option[String] = None,
	  /** The effective policies for the full_resource_name. These policies include the policy set on the full_resource_name and those set on its parents and ancestors up to the BatchGetEffectiveIamPoliciesRequest.scope. Note that these policies are not filtered according to the resource type of the full_resource_name. These policies are hierarchically ordered by PolicyInfo.attached_resource starting from full_resource_name itself to its parents and ancestors, such that policies[i]'s PolicyInfo.attached_resource is the child of policies[i+1]'s PolicyInfo.attached_resource, if policies[i+1] exists. */
		policies: Option[List[Schema.PolicyInfo]] = None
	)
	
	case class PolicyInfo(
	  /** The full resource name the policy is directly attached to. */
		attachedResource: Option[String] = None,
	  /** The IAM policy that's directly attached to the attached_resource. */
		policy: Option[Schema.Policy] = None
	)
	
	case class AnalyzeOrgPoliciesResponse(
	  /** The organization policies under the AnalyzeOrgPoliciesRequest.scope with the AnalyzeOrgPoliciesRequest.constraint. */
		orgPolicyResults: Option[List[Schema.OrgPolicyResult]] = None,
	  /** The definition of the constraint in the request. */
		constraint: Option[Schema.AnalyzerOrgPolicyConstraint] = None,
	  /** The page token to fetch the next page for AnalyzeOrgPoliciesResponse.org_policy_results. */
		nextPageToken: Option[String] = None
	)
	
	case class OrgPolicyResult(
	  /** The consolidated organization policy for the analyzed resource. The consolidated organization policy is computed by merging and evaluating policy_bundle. The evaluation will respect the organization policy [hierarchy rules](https://cloud.google.com/resource-manager/docs/organization-policy/understanding-hierarchy). */
		consolidatedPolicy: Option[Schema.AnalyzerOrgPolicy] = None,
	  /** The ordered list of all organization policies from the consolidated_policy.attached_resource. to the scope specified in the request. If the constraint is defined with default policy, it will also appear in the list. */
		policyBundle: Option[List[Schema.AnalyzerOrgPolicy]] = None,
	  /** The project that this consolidated policy belongs to, in the format of projects/{PROJECT_NUMBER}. This field is available when the consolidated policy belongs to a project. */
		project: Option[String] = None,
	  /** The folder(s) that this consolidated policy belongs to, in the format of folders/{FOLDER_NUMBER}. This field is available when the consolidated policy belongs (directly or cascadingly) to one or more folders. */
		folders: Option[List[String]] = None,
	  /** The organization that this consolidated policy belongs to, in the format of organizations/{ORGANIZATION_NUMBER}. This field is available when the consolidated policy belongs (directly or cascadingly) to an organization. */
		organization: Option[String] = None
	)
	
	case class AnalyzerOrgPolicy(
	  /** The [full resource name] (https://cloud.google.com/asset-inventory/docs/resource-name-format) of an organization/folder/project resource where this organization policy is set. Notice that some type of constraints are defined with default policy. This field will be empty for them. */
		attachedResource: Option[String] = None,
	  /** The [full resource name] (https://cloud.google.com/asset-inventory/docs/resource-name-format) of an organization/folder/project resource where this organization policy applies to. For any user defined org policies, this field has the same value as the [attached_resource] field. Only for default policy, this field has the different value. */
		appliedResource: Option[String] = None,
	  /** List of rules for this organization policy. */
		rules: Option[List[Schema.GoogleCloudAssetV1Rule]] = None,
	  /** If `inherit_from_parent` is true, Rules set higher up in the hierarchy (up to the closest root) are inherited and present in the effective policy. If it is false, then no rules are inherited, and this policy becomes the effective root for evaluation. */
		inheritFromParent: Option[Boolean] = None,
	  /** Ignores policies set above this resource and restores the default behavior of the constraint at this resource. This field can be set in policies for either list or boolean constraints. If set, `rules` must be empty and `inherit_from_parent` must be set to false. */
		reset: Option[Boolean] = None
	)
	
	case class GoogleCloudAssetV1Rule(
	  /** List of values to be used for this policy rule. This field can be set only in policies for list constraints. */
		values: Option[Schema.GoogleCloudAssetV1StringValues] = None,
	  /** Setting this to true means that all values are allowed. This field can be set only in Policies for list constraints. */
		allowAll: Option[Boolean] = None,
	  /** Setting this to true means that all values are denied. This field can be set only in Policies for list constraints. */
		denyAll: Option[Boolean] = None,
	  /** If `true`, then the `Policy` is enforced. If `false`, then any configuration is acceptable. This field can be set only in Policies for boolean constraints. */
		enforce: Option[Boolean] = None,
	  /** The evaluating condition for this rule. */
		condition: Option[Schema.Expr] = None,
	  /** The condition evaluation result for this rule. Only populated if it meets all the following criteria: &#42; There is a condition defined for this rule. &#42; This rule is within AnalyzeOrgPolicyGovernedContainersResponse.GovernedContainer.consolidated_policy, or AnalyzeOrgPolicyGovernedAssetsResponse.GovernedAsset.consolidated_policy when the AnalyzeOrgPolicyGovernedAssetsResponse.GovernedAsset has AnalyzeOrgPolicyGovernedAssetsResponse.GovernedAsset.governed_resource. */
		conditionEvaluation: Option[Schema.ConditionEvaluation] = None
	)
	
	case class GoogleCloudAssetV1StringValues(
	  /** List of values allowed at this resource. */
		allowedValues: Option[List[String]] = None,
	  /** List of values denied at this resource. */
		deniedValues: Option[List[String]] = None
	)
	
	case class AnalyzerOrgPolicyConstraint(
	  /** The definition of the canned constraint defined by Google. */
		googleDefinedConstraint: Option[Schema.GoogleCloudAssetV1Constraint] = None,
	  /** The definition of the custom constraint. */
		customConstraint: Option[Schema.GoogleCloudAssetV1CustomConstraint] = None
	)
	
	object GoogleCloudAssetV1Constraint {
		enum ConstraintDefaultEnum extends Enum[ConstraintDefaultEnum] { case CONSTRAINT_DEFAULT_UNSPECIFIED, ALLOW, DENY }
	}
	case class GoogleCloudAssetV1Constraint(
	  /** The unique name of the constraint. Format of the name should be &#42; `constraints/{constraint_name}` For example, `constraints/compute.disableSerialPortAccess`. */
		name: Option[String] = None,
	  /** The human readable name of the constraint. */
		displayName: Option[String] = None,
	  /** Detailed description of what this `Constraint` controls as well as how and where it is enforced. */
		description: Option[String] = None,
	  /** The evaluation behavior of this constraint in the absence of 'Policy'. */
		constraintDefault: Option[Schema.GoogleCloudAssetV1Constraint.ConstraintDefaultEnum] = None,
	  /** Defines this constraint as being a ListConstraint. */
		listConstraint: Option[Schema.GoogleCloudAssetV1ListConstraint] = None,
	  /** Defines this constraint as being a BooleanConstraint. */
		booleanConstraint: Option[Schema.GoogleCloudAssetV1BooleanConstraint] = None
	)
	
	case class GoogleCloudAssetV1ListConstraint(
	  /** Indicates whether values grouped into categories can be used in `Policy.allowed_values` and `Policy.denied_values`. For example, `"in:Python"` would match any value in the 'Python' group. */
		supportsIn: Option[Boolean] = None,
	  /** Indicates whether subtrees of Cloud Resource Manager resource hierarchy can be used in `Policy.allowed_values` and `Policy.denied_values`. For example, `"under:folders/123"` would match any resource under the 'folders/123' folder. */
		supportsUnder: Option[Boolean] = None
	)
	
	case class GoogleCloudAssetV1BooleanConstraint(
	
	)
	
	object GoogleCloudAssetV1CustomConstraint {
		enum MethodTypesEnum extends Enum[MethodTypesEnum] { case METHOD_TYPE_UNSPECIFIED, CREATE, UPDATE, DELETE, REMOVE_GRANT, GOVERN_TAGS }
		enum ActionTypeEnum extends Enum[ActionTypeEnum] { case ACTION_TYPE_UNSPECIFIED, ALLOW, DENY }
	}
	case class GoogleCloudAssetV1CustomConstraint(
	  /** Name of the constraint. This is unique within the organization. Format of the name should be &#42; `organizations/{organization_id}/customConstraints/{custom_constraint_id}` Example : "organizations/123/customConstraints/custom.createOnlyE2TypeVms" */
		name: Option[String] = None,
	  /** The Resource Instance type on which this policy applies to. Format will be of the form : "/" Example: &#42; `compute.googleapis.com/Instance`. */
		resourceTypes: Option[List[String]] = None,
	  /** All the operations being applied for this constraint. */
		methodTypes: Option[List[Schema.GoogleCloudAssetV1CustomConstraint.MethodTypesEnum]] = None,
	  /** Organization Policy condition/expression. For example: `resource.instanceName.matches("[production|test]_.&#42;_(\d)+")'` or, `resource.management.auto_upgrade == true` */
		condition: Option[String] = None,
	  /** Allow or deny type. */
		actionType: Option[Schema.GoogleCloudAssetV1CustomConstraint.ActionTypeEnum] = None,
	  /** One line display name for the UI. */
		displayName: Option[String] = None,
	  /** Detailed information about this custom policy constraint. */
		description: Option[String] = None
	)
	
	case class AnalyzeOrgPolicyGovernedContainersResponse(
	  /** The list of the analyzed governed containers. */
		governedContainers: Option[List[Schema.GoogleCloudAssetV1GovernedContainer]] = None,
	  /** The definition of the constraint in the request. */
		constraint: Option[Schema.AnalyzerOrgPolicyConstraint] = None,
	  /** The page token to fetch the next page for AnalyzeOrgPolicyGovernedContainersResponse.governed_containers. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudAssetV1GovernedContainer(
	  /** The [full resource name] (https://cloud.google.com/asset-inventory/docs/resource-name-format) of an organization/folder/project resource. */
		fullResourceName: Option[String] = None,
	  /** The [full resource name] (https://cloud.google.com/asset-inventory/docs/resource-name-format) of the parent of AnalyzeOrgPolicyGovernedContainersResponse.GovernedContainer.full_resource_name. */
		parent: Option[String] = None,
	  /** The consolidated organization policy for the analyzed resource. The consolidated organization policy is computed by merging and evaluating AnalyzeOrgPolicyGovernedContainersResponse.GovernedContainer.policy_bundle. The evaluation will respect the organization policy [hierarchy rules](https://cloud.google.com/resource-manager/docs/organization-policy/understanding-hierarchy). */
		consolidatedPolicy: Option[Schema.AnalyzerOrgPolicy] = None,
	  /** The ordered list of all organization policies from the consolidated_policy.attached_resource. to the scope specified in the request. If the constraint is defined with default policy, it will also appear in the list. */
		policyBundle: Option[List[Schema.AnalyzerOrgPolicy]] = None,
	  /** The project that this resource belongs to, in the format of projects/{PROJECT_NUMBER}. This field is available when the resource belongs to a project. */
		project: Option[String] = None,
	  /** The folder(s) that this resource belongs to, in the format of folders/{FOLDER_NUMBER}. This field is available when the resource belongs (directly or cascadingly) to one or more folders. */
		folders: Option[List[String]] = None,
	  /** The organization that this resource belongs to, in the format of organizations/{ORGANIZATION_NUMBER}. This field is available when the resource belongs (directly or cascadingly) to an organization. */
		organization: Option[String] = None,
	  /** The effective tags on this resource. */
		effectiveTags: Option[List[Schema.EffectiveTagDetails]] = None
	)
	
	case class AnalyzeOrgPolicyGovernedAssetsResponse(
	  /** The list of the analyzed governed assets. */
		governedAssets: Option[List[Schema.GoogleCloudAssetV1AnalyzeOrgPolicyGovernedAssetsResponseGovernedAsset]] = None,
	  /** The definition of the constraint in the request. */
		constraint: Option[Schema.AnalyzerOrgPolicyConstraint] = None,
	  /** The page token to fetch the next page for AnalyzeOrgPolicyGovernedAssetsResponse.governed_assets. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudAssetV1AnalyzeOrgPolicyGovernedAssetsResponseGovernedAsset(
	  /** A Google Cloud resource governed by the organization policies of the AnalyzeOrgPolicyGovernedAssetsRequest.constraint. */
		governedResource: Option[Schema.GoogleCloudAssetV1AnalyzeOrgPolicyGovernedAssetsResponseGovernedResource] = None,
	  /** An IAM policy governed by the organization policies of the AnalyzeOrgPolicyGovernedAssetsRequest.constraint. */
		governedIamPolicy: Option[Schema.GoogleCloudAssetV1AnalyzeOrgPolicyGovernedAssetsResponseGovernedIamPolicy] = None,
	  /** The consolidated policy for the analyzed asset. The consolidated policy is computed by merging and evaluating AnalyzeOrgPolicyGovernedAssetsResponse.GovernedAsset.policy_bundle. The evaluation will respect the organization policy [hierarchy rules](https://cloud.google.com/resource-manager/docs/organization-policy/understanding-hierarchy). */
		consolidatedPolicy: Option[Schema.AnalyzerOrgPolicy] = None,
	  /** The ordered list of all organization policies from the consolidated_policy.attached_resource to the scope specified in the request. If the constraint is defined with default policy, it will also appear in the list. */
		policyBundle: Option[List[Schema.AnalyzerOrgPolicy]] = None
	)
	
	case class GoogleCloudAssetV1AnalyzeOrgPolicyGovernedAssetsResponseGovernedResource(
	  /** The [full resource name] (https://cloud.google.com/asset-inventory/docs/resource-name-format) of the Google Cloud resource. */
		fullResourceName: Option[String] = None,
	  /** The [full resource name] (https://cloud.google.com/asset-inventory/docs/resource-name-format) of the parent of AnalyzeOrgPolicyGovernedAssetsResponse.GovernedResource.full_resource_name. */
		parent: Option[String] = None,
	  /** The project that this resource belongs to, in the format of projects/{PROJECT_NUMBER}. This field is available when the resource belongs to a project. */
		project: Option[String] = None,
	  /** The folder(s) that this resource belongs to, in the format of folders/{FOLDER_NUMBER}. This field is available when the resource belongs (directly or cascadingly) to one or more folders. */
		folders: Option[List[String]] = None,
	  /** The organization that this resource belongs to, in the format of organizations/{ORGANIZATION_NUMBER}. This field is available when the resource belongs (directly or cascadingly) to an organization. */
		organization: Option[String] = None,
	  /** The asset type of the AnalyzeOrgPolicyGovernedAssetsResponse.GovernedResource.full_resource_name Example: `cloudresourcemanager.googleapis.com/Project` See [Cloud Asset Inventory Supported Asset Types](https://cloud.google.com/asset-inventory/docs/supported-asset-types) for all supported asset types. */
		assetType: Option[String] = None,
	  /** The effective tags on this resource. */
		effectiveTags: Option[List[Schema.EffectiveTagDetails]] = None
	)
	
	case class GoogleCloudAssetV1AnalyzeOrgPolicyGovernedAssetsResponseGovernedIamPolicy(
	  /** The full resource name of the resource on which this IAM policy is set. Example: `//compute.googleapis.com/projects/my_project_123/zones/zone1/instances/instance1`. See [Cloud Asset Inventory Resource Name Format](https://cloud.google.com/asset-inventory/docs/resource-name-format) for more information. */
		attachedResource: Option[String] = None,
	  /** The IAM policy directly set on the given resource. */
		policy: Option[Schema.Policy] = None,
	  /** The project that this IAM policy belongs to, in the format of projects/{PROJECT_NUMBER}. This field is available when the IAM policy belongs to a project. */
		project: Option[String] = None,
	  /** The folder(s) that this IAM policy belongs to, in the format of folders/{FOLDER_NUMBER}. This field is available when the IAM policy belongs (directly or cascadingly) to one or more folders. */
		folders: Option[List[String]] = None,
	  /** The organization that this IAM policy belongs to, in the format of organizations/{ORGANIZATION_NUMBER}. This field is available when the IAM policy belongs (directly or cascadingly) to an organization. */
		organization: Option[String] = None,
	  /** The asset type of the AnalyzeOrgPolicyGovernedAssetsResponse.GovernedIamPolicy.attached_resource. Example: `cloudresourcemanager.googleapis.com/Project` See [Cloud Asset Inventory Supported Asset Types](https://cloud.google.com/asset-inventory/docs/supported-asset-types) for all supported asset types. */
		assetType: Option[String] = None
	)
	
	case class GoogleCloudAssetV1p7beta1Asset(
	  /** The last update timestamp of an asset. update_time is updated when create/update/delete operation is performed. */
		updateTime: Option[String] = None,
	  /** The full name of the asset. Example: `//compute.googleapis.com/projects/my_project_123/zones/zone1/instances/instance1` See [Resource names](https://cloud.google.com/apis/design/resource_names#full_resource_name) for more information. */
		name: Option[String] = None,
	  /** The type of the asset. Example: `compute.googleapis.com/Disk` See [Supported asset types](https://cloud.google.com/asset-inventory/docs/supported-asset-types) for more information. */
		assetType: Option[String] = None,
	  /** A representation of the resource. */
		resource: Option[Schema.GoogleCloudAssetV1p7beta1Resource] = None,
	  /** A representation of the IAM policy set on a Google Cloud resource. There can be a maximum of one IAM policy set on any given resource. In addition, IAM policies inherit their granted access scope from any policies set on parent resources in the resource hierarchy. Therefore, the effectively policy is the union of both the policy set on this resource and each policy set on all of the resource's ancestry resource levels in the hierarchy. See [this topic](https://cloud.google.com/iam/help/allow-policies/inheritance) for more information. */
		iamPolicy: Option[Schema.Policy] = None,
	  /** A representation of an [organization policy](https://cloud.google.com/resource-manager/docs/organization-policy/overview#organization_policy). There can be more than one organization policy with different constraints set on a given resource. */
		orgPolicy: Option[List[Schema.GoogleCloudOrgpolicyV1Policy]] = None,
	  /** Please also refer to the [access policy user guide](https://cloud.google.com/access-context-manager/docs/overview#access-policies). */
		accessPolicy: Option[Schema.GoogleIdentityAccesscontextmanagerV1AccessPolicy] = None,
	  /** Please also refer to the [access level user guide](https://cloud.google.com/access-context-manager/docs/overview#access-levels). */
		accessLevel: Option[Schema.GoogleIdentityAccesscontextmanagerV1AccessLevel] = None,
	  /** Please also refer to the [service perimeter user guide](https://cloud.google.com/vpc-service-controls/docs/overview). */
		servicePerimeter: Option[Schema.GoogleIdentityAccesscontextmanagerV1ServicePerimeter] = None,
	  /** The related assets of the asset of one relationship type. One asset only represents one type of relationship. */
		relatedAssets: Option[Schema.GoogleCloudAssetV1p7beta1RelatedAssets] = None,
	  /** The ancestry path of an asset in Google Cloud [resource hierarchy](https://cloud.google.com/resource-manager/docs/cloud-platform-resource-hierarchy), represented as a list of relative resource names. An ancestry path starts with the closest ancestor in the hierarchy and ends at root. If the asset is a project, folder, or organization, the ancestry path starts from the asset itself. Example: `["projects/123456789", "folders/5432", "organizations/1234"]` */
		ancestors: Option[List[String]] = None
	)
	
	case class GoogleCloudAssetV1p7beta1Resource(
	  /** The API version. Example: `v1` */
		version: Option[String] = None,
	  /** The URL of the discovery document containing the resource's JSON schema. Example: `https://www.googleapis.com/discovery/v1/apis/compute/v1/rest` This value is unspecified for resources that do not have an API based on a discovery document, such as Cloud Bigtable. */
		discoveryDocumentUri: Option[String] = None,
	  /** The JSON schema name listed in the discovery document. Example: `Project` This value is unspecified for resources that do not have an API based on a discovery document, such as Cloud Bigtable. */
		discoveryName: Option[String] = None,
	  /** The REST URL for accessing the resource. An HTTP `GET` request using this URL returns the resource itself. Example: `https://cloudresourcemanager.googleapis.com/v1/projects/my-project-123` This value is unspecified for resources without a REST API. */
		resourceUrl: Option[String] = None,
	  /** The full name of the immediate parent of this resource. See [Resource Names](https://cloud.google.com/apis/design/resource_names#full_resource_name) for more information. For Google Cloud assets, this value is the parent resource defined in the [IAM policy hierarchy](https://cloud.google.com/iam/docs/overview#policy_hierarchy). Example: `//cloudresourcemanager.googleapis.com/projects/my_project_123` For third-party assets, this field may be set differently. */
		parent: Option[String] = None,
	  /** The content of the resource, in which some sensitive fields are removed and may not be present. */
		data: Option[Map[String, JsValue]] = None,
	  /** The location of the resource in Google Cloud, such as its zone and region. For more information, see https://cloud.google.com/about/locations/. */
		location: Option[String] = None
	)
	
	case class GoogleCloudAssetV1p7beta1RelatedAssets(
	  /** The detailed relation attributes. */
		relationshipAttributes: Option[Schema.GoogleCloudAssetV1p7beta1RelationshipAttributes] = None,
	  /** The peer resources of the relationship. */
		assets: Option[List[Schema.GoogleCloudAssetV1p7beta1RelatedAsset]] = None
	)
	
	case class GoogleCloudAssetV1p7beta1RelationshipAttributes(
	  /** The unique identifier of the relationship type. Example: `INSTANCE_TO_INSTANCEGROUP` */
		`type`: Option[String] = None,
	  /** The source asset type. Example: `compute.googleapis.com/Instance` */
		sourceResourceType: Option[String] = None,
	  /** The target asset type. Example: `compute.googleapis.com/Disk` */
		targetResourceType: Option[String] = None,
	  /** The detail of the relationship, e.g. `contains`, `attaches` */
		action: Option[String] = None
	)
	
	case class GoogleCloudAssetV1p7beta1RelatedAsset(
	  /** The full name of the asset. Example: `//compute.googleapis.com/projects/my_project_123/zones/zone1/instances/instance1` See [Resource names](https://cloud.google.com/apis/design/resource_names#full_resource_name) for more information. */
		asset: Option[String] = None,
	  /** The type of the asset. Example: `compute.googleapis.com/Disk` See [Supported asset types](https://cloud.google.com/asset-inventory/docs/supported-asset-types) for more information. */
		assetType: Option[String] = None,
	  /** The ancestors of an asset in Google Cloud [resource hierarchy](https://cloud.google.com/resource-manager/docs/cloud-platform-resource-hierarchy), represented as a list of relative resource names. An ancestry path starts with the closest ancestor in the hierarchy and ends at root. Example: `["projects/123456789", "folders/5432", "organizations/1234"]` */
		ancestors: Option[List[String]] = None
	)
	
	case class AnalyzeIamPolicyLongrunningResponse(
	
	)
	
	case class AnalyzeIamPolicyLongrunningMetadata(
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None
	)
}
