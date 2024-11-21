package com.boresjo.play.api.google.datapipelines

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtGoogleCloudDatapipelinesV1Job: Format[Schema.GoogleCloudDatapipelinesV1Job] = Json.format[Schema.GoogleCloudDatapipelinesV1Job]
	given fmtGoogleRpcStatus: Format[Schema.GoogleRpcStatus] = Json.format[Schema.GoogleRpcStatus]
	given fmtGoogleCloudDatapipelinesV1JobStateEnum: Format[Schema.GoogleCloudDatapipelinesV1Job.StateEnum] = JsonEnumFormat[Schema.GoogleCloudDatapipelinesV1Job.StateEnum]
	given fmtGoogleCloudDatapipelinesV1DataflowJobDetails: Format[Schema.GoogleCloudDatapipelinesV1DataflowJobDetails] = Json.format[Schema.GoogleCloudDatapipelinesV1DataflowJobDetails]
	given fmtGoogleCloudDatapipelinesV1Workload: Format[Schema.GoogleCloudDatapipelinesV1Workload] = Json.format[Schema.GoogleCloudDatapipelinesV1Workload]
	given fmtGoogleCloudDatapipelinesV1LaunchFlexTemplateRequest: Format[Schema.GoogleCloudDatapipelinesV1LaunchFlexTemplateRequest] = Json.format[Schema.GoogleCloudDatapipelinesV1LaunchFlexTemplateRequest]
	given fmtGoogleCloudDatapipelinesV1LaunchTemplateRequest: Format[Schema.GoogleCloudDatapipelinesV1LaunchTemplateRequest] = Json.format[Schema.GoogleCloudDatapipelinesV1LaunchTemplateRequest]
	given fmtGoogleCloudDatapipelinesV1SdkVersion: Format[Schema.GoogleCloudDatapipelinesV1SdkVersion] = Json.format[Schema.GoogleCloudDatapipelinesV1SdkVersion]
	given fmtGoogleCloudDatapipelinesV1SdkVersionSdkSupportStatusEnum: Format[Schema.GoogleCloudDatapipelinesV1SdkVersion.SdkSupportStatusEnum] = JsonEnumFormat[Schema.GoogleCloudDatapipelinesV1SdkVersion.SdkSupportStatusEnum]
	given fmtGoogleCloudDatapipelinesV1LaunchTemplateParameters: Format[Schema.GoogleCloudDatapipelinesV1LaunchTemplateParameters] = Json.format[Schema.GoogleCloudDatapipelinesV1LaunchTemplateParameters]
	given fmtGoogleCloudDatapipelinesV1RuntimeEnvironment: Format[Schema.GoogleCloudDatapipelinesV1RuntimeEnvironment] = Json.format[Schema.GoogleCloudDatapipelinesV1RuntimeEnvironment]
	given fmtGoogleProtobufEmpty: Format[Schema.GoogleProtobufEmpty] = Json.format[Schema.GoogleProtobufEmpty]
	given fmtGoogleCloudDatapipelinesV1FlexTemplateRuntimeEnvironment: Format[Schema.GoogleCloudDatapipelinesV1FlexTemplateRuntimeEnvironment] = Json.format[Schema.GoogleCloudDatapipelinesV1FlexTemplateRuntimeEnvironment]
	given fmtGoogleCloudDatapipelinesV1FlexTemplateRuntimeEnvironmentFlexrsGoalEnum: Format[Schema.GoogleCloudDatapipelinesV1FlexTemplateRuntimeEnvironment.FlexrsGoalEnum] = JsonEnumFormat[Schema.GoogleCloudDatapipelinesV1FlexTemplateRuntimeEnvironment.FlexrsGoalEnum]
	given fmtGoogleCloudDatapipelinesV1FlexTemplateRuntimeEnvironmentIpConfigurationEnum: Format[Schema.GoogleCloudDatapipelinesV1FlexTemplateRuntimeEnvironment.IpConfigurationEnum] = JsonEnumFormat[Schema.GoogleCloudDatapipelinesV1FlexTemplateRuntimeEnvironment.IpConfigurationEnum]
	given fmtGoogleCloudDatapipelinesV1ListJobsResponse: Format[Schema.GoogleCloudDatapipelinesV1ListJobsResponse] = Json.format[Schema.GoogleCloudDatapipelinesV1ListJobsResponse]
	given fmtGoogleCloudDatapipelinesV1Pipeline: Format[Schema.GoogleCloudDatapipelinesV1Pipeline] = Json.format[Schema.GoogleCloudDatapipelinesV1Pipeline]
	given fmtGoogleCloudDatapipelinesV1PipelineTypeEnum: Format[Schema.GoogleCloudDatapipelinesV1Pipeline.TypeEnum] = JsonEnumFormat[Schema.GoogleCloudDatapipelinesV1Pipeline.TypeEnum]
	given fmtGoogleCloudDatapipelinesV1ScheduleSpec: Format[Schema.GoogleCloudDatapipelinesV1ScheduleSpec] = Json.format[Schema.GoogleCloudDatapipelinesV1ScheduleSpec]
	given fmtGoogleCloudDatapipelinesV1PipelineStateEnum: Format[Schema.GoogleCloudDatapipelinesV1Pipeline.StateEnum] = JsonEnumFormat[Schema.GoogleCloudDatapipelinesV1Pipeline.StateEnum]
	given fmtGoogleCloudDatapipelinesV1LaunchFlexTemplateParameter: Format[Schema.GoogleCloudDatapipelinesV1LaunchFlexTemplateParameter] = Json.format[Schema.GoogleCloudDatapipelinesV1LaunchFlexTemplateParameter]
	given fmtGoogleCloudDatapipelinesV1StopPipelineRequest: Format[Schema.GoogleCloudDatapipelinesV1StopPipelineRequest] = Json.format[Schema.GoogleCloudDatapipelinesV1StopPipelineRequest]
	given fmtGoogleCloudDatapipelinesV1RuntimeEnvironmentIpConfigurationEnum: Format[Schema.GoogleCloudDatapipelinesV1RuntimeEnvironment.IpConfigurationEnum] = JsonEnumFormat[Schema.GoogleCloudDatapipelinesV1RuntimeEnvironment.IpConfigurationEnum]
	given fmtGoogleCloudDatapipelinesV1ListPipelinesResponse: Format[Schema.GoogleCloudDatapipelinesV1ListPipelinesResponse] = Json.format[Schema.GoogleCloudDatapipelinesV1ListPipelinesResponse]
	given fmtGoogleCloudDatapipelinesV1RunPipelineResponse: Format[Schema.GoogleCloudDatapipelinesV1RunPipelineResponse] = Json.format[Schema.GoogleCloudDatapipelinesV1RunPipelineResponse]
	given fmtGoogleCloudDatapipelinesV1RunPipelineRequest: Format[Schema.GoogleCloudDatapipelinesV1RunPipelineRequest] = Json.format[Schema.GoogleCloudDatapipelinesV1RunPipelineRequest]
}