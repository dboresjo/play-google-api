package com.boresjo.play.api.google.orgpolicy

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleCloudOrgpolicyV2ListCustomConstraintsResponse(
	  /** Page token used to retrieve the next page. This is currently not used, but the server may at any point start supplying a valid token. */
		nextPageToken: Option[String] = None,
	  /** All custom constraints that exist on the organization resource. It will be empty if no custom constraints are set. */
		customConstraints: Option[List[Schema.GoogleCloudOrgpolicyV2CustomConstraint]] = None
	)
	
	case class GoogleCloudOrgpolicyV2ListPoliciesResponse(
	  /** Page token used to retrieve the next page. This is currently not used, but the server may at any point start supplying a valid token. */
		nextPageToken: Option[String] = None,
	  /** All policies that exist on the resource. It will be empty if no policies are set. */
		policies: Option[List[Schema.GoogleCloudOrgpolicyV2Policy]] = None
	)
	
	object GoogleCloudOrgpolicyV2Constraint {
		enum ConstraintDefaultEnum extends Enum[ConstraintDefaultEnum] { case CONSTRAINT_DEFAULT_UNSPECIFIED, ALLOW, DENY }
	}
	case class GoogleCloudOrgpolicyV2Constraint(
	  /** Immutable. The resource name of the constraint. Must be in one of the following forms: &#42; `projects/{project_number}/constraints/{constraint_name}` &#42; `folders/{folder_id}/constraints/{constraint_name}` &#42; `organizations/{organization_id}/constraints/{constraint_name}` For example, "/projects/123/constraints/compute.disableSerialPortAccess". */
		name: Option[String] = None,
	  /** The human readable name. Mutable. */
		displayName: Option[String] = None,
	  /** Defines this constraint as being a BooleanConstraint. */
		booleanConstraint: Option[Schema.GoogleCloudOrgpolicyV2ConstraintBooleanConstraint] = None,
	  /** Defines this constraint as being a ListConstraint. */
		listConstraint: Option[Schema.GoogleCloudOrgpolicyV2ConstraintListConstraint] = None,
	  /** The evaluation behavior of this constraint in the absence of a policy. */
		constraintDefault: Option[Schema.GoogleCloudOrgpolicyV2Constraint.ConstraintDefaultEnum] = None,
	  /** Shows if dry run is supported for this constraint or not. */
		supportsDryRun: Option[Boolean] = None,
	  /** Detailed description of what this constraint controls as well as how and where it is enforced. Mutable. */
		description: Option[String] = None
	)
	
	object GoogleCloudOrgpolicyV2CustomConstraint {
		enum MethodTypesEnum extends Enum[MethodTypesEnum] { case METHOD_TYPE_UNSPECIFIED, CREATE, UPDATE, DELETE, REMOVE_GRANT, GOVERN_TAGS }
		enum ActionTypeEnum extends Enum[ActionTypeEnum] { case ACTION_TYPE_UNSPECIFIED, ALLOW, DENY }
	}
	case class GoogleCloudOrgpolicyV2CustomConstraint(
	  /** One line display name for the UI. The max length of the display_name is 200 characters. */
		displayName: Option[String] = None,
	  /** All the operations being applied for this constraint. */
		methodTypes: Option[List[Schema.GoogleCloudOrgpolicyV2CustomConstraint.MethodTypesEnum]] = None,
	  /** Immutable. Name of the constraint. This is unique within the organization. Format of the name should be &#42; `organizations/{organization_id}/customConstraints/{custom_constraint_id}` Example: `organizations/123/customConstraints/custom.createOnlyE2TypeVms` The max length is 70 characters and the minimum length is 1. Note that the prefix `organizations/{organization_id}/customConstraints/` is not counted. */
		name: Option[String] = None,
	  /** Org policy condition/expression. For example: `resource.instanceName.matches("[production|test]_.&#42;_(\d)+")` or, `resource.management.auto_upgrade == true` The max length of the condition is 1000 characters. */
		condition: Option[String] = None,
	  /** Detailed information about this custom policy constraint. The max length of the description is 2000 characters. */
		description: Option[String] = None,
	  /** Output only. The last time this custom constraint was updated. This represents the last time that the `CreateCustomConstraint` or `UpdateCustomConstraint` RPC was called */
		updateTime: Option[String] = None,
	  /** Immutable. The resource instance type on which this policy applies. Format will be of the form : `/` Example: &#42; `compute.googleapis.com/Instance`. */
		resourceTypes: Option[List[String]] = None,
	  /** Allow or deny type. */
		actionType: Option[Schema.GoogleCloudOrgpolicyV2CustomConstraint.ActionTypeEnum] = None
	)
	
	case class GoogleCloudOrgpolicyV2AlternatePolicySpec(
	  /** Reference to the launch that will be used while audit logging and to control the launch. Should be set only in the alternate policy. */
		launch: Option[String] = None,
	  /** Specify constraint for configurations of Google Cloud resources. */
		spec: Option[Schema.GoogleCloudOrgpolicyV2PolicySpec] = None
	)
	
	object GoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinitionParameter {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, LIST, STRING, BOOLEAN }
		enum ItemEnum extends Enum[ItemEnum] { case TYPE_UNSPECIFIED, LIST, STRING, BOOLEAN }
	}
	case class GoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinitionParameter(
	  /** Type of the parameter. */
		`type`: Option[Schema.GoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinitionParameter.TypeEnum] = None,
	  /** Defines subproperties primarily used by the UI to display user-friendly information. */
		metadata: Option[Schema.GoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinitionParameterMetadata] = None,
	  /** Sets the value of the parameter in an assignment if no value is given. */
		defaultValue: Option[JsValue] = None,
	  /** Provides a CEL expression to specify the acceptable parameter values during assignment. For example, parameterName in ("parameterValue1", "parameterValue2") */
		validValuesExpr: Option[String] = None,
	  /** Determines the parameter’s value structure. For example, LIST can be specified by defining type : LIST, and item type as : STRING. */
		item: Option[Schema.GoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinitionParameter.ItemEnum] = None
	)
	
	case class GoogleProtobufEmpty(
	
	)
	
	case class GoogleCloudOrgpolicyV2ConstraintBooleanConstraint(
	  /** Custom constraint definition. This is set only for Managed Constraints */
		customConstraintDefinition: Option[Schema.GoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinition] = None
	)
	
	case class GoogleCloudOrgpolicyV2PolicySpecPolicyRuleStringValues(
	  /** List of values allowed at this resource. */
		allowedValues: Option[List[String]] = None,
	  /** List of values denied at this resource. */
		deniedValues: Option[List[String]] = None
	)
	
	case class GoogleCloudOrgpolicyV2PolicySpecPolicyRule(
	  /** Setting this to true means that all values are allowed. This field can be set only in policies for list constraints. */
		allowAll: Option[Boolean] = None,
	  /** If `true`, then the policy is enforced. If `false`, then any configuration is acceptable. This field can be set only in policies for boolean constraints. */
		enforce: Option[Boolean] = None,
	  /** List of values to be used for this policy rule. This field can be set only in policies for list constraints. */
		values: Option[Schema.GoogleCloudOrgpolicyV2PolicySpecPolicyRuleStringValues] = None,
	  /** A condition which determines whether this rule is used in the evaluation of the policy. When set, the `expression` field in the `Expr' must include from 1 to 10 subexpressions, joined by the "||" or "&&" operators. Each subexpression must be of the form "resource.matchTag('/tag_key_short_name, 'tag_value_short_name')". or "resource.matchTagId('tagKeys/key_id', 'tagValues/value_id')". where key_name and value_name are the resource names for Label Keys and Values. These names are available from the Tag Manager Service. An example expression is: "resource.matchTag('123456789/environment, 'prod')". or "resource.matchTagId('tagKeys/123', 'tagValues/456')". */
		condition: Option[Schema.GoogleTypeExpr] = None,
	  /** Setting this to true means that all values are denied. This field can be set only in policies for list constraints. */
		denyAll: Option[Boolean] = None
	)
	
	case class GoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinitionParameterMetadata(
	  /** Detailed description of what this `parameter` is and use of it. Mutable. */
		description: Option[String] = None
	)
	
	case class GoogleTypeExpr(
	  /** Optional. Title for the expression, i.e. a short string describing its purpose. This can be used e.g. in UIs which allow to enter the expression. */
		title: Option[String] = None,
	  /** Textual representation of an expression in Common Expression Language syntax. */
		expression: Option[String] = None,
	  /** Optional. String indicating the location of the expression for error reporting, e.g. a file name and a position in the file. */
		location: Option[String] = None,
	  /** Optional. Description of the expression. This is a longer text which describes the expression, e.g. when hovered over it in a UI. */
		description: Option[String] = None
	)
	
	object GoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinition {
		enum MethodTypesEnum extends Enum[MethodTypesEnum] { case METHOD_TYPE_UNSPECIFIED, CREATE, UPDATE, DELETE, REMOVE_GRANT, GOVERN_TAGS }
		enum ActionTypeEnum extends Enum[ActionTypeEnum] { case ACTION_TYPE_UNSPECIFIED, ALLOW, DENY }
	}
	case class GoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinition(
	  /** Org policy condition/expression. For example: `resource.instanceName.matches("[production|test]_.&#42;_(\d)+")` or, `resource.management.auto_upgrade == true` The max length of the condition is 1000 characters. */
		condition: Option[String] = None,
	  /** All the operations being applied for this constraint. */
		methodTypes: Option[List[Schema.GoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinition.MethodTypesEnum]] = None,
	  /** The resource instance type on which this policy applies. Format will be of the form : `/` Example: &#42; `compute.googleapis.com/Instance`. */
		resourceTypes: Option[List[String]] = None,
	  /** Stores Structure of parameters used by Constraint condition. Key of map represents name of the parameter. */
		parameters: Option[Map[String, Schema.GoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinitionParameter]] = None,
	  /** Allow or deny type. */
		actionType: Option[Schema.GoogleCloudOrgpolicyV2ConstraintCustomConstraintDefinition.ActionTypeEnum] = None
	)
	
	case class GoogleCloudOrgpolicyV2ListConstraintsResponse(
	  /** Page token used to retrieve the next page. This is currently not used. */
		nextPageToken: Option[String] = None,
	  /** The collection of constraints that are available on the targeted resource. */
		constraints: Option[List[Schema.GoogleCloudOrgpolicyV2Constraint]] = None
	)
	
	case class GoogleCloudOrgpolicyV2PolicySpec(
	  /** In policies for boolean constraints, the following requirements apply: - There must be one and only one policy rule where condition is unset. - Boolean policy rules with conditions must set `enforced` to the opposite of the policy rule without a condition. - During policy evaluation, policy rules with conditions that are true for a target resource take precedence. */
		rules: Option[List[Schema.GoogleCloudOrgpolicyV2PolicySpecPolicyRule]] = None,
	  /** Determines the inheritance behavior for this policy. If `inherit_from_parent` is true, policy rules set higher up in the hierarchy (up to the closest root) are inherited and present in the effective policy. If it is false, then no rules are inherited, and this policy becomes the new root for evaluation. This field can be set only for policies which configure list constraints. */
		inheritFromParent: Option[Boolean] = None,
	  /** An opaque tag indicating the current version of the policySpec, used for concurrency control. This field is ignored if used in a `CreatePolicy` request. When the policy is returned from either a `GetPolicy` or a `ListPolicies` request, this `etag` indicates the version of the current policySpec to use when executing a read-modify-write loop. When the policy is returned from a `GetEffectivePolicy` request, the `etag` will be unset. */
		etag: Option[String] = None,
	  /** Ignores policies set above this resource and restores the `constraint_default` enforcement behavior of the specific constraint at this resource. This field can be set in policies for either list or boolean constraints. If set, `rules` must be empty and `inherit_from_parent` must be set to false. */
		reset: Option[Boolean] = None,
	  /** Output only. The time stamp this was previously updated. This represents the last time a call to `CreatePolicy` or `UpdatePolicy` was made for that policy. */
		updateTime: Option[String] = None
	)
	
	case class GoogleCloudOrgpolicyV2ConstraintListConstraint(
	  /** Indicates whether subtrees of the Resource Manager resource hierarchy can be used in `Policy.allowed_values` and `Policy.denied_values`. For example, `"under:folders/123"` would match any resource under the 'folders/123' folder. */
		supportsUnder: Option[Boolean] = None,
	  /** Indicates whether values grouped into categories can be used in `Policy.allowed_values` and `Policy.denied_values`. For example, `"in:Python"` would match any value in the 'Python' group. */
		supportsIn: Option[Boolean] = None
	)
	
	case class GoogleCloudOrgpolicyV2Policy(
	  /** Optional. An opaque tag indicating the current state of the policy, used for concurrency control. This 'etag' is computed by the server based on the value of other fields, and may be sent on update and delete requests to ensure the client has an up-to-date value before proceeding. */
		etag: Option[String] = None,
	  /** Basic information about the Organization Policy. */
		spec: Option[Schema.GoogleCloudOrgpolicyV2PolicySpec] = None,
	  /** Deprecated. */
		alternate: Option[Schema.GoogleCloudOrgpolicyV2AlternatePolicySpec] = None,
	  /** Dry-run policy. Audit-only policy, can be used to monitor how the policy would have impacted the existing and future resources if it's enforced. */
		dryRunSpec: Option[Schema.GoogleCloudOrgpolicyV2PolicySpec] = None,
	  /** Immutable. The resource name of the policy. Must be one of the following forms, where `constraint_name` is the name of the constraint which this policy configures: &#42; `projects/{project_number}/policies/{constraint_name}` &#42; `folders/{folder_id}/policies/{constraint_name}` &#42; `organizations/{organization_id}/policies/{constraint_name}` For example, `projects/123/policies/compute.disableSerialPortAccess`. Note: `projects/{project_id}/policies/{constraint_name}` is also an acceptable name for API requests, but responses will return the name using the equivalent project number. */
		name: Option[String] = None
	)
}
