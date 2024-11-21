package com.boresjo.play.api.google.testing

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object TestMatrix {
		enum StateEnum extends Enum[StateEnum] { case TEST_STATE_UNSPECIFIED, VALIDATING, PENDING, RUNNING, FINISHED, ERROR, UNSUPPORTED_ENVIRONMENT, INCOMPATIBLE_ENVIRONMENT, INCOMPATIBLE_ARCHITECTURE, CANCELLED, INVALID }
		enum InvalidMatrixDetailsEnum extends Enum[InvalidMatrixDetailsEnum] { case INVALID_MATRIX_DETAILS_UNSPECIFIED, DETAILS_UNAVAILABLE, MALFORMED_APK, MALFORMED_TEST_APK, NO_MANIFEST, NO_PACKAGE_NAME, INVALID_PACKAGE_NAME, TEST_SAME_AS_APP, NO_INSTRUMENTATION, NO_SIGNATURE, INSTRUMENTATION_ORCHESTRATOR_INCOMPATIBLE, NO_TEST_RUNNER_CLASS, NO_LAUNCHER_ACTIVITY, FORBIDDEN_PERMISSIONS, INVALID_ROBO_DIRECTIVES, INVALID_RESOURCE_NAME, INVALID_DIRECTIVE_ACTION, TEST_LOOP_INTENT_FILTER_NOT_FOUND, SCENARIO_LABEL_NOT_DECLARED, SCENARIO_LABEL_MALFORMED, SCENARIO_NOT_DECLARED, DEVICE_ADMIN_RECEIVER, MALFORMED_XC_TEST_ZIP, BUILT_FOR_IOS_SIMULATOR, NO_TESTS_IN_XC_TEST_ZIP, USE_DESTINATION_ARTIFACTS, TEST_NOT_APP_HOSTED, PLIST_CANNOT_BE_PARSED, TEST_ONLY_APK, MALFORMED_IPA, MISSING_URL_SCHEME, MALFORMED_APP_BUNDLE, NO_CODE_APK, INVALID_INPUT_APK, INVALID_APK_PREVIEW_SDK, MATRIX_TOO_LARGE, TEST_QUOTA_EXCEEDED, SERVICE_NOT_ACTIVATED, UNKNOWN_PERMISSION_ERROR }
		enum OutcomeSummaryEnum extends Enum[OutcomeSummaryEnum] { case OUTCOME_SUMMARY_UNSPECIFIED, SUCCESS, FAILURE, INCONCLUSIVE, SKIPPED }
	}
	case class TestMatrix(
	  /** Output only. Unique id set by the service. */
		testMatrixId: Option[String] = None,
	  /** The cloud project that owns the test matrix. */
		projectId: Option[String] = None,
	  /** Information about the client which invoked the test. */
		clientInfo: Option[Schema.ClientInfo] = None,
	  /** Required. How to run the test. */
		testSpecification: Option[Schema.TestSpecification] = None,
	  /** Required. The devices the tests are being executed on. */
		environmentMatrix: Option[Schema.EnvironmentMatrix] = None,
	  /** Output only. The list of test executions that the service creates for this matrix. */
		testExecutions: Option[List[Schema.TestExecution]] = None,
	  /** Required. Where the results for the matrix are written. */
		resultStorage: Option[Schema.ResultStorage] = None,
	  /** Output only. Indicates the current progress of the test matrix. */
		state: Option[Schema.TestMatrix.StateEnum] = None,
	  /** Output only. The time this test matrix was initially created. */
		timestamp: Option[String] = None,
	  /** Output only. Describes why the matrix is considered invalid. Only useful for matrices in the INVALID state. */
		invalidMatrixDetails: Option[Schema.TestMatrix.InvalidMatrixDetailsEnum] = None,
	  /** Output only. Details about why a matrix was deemed invalid. If multiple checks can be safely performed, they will be reported but no assumptions should be made about the length of this list. */
		extendedInvalidMatrixDetails: Option[List[Schema.MatrixErrorDetail]] = None,
	  /** The number of times a TestExecution should be re-attempted if one or more of its test cases fail for any reason. The maximum number of reruns allowed is 10. Default is 0, which implies no reruns. */
		flakyTestAttempts: Option[Int] = None,
	  /** Output Only. The overall outcome of the test. Only set when the test matrix state is FINISHED. */
		outcomeSummary: Option[Schema.TestMatrix.OutcomeSummaryEnum] = None,
	  /** If true, only a single attempt at most will be made to run each execution/shard in the matrix. Flaky test attempts are not affected. Normally, 2 or more attempts are made if a potential infrastructure issue is detected. This feature is for latency sensitive workloads. The incidence of execution failures may be significantly greater for fail-fast matrices and support is more limited because of that expectation. */
		failFast: Option[Boolean] = None
	)
	
	case class ClientInfo(
	  /** Required. Client name, such as gcloud. */
		name: Option[String] = None,
	  /** The list of detailed information about client. */
		clientInfoDetails: Option[List[Schema.ClientInfoDetail]] = None
	)
	
	case class ClientInfoDetail(
	  /** Required. The key of detailed client information. */
		key: Option[String] = None,
	  /** Required. The value of detailed client information. */
		value: Option[String] = None
	)
	
	case class TestSpecification(
	  /** Max time a test execution is allowed to run before it is automatically cancelled. The default value is 5 min. */
		testTimeout: Option[String] = None,
	  /** Test setup requirements for Android e.g. files to install, bootstrap scripts. */
		testSetup: Option[Schema.TestSetup] = None,
	  /** Test setup requirements for iOS. */
		iosTestSetup: Option[Schema.IosTestSetup] = None,
	  /** An Android instrumentation test. */
		androidInstrumentationTest: Option[Schema.AndroidInstrumentationTest] = None,
	  /** An Android robo test. */
		androidRoboTest: Option[Schema.AndroidRoboTest] = None,
	  /** An Android Application with a Test Loop. */
		androidTestLoop: Option[Schema.AndroidTestLoop] = None,
	  /** An iOS XCTest, via an .xctestrun file. */
		iosXcTest: Option[Schema.IosXcTest] = None,
	  /** An iOS application with a test loop. */
		iosTestLoop: Option[Schema.IosTestLoop] = None,
	  /** An iOS Robo test. */
		iosRoboTest: Option[Schema.IosRoboTest] = None,
	  /** Disables video recording. May reduce test latency. */
		disableVideoRecording: Option[Boolean] = None,
	  /** Disables performance metrics recording. May reduce test latency. */
		disablePerformanceMetrics: Option[Boolean] = None
	)
	
	case class TestSetup(
	  /** List of files to push to the device before starting the test. */
		filesToPush: Option[List[Schema.DeviceFile]] = None,
	  /** List of directories on the device to upload to GCS at the end of the test; they must be absolute paths under /sdcard, /storage or /data/local/tmp. Path names are restricted to characters a-z A-Z 0-9 _ - . + and / Note: The paths /sdcard and /data will be made available and treated as implicit path substitutions. E.g. if /sdcard on a particular device does not map to external storage, the system will replace it with the external storage path prefix for that device. */
		directoriesToPull: Option[List[String]] = None,
	  /** Optional. Initial setup APKs to install before the app under test is installed. Currently capped at 100. */
		initialSetupApks: Option[List[Schema.Apk]] = None,
	  /** APKs to install in addition to those being directly tested. These will be installed after the app under test. Currently capped at 100. */
		additionalApks: Option[List[Schema.Apk]] = None,
	  /** The device will be logged in on this account for the duration of the test. */
		account: Option[Schema.Account] = None,
	  /** The network traffic profile used for running the test. Available network profiles can be queried by using the NETWORK_CONFIGURATION environment type when calling TestEnvironmentDiscoveryService.GetTestEnvironmentCatalog. */
		networkProfile: Option[String] = None,
	  /** Environment variables to set for the test (only applicable for instrumentation tests). */
		environmentVariables: Option[List[Schema.EnvironmentVariable]] = None,
	  /** Systrace configuration for the run. Deprecated: Systrace used Python 2 which was sunsetted on 2020-01-01. Systrace is no longer supported in the Cloud Testing API, and no Systrace file will be provided in the results. */
		systrace: Option[Schema.SystraceSetup] = None,
	  /** Whether to prevent all runtime permissions to be granted at app install */
		dontAutograntPermissions: Option[Boolean] = None
	)
	
	case class DeviceFile(
	  /** A reference to an opaque binary blob file. */
		obbFile: Option[Schema.ObbFile] = None,
	  /** A reference to a regular file. */
		regularFile: Option[Schema.RegularFile] = None
	)
	
	case class ObbFile(
	  /** Required. OBB file name which must conform to the format as specified by Android e.g. [main|patch].0300110.com.example.android.obb which will be installed into \/Android/obb/\/ on the device. */
		obbFileName: Option[String] = None,
	  /** Required. Opaque Binary Blob (OBB) file(s) to install on the device. */
		obb: Option[Schema.FileReference] = None
	)
	
	case class FileReference(
	  /** A path to a file in Google Cloud Storage. Example: gs://build-app-1414623860166/app%40debug-unaligned.apk These paths are expected to be url encoded (percent encoding) */
		gcsPath: Option[String] = None
	)
	
	case class RegularFile(
	  /** Required. The source file. */
		content: Option[Schema.FileReference] = None,
	  /** Required. Where to put the content on the device. Must be an absolute, allowlisted path. If the file exists, it will be replaced. The following device-side directories and any of their subdirectories are allowlisted: ${EXTERNAL_STORAGE}, /sdcard, or /storage ${ANDROID_DATA}/local/tmp, or /data/local/tmp Specifying a path outside of these directory trees is invalid. The paths /sdcard and /data will be made available and treated as implicit path substitutions. E.g. if /sdcard on a particular device does not map to external storage, the system will replace it with the external storage path prefix for that device and copy the file there. It is strongly advised to use the Environment API in app and test code to access files on the device in a portable way. */
		devicePath: Option[String] = None
	)
	
	case class Apk(
	  /** The path to an APK to be installed on the device before the test begins. */
		location: Option[Schema.FileReference] = None,
	  /** The java package for the APK to be installed. Value is determined by examining the application's manifest. */
		packageName: Option[String] = None
	)
	
	case class Account(
	  /** An automatic google login account. */
		googleAuto: Option[Schema.GoogleAuto] = None
	)
	
	case class GoogleAuto(
	
	)
	
	case class EnvironmentVariable(
	  /** Key for the environment variable. */
		key: Option[String] = None,
	  /** Value for the environment variable. */
		value: Option[String] = None
	)
	
	case class SystraceSetup(
	  /** Systrace duration in seconds. Should be between 1 and 30 seconds. 0 disables systrace. */
		durationSeconds: Option[Int] = None
	)
	
	case class IosTestSetup(
	  /** The network traffic profile used for running the test. Available network profiles can be queried by using the NETWORK_CONFIGURATION environment type when calling TestEnvironmentDiscoveryService.GetTestEnvironmentCatalog. */
		networkProfile: Option[String] = None,
	  /** iOS apps to install in addition to those being directly tested. */
		additionalIpas: Option[List[Schema.FileReference]] = None,
	  /** List of files to push to the device before starting the test. */
		pushFiles: Option[List[Schema.IosDeviceFile]] = None,
	  /** List of directories on the device to upload to Cloud Storage at the end of the test. Directories should either be in a shared directory (such as /private/var/mobile/Media) or within an accessible directory inside the app's filesystem (such as /Documents) by specifying the bundle ID. */
		pullDirectories: Option[List[Schema.IosDeviceFile]] = None
	)
	
	case class IosDeviceFile(
	  /** The source file */
		content: Option[Schema.FileReference] = None,
	  /** The bundle id of the app where this file lives. iOS apps sandbox their own filesystem, so app files must specify which app installed on the device. */
		bundleId: Option[String] = None,
	  /** Location of the file on the device, inside the app's sandboxed filesystem */
		devicePath: Option[String] = None
	)
	
	object AndroidInstrumentationTest {
		enum OrchestratorOptionEnum extends Enum[OrchestratorOptionEnum] { case ORCHESTRATOR_OPTION_UNSPECIFIED, USE_ORCHESTRATOR, DO_NOT_USE_ORCHESTRATOR }
	}
	case class AndroidInstrumentationTest(
	  /** The APK for the application under test. */
		appApk: Option[Schema.FileReference] = None,
	  /** A multi-apk app bundle for the application under test. */
		appBundle: Option[Schema.AppBundle] = None,
	  /** Required. The APK containing the test code to be executed. */
		testApk: Option[Schema.FileReference] = None,
	  /** The java package for the application under test. The default value is determined by examining the application's manifest. */
		appPackageId: Option[String] = None,
	  /** The java package for the test to be executed. The default value is determined by examining the application's manifest. */
		testPackageId: Option[String] = None,
	  /** The InstrumentationTestRunner class. The default value is determined by examining the application's manifest. */
		testRunnerClass: Option[String] = None,
	  /** Each target must be fully qualified with the package name or class name, in one of these formats: - "package package_name" - "class package_name.class_name" - "class package_name.class_name#method_name" If empty, all targets in the module will be run. */
		testTargets: Option[List[String]] = None,
	  /** The option of whether running each test within its own invocation of instrumentation with Android Test Orchestrator or not. &#42;&#42; Orchestrator is only compatible with AndroidJUnitRunner version 1.1 or higher! &#42;&#42; Orchestrator offers the following benefits: - No shared state - Crashes are isolated - Logs are scoped per test See for more information about Android Test Orchestrator. If not set, the test will be run without the orchestrator. */
		orchestratorOption: Option[Schema.AndroidInstrumentationTest.OrchestratorOptionEnum] = None,
	  /** The option to run tests in multiple shards in parallel. */
		shardingOption: Option[Schema.ShardingOption] = None
	)
	
	case class AppBundle(
	  /** .aab file representing the app bundle under test. */
		bundleLocation: Option[Schema.FileReference] = None
	)
	
	case class ShardingOption(
	  /** Uniformly shards test cases given a total number of shards. */
		uniformSharding: Option[Schema.UniformSharding] = None,
	  /** Shards test cases into the specified groups of packages, classes, and/or methods. */
		manualSharding: Option[Schema.ManualSharding] = None,
	  /** Shards test based on previous test case timing records. */
		smartSharding: Option[Schema.SmartSharding] = None
	)
	
	case class UniformSharding(
	  /** Required. The total number of shards to create. This must always be a positive number that is no greater than the total number of test cases. When you select one or more physical devices, the number of shards must be <= 50. When you select one or more ARM virtual devices, it must be <= 200. When you select only x86 virtual devices, it must be <= 500. */
		numShards: Option[Int] = None
	)
	
	case class ManualSharding(
	  /** Required. Group of packages, classes, and/or test methods to be run for each manually-created shard. You must specify at least one shard if this field is present. When you select one or more physical devices, the number of repeated test_targets_for_shard must be <= 50. When you select one or more ARM virtual devices, it must be <= 200. When you select only x86 virtual devices, it must be <= 500. */
		testTargetsForShard: Option[List[Schema.TestTargetsForShard]] = None
	)
	
	case class TestTargetsForShard(
	  /** Group of packages, classes, and/or test methods to be run for each shard. The targets need to be specified in AndroidJUnitRunner argument format. For example, "package com.my.packages" "class com.my.package.MyClass". The number of test_targets must be greater than 0. */
		testTargets: Option[List[String]] = None
	)
	
	case class SmartSharding(
	  /** The amount of time tests within a shard should take. Default: 300 seconds (5 minutes). The minimum allowed: 120 seconds (2 minutes). The shard count is dynamically set based on time, up to the maximum shard limit (described below). To guarantee at least one test case for each shard, the number of shards will not exceed the number of test cases. Shard duration will be exceeded if: - The maximum shard limit is reached and there is more calculated test time remaining to allocate into shards. - Any individual test is estimated to be longer than the targeted shard duration. Shard duration is not guaranteed because smart sharding uses test case history and default durations which may not be accurate. The rules for finding the test case timing records are: - If the service has processed a test case in the last 30 days, the record of the latest successful test case will be used. - For new test cases, the average duration of other known test cases will be used. - If there are no previous test case timing records available, the default test case duration is 15 seconds. Because the actual shard duration can exceed the targeted shard duration, we recommend that you set the targeted value at least 5 minutes less than the maximum allowed test timeout (45 minutes for physical devices and 60 minutes for virtual), or that you use the custom test timeout value that you set. This approach avoids cancelling the shard before all tests can finish. Note that there is a limit for maximum number of shards. When you select one or more physical devices, the number of shards must be <= 50. When you select one or more ARM virtual devices, it must be <= 200. When you select only x86 virtual devices, it must be <= 500. To guarantee at least one test case for per shard, the number of shards will not exceed the number of test cases. Each shard created counts toward daily test quota. */
		targetedShardDuration: Option[String] = None
	)
	
	object AndroidRoboTest {
		enum RoboModeEnum extends Enum[RoboModeEnum] { case ROBO_MODE_UNSPECIFIED, ROBO_VERSION_1, ROBO_VERSION_2 }
	}
	case class AndroidRoboTest(
	  /** The APK for the application under test. */
		appApk: Option[Schema.FileReference] = None,
	  /** A multi-apk app bundle for the application under test. */
		appBundle: Option[Schema.AppBundle] = None,
	  /** The java package for the application under test. The default value is determined by examining the application's manifest. */
		appPackageId: Option[String] = None,
	  /** The initial activity that should be used to start the app. */
		appInitialActivity: Option[String] = None,
	  /** The max depth of the traversal stack Robo can explore. Needs to be at least 2 to make Robo explore the app beyond the first activity. Default is 50. */
		maxDepth: Option[Int] = None,
	  /** The max number of steps Robo can execute. Default is no limit. */
		maxSteps: Option[Int] = None,
	  /** A set of directives Robo should apply during the crawl. This allows users to customize the crawl. For example, the username and password for a test account can be provided. */
		roboDirectives: Option[List[Schema.RoboDirective]] = None,
	  /** The mode in which Robo should run. Most clients should allow the server to populate this field automatically. */
		roboMode: Option[Schema.AndroidRoboTest.RoboModeEnum] = None,
	  /** A JSON file with a sequence of actions Robo should perform as a prologue for the crawl. */
		roboScript: Option[Schema.FileReference] = None,
	  /** The intents used to launch the app for the crawl. If none are provided, then the main launcher activity is launched. If some are provided, then only those provided are launched (the main launcher activity must be provided explicitly). */
		startingIntents: Option[List[Schema.RoboStartingIntent]] = None
	)
	
	object RoboDirective {
		enum ActionTypeEnum extends Enum[ActionTypeEnum] { case ACTION_TYPE_UNSPECIFIED, SINGLE_CLICK, ENTER_TEXT, IGNORE }
	}
	case class RoboDirective(
	  /** Required. The android resource name of the target UI element. For example, in Java: R.string.foo in xml: @string/foo Only the "foo" part is needed. Reference doc: https://developer.android.com/guide/topics/resources/accessing-resources.html */
		resourceName: Option[String] = None,
	  /** The text that Robo is directed to set. If left empty, the directive will be treated as a CLICK on the element matching the resource_name. */
		inputText: Option[String] = None,
	  /** Required. The type of action that Robo should perform on the specified element. */
		actionType: Option[Schema.RoboDirective.ActionTypeEnum] = None
	)
	
	case class RoboStartingIntent(
	  /** An intent that starts the main launcher activity. */
		launcherActivity: Option[Schema.LauncherActivityIntent] = None,
	  /** An intent that starts an activity with specific details. */
		startActivity: Option[Schema.StartActivityIntent] = None,
	  /** Skips the starting activity */
		noActivity: Option[Schema.NoActivityIntent] = None,
	  /** Timeout in seconds for each intent. */
		timeout: Option[String] = None
	)
	
	case class LauncherActivityIntent(
	
	)
	
	case class StartActivityIntent(
	  /** Action name. Required for START_ACTIVITY. */
		action: Option[String] = None,
	  /** URI for the action. */
		uri: Option[String] = None,
	  /** Intent categories to set on the intent. */
		categories: Option[List[String]] = None
	)
	
	case class NoActivityIntent(
	
	)
	
	case class AndroidTestLoop(
	  /** The APK for the application under test. */
		appApk: Option[Schema.FileReference] = None,
	  /** A multi-apk app bundle for the application under test. */
		appBundle: Option[Schema.AppBundle] = None,
	  /** The java package for the application under test. The default is determined by examining the application's manifest. */
		appPackageId: Option[String] = None,
	  /** The list of scenarios that should be run during the test. The default is all test loops, derived from the application's manifest. */
		scenarios: Option[List[Int]] = None,
	  /** The list of scenario labels that should be run during the test. The scenario labels should map to labels defined in the application's manifest. For example, player_experience and com.google.test.loops.player_experience add all of the loops labeled in the manifest with the com.google.test.loops.player_experience name to the execution. Scenarios can also be specified in the scenarios field. */
		scenarioLabels: Option[List[String]] = None
	)
	
	case class IosXcTest(
	  /** Required. The .zip containing the .xctestrun file and the contents of the DerivedData/Build/Products directory. The .xctestrun file in this zip is ignored if the xctestrun field is specified. */
		testsZip: Option[Schema.FileReference] = None,
	  /** An .xctestrun file that will override the .xctestrun file in the tests zip. Because the .xctestrun file contains environment variables along with test methods to run and/or ignore, this can be useful for sharding tests. Default is taken from the tests zip. */
		xctestrun: Option[Schema.FileReference] = None,
	  /** The Xcode version that should be used for the test. Use the TestEnvironmentDiscoveryService to get supported options. Defaults to the latest Xcode version Firebase Test Lab supports. */
		xcodeVersion: Option[String] = None,
	  /** Output only. The bundle id for the application under test. */
		appBundleId: Option[String] = None,
	  /** The option to test special app entitlements. Setting this would re-sign the app having special entitlements with an explicit application-identifier. Currently supports testing aps-environment entitlement. */
		testSpecialEntitlements: Option[Boolean] = None
	)
	
	case class IosTestLoop(
	  /** Required. The .ipa of the application to test. */
		appIpa: Option[Schema.FileReference] = None,
	  /** The list of scenarios that should be run during the test. Defaults to the single scenario 0 if unspecified. */
		scenarios: Option[List[Int]] = None,
	  /** Output only. The bundle id for the application under test. */
		appBundleId: Option[String] = None
	)
	
	case class IosRoboTest(
	  /** Required. The ipa stored at this file should be used to run the test. */
		appIpa: Option[Schema.FileReference] = None,
	  /** The bundle ID for the app-under-test. This is determined by examining the application's "Info.plist" file. */
		appBundleId: Option[String] = None,
	  /** An optional Roboscript to customize the crawl. See https://firebase.google.com/docs/test-lab/android/robo-scripts-reference for more information about Roboscripts. */
		roboScript: Option[Schema.FileReference] = None
	)
	
	case class EnvironmentMatrix(
	  /** A matrix of Android devices. */
		androidMatrix: Option[Schema.AndroidMatrix] = None,
	  /** A list of Android devices; the test will be run only on the specified devices. */
		androidDeviceList: Option[Schema.AndroidDeviceList] = None,
	  /** A list of iOS devices. */
		iosDeviceList: Option[Schema.IosDeviceList] = None
	)
	
	case class AndroidMatrix(
	  /** Required. The ids of the set of Android device to be used. Use the TestEnvironmentDiscoveryService to get supported options. */
		androidModelIds: Option[List[String]] = None,
	  /** Required. The ids of the set of Android OS version to be used. Use the TestEnvironmentDiscoveryService to get supported options. */
		androidVersionIds: Option[List[String]] = None,
	  /** Required. The set of locales the test device will enable for testing. Use the TestEnvironmentDiscoveryService to get supported options. */
		locales: Option[List[String]] = None,
	  /** Required. The set of orientations to test with. Use the TestEnvironmentDiscoveryService to get supported options. */
		orientations: Option[List[String]] = None
	)
	
	case class AndroidDeviceList(
	  /** Required. A list of Android devices. */
		androidDevices: Option[List[Schema.AndroidDevice]] = None
	)
	
	case class AndroidDevice(
	  /** Required. The id of the Android device to be used. Use the TestEnvironmentDiscoveryService to get supported options. */
		androidModelId: Option[String] = None,
	  /** Required. The id of the Android OS version to be used. Use the TestEnvironmentDiscoveryService to get supported options. */
		androidVersionId: Option[String] = None,
	  /** Required. The locale the test device used for testing. Use the TestEnvironmentDiscoveryService to get supported options. */
		locale: Option[String] = None,
	  /** Required. How the device is oriented during the test. Use the TestEnvironmentDiscoveryService to get supported options. */
		orientation: Option[String] = None
	)
	
	case class IosDeviceList(
	  /** Required. A list of iOS devices. */
		iosDevices: Option[List[Schema.IosDevice]] = None
	)
	
	case class IosDevice(
	  /** Required. The id of the iOS device to be used. Use the TestEnvironmentDiscoveryService to get supported options. */
		iosModelId: Option[String] = None,
	  /** Required. The id of the iOS major software version to be used. Use the TestEnvironmentDiscoveryService to get supported options. */
		iosVersionId: Option[String] = None,
	  /** Required. The locale the test device used for testing. Use the TestEnvironmentDiscoveryService to get supported options. */
		locale: Option[String] = None,
	  /** Required. How the device is oriented during the test. Use the TestEnvironmentDiscoveryService to get supported options. */
		orientation: Option[String] = None
	)
	
	object TestExecution {
		enum StateEnum extends Enum[StateEnum] { case TEST_STATE_UNSPECIFIED, VALIDATING, PENDING, RUNNING, FINISHED, ERROR, UNSUPPORTED_ENVIRONMENT, INCOMPATIBLE_ENVIRONMENT, INCOMPATIBLE_ARCHITECTURE, CANCELLED, INVALID }
	}
	case class TestExecution(
	  /** Output only. Unique id set by the service. */
		id: Option[String] = None,
	  /** Output only. Id of the containing TestMatrix. */
		matrixId: Option[String] = None,
	  /** Output only. The cloud project that owns the test execution. */
		projectId: Option[String] = None,
	  /** Output only. How to run the test. */
		testSpecification: Option[Schema.TestSpecification] = None,
	  /** Output only. Details about the shard. */
		shard: Option[Schema.Shard] = None,
	  /** Output only. How the host machine(s) are configured. */
		environment: Option[Schema.Environment] = None,
	  /** Output only. Indicates the current progress of the test execution (e.g., FINISHED). */
		state: Option[Schema.TestExecution.StateEnum] = None,
	  /** Output only. Where the results for this execution are written. */
		toolResultsStep: Option[Schema.ToolResultsStep] = None,
	  /** Output only. The time this test execution was initially created. */
		timestamp: Option[String] = None,
	  /** Output only. Additional details about the running test. */
		testDetails: Option[Schema.TestDetails] = None
	)
	
	case class Shard(
	  /** Output only. The index of the shard among all the shards. */
		shardIndex: Option[Int] = None,
	  /** Output only. The total number of shards. */
		numShards: Option[Int] = None,
	  /** Output only. Test targets for each shard. Only set for manual sharding. */
		testTargetsForShard: Option[Schema.TestTargetsForShard] = None,
	  /** Output only. The estimated shard duration based on previous test case timing records, if available. */
		estimatedShardDuration: Option[String] = None
	)
	
	case class Environment(
	  /** An Android device which must be used with an Android test. */
		androidDevice: Option[Schema.AndroidDevice] = None,
	  /** An iOS device which must be used with an iOS test. */
		iosDevice: Option[Schema.IosDevice] = None
	)
	
	case class ToolResultsStep(
	  /** Output only. The cloud project that owns the tool results step. */
		projectId: Option[String] = None,
	  /** Output only. A tool results history ID. */
		historyId: Option[String] = None,
	  /** Output only. A tool results execution ID. */
		executionId: Option[String] = None,
	  /** Output only. A tool results step ID. */
		stepId: Option[String] = None
	)
	
	case class TestDetails(
	  /** Output only. Human-readable, detailed descriptions of the test's progress. For example: "Provisioning a device", "Starting Test". During the course of execution new data may be appended to the end of progress_messages. */
		progressMessages: Option[List[String]] = None,
	  /** Output only. If the TestState is ERROR, then this string will contain human-readable details about the error. */
		errorMessage: Option[String] = None
	)
	
	case class ResultStorage(
	  /** Required. */
		googleCloudStorage: Option[Schema.GoogleCloudStorage] = None,
	  /** The tool results history that contains the tool results execution that results are written to. If not provided, the service will choose an appropriate value. */
		toolResultsHistory: Option[Schema.ToolResultsHistory] = None,
	  /** Output only. The tool results execution that results are written to. */
		toolResultsExecution: Option[Schema.ToolResultsExecution] = None,
	  /** Output only. URL to the results in the Firebase Web Console. */
		resultsUrl: Option[String] = None
	)
	
	case class GoogleCloudStorage(
	  /** Required. The path to a directory in GCS that will eventually contain the results for this test. The requesting user must have write access on the bucket in the supplied path. */
		gcsPath: Option[String] = None
	)
	
	case class ToolResultsHistory(
	  /** Required. The cloud project that owns the tool results history. */
		projectId: Option[String] = None,
	  /** Required. A tool results history ID. */
		historyId: Option[String] = None
	)
	
	case class ToolResultsExecution(
	  /** Output only. The cloud project that owns the tool results execution. */
		projectId: Option[String] = None,
	  /** Output only. A tool results history ID. */
		historyId: Option[String] = None,
	  /** Output only. A tool results execution ID. */
		executionId: Option[String] = None
	)
	
	case class MatrixErrorDetail(
	  /** Output only. The reason for the error. This is a constant value in UPPER_SNAKE_CASE that identifies the cause of the error. */
		reason: Option[String] = None,
	  /** Output only. A human-readable message about how the error in the TestMatrix. Expands on the `reason` field with additional details and possible options to fix the issue. */
		message: Option[String] = None
	)
	
	object CancelTestMatrixResponse {
		enum TestStateEnum extends Enum[TestStateEnum] { case TEST_STATE_UNSPECIFIED, VALIDATING, PENDING, RUNNING, FINISHED, ERROR, UNSUPPORTED_ENVIRONMENT, INCOMPATIBLE_ENVIRONMENT, INCOMPATIBLE_ARCHITECTURE, CANCELLED, INVALID }
	}
	case class CancelTestMatrixResponse(
	  /** The current rolled-up state of the test matrix. If this state is already final, then the cancelation request will have no effect. */
		testState: Option[Schema.CancelTestMatrixResponse.TestStateEnum] = None
	)
	
	case class GetApkDetailsResponse(
	  /** Details of the Android App. */
		apkDetail: Option[Schema.ApkDetail] = None
	)
	
	case class ApkDetail(
		apkManifest: Option[Schema.ApkManifest] = None
	)
	
	case class ApkManifest(
	  /** Full Java-style package name for this application, e.g. "com.example.foo". */
		packageName: Option[String] = None,
	  /** Minimum API level required for the application to run. */
		minSdkVersion: Option[Int] = None,
	  /** Maximum API level on which the application is designed to run. */
		maxSdkVersion: Option[Int] = None,
	  /** Specifies the API Level on which the application is designed to run. */
		targetSdkVersion: Option[Int] = None,
	  /** User-readable name for the application. */
		applicationLabel: Option[String] = None,
		intentFilters: Option[List[Schema.IntentFilter]] = None,
	  /** Permissions declared to be used by the application */
		usesPermissionTags: Option[List[Schema.UsesPermissionTag]] = None,
		usesPermission: Option[List[String]] = None,
	  /** Version number used internally by the app. */
		versionCode: Option[String] = None,
	  /** Version number shown to users. */
		versionName: Option[String] = None,
	  /** Meta-data tags defined in the manifest. */
		metadata: Option[List[Schema.Metadata]] = None,
	  /** Feature usage tags defined in the manifest. */
		usesFeature: Option[List[Schema.UsesFeature]] = None,
	  /** Services contained in the tag. */
		services: Option[List[Schema.Service]] = None
	)
	
	case class IntentFilter(
	  /** The android:name value of the tag. */
		actionNames: Option[List[String]] = None,
	  /** The android:name value of the tag. */
		categoryNames: Option[List[String]] = None,
	  /** The android:mimeType value of the tag. */
		mimeType: Option[String] = None
	)
	
	case class UsesPermissionTag(
	  /** The android:name value */
		name: Option[String] = None,
	  /** The android:name value */
		maxSdkVersion: Option[Int] = None
	)
	
	case class Metadata(
	  /** The android:name value */
		name: Option[String] = None,
	  /** The android:value value */
		value: Option[String] = None
	)
	
	case class UsesFeature(
	  /** The android:name value */
		name: Option[String] = None,
	  /** The android:required value */
		isRequired: Option[Boolean] = None
	)
	
	case class Service(
	  /** The android:name value */
		name: Option[String] = None,
	  /** Intent filters in the service */
		intentFilter: Option[List[Schema.IntentFilter]] = None
	)
	
	object DeviceSession {
		enum StateEnum extends Enum[StateEnum] { case SESSION_STATE_UNSPECIFIED, REQUESTED, PENDING, ACTIVE, EXPIRED, FINISHED, UNAVAILABLE, ERROR }
	}
	case class DeviceSession(
	  /** Optional. Name of the DeviceSession, e.g. "projects/{project_id}/deviceSessions/{session_id}" */
		name: Option[String] = None,
	  /** Output only. The title of the DeviceSession to be presented in the UI. */
		displayName: Option[String] = None,
	  /** Output only. Current state of the DeviceSession. */
		state: Option[Schema.DeviceSession.StateEnum] = None,
	  /** Output only. The historical state transitions of the session_state message including the current session state. */
		stateHistories: Option[List[Schema.SessionStateEvent]] = None,
	  /** Optional. The amount of time that a device will be initially allocated for. This can eventually be extended with the UpdateDeviceSession RPC. Default: 15 minutes. */
		ttl: Option[String] = None,
	  /** Optional. If the device is still in use at this time, any connections will be ended and the SessionState will transition from ACTIVE to FINISHED. */
		expireTime: Option[String] = None,
	  /** Output only. The interval of time that this device must be interacted with before it transitions from ACTIVE to TIMEOUT_INACTIVITY. */
		inactivityTimeout: Option[String] = None,
	  /** Output only. The time that the Session was created. */
		createTime: Option[String] = None,
	  /** Output only. The timestamp that the session first became ACTIVE. */
		activeStartTime: Option[String] = None,
	  /** Required. The requested device */
		androidDevice: Option[Schema.AndroidDevice] = None
	)
	
	object SessionStateEvent {
		enum SessionStateEnum extends Enum[SessionStateEnum] { case SESSION_STATE_UNSPECIFIED, REQUESTED, PENDING, ACTIVE, EXPIRED, FINISHED, UNAVAILABLE, ERROR }
	}
	case class SessionStateEvent(
	  /** Output only. The session_state tracked by this event */
		sessionState: Option[Schema.SessionStateEvent.SessionStateEnum] = None,
	  /** Output only. The time that the session_state first encountered that state. */
		eventTime: Option[String] = None,
	  /** Output only. A human-readable message to explain the state. */
		stateMessage: Option[String] = None
	)
	
	case class ListDeviceSessionsResponse(
	  /** The sessions matching the specified filter in the given cloud project. */
		deviceSessions: Option[List[Schema.DeviceSession]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class CancelDeviceSessionRequest(
	
	)
	
	case class Empty(
	
	)
	
	case class TestEnvironmentCatalog(
	  /** Supported Android devices. */
		androidDeviceCatalog: Option[Schema.AndroidDeviceCatalog] = None,
	  /** Supported iOS devices. */
		iosDeviceCatalog: Option[Schema.IosDeviceCatalog] = None,
	  /** Supported network configurations. */
		networkConfigurationCatalog: Option[Schema.NetworkConfigurationCatalog] = None,
	  /** The software test environment provided by TestExecutionService. */
		softwareCatalog: Option[Schema.ProvidedSoftwareCatalog] = None,
	  /** The IP blocks used by devices in the test environment. */
		deviceIpBlockCatalog: Option[Schema.DeviceIpBlockCatalog] = None
	)
	
	case class AndroidDeviceCatalog(
	  /** The set of supported Android device models. */
		models: Option[List[Schema.AndroidModel]] = None,
	  /** The set of supported Android OS versions. */
		versions: Option[List[Schema.AndroidVersion]] = None,
	  /** The set of supported runtime configurations. */
		runtimeConfiguration: Option[Schema.AndroidRuntimeConfiguration] = None
	)
	
	object AndroidModel {
		enum FormEnum extends Enum[FormEnum] { case DEVICE_FORM_UNSPECIFIED, VIRTUAL, PHYSICAL, EMULATOR }
		enum FormFactorEnum extends Enum[FormFactorEnum] { case DEVICE_FORM_FACTOR_UNSPECIFIED, PHONE, TABLET, WEARABLE }
	}
	case class AndroidModel(
	  /** The unique opaque id for this model. Use this for invoking the TestExecutionService. */
		id: Option[String] = None,
	  /** The human-readable marketing name for this device model. Examples: "Nexus 5", "Galaxy S5". */
		name: Option[String] = None,
	  /** The manufacturer of this device. */
		manufacturer: Option[String] = None,
	  /** The company that this device is branded with. Example: "Google", "Samsung". */
		brand: Option[String] = None,
	  /** The name of the industrial design. This corresponds to android.os.Build.DEVICE. */
		codename: Option[String] = None,
	  /** Whether this device is virtual or physical. */
		form: Option[Schema.AndroidModel.FormEnum] = None,
	  /** Whether this device is a phone, tablet, wearable, etc. */
		formFactor: Option[Schema.AndroidModel.FormFactorEnum] = None,
	  /** Version-specific information of an Android model. */
		perVersionInfo: Option[List[Schema.PerAndroidVersionInfo]] = None,
	  /** Screen size in the horizontal (X) dimension measured in pixels. */
		screenX: Option[Int] = None,
	  /** Screen size in the vertical (Y) dimension measured in pixels. */
		screenY: Option[Int] = None,
	  /** Screen density in DPI. This corresponds to ro.sf.lcd_density */
		screenDensity: Option[Int] = None,
	  /** True if and only if tests with this model are recorded by stitching together screenshots. See use_low_spec_video_recording in device config. */
		lowFpsVideoRecording: Option[Boolean] = None,
	  /** The set of Android versions this device supports. */
		supportedVersionIds: Option[List[String]] = None,
	  /** The list of supported ABIs for this device. This corresponds to either android.os.Build.SUPPORTED_ABIS (for API level 21 and above) or android.os.Build.CPU_ABI/CPU_ABI2. The most preferred ABI is the first element in the list. Elements are optionally prefixed by "version_id:" (where version_id is the id of an AndroidVersion), denoting an ABI that is supported only on a particular version. */
		supportedAbis: Option[List[String]] = None,
	  /** Tags for this dimension. Examples: "default", "preview", "deprecated". */
		tags: Option[List[String]] = None,
	  /** URL of a thumbnail image (photo) of the device. */
		thumbnailUrl: Option[String] = None,
	  /** Output only. Lab info of this device. */
		labInfo: Option[Schema.LabInfo] = None
	)
	
	object PerAndroidVersionInfo {
		enum DeviceCapacityEnum extends Enum[DeviceCapacityEnum] { case DEVICE_CAPACITY_UNSPECIFIED, DEVICE_CAPACITY_HIGH, DEVICE_CAPACITY_MEDIUM, DEVICE_CAPACITY_LOW, DEVICE_CAPACITY_NONE }
	}
	case class PerAndroidVersionInfo(
	  /** An Android version. */
		versionId: Option[String] = None,
	  /** The number of online devices for an Android version. */
		deviceCapacity: Option[Schema.PerAndroidVersionInfo.DeviceCapacityEnum] = None,
	  /** Output only. The estimated wait time for a single interactive device session using Direct Access. */
		interactiveDeviceAvailabilityEstimate: Option[String] = None,
	  /** Output only. Identifies supported clients for DirectAccess for this Android version. */
		directAccessVersionInfo: Option[Schema.DirectAccessVersionInfo] = None
	)
	
	case class DirectAccessVersionInfo(
	  /** Whether direct access is supported at all. Clients are expected to filter down the device list to only android models and versions which support Direct Access when that is the user intent. */
		directAccessSupported: Option[Boolean] = None,
	  /** Output only. Indicates client-device compatibility, where a device is known to work only with certain workarounds implemented in the Android Studio client. Expected format "major.minor.micro.patch", e.g. "5921.22.2211.8881706". */
		minimumAndroidStudioVersion: Option[String] = None
	)
	
	case class LabInfo(
	  /** Lab name where the device is hosted. If empty, the device is hosted in a Google owned lab. */
		name: Option[String] = None
	)
	
	case class AndroidVersion(
	  /** An opaque id for this Android version. Use this id to invoke the TestExecutionService. */
		id: Option[String] = None,
	  /** A string representing this version of the Android OS. Examples: "4.3", "4.4". */
		versionString: Option[String] = None,
	  /** The API level for this Android version. Examples: 18, 19. */
		apiLevel: Option[Int] = None,
	  /** The code name for this Android version. Examples: "JellyBean", "KitKat". */
		codeName: Option[String] = None,
	  /** The date this Android version became available in the market. */
		releaseDate: Option[Schema.Date] = None,
	  /** Market share for this version. */
		distribution: Option[Schema.Distribution] = None,
	  /** Tags for this dimension. Examples: "default", "preview", "deprecated". */
		tags: Option[List[String]] = None
	)
	
	case class Date(
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None,
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None,
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None
	)
	
	case class Distribution(
	  /** Output only. The time this distribution was measured. */
		measurementTime: Option[String] = None,
	  /** Output only. The estimated fraction (0-1) of the total market with this configuration. */
		marketShare: Option[BigDecimal] = None
	)
	
	case class AndroidRuntimeConfiguration(
	  /** The set of available locales. */
		locales: Option[List[Schema.Locale]] = None,
	  /** The set of available orientations. */
		orientations: Option[List[Schema.Orientation]] = None
	)
	
	case class Locale(
	  /** The id for this locale. Example: "en_US". */
		id: Option[String] = None,
	  /** A human-friendly name for this language/locale. Example: "English". */
		name: Option[String] = None,
	  /** A human-friendly string representing the region for this locale. Example: "United States". Not present for every locale. */
		region: Option[String] = None,
	  /** Tags for this dimension. Example: "default". */
		tags: Option[List[String]] = None
	)
	
	case class Orientation(
	  /** The id for this orientation. Example: "portrait". */
		id: Option[String] = None,
	  /** A human-friendly name for this orientation. Example: "portrait". */
		name: Option[String] = None,
	  /** Tags for this dimension. Example: "default". */
		tags: Option[List[String]] = None
	)
	
	case class IosDeviceCatalog(
	  /** The set of supported iOS device models. */
		models: Option[List[Schema.IosModel]] = None,
	  /** The set of supported iOS software versions. */
		versions: Option[List[Schema.IosVersion]] = None,
	  /** The set of supported Xcode versions. */
		xcodeVersions: Option[List[Schema.XcodeVersion]] = None,
	  /** The set of supported runtime configurations. */
		runtimeConfiguration: Option[Schema.IosRuntimeConfiguration] = None
	)
	
	object IosModel {
		enum FormFactorEnum extends Enum[FormFactorEnum] { case DEVICE_FORM_FACTOR_UNSPECIFIED, PHONE, TABLET, WEARABLE }
	}
	case class IosModel(
	  /** The unique opaque id for this model. Use this for invoking the TestExecutionService. */
		id: Option[String] = None,
	  /** The human-readable name for this device model. Examples: "iPhone 4s", "iPad Mini 2". */
		name: Option[String] = None,
	  /** The set of iOS major software versions this device supports. */
		supportedVersionIds: Option[List[String]] = None,
	  /** Tags for this dimension. Examples: "default", "preview", "deprecated". */
		tags: Option[List[String]] = None,
	  /** Device capabilities. Copied from https://developer.apple.com/library/archive/documentation/DeviceInformation/Reference/iOSDeviceCompatibility/DeviceCompatibilityMatrix/DeviceCompatibilityMatrix.html */
		deviceCapabilities: Option[List[String]] = None,
	  /** Screen size in the horizontal (X) dimension measured in pixels. */
		screenX: Option[Int] = None,
	  /** Screen size in the vertical (Y) dimension measured in pixels. */
		screenY: Option[Int] = None,
	  /** Screen density in DPI. */
		screenDensity: Option[Int] = None,
	  /** Whether this device is a phone, tablet, wearable, etc. */
		formFactor: Option[Schema.IosModel.FormFactorEnum] = None,
	  /** Version-specific information of an iOS model. */
		perVersionInfo: Option[List[Schema.PerIosVersionInfo]] = None
	)
	
	object PerIosVersionInfo {
		enum DeviceCapacityEnum extends Enum[DeviceCapacityEnum] { case DEVICE_CAPACITY_UNSPECIFIED, DEVICE_CAPACITY_HIGH, DEVICE_CAPACITY_MEDIUM, DEVICE_CAPACITY_LOW, DEVICE_CAPACITY_NONE }
	}
	case class PerIosVersionInfo(
	  /** An iOS version. */
		versionId: Option[String] = None,
	  /** The number of online devices for an iOS version. */
		deviceCapacity: Option[Schema.PerIosVersionInfo.DeviceCapacityEnum] = None
	)
	
	case class IosVersion(
	  /** An opaque id for this iOS version. Use this id to invoke the TestExecutionService. */
		id: Option[String] = None,
	  /** An integer representing the major iOS version. Examples: "8", "9". */
		majorVersion: Option[Int] = None,
	  /** An integer representing the minor iOS version. Examples: "1", "2". */
		minorVersion: Option[Int] = None,
	  /** Tags for this dimension. Examples: "default", "preview", "deprecated". */
		tags: Option[List[String]] = None,
	  /** The available Xcode versions for this version. */
		supportedXcodeVersionIds: Option[List[String]] = None
	)
	
	case class XcodeVersion(
	  /** The id for this version. Example: "9.2". */
		version: Option[String] = None,
	  /** Tags for this Xcode version. Example: "default". */
		tags: Option[List[String]] = None
	)
	
	case class IosRuntimeConfiguration(
	  /** The set of available locales. */
		locales: Option[List[Schema.Locale]] = None,
	  /** The set of available orientations. */
		orientations: Option[List[Schema.Orientation]] = None
	)
	
	case class NetworkConfigurationCatalog(
		configurations: Option[List[Schema.NetworkConfiguration]] = None
	)
	
	case class NetworkConfiguration(
	  /** The unique opaque id for this network traffic configuration. */
		id: Option[String] = None,
	  /** The emulation rule applying to the upload traffic. */
		upRule: Option[Schema.TrafficRule] = None,
	  /** The emulation rule applying to the download traffic. */
		downRule: Option[Schema.TrafficRule] = None
	)
	
	case class TrafficRule(
	  /** Packet delay, must be >= 0. */
		delay: Option[String] = None,
	  /** Packet loss ratio (0.0 - 1.0). */
		packetLossRatio: Option[BigDecimal] = None,
	  /** Packet duplication ratio (0.0 - 1.0). */
		packetDuplicationRatio: Option[BigDecimal] = None,
	  /** Bandwidth in kbits/second. */
		bandwidth: Option[BigDecimal] = None,
	  /** Burst size in kbits. */
		burst: Option[BigDecimal] = None
	)
	
	case class ProvidedSoftwareCatalog(
	  /** Deprecated: Use AndroidX Test Orchestrator going forward. A string representing the current version of Android Test Orchestrator that is used in the environment. The package is available at https://maven.google.com/web/index.html#com.android.support.test:orchestrator. */
		orchestratorVersion: Option[String] = None,
	  /** A string representing the current version of AndroidX Test Orchestrator that is used in the environment. The package is available at https://maven.google.com/web/index.html#androidx.test:orchestrator. */
		androidxOrchestratorVersion: Option[String] = None
	)
	
	case class DeviceIpBlockCatalog(
	  /** The device IP blocks used by Firebase Test Lab */
		ipBlocks: Option[List[Schema.DeviceIpBlock]] = None
	)
	
	object DeviceIpBlock {
		enum FormEnum extends Enum[FormEnum] { case DEVICE_FORM_UNSPECIFIED, VIRTUAL, PHYSICAL, EMULATOR }
	}
	case class DeviceIpBlock(
	  /** An IP address block in CIDR notation eg: 34.68.194.64/29 */
		block: Option[String] = None,
	  /** Whether this block is used by physical or virtual devices */
		form: Option[Schema.DeviceIpBlock.FormEnum] = None,
	  /** The date this block was added to Firebase Test Lab */
		addedDate: Option[Schema.Date] = None
	)
}
