package com.boresjo.play.api.google.accessapproval

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaApprovalRequest: Conversion[List[Schema.ApprovalRequest], Option[List[Schema.ApprovalRequest]]] = (fun: List[Schema.ApprovalRequest]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaAugmentedInfo: Conversion[Schema.AugmentedInfo, Option[Schema.AugmentedInfo]] = (fun: Schema.AugmentedInfo) => Option(fun)
		given putSchemaResourceProperties: Conversion[Schema.ResourceProperties, Option[Schema.ResourceProperties]] = (fun: Schema.ResourceProperties) => Option(fun)
		given putSchemaAccessReason: Conversion[Schema.AccessReason, Option[Schema.AccessReason]] = (fun: Schema.AccessReason) => Option(fun)
		given putSchemaAccessLocations: Conversion[Schema.AccessLocations, Option[Schema.AccessLocations]] = (fun: Schema.AccessLocations) => Option(fun)
		given putSchemaApproveDecision: Conversion[Schema.ApproveDecision, Option[Schema.ApproveDecision]] = (fun: Schema.ApproveDecision) => Option(fun)
		given putSchemaDismissDecision: Conversion[Schema.DismissDecision, Option[Schema.DismissDecision]] = (fun: Schema.DismissDecision) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaAccessReasonTypeEnum: Conversion[Schema.AccessReason.TypeEnum, Option[Schema.AccessReason.TypeEnum]] = (fun: Schema.AccessReason.TypeEnum) => Option(fun)
		given putSchemaSignatureInfo: Conversion[Schema.SignatureInfo, Option[Schema.SignatureInfo]] = (fun: Schema.SignatureInfo) => Option(fun)
		given putSchemaSignatureInfoGoogleKeyAlgorithmEnum: Conversion[Schema.SignatureInfo.GoogleKeyAlgorithmEnum, Option[Schema.SignatureInfo.GoogleKeyAlgorithmEnum]] = (fun: Schema.SignatureInfo.GoogleKeyAlgorithmEnum) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putListSchemaEnrolledService: Conversion[List[Schema.EnrolledService], Option[List[Schema.EnrolledService]]] = (fun: List[Schema.EnrolledService]) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaAccessApprovalSettingsRequestScopeMaxWidthPreferenceEnum: Conversion[Schema.AccessApprovalSettings.RequestScopeMaxWidthPreferenceEnum, Option[Schema.AccessApprovalSettings.RequestScopeMaxWidthPreferenceEnum]] = (fun: Schema.AccessApprovalSettings.RequestScopeMaxWidthPreferenceEnum) => Option(fun)
		given putSchemaEnrolledServiceEnrollmentLevelEnum: Conversion[Schema.EnrolledService.EnrollmentLevelEnum, Option[Schema.EnrolledService.EnrollmentLevelEnum]] = (fun: Schema.EnrolledService.EnrollmentLevelEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
