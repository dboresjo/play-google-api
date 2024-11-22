package com.boresjo.play.api.google.cloudkms

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtAutokeyConfig: Format[Schema.AutokeyConfig] = Json.format[Schema.AutokeyConfig]
	given fmtAutokeyConfigStateEnum: Format[Schema.AutokeyConfig.StateEnum] = JsonEnumFormat[Schema.AutokeyConfig.StateEnum]
	given fmtShowEffectiveAutokeyConfigResponse: Format[Schema.ShowEffectiveAutokeyConfigResponse] = Json.format[Schema.ShowEffectiveAutokeyConfigResponse]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtKeyHandle: Format[Schema.KeyHandle] = Json.format[Schema.KeyHandle]
	given fmtListKeyHandlesResponse: Format[Schema.ListKeyHandlesResponse] = Json.format[Schema.ListKeyHandlesResponse]
	given fmtListEkmConnectionsResponse: Format[Schema.ListEkmConnectionsResponse] = Json.format[Schema.ListEkmConnectionsResponse]
	given fmtEkmConnection: Format[Schema.EkmConnection] = Json.format[Schema.EkmConnection]
	given fmtServiceResolver: Format[Schema.ServiceResolver] = Json.format[Schema.ServiceResolver]
	given fmtEkmConnectionKeyManagementModeEnum: Format[Schema.EkmConnection.KeyManagementModeEnum] = JsonEnumFormat[Schema.EkmConnection.KeyManagementModeEnum]
	given fmtCertificate: Format[Schema.Certificate] = Json.format[Schema.Certificate]
	given fmtEkmConfig: Format[Schema.EkmConfig] = Json.format[Schema.EkmConfig]
	given fmtVerifyConnectivityResponse: Format[Schema.VerifyConnectivityResponse] = Json.format[Schema.VerifyConnectivityResponse]
	given fmtListKeyRingsResponse: Format[Schema.ListKeyRingsResponse] = Json.format[Schema.ListKeyRingsResponse]
	given fmtKeyRing: Format[Schema.KeyRing] = Json.format[Schema.KeyRing]
	given fmtListCryptoKeysResponse: Format[Schema.ListCryptoKeysResponse] = Json.format[Schema.ListCryptoKeysResponse]
	given fmtCryptoKey: Format[Schema.CryptoKey] = Json.format[Schema.CryptoKey]
	given fmtCryptoKeyVersion: Format[Schema.CryptoKeyVersion] = Json.format[Schema.CryptoKeyVersion]
	given fmtCryptoKeyPurposeEnum: Format[Schema.CryptoKey.PurposeEnum] = JsonEnumFormat[Schema.CryptoKey.PurposeEnum]
	given fmtCryptoKeyVersionTemplate: Format[Schema.CryptoKeyVersionTemplate] = Json.format[Schema.CryptoKeyVersionTemplate]
	given fmtKeyAccessJustificationsPolicy: Format[Schema.KeyAccessJustificationsPolicy] = Json.format[Schema.KeyAccessJustificationsPolicy]
	given fmtCryptoKeyVersionStateEnum: Format[Schema.CryptoKeyVersion.StateEnum] = JsonEnumFormat[Schema.CryptoKeyVersion.StateEnum]
	given fmtCryptoKeyVersionProtectionLevelEnum: Format[Schema.CryptoKeyVersion.ProtectionLevelEnum] = JsonEnumFormat[Schema.CryptoKeyVersion.ProtectionLevelEnum]
	given fmtCryptoKeyVersionAlgorithmEnum: Format[Schema.CryptoKeyVersion.AlgorithmEnum] = JsonEnumFormat[Schema.CryptoKeyVersion.AlgorithmEnum]
	given fmtKeyOperationAttestation: Format[Schema.KeyOperationAttestation] = Json.format[Schema.KeyOperationAttestation]
	given fmtExternalProtectionLevelOptions: Format[Schema.ExternalProtectionLevelOptions] = Json.format[Schema.ExternalProtectionLevelOptions]
	given fmtKeyOperationAttestationFormatEnum: Format[Schema.KeyOperationAttestation.FormatEnum] = JsonEnumFormat[Schema.KeyOperationAttestation.FormatEnum]
	given fmtCertificateChains: Format[Schema.CertificateChains] = Json.format[Schema.CertificateChains]
	given fmtCryptoKeyVersionTemplateProtectionLevelEnum: Format[Schema.CryptoKeyVersionTemplate.ProtectionLevelEnum] = JsonEnumFormat[Schema.CryptoKeyVersionTemplate.ProtectionLevelEnum]
	given fmtCryptoKeyVersionTemplateAlgorithmEnum: Format[Schema.CryptoKeyVersionTemplate.AlgorithmEnum] = JsonEnumFormat[Schema.CryptoKeyVersionTemplate.AlgorithmEnum]
	given fmtKeyAccessJustificationsPolicyAllowedAccessReasonsEnum: Format[Schema.KeyAccessJustificationsPolicy.AllowedAccessReasonsEnum] = JsonEnumFormat[Schema.KeyAccessJustificationsPolicy.AllowedAccessReasonsEnum]
	given fmtListCryptoKeyVersionsResponse: Format[Schema.ListCryptoKeyVersionsResponse] = Json.format[Schema.ListCryptoKeyVersionsResponse]
	given fmtListImportJobsResponse: Format[Schema.ListImportJobsResponse] = Json.format[Schema.ListImportJobsResponse]
	given fmtImportJob: Format[Schema.ImportJob] = Json.format[Schema.ImportJob]
	given fmtImportJobImportMethodEnum: Format[Schema.ImportJob.ImportMethodEnum] = JsonEnumFormat[Schema.ImportJob.ImportMethodEnum]
	given fmtImportJobProtectionLevelEnum: Format[Schema.ImportJob.ProtectionLevelEnum] = JsonEnumFormat[Schema.ImportJob.ProtectionLevelEnum]
	given fmtImportJobStateEnum: Format[Schema.ImportJob.StateEnum] = JsonEnumFormat[Schema.ImportJob.StateEnum]
	given fmtWrappingPublicKey: Format[Schema.WrappingPublicKey] = Json.format[Schema.WrappingPublicKey]
	given fmtPublicKey: Format[Schema.PublicKey] = Json.format[Schema.PublicKey]
	given fmtPublicKeyAlgorithmEnum: Format[Schema.PublicKey.AlgorithmEnum] = JsonEnumFormat[Schema.PublicKey.AlgorithmEnum]
	given fmtPublicKeyProtectionLevelEnum: Format[Schema.PublicKey.ProtectionLevelEnum] = JsonEnumFormat[Schema.PublicKey.ProtectionLevelEnum]
	given fmtImportCryptoKeyVersionRequest: Format[Schema.ImportCryptoKeyVersionRequest] = Json.format[Schema.ImportCryptoKeyVersionRequest]
	given fmtImportCryptoKeyVersionRequestAlgorithmEnum: Format[Schema.ImportCryptoKeyVersionRequest.AlgorithmEnum] = JsonEnumFormat[Schema.ImportCryptoKeyVersionRequest.AlgorithmEnum]
	given fmtUpdateCryptoKeyPrimaryVersionRequest: Format[Schema.UpdateCryptoKeyPrimaryVersionRequest] = Json.format[Schema.UpdateCryptoKeyPrimaryVersionRequest]
	given fmtDestroyCryptoKeyVersionRequest: Format[Schema.DestroyCryptoKeyVersionRequest] = Json.format[Schema.DestroyCryptoKeyVersionRequest]
	given fmtRestoreCryptoKeyVersionRequest: Format[Schema.RestoreCryptoKeyVersionRequest] = Json.format[Schema.RestoreCryptoKeyVersionRequest]
	given fmtEncryptRequest: Format[Schema.EncryptRequest] = Json.format[Schema.EncryptRequest]
	given fmtEncryptResponse: Format[Schema.EncryptResponse] = Json.format[Schema.EncryptResponse]
	given fmtEncryptResponseProtectionLevelEnum: Format[Schema.EncryptResponse.ProtectionLevelEnum] = JsonEnumFormat[Schema.EncryptResponse.ProtectionLevelEnum]
	given fmtDecryptRequest: Format[Schema.DecryptRequest] = Json.format[Schema.DecryptRequest]
	given fmtDecryptResponse: Format[Schema.DecryptResponse] = Json.format[Schema.DecryptResponse]
	given fmtDecryptResponseProtectionLevelEnum: Format[Schema.DecryptResponse.ProtectionLevelEnum] = JsonEnumFormat[Schema.DecryptResponse.ProtectionLevelEnum]
	given fmtRawEncryptRequest: Format[Schema.RawEncryptRequest] = Json.format[Schema.RawEncryptRequest]
	given fmtRawEncryptResponse: Format[Schema.RawEncryptResponse] = Json.format[Schema.RawEncryptResponse]
	given fmtRawEncryptResponseProtectionLevelEnum: Format[Schema.RawEncryptResponse.ProtectionLevelEnum] = JsonEnumFormat[Schema.RawEncryptResponse.ProtectionLevelEnum]
	given fmtRawDecryptRequest: Format[Schema.RawDecryptRequest] = Json.format[Schema.RawDecryptRequest]
	given fmtRawDecryptResponse: Format[Schema.RawDecryptResponse] = Json.format[Schema.RawDecryptResponse]
	given fmtRawDecryptResponseProtectionLevelEnum: Format[Schema.RawDecryptResponse.ProtectionLevelEnum] = JsonEnumFormat[Schema.RawDecryptResponse.ProtectionLevelEnum]
	given fmtAsymmetricSignRequest: Format[Schema.AsymmetricSignRequest] = Json.format[Schema.AsymmetricSignRequest]
	given fmtDigest: Format[Schema.Digest] = Json.format[Schema.Digest]
	given fmtAsymmetricSignResponse: Format[Schema.AsymmetricSignResponse] = Json.format[Schema.AsymmetricSignResponse]
	given fmtAsymmetricSignResponseProtectionLevelEnum: Format[Schema.AsymmetricSignResponse.ProtectionLevelEnum] = JsonEnumFormat[Schema.AsymmetricSignResponse.ProtectionLevelEnum]
	given fmtAsymmetricDecryptRequest: Format[Schema.AsymmetricDecryptRequest] = Json.format[Schema.AsymmetricDecryptRequest]
	given fmtAsymmetricDecryptResponse: Format[Schema.AsymmetricDecryptResponse] = Json.format[Schema.AsymmetricDecryptResponse]
	given fmtAsymmetricDecryptResponseProtectionLevelEnum: Format[Schema.AsymmetricDecryptResponse.ProtectionLevelEnum] = JsonEnumFormat[Schema.AsymmetricDecryptResponse.ProtectionLevelEnum]
	given fmtMacSignRequest: Format[Schema.MacSignRequest] = Json.format[Schema.MacSignRequest]
	given fmtMacSignResponse: Format[Schema.MacSignResponse] = Json.format[Schema.MacSignResponse]
	given fmtMacSignResponseProtectionLevelEnum: Format[Schema.MacSignResponse.ProtectionLevelEnum] = JsonEnumFormat[Schema.MacSignResponse.ProtectionLevelEnum]
	given fmtMacVerifyRequest: Format[Schema.MacVerifyRequest] = Json.format[Schema.MacVerifyRequest]
	given fmtMacVerifyResponse: Format[Schema.MacVerifyResponse] = Json.format[Schema.MacVerifyResponse]
	given fmtMacVerifyResponseProtectionLevelEnum: Format[Schema.MacVerifyResponse.ProtectionLevelEnum] = JsonEnumFormat[Schema.MacVerifyResponse.ProtectionLevelEnum]
	given fmtGenerateRandomBytesRequest: Format[Schema.GenerateRandomBytesRequest] = Json.format[Schema.GenerateRandomBytesRequest]
	given fmtGenerateRandomBytesRequestProtectionLevelEnum: Format[Schema.GenerateRandomBytesRequest.ProtectionLevelEnum] = JsonEnumFormat[Schema.GenerateRandomBytesRequest.ProtectionLevelEnum]
	given fmtGenerateRandomBytesResponse: Format[Schema.GenerateRandomBytesResponse] = Json.format[Schema.GenerateRandomBytesResponse]
	given fmtListLocationsResponse: Format[Schema.ListLocationsResponse] = Json.format[Schema.ListLocationsResponse]
	given fmtLocation: Format[Schema.Location] = Json.format[Schema.Location]
	given fmtSetIamPolicyRequest: Format[Schema.SetIamPolicyRequest] = Json.format[Schema.SetIamPolicyRequest]
	given fmtPolicy: Format[Schema.Policy] = Json.format[Schema.Policy]
	given fmtBinding: Format[Schema.Binding] = Json.format[Schema.Binding]
	given fmtAuditConfig: Format[Schema.AuditConfig] = Json.format[Schema.AuditConfig]
	given fmtExpr: Format[Schema.Expr] = Json.format[Schema.Expr]
	given fmtAuditLogConfig: Format[Schema.AuditLogConfig] = Json.format[Schema.AuditLogConfig]
	given fmtAuditLogConfigLogTypeEnum: Format[Schema.AuditLogConfig.LogTypeEnum] = JsonEnumFormat[Schema.AuditLogConfig.LogTypeEnum]
	given fmtTestIamPermissionsRequest: Format[Schema.TestIamPermissionsRequest] = Json.format[Schema.TestIamPermissionsRequest]
	given fmtTestIamPermissionsResponse: Format[Schema.TestIamPermissionsResponse] = Json.format[Schema.TestIamPermissionsResponse]
	given fmtLocationMetadata: Format[Schema.LocationMetadata] = Json.format[Schema.LocationMetadata]
}
