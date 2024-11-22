package com.boresjo.play.api.google.websecurityscanner

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaScanRunExecutionStateEnum: Conversion[Schema.ScanRun.ExecutionStateEnum, Option[Schema.ScanRun.ExecutionStateEnum]] = (fun: Schema.ScanRun.ExecutionStateEnum) => Option(fun)
		given putSchemaScanRunErrorTrace: Conversion[Schema.ScanRunErrorTrace, Option[Schema.ScanRunErrorTrace]] = (fun: Schema.ScanRunErrorTrace) => Option(fun)
		given putListSchemaScanRunWarningTrace: Conversion[List[Schema.ScanRunWarningTrace], Option[List[Schema.ScanRunWarningTrace]]] = (fun: List[Schema.ScanRunWarningTrace]) => Option(fun)
		given putSchemaScanRunResultStateEnum: Conversion[Schema.ScanRun.ResultStateEnum, Option[Schema.ScanRun.ResultStateEnum]] = (fun: Schema.ScanRun.ResultStateEnum) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaXssAttackVectorEnum: Conversion[Schema.Xss.AttackVectorEnum, Option[Schema.Xss.AttackVectorEnum]] = (fun: Schema.Xss.AttackVectorEnum) => Option(fun)
		given putListSchemaFindingTypeStats: Conversion[List[Schema.FindingTypeStats], Option[List[Schema.FindingTypeStats]]] = (fun: List[Schema.FindingTypeStats]) => Option(fun)
		given putSchemaGoogleAccount: Conversion[Schema.GoogleAccount, Option[Schema.GoogleAccount]] = (fun: Schema.GoogleAccount) => Option(fun)
		given putSchemaCustomAccount: Conversion[Schema.CustomAccount, Option[Schema.CustomAccount]] = (fun: Schema.CustomAccount) => Option(fun)
		given putSchemaIapCredential: Conversion[Schema.IapCredential, Option[Schema.IapCredential]] = (fun: Schema.IapCredential) => Option(fun)
		given putListSchemaFinding: Conversion[List[Schema.Finding], Option[List[Schema.Finding]]] = (fun: List[Schema.Finding]) => Option(fun)
		given putSchemaSchedule: Conversion[Schema.Schedule, Option[Schema.Schedule]] = (fun: Schema.Schedule) => Option(fun)
		given putSchemaScanConfigRiskLevelEnum: Conversion[Schema.ScanConfig.RiskLevelEnum, Option[Schema.ScanConfig.RiskLevelEnum]] = (fun: Schema.ScanConfig.RiskLevelEnum) => Option(fun)
		given putSchemaScanConfigUserAgentEnum: Conversion[Schema.ScanConfig.UserAgentEnum, Option[Schema.ScanConfig.UserAgentEnum]] = (fun: Schema.ScanConfig.UserAgentEnum) => Option(fun)
		given putSchemaScanConfigExportToSecurityCommandCenterEnum: Conversion[Schema.ScanConfig.ExportToSecurityCommandCenterEnum, Option[Schema.ScanConfig.ExportToSecurityCommandCenterEnum]] = (fun: Schema.ScanConfig.ExportToSecurityCommandCenterEnum) => Option(fun)
		given putSchemaAuthentication: Conversion[Schema.Authentication, Option[Schema.Authentication]] = (fun: Schema.Authentication) => Option(fun)
		given putSchemaScanConfigError: Conversion[Schema.ScanConfigError, Option[Schema.ScanConfigError]] = (fun: Schema.ScanConfigError) => Option(fun)
		given putSchemaScanRunErrorTraceCodeEnum: Conversion[Schema.ScanRunErrorTrace.CodeEnum, Option[Schema.ScanRunErrorTrace.CodeEnum]] = (fun: Schema.ScanRunErrorTrace.CodeEnum) => Option(fun)
		given putSchemaScanRunWarningTraceCodeEnum: Conversion[Schema.ScanRunWarningTrace.CodeEnum, Option[Schema.ScanRunWarningTrace.CodeEnum]] = (fun: Schema.ScanRunWarningTrace.CodeEnum) => Option(fun)
		given putSchemaIapTestServiceAccountInfo: Conversion[Schema.IapTestServiceAccountInfo, Option[Schema.IapTestServiceAccountInfo]] = (fun: Schema.IapTestServiceAccountInfo) => Option(fun)
		given putListSchemaCrawledUrl: Conversion[List[Schema.CrawledUrl], Option[List[Schema.CrawledUrl]]] = (fun: List[Schema.CrawledUrl]) => Option(fun)
		given putListSchemaScanConfig: Conversion[List[Schema.ScanConfig], Option[List[Schema.ScanConfig]]] = (fun: List[Schema.ScanConfig]) => Option(fun)
		given putSchemaScanConfigErrorCodeEnum: Conversion[Schema.ScanConfigError.CodeEnum, Option[Schema.ScanConfigError.CodeEnum]] = (fun: Schema.ScanConfigError.CodeEnum) => Option(fun)
		given putSchemaForm: Conversion[Schema.Form, Option[Schema.Form]] = (fun: Schema.Form) => Option(fun)
		given putSchemaXss: Conversion[Schema.Xss, Option[Schema.Xss]] = (fun: Schema.Xss) => Option(fun)
		given putSchemaOutdatedLibrary: Conversion[Schema.OutdatedLibrary, Option[Schema.OutdatedLibrary]] = (fun: Schema.OutdatedLibrary) => Option(fun)
		given putSchemaVulnerableParameters: Conversion[Schema.VulnerableParameters, Option[Schema.VulnerableParameters]] = (fun: Schema.VulnerableParameters) => Option(fun)
		given putSchemaXxe: Conversion[Schema.Xxe, Option[Schema.Xxe]] = (fun: Schema.Xxe) => Option(fun)
		given putSchemaVulnerableHeaders: Conversion[Schema.VulnerableHeaders, Option[Schema.VulnerableHeaders]] = (fun: Schema.VulnerableHeaders) => Option(fun)
		given putSchemaViolatingResource: Conversion[Schema.ViolatingResource, Option[Schema.ViolatingResource]] = (fun: Schema.ViolatingResource) => Option(fun)
		given putSchemaFindingSeverityEnum: Conversion[Schema.Finding.SeverityEnum, Option[Schema.Finding.SeverityEnum]] = (fun: Schema.Finding.SeverityEnum) => Option(fun)
		given putListSchemaHeader: Conversion[List[Schema.Header], Option[List[Schema.Header]]] = (fun: List[Schema.Header]) => Option(fun)
		given putSchemaXxePayloadLocationEnum: Conversion[Schema.Xxe.PayloadLocationEnum, Option[Schema.Xxe.PayloadLocationEnum]] = (fun: Schema.Xxe.PayloadLocationEnum) => Option(fun)
		given putListSchemaScanRun: Conversion[List[Schema.ScanRun], Option[List[Schema.ScanRun]]] = (fun: List[Schema.ScanRun]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
