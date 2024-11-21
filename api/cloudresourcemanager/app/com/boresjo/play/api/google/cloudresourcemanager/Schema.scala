package com.boresjo.play.api.google.cloudresourcemanager

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ListLiensResponse(
	  /** A list of Liens. */
		liens: Option[List[Schema.Lien]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class Lien(
	  /** A system-generated unique identifier for this Lien. Example: `liens/1234abcd` */
		name: Option[String] = None,
	  /** A reference to the resource this Lien is attached to. The server will validate the parent against those for which Liens are supported. Example: `projects/1234` */
		parent: Option[String] = None,
	  /** The types of operations which should be blocked as a result of this Lien. Each value should correspond to an IAM permission. The server will validate the permissions against those for which Liens are supported. An empty list is meaningless and will be rejected. Example: ['resourcemanager.projects.delete'] */
		restrictions: Option[List[String]] = None,
	  /** Concise user-visible strings indicating why an action cannot be performed on a resource. Maximum length of 200 characters. Example: 'Holds production API key' */
		reason: Option[String] = None,
	  /** A stable, user-visible/meaningful string identifying the origin of the Lien, intended to be inspected programmatically. Maximum length of 200 characters. Example: 'compute.googleapis.com' */
		origin: Option[String] = None,
	  /** The creation time of this Lien. */
		createTime: Option[String] = None
	)
	
	case class Empty(
	
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
	
	object Folder {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, DELETE_REQUESTED }
	}
	case class Folder(
	  /** Output only. The resource name of the folder. Its format is `folders/{folder_id}`, for example: "folders/1234". */
		name: Option[String] = None,
	  /** Required. The folder's parent's resource name. Updates to the folder's parent must be performed using MoveFolder. */
		parent: Option[String] = None,
	  /** The folder's display name. A folder's display name must be unique amongst its siblings. For example, no two folders with the same parent can share the same display name. The display name must start and end with a letter or digit, may contain letters, digits, spaces, hyphens and underscores and can be no longer than 30 characters. This is captured by the regular expression: `[\p{L}\p{N}]([\p{L}\p{N}_- ]{0,28}[\p{L}\p{N}])?`. */
		displayName: Option[String] = None,
	  /** Output only. The lifecycle state of the folder. Updates to the state must be performed using DeleteFolder and UndeleteFolder. */
		state: Option[Schema.Folder.StateEnum] = None,
	  /** Output only. Timestamp when the folder was created. */
		createTime: Option[String] = None,
	  /** Output only. Timestamp when the folder was last modified. */
		updateTime: Option[String] = None,
	  /** Output only. Timestamp when the folder was requested to be deleted. */
		deleteTime: Option[String] = None,
	  /** Output only. A checksum computed by the server based on the current value of the folder resource. This may be sent on update and delete requests to ensure the client has an up-to-date value before proceeding. */
		etag: Option[String] = None,
	  /** Optional. Input only. Immutable. Tag keys/values directly bound to this folder. Each item in the map must be expressed as " : ". For example: "123/environment" : "production", "123/costCenter" : "marketing" Note: Currently this field is in Preview. */
		tags: Option[Map[String, String]] = None
	)
	
	case class ListFoldersResponse(
	  /** A possibly paginated list of folders that are direct descendants of the specified parent resource. */
		folders: Option[List[Schema.Folder]] = None,
	  /** A pagination token returned from a previous call to `ListFolders` that indicates from where listing should continue. */
		nextPageToken: Option[String] = None
	)
	
	case class SearchFoldersResponse(
	  /** A possibly paginated folder search results. the specified parent resource. */
		folders: Option[List[Schema.Folder]] = None,
	  /** A pagination token returned from a previous call to `SearchFolders` that indicates from where searching should continue. */
		nextPageToken: Option[String] = None
	)
	
	case class MoveFolderRequest(
	  /** Required. The resource name of the folder or organization which should be the folder's new parent. Must be of the form `folders/{folder_id}` or `organizations/{org_id}`. */
		destinationParent: Option[String] = None
	)
	
	case class UndeleteFolderRequest(
	
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
	
	object Organization {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, DELETE_REQUESTED }
	}
	case class Organization(
	  /** Output only. The resource name of the organization. This is the organization's relative path in the API. Its format is "organizations/[organization_id]". For example, "organizations/1234". */
		name: Option[String] = None,
	  /** Output only. A human-readable string that refers to the organization in the Google Cloud Console. This string is set by the server and cannot be changed. The string will be set to the primary domain (for example, "google.com") of the Google Workspace customer that owns the organization. */
		displayName: Option[String] = None,
	  /** Immutable. The G Suite / Workspace customer id used in the Directory API. */
		directoryCustomerId: Option[String] = None,
	  /** Output only. The organization's current lifecycle state. */
		state: Option[Schema.Organization.StateEnum] = None,
	  /** Output only. Timestamp when the Organization was created. */
		createTime: Option[String] = None,
	  /** Output only. Timestamp when the Organization was last modified. */
		updateTime: Option[String] = None,
	  /** Output only. Timestamp when the Organization was requested for deletion. */
		deleteTime: Option[String] = None,
	  /** Output only. A checksum computed by the server based on the current value of the Organization resource. This may be sent on update and delete requests to ensure the client has an up-to-date value before proceeding. */
		etag: Option[String] = None
	)
	
	case class SearchOrganizationsResponse(
	  /** The list of Organizations that matched the search query, possibly paginated. */
		organizations: Option[List[Schema.Organization]] = None,
	  /** A pagination token to be used to retrieve the next page of results. If the result is too large to fit within the page size specified in the request, this field will be set with a token that can be used to fetch the next page of results. If this field is empty, it indicates that this response contains the last page of results. */
		nextPageToken: Option[String] = None
	)
	
	object Project {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, DELETE_REQUESTED }
	}
	case class Project(
	  /** Output only. The unique resource name of the project. It is an int64 generated number prefixed by "projects/". Example: `projects/415104041262` */
		name: Option[String] = None,
	  /** Optional. A reference to a parent Resource. eg., `organizations/123` or `folders/876`. */
		parent: Option[String] = None,
	  /** Immutable. The unique, user-assigned id of the project. It must be 6 to 30 lowercase ASCII letters, digits, or hyphens. It must start with a letter. Trailing hyphens are prohibited. Example: `tokyo-rain-123` */
		projectId: Option[String] = None,
	  /** Output only. The project lifecycle state. */
		state: Option[Schema.Project.StateEnum] = None,
	  /** Optional. A user-assigned display name of the project. When present it must be between 4 to 30 characters. Allowed characters are: lowercase and uppercase letters, numbers, hyphen, single-quote, double-quote, space, and exclamation point. Example: `My Project` */
		displayName: Option[String] = None,
	  /** Output only. Creation time. */
		createTime: Option[String] = None,
	  /** Output only. The most recent time this resource was modified. */
		updateTime: Option[String] = None,
	  /** Output only. The time at which this resource was requested for deletion. */
		deleteTime: Option[String] = None,
	  /** Output only. A checksum computed by the server based on the current value of the Project resource. This may be sent on update and delete requests to ensure the client has an up-to-date value before proceeding. */
		etag: Option[String] = None,
	  /** Optional. The labels associated with this project. Label keys must be between 1 and 63 characters long and must conform to the following regular expression: \[a-z\](\[-a-z0-9\]&#42;\[a-z0-9\])?. Label values must be between 0 and 63 characters long and must conform to the regular expression (\[a-z\](\[-a-z0-9\]&#42;\[a-z0-9\])?)?. No more than 64 labels can be associated with a given resource. Clients should store labels in a representation such as JSON that does not depend on specific characters being disallowed. Example: `"myBusinessDimension" : "businessValue"` */
		labels: Option[Map[String, String]] = None,
	  /** Optional. Input only. Immutable. Tag keys/values directly bound to this project. Each item in the map must be expressed as " : ". For example: "123/environment" : "production", "123/costCenter" : "marketing" Note: Currently this field is in Preview. */
		tags: Option[Map[String, String]] = None
	)
	
	case class ListProjectsResponse(
	  /** The list of Projects under the parent. This list can be paginated. */
		projects: Option[List[Schema.Project]] = None,
	  /** Pagination token. If the result set is too large to fit in a single response, this token is returned. It encodes the position of the current result cursor. Feeding this value into a new list request with the `page_token` parameter gives the next page of the results. When `next_page_token` is not filled in, there is no next page and the list returned is the last page in the result set. Pagination tokens have a limited lifetime. */
		nextPageToken: Option[String] = None
	)
	
	case class SearchProjectsResponse(
	  /** The list of Projects that matched the list filter query. This list can be paginated. */
		projects: Option[List[Schema.Project]] = None,
	  /** Pagination token. If the result set is too large to fit in a single response, this token is returned. It encodes the position of the current result cursor. Feeding this value into a new list request with the `page_token` parameter gives the next page of the results. When `next_page_token` is not filled in, there is no next page and the list returned is the last page in the result set. Pagination tokens have a limited lifetime. */
		nextPageToken: Option[String] = None
	)
	
	case class MoveProjectRequest(
	  /** Required. The new parent to move the Project under. */
		destinationParent: Option[String] = None
	)
	
	case class UndeleteProjectRequest(
	
	)
	
	case class ListTagBindingsResponse(
	  /** A possibly paginated list of TagBindings for the specified resource. */
		tagBindings: Option[List[Schema.TagBinding]] = None,
	  /** Pagination token. If the result set is too large to fit in a single response, this token is returned. It encodes the position of the current result cursor. Feeding this value into a new list request with the `page_token` parameter gives the next page of the results. When `next_page_token` is not filled in, there is no next page and the list returned is the last page in the result set. Pagination tokens have a limited lifetime. */
		nextPageToken: Option[String] = None
	)
	
	case class TagBinding(
	  /** Output only. The name of the TagBinding. This is a String of the form: `tagBindings/{full-resource-name}/{tag-value-name}` (e.g. `tagBindings/%2F%2Fcloudresourcemanager.googleapis.com%2Fprojects%2F123/tagValues/456`). */
		name: Option[String] = None,
	  /** The full resource name of the resource the TagValue is bound to. E.g. `//cloudresourcemanager.googleapis.com/projects/123` */
		parent: Option[String] = None,
	  /** The TagValue of the TagBinding. Must be of the form `tagValues/456`. */
		tagValue: Option[String] = None,
	  /** The namespaced name for the TagValue of the TagBinding. Must be in the format `{parent_id}/{tag_key_short_name}/{short_name}`. For methods that support TagValue namespaced name, only one of tag_value_namespaced_name or tag_value may be filled. Requests with both fields will be rejected. */
		tagValueNamespacedName: Option[String] = None
	)
	
	case class ListEffectiveTagsResponse(
	  /** A possibly paginated list of effective tags for the specified resource. */
		effectiveTags: Option[List[Schema.EffectiveTag]] = None,
	  /** Pagination token. If the result set is too large to fit in a single response, this token is returned. It encodes the position of the current result cursor. Feeding this value into a new list request with the `page_token` parameter gives the next page of the results. When `next_page_token` is not filled in, there is no next page and the list returned is the last page in the result set. Pagination tokens have a limited lifetime. */
		nextPageToken: Option[String] = None
	)
	
	case class EffectiveTag(
	  /** Resource name for TagValue in the format `tagValues/456`. */
		tagValue: Option[String] = None,
	  /** The namespaced name of the TagValue. Can be in the form `{organization_id}/{tag_key_short_name}/{tag_value_short_name}` or `{project_id}/{tag_key_short_name}/{tag_value_short_name}` or `{project_number}/{tag_key_short_name}/{tag_value_short_name}`. */
		namespacedTagValue: Option[String] = None,
	  /** The name of the TagKey, in the format `tagKeys/{id}`, such as `tagKeys/123`. */
		tagKey: Option[String] = None,
	  /** The namespaced name of the TagKey. Can be in the form `{organization_id}/{tag_key_short_name}` or `{project_id}/{tag_key_short_name}` or `{project_number}/{tag_key_short_name}`. */
		namespacedTagKey: Option[String] = None,
	  /** The parent name of the tag key. Must be in the format `organizations/{organization_id}` or `projects/{project_number}` */
		tagKeyParentName: Option[String] = None,
	  /** Indicates the inheritance status of a tag value attached to the given resource. If the tag value is inherited from one of the resource's ancestors, inherited will be true. If false, then the tag value is directly attached to the resource, inherited will be false. */
		inherited: Option[Boolean] = None
	)
	
	case class TagHold(
	  /** Output only. The resource name of a TagHold. This is a String of the form: `tagValues/{tag-value-id}/tagHolds/{tag-hold-id}` (e.g. `tagValues/123/tagHolds/456`). This resource name is generated by the server. */
		name: Option[String] = None,
	  /** Required. The name of the resource where the TagValue is being used. Must be less than 200 characters. E.g. `//compute.googleapis.com/compute/projects/myproject/regions/us-east-1/instanceGroupManagers/instance-group` */
		holder: Option[String] = None,
	  /** Optional. An optional string representing the origin of this request. This field should include human-understandable information to distinguish origins from each other. Must be less than 200 characters. E.g. `migs-35678234` */
		origin: Option[String] = None,
	  /** Optional. A URL where an end user can learn more about removing this hold. E.g. `https://cloud.google.com/resource-manager/docs/tags/tags-creating-and-managing` */
		helpLink: Option[String] = None,
	  /** Output only. The time this TagHold was created. */
		createTime: Option[String] = None
	)
	
	case class ListTagHoldsResponse(
	  /** A possibly paginated list of TagHolds. */
		tagHolds: Option[List[Schema.TagHold]] = None,
	  /** Pagination token. If the result set is too large to fit in a single response, this token is returned. It encodes the position of the current result cursor. Feeding this value into a new list request with the `page_token` parameter gives the next page of the results. When `next_page_token` is not filled in, there is no next page and the list returned is the last page in the result set. Pagination tokens have a limited lifetime. */
		nextPageToken: Option[String] = None
	)
	
	case class ListTagKeysResponse(
	  /** List of TagKeys that live under the specified parent in the request. */
		tagKeys: Option[List[Schema.TagKey]] = None,
	  /** A pagination token returned from a previous call to `ListTagKeys` that indicates from where listing should continue. */
		nextPageToken: Option[String] = None
	)
	
	object TagKey {
		enum PurposeEnum extends Enum[PurposeEnum] { case PURPOSE_UNSPECIFIED, GCE_FIREWALL, DATA_GOVERNANCE }
	}
	case class TagKey(
	  /** Immutable. The resource name for a TagKey. Must be in the format `tagKeys/{tag_key_id}`, where `tag_key_id` is the generated numeric id for the TagKey. */
		name: Option[String] = None,
	  /** Immutable. The resource name of the TagKey's parent. A TagKey can be parented by an Organization or a Project. For a TagKey parented by an Organization, its parent must be in the form `organizations/{org_id}`. For a TagKey parented by a Project, its parent can be in the form `projects/{project_id}` or `projects/{project_number}`. */
		parent: Option[String] = None,
	  /** Required. Immutable. The user friendly name for a TagKey. The short name should be unique for TagKeys within the same tag namespace. The short name must be 1-63 characters, beginning and ending with an alphanumeric character ([a-z0-9A-Z]) with dashes (-), underscores (_), dots (.), and alphanumerics between. */
		shortName: Option[String] = None,
	  /** Output only. Immutable. Namespaced name of the TagKey. */
		namespacedName: Option[String] = None,
	  /** Optional. User-assigned description of the TagKey. Must not exceed 256 characters. Read-write. */
		description: Option[String] = None,
	  /** Output only. Creation time. */
		createTime: Option[String] = None,
	  /** Output only. Update time. */
		updateTime: Option[String] = None,
	  /** Optional. Entity tag which users can pass to prevent race conditions. This field is always set in server responses. See UpdateTagKeyRequest for details. */
		etag: Option[String] = None,
	  /** Optional. A purpose denotes that this Tag is intended for use in policies of a specific policy engine, and will involve that policy engine in management operations involving this Tag. A purpose does not grant a policy engine exclusive rights to the Tag, and it may be referenced by other policy engines. A purpose cannot be changed once set. */
		purpose: Option[Schema.TagKey.PurposeEnum] = None,
	  /** Optional. Purpose data corresponds to the policy system that the tag is intended for. See documentation for `Purpose` for formatting of this field. Purpose data cannot be changed once set. */
		purposeData: Option[Map[String, String]] = None
	)
	
	case class ListTagValuesResponse(
	  /** A possibly paginated list of TagValues that are direct descendants of the specified parent TagKey. */
		tagValues: Option[List[Schema.TagValue]] = None,
	  /** A pagination token returned from a previous call to `ListTagValues` that indicates from where listing should continue. This is currently not used, but the server may at any point start supplying a valid token. */
		nextPageToken: Option[String] = None
	)
	
	case class TagValue(
	  /** Immutable. Resource name for TagValue in the format `tagValues/456`. */
		name: Option[String] = None,
	  /** Immutable. The resource name of the new TagValue's parent TagKey. Must be of the form `tagKeys/{tag_key_id}`. */
		parent: Option[String] = None,
	  /** Required. Immutable. User-assigned short name for TagValue. The short name should be unique for TagValues within the same parent TagKey. The short name must be 63 characters or less, beginning and ending with an alphanumeric character ([a-z0-9A-Z]) with dashes (-), underscores (_), dots (.), and alphanumerics between. */
		shortName: Option[String] = None,
	  /** Output only. The namespaced name of the TagValue. Can be in the form `{organization_id}/{tag_key_short_name}/{tag_value_short_name}` or `{project_id}/{tag_key_short_name}/{tag_value_short_name}` or `{project_number}/{tag_key_short_name}/{tag_value_short_name}`. */
		namespacedName: Option[String] = None,
	  /** Optional. User-assigned description of the TagValue. Must not exceed 256 characters. Read-write. */
		description: Option[String] = None,
	  /** Output only. Creation time. */
		createTime: Option[String] = None,
	  /** Output only. Update time. */
		updateTime: Option[String] = None,
	  /** Optional. Entity tag which users can pass to prevent race conditions. This field is always set in server responses. See UpdateTagValueRequest for details. */
		etag: Option[String] = None
	)
	
	case class ProjectCreationStatus(
	  /** Creation time of the project creation workflow. */
		createTime: Option[String] = None,
	  /** True if the project can be retrieved using GetProject. No other operations on the project are guaranteed to work until the project creation is complete. */
		gettable: Option[Boolean] = None,
	  /** True if the project creation process is complete. */
		ready: Option[Boolean] = None
	)
	
	object CloudresourcemanagerGoogleCloudResourcemanagerV2alpha1FolderOperation {
		enum OperationTypeEnum extends Enum[OperationTypeEnum] { case OPERATION_TYPE_UNSPECIFIED, CREATE, MOVE }
	}
	case class CloudresourcemanagerGoogleCloudResourcemanagerV2alpha1FolderOperation(
	  /** The display name of the folder. */
		displayName: Option[String] = None,
	  /** The type of this operation. */
		operationType: Option[Schema.CloudresourcemanagerGoogleCloudResourcemanagerV2alpha1FolderOperation.OperationTypeEnum] = None,
	  /** The resource name of the folder's parent. Only applicable when the operation_type is MOVE. */
		sourceParent: Option[String] = None,
	  /** The resource name of the folder or organization we are either creating the folder under or moving the folder to. */
		destinationParent: Option[String] = None
	)
	
	object FolderOperationError {
		enum ErrorMessageIdEnum extends Enum[ErrorMessageIdEnum] { case ERROR_TYPE_UNSPECIFIED, ACTIVE_FOLDER_HEIGHT_VIOLATION, MAX_CHILD_FOLDERS_VIOLATION, FOLDER_NAME_UNIQUENESS_VIOLATION, RESOURCE_DELETED_VIOLATION, PARENT_DELETED_VIOLATION, CYCLE_INTRODUCED_VIOLATION, FOLDER_BEING_MOVED_VIOLATION, FOLDER_TO_DELETE_NON_EMPTY_VIOLATION, DELETED_FOLDER_HEIGHT_VIOLATION }
	}
	case class FolderOperationError(
	  /** The type of operation error experienced. */
		errorMessageId: Option[Schema.FolderOperationError.ErrorMessageIdEnum] = None
	)
	
	object CloudresourcemanagerGoogleCloudResourcemanagerV2beta1FolderOperation {
		enum OperationTypeEnum extends Enum[OperationTypeEnum] { case OPERATION_TYPE_UNSPECIFIED, CREATE, MOVE }
	}
	case class CloudresourcemanagerGoogleCloudResourcemanagerV2beta1FolderOperation(
	  /** The display name of the folder. */
		displayName: Option[String] = None,
	  /** The type of this operation. */
		operationType: Option[Schema.CloudresourcemanagerGoogleCloudResourcemanagerV2beta1FolderOperation.OperationTypeEnum] = None,
	  /** The resource name of the folder's parent. Only applicable when the operation_type is MOVE. */
		sourceParent: Option[String] = None,
	  /** The resource name of the folder or organization we are either creating the folder under or moving the folder to. */
		destinationParent: Option[String] = None
	)
	
	object FolderOperation {
		enum OperationTypeEnum extends Enum[OperationTypeEnum] { case OPERATION_TYPE_UNSPECIFIED, CREATE, MOVE }
	}
	case class FolderOperation(
	  /** The display name of the folder. */
		displayName: Option[String] = None,
	  /** The type of this operation. */
		operationType: Option[Schema.FolderOperation.OperationTypeEnum] = None,
	  /** The resource name of the folder's parent. Only applicable when the operation_type is MOVE. */
		sourceParent: Option[String] = None,
	  /** The resource name of the folder or organization we are either creating the folder under or moving the folder to. */
		destinationParent: Option[String] = None
	)
	
	case class DeleteOrganizationMetadata(
	
	)
	
	case class UndeleteOrganizationMetadata(
	
	)
	
	case class CreateFolderMetadata(
	  /** The display name of the folder. */
		displayName: Option[String] = None,
	  /** The resource name of the folder or organization we are creating the folder under. */
		parent: Option[String] = None
	)
	
	case class UpdateFolderMetadata(
	
	)
	
	case class MoveFolderMetadata(
	  /** The display name of the folder. */
		displayName: Option[String] = None,
	  /** The resource name of the folder's parent. */
		sourceParent: Option[String] = None,
	  /** The resource name of the folder or organization to move the folder to. */
		destinationParent: Option[String] = None
	)
	
	case class DeleteFolderMetadata(
	
	)
	
	case class UndeleteFolderMetadata(
	
	)
	
	case class CreateProjectMetadata(
	  /** Creation time of the project creation workflow. */
		createTime: Option[String] = None,
	  /** True if the project can be retrieved using `GetProject`. No other operations on the project are guaranteed to work until the project creation is complete. */
		gettable: Option[Boolean] = None,
	  /** True if the project creation process is complete. */
		ready: Option[Boolean] = None
	)
	
	case class UpdateProjectMetadata(
	
	)
	
	case class MoveProjectMetadata(
	
	)
	
	case class DeleteProjectMetadata(
	
	)
	
	case class UndeleteProjectMetadata(
	
	)
	
	case class CreateTagKeyMetadata(
	
	)
	
	case class UpdateTagKeyMetadata(
	
	)
	
	case class DeleteTagKeyMetadata(
	
	)
	
	case class CreateTagValueMetadata(
	
	)
	
	case class UpdateTagValueMetadata(
	
	)
	
	case class DeleteTagValueMetadata(
	
	)
	
	case class CreateTagBindingMetadata(
	
	)
	
	case class DeleteTagBindingMetadata(
	
	)
}
