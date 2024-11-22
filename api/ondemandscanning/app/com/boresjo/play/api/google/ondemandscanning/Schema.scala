package com.boresjo.play.api.google.ondemandscanning

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ListOperationsResponse(
	  /** A list of operations that matches the specified filter in the request. */
		operations: Option[List[Schema.Operation]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class Operation(
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.Status] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None
	)
	
	case class Status(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class Empty(
	
	)
	
	case class AnalyzePackagesRequestV1(
	  /** Required. The resource URI of the container image being scanned. */
		resourceUri: Option[String] = None,
	  /** The packages to analyze. */
		packages: Option[List[Schema.PackageData]] = None,
	  /** [DEPRECATED] Whether to include OSV data in the scan. For backwards compatibility reasons, this field can be neither removed nor renamed. */
		includeOsvData: Option[Boolean] = None
	)
	
	object PackageData {
		enum PackageTypeEnum extends Enum[PackageTypeEnum] { case PACKAGE_TYPE_UNSPECIFIED, OS, MAVEN, GO, GO_STDLIB, PYPI, NPM, NUGET, RUBYGEMS, RUST, COMPOSER, SWIFT }
	}
	case class PackageData(
	  /** The package being analysed for vulnerabilities */
		`package`: Option[String] = None,
	  /** The version of the package being analysed */
		version: Option[String] = None,
	  /** The OS affected by a vulnerability Used to generate the cpe_uri for OS packages */
		os: Option[String] = None,
	  /** The version of the OS Used to generate the cpe_uri for OS packages */
		osVersion: Option[String] = None,
	  /** The cpe_uri in [cpe format] (https://cpe.mitre.org/specification/) in which the vulnerability may manifest. Examples include distro or storage location for vulnerable jar. */
		cpeUri: Option[String] = None,
		unused: Option[String] = None,
	  /** The type of package: os, maven, go, etc. */
		packageType: Option[Schema.PackageData.PackageTypeEnum] = None,
	  /** HashDigest stores the SHA512 hash digest of the jar file if the package is of type Maven. This field will be unset for non Maven packages. */
		hashDigest: Option[String] = None,
	  /** The path to the jar file / go binary file. */
		fileLocation: Option[List[Schema.FileLocation]] = None,
	  /** CVEs that this package is no longer vulnerable to go/drydock-dd-custom-binary-scanning */
		patchedCve: Option[List[String]] = None,
	  /** The dependency chain between this package and the user's artifact. List in order from the customer's package under review first, to the current package last. Inclusive of the original package and the current package. */
		dependencyChain: Option[List[Schema.LanguagePackageDependency]] = None,
	  /** The maintainer of the package. */
		maintainer: Option[Schema.Maintainer] = None,
	  /** The architecture of the package. */
		architecture: Option[String] = None,
	  /** DEPRECATED */
		binaryVersion: Option[Schema.PackageVersion] = None,
	  /** DEPRECATED */
		sourceVersion: Option[Schema.PackageVersion] = None,
	  /** A bundle containing the binary and source information. */
		binarySourceInfo: Option[List[Schema.BinarySourceInfo]] = None,
	  /** The list of licenses found that are related to a given package. Note that licenses may also be stored on the BinarySourceInfo. If there is no BinarySourceInfo (because there's no concept of source vs binary), then it will be stored here, while if there are BinarySourceInfos, it will be stored there, as one source can have multiple binaries with different licenses. */
		licenses: Option[List[String]] = None
	)
	
	case class FileLocation(
	  /** For jars that are contained inside .war files, this filepath can indicate the path to war file combined with the path to jar file. */
		filePath: Option[String] = None
	)
	
	case class LanguagePackageDependency(
		`package`: Option[String] = None,
		version: Option[String] = None
	)
	
	case class Maintainer(
		name: Option[String] = None,
		kind: Option[String] = None,
		email: Option[String] = None,
		url: Option[String] = None
	)
	
	case class PackageVersion(
		name: Option[String] = None,
		version: Option[String] = None,
	  /** The licenses associated with this package. Note that this has to go on the PackageVersion level, because we can have cases with images with the same source having different licences. E.g. in Alpine, musl and musl-utils both have the same origin musl, but have different sets of licenses. */
		licenses: Option[List[String]] = None
	)
	
	case class BinarySourceInfo(
	  /** The binary package. This is significant when the source is different than the binary itself. Historically if they've differed, we've stored the name of the source and its version in the package/version fields, but we should also store the binary package info, as that's what's actually installed. See b/175908657#comment15. */
		binaryVersion: Option[Schema.PackageVersion] = None,
	  /** The source package. Similar to the above, this is significant when the source is different than the binary itself. Since the top-level package/version fields are based on an if/else, we need a separate field for both binary and source if we want to know definitively where the data is coming from. */
		sourceVersion: Option[Schema.PackageVersion] = None
	)
	
	case class ListVulnerabilitiesResponseV1(
	  /** The list of Vulnerability Occurrences resulting from a scan. */
		occurrences: Option[List[Schema.Occurrence]] = None,
	  /** A page token that can be used in a subsequent call to ListVulnerabilities to continue retrieving results. */
		nextPageToken: Option[String] = None
	)
	
	object Occurrence {
		enum KindEnum extends Enum[KindEnum] { case NOTE_KIND_UNSPECIFIED, VULNERABILITY, BUILD, IMAGE, PACKAGE, DEPLOYMENT, DISCOVERY, ATTESTATION, UPGRADE, COMPLIANCE, DSSE_ATTESTATION, VULNERABILITY_ASSESSMENT, SBOM_REFERENCE }
	}
	case class Occurrence(
	  /** Output only. The name of the occurrence in the form of `projects/[PROJECT_ID]/occurrences/[OCCURRENCE_ID]`. */
		name: Option[String] = None,
	  /** Required. Immutable. A URI that represents the resource for which the occurrence applies. For example, `https://gcr.io/project/image@sha256:123abc` for a Docker image. */
		resourceUri: Option[String] = None,
	  /** Required. Immutable. The analysis note associated with this occurrence, in the form of `projects/[PROVIDER_ID]/notes/[NOTE_ID]`. This field can be used as a filter in list requests. */
		noteName: Option[String] = None,
	  /** Output only. This explicitly denotes which of the occurrence details are specified. This field can be used as a filter in list requests. */
		kind: Option[Schema.Occurrence.KindEnum] = None,
	  /** A description of actions that can be taken to remedy the note. */
		remediation: Option[String] = None,
	  /** Output only. The time this occurrence was created. */
		createTime: Option[String] = None,
	  /** Output only. The time this occurrence was last updated. */
		updateTime: Option[String] = None,
	  /** Describes a security vulnerability. */
		vulnerability: Option[Schema.VulnerabilityOccurrence] = None,
	  /** Describes a verifiable build. */
		build: Option[Schema.BuildOccurrence] = None,
	  /** Describes how this resource derives from the basis in the associated note. */
		image: Option[Schema.ImageOccurrence] = None,
	  /** Describes the installation of a package on the linked resource. */
		`package`: Option[Schema.PackageOccurrence] = None,
	  /** Describes the deployment of an artifact on a runtime. */
		deployment: Option[Schema.DeploymentOccurrence] = None,
	  /** Describes when a resource was discovered. */
		discovery: Option[Schema.DiscoveryOccurrence] = None,
	  /** Describes an attestation of an artifact. */
		attestation: Option[Schema.AttestationOccurrence] = None,
	  /** Describes an available package upgrade on the linked resource. */
		upgrade: Option[Schema.UpgradeOccurrence] = None,
	  /** Describes a compliance violation on a linked resource. */
		compliance: Option[Schema.ComplianceOccurrence] = None,
	  /** Describes an attestation of an artifact using dsse. */
		dsseAttestation: Option[Schema.DSSEAttestationOccurrence] = None,
	  /** Describes a specific SBOM reference occurrences. */
		sbomReference: Option[Schema.SBOMReferenceOccurrence] = None,
	  /** https://github.com/secure-systems-lab/dsse */
		envelope: Option[Schema.Envelope] = None
	)
	
	object VulnerabilityOccurrence {
		enum SeverityEnum extends Enum[SeverityEnum] { case SEVERITY_UNSPECIFIED, MINIMAL, LOW, MEDIUM, HIGH, CRITICAL }
		enum EffectiveSeverityEnum extends Enum[EffectiveSeverityEnum] { case SEVERITY_UNSPECIFIED, MINIMAL, LOW, MEDIUM, HIGH, CRITICAL }
		enum CvssVersionEnum extends Enum[CvssVersionEnum] { case CVSS_VERSION_UNSPECIFIED, CVSS_VERSION_2, CVSS_VERSION_3 }
	}
	case class VulnerabilityOccurrence(
	  /** The type of package; whether native or non native (e.g., ruby gems, node.js packages, etc.). */
		`type`: Option[String] = None,
	  /** Output only. The note provider assigned severity of this vulnerability. */
		severity: Option[Schema.VulnerabilityOccurrence.SeverityEnum] = None,
	  /** Output only. The CVSS score of this vulnerability. CVSS score is on a scale of 0 - 10 where 0 indicates low severity and 10 indicates high severity. */
		cvssScore: Option[BigDecimal] = None,
	  /** The cvss v3 score for the vulnerability. */
		cvssv3: Option[Schema.CVSS] = None,
	  /** Required. The set of affected locations and their fixes (if available) within the associated resource. */
		packageIssue: Option[List[Schema.PackageIssue]] = None,
	  /** Output only. A one sentence description of this vulnerability. */
		shortDescription: Option[String] = None,
	  /** Output only. A detailed description of this vulnerability. */
		longDescription: Option[String] = None,
	  /** Output only. URLs related to this vulnerability. */
		relatedUrls: Option[List[Schema.RelatedUrl]] = None,
	  /** The distro assigned severity for this vulnerability when it is available, otherwise this is the note provider assigned severity. When there are multiple PackageIssues for this vulnerability, they can have different effective severities because some might be provided by the distro while others are provided by the language ecosystem for a language pack. For this reason, it is advised to use the effective severity on the PackageIssue level. In the case where multiple PackageIssues have differing effective severities, this field should be the highest severity for any of the PackageIssues. */
		effectiveSeverity: Option[Schema.VulnerabilityOccurrence.EffectiveSeverityEnum] = None,
	  /** Output only. Whether at least one of the affected packages has a fix available. */
		fixAvailable: Option[Boolean] = None,
	  /** Output only. CVSS version used to populate cvss_score and severity. */
		cvssVersion: Option[Schema.VulnerabilityOccurrence.CvssVersionEnum] = None,
	  /** The cvss v2 score for the vulnerability. */
		cvssV2: Option[Schema.CVSS] = None,
		vexAssessment: Option[Schema.VexAssessment] = None,
	  /** Occurrence-specific extra details about the vulnerability. */
		extraDetails: Option[String] = None
	)
	
	object CVSS {
		enum AttackVectorEnum extends Enum[AttackVectorEnum] { case ATTACK_VECTOR_UNSPECIFIED, ATTACK_VECTOR_NETWORK, ATTACK_VECTOR_ADJACENT, ATTACK_VECTOR_LOCAL, ATTACK_VECTOR_PHYSICAL }
		enum AttackComplexityEnum extends Enum[AttackComplexityEnum] { case ATTACK_COMPLEXITY_UNSPECIFIED, ATTACK_COMPLEXITY_LOW, ATTACK_COMPLEXITY_HIGH, ATTACK_COMPLEXITY_MEDIUM }
		enum AuthenticationEnum extends Enum[AuthenticationEnum] { case AUTHENTICATION_UNSPECIFIED, AUTHENTICATION_MULTIPLE, AUTHENTICATION_SINGLE, AUTHENTICATION_NONE }
		enum PrivilegesRequiredEnum extends Enum[PrivilegesRequiredEnum] { case PRIVILEGES_REQUIRED_UNSPECIFIED, PRIVILEGES_REQUIRED_NONE, PRIVILEGES_REQUIRED_LOW, PRIVILEGES_REQUIRED_HIGH }
		enum UserInteractionEnum extends Enum[UserInteractionEnum] { case USER_INTERACTION_UNSPECIFIED, USER_INTERACTION_NONE, USER_INTERACTION_REQUIRED }
		enum ScopeEnum extends Enum[ScopeEnum] { case SCOPE_UNSPECIFIED, SCOPE_UNCHANGED, SCOPE_CHANGED }
		enum ConfidentialityImpactEnum extends Enum[ConfidentialityImpactEnum] { case IMPACT_UNSPECIFIED, IMPACT_HIGH, IMPACT_LOW, IMPACT_NONE, IMPACT_PARTIAL, IMPACT_COMPLETE }
		enum IntegrityImpactEnum extends Enum[IntegrityImpactEnum] { case IMPACT_UNSPECIFIED, IMPACT_HIGH, IMPACT_LOW, IMPACT_NONE, IMPACT_PARTIAL, IMPACT_COMPLETE }
		enum AvailabilityImpactEnum extends Enum[AvailabilityImpactEnum] { case IMPACT_UNSPECIFIED, IMPACT_HIGH, IMPACT_LOW, IMPACT_NONE, IMPACT_PARTIAL, IMPACT_COMPLETE }
	}
	case class CVSS(
	  /** The base score is a function of the base metric scores. */
		baseScore: Option[BigDecimal] = None,
		exploitabilityScore: Option[BigDecimal] = None,
		impactScore: Option[BigDecimal] = None,
	  /** Base Metrics Represents the intrinsic characteristics of a vulnerability that are constant over time and across user environments. */
		attackVector: Option[Schema.CVSS.AttackVectorEnum] = None,
		attackComplexity: Option[Schema.CVSS.AttackComplexityEnum] = None,
		authentication: Option[Schema.CVSS.AuthenticationEnum] = None,
		privilegesRequired: Option[Schema.CVSS.PrivilegesRequiredEnum] = None,
		userInteraction: Option[Schema.CVSS.UserInteractionEnum] = None,
		scope: Option[Schema.CVSS.ScopeEnum] = None,
		confidentialityImpact: Option[Schema.CVSS.ConfidentialityImpactEnum] = None,
		integrityImpact: Option[Schema.CVSS.IntegrityImpactEnum] = None,
		availabilityImpact: Option[Schema.CVSS.AvailabilityImpactEnum] = None
	)
	
	object PackageIssue {
		enum EffectiveSeverityEnum extends Enum[EffectiveSeverityEnum] { case SEVERITY_UNSPECIFIED, MINIMAL, LOW, MEDIUM, HIGH, CRITICAL }
	}
	case class PackageIssue(
	  /** Required. The [CPE URI](https://cpe.mitre.org/specification/) this vulnerability was found in. */
		affectedCpeUri: Option[String] = None,
	  /** Required. The package this vulnerability was found in. */
		affectedPackage: Option[String] = None,
	  /** Required. The version of the package that is installed on the resource affected by this vulnerability. */
		affectedVersion: Option[Schema.Version] = None,
	  /** The [CPE URI](https://cpe.mitre.org/specification/) this vulnerability was fixed in. It is possible for this to be different from the affected_cpe_uri. */
		fixedCpeUri: Option[String] = None,
	  /** The package this vulnerability was fixed in. It is possible for this to be different from the affected_package. */
		fixedPackage: Option[String] = None,
	  /** Required. The version of the package this vulnerability was fixed in. Setting this to VersionKind.MAXIMUM means no fix is yet available. */
		fixedVersion: Option[Schema.Version] = None,
	  /** Output only. Whether a fix is available for this package. */
		fixAvailable: Option[Boolean] = None,
	  /** The type of package (e.g. OS, MAVEN, GO). */
		packageType: Option[String] = None,
	  /** Output only. The distro or language system assigned severity for this vulnerability when that is available and note provider assigned severity when it is not available. */
		effectiveSeverity: Option[Schema.PackageIssue.EffectiveSeverityEnum] = None,
	  /** The location at which this package was found. */
		fileLocation: Option[List[Schema.GrafeasV1FileLocation]] = None
	)
	
	object Version {
		enum KindEnum extends Enum[KindEnum] { case VERSION_KIND_UNSPECIFIED, NORMAL, MINIMUM, MAXIMUM }
	}
	case class Version(
	  /** Used to correct mistakes in the version numbering scheme. */
		epoch: Option[Int] = None,
	  /** Required only when version kind is NORMAL. The main part of the version name. */
		name: Option[String] = None,
	  /** The iteration of the package build from the above version. */
		revision: Option[String] = None,
	  /** Whether this version is specifying part of an inclusive range. Grafeas does not have the capability to specify version ranges; instead we have fields that specify start version and end versions. At times this is insufficient - we also need to specify whether the version is included in the range or is excluded from the range. This boolean is expected to be set to true when the version is included in a range. */
		inclusive: Option[Boolean] = None,
	  /** Required. Distinguishes between sentinel MIN/MAX versions and normal versions. */
		kind: Option[Schema.Version.KindEnum] = None,
	  /** Human readable version string. This string is of the form :- and is only set when kind is NORMAL. */
		fullName: Option[String] = None
	)
	
	case class GrafeasV1FileLocation(
	  /** For jars that are contained inside .war files, this filepath can indicate the path to war file combined with the path to jar file. */
		filePath: Option[String] = None
	)
	
	case class RelatedUrl(
	  /** Specific URL associated with the resource. */
		url: Option[String] = None,
	  /** Label to describe usage of the URL. */
		label: Option[String] = None
	)
	
	object VexAssessment {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, AFFECTED, NOT_AFFECTED, FIXED, UNDER_INVESTIGATION }
	}
	case class VexAssessment(
	  /** Holds the MITRE standard Common Vulnerabilities and Exposures (CVE) tracking number for the vulnerability. Deprecated: Use vulnerability_id instead to denote CVEs. */
		cve: Option[String] = None,
	  /** The vulnerability identifier for this Assessment. Will hold one of common identifiers e.g. CVE, GHSA etc. */
		vulnerabilityId: Option[String] = None,
	  /** Holds a list of references associated with this vulnerability item and assessment. */
		relatedUris: Option[List[Schema.RelatedUrl]] = None,
	  /** The VulnerabilityAssessment note from which this VexAssessment was generated. This will be of the form: `projects/[PROJECT_ID]/notes/[NOTE_ID]`. */
		noteName: Option[String] = None,
	  /** Provides the state of this Vulnerability assessment. */
		state: Option[Schema.VexAssessment.StateEnum] = None,
	  /** Contains information about the impact of this vulnerability, this will change with time. */
		impacts: Option[List[String]] = None,
	  /** Specifies details on how to handle (and presumably, fix) a vulnerability. */
		remediations: Option[List[Schema.Remediation]] = None,
	  /** Justification provides the justification when the state of the assessment if NOT_AFFECTED. */
		justification: Option[Schema.Justification] = None
	)
	
	object Remediation {
		enum RemediationTypeEnum extends Enum[RemediationTypeEnum] { case REMEDIATION_TYPE_UNSPECIFIED, MITIGATION, NO_FIX_PLANNED, NONE_AVAILABLE, VENDOR_FIX, WORKAROUND }
	}
	case class Remediation(
	  /** The type of remediation that can be applied. */
		remediationType: Option[Schema.Remediation.RemediationTypeEnum] = None,
	  /** Contains a comprehensive human-readable discussion of the remediation. */
		details: Option[String] = None,
	  /** Contains the URL where to obtain the remediation. */
		remediationUri: Option[Schema.RelatedUrl] = None
	)
	
	object Justification {
		enum JustificationTypeEnum extends Enum[JustificationTypeEnum] { case JUSTIFICATION_TYPE_UNSPECIFIED, COMPONENT_NOT_PRESENT, VULNERABLE_CODE_NOT_PRESENT, VULNERABLE_CODE_NOT_IN_EXECUTE_PATH, VULNERABLE_CODE_CANNOT_BE_CONTROLLED_BY_ADVERSARY, INLINE_MITIGATIONS_ALREADY_EXIST }
	}
	case class Justification(
	  /** The justification type for this vulnerability. */
		justificationType: Option[Schema.Justification.JustificationTypeEnum] = None,
	  /** Additional details on why this justification was chosen. */
		details: Option[String] = None
	)
	
	case class BuildOccurrence(
	  /** The actual provenance for the build. */
		provenance: Option[Schema.BuildProvenance] = None,
	  /** Serialized JSON representation of the provenance, used in generating the build signature in the corresponding build note. After verifying the signature, `provenance_bytes` can be unmarshalled and compared to the provenance to confirm that it is unchanged. A base64-encoded string representation of the provenance bytes is used for the signature in order to interoperate with openssl which expects this format for signature verification. The serialized form is captured both to avoid ambiguity in how the provenance is marshalled to json as well to prevent incompatibilities with future changes. */
		provenanceBytes: Option[String] = None,
	  /** Deprecated. See InTotoStatement for the replacement. In-toto Provenance representation as defined in spec. */
		intotoProvenance: Option[Schema.InTotoProvenance] = None,
	  /** In-toto Statement representation as defined in spec. The intoto_statement can contain any type of provenance. The serialized payload of the statement can be stored and signed in the Occurrence's envelope. */
		intotoStatement: Option[Schema.InTotoStatement] = None,
	  /** In-Toto Slsa Provenance V1 represents a slsa provenance meeting the slsa spec, wrapped in an in-toto statement. This allows for direct jsonification of a to-spec in-toto slsa statement with a to-spec slsa provenance. */
		inTotoSlsaProvenanceV1: Option[Schema.InTotoSlsaProvenanceV1] = None
	)
	
	case class BuildProvenance(
	  /** Required. Unique identifier of the build. */
		id: Option[String] = None,
	  /** ID of the project. */
		projectId: Option[String] = None,
	  /** Commands requested by the build. */
		commands: Option[List[Schema.Command]] = None,
	  /** Output of the build. */
		builtArtifacts: Option[List[Schema.Artifact]] = None,
	  /** Time at which the build was created. */
		createTime: Option[String] = None,
	  /** Time at which execution of the build was started. */
		startTime: Option[String] = None,
	  /** Time at which execution of the build was finished. */
		endTime: Option[String] = None,
	  /** E-mail address of the user who initiated this build. Note that this was the user's e-mail address at the time the build was initiated; this address may not represent the same end-user for all time. */
		creator: Option[String] = None,
	  /** URI where any logs for this provenance were written. */
		logsUri: Option[String] = None,
	  /** Details of the Source input to the build. */
		sourceProvenance: Option[Schema.Source] = None,
	  /** Trigger identifier if the build was triggered automatically; empty if not. */
		triggerId: Option[String] = None,
	  /** Special options applied to this build. This is a catch-all field where build providers can enter any desired additional details. */
		buildOptions: Option[Map[String, String]] = None,
	  /** Version string of the builder at the time this build was executed. */
		builderVersion: Option[String] = None
	)
	
	case class Command(
	  /** Required. Name of the command, as presented on the command line, or if the command is packaged as a Docker container, as presented to `docker pull`. */
		name: Option[String] = None,
	  /** Environment variables set before running this command. */
		env: Option[List[String]] = None,
	  /** Command-line arguments used when executing this command. */
		args: Option[List[String]] = None,
	  /** Working directory (relative to project source root) used when running this command. */
		dir: Option[String] = None,
	  /** Optional unique identifier for this command, used in wait_for to reference this command as a dependency. */
		id: Option[String] = None,
	  /** The ID(s) of the command(s) that this command depends on. */
		waitFor: Option[List[String]] = None
	)
	
	case class Artifact(
	  /** Hash or checksum value of a binary, or Docker Registry 2.0 digest of a container. */
		checksum: Option[String] = None,
	  /** Artifact ID, if any; for container images, this will be a URL by digest like `gcr.io/projectID/imagename@sha256:123456`. */
		id: Option[String] = None,
	  /** Related artifact names. This may be the path to a binary or jar file, or in the case of a container build, the name used to push the container image to Google Container Registry, as presented to `docker push`. Note that a single Artifact ID can have multiple names, for example if two tags are applied to one image. */
		names: Option[List[String]] = None
	)
	
	case class Source(
	  /** If provided, the input binary artifacts for the build came from this location. */
		artifactStorageSourceUri: Option[String] = None,
	  /** Hash(es) of the build source, which can be used to verify that the original source integrity was maintained in the build. The keys to this map are file paths used as build source and the values contain the hash values for those files. If the build source came in a single package such as a gzipped tarfile (.tar.gz), the FileHash will be for the single path to that file. */
		fileHashes: Option[Map[String, Schema.FileHashes]] = None,
	  /** If provided, the source code used for the build came from this location. */
		context: Option[Schema.SourceContext] = None,
	  /** If provided, some of the source code used for the build may be found in these locations, in the case where the source repository had multiple remotes or submodules. This list will not include the context specified in the context field. */
		additionalContexts: Option[List[Schema.SourceContext]] = None
	)
	
	case class FileHashes(
	  /** Required. Collection of file hashes. */
		fileHash: Option[List[Schema.Hash]] = None
	)
	
	case class Hash(
	  /** Required. The type of hash that was performed, e.g. "SHA-256". */
		`type`: Option[String] = None,
	  /** Required. The hash value. */
		value: Option[String] = None
	)
	
	case class SourceContext(
	  /** A SourceContext referring to a revision in a Google Cloud Source Repo. */
		cloudRepo: Option[Schema.CloudRepoSourceContext] = None,
	  /** A SourceContext referring to a Gerrit project. */
		gerrit: Option[Schema.GerritSourceContext] = None,
	  /** A SourceContext referring to any third party Git repo (e.g., GitHub). */
		git: Option[Schema.GitSourceContext] = None,
	  /** Labels with user defined metadata. */
		labels: Option[Map[String, String]] = None
	)
	
	case class CloudRepoSourceContext(
	  /** The ID of the repo. */
		repoId: Option[Schema.RepoId] = None,
	  /** A revision ID. */
		revisionId: Option[String] = None,
	  /** An alias, which may be a branch or tag. */
		aliasContext: Option[Schema.AliasContext] = None
	)
	
	case class RepoId(
	  /** A combination of a project ID and a repo name. */
		projectRepoId: Option[Schema.ProjectRepoId] = None,
	  /** A server-assigned, globally unique identifier. */
		uid: Option[String] = None
	)
	
	case class ProjectRepoId(
	  /** The ID of the project. */
		projectId: Option[String] = None,
	  /** The name of the repo. Leave empty for the default repo. */
		repoName: Option[String] = None
	)
	
	object AliasContext {
		enum KindEnum extends Enum[KindEnum] { case KIND_UNSPECIFIED, FIXED, MOVABLE, OTHER }
	}
	case class AliasContext(
	  /** The alias kind. */
		kind: Option[Schema.AliasContext.KindEnum] = None,
	  /** The alias name. */
		name: Option[String] = None
	)
	
	case class GerritSourceContext(
	  /** The URI of a running Gerrit instance. */
		hostUri: Option[String] = None,
	  /** The full project name within the host. Projects may be nested, so "project/subproject" is a valid project name. The "repo name" is the hostURI/project. */
		gerritProject: Option[String] = None,
	  /** A revision (commit) ID. */
		revisionId: Option[String] = None,
	  /** An alias, which may be a branch or tag. */
		aliasContext: Option[Schema.AliasContext] = None
	)
	
	case class GitSourceContext(
	  /** Git repository URL. */
		url: Option[String] = None,
	  /** Git commit hash. */
		revisionId: Option[String] = None
	)
	
	case class InTotoProvenance(
	  /** required */
		builderConfig: Option[Schema.BuilderConfig] = None,
	  /** Identifies the configuration used for the build. When combined with materials, this SHOULD fully describe the build, such that re-running this recipe results in bit-for-bit identical output (if the build is reproducible). required */
		recipe: Option[Schema.Recipe] = None,
		metadata: Option[Schema.Metadata] = None,
	  /** The collection of artifacts that influenced the build including sources, dependencies, build tools, base images, and so on. This is considered to be incomplete unless metadata.completeness.materials is true. Unset or null is equivalent to empty. */
		materials: Option[List[String]] = None
	)
	
	case class BuilderConfig(
		id: Option[String] = None
	)
	
	case class Recipe(
	  /** URI indicating what type of recipe was performed. It determines the meaning of recipe.entryPoint, recipe.arguments, recipe.environment, and materials. */
		`type`: Option[String] = None,
	  /** Index in materials containing the recipe steps that are not implied by recipe.type. For example, if the recipe type were "make", then this would point to the source containing the Makefile, not the make program itself. Set to -1 if the recipe doesn't come from a material, as zero is default unset value for int64. */
		definedInMaterial: Option[String] = None,
	  /** String identifying the entry point into the build. This is often a path to a configuration file and/or a target label within that file. The syntax and meaning are defined by recipe.type. For example, if the recipe type were "make", then this would reference the directory in which to run make as well as which target to use. */
		entryPoint: Option[String] = None,
	  /** Collection of all external inputs that influenced the build on top of recipe.definedInMaterial and recipe.entryPoint. For example, if the recipe type were "make", then this might be the flags passed to make aside from the target, which is captured in recipe.entryPoint. Since the arguments field can greatly vary in structure, depending on the builder and recipe type, this is of form "Any". */
		arguments: Option[List[Map[String, JsValue]]] = None,
	  /** Any other builder-controlled inputs necessary for correctly evaluating the recipe. Usually only needed for reproducing the build but not evaluated as part of policy. Since the environment field can greatly vary in structure, depending on the builder and recipe type, this is of form "Any". */
		environment: Option[List[Map[String, JsValue]]] = None
	)
	
	case class Metadata(
	  /** Identifies the particular build invocation, which can be useful for finding associated logs or other ad-hoc analysis. The value SHOULD be globally unique, per in-toto Provenance spec. */
		buildInvocationId: Option[String] = None,
	  /** The timestamp of when the build started. */
		buildStartedOn: Option[String] = None,
	  /** The timestamp of when the build completed. */
		buildFinishedOn: Option[String] = None,
	  /** Indicates that the builder claims certain fields in this message to be complete. */
		completeness: Option[Schema.Completeness] = None,
	  /** If true, the builder claims that running the recipe on materials will produce bit-for-bit identical output. */
		reproducible: Option[Boolean] = None
	)
	
	case class Completeness(
	  /** If true, the builder claims that recipe.arguments is complete, meaning that all external inputs are properly captured in the recipe. */
		arguments: Option[Boolean] = None,
	  /** If true, the builder claims that recipe.environment is claimed to be complete. */
		environment: Option[Boolean] = None,
	  /** If true, the builder claims that materials are complete, usually through some controls to prevent network access. Sometimes called "hermetic". */
		materials: Option[Boolean] = None
	)
	
	case class InTotoStatement(
	  /** Always `https://in-toto.io/Statement/v0.1`. */
		_type: Option[String] = None,
		subject: Option[List[Schema.Subject]] = None,
	  /** `https://slsa.dev/provenance/v0.1` for SlsaProvenance. */
		predicateType: Option[String] = None,
		provenance: Option[Schema.InTotoProvenance] = None,
		slsaProvenance: Option[Schema.SlsaProvenance] = None,
		slsaProvenanceZeroTwo: Option[Schema.SlsaProvenanceZeroTwo] = None
	)
	
	case class Subject(
		name: Option[String] = None,
	  /** `"": ""` Algorithms can be e.g. sha256, sha512 See https://github.com/in-toto/attestation/blob/main/spec/field_types.md#DigestSet */
		digest: Option[Map[String, String]] = None
	)
	
	case class SlsaProvenance(
	  /** required */
		builder: Option[Schema.SlsaBuilder] = None,
	  /** Identifies the configuration used for the build. When combined with materials, this SHOULD fully describe the build, such that re-running this recipe results in bit-for-bit identical output (if the build is reproducible). required */
		recipe: Option[Schema.SlsaRecipe] = None,
		metadata: Option[Schema.SlsaMetadata] = None,
	  /** The collection of artifacts that influenced the build including sources, dependencies, build tools, base images, and so on. This is considered to be incomplete unless metadata.completeness.materials is true. Unset or null is equivalent to empty. */
		materials: Option[List[Schema.Material]] = None
	)
	
	case class SlsaBuilder(
		id: Option[String] = None
	)
	
	case class SlsaRecipe(
	  /** URI indicating what type of recipe was performed. It determines the meaning of recipe.entryPoint, recipe.arguments, recipe.environment, and materials. */
		`type`: Option[String] = None,
	  /** Index in materials containing the recipe steps that are not implied by recipe.type. For example, if the recipe type were "make", then this would point to the source containing the Makefile, not the make program itself. Set to -1 if the recipe doesn't come from a material, as zero is default unset value for int64. */
		definedInMaterial: Option[String] = None,
	  /** String identifying the entry point into the build. This is often a path to a configuration file and/or a target label within that file. The syntax and meaning are defined by recipe.type. For example, if the recipe type were "make", then this would reference the directory in which to run make as well as which target to use. */
		entryPoint: Option[String] = None,
	  /** Collection of all external inputs that influenced the build on top of recipe.definedInMaterial and recipe.entryPoint. For example, if the recipe type were "make", then this might be the flags passed to make aside from the target, which is captured in recipe.entryPoint. Depending on the recipe Type, the structure may be different. */
		arguments: Option[Map[String, JsValue]] = None,
	  /** Any other builder-controlled inputs necessary for correctly evaluating the recipe. Usually only needed for reproducing the build but not evaluated as part of policy. Depending on the recipe Type, the structure may be different. */
		environment: Option[Map[String, JsValue]] = None
	)
	
	case class SlsaMetadata(
	  /** Identifies the particular build invocation, which can be useful for finding associated logs or other ad-hoc analysis. The value SHOULD be globally unique, per in-toto Provenance spec. */
		buildInvocationId: Option[String] = None,
	  /** The timestamp of when the build started. */
		buildStartedOn: Option[String] = None,
	  /** The timestamp of when the build completed. */
		buildFinishedOn: Option[String] = None,
	  /** Indicates that the builder claims certain fields in this message to be complete. */
		completeness: Option[Schema.SlsaCompleteness] = None,
	  /** If true, the builder claims that running the recipe on materials will produce bit-for-bit identical output. */
		reproducible: Option[Boolean] = None
	)
	
	case class SlsaCompleteness(
	  /** If true, the builder claims that recipe.arguments is complete, meaning that all external inputs are properly captured in the recipe. */
		arguments: Option[Boolean] = None,
	  /** If true, the builder claims that recipe.environment is claimed to be complete. */
		environment: Option[Boolean] = None,
	  /** If true, the builder claims that materials are complete, usually through some controls to prevent network access. Sometimes called "hermetic". */
		materials: Option[Boolean] = None
	)
	
	case class Material(
		uri: Option[String] = None,
		digest: Option[Map[String, String]] = None
	)
	
	case class SlsaProvenanceZeroTwo(
		builder: Option[Schema.GrafeasV1SlsaProvenanceZeroTwoSlsaBuilder] = None,
		buildType: Option[String] = None,
		invocation: Option[Schema.GrafeasV1SlsaProvenanceZeroTwoSlsaInvocation] = None,
		buildConfig: Option[Map[String, JsValue]] = None,
		metadata: Option[Schema.GrafeasV1SlsaProvenanceZeroTwoSlsaMetadata] = None,
		materials: Option[List[Schema.GrafeasV1SlsaProvenanceZeroTwoSlsaMaterial]] = None
	)
	
	case class GrafeasV1SlsaProvenanceZeroTwoSlsaBuilder(
		id: Option[String] = None
	)
	
	case class GrafeasV1SlsaProvenanceZeroTwoSlsaInvocation(
		configSource: Option[Schema.GrafeasV1SlsaProvenanceZeroTwoSlsaConfigSource] = None,
		parameters: Option[Map[String, JsValue]] = None,
		environment: Option[Map[String, JsValue]] = None
	)
	
	case class GrafeasV1SlsaProvenanceZeroTwoSlsaConfigSource(
		uri: Option[String] = None,
		digest: Option[Map[String, String]] = None,
		entryPoint: Option[String] = None
	)
	
	case class GrafeasV1SlsaProvenanceZeroTwoSlsaMetadata(
		buildInvocationId: Option[String] = None,
		buildStartedOn: Option[String] = None,
		buildFinishedOn: Option[String] = None,
		completeness: Option[Schema.GrafeasV1SlsaProvenanceZeroTwoSlsaCompleteness] = None,
		reproducible: Option[Boolean] = None
	)
	
	case class GrafeasV1SlsaProvenanceZeroTwoSlsaCompleteness(
		parameters: Option[Boolean] = None,
		environment: Option[Boolean] = None,
		materials: Option[Boolean] = None
	)
	
	case class GrafeasV1SlsaProvenanceZeroTwoSlsaMaterial(
		uri: Option[String] = None,
		digest: Option[Map[String, String]] = None
	)
	
	case class InTotoSlsaProvenanceV1(
	  /** InToto spec defined at https://github.com/in-toto/attestation/tree/main/spec#statement */
		_type: Option[String] = None,
		subject: Option[List[Schema.Subject]] = None,
		predicateType: Option[String] = None,
		predicate: Option[Schema.SlsaProvenanceV1] = None
	)
	
	case class SlsaProvenanceV1(
		buildDefinition: Option[Schema.BuildDefinition] = None,
		runDetails: Option[Schema.RunDetails] = None
	)
	
	case class BuildDefinition(
		buildType: Option[String] = None,
		externalParameters: Option[Map[String, JsValue]] = None,
		internalParameters: Option[Map[String, JsValue]] = None,
		resolvedDependencies: Option[List[Schema.ResourceDescriptor]] = None
	)
	
	case class ResourceDescriptor(
		name: Option[String] = None,
		uri: Option[String] = None,
		digest: Option[Map[String, String]] = None,
		content: Option[String] = None,
		downloadLocation: Option[String] = None,
		mediaType: Option[String] = None,
		annotations: Option[Map[String, JsValue]] = None
	)
	
	case class RunDetails(
		builder: Option[Schema.ProvenanceBuilder] = None,
		metadata: Option[Schema.BuildMetadata] = None,
		byproducts: Option[List[Schema.ResourceDescriptor]] = None
	)
	
	case class ProvenanceBuilder(
		id: Option[String] = None,
		version: Option[Map[String, String]] = None,
		builderDependencies: Option[List[Schema.ResourceDescriptor]] = None
	)
	
	case class BuildMetadata(
		invocationId: Option[String] = None,
		startedOn: Option[String] = None,
		finishedOn: Option[String] = None
	)
	
	case class ImageOccurrence(
	  /** Required. The fingerprint of the derived image. */
		fingerprint: Option[Schema.Fingerprint] = None,
	  /** Output only. The number of layers by which this image differs from the associated image basis. */
		distance: Option[Int] = None,
	  /** This contains layer-specific metadata, if populated it has length "distance" and is ordered with [distance] being the layer immediately following the base image and [1] being the final layer. */
		layerInfo: Option[List[Schema.Layer]] = None,
	  /** Output only. This contains the base image URL for the derived image occurrence. */
		baseResourceUrl: Option[String] = None
	)
	
	case class Fingerprint(
	  /** Required. The layer ID of the final layer in the Docker image's v1 representation. */
		v1Name: Option[String] = None,
	  /** Required. The ordered list of v2 blobs that represent a given image. */
		v2Blob: Option[List[String]] = None,
	  /** Output only. The name of the image's v2 blobs computed via: [bottom] := v2_blobbottom := sha256(v2_blob[N] + " " + v2_name[N+1]) Only the name of the final blob is kept. */
		v2Name: Option[String] = None
	)
	
	case class Layer(
	  /** Required. The recovered Dockerfile directive used to construct this layer. See https://docs.docker.com/engine/reference/builder/ for more information. */
		directive: Option[String] = None,
	  /** The recovered arguments to the Dockerfile directive. */
		arguments: Option[String] = None
	)
	
	object PackageOccurrence {
		enum ArchitectureEnum extends Enum[ArchitectureEnum] { case ARCHITECTURE_UNSPECIFIED, X86, X64 }
	}
	case class PackageOccurrence(
	  /** Required. Output only. The name of the installed package. */
		name: Option[String] = None,
	  /** All of the places within the filesystem versions of this package have been found. */
		location: Option[List[Schema.Location]] = None,
	  /** Output only. The type of package; whether native or non native (e.g., ruby gems, node.js packages, etc.). */
		packageType: Option[String] = None,
	  /** Output only. The cpe_uri in [CPE format](https://cpe.mitre.org/specification/) denoting the package manager version distributing a package. The cpe_uri will be blank for language packages. */
		cpeUri: Option[String] = None,
	  /** Output only. The CPU architecture for which packages in this distribution channel were built. Architecture will be blank for language packages. */
		architecture: Option[Schema.PackageOccurrence.ArchitectureEnum] = None,
	  /** Licenses that have been declared by the authors of the package. */
		license: Option[Schema.License] = None,
	  /** Output only. The version of the package. */
		version: Option[Schema.Version] = None
	)
	
	case class Location(
	  /** Deprecated. The CPE URI in [CPE format](https://cpe.mitre.org/specification/) */
		cpeUri: Option[String] = None,
	  /** Deprecated. The version installed at this location. */
		version: Option[Schema.Version] = None,
	  /** The path from which we gathered that this package/version is installed. */
		path: Option[String] = None
	)
	
	case class License(
	  /** Often a single license can be used to represent the licensing terms. Sometimes it is necessary to include a choice of one or more licenses or some combination of license identifiers. Examples: "LGPL-2.1-only OR MIT", "LGPL-2.1-only AND MIT", "GPL-2.0-or-later WITH Bison-exception-2.2". */
		expression: Option[String] = None,
	  /** Comments */
		comments: Option[String] = None
	)
	
	object DeploymentOccurrence {
		enum PlatformEnum extends Enum[PlatformEnum] { case PLATFORM_UNSPECIFIED, GKE, FLEX, CUSTOM }
	}
	case class DeploymentOccurrence(
	  /** Identity of the user that triggered this deployment. */
		userEmail: Option[String] = None,
	  /** Required. Beginning of the lifetime of this deployment. */
		deployTime: Option[String] = None,
	  /** End of the lifetime of this deployment. */
		undeployTime: Option[String] = None,
	  /** Configuration used to create this deployment. */
		config: Option[String] = None,
	  /** Address of the runtime element hosting this deployment. */
		address: Option[String] = None,
	  /** Output only. Resource URI for the artifact being deployed taken from the deployable field with the same name. */
		resourceUri: Option[List[String]] = None,
	  /** Platform hosting this deployment. */
		platform: Option[Schema.DeploymentOccurrence.PlatformEnum] = None
	)
	
	object DiscoveryOccurrence {
		enum ContinuousAnalysisEnum extends Enum[ContinuousAnalysisEnum] { case CONTINUOUS_ANALYSIS_UNSPECIFIED, ACTIVE, INACTIVE }
		enum AnalysisStatusEnum extends Enum[AnalysisStatusEnum] { case ANALYSIS_STATUS_UNSPECIFIED, PENDING, SCANNING, FINISHED_SUCCESS, COMPLETE, FINISHED_FAILED, FINISHED_UNSUPPORTED }
	}
	case class DiscoveryOccurrence(
	  /** Whether the resource is continuously analyzed. */
		continuousAnalysis: Option[Schema.DiscoveryOccurrence.ContinuousAnalysisEnum] = None,
	  /** The status of discovery for the resource. */
		analysisStatus: Option[Schema.DiscoveryOccurrence.AnalysisStatusEnum] = None,
		analysisCompleted: Option[Schema.AnalysisCompleted] = None,
	  /** Indicates any errors encountered during analysis of a resource. There could be 0 or more of these errors. */
		analysisError: Option[List[Schema.Status]] = None,
	  /** When an error is encountered this will contain a LocalizedMessage under details to show to the user. The LocalizedMessage is output only and populated by the API. */
		analysisStatusError: Option[Schema.Status] = None,
	  /** The CPE of the resource being scanned. */
		cpe: Option[String] = None,
	  /** The last time this resource was scanned. */
		lastScanTime: Option[String] = None,
	  /** Output only. The time occurrences related to this discovery occurrence were archived. */
		archiveTime: Option[String] = None,
	  /** The status of an SBOM generation. */
		sbomStatus: Option[Schema.SBOMStatus] = None
	)
	
	case class AnalysisCompleted(
		analysisType: Option[List[String]] = None
	)
	
	object SBOMStatus {
		enum SbomStateEnum extends Enum[SbomStateEnum] { case SBOM_STATE_UNSPECIFIED, PENDING, COMPLETE }
	}
	case class SBOMStatus(
	  /** The progress of the SBOM generation. */
		sbomState: Option[Schema.SBOMStatus.SbomStateEnum] = None,
	  /** If there was an error generating an SBOM, this will indicate what that error was. */
		error: Option[String] = None
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
	
	case class UpgradeOccurrence(
	  /** Required for non-Windows OS. The package this Upgrade is for. */
		`package`: Option[String] = None,
	  /** Required for non-Windows OS. The version of the package in a machine + human readable form. */
		parsedVersion: Option[Schema.Version] = None,
	  /** Metadata about the upgrade for available for the specific operating system for the resource_url. This allows efficient filtering, as well as making it easier to use the occurrence. */
		distribution: Option[Schema.UpgradeDistribution] = None,
	  /** Required for Windows OS. Represents the metadata about the Windows update. */
		windowsUpdate: Option[Schema.WindowsUpdate] = None
	)
	
	case class UpgradeDistribution(
	  /** Required - The specific operating system this metadata applies to. See https://cpe.mitre.org/specification/. */
		cpeUri: Option[String] = None,
	  /** The operating system classification of this Upgrade, as specified by the upstream operating system upgrade feed. For Windows the classification is one of the category_ids listed at https://docs.microsoft.com/en-us/previous-versions/windows/desktop/ff357803(v=vs.85) */
		classification: Option[String] = None,
	  /** The severity as specified by the upstream operating system. */
		severity: Option[String] = None,
	  /** The cve tied to this Upgrade. */
		cve: Option[List[String]] = None
	)
	
	case class WindowsUpdate(
	  /** Required - The unique identifier for the update. */
		identity: Option[Schema.Identity] = None,
	  /** The localized title of the update. */
		title: Option[String] = None,
	  /** The localized description of the update. */
		description: Option[String] = None,
	  /** The list of categories to which the update belongs. */
		categories: Option[List[Schema.Category]] = None,
	  /** The Microsoft Knowledge Base article IDs that are associated with the update. */
		kbArticleIds: Option[List[String]] = None,
	  /** The hyperlink to the support information for the update. */
		supportUrl: Option[String] = None,
	  /** The last published timestamp of the update. */
		lastPublishedTimestamp: Option[String] = None
	)
	
	case class Identity(
	  /** The revision independent identifier of the update. */
		updateId: Option[String] = None,
	  /** The revision number of the update. */
		revision: Option[Int] = None
	)
	
	case class Category(
	  /** The identifier of the category. */
		categoryId: Option[String] = None,
	  /** The localized name of the category. */
		name: Option[String] = None
	)
	
	case class ComplianceOccurrence(
		nonCompliantFiles: Option[List[Schema.NonCompliantFile]] = None,
		nonComplianceReason: Option[String] = None,
	  /** The OS and config version the benchmark was run on. */
		version: Option[Schema.ComplianceVersion] = None
	)
	
	case class NonCompliantFile(
	  /** Empty if `display_command` is set. */
		path: Option[String] = None,
	  /** Command to display the non-compliant files. */
		displayCommand: Option[String] = None,
	  /** Explains why a file is non compliant for a CIS check. */
		reason: Option[String] = None
	)
	
	case class ComplianceVersion(
	  /** The CPE URI (https://cpe.mitre.org/specification/) this benchmark is applicable to. */
		cpeUri: Option[String] = None,
	  /** The name of the document that defines this benchmark, e.g. "CIS Container-Optimized OS". */
		benchmarkDocument: Option[String] = None,
	  /** The version of the benchmark. This is set to the version of the OS-specific CIS document the benchmark is defined in. */
		version: Option[String] = None
	)
	
	case class DSSEAttestationOccurrence(
	  /** If doing something security critical, make sure to verify the signatures in this metadata. */
		envelope: Option[Schema.Envelope] = None,
		statement: Option[Schema.InTotoStatement] = None
	)
	
	case class Envelope(
		payload: Option[String] = None,
		payloadType: Option[String] = None,
		signatures: Option[List[Schema.EnvelopeSignature]] = None
	)
	
	case class EnvelopeSignature(
		sig: Option[String] = None,
		keyid: Option[String] = None
	)
	
	case class SBOMReferenceOccurrence(
	  /** The actual payload that contains the SBOM reference data. */
		payload: Option[Schema.SbomReferenceIntotoPayload] = None,
	  /** The kind of payload that SbomReferenceIntotoPayload takes. Since it's in the intoto format, this value is expected to be 'application/vnd.in-toto+json'. */
		payloadType: Option[String] = None,
	  /** The signatures over the payload. */
		signatures: Option[List[Schema.EnvelopeSignature]] = None
	)
	
	case class SbomReferenceIntotoPayload(
	  /** Identifier for the schema of the Statement. */
		_type: Option[String] = None,
	  /** URI identifying the type of the Predicate. */
		predicateType: Option[String] = None,
	  /** Set of software artifacts that the attestation applies to. Each element represents a single software artifact. */
		subject: Option[List[Schema.Subject]] = None,
	  /** Additional parameters of the Predicate. Includes the actual data about the SBOM. */
		predicate: Option[Schema.SbomReferenceIntotoPredicate] = None
	)
	
	case class SbomReferenceIntotoPredicate(
	  /** The person or system referring this predicate to the consumer. */
		referrerId: Option[String] = None,
	  /** The location of the SBOM. */
		location: Option[String] = None,
	  /** The mime type of the SBOM. */
		mimeType: Option[String] = None,
	  /** A map of algorithm to digest of the contents of the SBOM. */
		digest: Option[Map[String, String]] = None
	)
	
	case class AnalyzePackagesMetadataV1(
	  /** The resource URI of the container image being scanned. */
		resourceUri: Option[String] = None,
	  /** When the scan was created. */
		createTime: Option[String] = None
	)
	
	case class AnalyzePackagesResponseV1(
	  /** The name of the scan resource created by this successful scan. */
		scan: Option[String] = None
	)
	
	case class AnalyzePackagesMetadata(
	  /** The resource URI of the container image being scanned. */
		resourceUri: Option[String] = None,
	  /** When the scan was created. */
		createTime: Option[String] = None
	)
	
	case class AnalyzePackagesResponse(
	  /** The name of the scan resource created by this successful scan. */
		scan: Option[String] = None
	)
}
