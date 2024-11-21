package com.boresjo.play.api.google.policysimulator

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleCloudPolicysimulatorV1betaOrgPolicyOverlayPolicyOverlay(
	  /** Optional. The new or updated OrgPolicy. */
		policy: Option[Schema.GoogleCloudOrgpolicyV2Policy] = None,
	  /** Optional. The parent of the policy we are attaching to. Example: "projects/123456" */
		policyParent: Option[String] = None
	)
	
	case class GoogleCloudOrgpolicyV2AlternatePolicySpec(
	  /** Specify constraint for configurations of Google Cloud resources. */
		spec: Option[Schema.GoogleCloudOrgpolicyV2PolicySpec] = None,
	  /** Reference to the launch that will be used while audit logging and to control the launch. Should be set only in the alternate policy. */
		launch: Option[String] = None
	)
	
	object GoogleCloudPolicysimulatorV1alphaCreateOrgPolicyViolationsPreviewOperationMetadata {
		enum StateEnum extends Enum[StateEnum] { case PREVIEW_STATE_UNSPECIFIED, PREVIEW_PENDING, PREVIEW_RUNNING, PREVIEW_SUCCEEDED, PREVIEW_FAILED }
	}
	case class GoogleCloudPolicysimulatorV1alphaCreateOrgPolicyViolationsPreviewOperationMetadata(
	  /** Time when the request started processing, i.e., when the state was set to RUNNING. */
		startTime: Option[String] = None,
	  /** Time when the request was received. */
		requestTime: Option[String] = None,
	  /** Number of resources already scanned. */
		resourcesScanned: Option[Int] = None,
	  /** Number of resources still to scan. */
		resourcesPending: Option[Int] = None,
	  /** Total number of resources that need scanning. Should equal resource_scanned + resources_pending */
		resourcesFound: Option[Int] = None,
	  /** Output only. The current state of the operation. */
		state: Option[Schema.GoogleCloudPolicysimulatorV1alphaCreateOrgPolicyViolationsPreviewOperationMetadata.StateEnum] = None
	)
	
	object GoogleCloudOrgpolicyV2CustomConstraint {
		enum ActionTypeEnum extends Enum[ActionTypeEnum] { case ACTION_TYPE_UNSPECIFIED, ALLOW, DENY }
		enum MethodTypesEnum extends Enum[MethodTypesEnum] { case METHOD_TYPE_UNSPECIFIED, CREATE, UPDATE, DELETE, REMOVE_GRANT, GOVERN_TAGS }
	}
	case class GoogleCloudOrgpolicyV2CustomConstraint(
	  /** One line display name for the UI. The max length of the display_name is 200 characters. */
		displayName: Option[String] = None,
	  /** Allow or deny type. */
		actionType: Option[Schema.GoogleCloudOrgpolicyV2CustomConstraint.ActionTypeEnum] = None,
	  /** Detailed information about this custom policy constraint. The max length of the description is 2000 characters. */
		description: Option[String] = None,
	  /** All the operations being applied for this constraint. */
		methodTypes: Option[List[Schema.GoogleCloudOrgpolicyV2CustomConstraint.MethodTypesEnum]] = None,
	  /** Immutable. Name of the constraint. This is unique within the organization. Format of the name should be &#42; `organizations/{organization_id}/customConstraints/{custom_constraint_id}` Example: `organizations/123/customConstraints/custom.createOnlyE2TypeVms` The max length is 70 characters and the minimum length is 1. Note that the prefix `organizations/{organization_id}/customConstraints/` is not counted. */
		name: Option[String] = None,
	  /** Immutable. The resource instance type on which this policy applies. Format will be of the form : `/` Example: &#42; `compute.googleapis.com/Instance`. */
		resourceTypes: Option[List[String]] = None,
	  /** Output only. The last time this custom constraint was updated. This represents the last time that the `CreateCustomConstraint` or `UpdateCustomConstraint` RPC was called */
		updateTime: Option[String] = None,
	  /** Org policy condition/expression. For example: `resource.instanceName.matches("[production|test]_.&#42;_(\d)+")` or, `resource.management.auto_upgrade == true` The max length of the condition is 1000 characters. */
		condition: Option[String] = None
	)
	
	case class GoogleCloudPolicysimulatorV1alphaOrgPolicyOverlay(
	  /** Optional. The OrgPolicy CustomConstraint changes to preview violations for. Any existing CustomConstraints with the same name will be overridden in the simulation. That is, violations will be determined as if all custom constraints in the overlay were instantiated. Only a single custom_constraint is supported in the overlay at a time. For evaluating multiple constraints, multiple `GenerateOrgPolicyViolationsPreview` requests are made, where each request evaluates a single constraint. */
		customConstraints: Option[List[Schema.GoogleCloudPolicysimulatorV1alphaOrgPolicyOverlayCustomConstraintOverlay]] = None,
	  /** Optional. The OrgPolicy changes to preview violations for. Any existing OrgPolicies with the same name will be overridden in the simulation. That is, violations will be determined as if all policies in the overlay were created or updated. */
		policies: Option[List[Schema.GoogleCloudPolicysimulatorV1alphaOrgPolicyOverlayPolicyOverlay]] = None
	)
	
	case class GoogleCloudPolicysimulatorV1alphaOrgPolicyOverlayPolicyOverlay(
	  /** Optional. The new or updated OrgPolicy. */
		policy: Option[Schema.GoogleCloudOrgpolicyV2Policy] = None,
	  /** Optional. The parent of the policy we are attaching to. Example: "projects/123456" */
		policyParent: Option[String] = None
	)
	
	object GoogleCloudPolicysimulatorV1AccessStateDiff {
		enum AccessChangeEnum extends Enum[AccessChangeEnum] { case ACCESS_CHANGE_TYPE_UNSPECIFIED, NO_CHANGE, UNKNOWN_CHANGE, ACCESS_REVOKED, ACCESS_GAINED, ACCESS_MAYBE_REVOKED, ACCESS_MAYBE_GAINED }
	}
	case class GoogleCloudPolicysimulatorV1AccessStateDiff(
	  /** The results of evaluating the access tuple under the proposed (simulated) policies. If the AccessState couldn't be fully evaluated, this field explains why. */
		simulated: Option[Schema.GoogleCloudPolicysimulatorV1ExplainedAccess] = None,
	  /** How the principal's access, specified in the AccessState field, changed between the current (baseline) policies and proposed (simulated) policies. */
		accessChange: Option[Schema.GoogleCloudPolicysimulatorV1AccessStateDiff.AccessChangeEnum] = None,
	  /** The results of evaluating the access tuple under the current (baseline) policies. If the AccessState couldn't be fully evaluated, this field explains why. */
		baseline: Option[Schema.GoogleCloudPolicysimulatorV1ExplainedAccess] = None
	)
	
	case class GoogleRpcStatus(
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None
	)
	
	case class GoogleCloudPolicysimulatorV1alphaOrgPolicyViolationsPreviewResourceCounts(
	  /** Output only. Number of scanned resources with zero violations. */
		compliant: Option[Int] = None,
	  /** Output only. Number of resources where the constraint was not enforced, i.e. the Policy set `enforced: false` for that resource. */
		unenforced: Option[Int] = None,
	  /** Output only. Number of resources that returned an error when scanned. */
		errors: Option[Int] = None,
	  /** Output only. Number of scanned resources with at least one violation. */
		noncompliant: Option[Int] = None,
	  /** Output only. Number of resources checked for compliance. Must equal: unenforced + noncompliant + compliant + error */
		scanned: Option[Int] = None
	)
	
	object GoogleCloudPolicysimulatorV1betaCreateOrgPolicyViolationsPreviewOperationMetadata {
		enum StateEnum extends Enum[StateEnum] { case PREVIEW_STATE_UNSPECIFIED, PREVIEW_PENDING, PREVIEW_RUNNING, PREVIEW_SUCCEEDED, PREVIEW_FAILED }
	}
	case class GoogleCloudPolicysimulatorV1betaCreateOrgPolicyViolationsPreviewOperationMetadata(
	  /** Time when the request was received. */
		requestTime: Option[String] = None,
	  /** Number of resources still to scan. */
		resourcesPending: Option[Int] = None,
	  /** Time when the request started processing, i.e., when the state was set to RUNNING. */
		startTime: Option[String] = None,
	  /** Output only. The current state of the operation. */
		state: Option[Schema.GoogleCloudPolicysimulatorV1betaCreateOrgPolicyViolationsPreviewOperationMetadata.StateEnum] = None,
	  /** Number of resources already scanned. */
		resourcesScanned: Option[Int] = None,
	  /** Total number of resources that need scanning. Should equal resource_scanned + resources_pending */
		resourcesFound: Option[Int] = None
	)
	
	case class GoogleCloudPolicysimulatorV1ReplayResult(
	  /** The error that caused the access tuple replay to fail. This field is only included for access tuples that were not replayed successfully. */
		error: Option[Schema.GoogleRpcStatus] = None,
	  /** The access tuple that was replayed. This field includes information about the principal, resource, and permission that were involved in the access attempt. */
		accessTuple: Option[Schema.GoogleCloudPolicysimulatorV1AccessTuple] = None,
	  /** The latest date this access tuple was seen in the logs. */
		lastSeenDate: Option[Schema.GoogleTypeDate] = None,
	  /** The difference between the principal's access under the current (baseline) policies and the principal's access under the proposed (simulated) policies. This field is only included for access tuples that were successfully replayed and had different results under the current policies and the proposed policies. */
		diff: Option[Schema.GoogleCloudPolicysimulatorV1ReplayDiff] = None,
	  /** The Replay that the access tuple was included in. */
		parent: Option[String] = None,
	  /** The resource name of the `ReplayResult`, in the following format: `{projects|folders|organizations}/{resource-id}/locations/global/replays/{replay-id}/results/{replay-result-id}`, where `{resource-id}` is the ID of the project, folder, or organization that owns the Replay. Example: `projects/my-example-project/locations/global/replays/506a5f7f-38ce-4d7d-8e03-479ce1833c36/results/1234` */
		name: Option[String] = None
	)
	
	case class GoogleCloudPolicysimulatorV1ReplayOperationMetadata(
	  /** Time when the request was received. */
		startTime: Option[String] = None
	)
	
	case class GoogleIamV1Binding(
	  /** The condition that is associated with this binding. If the condition evaluates to `true`, then this binding applies to the current request. If the condition evaluates to `false`, then this binding does not apply to the current request. However, a different role binding might grant the same role to one or more of the principals in this binding. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		condition: Option[Schema.GoogleTypeExpr] = None,
	  /** Role that is assigned to the list of `members`, or principals. For example, `roles/viewer`, `roles/editor`, or `roles/owner`. For an overview of the IAM roles and permissions, see the [IAM documentation](https://cloud.google.com/iam/docs/roles-overview). For a list of the available pre-defined roles, see [here](https://cloud.google.com/iam/docs/understanding-roles). */
		role: Option[String] = None,
	  /** Specifies the principals requesting access for a Google Cloud resource. `members` can have the following values: &#42; `allUsers`: A special identifier that represents anyone who is on the internet; with or without a Google account. &#42; `allAuthenticatedUsers`: A special identifier that represents anyone who is authenticated with a Google account or a service account. Does not include identities that come from external identity providers (IdPs) through identity federation. &#42; `user:{emailid}`: An email address that represents a specific Google account. For example, `alice@example.com` . &#42; `serviceAccount:{emailid}`: An email address that represents a Google service account. For example, `my-other-app@appspot.gserviceaccount.com`. &#42; `serviceAccount:{projectid}.svc.id.goog[{namespace}/{kubernetes-sa}]`: An identifier for a [Kubernetes service account](https://cloud.google.com/kubernetes-engine/docs/how-to/kubernetes-service-accounts). For example, `my-project.svc.id.goog[my-namespace/my-kubernetes-sa]`. &#42; `group:{emailid}`: An email address that represents a Google group. For example, `admins@example.com`. &#42; `domain:{domain}`: The G Suite domain (primary) that represents all the users of that domain. For example, `google.com` or `example.com`. &#42; `principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workforce identity pool. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/group/{group_id}`: All workforce identities in a group. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All workforce identities with a specific attribute value. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/&#42;`: All identities in a workforce identity pool. &#42; `principal://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workload identity pool. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/group/{group_id}`: A workload identity pool group. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All identities in a workload identity pool with a certain attribute. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/&#42;`: All identities in a workload identity pool. &#42; `deleted:user:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a user that has been recently deleted. For example, `alice@example.com?uid=123456789012345678901`. If the user is recovered, this value reverts to `user:{emailid}` and the recovered user retains the role in the binding. &#42; `deleted:serviceAccount:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a service account that has been recently deleted. For example, `my-other-app@appspot.gserviceaccount.com?uid=123456789012345678901`. If the service account is undeleted, this value reverts to `serviceAccount:{emailid}` and the undeleted service account retains the role in the binding. &#42; `deleted:group:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a Google group that has been recently deleted. For example, `admins@example.com?uid=123456789012345678901`. If the group is recovered, this value reverts to `group:{emailid}` and the recovered group retains the role in the binding. &#42; `deleted:principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: Deleted single identity in a workforce identity pool. For example, `deleted:principal://iam.googleapis.com/locations/global/workforcePools/my-pool-id/subject/my-subject-attribute-value`. */
		members: Option[List[String]] = None
	)
	
	case class GoogleLongrunningOperation(
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.GoogleRpcStatus] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None,
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None
	)
	
	case class GoogleCloudOrgpolicyV2PolicySpecPolicyRule(
	  /** Setting this to true means that all values are denied. This field can be set only in policies for list constraints. */
		denyAll: Option[Boolean] = None,
	  /** List of values to be used for this policy rule. This field can be set only in policies for list constraints. */
		values: Option[Schema.GoogleCloudOrgpolicyV2PolicySpecPolicyRuleStringValues] = None,
	  /** If `true`, then the policy is enforced. If `false`, then any configuration is acceptable. This field can be set only in policies for boolean constraints. */
		enforce: Option[Boolean] = None,
	  /** A condition which determines whether this rule is used in the evaluation of the policy. When set, the `expression` field in the `Expr' must include from 1 to 10 subexpressions, joined by the "||" or "&&" operators. Each subexpression must be of the form "resource.matchTag('/tag_key_short_name, 'tag_value_short_name')". or "resource.matchTagId('tagKeys/key_id', 'tagValues/value_id')". where key_name and value_name are the resource names for Label Keys and Values. These names are available from the Tag Manager Service. An example expression is: "resource.matchTag('123456789/environment, 'prod')". or "resource.matchTagId('tagKeys/123', 'tagValues/456')". */
		condition: Option[Schema.GoogleTypeExpr] = None,
	  /** Setting this to true means that all values are allowed. This field can be set only in policies for list constraints. */
		allowAll: Option[Boolean] = None
	)
	
	object GoogleCloudPolicysimulatorV1betaGenerateOrgPolicyViolationsPreviewOperationMetadata {
		enum StateEnum extends Enum[StateEnum] { case PREVIEW_STATE_UNSPECIFIED, PREVIEW_PENDING, PREVIEW_RUNNING, PREVIEW_SUCCEEDED, PREVIEW_FAILED }
	}
	case class GoogleCloudPolicysimulatorV1betaGenerateOrgPolicyViolationsPreviewOperationMetadata(
	  /** Time when the request started processing, i.e. when the state was set to RUNNING. */
		startTime: Option[String] = None,
	  /** Time when the request was received. */
		requestTime: Option[String] = None,
	  /** Total number of resources that need scanning. Should equal resource_scanned + resources_pending */
		resourcesFound: Option[Int] = None,
	  /** Number of resources already scanned. */
		resourcesScanned: Option[Int] = None,
	  /** The current state of the operation. */
		state: Option[Schema.GoogleCloudPolicysimulatorV1betaGenerateOrgPolicyViolationsPreviewOperationMetadata.StateEnum] = None,
	  /** Number of resources still to scan. */
		resourcesPending: Option[Int] = None
	)
	
	case class GoogleCloudPolicysimulatorV1ReplayDiff(
	  /** A summary and comparison of the principal's access under the current (baseline) policies and the proposed (simulated) policies for a single access tuple. The evaluation of the principal's access is reported in the AccessState field. */
		accessDiff: Option[Schema.GoogleCloudPolicysimulatorV1AccessStateDiff] = None
	)
	
	case class GoogleLongrunningListOperationsResponse(
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None,
	  /** A list of operations that matches the specified filter in the request. */
		operations: Option[List[Schema.GoogleLongrunningOperation]] = None
	)
	
	object GoogleCloudPolicysimulatorV1Replay {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PENDING, RUNNING, SUCCEEDED, FAILED }
	}
	case class GoogleCloudPolicysimulatorV1Replay(
	  /** Output only. The current state of the `Replay`. */
		state: Option[Schema.GoogleCloudPolicysimulatorV1Replay.StateEnum] = None,
	  /** Required. The configuration used for the `Replay`. */
		config: Option[Schema.GoogleCloudPolicysimulatorV1ReplayConfig] = None,
	  /** Output only. The resource name of the `Replay`, which has the following format: `{projects|folders|organizations}/{resource-id}/locations/global/replays/{replay-id}`, where `{resource-id}` is the ID of the project, folder, or organization that owns the Replay. Example: `projects/my-example-project/locations/global/replays/506a5f7f-38ce-4d7d-8e03-479ce1833c36` */
		name: Option[String] = None,
	  /** Output only. Summary statistics about the replayed log entries. */
		resultsSummary: Option[Schema.GoogleCloudPolicysimulatorV1ReplayResultsSummary] = None
	)
	
	case class GoogleCloudPolicysimulatorV1ListReplayResultsResponse(
	  /** A token that you can use to retrieve the next page of ReplayResult objects. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** The results of running a Replay. */
		replayResults: Option[List[Schema.GoogleCloudPolicysimulatorV1ReplayResult]] = None
	)
	
	case class GoogleCloudPolicysimulatorV1AccessTuple(
	  /** Required. The full resource name that identifies the resource. For example, `//compute.googleapis.com/projects/my-project/zones/us-central1-a/instances/my-instance`. For examples of full resource names for Google Cloud services, see https://cloud.google.com/iam/help/troubleshooter/full-resource-names. */
		fullResourceName: Option[String] = None,
	  /** Required. The IAM permission to check for the specified principal and resource. For a complete list of IAM permissions, see https://cloud.google.com/iam/help/permissions/reference. For a complete list of predefined IAM roles and the permissions in each role, see https://cloud.google.com/iam/help/roles/reference. */
		permission: Option[String] = None,
	  /** Required. The principal whose access you want to check, in the form of the email address that represents that principal. For example, `alice@example.com` or `my-service-account@my-project.iam.gserviceaccount.com`. The principal must be a Google Account or a service account. Other types of principals are not supported. */
		principal: Option[String] = None
	)
	
	case class GoogleCloudOrgpolicyV2PolicySpec(
	  /** Determines the inheritance behavior for this policy. If `inherit_from_parent` is true, policy rules set higher up in the hierarchy (up to the closest root) are inherited and present in the effective policy. If it is false, then no rules are inherited, and this policy becomes the new root for evaluation. This field can be set only for policies which configure list constraints. */
		inheritFromParent: Option[Boolean] = None,
	  /** Output only. The time stamp this was previously updated. This represents the last time a call to `CreatePolicy` or `UpdatePolicy` was made for that policy. */
		updateTime: Option[String] = None,
	  /** An opaque tag indicating the current version of the policySpec, used for concurrency control. This field is ignored if used in a `CreatePolicy` request. When the policy is returned from either a `GetPolicy` or a `ListPolicies` request, this `etag` indicates the version of the current policySpec to use when executing a read-modify-write loop. When the policy is returned from a `GetEffectivePolicy` request, the `etag` will be unset. */
		etag: Option[String] = None,
	  /** In policies for boolean constraints, the following requirements apply: - There must be one and only one policy rule where condition is unset. - Boolean policy rules with conditions must set `enforced` to the opposite of the policy rule without a condition. - During policy evaluation, policy rules with conditions that are true for a target resource take precedence. */
		rules: Option[List[Schema.GoogleCloudOrgpolicyV2PolicySpecPolicyRule]] = None,
	  /** Ignores policies set above this resource and restores the `constraint_default` enforcement behavior of the specific constraint at this resource. This field can be set in policies for either list or boolean constraints. If set, `rules` must be empty and `inherit_from_parent` must be set to false. */
		reset: Option[Boolean] = None
	)
	
	object GoogleCloudPolicysimulatorV1alphaOrgPolicyViolationsPreview {
		enum StateEnum extends Enum[StateEnum] { case PREVIEW_STATE_UNSPECIFIED, PREVIEW_PENDING, PREVIEW_RUNNING, PREVIEW_SUCCEEDED, PREVIEW_FAILED }
	}
	case class GoogleCloudPolicysimulatorV1alphaOrgPolicyViolationsPreview(
	  /** Output only. Time when this `OrgPolicyViolationsPreview` was created. */
		createTime: Option[String] = None,
	  /** Output only. The number of OrgPolicyViolations in this `OrgPolicyViolationsPreview`. This count may differ from `resource_summary.noncompliant_count` because each OrgPolicyViolation is specific to a resource &#42;&#42;and&#42;&#42; constraint. If there are multiple constraints being evaluated (i.e. multiple policies in the overlay), a single resource may violate multiple constraints. */
		violationsCount: Option[Int] = None,
	  /** Output only. The names of the constraints against which all `OrgPolicyViolations` were evaluated. If `OrgPolicyOverlay` only contains `PolicyOverlay` then it contains the name of the configured custom constraint, applicable to the specified policies. Otherwise it contains the name of the constraint specified in `CustomConstraintOverlay`. Format: `organizations/{organization_id}/customConstraints/{custom_constraint_id}` Example: `organizations/123/customConstraints/custom.createOnlyE2TypeVms` */
		customConstraints: Option[List[String]] = None,
	  /** Output only. A summary of the state of all resources scanned for compliance with the changed OrgPolicy. */
		resourceCounts: Option[Schema.GoogleCloudPolicysimulatorV1alphaOrgPolicyViolationsPreviewResourceCounts] = None,
	  /** Required. The proposed changes we are previewing violations for. */
		overlay: Option[Schema.GoogleCloudPolicysimulatorV1alphaOrgPolicyOverlay] = None,
	  /** Output only. The resource name of the `OrgPolicyViolationsPreview`. It has the following format: `organizations/{organization}/locations/{location}/orgPolicyViolationsPreviews/{orgPolicyViolationsPreview}` Example: `organizations/my-example-org/locations/global/orgPolicyViolationsPreviews/506a5f7f` */
		name: Option[String] = None,
	  /** Output only. The state of the `OrgPolicyViolationsPreview`. */
		state: Option[Schema.GoogleCloudPolicysimulatorV1alphaOrgPolicyViolationsPreview.StateEnum] = None
	)
	
	object GoogleCloudPolicysimulatorV1ReplayConfig {
		enum LogSourceEnum extends Enum[LogSourceEnum] { case LOG_SOURCE_UNSPECIFIED, RECENT_ACCESSES }
	}
	case class GoogleCloudPolicysimulatorV1ReplayConfig(
	  /** A mapping of the resources that you want to simulate policies for and the policies that you want to simulate. Keys are the full resource names for the resources. For example, `//cloudresourcemanager.googleapis.com/projects/my-project`. For examples of full resource names for Google Cloud services, see https://cloud.google.com/iam/help/troubleshooter/full-resource-names. Values are Policy objects representing the policies that you want to simulate. Replays automatically take into account any IAM policies inherited through the resource hierarchy, and any policies set on descendant resources. You do not need to include these policies in the policy overlay. */
		policyOverlay: Option[Map[String, Schema.GoogleIamV1Policy]] = None,
	  /** The logs to use as input for the Replay. */
		logSource: Option[Schema.GoogleCloudPolicysimulatorV1ReplayConfig.LogSourceEnum] = None
	)
	
	object GoogleCloudPolicysimulatorV1alphaGenerateOrgPolicyViolationsPreviewOperationMetadata {
		enum StateEnum extends Enum[StateEnum] { case PREVIEW_STATE_UNSPECIFIED, PREVIEW_PENDING, PREVIEW_RUNNING, PREVIEW_SUCCEEDED, PREVIEW_FAILED }
	}
	case class GoogleCloudPolicysimulatorV1alphaGenerateOrgPolicyViolationsPreviewOperationMetadata(
	  /** Time when the request started processing, i.e. when the state was set to RUNNING. */
		startTime: Option[String] = None,
	  /** Number of resources already scanned. */
		resourcesScanned: Option[Int] = None,
	  /** Time when the request was received. */
		requestTime: Option[String] = None,
	  /** Total number of resources that need scanning. Should equal resource_scanned + resources_pending */
		resourcesFound: Option[Int] = None,
	  /** Number of resources still to scan. */
		resourcesPending: Option[Int] = None,
	  /** The current state of the operation. */
		state: Option[Schema.GoogleCloudPolicysimulatorV1alphaGenerateOrgPolicyViolationsPreviewOperationMetadata.StateEnum] = None
	)
	
	object GoogleCloudPolicysimulatorV1ExplainedPolicy {
		enum AccessEnum extends Enum[AccessEnum] { case ACCESS_STATE_UNSPECIFIED, GRANTED, NOT_GRANTED, UNKNOWN_CONDITIONAL, UNKNOWN_INFO_DENIED }
		enum RelevanceEnum extends Enum[RelevanceEnum] { case HEURISTIC_RELEVANCE_UNSPECIFIED, NORMAL, HIGH }
	}
	case class GoogleCloudPolicysimulatorV1ExplainedPolicy(
	  /** Indicates whether _this policy_ provides the specified permission to the specified principal for the specified resource. This field does _not_ indicate whether the principal actually has the permission for the resource. There might be another policy that overrides this policy. To determine whether the principal actually has the permission, use the `access` field in the TroubleshootIamPolicyResponse. */
		access: Option[Schema.GoogleCloudPolicysimulatorV1ExplainedPolicy.AccessEnum] = None,
	  /** The IAM policy attached to the resource. If the user who created the Replay does not have access to the policy, this field is empty. */
		policy: Option[Schema.GoogleIamV1Policy] = None,
	  /** Details about how each binding in the policy affects the principal's ability, or inability, to use the permission for the resource. If the user who created the Replay does not have access to the policy, this field is omitted. */
		bindingExplanations: Option[List[Schema.GoogleCloudPolicysimulatorV1BindingExplanation]] = None,
	  /** The full resource name that identifies the resource. For example, `//compute.googleapis.com/projects/my-project/zones/us-central1-a/instances/my-instance`. If the user who created the Replay does not have access to the policy, this field is omitted. For examples of full resource names for Google Cloud services, see https://cloud.google.com/iam/help/troubleshooter/full-resource-names. */
		fullResourceName: Option[String] = None,
	  /** The relevance of this policy to the overall determination in the TroubleshootIamPolicyResponse. If the user who created the Replay does not have access to the policy, this field is omitted. */
		relevance: Option[Schema.GoogleCloudPolicysimulatorV1ExplainedPolicy.RelevanceEnum] = None
	)
	
	object GoogleCloudPolicysimulatorV1ExplainedAccess {
		enum AccessStateEnum extends Enum[AccessStateEnum] { case ACCESS_STATE_UNSPECIFIED, GRANTED, NOT_GRANTED, UNKNOWN_CONDITIONAL, UNKNOWN_INFO_DENIED }
	}
	case class GoogleCloudPolicysimulatorV1ExplainedAccess(
	  /** If the AccessState is `UNKNOWN`, this field contains the policies that led to that result. If the `AccessState` is `GRANTED` or `NOT_GRANTED`, this field is omitted. */
		policies: Option[List[Schema.GoogleCloudPolicysimulatorV1ExplainedPolicy]] = None,
	  /** If the AccessState is `UNKNOWN`, this field contains a list of errors explaining why the result is `UNKNOWN`. If the `AccessState` is `GRANTED` or `NOT_GRANTED`, this field is omitted. */
		errors: Option[List[Schema.GoogleRpcStatus]] = None,
	  /** Whether the principal in the access tuple has permission to access the resource in the access tuple under the given policies. */
		accessState: Option[Schema.GoogleCloudPolicysimulatorV1ExplainedAccess.AccessStateEnum] = None
	)
	
	case class GoogleCloudPolicysimulatorV1ReplayResultsSummary(
	  /** The total number of log entries replayed. */
		logCount: Option[Int] = None,
	  /** The number of log entries that could not be replayed. */
		errorCount: Option[Int] = None,
	  /** The date of the newest log entry replayed. */
		newestDate: Option[Schema.GoogleTypeDate] = None,
	  /** The number of replayed log entries with no difference between baseline and simulated policies. */
		unchangedCount: Option[Int] = None,
	  /** The date of the oldest log entry replayed. */
		oldestDate: Option[Schema.GoogleTypeDate] = None,
	  /** The number of replayed log entries with a difference between baseline and simulated policies. */
		differenceCount: Option[Int] = None
	)
	
	object GoogleCloudPolicysimulatorV1BindingExplanationAnnotatedMembership {
		enum MembershipEnum extends Enum[MembershipEnum] { case MEMBERSHIP_UNSPECIFIED, MEMBERSHIP_INCLUDED, MEMBERSHIP_NOT_INCLUDED, MEMBERSHIP_UNKNOWN_INFO_DENIED, MEMBERSHIP_UNKNOWN_UNSUPPORTED }
		enum RelevanceEnum extends Enum[RelevanceEnum] { case HEURISTIC_RELEVANCE_UNSPECIFIED, NORMAL, HIGH }
	}
	case class GoogleCloudPolicysimulatorV1BindingExplanationAnnotatedMembership(
	  /** Indicates whether the binding includes the principal. */
		membership: Option[Schema.GoogleCloudPolicysimulatorV1BindingExplanationAnnotatedMembership.MembershipEnum] = None,
	  /** The relevance of the principal's status to the overall determination for the binding. */
		relevance: Option[Schema.GoogleCloudPolicysimulatorV1BindingExplanationAnnotatedMembership.RelevanceEnum] = None
	)
	
	object GoogleCloudPolicysimulatorV1betaOrgPolicyViolationsPreview {
		enum StateEnum extends Enum[StateEnum] { case PREVIEW_STATE_UNSPECIFIED, PREVIEW_PENDING, PREVIEW_RUNNING, PREVIEW_SUCCEEDED, PREVIEW_FAILED }
	}
	case class GoogleCloudPolicysimulatorV1betaOrgPolicyViolationsPreview(
	  /** Output only. A summary of the state of all resources scanned for compliance with the changed OrgPolicy. */
		resourceCounts: Option[Schema.GoogleCloudPolicysimulatorV1betaOrgPolicyViolationsPreviewResourceCounts] = None,
	  /** Output only. The number of OrgPolicyViolations in this `OrgPolicyViolationsPreview`. This count may differ from `resource_summary.noncompliant_count` because each OrgPolicyViolation is specific to a resource &#42;&#42;and&#42;&#42; constraint. If there are multiple constraints being evaluated (i.e. multiple policies in the overlay), a single resource may violate multiple constraints. */
		violationsCount: Option[Int] = None,
	  /** Output only. The names of the constraints against which all `OrgPolicyViolations` were evaluated. If `OrgPolicyOverlay` only contains `PolicyOverlay` then it contains the name of the configured custom constraint, applicable to the specified policies. Otherwise it contains the name of the constraint specified in `CustomConstraintOverlay`. Format: `organizations/{organization_id}/customConstraints/{custom_constraint_id}` Example: `organizations/123/customConstraints/custom.createOnlyE2TypeVms` */
		customConstraints: Option[List[String]] = None,
	  /** Required. The proposed changes we are previewing violations for. */
		overlay: Option[Schema.GoogleCloudPolicysimulatorV1betaOrgPolicyOverlay] = None,
	  /** Output only. The state of the `OrgPolicyViolationsPreview`. */
		state: Option[Schema.GoogleCloudPolicysimulatorV1betaOrgPolicyViolationsPreview.StateEnum] = None,
	  /** Output only. Time when this `OrgPolicyViolationsPreview` was created. */
		createTime: Option[String] = None,
	  /** Output only. The resource name of the `OrgPolicyViolationsPreview`. It has the following format: `organizations/{organization}/locations/{location}/orgPolicyViolationsPreviews/{orgPolicyViolationsPreview}` Example: `organizations/my-example-org/locations/global/orgPolicyViolationsPreviews/506a5f7f` */
		name: Option[String] = None
	)
	
	object GoogleCloudPolicysimulatorV1BindingExplanation {
		enum RolePermissionEnum extends Enum[RolePermissionEnum] { case ROLE_PERMISSION_UNSPECIFIED, ROLE_PERMISSION_INCLUDED, ROLE_PERMISSION_NOT_INCLUDED, ROLE_PERMISSION_UNKNOWN_INFO_DENIED }
		enum RelevanceEnum extends Enum[RelevanceEnum] { case HEURISTIC_RELEVANCE_UNSPECIFIED, NORMAL, HIGH }
		enum AccessEnum extends Enum[AccessEnum] { case ACCESS_STATE_UNSPECIFIED, GRANTED, NOT_GRANTED, UNKNOWN_CONDITIONAL, UNKNOWN_INFO_DENIED }
		enum RolePermissionRelevanceEnum extends Enum[RolePermissionRelevanceEnum] { case HEURISTIC_RELEVANCE_UNSPECIFIED, NORMAL, HIGH }
	}
	case class GoogleCloudPolicysimulatorV1BindingExplanation(
	  /** Indicates whether the role granted by this binding contains the specified permission. */
		rolePermission: Option[Schema.GoogleCloudPolicysimulatorV1BindingExplanation.RolePermissionEnum] = None,
	  /** Indicates whether each principal in the binding includes the principal specified in the request, either directly or indirectly. Each key identifies a principal in the binding, and each value indicates whether the principal in the binding includes the principal in the request. For example, suppose that a binding includes the following principals: &#42; `user:alice@example.com` &#42; `group:product-eng@example.com` The principal in the replayed access tuple is `user:bob@example.com`. This user is a principal of the group `group:product-eng@example.com`. For the first principal in the binding, the key is `user:alice@example.com`, and the `membership` field in the value is set to `MEMBERSHIP_NOT_INCLUDED`. For the second principal in the binding, the key is `group:product-eng@example.com`, and the `membership` field in the value is set to `MEMBERSHIP_INCLUDED`. */
		memberships: Option[Map[String, Schema.GoogleCloudPolicysimulatorV1BindingExplanationAnnotatedMembership]] = None,
	  /** A condition expression that prevents this binding from granting access unless the expression evaluates to `true`. To learn about IAM Conditions, see https://cloud.google.com/iam/docs/conditions-overview. */
		condition: Option[Schema.GoogleTypeExpr] = None,
	  /** The relevance of this binding to the overall determination for the entire policy. */
		relevance: Option[Schema.GoogleCloudPolicysimulatorV1BindingExplanation.RelevanceEnum] = None,
	  /** The role that this binding grants. For example, `roles/compute.serviceAgent`. For a complete list of predefined IAM roles, as well as the permissions in each role, see https://cloud.google.com/iam/help/roles/reference. */
		role: Option[String] = None,
	  /** Required. Indicates whether _this binding_ provides the specified permission to the specified principal for the specified resource. This field does _not_ indicate whether the principal actually has the permission for the resource. There might be another binding that overrides this binding. To determine whether the principal actually has the permission, use the `access` field in the TroubleshootIamPolicyResponse. */
		access: Option[Schema.GoogleCloudPolicysimulatorV1BindingExplanation.AccessEnum] = None,
	  /** The relevance of the permission's existence, or nonexistence, in the role to the overall determination for the entire policy. */
		rolePermissionRelevance: Option[Schema.GoogleCloudPolicysimulatorV1BindingExplanation.RolePermissionRelevanceEnum] = None
	)
	
	case class GoogleTypeExpr(
	  /** Optional. Title for the expression, i.e. a short string describing its purpose. This can be used e.g. in UIs which allow to enter the expression. */
		title: Option[String] = None,
	  /** Optional. String indicating the location of the expression for error reporting, e.g. a file name and a position in the file. */
		location: Option[String] = None,
	  /** Optional. Description of the expression. This is a longer text which describes the expression, e.g. when hovered over it in a UI. */
		description: Option[String] = None,
	  /** Textual representation of an expression in Common Expression Language syntax. */
		expression: Option[String] = None
	)
	
	case class GoogleIamV1AuditConfig(
	  /** The configuration for logging of each type of permission. */
		auditLogConfigs: Option[List[Schema.GoogleIamV1AuditLogConfig]] = None,
	  /** Specifies a service that will be enabled for audit logging. For example, `storage.googleapis.com`, `cloudsql.googleapis.com`. `allServices` is a special value that covers all services. */
		service: Option[String] = None
	)
	
	case class GoogleCloudOrgpolicyV2Policy(
	  /** Basic information about the Organization Policy. */
		spec: Option[Schema.GoogleCloudOrgpolicyV2PolicySpec] = None,
	  /** Deprecated. */
		alternate: Option[Schema.GoogleCloudOrgpolicyV2AlternatePolicySpec] = None,
	  /** Immutable. The resource name of the policy. Must be one of the following forms, where `constraint_name` is the name of the constraint which this policy configures: &#42; `projects/{project_number}/policies/{constraint_name}` &#42; `folders/{folder_id}/policies/{constraint_name}` &#42; `organizations/{organization_id}/policies/{constraint_name}` For example, `projects/123/policies/compute.disableSerialPortAccess`. Note: `projects/{project_id}/policies/{constraint_name}` is also an acceptable name for API requests, but responses will return the name using the equivalent project number. */
		name: Option[String] = None,
	  /** Optional. An opaque tag indicating the current state of the policy, used for concurrency control. This 'etag' is computed by the server based on the value of other fields, and may be sent on update and delete requests to ensure the client has an up-to-date value before proceeding. */
		etag: Option[String] = None,
	  /** Dry-run policy. Audit-only policy, can be used to monitor how the policy would have impacted the existing and future resources if it's enforced. */
		dryRunSpec: Option[Schema.GoogleCloudOrgpolicyV2PolicySpec] = None
	)
	
	case class GoogleCloudPolicysimulatorV1betaOrgPolicyViolationsPreviewResourceCounts(
	  /** Output only. Number of resources where the constraint was not enforced, i.e. the Policy set `enforced: false` for that resource. */
		unenforced: Option[Int] = None,
	  /** Output only. Number of scanned resources with at least one violation. */
		noncompliant: Option[Int] = None,
	  /** Output only. Number of scanned resources with zero violations. */
		compliant: Option[Int] = None,
	  /** Output only. Number of resources that returned an error when scanned. */
		errors: Option[Int] = None,
	  /** Output only. Number of resources checked for compliance. Must equal: unenforced + noncompliant + compliant + error */
		scanned: Option[Int] = None
	)
	
	case class GoogleCloudOrgpolicyV2PolicySpecPolicyRuleStringValues(
	  /** List of values denied at this resource. */
		deniedValues: Option[List[String]] = None,
	  /** List of values allowed at this resource. */
		allowedValues: Option[List[String]] = None
	)
	
	case class GoogleCloudPolicysimulatorV1betaOrgPolicyOverlayCustomConstraintOverlay(
	  /** Optional. The new or updated custom constraint. */
		customConstraint: Option[Schema.GoogleCloudOrgpolicyV2CustomConstraint] = None,
	  /** Optional. Resource the constraint is attached to. Example: "organization/987654" */
		customConstraintParent: Option[String] = None
	)
	
	case class GoogleTypeDate(
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None,
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None,
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None
	)
	
	case class GoogleIamV1Policy(
	  /** `etag` is used for optimistic concurrency control as a way to help prevent simultaneous updates of a policy from overwriting each other. It is strongly suggested that systems make use of the `etag` in the read-modify-write cycle to perform policy updates in order to avoid race conditions: An `etag` is returned in the response to `getIamPolicy`, and systems are expected to put that etag in the request to `setIamPolicy` to ensure that their change will be applied to the same version of the policy. &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. */
		etag: Option[String] = None,
	  /** Specifies cloud audit logging configuration for this policy. */
		auditConfigs: Option[List[Schema.GoogleIamV1AuditConfig]] = None,
	  /** Associates a list of `members`, or principals, with a `role`. Optionally, may specify a `condition` that determines how and when the `bindings` are applied. Each of the `bindings` must contain at least one principal. The `bindings` in a `Policy` can refer to up to 1,500 principals; up to 250 of these principals can be Google groups. Each occurrence of a principal counts towards these limits. For example, if the `bindings` grant 50 different roles to `user:alice@example.com`, and not to any other principal, then you can add another 1,450 principals to the `bindings` in the `Policy`. */
		bindings: Option[List[Schema.GoogleIamV1Binding]] = None,
	  /** Specifies the format of the policy. Valid values are `0`, `1`, and `3`. Requests that specify an invalid value are rejected. Any operation that affects conditional role bindings must specify version `3`. This requirement applies to the following operations: &#42; Getting a policy that includes a conditional role binding &#42; Adding a conditional role binding to a policy &#42; Changing a conditional role binding in a policy &#42; Removing any role binding, with or without a condition, from a policy that includes conditions &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. If a policy does not include any conditions, operations on that policy may specify any valid version or leave the field unset. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		version: Option[Int] = None
	)
	
	case class GoogleCloudPolicysimulatorV1betaOrgPolicyOverlay(
	  /** Optional. The OrgPolicy changes to preview violations for. Any existing OrgPolicies with the same name will be overridden in the simulation. That is, violations will be determined as if all policies in the overlay were created or updated. */
		policies: Option[List[Schema.GoogleCloudPolicysimulatorV1betaOrgPolicyOverlayPolicyOverlay]] = None,
	  /** Optional. The OrgPolicy CustomConstraint changes to preview violations for. Any existing CustomConstraints with the same name will be overridden in the simulation. That is, violations will be determined as if all custom constraints in the overlay were instantiated. Only a single custom_constraint is supported in the overlay at a time. For evaluating multiple constraints, multiple `GenerateOrgPolicyViolationsPreview` requests are made, where each request evaluates a single constraint. */
		customConstraints: Option[List[Schema.GoogleCloudPolicysimulatorV1betaOrgPolicyOverlayCustomConstraintOverlay]] = None
	)
	
	case class GoogleCloudPolicysimulatorV1alphaOrgPolicyOverlayCustomConstraintOverlay(
	  /** Optional. Resource the constraint is attached to. Example: "organization/987654" */
		customConstraintParent: Option[String] = None,
	  /** Optional. The new or updated custom constraint. */
		customConstraint: Option[Schema.GoogleCloudOrgpolicyV2CustomConstraint] = None
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
}
