package com.boresjo.play.api.google.cloudcontrolspartner

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaWorkloadOnboardingState: Conversion[Schema.WorkloadOnboardingState, Option[Schema.WorkloadOnboardingState]] = (fun: Schema.WorkloadOnboardingState) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaWorkloadPartnerEnum: Conversion[Schema.Workload.PartnerEnum, Option[Schema.Workload.PartnerEnum]] = (fun: Schema.Workload.PartnerEnum) => Option(fun)
		given putListSchemaWorkloadOnboardingStep: Conversion[List[Schema.WorkloadOnboardingStep], Option[List[Schema.WorkloadOnboardingStep]]] = (fun: List[Schema.WorkloadOnboardingStep]) => Option(fun)
		given putSchemaWorkloadOnboardingStepStepEnum: Conversion[Schema.WorkloadOnboardingStep.StepEnum, Option[Schema.WorkloadOnboardingStep.StepEnum]] = (fun: Schema.WorkloadOnboardingStep.StepEnum) => Option(fun)
		given putSchemaWorkloadOnboardingStepCompletionStateEnum: Conversion[Schema.WorkloadOnboardingStep.CompletionStateEnum, Option[Schema.WorkloadOnboardingStep.CompletionStateEnum]] = (fun: Schema.WorkloadOnboardingStep.CompletionStateEnum) => Option(fun)
		given putListSchemaWorkload: Conversion[List[Schema.Workload], Option[List[Schema.Workload]]] = (fun: List[Schema.Workload]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaCustomerOnboardingState: Conversion[Schema.CustomerOnboardingState, Option[Schema.CustomerOnboardingState]] = (fun: Schema.CustomerOnboardingState) => Option(fun)
		given putListSchemaCustomerOnboardingStep: Conversion[List[Schema.CustomerOnboardingStep], Option[List[Schema.CustomerOnboardingStep]]] = (fun: List[Schema.CustomerOnboardingStep]) => Option(fun)
		given putSchemaCustomerOnboardingStepStepEnum: Conversion[Schema.CustomerOnboardingStep.StepEnum, Option[Schema.CustomerOnboardingStep.StepEnum]] = (fun: Schema.CustomerOnboardingStep.StepEnum) => Option(fun)
		given putSchemaCustomerOnboardingStepCompletionStateEnum: Conversion[Schema.CustomerOnboardingStep.CompletionStateEnum, Option[Schema.CustomerOnboardingStep.CompletionStateEnum]] = (fun: Schema.CustomerOnboardingStep.CompletionStateEnum) => Option(fun)
		given putListSchemaCustomer: Conversion[List[Schema.Customer], Option[List[Schema.Customer]]] = (fun: List[Schema.Customer]) => Option(fun)
		given putListSchemaEkmConnection: Conversion[List[Schema.EkmConnection], Option[List[Schema.EkmConnection]]] = (fun: List[Schema.EkmConnection]) => Option(fun)
		given putSchemaEkmConnectionConnectionStateEnum: Conversion[Schema.EkmConnection.ConnectionStateEnum, Option[Schema.EkmConnection.ConnectionStateEnum]] = (fun: Schema.EkmConnection.ConnectionStateEnum) => Option(fun)
		given putSchemaConnectionError: Conversion[Schema.ConnectionError, Option[Schema.ConnectionError]] = (fun: Schema.ConnectionError) => Option(fun)
		given putListSchemaPartnerPermissionsPartnerPermissionsEnum: Conversion[List[Schema.PartnerPermissions.PartnerPermissionsEnum], Option[List[Schema.PartnerPermissions.PartnerPermissionsEnum]]] = (fun: List[Schema.PartnerPermissions.PartnerPermissionsEnum]) => Option(fun)
		given putListSchemaAccessApprovalRequest: Conversion[List[Schema.AccessApprovalRequest], Option[List[Schema.AccessApprovalRequest]]] = (fun: List[Schema.AccessApprovalRequest]) => Option(fun)
		given putSchemaAccessReason: Conversion[Schema.AccessReason, Option[Schema.AccessReason]] = (fun: Schema.AccessReason) => Option(fun)
		given putSchemaAccessReasonTypeEnum: Conversion[Schema.AccessReason.TypeEnum, Option[Schema.AccessReason.TypeEnum]] = (fun: Schema.AccessReason.TypeEnum) => Option(fun)
		given putListSchemaSku: Conversion[List[Schema.Sku], Option[List[Schema.Sku]]] = (fun: List[Schema.Sku]) => Option(fun)
		given putListSchemaEkmMetadata: Conversion[List[Schema.EkmMetadata], Option[List[Schema.EkmMetadata]]] = (fun: List[Schema.EkmMetadata]) => Option(fun)
		given putSchemaEkmMetadataEkmSolutionEnum: Conversion[Schema.EkmMetadata.EkmSolutionEnum, Option[Schema.EkmMetadata.EkmSolutionEnum]] = (fun: Schema.EkmMetadata.EkmSolutionEnum) => Option(fun)
		given putListSchemaViolation: Conversion[List[Schema.Violation], Option[List[Schema.Violation]]] = (fun: List[Schema.Violation]) => Option(fun)
		given putSchemaViolationStateEnum: Conversion[Schema.Violation.StateEnum, Option[Schema.Violation.StateEnum]] = (fun: Schema.Violation.StateEnum) => Option(fun)
		given putSchemaRemediation: Conversion[Schema.Remediation, Option[Schema.Remediation]] = (fun: Schema.Remediation) => Option(fun)
		given putSchemaInstructions: Conversion[Schema.Instructions, Option[Schema.Instructions]] = (fun: Schema.Instructions) => Option(fun)
		given putSchemaRemediationRemediationTypeEnum: Conversion[Schema.Remediation.RemediationTypeEnum, Option[Schema.Remediation.RemediationTypeEnum]] = (fun: Schema.Remediation.RemediationTypeEnum) => Option(fun)
		given putSchemaGcloud: Conversion[Schema.Gcloud, Option[Schema.Gcloud]] = (fun: Schema.Gcloud) => Option(fun)
		given putSchemaConsole: Conversion[Schema.Console, Option[Schema.Console]] = (fun: Schema.Console) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
