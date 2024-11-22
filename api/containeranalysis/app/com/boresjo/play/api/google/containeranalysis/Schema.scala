package com.boresjo.play.api.google.containeranalysis

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class BatchCreateNotesResponse(
	  /** The notes that were created. */
		notes: Option[List[Schema.Note]] = None
	)
	
	case class Detail(
	  /** The type of package; whether native or non native (e.g., ruby gems, node.js packages, etc.). */
		packageType: Option[String] = None,
	  /** Whether this detail is obsolete. Occurrences are expected not to point to obsolete details. */
		isObsolete: Option[Boolean] = None,
	  /** The source from which the information in this Detail was obtained. */
		source: Option[String] = None,
	  /** The version number at the start of an interval in which this vulnerability exists. A vulnerability can affect a package between version numbers that are disjoint sets of intervals (example: [1.0.0-1.1.0], [2.4.6-2.4.8] and [4.5.6-4.6.8]) each of which will be represented in its own Detail. If a specific affected version is provided by a vulnerability database, affected_version_start and affected_version_end will be the same in that Detail. */
		affectedVersionStart: Option[Schema.Version] = None,
	  /** The distro recommended package to update to that contains a fix for this vulnerability. It is possible for this to be different from the affected_package. */
		fixedPackage: Option[String] = None,
	  /** Required. The [CPE URI](https://cpe.mitre.org/specification/) this vulnerability affects. */
		affectedCpeUri: Option[String] = None,
	  /** The version number at the end of an interval in which this vulnerability exists. A vulnerability can affect a package between version numbers that are disjoint sets of intervals (example: [1.0.0-1.1.0], [2.4.6-2.4.8] and [4.5.6-4.6.8]) each of which will be represented in its own Detail. If a specific affected version is provided by a vulnerability database, affected_version_start and affected_version_end will be the same in that Detail. */
		affectedVersionEnd: Option[Schema.Version] = None,
	  /** The distro assigned severity of this vulnerability. */
		severityName: Option[String] = None,
	  /** The time this information was last changed at the source. This is an upstream timestamp from the underlying information source - e.g. Ubuntu security tracker. */
		sourceUpdateTime: Option[String] = None,
	  /** The distro recommended [CPE URI](https://cpe.mitre.org/specification/) to update to that contains a fix for this vulnerability. It is possible for this to be different from the affected_cpe_uri. */
		fixedCpeUri: Option[String] = None,
	  /** The name of the vendor of the product. */
		vendor: Option[String] = None,
	  /** Required. The package this vulnerability affects. */
		affectedPackage: Option[String] = None,
	  /** A vendor-specific description of this vulnerability. */
		description: Option[String] = None,
	  /** The distro recommended version to update to that contains a fix for this vulnerability. Setting this to VersionKind.MAXIMUM means no such version is yet available. */
		fixedVersion: Option[Schema.Version] = None
	)
	
	object SBOMStatus {
		enum SbomStateEnum extends Enum[SbomStateEnum] { case SBOM_STATE_UNSPECIFIED, PENDING, COMPLETE }
	}
	case class SBOMStatus(
	  /** If there was an error generating an SBOM, this will indicate what that error was. */
		error: Option[String] = None,
	  /** The progress of the SBOM generation. */
		sbomState: Option[Schema.SBOMStatus.SbomStateEnum] = None
	)
	
	object BuildStep {
		enum StatusEnum extends Enum[StatusEnum] { case STATUS_UNKNOWN, PENDING, QUEUING, QUEUED, WORKING, SUCCESS, FAILURE, INTERNAL_ERROR, TIMEOUT, CANCELLED, EXPIRED }
	}
	case class BuildStep(
	  /** Output only. Stores timing information for pulling this build step's builder image only. */
		pullTiming: Option[Schema.TimeSpan] = None,
	  /** Entrypoint to be used instead of the build step image's default entrypoint. If unset, the image's default entrypoint is used. */
		entrypoint: Option[String] = None,
	  /** Output only. Stores timing information for executing this build step. */
		timing: Option[Schema.TimeSpan] = None,
	  /** Required. The name of the container image that will run this particular build step. If the image is available in the host's Docker daemon's cache, it will be run directly. If not, the host will attempt to pull the image first, using the builder service account's credentials if necessary. The Docker daemon's cache will already have the latest versions of all of the officially supported build steps ([https://github.com/GoogleCloudPlatform/cloud-builders](https://github.com/GoogleCloudPlatform/cloud-builders)). The Docker daemon will also have cached many of the layers for some popular images, like "ubuntu", "debian", but they will be refreshed at the time you attempt to use them. If you built an image in a previous build step, it will be stored in the host's Docker daemon's cache and is available to use as the name for a later build step. */
		name: Option[String] = None,
	  /** List of volumes to mount into the build step. Each volume is created as an empty volume prior to execution of the build step. Upon completion of the build, volumes and their contents are discarded. Using a named volume in only one step is not valid as it is indicative of a build request with an incorrect configuration. */
		volumes: Option[List[Schema.Volume]] = None,
	  /** A shell script to be executed in the step. When script is provided, the user cannot specify the entrypoint or args. */
		script: Option[String] = None,
	  /** The ID(s) of the step(s) that this build step depends on. This build step will not start until all the build steps in `wait_for` have completed successfully. If `wait_for` is empty, this build step will start when all previous build steps in the `Build.Steps` list have completed successfully. */
		waitFor: Option[List[String]] = None,
	  /** Option to include built-in and custom substitutions as env variables for this build step. This option will override the global option in BuildOption. */
		automapSubstitutions: Option[Boolean] = None,
	  /** Output only. Return code from running the step. */
		exitCode: Option[Int] = None,
	  /** Time limit for executing this build step. If not defined, the step has no time limit and will be allowed to continue to run until either it completes or the build itself times out. */
		timeout: Option[String] = None,
	  /** A list of arguments that will be presented to the step when it is started. If the image used to run the step's container has an entrypoint, the `args` are used as arguments to that entrypoint. If the image does not define an entrypoint, the first element in args is used as the entrypoint, and the remainder will be used as arguments. */
		args: Option[List[String]] = None,
	  /** Working directory to use when running this step's container. If this value is a relative path, it is relative to the build's working directory. If this value is absolute, it may be outside the build's working directory, in which case the contents of the path may not be persisted across build step executions, unless a `volume` for that path is specified. If the build specifies a `RepoSource` with `dir` and a step with a `dir`, which specifies an absolute path, the `RepoSource` `dir` is ignored for the step's execution. */
		dir: Option[String] = None,
	  /** Unique identifier for this build step, used in `wait_for` to reference this build step as a dependency. */
		id: Option[String] = None,
	  /** A list of environment variable definitions to be used when running a step. The elements are of the form "KEY=VALUE" for the environment variable "KEY" being given the value "VALUE". */
		env: Option[List[String]] = None,
	  /** Allow this build step to fail without failing the entire build if and only if the exit code is one of the specified codes. If allow_failure is also specified, this field will take precedence. */
		allowExitCodes: Option[List[Int]] = None,
	  /** A list of environment variables which are encrypted using a Cloud Key Management Service crypto key. These values must be specified in the build's `Secret`. */
		secretEnv: Option[List[String]] = None,
	  /** Output only. Status of the build step. At this time, build step status is only updated on build completion; step status is not updated in real-time as the build progresses. */
		status: Option[Schema.BuildStep.StatusEnum] = None,
	  /** Allow this build step to fail without failing the entire build. If false, the entire build will fail if this step fails. Otherwise, the build will succeed, but this step will still have a failure status. Error information will be reported in the failure_detail field. */
		allowFailure: Option[Boolean] = None
	)
	
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1ConnectedRepository(
	  /** Required. The revision to fetch from the Git repository such as a branch, a tag, a commit SHA, or any Git ref. */
		revision: Option[String] = None,
	  /** Required. Name of the Google Cloud Build repository, formatted as `projects/&#42;/locations/&#42;/connections/&#42;/repositories/&#42;`. */
		repository: Option[String] = None,
	  /** Optional. Directory, relative to the source root, in which to run the build. */
		dir: Option[String] = None
	)
	
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1BuildOptionsPoolOption(
	  /** The `WorkerPool` resource to execute the build on. You must have `cloudbuild.workerpools.use` on the project hosting the WorkerPool. Format projects/{project}/locations/{location}/workerPools/{workerPoolId} */
		name: Option[String] = None
	)
	
	case class CloudRepoSourceContext(
	  /** The ID of the repo. */
		repoId: Option[Schema.RepoId] = None,
	  /** An alias, which may be a branch or tag. */
		aliasContext: Option[Schema.AliasContext] = None,
	  /** A revision ID. */
		revisionId: Option[String] = None
	)
	
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1Volume(
	  /** Path at which to mount the volume. Paths must be absolute and cannot conflict with other volume paths on the same build step or with certain reserved volume paths. */
		path: Option[String] = None,
	  /** Name of the volume to mount. Volume names must be unique per build step and must be valid names for Docker volumes. Each named volume must be used by at least two build steps. */
		name: Option[String] = None
	)
	
	case class ListNoteOccurrencesResponse(
	  /** Token to provide to skip to a particular spot in the list. */
		nextPageToken: Option[String] = None,
	  /** The occurrences attached to the specified note. */
		occurrences: Option[List[Schema.Occurrence]] = None
	)
	
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1InlineSecret(
	  /** Resource name of Cloud KMS crypto key to decrypt the encrypted value. In format: projects/&#42;/locations/&#42;/keyRings/&#42;/cryptoKeys/&#42; */
		kmsKeyName: Option[String] = None,
	  /** Map of environment variable name to its encrypted value. Secret environment variables must be unique across all of a build's secrets, and must be used by at least one build step. Values can be at most 64 KB in size. There can be at most 100 secret values across all of a build's secrets. */
		envMap: Option[Map[String, String]] = None
	)
	
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1ArtifactsPythonPackage(
	  /** Path globs used to match files in the build's workspace. For Python/ Twine, this is usually `dist/&#42;`, and sometimes additionally an `.asc` file. */
		paths: Option[List[String]] = None,
	  /** Artifact Registry repository, in the form "https://$REGION-python.pkg.dev/$PROJECT/$REPOSITORY" Files in the workspace matching any path pattern will be uploaded to Artifact Registry with this location as a prefix. */
		repository: Option[String] = None
	)
	
	case class Policy(
	  /** `etag` is used for optimistic concurrency control as a way to help prevent simultaneous updates of a policy from overwriting each other. It is strongly suggested that systems make use of the `etag` in the read-modify-write cycle to perform policy updates in order to avoid race conditions: An `etag` is returned in the response to `getIamPolicy`, and systems are expected to put that etag in the request to `setIamPolicy` to ensure that their change will be applied to the same version of the policy. &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. */
		etag: Option[String] = None,
	  /** Associates a list of `members`, or principals, with a `role`. Optionally, may specify a `condition` that determines how and when the `bindings` are applied. Each of the `bindings` must contain at least one principal. The `bindings` in a `Policy` can refer to up to 1,500 principals; up to 250 of these principals can be Google groups. Each occurrence of a principal counts towards these limits. For example, if the `bindings` grant 50 different roles to `user:alice@example.com`, and not to any other principal, then you can add another 1,450 principals to the `bindings` in the `Policy`. */
		bindings: Option[List[Schema.Binding]] = None,
	  /** Specifies the format of the policy. Valid values are `0`, `1`, and `3`. Requests that specify an invalid value are rejected. Any operation that affects conditional role bindings must specify version `3`. This requirement applies to the following operations: &#42; Getting a policy that includes a conditional role binding &#42; Adding a conditional role binding to a policy &#42; Changing a conditional role binding in a policy &#42; Removing any role binding, with or without a condition, from a policy that includes conditions &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. If a policy does not include any conditions, operations on that policy may specify any valid version or leave the field unset. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		version: Option[Int] = None
	)
	
	case class Jwt(
	  /** The compact encoding of a JWS, which is always three base64 encoded strings joined by periods. For details, see: https://tools.ietf.org/html/rfc7515.html#section-3.1 */
		compactJwt: Option[String] = None
	)
	
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1Artifacts(
	  /** A list of Maven artifacts to be uploaded to Artifact Registry upon successful completion of all build steps. Artifacts in the workspace matching specified paths globs will be uploaded to the specified Artifact Registry repository using the builder service account's credentials. If any artifacts fail to be pushed, the build is marked FAILURE. */
		mavenArtifacts: Option[List[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1ArtifactsMavenArtifact]] = None,
	  /** A list of images to be pushed upon the successful completion of all build steps. The images will be pushed using the builder service account's credentials. The digests of the pushed images will be stored in the Build resource's results field. If any of the images fail to be pushed, the build is marked FAILURE. */
		images: Option[List[String]] = None,
	  /** A list of Python packages to be uploaded to Artifact Registry upon successful completion of all build steps. The build service account credentials will be used to perform the upload. If any objects fail to be pushed, the build is marked FAILURE. */
		pythonPackages: Option[List[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1ArtifactsPythonPackage]] = None,
	  /** A list of npm packages to be uploaded to Artifact Registry upon successful completion of all build steps. Npm packages in the specified paths will be uploaded to the specified Artifact Registry repository using the builder service account's credentials. If any packages fail to be pushed, the build is marked FAILURE. */
		npmPackages: Option[List[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1ArtifactsNpmPackage]] = None,
	  /** A list of objects to be uploaded to Cloud Storage upon successful completion of all build steps. Files in the workspace matching specified paths globs will be uploaded to the specified Cloud Storage location using the builder service account's credentials. The location and generation of the uploaded objects will be stored in the Build resource's results field. If any objects fail to be pushed, the build is marked FAILURE. */
		objects: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1ArtifactsArtifactObjects] = None
	)
	
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1StorageSourceManifest(
	  /** Required. Cloud Storage bucket containing the source manifest (see [Bucket Name Requirements](https://cloud.google.com/storage/docs/bucket-naming#requirements)). */
		bucket: Option[String] = None,
	  /** Required. Cloud Storage object containing the source manifest. This object must be a JSON file. */
		`object`: Option[String] = None,
	  /** Cloud Storage generation for the object. If the generation is omitted, the latest generation will be used. */
		generation: Option[String] = None
	)
	
	case class BuildOccurrence(
	  /** In-toto Statement representation as defined in spec. The intoto_statement can contain any type of provenance. The serialized payload of the statement can be stored and signed in the Occurrence's envelope. */
		intotoStatement: Option[Schema.InTotoStatement] = None,
	  /** In-Toto Slsa Provenance V1 represents a slsa provenance meeting the slsa spec, wrapped in an in-toto statement. This allows for direct jsonification of a to-spec in-toto slsa statement with a to-spec slsa provenance. */
		inTotoSlsaProvenanceV1: Option[Schema.InTotoSlsaProvenanceV1] = None,
	  /** Deprecated. See InTotoStatement for the replacement. In-toto Provenance representation as defined in spec. */
		intotoProvenance: Option[Schema.InTotoProvenance] = None,
	  /** The actual provenance for the build. */
		provenance: Option[Schema.BuildProvenance] = None,
	  /** Serialized JSON representation of the provenance, used in generating the build signature in the corresponding build note. After verifying the signature, `provenance_bytes` can be unmarshalled and compared to the provenance to confirm that it is unchanged. A base64-encoded string representation of the provenance bytes is used for the signature in order to interoperate with openssl which expects this format for signature verification. The serialized form is captured both to avoid ambiguity in how the provenance is marshalled to json as well to prevent incompatibilities with future changes. */
		provenanceBytes: Option[String] = None
	)
	
	object AliasContext {
		enum KindEnum extends Enum[KindEnum] { case KIND_UNSPECIFIED, FIXED, MOVABLE, OTHER }
	}
	case class AliasContext(
	  /** The alias name. */
		name: Option[String] = None,
	  /** The alias kind. */
		kind: Option[Schema.AliasContext.KindEnum] = None
	)
	
	case class InTotoStatement(
		provenance: Option[Schema.InTotoProvenance] = None,
		slsaProvenance: Option[Schema.SlsaProvenance] = None,
	  /** Always `https://in-toto.io/Statement/v0.1`. */
		_type: Option[String] = None,
		slsaProvenanceZeroTwo: Option[Schema.SlsaProvenanceZeroTwo] = None,
	  /** `https://slsa.dev/provenance/v0.1` for SlsaProvenance. */
		predicateType: Option[String] = None,
		subject: Option[List[Schema.Subject]] = None
	)
	
	object PackageOccurrence {
		enum ArchitectureEnum extends Enum[ArchitectureEnum] { case ARCHITECTURE_UNSPECIFIED, X86, X64 }
	}
	case class PackageOccurrence(
	  /** Output only. The type of package; whether native or non native (e.g., ruby gems, node.js packages, etc.). */
		packageType: Option[String] = None,
	  /** Output only. The CPU architecture for which packages in this distribution channel were built. Architecture will be blank for language packages. */
		architecture: Option[Schema.PackageOccurrence.ArchitectureEnum] = None,
	  /** Required. Output only. The name of the installed package. */
		name: Option[String] = None,
	  /** Output only. The cpe_uri in [CPE format](https://cpe.mitre.org/specification/) denoting the package manager version distributing a package. The cpe_uri will be blank for language packages. */
		cpeUri: Option[String] = None,
	  /** All of the places within the filesystem versions of this package have been found. */
		location: Option[List[Schema.Location]] = None,
	  /** Output only. The version of the package. */
		version: Option[Schema.Version] = None,
	  /** Licenses that have been declared by the authors of the package. */
		license: Option[Schema.License] = None
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
	
	object Remediation {
		enum RemediationTypeEnum extends Enum[RemediationTypeEnum] { case REMEDIATION_TYPE_UNSPECIFIED, MITIGATION, NO_FIX_PLANNED, NONE_AVAILABLE, VENDOR_FIX, WORKAROUND }
	}
	case class Remediation(
	  /** The type of remediation that can be applied. */
		remediationType: Option[Schema.Remediation.RemediationTypeEnum] = None,
	  /** Contains the URL where to obtain the remediation. */
		remediationUri: Option[Schema.RelatedUrl] = None,
	  /** Contains a comprehensive human-readable discussion of the remediation. */
		details: Option[String] = None
	)
	
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1UploadedMavenArtifact(
	  /** Output only. Stores timing information for pushing the specified artifact. */
		pushTiming: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1TimeSpan] = None,
	  /** Hash types and values of the Maven Artifact. */
		fileHashes: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1FileHashes] = None,
	  /** URI of the uploaded artifact. */
		uri: Option[String] = None
	)
	
	case class GrafeasV1SlsaProvenanceZeroTwoSlsaBuilder(
		id: Option[String] = None
	)
	
	case class DeploymentNote(
	  /** Required. Resource URI for the artifact being deployed. */
		resourceUri: Option[List[String]] = None
	)
	
	case class Completeness(
	  /** If true, the builder claims that materials are complete, usually through some controls to prevent network access. Sometimes called "hermetic". */
		materials: Option[Boolean] = None,
	  /** If true, the builder claims that recipe.environment is claimed to be complete. */
		environment: Option[Boolean] = None,
	  /** If true, the builder claims that recipe.arguments is complete, meaning that all external inputs are properly captured in the recipe. */
		arguments: Option[Boolean] = None
	)
	
	case class Volume(
	  /** Path at which to mount the volume. Paths must be absolute and cannot conflict with other volume paths on the same build step or with certain reserved volume paths. */
		path: Option[String] = None,
	  /** Name of the volume to mount. Volume names must be unique per build step and must be valid names for Docker volumes. Each named volume must be used by at least two build steps. */
		name: Option[String] = None
	)
	
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1FileHashes(
	  /** Collection of file hashes. */
		fileHash: Option[List[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1Hash]] = None
	)
	
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1GitConfig(
	  /** Configuration for HTTP related git operations. */
		http: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1GitConfigHttpConfig] = None
	)
	
	object CVSSv3 {
		enum IntegrityImpactEnum extends Enum[IntegrityImpactEnum] { case IMPACT_UNSPECIFIED, IMPACT_HIGH, IMPACT_LOW, IMPACT_NONE }
		enum ConfidentialityImpactEnum extends Enum[ConfidentialityImpactEnum] { case IMPACT_UNSPECIFIED, IMPACT_HIGH, IMPACT_LOW, IMPACT_NONE }
		enum UserInteractionEnum extends Enum[UserInteractionEnum] { case USER_INTERACTION_UNSPECIFIED, USER_INTERACTION_NONE, USER_INTERACTION_REQUIRED }
		enum AvailabilityImpactEnum extends Enum[AvailabilityImpactEnum] { case IMPACT_UNSPECIFIED, IMPACT_HIGH, IMPACT_LOW, IMPACT_NONE }
		enum PrivilegesRequiredEnum extends Enum[PrivilegesRequiredEnum] { case PRIVILEGES_REQUIRED_UNSPECIFIED, PRIVILEGES_REQUIRED_NONE, PRIVILEGES_REQUIRED_LOW, PRIVILEGES_REQUIRED_HIGH }
		enum AttackVectorEnum extends Enum[AttackVectorEnum] { case ATTACK_VECTOR_UNSPECIFIED, ATTACK_VECTOR_NETWORK, ATTACK_VECTOR_ADJACENT, ATTACK_VECTOR_LOCAL, ATTACK_VECTOR_PHYSICAL }
		enum AttackComplexityEnum extends Enum[AttackComplexityEnum] { case ATTACK_COMPLEXITY_UNSPECIFIED, ATTACK_COMPLEXITY_LOW, ATTACK_COMPLEXITY_HIGH }
		enum ScopeEnum extends Enum[ScopeEnum] { case SCOPE_UNSPECIFIED, SCOPE_UNCHANGED, SCOPE_CHANGED }
	}
	case class CVSSv3(
		impactScore: Option[BigDecimal] = None,
	  /** The base score is a function of the base metric scores. */
		baseScore: Option[BigDecimal] = None,
		integrityImpact: Option[Schema.CVSSv3.IntegrityImpactEnum] = None,
		confidentialityImpact: Option[Schema.CVSSv3.ConfidentialityImpactEnum] = None,
		exploitabilityScore: Option[BigDecimal] = None,
		userInteraction: Option[Schema.CVSSv3.UserInteractionEnum] = None,
		availabilityImpact: Option[Schema.CVSSv3.AvailabilityImpactEnum] = None,
		privilegesRequired: Option[Schema.CVSSv3.PrivilegesRequiredEnum] = None,
	  /** Base Metrics Represents the intrinsic characteristics of a vulnerability that are constant over time and across user environments. */
		attackVector: Option[Schema.CVSSv3.AttackVectorEnum] = None,
		attackComplexity: Option[Schema.CVSSv3.AttackComplexityEnum] = None,
		scope: Option[Schema.CVSSv3.ScopeEnum] = None
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
	
	case class SbomReferenceIntotoPayload(
	  /** Set of software artifacts that the attestation applies to. Each element represents a single software artifact. */
		subject: Option[List[Schema.Subject]] = None,
	  /** Identifier for the schema of the Statement. */
		_type: Option[String] = None,
	  /** URI identifying the type of the Predicate. */
		predicateType: Option[String] = None,
	  /** Additional parameters of the Predicate. Includes the actual data about the SBOM. */
		predicate: Option[Schema.SbomReferenceIntotoPredicate] = None
	)
	
	case class Material(
		digest: Option[Map[String, String]] = None,
		uri: Option[String] = None
	)
	
	object DeploymentOccurrence {
		enum PlatformEnum extends Enum[PlatformEnum] { case PLATFORM_UNSPECIFIED, GKE, FLEX, CUSTOM }
	}
	case class DeploymentOccurrence(
	  /** Required. Beginning of the lifetime of this deployment. */
		deployTime: Option[String] = None,
	  /** Address of the runtime element hosting this deployment. */
		address: Option[String] = None,
	  /** Identity of the user that triggered this deployment. */
		userEmail: Option[String] = None,
	  /** Output only. Resource URI for the artifact being deployed taken from the deployable field with the same name. */
		resourceUri: Option[List[String]] = None,
	  /** Configuration used to create this deployment. */
		config: Option[String] = None,
	  /** Platform hosting this deployment. */
		platform: Option[Schema.DeploymentOccurrence.PlatformEnum] = None,
	  /** End of the lifetime of this deployment. */
		undeployTime: Option[String] = None
	)
	
	object ContaineranalysisGoogleDevtoolsCloudbuildV1Hash {
		enum TypeEnum extends Enum[TypeEnum] { case NONE, SHA256, MD5, SHA512 }
	}
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1Hash(
	  /** The type of hash that was performed. */
		`type`: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1Hash.TypeEnum] = None,
	  /** The hash value. */
		value: Option[String] = None
	)
	
	case class GrafeasV1SlsaProvenanceZeroTwoSlsaMaterial(
		uri: Option[String] = None,
		digest: Option[Map[String, String]] = None
	)
	
	object ContaineranalysisGoogleDevtoolsCloudbuildV1BuildOptions {
		enum SubstitutionOptionEnum extends Enum[SubstitutionOptionEnum] { case MUST_MATCH, ALLOW_LOOSE }
		enum LogStreamingOptionEnum extends Enum[LogStreamingOptionEnum] { case STREAM_DEFAULT, STREAM_ON, STREAM_OFF }
		enum DefaultLogsBucketBehaviorEnum extends Enum[DefaultLogsBucketBehaviorEnum] { case DEFAULT_LOGS_BUCKET_BEHAVIOR_UNSPECIFIED, REGIONAL_USER_OWNED_BUCKET, LEGACY_BUCKET }
		enum MachineTypeEnum extends Enum[MachineTypeEnum] { case UNSPECIFIED, N1_HIGHCPU_8, N1_HIGHCPU_32, E2_HIGHCPU_8, E2_HIGHCPU_32, E2_MEDIUM }
		enum SourceProvenanceHashEnum extends Enum[SourceProvenanceHashEnum] { case NONE, SHA256, MD5, SHA512 }
		enum RequestedVerifyOptionEnum extends Enum[RequestedVerifyOptionEnum] { case NOT_VERIFIED, VERIFIED }
		enum LoggingEnum extends Enum[LoggingEnum] { case LOGGING_UNSPECIFIED, LEGACY, GCS_ONLY, STACKDRIVER_ONLY, CLOUD_LOGGING_ONLY, NONE }
	}
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1BuildOptions(
	  /** Requested disk size for the VM that runs the build. Note that this is &#42;NOT&#42; "disk free"; some of the space will be used by the operating system and build utilities. Also note that this is the minimum disk size that will be allocated for the build -- the build may run with a larger disk than requested. At present, the maximum disk size is 4000GB; builds that request more than the maximum are rejected with an error. */
		diskSizeGb: Option[String] = None,
	  /** Option to specify behavior when there is an error in the substitution checks. NOTE: this is always set to ALLOW_LOOSE for triggered builds and cannot be overridden in the build configuration file. */
		substitutionOption: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1BuildOptions.SubstitutionOptionEnum] = None,
	  /** Option to define build log streaming behavior to Cloud Storage. */
		logStreamingOption: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1BuildOptions.LogStreamingOptionEnum] = None,
	  /** Optional. Option to specify how default logs buckets are setup. */
		defaultLogsBucketBehavior: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1BuildOptions.DefaultLogsBucketBehaviorEnum] = None,
	  /** A list of global environment variables, which are encrypted using a Cloud Key Management Service crypto key. These values must be specified in the build's `Secret`. These variables will be available to all build steps in this build. */
		secretEnv: Option[List[String]] = None,
	  /** Option to include built-in and custom substitutions as env variables for all build steps. */
		automapSubstitutions: Option[Boolean] = None,
	  /** Compute Engine machine type on which to run the build. */
		machineType: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1BuildOptions.MachineTypeEnum] = None,
	  /** Requested hash for SourceProvenance. */
		sourceProvenanceHash: Option[List[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1BuildOptions.SourceProvenanceHashEnum]] = None,
	  /** Optional. Specification for execution on a `WorkerPool`. See [running builds in a private pool](https://cloud.google.com/build/docs/private-pools/run-builds-in-private-pool) for more information. */
		pool: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1BuildOptionsPoolOption] = None,
	  /** This field deprecated; please use `pool.name` instead. */
		workerPool: Option[String] = None,
	  /** A list of global environment variable definitions that will exist for all build steps in this build. If a variable is defined in both globally and in a build step, the variable will use the build step value. The elements are of the form "KEY=VALUE" for the environment variable "KEY" being given the value "VALUE". */
		env: Option[List[String]] = None,
	  /** Option to specify whether or not to apply bash style string operations to the substitutions. NOTE: this is always enabled for triggered builds and cannot be overridden in the build configuration file. */
		dynamicSubstitutions: Option[Boolean] = None,
	  /** Requested verifiability options. */
		requestedVerifyOption: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1BuildOptions.RequestedVerifyOptionEnum] = None,
	  /** Option to specify the logging mode, which determines if and where build logs are stored. */
		logging: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1BuildOptions.LoggingEnum] = None,
	  /** Global list of volumes to mount for ALL build steps Each volume is created as an empty volume prior to starting the build process. Upon completion of the build, volumes and their contents are discarded. Global volume names and paths cannot conflict with the volumes defined a build step. Using a global volume in a build with only one step is not valid as it is indicative of a build request with an incorrect configuration. */
		volumes: Option[List[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1Volume]] = None
	)
	
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1DeveloperConnectConfig(
	  /** Required. The revision to fetch from the Git repository such as a branch, a tag, a commit SHA, or any Git ref. */
		revision: Option[String] = None,
	  /** Required. Directory, relative to the source root, in which to run the build. */
		dir: Option[String] = None,
	  /** Required. The Developer Connect Git repository link, formatted as `projects/&#42;/locations/&#42;/connections/&#42;/gitRepositoryLink/&#42;`. */
		gitRepositoryLink: Option[String] = None
	)
	
	case class Signature(
	  /** The identifier for the public key that verifies this signature. &#42; The `public_key_id` is required. &#42; The `public_key_id` SHOULD be an RFC3986 conformant URI. &#42; When possible, the `public_key_id` SHOULD be an immutable reference, such as a cryptographic digest. Examples of valid `public_key_id`s: OpenPGP V4 public key fingerprint: &#42; "openpgp4fpr:74FAF3B861BDA0870C7B6DEF607E48D2A663AEEA" See https://www.iana.org/assignments/uri-schemes/prov/openpgp4fpr for more details on this scheme. RFC6920 digest-named SubjectPublicKeyInfo (digest of the DER serialization): &#42; "ni:///sha-256;cD9o9Cq6LG3jD0iKXqEi_vdjJGecm_iXkbqVoScViaU" &#42; "nih:///sha-256;703f68f42aba2c6de30f488a5ea122fef76324679c9bf89791ba95a1271589a5" */
		publicKeyId: Option[String] = None,
	  /** The content of the signature, an opaque bytestring. The payload that this signature verifies MUST be unambiguously provided with the Signature during verification. A wrapper message might provide the payload explicitly. Alternatively, a message might have a canonical serialization that can always be unambiguously computed to derive the payload. */
		signature: Option[String] = None
	)
	
	case class ComplianceVersion(
	  /** The name of the document that defines this benchmark, e.g. "CIS Container-Optimized OS". */
		benchmarkDocument: Option[String] = None,
	  /** The CPE URI (https://cpe.mitre.org/specification/) this benchmark is applicable to. */
		cpeUri: Option[String] = None,
	  /** The version of the benchmark. This is set to the version of the OS-specific CIS document the benchmark is defined in. */
		version: Option[String] = None
	)
	
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1UploadedNpmPackage(
	  /** URI of the uploaded npm package. */
		uri: Option[String] = None,
	  /** Output only. Stores timing information for pushing the specified artifact. */
		pushTiming: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1TimeSpan] = None,
	  /** Hash types and values of the npm package. */
		fileHashes: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1FileHashes] = None
	)
	
	case class ProvenanceBuilder(
		builderDependencies: Option[List[Schema.ResourceDescriptor]] = None,
		version: Option[Map[String, String]] = None,
		id: Option[String] = None
	)
	
	case class BuildDefinition(
		buildType: Option[String] = None,
		resolvedDependencies: Option[List[Schema.ResourceDescriptor]] = None,
		externalParameters: Option[Map[String, JsValue]] = None,
		internalParameters: Option[Map[String, JsValue]] = None
	)
	
	case class CloudStorageLocation(
	
	)
	
	object ContaineranalysisGoogleDevtoolsCloudbuildV1Build {
		enum StatusEnum extends Enum[StatusEnum] { case STATUS_UNKNOWN, PENDING, QUEUED, WORKING, SUCCESS, FAILURE, INTERNAL_ERROR, TIMEOUT, CANCELLED, EXPIRED }
	}
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1Build(
	  /** Output only. Time at which execution of the build was finished. The difference between finish_time and start_time is the duration of the build's execution. */
		finishTime: Option[String] = None,
	  /** Secrets to decrypt using Cloud Key Management Service. Note: Secret Manager is the recommended technique for managing sensitive data with Cloud Build. Use `available_secrets` to configure builds to access secrets from Secret Manager. For instructions, see: https://cloud.google.com/cloud-build/docs/securing-builds/use-secrets */
		secrets: Option[List[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1Secret]] = None,
	  /** Output only. A permanent fixed identifier for source. */
		sourceProvenance: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1SourceProvenance] = None,
	  /** Cloud Storage bucket where logs should be written (see [Bucket Name Requirements](https://cloud.google.com/storage/docs/bucket-naming#requirements)). Logs file names will be of the format `${logs_bucket}/log-${build_id}.txt`. */
		logsBucket: Option[String] = None,
	  /** Output only. Customer-readable message about the current status. */
		statusDetail: Option[String] = None,
	  /** Tags for annotation of a `Build`. These are not docker tags. */
		tags: Option[List[String]] = None,
	  /** A list of images to be pushed upon the successful completion of all build steps. The images are pushed using the builder service account's credentials. The digests of the pushed images will be stored in the `Build` resource's results field. If any of the images fail to be pushed, the build status is marked `FAILURE`. */
		images: Option[List[String]] = None,
	  /** Substitutions data for `Build` resource. */
		substitutions: Option[Map[String, String]] = None,
	  /** IAM service account whose credentials will be used at build runtime. Must be of the format `projects/{PROJECT_ID}/serviceAccounts/{ACCOUNT}`. ACCOUNT can be email address or uniqueId of the service account.  */
		serviceAccount: Option[String] = None,
	  /** Output only. URL to logs for this build in Google Cloud Console. */
		logUrl: Option[String] = None,
	  /** Output only. Results of the build. */
		results: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1Results] = None,
	  /** Output only. Stores timing information for phases of the build. Valid keys are: &#42; BUILD: time to execute all build steps. &#42; PUSH: time to push all artifacts including docker images and non docker artifacts. &#42; FETCHSOURCE: time to fetch source. &#42; SETUPBUILD: time to set up build. If the build does not specify source or images, these keys will not be included. */
		timing: Option[Map[String, Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1TimeSpan]] = None,
	  /** TTL in queue for this build. If provided and the build is enqueued longer than this value, the build will expire and the build status will be `EXPIRED`. The TTL starts ticking from create_time. */
		queueTtl: Option[String] = None,
	  /** Output only. The ID of the `BuildTrigger` that triggered this build, if it was triggered automatically. */
		buildTriggerId: Option[String] = None,
	  /** Amount of time that this build should be allowed to run, to second granularity. If this amount of time elapses, work on the build will cease and the build status will be `TIMEOUT`. `timeout` starts ticking from `startTime`. Default time is 60 minutes. */
		timeout: Option[String] = None,
	  /** Output only. Status of the build. */
		status: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1Build.StatusEnum] = None,
	  /** Optional. The location of the source files to build. */
		source: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1Source] = None,
	  /** Output only. Non-fatal problems encountered during the execution of the build. */
		warnings: Option[List[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1BuildWarning]] = None,
	  /** Output only. Time at which execution of the build was started. */
		startTime: Option[String] = None,
	  /** Special options for this build. */
		options: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1BuildOptions] = None,
	  /** Required. The operations to be performed on the workspace. */
		steps: Option[List[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1BuildStep]] = None,
	  /** Secrets and secret environment variables. */
		availableSecrets: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1Secrets] = None,
	  /** Output only. Describes this build's approval configuration, status, and result. */
		approval: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1BuildApproval] = None,
	  /** Output only. ID of the project. */
		projectId: Option[String] = None,
	  /** Optional. Configuration for git operations. */
		gitConfig: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1GitConfig] = None,
	  /** Output only. Unique identifier of the build. */
		id: Option[String] = None,
	  /** Artifacts produced by the build that should be uploaded upon successful completion of all build steps. */
		artifacts: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1Artifacts] = None,
	  /** Output only. Contains information about the build when status=FAILURE. */
		failureInfo: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1BuildFailureInfo] = None,
	  /** Output only. The 'Build' name with format: `projects/{project}/locations/{location}/builds/{build}`, where {build} is a unique identifier generated by the service. */
		name: Option[String] = None,
	  /** Output only. Time at which the request to create the build was received. */
		createTime: Option[String] = None
	)
	
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1ArtifactsNpmPackage(
	  /** Artifact Registry repository, in the form "https://$REGION-npm.pkg.dev/$PROJECT/$REPOSITORY" Npm package in the workspace specified by path will be zipped and uploaded to Artifact Registry with this location as a prefix. */
		repository: Option[String] = None,
	  /** Path to the package.json. e.g. workspace/path/to/package */
		packagePath: Option[String] = None
	)
	
	case class Expr(
	  /** Optional. Title for the expression, i.e. a short string describing its purpose. This can be used e.g. in UIs which allow to enter the expression. */
		title: Option[String] = None,
	  /** Textual representation of an expression in Common Expression Language syntax. */
		expression: Option[String] = None,
	  /** Optional. Description of the expression. This is a longer text which describes the expression, e.g. when hovered over it in a UI. */
		description: Option[String] = None,
	  /** Optional. String indicating the location of the expression for error reporting, e.g. a file name and a position in the file. */
		location: Option[String] = None
	)
	
	object CVSS {
		enum UserInteractionEnum extends Enum[UserInteractionEnum] { case USER_INTERACTION_UNSPECIFIED, USER_INTERACTION_NONE, USER_INTERACTION_REQUIRED }
		enum AvailabilityImpactEnum extends Enum[AvailabilityImpactEnum] { case IMPACT_UNSPECIFIED, IMPACT_HIGH, IMPACT_LOW, IMPACT_NONE, IMPACT_PARTIAL, IMPACT_COMPLETE }
		enum PrivilegesRequiredEnum extends Enum[PrivilegesRequiredEnum] { case PRIVILEGES_REQUIRED_UNSPECIFIED, PRIVILEGES_REQUIRED_NONE, PRIVILEGES_REQUIRED_LOW, PRIVILEGES_REQUIRED_HIGH }
		enum AttackVectorEnum extends Enum[AttackVectorEnum] { case ATTACK_VECTOR_UNSPECIFIED, ATTACK_VECTOR_NETWORK, ATTACK_VECTOR_ADJACENT, ATTACK_VECTOR_LOCAL, ATTACK_VECTOR_PHYSICAL }
		enum AttackComplexityEnum extends Enum[AttackComplexityEnum] { case ATTACK_COMPLEXITY_UNSPECIFIED, ATTACK_COMPLEXITY_LOW, ATTACK_COMPLEXITY_HIGH, ATTACK_COMPLEXITY_MEDIUM }
		enum ConfidentialityImpactEnum extends Enum[ConfidentialityImpactEnum] { case IMPACT_UNSPECIFIED, IMPACT_HIGH, IMPACT_LOW, IMPACT_NONE, IMPACT_PARTIAL, IMPACT_COMPLETE }
		enum ScopeEnum extends Enum[ScopeEnum] { case SCOPE_UNSPECIFIED, SCOPE_UNCHANGED, SCOPE_CHANGED }
		enum IntegrityImpactEnum extends Enum[IntegrityImpactEnum] { case IMPACT_UNSPECIFIED, IMPACT_HIGH, IMPACT_LOW, IMPACT_NONE, IMPACT_PARTIAL, IMPACT_COMPLETE }
		enum AuthenticationEnum extends Enum[AuthenticationEnum] { case AUTHENTICATION_UNSPECIFIED, AUTHENTICATION_MULTIPLE, AUTHENTICATION_SINGLE, AUTHENTICATION_NONE }
	}
	case class CVSS(
		exploitabilityScore: Option[BigDecimal] = None,
		userInteraction: Option[Schema.CVSS.UserInteractionEnum] = None,
		availabilityImpact: Option[Schema.CVSS.AvailabilityImpactEnum] = None,
		privilegesRequired: Option[Schema.CVSS.PrivilegesRequiredEnum] = None,
	  /** Base Metrics Represents the intrinsic characteristics of a vulnerability that are constant over time and across user environments. */
		attackVector: Option[Schema.CVSS.AttackVectorEnum] = None,
	  /** The base score is a function of the base metric scores. */
		baseScore: Option[BigDecimal] = None,
		attackComplexity: Option[Schema.CVSS.AttackComplexityEnum] = None,
		confidentialityImpact: Option[Schema.CVSS.ConfidentialityImpactEnum] = None,
		impactScore: Option[BigDecimal] = None,
		scope: Option[Schema.CVSS.ScopeEnum] = None,
		integrityImpact: Option[Schema.CVSS.IntegrityImpactEnum] = None,
		authentication: Option[Schema.CVSS.AuthenticationEnum] = None
	)
	
	case class KnowledgeBase(
	  /** The KB name (generally of the form KB[0-9]+ (e.g., KB123456)). */
		name: Option[String] = None,
	  /** A link to the KB in the [Windows update catalog] (https://www.catalog.update.microsoft.com/). */
		url: Option[String] = None
	)
	
	case class UpgradeNote(
	  /** Required for Windows OS. Represents the metadata about the Windows update. */
		windowsUpdate: Option[Schema.WindowsUpdate] = None,
	  /** Metadata about the upgrade for each specific operating system. */
		distributions: Option[List[Schema.UpgradeDistribution]] = None,
	  /** Required for non-Windows OS. The version of the package in machine + human readable form. */
		version: Option[Schema.Version] = None,
	  /** Required for non-Windows OS. The package this Upgrade is for. */
		`package`: Option[String] = None
	)
	
	case class Envelope(
		payloadType: Option[String] = None,
		signatures: Option[List[Schema.EnvelopeSignature]] = None,
		payload: Option[String] = None
	)
	
	case class VulnerabilityOccurrencesSummary(
	  /** A listing by resource of the number of fixable and total vulnerabilities. */
		counts: Option[List[Schema.FixableTotalByDigest]] = None
	)
	
	case class GrafeasV1SlsaProvenanceZeroTwoSlsaCompleteness(
		environment: Option[Boolean] = None,
		materials: Option[Boolean] = None,
		parameters: Option[Boolean] = None
	)
	
	case class GerritSourceContext(
	  /** An alias, which may be a branch or tag. */
		aliasContext: Option[Schema.AliasContext] = None,
	  /** A revision (commit) ID. */
		revisionId: Option[String] = None,
	  /** The full project name within the host. Projects may be nested, so "project/subproject" is a valid project name. The "repo name" is the hostURI/project. */
		gerritProject: Option[String] = None,
	  /** The URI of a running Gerrit instance. */
		hostUri: Option[String] = None
	)
	
	object Assessment {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, AFFECTED, NOT_AFFECTED, FIXED, UNDER_INVESTIGATION }
	}
	case class Assessment(
	  /** A detailed description of this Vex. */
		longDescription: Option[String] = None,
	  /** A one sentence description of this Vex. */
		shortDescription: Option[String] = None,
	  /** Specifies details on how to handle (and presumably, fix) a vulnerability. */
		remediations: Option[List[Schema.Remediation]] = None,
	  /** Holds a list of references associated with this vulnerability item and assessment. These uris have additional information about the vulnerability and the assessment itself. E.g. Link to a document which details how this assessment concluded the state of this vulnerability. */
		relatedUris: Option[List[Schema.RelatedUrl]] = None,
	  /** Justification provides the justification when the state of the assessment if NOT_AFFECTED. */
		justification: Option[Schema.Justification] = None,
	  /** Holds the MITRE standard Common Vulnerabilities and Exposures (CVE) tracking number for the vulnerability. Deprecated: Use vulnerability_id instead to denote CVEs. */
		cve: Option[String] = None,
	  /** Provides the state of this Vulnerability assessment. */
		state: Option[Schema.Assessment.StateEnum] = None,
	  /** The vulnerability identifier for this Assessment. Will hold one of common identifiers e.g. CVE, GHSA etc. */
		vulnerabilityId: Option[String] = None,
	  /** Contains information about the impact of this vulnerability, this will change with time. */
		impacts: Option[List[String]] = None
	)
	
	case class SBOMReferenceOccurrence(
	  /** The actual payload that contains the SBOM reference data. */
		payload: Option[Schema.SbomReferenceIntotoPayload] = None,
	  /** The signatures over the payload. */
		signatures: Option[List[Schema.EnvelopeSignature]] = None,
	  /** The kind of payload that SbomReferenceIntotoPayload takes. Since it's in the intoto format, this value is expected to be 'application/vnd.in-toto+json'. */
		payloadType: Option[String] = None
	)
	
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1GitConfigHttpConfig(
	  /** SecretVersion resource of the HTTP proxy URL. The Service Account used in the build (either the default Service Account or user-specified Service Account) should have `secretmanager.versions.access` permissions on this secret. The proxy URL should be in format `protocol://@]proxyhost[:port]`. */
		proxySecretVersionName: Option[String] = None
	)
	
	case class SlsaBuilder(
		id: Option[String] = None
	)
	
	case class GrafeasV1SlsaProvenanceZeroTwoSlsaConfigSource(
		digest: Option[Map[String, String]] = None,
		entryPoint: Option[String] = None,
		uri: Option[String] = None
	)
	
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1Secret(
	  /** Map of environment variable name to its encrypted value. Secret environment variables must be unique across all of a build's secrets, and must be used by at least one build step. Values can be at most 64 KB in size. There can be at most 100 secret values across all of a build's secrets. */
		secretEnv: Option[Map[String, String]] = None,
	  /** Cloud KMS key name to use to decrypt these envs. */
		kmsKeyName: Option[String] = None
	)
	
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1ApprovalConfig(
	  /** Whether or not approval is needed. If this is set on a build, it will become pending when created, and will need to be explicitly approved to start. */
		approvalRequired: Option[Boolean] = None
	)
	
	case class ImageNote(
	  /** Required. Immutable. The resource_url for the resource representing the basis of associated occurrence images. */
		resourceUrl: Option[String] = None,
	  /** Required. Immutable. The fingerprint of the base image. */
		fingerprint: Option[Schema.Fingerprint] = None
	)
	
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1SourceProvenance(
	  /** A copy of the build's `source.storage_source`, if exists, with any generations resolved. */
		resolvedStorageSource: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1StorageSource] = None,
	  /** Output only. A copy of the build's `source.git_source`, if exists, with any revisions resolved. */
		resolvedGitSource: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1GitSource] = None,
	  /** A copy of the build's `source.storage_source_manifest`, if exists, with any revisions resolved. This feature is in Preview. */
		resolvedStorageSourceManifest: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1StorageSourceManifest] = None,
	  /** Output only. Hash(es) of the build source, which can be used to verify that the original source integrity was maintained in the build. Note that `FileHashes` will only be populated if `BuildOptions` has requested a `SourceProvenanceHash`. The keys to this map are file paths used as build source and the values contain the hash values for those files. If the build source came in a single package such as a gzipped tarfile (`.tar.gz`), the `FileHash` will be for the single path to that file. */
		fileHashes: Option[Map[String, Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1FileHashes]] = None,
	  /** A copy of the build's `source.repo_source`, if exists, with any revisions resolved. */
		resolvedRepoSource: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1RepoSource] = None,
	  /** Output only. A copy of the build's `source.connected_repository`, if exists, with any revisions resolved. */
		resolvedConnectedRepository: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1ConnectedRepository] = None
	)
	
	case class Identity(
	  /** The revision independent identifier of the update. */
		updateId: Option[String] = None,
	  /** The revision number of the update. */
		revision: Option[Int] = None
	)
	
	case class Digest(
	  /** `SHA1`, `SHA512` etc. */
		algo: Option[String] = None,
	  /** Value of the digest. */
		digestBytes: Option[String] = None
	)
	
	object Distribution {
		enum ArchitectureEnum extends Enum[ArchitectureEnum] { case ARCHITECTURE_UNSPECIFIED, X86, X64 }
	}
	case class Distribution(
	  /** The distribution channel-specific homepage for this package. */
		url: Option[String] = None,
	  /** The latest available version of this package in this distribution channel. */
		latestVersion: Option[Schema.Version] = None,
	  /** A freeform string denoting the maintainer of this package. */
		maintainer: Option[String] = None,
	  /** Required. The cpe_uri in [CPE format](https://cpe.mitre.org/specification/) denoting the package manager version distributing a package. */
		cpeUri: Option[String] = None,
	  /** The CPU architecture for which packages in this distribution channel were built. */
		architecture: Option[Schema.Distribution.ArchitectureEnum] = None,
	  /** The distribution channel-specific description of this package. */
		description: Option[String] = None
	)
	
	case class ImageOccurrence(
	  /** This contains layer-specific metadata, if populated it has length "distance" and is ordered with [distance] being the layer immediately following the base image and [1] being the final layer. */
		layerInfo: Option[List[Schema.Layer]] = None,
	  /** Output only. The number of layers by which this image differs from the associated image basis. */
		distance: Option[Int] = None,
	  /** Output only. This contains the base image URL for the derived image occurrence. */
		baseResourceUrl: Option[String] = None,
	  /** Required. The fingerprint of the derived image. */
		fingerprint: Option[Schema.Fingerprint] = None
	)
	
	case class RunDetails(
		builder: Option[Schema.ProvenanceBuilder] = None,
		metadata: Option[Schema.BuildMetadata] = None,
		byproducts: Option[List[Schema.ResourceDescriptor]] = None
	)
	
	case class WindowsUpdate(
	  /** Required - The unique identifier for the update. */
		identity: Option[Schema.Identity] = None,
	  /** The hyperlink to the support information for the update. */
		supportUrl: Option[String] = None,
	  /** The last published timestamp of the update. */
		lastPublishedTimestamp: Option[String] = None,
	  /** The list of categories to which the update belongs. */
		categories: Option[List[Schema.Category]] = None,
	  /** The localized description of the update. */
		description: Option[String] = None,
	  /** The Microsoft Knowledge Base article IDs that are associated with the update. */
		kbArticleIds: Option[List[String]] = None,
	  /** The localized title of the update. */
		title: Option[String] = None
	)
	
	object Version {
		enum KindEnum extends Enum[KindEnum] { case VERSION_KIND_UNSPECIFIED, NORMAL, MINIMUM, MAXIMUM }
	}
	case class Version(
	  /** Required only when version kind is NORMAL. The main part of the version name. */
		name: Option[String] = None,
	  /** Required. Distinguishes between sentinel MIN/MAX versions and normal versions. */
		kind: Option[Schema.Version.KindEnum] = None,
	  /** Whether this version is specifying part of an inclusive range. Grafeas does not have the capability to specify version ranges; instead we have fields that specify start version and end versions. At times this is insufficient - we also need to specify whether the version is included in the range or is excluded from the range. This boolean is expected to be set to true when the version is included in a range. */
		inclusive: Option[Boolean] = None,
	  /** Used to correct mistakes in the version numbering scheme. */
		epoch: Option[Int] = None,
	  /** The iteration of the package build from the above version. */
		revision: Option[String] = None,
	  /** Human readable version string. This string is of the form :- and is only set when kind is NORMAL. */
		fullName: Option[String] = None
	)
	
	case class SBOMReferenceNote(
	  /** The format that SBOM takes. E.g. may be spdx, cyclonedx, etc... */
		format: Option[String] = None,
	  /** The version of the format that the SBOM takes. E.g. if the format is spdx, the version may be 2.3. */
		version: Option[String] = None
	)
	
	case class ExportSBOMResponse(
	  /** The name of the discovery occurrence in the form "projects/{project_id}/occurrences/{OCCURRENCE_ID} It can be used to track the progress of the SBOM export. */
		discoveryOccurrence: Option[String] = None
	)
	
	case class TestIamPermissionsRequest(
	  /** The set of permissions to check for the `resource`. Permissions with wildcards (such as `&#42;` or `storage.&#42;`) are not allowed. For more information see [IAM Overview](https://cloud.google.com/iam/docs/overview#permissions). */
		permissions: Option[List[String]] = None
	)
	
	case class ListNotesResponse(
	  /** The next pagination token in the list response. It should be used as `page_token` for the following request. An empty value means no more results. */
		nextPageToken: Option[String] = None,
	  /** The notes requested. */
		notes: Option[List[Schema.Note]] = None
	)
	
	object ContaineranalysisGoogleDevtoolsCloudbuildV1BuildFailureInfo {
		enum TypeEnum extends Enum[TypeEnum] { case FAILURE_TYPE_UNSPECIFIED, PUSH_FAILED, PUSH_IMAGE_NOT_FOUND, PUSH_NOT_AUTHORIZED, LOGGING_FAILURE, USER_BUILD_STEP, FETCH_SOURCE_FAILED }
	}
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1BuildFailureInfo(
	  /** The name of the failure. */
		`type`: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1BuildFailureInfo.TypeEnum] = None,
	  /** Explains the failure issue in more detail using hard-coded text. */
		detail: Option[String] = None
	)
	
	object DiscoveryOccurrence {
		enum AnalysisStatusEnum extends Enum[AnalysisStatusEnum] { case ANALYSIS_STATUS_UNSPECIFIED, PENDING, SCANNING, FINISHED_SUCCESS, COMPLETE, FINISHED_FAILED, FINISHED_UNSUPPORTED }
		enum ContinuousAnalysisEnum extends Enum[ContinuousAnalysisEnum] { case CONTINUOUS_ANALYSIS_UNSPECIFIED, ACTIVE, INACTIVE }
	}
	case class DiscoveryOccurrence(
	  /** The status of discovery for the resource. */
		analysisStatus: Option[Schema.DiscoveryOccurrence.AnalysisStatusEnum] = None,
	  /** The status of an SBOM generation. */
		sbomStatus: Option[Schema.SBOMStatus] = None,
	  /** The CPE of the resource being scanned. */
		cpe: Option[String] = None,
	  /** Whether the resource is continuously analyzed. */
		continuousAnalysis: Option[Schema.DiscoveryOccurrence.ContinuousAnalysisEnum] = None,
	  /** The last time this resource was scanned. */
		lastScanTime: Option[String] = None,
	  /** Indicates any errors encountered during analysis of a resource. There could be 0 or more of these errors. */
		analysisError: Option[List[Schema.Status]] = None,
		analysisCompleted: Option[Schema.AnalysisCompleted] = None,
	  /** Output only. The time occurrences related to this discovery occurrence were archived. */
		archiveTime: Option[String] = None,
	  /** When an error is encountered this will contain a LocalizedMessage under details to show to the user. The LocalizedMessage is output only and populated by the API. */
		analysisStatusError: Option[Schema.Status] = None
	)
	
	case class GrafeasV1SlsaProvenanceZeroTwoSlsaInvocation(
		parameters: Option[Map[String, JsValue]] = None,
		configSource: Option[Schema.GrafeasV1SlsaProvenanceZeroTwoSlsaConfigSource] = None,
		environment: Option[Map[String, JsValue]] = None
	)
	
	object VexAssessment {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, AFFECTED, NOT_AFFECTED, FIXED, UNDER_INVESTIGATION }
	}
	case class VexAssessment(
	  /** The VulnerabilityAssessment note from which this VexAssessment was generated. This will be of the form: `projects/[PROJECT_ID]/notes/[NOTE_ID]`. */
		noteName: Option[String] = None,
	  /** The vulnerability identifier for this Assessment. Will hold one of common identifiers e.g. CVE, GHSA etc. */
		vulnerabilityId: Option[String] = None,
	  /** Holds a list of references associated with this vulnerability item and assessment. */
		relatedUris: Option[List[Schema.RelatedUrl]] = None,
	  /** Provides the state of this Vulnerability assessment. */
		state: Option[Schema.VexAssessment.StateEnum] = None,
	  /** Contains information about the impact of this vulnerability, this will change with time. */
		impacts: Option[List[String]] = None,
	  /** Holds the MITRE standard Common Vulnerabilities and Exposures (CVE) tracking number for the vulnerability. Deprecated: Use vulnerability_id instead to denote CVEs. */
		cve: Option[String] = None,
	  /** Justification provides the justification when the state of the assessment if NOT_AFFECTED. */
		justification: Option[Schema.Justification] = None,
	  /** Specifies details on how to handle (and presumably, fix) a vulnerability. */
		remediations: Option[List[Schema.Remediation]] = None
	)
	
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1ArtifactsMavenArtifact(
	  /** Maven `groupId` value used when uploading the artifact to Artifact Registry. */
		groupId: Option[String] = None,
	  /** Path to an artifact in the build's workspace to be uploaded to Artifact Registry. This can be either an absolute path, e.g. /workspace/my-app/target/my-app-1.0.SNAPSHOT.jar or a relative path from /workspace, e.g. my-app/target/my-app-1.0.SNAPSHOT.jar. */
		path: Option[String] = None,
	  /** Maven `artifactId` value used when uploading the artifact to Artifact Registry. */
		artifactId: Option[String] = None,
	  /** Artifact Registry repository, in the form "https://$REGION-maven.pkg.dev/$PROJECT/$REPOSITORY" Artifact in the workspace specified by path will be uploaded to Artifact Registry with this location as a prefix. */
		repository: Option[String] = None,
	  /** Maven `version` value used when uploading the artifact to Artifact Registry. */
		version: Option[String] = None
	)
	
	case class SlsaProvenanceV1(
		runDetails: Option[Schema.RunDetails] = None,
		buildDefinition: Option[Schema.BuildDefinition] = None
	)
	
	case class RepoId(
	  /** A server-assigned, globally unique identifier. */
		uid: Option[String] = None,
	  /** A combination of a project ID and a repo name. */
		projectRepoId: Option[Schema.ProjectRepoId] = None
	)
	
	case class GrafeasV1SlsaProvenanceZeroTwoSlsaMetadata(
		reproducible: Option[Boolean] = None,
		buildFinishedOn: Option[String] = None,
		buildInvocationId: Option[String] = None,
		buildStartedOn: Option[String] = None,
		completeness: Option[Schema.GrafeasV1SlsaProvenanceZeroTwoSlsaCompleteness] = None
	)
	
	case class DSSEAttestationNote(
	  /** DSSEHint hints at the purpose of the attestation authority. */
		hint: Option[Schema.DSSEHint] = None
	)
	
	object Justification {
		enum JustificationTypeEnum extends Enum[JustificationTypeEnum] { case JUSTIFICATION_TYPE_UNSPECIFIED, COMPONENT_NOT_PRESENT, VULNERABLE_CODE_NOT_PRESENT, VULNERABLE_CODE_NOT_IN_EXECUTE_PATH, VULNERABLE_CODE_CANNOT_BE_CONTROLLED_BY_ADVERSARY, INLINE_MITIGATIONS_ALREADY_EXIST }
	}
	case class Justification(
	  /** Additional details on why this justification was chosen. */
		details: Option[String] = None,
	  /** The justification type for this vulnerability. */
		justificationType: Option[Schema.Justification.JustificationTypeEnum] = None
	)
	
	case class ResourceDescriptor(
		annotations: Option[Map[String, JsValue]] = None,
		digest: Option[Map[String, String]] = None,
		uri: Option[String] = None,
		name: Option[String] = None,
		mediaType: Option[String] = None,
		downloadLocation: Option[String] = None,
		content: Option[String] = None
	)
	
	case class ComplianceNote(
	  /** A description of remediation steps if the compliance check fails. */
		remediation: Option[String] = None,
	  /** A description about this compliance check. */
		description: Option[String] = None,
	  /** A rationale for the existence of this compliance check. */
		rationale: Option[String] = None,
	  /** Serialized scan instructions with a predefined format. */
		scanInstructions: Option[String] = None,
	  /** The OS and config versions the benchmark applies to. */
		version: Option[List[Schema.ComplianceVersion]] = None,
		cisBenchmark: Option[Schema.CisBenchmark] = None,
		impact: Option[String] = None,
	  /** The title that identifies this compliance check. */
		title: Option[String] = None
	)
	
	case class NonCompliantFile(
	  /** Empty if `display_command` is set. */
		path: Option[String] = None,
	  /** Explains why a file is non compliant for a CIS check. */
		reason: Option[String] = None,
	  /** Command to display the non-compliant files. */
		displayCommand: Option[String] = None
	)
	
	case class Command(
	  /** The ID(s) of the command(s) that this command depends on. */
		waitFor: Option[List[String]] = None,
	  /** Required. Name of the command, as presented on the command line, or if the command is packaged as a Docker container, as presented to `docker pull`. */
		name: Option[String] = None,
	  /** Environment variables set before running this command. */
		env: Option[List[String]] = None,
	  /** Working directory (relative to project source root) used when running this command. */
		dir: Option[String] = None,
	  /** Command-line arguments used when executing this command. */
		args: Option[List[String]] = None,
	  /** Optional unique identifier for this command, used in wait_for to reference this command as a dependency. */
		id: Option[String] = None
	)
	
	case class BatchCreateOccurrencesResponse(
	  /** The occurrences that were created. */
		occurrences: Option[List[Schema.Occurrence]] = None
	)
	
	case class EnvelopeSignature(
		sig: Option[String] = None,
		keyid: Option[String] = None
	)
	
	case class GitSourceContext(
	  /** Git repository URL. */
		url: Option[String] = None,
	  /** Git commit hash. */
		revisionId: Option[String] = None
	)
	
	case class Category(
	  /** The identifier of the category. */
		categoryId: Option[String] = None,
	  /** The localized name of the category. */
		name: Option[String] = None
	)
	
	case class License(
	  /** Comments */
		comments: Option[String] = None,
	  /** Often a single license can be used to represent the licensing terms. Sometimes it is necessary to include a choice of one or more licenses or some combination of license identifiers. Examples: "LGPL-2.1-only OR MIT", "LGPL-2.1-only AND MIT", "GPL-2.0-or-later WITH Bison-exception-2.2". */
		expression: Option[String] = None
	)
	
	case class AttestationOccurrence(
	  /** One or more signatures over `serialized_payload`. Verifier implementations should consider this attestation message verified if at least one `signature` verifies `serialized_payload`. See `Signature` in common.proto for more details on signature structure and verification. */
		signatures: Option[List[Schema.Signature]] = None,
	  /** One or more JWTs encoding a self-contained attestation. Each JWT encodes the payload that it verifies within the JWT itself. Verifier implementation SHOULD ignore the `serialized_payload` field when verifying these JWTs. If only JWTs are present on this AttestationOccurrence, then the `serialized_payload` SHOULD be left empty. Each JWT SHOULD encode a claim specific to the `resource_uri` of this Occurrence, but this is not validated by Grafeas metadata API implementations. The JWT itself is opaque to Grafeas. */
		jwts: Option[List[Schema.Jwt]] = None,
	  /** Required. The serialized payload that is verified by one or more `signatures`. */
		serializedPayload: Option[String] = None
	)
	
	case class Subject(
	  /** `"": ""` Algorithms can be e.g. sha256, sha512 See https://github.com/in-toto/attestation/blob/main/spec/field_types.md#DigestSet */
		digest: Option[Map[String, String]] = None,
		name: Option[String] = None
	)
	
	case class SetIamPolicyRequest(
	  /** REQUIRED: The complete policy to be applied to the `resource`. The size of the policy is limited to a few 10s of KB. An empty policy is a valid policy but certain Google Cloud services (such as Projects) might reject them. */
		policy: Option[Schema.Policy] = None
	)
	
	object DiscoveryNote {
		enum AnalysisKindEnum extends Enum[AnalysisKindEnum] { case NOTE_KIND_UNSPECIFIED, VULNERABILITY, BUILD, IMAGE, PACKAGE, DEPLOYMENT, DISCOVERY, ATTESTATION, UPGRADE, COMPLIANCE, DSSE_ATTESTATION, VULNERABILITY_ASSESSMENT, SBOM_REFERENCE }
	}
	case class DiscoveryNote(
	  /** Required. Immutable. The kind of analysis that is handled by this discovery. */
		analysisKind: Option[Schema.DiscoveryNote.AnalysisKindEnum] = None
	)
	
	object VulnerabilityOccurrence {
		enum SeverityEnum extends Enum[SeverityEnum] { case SEVERITY_UNSPECIFIED, MINIMAL, LOW, MEDIUM, HIGH, CRITICAL }
		enum CvssVersionEnum extends Enum[CvssVersionEnum] { case CVSS_VERSION_UNSPECIFIED, CVSS_VERSION_2, CVSS_VERSION_3 }
		enum EffectiveSeverityEnum extends Enum[EffectiveSeverityEnum] { case SEVERITY_UNSPECIFIED, MINIMAL, LOW, MEDIUM, HIGH, CRITICAL }
	}
	case class VulnerabilityOccurrence(
	  /** Output only. The note provider assigned severity of this vulnerability. */
		severity: Option[Schema.VulnerabilityOccurrence.SeverityEnum] = None,
		vexAssessment: Option[Schema.VexAssessment] = None,
	  /** Output only. CVSS version used to populate cvss_score and severity. */
		cvssVersion: Option[Schema.VulnerabilityOccurrence.CvssVersionEnum] = None,
	  /** Required. The set of affected locations and their fixes (if available) within the associated resource. */
		packageIssue: Option[List[Schema.PackageIssue]] = None,
	  /** The type of package; whether native or non native (e.g., ruby gems, node.js packages, etc.). */
		`type`: Option[String] = None,
	  /** Output only. A one sentence description of this vulnerability. */
		shortDescription: Option[String] = None,
	  /** Output only. URLs related to this vulnerability. */
		relatedUrls: Option[List[Schema.RelatedUrl]] = None,
	  /** The distro assigned severity for this vulnerability when it is available, otherwise this is the note provider assigned severity. When there are multiple PackageIssues for this vulnerability, they can have different effective severities because some might be provided by the distro while others are provided by the language ecosystem for a language pack. For this reason, it is advised to use the effective severity on the PackageIssue level. In the case where multiple PackageIssues have differing effective severities, this field should be the highest severity for any of the PackageIssues. */
		effectiveSeverity: Option[Schema.VulnerabilityOccurrence.EffectiveSeverityEnum] = None,
	  /** Output only. Whether at least one of the affected packages has a fix available. */
		fixAvailable: Option[Boolean] = None,
	  /** The cvss v2 score for the vulnerability. */
		cvssV2: Option[Schema.CVSS] = None,
	  /** Output only. The CVSS score of this vulnerability. CVSS score is on a scale of 0 - 10 where 0 indicates low severity and 10 indicates high severity. */
		cvssScore: Option[BigDecimal] = None,
	  /** Occurrence-specific extra details about the vulnerability. */
		extraDetails: Option[String] = None,
	  /** The cvss v3 score for the vulnerability. */
		cvssv3: Option[Schema.CVSS] = None,
	  /** Output only. A detailed description of this vulnerability. */
		longDescription: Option[String] = None
	)
	
	case class FileHashes(
	  /** Required. Collection of file hashes. */
		fileHash: Option[List[Schema.Hash]] = None
	)
	
	case class SourceContext(
	  /** A SourceContext referring to a revision in a Google Cloud Source Repo. */
		cloudRepo: Option[Schema.CloudRepoSourceContext] = None,
	  /** A SourceContext referring to any third party Git repo (e.g., GitHub). */
		git: Option[Schema.GitSourceContext] = None,
	  /** A SourceContext referring to a Gerrit project. */
		gerrit: Option[Schema.GerritSourceContext] = None,
	  /** Labels with user defined metadata. */
		labels: Option[Map[String, String]] = None
	)
	
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1Results(
	  /** Time to push all non-container artifacts to Cloud Storage. */
		artifactTiming: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1TimeSpan] = None,
	  /** Npm packages uploaded to Artifact Registry at the end of the build. */
		npmPackages: Option[List[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1UploadedNpmPackage]] = None,
	  /** Python artifacts uploaded to Artifact Registry at the end of the build. */
		pythonPackages: Option[List[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1UploadedPythonPackage]] = None,
	  /** Maven artifacts uploaded to Artifact Registry at the end of the build. */
		mavenArtifacts: Option[List[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1UploadedMavenArtifact]] = None,
	  /** Number of non-container artifacts uploaded to Cloud Storage. Only populated when artifacts are uploaded to Cloud Storage. */
		numArtifacts: Option[String] = None,
	  /** List of build step digests, in the order corresponding to build step indices. */
		buildStepImages: Option[List[String]] = None,
	  /** Container images that were built as a part of the build. */
		images: Option[List[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1BuiltImage]] = None,
	  /** List of build step outputs, produced by builder images, in the order corresponding to build step indices. [Cloud Builders](https://cloud.google.com/cloud-build/docs/cloud-builders) can produce this output by writing to `$BUILDER_OUTPUT/output`. Only the first 50KB of data is stored. Note that the `$BUILDER_OUTPUT` variable is read-only and can't be substituted. */
		buildStepOutputs: Option[List[String]] = None,
	  /** Path to the artifact manifest for non-container artifacts uploaded to Cloud Storage. Only populated when artifacts are uploaded to Cloud Storage. */
		artifactManifest: Option[String] = None
	)
	
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1Secrets(
	  /** Secrets in Secret Manager and associated secret environment variable. */
		secretManager: Option[List[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1SecretManagerSecret]] = None,
	  /** Secrets encrypted with KMS key and the associated secret environment variable. */
		inline: Option[List[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1InlineSecret]] = None
	)
	
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1UploadedPythonPackage(
	  /** Hash types and values of the Python Artifact. */
		fileHashes: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1FileHashes] = None,
	  /** Output only. Stores timing information for pushing the specified artifact. */
		pushTiming: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1TimeSpan] = None,
	  /** URI of the uploaded artifact. */
		uri: Option[String] = None
	)
	
	case class BuildNote(
	  /** Required. Immutable. Version of the builder which produced this build. */
		builderVersion: Option[String] = None
	)
	
	case class ExportSBOMRequest(
	  /** Empty placeholder to denote that this is a Google Cloud Storage export request. */
		cloudStorageLocation: Option[Schema.CloudStorageLocation] = None
	)
	
	case class SlsaProvenanceZeroTwo(
		invocation: Option[Schema.GrafeasV1SlsaProvenanceZeroTwoSlsaInvocation] = None,
		metadata: Option[Schema.GrafeasV1SlsaProvenanceZeroTwoSlsaMetadata] = None,
		builder: Option[Schema.GrafeasV1SlsaProvenanceZeroTwoSlsaBuilder] = None,
		buildType: Option[String] = None,
		buildConfig: Option[Map[String, JsValue]] = None,
		materials: Option[List[Schema.GrafeasV1SlsaProvenanceZeroTwoSlsaMaterial]] = None
	)
	
	object ContaineranalysisGoogleDevtoolsCloudbuildV1BuildStep {
		enum StatusEnum extends Enum[StatusEnum] { case STATUS_UNKNOWN, PENDING, QUEUED, WORKING, SUCCESS, FAILURE, INTERNAL_ERROR, TIMEOUT, CANCELLED, EXPIRED }
	}
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1BuildStep(
	  /** Working directory to use when running this step's container. If this value is a relative path, it is relative to the build's working directory. If this value is absolute, it may be outside the build's working directory, in which case the contents of the path may not be persisted across build step executions, unless a `volume` for that path is specified. If the build specifies a `RepoSource` with `dir` and a step with a `dir`, which specifies an absolute path, the `RepoSource` `dir` is ignored for the step's execution. */
		dir: Option[String] = None,
	  /** Output only. Stores timing information for executing this build step. */
		timing: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1TimeSpan] = None,
	  /** A shell script to be executed in the step. When script is provided, the user cannot specify the entrypoint or args. */
		script: Option[String] = None,
	  /** A list of environment variable definitions to be used when running a step. The elements are of the form "KEY=VALUE" for the environment variable "KEY" being given the value "VALUE". */
		env: Option[List[String]] = None,
	  /** Option to include built-in and custom substitutions as env variables for this build step. This option will override the global option in BuildOption. */
		automapSubstitutions: Option[Boolean] = None,
	  /** A list of environment variables which are encrypted using a Cloud Key Management Service crypto key. These values must be specified in the build's `Secret`. */
		secretEnv: Option[List[String]] = None,
	  /** Allow this build step to fail without failing the entire build. If false, the entire build will fail if this step fails. Otherwise, the build will succeed, but this step will still have a failure status. Error information will be reported in the failure_detail field. */
		allowFailure: Option[Boolean] = None,
	  /** Output only. Stores timing information for pulling this build step's builder image only. */
		pullTiming: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1TimeSpan] = None,
	  /** Output only. Return code from running the step. */
		exitCode: Option[Int] = None,
	  /** Entrypoint to be used instead of the build step image's default entrypoint. If unset, the image's default entrypoint is used. */
		entrypoint: Option[String] = None,
	  /** Required. The name of the container image that will run this particular build step. If the image is available in the host's Docker daemon's cache, it will be run directly. If not, the host will attempt to pull the image first, using the builder service account's credentials if necessary. The Docker daemon's cache will already have the latest versions of all of the officially supported build steps ([https://github.com/GoogleCloudPlatform/cloud-builders](https://github.com/GoogleCloudPlatform/cloud-builders)). The Docker daemon will also have cached many of the layers for some popular images, like "ubuntu", "debian", but they will be refreshed at the time you attempt to use them. If you built an image in a previous build step, it will be stored in the host's Docker daemon's cache and is available to use as the name for a later build step. */
		name: Option[String] = None,
	  /** The ID(s) of the step(s) that this build step depends on. This build step will not start until all the build steps in `wait_for` have completed successfully. If `wait_for` is empty, this build step will start when all previous build steps in the `Build.Steps` list have completed successfully. */
		waitFor: Option[List[String]] = None,
	  /** Output only. Status of the build step. At this time, build step status is only updated on build completion; step status is not updated in real-time as the build progresses. */
		status: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1BuildStep.StatusEnum] = None,
	  /** Allow this build step to fail without failing the entire build if and only if the exit code is one of the specified codes. If allow_failure is also specified, this field will take precedence. */
		allowExitCodes: Option[List[Int]] = None,
	  /** Time limit for executing this build step. If not defined, the step has no time limit and will be allowed to continue to run until either it completes or the build itself times out. */
		timeout: Option[String] = None,
	  /** A list of arguments that will be presented to the step when it is started. If the image used to run the step's container has an entrypoint, the `args` are used as arguments to that entrypoint. If the image does not define an entrypoint, the first element in args is used as the entrypoint, and the remainder will be used as arguments. */
		args: Option[List[String]] = None,
	  /** List of volumes to mount into the build step. Each volume is created as an empty volume prior to execution of the build step. Upon completion of the build, volumes and their contents are discarded. Using a named volume in only one step is not valid as it is indicative of a build request with an incorrect configuration. */
		volumes: Option[List[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1Volume]] = None,
	  /** Unique identifier for this build step, used in `wait_for` to reference this build step as a dependency. */
		id: Option[String] = None
	)
	
	object ContaineranalysisGoogleDevtoolsCloudbuildV1ApprovalResult {
		enum DecisionEnum extends Enum[DecisionEnum] { case DECISION_UNSPECIFIED, APPROVED, REJECTED }
	}
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1ApprovalResult(
	  /** Required. The decision of this manual approval. */
		decision: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1ApprovalResult.DecisionEnum] = None,
	  /** Output only. The time when the approval decision was made. */
		approvalTime: Option[String] = None,
	  /** Optional. An optional comment for this manual approval result. */
		comment: Option[String] = None,
	  /** Output only. Email of the user that called the ApproveBuild API to approve or reject a build at the time that the API was called. */
		approverAccount: Option[String] = None,
	  /** Optional. An optional URL tied to this manual approval result. This field is essentially the same as comment, except that it will be rendered by the UI differently. An example use case is a link to an external job that approved this Build. */
		url: Option[String] = None
	)
	
	case class Artifact(
	  /** Artifact ID, if any; for container images, this will be a URL by digest like `gcr.io/projectID/imagename@sha256:123456`. */
		id: Option[String] = None,
	  /** Related artifact names. This may be the path to a binary or jar file, or in the case of a container build, the name used to push the container image to Google Container Registry, as presented to `docker push`. Note that a single Artifact ID can have multiple names, for example if two tags are applied to one image. */
		names: Option[List[String]] = None,
	  /** Hash or checksum value of a binary, or Docker Registry 2.0 digest of a container. */
		checksum: Option[String] = None
	)
	
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1Source(
	  /** If provided, get the source from this location in a Cloud Source Repository. */
		repoSource: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1RepoSource] = None,
	  /** Optional. If provided, get the source from this 2nd-gen Google Cloud Build repository resource. */
		connectedRepository: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1ConnectedRepository] = None,
	  /** If provided, get the source from this Developer Connect config. */
		developerConnectConfig: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1DeveloperConnectConfig] = None,
	  /** If provided, get the source from this Git repository. */
		gitSource: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1GitSource] = None,
	  /** If provided, get the source from this manifest in Cloud Storage. This feature is in Preview; see description [here](https://github.com/GoogleCloudPlatform/cloud-builders/tree/master/gcs-fetcher). */
		storageSourceManifest: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1StorageSourceManifest] = None,
	  /** If provided, get the source from this location in Cloud Storage. */
		storageSource: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1StorageSource] = None
	)
	
	case class Binding(
	  /** The condition that is associated with this binding. If the condition evaluates to `true`, then this binding applies to the current request. If the condition evaluates to `false`, then this binding does not apply to the current request. However, a different role binding might grant the same role to one or more of the principals in this binding. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		condition: Option[Schema.Expr] = None,
	  /** Role that is assigned to the list of `members`, or principals. For example, `roles/viewer`, `roles/editor`, or `roles/owner`. For an overview of the IAM roles and permissions, see the [IAM documentation](https://cloud.google.com/iam/docs/roles-overview). For a list of the available pre-defined roles, see [here](https://cloud.google.com/iam/docs/understanding-roles). */
		role: Option[String] = None,
	  /** Specifies the principals requesting access for a Google Cloud resource. `members` can have the following values: &#42; `allUsers`: A special identifier that represents anyone who is on the internet; with or without a Google account. &#42; `allAuthenticatedUsers`: A special identifier that represents anyone who is authenticated with a Google account or a service account. Does not include identities that come from external identity providers (IdPs) through identity federation. &#42; `user:{emailid}`: An email address that represents a specific Google account. For example, `alice@example.com` . &#42; `serviceAccount:{emailid}`: An email address that represents a Google service account. For example, `my-other-app@appspot.gserviceaccount.com`. &#42; `serviceAccount:{projectid}.svc.id.goog[{namespace}/{kubernetes-sa}]`: An identifier for a [Kubernetes service account](https://cloud.google.com/kubernetes-engine/docs/how-to/kubernetes-service-accounts). For example, `my-project.svc.id.goog[my-namespace/my-kubernetes-sa]`. &#42; `group:{emailid}`: An email address that represents a Google group. For example, `admins@example.com`. &#42; `domain:{domain}`: The G Suite domain (primary) that represents all the users of that domain. For example, `google.com` or `example.com`. &#42; `principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workforce identity pool. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/group/{group_id}`: All workforce identities in a group. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All workforce identities with a specific attribute value. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/&#42;`: All identities in a workforce identity pool. &#42; `principal://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workload identity pool. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/group/{group_id}`: A workload identity pool group. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All identities in a workload identity pool with a certain attribute. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/&#42;`: All identities in a workload identity pool. &#42; `deleted:user:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a user that has been recently deleted. For example, `alice@example.com?uid=123456789012345678901`. If the user is recovered, this value reverts to `user:{emailid}` and the recovered user retains the role in the binding. &#42; `deleted:serviceAccount:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a service account that has been recently deleted. For example, `my-other-app@appspot.gserviceaccount.com?uid=123456789012345678901`. If the service account is undeleted, this value reverts to `serviceAccount:{emailid}` and the undeleted service account retains the role in the binding. &#42; `deleted:group:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a Google group that has been recently deleted. For example, `admins@example.com?uid=123456789012345678901`. If the group is recovered, this value reverts to `group:{emailid}` and the recovered group retains the role in the binding. &#42; `deleted:principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: Deleted single identity in a workforce identity pool. For example, `deleted:principal://iam.googleapis.com/locations/global/workforcePools/my-pool-id/subject/my-subject-attribute-value`. */
		members: Option[List[String]] = None
	)
	
	case class AnalysisCompleted(
		analysisType: Option[List[String]] = None
	)
	
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1RepoSource(
	  /** Optional. Substitutions to use in a triggered build. Should only be used with RunBuildTrigger */
		substitutions: Option[Map[String, String]] = None,
	  /** Regex matching tags to build. The syntax of the regular expressions accepted is the syntax accepted by RE2 and described at https://github.com/google/re2/wiki/Syntax */
		tagName: Option[String] = None,
	  /** Explicit commit SHA to build. */
		commitSha: Option[String] = None,
	  /** Optional. ID of the project that owns the Cloud Source Repository. If omitted, the project ID requesting the build is assumed. */
		projectId: Option[String] = None,
	  /** Optional. Directory, relative to the source root, in which to run the build. This must be a relative path. If a step's `dir` is specified and is an absolute path, this value is ignored for that step's execution. */
		dir: Option[String] = None,
	  /** Required. Name of the Cloud Source Repository. */
		repoName: Option[String] = None,
	  /** Optional. Only trigger a build if the revision regex does NOT match the revision regex. */
		invertRegex: Option[Boolean] = None,
	  /** Regex matching branches to build. The syntax of the regular expressions accepted is the syntax accepted by RE2 and described at https://github.com/google/re2/wiki/Syntax */
		branchName: Option[String] = None
	)
	
	case class SlsaProvenance(
	  /** The collection of artifacts that influenced the build including sources, dependencies, build tools, base images, and so on. This is considered to be incomplete unless metadata.completeness.materials is true. Unset or null is equivalent to empty. */
		materials: Option[List[Schema.Material]] = None,
		metadata: Option[Schema.SlsaMetadata] = None,
	  /** required */
		builder: Option[Schema.SlsaBuilder] = None,
	  /** Identifies the configuration used for the build. When combined with materials, this SHOULD fully describe the build, such that re-running this recipe results in bit-for-bit identical output (if the build is reproducible). required */
		recipe: Option[Schema.SlsaRecipe] = None
	)
	
	case class ComplianceOccurrence(
	  /** The OS and config version the benchmark was run on. */
		version: Option[Schema.ComplianceVersion] = None,
		nonCompliantFiles: Option[List[Schema.NonCompliantFile]] = None,
		nonComplianceReason: Option[String] = None
	)
	
	case class GetIamPolicyRequest(
	  /** OPTIONAL: A `GetPolicyOptions` object for specifying options to `GetIamPolicy`. */
		options: Option[Schema.GetPolicyOptions] = None
	)
	
	case class BatchCreateOccurrencesRequest(
	  /** Required. The occurrences to create. Max allowed length is 1000. */
		occurrences: Option[List[Schema.Occurrence]] = None
	)
	
	case class SlsaMetadata(
	  /** Identifies the particular build invocation, which can be useful for finding associated logs or other ad-hoc analysis. The value SHOULD be globally unique, per in-toto Provenance spec. */
		buildInvocationId: Option[String] = None,
	  /** Indicates that the builder claims certain fields in this message to be complete. */
		completeness: Option[Schema.SlsaCompleteness] = None,
	  /** The timestamp of when the build completed. */
		buildFinishedOn: Option[String] = None,
	  /** The timestamp of when the build started. */
		buildStartedOn: Option[String] = None,
	  /** If true, the builder claims that running the recipe on materials will produce bit-for-bit identical output. */
		reproducible: Option[Boolean] = None
	)
	
	case class BuildMetadata(
		startedOn: Option[String] = None,
		invocationId: Option[String] = None,
		finishedOn: Option[String] = None
	)
	
	object PackageNote {
		enum ArchitectureEnum extends Enum[ArchitectureEnum] { case ARCHITECTURE_UNSPECIFIED, X86, X64 }
	}
	case class PackageNote(
	  /** The homepage for this package. */
		url: Option[String] = None,
	  /** Deprecated. The various channels by which a package is distributed. */
		distribution: Option[List[Schema.Distribution]] = None,
	  /** Hash value, typically a file digest, that allows unique identification a specific package. */
		digest: Option[List[Schema.Digest]] = None,
	  /** The description of this package. */
		description: Option[String] = None,
	  /** A freeform text denoting the maintainer of this package. */
		maintainer: Option[String] = None,
	  /** The CPU architecture for which packages in this distribution channel were built. Architecture will be blank for language packages. */
		architecture: Option[Schema.PackageNote.ArchitectureEnum] = None,
	  /** The version of the package. */
		version: Option[Schema.Version] = None,
	  /** Required. Immutable. The name of the package. */
		name: Option[String] = None,
	  /** The type of package; whether native or non native (e.g., ruby gems, node.js packages, etc.). */
		packageType: Option[String] = None,
	  /** The cpe_uri in [CPE format](https://cpe.mitre.org/specification/) denoting the package manager version distributing a package. The cpe_uri will be blank for language packages. */
		cpeUri: Option[String] = None,
	  /** Licenses that have been declared by the authors of the package. */
		license: Option[Schema.License] = None
	)
	
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1BuiltImage(
	  /** Docker Registry 2.0 digest. */
		digest: Option[String] = None,
	  /** Name used to push the container image to Google Container Registry, as presented to `docker push`. */
		name: Option[String] = None,
	  /** Output only. Stores timing information for pushing the specified image. */
		pushTiming: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1TimeSpan] = None
	)
	
	case class WindowsDetail(
	  /** Required. The name of this vulnerability. */
		name: Option[String] = None,
	  /** The description of this vulnerability. */
		description: Option[String] = None,
	  /** Required. The names of the KBs which have hotfixes to mitigate this vulnerability. Note that there may be multiple hotfixes (and thus multiple KBs) that mitigate a given vulnerability. Currently any listed KBs presence is considered a fix. */
		fixingKbs: Option[List[Schema.KnowledgeBase]] = None,
	  /** Required. The [CPE URI](https://cpe.mitre.org/specification/) this vulnerability affects. */
		cpeUri: Option[String] = None
	)
	
	case class InTotoSlsaProvenanceV1(
		subject: Option[List[Schema.Subject]] = None,
	  /** InToto spec defined at https://github.com/in-toto/attestation/tree/main/spec#statement */
		_type: Option[String] = None,
		predicateType: Option[String] = None,
		predicate: Option[Schema.SlsaProvenanceV1] = None
	)
	
	case class Layer(
	  /** Required. The recovered Dockerfile directive used to construct this layer. See https://docs.docker.com/engine/reference/builder/ for more information. */
		directive: Option[String] = None,
	  /** The recovered arguments to the Dockerfile directive. */
		arguments: Option[String] = None
	)
	
	object ContaineranalysisGoogleDevtoolsCloudbuildV1BuildWarning {
		enum PriorityEnum extends Enum[PriorityEnum] { case PRIORITY_UNSPECIFIED, INFO, WARNING, ALERT }
	}
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1BuildWarning(
	  /** Explanation of the warning generated. */
		text: Option[String] = None,
	  /** The priority for this warning. */
		priority: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1BuildWarning.PriorityEnum] = None
	)
	
	case class TestIamPermissionsResponse(
	  /** A subset of `TestPermissionsRequest.permissions` that the caller is allowed. */
		permissions: Option[List[String]] = None
	)
	
	case class Hint(
	  /** Required. The human readable name of this attestation authority, for example "qa". */
		humanReadableName: Option[String] = None
	)
	
	case class Source(
	  /** Hash(es) of the build source, which can be used to verify that the original source integrity was maintained in the build. The keys to this map are file paths used as build source and the values contain the hash values for those files. If the build source came in a single package such as a gzipped tarfile (.tar.gz), the FileHash will be for the single path to that file. */
		fileHashes: Option[Map[String, Schema.FileHashes]] = None,
	  /** If provided, the input binary artifacts for the build came from this location. */
		artifactStorageSourceUri: Option[String] = None,
	  /** If provided, some of the source code used for the build may be found in these locations, in the case where the source repository had multiple remotes or submodules. This list will not include the context specified in the context field. */
		additionalContexts: Option[List[Schema.SourceContext]] = None,
	  /** If provided, the source code used for the build came from this location. */
		context: Option[Schema.SourceContext] = None
	)
	
	case class InTotoProvenance(
	  /** The collection of artifacts that influenced the build including sources, dependencies, build tools, base images, and so on. This is considered to be incomplete unless metadata.completeness.materials is true. Unset or null is equivalent to empty. */
		materials: Option[List[String]] = None,
		metadata: Option[Schema.Metadata] = None,
	  /** required */
		builderConfig: Option[Schema.BuilderConfig] = None,
	  /** Identifies the configuration used for the build. When combined with materials, this SHOULD fully describe the build, such that re-running this recipe results in bit-for-bit identical output (if the build is reproducible). required */
		recipe: Option[Schema.Recipe] = None
	)
	
	case class Location(
	  /** The path from which we gathered that this package/version is installed. */
		path: Option[String] = None,
	  /** Deprecated. The version installed at this location. */
		version: Option[Schema.Version] = None,
	  /** Deprecated. The CPE URI in [CPE format](https://cpe.mitre.org/specification/) */
		cpeUri: Option[String] = None
	)
	
	object Note {
		enum KindEnum extends Enum[KindEnum] { case NOTE_KIND_UNSPECIFIED, VULNERABILITY, BUILD, IMAGE, PACKAGE, DEPLOYMENT, DISCOVERY, ATTESTATION, UPGRADE, COMPLIANCE, DSSE_ATTESTATION, VULNERABILITY_ASSESSMENT, SBOM_REFERENCE }
	}
	case class Note(
	  /** A note describing a base image. */
		image: Option[Schema.ImageNote] = None,
	  /** A note describing a vulnerability assessment. */
		vulnerabilityAssessment: Option[Schema.VulnerabilityAssessmentNote] = None,
	  /** Output only. The name of the note in the form of `projects/[PROVIDER_ID]/notes/[NOTE_ID]`. */
		name: Option[String] = None,
	  /** A note describing a compliance check. */
		compliance: Option[Schema.ComplianceNote] = None,
	  /** A note describing an attestation role. */
		attestation: Option[Schema.AttestationNote] = None,
	  /** Time of expiration for this note. Empty if note does not expire. */
		expirationTime: Option[String] = None,
	  /** Output only. The time this note was created. This field can be used as a filter in list requests. */
		createTime: Option[String] = None,
	  /** A note describing available package upgrades. */
		upgrade: Option[Schema.UpgradeNote] = None,
	  /** A note describing an SBOM reference. */
		sbomReference: Option[Schema.SBOMReferenceNote] = None,
	  /** URLs associated with this note. */
		relatedUrl: Option[List[Schema.RelatedUrl]] = None,
	  /** Output only. The type of analysis. This field can be used as a filter in list requests. */
		kind: Option[Schema.Note.KindEnum] = None,
	  /** A note describing a package vulnerability. */
		vulnerability: Option[Schema.VulnerabilityNote] = None,
	  /** A note describing the initial analysis of a resource. */
		discovery: Option[Schema.DiscoveryNote] = None,
	  /** Other notes related to this note. */
		relatedNoteNames: Option[List[String]] = None,
	  /** Output only. The time this note was last updated. This field can be used as a filter in list requests. */
		updateTime: Option[String] = None,
	  /** A detailed description of this note. */
		longDescription: Option[String] = None,
	  /** A note describing something that can be deployed. */
		deployment: Option[Schema.DeploymentNote] = None,
	  /** A note describing build provenance for a verifiable build. */
		build: Option[Schema.BuildNote] = None,
	  /** A note describing a package hosted by various package managers. */
		`package`: Option[Schema.PackageNote] = None,
	  /** A one sentence description of this note. */
		shortDescription: Option[String] = None,
	  /** A note describing a dsse attestation note. */
		dsseAttestation: Option[Schema.DSSEAttestationNote] = None
	)
	
	case class DSSEHint(
	  /** Required. The human readable name of this attestation authority, for example "cloudbuild-prod". */
		humanReadableName: Option[String] = None
	)
	
	case class BuilderConfig(
		id: Option[String] = None
	)
	
	case class ListOccurrencesResponse(
	  /** The occurrences requested. */
		occurrences: Option[List[Schema.Occurrence]] = None,
	  /** The next pagination token in the list response. It should be used as `page_token` for the following request. An empty value means no more results. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleDevtoolsContaineranalysisV1alpha1OperationMetadata(
	  /** Output only. The time that this operation was marked completed or failed. */
		endTime: Option[String] = None,
	  /** Output only. The time this operation was created. */
		createTime: Option[String] = None
	)
	
	case class Status(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class Hash(
	  /** Required. The hash value. */
		value: Option[String] = None,
	  /** Required. The type of hash that was performed, e.g. "SHA-256". */
		`type`: Option[String] = None
	)
	
	case class SlsaRecipe(
	  /** Collection of all external inputs that influenced the build on top of recipe.definedInMaterial and recipe.entryPoint. For example, if the recipe type were "make", then this might be the flags passed to make aside from the target, which is captured in recipe.entryPoint. Depending on the recipe Type, the structure may be different. */
		arguments: Option[Map[String, JsValue]] = None,
	  /** String identifying the entry point into the build. This is often a path to a configuration file and/or a target label within that file. The syntax and meaning are defined by recipe.type. For example, if the recipe type were "make", then this would reference the directory in which to run make as well as which target to use. */
		entryPoint: Option[String] = None,
	  /** Index in materials containing the recipe steps that are not implied by recipe.type. For example, if the recipe type were "make", then this would point to the source containing the Makefile, not the make program itself. Set to -1 if the recipe doesn't come from a material, as zero is default unset value for int64. */
		definedInMaterial: Option[String] = None,
	  /** URI indicating what type of recipe was performed. It determines the meaning of recipe.entryPoint, recipe.arguments, recipe.environment, and materials. */
		`type`: Option[String] = None,
	  /** Any other builder-controlled inputs necessary for correctly evaluating the recipe. Usually only needed for reproducing the build but not evaluated as part of policy. Depending on the recipe Type, the structure may be different. */
		environment: Option[Map[String, JsValue]] = None
	)
	
	case class BuildProvenance(
	  /** Required. Unique identifier of the build. */
		id: Option[String] = None,
	  /** Time at which execution of the build was finished. */
		endTime: Option[String] = None,
	  /** URI where any logs for this provenance were written. */
		logsUri: Option[String] = None,
	  /** Commands requested by the build. */
		commands: Option[List[Schema.Command]] = None,
	  /** E-mail address of the user who initiated this build. Note that this was the user's e-mail address at the time the build was initiated; this address may not represent the same end-user for all time. */
		creator: Option[String] = None,
	  /** Details of the Source input to the build. */
		sourceProvenance: Option[Schema.Source] = None,
	  /** Time at which the build was created. */
		createTime: Option[String] = None,
	  /** Version string of the builder at the time this build was executed. */
		builderVersion: Option[String] = None,
	  /** Trigger identifier if the build was triggered automatically; empty if not. */
		triggerId: Option[String] = None,
	  /** Special options applied to this build. This is a catch-all field where build providers can enter any desired additional details. */
		buildOptions: Option[Map[String, String]] = None,
	  /** Output of the build. */
		builtArtifacts: Option[List[Schema.Artifact]] = None,
	  /** Time at which execution of the build was started. */
		startTime: Option[String] = None,
	  /** ID of the project. */
		projectId: Option[String] = None
	)
	
	case class Metadata(
	  /** The timestamp of when the build completed. */
		buildFinishedOn: Option[String] = None,
	  /** The timestamp of when the build started. */
		buildStartedOn: Option[String] = None,
	  /** Identifies the particular build invocation, which can be useful for finding associated logs or other ad-hoc analysis. The value SHOULD be globally unique, per in-toto Provenance spec. */
		buildInvocationId: Option[String] = None,
	  /** Indicates that the builder claims certain fields in this message to be complete. */
		completeness: Option[Schema.Completeness] = None,
	  /** If true, the builder claims that running the recipe on materials will produce bit-for-bit identical output. */
		reproducible: Option[Boolean] = None
	)
	
	case class ProjectRepoId(
	  /** The name of the repo. Leave empty for the default repo. */
		repoName: Option[String] = None,
	  /** The ID of the project. */
		projectId: Option[String] = None
	)
	
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1ArtifactsArtifactObjects(
	  /** Output only. Stores timing information for pushing all artifact objects. */
		timing: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1TimeSpan] = None,
	  /** Path globs used to match files in the build's workspace. */
		paths: Option[List[String]] = None,
	  /** Cloud Storage bucket and optional object path, in the form "gs://bucket/path/to/somewhere/". (see [Bucket Name Requirements](https://cloud.google.com/storage/docs/bucket-naming#requirements)). Files in the workspace matching any path pattern will be uploaded to Cloud Storage with this location as a prefix. */
		location: Option[String] = None
	)
	
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1TimeSpan(
	  /** End of time span. */
		endTime: Option[String] = None,
	  /** Start of time span. */
		startTime: Option[String] = None
	)
	
	case class DSSEAttestationOccurrence(
		statement: Option[Schema.InTotoStatement] = None,
	  /** If doing something security critical, make sure to verify the signatures in this metadata. */
		envelope: Option[Schema.Envelope] = None
	)
	
	object FixableTotalByDigest {
		enum SeverityEnum extends Enum[SeverityEnum] { case SEVERITY_UNSPECIFIED, MINIMAL, LOW, MEDIUM, HIGH, CRITICAL }
	}
	case class FixableTotalByDigest(
	  /** The affected resource. */
		resourceUri: Option[String] = None,
	  /** The severity for this count. SEVERITY_UNSPECIFIED indicates total across all severities. */
		severity: Option[Schema.FixableTotalByDigest.SeverityEnum] = None,
	  /** The number of fixable vulnerabilities associated with this resource. */
		fixableCount: Option[String] = None,
	  /** The total number of vulnerabilities associated with this resource. */
		totalCount: Option[String] = None
	)
	
	case class BatchCreateNotesRequest(
	  /** Required. The notes to create. Max allowed length is 1000. */
		notes: Option[Map[String, Schema.Note]] = None
	)
	
	case class UpgradeOccurrence(
	  /** Required for Windows OS. Represents the metadata about the Windows update. */
		windowsUpdate: Option[Schema.WindowsUpdate] = None,
	  /** Metadata about the upgrade for available for the specific operating system for the resource_url. This allows efficient filtering, as well as making it easier to use the occurrence. */
		distribution: Option[Schema.UpgradeDistribution] = None,
	  /** Required for non-Windows OS. The package this Upgrade is for. */
		`package`: Option[String] = None,
	  /** Required for non-Windows OS. The version of the package in a machine + human readable form. */
		parsedVersion: Option[Schema.Version] = None
	)
	
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1GitSource(
	  /** Optional. The revision to fetch from the Git repository such as a branch, a tag, a commit SHA, or any Git ref. Cloud Build uses `git fetch` to fetch the revision from the Git repository; therefore make sure that the string you provide for `revision` is parsable by the command. For information on string values accepted by `git fetch`, see https://git-scm.com/docs/gitrevisions#_specifying_revisions. For information on `git fetch`, see https://git-scm.com/docs/git-fetch. */
		revision: Option[String] = None,
	  /** Required. Location of the Git repo to build. This will be used as a `git remote`, see https://git-scm.com/docs/git-remote. */
		url: Option[String] = None,
	  /** Optional. Directory, relative to the source root, in which to run the build. This must be a relative path. If a step's `dir` is specified and is an absolute path, this value is ignored for that step's execution. */
		dir: Option[String] = None
	)
	
	case class Recipe(
	  /** Any other builder-controlled inputs necessary for correctly evaluating the recipe. Usually only needed for reproducing the build but not evaluated as part of policy. Since the environment field can greatly vary in structure, depending on the builder and recipe type, this is of form "Any". */
		environment: Option[List[Map[String, JsValue]]] = None,
	  /** Collection of all external inputs that influenced the build on top of recipe.definedInMaterial and recipe.entryPoint. For example, if the recipe type were "make", then this might be the flags passed to make aside from the target, which is captured in recipe.entryPoint. Since the arguments field can greatly vary in structure, depending on the builder and recipe type, this is of form "Any". */
		arguments: Option[List[Map[String, JsValue]]] = None,
	  /** String identifying the entry point into the build. This is often a path to a configuration file and/or a target label within that file. The syntax and meaning are defined by recipe.type. For example, if the recipe type were "make", then this would reference the directory in which to run make as well as which target to use. */
		entryPoint: Option[String] = None,
	  /** Index in materials containing the recipe steps that are not implied by recipe.type. For example, if the recipe type were "make", then this would point to the source containing the Makefile, not the make program itself. Set to -1 if the recipe doesn't come from a material, as zero is default unset value for int64. */
		definedInMaterial: Option[String] = None,
	  /** URI indicating what type of recipe was performed. It determines the meaning of recipe.entryPoint, recipe.arguments, recipe.environment, and materials. */
		`type`: Option[String] = None
	)
	
	case class Empty(
	
	)
	
	object PackageIssue {
		enum EffectiveSeverityEnum extends Enum[EffectiveSeverityEnum] { case SEVERITY_UNSPECIFIED, MINIMAL, LOW, MEDIUM, HIGH, CRITICAL }
	}
	case class PackageIssue(
	  /** The package this vulnerability was fixed in. It is possible for this to be different from the affected_package. */
		fixedPackage: Option[String] = None,
	  /** Required. The version of the package this vulnerability was fixed in. Setting this to VersionKind.MAXIMUM means no fix is yet available. */
		fixedVersion: Option[Schema.Version] = None,
	  /** Required. The package this vulnerability was found in. */
		affectedPackage: Option[String] = None,
	  /** The [CPE URI](https://cpe.mitre.org/specification/) this vulnerability was fixed in. It is possible for this to be different from the affected_cpe_uri. */
		fixedCpeUri: Option[String] = None,
	  /** The location at which this package was found. */
		fileLocation: Option[List[Schema.GrafeasV1FileLocation]] = None,
	  /** The type of package (e.g. OS, MAVEN, GO). */
		packageType: Option[String] = None,
	  /** Required. The version of the package that is installed on the resource affected by this vulnerability. */
		affectedVersion: Option[Schema.Version] = None,
	  /** Required. The [CPE URI](https://cpe.mitre.org/specification/) this vulnerability was found in. */
		affectedCpeUri: Option[String] = None,
	  /** Output only. The distro or language system assigned severity for this vulnerability when that is available and note provider assigned severity when it is not available. */
		effectiveSeverity: Option[Schema.PackageIssue.EffectiveSeverityEnum] = None,
	  /** Output only. Whether a fix is available for this package. */
		fixAvailable: Option[Boolean] = None
	)
	
	case class VulnerabilityAssessmentNote(
	  /** A detailed description of this Vex. */
		longDescription: Option[String] = None,
	  /** Publisher details of this Note. */
		publisher: Option[Schema.Publisher] = None,
	  /** Identifies the language used by this document, corresponding to IETF BCP 47 / RFC 5646. */
		languageCode: Option[String] = None,
	  /** Represents a vulnerability assessment for the product. */
		assessment: Option[Schema.Assessment] = None,
	  /** A one sentence description of this Vex. */
		shortDescription: Option[String] = None,
	  /** The product affected by this vex. */
		product: Option[Schema.Product] = None,
	  /** The title of the note. E.g. `Vex-Debian-11.4` */
		title: Option[String] = None
	)
	
	case class UpgradeDistribution(
	  /** Required - The specific operating system this metadata applies to. See https://cpe.mitre.org/specification/. */
		cpeUri: Option[String] = None,
	  /** The severity as specified by the upstream operating system. */
		severity: Option[String] = None,
	  /** The operating system classification of this Upgrade, as specified by the upstream operating system upgrade feed. For Windows the classification is one of the category_ids listed at https://docs.microsoft.com/en-us/previous-versions/windows/desktop/ff357803(v=vs.85) */
		classification: Option[String] = None,
	  /** The cve tied to this Upgrade. */
		cve: Option[List[String]] = None
	)
	
	case class Fingerprint(
	  /** Required. The layer ID of the final layer in the Docker image's v1 representation. */
		v1Name: Option[String] = None,
	  /** Required. The ordered list of v2 blobs that represent a given image. */
		v2Blob: Option[List[String]] = None,
	  /** Output only. The name of the image's v2 blobs computed via: [bottom] := v2_blobbottom := sha256(v2_blob[N] + " " + v2_name[N+1]) Only the name of the final blob is kept. */
		v2Name: Option[String] = None
	)
	
	case class Product(
	  /** Token that identifies a product so that it can be referred to from other parts in the document. There is no predefined format as long as it uniquely identifies a group in the context of the current document. */
		id: Option[String] = None,
	  /** Name of the product. */
		name: Option[String] = None,
	  /** Contains a URI which is vendor-specific. Example: The artifact repository URL of an image. */
		genericUri: Option[String] = None
	)
	
	case class SlsaCompleteness(
	  /** If true, the builder claims that recipe.arguments is complete, meaning that all external inputs are properly captured in the recipe. */
		arguments: Option[Boolean] = None,
	  /** If true, the builder claims that recipe.environment is claimed to be complete. */
		environment: Option[Boolean] = None,
	  /** If true, the builder claims that materials are complete, usually through some controls to prevent network access. Sometimes called "hermetic". */
		materials: Option[Boolean] = None
	)
	
	object VulnerabilityNote {
		enum CvssVersionEnum extends Enum[CvssVersionEnum] { case CVSS_VERSION_UNSPECIFIED, CVSS_VERSION_2, CVSS_VERSION_3 }
		enum SeverityEnum extends Enum[SeverityEnum] { case SEVERITY_UNSPECIFIED, MINIMAL, LOW, MEDIUM, HIGH, CRITICAL }
	}
	case class VulnerabilityNote(
	  /** The time this information was last changed at the source. This is an upstream timestamp from the underlying information source - e.g. Ubuntu security tracker. */
		sourceUpdateTime: Option[String] = None,
	  /** Details of all known distros and packages affected by this vulnerability. */
		details: Option[List[Schema.Detail]] = None,
	  /** The CVSS score of this vulnerability. CVSS score is on a scale of 0 - 10 where 0 indicates low severity and 10 indicates high severity. */
		cvssScore: Option[BigDecimal] = None,
	  /** CVSS version used to populate cvss_score and severity. */
		cvssVersion: Option[Schema.VulnerabilityNote.CvssVersionEnum] = None,
	  /** The note provider assigned severity of this vulnerability. */
		severity: Option[Schema.VulnerabilityNote.SeverityEnum] = None,
	  /** The full description of the CVSSv3 for this vulnerability. */
		cvssV3: Option[Schema.CVSSv3] = None,
	  /** Windows details get their own format because the information format and model don't match a normal detail. Specifically Windows updates are done as patches, thus Windows vulnerabilities really are a missing package, rather than a package being at an incorrect version. */
		windowsDetails: Option[List[Schema.WindowsDetail]] = None,
	  /** The full description of the v2 CVSS for this vulnerability. */
		cvssV2: Option[Schema.CVSS] = None
	)
	
	case class GetPolicyOptions(
	  /** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		requestedPolicyVersion: Option[Int] = None
	)
	
	object Occurrence {
		enum KindEnum extends Enum[KindEnum] { case NOTE_KIND_UNSPECIFIED, VULNERABILITY, BUILD, IMAGE, PACKAGE, DEPLOYMENT, DISCOVERY, ATTESTATION, UPGRADE, COMPLIANCE, DSSE_ATTESTATION, VULNERABILITY_ASSESSMENT, SBOM_REFERENCE }
	}
	case class Occurrence(
	  /** Describes when a resource was discovered. */
		discovery: Option[Schema.DiscoveryOccurrence] = None,
	  /** Describes an available package upgrade on the linked resource. */
		upgrade: Option[Schema.UpgradeOccurrence] = None,
	  /** Required. Immutable. A URI that represents the resource for which the occurrence applies. For example, `https://gcr.io/project/image@sha256:123abc` for a Docker image. */
		resourceUri: Option[String] = None,
	  /** Describes an attestation of an artifact. */
		attestation: Option[Schema.AttestationOccurrence] = None,
	  /** Describes a compliance violation on a linked resource. */
		compliance: Option[Schema.ComplianceOccurrence] = None,
	  /** Required. Immutable. The analysis note associated with this occurrence, in the form of `projects/[PROVIDER_ID]/notes/[NOTE_ID]`. This field can be used as a filter in list requests. */
		noteName: Option[String] = None,
	  /** Describes the deployment of an artifact on a runtime. */
		deployment: Option[Schema.DeploymentOccurrence] = None,
	  /** Describes a security vulnerability. */
		vulnerability: Option[Schema.VulnerabilityOccurrence] = None,
	  /** Output only. The name of the occurrence in the form of `projects/[PROJECT_ID]/occurrences/[OCCURRENCE_ID]`. */
		name: Option[String] = None,
	  /** Output only. This explicitly denotes which of the occurrence details are specified. This field can be used as a filter in list requests. */
		kind: Option[Schema.Occurrence.KindEnum] = None,
	  /** https://github.com/secure-systems-lab/dsse */
		envelope: Option[Schema.Envelope] = None,
	  /** Output only. The time this occurrence was last updated. */
		updateTime: Option[String] = None,
	  /** Describes a specific SBOM reference occurrences. */
		sbomReference: Option[Schema.SBOMReferenceOccurrence] = None,
	  /** Describes a verifiable build. */
		build: Option[Schema.BuildOccurrence] = None,
	  /** Describes the installation of a package on the linked resource. */
		`package`: Option[Schema.PackageOccurrence] = None,
	  /** Output only. The time this occurrence was created. */
		createTime: Option[String] = None,
	  /** A description of actions that can be taken to remedy the note. */
		remediation: Option[String] = None,
	  /** Describes an attestation of an artifact using dsse. */
		dsseAttestation: Option[Schema.DSSEAttestationOccurrence] = None,
	  /** Describes how this resource derives from the basis in the associated note. */
		image: Option[Schema.ImageOccurrence] = None
	)
	
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1SecretManagerSecret(
	  /** Environment variable name to associate with the secret. Secret environment variables must be unique across all of a build's secrets, and must be used by at least one build step. */
		env: Option[String] = None,
	  /** Resource name of the SecretVersion. In format: projects/&#42;/secrets/&#42;/versions/&#42; */
		versionName: Option[String] = None
	)
	
	case class Publisher(
	  /** Name of the publisher. Examples: 'Google', 'Google Cloud Platform'. */
		name: Option[String] = None,
	  /** The context or namespace. Contains a URL which is under control of the issuing party and can be used as a globally unique identifier for that issuing party. Example: https://csaf.io */
		publisherNamespace: Option[String] = None,
	  /** Provides information about the authority of the issuing party to release the document, in particular, the party's constituency and responsibilities or other obligations. */
		issuingAuthority: Option[String] = None
	)
	
	case class AttestationNote(
	  /** Hint hints at the purpose of the attestation authority. */
		hint: Option[Schema.Hint] = None
	)
	
	object CisBenchmark {
		enum SeverityEnum extends Enum[SeverityEnum] { case SEVERITY_UNSPECIFIED, MINIMAL, LOW, MEDIUM, HIGH, CRITICAL }
	}
	case class CisBenchmark(
		profileLevel: Option[Int] = None,
		severity: Option[Schema.CisBenchmark.SeverityEnum] = None
	)
	
	case class TimeSpan(
	  /** Start of time span. */
		startTime: Option[String] = None,
	  /** End of time span. */
		endTime: Option[String] = None
	)
	
	object ContaineranalysisGoogleDevtoolsCloudbuildV1BuildApproval {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PENDING, APPROVED, REJECTED, CANCELLED }
	}
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1BuildApproval(
	  /** Output only. The state of this build's approval. */
		state: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1BuildApproval.StateEnum] = None,
	  /** Output only. Configuration for manual approval of this build. */
		config: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1ApprovalConfig] = None,
	  /** Output only. Result of manual approval for this Build. */
		result: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1ApprovalResult] = None
	)
	
	object ContaineranalysisGoogleDevtoolsCloudbuildV1StorageSource {
		enum SourceFetcherEnum extends Enum[SourceFetcherEnum] { case SOURCE_FETCHER_UNSPECIFIED, GSUTIL, GCS_FETCHER }
	}
	case class ContaineranalysisGoogleDevtoolsCloudbuildV1StorageSource(
	  /** Cloud Storage bucket containing the source (see [Bucket Name Requirements](https://cloud.google.com/storage/docs/bucket-naming#requirements)). */
		bucket: Option[String] = None,
	  /** Optional. Cloud Storage generation for the object. If the generation is omitted, the latest generation will be used. */
		generation: Option[String] = None,
	  /** Required. Cloud Storage object containing the source. This object must be a zipped (`.zip`) or gzipped archive file (`.tar.gz`) containing source to build. */
		`object`: Option[String] = None,
	  /** Optional. Option to specify the tool to fetch the source file for the build. */
		sourceFetcher: Option[Schema.ContaineranalysisGoogleDevtoolsCloudbuildV1StorageSource.SourceFetcherEnum] = None
	)
}
