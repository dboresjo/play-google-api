package com.boresjo.play.api.google.artifactregistry

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
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
	
	case class ListDockerImagesResponse(
	  /** The docker images returned. */
		dockerImages: Option[List[Schema.DockerImage]] = None,
	  /** The token to retrieve the next page of artifacts, or empty if there are no more artifacts to return. */
		nextPageToken: Option[String] = None
	)
	
	case class DockerImage(
	  /** Required. registry_location, project_id, repository_name and image id forms a unique image name:`projects//locations//repository//dockerImages/`. For example, "projects/test-project/locations/us-west4/repositories/test-repo/dockerImages/ nginx@sha256:e9954c1fc875017be1c3e36eca16be2d9e9bccc4bf072163515467d6a823c7cf", where "us-west4" is the registry_location, "test-project" is the project_id, "test-repo" is the repository_name and "nginx@sha256:e9954c1fc875017be1c3e36eca16be2d9e9bccc4bf072163515467d6a823c7cf" is the image's digest. */
		name: Option[String] = None,
	  /** Required. URL to access the image. Example: us-west4-docker.pkg.dev/test-project/test-repo/nginx@sha256:e9954c1fc875017be1c3e36eca16be2d9e9bccc4bf072163515467d6a823c7cf */
		uri: Option[String] = None,
	  /** Tags attached to this image. */
		tags: Option[List[String]] = None,
	  /** Calculated size of the image. This field is returned as the 'metadata.imageSizeBytes' field in the Version resource. */
		imageSizeBytes: Option[String] = None,
	  /** Time the image was uploaded. */
		uploadTime: Option[String] = None,
	  /** Media type of this image, e.g. "application/vnd.docker.distribution.manifest.v2+json". This field is returned as the 'metadata.mediaType' field in the Version resource. */
		mediaType: Option[String] = None,
	  /** The time this image was built. This field is returned as the 'metadata.buildTime' field in the Version resource. The build time is returned to the client as an RFC 3339 string, which can be easily used with the JavaScript Date constructor. */
		buildTime: Option[String] = None,
	  /** Output only. The time when the docker image was last updated. */
		updateTime: Option[String] = None
	)
	
	case class ListMavenArtifactsResponse(
	  /** The maven artifacts returned. */
		mavenArtifacts: Option[List[Schema.MavenArtifact]] = None,
	  /** The token to retrieve the next page of artifacts, or empty if there are no more artifacts to return. */
		nextPageToken: Option[String] = None
	)
	
	case class MavenArtifact(
	  /** Required. registry_location, project_id, repository_name and maven_artifact forms a unique artifact For example, "projects/test-project/locations/us-west4/repositories/test-repo/mavenArtifacts/ com.google.guava:guava:31.0-jre", where "us-west4" is the registry_location, "test-project" is the project_id, "test-repo" is the repository_name and "com.google.guava:guava:31.0-jre" is the maven artifact. */
		name: Option[String] = None,
	  /** Required. URL to access the pom file of the artifact. Example: us-west4-maven.pkg.dev/test-project/test-repo/com/google/guava/guava/31.0/guava-31.0.pom */
		pomUri: Option[String] = None,
	  /** Group ID for the artifact. Example: com.google.guava */
		groupId: Option[String] = None,
	  /** Artifact ID for the artifact. */
		artifactId: Option[String] = None,
	  /** Version of this artifact. */
		version: Option[String] = None,
	  /** Output only. Time the artifact was created. */
		createTime: Option[String] = None,
	  /** Output only. Time the artifact was updated. */
		updateTime: Option[String] = None
	)
	
	case class ListNpmPackagesResponse(
	  /** The npm packages returned. */
		npmPackages: Option[List[Schema.NpmPackage]] = None,
	  /** The token to retrieve the next page of artifacts, or empty if there are no more artifacts to return. */
		nextPageToken: Option[String] = None
	)
	
	case class NpmPackage(
	  /** Required. registry_location, project_id, repository_name and npm_package forms a unique package For example, "projects/test-project/locations/us-west4/repositories/test-repo/npmPackages/ npm_test:1.0.0", where "us-west4" is the registry_location, "test-project" is the project_id, "test-repo" is the repository_name and npm_test:1.0.0" is the npm package. */
		name: Option[String] = None,
	  /** Package for the artifact. */
		packageName: Option[String] = None,
	  /** Version of this package. */
		version: Option[String] = None,
	  /** Tags attached to this package. */
		tags: Option[List[String]] = None,
	  /** Output only. Time the package was created. */
		createTime: Option[String] = None,
	  /** Output only. Time the package was updated. */
		updateTime: Option[String] = None
	)
	
	case class ListPythonPackagesResponse(
	  /** The python packages returned. */
		pythonPackages: Option[List[Schema.PythonPackage]] = None,
	  /** The token to retrieve the next page of artifacts, or empty if there are no more artifacts to return. */
		nextPageToken: Option[String] = None
	)
	
	case class PythonPackage(
	  /** Required. registry_location, project_id, repository_name and python_package forms a unique package name:`projects//locations//repository//pythonPackages/`. For example, "projects/test-project/locations/us-west4/repositories/test-repo/pythonPackages/ python_package:1.0.0", where "us-west4" is the registry_location, "test-project" is the project_id, "test-repo" is the repository_name and python_package:1.0.0" is the python package. */
		name: Option[String] = None,
	  /** Required. URL to access the package. Example: us-west4-python.pkg.dev/test-project/test-repo/python_package/file-name-1.0.0.tar.gz */
		uri: Option[String] = None,
	  /** Package for the artifact. */
		packageName: Option[String] = None,
	  /** Version of this package. */
		version: Option[String] = None,
	  /** Output only. Time the package was created. */
		createTime: Option[String] = None,
	  /** Output only. Time the package was updated. */
		updateTime: Option[String] = None
	)
	
	case class ImportAptArtifactsRequest(
	  /** Google Cloud Storage location where input content is located. */
		gcsSource: Option[Schema.ImportAptArtifactsGcsSource] = None
	)
	
	case class ImportAptArtifactsGcsSource(
	  /** Cloud Storage paths URI (e.g., gs://my_bucket//my_object). */
		uris: Option[List[String]] = None,
	  /** Supports URI wildcards for matching multiple objects from a single URI. */
		useWildcards: Option[Boolean] = None
	)
	
	case class ImportYumArtifactsRequest(
	  /** Google Cloud Storage location where input content is located. */
		gcsSource: Option[Schema.ImportYumArtifactsGcsSource] = None
	)
	
	case class ImportYumArtifactsGcsSource(
	  /** Cloud Storage paths URI (e.g., gs://my_bucket//my_object). */
		uris: Option[List[String]] = None,
	  /** Supports URI wildcards for matching multiple objects from a single URI. */
		useWildcards: Option[Boolean] = None
	)
	
	case class ImportGoogetArtifactsRequest(
	  /** Google Cloud Storage location where input content is located. */
		gcsSource: Option[Schema.ImportGoogetArtifactsGcsSource] = None
	)
	
	case class ImportGoogetArtifactsGcsSource(
	  /** Cloud Storage paths URI (e.g., `gs://my_bucket/my_object`). */
		uris: Option[List[String]] = None,
	  /** Supports URI wildcards for matching multiple objects from a single URI. */
		useWildcards: Option[Boolean] = None
	)
	
	case class UploadAptArtifactRequest(
	
	)
	
	case class UploadAptArtifactMediaResponse(
	  /** Operation to be returned to the user. */
		operation: Option[Schema.Operation] = None
	)
	
	case class UploadYumArtifactRequest(
	
	)
	
	case class UploadYumArtifactMediaResponse(
	  /** Operation to be returned to the user. */
		operation: Option[Schema.Operation] = None
	)
	
	case class UploadGenericArtifactRequest(
	  /** The ID of the package of the generic artifact. If the package does not exist, a new package will be created. The `package_id` should start and end with a letter or number, only contain letters, numbers, hyphens, underscores, and periods, and not exceed 256 characters. */
		packageId: Option[String] = None,
	  /** The ID of the version of the generic artifact. If the version does not exist, a new version will be created. The version_id must start and end with a letter or number, can only contain lowercase letters, numbers, hyphens and periods, i.e. [a-z0-9-.] and cannot exceed a total of 128 characters. Creating a version called `latest` is not allowed. */
		versionId: Option[String] = None,
	  /** The name of the file of the generic artifact to be uploaded. E.g. `example-file.zip` The filename is limited to letters, numbers, and url safe characters, i.e. [a-zA-Z0-9-_.~@]. */
		filename: Option[String] = None
	)
	
	case class UploadGenericArtifactMediaResponse(
	  /** Operation that will be returned to the user. */
		operation: Option[Schema.Operation] = None
	)
	
	case class UploadKfpArtifactRequest(
	  /** Tags to be created with the version. */
		tags: Option[List[String]] = None,
	  /** Description of the package version. */
		description: Option[String] = None
	)
	
	case class UploadKfpArtifactMediaResponse(
	  /** Operation that will be returned to the user. */
		operation: Option[Schema.Operation] = None
	)
	
	case class UploadGoModuleRequest(
	
	)
	
	case class UploadGoModuleMediaResponse(
	  /** Operation to be returned to the user. */
		operation: Option[Schema.Operation] = None
	)
	
	case class UploadGoogetArtifactRequest(
	
	)
	
	case class UploadGoogetArtifactMediaResponse(
	  /** Operation to be returned to the user. */
		operation: Option[Schema.Operation] = None
	)
	
	case class ListRepositoriesResponse(
	  /** The repositories returned. */
		repositories: Option[List[Schema.Repository]] = None,
	  /** The token to retrieve the next page of repositories, or empty if there are no more repositories to return. */
		nextPageToken: Option[String] = None
	)
	
	object Repository {
		enum FormatEnum extends Enum[FormatEnum] { case FORMAT_UNSPECIFIED, DOCKER, MAVEN, NPM, APT, YUM, GOOGET, PYTHON, KFP, GO, GENERIC }
		enum ModeEnum extends Enum[ModeEnum] { case MODE_UNSPECIFIED, STANDARD_REPOSITORY, VIRTUAL_REPOSITORY, REMOTE_REPOSITORY, AOSS_REPOSITORY, ASSURED_OSS_REPOSITORY }
	}
	case class Repository(
	  /** Maven repository config contains repository level configuration for the repositories of maven type. */
		mavenConfig: Option[Schema.MavenRepositoryConfig] = None,
	  /** Docker repository config contains repository level configuration for the repositories of docker type. */
		dockerConfig: Option[Schema.DockerRepositoryConfig] = None,
	  /** Configuration specific for a Virtual Repository. */
		virtualRepositoryConfig: Option[Schema.VirtualRepositoryConfig] = None,
	  /** Configuration specific for a Remote Repository. */
		remoteRepositoryConfig: Option[Schema.RemoteRepositoryConfig] = None,
	  /** The name of the repository, for example: `projects/p1/locations/us-central1/repositories/repo1`. For each location in a project, repository names must be unique. */
		name: Option[String] = None,
	  /** Optional. The format of packages that are stored in the repository. */
		format: Option[Schema.Repository.FormatEnum] = None,
	  /** The user-provided description of the repository. */
		description: Option[String] = None,
	  /** Labels with user-defined metadata. This field may contain up to 64 entries. Label keys and values may be no longer than 63 characters. Label keys must begin with a lowercase letter and may only contain lowercase letters, numeric characters, underscores, and dashes. */
		labels: Option[Map[String, String]] = None,
	  /** Output only. The time when the repository was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the repository was last updated. */
		updateTime: Option[String] = None,
	  /** The Cloud KMS resource name of the customer managed encryption key that's used to encrypt the contents of the Repository. Has the form: `projects/my-project/locations/my-region/keyRings/my-kr/cryptoKeys/my-key`. This value may not be changed after the Repository has been created. */
		kmsKeyName: Option[String] = None,
	  /** Optional. The mode of the repository. */
		mode: Option[Schema.Repository.ModeEnum] = None,
	  /** Optional. Cleanup policies for this repository. Cleanup policies indicate when certain package versions can be automatically deleted. Map keys are policy IDs supplied by users during policy creation. They must unique within a repository and be under 128 characters in length. */
		cleanupPolicies: Option[Map[String, Schema.CleanupPolicy]] = None,
	  /** Output only. The size, in bytes, of all artifact storage in this repository. Repositories that are generally available or in public preview use this to calculate storage costs. */
		sizeBytes: Option[String] = None,
	  /** Output only. If set, the repository satisfies physical zone separation. */
		satisfiesPzs: Option[Boolean] = None,
	  /** Optional. If true, the cleanup pipeline is prevented from deleting versions in this repository. */
		cleanupPolicyDryRun: Option[Boolean] = None,
	  /** Optional. Config and state for vulnerability scanning of resources within this Repository. */
		vulnerabilityScanningConfig: Option[Schema.VulnerabilityScanningConfig] = None,
	  /** Optional. If this is true, an unspecified repo type will be treated as error rather than defaulting to standard. */
		disallowUnspecifiedMode: Option[Boolean] = None,
	  /** Output only. If set, the repository satisfies physical zone isolation. */
		satisfiesPzi: Option[Boolean] = None
	)
	
	object MavenRepositoryConfig {
		enum VersionPolicyEnum extends Enum[VersionPolicyEnum] { case VERSION_POLICY_UNSPECIFIED, RELEASE, SNAPSHOT }
	}
	case class MavenRepositoryConfig(
	  /** The repository with this flag will allow publishing the same snapshot versions. */
		allowSnapshotOverwrites: Option[Boolean] = None,
	  /** Version policy defines the versions that the registry will accept. */
		versionPolicy: Option[Schema.MavenRepositoryConfig.VersionPolicyEnum] = None
	)
	
	case class DockerRepositoryConfig(
	  /** The repository which enabled this flag prevents all tags from being modified, moved or deleted. This does not prevent tags from being created. */
		immutableTags: Option[Boolean] = None
	)
	
	case class VirtualRepositoryConfig(
	  /** Policies that configure the upstream artifacts distributed by the Virtual Repository. Upstream policies cannot be set on a standard repository. */
		upstreamPolicies: Option[List[Schema.UpstreamPolicy]] = None
	)
	
	case class UpstreamPolicy(
	  /** The user-provided ID of the upstream policy. */
		id: Option[String] = None,
	  /** A reference to the repository resource, for example: `projects/p1/locations/us-central1/repositories/repo1`. */
		repository: Option[String] = None,
	  /** Entries with a greater priority value take precedence in the pull order. */
		priority: Option[Int] = None
	)
	
	case class RemoteRepositoryConfig(
	  /** Specific settings for a Docker remote repository. */
		dockerRepository: Option[Schema.DockerRepository] = None,
	  /** Specific settings for a Maven remote repository. */
		mavenRepository: Option[Schema.MavenRepository] = None,
	  /** Specific settings for an Npm remote repository. */
		npmRepository: Option[Schema.NpmRepository] = None,
	  /** Specific settings for a Python remote repository. */
		pythonRepository: Option[Schema.PythonRepository] = None,
	  /** Specific settings for an Apt remote repository. */
		aptRepository: Option[Schema.AptRepository] = None,
	  /** Specific settings for a Yum remote repository. */
		yumRepository: Option[Schema.YumRepository] = None,
	  /** Common remote repository settings. Used as the remote repository upstream URL. */
		commonRepository: Option[Schema.CommonRemoteRepository] = None,
	  /** The description of the remote source. */
		description: Option[String] = None,
	  /** Optional. The credentials used to access the remote repository. */
		upstreamCredentials: Option[Schema.UpstreamCredentials] = None,
	  /** Input only. A create/update remote repo option to avoid making a HEAD/GET request to validate a remote repo and any supplied upstream credentials. */
		disableUpstreamValidation: Option[Boolean] = None
	)
	
	object DockerRepository {
		enum PublicRepositoryEnum extends Enum[PublicRepositoryEnum] { case PUBLIC_REPOSITORY_UNSPECIFIED, DOCKER_HUB }
	}
	case class DockerRepository(
	  /** One of the publicly available Docker repositories supported by Artifact Registry. */
		publicRepository: Option[Schema.DockerRepository.PublicRepositoryEnum] = None,
	  /** Customer-specified remote repository. */
		customRepository: Option[Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigDockerRepositoryCustomRepository] = None
	)
	
	case class GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigDockerRepositoryCustomRepository(
	  /** An http/https uri reference to the custom remote repository, for ex: "https://registry-1.docker.io". */
		uri: Option[String] = None
	)
	
	object MavenRepository {
		enum PublicRepositoryEnum extends Enum[PublicRepositoryEnum] { case PUBLIC_REPOSITORY_UNSPECIFIED, MAVEN_CENTRAL }
	}
	case class MavenRepository(
	  /** One of the publicly available Maven repositories supported by Artifact Registry. */
		publicRepository: Option[Schema.MavenRepository.PublicRepositoryEnum] = None,
	  /** Customer-specified remote repository. */
		customRepository: Option[Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigMavenRepositoryCustomRepository] = None
	)
	
	case class GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigMavenRepositoryCustomRepository(
	  /** An http/https uri reference to the upstream remote repository, for ex: "https://my.maven.registry/". */
		uri: Option[String] = None
	)
	
	object NpmRepository {
		enum PublicRepositoryEnum extends Enum[PublicRepositoryEnum] { case PUBLIC_REPOSITORY_UNSPECIFIED, NPMJS }
	}
	case class NpmRepository(
	  /** One of the publicly available Npm repositories supported by Artifact Registry. */
		publicRepository: Option[Schema.NpmRepository.PublicRepositoryEnum] = None,
	  /** Customer-specified remote repository. */
		customRepository: Option[Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigNpmRepositoryCustomRepository] = None
	)
	
	case class GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigNpmRepositoryCustomRepository(
	  /** An http/https uri reference to the upstream remote repository, for ex: "https://my.npm.registry/". */
		uri: Option[String] = None
	)
	
	object PythonRepository {
		enum PublicRepositoryEnum extends Enum[PublicRepositoryEnum] { case PUBLIC_REPOSITORY_UNSPECIFIED, PYPI }
	}
	case class PythonRepository(
	  /** One of the publicly available Python repositories supported by Artifact Registry. */
		publicRepository: Option[Schema.PythonRepository.PublicRepositoryEnum] = None,
	  /** Customer-specified remote repository. */
		customRepository: Option[Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigPythonRepositoryCustomRepository] = None
	)
	
	case class GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigPythonRepositoryCustomRepository(
	  /** An http/https uri reference to the upstream remote repository, for ex: "https://my.python.registry/". */
		uri: Option[String] = None
	)
	
	case class AptRepository(
	  /** One of the publicly available Apt repositories supported by Artifact Registry. */
		publicRepository: Option[Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigAptRepositoryPublicRepository] = None,
	  /** Customer-specified remote repository. */
		customRepository: Option[Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigAptRepositoryCustomRepository] = None
	)
	
	object GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigAptRepositoryPublicRepository {
		enum RepositoryBaseEnum extends Enum[RepositoryBaseEnum] { case REPOSITORY_BASE_UNSPECIFIED, DEBIAN, UBUNTU, DEBIAN_SNAPSHOT }
	}
	case class GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigAptRepositoryPublicRepository(
	  /** A common public repository base for Apt. */
		repositoryBase: Option[Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigAptRepositoryPublicRepository.RepositoryBaseEnum] = None,
	  /** A custom field to define a path to a specific repository from the base. */
		repositoryPath: Option[String] = None
	)
	
	case class GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigAptRepositoryCustomRepository(
	  /** An http/https uri reference to the upstream remote repository, for ex: "https://my.apt.registry/". */
		uri: Option[String] = None
	)
	
	case class YumRepository(
	  /** One of the publicly available Yum repositories supported by Artifact Registry. */
		publicRepository: Option[Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigYumRepositoryPublicRepository] = None,
	  /** Customer-specified remote repository. */
		customRepository: Option[Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigYumRepositoryCustomRepository] = None
	)
	
	object GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigYumRepositoryPublicRepository {
		enum RepositoryBaseEnum extends Enum[RepositoryBaseEnum] { case REPOSITORY_BASE_UNSPECIFIED, CENTOS, CENTOS_DEBUG, CENTOS_VAULT, CENTOS_STREAM, ROCKY, EPEL }
	}
	case class GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigYumRepositoryPublicRepository(
	  /** A common public repository base for Yum. */
		repositoryBase: Option[Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigYumRepositoryPublicRepository.RepositoryBaseEnum] = None,
	  /** A custom field to define a path to a specific repository from the base. */
		repositoryPath: Option[String] = None
	)
	
	case class GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigYumRepositoryCustomRepository(
	  /** An http/https uri reference to the upstream remote repository, for ex: "https://my.yum.registry/". */
		uri: Option[String] = None
	)
	
	case class CommonRemoteRepository(
	  /** Required. A common public repository base for remote repository. */
		uri: Option[String] = None
	)
	
	case class UpstreamCredentials(
	  /** Use username and password to access the remote repository. */
		usernamePasswordCredentials: Option[Schema.UsernamePasswordCredentials] = None
	)
	
	case class UsernamePasswordCredentials(
	  /** The username to access the remote repository. */
		username: Option[String] = None,
	  /** The Secret Manager key version that holds the password to access the remote repository. Must be in the format of `projects/{project}/secrets/{secret}/versions/{version}`. */
		passwordSecretVersion: Option[String] = None
	)
	
	object CleanupPolicy {
		enum ActionEnum extends Enum[ActionEnum] { case ACTION_UNSPECIFIED, DELETE, KEEP }
	}
	case class CleanupPolicy(
	  /** Policy condition for matching versions. */
		condition: Option[Schema.CleanupPolicyCondition] = None,
	  /** Policy condition for retaining a minimum number of versions. May only be specified with a Keep action. */
		mostRecentVersions: Option[Schema.CleanupPolicyMostRecentVersions] = None,
	  /** The user-provided ID of the cleanup policy. */
		id: Option[String] = None,
	  /** Policy action. */
		action: Option[Schema.CleanupPolicy.ActionEnum] = None
	)
	
	object CleanupPolicyCondition {
		enum TagStateEnum extends Enum[TagStateEnum] { case TAG_STATE_UNSPECIFIED, TAGGED, UNTAGGED, ANY }
	}
	case class CleanupPolicyCondition(
	  /** Match versions by tag status. */
		tagState: Option[Schema.CleanupPolicyCondition.TagStateEnum] = None,
	  /** Match versions by tag prefix. Applied on any prefix match. */
		tagPrefixes: Option[List[String]] = None,
	  /** Match versions by version name prefix. Applied on any prefix match. */
		versionNamePrefixes: Option[List[String]] = None,
	  /** Match versions by package prefix. Applied on any prefix match. */
		packageNamePrefixes: Option[List[String]] = None,
	  /** Match versions older than a duration. */
		olderThan: Option[String] = None,
	  /** Match versions newer than a duration. */
		newerThan: Option[String] = None
	)
	
	case class CleanupPolicyMostRecentVersions(
	  /** List of package name prefixes that will apply this rule. */
		packageNamePrefixes: Option[List[String]] = None,
	  /** Minimum number of versions to keep. */
		keepCount: Option[Int] = None
	)
	
	object VulnerabilityScanningConfig {
		enum EnablementConfigEnum extends Enum[EnablementConfigEnum] { case ENABLEMENT_CONFIG_UNSPECIFIED, INHERITED, DISABLED }
		enum EnablementStateEnum extends Enum[EnablementStateEnum] { case ENABLEMENT_STATE_UNSPECIFIED, SCANNING_UNSUPPORTED, SCANNING_DISABLED, SCANNING_ACTIVE }
	}
	case class VulnerabilityScanningConfig(
	  /** Optional. Config for whether this repository has vulnerability scanning disabled. */
		enablementConfig: Option[Schema.VulnerabilityScanningConfig.EnablementConfigEnum] = None,
	  /** Output only. The last time this repository config was enabled. */
		lastEnableTime: Option[String] = None,
	  /** Output only. State of feature enablement, combining repository enablement config and API enablement state. */
		enablementState: Option[Schema.VulnerabilityScanningConfig.EnablementStateEnum] = None,
	  /** Output only. Reason for the repository state. */
		enablementStateReason: Option[String] = None
	)
	
	case class ListPackagesResponse(
	  /** The packages returned. */
		packages: Option[List[Schema.Package]] = None,
	  /** The token to retrieve the next page of packages, or empty if there are no more packages to return. */
		nextPageToken: Option[String] = None
	)
	
	case class Package(
	  /** The name of the package, for example: `projects/p1/locations/us-central1/repositories/repo1/packages/pkg1`. If the package ID part contains slashes, the slashes are escaped. */
		name: Option[String] = None,
	  /** The display name of the package. */
		displayName: Option[String] = None,
	  /** The time when the package was created. */
		createTime: Option[String] = None,
	  /** The time when the package was last updated. This includes publishing a new version of the package. */
		updateTime: Option[String] = None,
	  /** Optional. Client specified annotations. */
		annotations: Option[Map[String, String]] = None
	)
	
	case class ListVersionsResponse(
	  /** The versions returned. */
		versions: Option[List[Schema.Version]] = None,
	  /** The token to retrieve the next page of versions, or empty if there are no more versions to return. */
		nextPageToken: Option[String] = None
	)
	
	case class Version(
	  /** The name of the version, for example: `projects/p1/locations/us-central1/repositories/repo1/packages/pkg1/versions/art1`. If the package or version ID parts contain slashes, the slashes are escaped. */
		name: Option[String] = None,
	  /** Optional. Description of the version, as specified in its metadata. */
		description: Option[String] = None,
	  /** The time when the version was created. */
		createTime: Option[String] = None,
	  /** The time when the version was last updated. */
		updateTime: Option[String] = None,
	  /** Output only. A list of related tags. Will contain up to 100 tags that reference this version. */
		relatedTags: Option[List[Schema.Tag]] = None,
	  /** Output only. Repository-specific Metadata stored against this version. The fields returned are defined by the underlying repository-specific resource. Currently, the resources could be: DockerImage MavenArtifact */
		metadata: Option[Map[String, JsValue]] = None,
	  /** Optional. Client specified annotations. */
		annotations: Option[Map[String, String]] = None
	)
	
	case class Tag(
	  /** The name of the tag, for example: "projects/p1/locations/us-central1/repositories/repo1/packages/pkg1/tags/tag1". If the package part contains slashes, the slashes are escaped. The tag part can only have characters in [a-zA-Z0-9\-._~:@], anything else must be URL encoded. */
		name: Option[String] = None,
	  /** The name of the version the tag refers to, for example: `projects/p1/locations/us-central1/repositories/repo1/packages/pkg1/versions/sha256:5243811` If the package or version ID parts contain slashes, the slashes are escaped. */
		version: Option[String] = None
	)
	
	case class BatchDeleteVersionsRequest(
	  /** Required. The names of the versions to delete. A maximum of 10000 versions can be deleted in a batch. */
		names: Option[List[String]] = None,
	  /** If true, the request is performed without deleting data, following AIP-163. */
		validateOnly: Option[Boolean] = None
	)
	
	case class ListFilesResponse(
	  /** The files returned. */
		files: Option[List[Schema.GoogleDevtoolsArtifactregistryV1File]] = None,
	  /** The token to retrieve the next page of files, or empty if there are no more files to return. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleDevtoolsArtifactregistryV1File(
	  /** The name of the file, for example: `projects/p1/locations/us-central1/repositories/repo1/files/a%2Fb%2Fc.txt`. If the file ID part contains slashes, they are escaped. */
		name: Option[String] = None,
	  /** The size of the File in bytes. */
		sizeBytes: Option[String] = None,
	  /** The hashes of the file content. */
		hashes: Option[List[Schema.Hash]] = None,
	  /** Output only. The time when the File was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the File was last updated. */
		updateTime: Option[String] = None,
	  /** The name of the Package or Version that owns this file, if any. */
		owner: Option[String] = None,
	  /** Output only. The time when the last attempt to refresh the file's data was made. Only set when the repository is remote. */
		fetchTime: Option[String] = None,
	  /** Optional. Client specified annotations. */
		annotations: Option[Map[String, String]] = None
	)
	
	object Hash {
		enum TypeEnum extends Enum[TypeEnum] { case HASH_TYPE_UNSPECIFIED, SHA256, MD5 }
	}
	case class Hash(
	  /** The algorithm used to compute the hash value. */
		`type`: Option[Schema.Hash.TypeEnum] = None,
	  /** The hash value. */
		value: Option[String] = None
	)
	
	case class DownloadFileResponse(
	
	)
	
	case class UploadFileRequest(
	  /** Optional. The ID of the file. If left empty will default to sha256 digest of the content uploaded. */
		fileId: Option[String] = None
	)
	
	case class UploadFileMediaResponse(
	  /** Operation that will be returned to the user. */
		operation: Option[Schema.Operation] = None
	)
	
	case class ListTagsResponse(
	  /** The tags returned. */
		tags: Option[List[Schema.Tag]] = None,
	  /** The token to retrieve the next page of tags, or empty if there are no more tags to return. */
		nextPageToken: Option[String] = None
	)
	
	case class Empty(
	
	)
	
	object GoogleDevtoolsArtifactregistryV1Rule {
		enum ActionEnum extends Enum[ActionEnum] { case ACTION_UNSPECIFIED, ALLOW, DENY }
		enum OperationEnum extends Enum[OperationEnum] { case OPERATION_UNSPECIFIED, DOWNLOAD }
	}
	case class GoogleDevtoolsArtifactregistryV1Rule(
	  /** The name of the rule, for example: `projects/p1/locations/us-central1/repositories/repo1/rules/rule1`. */
		name: Option[String] = None,
	  /** The action this rule takes. */
		action: Option[Schema.GoogleDevtoolsArtifactregistryV1Rule.ActionEnum] = None,
		operation: Option[Schema.GoogleDevtoolsArtifactregistryV1Rule.OperationEnum] = None,
	  /** Optional. A CEL expression for conditions that must be met in order for the rule to apply. If not provided, the rule matches all objects. */
		condition: Option[Schema.Expr] = None,
	  /** The package ID the rule applies to. If empty, this rule applies to all packages inside the repository. */
		packageId: Option[String] = None
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
	
	case class ListRulesResponse(
	  /** The rules returned. */
		rules: Option[List[Schema.GoogleDevtoolsArtifactregistryV1Rule]] = None,
	  /** The token to retrieve the next page of rules, or empty if there are no more rules to return. */
		nextPageToken: Option[String] = None
	)
	
	case class SetIamPolicyRequest(
	  /** REQUIRED: The complete policy to be applied to the `resource`. The size of the policy is limited to a few 10s of KB. An empty policy is a valid policy but certain Google Cloud services (such as Projects) might reject them. */
		policy: Option[Schema.Policy] = None
	)
	
	case class Policy(
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
	
	case class TestIamPermissionsRequest(
	  /** The set of permissions to check for the `resource`. Permissions with wildcards (such as `&#42;` or `storage.&#42;`) are not allowed. For more information see [IAM Overview](https://cloud.google.com/iam/docs/overview#permissions). */
		permissions: Option[List[String]] = None
	)
	
	case class TestIamPermissionsResponse(
	  /** A subset of `TestPermissionsRequest.permissions` that the caller is allowed. */
		permissions: Option[List[String]] = None
	)
	
	object ProjectSettings {
		enum LegacyRedirectionStateEnum extends Enum[LegacyRedirectionStateEnum] { case REDIRECTION_STATE_UNSPECIFIED, REDIRECTION_FROM_GCR_IO_DISABLED, REDIRECTION_FROM_GCR_IO_ENABLED, REDIRECTION_FROM_GCR_IO_FINALIZED, REDIRECTION_FROM_GCR_IO_ENABLED_AND_COPYING, REDIRECTION_FROM_GCR_IO_PARTIAL_AND_COPYING }
	}
	case class ProjectSettings(
	  /** The name of the project's settings. Always of the form: projects/{project-id}/projectSettings In update request: never set In response: always set */
		name: Option[String] = None,
	  /** The redirection state of the legacy repositories in this project. */
		legacyRedirectionState: Option[Schema.ProjectSettings.LegacyRedirectionStateEnum] = None,
	  /** The percentage of pull traffic to redirect from GCR to AR when using partial redirection. */
		pullPercent: Option[Int] = None
	)
	
	object VPCSCConfig {
		enum VpcscPolicyEnum extends Enum[VpcscPolicyEnum] { case VPCSC_POLICY_UNSPECIFIED, DENY, ALLOW }
	}
	case class VPCSCConfig(
	  /** The name of the project's VPC SC Config. Always of the form: projects/{projectID}/locations/{location}/vpcscConfig In update request: never set In response: always set */
		name: Option[String] = None,
	  /** The project per location VPC SC policy that defines the VPC SC behavior for the Remote Repository (Allow/Deny). */
		vpcscPolicy: Option[Schema.VPCSCConfig.VpcscPolicyEnum] = None
	)
	
	case class ListAttachmentsResponse(
	  /** The attachments returned. */
		attachments: Option[List[Schema.Attachment]] = None,
	  /** The token to retrieve the next page of attachments, or empty if there are no more attachments to return. */
		nextPageToken: Option[String] = None
	)
	
	case class Attachment(
	  /** The name of the attachment. E.g. `projects/p1/locations/us/repositories/repo/attachments/sbom`. */
		name: Option[String] = None,
	  /** Required. The target the attachment is for, can be a Version, Package or Repository. E.g. `projects/p1/locations/us-central1/repositories/repo1/packages/p1/versions/v1`. */
		target: Option[String] = None,
	  /** Type of attachment. E.g. `application/vnd.spdx+json` */
		`type`: Option[String] = None,
	  /** The namespace this attachment belongs to. E.g. If an attachment is created by artifact analysis, namespace is set to `artifactanalysis.googleapis.com`. */
		attachmentNamespace: Option[String] = None,
	  /** Optional. User annotations. These attributes can only be set and used by the user, and not by Artifact Registry. See https://google.aip.dev/128#annotations for more details such as format and size limitations. */
		annotations: Option[Map[String, String]] = None,
	  /** Output only. The time when the attachment was created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the attachment was last updated. */
		updateTime: Option[String] = None,
	  /** Required. The files that belong to this attachment. If the file ID part contains slashes, they are escaped. E.g. `projects/p1/locations/us-central1/repositories/repo1/files/sha:`. */
		files: Option[List[String]] = None,
	  /** Output only. The name of the OCI version that this attachment created. Only populated for Docker attachments. E.g. `projects/p1/locations/us-central1/repositories/repo1/packages/p1/versions/v1`. */
		ociVersionName: Option[String] = None
	)
	
	case class ListLocationsResponse(
	  /** A list of locations that matches the specified filter in the request. */
		locations: Option[List[Schema.Location]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class Location(
	  /** Resource name for the location, which may vary between implementations. For example: `"projects/example-project/locations/us-east1"` */
		name: Option[String] = None,
	  /** The canonical id for this location. For example: `"us-east1"`. */
		locationId: Option[String] = None,
	  /** The friendly name for this location, typically a nearby city name. For example, "Tokyo". */
		displayName: Option[String] = None,
	  /** Cross-service attributes for the location. For example {"cloud.googleapis.com/region": "us-east1"} */
		labels: Option[Map[String, String]] = None,
	  /** Service-specific metadata. For example the available capacity at the given location. */
		metadata: Option[Map[String, JsValue]] = None
	)
	
	case class ImportAptArtifactsResponse(
	  /** The Apt artifacts imported. */
		aptArtifacts: Option[List[Schema.AptArtifact]] = None,
	  /** Detailed error info for packages that were not imported. */
		errors: Option[List[Schema.ImportAptArtifactsErrorInfo]] = None
	)
	
	object AptArtifact {
		enum PackageTypeEnum extends Enum[PackageTypeEnum] { case PACKAGE_TYPE_UNSPECIFIED, BINARY, SOURCE }
	}
	case class AptArtifact(
	  /** Output only. The Artifact Registry resource name of the artifact. */
		name: Option[String] = None,
	  /** Output only. The Apt package name of the artifact. */
		packageName: Option[String] = None,
	  /** Output only. An artifact is a binary or source package. */
		packageType: Option[Schema.AptArtifact.PackageTypeEnum] = None,
	  /** Output only. Operating system architecture of the artifact. */
		architecture: Option[String] = None,
	  /** Output only. Repository component of the artifact. */
		component: Option[String] = None,
	  /** Output only. Contents of the artifact's control metadata file. */
		controlFile: Option[String] = None
	)
	
	case class ImportAptArtifactsErrorInfo(
	  /** Google Cloud Storage location requested. */
		gcsSource: Option[Schema.ImportAptArtifactsGcsSource] = None,
	  /** The detailed error status. */
		error: Option[Schema.Status] = None
	)
	
	case class ImportAptArtifactsMetadata(
	
	)
	
	case class UploadAptArtifactResponse(
	  /** The Apt artifacts updated. */
		aptArtifacts: Option[List[Schema.AptArtifact]] = None
	)
	
	case class UploadAptArtifactMetadata(
	
	)
	
	case class ImportYumArtifactsResponse(
	  /** The yum artifacts imported. */
		yumArtifacts: Option[List[Schema.YumArtifact]] = None,
	  /** Detailed error info for packages that were not imported. */
		errors: Option[List[Schema.ImportYumArtifactsErrorInfo]] = None
	)
	
	object YumArtifact {
		enum PackageTypeEnum extends Enum[PackageTypeEnum] { case PACKAGE_TYPE_UNSPECIFIED, BINARY, SOURCE }
	}
	case class YumArtifact(
	  /** Output only. The Artifact Registry resource name of the artifact. */
		name: Option[String] = None,
	  /** Output only. The yum package name of the artifact. */
		packageName: Option[String] = None,
	  /** Output only. An artifact is a binary or source package. */
		packageType: Option[Schema.YumArtifact.PackageTypeEnum] = None,
	  /** Output only. Operating system architecture of the artifact. */
		architecture: Option[String] = None
	)
	
	case class ImportYumArtifactsErrorInfo(
	  /** Google Cloud Storage location requested. */
		gcsSource: Option[Schema.ImportYumArtifactsGcsSource] = None,
	  /** The detailed error status. */
		error: Option[Schema.Status] = None
	)
	
	case class ImportYumArtifactsMetadata(
	
	)
	
	case class UploadYumArtifactResponse(
	  /** The Yum artifacts updated. */
		yumArtifacts: Option[List[Schema.YumArtifact]] = None
	)
	
	case class UploadYumArtifactMetadata(
	
	)
	
	case class ImportGoogetArtifactsResponse(
	  /** The GooGet artifacts updated. */
		googetArtifacts: Option[List[Schema.GoogetArtifact]] = None,
	  /** Detailed error info for packages that were not imported. */
		errors: Option[List[Schema.ImportGoogetArtifactsErrorInfo]] = None
	)
	
	case class GoogetArtifact(
	  /** Output only. The Artifact Registry resource name of the artifact. */
		name: Option[String] = None,
	  /** Output only. The GooGet package name of the artifact. */
		packageName: Option[String] = None,
	  /** Output only. Operating system architecture of the artifact. */
		architecture: Option[String] = None
	)
	
	case class ImportGoogetArtifactsErrorInfo(
	  /** Google Cloud Storage location requested. */
		gcsSource: Option[Schema.ImportGoogetArtifactsGcsSource] = None,
	  /** The detailed error status. */
		error: Option[Schema.Status] = None
	)
	
	case class ImportGoogetArtifactsMetadata(
	
	)
	
	case class UploadGoogetArtifactResponse(
	  /** The GooGet artifacts updated. */
		googetArtifacts: Option[List[Schema.GoogetArtifact]] = None
	)
	
	case class UploadGoogetArtifactMetadata(
	
	)
	
	case class KfpArtifact(
	  /** Output only. Resource name of the KFP artifact. Since users don't directly interact with this resource, the name will be derived from the associated version. For example, when version = ".../versions/sha256:abcdef...", the name will be ".../kfpArtifacts/sha256:abcdef...". */
		name: Option[String] = None,
	  /** The version associated with the KFP artifact. Must follow the Semantic Versioning standard. */
		version: Option[String] = None
	)
	
	case class UploadKfpArtifactMetadata(
	
	)
	
	case class GenericArtifact(
	  /** Resource name of the generic artifact. project, location, repository, package_id and version_id create a unique generic artifact. i.e. "projects/test-project/locations/us-west4/repositories/test-repo/ genericArtifacts/package_id:version_id" */
		name: Option[String] = None,
	  /** The version of the generic artifact. */
		version: Option[String] = None,
	  /** Output only. The time when the Generic module is created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the Generic module is updated. */
		updateTime: Option[String] = None
	)
	
	case class UploadGenericArtifactMetadata(
	
	)
	
	case class GoModule(
	  /** The resource name of a Go module. */
		name: Option[String] = None,
	  /** The version of the Go module. Must be a valid canonical version as defined in https://go.dev/ref/mod#glos-canonical-version. */
		version: Option[String] = None,
	  /** Output only. The time when the Go module is created. */
		createTime: Option[String] = None,
	  /** Output only. The time when the Go module is updated. */
		updateTime: Option[String] = None
	)
	
	case class UploadGoModuleMetadata(
	
	)
	
	case class OperationMetadata(
	
	)
	
	case class BatchDeleteVersionsMetadata(
	  /** The versions the operation failed to delete. */
		failedVersions: Option[List[String]] = None
	)
}
