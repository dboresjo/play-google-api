package com.boresjo.play.api.google.cloudcontrolspartner

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtWorkload: Format[Schema.Workload] = Json.format[Schema.Workload]
	given fmtWorkloadOnboardingState: Format[Schema.WorkloadOnboardingState] = Json.format[Schema.WorkloadOnboardingState]
	given fmtWorkloadPartnerEnum: Format[Schema.Workload.PartnerEnum] = JsonEnumFormat[Schema.Workload.PartnerEnum]
	given fmtWorkloadOnboardingStep: Format[Schema.WorkloadOnboardingStep] = Json.format[Schema.WorkloadOnboardingStep]
	given fmtWorkloadOnboardingStepStepEnum: Format[Schema.WorkloadOnboardingStep.StepEnum] = JsonEnumFormat[Schema.WorkloadOnboardingStep.StepEnum]
	given fmtWorkloadOnboardingStepCompletionStateEnum: Format[Schema.WorkloadOnboardingStep.CompletionStateEnum] = JsonEnumFormat[Schema.WorkloadOnboardingStep.CompletionStateEnum]
	given fmtListWorkloadsResponse: Format[Schema.ListWorkloadsResponse] = Json.format[Schema.ListWorkloadsResponse]
	given fmtCustomer: Format[Schema.Customer] = Json.format[Schema.Customer]
	given fmtCustomerOnboardingState: Format[Schema.CustomerOnboardingState] = Json.format[Schema.CustomerOnboardingState]
	given fmtCustomerOnboardingStep: Format[Schema.CustomerOnboardingStep] = Json.format[Schema.CustomerOnboardingStep]
	given fmtCustomerOnboardingStepStepEnum: Format[Schema.CustomerOnboardingStep.StepEnum] = JsonEnumFormat[Schema.CustomerOnboardingStep.StepEnum]
	given fmtCustomerOnboardingStepCompletionStateEnum: Format[Schema.CustomerOnboardingStep.CompletionStateEnum] = JsonEnumFormat[Schema.CustomerOnboardingStep.CompletionStateEnum]
	given fmtListCustomersResponse: Format[Schema.ListCustomersResponse] = Json.format[Schema.ListCustomersResponse]
	given fmtEkmConnections: Format[Schema.EkmConnections] = Json.format[Schema.EkmConnections]
	given fmtEkmConnection: Format[Schema.EkmConnection] = Json.format[Schema.EkmConnection]
	given fmtEkmConnectionConnectionStateEnum: Format[Schema.EkmConnection.ConnectionStateEnum] = JsonEnumFormat[Schema.EkmConnection.ConnectionStateEnum]
	given fmtConnectionError: Format[Schema.ConnectionError] = Json.format[Schema.ConnectionError]
	given fmtPartnerPermissions: Format[Schema.PartnerPermissions] = Json.format[Schema.PartnerPermissions]
	given fmtPartnerPermissionsPartnerPermissionsEnum: Format[Schema.PartnerPermissions.PartnerPermissionsEnum] = JsonEnumFormat[Schema.PartnerPermissions.PartnerPermissionsEnum]
	given fmtListAccessApprovalRequestsResponse: Format[Schema.ListAccessApprovalRequestsResponse] = Json.format[Schema.ListAccessApprovalRequestsResponse]
	given fmtAccessApprovalRequest: Format[Schema.AccessApprovalRequest] = Json.format[Schema.AccessApprovalRequest]
	given fmtAccessReason: Format[Schema.AccessReason] = Json.format[Schema.AccessReason]
	given fmtAccessReasonTypeEnum: Format[Schema.AccessReason.TypeEnum] = JsonEnumFormat[Schema.AccessReason.TypeEnum]
	given fmtPartner: Format[Schema.Partner] = Json.format[Schema.Partner]
	given fmtSku: Format[Schema.Sku] = Json.format[Schema.Sku]
	given fmtEkmMetadata: Format[Schema.EkmMetadata] = Json.format[Schema.EkmMetadata]
	given fmtEkmMetadataEkmSolutionEnum: Format[Schema.EkmMetadata.EkmSolutionEnum] = JsonEnumFormat[Schema.EkmMetadata.EkmSolutionEnum]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtListViolationsResponse: Format[Schema.ListViolationsResponse] = Json.format[Schema.ListViolationsResponse]
	given fmtViolation: Format[Schema.Violation] = Json.format[Schema.Violation]
	given fmtViolationStateEnum: Format[Schema.Violation.StateEnum] = JsonEnumFormat[Schema.Violation.StateEnum]
	given fmtRemediation: Format[Schema.Remediation] = Json.format[Schema.Remediation]
	given fmtInstructions: Format[Schema.Instructions] = Json.format[Schema.Instructions]
	given fmtRemediationRemediationTypeEnum: Format[Schema.Remediation.RemediationTypeEnum] = JsonEnumFormat[Schema.Remediation.RemediationTypeEnum]
	given fmtGcloud: Format[Schema.Gcloud] = Json.format[Schema.Gcloud]
	given fmtConsole: Format[Schema.Console] = Json.format[Schema.Console]
	given fmtOperationMetadata: Format[Schema.OperationMetadata] = Json.format[Schema.OperationMetadata]
}
