package com.boresjo.play.api.google.script

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object GoogleAppsScriptTypeProcess {
		enum RuntimeVersionEnum extends Enum[RuntimeVersionEnum] { case RUNTIME_VERSION_UNSPECIFIED, DEPRECATED_ES5, V8 }
		enum UserAccessLevelEnum extends Enum[UserAccessLevelEnum] { case USER_ACCESS_LEVEL_UNSPECIFIED, NONE, READ, WRITE, OWNER }
		enum ProcessTypeEnum extends Enum[ProcessTypeEnum] { case PROCESS_TYPE_UNSPECIFIED, ADD_ON, EXECUTION_API, TIME_DRIVEN, TRIGGER, WEBAPP, EDITOR, SIMPLE_TRIGGER, MENU, BATCH_TASK }
		enum ProcessStatusEnum extends Enum[ProcessStatusEnum] { case PROCESS_STATUS_UNSPECIFIED, RUNNING, PAUSED, COMPLETED, CANCELED, FAILED, TIMED_OUT, UNKNOWN, DELAYED, EXECUTION_DISABLED }
	}
	case class GoogleAppsScriptTypeProcess(
	  /** Which version of maestro to use to execute the script. */
		runtimeVersion: Option[Schema.GoogleAppsScriptTypeProcess.RuntimeVersionEnum] = None,
	  /** The executing users access level to the script. */
		userAccessLevel: Option[Schema.GoogleAppsScriptTypeProcess.UserAccessLevelEnum] = None,
	  /** Name of the function the started the execution. */
		functionName: Option[String] = None,
	  /** Name of the script being executed. */
		projectName: Option[String] = None,
	  /** Duration the execution spent executing. */
		duration: Option[String] = None,
	  /** The executions type. */
		processType: Option[Schema.GoogleAppsScriptTypeProcess.ProcessTypeEnum] = None,
	  /** The executions status. */
		processStatus: Option[Schema.GoogleAppsScriptTypeProcess.ProcessStatusEnum] = None,
	  /** Time the execution started. */
		startTime: Option[String] = None
	)
	
	case class ListDeploymentsResponse(
	  /** The token that can be used in the next call to get the next page of results. */
		nextPageToken: Option[String] = None,
	  /** The list of deployments. */
		deployments: Option[List[Schema.Deployment]] = None
	)
	
	case class Deployment(
	  /** The deployment configuration. */
		deploymentConfig: Option[Schema.DeploymentConfig] = None,
	  /** The deployment ID for this deployment. */
		deploymentId: Option[String] = None,
	  /** Last modified date time stamp. */
		updateTime: Option[String] = None,
	  /** The deployment's entry points. */
		entryPoints: Option[List[Schema.EntryPoint]] = None
	)
	
	case class GoogleAppsScriptTypeWebAppEntryPoint(
	  /** The URL for the web application. */
		url: Option[String] = None,
	  /** The entry point's configuration. */
		entryPointConfig: Option[Schema.GoogleAppsScriptTypeWebAppConfig] = None
	)
	
	object EntryPoint {
		enum EntryPointTypeEnum extends Enum[EntryPointTypeEnum] { case ENTRY_POINT_TYPE_UNSPECIFIED, WEB_APP, EXECUTION_API, ADD_ON }
	}
	case class EntryPoint(
	  /** Add-on properties. */
		addOn: Option[Schema.GoogleAppsScriptTypeAddOnEntryPoint] = None,
	  /** An entry point specification for Apps Script API execution calls. */
		executionApi: Option[Schema.GoogleAppsScriptTypeExecutionApiEntryPoint] = None,
	  /** The type of the entry point. */
		entryPointType: Option[Schema.EntryPoint.EntryPointTypeEnum] = None,
	  /** An entry point specification for web apps. */
		webApp: Option[Schema.GoogleAppsScriptTypeWebAppEntryPoint] = None
	)
	
	case class CreateProjectRequest(
	  /** The Drive ID of a parent file that the created script project is bound to. This is usually the ID of a Google Doc, Google Sheet, Google Form, or Google Slides file. If not set, a standalone script project is created. */
		parentId: Option[String] = None,
	  /** The title for the project. */
		title: Option[String] = None
	)
	
	case class UpdateDeploymentRequest(
	  /** The deployment configuration. */
		deploymentConfig: Option[Schema.DeploymentConfig] = None
	)
	
	case class DeploymentConfig(
	  /** The description for this deployment. */
		description: Option[String] = None,
	  /** The script project's Drive ID. */
		scriptId: Option[String] = None,
	  /** The version number on which this deployment is based. */
		versionNumber: Option[Int] = None,
	  /** The manifest file name for this deployment. */
		manifestFileName: Option[String] = None
	)
	
	case class GoogleAppsScriptTypeFunctionSet(
	  /** A list of functions composing the set. */
		values: Option[List[Schema.GoogleAppsScriptTypeFunction]] = None
	)
	
	case class ExecutionRequest(
	  /** The name of the function to execute in the given script. The name does not include parentheses or parameters. It can reference a function in an included library such as `Library.libFunction1`. */
		function: Option[String] = None,
	  /** If `true` and the user is an owner of the script, the script runs at the most recently saved version rather than the version deployed for use with the Apps Script API. Optional; default is `false`. */
		devMode: Option[Boolean] = None,
	  /** The parameters to be passed to the function being executed. The object type for each parameter should match the expected type in Apps Script. Parameters cannot be Apps Script-specific object types (such as a `Document` or a `Calendar`); they can only be primitive types such as `string`, `number`, `array`, `object`, or `boolean`. Optional. */
		parameters: Option[List[JsValue]] = None,
	  /** &#42;Deprecated&#42;. For use with Android add-ons only. An ID that represents the user's current session in the Android app for Google Docs or Sheets, included as extra data in the [Intent](https://developer.android.com/guide/components/intents-filters.html) that launches the add-on. When an Android add-on is run with a session state, it gains the privileges of a [bound](https://developers.google.com/apps-script/guides/bound) script—that is, it can access information like the user's current cursor position (in Docs) or selected cell (in Sheets). To retrieve the state, call `Intent.getStringExtra("com.google.android.apps.docs.addons.SessionState")`. Optional. */
		sessionState: Option[String] = None
	)
	
	case class ListUserProcessesResponse(
	  /** Token for the next page of results. If empty, there are no more pages remaining. */
		nextPageToken: Option[String] = None,
	  /** List of processes matching request parameters. */
		processes: Option[List[Schema.GoogleAppsScriptTypeProcess]] = None
	)
	
	object GoogleAppsScriptTypeExecutionApiConfig {
		enum AccessEnum extends Enum[AccessEnum] { case UNKNOWN_ACCESS, MYSELF, DOMAIN, ANYONE, ANYONE_ANONYMOUS }
	}
	case class GoogleAppsScriptTypeExecutionApiConfig(
	  /** Who has permission to run the API executable. */
		access: Option[Schema.GoogleAppsScriptTypeExecutionApiConfig.AccessEnum] = None
	)
	
	case class ScriptStackTraceElement(
	  /** The name of the function that failed. */
		function: Option[String] = None,
	  /** The line number where the script failed. */
		lineNumber: Option[Int] = None
	)
	
	case class MetricsValue(
	  /** Indicates the number of executions counted. */
		value: Option[String] = None,
	  /** Required field indicating the end time of the interval. */
		endTime: Option[String] = None,
	  /** Required field indicating the start time of the interval. */
		startTime: Option[String] = None
	)
	
	case class Metrics(
	  /** Number of failed executions. */
		failedExecutions: Option[List[Schema.MetricsValue]] = None,
	  /** Number of total executions. */
		totalExecutions: Option[List[Schema.MetricsValue]] = None,
	  /** Number of active users. */
		activeUsers: Option[List[Schema.MetricsValue]] = None
	)
	
	case class ExecutionError(
	  /** The error type, for example `TypeError` or `ReferenceError`. If the error type is unavailable, this field is not included. */
		errorType: Option[String] = None,
	  /** The error message thrown by Apps Script, usually localized into the user's language. */
		errorMessage: Option[String] = None,
	  /** An array of objects that provide a stack trace through the script to show where the execution failed, with the deepest call first. */
		scriptStackTraceElements: Option[List[Schema.ScriptStackTraceElement]] = None
	)
	
	case class Status(
	  /** An array that contains a single ExecutionError object that provides information about the nature of the error. */
		details: Option[List[Map[String, JsValue]]] = None,
	  /** A developer-facing error message, which is in English. Any user-facing error message is localized and sent in the details field, or localized by the client. */
		message: Option[String] = None,
	  /** The status code. For this API, this value either: - 10, indicating a `SCRIPT_TIMEOUT` error, - 3, indicating an `INVALID_ARGUMENT` error, or - 1, indicating a `CANCELLED` execution.  */
		code: Option[Int] = None
	)
	
	object GoogleAppsScriptTypeWebAppConfig {
		enum ExecuteAsEnum extends Enum[ExecuteAsEnum] { case UNKNOWN_EXECUTE_AS, USER_ACCESSING, USER_DEPLOYING }
		enum AccessEnum extends Enum[AccessEnum] { case UNKNOWN_ACCESS, MYSELF, DOMAIN, ANYONE, ANYONE_ANONYMOUS }
	}
	case class GoogleAppsScriptTypeWebAppConfig(
	  /** Who to execute the web app as. */
		executeAs: Option[Schema.GoogleAppsScriptTypeWebAppConfig.ExecuteAsEnum] = None,
	  /** Who has permission to run the web app. */
		access: Option[Schema.GoogleAppsScriptTypeWebAppConfig.AccessEnum] = None
	)
	
	case class Content(
	  /** The script project's Drive ID. */
		scriptId: Option[String] = None,
	  /** The list of script project files. One of the files is a script manifest; it must be named "appsscript", must have type of JSON, and include the manifest configurations for the project. */
		files: Option[List[Schema.File]] = None
	)
	
	case class GoogleAppsScriptTypeExecutionApiEntryPoint(
	  /** The entry point's configuration. */
		entryPointConfig: Option[Schema.GoogleAppsScriptTypeExecutionApiConfig] = None
	)
	
	object Value {
		enum NullValueEnum extends Enum[NullValueEnum] { case NULL_VALUE }
	}
	case class Value(
	  /** Represents a string value. */
		stringValue: Option[String] = None,
	  /** Represents a boolean value. */
		boolValue: Option[Boolean] = None,
	  /** Represents a structured value. */
		structValue: Option[Schema.Struct] = None,
	  /** Represents a null value. */
		nullValue: Option[Schema.Value.NullValueEnum] = None,
	  /** Represents a double value. */
		numberValue: Option[BigDecimal] = None,
	  /** Represents a repeated `Value`. */
		listValue: Option[Schema.ListValue] = None,
	  /** Represents a date in ms since the epoch. */
		dateValue: Option[String] = None,
	  /** Represents raw byte values. */
		bytesValue: Option[String] = None,
	  /** Represents a structured proto value. */
		protoValue: Option[Map[String, JsValue]] = None
	)
	
	case class Struct(
	  /** Unordered map of dynamically typed values. */
		fields: Option[Map[String, Schema.Value]] = None
	)
	
	case class ExecutionResponse(
	  /** The return value of the script function. The type matches the object type returned in Apps Script. Functions called using the Apps Script API cannot return Apps Script-specific objects (such as a `Document` or a `Calendar`); they can only return primitive types such as a `string`, `number`, `array`, `object`, or `boolean`. */
		result: Option[JsValue] = None
	)
	
	case class GoogleAppsScriptTypeFunction(
	  /** The ordered list of parameter names of the function in the script project. */
		parameters: Option[List[String]] = None,
	  /** The function name in the script project. */
		name: Option[String] = None
	)
	
	case class ListValue(
	  /** Repeated field of dynamically typed values. */
		values: Option[List[Schema.Value]] = None
	)
	
	case class ScriptExecutionResult(
	  /** The returned value of the execution. */
		returnValue: Option[Schema.Value] = None
	)
	
	case class Version(
	  /** The script project's Drive ID. */
		scriptId: Option[String] = None,
	  /** The description for this version. */
		description: Option[String] = None,
	  /** The incremental ID that is created by Apps Script when a version is created. This is system assigned number and is immutable once created. */
		versionNumber: Option[Int] = None,
	  /** When the version was created. */
		createTime: Option[String] = None
	)
	
	case class ListVersionsResponse(
	  /** The token use to fetch the next page of records. if not exist in the response, that means no more versions to list. */
		nextPageToken: Option[String] = None,
	  /** The list of versions. */
		versions: Option[List[Schema.Version]] = None
	)
	
	object GoogleAppsScriptTypeAddOnEntryPoint {
		enum AddOnTypeEnum extends Enum[AddOnTypeEnum] { case UNKNOWN_ADDON_TYPE, GMAIL, DATA_STUDIO }
	}
	case class GoogleAppsScriptTypeAddOnEntryPoint(
	  /** The add-on's optional report issue URL. */
		reportIssueUrl: Option[String] = None,
	  /** The add-on's required list of supported container types. */
		addOnType: Option[Schema.GoogleAppsScriptTypeAddOnEntryPoint.AddOnTypeEnum] = None,
	  /** The add-on's optional help URL. */
		helpUrl: Option[String] = None,
	  /** The add-on's required post install tip URL. */
		postInstallTipUrl: Option[String] = None,
	  /** The add-on's required title. */
		title: Option[String] = None,
	  /** The add-on's optional description. */
		description: Option[String] = None
	)
	
	case class GoogleAppsScriptTypeUser(
	  /** The user's photo. */
		photoUrl: Option[String] = None,
	  /** The user's domain. */
		domain: Option[String] = None,
	  /** The user's identifying email address. */
		email: Option[String] = None,
	  /** The user's display name. */
		name: Option[String] = None
	)
	
	case class Empty(
	
	)
	
	case class Project(
	  /** The title for the project. */
		title: Option[String] = None,
	  /** User who last modified the script. */
		lastModifyUser: Option[Schema.GoogleAppsScriptTypeUser] = None,
	  /** The parent's Drive ID that the script will be attached to. This is usually the ID of a Google Document or Google Sheet. This filed is optional, and if not set, a stand-alone script will be created. */
		parentId: Option[String] = None,
	  /** The script project's Drive ID. */
		scriptId: Option[String] = None,
	  /** When the script was last updated. */
		updateTime: Option[String] = None,
	  /** When the script was created. */
		createTime: Option[String] = None,
	  /** User who originally created the script. */
		creator: Option[Schema.GoogleAppsScriptTypeUser] = None
	)
	
	case class Operation(
	  /** If a `run` call succeeds but the script function (or Apps Script itself) throws an exception, this field contains a Status object. The `Status` object's `details` field contains an array with a single ExecutionError object that provides information about the nature of the error. */
		error: Option[Schema.Status] = None,
	  /** This field indicates whether the script execution has completed. A completed execution has a populated `response` field containing the ExecutionResponse from function that was executed. */
		done: Option[Boolean] = None,
	  /** If the script function returns successfully, this field contains an ExecutionResponse object with the function's return value. */
		response: Option[Map[String, JsValue]] = None
	)
	
	object File {
		enum TypeEnum extends Enum[TypeEnum] { case ENUM_TYPE_UNSPECIFIED, SERVER_JS, HTML, JSON }
	}
	case class File(
	  /** The defined set of functions in the script file, if any. */
		functionSet: Option[Schema.GoogleAppsScriptTypeFunctionSet] = None,
	  /** Creation date timestamp. This read-only field is only visible to users who have WRITER permission for the script project. */
		createTime: Option[String] = None,
	  /** Last modified date timestamp. This read-only field is only visible to users who have WRITER permission for the script project. */
		updateTime: Option[String] = None,
	  /** The user who modified the file most recently. This read-only field is only visible to users who have WRITER permission for the script project. */
		lastModifyUser: Option[Schema.GoogleAppsScriptTypeUser] = None,
	  /** The name of the file. The file extension is not part of the file name, which can be identified from the type field. */
		name: Option[String] = None,
	  /** The file content. */
		source: Option[String] = None,
	  /** The type of the file. */
		`type`: Option[Schema.File.TypeEnum] = None
	)
	
	case class ExecuteStreamResponse(
	  /** The result of the execution. */
		result: Option[Schema.ScriptExecutionResult] = None
	)
	
	case class ListScriptProcessesResponse(
	  /** Token for the next page of results. If empty, there are no more pages remaining. */
		nextPageToken: Option[String] = None,
	  /** List of processes matching request parameters. */
		processes: Option[List[Schema.GoogleAppsScriptTypeProcess]] = None
	)
}
