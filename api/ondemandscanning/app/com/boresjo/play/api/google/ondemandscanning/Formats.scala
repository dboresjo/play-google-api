package com.boresjo.play.api.google.ondemandscanning

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtListOperationsResponse: Format[Schema.ListOperationsResponse] = Json.format[Schema.ListOperationsResponse]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtAnalyzePackagesRequestV1: Format[Schema.AnalyzePackagesRequestV1] = Json.format[Schema.AnalyzePackagesRequestV1]
	given fmtPackageData: Format[Schema.PackageData] = Json.format[Schema.PackageData]
	given fmtPackageDataPackageTypeEnum: Format[Schema.PackageData.PackageTypeEnum] = JsonEnumFormat[Schema.PackageData.PackageTypeEnum]
	given fmtFileLocation: Format[Schema.FileLocation] = Json.format[Schema.FileLocation]
	given fmtLanguagePackageDependency: Format[Schema.LanguagePackageDependency] = Json.format[Schema.LanguagePackageDependency]
	given fmtMaintainer: Format[Schema.Maintainer] = Json.format[Schema.Maintainer]
	given fmtPackageVersion: Format[Schema.PackageVersion] = Json.format[Schema.PackageVersion]
	given fmtBinarySourceInfo: Format[Schema.BinarySourceInfo] = Json.format[Schema.BinarySourceInfo]
	given fmtListVulnerabilitiesResponseV1: Format[Schema.ListVulnerabilitiesResponseV1] = Json.format[Schema.ListVulnerabilitiesResponseV1]
	given fmtOccurrence: Format[Schema.Occurrence] = Json.format[Schema.Occurrence]
	given fmtOccurrenceKindEnum: Format[Schema.Occurrence.KindEnum] = JsonEnumFormat[Schema.Occurrence.KindEnum]
	given fmtVulnerabilityOccurrence: Format[Schema.VulnerabilityOccurrence] = Json.format[Schema.VulnerabilityOccurrence]
	given fmtBuildOccurrence: Format[Schema.BuildOccurrence] = Json.format[Schema.BuildOccurrence]
	given fmtImageOccurrence: Format[Schema.ImageOccurrence] = Json.format[Schema.ImageOccurrence]
	given fmtPackageOccurrence: Format[Schema.PackageOccurrence] = Json.format[Schema.PackageOccurrence]
	given fmtDeploymentOccurrence: Format[Schema.DeploymentOccurrence] = Json.format[Schema.DeploymentOccurrence]
	given fmtDiscoveryOccurrence: Format[Schema.DiscoveryOccurrence] = Json.format[Schema.DiscoveryOccurrence]
	given fmtAttestationOccurrence: Format[Schema.AttestationOccurrence] = Json.format[Schema.AttestationOccurrence]
	given fmtUpgradeOccurrence: Format[Schema.UpgradeOccurrence] = Json.format[Schema.UpgradeOccurrence]
	given fmtComplianceOccurrence: Format[Schema.ComplianceOccurrence] = Json.format[Schema.ComplianceOccurrence]
	given fmtDSSEAttestationOccurrence: Format[Schema.DSSEAttestationOccurrence] = Json.format[Schema.DSSEAttestationOccurrence]
	given fmtSBOMReferenceOccurrence: Format[Schema.SBOMReferenceOccurrence] = Json.format[Schema.SBOMReferenceOccurrence]
	given fmtEnvelope: Format[Schema.Envelope] = Json.format[Schema.Envelope]
	given fmtVulnerabilityOccurrenceSeverityEnum: Format[Schema.VulnerabilityOccurrence.SeverityEnum] = JsonEnumFormat[Schema.VulnerabilityOccurrence.SeverityEnum]
	given fmtCVSS: Format[Schema.CVSS] = Json.format[Schema.CVSS]
	given fmtPackageIssue: Format[Schema.PackageIssue] = Json.format[Schema.PackageIssue]
	given fmtRelatedUrl: Format[Schema.RelatedUrl] = Json.format[Schema.RelatedUrl]
	given fmtVulnerabilityOccurrenceEffectiveSeverityEnum: Format[Schema.VulnerabilityOccurrence.EffectiveSeverityEnum] = JsonEnumFormat[Schema.VulnerabilityOccurrence.EffectiveSeverityEnum]
	given fmtVulnerabilityOccurrenceCvssVersionEnum: Format[Schema.VulnerabilityOccurrence.CvssVersionEnum] = JsonEnumFormat[Schema.VulnerabilityOccurrence.CvssVersionEnum]
	given fmtVexAssessment: Format[Schema.VexAssessment] = Json.format[Schema.VexAssessment]
	given fmtCVSSAttackVectorEnum: Format[Schema.CVSS.AttackVectorEnum] = JsonEnumFormat[Schema.CVSS.AttackVectorEnum]
	given fmtCVSSAttackComplexityEnum: Format[Schema.CVSS.AttackComplexityEnum] = JsonEnumFormat[Schema.CVSS.AttackComplexityEnum]
	given fmtCVSSAuthenticationEnum: Format[Schema.CVSS.AuthenticationEnum] = JsonEnumFormat[Schema.CVSS.AuthenticationEnum]
	given fmtCVSSPrivilegesRequiredEnum: Format[Schema.CVSS.PrivilegesRequiredEnum] = JsonEnumFormat[Schema.CVSS.PrivilegesRequiredEnum]
	given fmtCVSSUserInteractionEnum: Format[Schema.CVSS.UserInteractionEnum] = JsonEnumFormat[Schema.CVSS.UserInteractionEnum]
	given fmtCVSSScopeEnum: Format[Schema.CVSS.ScopeEnum] = JsonEnumFormat[Schema.CVSS.ScopeEnum]
	given fmtCVSSConfidentialityImpactEnum: Format[Schema.CVSS.ConfidentialityImpactEnum] = JsonEnumFormat[Schema.CVSS.ConfidentialityImpactEnum]
	given fmtCVSSIntegrityImpactEnum: Format[Schema.CVSS.IntegrityImpactEnum] = JsonEnumFormat[Schema.CVSS.IntegrityImpactEnum]
	given fmtCVSSAvailabilityImpactEnum: Format[Schema.CVSS.AvailabilityImpactEnum] = JsonEnumFormat[Schema.CVSS.AvailabilityImpactEnum]
	given fmtVersion: Format[Schema.Version] = Json.format[Schema.Version]
	given fmtPackageIssueEffectiveSeverityEnum: Format[Schema.PackageIssue.EffectiveSeverityEnum] = JsonEnumFormat[Schema.PackageIssue.EffectiveSeverityEnum]
	given fmtGrafeasV1FileLocation: Format[Schema.GrafeasV1FileLocation] = Json.format[Schema.GrafeasV1FileLocation]
	given fmtVersionKindEnum: Format[Schema.Version.KindEnum] = JsonEnumFormat[Schema.Version.KindEnum]
	given fmtVexAssessmentStateEnum: Format[Schema.VexAssessment.StateEnum] = JsonEnumFormat[Schema.VexAssessment.StateEnum]
	given fmtRemediation: Format[Schema.Remediation] = Json.format[Schema.Remediation]
	given fmtJustification: Format[Schema.Justification] = Json.format[Schema.Justification]
	given fmtRemediationRemediationTypeEnum: Format[Schema.Remediation.RemediationTypeEnum] = JsonEnumFormat[Schema.Remediation.RemediationTypeEnum]
	given fmtJustificationJustificationTypeEnum: Format[Schema.Justification.JustificationTypeEnum] = JsonEnumFormat[Schema.Justification.JustificationTypeEnum]
	given fmtBuildProvenance: Format[Schema.BuildProvenance] = Json.format[Schema.BuildProvenance]
	given fmtInTotoProvenance: Format[Schema.InTotoProvenance] = Json.format[Schema.InTotoProvenance]
	given fmtInTotoStatement: Format[Schema.InTotoStatement] = Json.format[Schema.InTotoStatement]
	given fmtInTotoSlsaProvenanceV1: Format[Schema.InTotoSlsaProvenanceV1] = Json.format[Schema.InTotoSlsaProvenanceV1]
	given fmtCommand: Format[Schema.Command] = Json.format[Schema.Command]
	given fmtArtifact: Format[Schema.Artifact] = Json.format[Schema.Artifact]
	given fmtSource: Format[Schema.Source] = Json.format[Schema.Source]
	given fmtFileHashes: Format[Schema.FileHashes] = Json.format[Schema.FileHashes]
	given fmtSourceContext: Format[Schema.SourceContext] = Json.format[Schema.SourceContext]
	given fmtHash: Format[Schema.Hash] = Json.format[Schema.Hash]
	given fmtCloudRepoSourceContext: Format[Schema.CloudRepoSourceContext] = Json.format[Schema.CloudRepoSourceContext]
	given fmtGerritSourceContext: Format[Schema.GerritSourceContext] = Json.format[Schema.GerritSourceContext]
	given fmtGitSourceContext: Format[Schema.GitSourceContext] = Json.format[Schema.GitSourceContext]
	given fmtRepoId: Format[Schema.RepoId] = Json.format[Schema.RepoId]
	given fmtAliasContext: Format[Schema.AliasContext] = Json.format[Schema.AliasContext]
	given fmtProjectRepoId: Format[Schema.ProjectRepoId] = Json.format[Schema.ProjectRepoId]
	given fmtAliasContextKindEnum: Format[Schema.AliasContext.KindEnum] = JsonEnumFormat[Schema.AliasContext.KindEnum]
	given fmtBuilderConfig: Format[Schema.BuilderConfig] = Json.format[Schema.BuilderConfig]
	given fmtRecipe: Format[Schema.Recipe] = Json.format[Schema.Recipe]
	given fmtMetadata: Format[Schema.Metadata] = Json.format[Schema.Metadata]
	given fmtCompleteness: Format[Schema.Completeness] = Json.format[Schema.Completeness]
	given fmtSubject: Format[Schema.Subject] = Json.format[Schema.Subject]
	given fmtSlsaProvenance: Format[Schema.SlsaProvenance] = Json.format[Schema.SlsaProvenance]
	given fmtSlsaProvenanceZeroTwo: Format[Schema.SlsaProvenanceZeroTwo] = Json.format[Schema.SlsaProvenanceZeroTwo]
	given fmtSlsaBuilder: Format[Schema.SlsaBuilder] = Json.format[Schema.SlsaBuilder]
	given fmtSlsaRecipe: Format[Schema.SlsaRecipe] = Json.format[Schema.SlsaRecipe]
	given fmtSlsaMetadata: Format[Schema.SlsaMetadata] = Json.format[Schema.SlsaMetadata]
	given fmtMaterial: Format[Schema.Material] = Json.format[Schema.Material]
	given fmtSlsaCompleteness: Format[Schema.SlsaCompleteness] = Json.format[Schema.SlsaCompleteness]
	given fmtGrafeasV1SlsaProvenanceZeroTwoSlsaBuilder: Format[Schema.GrafeasV1SlsaProvenanceZeroTwoSlsaBuilder] = Json.format[Schema.GrafeasV1SlsaProvenanceZeroTwoSlsaBuilder]
	given fmtGrafeasV1SlsaProvenanceZeroTwoSlsaInvocation: Format[Schema.GrafeasV1SlsaProvenanceZeroTwoSlsaInvocation] = Json.format[Schema.GrafeasV1SlsaProvenanceZeroTwoSlsaInvocation]
	given fmtGrafeasV1SlsaProvenanceZeroTwoSlsaMetadata: Format[Schema.GrafeasV1SlsaProvenanceZeroTwoSlsaMetadata] = Json.format[Schema.GrafeasV1SlsaProvenanceZeroTwoSlsaMetadata]
	given fmtGrafeasV1SlsaProvenanceZeroTwoSlsaMaterial: Format[Schema.GrafeasV1SlsaProvenanceZeroTwoSlsaMaterial] = Json.format[Schema.GrafeasV1SlsaProvenanceZeroTwoSlsaMaterial]
	given fmtGrafeasV1SlsaProvenanceZeroTwoSlsaConfigSource: Format[Schema.GrafeasV1SlsaProvenanceZeroTwoSlsaConfigSource] = Json.format[Schema.GrafeasV1SlsaProvenanceZeroTwoSlsaConfigSource]
	given fmtGrafeasV1SlsaProvenanceZeroTwoSlsaCompleteness: Format[Schema.GrafeasV1SlsaProvenanceZeroTwoSlsaCompleteness] = Json.format[Schema.GrafeasV1SlsaProvenanceZeroTwoSlsaCompleteness]
	given fmtSlsaProvenanceV1: Format[Schema.SlsaProvenanceV1] = Json.format[Schema.SlsaProvenanceV1]
	given fmtBuildDefinition: Format[Schema.BuildDefinition] = Json.format[Schema.BuildDefinition]
	given fmtRunDetails: Format[Schema.RunDetails] = Json.format[Schema.RunDetails]
	given fmtResourceDescriptor: Format[Schema.ResourceDescriptor] = Json.format[Schema.ResourceDescriptor]
	given fmtProvenanceBuilder: Format[Schema.ProvenanceBuilder] = Json.format[Schema.ProvenanceBuilder]
	given fmtBuildMetadata: Format[Schema.BuildMetadata] = Json.format[Schema.BuildMetadata]
	given fmtFingerprint: Format[Schema.Fingerprint] = Json.format[Schema.Fingerprint]
	given fmtLayer: Format[Schema.Layer] = Json.format[Schema.Layer]
	given fmtLocation: Format[Schema.Location] = Json.format[Schema.Location]
	given fmtPackageOccurrenceArchitectureEnum: Format[Schema.PackageOccurrence.ArchitectureEnum] = JsonEnumFormat[Schema.PackageOccurrence.ArchitectureEnum]
	given fmtLicense: Format[Schema.License] = Json.format[Schema.License]
	given fmtDeploymentOccurrencePlatformEnum: Format[Schema.DeploymentOccurrence.PlatformEnum] = JsonEnumFormat[Schema.DeploymentOccurrence.PlatformEnum]
	given fmtDiscoveryOccurrenceContinuousAnalysisEnum: Format[Schema.DiscoveryOccurrence.ContinuousAnalysisEnum] = JsonEnumFormat[Schema.DiscoveryOccurrence.ContinuousAnalysisEnum]
	given fmtDiscoveryOccurrenceAnalysisStatusEnum: Format[Schema.DiscoveryOccurrence.AnalysisStatusEnum] = JsonEnumFormat[Schema.DiscoveryOccurrence.AnalysisStatusEnum]
	given fmtAnalysisCompleted: Format[Schema.AnalysisCompleted] = Json.format[Schema.AnalysisCompleted]
	given fmtSBOMStatus: Format[Schema.SBOMStatus] = Json.format[Schema.SBOMStatus]
	given fmtSBOMStatusSbomStateEnum: Format[Schema.SBOMStatus.SbomStateEnum] = JsonEnumFormat[Schema.SBOMStatus.SbomStateEnum]
	given fmtSignature: Format[Schema.Signature] = Json.format[Schema.Signature]
	given fmtJwt: Format[Schema.Jwt] = Json.format[Schema.Jwt]
	given fmtUpgradeDistribution: Format[Schema.UpgradeDistribution] = Json.format[Schema.UpgradeDistribution]
	given fmtWindowsUpdate: Format[Schema.WindowsUpdate] = Json.format[Schema.WindowsUpdate]
	given fmtIdentity: Format[Schema.Identity] = Json.format[Schema.Identity]
	given fmtCategory: Format[Schema.Category] = Json.format[Schema.Category]
	given fmtNonCompliantFile: Format[Schema.NonCompliantFile] = Json.format[Schema.NonCompliantFile]
	given fmtComplianceVersion: Format[Schema.ComplianceVersion] = Json.format[Schema.ComplianceVersion]
	given fmtEnvelopeSignature: Format[Schema.EnvelopeSignature] = Json.format[Schema.EnvelopeSignature]
	given fmtSbomReferenceIntotoPayload: Format[Schema.SbomReferenceIntotoPayload] = Json.format[Schema.SbomReferenceIntotoPayload]
	given fmtSbomReferenceIntotoPredicate: Format[Schema.SbomReferenceIntotoPredicate] = Json.format[Schema.SbomReferenceIntotoPredicate]
	given fmtAnalyzePackagesMetadataV1: Format[Schema.AnalyzePackagesMetadataV1] = Json.format[Schema.AnalyzePackagesMetadataV1]
	given fmtAnalyzePackagesResponseV1: Format[Schema.AnalyzePackagesResponseV1] = Json.format[Schema.AnalyzePackagesResponseV1]
	given fmtAnalyzePackagesMetadata: Format[Schema.AnalyzePackagesMetadata] = Json.format[Schema.AnalyzePackagesMetadata]
	given fmtAnalyzePackagesResponse: Format[Schema.AnalyzePackagesResponse] = Json.format[Schema.AnalyzePackagesResponse]
}
