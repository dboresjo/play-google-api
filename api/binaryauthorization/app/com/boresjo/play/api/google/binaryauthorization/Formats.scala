package com.boresjo.play.api.google.binaryauthorization

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtEvaluateGkePolicyRequest: Format[Schema.EvaluateGkePolicyRequest] = Json.format[Schema.EvaluateGkePolicyRequest]
	given fmtEvaluateGkePolicyResponse: Format[Schema.EvaluateGkePolicyResponse] = Json.format[Schema.EvaluateGkePolicyResponse]
	given fmtEvaluateGkePolicyResponseVerdictEnum: Format[Schema.EvaluateGkePolicyResponse.VerdictEnum] = JsonEnumFormat[Schema.EvaluateGkePolicyResponse.VerdictEnum]
	given fmtPodResult: Format[Schema.PodResult] = Json.format[Schema.PodResult]
	given fmtPodResultVerdictEnum: Format[Schema.PodResult.VerdictEnum] = JsonEnumFormat[Schema.PodResult.VerdictEnum]
	given fmtImageResult: Format[Schema.ImageResult] = Json.format[Schema.ImageResult]
	given fmtImageResultVerdictEnum: Format[Schema.ImageResult.VerdictEnum] = JsonEnumFormat[Schema.ImageResult.VerdictEnum]
	given fmtAllowlistResult: Format[Schema.AllowlistResult] = Json.format[Schema.AllowlistResult]
	given fmtCheckSetResult: Format[Schema.CheckSetResult] = Json.format[Schema.CheckSetResult]
	given fmtScope: Format[Schema.Scope] = Json.format[Schema.Scope]
	given fmtCheckResults: Format[Schema.CheckResults] = Json.format[Schema.CheckResults]
	given fmtCheckResult: Format[Schema.CheckResult] = Json.format[Schema.CheckResult]
	given fmtEvaluationResult: Format[Schema.EvaluationResult] = Json.format[Schema.EvaluationResult]
	given fmtEvaluationResultVerdictEnum: Format[Schema.EvaluationResult.VerdictEnum] = JsonEnumFormat[Schema.EvaluationResult.VerdictEnum]
	given fmtPlatformPolicy: Format[Schema.PlatformPolicy] = Json.format[Schema.PlatformPolicy]
	given fmtGkePolicy: Format[Schema.GkePolicy] = Json.format[Schema.GkePolicy]
	given fmtImageAllowlist: Format[Schema.ImageAllowlist] = Json.format[Schema.ImageAllowlist]
	given fmtCheckSet: Format[Schema.CheckSet] = Json.format[Schema.CheckSet]
	given fmtCheck: Format[Schema.Check] = Json.format[Schema.Check]
	given fmtSimpleSigningAttestationCheck: Format[Schema.SimpleSigningAttestationCheck] = Json.format[Schema.SimpleSigningAttestationCheck]
	given fmtTrustedDirectoryCheck: Format[Schema.TrustedDirectoryCheck] = Json.format[Schema.TrustedDirectoryCheck]
	given fmtImageFreshnessCheck: Format[Schema.ImageFreshnessCheck] = Json.format[Schema.ImageFreshnessCheck]
	given fmtVulnerabilityCheck: Format[Schema.VulnerabilityCheck] = Json.format[Schema.VulnerabilityCheck]
	given fmtSlsaCheck: Format[Schema.SlsaCheck] = Json.format[Schema.SlsaCheck]
	given fmtSigstoreSignatureCheck: Format[Schema.SigstoreSignatureCheck] = Json.format[Schema.SigstoreSignatureCheck]
	given fmtAttestationAuthenticator: Format[Schema.AttestationAuthenticator] = Json.format[Schema.AttestationAuthenticator]
	given fmtPkixPublicKeySet: Format[Schema.PkixPublicKeySet] = Json.format[Schema.PkixPublicKeySet]
	given fmtPkixPublicKey: Format[Schema.PkixPublicKey] = Json.format[Schema.PkixPublicKey]
	given fmtPkixPublicKeySignatureAlgorithmEnum: Format[Schema.PkixPublicKey.SignatureAlgorithmEnum] = JsonEnumFormat[Schema.PkixPublicKey.SignatureAlgorithmEnum]
	given fmtVulnerabilityCheckMaximumUnfixableSeverityEnum: Format[Schema.VulnerabilityCheck.MaximumUnfixableSeverityEnum] = JsonEnumFormat[Schema.VulnerabilityCheck.MaximumUnfixableSeverityEnum]
	given fmtVulnerabilityCheckMaximumFixableSeverityEnum: Format[Schema.VulnerabilityCheck.MaximumFixableSeverityEnum] = JsonEnumFormat[Schema.VulnerabilityCheck.MaximumFixableSeverityEnum]
	given fmtVerificationRule: Format[Schema.VerificationRule] = Json.format[Schema.VerificationRule]
	given fmtVerificationRuleTrustedBuilderEnum: Format[Schema.VerificationRule.TrustedBuilderEnum] = JsonEnumFormat[Schema.VerificationRule.TrustedBuilderEnum]
	given fmtAttestationSource: Format[Schema.AttestationSource] = Json.format[Schema.AttestationSource]
	given fmtSigstoreAuthority: Format[Schema.SigstoreAuthority] = Json.format[Schema.SigstoreAuthority]
	given fmtSigstorePublicKeySet: Format[Schema.SigstorePublicKeySet] = Json.format[Schema.SigstorePublicKeySet]
	given fmtSigstorePublicKey: Format[Schema.SigstorePublicKey] = Json.format[Schema.SigstorePublicKey]
	given fmtListPlatformPoliciesResponse: Format[Schema.ListPlatformPoliciesResponse] = Json.format[Schema.ListPlatformPoliciesResponse]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtPolicy: Format[Schema.Policy] = Json.format[Schema.Policy]
	given fmtPolicyGlobalPolicyEvaluationModeEnum: Format[Schema.Policy.GlobalPolicyEvaluationModeEnum] = JsonEnumFormat[Schema.Policy.GlobalPolicyEvaluationModeEnum]
	given fmtAdmissionWhitelistPattern: Format[Schema.AdmissionWhitelistPattern] = Json.format[Schema.AdmissionWhitelistPattern]
	given fmtAdmissionRule: Format[Schema.AdmissionRule] = Json.format[Schema.AdmissionRule]
	given fmtAdmissionRuleEvaluationModeEnum: Format[Schema.AdmissionRule.EvaluationModeEnum] = JsonEnumFormat[Schema.AdmissionRule.EvaluationModeEnum]
	given fmtAdmissionRuleEnforcementModeEnum: Format[Schema.AdmissionRule.EnforcementModeEnum] = JsonEnumFormat[Schema.AdmissionRule.EnforcementModeEnum]
	given fmtAttestor: Format[Schema.Attestor] = Json.format[Schema.Attestor]
	given fmtUserOwnedGrafeasNote: Format[Schema.UserOwnedGrafeasNote] = Json.format[Schema.UserOwnedGrafeasNote]
	given fmtAttestorPublicKey: Format[Schema.AttestorPublicKey] = Json.format[Schema.AttestorPublicKey]
	given fmtListAttestorsResponse: Format[Schema.ListAttestorsResponse] = Json.format[Schema.ListAttestorsResponse]
	given fmtValidateAttestationOccurrenceRequest: Format[Schema.ValidateAttestationOccurrenceRequest] = Json.format[Schema.ValidateAttestationOccurrenceRequest]
	given fmtAttestationOccurrence: Format[Schema.AttestationOccurrence] = Json.format[Schema.AttestationOccurrence]
	given fmtSignature: Format[Schema.Signature] = Json.format[Schema.Signature]
	given fmtJwt: Format[Schema.Jwt] = Json.format[Schema.Jwt]
	given fmtValidateAttestationOccurrenceResponse: Format[Schema.ValidateAttestationOccurrenceResponse] = Json.format[Schema.ValidateAttestationOccurrenceResponse]
	given fmtValidateAttestationOccurrenceResponseResultEnum: Format[Schema.ValidateAttestationOccurrenceResponse.ResultEnum] = JsonEnumFormat[Schema.ValidateAttestationOccurrenceResponse.ResultEnum]
	given fmtSetIamPolicyRequest: Format[Schema.SetIamPolicyRequest] = Json.format[Schema.SetIamPolicyRequest]
	given fmtIamPolicy: Format[Schema.IamPolicy] = Json.format[Schema.IamPolicy]
	given fmtBinding: Format[Schema.Binding] = Json.format[Schema.Binding]
	given fmtExpr: Format[Schema.Expr] = Json.format[Schema.Expr]
	given fmtTestIamPermissionsRequest: Format[Schema.TestIamPermissionsRequest] = Json.format[Schema.TestIamPermissionsRequest]
	given fmtTestIamPermissionsResponse: Format[Schema.TestIamPermissionsResponse] = Json.format[Schema.TestIamPermissionsResponse]
}