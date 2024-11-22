package com.boresjo.play.api.google.chromepolicy

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleChromePolicyVersionsV1BatchModifyOrgUnitPoliciesRequest(
	  /** List of policies to modify as defined by the `requests`. All requests in the list must follow these restrictions: 1. All schemas in the list must have the same root namespace. 2. All `policyTargetKey.targetResource` values must point to an org unit resource. 3. All `policyTargetKey` values must have the same key names in the ` additionalTargetKeys`. This also means if one of the targets has an empty `additionalTargetKeys` map, all of the targets must have an empty `additionalTargetKeys` map. 4. No two modification requests can reference the same `policySchema` + ` policyTargetKey` pair.  */
		requests: Option[List[Schema.GoogleChromePolicyVersionsV1ModifyOrgUnitPolicyRequest]] = None
	)
	
	case class GoogleChromePolicyVersionsV1RemoveNetworkRequest(
	  /** Required. The target resource on which this network will be removed. The following resources are supported: &#42; Organizational Unit ("orgunits/{orgunit_id}") */
		targetResource: Option[String] = None,
	  /** Required. The GUID of the network to remove. */
		networkId: Option[String] = None
	)
	
	case class GoogleChromePolicyVersionsV1ResolveResponse(
	  /** The page token used to get the next set of resolved policies found by the request. */
		nextPageToken: Option[String] = None,
	  /** The list of resolved policies found by the resolve request. */
		resolvedPolicies: Option[List[Schema.GoogleChromePolicyVersionsV1ResolvedPolicy]] = None
	)
	
	case class GoogleChromePolicyVersionsV1PolicyModificationFieldError(
	  /** Output only. The name of the field with the error. */
		field: Option[String] = None,
	  /** Output only. The error message related to the field. */
		error: Option[String] = None
	)
	
	object GoogleChromePolicyVersionsV1PolicyApiLifecycle {
		enum PolicyApiLifecycleStageEnum extends Enum[PolicyApiLifecycleStageEnum] { case API_UNSPECIFIED, API_PREVIEW, API_DEVELOPMENT, API_CURRENT, API_DEPRECATED }
	}
	case class GoogleChromePolicyVersionsV1PolicyApiLifecycle(
	  /** Description about current life cycle. */
		description: Option[String] = None,
	  /** End supporting date for current policy. Attempting to modify a policy after its end support date will result in a Bad Request (400 error). Could only be set if policy_api_lifecycle_stage is API_DEPRECATED. */
		endSupport: Option[Schema.GoogleTypeDate] = None,
	  /** Indicates current life cycle stage of the policy API. */
		policyApiLifecycleStage: Option[Schema.GoogleChromePolicyVersionsV1PolicyApiLifecycle.PolicyApiLifecycleStageEnum] = None,
	  /** Corresponding to deprecated_in_favor_of, the fully qualified namespace(s) of the old policies that will be deprecated because of introduction of this policy. */
		scheduledToDeprecatePolicies: Option[List[String]] = None,
	  /** In the event that this policy was deprecated in favor of another policy, the fully qualified namespace(s) of the new policies as they will show in PolicyAPI. Could only be set if policy_api_lifecycle_stage is API_DEPRECATED. */
		deprecatedInFavorOf: Option[List[String]] = None
	)
	
	case class GoogleChromePolicyVersionsV1UpdateGroupPriorityOrderingRequest(
	  /** Required. The key of the target for which we want to update the group priority ordering. The target resource must point to an app. */
		policyTargetKey: Option[Schema.GoogleChromePolicyVersionsV1PolicyTargetKey] = None,
	  /** The schema name of the policy for the request. */
		policySchema: Option[String] = None,
	  /** Required. The group IDs, in desired priority ordering. */
		groupIds: Option[List[String]] = None,
	  /** The namespace of the policy type for the request. */
		policyNamespace: Option[String] = None
	)
	
	case class GoogleChromePolicyVersionsV1CertificateReference(
	  /** Output only. The name of the referencing network. */
		network: Option[String] = None,
	  /** Output only. The obfuscated id of the org unit the referencing network is in. */
		orgUnitId: Option[String] = None
	)
	
	case class GoogleChromePolicyVersionsV1ModifyGroupPolicyRequest(
	  /** The new value for the policy. */
		policyValue: Option[Schema.GoogleChromePolicyVersionsV1PolicyValue] = None,
	  /** Required. The key of the target for which we want to modify a policy. The target resource must point to a Group. */
		policyTargetKey: Option[Schema.GoogleChromePolicyVersionsV1PolicyTargetKey] = None,
	  /** Required. Policy fields to update. Only fields in this mask will be updated; other fields in `policy_value` will be ignored (even if they have values). If a field is in this list it must have a value in 'policy_value'. */
		updateMask: Option[String] = None
	)
	
	case class GoogleChromePolicyVersionsV1BatchDeleteGroupPoliciesRequest(
	  /** List of policies that will be deleted as defined by the `requests`. All requests in the list must follow these restrictions: 1. All schemas in the list must have the same root namespace. 2. All `policyTargetKey.targetResource` values must point to a group resource. 3. All `policyTargetKey` values must have the same `app_id` key name in the `additionalTargetKeys`. 4. No two modification requests can reference the same `policySchema` + ` policyTargetKey` pair.  */
		requests: Option[List[Schema.GoogleChromePolicyVersionsV1DeleteGroupPolicyRequest]] = None
	)
	
	case class GoogleProtobufEmpty(
	
	)
	
	case class GoogleChromePolicyVersionsV1UploadPolicyFileRequest(
	  /** Required. The fully qualified policy schema and field name this file is uploaded for. This information will be used to validate the content type of the file. */
		policyField: Option[String] = None
	)
	
	case class GoogleChromePolicyVersionsV1ResolveRequest(
	  /** Required. The key of the target resource on which the policies should be resolved. */
		policyTargetKey: Option[Schema.GoogleChromePolicyVersionsV1PolicyTargetKey] = None,
	  /** The page token used to retrieve a specific page of the request. */
		pageToken: Option[String] = None,
	  /** The maximum number of policies to return, defaults to 100 and has a maximum of 1000. */
		pageSize: Option[Int] = None,
	  /** Required. The schema filter to apply to the resolve request. Specify a schema name to view a particular schema, for example: chrome.users.ShowLogoutButton Wildcards are supported, but only in the leaf portion of the schema name. Wildcards cannot be used in namespace directly. Please read https://developers.google.com/chrome/policy/guides/policy-schemas for details on schema namespaces. For example: Valid: "chrome.users.&#42;", "chrome.users.apps.&#42;", "chrome.printers.&#42;" Invalid: "&#42;", "&#42;.users", "chrome.&#42;", "chrome.&#42;.apps.&#42;" */
		policySchemaFilter: Option[String] = None
	)
	
	case class GoogleChromePolicyVersionsV1PolicySchemaRequiredItems(
	  /** The fields that are required as a consequence of the field conditions. */
		requiredFields: Option[List[String]] = None,
	  /** The value(s) of the field that provoke required field enforcement. An empty field_conditions implies that any value assigned to this field will provoke required field enforcement. */
		fieldConditions: Option[List[String]] = None
	)
	
	case class Proto2EnumDescriptorProto(
		name: Option[String] = None,
		value: Option[List[Schema.Proto2EnumValueDescriptorProto]] = None
	)
	
	case class GoogleChromePolicyVersionsV1RemoveCertificateErrorDetails(
	  /** Output only. If the certificate was not removed, a list of references to the certificate that prevented it from being removed. Only unreferenced certificates can be removed. */
		certificateReferences: Option[List[Schema.GoogleChromePolicyVersionsV1CertificateReference]] = None
	)
	
	case class GoogleChromePolicyVersionsV1InheritOrgUnitPolicyRequest(
	  /** Required. The key of the target for which we want to modify a policy. The target resource must point to an Org Unit. */
		policyTargetKey: Option[Schema.GoogleChromePolicyVersionsV1PolicyTargetKey] = None,
	  /** The fully qualified name of the policy schema that is being inherited. */
		policySchema: Option[String] = None
	)
	
	object GoogleChromePolicyVersionsV1PolicySchema {
		enum ValidTargetResourcesEnum extends Enum[ValidTargetResourcesEnum] { case TARGET_RESOURCE_UNSPECIFIED, ORG_UNIT, GROUP }
		enum SupportedPlatformsEnum extends Enum[SupportedPlatformsEnum] { case PLATFORM_UNSPECIFIED, CHROME_OS, CHROME_BROWSER, CHROME_BROWSER_FOR_ANDROID, CHROME_BROWSER_FOR_IOS }
	}
	case class GoogleChromePolicyVersionsV1PolicySchema(
	  /** Format: name=customers/{customer}/policySchemas/{schema_namespace} */
		name: Option[String] = None,
	  /** Output only. Specific access restrictions related to this policy. */
		accessRestrictions: Option[List[String]] = None,
	  /** Output only. Description about the policy schema for user consumption. */
		policyDescription: Option[String] = None,
	  /** Output only. Information about applicable target resources for the policy. */
		validTargetResources: Option[List[Schema.GoogleChromePolicyVersionsV1PolicySchema.ValidTargetResourcesEnum]] = None,
	  /** Output only. The fully qualified name of the policy schema. This value is used to fill the field `policy_schema` in PolicyValue when calling BatchInheritOrgUnitPolicies BatchModifyOrgUnitPolicies BatchModifyGroupPolicies or BatchDeleteGroupPolicies. */
		schemaName: Option[String] = None,
	  /** Output only. List indicates that the policy will only apply to devices/users on these platforms. */
		supportedPlatforms: Option[List[Schema.GoogleChromePolicyVersionsV1PolicySchema.SupportedPlatformsEnum]] = None,
	  /** Output only. URI to related support article for this schema. */
		supportUri: Option[String] = None,
	  /** Title of the category in which a setting belongs. */
		categoryTitle: Option[String] = None,
	  /** Output only. Additional key names that will be used to identify the target of the policy value. When specifying a `policyTargetKey`, each of the additional keys specified here will have to be included in the `additionalTargetKeys` map. */
		additionalTargetKeyNames: Option[List[Schema.GoogleChromePolicyVersionsV1AdditionalTargetKeyName]] = None,
	  /** Schema definition using proto descriptor. */
		definition: Option[Schema.Proto2FileDescriptorProto] = None,
	  /** Output only. Special notice messages related to setting certain values in certain fields in the schema. */
		notices: Option[List[Schema.GoogleChromePolicyVersionsV1PolicySchemaNoticeDescription]] = None,
	  /** Output only. Detailed description of each field that is part of the schema. Fields are suggested to be displayed by the ordering in this list, not by field number. */
		fieldDescriptions: Option[List[Schema.GoogleChromePolicyVersionsV1PolicySchemaFieldDescription]] = None,
	  /** Output only. Current lifecycle information. */
		policyApiLifecycle: Option[Schema.GoogleChromePolicyVersionsV1PolicyApiLifecycle] = None
	)
	
	case class GoogleChromePolicyVersionsV1PolicySchemaFieldDependencies(
	  /** The value which the source field must have for this field to be allowed to be set. */
		sourceFieldValue: Option[String] = None,
	  /** The source field which this field depends on. */
		sourceField: Option[String] = None
	)
	
	case class Proto2EnumValueDescriptorProto(
		name: Option[String] = None,
		number: Option[Int] = None
	)
	
	case class GoogleChromePolicyVersionsV1ModifyOrgUnitPolicyRequest(
	  /** The new value for the policy. */
		policyValue: Option[Schema.GoogleChromePolicyVersionsV1PolicyValue] = None,
	  /** Required. Policy fields to update. Only fields in this mask will be updated; other fields in `policy_value` will be ignored (even if they have values). If a field is in this list it must have a value in 'policy_value'. */
		updateMask: Option[String] = None,
	  /** Required. The key of the target for which we want to modify a policy. The target resource must point to an Org Unit. */
		policyTargetKey: Option[Schema.GoogleChromePolicyVersionsV1PolicyTargetKey] = None
	)
	
	case class GoogleChromePolicyVersionsV1RemoveCertificateRequest(
	  /** Required. The target resource on which this certificate will be removed. The following resources are supported: &#42; Organizational Unit ("orgunits/{orgunit_id}") */
		targetResource: Option[String] = None,
	  /** Required. The GUID of the certificate to remove. */
		networkId: Option[String] = None
	)
	
	object GoogleChromePolicyVersionsV1UploadedFileConstraints {
		enum SupportedContentTypesEnum extends Enum[SupportedContentTypesEnum] { case CONTENT_TYPE_UNSPECIFIED, CONTENT_TYPE_PLAIN_TEXT, CONTENT_TYPE_HTML, CONTENT_TYPE_IMAGE_JPEG, CONTENT_TYPE_IMAGE_GIF, CONTENT_TYPE_IMAGE_PNG, CONTENT_TYPE_JSON, CONTENT_TYPE_ZIP, CONTENT_TYPE_GZIP, CONTENT_TYPE_CSV, CONTENT_TYPE_YAML, CONTENT_TYPE_IMAGE_WEBP }
	}
	case class GoogleChromePolicyVersionsV1UploadedFileConstraints(
	  /** File types that can be uploaded for a setting. */
		supportedContentTypes: Option[List[Schema.GoogleChromePolicyVersionsV1UploadedFileConstraints.SupportedContentTypesEnum]] = None,
	  /** The size limit of uploaded files for a setting, in bytes. */
		sizeLimitBytes: Option[String] = None
	)
	
	case class GoogleChromePolicyVersionsV1DefineCertificateResponse(
	  /** the affiliated settings of the certificate (NOT IMPLEMENTED) */
		settings: Option[List[Schema.GoogleChromePolicyVersionsV1NetworkSetting]] = None,
	  /** The guid of the certificate created by the action. */
		networkId: Option[String] = None,
	  /** the resource at which the certificate is defined. */
		targetResource: Option[String] = None
	)
	
	case class GoogleChromePolicyVersionsV1ListGroupPriorityOrderingRequest(
	  /** The schema name of the policy for the request. */
		policySchema: Option[String] = None,
	  /** Required. The key of the target for which we want to retrieve the group priority ordering. The target resource must point to an app. */
		policyTargetKey: Option[Schema.GoogleChromePolicyVersionsV1PolicyTargetKey] = None,
	  /** The namespace of the policy type for the request. */
		policyNamespace: Option[String] = None
	)
	
	case class GoogleChromePolicyVersionsV1DeleteGroupPolicyRequest(
	  /** Required. The key of the target for which we want to modify a policy. The target resource must point to a Group. */
		policyTargetKey: Option[Schema.GoogleChromePolicyVersionsV1PolicyTargetKey] = None,
	  /** The fully qualified name of the policy schema that is being inherited. */
		policySchema: Option[String] = None
	)
	
	case class GoogleChromePolicyVersionsV1BatchModifyGroupPoliciesRequest(
	  /** List of policies to modify as defined by the `requests`. All requests in the list must follow these restrictions: 1. All schemas in the list must have the same root namespace. 2. All `policyTargetKey.targetResource` values must point to a group resource. 3. All `policyTargetKey` values must have the same `app_id` key name in the `additionalTargetKeys`. 4. No two modification requests can reference the same `policySchema` + ` policyTargetKey` pair.  */
		requests: Option[List[Schema.GoogleChromePolicyVersionsV1ModifyGroupPolicyRequest]] = None
	)
	
	case class GoogleChromePolicyVersionsV1ListPolicySchemasResponse(
	  /** The page token used to get the next page of policy schemas. */
		nextPageToken: Option[String] = None,
	  /** The list of policy schemas that match the query. */
		policySchemas: Option[List[Schema.GoogleChromePolicyVersionsV1PolicySchema]] = None
	)
	
	case class GoogleChromePolicyVersionsV1BatchInheritOrgUnitPoliciesRequest(
	  /** List of policies that have to inherit their values as defined by the `requests`. All requests in the list must follow these restrictions: 1. All schemas in the list must have the same root namespace. 2. All `policyTargetKey.targetResource` values must point to an org unit resource. 3. All `policyTargetKey` values must have the same key names in the ` additionalTargetKeys`. This also means if one of the targets has an empty `additionalTargetKeys` map, all of the targets must have an empty `additionalTargetKeys` map. 4. No two modification requests can reference the same `policySchema` + ` policyTargetKey` pair.  */
		requests: Option[List[Schema.GoogleChromePolicyVersionsV1InheritOrgUnitPolicyRequest]] = None
	)
	
	case class GoogleChromePolicyVersionsV1NumericRangeConstraint(
	  /** Maximum value. */
		maximum: Option[String] = None,
	  /** Minimum value. */
		minimum: Option[String] = None
	)
	
	case class Proto2OneofDescriptorProto(
		name: Option[String] = None
	)
	
	case class Proto2FileDescriptorProto(
		enumType: Option[List[Schema.Proto2EnumDescriptorProto]] = None,
	  /** file name, relative to root of source tree */
		name: Option[String] = None,
	  /** BEGIN GOOGLE-INTERNAL TODO(b/297898292) Deprecate and remove this field in favor of enums. END GOOGLE-INTERNAL */
		editionDeprecated: Option[String] = None,
	  /** The syntax of the proto file. The supported values are "proto2", "proto3", and "editions". If `edition` is present, this value must be "editions". */
		syntax: Option[String] = None,
	  /** All top-level definitions in this file. */
		messageType: Option[List[Schema.Proto2DescriptorProto]] = None,
	  /** e.g. "foo", "foo.bar", etc. */
		`package`: Option[String] = None
	)
	
	case class GoogleChromePolicyVersionsV1PolicyTargetKey(
	  /** The target resource on which this policy is applied. The following resources are supported: &#42; Organizational Unit ("orgunits/{orgunit_id}") &#42; Group ("groups/{group_id}") */
		targetResource: Option[String] = None,
	  /** Map containing the additional target key name and value pairs used to further identify the target of the policy. */
		additionalTargetKeys: Option[Map[String, String]] = None
	)
	
	case class GoogleChromePolicyVersionsV1PolicySchemaNoticeDescription(
	  /** Output only. The field name associated with the notice. */
		field: Option[String] = None,
	  /** Output only. The notice message associate with the value of the field. */
		noticeMessage: Option[String] = None,
	  /** Output only. Whether the user needs to acknowledge the notice message before the value can be set. */
		acknowledgementRequired: Option[Boolean] = None,
	  /** Output only. The value of the field that has a notice. When setting the field to this value, the user may be required to acknowledge the notice message in order for the value to be set. */
		noticeValue: Option[String] = None
	)
	
	case class GoogleChromePolicyVersionsV1PolicyModificationErrorDetails(
	  /** Output only. List of specific policy modifications errors that may have occurred during a modifying request. */
		modificationErrors: Option[List[Schema.GoogleChromePolicyVersionsV1PolicyModificationError]] = None
	)
	
	case class GoogleChromePolicyVersionsV1FieldConstraints(
	  /** Constraints on the uploaded file of a file policy. If present, this policy requires a URL that can be fetched by uploading a file with the constraints specified in this proto. */
		uploadedFileConstraints: Option[Schema.GoogleChromePolicyVersionsV1UploadedFileConstraints] = None,
	  /** The allowed range for numeric fields. */
		numericRangeConstraint: Option[Schema.GoogleChromePolicyVersionsV1NumericRangeConstraint] = None
	)
	
	case class GoogleChromePolicyVersionsV1ListGroupPriorityOrderingResponse(
	  /** Output only. The group IDs, in priority ordering. */
		groupIds: Option[List[String]] = None,
	  /** Output only. The namespace of the policy type of the group IDs. */
		policyNamespace: Option[String] = None,
	  /** Output only. The target resource for which the group priority ordering has been retrieved. */
		policyTargetKey: Option[Schema.GoogleChromePolicyVersionsV1PolicyTargetKey] = None,
	  /** Output only. The schema name of the policy for the group IDs. */
		policySchema: Option[String] = None
	)
	
	case class GoogleChromePolicyVersionsV1DefineNetworkRequest(
	  /** Required. Name of the new created network. */
		name: Option[String] = None,
	  /** Required. Detailed network settings. */
		settings: Option[List[Schema.GoogleChromePolicyVersionsV1NetworkSetting]] = None,
	  /** Required. The target resource on which this new network will be defined. The following resources are supported: &#42; Organizational Unit ("orgunits/{orgunit_id}") */
		targetResource: Option[String] = None
	)
	
	case class GoogleChromePolicyVersionsV1RemoveNetworkResponse(
	
	)
	
	case class GoogleChromePolicyVersionsV1PolicyModificationError(
	  /** Output only. The specific policy target modification that had error. */
		policyTargetKey: Option[Schema.GoogleChromePolicyVersionsV1PolicyTargetKey] = None,
	  /** Output only. The non-field errors related to the modification. */
		errors: Option[List[String]] = None,
	  /** Output only. The error messages related to the modification. */
		fieldErrors: Option[List[Schema.GoogleChromePolicyVersionsV1PolicyModificationFieldError]] = None,
	  /** Output only. The specific policy schema modification that had an error. */
		policySchema: Option[String] = None
	)
	
	case class GoogleChromePolicyVersionsV1DefineCertificateRequest(
	  /** Optional. Certificate settings within the chrome.networks.certificates namespace. */
		settings: Option[List[Schema.GoogleChromePolicyVersionsV1NetworkSetting]] = None,
	  /** Optional. The optional name of the certificate. If not specified, the certificate issuer will be used as the name. */
		ceritificateName: Option[String] = None,
	  /** Required. The target resource on which this certificate is applied. The following resources are supported: &#42; Organizational Unit ("orgunits/{orgunit_id}") */
		targetResource: Option[String] = None,
	  /** Required. The raw contents of the .PEM, .CRT, or .CER file. */
		certificate: Option[String] = None
	)
	
	case class GoogleTypeDate(
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None,
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None,
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None
	)
	
	case class GoogleChromePolicyVersionsV1AdditionalTargetKeyName(
	  /** Key description. */
		keyDescription: Option[String] = None,
	  /** Key name. */
		key: Option[String] = None
	)
	
	case class GoogleChromePolicyVersionsV1UploadPolicyFileResponse(
	  /** The uri for end user to download the file. */
		downloadUri: Option[String] = None
	)
	
	case class GoogleChromePolicyVersionsV1PolicyValue(
	  /** The value of the policy that is compatible with the schema that it is associated with. */
		value: Option[Map[String, JsValue]] = None,
	  /** The fully qualified name of the policy schema associated with this policy. */
		policySchema: Option[String] = None
	)
	
	case class Proto2DescriptorProto(
		oneofDecl: Option[List[Schema.Proto2OneofDescriptorProto]] = None,
		name: Option[String] = None,
		enumType: Option[List[Schema.Proto2EnumDescriptorProto]] = None,
		field: Option[List[Schema.Proto2FieldDescriptorProto]] = None,
		nestedType: Option[List[Schema.Proto2DescriptorProto]] = None
	)
	
	case class GoogleChromePolicyVersionsV1PolicySchemaFieldKnownValueDescription(
	  /** Output only. The string represenstation of the value that can be set for the field. */
		value: Option[String] = None,
	  /** Output only. Field conditions required for this value to be valid. */
		fieldDependencies: Option[List[Schema.GoogleChromePolicyVersionsV1PolicySchemaFieldDependencies]] = None,
	  /** Output only. Additional description for this value. */
		description: Option[String] = None
	)
	
	object Proto2FieldDescriptorProto {
		enum LabelEnum extends Enum[LabelEnum] { case LABEL_OPTIONAL, LABEL_REPEATED, LABEL_REQUIRED }
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_DOUBLE, TYPE_FLOAT, TYPE_INT64, TYPE_UINT64, TYPE_INT32, TYPE_FIXED64, TYPE_FIXED32, TYPE_BOOL, TYPE_STRING, TYPE_GROUP, TYPE_MESSAGE, TYPE_BYTES, TYPE_UINT32, TYPE_ENUM, TYPE_SFIXED32, TYPE_SFIXED64, TYPE_SINT32, TYPE_SINT64 }
	}
	case class Proto2FieldDescriptorProto(
		label: Option[Schema.Proto2FieldDescriptorProto.LabelEnum] = None,
		number: Option[Int] = None,
	  /** For numeric types, contains the original text representation of the value. For booleans, "true" or "false". For strings, contains the default text contents (not escaped in any way). For bytes, contains the C escaped value. All bytes >= 128 are escaped. */
		defaultValue: Option[String] = None,
	  /** For message and enum types, this is the name of the type. If the name starts with a '.', it is fully-qualified. Otherwise, C++-like scoping rules are used to find the type (i.e. first the nested types within this message are searched, then within the parent, on up to the root namespace). */
		typeName: Option[String] = None,
	  /** If set, gives the index of a oneof in the containing type's oneof_decl list. This field is a member of that oneof. */
		oneofIndex: Option[Int] = None,
	  /** If type_name is set, this need not be set. If both this and type_name are set, this must be one of TYPE_ENUM, TYPE_MESSAGE or TYPE_GROUP. */
		`type`: Option[Schema.Proto2FieldDescriptorProto.TypeEnum] = None,
	  /** If true, this is a proto3 "optional". When a proto3 field is optional, it tracks presence regardless of field type. When proto3_optional is true, this field must belong to a oneof to signal to old proto3 clients that presence is tracked for this field. This oneof is known as a "synthetic" oneof, and this field must be its sole member (each proto3 optional field gets its own synthetic oneof). Synthetic oneofs exist in the descriptor only, and do not generate any API. Synthetic oneofs must be ordered after all "real" oneofs. For message fields, proto3_optional doesn't create any semantic change, since non-repeated message fields always track presence. However it still indicates the semantic detail of whether the user wrote "optional" or not. This can be useful for round-tripping the .proto file. For consistency we give message fields a synthetic oneof also, even though it is not required to track presence. This is especially important because the parser can't tell if a field is a message or an enum, so it must always create a synthetic oneof. Proto2 optional fields do not set this flag, because they already indicate optional with `LABEL_OPTIONAL`. */
		proto3Optional: Option[Boolean] = None,
	  /** JSON name of this field. The value is set by protocol compiler. If the user has set a "json_name" option on this field, that option's value will be used. Otherwise, it's deduced from the field's name by converting it to camelCase. */
		jsonName: Option[String] = None,
		name: Option[String] = None
	)
	
	case class GoogleChromePolicyVersionsV1ResolvedPolicy(
	  /** Output only. The source resource from which this policy value is obtained. May be the same as `targetKey` if the policy is directly modified on the target, otherwise it would be another resource from which the policy gets its value (if applicable). If not present, the source is the default value for the customer. */
		sourceKey: Option[Schema.GoogleChromePolicyVersionsV1PolicyTargetKey] = None,
	  /** Output only. The resolved value of the policy. */
		value: Option[Schema.GoogleChromePolicyVersionsV1PolicyValue] = None,
	  /** Output only. The added source key establishes at which level an entity was explicitly added for management. This is useful for certain type of policies that are only applied if they are explicitly added for management. For example: apps and networks. An entity can only be deleted from management in an Organizational Unit that it was explicitly added to. If this is not present it means that the policy is managed without the need to explicitly add an entity, for example: standard user or device policies. */
		addedSourceKey: Option[Schema.GoogleChromePolicyVersionsV1PolicyTargetKey] = None,
	  /** Output only. The target resource for which the resolved policy value applies. */
		targetKey: Option[Schema.GoogleChromePolicyVersionsV1PolicyTargetKey] = None
	)
	
	case class GoogleChromePolicyVersionsV1RemoveCertificateResponse(
	
	)
	
	case class GoogleChromePolicyVersionsV1DefineNetworkResponse(
	  /** Detailed network settings of the new created network */
		settings: Option[List[Schema.GoogleChromePolicyVersionsV1NetworkSetting]] = None,
	  /** The target resource on which this new network will be defined. The following resources are supported: &#42; Organizational Unit ("orgunits/{orgunit_id}") */
		targetResource: Option[String] = None,
	  /** Network ID of the new created network. */
		networkId: Option[String] = None
	)
	
	case class GoogleChromePolicyVersionsV1NetworkSetting(
	  /** The value of the network setting. */
		value: Option[Map[String, JsValue]] = None,
	  /** The fully qualified name of the network setting. */
		policySchema: Option[String] = None
	)
	
	case class GoogleChromePolicyVersionsV1PolicySchemaFieldDescription(
	  /** Output only. The name of the field. */
		name: Option[String] = None,
	  /** Output only. Provides a list of fields that are required to be set if this field has a certain value. */
		requiredItems: Option[List[Schema.GoogleChromePolicyVersionsV1PolicySchemaRequiredItems]] = None,
	  /** Output only. Client default if the policy is unset. */
		defaultValue: Option[JsValue] = None,
	  /** Output only. If the field has a set of known values, this field will provide a description for these values. */
		knownValueDescriptions: Option[List[Schema.GoogleChromePolicyVersionsV1PolicySchemaFieldKnownValueDescription]] = None,
	  /** Output only. Provides the description of the fields nested in this field, if the field is a message type that defines multiple fields. Fields are suggested to be displayed by the ordering in this list, not by field number. */
		nestedFieldDescriptions: Option[List[Schema.GoogleChromePolicyVersionsV1PolicySchemaFieldDescription]] = None,
	  /** Output only. Any input constraints associated on the values for the field. */
		inputConstraint: Option[String] = None,
	  /** Output only. Provides a list of fields and values. At least one of the fields must have the corresponding value in order for this field to be allowed to be set. */
		fieldDependencies: Option[List[Schema.GoogleChromePolicyVersionsV1PolicySchemaFieldDependencies]] = None,
	  /** Output only. The name of the field for associated with this description. */
		field: Option[String] = None,
	  /** Output only. Information on any input constraints associated on the values for the field. */
		fieldConstraints: Option[Schema.GoogleChromePolicyVersionsV1FieldConstraints] = None,
	  /** Deprecated. Use name and field_description instead. The description for the field. */
		description: Option[String] = None,
	  /** Output only. The description of the field. */
		fieldDescription: Option[String] = None
	)
}
