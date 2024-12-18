package com.boresjo.play.api.google.run

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtGoogleCloudRunV2Metadata: Format[Schema.GoogleCloudRunV2Metadata] = Json.format[Schema.GoogleCloudRunV2Metadata]
	given fmtGoogleCloudRunV2ExportImageRequest: Format[Schema.GoogleCloudRunV2ExportImageRequest] = Json.format[Schema.GoogleCloudRunV2ExportImageRequest]
	given fmtGoogleCloudRunV2ExportImageResponse: Format[Schema.GoogleCloudRunV2ExportImageResponse] = Json.format[Schema.GoogleCloudRunV2ExportImageResponse]
	given fmtGoogleCloudRunV2ExportStatusResponse: Format[Schema.GoogleCloudRunV2ExportStatusResponse] = Json.format[Schema.GoogleCloudRunV2ExportStatusResponse]
	given fmtGoogleCloudRunV2ExportStatusResponseOperationStateEnum: Format[Schema.GoogleCloudRunV2ExportStatusResponse.OperationStateEnum] = JsonEnumFormat[Schema.GoogleCloudRunV2ExportStatusResponse.OperationStateEnum]
	given fmtGoogleCloudRunV2ImageExportStatus: Format[Schema.GoogleCloudRunV2ImageExportStatus] = Json.format[Schema.GoogleCloudRunV2ImageExportStatus]
	given fmtGoogleCloudRunV2ImageExportStatusExportJobStateEnum: Format[Schema.GoogleCloudRunV2ImageExportStatus.ExportJobStateEnum] = JsonEnumFormat[Schema.GoogleCloudRunV2ImageExportStatus.ExportJobStateEnum]
	given fmtUtilStatusProto: Format[Schema.UtilStatusProto] = Json.format[Schema.UtilStatusProto]
	given fmtProto2BridgeMessageSet: Format[Schema.Proto2BridgeMessageSet] = Json.format[Schema.Proto2BridgeMessageSet]
	given fmtGoogleLongrunningListOperationsResponse: Format[Schema.GoogleLongrunningListOperationsResponse] = Json.format[Schema.GoogleLongrunningListOperationsResponse]
	given fmtGoogleLongrunningOperation: Format[Schema.GoogleLongrunningOperation] = Json.format[Schema.GoogleLongrunningOperation]
	given fmtGoogleRpcStatus: Format[Schema.GoogleRpcStatus] = Json.format[Schema.GoogleRpcStatus]
	given fmtGoogleProtobufEmpty: Format[Schema.GoogleProtobufEmpty] = Json.format[Schema.GoogleProtobufEmpty]
	given fmtGoogleLongrunningWaitOperationRequest: Format[Schema.GoogleLongrunningWaitOperationRequest] = Json.format[Schema.GoogleLongrunningWaitOperationRequest]
	given fmtGoogleCloudRunV2SubmitBuildRequest: Format[Schema.GoogleCloudRunV2SubmitBuildRequest] = Json.format[Schema.GoogleCloudRunV2SubmitBuildRequest]
	given fmtGoogleCloudRunV2StorageSource: Format[Schema.GoogleCloudRunV2StorageSource] = Json.format[Schema.GoogleCloudRunV2StorageSource]
	given fmtGoogleCloudRunV2BuildpacksBuild: Format[Schema.GoogleCloudRunV2BuildpacksBuild] = Json.format[Schema.GoogleCloudRunV2BuildpacksBuild]
	given fmtGoogleCloudRunV2DockerBuild: Format[Schema.GoogleCloudRunV2DockerBuild] = Json.format[Schema.GoogleCloudRunV2DockerBuild]
	given fmtGoogleCloudRunV2SubmitBuildResponse: Format[Schema.GoogleCloudRunV2SubmitBuildResponse] = Json.format[Schema.GoogleCloudRunV2SubmitBuildResponse]
	given fmtGoogleCloudRunV2Execution: Format[Schema.GoogleCloudRunV2Execution] = Json.format[Schema.GoogleCloudRunV2Execution]
	given fmtGoogleCloudRunV2ExecutionLaunchStageEnum: Format[Schema.GoogleCloudRunV2Execution.LaunchStageEnum] = JsonEnumFormat[Schema.GoogleCloudRunV2Execution.LaunchStageEnum]
	given fmtGoogleCloudRunV2TaskTemplate: Format[Schema.GoogleCloudRunV2TaskTemplate] = Json.format[Schema.GoogleCloudRunV2TaskTemplate]
	given fmtGoogleCloudRunV2Condition: Format[Schema.GoogleCloudRunV2Condition] = Json.format[Schema.GoogleCloudRunV2Condition]
	given fmtGoogleCloudRunV2Container: Format[Schema.GoogleCloudRunV2Container] = Json.format[Schema.GoogleCloudRunV2Container]
	given fmtGoogleCloudRunV2Volume: Format[Schema.GoogleCloudRunV2Volume] = Json.format[Schema.GoogleCloudRunV2Volume]
	given fmtGoogleCloudRunV2TaskTemplateExecutionEnvironmentEnum: Format[Schema.GoogleCloudRunV2TaskTemplate.ExecutionEnvironmentEnum] = JsonEnumFormat[Schema.GoogleCloudRunV2TaskTemplate.ExecutionEnvironmentEnum]
	given fmtGoogleCloudRunV2VpcAccess: Format[Schema.GoogleCloudRunV2VpcAccess] = Json.format[Schema.GoogleCloudRunV2VpcAccess]
	given fmtGoogleCloudRunV2EnvVar: Format[Schema.GoogleCloudRunV2EnvVar] = Json.format[Schema.GoogleCloudRunV2EnvVar]
	given fmtGoogleCloudRunV2ResourceRequirements: Format[Schema.GoogleCloudRunV2ResourceRequirements] = Json.format[Schema.GoogleCloudRunV2ResourceRequirements]
	given fmtGoogleCloudRunV2ContainerPort: Format[Schema.GoogleCloudRunV2ContainerPort] = Json.format[Schema.GoogleCloudRunV2ContainerPort]
	given fmtGoogleCloudRunV2VolumeMount: Format[Schema.GoogleCloudRunV2VolumeMount] = Json.format[Schema.GoogleCloudRunV2VolumeMount]
	given fmtGoogleCloudRunV2Probe: Format[Schema.GoogleCloudRunV2Probe] = Json.format[Schema.GoogleCloudRunV2Probe]
	given fmtGoogleCloudRunV2EnvVarSource: Format[Schema.GoogleCloudRunV2EnvVarSource] = Json.format[Schema.GoogleCloudRunV2EnvVarSource]
	given fmtGoogleCloudRunV2SecretKeySelector: Format[Schema.GoogleCloudRunV2SecretKeySelector] = Json.format[Schema.GoogleCloudRunV2SecretKeySelector]
	given fmtGoogleCloudRunV2HTTPGetAction: Format[Schema.GoogleCloudRunV2HTTPGetAction] = Json.format[Schema.GoogleCloudRunV2HTTPGetAction]
	given fmtGoogleCloudRunV2TCPSocketAction: Format[Schema.GoogleCloudRunV2TCPSocketAction] = Json.format[Schema.GoogleCloudRunV2TCPSocketAction]
	given fmtGoogleCloudRunV2GRPCAction: Format[Schema.GoogleCloudRunV2GRPCAction] = Json.format[Schema.GoogleCloudRunV2GRPCAction]
	given fmtGoogleCloudRunV2HTTPHeader: Format[Schema.GoogleCloudRunV2HTTPHeader] = Json.format[Schema.GoogleCloudRunV2HTTPHeader]
	given fmtGoogleCloudRunV2SecretVolumeSource: Format[Schema.GoogleCloudRunV2SecretVolumeSource] = Json.format[Schema.GoogleCloudRunV2SecretVolumeSource]
	given fmtGoogleCloudRunV2CloudSqlInstance: Format[Schema.GoogleCloudRunV2CloudSqlInstance] = Json.format[Schema.GoogleCloudRunV2CloudSqlInstance]
	given fmtGoogleCloudRunV2EmptyDirVolumeSource: Format[Schema.GoogleCloudRunV2EmptyDirVolumeSource] = Json.format[Schema.GoogleCloudRunV2EmptyDirVolumeSource]
	given fmtGoogleCloudRunV2NFSVolumeSource: Format[Schema.GoogleCloudRunV2NFSVolumeSource] = Json.format[Schema.GoogleCloudRunV2NFSVolumeSource]
	given fmtGoogleCloudRunV2GCSVolumeSource: Format[Schema.GoogleCloudRunV2GCSVolumeSource] = Json.format[Schema.GoogleCloudRunV2GCSVolumeSource]
	given fmtGoogleCloudRunV2VersionToPath: Format[Schema.GoogleCloudRunV2VersionToPath] = Json.format[Schema.GoogleCloudRunV2VersionToPath]
	given fmtGoogleCloudRunV2EmptyDirVolumeSourceMediumEnum: Format[Schema.GoogleCloudRunV2EmptyDirVolumeSource.MediumEnum] = JsonEnumFormat[Schema.GoogleCloudRunV2EmptyDirVolumeSource.MediumEnum]
	given fmtGoogleCloudRunV2VpcAccessEgressEnum: Format[Schema.GoogleCloudRunV2VpcAccess.EgressEnum] = JsonEnumFormat[Schema.GoogleCloudRunV2VpcAccess.EgressEnum]
	given fmtGoogleCloudRunV2NetworkInterface: Format[Schema.GoogleCloudRunV2NetworkInterface] = Json.format[Schema.GoogleCloudRunV2NetworkInterface]
	given fmtGoogleCloudRunV2ConditionStateEnum: Format[Schema.GoogleCloudRunV2Condition.StateEnum] = JsonEnumFormat[Schema.GoogleCloudRunV2Condition.StateEnum]
	given fmtGoogleCloudRunV2ConditionSeverityEnum: Format[Schema.GoogleCloudRunV2Condition.SeverityEnum] = JsonEnumFormat[Schema.GoogleCloudRunV2Condition.SeverityEnum]
	given fmtGoogleCloudRunV2ConditionReasonEnum: Format[Schema.GoogleCloudRunV2Condition.ReasonEnum] = JsonEnumFormat[Schema.GoogleCloudRunV2Condition.ReasonEnum]
	given fmtGoogleCloudRunV2ConditionRevisionReasonEnum: Format[Schema.GoogleCloudRunV2Condition.RevisionReasonEnum] = JsonEnumFormat[Schema.GoogleCloudRunV2Condition.RevisionReasonEnum]
	given fmtGoogleCloudRunV2ConditionExecutionReasonEnum: Format[Schema.GoogleCloudRunV2Condition.ExecutionReasonEnum] = JsonEnumFormat[Schema.GoogleCloudRunV2Condition.ExecutionReasonEnum]
	given fmtGoogleCloudRunV2ListExecutionsResponse: Format[Schema.GoogleCloudRunV2ListExecutionsResponse] = Json.format[Schema.GoogleCloudRunV2ListExecutionsResponse]
	given fmtGoogleCloudRunV2CancelExecutionRequest: Format[Schema.GoogleCloudRunV2CancelExecutionRequest] = Json.format[Schema.GoogleCloudRunV2CancelExecutionRequest]
	given fmtGoogleCloudRunV2Job: Format[Schema.GoogleCloudRunV2Job] = Json.format[Schema.GoogleCloudRunV2Job]
	given fmtGoogleCloudRunV2JobLaunchStageEnum: Format[Schema.GoogleCloudRunV2Job.LaunchStageEnum] = JsonEnumFormat[Schema.GoogleCloudRunV2Job.LaunchStageEnum]
	given fmtGoogleCloudRunV2BinaryAuthorization: Format[Schema.GoogleCloudRunV2BinaryAuthorization] = Json.format[Schema.GoogleCloudRunV2BinaryAuthorization]
	given fmtGoogleCloudRunV2ExecutionTemplate: Format[Schema.GoogleCloudRunV2ExecutionTemplate] = Json.format[Schema.GoogleCloudRunV2ExecutionTemplate]
	given fmtGoogleCloudRunV2ExecutionReference: Format[Schema.GoogleCloudRunV2ExecutionReference] = Json.format[Schema.GoogleCloudRunV2ExecutionReference]
	given fmtGoogleCloudRunV2ExecutionReferenceCompletionStatusEnum: Format[Schema.GoogleCloudRunV2ExecutionReference.CompletionStatusEnum] = JsonEnumFormat[Schema.GoogleCloudRunV2ExecutionReference.CompletionStatusEnum]
	given fmtGoogleCloudRunV2ListJobsResponse: Format[Schema.GoogleCloudRunV2ListJobsResponse] = Json.format[Schema.GoogleCloudRunV2ListJobsResponse]
	given fmtGoogleCloudRunV2RunJobRequest: Format[Schema.GoogleCloudRunV2RunJobRequest] = Json.format[Schema.GoogleCloudRunV2RunJobRequest]
	given fmtGoogleCloudRunV2Overrides: Format[Schema.GoogleCloudRunV2Overrides] = Json.format[Schema.GoogleCloudRunV2Overrides]
	given fmtGoogleCloudRunV2ContainerOverride: Format[Schema.GoogleCloudRunV2ContainerOverride] = Json.format[Schema.GoogleCloudRunV2ContainerOverride]
	given fmtGoogleIamV1Policy: Format[Schema.GoogleIamV1Policy] = Json.format[Schema.GoogleIamV1Policy]
	given fmtGoogleIamV1Binding: Format[Schema.GoogleIamV1Binding] = Json.format[Schema.GoogleIamV1Binding]
	given fmtGoogleIamV1AuditConfig: Format[Schema.GoogleIamV1AuditConfig] = Json.format[Schema.GoogleIamV1AuditConfig]
	given fmtGoogleTypeExpr: Format[Schema.GoogleTypeExpr] = Json.format[Schema.GoogleTypeExpr]
	given fmtGoogleIamV1AuditLogConfig: Format[Schema.GoogleIamV1AuditLogConfig] = Json.format[Schema.GoogleIamV1AuditLogConfig]
	given fmtGoogleIamV1AuditLogConfigLogTypeEnum: Format[Schema.GoogleIamV1AuditLogConfig.LogTypeEnum] = JsonEnumFormat[Schema.GoogleIamV1AuditLogConfig.LogTypeEnum]
	given fmtGoogleIamV1SetIamPolicyRequest: Format[Schema.GoogleIamV1SetIamPolicyRequest] = Json.format[Schema.GoogleIamV1SetIamPolicyRequest]
	given fmtGoogleIamV1TestIamPermissionsRequest: Format[Schema.GoogleIamV1TestIamPermissionsRequest] = Json.format[Schema.GoogleIamV1TestIamPermissionsRequest]
	given fmtGoogleIamV1TestIamPermissionsResponse: Format[Schema.GoogleIamV1TestIamPermissionsResponse] = Json.format[Schema.GoogleIamV1TestIamPermissionsResponse]
	given fmtGoogleCloudRunV2Revision: Format[Schema.GoogleCloudRunV2Revision] = Json.format[Schema.GoogleCloudRunV2Revision]
	given fmtGoogleCloudRunV2RevisionLaunchStageEnum: Format[Schema.GoogleCloudRunV2Revision.LaunchStageEnum] = JsonEnumFormat[Schema.GoogleCloudRunV2Revision.LaunchStageEnum]
	given fmtGoogleCloudRunV2RevisionScaling: Format[Schema.GoogleCloudRunV2RevisionScaling] = Json.format[Schema.GoogleCloudRunV2RevisionScaling]
	given fmtGoogleCloudRunV2RevisionExecutionEnvironmentEnum: Format[Schema.GoogleCloudRunV2Revision.ExecutionEnvironmentEnum] = JsonEnumFormat[Schema.GoogleCloudRunV2Revision.ExecutionEnvironmentEnum]
	given fmtGoogleCloudRunV2ServiceMesh: Format[Schema.GoogleCloudRunV2ServiceMesh] = Json.format[Schema.GoogleCloudRunV2ServiceMesh]
	given fmtGoogleCloudRunV2RevisionEncryptionKeyRevocationActionEnum: Format[Schema.GoogleCloudRunV2Revision.EncryptionKeyRevocationActionEnum] = JsonEnumFormat[Schema.GoogleCloudRunV2Revision.EncryptionKeyRevocationActionEnum]
	given fmtGoogleCloudRunV2RevisionScalingStatus: Format[Schema.GoogleCloudRunV2RevisionScalingStatus] = Json.format[Schema.GoogleCloudRunV2RevisionScalingStatus]
	given fmtGoogleCloudRunV2NodeSelector: Format[Schema.GoogleCloudRunV2NodeSelector] = Json.format[Schema.GoogleCloudRunV2NodeSelector]
	given fmtGoogleCloudRunV2ListRevisionsResponse: Format[Schema.GoogleCloudRunV2ListRevisionsResponse] = Json.format[Schema.GoogleCloudRunV2ListRevisionsResponse]
	given fmtGoogleCloudRunV2Service: Format[Schema.GoogleCloudRunV2Service] = Json.format[Schema.GoogleCloudRunV2Service]
	given fmtGoogleCloudRunV2ServiceIngressEnum: Format[Schema.GoogleCloudRunV2Service.IngressEnum] = JsonEnumFormat[Schema.GoogleCloudRunV2Service.IngressEnum]
	given fmtGoogleCloudRunV2ServiceLaunchStageEnum: Format[Schema.GoogleCloudRunV2Service.LaunchStageEnum] = JsonEnumFormat[Schema.GoogleCloudRunV2Service.LaunchStageEnum]
	given fmtGoogleCloudRunV2RevisionTemplate: Format[Schema.GoogleCloudRunV2RevisionTemplate] = Json.format[Schema.GoogleCloudRunV2RevisionTemplate]
	given fmtGoogleCloudRunV2TrafficTarget: Format[Schema.GoogleCloudRunV2TrafficTarget] = Json.format[Schema.GoogleCloudRunV2TrafficTarget]
	given fmtGoogleCloudRunV2ServiceScaling: Format[Schema.GoogleCloudRunV2ServiceScaling] = Json.format[Schema.GoogleCloudRunV2ServiceScaling]
	given fmtGoogleCloudRunV2TrafficTargetStatus: Format[Schema.GoogleCloudRunV2TrafficTargetStatus] = Json.format[Schema.GoogleCloudRunV2TrafficTargetStatus]
	given fmtGoogleCloudRunV2RevisionTemplateExecutionEnvironmentEnum: Format[Schema.GoogleCloudRunV2RevisionTemplate.ExecutionEnvironmentEnum] = JsonEnumFormat[Schema.GoogleCloudRunV2RevisionTemplate.ExecutionEnvironmentEnum]
	given fmtGoogleCloudRunV2RevisionTemplateEncryptionKeyRevocationActionEnum: Format[Schema.GoogleCloudRunV2RevisionTemplate.EncryptionKeyRevocationActionEnum] = JsonEnumFormat[Schema.GoogleCloudRunV2RevisionTemplate.EncryptionKeyRevocationActionEnum]
	given fmtGoogleCloudRunV2TrafficTargetTypeEnum: Format[Schema.GoogleCloudRunV2TrafficTarget.TypeEnum] = JsonEnumFormat[Schema.GoogleCloudRunV2TrafficTarget.TypeEnum]
	given fmtGoogleCloudRunV2ServiceScalingScalingModeEnum: Format[Schema.GoogleCloudRunV2ServiceScaling.ScalingModeEnum] = JsonEnumFormat[Schema.GoogleCloudRunV2ServiceScaling.ScalingModeEnum]
	given fmtGoogleCloudRunV2TrafficTargetStatusTypeEnum: Format[Schema.GoogleCloudRunV2TrafficTargetStatus.TypeEnum] = JsonEnumFormat[Schema.GoogleCloudRunV2TrafficTargetStatus.TypeEnum]
	given fmtGoogleCloudRunV2ListServicesResponse: Format[Schema.GoogleCloudRunV2ListServicesResponse] = Json.format[Schema.GoogleCloudRunV2ListServicesResponse]
	given fmtGoogleCloudRunV2Task: Format[Schema.GoogleCloudRunV2Task] = Json.format[Schema.GoogleCloudRunV2Task]
	given fmtGoogleCloudRunV2TaskExecutionEnvironmentEnum: Format[Schema.GoogleCloudRunV2Task.ExecutionEnvironmentEnum] = JsonEnumFormat[Schema.GoogleCloudRunV2Task.ExecutionEnvironmentEnum]
	given fmtGoogleCloudRunV2TaskAttemptResult: Format[Schema.GoogleCloudRunV2TaskAttemptResult] = Json.format[Schema.GoogleCloudRunV2TaskAttemptResult]
	given fmtGoogleCloudRunV2ListTasksResponse: Format[Schema.GoogleCloudRunV2ListTasksResponse] = Json.format[Schema.GoogleCloudRunV2ListTasksResponse]
	given fmtGoogleDevtoolsCloudbuildV1BuildOperationMetadata: Format[Schema.GoogleDevtoolsCloudbuildV1BuildOperationMetadata] = Json.format[Schema.GoogleDevtoolsCloudbuildV1BuildOperationMetadata]
	given fmtGoogleDevtoolsCloudbuildV1Build: Format[Schema.GoogleDevtoolsCloudbuildV1Build] = Json.format[Schema.GoogleDevtoolsCloudbuildV1Build]
	given fmtGoogleDevtoolsCloudbuildV1BuildStatusEnum: Format[Schema.GoogleDevtoolsCloudbuildV1Build.StatusEnum] = JsonEnumFormat[Schema.GoogleDevtoolsCloudbuildV1Build.StatusEnum]
	given fmtGoogleDevtoolsCloudbuildV1Source: Format[Schema.GoogleDevtoolsCloudbuildV1Source] = Json.format[Schema.GoogleDevtoolsCloudbuildV1Source]
	given fmtGoogleDevtoolsCloudbuildV1BuildStep: Format[Schema.GoogleDevtoolsCloudbuildV1BuildStep] = Json.format[Schema.GoogleDevtoolsCloudbuildV1BuildStep]
	given fmtGoogleDevtoolsCloudbuildV1Results: Format[Schema.GoogleDevtoolsCloudbuildV1Results] = Json.format[Schema.GoogleDevtoolsCloudbuildV1Results]
	given fmtGoogleDevtoolsCloudbuildV1Artifacts: Format[Schema.GoogleDevtoolsCloudbuildV1Artifacts] = Json.format[Schema.GoogleDevtoolsCloudbuildV1Artifacts]
	given fmtGoogleDevtoolsCloudbuildV1SourceProvenance: Format[Schema.GoogleDevtoolsCloudbuildV1SourceProvenance] = Json.format[Schema.GoogleDevtoolsCloudbuildV1SourceProvenance]
	given fmtGoogleDevtoolsCloudbuildV1BuildOptions: Format[Schema.GoogleDevtoolsCloudbuildV1BuildOptions] = Json.format[Schema.GoogleDevtoolsCloudbuildV1BuildOptions]
	given fmtGoogleDevtoolsCloudbuildV1Secret: Format[Schema.GoogleDevtoolsCloudbuildV1Secret] = Json.format[Schema.GoogleDevtoolsCloudbuildV1Secret]
	given fmtGoogleDevtoolsCloudbuildV1TimeSpan: Format[Schema.GoogleDevtoolsCloudbuildV1TimeSpan] = Json.format[Schema.GoogleDevtoolsCloudbuildV1TimeSpan]
	given fmtGoogleDevtoolsCloudbuildV1BuildApproval: Format[Schema.GoogleDevtoolsCloudbuildV1BuildApproval] = Json.format[Schema.GoogleDevtoolsCloudbuildV1BuildApproval]
	given fmtGoogleDevtoolsCloudbuildV1Secrets: Format[Schema.GoogleDevtoolsCloudbuildV1Secrets] = Json.format[Schema.GoogleDevtoolsCloudbuildV1Secrets]
	given fmtGoogleDevtoolsCloudbuildV1Warning: Format[Schema.GoogleDevtoolsCloudbuildV1Warning] = Json.format[Schema.GoogleDevtoolsCloudbuildV1Warning]
	given fmtGoogleDevtoolsCloudbuildV1GitConfig: Format[Schema.GoogleDevtoolsCloudbuildV1GitConfig] = Json.format[Schema.GoogleDevtoolsCloudbuildV1GitConfig]
	given fmtGoogleDevtoolsCloudbuildV1FailureInfo: Format[Schema.GoogleDevtoolsCloudbuildV1FailureInfo] = Json.format[Schema.GoogleDevtoolsCloudbuildV1FailureInfo]
	given fmtGoogleDevtoolsCloudbuildV1StorageSource: Format[Schema.GoogleDevtoolsCloudbuildV1StorageSource] = Json.format[Schema.GoogleDevtoolsCloudbuildV1StorageSource]
	given fmtGoogleDevtoolsCloudbuildV1RepoSource: Format[Schema.GoogleDevtoolsCloudbuildV1RepoSource] = Json.format[Schema.GoogleDevtoolsCloudbuildV1RepoSource]
	given fmtGoogleDevtoolsCloudbuildV1GitSource: Format[Schema.GoogleDevtoolsCloudbuildV1GitSource] = Json.format[Schema.GoogleDevtoolsCloudbuildV1GitSource]
	given fmtGoogleDevtoolsCloudbuildV1StorageSourceManifest: Format[Schema.GoogleDevtoolsCloudbuildV1StorageSourceManifest] = Json.format[Schema.GoogleDevtoolsCloudbuildV1StorageSourceManifest]
	given fmtGoogleDevtoolsCloudbuildV1ConnectedRepository: Format[Schema.GoogleDevtoolsCloudbuildV1ConnectedRepository] = Json.format[Schema.GoogleDevtoolsCloudbuildV1ConnectedRepository]
	given fmtGoogleDevtoolsCloudbuildV1DeveloperConnectConfig: Format[Schema.GoogleDevtoolsCloudbuildV1DeveloperConnectConfig] = Json.format[Schema.GoogleDevtoolsCloudbuildV1DeveloperConnectConfig]
	given fmtGoogleDevtoolsCloudbuildV1StorageSourceSourceFetcherEnum: Format[Schema.GoogleDevtoolsCloudbuildV1StorageSource.SourceFetcherEnum] = JsonEnumFormat[Schema.GoogleDevtoolsCloudbuildV1StorageSource.SourceFetcherEnum]
	given fmtGoogleDevtoolsCloudbuildV1Volume: Format[Schema.GoogleDevtoolsCloudbuildV1Volume] = Json.format[Schema.GoogleDevtoolsCloudbuildV1Volume]
	given fmtGoogleDevtoolsCloudbuildV1BuildStepStatusEnum: Format[Schema.GoogleDevtoolsCloudbuildV1BuildStep.StatusEnum] = JsonEnumFormat[Schema.GoogleDevtoolsCloudbuildV1BuildStep.StatusEnum]
	given fmtGoogleDevtoolsCloudbuildV1BuiltImage: Format[Schema.GoogleDevtoolsCloudbuildV1BuiltImage] = Json.format[Schema.GoogleDevtoolsCloudbuildV1BuiltImage]
	given fmtGoogleDevtoolsCloudbuildV1UploadedPythonPackage: Format[Schema.GoogleDevtoolsCloudbuildV1UploadedPythonPackage] = Json.format[Schema.GoogleDevtoolsCloudbuildV1UploadedPythonPackage]
	given fmtGoogleDevtoolsCloudbuildV1UploadedMavenArtifact: Format[Schema.GoogleDevtoolsCloudbuildV1UploadedMavenArtifact] = Json.format[Schema.GoogleDevtoolsCloudbuildV1UploadedMavenArtifact]
	given fmtGoogleDevtoolsCloudbuildV1UploadedNpmPackage: Format[Schema.GoogleDevtoolsCloudbuildV1UploadedNpmPackage] = Json.format[Schema.GoogleDevtoolsCloudbuildV1UploadedNpmPackage]
	given fmtGoogleDevtoolsCloudbuildV1FileHashes: Format[Schema.GoogleDevtoolsCloudbuildV1FileHashes] = Json.format[Schema.GoogleDevtoolsCloudbuildV1FileHashes]
	given fmtGoogleDevtoolsCloudbuildV1Hash: Format[Schema.GoogleDevtoolsCloudbuildV1Hash] = Json.format[Schema.GoogleDevtoolsCloudbuildV1Hash]
	given fmtGoogleDevtoolsCloudbuildV1HashTypeEnum: Format[Schema.GoogleDevtoolsCloudbuildV1Hash.TypeEnum] = JsonEnumFormat[Schema.GoogleDevtoolsCloudbuildV1Hash.TypeEnum]
	given fmtGoogleDevtoolsCloudbuildV1ArtifactObjects: Format[Schema.GoogleDevtoolsCloudbuildV1ArtifactObjects] = Json.format[Schema.GoogleDevtoolsCloudbuildV1ArtifactObjects]
	given fmtGoogleDevtoolsCloudbuildV1MavenArtifact: Format[Schema.GoogleDevtoolsCloudbuildV1MavenArtifact] = Json.format[Schema.GoogleDevtoolsCloudbuildV1MavenArtifact]
	given fmtGoogleDevtoolsCloudbuildV1PythonPackage: Format[Schema.GoogleDevtoolsCloudbuildV1PythonPackage] = Json.format[Schema.GoogleDevtoolsCloudbuildV1PythonPackage]
	given fmtGoogleDevtoolsCloudbuildV1NpmPackage: Format[Schema.GoogleDevtoolsCloudbuildV1NpmPackage] = Json.format[Schema.GoogleDevtoolsCloudbuildV1NpmPackage]
	given fmtGoogleDevtoolsCloudbuildV1BuildOptionsSourceProvenanceHashEnum: Format[Schema.GoogleDevtoolsCloudbuildV1BuildOptions.SourceProvenanceHashEnum] = JsonEnumFormat[Schema.GoogleDevtoolsCloudbuildV1BuildOptions.SourceProvenanceHashEnum]
	given fmtGoogleDevtoolsCloudbuildV1BuildOptionsRequestedVerifyOptionEnum: Format[Schema.GoogleDevtoolsCloudbuildV1BuildOptions.RequestedVerifyOptionEnum] = JsonEnumFormat[Schema.GoogleDevtoolsCloudbuildV1BuildOptions.RequestedVerifyOptionEnum]
	given fmtGoogleDevtoolsCloudbuildV1BuildOptionsMachineTypeEnum: Format[Schema.GoogleDevtoolsCloudbuildV1BuildOptions.MachineTypeEnum] = JsonEnumFormat[Schema.GoogleDevtoolsCloudbuildV1BuildOptions.MachineTypeEnum]
	given fmtGoogleDevtoolsCloudbuildV1BuildOptionsSubstitutionOptionEnum: Format[Schema.GoogleDevtoolsCloudbuildV1BuildOptions.SubstitutionOptionEnum] = JsonEnumFormat[Schema.GoogleDevtoolsCloudbuildV1BuildOptions.SubstitutionOptionEnum]
	given fmtGoogleDevtoolsCloudbuildV1BuildOptionsLogStreamingOptionEnum: Format[Schema.GoogleDevtoolsCloudbuildV1BuildOptions.LogStreamingOptionEnum] = JsonEnumFormat[Schema.GoogleDevtoolsCloudbuildV1BuildOptions.LogStreamingOptionEnum]
	given fmtGoogleDevtoolsCloudbuildV1PoolOption: Format[Schema.GoogleDevtoolsCloudbuildV1PoolOption] = Json.format[Schema.GoogleDevtoolsCloudbuildV1PoolOption]
	given fmtGoogleDevtoolsCloudbuildV1BuildOptionsLoggingEnum: Format[Schema.GoogleDevtoolsCloudbuildV1BuildOptions.LoggingEnum] = JsonEnumFormat[Schema.GoogleDevtoolsCloudbuildV1BuildOptions.LoggingEnum]
	given fmtGoogleDevtoolsCloudbuildV1BuildOptionsDefaultLogsBucketBehaviorEnum: Format[Schema.GoogleDevtoolsCloudbuildV1BuildOptions.DefaultLogsBucketBehaviorEnum] = JsonEnumFormat[Schema.GoogleDevtoolsCloudbuildV1BuildOptions.DefaultLogsBucketBehaviorEnum]
	given fmtGoogleDevtoolsCloudbuildV1BuildApprovalStateEnum: Format[Schema.GoogleDevtoolsCloudbuildV1BuildApproval.StateEnum] = JsonEnumFormat[Schema.GoogleDevtoolsCloudbuildV1BuildApproval.StateEnum]
	given fmtGoogleDevtoolsCloudbuildV1ApprovalConfig: Format[Schema.GoogleDevtoolsCloudbuildV1ApprovalConfig] = Json.format[Schema.GoogleDevtoolsCloudbuildV1ApprovalConfig]
	given fmtGoogleDevtoolsCloudbuildV1ApprovalResult: Format[Schema.GoogleDevtoolsCloudbuildV1ApprovalResult] = Json.format[Schema.GoogleDevtoolsCloudbuildV1ApprovalResult]
	given fmtGoogleDevtoolsCloudbuildV1ApprovalResultDecisionEnum: Format[Schema.GoogleDevtoolsCloudbuildV1ApprovalResult.DecisionEnum] = JsonEnumFormat[Schema.GoogleDevtoolsCloudbuildV1ApprovalResult.DecisionEnum]
	given fmtGoogleDevtoolsCloudbuildV1SecretManagerSecret: Format[Schema.GoogleDevtoolsCloudbuildV1SecretManagerSecret] = Json.format[Schema.GoogleDevtoolsCloudbuildV1SecretManagerSecret]
	given fmtGoogleDevtoolsCloudbuildV1InlineSecret: Format[Schema.GoogleDevtoolsCloudbuildV1InlineSecret] = Json.format[Schema.GoogleDevtoolsCloudbuildV1InlineSecret]
	given fmtGoogleDevtoolsCloudbuildV1WarningPriorityEnum: Format[Schema.GoogleDevtoolsCloudbuildV1Warning.PriorityEnum] = JsonEnumFormat[Schema.GoogleDevtoolsCloudbuildV1Warning.PriorityEnum]
	given fmtGoogleDevtoolsCloudbuildV1HttpConfig: Format[Schema.GoogleDevtoolsCloudbuildV1HttpConfig] = Json.format[Schema.GoogleDevtoolsCloudbuildV1HttpConfig]
	given fmtGoogleDevtoolsCloudbuildV1FailureInfoTypeEnum: Format[Schema.GoogleDevtoolsCloudbuildV1FailureInfo.TypeEnum] = JsonEnumFormat[Schema.GoogleDevtoolsCloudbuildV1FailureInfo.TypeEnum]
}
