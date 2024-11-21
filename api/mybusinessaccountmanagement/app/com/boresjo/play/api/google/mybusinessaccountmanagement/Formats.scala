package com.boresjo.play.api.google.mybusinessaccountmanagement

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtAcceptInvitationRequest: Format[Schema.AcceptInvitationRequest] = Json.format[Schema.AcceptInvitationRequest]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtDeclineInvitationRequest: Format[Schema.DeclineInvitationRequest] = Json.format[Schema.DeclineInvitationRequest]
	given fmtListInvitationsResponse: Format[Schema.ListInvitationsResponse] = Json.format[Schema.ListInvitationsResponse]
	given fmtInvitation: Format[Schema.Invitation] = Json.format[Schema.Invitation]
	given fmtAccount: Format[Schema.Account] = Json.format[Schema.Account]
	given fmtTargetLocation: Format[Schema.TargetLocation] = Json.format[Schema.TargetLocation]
	given fmtInvitationRoleEnum: Format[Schema.Invitation.RoleEnum] = JsonEnumFormat[Schema.Invitation.RoleEnum]
	given fmtInvitationTargetTypeEnum: Format[Schema.Invitation.TargetTypeEnum] = JsonEnumFormat[Schema.Invitation.TargetTypeEnum]
	given fmtAccountTypeEnum: Format[Schema.Account.TypeEnum] = JsonEnumFormat[Schema.Account.TypeEnum]
	given fmtAccountRoleEnum: Format[Schema.Account.RoleEnum] = JsonEnumFormat[Schema.Account.RoleEnum]
	given fmtAccountVerificationStateEnum: Format[Schema.Account.VerificationStateEnum] = JsonEnumFormat[Schema.Account.VerificationStateEnum]
	given fmtAccountVettedStateEnum: Format[Schema.Account.VettedStateEnum] = JsonEnumFormat[Schema.Account.VettedStateEnum]
	given fmtAccountPermissionLevelEnum: Format[Schema.Account.PermissionLevelEnum] = JsonEnumFormat[Schema.Account.PermissionLevelEnum]
	given fmtOrganizationInfo: Format[Schema.OrganizationInfo] = Json.format[Schema.OrganizationInfo]
	given fmtPostalAddress: Format[Schema.PostalAddress] = Json.format[Schema.PostalAddress]
	given fmtListAccountAdminsResponse: Format[Schema.ListAccountAdminsResponse] = Json.format[Schema.ListAccountAdminsResponse]
	given fmtAdmin: Format[Schema.Admin] = Json.format[Schema.Admin]
	given fmtAdminRoleEnum: Format[Schema.Admin.RoleEnum] = JsonEnumFormat[Schema.Admin.RoleEnum]
	given fmtListLocationAdminsResponse: Format[Schema.ListLocationAdminsResponse] = Json.format[Schema.ListLocationAdminsResponse]
	given fmtTransferLocationRequest: Format[Schema.TransferLocationRequest] = Json.format[Schema.TransferLocationRequest]
	given fmtListAccountsResponse: Format[Schema.ListAccountsResponse] = Json.format[Schema.ListAccountsResponse]
}
