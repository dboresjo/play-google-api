package com.boresjo.play.api.google.certificatemanager

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListOperationsResponse: Format[Schema.ListOperationsResponse] = Json.format[Schema.ListOperationsResponse]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtCancelOperationRequest: Format[Schema.CancelOperationRequest] = Json.format[Schema.CancelOperationRequest]
	given fmtListCertificatesResponse: Format[Schema.ListCertificatesResponse] = Json.format[Schema.ListCertificatesResponse]
	given fmtCertificate: Format[Schema.Certificate] = Json.format[Schema.Certificate]
	given fmtSelfManagedCertificate: Format[Schema.SelfManagedCertificate] = Json.format[Schema.SelfManagedCertificate]
	given fmtManagedCertificate: Format[Schema.ManagedCertificate] = Json.format[Schema.ManagedCertificate]
	given fmtCertificateScopeEnum: Format[Schema.Certificate.ScopeEnum] = JsonEnumFormat[Schema.Certificate.ScopeEnum]
	given fmtManagedCertificateStateEnum: Format[Schema.ManagedCertificate.StateEnum] = JsonEnumFormat[Schema.ManagedCertificate.StateEnum]
	given fmtProvisioningIssue: Format[Schema.ProvisioningIssue] = Json.format[Schema.ProvisioningIssue]
	given fmtAuthorizationAttemptInfo: Format[Schema.AuthorizationAttemptInfo] = Json.format[Schema.AuthorizationAttemptInfo]
	given fmtProvisioningIssueReasonEnum: Format[Schema.ProvisioningIssue.ReasonEnum] = JsonEnumFormat[Schema.ProvisioningIssue.ReasonEnum]
	given fmtAuthorizationAttemptInfoStateEnum: Format[Schema.AuthorizationAttemptInfo.StateEnum] = JsonEnumFormat[Schema.AuthorizationAttemptInfo.StateEnum]
	given fmtAuthorizationAttemptInfoFailureReasonEnum: Format[Schema.AuthorizationAttemptInfo.FailureReasonEnum] = JsonEnumFormat[Schema.AuthorizationAttemptInfo.FailureReasonEnum]
	given fmtListCertificateMapsResponse: Format[Schema.ListCertificateMapsResponse] = Json.format[Schema.ListCertificateMapsResponse]
	given fmtCertificateMap: Format[Schema.CertificateMap] = Json.format[Schema.CertificateMap]
	given fmtGclbTarget: Format[Schema.GclbTarget] = Json.format[Schema.GclbTarget]
	given fmtIpConfig: Format[Schema.IpConfig] = Json.format[Schema.IpConfig]
	given fmtListCertificateMapEntriesResponse: Format[Schema.ListCertificateMapEntriesResponse] = Json.format[Schema.ListCertificateMapEntriesResponse]
	given fmtCertificateMapEntry: Format[Schema.CertificateMapEntry] = Json.format[Schema.CertificateMapEntry]
	given fmtCertificateMapEntryMatcherEnum: Format[Schema.CertificateMapEntry.MatcherEnum] = JsonEnumFormat[Schema.CertificateMapEntry.MatcherEnum]
	given fmtCertificateMapEntryStateEnum: Format[Schema.CertificateMapEntry.StateEnum] = JsonEnumFormat[Schema.CertificateMapEntry.StateEnum]
	given fmtListDnsAuthorizationsResponse: Format[Schema.ListDnsAuthorizationsResponse] = Json.format[Schema.ListDnsAuthorizationsResponse]
	given fmtDnsAuthorization: Format[Schema.DnsAuthorization] = Json.format[Schema.DnsAuthorization]
	given fmtDnsResourceRecord: Format[Schema.DnsResourceRecord] = Json.format[Schema.DnsResourceRecord]
	given fmtDnsAuthorizationTypeEnum: Format[Schema.DnsAuthorization.TypeEnum] = JsonEnumFormat[Schema.DnsAuthorization.TypeEnum]
	given fmtListCertificateIssuanceConfigsResponse: Format[Schema.ListCertificateIssuanceConfigsResponse] = Json.format[Schema.ListCertificateIssuanceConfigsResponse]
	given fmtCertificateIssuanceConfig: Format[Schema.CertificateIssuanceConfig] = Json.format[Schema.CertificateIssuanceConfig]
	given fmtCertificateAuthorityConfig: Format[Schema.CertificateAuthorityConfig] = Json.format[Schema.CertificateAuthorityConfig]
	given fmtCertificateIssuanceConfigKeyAlgorithmEnum: Format[Schema.CertificateIssuanceConfig.KeyAlgorithmEnum] = JsonEnumFormat[Schema.CertificateIssuanceConfig.KeyAlgorithmEnum]
	given fmtCertificateAuthorityServiceConfig: Format[Schema.CertificateAuthorityServiceConfig] = Json.format[Schema.CertificateAuthorityServiceConfig]
	given fmtListTrustConfigsResponse: Format[Schema.ListTrustConfigsResponse] = Json.format[Schema.ListTrustConfigsResponse]
	given fmtTrustConfig: Format[Schema.TrustConfig] = Json.format[Schema.TrustConfig]
	given fmtTrustStore: Format[Schema.TrustStore] = Json.format[Schema.TrustStore]
	given fmtAllowlistedCertificate: Format[Schema.AllowlistedCertificate] = Json.format[Schema.AllowlistedCertificate]
	given fmtTrustAnchor: Format[Schema.TrustAnchor] = Json.format[Schema.TrustAnchor]
	given fmtIntermediateCA: Format[Schema.IntermediateCA] = Json.format[Schema.IntermediateCA]
	given fmtListLocationsResponse: Format[Schema.ListLocationsResponse] = Json.format[Schema.ListLocationsResponse]
	given fmtLocation: Format[Schema.Location] = Json.format[Schema.Location]
	given fmtOperationMetadata: Format[Schema.OperationMetadata] = Json.format[Schema.OperationMetadata]
}
