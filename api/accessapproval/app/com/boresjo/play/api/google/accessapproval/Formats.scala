package com.boresjo.play.api.google.accessapproval

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListApprovalRequestsResponse: Format[Schema.ListApprovalRequestsResponse] = Json.format[Schema.ListApprovalRequestsResponse]
	given fmtApprovalRequest: Format[Schema.ApprovalRequest] = Json.format[Schema.ApprovalRequest]
	given fmtAugmentedInfo: Format[Schema.AugmentedInfo] = Json.format[Schema.AugmentedInfo]
	given fmtResourceProperties: Format[Schema.ResourceProperties] = Json.format[Schema.ResourceProperties]
	given fmtAccessReason: Format[Schema.AccessReason] = Json.format[Schema.AccessReason]
	given fmtAccessLocations: Format[Schema.AccessLocations] = Json.format[Schema.AccessLocations]
	given fmtApproveDecision: Format[Schema.ApproveDecision] = Json.format[Schema.ApproveDecision]
	given fmtDismissDecision: Format[Schema.DismissDecision] = Json.format[Schema.DismissDecision]
	given fmtAccessReasonTypeEnum: Format[Schema.AccessReason.TypeEnum] = JsonEnumFormat[Schema.AccessReason.TypeEnum]
	given fmtSignatureInfo: Format[Schema.SignatureInfo] = Json.format[Schema.SignatureInfo]
	given fmtSignatureInfoGoogleKeyAlgorithmEnum: Format[Schema.SignatureInfo.GoogleKeyAlgorithmEnum] = JsonEnumFormat[Schema.SignatureInfo.GoogleKeyAlgorithmEnum]
	given fmtApproveApprovalRequestMessage: Format[Schema.ApproveApprovalRequestMessage] = Json.format[Schema.ApproveApprovalRequestMessage]
	given fmtDismissApprovalRequestMessage: Format[Schema.DismissApprovalRequestMessage] = Json.format[Schema.DismissApprovalRequestMessage]
	given fmtInvalidateApprovalRequestMessage: Format[Schema.InvalidateApprovalRequestMessage] = Json.format[Schema.InvalidateApprovalRequestMessage]
	given fmtAccessApprovalSettings: Format[Schema.AccessApprovalSettings] = Json.format[Schema.AccessApprovalSettings]
	given fmtEnrolledService: Format[Schema.EnrolledService] = Json.format[Schema.EnrolledService]
	given fmtAccessApprovalSettingsRequestScopeMaxWidthPreferenceEnum: Format[Schema.AccessApprovalSettings.RequestScopeMaxWidthPreferenceEnum] = JsonEnumFormat[Schema.AccessApprovalSettings.RequestScopeMaxWidthPreferenceEnum]
	given fmtEnrolledServiceEnrollmentLevelEnum: Format[Schema.EnrolledService.EnrollmentLevelEnum] = JsonEnumFormat[Schema.EnrolledService.EnrollmentLevelEnum]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtAccessApprovalServiceAccount: Format[Schema.AccessApprovalServiceAccount] = Json.format[Schema.AccessApprovalServiceAccount]
}
