package com.boresjo.play.api.google.cloudidentity

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtGoogleAppsCloudidentityDevicesV1Device: Format[Schema.GoogleAppsCloudidentityDevicesV1Device] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1Device]
	given fmtGoogleAppsCloudidentityDevicesV1DeviceOwnerTypeEnum: Format[Schema.GoogleAppsCloudidentityDevicesV1Device.OwnerTypeEnum] = JsonEnumFormat[Schema.GoogleAppsCloudidentityDevicesV1Device.OwnerTypeEnum]
	given fmtGoogleAppsCloudidentityDevicesV1DeviceDeviceTypeEnum: Format[Schema.GoogleAppsCloudidentityDevicesV1Device.DeviceTypeEnum] = JsonEnumFormat[Schema.GoogleAppsCloudidentityDevicesV1Device.DeviceTypeEnum]
	given fmtGoogleAppsCloudidentityDevicesV1DeviceEncryptionStateEnum: Format[Schema.GoogleAppsCloudidentityDevicesV1Device.EncryptionStateEnum] = JsonEnumFormat[Schema.GoogleAppsCloudidentityDevicesV1Device.EncryptionStateEnum]
	given fmtGoogleAppsCloudidentityDevicesV1AndroidAttributes: Format[Schema.GoogleAppsCloudidentityDevicesV1AndroidAttributes] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1AndroidAttributes]
	given fmtGoogleAppsCloudidentityDevicesV1DeviceManagementStateEnum: Format[Schema.GoogleAppsCloudidentityDevicesV1Device.ManagementStateEnum] = JsonEnumFormat[Schema.GoogleAppsCloudidentityDevicesV1Device.ManagementStateEnum]
	given fmtGoogleAppsCloudidentityDevicesV1DeviceCompromisedStateEnum: Format[Schema.GoogleAppsCloudidentityDevicesV1Device.CompromisedStateEnum] = JsonEnumFormat[Schema.GoogleAppsCloudidentityDevicesV1Device.CompromisedStateEnum]
	given fmtGoogleAppsCloudidentityDevicesV1EndpointVerificationSpecificAttributes: Format[Schema.GoogleAppsCloudidentityDevicesV1EndpointVerificationSpecificAttributes] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1EndpointVerificationSpecificAttributes]
	given fmtGoogleAppsCloudidentityDevicesV1AndroidAttributesOwnershipPrivilegeEnum: Format[Schema.GoogleAppsCloudidentityDevicesV1AndroidAttributes.OwnershipPrivilegeEnum] = JsonEnumFormat[Schema.GoogleAppsCloudidentityDevicesV1AndroidAttributes.OwnershipPrivilegeEnum]
	given fmtGoogleAppsCloudidentityDevicesV1CertificateAttributes: Format[Schema.GoogleAppsCloudidentityDevicesV1CertificateAttributes] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1CertificateAttributes]
	given fmtGoogleAppsCloudidentityDevicesV1BrowserAttributes: Format[Schema.GoogleAppsCloudidentityDevicesV1BrowserAttributes] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1BrowserAttributes]
	given fmtGoogleAppsCloudidentityDevicesV1CertificateAttributesValidationStateEnum: Format[Schema.GoogleAppsCloudidentityDevicesV1CertificateAttributes.ValidationStateEnum] = JsonEnumFormat[Schema.GoogleAppsCloudidentityDevicesV1CertificateAttributes.ValidationStateEnum]
	given fmtGoogleAppsCloudidentityDevicesV1CertificateTemplate: Format[Schema.GoogleAppsCloudidentityDevicesV1CertificateTemplate] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1CertificateTemplate]
	given fmtGoogleAppsCloudidentityDevicesV1BrowserInfo: Format[Schema.GoogleAppsCloudidentityDevicesV1BrowserInfo] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1BrowserInfo]
	given fmtGoogleAppsCloudidentityDevicesV1BrowserInfoBrowserManagementStateEnum: Format[Schema.GoogleAppsCloudidentityDevicesV1BrowserInfo.BrowserManagementStateEnum] = JsonEnumFormat[Schema.GoogleAppsCloudidentityDevicesV1BrowserInfo.BrowserManagementStateEnum]
	given fmtGoogleAppsCloudidentityDevicesV1BrowserInfoSafeBrowsingProtectionLevelEnum: Format[Schema.GoogleAppsCloudidentityDevicesV1BrowserInfo.SafeBrowsingProtectionLevelEnum] = JsonEnumFormat[Schema.GoogleAppsCloudidentityDevicesV1BrowserInfo.SafeBrowsingProtectionLevelEnum]
	given fmtGoogleAppsCloudidentityDevicesV1BrowserInfoPasswordProtectionWarningTriggerEnum: Format[Schema.GoogleAppsCloudidentityDevicesV1BrowserInfo.PasswordProtectionWarningTriggerEnum] = JsonEnumFormat[Schema.GoogleAppsCloudidentityDevicesV1BrowserInfo.PasswordProtectionWarningTriggerEnum]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtGoogleAppsCloudidentityDevicesV1ListDevicesResponse: Format[Schema.GoogleAppsCloudidentityDevicesV1ListDevicesResponse] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1ListDevicesResponse]
	given fmtGoogleAppsCloudidentityDevicesV1WipeDeviceRequest: Format[Schema.GoogleAppsCloudidentityDevicesV1WipeDeviceRequest] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1WipeDeviceRequest]
	given fmtGoogleAppsCloudidentityDevicesV1CancelWipeDeviceRequest: Format[Schema.GoogleAppsCloudidentityDevicesV1CancelWipeDeviceRequest] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1CancelWipeDeviceRequest]
	given fmtGoogleAppsCloudidentityDevicesV1DeviceUser: Format[Schema.GoogleAppsCloudidentityDevicesV1DeviceUser] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1DeviceUser]
	given fmtGoogleAppsCloudidentityDevicesV1DeviceUserManagementStateEnum: Format[Schema.GoogleAppsCloudidentityDevicesV1DeviceUser.ManagementStateEnum] = JsonEnumFormat[Schema.GoogleAppsCloudidentityDevicesV1DeviceUser.ManagementStateEnum]
	given fmtGoogleAppsCloudidentityDevicesV1DeviceUserCompromisedStateEnum: Format[Schema.GoogleAppsCloudidentityDevicesV1DeviceUser.CompromisedStateEnum] = JsonEnumFormat[Schema.GoogleAppsCloudidentityDevicesV1DeviceUser.CompromisedStateEnum]
	given fmtGoogleAppsCloudidentityDevicesV1DeviceUserPasswordStateEnum: Format[Schema.GoogleAppsCloudidentityDevicesV1DeviceUser.PasswordStateEnum] = JsonEnumFormat[Schema.GoogleAppsCloudidentityDevicesV1DeviceUser.PasswordStateEnum]
	given fmtGoogleAppsCloudidentityDevicesV1ListDeviceUsersResponse: Format[Schema.GoogleAppsCloudidentityDevicesV1ListDeviceUsersResponse] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1ListDeviceUsersResponse]
	given fmtGoogleAppsCloudidentityDevicesV1LookupSelfDeviceUsersResponse: Format[Schema.GoogleAppsCloudidentityDevicesV1LookupSelfDeviceUsersResponse] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1LookupSelfDeviceUsersResponse]
	given fmtGoogleAppsCloudidentityDevicesV1ApproveDeviceUserRequest: Format[Schema.GoogleAppsCloudidentityDevicesV1ApproveDeviceUserRequest] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1ApproveDeviceUserRequest]
	given fmtGoogleAppsCloudidentityDevicesV1BlockDeviceUserRequest: Format[Schema.GoogleAppsCloudidentityDevicesV1BlockDeviceUserRequest] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1BlockDeviceUserRequest]
	given fmtGoogleAppsCloudidentityDevicesV1WipeDeviceUserRequest: Format[Schema.GoogleAppsCloudidentityDevicesV1WipeDeviceUserRequest] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1WipeDeviceUserRequest]
	given fmtGoogleAppsCloudidentityDevicesV1CancelWipeDeviceUserRequest: Format[Schema.GoogleAppsCloudidentityDevicesV1CancelWipeDeviceUserRequest] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1CancelWipeDeviceUserRequest]
	given fmtGoogleAppsCloudidentityDevicesV1ClientState: Format[Schema.GoogleAppsCloudidentityDevicesV1ClientState] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1ClientState]
	given fmtGoogleAppsCloudidentityDevicesV1ClientStateHealthScoreEnum: Format[Schema.GoogleAppsCloudidentityDevicesV1ClientState.HealthScoreEnum] = JsonEnumFormat[Schema.GoogleAppsCloudidentityDevicesV1ClientState.HealthScoreEnum]
	given fmtGoogleAppsCloudidentityDevicesV1ClientStateManagedEnum: Format[Schema.GoogleAppsCloudidentityDevicesV1ClientState.ManagedEnum] = JsonEnumFormat[Schema.GoogleAppsCloudidentityDevicesV1ClientState.ManagedEnum]
	given fmtGoogleAppsCloudidentityDevicesV1ClientStateComplianceStateEnum: Format[Schema.GoogleAppsCloudidentityDevicesV1ClientState.ComplianceStateEnum] = JsonEnumFormat[Schema.GoogleAppsCloudidentityDevicesV1ClientState.ComplianceStateEnum]
	given fmtGoogleAppsCloudidentityDevicesV1CustomAttributeValue: Format[Schema.GoogleAppsCloudidentityDevicesV1CustomAttributeValue] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1CustomAttributeValue]
	given fmtGoogleAppsCloudidentityDevicesV1ClientStateOwnerTypeEnum: Format[Schema.GoogleAppsCloudidentityDevicesV1ClientState.OwnerTypeEnum] = JsonEnumFormat[Schema.GoogleAppsCloudidentityDevicesV1ClientState.OwnerTypeEnum]
	given fmtGoogleAppsCloudidentityDevicesV1ListClientStatesResponse: Format[Schema.GoogleAppsCloudidentityDevicesV1ListClientStatesResponse] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1ListClientStatesResponse]
	given fmtGroup: Format[Schema.Group] = Json.format[Schema.Group]
	given fmtEntityKey: Format[Schema.EntityKey] = Json.format[Schema.EntityKey]
	given fmtDynamicGroupMetadata: Format[Schema.DynamicGroupMetadata] = Json.format[Schema.DynamicGroupMetadata]
	given fmtDynamicGroupQuery: Format[Schema.DynamicGroupQuery] = Json.format[Schema.DynamicGroupQuery]
	given fmtDynamicGroupStatus: Format[Schema.DynamicGroupStatus] = Json.format[Schema.DynamicGroupStatus]
	given fmtDynamicGroupQueryResourceTypeEnum: Format[Schema.DynamicGroupQuery.ResourceTypeEnum] = JsonEnumFormat[Schema.DynamicGroupQuery.ResourceTypeEnum]
	given fmtDynamicGroupStatusStatusEnum: Format[Schema.DynamicGroupStatus.StatusEnum] = JsonEnumFormat[Schema.DynamicGroupStatus.StatusEnum]
	given fmtSecuritySettings: Format[Schema.SecuritySettings] = Json.format[Schema.SecuritySettings]
	given fmtMemberRestriction: Format[Schema.MemberRestriction] = Json.format[Schema.MemberRestriction]
	given fmtRestrictionEvaluation: Format[Schema.RestrictionEvaluation] = Json.format[Schema.RestrictionEvaluation]
	given fmtRestrictionEvaluationStateEnum: Format[Schema.RestrictionEvaluation.StateEnum] = JsonEnumFormat[Schema.RestrictionEvaluation.StateEnum]
	given fmtLookupGroupNameResponse: Format[Schema.LookupGroupNameResponse] = Json.format[Schema.LookupGroupNameResponse]
	given fmtSearchGroupsResponse: Format[Schema.SearchGroupsResponse] = Json.format[Schema.SearchGroupsResponse]
	given fmtListGroupsResponse: Format[Schema.ListGroupsResponse] = Json.format[Schema.ListGroupsResponse]
	given fmtMembership: Format[Schema.Membership] = Json.format[Schema.Membership]
	given fmtMembershipRole: Format[Schema.MembershipRole] = Json.format[Schema.MembershipRole]
	given fmtMembershipTypeEnum: Format[Schema.Membership.TypeEnum] = JsonEnumFormat[Schema.Membership.TypeEnum]
	given fmtMembershipDeliverySettingEnum: Format[Schema.Membership.DeliverySettingEnum] = JsonEnumFormat[Schema.Membership.DeliverySettingEnum]
	given fmtExpiryDetail: Format[Schema.ExpiryDetail] = Json.format[Schema.ExpiryDetail]
	given fmtRestrictionEvaluations: Format[Schema.RestrictionEvaluations] = Json.format[Schema.RestrictionEvaluations]
	given fmtMembershipRoleRestrictionEvaluation: Format[Schema.MembershipRoleRestrictionEvaluation] = Json.format[Schema.MembershipRoleRestrictionEvaluation]
	given fmtMembershipRoleRestrictionEvaluationStateEnum: Format[Schema.MembershipRoleRestrictionEvaluation.StateEnum] = JsonEnumFormat[Schema.MembershipRoleRestrictionEvaluation.StateEnum]
	given fmtLookupMembershipNameResponse: Format[Schema.LookupMembershipNameResponse] = Json.format[Schema.LookupMembershipNameResponse]
	given fmtListMembershipsResponse: Format[Schema.ListMembershipsResponse] = Json.format[Schema.ListMembershipsResponse]
	given fmtModifyMembershipRolesRequest: Format[Schema.ModifyMembershipRolesRequest] = Json.format[Schema.ModifyMembershipRolesRequest]
	given fmtUpdateMembershipRolesParams: Format[Schema.UpdateMembershipRolesParams] = Json.format[Schema.UpdateMembershipRolesParams]
	given fmtModifyMembershipRolesResponse: Format[Schema.ModifyMembershipRolesResponse] = Json.format[Schema.ModifyMembershipRolesResponse]
	given fmtSearchTransitiveMembershipsResponse: Format[Schema.SearchTransitiveMembershipsResponse] = Json.format[Schema.SearchTransitiveMembershipsResponse]
	given fmtMemberRelation: Format[Schema.MemberRelation] = Json.format[Schema.MemberRelation]
	given fmtTransitiveMembershipRole: Format[Schema.TransitiveMembershipRole] = Json.format[Schema.TransitiveMembershipRole]
	given fmtMemberRelationRelationTypeEnum: Format[Schema.MemberRelation.RelationTypeEnum] = JsonEnumFormat[Schema.MemberRelation.RelationTypeEnum]
	given fmtSearchTransitiveGroupsResponse: Format[Schema.SearchTransitiveGroupsResponse] = Json.format[Schema.SearchTransitiveGroupsResponse]
	given fmtGroupRelation: Format[Schema.GroupRelation] = Json.format[Schema.GroupRelation]
	given fmtGroupRelationRelationTypeEnum: Format[Schema.GroupRelation.RelationTypeEnum] = JsonEnumFormat[Schema.GroupRelation.RelationTypeEnum]
	given fmtCheckTransitiveMembershipResponse: Format[Schema.CheckTransitiveMembershipResponse] = Json.format[Schema.CheckTransitiveMembershipResponse]
	given fmtSearchDirectGroupsResponse: Format[Schema.SearchDirectGroupsResponse] = Json.format[Schema.SearchDirectGroupsResponse]
	given fmtMembershipRelation: Format[Schema.MembershipRelation] = Json.format[Schema.MembershipRelation]
	given fmtInboundSamlSsoProfile: Format[Schema.InboundSamlSsoProfile] = Json.format[Schema.InboundSamlSsoProfile]
	given fmtSamlIdpConfig: Format[Schema.SamlIdpConfig] = Json.format[Schema.SamlIdpConfig]
	given fmtSamlSpConfig: Format[Schema.SamlSpConfig] = Json.format[Schema.SamlSpConfig]
	given fmtListInboundSamlSsoProfilesResponse: Format[Schema.ListInboundSamlSsoProfilesResponse] = Json.format[Schema.ListInboundSamlSsoProfilesResponse]
	given fmtIdpCredential: Format[Schema.IdpCredential] = Json.format[Schema.IdpCredential]
	given fmtRsaPublicKeyInfo: Format[Schema.RsaPublicKeyInfo] = Json.format[Schema.RsaPublicKeyInfo]
	given fmtDsaPublicKeyInfo: Format[Schema.DsaPublicKeyInfo] = Json.format[Schema.DsaPublicKeyInfo]
	given fmtListIdpCredentialsResponse: Format[Schema.ListIdpCredentialsResponse] = Json.format[Schema.ListIdpCredentialsResponse]
	given fmtAddIdpCredentialRequest: Format[Schema.AddIdpCredentialRequest] = Json.format[Schema.AddIdpCredentialRequest]
	given fmtInboundSsoAssignment: Format[Schema.InboundSsoAssignment] = Json.format[Schema.InboundSsoAssignment]
	given fmtInboundSsoAssignmentSsoModeEnum: Format[Schema.InboundSsoAssignment.SsoModeEnum] = JsonEnumFormat[Schema.InboundSsoAssignment.SsoModeEnum]
	given fmtSamlSsoInfo: Format[Schema.SamlSsoInfo] = Json.format[Schema.SamlSsoInfo]
	given fmtSignInBehavior: Format[Schema.SignInBehavior] = Json.format[Schema.SignInBehavior]
	given fmtSignInBehaviorRedirectConditionEnum: Format[Schema.SignInBehavior.RedirectConditionEnum] = JsonEnumFormat[Schema.SignInBehavior.RedirectConditionEnum]
	given fmtListInboundSsoAssignmentsResponse: Format[Schema.ListInboundSsoAssignmentsResponse] = Json.format[Schema.ListInboundSsoAssignmentsResponse]
	given fmtUserInvitation: Format[Schema.UserInvitation] = Json.format[Schema.UserInvitation]
	given fmtUserInvitationStateEnum: Format[Schema.UserInvitation.StateEnum] = JsonEnumFormat[Schema.UserInvitation.StateEnum]
	given fmtListUserInvitationsResponse: Format[Schema.ListUserInvitationsResponse] = Json.format[Schema.ListUserInvitationsResponse]
	given fmtSendUserInvitationRequest: Format[Schema.SendUserInvitationRequest] = Json.format[Schema.SendUserInvitationRequest]
	given fmtCancelUserInvitationRequest: Format[Schema.CancelUserInvitationRequest] = Json.format[Schema.CancelUserInvitationRequest]
	given fmtIsInvitableUserResponse: Format[Schema.IsInvitableUserResponse] = Json.format[Schema.IsInvitableUserResponse]
	given fmtGetMembershipGraphResponse: Format[Schema.GetMembershipGraphResponse] = Json.format[Schema.GetMembershipGraphResponse]
	given fmtMembershipAdjacencyList: Format[Schema.MembershipAdjacencyList] = Json.format[Schema.MembershipAdjacencyList]
	given fmtGoogleAppsCloudidentityDevicesV1ApproveDeviceUserResponse: Format[Schema.GoogleAppsCloudidentityDevicesV1ApproveDeviceUserResponse] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1ApproveDeviceUserResponse]
	given fmtGoogleAppsCloudidentityDevicesV1BlockDeviceUserResponse: Format[Schema.GoogleAppsCloudidentityDevicesV1BlockDeviceUserResponse] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1BlockDeviceUserResponse]
	given fmtGoogleAppsCloudidentityDevicesV1WipeDeviceUserResponse: Format[Schema.GoogleAppsCloudidentityDevicesV1WipeDeviceUserResponse] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1WipeDeviceUserResponse]
	given fmtGoogleAppsCloudidentityDevicesV1CancelWipeDeviceUserResponse: Format[Schema.GoogleAppsCloudidentityDevicesV1CancelWipeDeviceUserResponse] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1CancelWipeDeviceUserResponse]
	given fmtGoogleAppsCloudidentityDevicesV1WipeDeviceResponse: Format[Schema.GoogleAppsCloudidentityDevicesV1WipeDeviceResponse] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1WipeDeviceResponse]
	given fmtGoogleAppsCloudidentityDevicesV1CancelWipeDeviceResponse: Format[Schema.GoogleAppsCloudidentityDevicesV1CancelWipeDeviceResponse] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1CancelWipeDeviceResponse]
	given fmtGoogleAppsCloudidentityDevicesV1CreateDeviceMetadata: Format[Schema.GoogleAppsCloudidentityDevicesV1CreateDeviceMetadata] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1CreateDeviceMetadata]
	given fmtGoogleAppsCloudidentityDevicesV1DeleteDeviceMetadata: Format[Schema.GoogleAppsCloudidentityDevicesV1DeleteDeviceMetadata] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1DeleteDeviceMetadata]
	given fmtGoogleAppsCloudidentityDevicesV1UpdateDeviceMetadata: Format[Schema.GoogleAppsCloudidentityDevicesV1UpdateDeviceMetadata] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1UpdateDeviceMetadata]
	given fmtGoogleAppsCloudidentityDevicesV1WipeDeviceMetadata: Format[Schema.GoogleAppsCloudidentityDevicesV1WipeDeviceMetadata] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1WipeDeviceMetadata]
	given fmtGoogleAppsCloudidentityDevicesV1CancelWipeDeviceMetadata: Format[Schema.GoogleAppsCloudidentityDevicesV1CancelWipeDeviceMetadata] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1CancelWipeDeviceMetadata]
	given fmtGoogleAppsCloudidentityDevicesV1DeleteDeviceUserMetadata: Format[Schema.GoogleAppsCloudidentityDevicesV1DeleteDeviceUserMetadata] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1DeleteDeviceUserMetadata]
	given fmtGoogleAppsCloudidentityDevicesV1ApproveDeviceUserMetadata: Format[Schema.GoogleAppsCloudidentityDevicesV1ApproveDeviceUserMetadata] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1ApproveDeviceUserMetadata]
	given fmtGoogleAppsCloudidentityDevicesV1SignoutDeviceUserMetadata: Format[Schema.GoogleAppsCloudidentityDevicesV1SignoutDeviceUserMetadata] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1SignoutDeviceUserMetadata]
	given fmtGoogleAppsCloudidentityDevicesV1BlockDeviceUserMetadata: Format[Schema.GoogleAppsCloudidentityDevicesV1BlockDeviceUserMetadata] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1BlockDeviceUserMetadata]
	given fmtGoogleAppsCloudidentityDevicesV1WipeDeviceUserMetadata: Format[Schema.GoogleAppsCloudidentityDevicesV1WipeDeviceUserMetadata] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1WipeDeviceUserMetadata]
	given fmtGoogleAppsCloudidentityDevicesV1CancelWipeDeviceUserMetadata: Format[Schema.GoogleAppsCloudidentityDevicesV1CancelWipeDeviceUserMetadata] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1CancelWipeDeviceUserMetadata]
	given fmtGoogleAppsCloudidentityDevicesV1ListEndpointAppsMetadata: Format[Schema.GoogleAppsCloudidentityDevicesV1ListEndpointAppsMetadata] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1ListEndpointAppsMetadata]
	given fmtGoogleAppsCloudidentityDevicesV1UpdateClientStateMetadata: Format[Schema.GoogleAppsCloudidentityDevicesV1UpdateClientStateMetadata] = Json.format[Schema.GoogleAppsCloudidentityDevicesV1UpdateClientStateMetadata]
	given fmtCreateGroupMetadata: Format[Schema.CreateGroupMetadata] = Json.format[Schema.CreateGroupMetadata]
	given fmtDeleteGroupMetadata: Format[Schema.DeleteGroupMetadata] = Json.format[Schema.DeleteGroupMetadata]
	given fmtUpdateGroupMetadata: Format[Schema.UpdateGroupMetadata] = Json.format[Schema.UpdateGroupMetadata]
	given fmtCreateMembershipMetadata: Format[Schema.CreateMembershipMetadata] = Json.format[Schema.CreateMembershipMetadata]
	given fmtDeleteMembershipMetadata: Format[Schema.DeleteMembershipMetadata] = Json.format[Schema.DeleteMembershipMetadata]
	given fmtUpdateMembershipMetadata: Format[Schema.UpdateMembershipMetadata] = Json.format[Schema.UpdateMembershipMetadata]
	given fmtGetMembershipGraphMetadata: Format[Schema.GetMembershipGraphMetadata] = Json.format[Schema.GetMembershipGraphMetadata]
	given fmtAddIdpCredentialOperationMetadata: Format[Schema.AddIdpCredentialOperationMetadata] = Json.format[Schema.AddIdpCredentialOperationMetadata]
	given fmtCreateInboundSamlSsoProfileOperationMetadata: Format[Schema.CreateInboundSamlSsoProfileOperationMetadata] = Json.format[Schema.CreateInboundSamlSsoProfileOperationMetadata]
	given fmtDeleteIdpCredentialOperationMetadata: Format[Schema.DeleteIdpCredentialOperationMetadata] = Json.format[Schema.DeleteIdpCredentialOperationMetadata]
	given fmtDeleteInboundSamlSsoProfileOperationMetadata: Format[Schema.DeleteInboundSamlSsoProfileOperationMetadata] = Json.format[Schema.DeleteInboundSamlSsoProfileOperationMetadata]
	given fmtUpdateInboundSamlSsoProfileOperationMetadata: Format[Schema.UpdateInboundSamlSsoProfileOperationMetadata] = Json.format[Schema.UpdateInboundSamlSsoProfileOperationMetadata]
	given fmtCreateInboundSsoAssignmentOperationMetadata: Format[Schema.CreateInboundSsoAssignmentOperationMetadata] = Json.format[Schema.CreateInboundSsoAssignmentOperationMetadata]
	given fmtDeleteInboundSsoAssignmentOperationMetadata: Format[Schema.DeleteInboundSsoAssignmentOperationMetadata] = Json.format[Schema.DeleteInboundSsoAssignmentOperationMetadata]
	given fmtUpdateInboundSsoAssignmentOperationMetadata: Format[Schema.UpdateInboundSsoAssignmentOperationMetadata] = Json.format[Schema.UpdateInboundSsoAssignmentOperationMetadata]
}