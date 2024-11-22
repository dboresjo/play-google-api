package com.boresjo.play.api.google.artifactregistry

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putListSchemaDockerImage: Conversion[List[Schema.DockerImage], Option[List[Schema.DockerImage]]] = (fun: List[Schema.DockerImage]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putListSchemaMavenArtifact: Conversion[List[Schema.MavenArtifact], Option[List[Schema.MavenArtifact]]] = (fun: List[Schema.MavenArtifact]) => Option(fun)
		given putListSchemaNpmPackage: Conversion[List[Schema.NpmPackage], Option[List[Schema.NpmPackage]]] = (fun: List[Schema.NpmPackage]) => Option(fun)
		given putListSchemaPythonPackage: Conversion[List[Schema.PythonPackage], Option[List[Schema.PythonPackage]]] = (fun: List[Schema.PythonPackage]) => Option(fun)
		given putSchemaImportAptArtifactsGcsSource: Conversion[Schema.ImportAptArtifactsGcsSource, Option[Schema.ImportAptArtifactsGcsSource]] = (fun: Schema.ImportAptArtifactsGcsSource) => Option(fun)
		given putSchemaImportYumArtifactsGcsSource: Conversion[Schema.ImportYumArtifactsGcsSource, Option[Schema.ImportYumArtifactsGcsSource]] = (fun: Schema.ImportYumArtifactsGcsSource) => Option(fun)
		given putSchemaImportGoogetArtifactsGcsSource: Conversion[Schema.ImportGoogetArtifactsGcsSource, Option[Schema.ImportGoogetArtifactsGcsSource]] = (fun: Schema.ImportGoogetArtifactsGcsSource) => Option(fun)
		given putSchemaOperation: Conversion[Schema.Operation, Option[Schema.Operation]] = (fun: Schema.Operation) => Option(fun)
		given putListSchemaRepository: Conversion[List[Schema.Repository], Option[List[Schema.Repository]]] = (fun: List[Schema.Repository]) => Option(fun)
		given putSchemaMavenRepositoryConfig: Conversion[Schema.MavenRepositoryConfig, Option[Schema.MavenRepositoryConfig]] = (fun: Schema.MavenRepositoryConfig) => Option(fun)
		given putSchemaDockerRepositoryConfig: Conversion[Schema.DockerRepositoryConfig, Option[Schema.DockerRepositoryConfig]] = (fun: Schema.DockerRepositoryConfig) => Option(fun)
		given putSchemaVirtualRepositoryConfig: Conversion[Schema.VirtualRepositoryConfig, Option[Schema.VirtualRepositoryConfig]] = (fun: Schema.VirtualRepositoryConfig) => Option(fun)
		given putSchemaRemoteRepositoryConfig: Conversion[Schema.RemoteRepositoryConfig, Option[Schema.RemoteRepositoryConfig]] = (fun: Schema.RemoteRepositoryConfig) => Option(fun)
		given putSchemaRepositoryFormatEnum: Conversion[Schema.Repository.FormatEnum, Option[Schema.Repository.FormatEnum]] = (fun: Schema.Repository.FormatEnum) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaRepositoryModeEnum: Conversion[Schema.Repository.ModeEnum, Option[Schema.Repository.ModeEnum]] = (fun: Schema.Repository.ModeEnum) => Option(fun)
		given putMapStringSchemaCleanupPolicy: Conversion[Map[String, Schema.CleanupPolicy], Option[Map[String, Schema.CleanupPolicy]]] = (fun: Map[String, Schema.CleanupPolicy]) => Option(fun)
		given putSchemaVulnerabilityScanningConfig: Conversion[Schema.VulnerabilityScanningConfig, Option[Schema.VulnerabilityScanningConfig]] = (fun: Schema.VulnerabilityScanningConfig) => Option(fun)
		given putSchemaMavenRepositoryConfigVersionPolicyEnum: Conversion[Schema.MavenRepositoryConfig.VersionPolicyEnum, Option[Schema.MavenRepositoryConfig.VersionPolicyEnum]] = (fun: Schema.MavenRepositoryConfig.VersionPolicyEnum) => Option(fun)
		given putListSchemaUpstreamPolicy: Conversion[List[Schema.UpstreamPolicy], Option[List[Schema.UpstreamPolicy]]] = (fun: List[Schema.UpstreamPolicy]) => Option(fun)
		given putSchemaDockerRepository: Conversion[Schema.DockerRepository, Option[Schema.DockerRepository]] = (fun: Schema.DockerRepository) => Option(fun)
		given putSchemaMavenRepository: Conversion[Schema.MavenRepository, Option[Schema.MavenRepository]] = (fun: Schema.MavenRepository) => Option(fun)
		given putSchemaNpmRepository: Conversion[Schema.NpmRepository, Option[Schema.NpmRepository]] = (fun: Schema.NpmRepository) => Option(fun)
		given putSchemaPythonRepository: Conversion[Schema.PythonRepository, Option[Schema.PythonRepository]] = (fun: Schema.PythonRepository) => Option(fun)
		given putSchemaAptRepository: Conversion[Schema.AptRepository, Option[Schema.AptRepository]] = (fun: Schema.AptRepository) => Option(fun)
		given putSchemaYumRepository: Conversion[Schema.YumRepository, Option[Schema.YumRepository]] = (fun: Schema.YumRepository) => Option(fun)
		given putSchemaCommonRemoteRepository: Conversion[Schema.CommonRemoteRepository, Option[Schema.CommonRemoteRepository]] = (fun: Schema.CommonRemoteRepository) => Option(fun)
		given putSchemaUpstreamCredentials: Conversion[Schema.UpstreamCredentials, Option[Schema.UpstreamCredentials]] = (fun: Schema.UpstreamCredentials) => Option(fun)
		given putSchemaDockerRepositoryPublicRepositoryEnum: Conversion[Schema.DockerRepository.PublicRepositoryEnum, Option[Schema.DockerRepository.PublicRepositoryEnum]] = (fun: Schema.DockerRepository.PublicRepositoryEnum) => Option(fun)
		given putSchemaGoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigDockerRepositoryCustomRepository: Conversion[Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigDockerRepositoryCustomRepository, Option[Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigDockerRepositoryCustomRepository]] = (fun: Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigDockerRepositoryCustomRepository) => Option(fun)
		given putSchemaMavenRepositoryPublicRepositoryEnum: Conversion[Schema.MavenRepository.PublicRepositoryEnum, Option[Schema.MavenRepository.PublicRepositoryEnum]] = (fun: Schema.MavenRepository.PublicRepositoryEnum) => Option(fun)
		given putSchemaGoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigMavenRepositoryCustomRepository: Conversion[Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigMavenRepositoryCustomRepository, Option[Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigMavenRepositoryCustomRepository]] = (fun: Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigMavenRepositoryCustomRepository) => Option(fun)
		given putSchemaNpmRepositoryPublicRepositoryEnum: Conversion[Schema.NpmRepository.PublicRepositoryEnum, Option[Schema.NpmRepository.PublicRepositoryEnum]] = (fun: Schema.NpmRepository.PublicRepositoryEnum) => Option(fun)
		given putSchemaGoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigNpmRepositoryCustomRepository: Conversion[Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigNpmRepositoryCustomRepository, Option[Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigNpmRepositoryCustomRepository]] = (fun: Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigNpmRepositoryCustomRepository) => Option(fun)
		given putSchemaPythonRepositoryPublicRepositoryEnum: Conversion[Schema.PythonRepository.PublicRepositoryEnum, Option[Schema.PythonRepository.PublicRepositoryEnum]] = (fun: Schema.PythonRepository.PublicRepositoryEnum) => Option(fun)
		given putSchemaGoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigPythonRepositoryCustomRepository: Conversion[Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigPythonRepositoryCustomRepository, Option[Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigPythonRepositoryCustomRepository]] = (fun: Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigPythonRepositoryCustomRepository) => Option(fun)
		given putSchemaGoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigAptRepositoryPublicRepository: Conversion[Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigAptRepositoryPublicRepository, Option[Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigAptRepositoryPublicRepository]] = (fun: Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigAptRepositoryPublicRepository) => Option(fun)
		given putSchemaGoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigAptRepositoryCustomRepository: Conversion[Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigAptRepositoryCustomRepository, Option[Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigAptRepositoryCustomRepository]] = (fun: Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigAptRepositoryCustomRepository) => Option(fun)
		given putSchemaGoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigAptRepositoryPublicRepositoryRepositoryBaseEnum: Conversion[Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigAptRepositoryPublicRepository.RepositoryBaseEnum, Option[Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigAptRepositoryPublicRepository.RepositoryBaseEnum]] = (fun: Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigAptRepositoryPublicRepository.RepositoryBaseEnum) => Option(fun)
		given putSchemaGoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigYumRepositoryPublicRepository: Conversion[Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigYumRepositoryPublicRepository, Option[Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigYumRepositoryPublicRepository]] = (fun: Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigYumRepositoryPublicRepository) => Option(fun)
		given putSchemaGoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigYumRepositoryCustomRepository: Conversion[Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigYumRepositoryCustomRepository, Option[Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigYumRepositoryCustomRepository]] = (fun: Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigYumRepositoryCustomRepository) => Option(fun)
		given putSchemaGoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigYumRepositoryPublicRepositoryRepositoryBaseEnum: Conversion[Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigYumRepositoryPublicRepository.RepositoryBaseEnum, Option[Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigYumRepositoryPublicRepository.RepositoryBaseEnum]] = (fun: Schema.GoogleDevtoolsArtifactregistryV1RemoteRepositoryConfigYumRepositoryPublicRepository.RepositoryBaseEnum) => Option(fun)
		given putSchemaUsernamePasswordCredentials: Conversion[Schema.UsernamePasswordCredentials, Option[Schema.UsernamePasswordCredentials]] = (fun: Schema.UsernamePasswordCredentials) => Option(fun)
		given putSchemaCleanupPolicyCondition: Conversion[Schema.CleanupPolicyCondition, Option[Schema.CleanupPolicyCondition]] = (fun: Schema.CleanupPolicyCondition) => Option(fun)
		given putSchemaCleanupPolicyMostRecentVersions: Conversion[Schema.CleanupPolicyMostRecentVersions, Option[Schema.CleanupPolicyMostRecentVersions]] = (fun: Schema.CleanupPolicyMostRecentVersions) => Option(fun)
		given putSchemaCleanupPolicyActionEnum: Conversion[Schema.CleanupPolicy.ActionEnum, Option[Schema.CleanupPolicy.ActionEnum]] = (fun: Schema.CleanupPolicy.ActionEnum) => Option(fun)
		given putSchemaCleanupPolicyConditionTagStateEnum: Conversion[Schema.CleanupPolicyCondition.TagStateEnum, Option[Schema.CleanupPolicyCondition.TagStateEnum]] = (fun: Schema.CleanupPolicyCondition.TagStateEnum) => Option(fun)
		given putSchemaVulnerabilityScanningConfigEnablementConfigEnum: Conversion[Schema.VulnerabilityScanningConfig.EnablementConfigEnum, Option[Schema.VulnerabilityScanningConfig.EnablementConfigEnum]] = (fun: Schema.VulnerabilityScanningConfig.EnablementConfigEnum) => Option(fun)
		given putSchemaVulnerabilityScanningConfigEnablementStateEnum: Conversion[Schema.VulnerabilityScanningConfig.EnablementStateEnum, Option[Schema.VulnerabilityScanningConfig.EnablementStateEnum]] = (fun: Schema.VulnerabilityScanningConfig.EnablementStateEnum) => Option(fun)
		given putListSchemaPackage: Conversion[List[Schema.Package], Option[List[Schema.Package]]] = (fun: List[Schema.Package]) => Option(fun)
		given putListSchemaVersion: Conversion[List[Schema.Version], Option[List[Schema.Version]]] = (fun: List[Schema.Version]) => Option(fun)
		given putListSchemaTag: Conversion[List[Schema.Tag], Option[List[Schema.Tag]]] = (fun: List[Schema.Tag]) => Option(fun)
		given putListSchemaGoogleDevtoolsArtifactregistryV1File: Conversion[List[Schema.GoogleDevtoolsArtifactregistryV1File], Option[List[Schema.GoogleDevtoolsArtifactregistryV1File]]] = (fun: List[Schema.GoogleDevtoolsArtifactregistryV1File]) => Option(fun)
		given putListSchemaHash: Conversion[List[Schema.Hash], Option[List[Schema.Hash]]] = (fun: List[Schema.Hash]) => Option(fun)
		given putSchemaHashTypeEnum: Conversion[Schema.Hash.TypeEnum, Option[Schema.Hash.TypeEnum]] = (fun: Schema.Hash.TypeEnum) => Option(fun)
		given putSchemaGoogleDevtoolsArtifactregistryV1RuleActionEnum: Conversion[Schema.GoogleDevtoolsArtifactregistryV1Rule.ActionEnum, Option[Schema.GoogleDevtoolsArtifactregistryV1Rule.ActionEnum]] = (fun: Schema.GoogleDevtoolsArtifactregistryV1Rule.ActionEnum) => Option(fun)
		given putSchemaGoogleDevtoolsArtifactregistryV1RuleOperationEnum: Conversion[Schema.GoogleDevtoolsArtifactregistryV1Rule.OperationEnum, Option[Schema.GoogleDevtoolsArtifactregistryV1Rule.OperationEnum]] = (fun: Schema.GoogleDevtoolsArtifactregistryV1Rule.OperationEnum) => Option(fun)
		given putSchemaExpr: Conversion[Schema.Expr, Option[Schema.Expr]] = (fun: Schema.Expr) => Option(fun)
		given putListSchemaGoogleDevtoolsArtifactregistryV1Rule: Conversion[List[Schema.GoogleDevtoolsArtifactregistryV1Rule], Option[List[Schema.GoogleDevtoolsArtifactregistryV1Rule]]] = (fun: List[Schema.GoogleDevtoolsArtifactregistryV1Rule]) => Option(fun)
		given putSchemaPolicy: Conversion[Schema.Policy, Option[Schema.Policy]] = (fun: Schema.Policy) => Option(fun)
		given putListSchemaBinding: Conversion[List[Schema.Binding], Option[List[Schema.Binding]]] = (fun: List[Schema.Binding]) => Option(fun)
		given putSchemaProjectSettingsLegacyRedirectionStateEnum: Conversion[Schema.ProjectSettings.LegacyRedirectionStateEnum, Option[Schema.ProjectSettings.LegacyRedirectionStateEnum]] = (fun: Schema.ProjectSettings.LegacyRedirectionStateEnum) => Option(fun)
		given putSchemaVPCSCConfigVpcscPolicyEnum: Conversion[Schema.VPCSCConfig.VpcscPolicyEnum, Option[Schema.VPCSCConfig.VpcscPolicyEnum]] = (fun: Schema.VPCSCConfig.VpcscPolicyEnum) => Option(fun)
		given putListSchemaAttachment: Conversion[List[Schema.Attachment], Option[List[Schema.Attachment]]] = (fun: List[Schema.Attachment]) => Option(fun)
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
		given putListSchemaAptArtifact: Conversion[List[Schema.AptArtifact], Option[List[Schema.AptArtifact]]] = (fun: List[Schema.AptArtifact]) => Option(fun)
		given putListSchemaImportAptArtifactsErrorInfo: Conversion[List[Schema.ImportAptArtifactsErrorInfo], Option[List[Schema.ImportAptArtifactsErrorInfo]]] = (fun: List[Schema.ImportAptArtifactsErrorInfo]) => Option(fun)
		given putSchemaAptArtifactPackageTypeEnum: Conversion[Schema.AptArtifact.PackageTypeEnum, Option[Schema.AptArtifact.PackageTypeEnum]] = (fun: Schema.AptArtifact.PackageTypeEnum) => Option(fun)
		given putListSchemaYumArtifact: Conversion[List[Schema.YumArtifact], Option[List[Schema.YumArtifact]]] = (fun: List[Schema.YumArtifact]) => Option(fun)
		given putListSchemaImportYumArtifactsErrorInfo: Conversion[List[Schema.ImportYumArtifactsErrorInfo], Option[List[Schema.ImportYumArtifactsErrorInfo]]] = (fun: List[Schema.ImportYumArtifactsErrorInfo]) => Option(fun)
		given putSchemaYumArtifactPackageTypeEnum: Conversion[Schema.YumArtifact.PackageTypeEnum, Option[Schema.YumArtifact.PackageTypeEnum]] = (fun: Schema.YumArtifact.PackageTypeEnum) => Option(fun)
		given putListSchemaGoogetArtifact: Conversion[List[Schema.GoogetArtifact], Option[List[Schema.GoogetArtifact]]] = (fun: List[Schema.GoogetArtifact]) => Option(fun)
		given putListSchemaImportGoogetArtifactsErrorInfo: Conversion[List[Schema.ImportGoogetArtifactsErrorInfo], Option[List[Schema.ImportGoogetArtifactsErrorInfo]]] = (fun: List[Schema.ImportGoogetArtifactsErrorInfo]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
