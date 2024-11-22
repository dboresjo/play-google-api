package com.boresjo.play.api.google.assuredworkloads

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleCloudAssuredworkloadsV1AcknowledgeViolationResponse(
	
	)
	
	case class GoogleCloudAssuredworkloadsV1WorkloadKMSSettings(
	  /** Required. Input only. Immutable. [next_rotation_time] will be advanced by this period when the Key Management Service automatically rotates a key. Must be at least 24 hours and at most 876,000 hours. */
		rotationPeriod: Option[String] = None,
	  /** Required. Input only. Immutable. The time at which the Key Management Service will automatically create a new version of the crypto key and mark it as the primary. */
		nextRotationTime: Option[String] = None
	)
	
	case class GoogleCloudAssuredworkloadsV1MoveImpact(
	  /** Explanation of the impact. */
		detail: Option[String] = None
	)
	
	case class GoogleCloudAssuredworkloadsV1MoveAnalysisResult(
	  /** List of warnings. These are risks that may or may not result in compliance violations. */
		warnings: Option[List[Schema.GoogleCloudAssuredworkloadsV1MoveImpact]] = None,
	  /** List of blockers. If not resolved, these will result in compliance violations in the target. */
		blockers: Option[List[Schema.GoogleCloudAssuredworkloadsV1MoveImpact]] = None
	)
	
	object GoogleCloudAssuredworkloadsV1RestrictAllowedResourcesRequest {
		enum RestrictionTypeEnum extends Enum[RestrictionTypeEnum] { case RESTRICTION_TYPE_UNSPECIFIED, ALLOW_ALL_GCP_RESOURCES, ALLOW_COMPLIANT_RESOURCES, APPEND_COMPLIANT_RESOURCES }
	}
	case class GoogleCloudAssuredworkloadsV1RestrictAllowedResourcesRequest(
	  /** Required. The type of restriction for using gcp products in the Workload environment. */
		restrictionType: Option[Schema.GoogleCloudAssuredworkloadsV1RestrictAllowedResourcesRequest.RestrictionTypeEnum] = None
	)
	
	case class GoogleProtobufEmpty(
	
	)
	
	object GoogleCloudAssuredworkloadsV1Violation {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, RESOLVED, UNRESOLVED, EXCEPTION }
		enum ViolationTypeEnum extends Enum[ViolationTypeEnum] { case VIOLATION_TYPE_UNSPECIFIED, ORG_POLICY, RESOURCE }
	}
	case class GoogleCloudAssuredworkloadsV1Violation(
	  /** Output only. Immutable. Name of the OrgPolicy which was modified with non-compliant change and resulted this violation. Format: projects/{project_number}/policies/{constraint_name} folders/{folder_id}/policies/{constraint_name} organizations/{organization_id}/policies/{constraint_name} */
		nonCompliantOrgPolicy: Option[String] = None,
	  /** Output only. Time of the event which triggered the Violation. */
		beginTime: Option[String] = None,
	  /** Output only. Time of the event which fixed the Violation. If the violation is ACTIVE this will be empty. */
		resolveTime: Option[String] = None,
	  /** Optional. Output only. Name of the resource like //storage.googleapis.com/myprojectxyz-testbucket. Empty for org-policy violations. */
		resourceName: Option[String] = None,
	  /** Output only. Compliance violation remediation */
		remediation: Option[Schema.GoogleCloudAssuredworkloadsV1ViolationRemediation] = None,
	  /** Output only. State of the violation */
		state: Option[Schema.GoogleCloudAssuredworkloadsV1Violation.StateEnum] = None,
	  /** Output only. Immutable. The org-policy-constraint that was incorrectly changed, which resulted in this violation. */
		orgPolicyConstraint: Option[String] = None,
	  /** Optional. Timestamp when this violation was acknowledged first. Check exception_contexts to find the last time the violation was acknowledged when there are more than one violations. This field will be absent when acknowledged field is marked as false. */
		acknowledgementTime: Option[String] = None,
	  /** Optional. Output only. Type of the resource like compute.googleapis.com/Disk, etc. Empty for org-policy violations. */
		resourceType: Option[String] = None,
	  /** Output only. The last time when the Violation record was updated. */
		updateTime: Option[String] = None,
	  /** Output only. Immutable. Name of the Violation. Format: organizations/{organization}/locations/{location}/workloads/{workload_id}/violations/{violations_id} */
		name: Option[String] = None,
	  /** Output only. Type of the violation */
		violationType: Option[Schema.GoogleCloudAssuredworkloadsV1Violation.ViolationTypeEnum] = None,
	  /** Output only. List of all the exception detail added for the violation. */
		exceptionContexts: Option[List[Schema.GoogleCloudAssuredworkloadsV1ViolationExceptionContext]] = None,
	  /** Optional. Output only. Violation Id of the org-policy violation due to which the resource violation is caused. Empty for org-policy violations. */
		associatedOrgPolicyViolationId: Option[String] = None,
	  /** Output only. Immutable. Audit Log Link for violated resource Format: https://console.cloud.google.com/logs/query;query={logName}{protoPayload.resourceName}{timeRange}{folder} */
		auditLogLink: Option[String] = None,
	  /** Output only. Immutable. Audit Log link to find business justification provided for violation exception. Format: https://console.cloud.google.com/logs/query;query={logName}{protoPayload.resourceName}{protoPayload.methodName}{timeRange}{organization} */
		exceptionAuditLogLink: Option[String] = None,
	  /** Optional. Output only. Parent project number where resource is present. Empty for org-policy violations. */
		parentProjectNumber: Option[String] = None,
	  /** A boolean that indicates if the violation is acknowledged */
		acknowledged: Option[Boolean] = None,
	  /** Output only. Description for the Violation. e.g. OrgPolicy gcp.resourceLocations has non compliant value. */
		description: Option[String] = None,
	  /** Output only. Category under which this violation is mapped. e.g. Location, Service Usage, Access, Encryption, etc. */
		category: Option[String] = None
	)
	
	case class GoogleCloudAssuredworkloadsV1ListViolationsResponse(
	  /** List of Violations under a Workload. */
		violations: Option[List[Schema.GoogleCloudAssuredworkloadsV1Violation]] = None,
	  /** The next page token. Returns empty if reached the last page. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudAssuredworkloadsV1ListWorkloadsResponse(
	  /** The next page token. Return empty if reached the last page. */
		nextPageToken: Option[String] = None,
	  /** List of Workloads under a given parent. */
		workloads: Option[List[Schema.GoogleCloudAssuredworkloadsV1Workload]] = None
	)
	
	object GoogleCloudAssuredworkloadsV1Workload {
		enum ComplianceRegimeEnum extends Enum[ComplianceRegimeEnum] { case COMPLIANCE_REGIME_UNSPECIFIED, IL4, CJIS, FEDRAMP_HIGH, FEDRAMP_MODERATE, US_REGIONAL_ACCESS, HIPAA, HITRUST, EU_REGIONS_AND_SUPPORT, CA_REGIONS_AND_SUPPORT, ITAR, AU_REGIONS_AND_US_SUPPORT, ASSURED_WORKLOADS_FOR_PARTNERS, ISR_REGIONS, ISR_REGIONS_AND_SUPPORT, CA_PROTECTED_B, IL5, IL2, JP_REGIONS_AND_SUPPORT, KSA_REGIONS_AND_SUPPORT_WITH_SOVEREIGNTY_CONTROLS, REGIONAL_CONTROLS, HEALTHCARE_AND_LIFE_SCIENCES_CONTROLS, HEALTHCARE_AND_LIFE_SCIENCES_CONTROLS_US_SUPPORT, IRS_1075 }
		enum KajEnrollmentStateEnum extends Enum[KajEnrollmentStateEnum] { case KAJ_ENROLLMENT_STATE_UNSPECIFIED, KAJ_ENROLLMENT_STATE_PENDING, KAJ_ENROLLMENT_STATE_COMPLETE }
		enum PartnerEnum extends Enum[PartnerEnum] { case PARTNER_UNSPECIFIED, LOCAL_CONTROLS_BY_S3NS, SOVEREIGN_CONTROLS_BY_T_SYSTEMS, SOVEREIGN_CONTROLS_BY_SIA_MINSAIT, SOVEREIGN_CONTROLS_BY_PSN, SOVEREIGN_CONTROLS_BY_CNTXT, SOVEREIGN_CONTROLS_BY_CNTXT_NO_EKM }
	}
	case class GoogleCloudAssuredworkloadsV1Workload(
	  /** Required. Immutable. Compliance Regime associated with this workload. */
		complianceRegime: Option[Schema.GoogleCloudAssuredworkloadsV1Workload.ComplianceRegimeEnum] = None,
	  /** Output only. Immutable. The Workload creation timestamp. */
		createTime: Option[String] = None,
	  /** Optional. Options to be set for the given created workload. */
		workloadOptions: Option[Schema.GoogleCloudAssuredworkloadsV1WorkloadWorkloadOptions] = None,
	  /** Optional. Permissions granted to the AW Partner SA account for the customer workload */
		partnerPermissions: Option[Schema.GoogleCloudAssuredworkloadsV1WorkloadPartnerPermissions] = None,
	  /** Output only. Represents the KAJ enrollment state of the given workload. */
		kajEnrollmentState: Option[Schema.GoogleCloudAssuredworkloadsV1Workload.KajEnrollmentStateEnum] = None,
	  /** Optional. Labels applied to the workload. */
		labels: Option[Map[String, String]] = None,
	  /** Optional. The resource name of the workload. Format: organizations/{organization}/locations/{location}/workloads/{workload} Read-only. */
		name: Option[String] = None,
	  /** Optional. Indicates whether the e-mail notification for a violation is enabled for a workload. This value will be by default True, and if not present will be considered as true. This should only be updated via updateWorkload call. Any Changes to this field during the createWorkload call will not be honored. This will always be true while creating the workload. */
		violationNotificationsEnabled: Option[Boolean] = None,
	  /** Output only. Count of active Violations in the Workload. */
		complianceStatus: Option[Schema.GoogleCloudAssuredworkloadsV1WorkloadComplianceStatus] = None,
	  /** Optional. Billing account necessary for purchasing services from Sovereign Partners. This field is required for creating SIA/PSN/CNTXT partner workloads. The caller should have 'billing.resourceAssociations.create' IAM permission on this billing-account. The format of this string is billingAccounts/AAAAAA-BBBBBB-CCCCCC */
		partnerServicesBillingAccount: Option[String] = None,
	  /** Output only. Represents the SAA enrollment response of the given workload. SAA enrollment response is queried during GetWorkload call. In failure cases, user friendly error message is shown in SAA details page. */
		saaEnrollmentResponse: Option[Schema.GoogleCloudAssuredworkloadsV1WorkloadSaaEnrollmentResponse] = None,
	  /** Output only. Represents the Ekm Provisioning State of the given workload. */
		ekmProvisioningResponse: Option[Schema.GoogleCloudAssuredworkloadsV1WorkloadEkmProvisioningResponse] = None,
	  /** Output only. The resources associated with this workload. These resources will be created when creating the workload. If any of the projects already exist, the workload creation will fail. Always read only. */
		resources: Option[List[Schema.GoogleCloudAssuredworkloadsV1WorkloadResourceInfo]] = None,
	  /** Output only. Urls for services which are compliant for this Assured Workload, but which are currently disallowed by the ResourceUsageRestriction org policy. Invoke RestrictAllowedResources endpoint to allow your project developers to use these services in their environment. */
		compliantButDisallowedServices: Option[List[String]] = None,
	  /** Output only. Indicates whether resource monitoring is enabled for workload or not. It is true when Resource feed is subscribed to AWM topic and AWM Service Agent Role is binded to AW Service Account for resource Assured workload. */
		resourceMonitoringEnabled: Option[Boolean] = None,
	  /** Input only. Settings used to create a CMEK crypto key. When set, a project with a KMS CMEK key is provisioned. This field is deprecated as of Feb 28, 2022. In order to create a Keyring, callers should specify, ENCRYPTION_KEYS_PROJECT or KEYRING in ResourceSettings.resource_type field. */
		kmsSettings: Option[Schema.GoogleCloudAssuredworkloadsV1WorkloadKMSSettings] = None,
	  /** Input only. Resource properties that are used to customize workload resources. These properties (such as custom project id) will be used to create workload resources if possible. This field is optional. */
		resourceSettings: Option[List[Schema.GoogleCloudAssuredworkloadsV1WorkloadResourceSettings]] = None,
	  /** Optional. ETag of the workload, it is calculated on the basis of the Workload contents. It will be used in Update & Delete operations. */
		etag: Option[String] = None,
	  /** Optional. The billing account used for the resources which are direct children of workload. This billing account is initially associated with the resources created as part of Workload creation. After the initial creation of these resources, the customer can change the assigned billing account. The resource name has the form `billingAccounts/{billing_account_id}`. For example, `billingAccounts/012345-567890-ABCDEF`. */
		billingAccount: Option[String] = None,
	  /** Input only. The parent resource for the resources managed by this Assured Workload. May be either empty or a folder resource which is a child of the Workload parent. If not specified all resources are created under the parent organization. Format: folders/{folder_id} */
		provisionedResourcesParent: Option[String] = None,
	  /** Required. The user-assigned display name of the Workload. When present it must be between 4 to 30 characters. Allowed characters are: lowercase and uppercase letters, numbers, hyphen, and spaces. Example: My Workload */
		displayName: Option[String] = None,
	  /** Optional. Indicates the sovereignty status of the given workload. Currently meant to be used by Europe/Canada customers. */
		enableSovereignControls: Option[Boolean] = None,
	  /** Optional. Partner regime associated with this workload. */
		partner: Option[Schema.GoogleCloudAssuredworkloadsV1Workload.PartnerEnum] = None
	)
	
	object GoogleCloudAssuredworkloadsV1WorkloadWorkloadOptions {
		enum KajEnrollmentTypeEnum extends Enum[KajEnrollmentTypeEnum] { case KAJ_ENROLLMENT_TYPE_UNSPECIFIED, KEY_ACCESS_TRANSPARENCY_OFF }
	}
	case class GoogleCloudAssuredworkloadsV1WorkloadWorkloadOptions(
	  /** Optional. Specifies type of KAJ Enrollment if provided. */
		kajEnrollmentType: Option[Schema.GoogleCloudAssuredworkloadsV1WorkloadWorkloadOptions.KajEnrollmentTypeEnum] = None
	)
	
	object GoogleCloudAssuredworkloadsV1WorkloadResourceSettings {
		enum ResourceTypeEnum extends Enum[ResourceTypeEnum] { case RESOURCE_TYPE_UNSPECIFIED, CONSUMER_PROJECT, CONSUMER_FOLDER, ENCRYPTION_KEYS_PROJECT, KEYRING }
	}
	case class GoogleCloudAssuredworkloadsV1WorkloadResourceSettings(
	  /** User-assigned resource display name. If not empty it will be used to create a resource with the specified name. */
		displayName: Option[String] = None,
	  /** Indicates the type of resource. This field should be specified to correspond the id to the right project type (CONSUMER_PROJECT or ENCRYPTION_KEYS_PROJECT) */
		resourceType: Option[Schema.GoogleCloudAssuredworkloadsV1WorkloadResourceSettings.ResourceTypeEnum] = None,
	  /** Resource identifier. For a project this represents project_id. If the project is already taken, the workload creation will fail. For KeyRing, this represents the keyring_id. For a folder, don't set this value as folder_id is assigned by Google. */
		resourceId: Option[String] = None
	)
	
	object GoogleCloudAssuredworkloadsV1ViolationRemediation {
		enum RemediationTypeEnum extends Enum[RemediationTypeEnum] { case REMEDIATION_TYPE_UNSPECIFIED, REMEDIATION_BOOLEAN_ORG_POLICY_VIOLATION, REMEDIATION_LIST_ALLOWED_VALUES_ORG_POLICY_VIOLATION, REMEDIATION_LIST_DENIED_VALUES_ORG_POLICY_VIOLATION, REMEDIATION_RESTRICT_CMEK_CRYPTO_KEY_PROJECTS_ORG_POLICY_VIOLATION, REMEDIATION_RESOURCE_VIOLATION, REMEDIATION_RESOURCE_VIOLATION_NON_CMEK_SERVICES }
	}
	case class GoogleCloudAssuredworkloadsV1ViolationRemediation(
	  /** Output only. Reemediation type based on the type of org policy values violated */
		remediationType: Option[Schema.GoogleCloudAssuredworkloadsV1ViolationRemediation.RemediationTypeEnum] = None,
	  /** Values that can resolve the violation For example: for list org policy violations, this will either be the list of allowed or denied values */
		compliantValues: Option[List[String]] = None,
	  /** Required. Remediation instructions to resolve violations */
		instructions: Option[Schema.GoogleCloudAssuredworkloadsV1ViolationRemediationInstructions] = None
	)
	
	case class GoogleCloudAssuredworkloadsV1ViolationRemediationInstructions(
	  /** Remediation instructions to resolve violation via cloud console */
		consoleInstructions: Option[Schema.GoogleCloudAssuredworkloadsV1ViolationRemediationInstructionsConsole] = None,
	  /** Remediation instructions to resolve violation via gcloud cli */
		gcloudInstructions: Option[Schema.GoogleCloudAssuredworkloadsV1ViolationRemediationInstructionsGcloud] = None
	)
	
	case class GoogleCloudAssuredworkloadsV1ViolationExceptionContext(
	  /** Timestamp when the violation was acknowledged. */
		acknowledgementTime: Option[String] = None,
	  /** Business justification provided towards the acknowledgement of the violation. */
		comment: Option[String] = None,
	  /** Name of the user (or service account) who acknowledged the violation. */
		userName: Option[String] = None
	)
	
	object GoogleCloudAssuredworkloadsV1WorkloadSaaEnrollmentResponse {
		enum SetupStatusEnum extends Enum[SetupStatusEnum] { case SETUP_STATE_UNSPECIFIED, STATUS_PENDING, STATUS_COMPLETE }
		enum SetupErrorsEnum extends Enum[SetupErrorsEnum] { case SETUP_ERROR_UNSPECIFIED, ERROR_INVALID_BASE_SETUP, ERROR_MISSING_EXTERNAL_SIGNING_KEY, ERROR_NOT_ALL_SERVICES_ENROLLED, ERROR_SETUP_CHECK_FAILED }
	}
	case class GoogleCloudAssuredworkloadsV1WorkloadSaaEnrollmentResponse(
	  /** Indicates SAA enrollment status of a given workload. */
		setupStatus: Option[Schema.GoogleCloudAssuredworkloadsV1WorkloadSaaEnrollmentResponse.SetupStatusEnum] = None,
	  /** Indicates SAA enrollment setup error if any. */
		setupErrors: Option[List[Schema.GoogleCloudAssuredworkloadsV1WorkloadSaaEnrollmentResponse.SetupErrorsEnum]] = None
	)
	
	case class GoogleLongrunningListOperationsResponse(
	  /** A list of operations that matches the specified filter in the request. */
		operations: Option[List[Schema.GoogleLongrunningOperation]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleCloudAssuredworkloadsV1AssetMoveAnalysis(
	  /** List of eligible analyses performed for the asset. */
		analysisGroups: Option[List[Schema.GoogleCloudAssuredworkloadsV1MoveAnalysisGroup]] = None,
	  /** Type of the asset being analyzed. Possible values will be among the ones listed [here](https://cloud.google.com/asset-inventory/docs/supported-asset-types). */
		assetType: Option[String] = None,
	  /** The full resource name of the asset being analyzed. Example: //compute.googleapis.com/projects/my_project_123/zones/zone1/instances/instance1 */
		asset: Option[String] = None
	)
	
	case class GoogleCloudAssuredworkloadsV1WorkloadPartnerPermissions(
	  /** Optional. Allow partner to view support case details for an AXT log */
		accessTransparencyLogsSupportCaseViewer: Option[Boolean] = None,
	  /** Optional. Allow partner to view access approval logs. */
		serviceAccessApprover: Option[Boolean] = None,
	  /** Optional. Allow partner to view violation alerts. */
		assuredWorkloadsMonitoring: Option[Boolean] = None,
	  /** Allow the partner to view inspectability logs and monitoring violations. */
		dataLogsViewer: Option[Boolean] = None
	)
	
	case class GoogleCloudAssuredworkloadsV1ViolationRemediationInstructionsGcloud(
	  /** Additional urls for more information about steps */
		additionalLinks: Option[List[String]] = None,
	  /** Gcloud command to resolve violation */
		gcloudCommands: Option[List[String]] = None,
	  /** Steps to resolve violation via gcloud cli */
		steps: Option[List[String]] = None
	)
	
	case class GoogleCloudAssuredworkloadsV1MoveAnalysisGroup(
	  /** Result of a successful analysis. */
		analysisResult: Option[Schema.GoogleCloudAssuredworkloadsV1MoveAnalysisResult] = None,
	  /** Error details for a failed analysis. */
		error: Option[Schema.GoogleRpcStatus] = None,
	  /** Name of the analysis group. */
		displayName: Option[String] = None
	)
	
	case class GoogleCloudAssuredworkloadsV1MutatePartnerPermissionsRequest(
	  /** Required. The partner permissions to be updated. */
		partnerPermissions: Option[Schema.GoogleCloudAssuredworkloadsV1WorkloadPartnerPermissions] = None,
	  /** Optional. The etag of the workload. If this is provided, it must match the server's etag. */
		etag: Option[String] = None,
	  /** Required. The list of fields to be updated. E.g. update_mask { paths: "partner_permissions.data_logs_viewer"} */
		updateMask: Option[String] = None
	)
	
	case class GoogleCloudAssuredworkloadsV1EnableResourceMonitoringResponse(
	
	)
	
	case class GoogleLongrunningOperation(
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.GoogleRpcStatus] = None,
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None
	)
	
	object GoogleCloudAssuredworkloadsV1WorkloadEkmProvisioningResponse {
		enum EkmProvisioningStateEnum extends Enum[EkmProvisioningStateEnum] { case EKM_PROVISIONING_STATE_UNSPECIFIED, EKM_PROVISIONING_STATE_PENDING, EKM_PROVISIONING_STATE_FAILED, EKM_PROVISIONING_STATE_COMPLETED }
		enum EkmProvisioningErrorDomainEnum extends Enum[EkmProvisioningErrorDomainEnum] { case EKM_PROVISIONING_ERROR_DOMAIN_UNSPECIFIED, UNSPECIFIED_ERROR, GOOGLE_SERVER_ERROR, EXTERNAL_USER_ERROR, EXTERNAL_PARTNER_ERROR, TIMEOUT_ERROR }
		enum EkmProvisioningErrorMappingEnum extends Enum[EkmProvisioningErrorMappingEnum] { case EKM_PROVISIONING_ERROR_MAPPING_UNSPECIFIED, INVALID_SERVICE_ACCOUNT, MISSING_METRICS_SCOPE_ADMIN_PERMISSION, MISSING_EKM_CONNECTION_ADMIN_PERMISSION }
	}
	case class GoogleCloudAssuredworkloadsV1WorkloadEkmProvisioningResponse(
	  /** Indicates Ekm enrollment Provisioning of a given workload. */
		ekmProvisioningState: Option[Schema.GoogleCloudAssuredworkloadsV1WorkloadEkmProvisioningResponse.EkmProvisioningStateEnum] = None,
	  /** Indicates Ekm provisioning error if any. */
		ekmProvisioningErrorDomain: Option[Schema.GoogleCloudAssuredworkloadsV1WorkloadEkmProvisioningResponse.EkmProvisioningErrorDomainEnum] = None,
	  /** Detailed error message if Ekm provisioning fails */
		ekmProvisioningErrorMapping: Option[Schema.GoogleCloudAssuredworkloadsV1WorkloadEkmProvisioningResponse.EkmProvisioningErrorMappingEnum] = None
	)
	
	object GoogleCloudAssuredworkloadsV1WorkloadResourceInfo {
		enum ResourceTypeEnum extends Enum[ResourceTypeEnum] { case RESOURCE_TYPE_UNSPECIFIED, CONSUMER_PROJECT, CONSUMER_FOLDER, ENCRYPTION_KEYS_PROJECT, KEYRING }
	}
	case class GoogleCloudAssuredworkloadsV1WorkloadResourceInfo(
	  /** Resource identifier. For a project this represents project_number. */
		resourceId: Option[String] = None,
	  /** Indicates the type of resource. */
		resourceType: Option[Schema.GoogleCloudAssuredworkloadsV1WorkloadResourceInfo.ResourceTypeEnum] = None
	)
	
	case class GoogleCloudAssuredworkloadsV1ViolationRemediationInstructionsConsole(
	  /** Steps to resolve violation via cloud console */
		steps: Option[List[String]] = None,
	  /** Link to console page where violations can be resolved */
		consoleUris: Option[List[String]] = None,
	  /** Additional urls for more information about steps */
		additionalLinks: Option[List[String]] = None
	)
	
	object GoogleCloudAssuredworkloadsV1CreateWorkloadOperationMetadata {
		enum ComplianceRegimeEnum extends Enum[ComplianceRegimeEnum] { case COMPLIANCE_REGIME_UNSPECIFIED, IL4, CJIS, FEDRAMP_HIGH, FEDRAMP_MODERATE, US_REGIONAL_ACCESS, HIPAA, HITRUST, EU_REGIONS_AND_SUPPORT, CA_REGIONS_AND_SUPPORT, ITAR, AU_REGIONS_AND_US_SUPPORT, ASSURED_WORKLOADS_FOR_PARTNERS, ISR_REGIONS, ISR_REGIONS_AND_SUPPORT, CA_PROTECTED_B, IL5, IL2, JP_REGIONS_AND_SUPPORT, KSA_REGIONS_AND_SUPPORT_WITH_SOVEREIGNTY_CONTROLS, REGIONAL_CONTROLS, HEALTHCARE_AND_LIFE_SCIENCES_CONTROLS, HEALTHCARE_AND_LIFE_SCIENCES_CONTROLS_US_SUPPORT, IRS_1075 }
	}
	case class GoogleCloudAssuredworkloadsV1CreateWorkloadOperationMetadata(
	  /** Optional. The display name of the workload. */
		displayName: Option[String] = None,
	  /** Optional. Time when the operation was created. */
		createTime: Option[String] = None,
	  /** Optional. The parent of the workload. */
		parent: Option[String] = None,
	  /** Optional. Compliance controls that should be applied to the resources managed by the workload. */
		complianceRegime: Option[Schema.GoogleCloudAssuredworkloadsV1CreateWorkloadOperationMetadata.ComplianceRegimeEnum] = None
	)
	
	case class GoogleCloudAssuredworkloadsV1WorkloadComplianceStatus(
	  /** Number of current orgPolicy violations which are not acknowledged. */
		activeViolationCount: Option[Int] = None,
	  /** Number of current resource violations which are not acknowledged. */
		acknowledgedResourceViolationCount: Option[Int] = None,
	  /** Number of current orgPolicy violations which are acknowledged. */
		acknowledgedViolationCount: Option[Int] = None,
	  /** Number of current resource violations which are acknowledged. */
		activeResourceViolationCount: Option[Int] = None
	)
	
	case class GoogleCloudAssuredworkloadsV1RestrictAllowedResourcesResponse(
	
	)
	
	case class GoogleRpcStatus(
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None,
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None
	)
	
	object GoogleCloudAssuredworkloadsV1AcknowledgeViolationRequest {
		enum AcknowledgeTypeEnum extends Enum[AcknowledgeTypeEnum] { case ACKNOWLEDGE_TYPE_UNSPECIFIED, SINGLE_VIOLATION, EXISTING_CHILD_RESOURCE_VIOLATIONS }
	}
	case class GoogleCloudAssuredworkloadsV1AcknowledgeViolationRequest(
	  /** Optional. Acknowledge type of specified violation. */
		acknowledgeType: Option[Schema.GoogleCloudAssuredworkloadsV1AcknowledgeViolationRequest.AcknowledgeTypeEnum] = None,
	  /** Optional. This field is deprecated and will be removed in future version of the API. Name of the OrgPolicy which was modified with non-compliant change and resulted in this violation. Format: projects/{project_number}/policies/{constraint_name} folders/{folder_id}/policies/{constraint_name} organizations/{organization_id}/policies/{constraint_name} */
		nonCompliantOrgPolicy: Option[String] = None,
	  /** Required. Business justification explaining the need for violation acknowledgement */
		comment: Option[String] = None
	)
	
	case class GoogleCloudAssuredworkloadsV1AnalyzeWorkloadMoveResponse(
	  /** The next page token. Is empty if the last page is reached. */
		nextPageToken: Option[String] = None,
	  /** List of analysis results for each asset in scope. */
		assetMoveAnalyses: Option[List[Schema.GoogleCloudAssuredworkloadsV1AssetMoveAnalysis]] = None
	)
}
