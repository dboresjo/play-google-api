package com.boresjo.play.api.google.toolresults

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ListStepAccessibilityClustersResponse(
	  /** A full resource name of the step. For example, projects/my-project/histories/bh.1234567890abcdef/executions/ 1234567890123456789/steps/bs.1234567890abcdef Always presents. */
		name: Option[String] = None,
	  /** A sequence of accessibility suggestions, grouped into clusters. Within the sequence, clusters that belong to the same SuggestionCategory should be adjacent. Within each category, clusters should be ordered by their SuggestionPriority (ERRORs first). The categories should be ordered by their highest priority cluster. */
		clusters: Option[List[Schema.SuggestionClusterProto]] = None
	)
	
	object SuggestionClusterProto {
		enum CategoryEnum extends Enum[CategoryEnum] { case unknownCategory, contentLabeling, touchTargetSize, lowContrast, implementation }
	}
	case class SuggestionClusterProto(
	  /** Category in which these types of suggestions should appear. Always set. */
		category: Option[Schema.SuggestionClusterProto.CategoryEnum] = None,
	  /** A sequence of suggestions. All of the suggestions within a cluster must have the same SuggestionPriority and belong to the same SuggestionCategory. Suggestions with the same screenshot URL should be adjacent. */
		suggestions: Option[List[Schema.SuggestionProto]] = None
	)
	
	object SuggestionProto {
		enum PriorityEnum extends Enum[PriorityEnum] { case unknownPriority, error, warning, info }
	}
	case class SuggestionProto(
	  /** General title for the suggestion, in the user's language, without markup. Always set. */
		title: Option[String] = None,
	  /** Concise message, in the user's language, representing the suggestion, which may contain markup. Always set. */
		shortMessage: Option[Schema.SafeHtmlProto] = None,
	  /** Message, in the user's language, explaining the suggestion, which may contain markup. Always set. */
		longMessage: Option[Schema.SafeHtmlProto] = None,
	  /** Relative importance of a suggestion. Always set. */
		priority: Option[Schema.SuggestionProto.PriorityEnum] = None,
	  /** Reference to a help center article concerning this type of suggestion. Always set. */
		helpUrl: Option[String] = None,
	  /** Reference to a view element, identified by its resource name, if it has one. */
		resourceName: Option[String] = None,
	  /** A somewhat human readable identifier of the source view, if it does not have a resource_name. This is a path within the accessibility hierarchy, an element with resource name; similar to an XPath. */
		pseudoResourceId: Option[String] = None,
	  /** Region within the screenshot that is relevant to this suggestion. Optional. */
		region: Option[Schema.RegionProto] = None,
	  /** Relative importance of a suggestion as compared with other suggestions that have the same priority and category. This is a meaningless value that can be used to order suggestions that are in the same category and have the same priority. The larger values have higher priority (i.e., are more important). Optional. */
		secondaryPriority: Option[BigDecimal] = None,
	  /** ID of the screen for the suggestion. It is used for getting the corresponding screenshot path. For example, screen_id "1" corresponds to "1.png" file in GCS. Always set. */
		screenId: Option[String] = None
	)
	
	case class SafeHtmlProto(
	  /** IMPORTANT: Never set or read this field, even from tests, it is private. See documentation at the top of .proto file for programming language packages with which to create or read this message. */
		privateDoNotAccessOrElseSafeHtmlWrappedValue: Option[String] = None
	)
	
	case class RegionProto(
	  /** The top of the rectangle, in pixels. Always set. */
		topPx: Option[Int] = None,
	  /** The left side of the rectangle, in pixels. Always set. */
		leftPx: Option[Int] = None,
	  /** The height, in pixels. Always set. */
		heightPx: Option[Int] = None,
	  /** The width, in pixels. Always set. */
		widthPx: Option[Int] = None
	)
	
	object Step {
		enum StateEnum extends Enum[StateEnum] { case unknownState, pending, inProgress, complete }
	}
	case class Step(
	  /** An execution of a test runner. */
		testExecutionStep: Option[Schema.TestExecutionStep] = None,
	  /** An execution of a tool (used for steps we don't explicitly support). */
		toolExecutionStep: Option[Schema.ToolExecutionStep] = None,
	  /** A unique identifier within a Execution for this Step. Returns INVALID_ARGUMENT if this field is set or overwritten by the caller. - In response: always set - In create/update request: never set */
		stepId: Option[String] = None,
	  /** The time when the step was created. - In response: always set - In create/update request: never set */
		creationTime: Option[Schema.Timestamp] = None,
	  /** The time when the step status was set to complete. This value will be set automatically when state transitions to COMPLETE. - In response: set if the execution state is COMPLETE. - In create/update request: never set */
		completionTime: Option[Schema.Timestamp] = None,
	  /** A short human-readable name to display in the UI. Maximum of 100 characters. For example: Clean build A PRECONDITION_FAILED will be returned upon creating a new step if it shares its name and dimension_value with an existing step. If two steps represent a similar action, but have different dimension values, they should share the same name. For instance, if the same set of tests is run on two different platforms, the two steps should have the same name. - In response: always set - In create request: always set - In update request: never set */
		name: Option[String] = None,
	  /** A description of this tool For example: mvn clean package -D skipTests=true - In response: present if set by create/update request - In create/update request: optional */
		description: Option[String] = None,
	  /** The initial state is IN_PROGRESS. The only legal state transitions are &#42; IN_PROGRESS -> COMPLETE A PRECONDITION_FAILED will be returned if an invalid transition is requested. It is valid to create Step with a state set to COMPLETE. The state can only be set to COMPLETE once. A PRECONDITION_FAILED will be returned if the state is set to COMPLETE multiple times. - In response: always set - In create/update request: optional */
		state: Option[Schema.Step.StateEnum] = None,
	  /** Classification of the result, for example into SUCCESS or FAILURE - In response: present if set by create/update request - In create/update request: optional */
		outcome: Option[Schema.Outcome] = None,
	  /** Whether any of the outputs of this step are images whose thumbnails can be fetched with ListThumbnails. - In response: always set - In create/update request: never set */
		hasImages: Option[Boolean] = None,
	  /** Arbitrary user-supplied key/value pairs that are associated with the step. Users are responsible for managing the key namespace such that keys don't accidentally collide. An INVALID_ARGUMENT will be returned if the number of labels exceeds 100 or if the length of any of the keys or values exceeds 100 characters. - In response: always set - In create request: optional - In update request: optional; any new key/value pair will be added to the map, and any new value for an existing key will update that key's value */
		labels: Option[List[Schema.StepLabelsEntry]] = None,
	  /** If the execution containing this step has any dimension_definition set, then this field allows the child to specify the values of the dimensions. The keys must exactly match the dimension_definition of the execution. For example, if the execution has `dimension_definition = ['attempt', 'device']` then a step must define values for those dimensions, eg. `dimension_value = ['attempt': '1', 'device': 'Nexus 6']` If a step does not participate in one dimension of the matrix, the value for that dimension should be empty string. For example, if one of the tests is executed by a runner which does not support retries, the step could have `dimension_value = ['attempt': '', 'device': 'Nexus 6']` If the step does not participate in any dimensions of the matrix, it may leave dimension_value unset. A PRECONDITION_FAILED will be returned if any of the keys do not exist in the dimension_definition of the execution. A PRECONDITION_FAILED will be returned if another step in this execution already has the same name and dimension_value, but differs on other data fields, for example, step field is different. A PRECONDITION_FAILED will be returned if dimension_value is set, and there is a dimension_definition in the execution which is not specified as one of the keys. - In response: present if set by create - In create request: optional - In update request: never set */
		dimensionValue: Option[List[Schema.StepDimensionValueEntry]] = None,
	  /** How long it took for this step to run. If unset, this is set to the difference between creation_time and completion_time when the step is set to the COMPLETE state. In some cases, it is appropriate to set this value separately: For instance, if a step is created, but the operation it represents is queued for a few minutes before it executes, it would be appropriate not to include the time spent queued in its run_duration. PRECONDITION_FAILED will be returned if one attempts to set a run_duration on a step which already has this field set. - In response: present if previously set; always present on COMPLETE step - In create request: optional - In update request: optional */
		runDuration: Option[Schema.Duration] = None,
	  /** How much the device resource is used to perform the test. This is the device usage used for billing purpose, which is different from the run_duration, for example, infrastructure failure won't be charged for device usage. PRECONDITION_FAILED will be returned if one attempts to set a device_usage on a step which already has this field set. - In response: present if previously set. - In create request: optional - In update request: optional */
		deviceUsageDuration: Option[Schema.Duration] = None,
	  /** Details when multiple steps are run with the same configuration as a group. These details can be used identify which group this step is part of. It also identifies the groups 'primary step' which indexes all the group members. - In response: present if previously set. - In create request: optional, set iff this step was performed more than once. - In update request: optional */
		multiStep: Option[Schema.MultiStep] = None
	)
	
	case class TestExecutionStep(
	  /** List of test suite overview contents. This could be parsed from xUnit XML log by server, or uploaded directly by user. This references should only be called when test suites are fully parsed or uploaded. The maximum allowed number of test suite overviews per step is 1000. - In response: always set - In create request: optional - In update request: never (use publishXunitXmlFiles custom method instead) */
		testSuiteOverviews: Option[List[Schema.TestSuiteOverview]] = None,
	  /** Represents the execution of the test runner. The exit code of this tool will be used to determine if the test passed. - In response: always set - In create/update request: optional */
		toolExecution: Option[Schema.ToolExecution] = None,
	  /** Issues observed during the test execution. For example, if the mobile app under test crashed during the test, the error message and the stack trace content can be recorded here to assist debugging. - In response: present if set by create or update - In create/update request: optional */
		testIssues: Option[List[Schema.TestIssue]] = None,
	  /** The timing break down of the test execution. - In response: present if set by create or update - In create/update request: optional */
		testTiming: Option[Schema.TestTiming] = None
	)
	
	case class TestSuiteOverview(
	  /** If this test suite was parsed from XML, this is the URI where the original XML file is stored. Note: Multiple test suites can share the same xml_source Returns INVALID_ARGUMENT if the uri format is not supported. - In create/response: optional - In update request: never */
		xmlSource: Option[Schema.FileReference] = None,
	  /** The name of the test suite. - In create/response: always set - In update request: never */
		name: Option[String] = None,
	  /** Number of test cases, typically set by the service by parsing the xml_source. - In create/response: always set - In update request: never */
		totalCount: Option[Int] = None,
	  /** Number of failed test cases, typically set by the service by parsing the xml_source. May also be set by the user. - In create/response: always set - In update request: never */
		failureCount: Option[Int] = None,
	  /** Number of test cases in error, typically set by the service by parsing the xml_source. - In create/response: always set - In update request: never */
		errorCount: Option[Int] = None,
	  /** Number of test cases not run, typically set by the service by parsing the xml_source. - In create/response: always set - In update request: never */
		skippedCount: Option[Int] = None,
	  /** Number of flaky test cases, set by the service by rolling up flaky test attempts. Present only for rollup test suite overview at environment level. A step cannot have flaky test cases. */
		flakyCount: Option[Int] = None,
	  /** Elapsed time of test suite. */
		elapsedTime: Option[Schema.Duration] = None
	)
	
	case class FileReference(
	  /** The URI of a file stored in Google Cloud Storage. For example: http://storage.googleapis.com/mybucket/path/to/test.xml or in gsutil format: gs://mybucket/path/to/test.xml with version-specific info, gs://mybucket/path/to/test.xml#1360383693690000 An INVALID_ARGUMENT error will be returned if the URI format is not supported. - In response: always set - In create/update request: always set */
		fileUri: Option[String] = None
	)
	
	case class Duration(
	  /** Signed seconds of the span of time. Must be from -315,576,000,000 to +315,576,000,000 inclusive. Note: these bounds are computed from: 60 sec/min &#42; 60 min/hr &#42; 24 hr/day &#42; 365.25 days/year &#42; 10000 years */
		seconds: Option[String] = None,
	  /** Signed fractions of a second at nanosecond resolution of the span of time. Durations less than one second are represented with a 0 `seconds` field and a positive or negative `nanos` field. For durations of one second or more, a non-zero value for the `nanos` field must be of the same sign as the `seconds` field. Must be from -999,999,999 to +999,999,999 inclusive. */
		nanos: Option[Int] = None
	)
	
	case class ToolExecution(
	  /** The full tokenized command line including the program name (equivalent to argv in a C program). - In response: present if set by create request - In create request: optional - In update request: never set */
		commandLineArguments: Option[List[String]] = None,
	  /** References to any plain text logs output the tool execution. This field can be set before the tool has exited in order to be able to have access to a live view of the logs while the tool is running. The maximum allowed number of tool logs per step is 1000. - In response: present if set by create/update request - In create request: optional - In update request: optional, any value provided will be appended to the existing list */
		toolLogs: Option[List[Schema.FileReference]] = None,
	  /** Tool execution exit code. This field will be set once the tool has exited. - In response: present if set by create/update request - In create request: optional - In update request: optional, a FAILED_PRECONDITION error will be returned if an exit_code is already set. */
		exitCode: Option[Schema.ToolExitCode] = None,
	  /** References to opaque files of any format output by the tool execution. The maximum allowed number of tool outputs per step is 1000. - In response: present if set by create/update request - In create request: optional - In update request: optional, any value provided will be appended to the existing list */
		toolOutputs: Option[List[Schema.ToolOutputReference]] = None
	)
	
	case class ToolExitCode(
	  /** Tool execution exit code. A value of 0 means that the execution was successful. - In response: always set - In create/update request: always set */
		number: Option[Int] = None
	)
	
	case class ToolOutputReference(
	  /** A FileReference to an output file. - In response: always set - In create/update request: always set */
		output: Option[Schema.FileReference] = None,
	  /** The creation time of the file. - In response: present if set by create/update request - In create/update request: optional */
		creationTime: Option[Schema.Timestamp] = None,
	  /** The test case to which this output file belongs. - In response: present if set by create/update request - In create/update request: optional */
		testCase: Option[Schema.TestCaseReference] = None
	)
	
	case class Timestamp(
	  /** Represents seconds of UTC time since Unix epoch 1970-01-01T00:00:00Z. Must be from 0001-01-01T00:00:00Z to 9999-12-31T23:59:59Z inclusive. */
		seconds: Option[String] = None,
	  /** Non-negative fractions of a second at nanosecond resolution. Negative second values with fractions must still have non-negative nanos values that count forward in time. Must be from 0 to 999,999,999 inclusive. */
		nanos: Option[Int] = None
	)
	
	case class TestCaseReference(
	  /** The name of the test case. Required. */
		name: Option[String] = None,
	  /** The name of the class. */
		className: Option[String] = None,
	  /** The name of the test suite to which this test case belongs. */
		testSuiteName: Option[String] = None
	)
	
	object TestIssue {
		enum SeverityEnum extends Enum[SeverityEnum] { case unspecifiedSeverity, info, suggestion, warning, severe }
		enum TypeEnum extends Enum[TypeEnum] { case unspecifiedType, fatalException, nativeCrash, anr, unusedRoboDirective, compatibleWithOrchestrator, launcherActivityNotFound, startActivityNotFound, incompleteRoboScriptExecution, completeRoboScriptExecution, failedToInstall, availableDeepLinks, nonSdkApiUsageViolation, nonSdkApiUsageReport, encounteredNonAndroidUiWidgetScreen, encounteredLoginScreen, performedGoogleLogin, iosException, iosCrash, performedMonkeyActions, usedRoboDirective, usedRoboIgnoreDirective, insufficientCoverage, inAppPurchases, crashDialogError, uiElementsTooDeep, blankScreen, overlappingUiElements, unityException, deviceOutOfMemory, logcatCollectionError, detectedAppSplashScreen, assetIssue }
		enum CategoryEnum extends Enum[CategoryEnum] { case unspecifiedCategory, common, robo }
	}
	case class TestIssue(
	  /** A brief human-readable message describing the issue. Required. */
		errorMessage: Option[String] = None,
	  /** Deprecated in favor of stack trace fields inside specific warnings. */
		stackTrace: Option[Schema.StackTrace] = None,
	  /** Warning message with additional details of the issue. Should always be a message from com.google.devtools.toolresults.v1.warnings */
		warning: Option[Schema.Any] = None,
	  /** Severity of issue. Required. */
		severity: Option[Schema.TestIssue.SeverityEnum] = None,
	  /** Type of issue. Required. */
		`type`: Option[Schema.TestIssue.TypeEnum] = None,
	  /** Category of issue. Required. */
		category: Option[Schema.TestIssue.CategoryEnum] = None
	)
	
	case class StackTrace(
	  /** The stack trace message. Required */
		exception: Option[String] = None
	)
	
	case class Any(
	  /** A URL/resource name that uniquely identifies the type of the serialized protocol buffer message. This string must contain at least one "/" character. The last segment of the URL's path must represent the fully qualified name of the type (as in `path/google.protobuf.Duration`). The name should be in a canonical form (e.g., leading "." is not accepted). In practice, teams usually precompile into the binary all types that they expect it to use in the context of Any. However, for URLs which use the scheme `http`, `https`, or no scheme, one can optionally set up a type server that maps type URLs to message definitions as follows: &#42; If no scheme is provided, `https` is assumed. &#42; An HTTP GET on the URL must yield a google.protobuf.Type value in binary format, or produce an error. &#42; Applications are allowed to cache lookup results based on the URL, or have them precompiled into a binary to avoid any lookup. Therefore, binary compatibility needs to be preserved on changes to types. (Use versioned type names to manage breaking changes.) Note: this functionality is not currently available in the official protobuf release, and it is not used for type URLs beginning with type.googleapis.com. Schemes other than `http`, `https` (or the empty scheme) might be used with implementation specific semantics. */
		typeUrl: Option[String] = None,
	  /** Must be a valid serialized protocol buffer of the above specified type. */
		value: Option[String] = None
	)
	
	case class TestTiming(
	  /** How long it took to run the test process. - In response: present if previously set. - In create/update request: optional */
		testProcessDuration: Option[Schema.Duration] = None
	)
	
	case class ToolExecutionStep(
	  /** A Tool execution. - In response: present if set by create/update request - In create/update request: optional */
		toolExecution: Option[Schema.ToolExecution] = None
	)
	
	object Outcome {
		enum SummaryEnum extends Enum[SummaryEnum] { case unset, success, failure, inconclusive, skipped, flaky }
	}
	case class Outcome(
	  /** The simplest way to interpret a result. Required */
		summary: Option[Schema.Outcome.SummaryEnum] = None,
	  /** More information about a SUCCESS outcome. Returns INVALID_ARGUMENT if this field is set but the summary is not SUCCESS. Optional */
		successDetail: Option[Schema.SuccessDetail] = None,
	  /** More information about a FAILURE outcome. Returns INVALID_ARGUMENT if this field is set but the summary is not FAILURE. Optional */
		failureDetail: Option[Schema.FailureDetail] = None,
	  /** More information about an INCONCLUSIVE outcome. Returns INVALID_ARGUMENT if this field is set but the summary is not INCONCLUSIVE. Optional */
		inconclusiveDetail: Option[Schema.InconclusiveDetail] = None,
	  /** More information about a SKIPPED outcome. Returns INVALID_ARGUMENT if this field is set but the summary is not SKIPPED. Optional */
		skippedDetail: Option[Schema.SkippedDetail] = None
	)
	
	case class SuccessDetail(
	  /** If a native process other than the app crashed. */
		otherNativeCrash: Option[Boolean] = None
	)
	
	case class FailureDetail(
	  /** If the failure was severe because the system (app) under test crashed. */
		crashed: Option[Boolean] = None,
	  /** If the test overran some time limit, and that is why it failed. */
		timedOut: Option[Boolean] = None,
	  /** If an app is not installed and thus no test can be run with the app. This might be caused by trying to run a test on an unsupported platform. */
		notInstalled: Option[Boolean] = None,
	  /** If a native process (including any other than the app) crashed. */
		otherNativeCrash: Option[Boolean] = None,
	  /** If the robo was unable to crawl the app; perhaps because the app did not start. */
		unableToCrawl: Option[Boolean] = None,
	  /** If the Roboscript failed to complete successfully, e.g., because a Roboscript action or assertion failed or a Roboscript action could not be matched during the entire crawl. */
		failedRoboscript: Option[Boolean] = None,
	  /** If the device ran out of memory during a test, causing the test to crash. */
		deviceOutOfMemory: Option[Boolean] = None
	)
	
	case class InconclusiveDetail(
	  /** If the test runner could not determine success or failure because the test depends on a component other than the system under test which failed. For example, a mobile test requires provisioning a device where the test executes, and that provisioning can fail. */
		infrastructureFailure: Option[Boolean] = None,
	  /** If the end user aborted the test execution before a pass or fail could be determined. For example, the user pressed ctrl-c which sent a kill signal to the test runner while the test was running. */
		abortedByUser: Option[Boolean] = None,
	  /** If results are being provided to the user in certain cases of infrastructure failures */
		hasErrorLogs: Option[Boolean] = None
	)
	
	case class SkippedDetail(
	  /** If the requested OS version doesn't run on the specific device model. */
		incompatibleDevice: Option[Boolean] = None,
	  /** If the App doesn't support the specific API level. */
		incompatibleAppVersion: Option[Boolean] = None,
	  /** If the App doesn't run on the specific architecture, for example, x86. */
		incompatibleArchitecture: Option[Boolean] = None
	)
	
	case class StepLabelsEntry(
		key: Option[String] = None,
		value: Option[String] = None
	)
	
	case class StepDimensionValueEntry(
		key: Option[String] = None,
		value: Option[String] = None
	)
	
	case class MultiStep(
	  /** Step Id of the primary (original) step, which might be this step. */
		primaryStepId: Option[String] = None,
	  /** Unique int given to each step. Ranges from 0(inclusive) to total number of steps(exclusive). The primary step is 0. */
		multistepNumber: Option[Int] = None,
	  /** Present if it is a primary (original) step. */
		primaryStep: Option[Schema.PrimaryStep] = None
	)
	
	object PrimaryStep {
		enum RollUpEnum extends Enum[RollUpEnum] { case unset, success, failure, inconclusive, skipped, flaky }
	}
	case class PrimaryStep(
	  /** Rollup test status of multiple steps that were run with the same configuration as a group. */
		rollUp: Option[Schema.PrimaryStep.RollUpEnum] = None,
	  /** Step Id and outcome of each individual step. */
		individualOutcome: Option[List[Schema.IndividualOutcome]] = None
	)
	
	object IndividualOutcome {
		enum OutcomeSummaryEnum extends Enum[OutcomeSummaryEnum] { case unset, success, failure, inconclusive, skipped, flaky }
	}
	case class IndividualOutcome(
		stepId: Option[String] = None,
		outcomeSummary: Option[Schema.IndividualOutcome.OutcomeSummaryEnum] = None,
	  /** Unique int given to each step. Ranges from 0(inclusive) to total number of steps(exclusive). The primary step is 0. */
		multistepNumber: Option[Int] = None,
	  /** How long it took for this step to run. */
		runDuration: Option[Schema.Duration] = None
	)
	
	case class ListStepsResponse(
	  /** Steps. */
		steps: Option[List[Schema.Step]] = None,
	  /** A continuation token to resume the query at the next item. If set, indicates that there are more steps to read, by calling list again with this value in the page_token field. */
		nextPageToken: Option[String] = None
	)
	
	case class PublishXunitXmlFilesRequest(
	  /** URI of the Xunit XML files to publish. The maximum size of the file this reference is pointing to is 50MB. Required. */
		xunitXmlFiles: Option[List[Schema.FileReference]] = None
	)
	
	object TestCase {
		enum StatusEnum extends Enum[StatusEnum] { case passed, failed, error, skipped, flaky }
	}
	case class TestCase(
	  /** A unique identifier within a Step for this Test Case. */
		testCaseId: Option[String] = None,
	  /** The elapsed run time of the test case. Required. */
		elapsedTime: Option[Schema.Duration] = None,
	  /** The start time of the test case. */
		startTime: Option[Schema.Timestamp] = None,
	  /** The end time of the test case. */
		endTime: Option[Schema.Timestamp] = None,
	  /** The stack trace details if the test case failed or encountered an error. The maximum size of the stack traces is 100KiB, beyond which the stack track will be truncated. Zero if the test case passed. */
		stackTraces: Option[List[Schema.StackTrace]] = None,
	  /** The status of the test case. Required. */
		status: Option[Schema.TestCase.StatusEnum] = None,
	  /** Why the test case was skipped. Present only for skipped test case */
		skippedMessage: Option[String] = None,
	  /** Test case reference, e.g. name, class name and test suite name. Required. */
		testCaseReference: Option[Schema.TestCaseReference] = None,
	  /** References to opaque files of any format output by the tool execution. @OutputOnly */
		toolOutputs: Option[List[Schema.ToolOutputReference]] = None
	)
	
	case class ListTestCasesResponse(
	  /** List of test cases. */
		testCases: Option[List[Schema.TestCase]] = None,
		nextPageToken: Option[String] = None
	)
	
	case class ListStepThumbnailsResponse(
	  /** A list of image data. Images are returned in a deterministic order; they are ordered by these factors, in order of importance: &#42; First, by their associated test case. Images without a test case are considered greater than images with one. &#42; Second, by their creation time. Images without a creation time are greater than images with one. &#42; Third, by the order in which they were added to the step (by calls to CreateStep or UpdateStep). */
		thumbnails: Option[List[Schema.Image]] = None,
	  /** A continuation token to resume the query at the next item. If set, indicates that there are more thumbnails to read, by calling list again with this value in the page_token field. */
		nextPageToken: Option[String] = None
	)
	
	case class Image(
	  /** The step to which the image is attached. Always set. */
		stepId: Option[String] = None,
	  /** A reference to the full-size, original image. This is the same as the tool_outputs entry for the image under its Step. Always set. */
		sourceImage: Option[Schema.ToolOutputReference] = None,
	  /** The thumbnail. */
		thumbnail: Option[Schema.Thumbnail] = None,
	  /** An error explaining why the thumbnail could not be rendered. */
		error: Option[Schema.Status] = None
	)
	
	case class Thumbnail(
	  /** The thumbnail's content type, i.e. "image/png". Always set. */
		contentType: Option[String] = None,
	  /** The height of the thumbnail, in pixels. Always set. */
		heightPx: Option[Int] = None,
	  /** The width of the thumbnail, in pixels. Always set. */
		widthPx: Option[Int] = None,
	  /** The thumbnail file itself. That is, the bytes here are precisely the bytes that make up the thumbnail file; they can be served as an image as-is (with the appropriate content type.) Always set. */
		data: Option[String] = None
	)
	
	case class Status(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	object Execution {
		enum StateEnum extends Enum[StateEnum] { case unknownState, pending, inProgress, complete }
	}
	case class Execution(
	  /** A unique identifier within a History for this Execution. Returns INVALID_ARGUMENT if this field is set or overwritten by the caller. - In response always set - In create/update request: never set */
		executionId: Option[String] = None,
	  /** The initial state is IN_PROGRESS. The only legal state transitions is from IN_PROGRESS to COMPLETE. A PRECONDITION_FAILED will be returned if an invalid transition is requested. The state can only be set to COMPLETE once. A FAILED_PRECONDITION will be returned if the state is set to COMPLETE multiple times. If the state is set to COMPLETE, all the in-progress steps within the execution will be set as COMPLETE. If the outcome of the step is not set, the outcome will be set to INCONCLUSIVE. - In response always set - In create/update request: optional */
		state: Option[Schema.Execution.StateEnum] = None,
	  /** The time when the Execution was created. This value will be set automatically when CreateExecution is called. - In response: always set - In create/update request: never set */
		creationTime: Option[Schema.Timestamp] = None,
	  /** The time when the Execution status transitioned to COMPLETE. This value will be set automatically when state transitions to COMPLETE. - In response: set if the execution state is COMPLETE. - In create/update request: never set */
		completionTime: Option[Schema.Timestamp] = None,
	  /** Classify the result, for example into SUCCESS or FAILURE - In response: present if set by create/update request - In create/update request: optional */
		outcome: Option[Schema.Outcome] = None,
	  /** The dimensions along which different steps in this execution may vary. This must remain fixed over the life of the execution. Returns INVALID_ARGUMENT if this field is set in an update request. Returns INVALID_ARGUMENT if the same name occurs in more than one dimension_definition. Returns INVALID_ARGUMENT if the size of the list is over 100. - In response: present if set by create - In create request: optional - In update request: never set */
		dimensionDefinitions: Option[List[Schema.MatrixDimensionDefinition]] = None,
	  /** Lightweight information about execution request. - In response: present if set by create - In create: optional - In update: optional */
		specification: Option[Schema.Specification] = None,
	  /** TestExecution Matrix ID that the TestExecutionService uses. - In response: present if set by create - In create: optional - In update: never set */
		testExecutionMatrixId: Option[String] = None
	)
	
	case class MatrixDimensionDefinition(
	
	)
	
	case class Specification(
	  /** An Android mobile test execution specification. */
		androidTest: Option[Schema.AndroidTest] = None,
	  /** An iOS mobile test execution specification. */
		iosTest: Option[Schema.IosTest] = None
	)
	
	case class AndroidTest(
	  /** Information about the application under test. */
		androidAppInfo: Option[Schema.AndroidAppInfo] = None,
	  /** Max time a test is allowed to run before it is automatically cancelled. */
		testTimeout: Option[Schema.Duration] = None,
	  /** An Android instrumentation test. */
		androidInstrumentationTest: Option[Schema.AndroidInstrumentationTest] = None,
	  /** An Android robo test. */
		androidRoboTest: Option[Schema.AndroidRoboTest] = None,
	  /** An Android test loop. */
		androidTestLoop: Option[Schema.AndroidTestLoop] = None
	)
	
	case class AndroidAppInfo(
	  /** The name of the app. Optional */
		name: Option[String] = None,
	  /** The package name of the app. Required. */
		packageName: Option[String] = None,
	  /** The version name of the app. Optional. */
		versionName: Option[String] = None,
	  /** The internal version code of the app. Optional. */
		versionCode: Option[String] = None
	)
	
	case class AndroidInstrumentationTest(
	  /** The java package for the test to be executed. Required */
		testPackageId: Option[String] = None,
	  /** The InstrumentationTestRunner class. Required */
		testRunnerClass: Option[String] = None,
	  /** Each target must be fully qualified with the package name or class name, in one of these formats: - "package package_name" - "class package_name.class_name" - "class package_name.class_name#method_name" If empty, all targets in the module will be run. */
		testTargets: Option[List[String]] = None,
	  /** The flag indicates whether Android Test Orchestrator will be used to run test or not. */
		useOrchestrator: Option[Boolean] = None
	)
	
	case class AndroidRoboTest(
	  /** The initial activity that should be used to start the app. Optional */
		appInitialActivity: Option[String] = None,
	  /** The java package for the bootstrap. Optional */
		bootstrapPackageId: Option[String] = None,
	  /** The runner class for the bootstrap. Optional */
		bootstrapRunnerClass: Option[String] = None,
	  /** The max depth of the traversal stack Robo can explore. Optional */
		maxDepth: Option[Int] = None,
	  /** The max number of steps/actions Robo can execute. Default is no limit (0). Optional */
		maxSteps: Option[Int] = None
	)
	
	case class AndroidTestLoop(
	
	)
	
	case class IosTest(
	  /** Information about the application under test. */
		iosAppInfo: Option[Schema.IosAppInfo] = None,
	  /** Max time a test is allowed to run before it is automatically cancelled. */
		testTimeout: Option[Schema.Duration] = None,
	  /** An iOS XCTest. */
		iosXcTest: Option[Schema.IosXcTest] = None,
	  /** An iOS test loop. */
		iosTestLoop: Option[Schema.IosTestLoop] = None,
	  /** An iOS Robo test. */
		iosRoboTest: Option[Schema.IosRoboTest] = None
	)
	
	case class IosAppInfo(
	  /** The name of the app. Required */
		name: Option[String] = None
	)
	
	case class IosXcTest(
	  /** Bundle ID of the app. */
		bundleId: Option[String] = None,
	  /** Xcode version that the test was run with. */
		xcodeVersion: Option[String] = None
	)
	
	case class IosTestLoop(
	  /** Bundle ID of the app. */
		bundleId: Option[String] = None
	)
	
	case class IosRoboTest(
	
	)
	
	case class ListExecutionsResponse(
	  /** Executions. Always set. */
		executions: Option[List[Schema.Execution]] = None,
	  /** A continuation token to resume the query at the next item. Will only be set if there are more Executions to fetch. */
		nextPageToken: Option[String] = None
	)
	
	case class ScreenshotCluster(
	  /** A unique identifier for the cluster. @OutputOnly */
		clusterId: Option[String] = None,
	  /** A singular screen that represents the cluster as a whole. This screen will act as the "cover" of the entire cluster. When users look at the clusters, only the key screen from each cluster will be shown. Which screen is the key screen is determined by the ClusteringAlgorithm */
		keyScreen: Option[Schema.Screen] = None,
	  /** A string that describes the activity of every screen in the cluster. */
		activity: Option[String] = None,
	  /** Full list of screens. */
		screens: Option[List[Schema.Screen]] = None
	)
	
	case class Screen(
	  /** File reference of the png file. Required. */
		fileReference: Option[String] = None,
	  /** Model of the device that the screenshot was taken on. Required. */
		model: Option[String] = None,
	  /** OS version of the device that the screenshot was taken on. Required. */
		version: Option[String] = None,
	  /** Locale of the device that the screenshot was taken on. Required. */
		locale: Option[String] = None
	)
	
	case class ListScreenshotClustersResponse(
	  /** The set of clusters associated with an execution Always set */
		clusters: Option[List[Schema.ScreenshotCluster]] = None
	)
	
	case class Environment(
	  /** Output only. A Project id. */
		projectId: Option[String] = None,
	  /** Output only. A History id. */
		historyId: Option[String] = None,
	  /** Output only. An Execution id. */
		executionId: Option[String] = None,
	  /** Output only. An Environment id. */
		environmentId: Option[String] = None,
	  /** Dimension values describing the environment. Dimension values always consist of "Model", "Version", "Locale", and "Orientation". - In response: always set - In create request: always set - In update request: never set */
		dimensionValue: Option[List[Schema.EnvironmentDimensionValueEntry]] = None,
	  /** A short human-readable name to display in the UI. Maximum of 100 characters. For example: Nexus 5, API 27. */
		displayName: Option[String] = None,
	  /** Output only. The time when the Environment was created. */
		creationTime: Option[Schema.Timestamp] = None,
	  /** Output only. The time when the Environment status was set to complete. This value will be set automatically when state transitions to COMPLETE. */
		completionTime: Option[Schema.Timestamp] = None,
	  /** Output only. Summaries of shards. Only one shard will present unless sharding feature is enabled in TestExecutionService. */
		shardSummaries: Option[List[Schema.ShardSummary]] = None,
	  /** Merged result of the environment. */
		environmentResult: Option[Schema.MergedResult] = None,
	  /** The location where output files are stored in the user bucket. */
		resultsStorage: Option[Schema.ResultsStorage] = None
	)
	
	case class EnvironmentDimensionValueEntry(
		key: Option[String] = None,
		value: Option[String] = None
	)
	
	case class ShardSummary(
	  /** Summaries of the steps belonging to the shard. With flaky_test_attempts enabled from TestExecutionService, more than one run (Step) can present. And the runs will be sorted by multistep_number. */
		runs: Option[List[Schema.StepSummary]] = None,
	  /** Merged result of the shard. */
		shardResult: Option[Schema.MergedResult] = None
	)
	
	case class StepSummary(
	
	)
	
	object MergedResult {
		enum StateEnum extends Enum[StateEnum] { case unknownState, pending, inProgress, complete }
	}
	case class MergedResult(
	  /** State of the resource */
		state: Option[Schema.MergedResult.StateEnum] = None,
	  /** Outcome of the resource */
		outcome: Option[Schema.Outcome] = None,
	  /** The combined and rolled-up result of each test suite that was run as part of this environment. Combining: When the test cases from a suite are run in different steps (sharding), the results are added back together in one overview. (e.g., if shard1 has 2 failures and shard2 has 1 failure than the overview failure_count = 3). Rollup: When test cases from the same suite are run multiple times (flaky), the results are combined (e.g., if testcase1.run1 fails, testcase1.run2 passes, and both testcase2.run1 and testcase2.run2 fail then the overview flaky_count = 1 and failure_count = 1). */
		testSuiteOverviews: Option[List[Schema.TestSuiteOverview]] = None
	)
	
	case class ResultsStorage(
	  /** The root directory for test results. */
		resultsStoragePath: Option[Schema.FileReference] = None,
	  /** The path to the Xunit XML file. */
		xunitXmlFile: Option[Schema.FileReference] = None
	)
	
	case class ListEnvironmentsResponse(
	  /** A Project id. Always set. */
		projectId: Option[String] = None,
	  /** A History id. Always set. */
		historyId: Option[String] = None,
	  /** A Execution id Always set. */
		executionId: Option[String] = None,
	  /** Environments. Always set. */
		environments: Option[List[Schema.Environment]] = None,
	  /** A continuation token to resume the query at the next item. Will only be set if there are more Environments to fetch. */
		nextPageToken: Option[String] = None
	)
	
	object History {
		enum TestPlatformEnum extends Enum[TestPlatformEnum] { case unknownPlatform, android, ios }
	}
	case class History(
	  /** A unique identifier within a project for this History. Returns INVALID_ARGUMENT if this field is set or overwritten by the caller. - In response always set - In create request: never set */
		historyId: Option[String] = None,
	  /** A name to uniquely identify a history within a project. Maximum of 200 characters. - In response always set - In create request: always set */
		name: Option[String] = None,
	  /** A short human-readable (plain text) name to display in the UI. Maximum of 100 characters. - In response: present if set during create. - In create request: optional */
		displayName: Option[String] = None,
	  /** The platform of the test history. - In response: always set. Returns the platform of the last execution if unknown. */
		testPlatform: Option[Schema.History.TestPlatformEnum] = None
	)
	
	case class ListHistoriesResponse(
	  /** Histories. */
		histories: Option[List[Schema.History]] = None,
	  /** A continuation token to resume the query at the next item. Will only be set if there are more histories to fetch. Tokens are valid for up to one hour from the time of the first list request. For instance, if you make a list request at 1PM and use the token from this first request 10 minutes later, the token from this second response will only be valid for 50 minutes. */
		nextPageToken: Option[String] = None
	)
	
	object PerfMetricsSummary {
		enum PerfMetricsEnum extends Enum[PerfMetricsEnum] { case perfMetricTypeUnspecified, memory, cpu, network, graphics }
	}
	case class PerfMetricsSummary(
	  /** The cloud project @OutputOnly */
		projectId: Option[String] = None,
	  /** A tool results history ID. @OutputOnly */
		historyId: Option[String] = None,
	  /** A tool results execution ID. @OutputOnly */
		executionId: Option[String] = None,
	  /** A tool results step ID. @OutputOnly */
		stepId: Option[String] = None,
	  /** Set of resource collected */
		perfMetrics: Option[List[Schema.PerfMetricsSummary.PerfMetricsEnum]] = None,
	  /** Describes the environment in which the performance metrics were collected */
		perfEnvironment: Option[Schema.PerfEnvironment] = None,
		appStartTime: Option[Schema.AppStartTime] = None,
	  /** Graphics statistics for the entire run. Statistics are reset at the beginning of the run and collected at the end of the run. */
		graphicsStats: Option[Schema.GraphicsStats] = None
	)
	
	case class PerfEnvironment(
	  /** CPU related environment info */
		cpuInfo: Option[Schema.CPUInfo] = None,
	  /** Memory related environment info */
		memoryInfo: Option[Schema.MemoryInfo] = None
	)
	
	case class CPUInfo(
	  /** description of the device processor ie '1.8 GHz hexa core 64-bit ARMv8-A' */
		cpuProcessor: Option[String] = None,
	  /** the CPU clock speed in GHz */
		cpuSpeedInGhz: Option[BigDecimal] = None,
	  /** the number of CPU cores */
		numberOfCores: Option[Int] = None
	)
	
	case class MemoryInfo(
	  /** Total memory available on the device in KiB */
		memoryTotalInKibibyte: Option[String] = None,
	  /** Maximum memory that can be allocated to the process in KiB */
		memoryCapInKibibyte: Option[String] = None
	)
	
	case class AppStartTime(
	  /** The time from app start to the first displayed activity being drawn, as reported in Logcat. See https://developer.android.com/topic/performance/launch-time.html#time-initial */
		initialDisplayTime: Option[Schema.Duration] = None,
	  /** Optional. The time from app start to reaching the developer-reported "fully drawn" time. This is only stored if the app includes a call to Activity.reportFullyDrawn(). See https://developer.android.com/topic/performance/launch-time.html#time-full */
		fullyDrawnTime: Option[Schema.Duration] = None
	)
	
	case class GraphicsStats(
	  /** Total frames rendered by package. */
		totalFrames: Option[String] = None,
	  /** Total frames with slow render time. Should be <= total_frames. */
		jankyFrames: Option[String] = None,
	  /** 50th percentile frame render time in milliseconds. */
		p50Millis: Option[String] = None,
	  /** 90th percentile frame render time in milliseconds. */
		p90Millis: Option[String] = None,
	  /** 95th percentile frame render time in milliseconds. */
		p95Millis: Option[String] = None,
	  /** 99th percentile frame render time in milliseconds. */
		p99Millis: Option[String] = None,
	  /** Total "missed vsync" events. */
		missedVsyncCount: Option[String] = None,
	  /** Total "high input latency" events. */
		highInputLatencyCount: Option[String] = None,
	  /** Total "slow UI thread" events. */
		slowUiThreadCount: Option[String] = None,
	  /** Total "slow bitmap upload" events. */
		slowBitmapUploadCount: Option[String] = None,
	  /** Total "slow draw" events. */
		slowDrawCount: Option[String] = None,
	  /** Histogram of frame render times. There should be 154 buckets ranging from [5ms, 6ms) to [4950ms, infinity) */
		buckets: Option[List[Schema.GraphicsStatsBucket]] = None
	)
	
	case class GraphicsStatsBucket(
	  /** Lower bound of render time in milliseconds. */
		renderMillis: Option[String] = None,
	  /** Number of frames in the bucket. */
		frameCount: Option[String] = None
	)
	
	case class PerfSampleSeries(
	  /** The cloud project @OutputOnly */
		projectId: Option[String] = None,
	  /** A tool results history ID. @OutputOnly */
		historyId: Option[String] = None,
	  /** A tool results execution ID. @OutputOnly */
		executionId: Option[String] = None,
	  /** A tool results step ID. @OutputOnly */
		stepId: Option[String] = None,
	  /** A sample series id @OutputOnly */
		sampleSeriesId: Option[String] = None,
	  /** Basic series represented by a line chart */
		basicPerfSampleSeries: Option[Schema.BasicPerfSampleSeries] = None
	)
	
	object BasicPerfSampleSeries {
		enum PerfMetricTypeEnum extends Enum[PerfMetricTypeEnum] { case perfMetricTypeUnspecified, memory, cpu, network, graphics }
		enum PerfUnitEnum extends Enum[PerfUnitEnum] { case perfUnitUnspecified, kibibyte, percent, bytesPerSecond, framesPerSecond, `byte` }
		enum SampleSeriesLabelEnum extends Enum[SampleSeriesLabelEnum] { case sampleSeriesTypeUnspecified, memoryRssPrivate, memoryRssShared, memoryRssTotal, memoryTotal, cpuUser, cpuKernel, cpuTotal, ntBytesTransferred, ntBytesReceived, networkSent, networkReceived, graphicsFrameRate }
	}
	case class BasicPerfSampleSeries(
		perfMetricType: Option[Schema.BasicPerfSampleSeries.PerfMetricTypeEnum] = None,
		perfUnit: Option[Schema.BasicPerfSampleSeries.PerfUnitEnum] = None,
		sampleSeriesLabel: Option[Schema.BasicPerfSampleSeries.SampleSeriesLabelEnum] = None
	)
	
	case class ListPerfSampleSeriesResponse(
	  /** The resulting PerfSampleSeries sorted by id */
		perfSampleSeries: Option[List[Schema.PerfSampleSeries]] = None
	)
	
	case class BatchCreatePerfSamplesRequest(
	  /** The set of PerfSamples to create should not include existing timestamps */
		perfSamples: Option[List[Schema.PerfSample]] = None
	)
	
	case class PerfSample(
	  /** Timestamp of collection. */
		sampleTime: Option[Schema.Timestamp] = None,
	  /** Value observed */
		value: Option[BigDecimal] = None
	)
	
	case class BatchCreatePerfSamplesResponse(
		perfSamples: Option[List[Schema.PerfSample]] = None
	)
	
	case class ListPerfSamplesResponse(
		perfSamples: Option[List[Schema.PerfSample]] = None,
	  /** Optional, returned if result size exceeds the page size specified in the request (or the default page size, 500, if unspecified). It indicates the last sample timestamp to be used as page_token in subsequent request */
		nextPageToken: Option[String] = None
	)
	
	case class ProjectSettings(
	  /** The name of the project's settings. Always of the form: projects/{project-id}/settings In update request: never set In response: always set */
		name: Option[String] = None,
	  /** The name of the Google Cloud Storage bucket to which results are written. By default, this is unset. In update request: optional In response: optional */
		defaultBucket: Option[String] = None
	)
	
	case class ANR(
	  /** The stack trace of the ANR crash. Optional. */
		stackTrace: Option[Schema.StackTrace] = None
	)
	
	case class AvailableDeepLinks(
	
	)
	
	case class BlankScreen(
	  /** The screen id of the element */
		screenId: Option[String] = None
	)
	
	case class CrashDialogError(
	  /** The name of the package that caused the dialog. */
		crashPackage: Option[String] = None
	)
	
	case class DetectedAppSplashScreen(
	
	)
	
	case class DeviceOutOfMemory(
	
	)
	
	case class EncounteredLoginScreen(
	  /** Number of encountered distinct login screens. */
		distinctScreens: Option[Int] = None,
	  /** Subset of login screens. */
		screenIds: Option[List[String]] = None
	)
	
	case class EncounteredNonAndroidUiWidgetScreen(
	  /** Number of encountered distinct screens with non Android UI widgets. */
		distinctScreens: Option[Int] = None,
	  /** Subset of screens which contain non Android UI widgets. */
		screenIds: Option[List[String]] = None
	)
	
	case class FailedToInstall(
	
	)
	
	case class AssetIssue(
	
	)
	
	case class FatalException(
	  /** The stack trace of the fatal exception. Optional. */
		stackTrace: Option[Schema.StackTrace] = None
	)
	
	case class InAppPurchasesFound(
	  /** The total number of in-app purchases flows started. */
		inAppPurchasesFlowsStarted: Option[Int] = None,
	  /** The total number of in-app purchases flows explored: how many times the robo tries to buy a SKU. */
		inAppPurchasesFlowsExplored: Option[Int] = None
	)
	
	case class InsufficientCoverage(
	
	)
	
	case class IosAppCrashed(
	  /** The stack trace, if one is available. Optional. */
		stackTrace: Option[Schema.StackTrace] = None
	)
	
	case class LauncherActivityNotFound(
	
	)
	
	case class LogcatCollectionError(
	
	)
	
	case class NativeCrash(
	  /** The stack trace of the native crash. Optional. */
		stackTrace: Option[Schema.StackTrace] = None
	)
	
	case class NonSdkApiUsageViolation(
	  /** Total number of unique hidden API's accessed. */
		uniqueApis: Option[Int] = None,
	  /** Signatures of a subset of those hidden API's. */
		apiSignatures: Option[List[String]] = None
	)
	
	case class NonSdkApiUsageViolationReport(
	  /** Total number of unique Non-SDK API's accessed. */
		uniqueApis: Option[Int] = None,
	  /** Minimum API level required for the application to run. */
		minSdkVersion: Option[Int] = None,
	  /** Specifies the API Level on which the application is designed to run. */
		targetSdkVersion: Option[Int] = None,
	  /** Examples of the detected API usages. */
		exampleApis: Option[List[Schema.NonSdkApi]] = None
	)
	
	object NonSdkApi {
		enum ListEnum extends Enum[ListEnum] { case NONE, WHITE, BLACK, GREY, GREY_MAX_O, GREY_MAX_P, GREY_MAX_Q, GREY_MAX_R, GREY_MAX_S }
	}
	case class NonSdkApi(
	  /** The signature of the Non-SDK API */
		apiSignature: Option[String] = None,
	  /** The total number of times this API was observed to have been called. */
		invocationCount: Option[Int] = None,
	  /** Which list this API appears on */
		list: Option[Schema.NonSdkApi.ListEnum] = None,
	  /** Example stack traces of this API being called. */
		exampleStackTraces: Option[List[String]] = None,
	  /** Optional debugging insights for non-SDK API violations. */
		insights: Option[List[Schema.NonSdkApiInsight]] = None
	)
	
	case class NonSdkApiInsight(
	  /** An insight indicating that the hidden API usage originates from the use of a library that needs to be upgraded. */
		upgradeInsight: Option[Schema.UpgradeInsight] = None,
	  /** An insight indicating that the hidden API usage originates from a Google-provided library. */
		pendingGoogleUpdateInsight: Option[Schema.PendingGoogleUpdateInsight] = None,
	  /** A unique ID, to be used for determining the effectiveness of this particular insight in the context of a matcher. (required) */
		matcherId: Option[String] = None,
	  /** Optional sample stack traces, for which this insight applies (there should be at least one). */
		exampleTraceMessages: Option[List[String]] = None
	)
	
	case class UpgradeInsight(
	  /** The name of the package to be upgraded. */
		packageName: Option[String] = None,
	  /** The suggested version to upgrade to. Optional: In case we are not sure which version solves this problem */
		upgradeToVersion: Option[String] = None
	)
	
	case class PendingGoogleUpdateInsight(
	  /** The name of the Google-provided library with the non-SDK API dependency. */
		nameOfGoogleLibrary: Option[String] = None
	)
	
	case class OverlappingUIElements(
	  /** The screen id of the elements */
		screenId: Option[String] = None,
	  /** Resource names of the overlapping screen elements */
		resourceName: Option[List[String]] = None
	)
	
	case class PerformedGoogleLogin(
	
	)
	
	case class PerformedMonkeyActions(
	  /** The total number of monkey actions performed during the crawl. */
		totalActions: Option[Int] = None
	)
	
	case class RoboScriptExecution(
	  /** The total number of actions in the Robo script. */
		totalActions: Option[Int] = None,
	  /** The number of Robo script actions executed successfully. */
		successfulActions: Option[Int] = None
	)
	
	case class StartActivityNotFound(
		action: Option[String] = None,
		uri: Option[String] = None
	)
	
	case class UIElementTooDeep(
	  /** The screen id of the element */
		screenId: Option[String] = None,
	  /** The screen state id of the element */
		screenStateId: Option[String] = None,
	  /** The depth of the screen element */
		depth: Option[Int] = None
	)
	
	case class UnspecifiedWarning(
	
	)
	
	case class UnusedRoboDirective(
	  /** The name of the resource that was unused. */
		resourceName: Option[String] = None
	)
	
	case class UsedRoboDirective(
	  /** The name of the resource that was used. */
		resourceName: Option[String] = None
	)
	
	case class UsedRoboIgnoreDirective(
	  /** The name of the resource that was ignored. */
		resourceName: Option[String] = None
	)
}
