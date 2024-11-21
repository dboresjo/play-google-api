package com.boresjo.play.api.google.iap

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class SetIamPolicyRequest(
	  /** REQUIRED: The complete policy to be applied to the `resource`. The size of the policy is limited to a few 10s of KB. An empty policy is a valid policy but certain Google Cloud services (such as Projects) might reject them. */
		policy: Option[Schema.Policy] = None
	)
	
	case class Policy(
	  /** Specifies the format of the policy. Valid values are `0`, `1`, and `3`. Requests that specify an invalid value are rejected. Any operation that affects conditional role bindings must specify version `3`. This requirement applies to the following operations: &#42; Getting a policy that includes a conditional role binding &#42; Adding a conditional role binding to a policy &#42; Changing a conditional role binding in a policy &#42; Removing any role binding, with or without a condition, from a policy that includes conditions &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. If a policy does not include any conditions, operations on that policy may specify any valid version or leave the field unset. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		version: Option[Int] = None,
	  /** Associates a list of `members`, or principals, with a `role`. Optionally, may specify a `condition` that determines how and when the `bindings` are applied. Each of the `bindings` must contain at least one principal. The `bindings` in a `Policy` can refer to up to 1,500 principals; up to 250 of these principals can be Google groups. Each occurrence of a principal counts towards these limits. For example, if the `bindings` grant 50 different roles to `user:alice@example.com`, and not to any other principal, then you can add another 1,450 principals to the `bindings` in the `Policy`. */
		bindings: Option[List[Schema.Binding]] = None,
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
	
	case class GetIamPolicyRequest(
	  /** OPTIONAL: A `GetPolicyOptions` object for specifying options to `GetIamPolicy`. */
		options: Option[Schema.GetPolicyOptions] = None
	)
	
	case class GetPolicyOptions(
	  /** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		requestedPolicyVersion: Option[Int] = None
	)
	
	case class TestIamPermissionsRequest(
	  /** The set of permissions to check for the `resource`. Permissions with wildcards (such as `&#42;` or `storage.&#42;`) are not allowed. For more information see [IAM Overview](https://cloud.google.com/iam/docs/overview#permissions). */
		permissions: Option[List[String]] = None
	)
	
	case class TestIamPermissionsResponse(
	  /** A subset of `TestPermissionsRequest.permissions` that the caller is allowed. */
		permissions: Option[List[String]] = None
	)
	
	case class IapSettings(
	  /** Required. The resource name of the IAP protected resource. */
		name: Option[String] = None,
	  /** Optional. Top level wrapper for all access related setting in IAP */
		accessSettings: Option[Schema.AccessSettings] = None,
	  /** Optional. Top level wrapper for all application related settings in IAP */
		applicationSettings: Option[Schema.ApplicationSettings] = None
	)
	
	object AccessSettings {
		enum IdentitySourcesEnum extends Enum[IdentitySourcesEnum] { case IDENTITY_SOURCE_UNSPECIFIED, WORKFORCE_IDENTITY_FEDERATION }
	}
	case class AccessSettings(
	  /** Optional. GCIP claims and endpoint configurations for 3p identity providers. */
		gcipSettings: Option[Schema.GcipSettings] = None,
	  /** Optional. Configuration to allow cross-origin requests via IAP. */
		corsSettings: Option[Schema.CorsSettings] = None,
	  /** Optional. Settings to configure IAP's OAuth behavior. */
		oauthSettings: Option[Schema.OAuthSettings] = None,
	  /** Optional. Settings to allow google-internal teams to use IAP for apps hosted in a tenant project. */
		policyDelegationSettings: Option[Schema.PolicyDelegationSettings] = None,
	  /** Optional. Settings to configure reauthentication policies in IAP. */
		reauthSettings: Option[Schema.ReauthSettings] = None,
	  /** Optional. Settings to configure and enable allowed domains. */
		allowedDomainsSettings: Option[Schema.AllowedDomainsSettings] = None,
	  /** Optional. Settings to configure the workforce identity federation, including workforce pools and OAuth 2.0 settings. */
		workforceIdentitySettings: Option[Schema.WorkforceIdentitySettings] = None,
	  /** Optional. Identity sources that IAP can use to authenticate the end user. Only one identity source can be configured. */
		identitySources: Option[List[Schema.AccessSettings.IdentitySourcesEnum]] = None
	)
	
	case class GcipSettings(
	  /** Optional. GCIP tenant ids that are linked to the IAP resource. tenant_ids could be a string beginning with a number character to indicate authenticating with GCIP tenant flow, or in the format of _ to indicate authenticating with GCIP agent flow. If agent flow is used, tenant_ids should only contain one single element, while for tenant flow, tenant_ids can contain multiple elements. */
		tenantIds: Option[List[String]] = None,
	  /** Login page URI associated with the GCIP tenants. Typically, all resources within the same project share the same login page, though it could be overridden at the sub resource level. */
		loginPageUri: Option[String] = None
	)
	
	case class CorsSettings(
	  /** Configuration to allow HTTP OPTIONS calls to skip authorization. If undefined, IAP will not apply any special logic to OPTIONS requests. */
		allowHttpOptions: Option[Boolean] = None
	)
	
	case class OAuthSettings(
	  /** Domain hint to send as hd=? parameter in OAuth request flow. Enables redirect to primary IDP by skipping Google's login screen. https://developers.google.com/identity/protocols/OpenIDConnect#hd-param Note: IAP does not verify that the id token's hd claim matches this value since access behavior is managed by IAM policies. */
		loginHint: Option[String] = None,
	  /** Optional. List of client ids allowed to use IAP programmatically. */
		programmaticClients: Option[List[String]] = None
	)
	
	case class PolicyDelegationSettings(
	  /** The DNS name of the service (e.g. "resourcemanager.googleapis.com"). This should be the domain name part of the full resource names (see https://aip.dev/122#full-resource-names), which is usually the same as IamServiceSpec.service of the service where the resource type is defined. */
		iamServiceName: Option[String] = None,
	  /** Permission to check in IAM. */
		iamPermission: Option[String] = None,
	  /** IAM resource to check permission on */
		resource: Option[Schema.Resource] = None,
	  /** Policy name to be checked */
		policyName: Option[Schema.PolicyName] = None
	)
	
	case class Resource(
	  /** The &#42;&#42;relative&#42;&#42; name of the resource, which is the URI path of the resource without the leading "/". See https://cloud.google.com/iam/docs/conditions-resource-attributes#resource-name for examples used by other GCP Services. This field is &#42;&#42;required&#42;&#42; for services integrated with resource-attribute-based IAM conditions and/or CustomOrgPolicy. This field requires special handling for parents-only permissions such as `create` and `list`. See the document linked below for further details. See go/iam-conditions-sig-g3#populate-resource-attributes for specific details on populating this field. */
		name: Option[String] = None,
	  /** The public resource type name of the resource. It is configured using the official_name of the ResourceType as defined in service configurations under //configs/cloud/resourcetypes. For example, the official_name for GCP projects is set as 'cloudresourcemanager.googleapis.com/Project' according to //configs/cloud/resourcetypes/google/cloud/resourcemanager/prod.yaml This field is &#42;&#42;required&#42;&#42; for services integrated with resource-attribute-based IAM conditions and/or CustomOrgPolicy. This field requires special handling for parents-only permissions such as `create` and `list`. See the document linked below for further details. See go/iam-conditions-sig-g3#populate-resource-attributes for specific details on populating this field. */
		`type`: Option[String] = None,
	  /** The name of the service this resource belongs to. It is configured using the official_service_name of the Service as defined in service configurations under //configs/cloud/resourcetypes. For example, the official_service_name of cloud resource manager service is set as 'cloudresourcemanager.googleapis.com' according to //configs/cloud/resourcetypes/google/cloud/resourcemanager/prod.yaml This field is &#42;&#42;required&#42;&#42; for services integrated with resource-attribute-based IAM conditions and/or CustomOrgPolicy. This field requires special handling for parents-only permissions such as `create` and `list`. See the document linked below for further details. See go/iam-conditions-sig-g3#populate-resource-attributes for specific details on populating this field. */
		service: Option[String] = None,
	  /** The service defined labels of the resource on which the conditions will be evaluated. The semantics - including the key names - are vague to IAM. If the effective condition has a reference to a `resource.labels[foo]` construct, IAM consults with this map to retrieve the values associated with `foo` key for Conditions evaluation. If the provided key is not found in the labels map, the condition would evaluate to false. This field is in limited use. If your intended use case is not expected to express resource.labels attribute in IAM Conditions, leave this field empty. Before planning on using this attribute please: &#42; Read go/iam-conditions-labels-comm and ensure your service can meet the data availability and management requirements. &#42; Talk to iam-conditions-eng@ about your use case. */
		labels: Option[Map[String, String]] = None,
	  /** The proto or JSON formatted expected next state of the resource, wrapped in a google.protobuf.Any proto, against which the policy rules are evaluated. Services not integrated with custom org policy can omit this field. Services integrated with custom org policy must populate this field for all requests where the API call changes the state of the resource. Custom org policy backend uses these attributes to enforce custom org policies. When a proto is wrapped, it is generally the One Platform API proto. When a JSON string is wrapped, use `google.protobuf.StringValue` for the inner value. For create operations, GCP service is expected to pass resource from customer request as is. For update/patch operations, GCP service is expected to compute the next state with the patch provided by the user. See go/custom-constraints-org-policy-integration-guide for additional details. */
		expectedNextState: Option[Map[String, JsValue]] = None,
	  /** Used for calculating the next state of tags on the resource being passed for Custom Org Policy enforcement. NOTE: Only one of the tags representations (i.e. numeric or namespaced) should be populated. The input tags will be converted to the same representation before the calculation. This behavior intentionally may differ from other tags related fields in CheckPolicy request, which may require both formats to be passed in. IMPORTANT: If tags are unchanged, this field should not be set. */
		nextStateOfTags: Option[Schema.NextStateOfTags] = None
	)
	
	case class NextStateOfTags(
		tagsFullState: Option[Schema.TagsFullState] = None,
		tagsPartialState: Option[Schema.TagsPartialState] = None,
		tagsFullStateForChildResource: Option[Schema.TagsFullStateForChildResource] = None
	)
	
	case class TagsFullState(
	  /** If TagsFullState is initialized, the values in this field fully represent all the tags in the next state (the current tag values are not used). If tags.size() == 0, the next state of tags would be no tags for evaluation purposes. Only one type of tags reference (numeric or namespace) is required to be passed. */
		tags: Option[Map[String, String]] = None
	)
	
	case class TagsPartialState(
	  /** Tags that’ll be updated or added to the current state of tags for evaluation purposes. If a key exists in both "tags_to_upsert" and "tag_keys_to_remove", the one in "tag_keys_to_remove" is ignored. Only one type of tags reference (numeric or namespace) is required to be passed. */
		tagsToUpsert: Option[Map[String, String]] = None,
	  /** Keys of the tags that should be removed for evaluation purposes. IMPORTANT: Currently only numeric references are supported. Once support for namespace references is added, both the tag references (numeric and namespace) will be removed. */
		tagKeysToRemove: Option[List[String]] = None
	)
	
	case class TagsFullStateForChildResource(
	  /** If TagsFullStateForChildResource is initialized, the values in this field represent all the tags in the next state for the child resource. Only one type of tags reference (numeric or namespace) is required to be passed. IMPORTANT: This field should only be used when the target resource IAM policy name is UNKNOWN and the resource's parent IAM policy name is being passed in the request. */
		tags: Option[Map[String, String]] = None
	)
	
	case class PolicyName(
	  /** Resource type. Types are defined in IAM's .service files. Valid values for type might be 'storage_buckets', 'compute_instances', 'resourcemanager_customers', 'billing_accounts', etc. */
		`type`: Option[String] = None,
	  /** Identifies an instance of the type. ID format varies by type. The ID format is defined in the IAM .service file that defines the type, either in path_mapping or in a comment. */
		id: Option[String] = None,
	  /** For Cloud IAM: The location of the Policy. Must be empty or "global" for Policies owned by global IAM. Must name a region from prodspec/cloud-iam-cloudspec for Regional IAM Policies, see go/iam-faq#where-is-iam-currently-deployed. For Local IAM: This field should be set to "local". */
		region: Option[String] = None
	)
	
	object ReauthSettings {
		enum MethodEnum extends Enum[MethodEnum] { case METHOD_UNSPECIFIED, LOGIN, PASSWORD, SECURE_KEY, ENROLLED_SECOND_FACTORS }
		enum PolicyTypeEnum extends Enum[PolicyTypeEnum] { case POLICY_TYPE_UNSPECIFIED, MINIMUM, DEFAULT }
	}
	case class ReauthSettings(
	  /** Optional. Reauth method requested. */
		method: Option[Schema.ReauthSettings.MethodEnum] = None,
	  /** Optional. Reauth session lifetime, how long before a user has to reauthenticate again. */
		maxAge: Option[String] = None,
	  /** Optional. How IAP determines the effective policy in cases of hierarchical policies. Policies are merged from higher in the hierarchy to lower in the hierarchy. */
		policyType: Option[Schema.ReauthSettings.PolicyTypeEnum] = None
	)
	
	case class AllowedDomainsSettings(
	  /** Optional. Configuration for customers to opt in for the feature. */
		enable: Option[Boolean] = None,
	  /** Optional. List of trusted domains. */
		domains: Option[List[String]] = None
	)
	
	case class WorkforceIdentitySettings(
	  /** The workforce pool resources. Only one workforce pool is accepted. */
		workforcePools: Option[List[String]] = None,
	  /** OAuth 2.0 settings for IAP to perform OIDC flow with workforce identity federation services. */
		oauth2: Option[Schema.OAuth2] = None
	)
	
	case class OAuth2(
	  /** The OAuth 2.0 client ID registered in the workforce identity federation OAuth 2.0 Server. */
		clientId: Option[String] = None,
	  /** Input only. The OAuth 2.0 client secret created while registering the client ID. */
		clientSecret: Option[String] = None,
	  /** Output only. SHA256 hash value for the client secret. This field is returned by IAP when the settings are retrieved. */
		clientSecretSha256: Option[String] = None
	)
	
	case class ApplicationSettings(
	  /** Optional. Settings to configure IAP's behavior for a service mesh. */
		csmSettings: Option[Schema.CsmSettings] = None,
	  /** Optional. Customization for Access Denied page. */
		accessDeniedPageSettings: Option[Schema.AccessDeniedPageSettings] = None,
	  /** The Domain value to set for cookies generated by IAP. This value is not validated by the API, but will be ignored at runtime if invalid. */
		cookieDomain: Option[String] = None,
	  /** Optional. Settings to configure attribute propagation. */
		attributePropagationSettings: Option[Schema.AttributePropagationSettings] = None
	)
	
	case class CsmSettings(
	  /** Audience claim set in the generated RCToken. This value is not validated by IAP. */
		rctokenAud: Option[String] = None
	)
	
	case class AccessDeniedPageSettings(
	  /** The URI to be redirected to when access is denied. */
		accessDeniedPageUri: Option[String] = None,
	  /** Whether to generate a troubleshooting URL on access denied events to this application. */
		generateTroubleshootingUri: Option[Boolean] = None,
	  /** Whether to generate remediation token on access denied events to this application. */
		remediationTokenGenerationEnabled: Option[Boolean] = None
	)
	
	object AttributePropagationSettings {
		enum OutputCredentialsEnum extends Enum[OutputCredentialsEnum] { case OUTPUT_CREDENTIALS_UNSPECIFIED, HEADER, JWT, RCTOKEN }
	}
	case class AttributePropagationSettings(
	  /** Optional. Raw string CEL expression. Must return a list of attributes. A maximum of 45 attributes can be selected. Expressions can select different attribute types from `attributes`: `attributes.saml_attributes`, `attributes.iap_attributes`. The following functions are supported: - filter `.filter(, )`: Returns a subset of `` where `` is true for every item. - in ` in `: Returns true if `` contains ``. - selectByName `.selectByName()`: Returns the attribute in `` with the given `` name, otherwise returns empty. - emitAs `.emitAs()`: Sets the `` name field to the given `` for propagation in selected output credentials. - strict `.strict()`: Ignores the `x-goog-iap-attr-` prefix for the provided `` when propagating with the `HEADER` output credential, such as request headers. - append `.append()` OR `.append()`: Appends the provided `` or `` to the end of ``. Example expression: `attributes.saml_attributes.filter(x, x.name in ['test']).append(attributes.iap_attributes.selectByName('exact').emitAs('custom').strict())` */
		expression: Option[String] = None,
	  /** Optional. Which output credentials attributes selected by the CEL expression should be propagated in. All attributes will be fully duplicated in each selected output credential. */
		outputCredentials: Option[List[Schema.AttributePropagationSettings.OutputCredentialsEnum]] = None,
	  /** Optional. Whether the provided attribute propagation settings should be evaluated on user requests. If set to true, attributes returned from the expression will be propagated in the set output credentials. */
		enable: Option[Boolean] = None
	)
	
	case class ValidateIapAttributeExpressionResponse(
	
	)
	
	case class ListTunnelDestGroupsResponse(
	  /** TunnelDestGroup existing in the project. */
		tunnelDestGroups: Option[List[Schema.TunnelDestGroup]] = None,
	  /** A token that you can send as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class TunnelDestGroup(
	  /** Identifier. Identifier for the TunnelDestGroup. Must be unique within the project and contain only lower case letters (a-z) and dashes (-). */
		name: Option[String] = None,
	  /** Optional. Unordered list. List of CIDRs that this group applies to. */
		cidrs: Option[List[String]] = None,
	  /** Optional. Unordered list. List of FQDNs that this group applies to. */
		fqdns: Option[List[String]] = None
	)
	
	case class Empty(
	
	)
	
	case class ListBrandsResponse(
	  /** Brands existing in the project. */
		brands: Option[List[Schema.Brand]] = None
	)
	
	case class Brand(
	  /** Output only. Identifier of the brand. NOTE: GCP project number achieves the same brand identification purpose as only one brand per project can be created. */
		name: Option[String] = None,
	  /** Support email displayed on the OAuth consent screen. */
		supportEmail: Option[String] = None,
	  /** Application name displayed on OAuth consent screen. */
		applicationTitle: Option[String] = None,
	  /** Output only. Whether the brand is only intended for usage inside the G Suite organization only. */
		orgInternalOnly: Option[Boolean] = None
	)
	
	case class IdentityAwareProxyClient(
	  /** Output only. Unique identifier of the OAuth client. */
		name: Option[String] = None,
	  /** Output only. Client secret of the OAuth client. */
		secret: Option[String] = None,
	  /** Human-friendly name given to the OAuth client. */
		displayName: Option[String] = None
	)
	
	case class ListIdentityAwareProxyClientsResponse(
	  /** Clients existing in the brand. */
		identityAwareProxyClients: Option[List[Schema.IdentityAwareProxyClient]] = None,
	  /** A token, which can be send as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class ResetIdentityAwareProxyClientSecretRequest(
	
	)
}