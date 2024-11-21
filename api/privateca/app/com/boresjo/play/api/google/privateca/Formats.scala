package com.boresjo.play.api.google.privateca

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListLocationsResponse: Format[Schema.ListLocationsResponse] = Json.format[Schema.ListLocationsResponse]
	given fmtLocation: Format[Schema.Location] = Json.format[Schema.Location]
	given fmtListOperationsResponse: Format[Schema.ListOperationsResponse] = Json.format[Schema.ListOperationsResponse]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtCancelOperationRequest: Format[Schema.CancelOperationRequest] = Json.format[Schema.CancelOperationRequest]
	given fmtCertificate: Format[Schema.Certificate] = Json.format[Schema.Certificate]
	given fmtCertificateConfig: Format[Schema.CertificateConfig] = Json.format[Schema.CertificateConfig]
	given fmtCertificateSubjectModeEnum: Format[Schema.Certificate.SubjectModeEnum] = JsonEnumFormat[Schema.Certificate.SubjectModeEnum]
	given fmtRevocationDetails: Format[Schema.RevocationDetails] = Json.format[Schema.RevocationDetails]
	given fmtCertificateDescription: Format[Schema.CertificateDescription] = Json.format[Schema.CertificateDescription]
	given fmtSubjectConfig: Format[Schema.SubjectConfig] = Json.format[Schema.SubjectConfig]
	given fmtX509Parameters: Format[Schema.X509Parameters] = Json.format[Schema.X509Parameters]
	given fmtPublicKey: Format[Schema.PublicKey] = Json.format[Schema.PublicKey]
	given fmtCertificateConfigKeyId: Format[Schema.CertificateConfigKeyId] = Json.format[Schema.CertificateConfigKeyId]
	given fmtSubject: Format[Schema.Subject] = Json.format[Schema.Subject]
	given fmtSubjectAltNames: Format[Schema.SubjectAltNames] = Json.format[Schema.SubjectAltNames]
	given fmtX509Extension: Format[Schema.X509Extension] = Json.format[Schema.X509Extension]
	given fmtObjectId: Format[Schema.ObjectId] = Json.format[Schema.ObjectId]
	given fmtKeyUsage: Format[Schema.KeyUsage] = Json.format[Schema.KeyUsage]
	given fmtCaOptions: Format[Schema.CaOptions] = Json.format[Schema.CaOptions]
	given fmtNameConstraints: Format[Schema.NameConstraints] = Json.format[Schema.NameConstraints]
	given fmtKeyUsageOptions: Format[Schema.KeyUsageOptions] = Json.format[Schema.KeyUsageOptions]
	given fmtExtendedKeyUsageOptions: Format[Schema.ExtendedKeyUsageOptions] = Json.format[Schema.ExtendedKeyUsageOptions]
	given fmtPublicKeyFormatEnum: Format[Schema.PublicKey.FormatEnum] = JsonEnumFormat[Schema.PublicKey.FormatEnum]
	given fmtRevocationDetailsRevocationStateEnum: Format[Schema.RevocationDetails.RevocationStateEnum] = JsonEnumFormat[Schema.RevocationDetails.RevocationStateEnum]
	given fmtSubjectDescription: Format[Schema.SubjectDescription] = Json.format[Schema.SubjectDescription]
	given fmtKeyId: Format[Schema.KeyId] = Json.format[Schema.KeyId]
	given fmtCertificateFingerprint: Format[Schema.CertificateFingerprint] = Json.format[Schema.CertificateFingerprint]
	given fmtListCertificatesResponse: Format[Schema.ListCertificatesResponse] = Json.format[Schema.ListCertificatesResponse]
	given fmtRevokeCertificateRequest: Format[Schema.RevokeCertificateRequest] = Json.format[Schema.RevokeCertificateRequest]
	given fmtRevokeCertificateRequestReasonEnum: Format[Schema.RevokeCertificateRequest.ReasonEnum] = JsonEnumFormat[Schema.RevokeCertificateRequest.ReasonEnum]
	given fmtActivateCertificateAuthorityRequest: Format[Schema.ActivateCertificateAuthorityRequest] = Json.format[Schema.ActivateCertificateAuthorityRequest]
	given fmtSubordinateConfig: Format[Schema.SubordinateConfig] = Json.format[Schema.SubordinateConfig]
	given fmtSubordinateConfigChain: Format[Schema.SubordinateConfigChain] = Json.format[Schema.SubordinateConfigChain]
	given fmtCertificateAuthority: Format[Schema.CertificateAuthority] = Json.format[Schema.CertificateAuthority]
	given fmtCertificateAuthorityTypeEnum: Format[Schema.CertificateAuthority.TypeEnum] = JsonEnumFormat[Schema.CertificateAuthority.TypeEnum]
	given fmtKeyVersionSpec: Format[Schema.KeyVersionSpec] = Json.format[Schema.KeyVersionSpec]
	given fmtCertificateAuthorityTierEnum: Format[Schema.CertificateAuthority.TierEnum] = JsonEnumFormat[Schema.CertificateAuthority.TierEnum]
	given fmtCertificateAuthorityStateEnum: Format[Schema.CertificateAuthority.StateEnum] = JsonEnumFormat[Schema.CertificateAuthority.StateEnum]
	given fmtAccessUrls: Format[Schema.AccessUrls] = Json.format[Schema.AccessUrls]
	given fmtKeyVersionSpecAlgorithmEnum: Format[Schema.KeyVersionSpec.AlgorithmEnum] = JsonEnumFormat[Schema.KeyVersionSpec.AlgorithmEnum]
	given fmtDisableCertificateAuthorityRequest: Format[Schema.DisableCertificateAuthorityRequest] = Json.format[Schema.DisableCertificateAuthorityRequest]
	given fmtEnableCertificateAuthorityRequest: Format[Schema.EnableCertificateAuthorityRequest] = Json.format[Schema.EnableCertificateAuthorityRequest]
	given fmtFetchCertificateAuthorityCsrResponse: Format[Schema.FetchCertificateAuthorityCsrResponse] = Json.format[Schema.FetchCertificateAuthorityCsrResponse]
	given fmtListCertificateAuthoritiesResponse: Format[Schema.ListCertificateAuthoritiesResponse] = Json.format[Schema.ListCertificateAuthoritiesResponse]
	given fmtUndeleteCertificateAuthorityRequest: Format[Schema.UndeleteCertificateAuthorityRequest] = Json.format[Schema.UndeleteCertificateAuthorityRequest]
	given fmtCaPool: Format[Schema.CaPool] = Json.format[Schema.CaPool]
	given fmtCaPoolTierEnum: Format[Schema.CaPool.TierEnum] = JsonEnumFormat[Schema.CaPool.TierEnum]
	given fmtIssuancePolicy: Format[Schema.IssuancePolicy] = Json.format[Schema.IssuancePolicy]
	given fmtPublishingOptions: Format[Schema.PublishingOptions] = Json.format[Schema.PublishingOptions]
	given fmtAllowedKeyType: Format[Schema.AllowedKeyType] = Json.format[Schema.AllowedKeyType]
	given fmtIssuanceModes: Format[Schema.IssuanceModes] = Json.format[Schema.IssuanceModes]
	given fmtCertificateIdentityConstraints: Format[Schema.CertificateIdentityConstraints] = Json.format[Schema.CertificateIdentityConstraints]
	given fmtCertificateExtensionConstraints: Format[Schema.CertificateExtensionConstraints] = Json.format[Schema.CertificateExtensionConstraints]
	given fmtRsaKeyType: Format[Schema.RsaKeyType] = Json.format[Schema.RsaKeyType]
	given fmtEcKeyType: Format[Schema.EcKeyType] = Json.format[Schema.EcKeyType]
	given fmtEcKeyTypeSignatureAlgorithmEnum: Format[Schema.EcKeyType.SignatureAlgorithmEnum] = JsonEnumFormat[Schema.EcKeyType.SignatureAlgorithmEnum]
	given fmtExpr: Format[Schema.Expr] = Json.format[Schema.Expr]
	given fmtCertificateExtensionConstraintsKnownExtensionsEnum: Format[Schema.CertificateExtensionConstraints.KnownExtensionsEnum] = JsonEnumFormat[Schema.CertificateExtensionConstraints.KnownExtensionsEnum]
	given fmtPublishingOptionsEncodingFormatEnum: Format[Schema.PublishingOptions.EncodingFormatEnum] = JsonEnumFormat[Schema.PublishingOptions.EncodingFormatEnum]
	given fmtListCaPoolsResponse: Format[Schema.ListCaPoolsResponse] = Json.format[Schema.ListCaPoolsResponse]
	given fmtFetchCaCertsRequest: Format[Schema.FetchCaCertsRequest] = Json.format[Schema.FetchCaCertsRequest]
	given fmtFetchCaCertsResponse: Format[Schema.FetchCaCertsResponse] = Json.format[Schema.FetchCaCertsResponse]
	given fmtCertChain: Format[Schema.CertChain] = Json.format[Schema.CertChain]
	given fmtCertificateRevocationList: Format[Schema.CertificateRevocationList] = Json.format[Schema.CertificateRevocationList]
	given fmtRevokedCertificate: Format[Schema.RevokedCertificate] = Json.format[Schema.RevokedCertificate]
	given fmtCertificateRevocationListStateEnum: Format[Schema.CertificateRevocationList.StateEnum] = JsonEnumFormat[Schema.CertificateRevocationList.StateEnum]
	given fmtRevokedCertificateRevocationReasonEnum: Format[Schema.RevokedCertificate.RevocationReasonEnum] = JsonEnumFormat[Schema.RevokedCertificate.RevocationReasonEnum]
	given fmtListCertificateRevocationListsResponse: Format[Schema.ListCertificateRevocationListsResponse] = Json.format[Schema.ListCertificateRevocationListsResponse]
	given fmtCertificateTemplate: Format[Schema.CertificateTemplate] = Json.format[Schema.CertificateTemplate]
	given fmtListCertificateTemplatesResponse: Format[Schema.ListCertificateTemplatesResponse] = Json.format[Schema.ListCertificateTemplatesResponse]
	given fmtSetIamPolicyRequest: Format[Schema.SetIamPolicyRequest] = Json.format[Schema.SetIamPolicyRequest]
	given fmtPolicy: Format[Schema.Policy] = Json.format[Schema.Policy]
	given fmtBinding: Format[Schema.Binding] = Json.format[Schema.Binding]
	given fmtAuditConfig: Format[Schema.AuditConfig] = Json.format[Schema.AuditConfig]
	given fmtAuditLogConfig: Format[Schema.AuditLogConfig] = Json.format[Schema.AuditLogConfig]
	given fmtAuditLogConfigLogTypeEnum: Format[Schema.AuditLogConfig.LogTypeEnum] = JsonEnumFormat[Schema.AuditLogConfig.LogTypeEnum]
	given fmtTestIamPermissionsRequest: Format[Schema.TestIamPermissionsRequest] = Json.format[Schema.TestIamPermissionsRequest]
	given fmtTestIamPermissionsResponse: Format[Schema.TestIamPermissionsResponse] = Json.format[Schema.TestIamPermissionsResponse]
	given fmtOperationMetadata: Format[Schema.OperationMetadata] = Json.format[Schema.OperationMetadata]
	given fmtReconciliationOperationMetadata: Format[Schema.ReconciliationOperationMetadata] = Json.format[Schema.ReconciliationOperationMetadata]
	given fmtReconciliationOperationMetadataExclusiveActionEnum: Format[Schema.ReconciliationOperationMetadata.ExclusiveActionEnum] = JsonEnumFormat[Schema.ReconciliationOperationMetadata.ExclusiveActionEnum]
}