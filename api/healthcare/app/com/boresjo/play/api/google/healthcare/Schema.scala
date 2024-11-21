package com.boresjo.play.api.google.healthcare

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

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
	
	case class CancelOperationRequest(
	
	)
	
	case class Empty(
	
	)
	
	case class ConsentStore(
	  /** Identifier. Resource name of the consent store, of the form `projects/{project_id}/locations/{location_id}/datasets/{dataset_id}/consentStores/{consent_store_id}`. Cannot be changed after creation. */
		name: Option[String] = None,
	  /** Optional. Default time to live for Consents created in this store. Must be at least 24 hours. Updating this field will not affect the expiration time of existing consents. */
		defaultConsentTtl: Option[String] = None,
	  /** Optional. User-supplied key-value pairs used to organize consent stores. Label keys must be between 1 and 63 characters long, have a UTF-8 encoding of maximum 128 bytes, and must conform to the following PCRE regular expression: \p{Ll}\p{Lo}{0,62}. Label values must be between 1 and 63 characters long, have a UTF-8 encoding of maximum 128 bytes, and must conform to the following PCRE regular expression: [\p{Ll}\p{Lo}\p{N}_-]{0,63}. No more than 64 labels can be associated with a given store. For more information: https://cloud.google.com/healthcare/docs/how-tos/labeling-resources */
		labels: Option[Map[String, String]] = None,
	  /** Optional. If `true`, UpdateConsent creates the Consent if it does not already exist. If unspecified, defaults to `false`. */
		enableConsentCreateOnUpdate: Option[Boolean] = None
	)
	
	case class ListConsentStoresResponse(
	  /** The returned consent stores. The maximum number of stores returned is determined by the value of page_size in the ListConsentStoresRequest. */
		consentStores: Option[List[Schema.ConsentStore]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	object AttributeDefinition {
		enum CategoryEnum extends Enum[CategoryEnum] { case CATEGORY_UNSPECIFIED, RESOURCE, REQUEST }
	}
	case class AttributeDefinition(
	  /** Identifier. Resource name of the Attribute definition, of the form `projects/{project_id}/locations/{location_id}/datasets/{dataset_id}/consentStores/{consent_store_id}/attributeDefinitions/{attribute_definition_id}`. Cannot be changed after creation. */
		name: Option[String] = None,
	  /** Optional. A description of the attribute. */
		description: Option[String] = None,
	  /** Required. The category of the attribute. The value of this field cannot be changed after creation. */
		category: Option[Schema.AttributeDefinition.CategoryEnum] = None,
	  /** Required. Possible values for the attribute. The number of allowed values must not exceed 500. An empty list is invalid. The list can only be expanded after creation. */
		allowedValues: Option[List[String]] = None,
	  /** Optional. Default values of the attribute in Consents. If no default values are specified, it defaults to an empty value. */
		consentDefaultValues: Option[List[String]] = None,
	  /** Optional. Default value of the attribute in User data mappings. If no default value is specified, it defaults to an empty value. This field is only applicable to attributes of the category `RESOURCE`. */
		dataMappingDefaultValue: Option[String] = None
	)
	
	case class ListAttributeDefinitionsResponse(
	  /** The returned Attribute definitions. The maximum number of attributes returned is determined by the value of page_size in the ListAttributeDefinitionsRequest. */
		attributeDefinitions: Option[List[Schema.AttributeDefinition]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class ConsentArtifact(
	  /** Identifier. Resource name of the Consent artifact, of the form `projects/{project_id}/locations/{location_id}/datasets/{dataset_id}/consentStores/{consent_store_id}/consentArtifacts/{consent_artifact_id}`. Cannot be changed after creation. */
		name: Option[String] = None,
	  /** Required. User's UUID provided by the client. */
		userId: Option[String] = None,
	  /** Optional. User's signature. */
		userSignature: Option[Schema.Signature] = None,
	  /** Optional. A signature from a guardian. */
		guardianSignature: Option[Schema.Signature] = None,
	  /** Optional. A signature from a witness. */
		witnessSignature: Option[Schema.Signature] = None,
	  /** Optional. Screenshots, PDFs, or other binary information documenting the user's consent. */
		consentContentScreenshots: Option[List[Schema.Image]] = None,
	  /** Optional. An string indicating the version of the consent information shown to the user. */
		consentContentVersion: Option[String] = None,
	  /** Optional. Metadata associated with the Consent artifact. For example, the consent locale or user agent version. */
		metadata: Option[Map[String, String]] = None
	)
	
	case class Signature(
	  /** Required. User's UUID provided by the client. */
		userId: Option[String] = None,
	  /** Optional. An image of the user's signature. */
		image: Option[Schema.Image] = None,
	  /** Optional. Metadata associated with the user's signature. For example, the user's name or the user's title. */
		metadata: Option[Map[String, String]] = None,
	  /** Optional. Timestamp of the signature. */
		signatureTime: Option[String] = None
	)
	
	case class Image(
	  /** Consent artifact content represented as a stream of bytes. This field is populated when returned in GetConsentArtifact response, but not included in CreateConsentArtifact and ListConsentArtifact response. */
		rawBytes: Option[String] = None,
	  /** Input only. Points to a Cloud Storage URI containing the consent artifact content. The URI must be in the following format: `gs://{bucket_id}/{object_id}`. The Cloud Healthcare API service account must have the `roles/storage.objectViewer` Cloud IAM role for this Cloud Storage location. The consent artifact content at this URI is copied to a Cloud Storage location managed by the Cloud Healthcare API. Responses to fetching requests return the consent artifact content in raw_bytes. */
		gcsUri: Option[String] = None
	)
	
	case class ListConsentArtifactsResponse(
	  /** The returned Consent artifacts. The maximum number of artifacts returned is determined by the value of page_size in the ListConsentArtifactsRequest. */
		consentArtifacts: Option[List[Schema.ConsentArtifact]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	object Consent {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ACTIVE, ARCHIVED, REVOKED, DRAFT, REJECTED }
	}
	case class Consent(
	  /** Identifier. Resource name of the Consent, of the form `projects/{project_id}/locations/{location_id}/datasets/{dataset_id}/consentStores/{consent_store_id}/consents/{consent_id}`. Cannot be changed after creation. */
		name: Option[String] = None,
	  /** Output only. The revision ID of the Consent. The format is an 8-character hexadecimal string. Refer to a specific revision of a Consent by appending `@{revision_id}` to the Consent's resource name. */
		revisionId: Option[String] = None,
	  /** Output only. The timestamp that the revision was created. */
		revisionCreateTime: Option[String] = None,
	  /** Required. User's UUID provided by the client. */
		userId: Option[String] = None,
	  /** Optional. Represents a user's consent in terms of the resources that can be accessed and under what conditions. */
		policies: Option[List[Schema.GoogleCloudHealthcareV1ConsentPolicy]] = None,
	  /** Required. The resource name of the Consent artifact that contains proof of the end user's consent, of the form `projects/{project_id}/locations/{location_id}/datasets/{dataset_id}/consentStores/{consent_store_id}/consentArtifacts/{consent_artifact_id}`. */
		consentArtifact: Option[String] = None,
	  /** Required. Indicates the current state of this Consent. */
		state: Option[Schema.Consent.StateEnum] = None,
	  /** Timestamp in UTC of when this Consent is considered expired. */
		expireTime: Option[String] = None,
	  /** Input only. The time to live for this Consent from when it is created. */
		ttl: Option[String] = None,
	  /** Optional. User-supplied key-value pairs used to organize Consent resources. Metadata keys must: - be between 1 and 63 characters long - have a UTF-8 encoding of maximum 128 bytes - begin with a letter - consist of up to 63 characters including lowercase letters, numeric characters, underscores, and dashes Metadata values must be: - be between 1 and 63 characters long - have a UTF-8 encoding of maximum 128 bytes - consist of up to 63 characters including lowercase letters, numeric characters, underscores, and dashes No more than 64 metadata entries can be associated with a given consent. */
		metadata: Option[Map[String, String]] = None
	)
	
	case class GoogleCloudHealthcareV1ConsentPolicy(
	  /** The resources that this policy applies to. A resource is a match if it matches all the attributes listed here. If empty, this policy applies to all User data mappings for the given user. */
		resourceAttributes: Option[List[Schema.Attribute]] = None,
	  /** Required. The request conditions to meet to grant access. In addition to any supported comparison operators, authorization rules may have `IN` operator as well as at most 10 logical operators that are limited to `AND` (`&&`), `OR` (`||`). */
		authorizationRule: Option[Schema.Expr] = None
	)
	
	case class Attribute(
	  /** Indicates the name of an attribute defined in the consent store. */
		attributeDefinitionId: Option[String] = None,
	  /** Required. The value of the attribute. Must be an acceptable value as defined in the consent store. For example, if the consent store defines "data type" with acceptable values "questionnaire" and "step-count", when the attribute name is data type, this field must contain one of those values. */
		values: Option[List[String]] = None
	)
	
	case class ActivateConsentRequest(
	  /** Required. The resource name of the Consent artifact that contains documentation of the user's consent, of the form `projects/{project_id}/locations/{location_id}/datasets/{dataset_id}/consentStores/{consent_store_id}/consentArtifacts/{consent_artifact_id}`. If the draft Consent had a Consent artifact, this Consent artifact overwrites it. */
		consentArtifact: Option[String] = None,
	  /** Timestamp in UTC of when this Consent is considered expired. */
		expireTime: Option[String] = None,
	  /** The time to live for this Consent from when it is marked as active. */
		ttl: Option[String] = None
	)
	
	case class RejectConsentRequest(
	  /** Optional. The resource name of the Consent artifact that contains documentation of the user's rejection of the draft Consent, of the form `projects/{project_id}/locations/{location_id}/datasets/{dataset_id}/consentStores/{consent_store_id}/consentArtifacts/{consent_artifact_id}`. If the draft Consent had a Consent artifact, this Consent artifact overwrites it. */
		consentArtifact: Option[String] = None
	)
	
	case class ListConsentsResponse(
	  /** The returned Consents. The maximum number of Consents returned is determined by the value of page_size in the ListConsentsRequest. */
		consents: Option[List[Schema.Consent]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class ListConsentRevisionsResponse(
	  /** The returned Consent revisions. The maximum number of revisions returned is determined by the value of `page_size` in the ListConsentRevisionsRequest. */
		consents: Option[List[Schema.Consent]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class RevokeConsentRequest(
	  /** Optional. The resource name of the Consent artifact that contains proof of the user's revocation of the Consent, of the form `projects/{project_id}/locations/{location_id}/datasets/{dataset_id}/consentStores/{consent_store_id}/consentArtifacts/{consent_artifact_id}`. */
		consentArtifact: Option[String] = None
	)
	
	case class UserDataMapping(
	  /** Resource name of the User data mapping, of the form `projects/{project_id}/locations/{location_id}/datasets/{dataset_id}/consentStores/{consent_store_id}/userDataMappings/{user_data_mapping_id}`. */
		name: Option[String] = None,
	  /** Required. A unique identifier for the mapped resource. */
		dataId: Option[String] = None,
	  /** Required. User's UUID provided by the client. */
		userId: Option[String] = None,
	  /** Attributes of the resource. Only explicitly set attributes are displayed here. Attribute definitions with defaults set implicitly apply to these User data mappings. Attributes listed here must be single valued, that is, exactly one value is specified for the field "values" in each Attribute. */
		resourceAttributes: Option[List[Schema.Attribute]] = None,
	  /** Output only. Indicates whether this mapping is archived. */
		archived: Option[Boolean] = None,
	  /** Output only. Indicates the time when this mapping was archived. */
		archiveTime: Option[String] = None
	)
	
	case class ListUserDataMappingsResponse(
	  /** The returned User data mappings. The maximum number of User data mappings returned is determined by the value of page_size in the ListUserDataMappingsRequest. */
		userDataMappings: Option[List[Schema.UserDataMapping]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class ArchiveUserDataMappingRequest(
	
	)
	
	case class ArchiveUserDataMappingResponse(
	
	)
	
	object CheckDataAccessRequest {
		enum ResponseViewEnum extends Enum[ResponseViewEnum] { case RESPONSE_VIEW_UNSPECIFIED, BASIC, FULL }
	}
	case class CheckDataAccessRequest(
	  /** Required. The unique identifier of the resource to check access for. This identifier must correspond to a User data mapping in the given consent store. */
		dataId: Option[String] = None,
	  /** The values of request attributes associated with this access request. */
		requestAttributes: Option[Map[String, String]] = None,
	  /** Optional. Specific Consents to evaluate the access request against. These Consents must have the same `user_id` as the evaluated User data mapping, must exist in the current `consent_store`, and have a `state` of either `ACTIVE` or `DRAFT`. A maximum of 100 Consents can be provided here. If no selection is specified, the access request is evaluated against all `ACTIVE` unexpired Consents with the same `user_id` as the evaluated User data mapping. */
		consentList: Option[Schema.ConsentList] = None,
	  /** Optional. The view for CheckDataAccessResponse. If unspecified, defaults to `BASIC` and returns `consented` as `TRUE` or `FALSE`. */
		responseView: Option[Schema.CheckDataAccessRequest.ResponseViewEnum] = None
	)
	
	case class ConsentList(
	  /** The resource names of the Consents to evaluate against, of the form `projects/{project_id}/locations/{location_id}/datasets/{dataset_id}/consentStores/{consent_store_id}/consents/{consent_id}`. */
		consents: Option[List[String]] = None
	)
	
	case class CheckDataAccessResponse(
	  /** Whether the requested resource is consented for the given use. */
		consented: Option[Boolean] = None,
	  /** The resource names of all evaluated Consents mapped to their evaluation. */
		consentDetails: Option[Map[String, Schema.ConsentEvaluation]] = None
	)
	
	object ConsentEvaluation {
		enum EvaluationResultEnum extends Enum[EvaluationResultEnum] { case EVALUATION_RESULT_UNSPECIFIED, NOT_APPLICABLE, NO_MATCHING_POLICY, NO_SATISFIED_POLICY, HAS_SATISFIED_POLICY }
	}
	case class ConsentEvaluation(
	  /** The evaluation result. */
		evaluationResult: Option[Schema.ConsentEvaluation.EvaluationResultEnum] = None
	)
	
	case class QueryAccessibleDataRequest(
	  /** The Cloud Storage destination. The Cloud Healthcare API service account must have the `roles/storage.objectAdmin` Cloud IAM role for this Cloud Storage location. */
		gcsDestination: Option[Schema.GoogleCloudHealthcareV1ConsentGcsDestination] = None,
	  /** Optional. The values of resource attributes associated with the type of resources being requested. If no values are specified, then all resource types are included in the output. */
		resourceAttributes: Option[Map[String, String]] = None,
	  /** The values of request attributes associated with this access request. */
		requestAttributes: Option[Map[String, String]] = None
	)
	
	case class GoogleCloudHealthcareV1ConsentGcsDestination(
	  /** URI for a Cloud Storage directory where the server writes result files, in the format `gs://{bucket-id}/{path/to/destination/dir}`. If there is no trailing slash, the service appends one when composing the object path. The user is responsible for creating the Cloud Storage bucket and directory referenced in `uri_prefix`. */
		uriPrefix: Option[String] = None
	)
	
	object EvaluateUserConsentsRequest {
		enum ResponseViewEnum extends Enum[ResponseViewEnum] { case RESPONSE_VIEW_UNSPECIFIED, BASIC, FULL }
	}
	case class EvaluateUserConsentsRequest(
	  /** Required. User ID to evaluate consents for. */
		userId: Option[String] = None,
	  /** Optional. The values of resource attributes associated with the resources being requested. If no values are specified, then all resources are queried. */
		resourceAttributes: Option[Map[String, String]] = None,
	  /** Required. The values of request attributes associated with this access request. */
		requestAttributes: Option[Map[String, String]] = None,
	  /** Optional. Specific Consents to evaluate the access request against. These Consents must have the same `user_id` as the User data mappings being evalauted, must exist in the current `consent_store`, and must have a `state` of either `ACTIVE` or `DRAFT`. A maximum of 100 Consents can be provided here. If unspecified, all `ACTIVE` unexpired Consents in the current `consent_store` will be evaluated. */
		consentList: Option[Schema.ConsentList] = None,
	  /** Optional. The view for EvaluateUserConsentsResponse. If unspecified, defaults to `BASIC` and returns `consented` as `TRUE` or `FALSE`. */
		responseView: Option[Schema.EvaluateUserConsentsRequest.ResponseViewEnum] = None,
	  /** Optional. Limit on the number of User data mappings to return in a single response. If not specified, 100 is used. May not be larger than 1000. */
		pageSize: Option[Int] = None,
	  /** Optional. Token to retrieve the next page of results, or empty to get the first page. */
		pageToken: Option[String] = None
	)
	
	case class EvaluateUserConsentsResponse(
	  /** The consent evaluation result for each `data_id`. */
		results: Option[List[Schema.Result]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. This token is valid for 72 hours after it is created. */
		nextPageToken: Option[String] = None
	)
	
	case class Result(
	  /** The unique identifier of the evaluated resource. */
		dataId: Option[String] = None,
	  /** Whether the resource is consented for the given use. */
		consented: Option[Boolean] = None,
	  /** The resource names of all evaluated Consents mapped to their evaluation. */
		consentDetails: Option[Map[String, Schema.ConsentEvaluation]] = None
	)
	
	case class DeidentifyFhirStoreRequest(
	  /** Required. The name of the FHIR store to create and write the redacted data to. For example, `projects/{project_id}/locations/{location_id}/datasets/{dataset_id}/fhirStores/{fhir_store_id}`. &#42; The destination dataset must exist. &#42; The source dataset and destination dataset must both reside in the same location. De-identifying data across multiple locations is not supported. &#42; The destination FHIR store must exist. &#42; The caller must have the healthcare.fhirResources.update permission to write to the destination FHIR store. */
		destinationStore: Option[String] = None,
	  /** Deidentify configuration. Only one of `config` and `gcs_config_uri` can be specified. */
		config: Option[Schema.DeidentifyConfig] = None,
	  /** Cloud Storage location to read the JSON cloud.healthcare.deidentify.DeidentifyConfig from, overriding the default config. Must be of the form `gs://{bucket_id}/path/to/object`. The Cloud Storage location must grant the Cloud IAM role `roles/storage.objectViewer` to the project's Cloud Healthcare Service Agent service account. Only one of `config` and `gcs_config_uri` can be specified. */
		gcsConfigUri: Option[String] = None,
	  /** A filter specifying the resources to include in the output. If not specified, all resources are included in the output. */
		resourceFilter: Option[Schema.FhirFilter] = None,
	  /** If true, skips resources that are created or modified after the de-identify operation is created. */
		skipModifiedResources: Option[Boolean] = None
	)
	
	case class DeidentifyConfig(
	  /** Optional. Configures de-id of application/DICOM content. */
		dicom: Option[Schema.DicomConfig] = None,
	  /** Optional. Configures de-id of application/FHIR content. */
		fhir: Option[Schema.FhirConfig] = None,
	  /** Optional. Configures de-identification of image pixels wherever they are found in the source_dataset. */
		image: Option[Schema.ImageConfig] = None,
	  /** Optional. Configures de-identification of text wherever it is found in the source_dataset. */
		text: Option[Schema.TextConfig] = None,
	  /** Optional. Ensures in-flight data remains in the region of origin during de-identification. The default value is false. Using this option results in a significant reduction of throughput, and is not compatible with `LOCATION` or `ORGANIZATION_NAME` infoTypes. `LOCATION` must be excluded within TextConfig, and must also be excluded within ImageConfig if image redaction is required. */
		useRegionalDataProcessing: Option[Boolean] = None
	)
	
	object DicomConfig {
		enum FilterProfileEnum extends Enum[FilterProfileEnum] { case TAG_FILTER_PROFILE_UNSPECIFIED, MINIMAL_KEEP_LIST_PROFILE, ATTRIBUTE_CONFIDENTIALITY_BASIC_PROFILE, KEEP_ALL_PROFILE, DEIDENTIFY_TAG_CONTENTS }
	}
	case class DicomConfig(
	  /** Optional. If true, skip replacing StudyInstanceUID, SeriesInstanceUID, SOPInstanceUID, and MediaStorageSOPInstanceUID and leave them untouched. The Cloud Healthcare API regenerates these UIDs by default based on the DICOM Standard's reasoning: "Whilst these UIDs cannot be mapped directly to an individual out of context, given access to the original images, or to a database of the original images containing the UIDs, it would be possible to recover the individual's identity." http://dicom.nema.org/medical/dicom/current/output/chtml/part15/sect_E.3.9.html */
		skipIdRedaction: Option[Boolean] = None,
	  /** List of tags to keep. Remove all other tags. */
		keepList: Option[Schema.TagFilterList] = None,
	  /** List of tags to remove. Keep all other tags. */
		removeList: Option[Schema.TagFilterList] = None,
	  /** Tag filtering profile that determines which tags to keep/remove. */
		filterProfile: Option[Schema.DicomConfig.FilterProfileEnum] = None
	)
	
	case class TagFilterList(
	  /** Optional. Tags to be filtered. Tags must be DICOM Data Elements, File Meta Elements, or Directory Structuring Elements, as defined at: http://dicom.nema.org/medical/dicom/current/output/html/part06.html#table_6-1,. They may be provided by "Keyword" or "Tag". For example "PatientID", "00100010". */
		tags: Option[List[String]] = None
	)
	
	case class FhirConfig(
	  /** Optional. Specifies FHIR paths to match and how to transform them. Any field that is not matched by a FieldMetadata is passed through to the output dataset unmodified. All extensions will be processed according to `default_keep_extensions`. */
		fieldMetadataList: Option[List[Schema.FieldMetadata]] = None,
	  /** Optional. The behaviour for handling FHIR extensions that aren't otherwise specified for de-identification. If true, all extensions are preserved during de-identification by default. If false or unspecified, all extensions are removed during de-identification by default. */
		defaultKeepExtensions: Option[Boolean] = None
	)
	
	object FieldMetadata {
		enum ActionEnum extends Enum[ActionEnum] { case ACTION_UNSPECIFIED, TRANSFORM, INSPECT_AND_TRANSFORM, DO_NOT_TRANSFORM }
	}
	case class FieldMetadata(
	  /** Optional. List of paths to FHIR fields to be redacted. Each path is a period-separated list where each component is either a field name or FHIR type name, for example: Patient, HumanName. For "choice" types (those defined in the FHIR spec with the form: field[x]) we use two separate components. For example, "deceasedAge.unit" is matched by "Deceased.Age.unit". Supported types are: AdministrativeGenderCode, Base64Binary, Boolean, Code, Date, DateTime, Decimal, HumanName, Id, Instant, Integer, LanguageCode, Markdown, Oid, PositiveInt, String, UnsignedInt, Uri, Uuid, Xhtml. */
		paths: Option[List[String]] = None,
	  /** Optional. Deidentify action for one field. */
		action: Option[Schema.FieldMetadata.ActionEnum] = None
	)
	
	object ImageConfig {
		enum TextRedactionModeEnum extends Enum[TextRedactionModeEnum] { case TEXT_REDACTION_MODE_UNSPECIFIED, REDACT_ALL_TEXT, REDACT_SENSITIVE_TEXT, REDACT_NO_TEXT }
	}
	case class ImageConfig(
	  /** Optional. Determines how to redact text from image. */
		textRedactionMode: Option[Schema.ImageConfig.TextRedactionModeEnum] = None
	)
	
	case class TextConfig(
	  /** Optional. The transformations to apply to the detected data. Deprecated. Use `additional_transformations` instead. */
		transformations: Option[List[Schema.InfoTypeTransformation]] = None,
	  /** Optional. Transformations to apply to the detected data, overridden by `exclude_info_types`. */
		additionalTransformations: Option[List[Schema.InfoTypeTransformation]] = None,
	  /** Optional. InfoTypes to skip transforming, overriding `additional_transformations`. */
		excludeInfoTypes: Option[List[String]] = None
	)
	
	case class InfoTypeTransformation(
	  /** Optional. InfoTypes to apply this transformation to. If this is not specified, the transformation applies to any info_type. */
		infoTypes: Option[List[String]] = None,
	  /** Config for text redaction. */
		redactConfig: Option[Schema.RedactConfig] = None,
	  /** Config for character mask. */
		characterMaskConfig: Option[Schema.CharacterMaskConfig] = None,
	  /** Config for date shift. */
		dateShiftConfig: Option[Schema.DateShiftConfig] = None,
	  /** Config for crypto hash. */
		cryptoHashConfig: Option[Schema.CryptoHashConfig] = None,
	  /** Config for replace with InfoType. */
		replaceWithInfoTypeConfig: Option[Schema.ReplaceWithInfoTypeConfig] = None
	)
	
	case class RedactConfig(
	
	)
	
	case class CharacterMaskConfig(
	  /** Optional. Character to mask the sensitive values. If not supplied, defaults to "&#42;". */
		maskingCharacter: Option[String] = None
	)
	
	case class DateShiftConfig(
	  /** An AES 128/192/256 bit key. The date shift is computed based on this key and the patient ID. If the patient ID is empty for a DICOM resource, the date shift is computed based on this key and the study instance UID. If `crypto_key` is not set, then `kms_wrapped` is used to calculate the date shift. If neither is set, a default key is generated for each de-identify operation. Must not be set if `kms_wrapped` is set. */
		cryptoKey: Option[String] = None,
	  /** KMS wrapped key. If `kms_wrapped` is not set, then `crypto_key` is used to calculate the date shift. If neither is set, a default key is generated for each de-identify operation. Must not be set if `crypto_key` is set. */
		kmsWrapped: Option[Schema.KmsWrappedCryptoKey] = None
	)
	
	case class KmsWrappedCryptoKey(
	  /** Required. The wrapped data crypto key. */
		wrappedKey: Option[String] = None,
	  /** Required. The resource name of the KMS CryptoKey to use for unwrapping. For example, `projects/{project_id}/locations/{location_id}/keyRings/{keyring}/cryptoKeys/{key}`. */
		cryptoKey: Option[String] = None
	)
	
	case class CryptoHashConfig(
	  /** An AES 128/192/256 bit key. Causes the hash to be computed based on this key. A default key is generated for each Deidentify operation and is used when neither `crypto_key` nor `kms_wrapped` is specified. Must not be set if `kms_wrapped` is set. */
		cryptoKey: Option[String] = None,
	  /** KMS wrapped key. Must not be set if `crypto_key` is set. */
		kmsWrapped: Option[Schema.KmsWrappedCryptoKey] = None
	)
	
	case class ReplaceWithInfoTypeConfig(
	
	)
	
	case class FhirFilter(
	  /** List of resources to include in the output. If this list is empty or not specified, all resources are included in the output. */
		resources: Option[Schema.Resources] = None
	)
	
	case class Resources(
	  /** List of resources IDs. For example, "Patient/1234". */
		resources: Option[List[String]] = None
	)
	
	case class DeidentifyDicomStoreRequest(
	  /** Required. The name of the DICOM store to create and write the redacted data to. For example, `projects/{project_id}/locations/{location_id}/datasets/{dataset_id}/dicomStores/{dicom_store_id}`. &#42; The destination dataset must exist. &#42; The source dataset and destination dataset must both reside in the same location. De-identifying data across multiple locations is not supported. &#42; The destination DICOM store must not exist. &#42; The caller must have the necessary permissions to create the destination DICOM store. */
		destinationStore: Option[String] = None,
	  /** Deidentify configuration. Only one of `config` and `gcs_config_uri` can be specified. */
		config: Option[Schema.DeidentifyConfig] = None,
	  /** Cloud Storage location to read the JSON cloud.healthcare.deidentify.DeidentifyConfig from, overriding the default config. Must be of the form `gs://{bucket_id}/path/to/object`. The Cloud Storage location must grant the Cloud IAM role `roles/storage.objectViewer` to the project's Cloud Healthcare Service Agent service account. Only one of `config` and `gcs_config_uri` can be specified. */
		gcsConfigUri: Option[String] = None,
	  /** Filter configuration. */
		filterConfig: Option[Schema.DicomFilterConfig] = None
	)
	
	case class DicomFilterConfig(
	  /** The Cloud Storage location of the filter configuration file. The `gcs_uri` must be in the format `gs://bucket/path/to/object`. The filter configuration file must contain a list of resource paths separated by newline characters (\n or \r\n). Each resource path must be in the format "/studies/{studyUID}[/series/{seriesUID}[/instances/{instanceUID}]]" The Cloud Healthcare API service account must have the `roles/storage.objectViewer` Cloud IAM role for this Cloud Storage location. */
		resourcePathsGcsUri: Option[String] = None
	)
	
	case class Dataset(
	  /** Identifier. Resource name of the dataset, of the form `projects/{project_id}/locations/{location_id}/datasets/{dataset_id}`. */
		name: Option[String] = None,
	  /** Optional. The default timezone used by this dataset. Must be a either a valid IANA time zone name such as "America/New_York" or empty, which defaults to UTC. This is used for parsing times in resources, such as HL7 messages, where no explicit timezone is specified. */
		timeZone: Option[String] = None,
	  /** Optional. Customer-managed encryption key spec for a Dataset. If set, this Dataset and all of its sub-resources will be secured by this key. If empty, the Dataset is secured by the default Google encryption key. */
		encryptionSpec: Option[Schema.EncryptionSpec] = None
	)
	
	case class EncryptionSpec(
	  /** Required. The resource name of customer-managed encryption key that is used to secure a resource and its sub-resources. Only the key in the same location as this Dataset is allowed to be used for encryption. Format is: `projects/{project}/locations/{location}/keyRings/{keyRing}/cryptoKeys/{key}` */
		kmsKeyName: Option[String] = None
	)
	
	case class ListDatasetsResponse(
	  /** The first page of datasets. */
		datasets: Option[List[Schema.Dataset]] = None,
	  /** Token to retrieve the next page of results, or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class DeidentifyDatasetRequest(
	  /** Required. The name of the dataset resource to create and write the redacted data to. &#42; The destination dataset must not exist. &#42; The destination dataset must be in the same location as the source dataset. De-identifying data across multiple locations is not supported. */
		destinationDataset: Option[String] = None,
	  /** Deidentify configuration. Only one of `config` and `gcs_config_uri` can be specified. */
		config: Option[Schema.DeidentifyConfig] = None,
	  /** Cloud Storage location to read the JSON cloud.healthcare.deidentify.DeidentifyConfig from, overriding the default config. Must be of the form `gs://{bucket_id}/path/to/object`. The Cloud Storage location must grant the Cloud IAM role `roles/storage.objectViewer` to the project's Cloud Healthcare Service Agent service account. Only one of `config` and `gcs_config_uri` can be specified. */
		gcsConfigUri: Option[String] = None
	)
	
	case class StorageInfo(
	  /** The resource whose storage info is returned. For example: `projects/{projectID}/locations/{locationID}/datasets/{datasetID}/dicomStores/{dicomStoreID}/dicomWeb/studies/{studyUID}/series/{seriesUID}/instances/{instanceUID}` */
		referencedResource: Option[String] = None,
	  /** Info about the data stored in structured storage for the resource. */
		structuredStorageInfo: Option[Schema.StructuredStorageInfo] = None,
	  /** Info about the data stored in blob storage for the resource. */
		blobStorageInfo: Option[Schema.BlobStorageInfo] = None
	)
	
	case class StructuredStorageInfo(
	  /** Size in bytes of data stored in structured storage. */
		sizeBytes: Option[String] = None
	)
	
	object BlobStorageInfo {
		enum StorageClassEnum extends Enum[StorageClassEnum] { case BLOB_STORAGE_CLASS_UNSPECIFIED, STANDARD, NEARLINE, COLDLINE, ARCHIVE }
	}
	case class BlobStorageInfo(
	  /** Size in bytes of data stored in Blob Storage. */
		sizeBytes: Option[String] = None,
	  /** The storage class in which the Blob data is stored. */
		storageClass: Option[Schema.BlobStorageInfo.StorageClassEnum] = None,
	  /** The time at which the storage class was updated. This is used to compute early deletion fees of the resource. */
		storageClassUpdateTime: Option[String] = None
	)
	
	case class SetBlobStorageSettingsRequest(
	  /** Optional. A filter configuration. If `filter_config` is specified, set the value of `resource` to the resource name of a DICOM store in the format `projects/{projectID}/locations/{locationID}/datasets/{datasetID}/dicomStores/{dicomStoreID}`. */
		filterConfig: Option[Schema.DicomFilterConfig] = None,
	  /** The blob storage settings to update for the specified resources. Only fields listed in `update_mask` are applied. */
		blobStorageSettings: Option[Schema.BlobStorageSettings] = None
	)
	
	object BlobStorageSettings {
		enum BlobStorageClassEnum extends Enum[BlobStorageClassEnum] { case BLOB_STORAGE_CLASS_UNSPECIFIED, STANDARD, NEARLINE, COLDLINE, ARCHIVE }
	}
	case class BlobStorageSettings(
	  /** The Storage class in which the Blob data is stored. */
		blobStorageClass: Option[Schema.BlobStorageSettings.BlobStorageClassEnum] = None
	)
	
	case class DicomStore(
	  /** Identifier. Resource name of the DICOM store, of the form `projects/{project_id}/locations/{location_id}/datasets/{dataset_id}/dicomStores/{dicom_store_id}`. */
		name: Option[String] = None,
	  /** Optional. Notification destination for new DICOM instances. Supplied by the client. */
		notificationConfig: Option[Schema.NotificationConfig] = None,
	  /** User-supplied key-value pairs used to organize DICOM stores. Label keys must be between 1 and 63 characters long, have a UTF-8 encoding of maximum 128 bytes, and must conform to the following PCRE regular expression: \p{Ll}\p{Lo}{0,62} Label values are optional, must be between 1 and 63 characters long, have a UTF-8 encoding of maximum 128 bytes, and must conform to the following PCRE regular expression: [\p{Ll}\p{Lo}\p{N}_-]{0,63} No more than 64 labels can be associated with a given store. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. A list of streaming configs used to configure the destination of streaming exports for every DICOM instance insertion in this DICOM store. After a new config is added to `stream_configs`, DICOM instance insertions are streamed to the new destination. When a config is removed from `stream_configs`, the server stops streaming to that destination. Each config must contain a unique destination. */
		streamConfigs: Option[List[Schema.GoogleCloudHealthcareV1DicomStreamConfig]] = None
	)
	
	case class NotificationConfig(
	  /** The [Pub/Sub](https://cloud.google.com/pubsub/docs/) topic that notifications of changes are published on. Supplied by the client. PubsubMessage.Data contains the resource name. PubsubMessage.MessageId is the ID of this message. It is guaranteed to be unique within the topic. PubsubMessage.PublishTime is the time at which the message was published. Notifications are only sent if the topic is non-empty. [Topic names](https://cloud.google.com/pubsub/docs/overview#names) must be scoped to a project. Cloud Healthcare API service account must have publisher permissions on the given Pub/Sub topic. Not having adequate permissions causes the calls that send notifications to fail. If a notification can't be published to Pub/Sub, errors are logged to Cloud Logging (see [Viewing error logs in Cloud Logging](https://cloud.google.com/healthcare/docs/how-tos/logging)). If the number of errors exceeds a certain rate, some aren't submitted. Note that not all operations trigger notifications, see [Configuring Pub/Sub notifications](https://cloud.google.com/healthcare/docs/how-tos/pubsub) for specific details. */
		pubsubTopic: Option[String] = None,
	  /** Indicates whether or not to send Pub/Sub notifications on bulk import. Only supported for DICOM imports. */
		sendForBulkImport: Option[Boolean] = None
	)
	
	case class GoogleCloudHealthcareV1DicomStreamConfig(
	  /** Results are appended to this table. The server creates a new table in the given BigQuery dataset if the specified table does not exist. To enable the Cloud Healthcare API to write to your BigQuery table, you must give the Cloud Healthcare API service account the bigquery.dataEditor role. The service account is: `service-{PROJECT_NUMBER}@gcp-sa-healthcare.iam.gserviceaccount.com`. The PROJECT_NUMBER identifies the project that the DICOM store resides in. To get the project number, go to the Cloud Console Dashboard. It is recommended to not have a custom schema in the destination table which could conflict with the schema created by the Cloud Healthcare API. Instance deletions are not applied to the destination table. The destination's table schema will be automatically updated in case a new instance's data is incompatible with the current schema. The schema should not be updated manually as this can cause incompatibilies that cannot be resolved automatically. One resolution in this case is to delete the incompatible table and let the server recreate one, though the newly created table only contains data after the table recreation. BigQuery imposes a 1 MB limit on streaming insert row size, therefore any instance that generates more than 1 MB of BigQuery data will not be streamed. If an instance cannot be streamed to BigQuery, errors will be logged to Cloud Logging (see [Viewing error logs in Cloud Logging](https://cloud.google.com/healthcare/docs/how-tos/logging)). */
		bigqueryDestination: Option[Schema.GoogleCloudHealthcareV1DicomBigQueryDestination] = None
	)
	
	object GoogleCloudHealthcareV1DicomBigQueryDestination {
		enum WriteDispositionEnum extends Enum[WriteDispositionEnum] { case WRITE_DISPOSITION_UNSPECIFIED, WRITE_EMPTY, WRITE_TRUNCATE, WRITE_APPEND }
	}
	case class GoogleCloudHealthcareV1DicomBigQueryDestination(
	  /** Optional. BigQuery URI to a table, up to 2000 characters long, in the format `bq://projectId.bqDatasetId.tableId` */
		tableUri: Option[String] = None,
	  /** Optional. Use `write_disposition` instead. If `write_disposition` is specified, this parameter is ignored. force=false is equivalent to write_disposition=WRITE_EMPTY and force=true is equivalent to write_disposition=WRITE_TRUNCATE. */
		force: Option[Boolean] = None,
	  /** Optional. Determines whether the existing table in the destination is to be overwritten or appended to. If a write_disposition is specified, the `force` parameter is ignored. */
		writeDisposition: Option[Schema.GoogleCloudHealthcareV1DicomBigQueryDestination.WriteDispositionEnum] = None
	)
	
	case class ListDicomStoresResponse(
	  /** The returned DICOM stores. Won't be more DICOM stores than the value of page_size in the request. */
		dicomStores: Option[List[Schema.DicomStore]] = None,
	  /** Token to retrieve the next page of results or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class ImportDicomDataRequest(
	  /** Cloud Storage source data location and import configuration. The Cloud Healthcare Service Agent requires the `roles/storage.objectViewer` Cloud IAM roles on the Cloud Storage location. */
		gcsSource: Option[Schema.GoogleCloudHealthcareV1DicomGcsSource] = None,
	  /** Optional. The blob storage settings for the data imported by this operation. */
		blobStorageSettings: Option[Schema.BlobStorageSettings] = None
	)
	
	case class GoogleCloudHealthcareV1DicomGcsSource(
	  /** Points to a Cloud Storage URI containing file(s) with content only. The URI must be in the following format: `gs://{bucket_id}/{object_id}`. The URI can include wildcards in `object_id` and thus identify multiple files. Supported wildcards: &#42; '&#42;' to match 0 or more non-separator characters &#42; '&#42;&#42;' to match 0 or more characters (including separators). Must be used at the end of a path and with no other wildcards in the path. Can also be used with a file extension (such as .dcm), which imports all files with the extension in the specified directory and its sub-directories. For example, `gs://my-bucket/my-directory/&#42;&#42;.dcm` imports all files with .dcm extensions in `my-directory/` and its sub-directories. &#42; '?' to match 1 character. All other URI formats are invalid. Files matching the wildcard are expected to contain content only, no metadata. */
		uri: Option[String] = None
	)
	
	case class ExportDicomDataRequest(
	  /** The Cloud Storage output destination. The Cloud Healthcare Service Agent requires the `roles/storage.objectAdmin` Cloud IAM roles on the Cloud Storage location. */
		gcsDestination: Option[Schema.GoogleCloudHealthcareV1DicomGcsDestination] = None,
	  /** The BigQuery output destination. You can only export to a BigQuery dataset that's in the same project as the DICOM store you're exporting from. The Cloud Healthcare Service Agent requires two IAM roles on the BigQuery location: `roles/bigquery.dataEditor` and `roles/bigquery.jobUser`. */
		bigqueryDestination: Option[Schema.GoogleCloudHealthcareV1DicomBigQueryDestination] = None
	)
	
	case class GoogleCloudHealthcareV1DicomGcsDestination(
	  /** The Cloud Storage destination to export to. URI for a Cloud Storage directory where the server writes the result files, in the format `gs://{bucket-id}/{path/to/destination/dir}`). If there is no trailing slash, the service appends one when composing the object path. The user is responsible for creating the Cloud Storage bucket referenced in `uri_prefix`. */
		uriPrefix: Option[String] = None,
	  /** MIME types supported by DICOM spec. Each file is written in the following format: `.../{study_id}/{series_id}/{instance_id}[/{frame_number}].{extension}` The frame_number component exists only for multi-frame instances. Supported MIME types are consistent with supported formats in DICOMweb: https://cloud.google.com/healthcare/docs/dicom#retrieve_transaction. Specifically, the following are supported: - application/dicom; transfer-syntax=1.2.840.10008.1.2.1 (uncompressed DICOM) - application/dicom; transfer-syntax=1.2.840.10008.1.2.4.50 (DICOM with embedded JPEG Baseline) - application/dicom; transfer-syntax=1.2.840.10008.1.2.4.90 (DICOM with embedded JPEG 2000 Lossless Only) - application/dicom; transfer-syntax=1.2.840.10008.1.2.4.91 (DICOM with embedded JPEG 2000) - application/dicom; transfer-syntax=&#42; (DICOM with no transcoding) - application/octet-stream; transfer-syntax=1.2.840.10008.1.2.1 (raw uncompressed PixelData) - application/octet-stream; transfer-syntax=&#42; (raw PixelData in whatever format it was uploaded in) - image/jpeg; transfer-syntax=1.2.840.10008.1.2.4.50 (Consumer JPEG) - image/png The following extensions are used for output files: - application/dicom -> .dcm - image/jpeg -> .jpg - image/png -> .png - application/octet-stream -> no extension If unspecified, the instances are exported in the original DICOM format they were uploaded in. */
		mimeType: Option[String] = None
	)
	
	case class DicomStoreMetrics(
	  /** Resource name of the DICOM store, of the form `projects/{project_id}/locations/{location_id}/datasets/{dataset_id}/dicomStores/{dicom_store_id}`. */
		name: Option[String] = None,
	  /** Number of studies in the store. */
		studyCount: Option[String] = None,
	  /** Number of series in the store. */
		seriesCount: Option[String] = None,
	  /** Number of instances in the store. */
		instanceCount: Option[String] = None,
	  /** Total structured storage bytes for all instances in the store. */
		structuredStorageSizeBytes: Option[String] = None,
	  /** Total blob storage bytes for all instances in the store. */
		blobStorageSizeBytes: Option[String] = None
	)
	
	case class HttpBody(
	  /** The HTTP Content-Type header value specifying the content type of the body. */
		contentType: Option[String] = None,
	  /** The HTTP request/response body as raw binary. */
		data: Option[String] = None,
	  /** Application specific response metadata. Must be set in the first response for streaming APIs. */
		extensions: Option[List[Map[String, JsValue]]] = None
	)
	
	case class StudyMetrics(
	  /** The study resource path. For example, `projects/{project_id}/locations/{location_id}/datasets/{dataset_id}/dicomStores/{dicom_store_id}/dicomWeb/studies/{study_uid}`. */
		study: Option[String] = None,
	  /** Total structured storage bytes for all instances in the study. */
		structuredStorageSizeBytes: Option[String] = None,
	  /** Total blob storage bytes for all instances in the study. */
		blobStorageSizeBytes: Option[String] = None,
	  /** Number of instances in the study. */
		instanceCount: Option[String] = None,
	  /** Number of series in the study. */
		seriesCount: Option[String] = None
	)
	
	case class SeriesMetrics(
	  /** The series resource path. For example, `projects/{project_id}/locations/{location_id}/datasets/{dataset_id}/dicomStores/{dicom_store_id}/dicomWeb/studies/{study_uid}/series/{series_uid}`. */
		series: Option[String] = None,
	  /** Total structured storage bytes for all instances in the series. */
		structuredStorageSizeBytes: Option[String] = None,
	  /** Total blob storage bytes for all instances in the series. */
		blobStorageSizeBytes: Option[String] = None,
	  /** Number of instances in the series. */
		instanceCount: Option[String] = None
	)
	
	case class Hl7V2Store(
	  /** Identifier. Resource name of the HL7v2 store, of the form `projects/{project_id}/locations/{location_id}/datasets/{dataset_id}/hl7V2Stores/{hl7v2_store_id}`. */
		name: Option[String] = None,
	  /** Optional. The configuration for the parser. It determines how the server parses the messages. */
		parserConfig: Option[Schema.ParserConfig] = None,
	  /** User-supplied key-value pairs used to organize HL7v2 stores. Label keys must be between 1 and 63 characters long, have a UTF-8 encoding of maximum 128 bytes, and must conform to the following PCRE regular expression: \p{Ll}\p{Lo}{0,62} Label values are optional, must be between 1 and 63 characters long, have a UTF-8 encoding of maximum 128 bytes, and must conform to the following PCRE regular expression: [\p{Ll}\p{Lo}\p{N}_-]{0,63} No more than 64 labels can be associated with a given store. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. A list of notification configs. Each configuration uses a filter to determine whether to publish a message (both Ingest & Create) on the corresponding notification destination. Only the message name is sent as part of the notification. Supplied by the client. */
		notificationConfigs: Option[List[Schema.Hl7V2NotificationConfig]] = None,
	  /** Optional. Determines whether to reject duplicate messages. A duplicate message is a message with the same raw bytes as a message that has already been ingested/created in this HL7v2 store. The default value is false, meaning that the store accepts the duplicate messages and it also returns the same ACK message in the IngestMessageResponse as has been returned previously. Note that only one resource is created in the store. When this field is set to true, CreateMessage/IngestMessage requests with a duplicate message will be rejected by the store, and IngestMessageErrorDetail returns a NACK message upon rejection. */
		rejectDuplicateMessage: Option[Boolean] = None
	)
	
	object ParserConfig {
		enum VersionEnum extends Enum[VersionEnum] { case PARSER_VERSION_UNSPECIFIED, V1, V2, V3 }
	}
	case class ParserConfig(
	  /** Optional. Determines whether messages with no header are allowed. */
		allowNullHeader: Option[Boolean] = None,
	  /** Optional. Byte(s) to use as the segment terminator. If this is unset, '\r' is used as segment terminator, matching the HL7 version 2 specification. */
		segmentTerminator: Option[String] = None,
	  /** Optional. Schemas used to parse messages in this store, if schematized parsing is desired. */
		schema: Option[Schema.SchemaPackage] = None,
	  /** Immutable. Determines the version of both the default parser to be used when `schema` is not given, as well as the schematized parser used when `schema` is specified. This field is immutable after HL7v2 store creation. */
		version: Option[Schema.ParserConfig.VersionEnum] = None
	)
	
	object SchemaPackage {
		enum SchematizedParsingTypeEnum extends Enum[SchematizedParsingTypeEnum] { case SCHEMATIZED_PARSING_TYPE_UNSPECIFIED, SOFT_FAIL, HARD_FAIL }
		enum UnexpectedSegmentHandlingEnum extends Enum[UnexpectedSegmentHandlingEnum] { case UNEXPECTED_SEGMENT_HANDLING_MODE_UNSPECIFIED, FAIL, SKIP, PARSE }
	}
	case class SchemaPackage(
	  /** Optional. Determines how messages that fail to parse are handled. */
		schematizedParsingType: Option[Schema.SchemaPackage.SchematizedParsingTypeEnum] = None,
	  /** Optional. Schema configs that are layered based on their VersionSources that match the incoming message. Schema configs present in higher indices override those in lower indices with the same message type and trigger event if their VersionSources all match an incoming message. */
		schemas: Option[List[Schema.Hl7SchemaConfig]] = None,
	  /** Optional. Schema type definitions that are layered based on their VersionSources that match the incoming message. Type definitions present in higher indices override those in lower indices with the same type name if their VersionSources all match an incoming message. */
		types: Option[List[Schema.Hl7TypesConfig]] = None,
	  /** Optional. Flag to ignore all min_occurs restrictions in the schema. This means that incoming messages can omit any group, segment, field, component, or subcomponent. */
		ignoreMinOccurs: Option[Boolean] = None,
	  /** Optional. Determines how unexpected segments (segments not matched to the schema) are handled. */
		unexpectedSegmentHandling: Option[Schema.SchemaPackage.UnexpectedSegmentHandlingEnum] = None
	)
	
	case class Hl7SchemaConfig(
	  /** Each VersionSource is tested and only if they all match is the schema used for the message. */
		version: Option[List[Schema.VersionSource]] = None,
	  /** Map from each HL7v2 message type and trigger event pair, such as ADT_A04, to its schema configuration root group. */
		messageSchemaConfigs: Option[Map[String, Schema.SchemaGroup]] = None
	)
	
	case class VersionSource(
	  /** The field to extract from the MSH segment. For example, "3.1" or "18[1].1". */
		mshField: Option[String] = None,
	  /** The value to match with the field. For example, "My Application Name" or "2.3". */
		value: Option[String] = None
	)
	
	case class SchemaGroup(
	  /** The name of this group. For example, "ORDER_DETAIL". */
		name: Option[String] = None,
	  /** True indicates that this is a choice group, meaning that only one of its segments can exist in a given message. */
		choice: Option[Boolean] = None,
	  /** The minimum number of times this group must be present/repeated. */
		minOccurs: Option[Int] = None,
	  /** The maximum number of times this group can be repeated. 0 or -1 means unbounded. */
		maxOccurs: Option[Int] = None,
	  /** Nested groups and/or segments. */
		members: Option[List[Schema.GroupOrSegment]] = None
	)
	
	case class GroupOrSegment(
		segment: Option[Schema.SchemaSegment] = None,
		group: Option[Schema.SchemaGroup] = None
	)
	
	case class SchemaSegment(
	  /** The Segment type. For example, "PID". */
		`type`: Option[String] = None,
	  /** The minimum number of times this segment can be present in this group. */
		minOccurs: Option[Int] = None,
	  /** The maximum number of times this segment can be present in this group. 0 or -1 means unbounded. */
		maxOccurs: Option[Int] = None
	)
	
	case class Hl7TypesConfig(
	  /** The version selectors that this config applies to. A message must match ALL version sources to apply. */
		version: Option[List[Schema.VersionSource]] = None,
	  /** The HL7v2 type definitions. */
		`type`: Option[List[Schema.Type]] = None
	)
	
	object Type {
		enum PrimitiveEnum extends Enum[PrimitiveEnum] { case PRIMITIVE_UNSPECIFIED, STRING, VARIES, UNESCAPED_STRING }
	}
	case class Type(
	  /** The name of this type. This would be the segment or datatype name. For example, "PID" or "XPN". */
		name: Option[String] = None,
	  /** If this is a primitive type then this field is the type of the primitive For example, STRING. Leave unspecified for composite types. */
		primitive: Option[Schema.Type.PrimitiveEnum] = None,
	  /** The (sub) fields this type has (if not primitive). */
		fields: Option[List[Schema.Field]] = None
	)
	
	case class Field(
	  /** The name of the field. For example, "PID-1" or just "1". */
		name: Option[String] = None,
	  /** The type of this field. A Type with this name must be defined in an Hl7TypesConfig. */
		`type`: Option[String] = None,
	  /** The HL7v2 table this field refers to. For example, PID-15 (Patient's Primary Language) usually refers to table "0296". */
		table: Option[String] = None,
	  /** The minimum number of times this field must be present/repeated. */
		minOccurs: Option[Int] = None,
	  /** The maximum number of times this field can be repeated. 0 or -1 means unbounded. */
		maxOccurs: Option[Int] = None
	)
	
	case class Hl7V2NotificationConfig(
	  /** The [Pub/Sub](https://cloud.google.com/pubsub/docs/) topic that notifications of changes are published on. Supplied by the client. The notification is a `PubsubMessage` with the following fields: &#42; `PubsubMessage.Data` contains the resource name. &#42; `PubsubMessage.MessageId` is the ID of this notification. It's guaranteed to be unique within the topic. &#42; `PubsubMessage.PublishTime` is the time when the message was published. Note that notifications are only sent if the topic is non-empty. [Topic names](https://cloud.google.com/pubsub/docs/overview#names) must be scoped to a project. The Cloud Healthcare API service account, service-PROJECT_NUMBER@gcp-sa-healthcare.iam.gserviceaccount.com, must have publisher permissions on the given Pub/Sub topic. Not having adequate permissions causes the calls that send notifications to fail. If a notification cannot be published to Pub/Sub, errors are logged to Cloud Logging. For more information, see [Viewing error logs in Cloud Logging](https://cloud.google.com/healthcare/docs/how-tos/logging)). */
		pubsubTopic: Option[String] = None,
	  /** Optional. Restricts notifications sent for messages matching a filter. If this is empty, all messages are matched. The following syntax is available: &#42; A string field value can be written as text inside quotation marks, for example `"query text"`. The only valid relational operation for text fields is equality (`=`), where text is searched within the field, rather than having the field be equal to the text. For example, `"Comment = great"` returns messages with `great` in the comment field. &#42; A number field value can be written as an integer, a decimal, or an exponential. The valid relational operators for number fields are the equality operator (`=`), along with the less than/greater than operators (`<`, `<=`, `>`, `>=`). Note that there is no inequality (`!=`) operator. You can prepend the `NOT` operator to an expression to negate it. &#42; A date field value must be written in `yyyy-mm-dd` form. Fields with date and time use the RFC3339 time format. Leading zeros are required for one-digit months and days. The valid relational operators for date fields are the equality operator (`=`) , along with the less than/greater than operators (`<`, `<=`, `>`, `>=`). Note that there is no inequality (`!=`) operator. You can prepend the `NOT` operator to an expression to negate it. &#42; Multiple field query expressions can be combined in one query by adding `AND` or `OR` operators between the expressions. If a boolean operator appears within a quoted string, it is not treated as special, it's just another part of the character string to be matched. You can prepend the `NOT` operator to an expression to negate it. The following fields and functions are available for filtering: &#42; `message_type`, from the MSH-9.1 field. For example, `NOT message_type = "ADT"`. &#42; `send_date` or `sendDate`, the YYYY-MM-DD date the message was sent in the dataset's time_zone, from the MSH-7 segment. For example, `send_date < "2017-01-02"`. &#42; `send_time`, the timestamp when the message was sent, using the RFC3339 time format for comparisons, from the MSH-7 segment. For example, `send_time < "2017-01-02T00:00:00-05:00"`. &#42; `create_time`, the timestamp when the message was created in the HL7v2 store. Use the RFC3339 time format for comparisons. For example, `create_time < "2017-01-02T00:00:00-05:00"`. &#42; `send_facility`, the care center that the message came from, from the MSH-4 segment. For example, `send_facility = "ABC"`. &#42; `PatientId(value, type)`, which matches if the message lists a patient having an ID of the given value and type in the PID-2, PID-3, or PID-4 segments. For example, `PatientId("123456", "MRN")`. &#42; `labels.x`, a string value of the label with key `x` as set using the Message.labels map. For example, `labels."priority"="high"`. The operator `:&#42;` can be used to assert the existence of a label. For example, `labels."priority":&#42;`. */
		filter: Option[String] = None
	)
	
	case class ListHl7V2StoresResponse(
	  /** The returned HL7v2 stores. Won't be more HL7v2 stores than the value of page_size in the request. */
		hl7V2Stores: Option[List[Schema.Hl7V2Store]] = None,
	  /** Token to retrieve the next page of results or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class IngestMessageRequest(
	  /** Required. HL7v2 message to ingest. */
		message: Option[Schema.Message] = None
	)
	
	case class Message(
	  /** Output only. Resource name of the Message, of the form `projects/{project_id}/locations/{location_id}/datasets/{dataset_id}/hl7V2Stores/{hl7_v2_store_id}/messages/{message_id}`. */
		name: Option[String] = None,
	  /** Required. Raw message bytes. */
		data: Option[String] = None,
	  /** Output only. The datetime when the message was created. Set by the server. */
		createTime: Option[String] = None,
	  /** Output only. The hospital that this message came from. MSH-4. */
		sendFacility: Option[String] = None,
	  /** Output only. The datetime the sending application sent this message. MSH-7. */
		sendTime: Option[String] = None,
	  /** Output only. The message type for this message. MSH-9.1. */
		messageType: Option[String] = None,
	  /** Output only. All patient IDs listed in the PID-2, PID-3, and PID-4 segments of this message. */
		patientIds: Option[List[Schema.PatientId]] = None,
	  /** User-supplied key-value pairs used to organize HL7v2 stores. Label keys must be between 1 and 63 characters long, have a UTF-8 encoding of maximum 128 bytes, and must conform to the following PCRE regular expression: \p{Ll}\p{Lo}{0,62} Label values are optional, must be between 1 and 63 characters long, have a UTF-8 encoding of maximum 128 bytes, and must conform to the following PCRE regular expression: [\p{Ll}\p{Lo}\p{N}_-]{0,63} No more than 64 labels can be associated with a given store. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. The parsed version of the raw message data. */
		parsedData: Option[Schema.ParsedData] = None,
	  /** Output only. The parsed version of the raw message data schematized according to this store's schemas and type definitions. */
		schematizedData: Option[Schema.SchematizedData] = None
	)
	
	case class PatientId(
	  /** The patient's unique identifier. */
		value: Option[String] = None,
	  /** ID type. For example, MRN or NHS. */
		`type`: Option[String] = None
	)
	
	case class ParsedData(
		segments: Option[List[Schema.Segment]] = None
	)
	
	case class Segment(
	  /** A string that indicates the type of segment. For example, EVN or PID. */
		segmentId: Option[String] = None,
	  /** Set ID for segments that can be in a set. This can be empty if it's missing or isn't applicable. */
		setId: Option[String] = None,
	  /** A mapping from the positional location to the value. The key string uses zero-based indexes separated by dots to identify Fields, components and sub-components. A bracket notation is also used to identify different instances of a repeated field. Regex for key: (\d+)(\[\d+\])?(.\d+)?(.\d+)? Examples of (key, value) pairs: &#42; (0.1, "hemoglobin") denotes that the first component of Field 0 has the value "hemoglobin". &#42; (1.1.2, "CBC") denotes that the second sub-component of the first component of Field 1 has the value "CBC". &#42; (1[0].1, "HbA1c") denotes that the first component of the first Instance of Field 1, which is repeated, has the value "HbA1c". */
		fields: Option[Map[String, String]] = None
	)
	
	case class SchematizedData(
	  /** JSON output of the parser. */
		data: Option[String] = None,
	  /** The error output of the parser. */
		error: Option[String] = None
	)
	
	case class IngestMessageResponse(
	  /** HL7v2 ACK message. */
		hl7Ack: Option[String] = None,
	  /** Created message resource. */
		message: Option[Schema.Message] = None
	)
	
	case class CreateMessageRequest(
	  /** Required. HL7v2 message. */
		message: Option[Schema.Message] = None
	)
	
	case class ListMessagesResponse(
	  /** The returned Messages. Won't be more Messages than the value of page_size in the request. See view for populated fields. */
		hl7V2Messages: Option[List[Schema.Message]] = None,
	  /** Token to retrieve the next page of results or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	case class ExportMessagesRequest(
	  /** The start of the range in `send_time` (MSH.7, https://www.hl7.org/documentcenter/public_temp_2E58C1F9-1C23-BA17-0C6126475344DA9D/wg/conf/HL7MSH.htm) to process. If not specified, the UNIX epoch (1970-01-01T00:00:00Z) is used. This value has to come before the `end_time` defined below. Only messages whose `send_time` lies in the range `start_time` (inclusive) to `end_time` (exclusive) are exported. */
		startTime: Option[String] = None,
	  /** The end of the range in `send_time` (MSH.7, https://www.hl7.org/documentcenter/public_temp_2E58C1F9-1C23-BA17-0C6126475344DA9D/wg/conf/HL7MSH.htm) to process. If not specified, the time when the export is scheduled is used. This value has to come after the `start_time` defined below. Only messages whose `send_time` lies in the range `start_time` (inclusive) to `end_time` (exclusive) are exported. */
		endTime: Option[String] = None,
	  /** Restricts messages exported to those matching a filter, only applicable to PubsubDestination and GcsDestination. The following syntax is available: &#42; A string field value can be written as text inside quotation marks, for example `"query text"`. The only valid relational operation for text fields is equality (`=`), where text is searched within the field, rather than having the field be equal to the text. For example, `"Comment = great"` returns messages with `great` in the comment field. &#42; A number field value can be written as an integer, a decimal, or an exponential. The valid relational operators for number fields are the equality operator (`=`), along with the less than/greater than operators (`<`, `<=`, `>`, `>=`). Note that there is no inequality (`!=`) operator. You can prepend the `NOT` operator to an expression to negate it. &#42; A date field value must be written in the `yyyy-mm-dd` format. Fields with date and time use the RFC3339 time format. Leading zeros are required for one-digit months and days. The valid relational operators for date fields are the equality operator (`=`) , along with the less than/greater than operators (`<`, `<=`, `>`, `>=`). Note that there is no inequality (`!=`) operator. You can prepend the `NOT` operator to an expression to negate it. &#42; Multiple field query expressions can be combined in one query by adding `AND` or `OR` operators between the expressions. If a boolean operator appears within a quoted string, it is not treated as special, and is just another part of the character string to be matched. You can prepend the `NOT` operator to an expression to negate it. The following fields and functions are available for filtering: &#42; `message_type`, from the MSH-9.1 field. For example, `NOT message_type = "ADT"`. &#42; `send_date` or `sendDate`, the `yyyy-mm-dd` date the message was sent in the dataset's time_zone, from the MSH-7 segment. For example, `send_date < "2017-01-02"`. &#42; `send_time`, the timestamp when the message was sent, using the RFC3339 time format for comparisons, from the MSH-7 segment. For example, `send_time < "2017-01-02T00:00:00-05:00"`. &#42; `create_time`, the timestamp when the message was created in the HL7v2 store. Use the RFC3339 time format for comparisons. For example, `create_time < "2017-01-02T00:00:00-05:00"`. &#42; `send_facility`, the care center that the message came from, from the MSH-4 segment. For example, `send_facility = "ABC"`. Note: The filter will be applied to every message in the HL7v2 store whose `send_time` lies in the range defined by the `start_time` and the `end_time`. Even if the filter only matches a small set of messages, the export operation can still take a long time to finish when a lot of messages are between the specified `start_time` and `end_time` range. */
		filter: Option[String] = None,
	  /** Export to a Cloud Storage destination. */
		gcsDestination: Option[Schema.GcsDestination] = None,
	  /** Export messages to a Pub/Sub topic. */
		pubsubDestination: Option[Schema.PubsubDestination] = None
	)
	
	object GcsDestination {
		enum MessageViewEnum extends Enum[MessageViewEnum] { case MESSAGE_VIEW_UNSPECIFIED, RAW_ONLY, PARSED_ONLY, FULL, SCHEMATIZED_ONLY, BASIC }
		enum ContentStructureEnum extends Enum[ContentStructureEnum] { case CONTENT_STRUCTURE_UNSPECIFIED, MESSAGE_JSON }
	}
	case class GcsDestination(
	  /** URI of an existing Cloud Storage directory where the server writes result files, in the format `gs://{bucket-id}/{path/to/destination/dir}`. If there is no trailing slash, the service appends one when composing the object path. */
		uriPrefix: Option[String] = None,
	  /** Specifies the parts of the Message resource to include in the export. If not specified, FULL is used. */
		messageView: Option[Schema.GcsDestination.MessageViewEnum] = None,
	  /** The format of the exported HL7v2 message files. */
		contentStructure: Option[Schema.GcsDestination.ContentStructureEnum] = None
	)
	
	case class PubsubDestination(
	  /** The [Pub/Sub](https://cloud.google.com/pubsub/docs/) topic that Pub/Sub messages are published on. Supplied by the client. The `PubsubMessage` contains the following fields: &#42; `PubsubMessage.Data` contains the resource name. &#42; `PubsubMessage.MessageId` is the ID of this notification. It is guaranteed to be unique within the topic. &#42; `PubsubMessage.PublishTime` is the time when the message was published. [Topic names](https://cloud.google.com/pubsub/docs/overview#names) must be scoped to a project. The Cloud Healthcare API service account, service-PROJECT_NUMBER@gcp-sa-healthcare.iam.gserviceaccount.com, must have publisher permissions on the given Pub/Sub topic. Not having adequate permissions causes the calls that send notifications to fail. */
		pubsubTopic: Option[String] = None
	)
	
	case class ImportMessagesRequest(
	  /** Cloud Storage source data location and import configuration. The Cloud Healthcare Service Agent requires the `roles/storage.objectViewer` Cloud IAM roles on the Cloud Storage location. */
		gcsSource: Option[Schema.GcsSource] = None
	)
	
	case class GcsSource(
	  /** Points to a Cloud Storage URI containing file(s) to import. The URI must be in the following format: `gs://{bucket_id}/{object_id}`. The URI can include wildcards in `object_id` and thus identify multiple files. Supported wildcards: &#42; `&#42;` to match 0 or more non-separator characters &#42; `&#42;&#42;` to match 0 or more characters (including separators). Must be used at the end of a path and with no other wildcards in the path. Can also be used with a file extension (such as .ndjson), which imports all files with the extension in the specified directory and its sub-directories. For example, `gs://my-bucket/my-directory/&#42;&#42;.ndjson` imports all files with `.ndjson` extensions in `my-directory/` and its sub-directories. &#42; `?` to match 1 character Files matching the wildcard are expected to contain content only, no metadata. */
		uri: Option[String] = None
	)
	
	case class Hl7V2StoreMetrics(
	  /** The resource name of the HL7v2 store to get metrics for, in the format `projects/{project_id}/datasets/{dataset_id}/hl7V2Stores/{hl7v2_store_id}`. */
		name: Option[String] = None,
	  /** List of HL7v2 store metrics by message type. */
		metrics: Option[List[Schema.Hl7V2StoreMetric]] = None
	)
	
	case class Hl7V2StoreMetric(
	  /** The Hl7v2 message type this metric applies to, such as `ADT` or `ORU`. */
		messageType: Option[String] = None,
	  /** The total count of HL7v2 messages in the store for the given message type. */
		count: Option[String] = None,
	  /** The total amount of structured storage used by HL7v2 messages of this message type in the store. */
		structuredStorageSizeBytes: Option[String] = None
	)
	
	object RollbackHl7V2MessagesRequest {
		enum ChangeTypeEnum extends Enum[ChangeTypeEnum] { case CHANGE_TYPE_UNSPECIFIED, ALL, CREATE, UPDATE, DELETE }
	}
	case class RollbackHl7V2MessagesRequest(
	  /** Required. Times point to rollback to. */
		rollbackTime: Option[String] = None,
	  /** Optional. When enabled, changes will be reverted without explicit confirmation. */
		force: Option[Boolean] = None,
	  /** Optional. CREATE/UPDATE/DELETE/ALL for reverting all txns of a certain type. */
		changeType: Option[Schema.RollbackHl7V2MessagesRequest.ChangeTypeEnum] = None,
	  /** Required. Bucket to deposit result */
		resultGcsBucket: Option[String] = None,
	  /** Optional. Cloud storage object containing list of {resourceId} lines, identifying resources to be reverted */
		inputGcsObject: Option[String] = None,
	  /** Optional. Specifies whether to exclude earlier rollbacks. */
		excludeRollbacks: Option[Boolean] = None,
	  /** Optional. Parameters for filtering. */
		filteringFields: Option[Schema.RollbackHL7MessagesFilteringFields] = None
	)
	
	case class RollbackHL7MessagesFilteringFields(
	  /** Optional. A list of operation IDs to roll back. */
		operationIds: Option[List[String]] = None
	)
	
	case class SearchResourcesRequest(
	  /** Required. The FHIR resource type to search, such as Patient or Observation. For a complete list, see the FHIR Resource Index ([DSTU2](http://hl7.org/implement/standards/fhir/DSTU2/resourcelist.html), [STU3](http://hl7.org/implement/standards/fhir/STU3/resourcelist.html), [R4](http://hl7.org/implement/standards/fhir/R4/resourcelist.html)). */
		resourceType: Option[String] = None
	)
	
	object FhirStore {
		enum VersionEnum extends Enum[VersionEnum] { case VERSION_UNSPECIFIED, DSTU2, STU3, R4 }
		enum ComplexDataTypeReferenceParsingEnum extends Enum[ComplexDataTypeReferenceParsingEnum] { case COMPLEX_DATA_TYPE_REFERENCE_PARSING_UNSPECIFIED, DISABLED, ENABLED }
	}
	case class FhirStore(
	  /** Output only. Identifier. Resource name of the FHIR store, of the form `projects/{project_id}/locations/{location}/datasets/{dataset_id}/fhirStores/{fhir_store_id}`. */
		name: Option[String] = None,
	  /** Optional. Whether this FHIR store has the [updateCreate capability](https://www.hl7.org/fhir/capabilitystatement-definitions.html#CapabilityStatement.rest.resource.updateCreate). This determines if the client can use an Update operation to create a new resource with a client-specified ID. If false, all IDs are server-assigned through the Create operation and attempts to update a non-existent resource return errors. It is strongly advised not to include or encode any sensitive data such as patient identifiers in client-specified resource IDs. Those IDs are part of the FHIR resource path recorded in Cloud audit logs and Pub/Sub notifications. Those IDs can also be contained in reference fields within other resources. Defaults to false. */
		enableUpdateCreate: Option[Boolean] = None,
	  /** Deprecated. Use `notification_configs` instead. If non-empty, publish all resource modifications of this FHIR store to this destination. The Pub/Sub message attributes contain a map with a string describing the action that has triggered the notification. For example, "action":"CreateResource". */
		notificationConfig: Option[Schema.NotificationConfig] = None,
	  /** Immutable. Whether to disable referential integrity in this FHIR store. This field is immutable after FHIR store creation. The default value is false, meaning that the API enforces referential integrity and fails the requests that result in inconsistent state in the FHIR store. When this field is set to true, the API skips referential integrity checks. Consequently, operations that rely on references, such as GetPatientEverything, do not return all the results if broken references exist. */
		disableReferentialIntegrity: Option[Boolean] = None,
	  /** Immutable. Whether to disable resource versioning for this FHIR store. This field can not be changed after the creation of FHIR store. If set to false, all write operations cause historical versions to be recorded automatically. The historical versions can be fetched through the history APIs, but cannot be updated. If set to true, no historical versions are kept. The server sends errors for attempts to read the historical versions. Defaults to false. */
		disableResourceVersioning: Option[Boolean] = None,
	  /** User-supplied key-value pairs used to organize FHIR stores. Label keys must be between 1 and 63 characters long, have a UTF-8 encoding of maximum 128 bytes, and must conform to the following PCRE regular expression: \p{Ll}\p{Lo}{0,62} Label values are optional, must be between 1 and 63 characters long, have a UTF-8 encoding of maximum 128 bytes, and must conform to the following PCRE regular expression: [\p{Ll}\p{Lo}\p{N}_-]{0,63} No more than 64 labels can be associated with a given store. */
		labels: Option[Map[String, String]] = None,
	  /** Required. Immutable. The FHIR specification version that this FHIR store supports natively. This field is immutable after store creation. Requests are rejected if they contain FHIR resources of a different version. Version is required for every FHIR store. */
		version: Option[Schema.FhirStore.VersionEnum] = None,
	  /** Optional. A list of streaming configs that configure the destinations of streaming export for every resource mutation in this FHIR store. Each store is allowed to have up to 10 streaming configs. After a new config is added, the next resource mutation is streamed to the new location in addition to the existing ones. When a location is removed from the list, the server stops streaming to that location. Before adding a new config, you must add the required [`bigquery.dataEditor`](https://cloud.google.com/bigquery/docs/access-control#bigquery.dataEditor) role to your project's &#42;&#42;Cloud Healthcare Service Agent&#42;&#42; [service account](https://cloud.google.com/iam/docs/service-accounts). Some lag (typically on the order of dozens of seconds) is expected before the results show up in the streaming destination. */
		streamConfigs: Option[List[Schema.StreamConfig]] = None,
	  /** Optional. Configuration for how to validate incoming FHIR resources against configured profiles. */
		validationConfig: Option[Schema.ValidationConfig] = None,
	  /** Optional. If true, overrides the default search behavior for this FHIR store to `handling=strict` which returns an error for unrecognized search parameters. If false, uses the FHIR specification default `handling=lenient` which ignores unrecognized search parameters. The handling can always be changed from the default on an individual API call by setting the HTTP header `Prefer: handling=strict` or `Prefer: handling=lenient`. Defaults to false. */
		defaultSearchHandlingStrict: Option[Boolean] = None,
	  /** Optional. Enable parsing of references within complex FHIR data types such as Extensions. If this value is set to ENABLED, then features like referential integrity and Bundle reference rewriting apply to all references. If this flag has not been specified the behavior of the FHIR store will not change, references in complex data types will not be parsed. New stores will have this value set to ENABLED after a notification period. Warning: turning on this flag causes processing existing resources to fail if they contain references to non-existent resources. */
		complexDataTypeReferenceParsing: Option[Schema.FhirStore.ComplexDataTypeReferenceParsingEnum] = None,
	  /** Optional. Specifies where and whether to send notifications upon changes to a FHIR store. */
		notificationConfigs: Option[List[Schema.FhirNotificationConfig]] = None
	)
	
	case class StreamConfig(
	  /** Optional. Supply a FHIR resource type (such as "Patient" or "Observation"). See https://www.hl7.org/fhir/valueset-resource-types.html for a list of all FHIR resource types. The server treats an empty list as an intent to stream all the supported resource types in this FHIR store. */
		resourceTypes: Option[List[String]] = None,
	  /** Optional. The destination BigQuery structure that contains both the dataset location and corresponding schema config. The output is organized in one table per resource type. The server reuses the existing tables (if any) that are named after the resource types. For example, "Patient", "Observation". When there is no existing table for a given resource type, the server attempts to create one. When a table schema doesn't align with the schema config, either because of existing incompatible schema or out of band incompatible modification, the server does not stream in new data. BigQuery imposes a 1 MB limit on streaming insert row size, therefore any resource mutation that generates more than 1 MB of BigQuery data is not streamed. One resolution in this case is to delete the incompatible table and let the server recreate one, though the newly created table only contains data after the table recreation. Results are written to BigQuery tables according to the parameters in BigQueryDestination.WriteDisposition. Different versions of the same resource are distinguishable by the meta.versionId and meta.lastUpdated columns. The operation (CREATE/UPDATE/DELETE) that results in the new version is recorded in the meta.tag. The tables contain all historical resource versions since streaming was enabled. For query convenience, the server also creates one view per table of the same name containing only the current resource version. The streamed data in the BigQuery dataset is not guaranteed to be completely unique. The combination of the id and meta.versionId columns should ideally identify a single unique row. But in rare cases, duplicates may exist. At query time, users may use the SQL select statement to keep only one of the duplicate rows given an id and meta.versionId pair. Alternatively, the server created view mentioned above also filters out duplicates. If a resource mutation cannot be streamed to BigQuery, errors are logged to Cloud Logging. For more information, see [Viewing error logs in Cloud Logging](https://cloud.google.com/healthcare/docs/how-tos/logging)). */
		bigqueryDestination: Option[Schema.GoogleCloudHealthcareV1FhirBigQueryDestination] = None,
	  /** The destination FHIR store for de-identified resources. After this field is added, all subsequent creates/updates/patches to the source store will be de-identified using the provided configuration and applied to the destination store. Resources deleted from the source store will be deleted from the destination store. Importing resources to the source store will not trigger the streaming. If the source store already contains resources when this option is enabled, those resources will not be copied to the destination store unless they are subsequently updated. This may result in invalid references in the destination store. Before adding this config, you must grant the healthcare.fhirResources.update permission on the destination store to your project's &#42;&#42;Cloud Healthcare Service Agent&#42;&#42; [service account](https://cloud.google.com/healthcare/docs/how-tos/permissions-healthcare-api-gcp-products#the_cloud_healthcare_service_agent). The destination store must set enable_update_create to true. The destination store must have disable_referential_integrity set to true. If a resource cannot be de-identified, errors will be logged to Cloud Logging (see [Viewing error logs in Cloud Logging](https://cloud.google.com/healthcare/docs/how-tos/logging)). */
		deidentifiedStoreDestination: Option[Schema.DeidentifiedStoreDestination] = None
	)
	
	object GoogleCloudHealthcareV1FhirBigQueryDestination {
		enum WriteDispositionEnum extends Enum[WriteDispositionEnum] { case WRITE_DISPOSITION_UNSPECIFIED, WRITE_EMPTY, WRITE_TRUNCATE, WRITE_APPEND }
	}
	case class GoogleCloudHealthcareV1FhirBigQueryDestination(
	  /** Optional. BigQuery URI to an existing dataset, up to 2000 characters long, in the format `bq://projectId.bqDatasetId`. */
		datasetUri: Option[String] = None,
	  /** Optional. The configuration for the exported BigQuery schema. */
		schemaConfig: Option[Schema.SchemaConfig] = None,
	  /** Optional. The default value is false. If this flag is `TRUE`, all tables are deleted from the dataset before the new exported tables are written. If the flag is not set and the destination dataset contains tables, the export call returns an error. If `write_disposition` is specified, this parameter is ignored. force=false is equivalent to write_disposition=WRITE_EMPTY and force=true is equivalent to write_disposition=WRITE_TRUNCATE. */
		force: Option[Boolean] = None,
	  /** Optional. Determines if existing data in the destination dataset is overwritten, appended to, or not written if the tables contain data. If a write_disposition is specified, the `force` parameter is ignored. */
		writeDisposition: Option[Schema.GoogleCloudHealthcareV1FhirBigQueryDestination.WriteDispositionEnum] = None
	)
	
	object SchemaConfig {
		enum SchemaTypeEnum extends Enum[SchemaTypeEnum] { case SCHEMA_TYPE_UNSPECIFIED, ANALYTICS, ANALYTICS_V2 }
	}
	case class SchemaConfig(
	  /** Specifies the output schema type. Schema type is required. */
		schemaType: Option[Schema.SchemaConfig.SchemaTypeEnum] = None,
	  /** The depth for all recursive structures in the output analytics schema. For example, `concept` in the CodeSystem resource is a recursive structure; when the depth is 2, the CodeSystem table will have a column called `concept.concept` but not `concept.concept.concept`. If not specified or set to 0, the server will use the default value 2. The maximum depth allowed is 5. */
		recursiveStructureDepth: Option[String] = None,
	  /** The configuration for exported BigQuery tables to be partitioned by FHIR resource's last updated time column. */
		lastUpdatedPartitionConfig: Option[Schema.TimePartitioning] = None
	)
	
	object TimePartitioning {
		enum TypeEnum extends Enum[TypeEnum] { case PARTITION_TYPE_UNSPECIFIED, HOUR, DAY, MONTH, YEAR }
	}
	case class TimePartitioning(
	  /** Type of partitioning. */
		`type`: Option[Schema.TimePartitioning.TypeEnum] = None,
	  /** Number of milliseconds for which to keep the storage for a partition. */
		expirationMs: Option[String] = None
	)
	
	case class DeidentifiedStoreDestination(
	  /** Optional. The full resource name of a Cloud Healthcare FHIR store, for example, `projects/{project_id}/locations/{location_id}/datasets/{dataset_id}/fhirStores/{fhir_store_id}`. */
		store: Option[String] = None,
	  /** Optional. The configuration to use when de-identifying resources that are added to this store. */
		config: Option[Schema.DeidentifyConfig] = None
	)
	
	case class ValidationConfig(
	  /** Optional. Whether to disable profile validation for this FHIR store. The default value is false. Set this to true to disable checking incoming resources for conformance against structure definitions in this FHIR store. */
		disableProfileValidation: Option[Boolean] = None,
	  /** Optional. A list of implementation guide URLs in this FHIR store that are used to configure the profiles to use for validation. For example, to use the US Core profiles for validation, set `enabled_implementation_guides` to `["http://hl7.org/fhir/us/core/ImplementationGuide/ig"]`. If `enabled_implementation_guides` is empty or omitted, then incoming resources are only required to conform to the base FHIR profiles. Otherwise, a resource must conform to at least one profile listed in the `global` property of one of the enabled ImplementationGuides. The Cloud Healthcare API does not currently enforce all of the rules in a StructureDefinition. The following rules are supported: - min/max - minValue/maxValue - maxLength - type - fixed[x] - pattern[x] on simple types - slicing, when using "value" as the discriminator type When a URL cannot be resolved (for example, in a type assertion), the server does not return an error. */
		enabledImplementationGuides: Option[List[String]] = None,
	  /** Optional. Whether to disable required fields validation for incoming resources. The default value is false. Set this to true to disable checking incoming resources for conformance against required fields requirement defined in the FHIR specification. This property only affects resource types that do not have profiles configured for them, any rules in enabled implementation guides will still be enforced. */
		disableRequiredFieldValidation: Option[Boolean] = None,
	  /** Optional. Whether to disable reference type validation for incoming resources. The default value is false. Set this to true to disable checking incoming resources for conformance against reference type requirement defined in the FHIR specification. This property only affects resource types that do not have profiles configured for them, any rules in enabled implementation guides will still be enforced. */
		disableReferenceTypeValidation: Option[Boolean] = None,
	  /** Optional. Whether to disable FHIRPath validation for incoming resources. The default value is false. Set this to true to disable checking incoming resources for conformance against FHIRPath requirement defined in the FHIR specification. This property only affects resource types that do not have profiles configured for them, any rules in enabled implementation guides will still be enforced. */
		disableFhirpathValidation: Option[Boolean] = None
	)
	
	case class FhirNotificationConfig(
	  /** Optional. The [Pub/Sub](https://cloud.google.com/pubsub/docs/) topic that notifications of changes are published on. Supplied by the client. The notification is a `PubsubMessage` with the following fields: &#42; `PubsubMessage.Data` contains the resource name. &#42; `PubsubMessage.MessageId` is the ID of this notification. It is guaranteed to be unique within the topic. &#42; `PubsubMessage.PublishTime` is the time when the message was published. Note that notifications are only sent if the topic is non-empty. [Topic names](https://cloud.google.com/pubsub/docs/overview#names) must be scoped to a project. The Cloud Healthcare API service account, service-@gcp-sa-healthcare.iam.gserviceaccount.com, must have publisher permissions on the given Pub/Sub topic. Not having adequate permissions causes the calls that send notifications to fail (https://cloud.google.com/healthcare-api/docs/permissions-healthcare-api-gcp-products#dicom_fhir_and_hl7v2_store_cloud_pubsub_permissions). If a notification can't be published to Pub/Sub, errors are logged to Cloud Logging. For more information, see [Viewing error logs in Cloud Logging](https://cloud.google.com/healthcare-api/docs/how-tos/logging). */
		pubsubTopic: Option[String] = None,
	  /** Optional. Whether to send full FHIR resource to this Pub/Sub topic. The default value is false. */
		sendFullResource: Option[Boolean] = None,
	  /** Optional. Whether to send full FHIR resource to this Pub/Sub topic for deleting FHIR resource. The default value is false. Note that setting this to true does not guarantee that all previous resources will be sent in the format of full FHIR resource. When a resource change is too large or during heavy traffic, only the resource name will be sent. Clients should always check the "payloadType" label from a Pub/Sub message to determine whether it needs to fetch the full previous resource as a separate operation. */
		sendPreviousResourceOnDelete: Option[Boolean] = None
	)
	
	case class ListFhirStoresResponse(
	  /** The returned FHIR stores. Won't be more FHIR stores than the value of page_size in the request. */
		fhirStores: Option[List[Schema.FhirStore]] = None,
	  /** Token to retrieve the next page of results or empty if there are no more results in the list. */
		nextPageToken: Option[String] = None
	)
	
	object ImportResourcesRequest {
		enum ContentStructureEnum extends Enum[ContentStructureEnum] { case CONTENT_STRUCTURE_UNSPECIFIED, BUNDLE, RESOURCE, BUNDLE_PRETTY, RESOURCE_PRETTY }
	}
	case class ImportResourcesRequest(
	  /** The content structure in the source location. If not specified, the server treats the input source files as BUNDLE. */
		contentStructure: Option[Schema.ImportResourcesRequest.ContentStructureEnum] = None,
	  /** Cloud Storage source data location and import configuration. The Healthcare Service Agent account requires the `roles/storage.objectAdmin` role on the Cloud Storage location. Each Cloud Storage object should be a text file that contains the format specified in ContentStructure. */
		gcsSource: Option[Schema.GoogleCloudHealthcareV1FhirGcsSource] = None
	)
	
	case class GoogleCloudHealthcareV1FhirGcsSource(
	  /** Points to a Cloud Storage URI containing file(s) to import. The URI must be in the following format: `gs://{bucket_id}/{object_id}`. The URI can include wildcards in `object_id` and thus identify multiple files. Supported wildcards: &#42; `&#42;` to match 0 or more non-separator characters &#42; `&#42;&#42;` to match 0 or more characters (including separators). Must be used at the end of a path and with no other wildcards in the path. Can also be used with a file extension (such as .ndjson), which imports all files with the extension in the specified directory and its sub-directories. For example, `gs://my-bucket/my-directory/&#42;&#42;.ndjson` imports all files with `.ndjson` extensions in `my-directory/` and its sub-directories. &#42; `?` to match 1 character Files matching the wildcard are expected to contain content only, no metadata. */
		uri: Option[String] = None
	)
	
	case class ExportResourcesRequest(
	  /** The Cloud Storage output destination. The Healthcare Service Agent account requires the `roles/storage.objectAdmin` role on the Cloud Storage location. The exported outputs are organized by FHIR resource types. The server creates one object per resource type. Each object contains newline delimited JSON, and each line is a FHIR resource. */
		gcsDestination: Option[Schema.GoogleCloudHealthcareV1FhirGcsDestination] = None,
	  /** The BigQuery output destination. The Cloud Healthcare Service Agent requires two IAM roles on the BigQuery location: `roles/bigquery.dataEditor` and `roles/bigquery.jobUser`. The output is one BigQuery table per resource type. Unlike when setting `BigQueryDestination` for `StreamConfig`, `ExportResources` does not create BigQuery views. */
		bigqueryDestination: Option[Schema.GoogleCloudHealthcareV1FhirBigQueryDestination] = None,
	  /** If provided, only resources updated after this time are exported. The time uses the format YYYY-MM-DDThh:mm:ss.sss+zz:zz. For example, `2015-02-07T13:28:17.239+02:00` or `2017-01-01T00:00:00Z`. The time must be specified to the second and include a time zone. */
		_since: Option[String] = None,
	  /** String of comma-delimited FHIR resource types. If provided, only resources of the specified resource type(s) are exported. */
		_type: Option[String] = None
	)
	
	case class GoogleCloudHealthcareV1FhirGcsDestination(
	  /** URI for a Cloud Storage directory where result files should be written, in the format of `gs://{bucket-id}/{path/to/destination/dir}`. If there is no trailing slash, the service appends one when composing the object path. The user is responsible for creating the Cloud Storage bucket referenced in `uri_prefix`. */
		uriPrefix: Option[String] = None
	)
	
	case class FhirStoreMetrics(
	  /** The resource name of the FHIR store to get metrics for, in the format `projects/{project_id}/datasets/{dataset_id}/fhirStores/{fhir_store_id}`. */
		name: Option[String] = None,
	  /** List of FhirStoreMetric by resource type. */
		metrics: Option[List[Schema.FhirStoreMetric]] = None
	)
	
	case class FhirStoreMetric(
	  /** The FHIR resource type this metric applies to. */
		resourceType: Option[String] = None,
	  /** The total count of FHIR resources in the store of this resource type. */
		count: Option[String] = None,
	  /** The total amount of structured storage used by FHIR resources of this resource type in the store. */
		structuredStorageSizeBytes: Option[String] = None
	)
	
	object RollbackFhirResourcesRequest {
		enum ChangeTypeEnum extends Enum[ChangeTypeEnum] { case CHANGE_TYPE_UNSPECIFIED, ALL, CREATE, UPDATE, DELETE }
	}
	case class RollbackFhirResourcesRequest(
	  /** Optional. If specified, revert only resources of these types */
		`type`: Option[List[String]] = None,
	  /** Required. Time point to rollback to. */
		rollbackTime: Option[String] = None,
	  /** Optional. Parameters for filtering resources */
		filteringFields: Option[Schema.RollbackFhirResourceFilteringFields] = None,
	  /** Optional. When enabled, changes will be reverted without explicit confirmation */
		force: Option[Boolean] = None,
	  /** Optional. CREATE/UPDATE/DELETE/ALL for reverting all txns of a certain type. */
		changeType: Option[Schema.RollbackFhirResourcesRequest.ChangeTypeEnum] = None,
	  /** Required. Bucket to deposit result */
		resultGcsBucket: Option[String] = None,
	  /** Optional. Cloud Storage object containing list of {resourceType}/{resourceId} lines, identifying resources to be reverted */
		inputGcsObject: Option[String] = None,
	  /** Optional. Specifies whether to exclude earlier rollbacks. */
		excludeRollbacks: Option[Boolean] = None
	)
	
	case class RollbackFhirResourceFilteringFields(
	  /** Optional. A list of operation IDs to roll back. */
		operationIds: Option[List[String]] = None,
	  /** Optional. A filter expression that matches data in the `Resource.meta` element. Supports all filters in [AIP-160](https://google.aip.dev/160) except the "has" (`:`) operator. Supports the following custom functions: &#42; `tag("") = ""` for tag filtering. &#42; `extension_value_ts("") = ` for filtering extensions with a timestamp, where `` is a Unix timestamp. Supports the `>`, `<`, `<=`, `>=`, and `!=` comparison operators. */
		metadataFilter: Option[String] = None
	)
	
	object AnalyzeEntitiesRequest {
		enum LicensedVocabulariesEnum extends Enum[LicensedVocabulariesEnum] { case LICENSED_VOCABULARY_UNSPECIFIED, ICD10CM, SNOMEDCT_US }
		enum AlternativeOutputFormatEnum extends Enum[AlternativeOutputFormatEnum] { case ALTERNATIVE_OUTPUT_FORMAT_UNSPECIFIED, FHIR_BUNDLE }
	}
	case class AnalyzeEntitiesRequest(
	  /** document_content is a document to be annotated. */
		documentContent: Option[String] = None,
	  /** A list of licensed vocabularies to use in the request, in addition to the default unlicensed vocabularies. */
		licensedVocabularies: Option[List[Schema.AnalyzeEntitiesRequest.LicensedVocabulariesEnum]] = None,
	  /** Optional. Alternative output format to be generated based on the results of analysis. */
		alternativeOutputFormat: Option[Schema.AnalyzeEntitiesRequest.AlternativeOutputFormatEnum] = None
	)
	
	case class AnalyzeEntitiesResponse(
	  /** The `entity_mentions` field contains all the annotated medical entities that were mentioned in the provided document. */
		entityMentions: Option[List[Schema.EntityMention]] = None,
	  /** The union of all the candidate entities that the entity_mentions in this response could link to. These are UMLS concepts or normalized mention content. */
		entities: Option[List[Schema.Entity]] = None,
	  /** relationships contains all the binary relationships that were identified between entity mentions within the provided document. */
		relationships: Option[List[Schema.EntityMentionRelationship]] = None,
	  /** The FHIR bundle ([`R4`](http://hl7.org/fhir/R4/bundle.html)) that includes all the entities, the entity mentions, and the relationships in JSON format. */
		fhirBundle: Option[String] = None
	)
	
	case class EntityMention(
	  /** mention_id uniquely identifies each entity mention in a single response. */
		mentionId: Option[String] = None,
	  /** The semantic type of the entity: UNKNOWN_ENTITY_TYPE, ALONE, ANATOMICAL_STRUCTURE, ASSISTED_LIVING, BF_RESULT, BM_RESULT, BM_UNIT, BM_VALUE, BODY_FUNCTION, BODY_MEASUREMENT, COMPLIANT, DOESNOT_FOLLOWUP, FAMILY, FOLLOWSUP, LABORATORY_DATA, LAB_RESULT, LAB_UNIT, LAB_VALUE, MEDICAL_DEVICE, MEDICINE, MED_DOSE, MED_DURATION, MED_FORM, MED_FREQUENCY, MED_ROUTE, MED_STATUS, MED_STRENGTH, MED_TOTALDOSE, MED_UNIT, NON_COMPLIANT, OTHER_LIVINGSTATUS, PROBLEM, PROCEDURE, PROCEDURE_RESULT, PROC_METHOD, REASON_FOR_NONCOMPLIANCE, SEVERITY, SUBSTANCE_ABUSE, UNCLEAR_FOLLOWUP. */
		`type`: Option[String] = None,
	  /** text is the location of the entity mention in the document. */
		text: Option[Schema.TextSpan] = None,
	  /** linked_entities are candidate ontological concepts that this entity mention may refer to. They are sorted by decreasing confidence. */
		linkedEntities: Option[List[Schema.LinkedEntity]] = None,
	  /** How this entity mention relates to the subject temporally. Its value is one of: CURRENT, CLINICAL_HISTORY, FAMILY_HISTORY, UPCOMING, ALLERGY */
		temporalAssessment: Option[Schema.Feature] = None,
	  /** The certainty assessment of the entity mention. Its value is one of: LIKELY, SOMEWHAT_LIKELY, UNCERTAIN, SOMEWHAT_UNLIKELY, UNLIKELY, CONDITIONAL */
		certaintyAssessment: Option[Schema.Feature] = None,
	  /** The subject this entity mention relates to. Its value is one of: PATIENT, FAMILY_MEMBER, OTHER */
		subject: Option[Schema.Feature] = None,
	  /** The model's confidence in this entity mention annotation. A number between 0 and 1. */
		confidence: Option[BigDecimal] = None
	)
	
	case class TextSpan(
	  /** The original text contained in this span. */
		content: Option[String] = None,
	  /** The unicode codepoint index of the beginning of this span. */
		beginOffset: Option[Int] = None
	)
	
	case class LinkedEntity(
	  /** entity_id is a concept unique identifier. These are prefixed by a string that identifies the entity coding system, followed by the unique identifier within that system. For example, "UMLS/C0000970". This also supports ad hoc entities, which are formed by normalizing entity mention content. */
		entityId: Option[String] = None
	)
	
	case class Feature(
	  /** The value of this feature annotation. Its range depends on the type of the feature. */
		value: Option[String] = None,
	  /** The model's confidence in this feature annotation. A number between 0 and 1. */
		confidence: Option[BigDecimal] = None
	)
	
	case class Entity(
	  /** entity_id is a first class field entity_id uniquely identifies this concept and its meta-vocabulary. For example, "UMLS/C0000970". */
		entityId: Option[String] = None,
	  /** preferred_term is the preferred term for this concept. For example, "Acetaminophen". For ad hoc entities formed by normalization, this is the most popular unnormalized string. */
		preferredTerm: Option[String] = None,
	  /** Vocabulary codes are first-class fields and differentiated from the concept unique identifier (entity_id). vocabulary_codes contains the representation of this concept in particular vocabularies, such as ICD-10, SNOMED-CT and RxNORM. These are prefixed by the name of the vocabulary, followed by the unique code within that vocabulary. For example, "RXNORM/A10334543". */
		vocabularyCodes: Option[List[String]] = None
	)
	
	case class EntityMentionRelationship(
	  /** subject_id is the id of the subject entity mention. */
		subjectId: Option[String] = None,
	  /** object_id is the id of the object entity mention. */
		objectId: Option[String] = None,
	  /** The model's confidence in this annotation. A number between 0 and 1. */
		confidence: Option[BigDecimal] = None
	)
	
	case class OperationMetadata(
	  /** The name of the API method that initiated the operation. */
		apiMethodName: Option[String] = None,
	  /** The time at which the operation was created by the API. */
		createTime: Option[String] = None,
	  /** The time at which execution was completed. */
		endTime: Option[String] = None,
		counter: Option[Schema.ProgressCounter] = None,
	  /** Specifies if cancellation was requested for the operation. */
		cancelRequested: Option[Boolean] = None,
	  /** A link to audit and error logs in the log viewer. Error logs are generated only by some operations, listed at [Viewing error logs in Cloud Logging](https://cloud.google.com/healthcare/docs/how-tos/logging). */
		logsUrl: Option[String] = None
	)
	
	case class ProgressCounter(
	  /** The number of units that are pending in the operation. */
		pending: Option[String] = None,
	  /** The number of units that succeeded in the operation. */
		success: Option[String] = None,
	  /** The number of units that failed in the operation. */
		failure: Option[String] = None
	)
	
	case class QueryAccessibleDataResponse(
	  /** List of files, each of which contains a list of data_id(s) that are consented for a specified use in the request. */
		gcsUris: Option[List[String]] = None
	)
	
	case class DeidentifySummary(
	
	)
	
	case class GoogleCloudHealthcareV1DeidentifyDeidentifyDicomStoreSummary(
	
	)
	
	case class GoogleCloudHealthcareV1DeidentifyDeidentifyFhirStoreSummary(
	
	)
	
	case class ImportDicomDataResponse(
	
	)
	
	case class ExportDicomDataResponse(
	
	)
	
	case class ImportResourcesResponse(
	
	)
	
	case class ExportResourcesResponse(
	
	)
	
	case class RollbackFhirResourcesResponse(
	  /** The name of the FHIR store to rollback, in the format of "projects/{project_id}/locations/{location_id}/datasets/{dataset_id} /fhirStores/{fhir_store_id}". */
		fhirStore: Option[String] = None
	)
	
	case class ExportMessagesResponse(
	
	)
	
	case class ImportMessagesResponse(
	
	)
	
	case class RollbackHl7V2MessagesResponse(
	  /** The name of the HL7v2 store to rollback, in the format of "projects/{project_id}/locations/{location_id}/datasets/{dataset_id} /hl7v2Stores/{hl7v2_store_id}". */
		hl7v2Store: Option[String] = None
	)
	
	case class SetBlobStorageSettingsResponse(
	
	)
}
