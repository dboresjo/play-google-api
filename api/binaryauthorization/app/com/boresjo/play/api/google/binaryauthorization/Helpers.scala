package com.boresjo.play.api.google.binaryauthorization

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putSchemaEvaluateGkePolicyResponseVerdictEnum: Conversion[Schema.EvaluateGkePolicyResponse.VerdictEnum, Option[Schema.EvaluateGkePolicyResponse.VerdictEnum]] = (fun: Schema.EvaluateGkePolicyResponse.VerdictEnum) => Option(fun)
		given putListSchemaPodResult: Conversion[List[Schema.PodResult], Option[List[Schema.PodResult]]] = (fun: List[Schema.PodResult]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaPodResultVerdictEnum: Conversion[Schema.PodResult.VerdictEnum, Option[Schema.PodResult.VerdictEnum]] = (fun: Schema.PodResult.VerdictEnum) => Option(fun)
		given putListSchemaImageResult: Conversion[List[Schema.ImageResult], Option[List[Schema.ImageResult]]] = (fun: List[Schema.ImageResult]) => Option(fun)
		given putSchemaImageResultVerdictEnum: Conversion[Schema.ImageResult.VerdictEnum, Option[Schema.ImageResult.VerdictEnum]] = (fun: Schema.ImageResult.VerdictEnum) => Option(fun)
		given putSchemaAllowlistResult: Conversion[Schema.AllowlistResult, Option[Schema.AllowlistResult]] = (fun: Schema.AllowlistResult) => Option(fun)
		given putSchemaCheckSetResult: Conversion[Schema.CheckSetResult, Option[Schema.CheckSetResult]] = (fun: Schema.CheckSetResult) => Option(fun)
		given putSchemaScope: Conversion[Schema.Scope, Option[Schema.Scope]] = (fun: Schema.Scope) => Option(fun)
		given putSchemaCheckResults: Conversion[Schema.CheckResults, Option[Schema.CheckResults]] = (fun: Schema.CheckResults) => Option(fun)
		given putListSchemaCheckResult: Conversion[List[Schema.CheckResult], Option[List[Schema.CheckResult]]] = (fun: List[Schema.CheckResult]) => Option(fun)
		given putSchemaEvaluationResult: Conversion[Schema.EvaluationResult, Option[Schema.EvaluationResult]] = (fun: Schema.EvaluationResult) => Option(fun)
		given putSchemaEvaluationResultVerdictEnum: Conversion[Schema.EvaluationResult.VerdictEnum, Option[Schema.EvaluationResult.VerdictEnum]] = (fun: Schema.EvaluationResult.VerdictEnum) => Option(fun)
		given putSchemaGkePolicy: Conversion[Schema.GkePolicy, Option[Schema.GkePolicy]] = (fun: Schema.GkePolicy) => Option(fun)
		given putSchemaImageAllowlist: Conversion[Schema.ImageAllowlist, Option[Schema.ImageAllowlist]] = (fun: Schema.ImageAllowlist) => Option(fun)
		given putListSchemaCheckSet: Conversion[List[Schema.CheckSet], Option[List[Schema.CheckSet]]] = (fun: List[Schema.CheckSet]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putListSchemaCheck: Conversion[List[Schema.Check], Option[List[Schema.Check]]] = (fun: List[Schema.Check]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaSimpleSigningAttestationCheck: Conversion[Schema.SimpleSigningAttestationCheck, Option[Schema.SimpleSigningAttestationCheck]] = (fun: Schema.SimpleSigningAttestationCheck) => Option(fun)
		given putSchemaTrustedDirectoryCheck: Conversion[Schema.TrustedDirectoryCheck, Option[Schema.TrustedDirectoryCheck]] = (fun: Schema.TrustedDirectoryCheck) => Option(fun)
		given putSchemaImageFreshnessCheck: Conversion[Schema.ImageFreshnessCheck, Option[Schema.ImageFreshnessCheck]] = (fun: Schema.ImageFreshnessCheck) => Option(fun)
		given putSchemaVulnerabilityCheck: Conversion[Schema.VulnerabilityCheck, Option[Schema.VulnerabilityCheck]] = (fun: Schema.VulnerabilityCheck) => Option(fun)
		given putSchemaSlsaCheck: Conversion[Schema.SlsaCheck, Option[Schema.SlsaCheck]] = (fun: Schema.SlsaCheck) => Option(fun)
		given putSchemaSigstoreSignatureCheck: Conversion[Schema.SigstoreSignatureCheck, Option[Schema.SigstoreSignatureCheck]] = (fun: Schema.SigstoreSignatureCheck) => Option(fun)
		given putListSchemaAttestationAuthenticator: Conversion[List[Schema.AttestationAuthenticator], Option[List[Schema.AttestationAuthenticator]]] = (fun: List[Schema.AttestationAuthenticator]) => Option(fun)
		given putSchemaPkixPublicKeySet: Conversion[Schema.PkixPublicKeySet, Option[Schema.PkixPublicKeySet]] = (fun: Schema.PkixPublicKeySet) => Option(fun)
		given putListSchemaPkixPublicKey: Conversion[List[Schema.PkixPublicKey], Option[List[Schema.PkixPublicKey]]] = (fun: List[Schema.PkixPublicKey]) => Option(fun)
		given putSchemaPkixPublicKeySignatureAlgorithmEnum: Conversion[Schema.PkixPublicKey.SignatureAlgorithmEnum, Option[Schema.PkixPublicKey.SignatureAlgorithmEnum]] = (fun: Schema.PkixPublicKey.SignatureAlgorithmEnum) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaVulnerabilityCheckMaximumUnfixableSeverityEnum: Conversion[Schema.VulnerabilityCheck.MaximumUnfixableSeverityEnum, Option[Schema.VulnerabilityCheck.MaximumUnfixableSeverityEnum]] = (fun: Schema.VulnerabilityCheck.MaximumUnfixableSeverityEnum) => Option(fun)
		given putSchemaVulnerabilityCheckMaximumFixableSeverityEnum: Conversion[Schema.VulnerabilityCheck.MaximumFixableSeverityEnum, Option[Schema.VulnerabilityCheck.MaximumFixableSeverityEnum]] = (fun: Schema.VulnerabilityCheck.MaximumFixableSeverityEnum) => Option(fun)
		given putListSchemaVerificationRule: Conversion[List[Schema.VerificationRule], Option[List[Schema.VerificationRule]]] = (fun: List[Schema.VerificationRule]) => Option(fun)
		given putSchemaVerificationRuleTrustedBuilderEnum: Conversion[Schema.VerificationRule.TrustedBuilderEnum, Option[Schema.VerificationRule.TrustedBuilderEnum]] = (fun: Schema.VerificationRule.TrustedBuilderEnum) => Option(fun)
		given putSchemaAttestationSource: Conversion[Schema.AttestationSource, Option[Schema.AttestationSource]] = (fun: Schema.AttestationSource) => Option(fun)
		given putListSchemaSigstoreAuthority: Conversion[List[Schema.SigstoreAuthority], Option[List[Schema.SigstoreAuthority]]] = (fun: List[Schema.SigstoreAuthority]) => Option(fun)
		given putSchemaSigstorePublicKeySet: Conversion[Schema.SigstorePublicKeySet, Option[Schema.SigstorePublicKeySet]] = (fun: Schema.SigstorePublicKeySet) => Option(fun)
		given putListSchemaSigstorePublicKey: Conversion[List[Schema.SigstorePublicKey], Option[List[Schema.SigstorePublicKey]]] = (fun: List[Schema.SigstorePublicKey]) => Option(fun)
		given putListSchemaPlatformPolicy: Conversion[List[Schema.PlatformPolicy], Option[List[Schema.PlatformPolicy]]] = (fun: List[Schema.PlatformPolicy]) => Option(fun)
		given putSchemaPolicyGlobalPolicyEvaluationModeEnum: Conversion[Schema.Policy.GlobalPolicyEvaluationModeEnum, Option[Schema.Policy.GlobalPolicyEvaluationModeEnum]] = (fun: Schema.Policy.GlobalPolicyEvaluationModeEnum) => Option(fun)
		given putListSchemaAdmissionWhitelistPattern: Conversion[List[Schema.AdmissionWhitelistPattern], Option[List[Schema.AdmissionWhitelistPattern]]] = (fun: List[Schema.AdmissionWhitelistPattern]) => Option(fun)
		given putMapStringSchemaAdmissionRule: Conversion[Map[String, Schema.AdmissionRule], Option[Map[String, Schema.AdmissionRule]]] = (fun: Map[String, Schema.AdmissionRule]) => Option(fun)
		given putSchemaAdmissionRule: Conversion[Schema.AdmissionRule, Option[Schema.AdmissionRule]] = (fun: Schema.AdmissionRule) => Option(fun)
		given putSchemaAdmissionRuleEvaluationModeEnum: Conversion[Schema.AdmissionRule.EvaluationModeEnum, Option[Schema.AdmissionRule.EvaluationModeEnum]] = (fun: Schema.AdmissionRule.EvaluationModeEnum) => Option(fun)
		given putSchemaAdmissionRuleEnforcementModeEnum: Conversion[Schema.AdmissionRule.EnforcementModeEnum, Option[Schema.AdmissionRule.EnforcementModeEnum]] = (fun: Schema.AdmissionRule.EnforcementModeEnum) => Option(fun)
		given putSchemaUserOwnedGrafeasNote: Conversion[Schema.UserOwnedGrafeasNote, Option[Schema.UserOwnedGrafeasNote]] = (fun: Schema.UserOwnedGrafeasNote) => Option(fun)
		given putListSchemaAttestorPublicKey: Conversion[List[Schema.AttestorPublicKey], Option[List[Schema.AttestorPublicKey]]] = (fun: List[Schema.AttestorPublicKey]) => Option(fun)
		given putSchemaPkixPublicKey: Conversion[Schema.PkixPublicKey, Option[Schema.PkixPublicKey]] = (fun: Schema.PkixPublicKey) => Option(fun)
		given putListSchemaAttestor: Conversion[List[Schema.Attestor], Option[List[Schema.Attestor]]] = (fun: List[Schema.Attestor]) => Option(fun)
		given putSchemaAttestationOccurrence: Conversion[Schema.AttestationOccurrence, Option[Schema.AttestationOccurrence]] = (fun: Schema.AttestationOccurrence) => Option(fun)
		given putListSchemaSignature: Conversion[List[Schema.Signature], Option[List[Schema.Signature]]] = (fun: List[Schema.Signature]) => Option(fun)
		given putListSchemaJwt: Conversion[List[Schema.Jwt], Option[List[Schema.Jwt]]] = (fun: List[Schema.Jwt]) => Option(fun)
		given putSchemaValidateAttestationOccurrenceResponseResultEnum: Conversion[Schema.ValidateAttestationOccurrenceResponse.ResultEnum, Option[Schema.ValidateAttestationOccurrenceResponse.ResultEnum]] = (fun: Schema.ValidateAttestationOccurrenceResponse.ResultEnum) => Option(fun)
		given putSchemaIamPolicy: Conversion[Schema.IamPolicy, Option[Schema.IamPolicy]] = (fun: Schema.IamPolicy) => Option(fun)
		given putListSchemaBinding: Conversion[List[Schema.Binding], Option[List[Schema.Binding]]] = (fun: List[Schema.Binding]) => Option(fun)
		given putSchemaExpr: Conversion[Schema.Expr, Option[Schema.Expr]] = (fun: Schema.Expr) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
