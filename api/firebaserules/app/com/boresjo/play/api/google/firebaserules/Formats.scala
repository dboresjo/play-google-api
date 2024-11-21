package com.boresjo.play.api.google.firebaserules

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtTestRulesetRequest: Format[Schema.TestRulesetRequest] = Json.format[Schema.TestRulesetRequest]
	given fmtSource: Format[Schema.Source] = Json.format[Schema.Source]
	given fmtTestSuite: Format[Schema.TestSuite] = Json.format[Schema.TestSuite]
	given fmtFile: Format[Schema.File] = Json.format[Schema.File]
	given fmtTestCase: Format[Schema.TestCase] = Json.format[Schema.TestCase]
	given fmtTestCaseExpectationEnum: Format[Schema.TestCase.ExpectationEnum] = JsonEnumFormat[Schema.TestCase.ExpectationEnum]
	given fmtFunctionMock: Format[Schema.FunctionMock] = Json.format[Schema.FunctionMock]
	given fmtTestCasePathEncodingEnum: Format[Schema.TestCase.PathEncodingEnum] = JsonEnumFormat[Schema.TestCase.PathEncodingEnum]
	given fmtTestCaseExpressionReportLevelEnum: Format[Schema.TestCase.ExpressionReportLevelEnum] = JsonEnumFormat[Schema.TestCase.ExpressionReportLevelEnum]
	given fmtArg: Format[Schema.Arg] = Json.format[Schema.Arg]
	given fmtResult: Format[Schema.Result] = Json.format[Schema.Result]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
	given fmtTestRulesetResponse: Format[Schema.TestRulesetResponse] = Json.format[Schema.TestRulesetResponse]
	given fmtIssue: Format[Schema.Issue] = Json.format[Schema.Issue]
	given fmtTestResult: Format[Schema.TestResult] = Json.format[Schema.TestResult]
	given fmtSourcePosition: Format[Schema.SourcePosition] = Json.format[Schema.SourcePosition]
	given fmtIssueSeverityEnum: Format[Schema.Issue.SeverityEnum] = JsonEnumFormat[Schema.Issue.SeverityEnum]
	given fmtTestResultStateEnum: Format[Schema.TestResult.StateEnum] = JsonEnumFormat[Schema.TestResult.StateEnum]
	given fmtFunctionCall: Format[Schema.FunctionCall] = Json.format[Schema.FunctionCall]
	given fmtVisitedExpression: Format[Schema.VisitedExpression] = Json.format[Schema.VisitedExpression]
	given fmtExpressionReport: Format[Schema.ExpressionReport] = Json.format[Schema.ExpressionReport]
	given fmtValueCount: Format[Schema.ValueCount] = Json.format[Schema.ValueCount]
	given fmtRuleset: Format[Schema.Ruleset] = Json.format[Schema.Ruleset]
	given fmtMetadata: Format[Schema.Metadata] = Json.format[Schema.Metadata]
	given fmtListRulesetsResponse: Format[Schema.ListRulesetsResponse] = Json.format[Schema.ListRulesetsResponse]
	given fmtRelease: Format[Schema.Release] = Json.format[Schema.Release]
	given fmtUpdateReleaseRequest: Format[Schema.UpdateReleaseRequest] = Json.format[Schema.UpdateReleaseRequest]
	given fmtListReleasesResponse: Format[Schema.ListReleasesResponse] = Json.format[Schema.ListReleasesResponse]
	given fmtGetReleaseExecutableResponse: Format[Schema.GetReleaseExecutableResponse] = Json.format[Schema.GetReleaseExecutableResponse]
	given fmtGetReleaseExecutableResponseLanguageEnum: Format[Schema.GetReleaseExecutableResponse.LanguageEnum] = JsonEnumFormat[Schema.GetReleaseExecutableResponse.LanguageEnum]
	given fmtGetReleaseExecutableResponseExecutableVersionEnum: Format[Schema.GetReleaseExecutableResponse.ExecutableVersionEnum] = JsonEnumFormat[Schema.GetReleaseExecutableResponse.ExecutableVersionEnum]
}
