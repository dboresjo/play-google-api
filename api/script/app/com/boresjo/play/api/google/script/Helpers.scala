package com.boresjo.play.api.google.script

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putSchemaGoogleAppsScriptTypeProcessRuntimeVersionEnum: Conversion[Schema.GoogleAppsScriptTypeProcess.RuntimeVersionEnum, Option[Schema.GoogleAppsScriptTypeProcess.RuntimeVersionEnum]] = (fun: Schema.GoogleAppsScriptTypeProcess.RuntimeVersionEnum) => Option(fun)
		given putSchemaGoogleAppsScriptTypeProcessUserAccessLevelEnum: Conversion[Schema.GoogleAppsScriptTypeProcess.UserAccessLevelEnum, Option[Schema.GoogleAppsScriptTypeProcess.UserAccessLevelEnum]] = (fun: Schema.GoogleAppsScriptTypeProcess.UserAccessLevelEnum) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaGoogleAppsScriptTypeProcessProcessTypeEnum: Conversion[Schema.GoogleAppsScriptTypeProcess.ProcessTypeEnum, Option[Schema.GoogleAppsScriptTypeProcess.ProcessTypeEnum]] = (fun: Schema.GoogleAppsScriptTypeProcess.ProcessTypeEnum) => Option(fun)
		given putSchemaGoogleAppsScriptTypeProcessProcessStatusEnum: Conversion[Schema.GoogleAppsScriptTypeProcess.ProcessStatusEnum, Option[Schema.GoogleAppsScriptTypeProcess.ProcessStatusEnum]] = (fun: Schema.GoogleAppsScriptTypeProcess.ProcessStatusEnum) => Option(fun)
		given putListSchemaDeployment: Conversion[List[Schema.Deployment], Option[List[Schema.Deployment]]] = (fun: List[Schema.Deployment]) => Option(fun)
		given putSchemaDeploymentConfig: Conversion[Schema.DeploymentConfig, Option[Schema.DeploymentConfig]] = (fun: Schema.DeploymentConfig) => Option(fun)
		given putListSchemaEntryPoint: Conversion[List[Schema.EntryPoint], Option[List[Schema.EntryPoint]]] = (fun: List[Schema.EntryPoint]) => Option(fun)
		given putSchemaGoogleAppsScriptTypeWebAppConfig: Conversion[Schema.GoogleAppsScriptTypeWebAppConfig, Option[Schema.GoogleAppsScriptTypeWebAppConfig]] = (fun: Schema.GoogleAppsScriptTypeWebAppConfig) => Option(fun)
		given putSchemaGoogleAppsScriptTypeAddOnEntryPoint: Conversion[Schema.GoogleAppsScriptTypeAddOnEntryPoint, Option[Schema.GoogleAppsScriptTypeAddOnEntryPoint]] = (fun: Schema.GoogleAppsScriptTypeAddOnEntryPoint) => Option(fun)
		given putSchemaGoogleAppsScriptTypeExecutionApiEntryPoint: Conversion[Schema.GoogleAppsScriptTypeExecutionApiEntryPoint, Option[Schema.GoogleAppsScriptTypeExecutionApiEntryPoint]] = (fun: Schema.GoogleAppsScriptTypeExecutionApiEntryPoint) => Option(fun)
		given putSchemaEntryPointEntryPointTypeEnum: Conversion[Schema.EntryPoint.EntryPointTypeEnum, Option[Schema.EntryPoint.EntryPointTypeEnum]] = (fun: Schema.EntryPoint.EntryPointTypeEnum) => Option(fun)
		given putSchemaGoogleAppsScriptTypeWebAppEntryPoint: Conversion[Schema.GoogleAppsScriptTypeWebAppEntryPoint, Option[Schema.GoogleAppsScriptTypeWebAppEntryPoint]] = (fun: Schema.GoogleAppsScriptTypeWebAppEntryPoint) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListSchemaGoogleAppsScriptTypeFunction: Conversion[List[Schema.GoogleAppsScriptTypeFunction], Option[List[Schema.GoogleAppsScriptTypeFunction]]] = (fun: List[Schema.GoogleAppsScriptTypeFunction]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putListJsValue: Conversion[List[JsValue], Option[List[JsValue]]] = (fun: List[JsValue]) => Option(fun)
		given putListSchemaGoogleAppsScriptTypeProcess: Conversion[List[Schema.GoogleAppsScriptTypeProcess], Option[List[Schema.GoogleAppsScriptTypeProcess]]] = (fun: List[Schema.GoogleAppsScriptTypeProcess]) => Option(fun)
		given putSchemaGoogleAppsScriptTypeExecutionApiConfigAccessEnum: Conversion[Schema.GoogleAppsScriptTypeExecutionApiConfig.AccessEnum, Option[Schema.GoogleAppsScriptTypeExecutionApiConfig.AccessEnum]] = (fun: Schema.GoogleAppsScriptTypeExecutionApiConfig.AccessEnum) => Option(fun)
		given putListSchemaMetricsValue: Conversion[List[Schema.MetricsValue], Option[List[Schema.MetricsValue]]] = (fun: List[Schema.MetricsValue]) => Option(fun)
		given putListSchemaScriptStackTraceElement: Conversion[List[Schema.ScriptStackTraceElement], Option[List[Schema.ScriptStackTraceElement]]] = (fun: List[Schema.ScriptStackTraceElement]) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putSchemaGoogleAppsScriptTypeWebAppConfigExecuteAsEnum: Conversion[Schema.GoogleAppsScriptTypeWebAppConfig.ExecuteAsEnum, Option[Schema.GoogleAppsScriptTypeWebAppConfig.ExecuteAsEnum]] = (fun: Schema.GoogleAppsScriptTypeWebAppConfig.ExecuteAsEnum) => Option(fun)
		given putSchemaGoogleAppsScriptTypeWebAppConfigAccessEnum: Conversion[Schema.GoogleAppsScriptTypeWebAppConfig.AccessEnum, Option[Schema.GoogleAppsScriptTypeWebAppConfig.AccessEnum]] = (fun: Schema.GoogleAppsScriptTypeWebAppConfig.AccessEnum) => Option(fun)
		given putListSchemaFile: Conversion[List[Schema.File], Option[List[Schema.File]]] = (fun: List[Schema.File]) => Option(fun)
		given putSchemaGoogleAppsScriptTypeExecutionApiConfig: Conversion[Schema.GoogleAppsScriptTypeExecutionApiConfig, Option[Schema.GoogleAppsScriptTypeExecutionApiConfig]] = (fun: Schema.GoogleAppsScriptTypeExecutionApiConfig) => Option(fun)
		given putSchemaStruct: Conversion[Schema.Struct, Option[Schema.Struct]] = (fun: Schema.Struct) => Option(fun)
		given putSchemaValueNullValueEnum: Conversion[Schema.Value.NullValueEnum, Option[Schema.Value.NullValueEnum]] = (fun: Schema.Value.NullValueEnum) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putSchemaListValue: Conversion[Schema.ListValue, Option[Schema.ListValue]] = (fun: Schema.ListValue) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putMapStringSchemaValue: Conversion[Map[String, Schema.Value], Option[Map[String, Schema.Value]]] = (fun: Map[String, Schema.Value]) => Option(fun)
		given putJsValue: Conversion[JsValue, Option[JsValue]] = (fun: JsValue) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putListSchemaValue: Conversion[List[Schema.Value], Option[List[Schema.Value]]] = (fun: List[Schema.Value]) => Option(fun)
		given putSchemaValue: Conversion[Schema.Value, Option[Schema.Value]] = (fun: Schema.Value) => Option(fun)
		given putListSchemaVersion: Conversion[List[Schema.Version], Option[List[Schema.Version]]] = (fun: List[Schema.Version]) => Option(fun)
		given putSchemaGoogleAppsScriptTypeAddOnEntryPointAddOnTypeEnum: Conversion[Schema.GoogleAppsScriptTypeAddOnEntryPoint.AddOnTypeEnum, Option[Schema.GoogleAppsScriptTypeAddOnEntryPoint.AddOnTypeEnum]] = (fun: Schema.GoogleAppsScriptTypeAddOnEntryPoint.AddOnTypeEnum) => Option(fun)
		given putSchemaGoogleAppsScriptTypeUser: Conversion[Schema.GoogleAppsScriptTypeUser, Option[Schema.GoogleAppsScriptTypeUser]] = (fun: Schema.GoogleAppsScriptTypeUser) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putSchemaGoogleAppsScriptTypeFunctionSet: Conversion[Schema.GoogleAppsScriptTypeFunctionSet, Option[Schema.GoogleAppsScriptTypeFunctionSet]] = (fun: Schema.GoogleAppsScriptTypeFunctionSet) => Option(fun)
		given putSchemaFileTypeEnum: Conversion[Schema.File.TypeEnum, Option[Schema.File.TypeEnum]] = (fun: Schema.File.TypeEnum) => Option(fun)
		given putSchemaScriptExecutionResult: Conversion[Schema.ScriptExecutionResult, Option[Schema.ScriptExecutionResult]] = (fun: Schema.ScriptExecutionResult) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
