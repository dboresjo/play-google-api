package com.boresjo.play.api.google.cloudkms

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaAutokeyConfigStateEnum: Conversion[Schema.AutokeyConfig.StateEnum, Option[Schema.AutokeyConfig.StateEnum]] = (fun: Schema.AutokeyConfig.StateEnum) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putListSchemaKeyHandle: Conversion[List[Schema.KeyHandle], Option[List[Schema.KeyHandle]]] = (fun: List[Schema.KeyHandle]) => Option(fun)
		given putListSchemaEkmConnection: Conversion[List[Schema.EkmConnection], Option[List[Schema.EkmConnection]]] = (fun: List[Schema.EkmConnection]) => Option(fun)
		given putListSchemaServiceResolver: Conversion[List[Schema.ServiceResolver], Option[List[Schema.ServiceResolver]]] = (fun: List[Schema.ServiceResolver]) => Option(fun)
		given putSchemaEkmConnectionKeyManagementModeEnum: Conversion[Schema.EkmConnection.KeyManagementModeEnum, Option[Schema.EkmConnection.KeyManagementModeEnum]] = (fun: Schema.EkmConnection.KeyManagementModeEnum) => Option(fun)
		given putListSchemaCertificate: Conversion[List[Schema.Certificate], Option[List[Schema.Certificate]]] = (fun: List[Schema.Certificate]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putListSchemaKeyRing: Conversion[List[Schema.KeyRing], Option[List[Schema.KeyRing]]] = (fun: List[Schema.KeyRing]) => Option(fun)
		given putListSchemaCryptoKey: Conversion[List[Schema.CryptoKey], Option[List[Schema.CryptoKey]]] = (fun: List[Schema.CryptoKey]) => Option(fun)
		given putSchemaCryptoKeyVersion: Conversion[Schema.CryptoKeyVersion, Option[Schema.CryptoKeyVersion]] = (fun: Schema.CryptoKeyVersion) => Option(fun)
		given putSchemaCryptoKeyPurposeEnum: Conversion[Schema.CryptoKey.PurposeEnum, Option[Schema.CryptoKey.PurposeEnum]] = (fun: Schema.CryptoKey.PurposeEnum) => Option(fun)
		given putSchemaCryptoKeyVersionTemplate: Conversion[Schema.CryptoKeyVersionTemplate, Option[Schema.CryptoKeyVersionTemplate]] = (fun: Schema.CryptoKeyVersionTemplate) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaKeyAccessJustificationsPolicy: Conversion[Schema.KeyAccessJustificationsPolicy, Option[Schema.KeyAccessJustificationsPolicy]] = (fun: Schema.KeyAccessJustificationsPolicy) => Option(fun)
		given putSchemaCryptoKeyVersionStateEnum: Conversion[Schema.CryptoKeyVersion.StateEnum, Option[Schema.CryptoKeyVersion.StateEnum]] = (fun: Schema.CryptoKeyVersion.StateEnum) => Option(fun)
		given putSchemaCryptoKeyVersionProtectionLevelEnum: Conversion[Schema.CryptoKeyVersion.ProtectionLevelEnum, Option[Schema.CryptoKeyVersion.ProtectionLevelEnum]] = (fun: Schema.CryptoKeyVersion.ProtectionLevelEnum) => Option(fun)
		given putSchemaCryptoKeyVersionAlgorithmEnum: Conversion[Schema.CryptoKeyVersion.AlgorithmEnum, Option[Schema.CryptoKeyVersion.AlgorithmEnum]] = (fun: Schema.CryptoKeyVersion.AlgorithmEnum) => Option(fun)
		given putSchemaKeyOperationAttestation: Conversion[Schema.KeyOperationAttestation, Option[Schema.KeyOperationAttestation]] = (fun: Schema.KeyOperationAttestation) => Option(fun)
		given putSchemaExternalProtectionLevelOptions: Conversion[Schema.ExternalProtectionLevelOptions, Option[Schema.ExternalProtectionLevelOptions]] = (fun: Schema.ExternalProtectionLevelOptions) => Option(fun)
		given putSchemaKeyOperationAttestationFormatEnum: Conversion[Schema.KeyOperationAttestation.FormatEnum, Option[Schema.KeyOperationAttestation.FormatEnum]] = (fun: Schema.KeyOperationAttestation.FormatEnum) => Option(fun)
		given putSchemaCertificateChains: Conversion[Schema.CertificateChains, Option[Schema.CertificateChains]] = (fun: Schema.CertificateChains) => Option(fun)
		given putSchemaCryptoKeyVersionTemplateProtectionLevelEnum: Conversion[Schema.CryptoKeyVersionTemplate.ProtectionLevelEnum, Option[Schema.CryptoKeyVersionTemplate.ProtectionLevelEnum]] = (fun: Schema.CryptoKeyVersionTemplate.ProtectionLevelEnum) => Option(fun)
		given putSchemaCryptoKeyVersionTemplateAlgorithmEnum: Conversion[Schema.CryptoKeyVersionTemplate.AlgorithmEnum, Option[Schema.CryptoKeyVersionTemplate.AlgorithmEnum]] = (fun: Schema.CryptoKeyVersionTemplate.AlgorithmEnum) => Option(fun)
		given putListSchemaKeyAccessJustificationsPolicyAllowedAccessReasonsEnum: Conversion[List[Schema.KeyAccessJustificationsPolicy.AllowedAccessReasonsEnum], Option[List[Schema.KeyAccessJustificationsPolicy.AllowedAccessReasonsEnum]]] = (fun: List[Schema.KeyAccessJustificationsPolicy.AllowedAccessReasonsEnum]) => Option(fun)
		given putListSchemaCryptoKeyVersion: Conversion[List[Schema.CryptoKeyVersion], Option[List[Schema.CryptoKeyVersion]]] = (fun: List[Schema.CryptoKeyVersion]) => Option(fun)
		given putListSchemaImportJob: Conversion[List[Schema.ImportJob], Option[List[Schema.ImportJob]]] = (fun: List[Schema.ImportJob]) => Option(fun)
		given putSchemaImportJobImportMethodEnum: Conversion[Schema.ImportJob.ImportMethodEnum, Option[Schema.ImportJob.ImportMethodEnum]] = (fun: Schema.ImportJob.ImportMethodEnum) => Option(fun)
		given putSchemaImportJobProtectionLevelEnum: Conversion[Schema.ImportJob.ProtectionLevelEnum, Option[Schema.ImportJob.ProtectionLevelEnum]] = (fun: Schema.ImportJob.ProtectionLevelEnum) => Option(fun)
		given putSchemaImportJobStateEnum: Conversion[Schema.ImportJob.StateEnum, Option[Schema.ImportJob.StateEnum]] = (fun: Schema.ImportJob.StateEnum) => Option(fun)
		given putSchemaWrappingPublicKey: Conversion[Schema.WrappingPublicKey, Option[Schema.WrappingPublicKey]] = (fun: Schema.WrappingPublicKey) => Option(fun)
		given putSchemaPublicKeyAlgorithmEnum: Conversion[Schema.PublicKey.AlgorithmEnum, Option[Schema.PublicKey.AlgorithmEnum]] = (fun: Schema.PublicKey.AlgorithmEnum) => Option(fun)
		given putSchemaPublicKeyProtectionLevelEnum: Conversion[Schema.PublicKey.ProtectionLevelEnum, Option[Schema.PublicKey.ProtectionLevelEnum]] = (fun: Schema.PublicKey.ProtectionLevelEnum) => Option(fun)
		given putSchemaImportCryptoKeyVersionRequestAlgorithmEnum: Conversion[Schema.ImportCryptoKeyVersionRequest.AlgorithmEnum, Option[Schema.ImportCryptoKeyVersionRequest.AlgorithmEnum]] = (fun: Schema.ImportCryptoKeyVersionRequest.AlgorithmEnum) => Option(fun)
		given putSchemaEncryptResponseProtectionLevelEnum: Conversion[Schema.EncryptResponse.ProtectionLevelEnum, Option[Schema.EncryptResponse.ProtectionLevelEnum]] = (fun: Schema.EncryptResponse.ProtectionLevelEnum) => Option(fun)
		given putSchemaDecryptResponseProtectionLevelEnum: Conversion[Schema.DecryptResponse.ProtectionLevelEnum, Option[Schema.DecryptResponse.ProtectionLevelEnum]] = (fun: Schema.DecryptResponse.ProtectionLevelEnum) => Option(fun)
		given putSchemaRawEncryptResponseProtectionLevelEnum: Conversion[Schema.RawEncryptResponse.ProtectionLevelEnum, Option[Schema.RawEncryptResponse.ProtectionLevelEnum]] = (fun: Schema.RawEncryptResponse.ProtectionLevelEnum) => Option(fun)
		given putSchemaRawDecryptResponseProtectionLevelEnum: Conversion[Schema.RawDecryptResponse.ProtectionLevelEnum, Option[Schema.RawDecryptResponse.ProtectionLevelEnum]] = (fun: Schema.RawDecryptResponse.ProtectionLevelEnum) => Option(fun)
		given putSchemaDigest: Conversion[Schema.Digest, Option[Schema.Digest]] = (fun: Schema.Digest) => Option(fun)
		given putSchemaAsymmetricSignResponseProtectionLevelEnum: Conversion[Schema.AsymmetricSignResponse.ProtectionLevelEnum, Option[Schema.AsymmetricSignResponse.ProtectionLevelEnum]] = (fun: Schema.AsymmetricSignResponse.ProtectionLevelEnum) => Option(fun)
		given putSchemaAsymmetricDecryptResponseProtectionLevelEnum: Conversion[Schema.AsymmetricDecryptResponse.ProtectionLevelEnum, Option[Schema.AsymmetricDecryptResponse.ProtectionLevelEnum]] = (fun: Schema.AsymmetricDecryptResponse.ProtectionLevelEnum) => Option(fun)
		given putSchemaMacSignResponseProtectionLevelEnum: Conversion[Schema.MacSignResponse.ProtectionLevelEnum, Option[Schema.MacSignResponse.ProtectionLevelEnum]] = (fun: Schema.MacSignResponse.ProtectionLevelEnum) => Option(fun)
		given putSchemaMacVerifyResponseProtectionLevelEnum: Conversion[Schema.MacVerifyResponse.ProtectionLevelEnum, Option[Schema.MacVerifyResponse.ProtectionLevelEnum]] = (fun: Schema.MacVerifyResponse.ProtectionLevelEnum) => Option(fun)
		given putSchemaGenerateRandomBytesRequestProtectionLevelEnum: Conversion[Schema.GenerateRandomBytesRequest.ProtectionLevelEnum, Option[Schema.GenerateRandomBytesRequest.ProtectionLevelEnum]] = (fun: Schema.GenerateRandomBytesRequest.ProtectionLevelEnum) => Option(fun)
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
		given putSchemaPolicy: Conversion[Schema.Policy, Option[Schema.Policy]] = (fun: Schema.Policy) => Option(fun)
		given putListSchemaBinding: Conversion[List[Schema.Binding], Option[List[Schema.Binding]]] = (fun: List[Schema.Binding]) => Option(fun)
		given putListSchemaAuditConfig: Conversion[List[Schema.AuditConfig], Option[List[Schema.AuditConfig]]] = (fun: List[Schema.AuditConfig]) => Option(fun)
		given putSchemaExpr: Conversion[Schema.Expr, Option[Schema.Expr]] = (fun: Schema.Expr) => Option(fun)
		given putListSchemaAuditLogConfig: Conversion[List[Schema.AuditLogConfig], Option[List[Schema.AuditLogConfig]]] = (fun: List[Schema.AuditLogConfig]) => Option(fun)
		given putSchemaAuditLogConfigLogTypeEnum: Conversion[Schema.AuditLogConfig.LogTypeEnum, Option[Schema.AuditLogConfig.LogTypeEnum]] = (fun: Schema.AuditLogConfig.LogTypeEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
