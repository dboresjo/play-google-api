package com.boresjo.play.api.google.datapipelines

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaGoogleRpcStatus: Conversion[Schema.GoogleRpcStatus, Option[Schema.GoogleRpcStatus]] = (fun: Schema.GoogleRpcStatus) => Option(fun)
		given putSchemaGoogleCloudDatapipelinesV1JobStateEnum: Conversion[Schema.GoogleCloudDatapipelinesV1Job.StateEnum, Option[Schema.GoogleCloudDatapipelinesV1Job.StateEnum]] = (fun: Schema.GoogleCloudDatapipelinesV1Job.StateEnum) => Option(fun)
		given putSchemaGoogleCloudDatapipelinesV1DataflowJobDetails: Conversion[Schema.GoogleCloudDatapipelinesV1DataflowJobDetails, Option[Schema.GoogleCloudDatapipelinesV1DataflowJobDetails]] = (fun: Schema.GoogleCloudDatapipelinesV1DataflowJobDetails) => Option(fun)
		given putSchemaGoogleCloudDatapipelinesV1LaunchFlexTemplateRequest: Conversion[Schema.GoogleCloudDatapipelinesV1LaunchFlexTemplateRequest, Option[Schema.GoogleCloudDatapipelinesV1LaunchFlexTemplateRequest]] = (fun: Schema.GoogleCloudDatapipelinesV1LaunchFlexTemplateRequest) => Option(fun)
		given putSchemaGoogleCloudDatapipelinesV1LaunchTemplateRequest: Conversion[Schema.GoogleCloudDatapipelinesV1LaunchTemplateRequest, Option[Schema.GoogleCloudDatapipelinesV1LaunchTemplateRequest]] = (fun: Schema.GoogleCloudDatapipelinesV1LaunchTemplateRequest) => Option(fun)
		given putSchemaGoogleCloudDatapipelinesV1SdkVersionSdkSupportStatusEnum: Conversion[Schema.GoogleCloudDatapipelinesV1SdkVersion.SdkSupportStatusEnum, Option[Schema.GoogleCloudDatapipelinesV1SdkVersion.SdkSupportStatusEnum]] = (fun: Schema.GoogleCloudDatapipelinesV1SdkVersion.SdkSupportStatusEnum) => Option(fun)
		given putSchemaGoogleCloudDatapipelinesV1LaunchTemplateParameters: Conversion[Schema.GoogleCloudDatapipelinesV1LaunchTemplateParameters, Option[Schema.GoogleCloudDatapipelinesV1LaunchTemplateParameters]] = (fun: Schema.GoogleCloudDatapipelinesV1LaunchTemplateParameters) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putMapStringBigDecimal: Conversion[Map[String, BigDecimal], Option[Map[String, BigDecimal]]] = (fun: Map[String, BigDecimal]) => Option(fun)
		given putSchemaGoogleCloudDatapipelinesV1SdkVersion: Conversion[Schema.GoogleCloudDatapipelinesV1SdkVersion, Option[Schema.GoogleCloudDatapipelinesV1SdkVersion]] = (fun: Schema.GoogleCloudDatapipelinesV1SdkVersion) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaGoogleCloudDatapipelinesV1RuntimeEnvironment: Conversion[Schema.GoogleCloudDatapipelinesV1RuntimeEnvironment, Option[Schema.GoogleCloudDatapipelinesV1RuntimeEnvironment]] = (fun: Schema.GoogleCloudDatapipelinesV1RuntimeEnvironment) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaGoogleCloudDatapipelinesV1FlexTemplateRuntimeEnvironmentFlexrsGoalEnum: Conversion[Schema.GoogleCloudDatapipelinesV1FlexTemplateRuntimeEnvironment.FlexrsGoalEnum, Option[Schema.GoogleCloudDatapipelinesV1FlexTemplateRuntimeEnvironment.FlexrsGoalEnum]] = (fun: Schema.GoogleCloudDatapipelinesV1FlexTemplateRuntimeEnvironment.FlexrsGoalEnum) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaGoogleCloudDatapipelinesV1FlexTemplateRuntimeEnvironmentIpConfigurationEnum: Conversion[Schema.GoogleCloudDatapipelinesV1FlexTemplateRuntimeEnvironment.IpConfigurationEnum, Option[Schema.GoogleCloudDatapipelinesV1FlexTemplateRuntimeEnvironment.IpConfigurationEnum]] = (fun: Schema.GoogleCloudDatapipelinesV1FlexTemplateRuntimeEnvironment.IpConfigurationEnum) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putListSchemaGoogleCloudDatapipelinesV1Job: Conversion[List[Schema.GoogleCloudDatapipelinesV1Job], Option[List[Schema.GoogleCloudDatapipelinesV1Job]]] = (fun: List[Schema.GoogleCloudDatapipelinesV1Job]) => Option(fun)
		given putSchemaGoogleCloudDatapipelinesV1PipelineTypeEnum: Conversion[Schema.GoogleCloudDatapipelinesV1Pipeline.TypeEnum, Option[Schema.GoogleCloudDatapipelinesV1Pipeline.TypeEnum]] = (fun: Schema.GoogleCloudDatapipelinesV1Pipeline.TypeEnum) => Option(fun)
		given putSchemaGoogleCloudDatapipelinesV1ScheduleSpec: Conversion[Schema.GoogleCloudDatapipelinesV1ScheduleSpec, Option[Schema.GoogleCloudDatapipelinesV1ScheduleSpec]] = (fun: Schema.GoogleCloudDatapipelinesV1ScheduleSpec) => Option(fun)
		given putSchemaGoogleCloudDatapipelinesV1PipelineStateEnum: Conversion[Schema.GoogleCloudDatapipelinesV1Pipeline.StateEnum, Option[Schema.GoogleCloudDatapipelinesV1Pipeline.StateEnum]] = (fun: Schema.GoogleCloudDatapipelinesV1Pipeline.StateEnum) => Option(fun)
		given putSchemaGoogleCloudDatapipelinesV1Workload: Conversion[Schema.GoogleCloudDatapipelinesV1Workload, Option[Schema.GoogleCloudDatapipelinesV1Workload]] = (fun: Schema.GoogleCloudDatapipelinesV1Workload) => Option(fun)
		given putSchemaGoogleCloudDatapipelinesV1FlexTemplateRuntimeEnvironment: Conversion[Schema.GoogleCloudDatapipelinesV1FlexTemplateRuntimeEnvironment, Option[Schema.GoogleCloudDatapipelinesV1FlexTemplateRuntimeEnvironment]] = (fun: Schema.GoogleCloudDatapipelinesV1FlexTemplateRuntimeEnvironment) => Option(fun)
		given putSchemaGoogleCloudDatapipelinesV1RuntimeEnvironmentIpConfigurationEnum: Conversion[Schema.GoogleCloudDatapipelinesV1RuntimeEnvironment.IpConfigurationEnum, Option[Schema.GoogleCloudDatapipelinesV1RuntimeEnvironment.IpConfigurationEnum]] = (fun: Schema.GoogleCloudDatapipelinesV1RuntimeEnvironment.IpConfigurationEnum) => Option(fun)
		given putListSchemaGoogleCloudDatapipelinesV1Pipeline: Conversion[List[Schema.GoogleCloudDatapipelinesV1Pipeline], Option[List[Schema.GoogleCloudDatapipelinesV1Pipeline]]] = (fun: List[Schema.GoogleCloudDatapipelinesV1Pipeline]) => Option(fun)
		given putSchemaGoogleCloudDatapipelinesV1Job: Conversion[Schema.GoogleCloudDatapipelinesV1Job, Option[Schema.GoogleCloudDatapipelinesV1Job]] = (fun: Schema.GoogleCloudDatapipelinesV1Job) => Option(fun)
		given putSchemaGoogleCloudDatapipelinesV1LaunchFlexTemplateParameter: Conversion[Schema.GoogleCloudDatapipelinesV1LaunchFlexTemplateParameter, Option[Schema.GoogleCloudDatapipelinesV1LaunchFlexTemplateParameter]] = (fun: Schema.GoogleCloudDatapipelinesV1LaunchFlexTemplateParameter) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
