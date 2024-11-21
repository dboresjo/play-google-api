package com.boresjo.play.api.google.kmsinventory

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putSchemaGoogleCloudKmsV1CryptoKeyVersionTemplateAlgorithmEnum: Conversion[Schema.GoogleCloudKmsV1CryptoKeyVersionTemplate.AlgorithmEnum, Option[Schema.GoogleCloudKmsV1CryptoKeyVersionTemplate.AlgorithmEnum]] = (fun: Schema.GoogleCloudKmsV1CryptoKeyVersionTemplate.AlgorithmEnum) => Option(fun)
		given putSchemaGoogleCloudKmsV1CryptoKeyVersionTemplateProtectionLevelEnum: Conversion[Schema.GoogleCloudKmsV1CryptoKeyVersionTemplate.ProtectionLevelEnum, Option[Schema.GoogleCloudKmsV1CryptoKeyVersionTemplate.ProtectionLevelEnum]] = (fun: Schema.GoogleCloudKmsV1CryptoKeyVersionTemplate.ProtectionLevelEnum) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListSchemaGoogleCloudKmsV1KeyAccessJustificationsPolicyAllowedAccessReasonsEnum: Conversion[List[Schema.GoogleCloudKmsV1KeyAccessJustificationsPolicy.AllowedAccessReasonsEnum], Option[List[Schema.GoogleCloudKmsV1KeyAccessJustificationsPolicy.AllowedAccessReasonsEnum]]] = (fun: List[Schema.GoogleCloudKmsV1KeyAccessJustificationsPolicy.AllowedAccessReasonsEnum]) => Option(fun)
		given putSchemaGoogleCloudKmsV1CryptoKeyVersionProtectionLevelEnum: Conversion[Schema.GoogleCloudKmsV1CryptoKeyVersion.ProtectionLevelEnum, Option[Schema.GoogleCloudKmsV1CryptoKeyVersion.ProtectionLevelEnum]] = (fun: Schema.GoogleCloudKmsV1CryptoKeyVersion.ProtectionLevelEnum) => Option(fun)
		given putSchemaGoogleCloudKmsV1ExternalProtectionLevelOptions: Conversion[Schema.GoogleCloudKmsV1ExternalProtectionLevelOptions, Option[Schema.GoogleCloudKmsV1ExternalProtectionLevelOptions]] = (fun: Schema.GoogleCloudKmsV1ExternalProtectionLevelOptions) => Option(fun)
		given putSchemaGoogleCloudKmsV1KeyOperationAttestation: Conversion[Schema.GoogleCloudKmsV1KeyOperationAttestation, Option[Schema.GoogleCloudKmsV1KeyOperationAttestation]] = (fun: Schema.GoogleCloudKmsV1KeyOperationAttestation) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaGoogleCloudKmsV1CryptoKeyVersionAlgorithmEnum: Conversion[Schema.GoogleCloudKmsV1CryptoKeyVersion.AlgorithmEnum, Option[Schema.GoogleCloudKmsV1CryptoKeyVersion.AlgorithmEnum]] = (fun: Schema.GoogleCloudKmsV1CryptoKeyVersion.AlgorithmEnum) => Option(fun)
		given putSchemaGoogleCloudKmsV1CryptoKeyVersionStateEnum: Conversion[Schema.GoogleCloudKmsV1CryptoKeyVersion.StateEnum, Option[Schema.GoogleCloudKmsV1CryptoKeyVersion.StateEnum]] = (fun: Schema.GoogleCloudKmsV1CryptoKeyVersion.StateEnum) => Option(fun)
		given putListSchemaGoogleCloudKmsInventoryV1ProtectedResource: Conversion[List[Schema.GoogleCloudKmsInventoryV1ProtectedResource], Option[List[Schema.GoogleCloudKmsInventoryV1ProtectedResource]]] = (fun: List[Schema.GoogleCloudKmsInventoryV1ProtectedResource]) => Option(fun)
		given putListSchemaGoogleCloudKmsV1CryptoKey: Conversion[List[Schema.GoogleCloudKmsV1CryptoKey], Option[List[Schema.GoogleCloudKmsV1CryptoKey]]] = (fun: List[Schema.GoogleCloudKmsV1CryptoKey]) => Option(fun)
		given putSchemaGoogleCloudKmsV1KeyOperationAttestationCertificateChains: Conversion[Schema.GoogleCloudKmsV1KeyOperationAttestationCertificateChains, Option[Schema.GoogleCloudKmsV1KeyOperationAttestationCertificateChains]] = (fun: Schema.GoogleCloudKmsV1KeyOperationAttestationCertificateChains) => Option(fun)
		given putSchemaGoogleCloudKmsV1KeyOperationAttestationFormatEnum: Conversion[Schema.GoogleCloudKmsV1KeyOperationAttestation.FormatEnum, Option[Schema.GoogleCloudKmsV1KeyOperationAttestation.FormatEnum]] = (fun: Schema.GoogleCloudKmsV1KeyOperationAttestation.FormatEnum) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaGoogleCloudKmsV1CryptoKeyVersion: Conversion[Schema.GoogleCloudKmsV1CryptoKeyVersion, Option[Schema.GoogleCloudKmsV1CryptoKeyVersion]] = (fun: Schema.GoogleCloudKmsV1CryptoKeyVersion) => Option(fun)
		given putSchemaGoogleCloudKmsV1CryptoKeyVersionTemplate: Conversion[Schema.GoogleCloudKmsV1CryptoKeyVersionTemplate, Option[Schema.GoogleCloudKmsV1CryptoKeyVersionTemplate]] = (fun: Schema.GoogleCloudKmsV1CryptoKeyVersionTemplate) => Option(fun)
		given putSchemaGoogleCloudKmsV1KeyAccessJustificationsPolicy: Conversion[Schema.GoogleCloudKmsV1KeyAccessJustificationsPolicy, Option[Schema.GoogleCloudKmsV1KeyAccessJustificationsPolicy]] = (fun: Schema.GoogleCloudKmsV1KeyAccessJustificationsPolicy) => Option(fun)
		given putSchemaGoogleCloudKmsV1CryptoKeyPurposeEnum: Conversion[Schema.GoogleCloudKmsV1CryptoKey.PurposeEnum, Option[Schema.GoogleCloudKmsV1CryptoKey.PurposeEnum]] = (fun: Schema.GoogleCloudKmsV1CryptoKey.PurposeEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
