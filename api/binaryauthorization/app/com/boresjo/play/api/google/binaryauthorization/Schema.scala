package com.boresjo.play.api.google.binaryauthorization

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class EvaluateGkePolicyRequest(
	  /** Required. JSON or YAML blob representing a Kubernetes resource. */
		resource: Option[Map[String, JsValue]] = None
	)
	
	object EvaluateGkePolicyResponse {
		enum VerdictEnum extends Enum[VerdictEnum] { case VERDICT_UNSPECIFIED, CONFORMANT, NON_CONFORMANT, ERROR }
	}
	case class EvaluateGkePolicyResponse(
	  /** The result of evaluating all Pods in the request. */
		verdict: Option[Schema.EvaluateGkePolicyResponse.VerdictEnum] = None,
	  /** Evaluation result for each Pod contained in the request. */
		results: Option[List[Schema.PodResult]] = None
	)
	
	object PodResult {
		enum VerdictEnum extends Enum[VerdictEnum] { case POD_VERDICT_UNSPECIFIED, CONFORMANT, NON_CONFORMANT, ERROR }
	}
	case class PodResult(
	  /** The name of the Pod. */
		podName: Option[String] = None,
	  /** The Kubernetes namespace of the Pod. */
		kubernetesNamespace: Option[String] = None,
	  /** The Kubernetes service account of the Pod. */
		kubernetesServiceAccount: Option[String] = None,
	  /** The result of evaluating this Pod. */
		verdict: Option[Schema.PodResult.VerdictEnum] = None,
	  /** Per-image details. */
		imageResults: Option[List[Schema.ImageResult]] = None
	)
	
	object ImageResult {
		enum VerdictEnum extends Enum[VerdictEnum] { case IMAGE_VERDICT_UNSPECIFIED, CONFORMANT, NON_CONFORMANT, ERROR }
	}
	case class ImageResult(
	  /** Image URI from the request. */
		imageUri: Option[String] = None,
	  /** The result of evaluating this image. */
		verdict: Option[Schema.ImageResult.VerdictEnum] = None,
	  /** If the image was exempted by a top-level allow_pattern, contains the allowlist pattern that the image name matched. */
		allowlistResult: Option[Schema.AllowlistResult] = None,
	  /** If a check set was evaluated, contains the result of the check set. Empty if there were no check sets. */
		checkSetResult: Option[Schema.CheckSetResult] = None,
	  /** Explanation of this image result. Only populated if no check sets were evaluated. */
		explanation: Option[String] = None
	)
	
	case class AllowlistResult(
	  /** The allowlist pattern that the image matched. */
		matchedPattern: Option[String] = None
	)
	
	case class CheckSetResult(
	  /** The index of the check set. */
		index: Option[String] = None,
	  /** The name of the check set. */
		displayName: Option[String] = None,
	  /** The scope of the check set. */
		scope: Option[Schema.Scope] = None,
	  /** If the image was exempted by an allow_pattern in the check set, contains the pattern that the image name matched. */
		allowlistResult: Option[Schema.AllowlistResult] = None,
	  /** If checks were evaluated, contains the results of evaluating each check. */
		checkResults: Option[Schema.CheckResults] = None,
	  /** Explanation of this check set result. Only populated if no checks were evaluated. */
		explanation: Option[String] = None
	)
	
	case class Scope(
	  /** Optional. Matches a single Kubernetes service account, e.g. `my-namespace:my-service-account`. `kubernetes_service_account` scope is always more specific than `kubernetes_namespace` scope for the same namespace. */
		kubernetesServiceAccount: Option[String] = None,
	  /** Optional. Matches all Kubernetes service accounts in the provided namespace, unless a more specific `kubernetes_service_account` scope already matched. */
		kubernetesNamespace: Option[String] = None
	)
	
	case class CheckResults(
	  /** Per-check details. */
		results: Option[List[Schema.CheckResult]] = None
	)
	
	case class CheckResult(
	  /** The index of the check. */
		index: Option[String] = None,
	  /** The name of the check. */
		displayName: Option[String] = None,
	  /** The type of the check. */
		`type`: Option[String] = None,
	  /** If the image was exempted by an allow_pattern in the check, contains the pattern that the image name matched. */
		allowlistResult: Option[Schema.AllowlistResult] = None,
	  /** If a check was evaluated, contains the result of the check. */
		evaluationResult: Option[Schema.EvaluationResult] = None,
	  /** Explanation of this check result. */
		explanation: Option[String] = None
	)
	
	object EvaluationResult {
		enum VerdictEnum extends Enum[VerdictEnum] { case CHECK_VERDICT_UNSPECIFIED, CONFORMANT, NON_CONFORMANT, ERROR }
	}
	case class EvaluationResult(
	  /** The result of evaluating this check. */
		verdict: Option[Schema.EvaluationResult.VerdictEnum] = None
	)
	
	case class PlatformPolicy(
	  /** Output only. The relative resource name of the Binary Authorization platform policy, in the form of `projects/&#42;/platforms/&#42;/policies/&#42;`. */
		name: Option[String] = None,
	  /** Optional. A description comment about the policy. */
		description: Option[String] = None,
	  /** Optional. GKE platform-specific policy. */
		gkePolicy: Option[Schema.GkePolicy] = None,
	  /** Output only. Time when the policy was last updated. */
		updateTime: Option[String] = None,
	  /** Optional. Used to prevent updating the policy when another request has updated it since it was retrieved. */
		etag: Option[String] = None
	)
	
	case class GkePolicy(
	  /** Optional. Images exempted from this policy. If any of the patterns match the image being evaluated, the rest of the policy will not be evaluated. */
		imageAllowlist: Option[Schema.ImageAllowlist] = None,
	  /** Optional. The `CheckSet` objects to apply, scoped by namespace or namespace and service account. Exactly one `CheckSet` will be evaluated for a given Pod (unless the list is empty, in which case the behavior is "always allow"). If multiple `CheckSet` objects have scopes that match the namespace and service account of the Pod being evaluated, only the `CheckSet` with the MOST SPECIFIC scope will match. `CheckSet` objects must be listed in order of decreasing specificity, i.e. if a scope matches a given service account (which must include the namespace), it must come before a `CheckSet` with a scope matching just that namespace. This property is enforced by server-side validation. The purpose of this restriction is to ensure that if more than one `CheckSet` matches a given Pod, the `CheckSet` that will be evaluated will always be the first in the list to match (because if any other matches, it must be less specific). If `check_sets` is empty, the default behavior is to allow all images. If `check_sets` is non-empty, the last `check_sets` entry must always be a `CheckSet` with no scope set, i.e. a catchall to handle any situation not caught by the preceding `CheckSet` objects. */
		checkSets: Option[List[Schema.CheckSet]] = None
	)
	
	case class ImageAllowlist(
	  /** Required. A disjunction of image patterns to allow. If any of these patterns match, then the image is considered exempted by this allowlist. */
		allowPattern: Option[List[String]] = None
	)
	
	case class CheckSet(
	  /** Optional. A user-provided name for this `CheckSet`. This field has no effect on the policy evaluation behavior except to improve readability of messages in evaluation results. */
		displayName: Option[String] = None,
	  /** Optional. The scope to which this `CheckSet` applies. If unset or an empty string (the default), applies to all namespaces and service accounts. See the `Scope` message documentation for details on scoping rules. */
		scope: Option[Schema.Scope] = None,
	  /** Optional. Images exempted from this `CheckSet`. If any of the patterns match the image being evaluated, no checks in the `CheckSet` will be evaluated. */
		imageAllowlist: Option[Schema.ImageAllowlist] = None,
	  /** Optional. The checks to apply. The ultimate result of evaluating the check set will be "allow" if and only if every check in `checks` evaluates to "allow". If `checks` is empty, the default behavior is "always allow". */
		checks: Option[List[Schema.Check]] = None
	)
	
	case class Check(
	  /** Optional. A user-provided name for this check. This field has no effect on the policy evaluation behavior except to improve readability of messages in evaluation results. */
		displayName: Option[String] = None,
	  /** Optional. Images exempted from this check. If any of the patterns match the image url, the check will not be evaluated. */
		imageAllowlist: Option[Schema.ImageAllowlist] = None,
	  /** Optional. A special-case check that always denies. Note that this still only applies when the scope of the `CheckSet` applies and the image isn't exempted by an image allowlist. This check is primarily useful for testing, or to set the default behavior for all unmatched scopes to "deny". */
		alwaysDeny: Option[Boolean] = None,
	  /** Optional. Require a SimpleSigning-type attestation for every image in the deployment. */
		simpleSigningAttestationCheck: Option[Schema.SimpleSigningAttestationCheck] = None,
	  /** Optional. Require that an image lives in a trusted directory. */
		trustedDirectoryCheck: Option[Schema.TrustedDirectoryCheck] = None,
	  /** Optional. Require that an image is no older than a configured expiration time. Image age is determined by its upload time. */
		imageFreshnessCheck: Option[Schema.ImageFreshnessCheck] = None,
	  /** Optional. Require that an image does not contain vulnerabilities that violate the configured rules, such as based on severity levels. */
		vulnerabilityCheck: Option[Schema.VulnerabilityCheck] = None,
	  /** Optional. Require that an image was built by a trusted builder (such as Google Cloud Build), meets requirements for Supply chain Levels for Software Artifacts (SLSA), and was built from a trusted source code repostitory. */
		slsaCheck: Option[Schema.SlsaCheck] = None,
	  /** Optional. Require that an image was signed by Cosign with a trusted key. This check requires that both the image and signature are stored in Artifact Registry. */
		sigstoreSignatureCheck: Option[Schema.SigstoreSignatureCheck] = None
	)
	
	case class SimpleSigningAttestationCheck(
	  /** Required. The authenticators required by this check to verify an attestation. Typically this is one or more PKIX public keys for signature verification. Only one authenticator needs to consider an attestation verified in order for an attestation to be considered fully authenticated. In otherwords, this list of authenticators is an "OR" of the authenticator results. At least one authenticator is required. */
		attestationAuthenticators: Option[List[Schema.AttestationAuthenticator]] = None,
	  /** Optional. The projects where attestations are stored as Container Analysis Occurrences, in the format `projects/[PROJECT_ID]`. Only one attestation needs to successfully verify an image for this check to pass, so a single verified attestation found in any of `container_analysis_attestation_projects` is sufficient for the check to pass. A project ID must be used, not a project number. When fetching Occurrences from Container Analysis, only `AttestationOccurrence` kinds are considered. In the future, additional Occurrence kinds may be added to the query. Maximum number of `container_analysis_attestation_projects` allowed in each `SimpleSigningAttestationCheck` is 10. */
		containerAnalysisAttestationProjects: Option[List[String]] = None
	)
	
	case class AttestationAuthenticator(
	  /** Optional. A user-provided name for this `AttestationAuthenticator`. This field has no effect on the policy evaluation behavior except to improve readability of messages in evaluation results. */
		displayName: Option[String] = None,
	  /** Optional. A set of raw PKIX SubjectPublicKeyInfo format public keys. If any public key in the set validates the attestation signature, then the signature is considered authenticated (i.e. any one key is sufficient to authenticate). */
		pkixPublicKeySet: Option[Schema.PkixPublicKeySet] = None
	)
	
	case class PkixPublicKeySet(
	  /** Required. `pkix_public_keys` must have at least one entry. */
		pkixPublicKeys: Option[List[Schema.PkixPublicKey]] = None
	)
	
	object PkixPublicKey {
		enum SignatureAlgorithmEnum extends Enum[SignatureAlgorithmEnum] { case SIGNATURE_ALGORITHM_UNSPECIFIED, RSA_PSS_2048_SHA256, RSA_SIGN_PSS_2048_SHA256, RSA_PSS_3072_SHA256, RSA_SIGN_PSS_3072_SHA256, RSA_PSS_4096_SHA256, RSA_SIGN_PSS_4096_SHA256, RSA_PSS_4096_SHA512, RSA_SIGN_PSS_4096_SHA512, RSA_SIGN_PKCS1_2048_SHA256, RSA_SIGN_PKCS1_3072_SHA256, RSA_SIGN_PKCS1_4096_SHA256, RSA_SIGN_PKCS1_4096_SHA512, ECDSA_P256_SHA256, EC_SIGN_P256_SHA256, ECDSA_P384_SHA384, EC_SIGN_P384_SHA384, ECDSA_P521_SHA512, EC_SIGN_P521_SHA512 }
	}
	case class PkixPublicKey(
	  /** A PEM-encoded public key, as described in https://tools.ietf.org/html/rfc7468#section-13 */
		publicKeyPem: Option[String] = None,
	  /** The signature algorithm used to verify a message against a signature using this key. These signature algorithm must match the structure and any object identifiers encoded in `public_key_pem` (i.e. this algorithm must match that of the public key). */
		signatureAlgorithm: Option[Schema.PkixPublicKey.SignatureAlgorithmEnum] = None,
	  /** Optional. The ID of this public key. Signatures verified by Binary Authorization must include the ID of the public key that can be used to verify them. The ID must match exactly contents of the `key_id` field exactly. The ID may be explicitly provided by the caller, but it MUST be a valid RFC3986 URI. If `key_id` is left blank and this `PkixPublicKey` is not used in the context of a wrapper (see next paragraph), a default key ID will be computed based on the digest of the DER encoding of the public key. If this `PkixPublicKey` is used in the context of a wrapper that has its own notion of key ID (e.g. `AttestorPublicKey`), then this field can either match that value exactly, or be left blank, in which case it behaves exactly as though it is equal to that wrapper value. */
		keyId: Option[String] = None
	)
	
	case class TrustedDirectoryCheck(
	  /** Required. List of trusted directory patterns. A pattern is in the form "registry/path/to/directory". The registry domain part is defined as two or more dot-separated words, e.g., `us.pkg.dev`, or `gcr.io`. Additionally, `&#42;` can be used in three ways as wildcards: 1. leading `&#42;` to match varying prefixes in registry subdomain (useful for location prefixes); 2. trailing `&#42;` after registry/ to match varying endings; 3. trailing `&#42;&#42;` after registry/ to match "/" as well. For example: -- `gcr.io/my-project/my-repo` is valid to match a single directory -- `&#42;-docker.pkg.dev/my-project/my-repo` or `&#42;.gcr.io/my-project` are valid to match varying prefixes -- `gcr.io/my-project/&#42;` will match all direct directories in `my-project` -- `gcr.io/my-project/&#42;&#42;` would match all directories in `my-project` -- `gcr.i&#42;` is not allowed since the registry is not completely specified -- `sub&#42;domain.gcr.io/nginx` is not valid because only leading `&#42;` or trailing `&#42;` are allowed. -- `&#42;pkg.dev/my-project/my-repo` is not valid because leading `&#42;` can only match subdomain -- `&#42;&#42;-docker.pkg.dev` is not valid because one leading `&#42;` is allowed, and that it cannot match `/` */
		trustedDirPatterns: Option[List[String]] = None
	)
	
	case class ImageFreshnessCheck(
	  /** Required. The max number of days that is allowed since the image was uploaded. Must be greater than zero. */
		maxUploadAgeDays: Option[Int] = None
	)
	
	object VulnerabilityCheck {
		enum MaximumUnfixableSeverityEnum extends Enum[MaximumUnfixableSeverityEnum] { case MAXIMUM_ALLOWED_SEVERITY_UNSPECIFIED, BLOCK_ALL, MINIMAL, LOW, MEDIUM, HIGH, CRITICAL, ALLOW_ALL }
		enum MaximumFixableSeverityEnum extends Enum[MaximumFixableSeverityEnum] { case MAXIMUM_ALLOWED_SEVERITY_UNSPECIFIED, BLOCK_ALL, MINIMAL, LOW, MEDIUM, HIGH, CRITICAL, ALLOW_ALL }
	}
	case class VulnerabilityCheck(
	  /** Optional. A list of specific CVEs to ignore even if the vulnerability level violates `maximumUnfixableSeverity` or `maximumFixableSeverity`. CVEs are listed in the format of Container Analysis note id. For example: - CVE-2021-20305 - CVE-2020-10543 The CVEs are applicable regardless of note provider project, e.g., an entry of `CVE-2021-20305` will allow vulnerabilities with a note name of either `projects/goog-vulnz/notes/CVE-2021-20305` or `projects/CUSTOM-PROJECT/notes/CVE-2021-20305`. */
		allowedCves: Option[List[String]] = None,
	  /** Optional. A list of specific CVEs to always raise warnings about even if the vulnerability level meets `maximumUnfixableSeverity` or `maximumFixableSeverity`. CVEs are listed in the format of Container Analysis note id. For example: - CVE-2021-20305 - CVE-2020-10543 The CVEs are applicable regardless of note provider project, e.g., an entry of `CVE-2021-20305` will block vulnerabilities with a note name of either `projects/goog-vulnz/notes/CVE-2021-20305` or `projects/CUSTOM-PROJECT/notes/CVE-2021-20305`. */
		blockedCves: Option[List[String]] = None,
	  /** Required. The threshold for severity for which a fix isn't currently available. This field is required and must be set. */
		maximumUnfixableSeverity: Option[Schema.VulnerabilityCheck.MaximumUnfixableSeverityEnum] = None,
	  /** Required. The threshold for severity for which a fix is currently available. This field is required and must be set. */
		maximumFixableSeverity: Option[Schema.VulnerabilityCheck.MaximumFixableSeverityEnum] = None,
	  /** Optional. The projects where vulnerabilities are stored as Container Analysis Occurrences. Each project is expressed in the resource format of `projects/[PROJECT_ID]`, e.g., `projects/my-gcp-project`. An attempt will be made for each project to fetch vulnerabilities, and all valid vulnerabilities will be used to check against the vulnerability policy. If no valid scan is found in all projects configured here, an error will be returned for the check. Maximum number of `container_analysis_vulnerability_projects` allowed in each `VulnerabilityCheck` is 10. */
		containerAnalysisVulnerabilityProjects: Option[List[String]] = None
	)
	
	case class SlsaCheck(
	  /** Specifies a list of verification rules for the SLSA attestations. An image is considered compliant with the SlsaCheck if any of the rules are satisfied. */
		rules: Option[List[Schema.VerificationRule]] = None
	)
	
	object VerificationRule {
		enum TrustedBuilderEnum extends Enum[TrustedBuilderEnum] { case BUILDER_UNSPECIFIED, GOOGLE_CLOUD_BUILD }
	}
	case class VerificationRule(
	  /** Each verification rule is used for evaluation against provenances generated by a specific builder (group). For some of the builders, such as the Google Cloud Build, users don't need to explicitly specify their roots of trust in the policy since the evaluation service can automatically fetch them based on the builder (group). */
		trustedBuilder: Option[Schema.VerificationRule.TrustedBuilderEnum] = None,
	  /** Specifies where to fetch the provenances attestations generated by the builder (group). */
		attestationSource: Option[Schema.AttestationSource] = None,
	  /** If true, require the image to be built from a top-level configuration. `trusted_source_repo_patterns` specifies the repositories containing this configuration. */
		configBasedBuildRequired: Option[Boolean] = None,
	  /** List of trusted source code repository URL patterns. These patterns match the full repository URL without its scheme (e.g. `https://`). The patterns must not include schemes. For example, the pattern `source.cloud.google.com/my-project/my-repo-name` matches the following URLs: - `source.cloud.google.com/my-project/my-repo-name` - `git+ssh://source.cloud.google.com/my-project/my-repo-name` - `https://source.cloud.google.com/my-project/my-repo-name` A pattern matches a URL either exactly or with `&#42;` wildcards. `&#42;` can be used in only two ways: 1. trailing `&#42;` after hosturi/ to match varying endings; 2. trailing `&#42;&#42;` after hosturi/ to match `/` as well. `&#42;` and `&#42;&#42;` can only be used as wildcards and can only occur at the end of the pattern after a `/`. (So it's not possible to match a URL that contains literal `&#42;`.) For example: - `github.com/my-project/my-repo` is valid to match a single repo - `github.com/my-project/&#42;` will match all direct repos in `my-project` - `github.com/&#42;&#42;` matches all repos in GitHub */
		trustedSourceRepoPatterns: Option[List[String]] = None
	)
	
	case class AttestationSource(
	  /** The IDs of the Google Cloud projects that store the SLSA attestations as Container Analysis Occurrences, in the format `projects/[PROJECT_ID]`. Maximum number of `container_analysis_attestation_projects` allowed in each `AttestationSource` is 10. */
		containerAnalysisAttestationProjects: Option[List[String]] = None
	)
	
	case class SigstoreSignatureCheck(
	  /** Required. The authorities required by this check to verify the signature. A signature only needs to be verified by one authority to pass the check. */
		sigstoreAuthorities: Option[List[Schema.SigstoreAuthority]] = None
	)
	
	case class SigstoreAuthority(
	  /** Optional. A user-provided name for this `SigstoreAuthority`. This field has no effect on the policy evaluation behavior except to improve readability of messages in evaluation results. */
		displayName: Option[String] = None,
	  /** Required. A simple set of public keys. A signature is considered valid if any keys in the set validate the signature. */
		publicKeySet: Option[Schema.SigstorePublicKeySet] = None
	)
	
	case class SigstorePublicKeySet(
	  /** Required. `public_keys` must have at least one entry. */
		publicKeys: Option[List[Schema.SigstorePublicKey]] = None
	)
	
	case class SigstorePublicKey(
	  /** The public key material in PEM format. */
		publicKeyPem: Option[String] = None
	)
	
	case class ListPlatformPoliciesResponse(
	  /** The list of platform policies. */
		platformPolicies: Option[List[Schema.PlatformPolicy]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the ListPlatformPoliciesRequest.page_token field in the subsequent call to the `ListPlatformPolicies` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class Empty(
	
	)
	
	object Policy {
		enum GlobalPolicyEvaluationModeEnum extends Enum[GlobalPolicyEvaluationModeEnum] { case GLOBAL_POLICY_EVALUATION_MODE_UNSPECIFIED, ENABLE, DISABLE }
	}
	case class Policy(
	  /** Output only. The resource name, in the format `projects/&#42;/policy`. There is at most one policy per project. */
		name: Option[String] = None,
	  /** Optional. A descriptive comment. */
		description: Option[String] = None,
	  /** Optional. Controls the evaluation of a Google-maintained global admission policy for common system-level images. Images not covered by the global policy will be subject to the project admission policy. This setting has no effect when specified inside a global admission policy. */
		globalPolicyEvaluationMode: Option[Schema.Policy.GlobalPolicyEvaluationModeEnum] = None,
	  /** Optional. Admission policy allowlisting. A matching admission request will always be permitted. This feature is typically used to exclude Google or third-party infrastructure images from Binary Authorization policies. */
		admissionWhitelistPatterns: Option[List[Schema.AdmissionWhitelistPattern]] = None,
	  /** Optional. Per-cluster admission rules. Cluster spec format: `location.clusterId`. There can be at most one admission rule per cluster spec. A `location` is either a compute zone (e.g. us-central1-a) or a region (e.g. us-central1). For `clusterId` syntax restrictions see https://cloud.google.com/container-engine/reference/rest/v1/projects.zones.clusters. */
		clusterAdmissionRules: Option[Map[String, Schema.AdmissionRule]] = None,
	  /** Optional. Per-kubernetes-namespace admission rules. K8s namespace spec format: `[a-z.-]+`, e.g. `some-namespace` */
		kubernetesNamespaceAdmissionRules: Option[Map[String, Schema.AdmissionRule]] = None,
	  /** Optional. Per-kubernetes-service-account admission rules. Service account spec format: `namespace:serviceaccount`. e.g. `test-ns:default` */
		kubernetesServiceAccountAdmissionRules: Option[Map[String, Schema.AdmissionRule]] = None,
	  /** Optional. Per-istio-service-identity admission rules. Istio service identity spec format: `spiffe:///ns//sa/` or `/ns//sa/` e.g. `spiffe://example.com/ns/test-ns/sa/default` */
		istioServiceIdentityAdmissionRules: Option[Map[String, Schema.AdmissionRule]] = None,
	  /** Required. Default admission rule for a cluster without a per-cluster, per- kubernetes-service-account, or per-istio-service-identity admission rule. */
		defaultAdmissionRule: Option[Schema.AdmissionRule] = None,
	  /** Output only. Time when the policy was last updated. */
		updateTime: Option[String] = None,
	  /** Optional. A checksum, returned by the server, that can be sent on update requests to ensure the policy has an up-to-date value before attempting to update it. See https://google.aip.dev/154. */
		etag: Option[String] = None
	)
	
	case class AdmissionWhitelistPattern(
	  /** An image name pattern to allowlist, in the form `registry/path/to/image`. This supports a trailing `&#42;` wildcard, but this is allowed only in text after the `registry/` part. This also supports a trailing `&#42;&#42;` wildcard which matches subdirectories of a given entry. */
		namePattern: Option[String] = None
	)
	
	object AdmissionRule {
		enum EvaluationModeEnum extends Enum[EvaluationModeEnum] { case EVALUATION_MODE_UNSPECIFIED, ALWAYS_ALLOW, REQUIRE_ATTESTATION, ALWAYS_DENY }
		enum EnforcementModeEnum extends Enum[EnforcementModeEnum] { case ENFORCEMENT_MODE_UNSPECIFIED, ENFORCED_BLOCK_AND_AUDIT_LOG, DRYRUN_AUDIT_LOG_ONLY }
	}
	case class AdmissionRule(
	  /** Required. How this admission rule will be evaluated. */
		evaluationMode: Option[Schema.AdmissionRule.EvaluationModeEnum] = None,
	  /** Optional. The resource names of the attestors that must attest to a container image, in the format `projects/&#42;/attestors/&#42;`. Each attestor must exist before a policy can reference it. To add an attestor to a policy the principal issuing the policy change request must be able to read the attestor resource. Note: this field must be non-empty when the `evaluation_mode` field specifies `REQUIRE_ATTESTATION`, otherwise it must be empty. */
		requireAttestationsBy: Option[List[String]] = None,
	  /** Required. The action when a pod creation is denied by the admission rule. */
		enforcementMode: Option[Schema.AdmissionRule.EnforcementModeEnum] = None
	)
	
	case class Attestor(
	  /** Required. The resource name, in the format: `projects/&#42;/attestors/&#42;`. This field may not be updated. */
		name: Option[String] = None,
	  /** Optional. A descriptive comment. This field may be updated. The field may be displayed in chooser dialogs. */
		description: Option[String] = None,
	  /** This specifies how an attestation will be read, and how it will be used during policy enforcement. */
		userOwnedGrafeasNote: Option[Schema.UserOwnedGrafeasNote] = None,
	  /** Output only. Time when the attestor was last updated. */
		updateTime: Option[String] = None,
	  /** Optional. A checksum, returned by the server, that can be sent on update requests to ensure the attestor has an up-to-date value before attempting to update it. See https://google.aip.dev/154. */
		etag: Option[String] = None
	)
	
	case class UserOwnedGrafeasNote(
	  /** Required. The Grafeas resource name of a Attestation.Authority Note, created by the user, in the format: `projects/[PROJECT_ID]/notes/&#42;`. This field may not be updated. A project ID must be used, not a project number. An attestation by this attestor is stored as a Grafeas Attestation.Authority Occurrence that names a container image and that links to this Note. Grafeas is an external dependency. */
		noteReference: Option[String] = None,
	  /** Optional. Public keys that verify attestations signed by this attestor. This field may be updated. If this field is non-empty, one of the specified public keys must verify that an attestation was signed by this attestor for the image specified in the admission request. If this field is empty, this attestor always returns that no valid attestations exist. */
		publicKeys: Option[List[Schema.AttestorPublicKey]] = None,
	  /** Output only. This field will contain the service account email address that this attestor will use as the principal when querying Container Analysis. Attestor administrators must grant this service account the IAM role needed to read attestations from the note_reference in Container Analysis (`containeranalysis.notes.occurrences.viewer`). This email address is fixed for the lifetime of the attestor, but callers should not make any other assumptions about the service account email; future versions may use an email based on a different naming pattern. */
		delegationServiceAccountEmail: Option[String] = None
	)
	
	case class AttestorPublicKey(
	  /** Optional. A descriptive comment. This field may be updated. */
		comment: Option[String] = None,
	  /** The ID of this public key. Signatures verified by Binary Authorization must include the ID of the public key that can be used to verify them, and that ID must match the contents of this field exactly. Additional restrictions on this field can be imposed based on which public key type is encapsulated. See the documentation on `public_key` cases below for details. */
		id: Option[String] = None,
	  /** ASCII-armored representation of a PGP public key, as the entire output by the command `gpg --export --armor foo@example.com` (either LF or CRLF line endings). When using this field, `id` should be left blank. The Binary Authorization API handlers will calculate the ID and fill it in automatically. Binary Authorization computes this ID as the OpenPGP RFC4880 V4 fingerprint, represented as upper-case hex. If `id` is provided by the caller, it will be overwritten by the API-calculated ID. */
		asciiArmoredPgpPublicKey: Option[String] = None,
	  /** A raw PKIX SubjectPublicKeyInfo format public key. NOTE: `id` may be explicitly provided by the caller when using this type of public key, but it MUST be a valid RFC3986 URI. If `id` is left blank, a default one will be computed based on the digest of the DER encoding of the public key. */
		pkixPublicKey: Option[Schema.PkixPublicKey] = None
	)
	
	case class ListAttestorsResponse(
	  /** The list of attestors. */
		attestors: Option[List[Schema.Attestor]] = None,
	  /** A token to retrieve the next page of results. Pass this value in the ListAttestorsRequest.page_token field in the subsequent call to the `ListAttestors` method to retrieve the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class ValidateAttestationOccurrenceRequest(
	  /** Required. An AttestationOccurrence to be checked that it can be verified by the `Attestor`. It does not have to be an existing entity in Container Analysis. It must otherwise be a valid `AttestationOccurrence`. */
		attestation: Option[Schema.AttestationOccurrence] = None,
	  /** Required. The resource name of the Note to which the containing Occurrence is associated. */
		occurrenceNote: Option[String] = None,
	  /** Required. The URI of the artifact (e.g. container image) that is the subject of the containing Occurrence. */
		occurrenceResourceUri: Option[String] = None
	)
	
	case class AttestationOccurrence(
	  /** Required. The serialized payload that is verified by one or more `signatures`. */
		serializedPayload: Option[String] = None,
	  /** One or more signatures over `serialized_payload`. Verifier implementations should consider this attestation message verified if at least one `signature` verifies `serialized_payload`. See `Signature` in common.proto for more details on signature structure and verification. */
		signatures: Option[List[Schema.Signature]] = None,
	  /** One or more JWTs encoding a self-contained attestation. Each JWT encodes the payload that it verifies within the JWT itself. Verifier implementation SHOULD ignore the `serialized_payload` field when verifying these JWTs. If only JWTs are present on this AttestationOccurrence, then the `serialized_payload` SHOULD be left empty. Each JWT SHOULD encode a claim specific to the `resource_uri` of this Occurrence, but this is not validated by Grafeas metadata API implementations. The JWT itself is opaque to Grafeas. */
		jwts: Option[List[Schema.Jwt]] = None
	)
	
	case class Signature(
	  /** The content of the signature, an opaque bytestring. The payload that this signature verifies MUST be unambiguously provided with the Signature during verification. A wrapper message might provide the payload explicitly. Alternatively, a message might have a canonical serialization that can always be unambiguously computed to derive the payload. */
		signature: Option[String] = None,
	  /** The identifier for the public key that verifies this signature. &#42; The `public_key_id` is required. &#42; The `public_key_id` SHOULD be an RFC3986 conformant URI. &#42; When possible, the `public_key_id` SHOULD be an immutable reference, such as a cryptographic digest. Examples of valid `public_key_id`s: OpenPGP V4 public key fingerprint: &#42; "openpgp4fpr:74FAF3B861BDA0870C7B6DEF607E48D2A663AEEA" See https://www.iana.org/assignments/uri-schemes/prov/openpgp4fpr for more details on this scheme. RFC6920 digest-named SubjectPublicKeyInfo (digest of the DER serialization): &#42; "ni:///sha-256;cD9o9Cq6LG3jD0iKXqEi_vdjJGecm_iXkbqVoScViaU" &#42; "nih:///sha-256;703f68f42aba2c6de30f488a5ea122fef76324679c9bf89791ba95a1271589a5" */
		publicKeyId: Option[String] = None
	)
	
	case class Jwt(
	  /** The compact encoding of a JWS, which is always three base64 encoded strings joined by periods. For details, see: https://tools.ietf.org/html/rfc7515.html#section-3.1 */
		compactJwt: Option[String] = None
	)
	
	object ValidateAttestationOccurrenceResponse {
		enum ResultEnum extends Enum[ResultEnum] { case RESULT_UNSPECIFIED, VERIFIED, ATTESTATION_NOT_VERIFIABLE }
	}
	case class ValidateAttestationOccurrenceResponse(
	  /** The result of the Attestation validation. */
		result: Option[Schema.ValidateAttestationOccurrenceResponse.ResultEnum] = None,
	  /** The reason for denial if the Attestation couldn't be validated. */
		denialReason: Option[String] = None
	)
	
	case class SetIamPolicyRequest(
	  /** REQUIRED: The complete policy to be applied to the `resource`. The size of the policy is limited to a few 10s of KB. An empty policy is a valid policy but certain Google Cloud services (such as Projects) might reject them. */
		policy: Option[Schema.IamPolicy] = None
	)
	
	case class IamPolicy(
	  /** Specifies the format of the policy. Valid values are `0`, `1`, and `3`. Requests that specify an invalid value are rejected. Any operation that affects conditional role bindings must specify version `3`. This requirement applies to the following operations: &#42; Getting a policy that includes a conditional role binding &#42; Adding a conditional role binding to a policy &#42; Changing a conditional role binding in a policy &#42; Removing any role binding, with or without a condition, from a policy that includes conditions &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. If a policy does not include any conditions, operations on that policy may specify any valid version or leave the field unset. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		version: Option[Int] = None,
	  /** Associates a list of `members`, or principals, with a `role`. Optionally, may specify a `condition` that determines how and when the `bindings` are applied. Each of the `bindings` must contain at least one principal. The `bindings` in a `Policy` can refer to up to 1,500 principals; up to 250 of these principals can be Google groups. Each occurrence of a principal counts towards these limits. For example, if the `bindings` grant 50 different roles to `user:alice@example.com`, and not to any other principal, then you can add another 1,450 principals to the `bindings` in the `Policy`. */
		bindings: Option[List[Schema.Binding]] = None,
	  /** `etag` is used for optimistic concurrency control as a way to help prevent simultaneous updates of a policy from overwriting each other. It is strongly suggested that systems make use of the `etag` in the read-modify-write cycle to perform policy updates in order to avoid race conditions: An `etag` is returned in the response to `getIamPolicy`, and systems are expected to put that etag in the request to `setIamPolicy` to ensure that their change will be applied to the same version of the policy. &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. */
		etag: Option[String] = None
	)
	
	case class Binding(
	  /** Role that is assigned to the list of `members`, or principals. For example, `roles/viewer`, `roles/editor`, or `roles/owner`. For an overview of the IAM roles and permissions, see the [IAM documentation](https://cloud.google.com/iam/docs/roles-overview). For a list of the available pre-defined roles, see [here](https://cloud.google.com/iam/docs/understanding-roles). */
		role: Option[String] = None,
	  /** Specifies the principals requesting access for a Google Cloud resource. `members` can have the following values: &#42; `allUsers`: A special identifier that represents anyone who is on the internet; with or without a Google account. &#42; `allAuthenticatedUsers`: A special identifier that represents anyone who is authenticated with a Google account or a service account. Does not include identities that come from external identity providers (IdPs) through identity federation. &#42; `user:{emailid}`: An email address that represents a specific Google account. For example, `alice@example.com` . &#42; `serviceAccount:{emailid}`: An email address that represents a Google service account. For example, `my-other-app@appspot.gserviceaccount.com`. &#42; `serviceAccount:{projectid}.svc.id.goog[{namespace}/{kubernetes-sa}]`: An identifier for a [Kubernetes service account](https://cloud.google.com/kubernetes-engine/docs/how-to/kubernetes-service-accounts). For example, `my-project.svc.id.goog[my-namespace/my-kubernetes-sa]`. &#42; `group:{emailid}`: An email address that represents a Google group. For example, `admins@example.com`. &#42; `domain:{domain}`: The G Suite domain (primary) that represents all the users of that domain. For example, `google.com` or `example.com`. &#42; `principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workforce identity pool. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/group/{group_id}`: All workforce identities in a group. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All workforce identities with a specific attribute value. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/&#42;`: All identities in a workforce identity pool. &#42; `principal://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workload identity pool. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/group/{group_id}`: A workload identity pool group. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All identities in a workload identity pool with a certain attribute. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/&#42;`: All identities in a workload identity pool. &#42; `deleted:user:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a user that has been recently deleted. For example, `alice@example.com?uid=123456789012345678901`. If the user is recovered, this value reverts to `user:{emailid}` and the recovered user retains the role in the binding. &#42; `deleted:serviceAccount:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a service account that has been recently deleted. For example, `my-other-app@appspot.gserviceaccount.com?uid=123456789012345678901`. If the service account is undeleted, this value reverts to `serviceAccount:{emailid}` and the undeleted service account retains the role in the binding. &#42; `deleted:group:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a Google group that has been recently deleted. For example, `admins@example.com?uid=123456789012345678901`. If the group is recovered, this value reverts to `group:{emailid}` and the recovered group retains the role in the binding. &#42; `deleted:principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: Deleted single identity in a workforce identity pool. For example, `deleted:principal://iam.googleapis.com/locations/global/workforcePools/my-pool-id/subject/my-subject-attribute-value`. */
		members: Option[List[String]] = None,
	  /** The condition that is associated with this binding. If the condition evaluates to `true`, then this binding applies to the current request. If the condition evaluates to `false`, then this binding does not apply to the current request. However, a different role binding might grant the same role to one or more of the principals in this binding. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		condition: Option[Schema.Expr] = None
	)
	
	case class Expr(
	  /** Textual representation of an expression in Common Expression Language syntax. */
		expression: Option[String] = None,
	  /** Optional. Title for the expression, i.e. a short string describing its purpose. This can be used e.g. in UIs which allow to enter the expression. */
		title: Option[String] = None,
	  /** Optional. Description of the expression. This is a longer text which describes the expression, e.g. when hovered over it in a UI. */
		description: Option[String] = None,
	  /** Optional. String indicating the location of the expression for error reporting, e.g. a file name and a position in the file. */
		location: Option[String] = None
	)
	
	case class TestIamPermissionsRequest(
	  /** The set of permissions to check for the `resource`. Permissions with wildcards (such as `&#42;` or `storage.&#42;`) are not allowed. For more information see [IAM Overview](https://cloud.google.com/iam/docs/overview#permissions). */
		permissions: Option[List[String]] = None
	)
	
	case class TestIamPermissionsResponse(
	  /** A subset of `TestPermissionsRequest.permissions` that the caller is allowed. */
		permissions: Option[List[String]] = None
	)
}
