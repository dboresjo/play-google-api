package com.boresjo.play.api.google.firebaserules

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class TestRulesetRequest(
	  /** Optional `Source` to be checked for correctness. This field must not be set when the resource name refers to a `Ruleset`. */
		source: Option[Schema.Source] = None,
	  /** The tests to execute against the `Source`. When `Source` is provided inline, the test cases will only be run if the `Source` is syntactically and semantically valid. Inline `TestSuite` to run. */
		testSuite: Option[Schema.TestSuite] = None
	)
	
	case class Source(
	  /** Required. `File` set constituting the `Source` bundle. */
		files: Option[List[Schema.File]] = None
	)
	
	case class File(
	  /** Required. Textual Content. */
		content: Option[String] = None,
	  /** Required. File name. */
		name: Option[String] = None,
	  /** Fingerprint (e.g. github sha) associated with the `File`. */
		fingerprint: Option[String] = None
	)
	
	case class TestSuite(
	  /** Collection of test cases associated with the `TestSuite`. */
		testCases: Option[List[Schema.TestCase]] = None
	)
	
	object TestCase {
		enum ExpectationEnum extends Enum[ExpectationEnum] { case EXPECTATION_UNSPECIFIED, ALLOW, DENY }
		enum PathEncodingEnum extends Enum[PathEncodingEnum] { case ENCODING_UNSPECIFIED, URL_ENCODED, PLAIN }
		enum ExpressionReportLevelEnum extends Enum[ExpressionReportLevelEnum] { case LEVEL_UNSPECIFIED, NONE, FULL, VISITED }
	}
	case class TestCase(
	  /** Test expectation. */
		expectation: Option[Schema.TestCase.ExpectationEnum] = None,
	  /** Request context. The exact format of the request context is service-dependent. See the appropriate service documentation for information about the supported fields and types on the request. Minimally, all services support the following fields and types: Request field | Type ---------------|----------------- auth.uid | `string` auth.token | `map` headers | `map` method | `string` params | `map` path | `string` time | `google.protobuf.Timestamp` If the request value is not well-formed for the service, the request will be rejected as an invalid argument. */
		request: Option[JsValue] = None,
	  /** Optional resource value as it appears in persistent storage before the request is fulfilled. The resource type depends on the `request.path` value. */
		resource: Option[JsValue] = None,
	  /** Optional function mocks for service-defined functions. If not set, any service defined function is expected to return an error, which may or may not influence the test outcome. */
		functionMocks: Option[List[Schema.FunctionMock]] = None,
	  /** Specifies whether paths (such as request.path) are encoded and how. */
		pathEncoding: Option[Schema.TestCase.PathEncodingEnum] = None,
	  /** Specifies what should be included in the response. */
		expressionReportLevel: Option[Schema.TestCase.ExpressionReportLevelEnum] = None
	)
	
	case class FunctionMock(
	  /** The name of the function. The function name must match one provided by a service declaration. */
		function: Option[String] = None,
	  /** The list of `Arg` values to match. The order in which the arguments are provided is the order in which they must appear in the function invocation. */
		args: Option[List[Schema.Arg]] = None,
	  /** The mock result of the function call. */
		result: Option[Schema.Result] = None
	)
	
	case class Arg(
	  /** Argument exactly matches value provided. */
		exactValue: Option[JsValue] = None,
	  /** Argument matches any value provided. */
		anyValue: Option[Schema.Empty] = None
	)
	
	case class Empty(
	
	)
	
	case class Result(
	  /** The result is an actual value. The type of the value must match that of the type declared by the service. */
		value: Option[JsValue] = None,
	  /** The result is undefined, meaning the result could not be computed. */
		undefined: Option[Schema.Empty] = None
	)
	
	case class TestRulesetResponse(
	  /** Syntactic and semantic `Source` issues of varying severity. Issues of `ERROR` severity will prevent tests from executing. */
		issues: Option[List[Schema.Issue]] = None,
	  /** The set of test results given the test cases in the `TestSuite`. The results will appear in the same order as the test cases appear in the `TestSuite`. */
		testResults: Option[List[Schema.TestResult]] = None
	)
	
	object Issue {
		enum SeverityEnum extends Enum[SeverityEnum] { case SEVERITY_UNSPECIFIED, DEPRECATION, WARNING, ERROR }
	}
	case class Issue(
	  /** Position of the issue in the `Source`. */
		sourcePosition: Option[Schema.SourcePosition] = None,
	  /** Short error description. */
		description: Option[String] = None,
	  /** The severity of the issue. */
		severity: Option[Schema.Issue.SeverityEnum] = None
	)
	
	case class SourcePosition(
	  /** Name of the `File`. */
		fileName: Option[String] = None,
	  /** Line number of the source fragment. 1-based. */
		line: Option[Int] = None,
	  /** First column on the source line associated with the source fragment. */
		column: Option[Int] = None,
	  /** Start position relative to the beginning of the file. */
		currentOffset: Option[Int] = None,
	  /** End position relative to the beginning of the file. */
		endOffset: Option[Int] = None
	)
	
	object TestResult {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, SUCCESS, FAILURE }
	}
	case class TestResult(
	  /** State of the test. */
		state: Option[Schema.TestResult.StateEnum] = None,
	  /** Debug messages related to test execution issues encountered during evaluation. Debug messages may be related to too many or too few invocations of function mocks or to runtime errors that occur during evaluation. For example: ```Unable to read variable [name: "resource"]``` */
		debugMessages: Option[List[String]] = None,
	  /** Position in the `Source` or `Ruleset` where the principle runtime error occurs. Evaluation of an expression may result in an error. Rules are deny by default, so a `DENY` expectation when an error is generated is valid. When there is a `DENY` with an error, the `SourcePosition` is returned. E.g. `error_position { line: 19 column: 37 }` */
		errorPosition: Option[Schema.SourcePosition] = None,
	  /** The set of function calls made to service-defined methods. Function calls are included in the order in which they are encountered during evaluation, are provided for both mocked and unmocked functions, and included on the response regardless of the test `state`. */
		functionCalls: Option[List[Schema.FunctionCall]] = None,
	  /** The set of visited permission expressions for a given test. This returns the positions and evaluation results of all visited permission expressions which were relevant to the test case, e.g. ``` match /path { allow read if: } ``` For a detailed report of the intermediate evaluation states, see the `expression_reports` field */
		visitedExpressions: Option[List[Schema.VisitedExpression]] = None,
	  /** The mapping from expression in the ruleset AST to the values they were evaluated to. Partially-nested to mirror AST structure. Note that this field is actually tracking expressions and not permission statements in contrast to the "visited_expressions" field above. Literal expressions are omitted. */
		expressionReports: Option[List[Schema.ExpressionReport]] = None
	)
	
	case class FunctionCall(
	  /** Name of the function invoked. */
		function: Option[String] = None,
	  /** The arguments that were provided to the function. */
		args: Option[List[JsValue]] = None
	)
	
	case class VisitedExpression(
	  /** Position in the `Source` or `Ruleset` where an expression was visited. */
		sourcePosition: Option[Schema.SourcePosition] = None,
	  /** The evaluated value for the visited expression, e.g. true/false */
		value: Option[JsValue] = None
	)
	
	case class ExpressionReport(
	  /** Position of expression in original rules source. */
		sourcePosition: Option[Schema.SourcePosition] = None,
	  /** Values that this expression evaluated to when encountered. */
		values: Option[List[Schema.ValueCount]] = None,
	  /** Subexpressions */
		children: Option[List[Schema.ExpressionReport]] = None
	)
	
	case class ValueCount(
	  /** The return value of the expression */
		value: Option[JsValue] = None,
	  /** The amount of times that expression returned. */
		count: Option[Int] = None
	)
	
	case class Ruleset(
	  /** Output only. Name of the `Ruleset`. The ruleset_id is auto generated by the service. Format: `projects/{project_id}/rulesets/{ruleset_id}` */
		name: Option[String] = None,
	  /** Required. `Source` for the `Ruleset`. */
		source: Option[Schema.Source] = None,
	  /** Output only. Time the `Ruleset` was created. */
		createTime: Option[String] = None,
	  /** Output only. The metadata for this ruleset. */
		metadata: Option[Schema.Metadata] = None,
	  /** Immutable. Intended resource to which this Ruleset should be released. May be left blank to signify the resource associated with the default release. Expected format: firestore.googleapis.com/projects//databases/ */
		attachmentPoint: Option[String] = None
	)
	
	case class Metadata(
	  /** Services that this ruleset has declarations for (e.g., "cloud.firestore"). There may be 0+ of these. */
		services: Option[List[String]] = None
	)
	
	case class ListRulesetsResponse(
	  /** List of `Ruleset` instances. */
		rulesets: Option[List[Schema.Ruleset]] = None,
	  /** The pagination token to retrieve the next page of results. If the value is empty, no further results remain. */
		nextPageToken: Option[String] = None
	)
	
	case class Release(
	  /** Required. Format: `projects/{project_id}/releases/{release_id}` */
		name: Option[String] = None,
	  /** Required. Name of the `Ruleset` referred to by this `Release`. The `Ruleset` must exist for the `Release` to be created. */
		rulesetName: Option[String] = None,
	  /** Output only. Time the release was created. */
		createTime: Option[String] = None,
	  /** Output only. Time the release was updated. */
		updateTime: Option[String] = None
	)
	
	case class UpdateReleaseRequest(
	  /** Required. `Release` to update. */
		release: Option[Schema.Release] = None,
	  /** Specifies which fields to update. */
		updateMask: Option[String] = None
	)
	
	case class ListReleasesResponse(
	  /** List of `Release` instances. */
		releases: Option[List[Schema.Release]] = None,
	  /** The pagination token to retrieve the next page of results. If the value is empty, no further results remain. */
		nextPageToken: Option[String] = None
	)
	
	object GetReleaseExecutableResponse {
		enum LanguageEnum extends Enum[LanguageEnum] { case LANGUAGE_UNSPECIFIED, FIREBASE_RULES, EVENT_FLOW_TRIGGERS }
		enum ExecutableVersionEnum extends Enum[ExecutableVersionEnum] { case RELEASE_EXECUTABLE_VERSION_UNSPECIFIED, FIREBASE_RULES_EXECUTABLE_V1, FIREBASE_RULES_EXECUTABLE_V2 }
	}
	case class GetReleaseExecutableResponse(
	  /** Executable view of the `Ruleset` referenced by the `Release`. */
		executable: Option[String] = None,
	  /** `Language` used to generate the executable bytes. */
		language: Option[Schema.GetReleaseExecutableResponse.LanguageEnum] = None,
	  /** `Ruleset` name associated with the `Release` executable. */
		rulesetName: Option[String] = None,
	  /** Timestamp for the most recent `Release.update_time`. */
		updateTime: Option[String] = None,
	  /** The Rules runtime version of the executable. */
		executableVersion: Option[Schema.GetReleaseExecutableResponse.ExecutableVersionEnum] = None,
	  /** Optional, indicates the freshness of the result. The response is guaranteed to be the latest within an interval up to the sync_time (inclusive). */
		syncTime: Option[String] = None
	)
}
