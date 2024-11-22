package com.boresjo.play.api.google.script

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtGoogleAppsScriptTypeProcess: Format[Schema.GoogleAppsScriptTypeProcess] = Json.format[Schema.GoogleAppsScriptTypeProcess]
	given fmtGoogleAppsScriptTypeProcessRuntimeVersionEnum: Format[Schema.GoogleAppsScriptTypeProcess.RuntimeVersionEnum] = JsonEnumFormat[Schema.GoogleAppsScriptTypeProcess.RuntimeVersionEnum]
	given fmtGoogleAppsScriptTypeProcessUserAccessLevelEnum: Format[Schema.GoogleAppsScriptTypeProcess.UserAccessLevelEnum] = JsonEnumFormat[Schema.GoogleAppsScriptTypeProcess.UserAccessLevelEnum]
	given fmtGoogleAppsScriptTypeProcessProcessTypeEnum: Format[Schema.GoogleAppsScriptTypeProcess.ProcessTypeEnum] = JsonEnumFormat[Schema.GoogleAppsScriptTypeProcess.ProcessTypeEnum]
	given fmtGoogleAppsScriptTypeProcessProcessStatusEnum: Format[Schema.GoogleAppsScriptTypeProcess.ProcessStatusEnum] = JsonEnumFormat[Schema.GoogleAppsScriptTypeProcess.ProcessStatusEnum]
	given fmtListDeploymentsResponse: Format[Schema.ListDeploymentsResponse] = Json.format[Schema.ListDeploymentsResponse]
	given fmtDeployment: Format[Schema.Deployment] = Json.format[Schema.Deployment]
	given fmtDeploymentConfig: Format[Schema.DeploymentConfig] = Json.format[Schema.DeploymentConfig]
	given fmtEntryPoint: Format[Schema.EntryPoint] = Json.format[Schema.EntryPoint]
	given fmtGoogleAppsScriptTypeWebAppEntryPoint: Format[Schema.GoogleAppsScriptTypeWebAppEntryPoint] = Json.format[Schema.GoogleAppsScriptTypeWebAppEntryPoint]
	given fmtGoogleAppsScriptTypeWebAppConfig: Format[Schema.GoogleAppsScriptTypeWebAppConfig] = Json.format[Schema.GoogleAppsScriptTypeWebAppConfig]
	given fmtGoogleAppsScriptTypeAddOnEntryPoint: Format[Schema.GoogleAppsScriptTypeAddOnEntryPoint] = Json.format[Schema.GoogleAppsScriptTypeAddOnEntryPoint]
	given fmtGoogleAppsScriptTypeExecutionApiEntryPoint: Format[Schema.GoogleAppsScriptTypeExecutionApiEntryPoint] = Json.format[Schema.GoogleAppsScriptTypeExecutionApiEntryPoint]
	given fmtEntryPointEntryPointTypeEnum: Format[Schema.EntryPoint.EntryPointTypeEnum] = JsonEnumFormat[Schema.EntryPoint.EntryPointTypeEnum]
	given fmtCreateProjectRequest: Format[Schema.CreateProjectRequest] = Json.format[Schema.CreateProjectRequest]
	given fmtUpdateDeploymentRequest: Format[Schema.UpdateDeploymentRequest] = Json.format[Schema.UpdateDeploymentRequest]
	given fmtGoogleAppsScriptTypeFunctionSet: Format[Schema.GoogleAppsScriptTypeFunctionSet] = Json.format[Schema.GoogleAppsScriptTypeFunctionSet]
	given fmtGoogleAppsScriptTypeFunction: Format[Schema.GoogleAppsScriptTypeFunction] = Json.format[Schema.GoogleAppsScriptTypeFunction]
	given fmtExecutionRequest: Format[Schema.ExecutionRequest] = Json.format[Schema.ExecutionRequest]
	given fmtListUserProcessesResponse: Format[Schema.ListUserProcessesResponse] = Json.format[Schema.ListUserProcessesResponse]
	given fmtGoogleAppsScriptTypeExecutionApiConfig: Format[Schema.GoogleAppsScriptTypeExecutionApiConfig] = Json.format[Schema.GoogleAppsScriptTypeExecutionApiConfig]
	given fmtGoogleAppsScriptTypeExecutionApiConfigAccessEnum: Format[Schema.GoogleAppsScriptTypeExecutionApiConfig.AccessEnum] = JsonEnumFormat[Schema.GoogleAppsScriptTypeExecutionApiConfig.AccessEnum]
	given fmtScriptStackTraceElement: Format[Schema.ScriptStackTraceElement] = Json.format[Schema.ScriptStackTraceElement]
	given fmtMetricsValue: Format[Schema.MetricsValue] = Json.format[Schema.MetricsValue]
	given fmtMetrics: Format[Schema.Metrics] = Json.format[Schema.Metrics]
	given fmtExecutionError: Format[Schema.ExecutionError] = Json.format[Schema.ExecutionError]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtGoogleAppsScriptTypeWebAppConfigExecuteAsEnum: Format[Schema.GoogleAppsScriptTypeWebAppConfig.ExecuteAsEnum] = JsonEnumFormat[Schema.GoogleAppsScriptTypeWebAppConfig.ExecuteAsEnum]
	given fmtGoogleAppsScriptTypeWebAppConfigAccessEnum: Format[Schema.GoogleAppsScriptTypeWebAppConfig.AccessEnum] = JsonEnumFormat[Schema.GoogleAppsScriptTypeWebAppConfig.AccessEnum]
	given fmtContent: Format[Schema.Content] = Json.format[Schema.Content]
	given fmtFile: Format[Schema.File] = Json.format[Schema.File]
	given fmtValue: Format[Schema.Value] = Json.format[Schema.Value]
	given fmtStruct: Format[Schema.Struct] = Json.format[Schema.Struct]
	given fmtValueNullValueEnum: Format[Schema.Value.NullValueEnum] = JsonEnumFormat[Schema.Value.NullValueEnum]
	given fmtListValue: Format[Schema.ListValue] = Json.format[Schema.ListValue]
	given fmtExecutionResponse: Format[Schema.ExecutionResponse] = Json.format[Schema.ExecutionResponse]
	given fmtScriptExecutionResult: Format[Schema.ScriptExecutionResult] = Json.format[Schema.ScriptExecutionResult]
	given fmtVersion: Format[Schema.Version] = Json.format[Schema.Version]
	given fmtListVersionsResponse: Format[Schema.ListVersionsResponse] = Json.format[Schema.ListVersionsResponse]
	given fmtGoogleAppsScriptTypeAddOnEntryPointAddOnTypeEnum: Format[Schema.GoogleAppsScriptTypeAddOnEntryPoint.AddOnTypeEnum] = JsonEnumFormat[Schema.GoogleAppsScriptTypeAddOnEntryPoint.AddOnTypeEnum]
	given fmtGoogleAppsScriptTypeUser: Format[Schema.GoogleAppsScriptTypeUser] = Json.format[Schema.GoogleAppsScriptTypeUser]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtProject: Format[Schema.Project] = Json.format[Schema.Project]
	given fmtOperation: Format[Schema.Operation] = Json.format[Schema.Operation]
	given fmtFileTypeEnum: Format[Schema.File.TypeEnum] = JsonEnumFormat[Schema.File.TypeEnum]
	given fmtExecuteStreamResponse: Format[Schema.ExecuteStreamResponse] = Json.format[Schema.ExecuteStreamResponse]
	given fmtListScriptProcessesResponse: Format[Schema.ListScriptProcessesResponse] = Json.format[Schema.ListScriptProcessesResponse]
}
