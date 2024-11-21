package com.boresjo.play.api.google.privateca

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putListSchemaOperation: Conversion[List[Schema.Operation], Option[List[Schema.Operation]]] = (fun: List[Schema.Operation]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putSchemaCertificateConfig: Conversion[Schema.CertificateConfig, Option[Schema.CertificateConfig]] = (fun: Schema.CertificateConfig) => Option(fun)
		given putSchemaCertificateSubjectModeEnum: Conversion[Schema.Certificate.SubjectModeEnum, Option[Schema.Certificate.SubjectModeEnum]] = (fun: Schema.Certificate.SubjectModeEnum) => Option(fun)
		given putSchemaRevocationDetails: Conversion[Schema.RevocationDetails, Option[Schema.RevocationDetails]] = (fun: Schema.RevocationDetails) => Option(fun)
		given putSchemaCertificateDescription: Conversion[Schema.CertificateDescription, Option[Schema.CertificateDescription]] = (fun: Schema.CertificateDescription) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaSubjectConfig: Conversion[Schema.SubjectConfig, Option[Schema.SubjectConfig]] = (fun: Schema.SubjectConfig) => Option(fun)
		given putSchemaX509Parameters: Conversion[Schema.X509Parameters, Option[Schema.X509Parameters]] = (fun: Schema.X509Parameters) => Option(fun)
		given putSchemaPublicKey: Conversion[Schema.PublicKey, Option[Schema.PublicKey]] = (fun: Schema.PublicKey) => Option(fun)
		given putSchemaCertificateConfigKeyId: Conversion[Schema.CertificateConfigKeyId, Option[Schema.CertificateConfigKeyId]] = (fun: Schema.CertificateConfigKeyId) => Option(fun)
		given putSchemaSubject: Conversion[Schema.Subject, Option[Schema.Subject]] = (fun: Schema.Subject) => Option(fun)
		given putSchemaSubjectAltNames: Conversion[Schema.SubjectAltNames, Option[Schema.SubjectAltNames]] = (fun: Schema.SubjectAltNames) => Option(fun)
		given putListSchemaX509Extension: Conversion[List[Schema.X509Extension], Option[List[Schema.X509Extension]]] = (fun: List[Schema.X509Extension]) => Option(fun)
		given putSchemaObjectId: Conversion[Schema.ObjectId, Option[Schema.ObjectId]] = (fun: Schema.ObjectId) => Option(fun)
		given putListInt: Conversion[List[Int], Option[List[Int]]] = (fun: List[Int]) => Option(fun)
		given putSchemaKeyUsage: Conversion[Schema.KeyUsage, Option[Schema.KeyUsage]] = (fun: Schema.KeyUsage) => Option(fun)
		given putSchemaCaOptions: Conversion[Schema.CaOptions, Option[Schema.CaOptions]] = (fun: Schema.CaOptions) => Option(fun)
		given putListSchemaObjectId: Conversion[List[Schema.ObjectId], Option[List[Schema.ObjectId]]] = (fun: List[Schema.ObjectId]) => Option(fun)
		given putSchemaNameConstraints: Conversion[Schema.NameConstraints, Option[Schema.NameConstraints]] = (fun: Schema.NameConstraints) => Option(fun)
		given putSchemaKeyUsageOptions: Conversion[Schema.KeyUsageOptions, Option[Schema.KeyUsageOptions]] = (fun: Schema.KeyUsageOptions) => Option(fun)
		given putSchemaExtendedKeyUsageOptions: Conversion[Schema.ExtendedKeyUsageOptions, Option[Schema.ExtendedKeyUsageOptions]] = (fun: Schema.ExtendedKeyUsageOptions) => Option(fun)
		given putSchemaPublicKeyFormatEnum: Conversion[Schema.PublicKey.FormatEnum, Option[Schema.PublicKey.FormatEnum]] = (fun: Schema.PublicKey.FormatEnum) => Option(fun)
		given putSchemaRevocationDetailsRevocationStateEnum: Conversion[Schema.RevocationDetails.RevocationStateEnum, Option[Schema.RevocationDetails.RevocationStateEnum]] = (fun: Schema.RevocationDetails.RevocationStateEnum) => Option(fun)
		given putSchemaSubjectDescription: Conversion[Schema.SubjectDescription, Option[Schema.SubjectDescription]] = (fun: Schema.SubjectDescription) => Option(fun)
		given putSchemaKeyId: Conversion[Schema.KeyId, Option[Schema.KeyId]] = (fun: Schema.KeyId) => Option(fun)
		given putSchemaCertificateFingerprint: Conversion[Schema.CertificateFingerprint, Option[Schema.CertificateFingerprint]] = (fun: Schema.CertificateFingerprint) => Option(fun)
		given putListSchemaCertificate: Conversion[List[Schema.Certificate], Option[List[Schema.Certificate]]] = (fun: List[Schema.Certificate]) => Option(fun)
		given putSchemaRevokeCertificateRequestReasonEnum: Conversion[Schema.RevokeCertificateRequest.ReasonEnum, Option[Schema.RevokeCertificateRequest.ReasonEnum]] = (fun: Schema.RevokeCertificateRequest.ReasonEnum) => Option(fun)
		given putSchemaSubordinateConfig: Conversion[Schema.SubordinateConfig, Option[Schema.SubordinateConfig]] = (fun: Schema.SubordinateConfig) => Option(fun)
		given putSchemaSubordinateConfigChain: Conversion[Schema.SubordinateConfigChain, Option[Schema.SubordinateConfigChain]] = (fun: Schema.SubordinateConfigChain) => Option(fun)
		given putSchemaCertificateAuthorityTypeEnum: Conversion[Schema.CertificateAuthority.TypeEnum, Option[Schema.CertificateAuthority.TypeEnum]] = (fun: Schema.CertificateAuthority.TypeEnum) => Option(fun)
		given putSchemaKeyVersionSpec: Conversion[Schema.KeyVersionSpec, Option[Schema.KeyVersionSpec]] = (fun: Schema.KeyVersionSpec) => Option(fun)
		given putSchemaCertificateAuthorityTierEnum: Conversion[Schema.CertificateAuthority.TierEnum, Option[Schema.CertificateAuthority.TierEnum]] = (fun: Schema.CertificateAuthority.TierEnum) => Option(fun)
		given putSchemaCertificateAuthorityStateEnum: Conversion[Schema.CertificateAuthority.StateEnum, Option[Schema.CertificateAuthority.StateEnum]] = (fun: Schema.CertificateAuthority.StateEnum) => Option(fun)
		given putListSchemaCertificateDescription: Conversion[List[Schema.CertificateDescription], Option[List[Schema.CertificateDescription]]] = (fun: List[Schema.CertificateDescription]) => Option(fun)
		given putSchemaAccessUrls: Conversion[Schema.AccessUrls, Option[Schema.AccessUrls]] = (fun: Schema.AccessUrls) => Option(fun)
		given putSchemaKeyVersionSpecAlgorithmEnum: Conversion[Schema.KeyVersionSpec.AlgorithmEnum, Option[Schema.KeyVersionSpec.AlgorithmEnum]] = (fun: Schema.KeyVersionSpec.AlgorithmEnum) => Option(fun)
		given putListSchemaCertificateAuthority: Conversion[List[Schema.CertificateAuthority], Option[List[Schema.CertificateAuthority]]] = (fun: List[Schema.CertificateAuthority]) => Option(fun)
		given putSchemaCaPoolTierEnum: Conversion[Schema.CaPool.TierEnum, Option[Schema.CaPool.TierEnum]] = (fun: Schema.CaPool.TierEnum) => Option(fun)
		given putSchemaIssuancePolicy: Conversion[Schema.IssuancePolicy, Option[Schema.IssuancePolicy]] = (fun: Schema.IssuancePolicy) => Option(fun)
		given putSchemaPublishingOptions: Conversion[Schema.PublishingOptions, Option[Schema.PublishingOptions]] = (fun: Schema.PublishingOptions) => Option(fun)
		given putListSchemaAllowedKeyType: Conversion[List[Schema.AllowedKeyType], Option[List[Schema.AllowedKeyType]]] = (fun: List[Schema.AllowedKeyType]) => Option(fun)
		given putSchemaIssuanceModes: Conversion[Schema.IssuanceModes, Option[Schema.IssuanceModes]] = (fun: Schema.IssuanceModes) => Option(fun)
		given putSchemaCertificateIdentityConstraints: Conversion[Schema.CertificateIdentityConstraints, Option[Schema.CertificateIdentityConstraints]] = (fun: Schema.CertificateIdentityConstraints) => Option(fun)
		given putSchemaCertificateExtensionConstraints: Conversion[Schema.CertificateExtensionConstraints, Option[Schema.CertificateExtensionConstraints]] = (fun: Schema.CertificateExtensionConstraints) => Option(fun)
		given putSchemaRsaKeyType: Conversion[Schema.RsaKeyType, Option[Schema.RsaKeyType]] = (fun: Schema.RsaKeyType) => Option(fun)
		given putSchemaEcKeyType: Conversion[Schema.EcKeyType, Option[Schema.EcKeyType]] = (fun: Schema.EcKeyType) => Option(fun)
		given putSchemaEcKeyTypeSignatureAlgorithmEnum: Conversion[Schema.EcKeyType.SignatureAlgorithmEnum, Option[Schema.EcKeyType.SignatureAlgorithmEnum]] = (fun: Schema.EcKeyType.SignatureAlgorithmEnum) => Option(fun)
		given putSchemaExpr: Conversion[Schema.Expr, Option[Schema.Expr]] = (fun: Schema.Expr) => Option(fun)
		given putListSchemaCertificateExtensionConstraintsKnownExtensionsEnum: Conversion[List[Schema.CertificateExtensionConstraints.KnownExtensionsEnum], Option[List[Schema.CertificateExtensionConstraints.KnownExtensionsEnum]]] = (fun: List[Schema.CertificateExtensionConstraints.KnownExtensionsEnum]) => Option(fun)
		given putSchemaPublishingOptionsEncodingFormatEnum: Conversion[Schema.PublishingOptions.EncodingFormatEnum, Option[Schema.PublishingOptions.EncodingFormatEnum]] = (fun: Schema.PublishingOptions.EncodingFormatEnum) => Option(fun)
		given putListSchemaCaPool: Conversion[List[Schema.CaPool], Option[List[Schema.CaPool]]] = (fun: List[Schema.CaPool]) => Option(fun)
		given putListSchemaCertChain: Conversion[List[Schema.CertChain], Option[List[Schema.CertChain]]] = (fun: List[Schema.CertChain]) => Option(fun)
		given putListSchemaRevokedCertificate: Conversion[List[Schema.RevokedCertificate], Option[List[Schema.RevokedCertificate]]] = (fun: List[Schema.RevokedCertificate]) => Option(fun)
		given putSchemaCertificateRevocationListStateEnum: Conversion[Schema.CertificateRevocationList.StateEnum, Option[Schema.CertificateRevocationList.StateEnum]] = (fun: Schema.CertificateRevocationList.StateEnum) => Option(fun)
		given putSchemaRevokedCertificateRevocationReasonEnum: Conversion[Schema.RevokedCertificate.RevocationReasonEnum, Option[Schema.RevokedCertificate.RevocationReasonEnum]] = (fun: Schema.RevokedCertificate.RevocationReasonEnum) => Option(fun)
		given putListSchemaCertificateRevocationList: Conversion[List[Schema.CertificateRevocationList], Option[List[Schema.CertificateRevocationList]]] = (fun: List[Schema.CertificateRevocationList]) => Option(fun)
		given putListSchemaCertificateTemplate: Conversion[List[Schema.CertificateTemplate], Option[List[Schema.CertificateTemplate]]] = (fun: List[Schema.CertificateTemplate]) => Option(fun)
		given putSchemaPolicy: Conversion[Schema.Policy, Option[Schema.Policy]] = (fun: Schema.Policy) => Option(fun)
		given putListSchemaBinding: Conversion[List[Schema.Binding], Option[List[Schema.Binding]]] = (fun: List[Schema.Binding]) => Option(fun)
		given putListSchemaAuditConfig: Conversion[List[Schema.AuditConfig], Option[List[Schema.AuditConfig]]] = (fun: List[Schema.AuditConfig]) => Option(fun)
		given putListSchemaAuditLogConfig: Conversion[List[Schema.AuditLogConfig], Option[List[Schema.AuditLogConfig]]] = (fun: List[Schema.AuditLogConfig]) => Option(fun)
		given putSchemaAuditLogConfigLogTypeEnum: Conversion[Schema.AuditLogConfig.LogTypeEnum, Option[Schema.AuditLogConfig.LogTypeEnum]] = (fun: Schema.AuditLogConfig.LogTypeEnum) => Option(fun)
		given putSchemaReconciliationOperationMetadataExclusiveActionEnum: Conversion[Schema.ReconciliationOperationMetadata.ExclusiveActionEnum, Option[Schema.ReconciliationOperationMetadata.ExclusiveActionEnum]] = (fun: Schema.ReconciliationOperationMetadata.ExclusiveActionEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
