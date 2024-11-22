package com.boresjo.play.api.google.cloudcontrolspartner

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object Workload {
		enum PartnerEnum extends Enum[PartnerEnum] { case PARTNER_UNSPECIFIED, PARTNER_LOCAL_CONTROLS_BY_S3NS, PARTNER_SOVEREIGN_CONTROLS_BY_T_SYSTEMS, PARTNER_SOVEREIGN_CONTROLS_BY_SIA_MINSAIT, PARTNER_SOVEREIGN_CONTROLS_BY_PSN, PARTNER_SOVEREIGN_CONTROLS_BY_CNTXT, PARTNER_SOVEREIGN_CONTROLS_BY_CNTXT_NO_EKM }
	}
	case class Workload(
	  /** Identifier. Format: `organizations/{organization}/locations/{location}/customers/{customer}/workloads/{workload}` */
		name: Option[String] = None,
	  /** Output only. Folder id this workload is associated with */
		folderId: Option[String] = None,
	  /** Output only. Time the resource was created. */
		createTime: Option[String] = None,
	  /** Output only. The name of container folder of the assured workload */
		folder: Option[String] = None,
	  /** Container for workload onboarding steps. */
		workloadOnboardingState: Option[Schema.WorkloadOnboardingState] = None,
	  /** Indicates whether a workload is fully onboarded. */
		isOnboarded: Option[Boolean] = None,
	  /** The project id of the key management project for the workload */
		keyManagementProjectId: Option[String] = None,
	  /** The Google Cloud location of the workload */
		location: Option[String] = None,
	  /** Partner associated with this workload. */
		partner: Option[Schema.Workload.PartnerEnum] = None
	)
	
	case class WorkloadOnboardingState(
	  /** List of workload onboarding steps. */
		onboardingSteps: Option[List[Schema.WorkloadOnboardingStep]] = None
	)
	
	object WorkloadOnboardingStep {
		enum StepEnum extends Enum[StepEnum] { case STEP_UNSPECIFIED, EKM_PROVISIONED, SIGNED_ACCESS_APPROVAL_CONFIGURED }
		enum CompletionStateEnum extends Enum[CompletionStateEnum] { case COMPLETION_STATE_UNSPECIFIED, PENDING, SUCCEEDED, FAILED, NOT_APPLICABLE }
	}
	case class WorkloadOnboardingStep(
	  /** The onboarding step. */
		step: Option[Schema.WorkloadOnboardingStep.StepEnum] = None,
	  /** The starting time of the onboarding step. */
		startTime: Option[String] = None,
	  /** The completion time of the onboarding step. */
		completionTime: Option[String] = None,
	  /** Output only. The completion state of the onboarding step. */
		completionState: Option[Schema.WorkloadOnboardingStep.CompletionStateEnum] = None
	)
	
	case class ListWorkloadsResponse(
	  /** List of customer workloads */
		workloads: Option[List[Schema.Workload]] = None,
	  /** A token that can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class Customer(
	  /** Identifier. Format: `organizations/{organization}/locations/{location}/customers/{customer}` */
		name: Option[String] = None,
	  /** Required. Display name for the customer */
		displayName: Option[String] = None,
	  /** Output only. Container for customer onboarding steps */
		customerOnboardingState: Option[Schema.CustomerOnboardingState] = None,
	  /** Output only. Indicates whether a customer is fully onboarded */
		isOnboarded: Option[Boolean] = None,
	  /** Output only. The customer organization domain, extracted from CRM Organization’s display_name field. e.g. "google.com" */
		organizationDomain: Option[String] = None
	)
	
	case class CustomerOnboardingState(
	  /** List of customer onboarding steps */
		onboardingSteps: Option[List[Schema.CustomerOnboardingStep]] = None
	)
	
	object CustomerOnboardingStep {
		enum StepEnum extends Enum[StepEnum] { case STEP_UNSPECIFIED, KAJ_ENROLLMENT, CUSTOMER_ENVIRONMENT }
		enum CompletionStateEnum extends Enum[CompletionStateEnum] { case COMPLETION_STATE_UNSPECIFIED, PENDING, SUCCEEDED, FAILED, NOT_APPLICABLE }
	}
	case class CustomerOnboardingStep(
	  /** The onboarding step */
		step: Option[Schema.CustomerOnboardingStep.StepEnum] = None,
	  /** The starting time of the onboarding step */
		startTime: Option[String] = None,
	  /** The completion time of the onboarding step */
		completionTime: Option[String] = None,
	  /** Output only. Current state of the step */
		completionState: Option[Schema.CustomerOnboardingStep.CompletionStateEnum] = None
	)
	
	case class ListCustomersResponse(
	  /** List of customers */
		customers: Option[List[Schema.Customer]] = None,
	  /** A token that can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class EkmConnections(
	  /** Identifier. Format: `organizations/{organization}/locations/{location}/customers/{customer}/workloads/{workload}/ekmConnections` */
		name: Option[String] = None,
	  /** The EKM connections associated with the workload */
		ekmConnections: Option[List[Schema.EkmConnection]] = None
	)
	
	object EkmConnection {
		enum ConnectionStateEnum extends Enum[ConnectionStateEnum] { case CONNECTION_STATE_UNSPECIFIED, AVAILABLE, NOT_AVAILABLE, ERROR, PERMISSION_DENIED }
	}
	case class EkmConnection(
	  /** Resource name of the EKM connection in the format: projects/{project}/locations/{location}/ekmConnections/{ekm_connection} */
		connectionName: Option[String] = None,
	  /** Output only. The connection state */
		connectionState: Option[Schema.EkmConnection.ConnectionStateEnum] = None,
	  /** The connection error that occurred if any */
		connectionError: Option[Schema.ConnectionError] = None
	)
	
	case class ConnectionError(
	  /** The error domain for the error */
		errorDomain: Option[String] = None,
	  /** The error message for the error */
		errorMessage: Option[String] = None
	)
	
	object PartnerPermissions {
		enum PartnerPermissionsEnum extends Enum[PartnerPermissionsEnum] { case PERMISSION_UNSPECIFIED, ACCESS_TRANSPARENCY_AND_EMERGENCY_ACCESS_LOGS, ASSURED_WORKLOADS_MONITORING, ACCESS_APPROVAL_REQUESTS, ASSURED_WORKLOADS_EKM_CONNECTION_STATUS, ACCESS_TRANSPARENCY_LOGS_SUPPORT_CASE_VIEWER }
	}
	case class PartnerPermissions(
	  /** Identifier. Format: `organizations/{organization}/locations/{location}/customers/{customer}/workloads/{workload}/partnerPermissions` */
		name: Option[String] = None,
	  /** The partner permissions granted for the workload */
		partnerPermissions: Option[List[Schema.PartnerPermissions.PartnerPermissionsEnum]] = None
	)
	
	case class ListAccessApprovalRequestsResponse(
	  /** List of access approval requests */
		accessApprovalRequests: Option[List[Schema.AccessApprovalRequest]] = None,
	  /** A token that can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Locations that could not be reached. */
		unreachable: Option[List[String]] = None
	)
	
	case class AccessApprovalRequest(
	  /** Identifier. Format: `organizations/{organization}/locations/{location}/customers/{customer}/workloads/{workload}/accessApprovalRequests/{access_approval_request}` */
		name: Option[String] = None,
	  /** The time at which approval was requested. */
		requestTime: Option[String] = None,
	  /** The justification for which approval is being requested. */
		requestedReason: Option[Schema.AccessReason] = None,
	  /** The requested expiration for the approval. If the request is approved, access will be granted from the time of approval until the expiration time. */
		requestedExpirationTime: Option[String] = None
	)
	
	object AccessReason {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, CUSTOMER_INITIATED_SUPPORT, GOOGLE_INITIATED_SERVICE, GOOGLE_INITIATED_REVIEW, THIRD_PARTY_DATA_REQUEST, GOOGLE_RESPONSE_TO_PRODUCTION_ALERT, CLOUD_INITIATED_ACCESS }
	}
	case class AccessReason(
	  /** Type of access justification. */
		`type`: Option[Schema.AccessReason.TypeEnum] = None,
	  /** More detail about certain reason types. See comments for each type above. */
		detail: Option[String] = None
	)
	
	case class Partner(
	  /** Identifier. The resource name of the partner. Format: `organizations/{organization}/locations/{location}/partner` Example: "organizations/123456/locations/us-central1/partner" */
		name: Option[String] = None,
	  /** List of SKUs the partner is offering */
		skus: Option[List[Schema.Sku]] = None,
	  /** List of Google Cloud supported EKM partners supported by the partner */
		ekmSolutions: Option[List[Schema.EkmMetadata]] = None,
	  /** List of Google Cloud regions that the partner sells services to customers. Valid Google Cloud regions found here: https://cloud.google.com/compute/docs/regions-zones */
		operatedCloudRegions: Option[List[String]] = None,
	  /** Google Cloud project ID in the partner's Google Cloud organization for receiving enhanced Logs for Partners. */
		partnerProjectId: Option[String] = None,
	  /** Output only. Time the resource was created */
		createTime: Option[String] = None,
	  /** Output only. The last time the resource was updated */
		updateTime: Option[String] = None
	)
	
	case class Sku(
	  /** Argentum product SKU, that is associated with the partner offerings to customers used by Syntro for billing purposes. SKUs can represent resold Google products or support services. */
		id: Option[String] = None,
	  /** Display name of the product identified by the SKU. A partner may want to show partner branded names for their offerings such as local sovereign cloud solutions. */
		displayName: Option[String] = None
	)
	
	object EkmMetadata {
		enum EkmSolutionEnum extends Enum[EkmSolutionEnum] { case EKM_SOLUTION_UNSPECIFIED, FORTANIX, FUTUREX, THALES, VIRTRU }
	}
	case class EkmMetadata(
	  /** The Cloud EKM partner. */
		ekmSolution: Option[Schema.EkmMetadata.EkmSolutionEnum] = None,
	  /** Endpoint for sending requests to the EKM for key provisioning during Assured Workload creation. */
		ekmEndpointUri: Option[String] = None
	)
	
	case class Empty(
	
	)
	
	case class ListViolationsResponse(
	  /** List of violation */
		violations: Option[List[Schema.Violation]] = None,
	  /** A token that can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Workloads that could not be reached due to permission errors or any other error. Ref: https://google.aip.dev/217 */
		unreachable: Option[List[String]] = None
	)
	
	object Violation {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, RESOLVED, UNRESOLVED, EXCEPTION }
	}
	case class Violation(
	  /** Identifier. Format: `organizations/{organization}/locations/{location}/customers/{customer}/workloads/{workload}/violations/{violation}` */
		name: Option[String] = None,
	  /** Output only. Description for the Violation. e.g. OrgPolicy gcp.resourceLocations has non compliant value. */
		description: Option[String] = None,
	  /** Output only. Time of the event which triggered the Violation. */
		beginTime: Option[String] = None,
	  /** Output only. The last time when the Violation record was updated. */
		updateTime: Option[String] = None,
	  /** Output only. Time of the event which fixed the Violation. If the violation is ACTIVE this will be empty. */
		resolveTime: Option[String] = None,
	  /** Output only. Category under which this violation is mapped. e.g. Location, Service Usage, Access, Encryption, etc. */
		category: Option[String] = None,
	  /** Output only. State of the violation */
		state: Option[Schema.Violation.StateEnum] = None,
	  /** Output only. Immutable. Name of the OrgPolicy which was modified with non-compliant change and resulted this violation. Format: `projects/{project_number}/policies/{constraint_name}` `folders/{folder_id}/policies/{constraint_name}` `organizations/{organization_id}/policies/{constraint_name}` */
		nonCompliantOrgPolicy: Option[String] = None,
	  /** The folder_id of the violation */
		folderId: Option[String] = None,
	  /** Output only. Compliance violation remediation */
		remediation: Option[Schema.Remediation] = None
	)
	
	object Remediation {
		enum RemediationTypeEnum extends Enum[RemediationTypeEnum] { case REMEDIATION_TYPE_UNSPECIFIED, REMEDIATION_BOOLEAN_ORG_POLICY_VIOLATION, REMEDIATION_LIST_ALLOWED_VALUES_ORG_POLICY_VIOLATION, REMEDIATION_LIST_DENIED_VALUES_ORG_POLICY_VIOLATION, REMEDIATION_RESTRICT_CMEK_CRYPTO_KEY_PROJECTS_ORG_POLICY_VIOLATION, REMEDIATION_RESOURCE_VIOLATION }
	}
	case class Remediation(
	  /** Required. Remediation instructions to resolve violations */
		instructions: Option[Schema.Instructions] = None,
	  /** Values that can resolve the violation For example: for list org policy violations, this will either be the list of allowed or denied values */
		compliantValues: Option[List[String]] = None,
	  /** Output only. Remediation type based on the type of org policy values violated */
		remediationType: Option[Schema.Remediation.RemediationTypeEnum] = None
	)
	
	case class Instructions(
	  /** Remediation instructions to resolve violation via gcloud cli */
		gcloudInstructions: Option[Schema.Gcloud] = None,
	  /** Remediation instructions to resolve violation via cloud console */
		consoleInstructions: Option[Schema.Console] = None
	)
	
	case class Gcloud(
	  /** Gcloud command to resolve violation */
		gcloudCommands: Option[List[String]] = None,
	  /** Steps to resolve violation via gcloud cli */
		steps: Option[List[String]] = None,
	  /** Additional urls for more information about steps */
		additionalLinks: Option[List[String]] = None
	)
	
	case class Console(
	  /** Link to console page where violations can be resolved */
		consoleUris: Option[List[String]] = None,
	  /** Steps to resolve violation via cloud console */
		steps: Option[List[String]] = None,
	  /** Additional urls for more information about steps */
		additionalLinks: Option[List[String]] = None
	)
	
	case class OperationMetadata(
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Output only. Server-defined resource path for the target of the operation. */
		target: Option[String] = None,
	  /** Output only. Name of the verb executed by the operation. */
		verb: Option[String] = None,
	  /** Output only. Human-readable status of the operation, if any. */
		statusMessage: Option[String] = None,
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have been cancelled successfully have Operation.error value with a google.rpc.Status.code of 1, corresponding to `Code.CANCELLED`. */
		requestedCancellation: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None
	)
}
