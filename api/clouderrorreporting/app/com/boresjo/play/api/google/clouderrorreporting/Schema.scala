package com.boresjo.play.api.google.clouderrorreporting

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object ErrorGroup {
		enum ResolutionStatusEnum extends Enum[ResolutionStatusEnum] { case RESOLUTION_STATUS_UNSPECIFIED, OPEN, ACKNOWLEDGED, RESOLVED, MUTED }
	}
	case class ErrorGroup(
	  /** The group resource name. Written as `projects/{projectID}/groups/{group_id}` or `projects/{projectID}/locations/{location}/groups/{group_id}` Examples: `projects/my-project-123/groups/my-group`, `projects/my-project-123/locations/us-central1/groups/my-group` In the group resource name, the `group_id` is a unique identifier for a particular error group. The identifier is derived from key parts of the error-log content and is treated as Service Data. For information about how Service Data is handled, see [Google Cloud Privacy Notice](https://cloud.google.com/terms/cloud-privacy-notice). For a list of supported locations, see [Supported Regions](https://cloud.google.com/logging/docs/region-support). `global` is the default when unspecified. */
		name: Option[String] = None,
	  /** An opaque identifier of the group. This field is assigned by the Error Reporting system and always populated. In the group resource name, the `group_id` is a unique identifier for a particular error group. The identifier is derived from key parts of the error-log content and is treated as Service Data. For information about how Service Data is handled, see [Google Cloud Privacy Notice](https://cloud.google.com/terms/cloud-privacy-notice). */
		groupId: Option[String] = None,
	  /** Associated tracking issues. */
		trackingIssues: Option[List[Schema.TrackingIssue]] = None,
	  /** Error group's resolution status. An unspecified resolution status will be interpreted as OPEN */
		resolutionStatus: Option[Schema.ErrorGroup.ResolutionStatusEnum] = None
	)
	
	case class TrackingIssue(
	  /** A URL pointing to a related entry in an issue tracking system. Example: `https://github.com/user/project/issues/4` */
		url: Option[String] = None
	)
	
	case class ListGroupStatsResponse(
	  /** The error group stats which match the given request. */
		errorGroupStats: Option[List[Schema.ErrorGroupStats]] = None,
	  /** If non-empty, more results are available. Pass this token, along with the same query parameters as the first request, to view the next page of results. */
		nextPageToken: Option[String] = None,
	  /** The timestamp specifies the start time to which the request was restricted. The start time is set based on the requested time range. It may be adjusted to a later time if a project has exceeded the storage quota and older data has been deleted. */
		timeRangeBegin: Option[String] = None
	)
	
	case class ErrorGroupStats(
	  /** Group data that is independent of the filter criteria. */
		group: Option[Schema.ErrorGroup] = None,
	  /** Approximate total number of events in the given group that match the filter criteria. */
		count: Option[String] = None,
	  /** Approximate number of affected users in the given group that match the filter criteria. Users are distinguished by data in the ErrorContext of the individual error events, such as their login name or their remote IP address in case of HTTP requests. The number of affected users can be zero even if the number of errors is non-zero if no data was provided from which the affected user could be deduced. Users are counted based on data in the request context that was provided in the error report. If more users are implicitly affected, such as due to a crash of the whole service, this is not reflected here. */
		affectedUsersCount: Option[String] = None,
	  /** Approximate number of occurrences over time. Timed counts returned by ListGroups are guaranteed to be: - Inside the requested time interval - Non-overlapping, and - Ordered by ascending time. */
		timedCounts: Option[List[Schema.TimedCount]] = None,
	  /** Approximate first occurrence that was ever seen for this group and which matches the given filter criteria, ignoring the time_range that was specified in the request. */
		firstSeenTime: Option[String] = None,
	  /** Approximate last occurrence that was ever seen for this group and which matches the given filter criteria, ignoring the time_range that was specified in the request. */
		lastSeenTime: Option[String] = None,
	  /** Service contexts with a non-zero error count for the given filter criteria. This list can be truncated if multiple services are affected. Refer to `num_affected_services` for the total count. */
		affectedServices: Option[List[Schema.ServiceContext]] = None,
	  /** The total number of services with a non-zero error count for the given filter criteria. */
		numAffectedServices: Option[Int] = None,
	  /** An arbitrary event that is chosen as representative for the whole group. The representative event is intended to be used as a quick preview for the whole group. Events in the group are usually sufficiently similar to each other such that showing an arbitrary representative provides insight into the characteristics of the group as a whole. */
		representative: Option[Schema.ErrorEvent] = None
	)
	
	case class TimedCount(
	  /** Approximate number of occurrences in the given time period. */
		count: Option[String] = None,
	  /** Start of the time period to which `count` refers (included). */
		startTime: Option[String] = None,
	  /** End of the time period to which `count` refers (excluded). */
		endTime: Option[String] = None
	)
	
	case class ServiceContext(
	  /** An identifier of the service, such as the name of the executable, job, or Google App Engine service name. This field is expected to have a low number of values that are relatively stable over time, as opposed to `version`, which can be changed whenever new code is deployed. Contains the service name for error reports extracted from Google App Engine logs or `default` if the App Engine default service is used. */
		service: Option[String] = None,
	  /** Represents the source code version that the developer provided, which could represent a version label or a Git SHA-1 hash, for example. For App Engine standard environment, the version is set to the version of the app. */
		version: Option[String] = None,
	  /** Type of the MonitoredResource. List of possible values: https://cloud.google.com/monitoring/api/resources Value is set automatically for incoming errors and must not be set when reporting errors. */
		resourceType: Option[String] = None
	)
	
	case class ErrorEvent(
	  /** Time when the event occurred as provided in the error report. If the report did not contain a timestamp, the time the error was received by the Error Reporting system is used. */
		eventTime: Option[String] = None,
	  /** The `ServiceContext` for which this error was reported. */
		serviceContext: Option[Schema.ServiceContext] = None,
	  /** The stack trace that was reported or logged by the service. */
		message: Option[String] = None,
	  /** Data about the context in which the error occurred. */
		context: Option[Schema.ErrorContext] = None
	)
	
	case class ErrorContext(
	  /** The HTTP request which was processed when the error was triggered. */
		httpRequest: Option[Schema.HttpRequestContext] = None,
	  /** The user who caused or was affected by the crash. This can be a user ID, an email address, or an arbitrary token that uniquely identifies the user. When sending an error report, leave this field empty if the user was not logged in. In this case the Error Reporting system will use other data, such as remote IP address, to distinguish affected users. See `affected_users_count` in `ErrorGroupStats`. */
		user: Option[String] = None,
	  /** The location in the source code where the decision was made to report the error, usually the place where it was logged. For a logged exception this would be the source line where the exception is logged, usually close to the place where it was caught. */
		reportLocation: Option[Schema.SourceLocation] = None,
	  /** Source code that was used to build the executable which has caused the given error message. */
		sourceReferences: Option[List[Schema.SourceReference]] = None
	)
	
	case class HttpRequestContext(
	  /** The type of HTTP request, such as `GET`, `POST`, etc. */
		method: Option[String] = None,
	  /** The URL of the request. */
		url: Option[String] = None,
	  /** The user agent information that is provided with the request. */
		userAgent: Option[String] = None,
	  /** The referrer information that is provided with the request. */
		referrer: Option[String] = None,
	  /** The HTTP response status code for the request. */
		responseStatusCode: Option[Int] = None,
	  /** The IP address from which the request originated. This can be IPv4, IPv6, or a token which is derived from the IP address, depending on the data that has been provided in the error report. */
		remoteIp: Option[String] = None
	)
	
	case class SourceLocation(
	  /** The source code filename, which can include a truncated relative path, or a full path from a production machine. */
		filePath: Option[String] = None,
	  /** 1-based. 0 indicates that the line number is unknown. */
		lineNumber: Option[Int] = None,
	  /** Human-readable name of a function or method. The value can include optional context like the class or package name. For example, `my.package.MyClass.method` in case of Java. */
		functionName: Option[String] = None
	)
	
	case class SourceReference(
	  /** Optional. A URI string identifying the repository. Example: "https://github.com/GoogleCloudPlatform/kubernetes.git" */
		repository: Option[String] = None,
	  /** The canonical and persistent identifier of the deployed revision. Example (git): "0035781c50ec7aa23385dc841529ce8a4b70db1b" */
		revisionId: Option[String] = None
	)
	
	case class ListEventsResponse(
	  /** The error events which match the given request. */
		errorEvents: Option[List[Schema.ErrorEvent]] = None,
	  /** If non-empty, more results are available. Pass this token, along with the same query parameters as the first request, to view the next page of results. */
		nextPageToken: Option[String] = None,
	  /** The timestamp specifies the start time to which the request was restricted. */
		timeRangeBegin: Option[String] = None
	)
	
	case class DeleteEventsResponse(
	
	)
	
	case class ReportedErrorEvent(
	  /** Optional. Time when the event occurred. If not provided, the time when the event was received by the Error Reporting system is used. If provided, the time must not exceed the [logs retention period](https://cloud.google.com/logging/quotas#logs_retention_periods) in the past, or be more than 24 hours in the future. If an invalid time is provided, then an error is returned. */
		eventTime: Option[String] = None,
	  /** Required. The service context in which this error has occurred. */
		serviceContext: Option[Schema.ServiceContext] = None,
	  /** Required. The error message. If no `context.reportLocation` is provided, the message must contain a header (typically consisting of the exception type name and an error message) and an exception stack trace in one of the supported programming languages and formats. Supported languages are Java, Python, JavaScript, Ruby, C#, PHP, and Go. Supported stack trace formats are: &#42; &#42;&#42;Java&#42;&#42;: Must be the return value of [`Throwable.printStackTrace()`](https://docs.oracle.com/javase/7/docs/api/java/lang/Throwable.html#printStackTrace%28%29). &#42; &#42;&#42;Python&#42;&#42;: Must be the return value of [`traceback.format_exc()`](https://docs.python.org/2/library/traceback.html#traceback.format_exc). &#42; &#42;&#42;JavaScript&#42;&#42;: Must be the value of [`error.stack`](https://github.com/v8/v8/wiki/Stack-Trace-API) as returned by V8. &#42; &#42;&#42;Ruby&#42;&#42;: Must contain frames returned by [`Exception.backtrace`](https://ruby-doc.org/core-2.2.0/Exception.html#method-i-backtrace). &#42; &#42;&#42;C#&#42;&#42;: Must be the return value of [`Exception.ToString()`](https://msdn.microsoft.com/en-us/library/system.exception.tostring.aspx). &#42; &#42;&#42;PHP&#42;&#42;: Must be prefixed with `"PHP (Notice|Parse error|Fatal error|Warning): "` and contain the result of [`(string)$exception`](https://php.net/manual/en/exception.tostring.php). &#42; &#42;&#42;Go&#42;&#42;: Must be the return value of [`debug.Stack()`](https://pkg.go.dev/runtime/debug#Stack). */
		message: Option[String] = None,
	  /** Optional. A description of the context in which the error occurred. */
		context: Option[Schema.ErrorContext] = None
	)
	
	case class ReportErrorEventResponse(
	
	)
}
