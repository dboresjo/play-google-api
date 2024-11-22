package com.boresjo.play.api.google.firebaserules

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putSchemaSource: Conversion[Schema.Source, Option[Schema.Source]] = (fun: Schema.Source) => Option(fun)
		given putSchemaTestSuite: Conversion[Schema.TestSuite, Option[Schema.TestSuite]] = (fun: Schema.TestSuite) => Option(fun)
		given putListSchemaFile: Conversion[List[Schema.File], Option[List[Schema.File]]] = (fun: List[Schema.File]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListSchemaTestCase: Conversion[List[Schema.TestCase], Option[List[Schema.TestCase]]] = (fun: List[Schema.TestCase]) => Option(fun)
		given putSchemaTestCaseExpectationEnum: Conversion[Schema.TestCase.ExpectationEnum, Option[Schema.TestCase.ExpectationEnum]] = (fun: Schema.TestCase.ExpectationEnum) => Option(fun)
		given putJsValue: Conversion[JsValue, Option[JsValue]] = (fun: JsValue) => Option(fun)
		given putListSchemaFunctionMock: Conversion[List[Schema.FunctionMock], Option[List[Schema.FunctionMock]]] = (fun: List[Schema.FunctionMock]) => Option(fun)
		given putSchemaTestCasePathEncodingEnum: Conversion[Schema.TestCase.PathEncodingEnum, Option[Schema.TestCase.PathEncodingEnum]] = (fun: Schema.TestCase.PathEncodingEnum) => Option(fun)
		given putSchemaTestCaseExpressionReportLevelEnum: Conversion[Schema.TestCase.ExpressionReportLevelEnum, Option[Schema.TestCase.ExpressionReportLevelEnum]] = (fun: Schema.TestCase.ExpressionReportLevelEnum) => Option(fun)
		given putListSchemaArg: Conversion[List[Schema.Arg], Option[List[Schema.Arg]]] = (fun: List[Schema.Arg]) => Option(fun)
		given putSchemaResult: Conversion[Schema.Result, Option[Schema.Result]] = (fun: Schema.Result) => Option(fun)
		given putSchemaEmpty: Conversion[Schema.Empty, Option[Schema.Empty]] = (fun: Schema.Empty) => Option(fun)
		given putListSchemaIssue: Conversion[List[Schema.Issue], Option[List[Schema.Issue]]] = (fun: List[Schema.Issue]) => Option(fun)
		given putListSchemaTestResult: Conversion[List[Schema.TestResult], Option[List[Schema.TestResult]]] = (fun: List[Schema.TestResult]) => Option(fun)
		given putSchemaSourcePosition: Conversion[Schema.SourcePosition, Option[Schema.SourcePosition]] = (fun: Schema.SourcePosition) => Option(fun)
		given putSchemaIssueSeverityEnum: Conversion[Schema.Issue.SeverityEnum, Option[Schema.Issue.SeverityEnum]] = (fun: Schema.Issue.SeverityEnum) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaTestResultStateEnum: Conversion[Schema.TestResult.StateEnum, Option[Schema.TestResult.StateEnum]] = (fun: Schema.TestResult.StateEnum) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putListSchemaFunctionCall: Conversion[List[Schema.FunctionCall], Option[List[Schema.FunctionCall]]] = (fun: List[Schema.FunctionCall]) => Option(fun)
		given putListSchemaVisitedExpression: Conversion[List[Schema.VisitedExpression], Option[List[Schema.VisitedExpression]]] = (fun: List[Schema.VisitedExpression]) => Option(fun)
		given putListSchemaExpressionReport: Conversion[List[Schema.ExpressionReport], Option[List[Schema.ExpressionReport]]] = (fun: List[Schema.ExpressionReport]) => Option(fun)
		given putListJsValue: Conversion[List[JsValue], Option[List[JsValue]]] = (fun: List[JsValue]) => Option(fun)
		given putListSchemaValueCount: Conversion[List[Schema.ValueCount], Option[List[Schema.ValueCount]]] = (fun: List[Schema.ValueCount]) => Option(fun)
		given putSchemaMetadata: Conversion[Schema.Metadata, Option[Schema.Metadata]] = (fun: Schema.Metadata) => Option(fun)
		given putListSchemaRuleset: Conversion[List[Schema.Ruleset], Option[List[Schema.Ruleset]]] = (fun: List[Schema.Ruleset]) => Option(fun)
		given putSchemaRelease: Conversion[Schema.Release, Option[Schema.Release]] = (fun: Schema.Release) => Option(fun)
		given putListSchemaRelease: Conversion[List[Schema.Release], Option[List[Schema.Release]]] = (fun: List[Schema.Release]) => Option(fun)
		given putSchemaGetReleaseExecutableResponseLanguageEnum: Conversion[Schema.GetReleaseExecutableResponse.LanguageEnum, Option[Schema.GetReleaseExecutableResponse.LanguageEnum]] = (fun: Schema.GetReleaseExecutableResponse.LanguageEnum) => Option(fun)
		given putSchemaGetReleaseExecutableResponseExecutableVersionEnum: Conversion[Schema.GetReleaseExecutableResponse.ExecutableVersionEnum, Option[Schema.GetReleaseExecutableResponse.ExecutableVersionEnum]] = (fun: Schema.GetReleaseExecutableResponse.ExecutableVersionEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
