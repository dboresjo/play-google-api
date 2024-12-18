package com.boresjo.play.api.google.ml

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtGoogleCloudMlV1Job: Format[Schema.GoogleCloudMlV1__Job] = Json.format[Schema.GoogleCloudMlV1__Job]
	given fmtGoogleCloudMlV1TrainingInput: Format[Schema.GoogleCloudMlV1__TrainingInput] = Json.format[Schema.GoogleCloudMlV1__TrainingInput]
	given fmtGoogleCloudMlV1PredictionInput: Format[Schema.GoogleCloudMlV1__PredictionInput] = Json.format[Schema.GoogleCloudMlV1__PredictionInput]
	given fmtGoogleCloudMlV1JobStateEnum: Format[Schema.GoogleCloudMlV1__Job.StateEnum] = JsonEnumFormat[Schema.GoogleCloudMlV1__Job.StateEnum]
	given fmtGoogleCloudMlV1TrainingOutput: Format[Schema.GoogleCloudMlV1__TrainingOutput] = Json.format[Schema.GoogleCloudMlV1__TrainingOutput]
	given fmtGoogleCloudMlV1PredictionOutput: Format[Schema.GoogleCloudMlV1__PredictionOutput] = Json.format[Schema.GoogleCloudMlV1__PredictionOutput]
	given fmtGoogleCloudMlV1TrainingInputScaleTierEnum: Format[Schema.GoogleCloudMlV1__TrainingInput.ScaleTierEnum] = JsonEnumFormat[Schema.GoogleCloudMlV1__TrainingInput.ScaleTierEnum]
	given fmtGoogleCloudMlV1ReplicaConfig: Format[Schema.GoogleCloudMlV1__ReplicaConfig] = Json.format[Schema.GoogleCloudMlV1__ReplicaConfig]
	given fmtGoogleCloudMlV1HyperparameterSpec: Format[Schema.GoogleCloudMlV1__HyperparameterSpec] = Json.format[Schema.GoogleCloudMlV1__HyperparameterSpec]
	given fmtGoogleCloudMlV1EncryptionConfig: Format[Schema.GoogleCloudMlV1__EncryptionConfig] = Json.format[Schema.GoogleCloudMlV1__EncryptionConfig]
	given fmtGoogleCloudMlV1Scheduling: Format[Schema.GoogleCloudMlV1__Scheduling] = Json.format[Schema.GoogleCloudMlV1__Scheduling]
	given fmtGoogleCloudMlV1AcceleratorConfig: Format[Schema.GoogleCloudMlV1__AcceleratorConfig] = Json.format[Schema.GoogleCloudMlV1__AcceleratorConfig]
	given fmtGoogleCloudMlV1DiskConfig: Format[Schema.GoogleCloudMlV1__DiskConfig] = Json.format[Schema.GoogleCloudMlV1__DiskConfig]
	given fmtGoogleCloudMlV1AcceleratorConfigTypeEnum: Format[Schema.GoogleCloudMlV1__AcceleratorConfig.TypeEnum] = JsonEnumFormat[Schema.GoogleCloudMlV1__AcceleratorConfig.TypeEnum]
	given fmtGoogleCloudMlV1HyperparameterSpecGoalEnum: Format[Schema.GoogleCloudMlV1__HyperparameterSpec.GoalEnum] = JsonEnumFormat[Schema.GoogleCloudMlV1__HyperparameterSpec.GoalEnum]
	given fmtGoogleCloudMlV1ParameterSpec: Format[Schema.GoogleCloudMlV1__ParameterSpec] = Json.format[Schema.GoogleCloudMlV1__ParameterSpec]
	given fmtGoogleCloudMlV1HyperparameterSpecAlgorithmEnum: Format[Schema.GoogleCloudMlV1__HyperparameterSpec.AlgorithmEnum] = JsonEnumFormat[Schema.GoogleCloudMlV1__HyperparameterSpec.AlgorithmEnum]
	given fmtGoogleCloudMlV1ParameterSpecTypeEnum: Format[Schema.GoogleCloudMlV1__ParameterSpec.TypeEnum] = JsonEnumFormat[Schema.GoogleCloudMlV1__ParameterSpec.TypeEnum]
	given fmtGoogleCloudMlV1ParameterSpecScaleTypeEnum: Format[Schema.GoogleCloudMlV1__ParameterSpec.ScaleTypeEnum] = JsonEnumFormat[Schema.GoogleCloudMlV1__ParameterSpec.ScaleTypeEnum]
	given fmtGoogleCloudMlV1PredictionInputDataFormatEnum: Format[Schema.GoogleCloudMlV1__PredictionInput.DataFormatEnum] = JsonEnumFormat[Schema.GoogleCloudMlV1__PredictionInput.DataFormatEnum]
	given fmtGoogleCloudMlV1PredictionInputOutputDataFormatEnum: Format[Schema.GoogleCloudMlV1__PredictionInput.OutputDataFormatEnum] = JsonEnumFormat[Schema.GoogleCloudMlV1__PredictionInput.OutputDataFormatEnum]
	given fmtGoogleCloudMlV1HyperparameterOutput: Format[Schema.GoogleCloudMlV1__HyperparameterOutput] = Json.format[Schema.GoogleCloudMlV1__HyperparameterOutput]
	given fmtGoogleCloudMlV1BuiltInAlgorithmOutput: Format[Schema.GoogleCloudMlV1__BuiltInAlgorithmOutput] = Json.format[Schema.GoogleCloudMlV1__BuiltInAlgorithmOutput]
	given fmtGoogleCloudMlV1HyperparameterOutputStateEnum: Format[Schema.GoogleCloudMlV1__HyperparameterOutput.StateEnum] = JsonEnumFormat[Schema.GoogleCloudMlV1__HyperparameterOutput.StateEnum]
	given fmtGoogleCloudMlV1HyperparameterOutputHyperparameterMetric: Format[Schema.GoogleCloudMlV1_HyperparameterOutput_HyperparameterMetric] = Json.format[Schema.GoogleCloudMlV1_HyperparameterOutput_HyperparameterMetric]
	given fmtGoogleCloudMlV1ListJobsResponse: Format[Schema.GoogleCloudMlV1__ListJobsResponse] = Json.format[Schema.GoogleCloudMlV1__ListJobsResponse]
	given fmtGoogleCloudMlV1CancelJobRequest: Format[Schema.GoogleCloudMlV1__CancelJobRequest] = Json.format[Schema.GoogleCloudMlV1__CancelJobRequest]
	given fmtGoogleProtobufEmpty: Format[Schema.GoogleProtobuf__Empty] = Json.format[Schema.GoogleProtobuf__Empty]
	given fmtGoogleCloudMlV1Location: Format[Schema.GoogleCloudMlV1__Location] = Json.format[Schema.GoogleCloudMlV1__Location]
	given fmtGoogleCloudMlV1Capability: Format[Schema.GoogleCloudMlV1__Capability] = Json.format[Schema.GoogleCloudMlV1__Capability]
	given fmtGoogleCloudMlV1CapabilityTypeEnum: Format[Schema.GoogleCloudMlV1__Capability.TypeEnum] = JsonEnumFormat[Schema.GoogleCloudMlV1__Capability.TypeEnum]
	given fmtGoogleCloudMlV1CapabilityAvailableAcceleratorsEnum: Format[Schema.GoogleCloudMlV1__Capability.AvailableAcceleratorsEnum] = JsonEnumFormat[Schema.GoogleCloudMlV1__Capability.AvailableAcceleratorsEnum]
	given fmtGoogleCloudMlV1ListLocationsResponse: Format[Schema.GoogleCloudMlV1__ListLocationsResponse] = Json.format[Schema.GoogleCloudMlV1__ListLocationsResponse]
	given fmtGoogleLongrunningListOperationsResponse: Format[Schema.GoogleLongrunning__ListOperationsResponse] = Json.format[Schema.GoogleLongrunning__ListOperationsResponse]
	given fmtGoogleLongrunningOperation: Format[Schema.GoogleLongrunning__Operation] = Json.format[Schema.GoogleLongrunning__Operation]
	given fmtGoogleRpcStatus: Format[Schema.GoogleRpc__Status] = Json.format[Schema.GoogleRpc__Status]
	given fmtGoogleCloudMlV1Model: Format[Schema.GoogleCloudMlV1__Model] = Json.format[Schema.GoogleCloudMlV1__Model]
	given fmtGoogleCloudMlV1Version: Format[Schema.GoogleCloudMlV1__Version] = Json.format[Schema.GoogleCloudMlV1__Version]
	given fmtGoogleCloudMlV1AutoScaling: Format[Schema.GoogleCloudMlV1__AutoScaling] = Json.format[Schema.GoogleCloudMlV1__AutoScaling]
	given fmtGoogleCloudMlV1ManualScaling: Format[Schema.GoogleCloudMlV1__ManualScaling] = Json.format[Schema.GoogleCloudMlV1__ManualScaling]
	given fmtGoogleCloudMlV1VersionStateEnum: Format[Schema.GoogleCloudMlV1__Version.StateEnum] = JsonEnumFormat[Schema.GoogleCloudMlV1__Version.StateEnum]
	given fmtGoogleCloudMlV1VersionFrameworkEnum: Format[Schema.GoogleCloudMlV1__Version.FrameworkEnum] = JsonEnumFormat[Schema.GoogleCloudMlV1__Version.FrameworkEnum]
	given fmtGoogleCloudMlV1RequestLoggingConfig: Format[Schema.GoogleCloudMlV1__RequestLoggingConfig] = Json.format[Schema.GoogleCloudMlV1__RequestLoggingConfig]
	given fmtGoogleCloudMlV1ExplanationConfig: Format[Schema.GoogleCloudMlV1__ExplanationConfig] = Json.format[Schema.GoogleCloudMlV1__ExplanationConfig]
	given fmtGoogleCloudMlV1ContainerSpec: Format[Schema.GoogleCloudMlV1__ContainerSpec] = Json.format[Schema.GoogleCloudMlV1__ContainerSpec]
	given fmtGoogleCloudMlV1RouteMap: Format[Schema.GoogleCloudMlV1__RouteMap] = Json.format[Schema.GoogleCloudMlV1__RouteMap]
	given fmtGoogleCloudMlV1MetricSpec: Format[Schema.GoogleCloudMlV1__MetricSpec] = Json.format[Schema.GoogleCloudMlV1__MetricSpec]
	given fmtGoogleCloudMlV1MetricSpecNameEnum: Format[Schema.GoogleCloudMlV1__MetricSpec.NameEnum] = JsonEnumFormat[Schema.GoogleCloudMlV1__MetricSpec.NameEnum]
	given fmtGoogleCloudMlV1IntegratedGradientsAttribution: Format[Schema.GoogleCloudMlV1__IntegratedGradientsAttribution] = Json.format[Schema.GoogleCloudMlV1__IntegratedGradientsAttribution]
	given fmtGoogleCloudMlV1SampledShapleyAttribution: Format[Schema.GoogleCloudMlV1__SampledShapleyAttribution] = Json.format[Schema.GoogleCloudMlV1__SampledShapleyAttribution]
	given fmtGoogleCloudMlV1XraiAttribution: Format[Schema.GoogleCloudMlV1__XraiAttribution] = Json.format[Schema.GoogleCloudMlV1__XraiAttribution]
	given fmtGoogleCloudMlV1ContainerPort: Format[Schema.GoogleCloudMlV1__ContainerPort] = Json.format[Schema.GoogleCloudMlV1__ContainerPort]
	given fmtGoogleCloudMlV1EnvVar: Format[Schema.GoogleCloudMlV1__EnvVar] = Json.format[Schema.GoogleCloudMlV1__EnvVar]
	given fmtGoogleCloudMlV1ListModelsResponse: Format[Schema.GoogleCloudMlV1__ListModelsResponse] = Json.format[Schema.GoogleCloudMlV1__ListModelsResponse]
	given fmtGoogleCloudMlV1ListVersionsResponse: Format[Schema.GoogleCloudMlV1__ListVersionsResponse] = Json.format[Schema.GoogleCloudMlV1__ListVersionsResponse]
	given fmtGoogleCloudMlV1SetDefaultVersionRequest: Format[Schema.GoogleCloudMlV1__SetDefaultVersionRequest] = Json.format[Schema.GoogleCloudMlV1__SetDefaultVersionRequest]
	given fmtGoogleCloudMlV1PredictRequest: Format[Schema.GoogleCloudMlV1__PredictRequest] = Json.format[Schema.GoogleCloudMlV1__PredictRequest]
	given fmtGoogleApiHttpBody: Format[Schema.GoogleApi__HttpBody] = Json.format[Schema.GoogleApi__HttpBody]
	given fmtGoogleCloudMlV1ExplainRequest: Format[Schema.GoogleCloudMlV1__ExplainRequest] = Json.format[Schema.GoogleCloudMlV1__ExplainRequest]
	given fmtGoogleCloudMlV1GetConfigResponse: Format[Schema.GoogleCloudMlV1__GetConfigResponse] = Json.format[Schema.GoogleCloudMlV1__GetConfigResponse]
	given fmtGoogleCloudMlV1Config: Format[Schema.GoogleCloudMlV1__Config] = Json.format[Schema.GoogleCloudMlV1__Config]
	given fmtGoogleCloudMlV1Study: Format[Schema.GoogleCloudMlV1__Study] = Json.format[Schema.GoogleCloudMlV1__Study]
	given fmtGoogleCloudMlV1StudyConfig: Format[Schema.GoogleCloudMlV1__StudyConfig] = Json.format[Schema.GoogleCloudMlV1__StudyConfig]
	given fmtGoogleCloudMlV1StudyStateEnum: Format[Schema.GoogleCloudMlV1__Study.StateEnum] = JsonEnumFormat[Schema.GoogleCloudMlV1__Study.StateEnum]
	given fmtGoogleCloudMlV1StudyConfigMetricSpec: Format[Schema.GoogleCloudMlV1_StudyConfig_MetricSpec] = Json.format[Schema.GoogleCloudMlV1_StudyConfig_MetricSpec]
	given fmtGoogleCloudMlV1StudyConfigParameterSpec: Format[Schema.GoogleCloudMlV1_StudyConfig_ParameterSpec] = Json.format[Schema.GoogleCloudMlV1_StudyConfig_ParameterSpec]
	given fmtGoogleCloudMlV1StudyConfigAlgorithmEnum: Format[Schema.GoogleCloudMlV1__StudyConfig.AlgorithmEnum] = JsonEnumFormat[Schema.GoogleCloudMlV1__StudyConfig.AlgorithmEnum]
	given fmtGoogleCloudMlV1AutomatedStoppingConfig: Format[Schema.GoogleCloudMlV1__AutomatedStoppingConfig] = Json.format[Schema.GoogleCloudMlV1__AutomatedStoppingConfig]
	given fmtGoogleCloudMlV1StudyConfigMetricSpecGoalEnum: Format[Schema.GoogleCloudMlV1_StudyConfig_MetricSpec.GoalEnum] = JsonEnumFormat[Schema.GoogleCloudMlV1_StudyConfig_MetricSpec.GoalEnum]
	given fmtGoogleCloudMlV1StudyConfigParameterSpecTypeEnum: Format[Schema.GoogleCloudMlV1_StudyConfig_ParameterSpec.TypeEnum] = JsonEnumFormat[Schema.GoogleCloudMlV1_StudyConfig_ParameterSpec.TypeEnum]
	given fmtGoogleCloudMlV1StudyConfigParameterSpecDoubleValueSpec: Format[Schema.GoogleCloudMlV1_StudyConfigParameterSpec_DoubleValueSpec] = Json.format[Schema.GoogleCloudMlV1_StudyConfigParameterSpec_DoubleValueSpec]
	given fmtGoogleCloudMlV1StudyConfigParameterSpecIntegerValueSpec: Format[Schema.GoogleCloudMlV1_StudyConfigParameterSpec_IntegerValueSpec] = Json.format[Schema.GoogleCloudMlV1_StudyConfigParameterSpec_IntegerValueSpec]
	given fmtGoogleCloudMlV1StudyConfigParameterSpecCategoricalValueSpec: Format[Schema.GoogleCloudMlV1_StudyConfigParameterSpec_CategoricalValueSpec] = Json.format[Schema.GoogleCloudMlV1_StudyConfigParameterSpec_CategoricalValueSpec]
	given fmtGoogleCloudMlV1StudyConfigParameterSpecDiscreteValueSpec: Format[Schema.GoogleCloudMlV1_StudyConfigParameterSpec_DiscreteValueSpec] = Json.format[Schema.GoogleCloudMlV1_StudyConfigParameterSpec_DiscreteValueSpec]
	given fmtGoogleCloudMlV1StudyConfigParameterSpecScaleTypeEnum: Format[Schema.GoogleCloudMlV1_StudyConfig_ParameterSpec.ScaleTypeEnum] = JsonEnumFormat[Schema.GoogleCloudMlV1_StudyConfig_ParameterSpec.ScaleTypeEnum]
	given fmtGoogleCloudMlV1StudyConfigParameterSpecMatchingParentDiscreteValueSpec: Format[Schema.GoogleCloudMlV1_StudyConfigParameterSpec_MatchingParentDiscreteValueSpec] = Json.format[Schema.GoogleCloudMlV1_StudyConfigParameterSpec_MatchingParentDiscreteValueSpec]
	given fmtGoogleCloudMlV1StudyConfigParameterSpecMatchingParentIntValueSpec: Format[Schema.GoogleCloudMlV1_StudyConfigParameterSpec_MatchingParentIntValueSpec] = Json.format[Schema.GoogleCloudMlV1_StudyConfigParameterSpec_MatchingParentIntValueSpec]
	given fmtGoogleCloudMlV1StudyConfigParameterSpecMatchingParentCategoricalValueSpec: Format[Schema.GoogleCloudMlV1_StudyConfigParameterSpec_MatchingParentCategoricalValueSpec] = Json.format[Schema.GoogleCloudMlV1_StudyConfigParameterSpec_MatchingParentCategoricalValueSpec]
	given fmtGoogleCloudMlV1AutomatedStoppingConfigDecayCurveAutomatedStoppingConfig: Format[Schema.GoogleCloudMlV1_AutomatedStoppingConfig_DecayCurveAutomatedStoppingConfig] = Json.format[Schema.GoogleCloudMlV1_AutomatedStoppingConfig_DecayCurveAutomatedStoppingConfig]
	given fmtGoogleCloudMlV1AutomatedStoppingConfigMedianAutomatedStoppingConfig: Format[Schema.GoogleCloudMlV1_AutomatedStoppingConfig_MedianAutomatedStoppingConfig] = Json.format[Schema.GoogleCloudMlV1_AutomatedStoppingConfig_MedianAutomatedStoppingConfig]
	given fmtGoogleCloudMlV1ListStudiesResponse: Format[Schema.GoogleCloudMlV1__ListStudiesResponse] = Json.format[Schema.GoogleCloudMlV1__ListStudiesResponse]
	given fmtGoogleCloudMlV1SuggestTrialsRequest: Format[Schema.GoogleCloudMlV1__SuggestTrialsRequest] = Json.format[Schema.GoogleCloudMlV1__SuggestTrialsRequest]
	given fmtGoogleCloudMlV1Trial: Format[Schema.GoogleCloudMlV1__Trial] = Json.format[Schema.GoogleCloudMlV1__Trial]
	given fmtGoogleCloudMlV1TrialStateEnum: Format[Schema.GoogleCloudMlV1__Trial.StateEnum] = JsonEnumFormat[Schema.GoogleCloudMlV1__Trial.StateEnum]
	given fmtGoogleCloudMlV1TrialParameter: Format[Schema.GoogleCloudMlV1_Trial_Parameter] = Json.format[Schema.GoogleCloudMlV1_Trial_Parameter]
	given fmtGoogleCloudMlV1Measurement: Format[Schema.GoogleCloudMlV1__Measurement] = Json.format[Schema.GoogleCloudMlV1__Measurement]
	given fmtGoogleCloudMlV1MeasurementMetric: Format[Schema.GoogleCloudMlV1_Measurement_Metric] = Json.format[Schema.GoogleCloudMlV1_Measurement_Metric]
	given fmtGoogleCloudMlV1ListTrialsResponse: Format[Schema.GoogleCloudMlV1__ListTrialsResponse] = Json.format[Schema.GoogleCloudMlV1__ListTrialsResponse]
	given fmtGoogleCloudMlV1AddTrialMeasurementRequest: Format[Schema.GoogleCloudMlV1__AddTrialMeasurementRequest] = Json.format[Schema.GoogleCloudMlV1__AddTrialMeasurementRequest]
	given fmtGoogleCloudMlV1CompleteTrialRequest: Format[Schema.GoogleCloudMlV1__CompleteTrialRequest] = Json.format[Schema.GoogleCloudMlV1__CompleteTrialRequest]
	given fmtGoogleCloudMlV1CheckTrialEarlyStoppingStateRequest: Format[Schema.GoogleCloudMlV1__CheckTrialEarlyStoppingStateRequest] = Json.format[Schema.GoogleCloudMlV1__CheckTrialEarlyStoppingStateRequest]
	given fmtGoogleCloudMlV1StopTrialRequest: Format[Schema.GoogleCloudMlV1__StopTrialRequest] = Json.format[Schema.GoogleCloudMlV1__StopTrialRequest]
	given fmtGoogleCloudMlV1ListOptimalTrialsRequest: Format[Schema.GoogleCloudMlV1__ListOptimalTrialsRequest] = Json.format[Schema.GoogleCloudMlV1__ListOptimalTrialsRequest]
	given fmtGoogleCloudMlV1ListOptimalTrialsResponse: Format[Schema.GoogleCloudMlV1__ListOptimalTrialsResponse] = Json.format[Schema.GoogleCloudMlV1__ListOptimalTrialsResponse]
	given fmtGoogleIamV1SetIamPolicyRequest: Format[Schema.GoogleIamV1__SetIamPolicyRequest] = Json.format[Schema.GoogleIamV1__SetIamPolicyRequest]
	given fmtGoogleIamV1Policy: Format[Schema.GoogleIamV1__Policy] = Json.format[Schema.GoogleIamV1__Policy]
	given fmtGoogleIamV1Binding: Format[Schema.GoogleIamV1__Binding] = Json.format[Schema.GoogleIamV1__Binding]
	given fmtGoogleIamV1AuditConfig: Format[Schema.GoogleIamV1__AuditConfig] = Json.format[Schema.GoogleIamV1__AuditConfig]
	given fmtGoogleTypeExpr: Format[Schema.GoogleType__Expr] = Json.format[Schema.GoogleType__Expr]
	given fmtGoogleIamV1AuditLogConfig: Format[Schema.GoogleIamV1__AuditLogConfig] = Json.format[Schema.GoogleIamV1__AuditLogConfig]
	given fmtGoogleIamV1AuditLogConfigLogTypeEnum: Format[Schema.GoogleIamV1__AuditLogConfig.LogTypeEnum] = JsonEnumFormat[Schema.GoogleIamV1__AuditLogConfig.LogTypeEnum]
	given fmtGoogleIamV1TestIamPermissionsRequest: Format[Schema.GoogleIamV1__TestIamPermissionsRequest] = Json.format[Schema.GoogleIamV1__TestIamPermissionsRequest]
	given fmtGoogleIamV1TestIamPermissionsResponse: Format[Schema.GoogleIamV1__TestIamPermissionsResponse] = Json.format[Schema.GoogleIamV1__TestIamPermissionsResponse]
	given fmtGoogleCloudMlV1OperationMetadata: Format[Schema.GoogleCloudMlV1__OperationMetadata] = Json.format[Schema.GoogleCloudMlV1__OperationMetadata]
	given fmtGoogleCloudMlV1OperationMetadataOperationTypeEnum: Format[Schema.GoogleCloudMlV1__OperationMetadata.OperationTypeEnum] = JsonEnumFormat[Schema.GoogleCloudMlV1__OperationMetadata.OperationTypeEnum]
	given fmtGoogleCloudMlV1SuggestTrialsMetadata: Format[Schema.GoogleCloudMlV1__SuggestTrialsMetadata] = Json.format[Schema.GoogleCloudMlV1__SuggestTrialsMetadata]
	given fmtGoogleCloudMlV1SuggestTrialsResponse: Format[Schema.GoogleCloudMlV1__SuggestTrialsResponse] = Json.format[Schema.GoogleCloudMlV1__SuggestTrialsResponse]
	given fmtGoogleCloudMlV1SuggestTrialsResponseStudyStateEnum: Format[Schema.GoogleCloudMlV1__SuggestTrialsResponse.StudyStateEnum] = JsonEnumFormat[Schema.GoogleCloudMlV1__SuggestTrialsResponse.StudyStateEnum]
	given fmtGoogleCloudMlV1CheckTrialEarlyStoppingStateMetatdata: Format[Schema.GoogleCloudMlV1__CheckTrialEarlyStoppingStateMetatdata] = Json.format[Schema.GoogleCloudMlV1__CheckTrialEarlyStoppingStateMetatdata]
	given fmtGoogleCloudMlV1CheckTrialEarlyStoppingStateResponse: Format[Schema.GoogleCloudMlV1__CheckTrialEarlyStoppingStateResponse] = Json.format[Schema.GoogleCloudMlV1__CheckTrialEarlyStoppingStateResponse]
}
