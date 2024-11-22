package com.boresjo.play.api.google.iam

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleLongrunningOperation(
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.GoogleRpcStatus] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None
	)
	
	case class GoogleRpcStatus(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class GoogleIamV2ListPoliciesResponse(
	  /** Metadata for the policies that are attached to the resource. */
		policies: Option[List[Schema.GoogleIamV2Policy]] = None,
	  /** A page token that you can use in a ListPoliciesRequest to retrieve the next page. If this field is omitted, there are no additional pages. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleIamV2Policy(
	  /** Immutable. The resource name of the `Policy`, which must be unique. Format: `policies/{attachment_point}/denypolicies/{policy_id}` The attachment point is identified by its URL-encoded full resource name, which means that the forward-slash character, `/`, must be written as `%2F`. For example, `policies/cloudresourcemanager.googleapis.com%2Fprojects%2Fmy-project/denypolicies/my-deny-policy`. For organizations and folders, use the numeric ID in the full resource name. For projects, requests can use the alphanumeric or the numeric ID. Responses always contain the numeric ID. */
		name: Option[String] = None,
	  /** Immutable. The globally unique ID of the `Policy`. Assigned automatically when the `Policy` is created. */
		uid: Option[String] = None,
	  /** Output only. The kind of the `Policy`. Always contains the value `DenyPolicy`. */
		kind: Option[String] = None,
	  /** A user-specified description of the `Policy`. This value can be up to 63 characters. */
		displayName: Option[String] = None,
	  /** A key-value map to store arbitrary metadata for the `Policy`. Keys can be up to 63 characters. Values can be up to 255 characters. */
		annotations: Option[Map[String, String]] = None,
	  /** An opaque tag that identifies the current version of the `Policy`. IAM uses this value to help manage concurrent updates, so they do not cause one update to be overwritten by another. If this field is present in a CreatePolicyRequest, the value is ignored. */
		etag: Option[String] = None,
	  /** Output only. The time when the `Policy` was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the `Policy` was last updated. */
		updateTime: Option[String] = None,
	  /** Output only. The time when the `Policy` was deleted. Empty if the policy is not deleted. */
		deleteTime: Option[String] = None,
	  /** A list of rules that specify the behavior of the `Policy`. All of the rules should be of the `kind` specified in the `Policy`. */
		rules: Option[List[Schema.GoogleIamV2PolicyRule]] = None
	)
	
	case class GoogleIamV2PolicyRule(
	  /** A rule for a deny policy. */
		denyRule: Option[Schema.GoogleIamV2DenyRule] = None,
	  /** A user-specified description of the rule. This value can be up to 256 characters. */
		description: Option[String] = None
	)
	
	case class GoogleIamV2DenyRule(
	  /** The identities that are prevented from using one or more permissions on Google Cloud resources. This field can contain the following values: &#42; `principal://goog/subject/{email_id}`: A specific Google Account. Includes Gmail, Cloud Identity, and Google Workspace user accounts. For example, `principal://goog/subject/alice@example.com`. &#42; `principal://iam.googleapis.com/projects/-/serviceAccounts/{service_account_id}`: A Google Cloud service account. For example, `principal://iam.googleapis.com/projects/-/serviceAccounts/my-service-account@iam.gserviceaccount.com`. &#42; `principalSet://goog/group/{group_id}`: A Google group. For example, `principalSet://goog/group/admins@example.com`. &#42; `principalSet://goog/public:all`: A special identifier that represents any principal that is on the internet, even if they do not have a Google Account or are not logged in. &#42; `principalSet://goog/cloudIdentityCustomerId/{customer_id}`: All of the principals associated with the specified Google Workspace or Cloud Identity customer ID. For example, `principalSet://goog/cloudIdentityCustomerId/C01Abc35`. &#42; `principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workforce identity pool. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/group/{group_id}`: All workforce identities in a group. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All workforce identities with a specific attribute value. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/&#42;`: All identities in a workforce identity pool. &#42; `principal://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workload identity pool. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/group/{group_id}`: A workload identity pool group. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All identities in a workload identity pool with a certain attribute. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/&#42;`: All identities in a workload identity pool. &#42; `deleted:principal://goog/subject/{email_id}?uid={uid}`: A specific Google Account that was deleted recently. For example, `deleted:principal://goog/subject/alice@example.com?uid=1234567890`. If the Google Account is recovered, this identifier reverts to the standard identifier for a Google Account. &#42; `deleted:principalSet://goog/group/{group_id}?uid={uid}`: A Google group that was deleted recently. For example, `deleted:principalSet://goog/group/admins@example.com?uid=1234567890`. If the Google group is restored, this identifier reverts to the standard identifier for a Google group. &#42; `deleted:principal://iam.googleapis.com/projects/-/serviceAccounts/{service_account_id}?uid={uid}`: A Google Cloud service account that was deleted recently. For example, `deleted:principal://iam.googleapis.com/projects/-/serviceAccounts/my-service-account@iam.gserviceaccount.com?uid=1234567890`. If the service account is undeleted, this identifier reverts to the standard identifier for a service account. &#42; `deleted:principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: Deleted single identity in a workforce identity pool. For example, `deleted:principal://iam.googleapis.com/locations/global/workforcePools/my-pool-id/subject/my-subject-attribute-value`. */
		deniedPrincipals: Option[List[String]] = None,
	  /** The identities that are excluded from the deny rule, even if they are listed in the `denied_principals`. For example, you could add a Google group to the `denied_principals`, then exclude specific users who belong to that group. This field can contain the same values as the `denied_principals` field, excluding `principalSet://goog/public:all`, which represents all users on the internet. */
		exceptionPrincipals: Option[List[String]] = None,
	  /** The permissions that are explicitly denied by this rule. Each permission uses the format `{service_fqdn}/{resource}.{verb}`, where `{service_fqdn}` is the fully qualified domain name for the service. For example, `iam.googleapis.com/roles.list`. */
		deniedPermissions: Option[List[String]] = None,
	  /** Specifies the permissions that this rule excludes from the set of denied permissions given by `denied_permissions`. If a permission appears in `denied_permissions` _and_ in `exception_permissions` then it will _not_ be denied. The excluded permissions can be specified using the same syntax as `denied_permissions`. */
		exceptionPermissions: Option[List[String]] = None,
	  /** The condition that determines whether this deny rule applies to a request. If the condition expression evaluates to `true`, then the deny rule is applied; otherwise, the deny rule is not applied. Each deny rule is evaluated independently. If this deny rule does not apply to a request, other deny rules might still apply. The condition can use CEL functions that evaluate [resource tags](https://cloud.google.com/iam/help/conditions/resource-tags). Other functions and operators are not supported. */
		denialCondition: Option[Schema.GoogleTypeExpr] = None
	)
	
	case class GoogleTypeExpr(
	  /** Textual representation of an expression in Common Expression Language syntax. */
		expression: Option[String] = None,
	  /** Optional. Title for the expression, i.e. a short string describing its purpose. This can be used e.g. in UIs which allow to enter the expression. */
		title: Option[String] = None,
	  /** Optional. Description of the expression. This is a longer text which describes the expression, e.g. when hovered over it in a UI. */
		description: Option[String] = None,
	  /** Optional. String indicating the location of the expression for error reporting, e.g. a file name and a position in the file. */
		location: Option[String] = None
	)
	
	case class GoogleIamAdminV1AuditData(
	  /** The permission_delta when when creating or updating a Role. */
		permissionDelta: Option[Schema.GoogleIamAdminV1AuditDataPermissionDelta] = None
	)
	
	case class GoogleIamAdminV1AuditDataPermissionDelta(
	  /** Added permissions. */
		addedPermissions: Option[List[String]] = None,
	  /** Removed permissions. */
		removedPermissions: Option[List[String]] = None
	)
	
	case class GoogleIamV1LoggingAuditData(
	  /** Policy delta between the original policy and the newly set policy. */
		policyDelta: Option[Schema.GoogleIamV1PolicyDelta] = None
	)
	
	case class GoogleIamV1PolicyDelta(
	  /** The delta for Bindings between two policies. */
		bindingDeltas: Option[List[Schema.GoogleIamV1BindingDelta]] = None
	)
	
	object GoogleIamV1BindingDelta {
		enum ActionEnum extends Enum[ActionEnum] { case ACTION_UNSPECIFIED, ADD, REMOVE }
	}
	case class GoogleIamV1BindingDelta(
	  /** The action that was performed on a Binding. Required */
		action: Option[Schema.GoogleIamV1BindingDelta.ActionEnum] = None,
	  /** Role that is assigned to `members`. For example, `roles/viewer`, `roles/editor`, or `roles/owner`. Required */
		role: Option[String] = None,
	  /** A single identity requesting access for a Google Cloud resource. Follows the same format of Binding.members. Required */
		member: Option[String] = None,
	  /** The condition that is associated with this binding. */
		condition: Option[Schema.GoogleTypeExpr] = None
	)
	
	case class GoogleIamV2PolicyOperationMetadata(
	  /** Timestamp when the `google.longrunning.Operation` was created. */
		createTime: Option[String] = None
	)
	
	case class GoogleIamV1betaWorkloadIdentityPoolOperationMetadata(
	
	)
	
	case class GoogleCloudCommonOperationMetadata(
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
	
	case class GoogleIamV3mainOperationMetadata(
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Output only. Server-defined resource path for the target of the */
		target: Option[String] = None,
	  /** Output only. Name of the verb executed by the operation. */
		verb: Option[String] = None,
	  /** Output only. Human-readable status of the operation, if any. */
		statusMessage: Option[String] = None,
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have successfully been cancelled have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		requestedCancellation: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None
	)
	
	case class GoogleIamV3alphaOperationMetadata(
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Output only. Server-defined resource path for the target of the */
		target: Option[String] = None,
	  /** Output only. Name of the verb executed by the operation. */
		verb: Option[String] = None,
	  /** Output only. Human-readable status of the operation, if any. */
		statusMessage: Option[String] = None,
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have successfully been cancelled have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		requestedCancellation: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None
	)
	
	case class GoogleIamV3betaOperationMetadata(
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Output only. Server-defined resource path for the target of the */
		target: Option[String] = None,
	  /** Output only. Name of the verb executed by the operation. */
		verb: Option[String] = None,
	  /** Output only. Human-readable status of the operation, if any. */
		statusMessage: Option[String] = None,
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have successfully been cancelled have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		requestedCancellation: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None
	)
	
	case class GoogleIamV3OperationMetadata(
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Output only. Server-defined resource path for the target of the */
		target: Option[String] = None,
	  /** Output only. Name of the verb executed by the operation. */
		verb: Option[String] = None,
	  /** Output only. Human-readable status of the operation, if any. */
		statusMessage: Option[String] = None,
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have successfully been cancelled have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		requestedCancellation: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None
	)
	
	object CloudControl2SharedOperationsReconciliationOperationMetadata {
		enum ExclusiveActionEnum extends Enum[ExclusiveActionEnum] { case UNKNOWN_REPAIR_ACTION, DELETE, RETRY }
	}
	case class CloudControl2SharedOperationsReconciliationOperationMetadata(
	  /** DEPRECATED. Use exclusive_action instead. */
		deleteResource: Option[Boolean] = None,
	  /** Excluisive action returned by the CLH. */
		exclusiveAction: Option[Schema.CloudControl2SharedOperationsReconciliationOperationMetadata.ExclusiveActionEnum] = None
	)
}
