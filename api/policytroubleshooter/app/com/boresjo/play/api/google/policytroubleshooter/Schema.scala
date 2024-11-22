package com.boresjo.play.api.google.policytroubleshooter

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object GoogleCloudPolicytroubleshooterV1ExplainedPolicy {
		enum AccessEnum extends Enum[AccessEnum] { case ACCESS_STATE_UNSPECIFIED, GRANTED, NOT_GRANTED, UNKNOWN_CONDITIONAL, UNKNOWN_INFO_DENIED }
		enum RelevanceEnum extends Enum[RelevanceEnum] { case HEURISTIC_RELEVANCE_UNSPECIFIED, NORMAL, HIGH }
	}
	case class GoogleCloudPolicytroubleshooterV1ExplainedPolicy(
	  /** Indicates whether _this policy_ provides the specified permission to the specified principal for the specified resource. This field does _not_ indicate whether the principal actually has the permission for the resource. There might be another policy that overrides this policy. To determine whether the principal actually has the permission, use the `access` field in the TroubleshootIamPolicyResponse. */
		access: Option[Schema.GoogleCloudPolicytroubleshooterV1ExplainedPolicy.AccessEnum] = None,
	  /** The relevance of this policy to the overall determination in the TroubleshootIamPolicyResponse. If the sender of the request does not have access to the policy, this field is omitted. */
		relevance: Option[Schema.GoogleCloudPolicytroubleshooterV1ExplainedPolicy.RelevanceEnum] = None,
	  /** Details about how each binding in the policy affects the principal's ability, or inability, to use the permission for the resource. If the sender of the request does not have access to the policy, this field is omitted. */
		bindingExplanations: Option[List[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanation]] = None,
	  /** The full resource name that identifies the resource. For example, `//compute.googleapis.com/projects/my-project/zones/us-central1-a/instances/my-instance`. If the sender of the request does not have access to the policy, this field is omitted. For examples of full resource names for Google Cloud services, see https://cloud.google.com/iam/help/troubleshooter/full-resource-names. */
		fullResourceName: Option[String] = None,
	  /** The IAM policy attached to the resource. If the sender of the request does not have access to the policy, this field is empty. */
		policy: Option[Schema.GoogleIamV1Policy] = None
	)
	
	object GoogleCloudPolicytroubleshooterV1BindingExplanation {
		enum RolePermissionEnum extends Enum[RolePermissionEnum] { case ROLE_PERMISSION_UNSPECIFIED, ROLE_PERMISSION_INCLUDED, ROLE_PERMISSION_NOT_INCLUDED, ROLE_PERMISSION_UNKNOWN_INFO_DENIED }
		enum RelevanceEnum extends Enum[RelevanceEnum] { case HEURISTIC_RELEVANCE_UNSPECIFIED, NORMAL, HIGH }
		enum RolePermissionRelevanceEnum extends Enum[RolePermissionRelevanceEnum] { case HEURISTIC_RELEVANCE_UNSPECIFIED, NORMAL, HIGH }
		enum AccessEnum extends Enum[AccessEnum] { case ACCESS_STATE_UNSPECIFIED, GRANTED, NOT_GRANTED, UNKNOWN_CONDITIONAL, UNKNOWN_INFO_DENIED }
	}
	case class GoogleCloudPolicytroubleshooterV1BindingExplanation(
	  /** Indicates whether the role granted by this binding contains the specified permission. */
		rolePermission: Option[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanation.RolePermissionEnum] = None,
	  /** A condition expression that prevents this binding from granting access unless the expression evaluates to `true`. To learn about IAM Conditions, see https://cloud.google.com/iam/help/conditions/overview. */
		condition: Option[Schema.GoogleTypeExpr] = None,
	  /** Indicates whether each principal in the binding includes the principal specified in the request, either directly or indirectly. Each key identifies a principal in the binding, and each value indicates whether the principal in the binding includes the principal in the request. For example, suppose that a binding includes the following principals: &#42; `user:alice@example.com` &#42; `group:product-eng@example.com` You want to troubleshoot access for `user:bob@example.com`. This user is a principal of the group `group:product-eng@example.com`. For the first principal in the binding, the key is `user:alice@example.com`, and the `membership` field in the value is set to `MEMBERSHIP_NOT_INCLUDED`. For the second principal in the binding, the key is `group:product-eng@example.com`, and the `membership` field in the value is set to `MEMBERSHIP_INCLUDED`. */
		memberships: Option[Map[String, Schema.GoogleCloudPolicytroubleshooterV1BindingExplanationAnnotatedMembership]] = None,
	  /** The role that this binding grants. For example, `roles/compute.serviceAgent`. For a complete list of predefined IAM roles, as well as the permissions in each role, see https://cloud.google.com/iam/help/roles/reference. */
		role: Option[String] = None,
	  /** The relevance of this binding to the overall determination for the entire policy. */
		relevance: Option[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanation.RelevanceEnum] = None,
	  /** The relevance of the permission's existence, or nonexistence, in the role to the overall determination for the entire policy. */
		rolePermissionRelevance: Option[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanation.RolePermissionRelevanceEnum] = None,
	  /** Required. Indicates whether _this binding_ provides the specified permission to the specified principal for the specified resource. This field does _not_ indicate whether the principal actually has the permission for the resource. There might be another binding that overrides this binding. To determine whether the principal actually has the permission, use the `access` field in the TroubleshootIamPolicyResponse. */
		access: Option[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanation.AccessEnum] = None
	)
	
	case class GoogleCloudPolicytroubleshooterV1AccessTuple(
	  /** Required. The IAM permission to check for the specified principal and resource. For a complete list of IAM permissions, see https://cloud.google.com/iam/help/permissions/reference. For a complete list of predefined IAM roles and the permissions in each role, see https://cloud.google.com/iam/help/roles/reference. */
		permission: Option[String] = None,
	  /** Required. The full resource name that identifies the resource. For example, `//compute.googleapis.com/projects/my-project/zones/us-central1-a/instances/my-instance`. For examples of full resource names for Google Cloud services, see https://cloud.google.com/iam/help/troubleshooter/full-resource-names. */
		fullResourceName: Option[String] = None,
	  /** Required. The principal whose access you want to check, in the form of the email address that represents that principal. For example, `alice@example.com` or `my-service-account@my-project.iam.gserviceaccount.com`. The principal must be a Google Account or a service account. Other types of principals are not supported. */
		principal: Option[String] = None
	)
	
	object GoogleCloudPolicytroubleshooterV1TroubleshootIamPolicyResponse {
		enum AccessEnum extends Enum[AccessEnum] { case ACCESS_STATE_UNSPECIFIED, GRANTED, NOT_GRANTED, UNKNOWN_CONDITIONAL, UNKNOWN_INFO_DENIED }
	}
	case class GoogleCloudPolicytroubleshooterV1TroubleshootIamPolicyResponse(
	  /** The general errors contained in the troubleshooting response. */
		errors: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Indicates whether the principal has the specified permission for the specified resource, based on evaluating all of the applicable IAM policies. */
		access: Option[Schema.GoogleCloudPolicytroubleshooterV1TroubleshootIamPolicyResponse.AccessEnum] = None,
	  /** List of IAM policies that were evaluated to check the principal's permissions, with annotations to indicate how each policy contributed to the final result. The list of policies can include the policy for the resource itself. It can also include policies that are inherited from higher levels of the resource hierarchy, including the organization, the folder, and the project. To learn more about the resource hierarchy, see https://cloud.google.com/iam/help/resource-hierarchy. */
		explainedPolicies: Option[List[Schema.GoogleCloudPolicytroubleshooterV1ExplainedPolicy]] = None
	)
	
	case class GoogleIamV1Policy(
	  /** `etag` is used for optimistic concurrency control as a way to help prevent simultaneous updates of a policy from overwriting each other. It is strongly suggested that systems make use of the `etag` in the read-modify-write cycle to perform policy updates in order to avoid race conditions: An `etag` is returned in the response to `getIamPolicy`, and systems are expected to put that etag in the request to `setIamPolicy` to ensure that their change will be applied to the same version of the policy. &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. */
		etag: Option[String] = None,
	  /** Associates a list of `members`, or principals, with a `role`. Optionally, may specify a `condition` that determines how and when the `bindings` are applied. Each of the `bindings` must contain at least one principal. The `bindings` in a `Policy` can refer to up to 1,500 principals; up to 250 of these principals can be Google groups. Each occurrence of a principal counts towards these limits. For example, if the `bindings` grant 50 different roles to `user:alice@example.com`, and not to any other principal, then you can add another 1,450 principals to the `bindings` in the `Policy`. */
		bindings: Option[List[Schema.GoogleIamV1Binding]] = None,
	  /** Specifies cloud audit logging configuration for this policy. */
		auditConfigs: Option[List[Schema.GoogleIamV1AuditConfig]] = None,
	  /** Specifies the format of the policy. Valid values are `0`, `1`, and `3`. Requests that specify an invalid value are rejected. Any operation that affects conditional role bindings must specify version `3`. This requirement applies to the following operations: &#42; Getting a policy that includes a conditional role binding &#42; Adding a conditional role binding to a policy &#42; Changing a conditional role binding in a policy &#42; Removing any role binding, with or without a condition, from a policy that includes conditions &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. If a policy does not include any conditions, operations on that policy may specify any valid version or leave the field unset. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		version: Option[Int] = None
	)
	
	object GoogleIamV1AuditLogConfig {
		enum LogTypeEnum extends Enum[LogTypeEnum] { case LOG_TYPE_UNSPECIFIED, ADMIN_READ, DATA_WRITE, DATA_READ }
	}
	case class GoogleIamV1AuditLogConfig(
	  /** Specifies the identities that do not cause logging for this type of permission. Follows the same format of Binding.members. */
		exemptedMembers: Option[List[String]] = None,
	  /** The log type that this config enables. */
		logType: Option[Schema.GoogleIamV1AuditLogConfig.LogTypeEnum] = None
	)
	
	case class GoogleRpcStatus(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None
	)
	
	case class GoogleIamV1Binding(
	  /** Role that is assigned to the list of `members`, or principals. For example, `roles/viewer`, `roles/editor`, or `roles/owner`. For an overview of the IAM roles and permissions, see the [IAM documentation](https://cloud.google.com/iam/docs/roles-overview). For a list of the available pre-defined roles, see [here](https://cloud.google.com/iam/docs/understanding-roles). */
		role: Option[String] = None,
	  /** Specifies the principals requesting access for a Google Cloud resource. `members` can have the following values: &#42; `allUsers`: A special identifier that represents anyone who is on the internet; with or without a Google account. &#42; `allAuthenticatedUsers`: A special identifier that represents anyone who is authenticated with a Google account or a service account. Does not include identities that come from external identity providers (IdPs) through identity federation. &#42; `user:{emailid}`: An email address that represents a specific Google account. For example, `alice@example.com` . &#42; `serviceAccount:{emailid}`: An email address that represents a Google service account. For example, `my-other-app@appspot.gserviceaccount.com`. &#42; `serviceAccount:{projectid}.svc.id.goog[{namespace}/{kubernetes-sa}]`: An identifier for a [Kubernetes service account](https://cloud.google.com/kubernetes-engine/docs/how-to/kubernetes-service-accounts). For example, `my-project.svc.id.goog[my-namespace/my-kubernetes-sa]`. &#42; `group:{emailid}`: An email address that represents a Google group. For example, `admins@example.com`. &#42; `domain:{domain}`: The G Suite domain (primary) that represents all the users of that domain. For example, `google.com` or `example.com`. &#42; `principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workforce identity pool. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/group/{group_id}`: All workforce identities in a group. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All workforce identities with a specific attribute value. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/&#42;`: All identities in a workforce identity pool. &#42; `principal://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workload identity pool. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/group/{group_id}`: A workload identity pool group. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All identities in a workload identity pool with a certain attribute. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/&#42;`: All identities in a workload identity pool. &#42; `deleted:user:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a user that has been recently deleted. For example, `alice@example.com?uid=123456789012345678901`. If the user is recovered, this value reverts to `user:{emailid}` and the recovered user retains the role in the binding. &#42; `deleted:serviceAccount:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a service account that has been recently deleted. For example, `my-other-app@appspot.gserviceaccount.com?uid=123456789012345678901`. If the service account is undeleted, this value reverts to `serviceAccount:{emailid}` and the undeleted service account retains the role in the binding. &#42; `deleted:group:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a Google group that has been recently deleted. For example, `admins@example.com?uid=123456789012345678901`. If the group is recovered, this value reverts to `group:{emailid}` and the recovered group retains the role in the binding. &#42; `deleted:principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: Deleted single identity in a workforce identity pool. For example, `deleted:principal://iam.googleapis.com/locations/global/workforcePools/my-pool-id/subject/my-subject-attribute-value`. */
		members: Option[List[String]] = None,
	  /** The condition that is associated with this binding. If the condition evaluates to `true`, then this binding applies to the current request. If the condition evaluates to `false`, then this binding does not apply to the current request. However, a different role binding might grant the same role to one or more of the principals in this binding. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		condition: Option[Schema.GoogleTypeExpr] = None
	)
	
	case class GoogleTypeExpr(
	  /** Textual representation of an expression in Common Expression Language syntax. */
		expression: Option[String] = None,
	  /** Optional. Description of the expression. This is a longer text which describes the expression, e.g. when hovered over it in a UI. */
		description: Option[String] = None,
	  /** Optional. String indicating the location of the expression for error reporting, e.g. a file name and a position in the file. */
		location: Option[String] = None,
	  /** Optional. Title for the expression, i.e. a short string describing its purpose. This can be used e.g. in UIs which allow to enter the expression. */
		title: Option[String] = None
	)
	
	case class GoogleIamV1AuditConfig(
	  /** The configuration for logging of each type of permission. */
		auditLogConfigs: Option[List[Schema.GoogleIamV1AuditLogConfig]] = None,
	  /** Specifies a service that will be enabled for audit logging. For example, `storage.googleapis.com`, `cloudsql.googleapis.com`. `allServices` is a special value that covers all services. */
		service: Option[String] = None
	)
	
	case class GoogleCloudPolicytroubleshooterV1TroubleshootIamPolicyRequest(
	  /** The information to use for checking whether a principal has a permission for a resource. */
		accessTuple: Option[Schema.GoogleCloudPolicytroubleshooterV1AccessTuple] = None
	)
	
	object GoogleCloudPolicytroubleshooterV1BindingExplanationAnnotatedMembership {
		enum RelevanceEnum extends Enum[RelevanceEnum] { case HEURISTIC_RELEVANCE_UNSPECIFIED, NORMAL, HIGH }
		enum MembershipEnum extends Enum[MembershipEnum] { case MEMBERSHIP_UNSPECIFIED, MEMBERSHIP_INCLUDED, MEMBERSHIP_NOT_INCLUDED, MEMBERSHIP_UNKNOWN_INFO_DENIED, MEMBERSHIP_UNKNOWN_UNSUPPORTED }
	}
	case class GoogleCloudPolicytroubleshooterV1BindingExplanationAnnotatedMembership(
	  /** The relevance of the principal's status to the overall determination for the binding. */
		relevance: Option[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanationAnnotatedMembership.RelevanceEnum] = None,
	  /** Indicates whether the binding includes the principal. */
		membership: Option[Schema.GoogleCloudPolicytroubleshooterV1BindingExplanationAnnotatedMembership.MembershipEnum] = None
	)
}
