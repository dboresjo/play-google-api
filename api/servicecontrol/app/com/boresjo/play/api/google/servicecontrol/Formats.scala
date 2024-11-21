package com.boresjo.play.api.google.servicecontrol

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtCheckRequest: Format[Schema.CheckRequest] = Json.format[Schema.CheckRequest]
	given fmtAttributeContext: Format[Schema.AttributeContext] = Json.format[Schema.AttributeContext]
	given fmtResourceInfo: Format[Schema.ResourceInfo] = Json.format[Schema.ResourceInfo]
	given fmtPeer: Format[Schema.Peer] = Json.format[Schema.Peer]
	given fmtRequest: Format[Schema.Request] = Json.format[Schema.Request]
	given fmtResponse: Format[Schema.Response] = Json.format[Schema.Response]
	given fmtResource: Format[Schema.Resource] = Json.format[Schema.Resource]
	given fmtApi: Format[Schema.Api] = Json.format[Schema.Api]
	given fmtAuth: Format[Schema.Auth] = Json.format[Schema.Auth]
	given fmtCheckResponse: Format[Schema.CheckResponse] = Json.format[Schema.CheckResponse]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtReportRequest: Format[Schema.ReportRequest] = Json.format[Schema.ReportRequest]
	given fmtReportResponse: Format[Schema.ReportResponse] = Json.format[Schema.ReportResponse]
	given fmtAuditLog: Format[Schema.AuditLog] = Json.format[Schema.AuditLog]
	given fmtResourceLocation: Format[Schema.ResourceLocation] = Json.format[Schema.ResourceLocation]
	given fmtAuthenticationInfo: Format[Schema.AuthenticationInfo] = Json.format[Schema.AuthenticationInfo]
	given fmtAuthorizationInfo: Format[Schema.AuthorizationInfo] = Json.format[Schema.AuthorizationInfo]
	given fmtPolicyViolationInfo: Format[Schema.PolicyViolationInfo] = Json.format[Schema.PolicyViolationInfo]
	given fmtRequestMetadata: Format[Schema.RequestMetadata] = Json.format[Schema.RequestMetadata]
	given fmtServiceAccountDelegationInfo: Format[Schema.ServiceAccountDelegationInfo] = Json.format[Schema.ServiceAccountDelegationInfo]
	given fmtServiceDelegationHistory: Format[Schema.ServiceDelegationHistory] = Json.format[Schema.ServiceDelegationHistory]
	given fmtFirstPartyPrincipal: Format[Schema.FirstPartyPrincipal] = Json.format[Schema.FirstPartyPrincipal]
	given fmtThirdPartyPrincipal: Format[Schema.ThirdPartyPrincipal] = Json.format[Schema.ThirdPartyPrincipal]
	given fmtServiceMetadata: Format[Schema.ServiceMetadata] = Json.format[Schema.ServiceMetadata]
	given fmtAuthorizationInfoPermissionTypeEnum: Format[Schema.AuthorizationInfo.PermissionTypeEnum] = JsonEnumFormat[Schema.AuthorizationInfo.PermissionTypeEnum]
	given fmtOrgPolicyViolationInfo: Format[Schema.OrgPolicyViolationInfo] = Json.format[Schema.OrgPolicyViolationInfo]
	given fmtViolationInfo: Format[Schema.ViolationInfo] = Json.format[Schema.ViolationInfo]
	given fmtViolationInfoPolicyTypeEnum: Format[Schema.ViolationInfo.PolicyTypeEnum] = JsonEnumFormat[Schema.ViolationInfo.PolicyTypeEnum]
	given fmtSpanContext: Format[Schema.SpanContext] = Json.format[Schema.SpanContext]
	given fmtV2LogEntry: Format[Schema.V2LogEntry] = Json.format[Schema.V2LogEntry]
	given fmtV2LogEntrySeverityEnum: Format[Schema.V2LogEntry.SeverityEnum] = JsonEnumFormat[Schema.V2LogEntry.SeverityEnum]
	given fmtV2HttpRequest: Format[Schema.V2HttpRequest] = Json.format[Schema.V2HttpRequest]
	given fmtV2LogEntryOperation: Format[Schema.V2LogEntryOperation] = Json.format[Schema.V2LogEntryOperation]
	given fmtV2LogEntrySourceLocation: Format[Schema.V2LogEntrySourceLocation] = Json.format[Schema.V2LogEntrySourceLocation]
	given fmtV2ResourceEvent: Format[Schema.V2ResourceEvent] = Json.format[Schema.V2ResourceEvent]
	given fmtV2ResourceEventTypeEnum: Format[Schema.V2ResourceEvent.TypeEnum] = JsonEnumFormat[Schema.V2ResourceEvent.TypeEnum]
	given fmtV2ResourceEventPathEnum: Format[Schema.V2ResourceEvent.PathEnum] = JsonEnumFormat[Schema.V2ResourceEvent.PathEnum]
}
