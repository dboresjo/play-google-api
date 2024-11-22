package com.boresjo.play.api.google.certificatemanager

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaOperation: Conversion[List[Schema.Operation], Option[List[Schema.Operation]]] = (fun: List[Schema.Operation]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putListSchemaCertificate: Conversion[List[Schema.Certificate], Option[List[Schema.Certificate]]] = (fun: List[Schema.Certificate]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaSelfManagedCertificate: Conversion[Schema.SelfManagedCertificate, Option[Schema.SelfManagedCertificate]] = (fun: Schema.SelfManagedCertificate) => Option(fun)
		given putSchemaManagedCertificate: Conversion[Schema.ManagedCertificate, Option[Schema.ManagedCertificate]] = (fun: Schema.ManagedCertificate) => Option(fun)
		given putSchemaCertificateScopeEnum: Conversion[Schema.Certificate.ScopeEnum, Option[Schema.Certificate.ScopeEnum]] = (fun: Schema.Certificate.ScopeEnum) => Option(fun)
		given putSchemaManagedCertificateStateEnum: Conversion[Schema.ManagedCertificate.StateEnum, Option[Schema.ManagedCertificate.StateEnum]] = (fun: Schema.ManagedCertificate.StateEnum) => Option(fun)
		given putSchemaProvisioningIssue: Conversion[Schema.ProvisioningIssue, Option[Schema.ProvisioningIssue]] = (fun: Schema.ProvisioningIssue) => Option(fun)
		given putListSchemaAuthorizationAttemptInfo: Conversion[List[Schema.AuthorizationAttemptInfo], Option[List[Schema.AuthorizationAttemptInfo]]] = (fun: List[Schema.AuthorizationAttemptInfo]) => Option(fun)
		given putSchemaProvisioningIssueReasonEnum: Conversion[Schema.ProvisioningIssue.ReasonEnum, Option[Schema.ProvisioningIssue.ReasonEnum]] = (fun: Schema.ProvisioningIssue.ReasonEnum) => Option(fun)
		given putSchemaAuthorizationAttemptInfoStateEnum: Conversion[Schema.AuthorizationAttemptInfo.StateEnum, Option[Schema.AuthorizationAttemptInfo.StateEnum]] = (fun: Schema.AuthorizationAttemptInfo.StateEnum) => Option(fun)
		given putSchemaAuthorizationAttemptInfoFailureReasonEnum: Conversion[Schema.AuthorizationAttemptInfo.FailureReasonEnum, Option[Schema.AuthorizationAttemptInfo.FailureReasonEnum]] = (fun: Schema.AuthorizationAttemptInfo.FailureReasonEnum) => Option(fun)
		given putListSchemaCertificateMap: Conversion[List[Schema.CertificateMap], Option[List[Schema.CertificateMap]]] = (fun: List[Schema.CertificateMap]) => Option(fun)
		given putListSchemaGclbTarget: Conversion[List[Schema.GclbTarget], Option[List[Schema.GclbTarget]]] = (fun: List[Schema.GclbTarget]) => Option(fun)
		given putListSchemaIpConfig: Conversion[List[Schema.IpConfig], Option[List[Schema.IpConfig]]] = (fun: List[Schema.IpConfig]) => Option(fun)
		given putListInt: Conversion[List[Int], Option[List[Int]]] = (fun: List[Int]) => Option(fun)
		given putListSchemaCertificateMapEntry: Conversion[List[Schema.CertificateMapEntry], Option[List[Schema.CertificateMapEntry]]] = (fun: List[Schema.CertificateMapEntry]) => Option(fun)
		given putSchemaCertificateMapEntryMatcherEnum: Conversion[Schema.CertificateMapEntry.MatcherEnum, Option[Schema.CertificateMapEntry.MatcherEnum]] = (fun: Schema.CertificateMapEntry.MatcherEnum) => Option(fun)
		given putSchemaCertificateMapEntryStateEnum: Conversion[Schema.CertificateMapEntry.StateEnum, Option[Schema.CertificateMapEntry.StateEnum]] = (fun: Schema.CertificateMapEntry.StateEnum) => Option(fun)
		given putListSchemaDnsAuthorization: Conversion[List[Schema.DnsAuthorization], Option[List[Schema.DnsAuthorization]]] = (fun: List[Schema.DnsAuthorization]) => Option(fun)
		given putSchemaDnsResourceRecord: Conversion[Schema.DnsResourceRecord, Option[Schema.DnsResourceRecord]] = (fun: Schema.DnsResourceRecord) => Option(fun)
		given putSchemaDnsAuthorizationTypeEnum: Conversion[Schema.DnsAuthorization.TypeEnum, Option[Schema.DnsAuthorization.TypeEnum]] = (fun: Schema.DnsAuthorization.TypeEnum) => Option(fun)
		given putListSchemaCertificateIssuanceConfig: Conversion[List[Schema.CertificateIssuanceConfig], Option[List[Schema.CertificateIssuanceConfig]]] = (fun: List[Schema.CertificateIssuanceConfig]) => Option(fun)
		given putSchemaCertificateAuthorityConfig: Conversion[Schema.CertificateAuthorityConfig, Option[Schema.CertificateAuthorityConfig]] = (fun: Schema.CertificateAuthorityConfig) => Option(fun)
		given putSchemaCertificateIssuanceConfigKeyAlgorithmEnum: Conversion[Schema.CertificateIssuanceConfig.KeyAlgorithmEnum, Option[Schema.CertificateIssuanceConfig.KeyAlgorithmEnum]] = (fun: Schema.CertificateIssuanceConfig.KeyAlgorithmEnum) => Option(fun)
		given putSchemaCertificateAuthorityServiceConfig: Conversion[Schema.CertificateAuthorityServiceConfig, Option[Schema.CertificateAuthorityServiceConfig]] = (fun: Schema.CertificateAuthorityServiceConfig) => Option(fun)
		given putListSchemaTrustConfig: Conversion[List[Schema.TrustConfig], Option[List[Schema.TrustConfig]]] = (fun: List[Schema.TrustConfig]) => Option(fun)
		given putListSchemaTrustStore: Conversion[List[Schema.TrustStore], Option[List[Schema.TrustStore]]] = (fun: List[Schema.TrustStore]) => Option(fun)
		given putListSchemaAllowlistedCertificate: Conversion[List[Schema.AllowlistedCertificate], Option[List[Schema.AllowlistedCertificate]]] = (fun: List[Schema.AllowlistedCertificate]) => Option(fun)
		given putListSchemaTrustAnchor: Conversion[List[Schema.TrustAnchor], Option[List[Schema.TrustAnchor]]] = (fun: List[Schema.TrustAnchor]) => Option(fun)
		given putListSchemaIntermediateCA: Conversion[List[Schema.IntermediateCA], Option[List[Schema.IntermediateCA]]] = (fun: List[Schema.IntermediateCA]) => Option(fun)
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
