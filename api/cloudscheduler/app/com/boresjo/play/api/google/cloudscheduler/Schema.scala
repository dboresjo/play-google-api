package com.boresjo.play.api.google.cloudscheduler

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ListLocationsResponse(
	  /** A list of locations that matches the specified filter in the request. */
		locations: Option[List[Schema.Location]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class Location(
	  /** Resource name for the location, which may vary between implementations. For example: `"projects/example-project/locations/us-east1"` */
		name: Option[String] = None,
	  /** The canonical id for this location. For example: `"us-east1"`. */
		locationId: Option[String] = None,
	  /** The friendly name for this location, typically a nearby city name. For example, "Tokyo". */
		displayName: Option[String] = None,
	  /** Cross-service attributes for the location. For example {"cloud.googleapis.com/region": "us-east1"} */
		labels: Option[Map[String, String]] = None,
	  /** Service-specific metadata. For example the available capacity at the given location. */
		metadata: Option[Map[String, JsValue]] = None
	)
	
	case class ListJobsResponse(
	  /** The list of jobs. */
		jobs: Option[List[Schema.Job]] = None,
	  /** A token to retrieve next page of results. Pass this value in the page_token field in the subsequent call to ListJobs to retrieve the next page of results. If this is empty it indicates that there are no more results through which to paginate. The page token is valid for only 2 hours. */
		nextPageToken: Option[String] = None
	)
	
	object Job {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, ENABLED, PAUSED, DISABLED, UPDATE_FAILED }
	}
	case class Job(
	  /** Optionally caller-specified in CreateJob, after which it becomes output only. The job name. For example: `projects/PROJECT_ID/locations/LOCATION_ID/jobs/JOB_ID`. &#42; `PROJECT_ID` can contain letters ([A-Za-z]), numbers ([0-9]), hyphens (-), colons (:), or periods (.). For more information, see [Identifying projects](https://cloud.google.com/resource-manager/docs/creating-managing-projects#identifying_projects) &#42; `LOCATION_ID` is the canonical ID for the job's location. The list of available locations can be obtained by calling ListLocations. For more information, see https://cloud.google.com/about/locations/. &#42; `JOB_ID` can contain only letters ([A-Za-z]), numbers ([0-9]), hyphens (-), or underscores (_). The maximum length is 500 characters. */
		name: Option[String] = None,
	  /** Optionally caller-specified in CreateJob or UpdateJob. A human-readable description for the job. This string must not contain more than 500 characters. */
		description: Option[String] = None,
	  /** Pub/Sub target. */
		pubsubTarget: Option[Schema.PubsubTarget] = None,
	  /** App Engine HTTP target. */
		appEngineHttpTarget: Option[Schema.AppEngineHttpTarget] = None,
	  /** HTTP target. */
		httpTarget: Option[Schema.HttpTarget] = None,
	  /** Required, except when used with UpdateJob. Describes the schedule on which the job will be executed. The schedule can be either of the following types: &#42; [Crontab](https://en.wikipedia.org/wiki/Cron#Overview) &#42; English-like [schedule](https://cloud.google.com/scheduler/docs/configuring/cron-job-schedules) As a general rule, execution `n + 1` of a job will not begin until execution `n` has finished. Cloud Scheduler will never allow two simultaneously outstanding executions. For example, this implies that if the `n+1`th execution is scheduled to run at 16:00 but the `n`th execution takes until 16:15, the `n+1`th execution will not start until `16:15`. A scheduled start time will be delayed if the previous execution has not ended when its scheduled time occurs. If retry_count > 0 and a job attempt fails, the job will be tried a total of retry_count times, with exponential backoff, until the next scheduled start time. If retry_count is 0, a job attempt will not be retried if it fails. Instead the Cloud Scheduler system will wait for the next scheduled execution time. Setting retry_count to 0 does not prevent failed jobs from running according to schedule after the failure. */
		schedule: Option[String] = None,
	  /** Specifies the time zone to be used in interpreting schedule. The value of this field must be a time zone name from the [tz database](http://en.wikipedia.org/wiki/Tz_database). Note that some time zones include a provision for daylight savings time. The rules for daylight saving time are determined by the chosen tz. For UTC use the string "utc". If a time zone is not specified, the default will be in UTC (also known as GMT). */
		timeZone: Option[String] = None,
	  /** Output only. The creation time of the job. */
		userUpdateTime: Option[String] = None,
	  /** Output only. State of the job. */
		state: Option[Schema.Job.StateEnum] = None,
	  /** Output only. The response from the target for the last attempted execution. */
		status: Option[Schema.Status] = None,
	  /** Output only. The next time the job is scheduled. Note that this may be a retry of a previously failed attempt or the next execution time according to the schedule. */
		scheduleTime: Option[String] = None,
	  /** Output only. The time the last job attempt started. */
		lastAttemptTime: Option[String] = None,
	  /** Settings that determine the retry behavior. */
		retryConfig: Option[Schema.RetryConfig] = None,
	  /** The deadline for job attempts. If the request handler does not respond by this deadline then the request is cancelled and the attempt is marked as a `DEADLINE_EXCEEDED` failure. The failed attempt can be viewed in execution logs. Cloud Scheduler will retry the job according to the RetryConfig. The default and the allowed values depend on the type of target: &#42; For HTTP targets, the default is 3 minutes. The deadline must be in the interval [15 seconds, 30 minutes]. &#42; For App Engine HTTP targets, 0 indicates that the request has the default deadline. The default deadline depends on the scaling type of the service: 10 minutes for standard apps with automatic scaling, 24 hours for standard apps with manual and basic scaling, and 60 minutes for flex apps. If the request deadline is set, it must be in the interval [15 seconds, 24 hours 15 seconds]. &#42; For Pub/Sub targets, this field is ignored. */
		attemptDeadline: Option[String] = None
	)
	
	case class PubsubTarget(
	  /** Required. The name of the Cloud Pub/Sub topic to which messages will be published when a job is delivered. The topic name must be in the same format as required by Pub/Sub's [PublishRequest.name](https://cloud.google.com/pubsub/docs/reference/rpc/google.pubsub.v1#publishrequest), for example `projects/PROJECT_ID/topics/TOPIC_ID`. The topic must be in the same project as the Cloud Scheduler job. */
		topicName: Option[String] = None,
	  /** The message payload for PubsubMessage. Pubsub message must contain either non-empty data, or at least one attribute. */
		data: Option[String] = None,
	  /** Attributes for PubsubMessage. Pubsub message must contain either non-empty data, or at least one attribute. */
		attributes: Option[Map[String, String]] = None
	)
	
	object AppEngineHttpTarget {
		enum HttpMethodEnum extends Enum[HttpMethodEnum] { case HTTP_METHOD_UNSPECIFIED, POST, GET, HEAD, PUT, DELETE, PATCH, OPTIONS }
	}
	case class AppEngineHttpTarget(
	  /** The HTTP method to use for the request. PATCH and OPTIONS are not permitted. */
		httpMethod: Option[Schema.AppEngineHttpTarget.HttpMethodEnum] = None,
	  /** App Engine Routing setting for the job. */
		appEngineRouting: Option[Schema.AppEngineRouting] = None,
	  /** The relative URI. The relative URL must begin with "/" and must be a valid HTTP relative URL. It can contain a path, query string arguments, and `#` fragments. If the relative URL is empty, then the root path "/" will be used. No spaces are allowed, and the maximum length allowed is 2083 characters. */
		relativeUri: Option[String] = None,
	  /** HTTP request headers. This map contains the header field names and values. Headers can be set when the job is created. Cloud Scheduler sets some headers to default values: &#42; `User-Agent`: By default, this header is `"AppEngine-Google; (+http://code.google.com/appengine)"`. This header can be modified, but Cloud Scheduler will append `"AppEngine-Google; (+http://code.google.com/appengine)"` to the modified `User-Agent`. &#42; `X-CloudScheduler`: This header will be set to true. &#42; `X-CloudScheduler-JobName`: This header will contain the job name. &#42; `X-CloudScheduler-ScheduleTime`: For Cloud Scheduler jobs specified in the unix-cron format, this header will contain the job schedule as an offset of UTC parsed according to RFC3339. If the job has a body and the following headers are not set by the user, Cloud Scheduler sets default values: &#42; `Content-Type`: This will be set to `"application/octet-stream"`. You can override this default by explicitly setting `Content-Type` to a particular media type when creating the job. For example, you can set `Content-Type` to `"application/json"`. The headers below are output only. They cannot be set or overridden: &#42; `Content-Length`: This is computed by Cloud Scheduler. &#42; `X-Google-&#42;`: For Google internal use only. &#42; `X-AppEngine-&#42;`: For Google internal use only. In addition, some App Engine headers, which contain job-specific information, are also be sent to the job handler. */
		headers: Option[Map[String, String]] = None,
	  /** Body. HTTP request body. A request body is allowed only if the HTTP method is POST or PUT. It will result in invalid argument error to set a body on a job with an incompatible HttpMethod. */
		body: Option[String] = None
	)
	
	case class AppEngineRouting(
	  /** App service. By default, the job is sent to the service which is the default service when the job is attempted. */
		service: Option[String] = None,
	  /** App version. By default, the job is sent to the version which is the default version when the job is attempted. */
		version: Option[String] = None,
	  /** App instance. By default, the job is sent to an instance which is available when the job is attempted. Requests can only be sent to a specific instance if [manual scaling is used in App Engine Standard](https://cloud.google.com/appengine/docs/python/an-overview-of-app-engine?#scaling_types_and_instance_classes). App Engine Flex does not support instances. For more information, see [App Engine Standard request routing](https://cloud.google.com/appengine/docs/standard/python/how-requests-are-routed) and [App Engine Flex request routing](https://cloud.google.com/appengine/docs/flexible/python/how-requests-are-routed). */
		instance: Option[String] = None,
	  /** Output only. The host that the job is sent to. For more information about how App Engine requests are routed, see [here](https://cloud.google.com/appengine/docs/standard/python/how-requests-are-routed). The host is constructed as: &#42; `host = [application_domain_name]` `| [service] + '.' + [application_domain_name]` `| [version] + '.' + [application_domain_name]` `| [version_dot_service]+ '.' + [application_domain_name]` `| [instance] + '.' + [application_domain_name]` `| [instance_dot_service] + '.' + [application_domain_name]` `| [instance_dot_version] + '.' + [application_domain_name]` `| [instance_dot_version_dot_service] + '.' + [application_domain_name]` &#42; `application_domain_name` = The domain name of the app, for example .appspot.com, which is associated with the job's project ID. &#42; `service =` service &#42; `version =` version &#42; `version_dot_service =` version `+ '.' +` service &#42; `instance =` instance &#42; `instance_dot_service =` instance `+ '.' +` service &#42; `instance_dot_version =` instance `+ '.' +` version &#42; `instance_dot_version_dot_service =` instance `+ '.' +` version `+ '.' +` service If service is empty, then the job will be sent to the service which is the default service when the job is attempted. If version is empty, then the job will be sent to the version which is the default version when the job is attempted. If instance is empty, then the job will be sent to an instance which is available when the job is attempted. If service, version, or instance is invalid, then the job will be sent to the default version of the default service when the job is attempted. */
		host: Option[String] = None
	)
	
	object HttpTarget {
		enum HttpMethodEnum extends Enum[HttpMethodEnum] { case HTTP_METHOD_UNSPECIFIED, POST, GET, HEAD, PUT, DELETE, PATCH, OPTIONS }
	}
	case class HttpTarget(
	  /** Required. The full URI path that the request will be sent to. This string must begin with either "http://" or "https://". Some examples of valid values for uri are: `http://acme.com` and `https://acme.com/sales:8080`. Cloud Scheduler will encode some characters for safety and compatibility. The maximum allowed URL length is 2083 characters after encoding. */
		uri: Option[String] = None,
	  /** Which HTTP method to use for the request. */
		httpMethod: Option[Schema.HttpTarget.HttpMethodEnum] = None,
	  /** HTTP request headers. This map contains the header field names and values. The user can specify HTTP request headers to send with the job's HTTP request. Repeated headers are not supported, but a header value can contain commas. The following headers represent a subset of the headers that accompany the job's HTTP request. Some HTTP request headers are ignored or replaced. A partial list of headers that are ignored or replaced is below: &#42; Host: This will be computed by Cloud Scheduler and derived from uri. &#42; `Content-Length`: This will be computed by Cloud Scheduler. &#42; `User-Agent`: This will be set to `"Google-Cloud-Scheduler"`. &#42; `X-Google-&#42;`: Google internal use only. &#42; `X-AppEngine-&#42;`: Google internal use only. &#42; `X-CloudScheduler`: This header will be set to true. &#42; `X-CloudScheduler-JobName`: This header will contain the job name. &#42; `X-CloudScheduler-ScheduleTime`: For Cloud Scheduler jobs specified in the unix-cron format, this header will contain the job schedule as an offset of UTC parsed according to RFC3339. If the job has a body and the following headers are not set by the user, Cloud Scheduler sets default values: &#42; `Content-Type`: This will be set to `"application/octet-stream"`. You can override this default by explicitly setting `Content-Type` to a particular media type when creating the job. For example, you can set `Content-Type` to `"application/json"`. The total size of headers must be less than 80KB. */
		headers: Option[Map[String, String]] = None,
	  /** HTTP request body. A request body is allowed only if the HTTP method is POST, PUT, or PATCH. It is an error to set body on a job with an incompatible HttpMethod. */
		body: Option[String] = None,
	  /** If specified, an [OAuth token](https://developers.google.com/identity/protocols/OAuth2) will be generated and attached as an `Authorization` header in the HTTP request. This type of authorization should generally only be used when calling Google APIs hosted on &#42;.googleapis.com. */
		oauthToken: Option[Schema.OAuthToken] = None,
	  /** If specified, an [OIDC](https://developers.google.com/identity/protocols/OpenIDConnect) token will be generated and attached as an `Authorization` header in the HTTP request. This type of authorization can be used for many scenarios, including calling Cloud Run, or endpoints where you intend to validate the token yourself. */
		oidcToken: Option[Schema.OidcToken] = None
	)
	
	case class OAuthToken(
	  /** [Service account email](https://cloud.google.com/iam/docs/service-accounts) to be used for generating OAuth token. The service account must be within the same project as the job. The caller must have iam.serviceAccounts.actAs permission for the service account. */
		serviceAccountEmail: Option[String] = None,
	  /** OAuth scope to be used for generating OAuth access token. If not specified, "https://www.googleapis.com/auth/cloud-platform" will be used. */
		scope: Option[String] = None
	)
	
	case class OidcToken(
	  /** [Service account email](https://cloud.google.com/iam/docs/service-accounts) to be used for generating OIDC token. The service account must be within the same project as the job. The caller must have iam.serviceAccounts.actAs permission for the service account. */
		serviceAccountEmail: Option[String] = None,
	  /** Audience to be used when generating OIDC token. If not specified, the URI specified in target will be used. */
		audience: Option[String] = None
	)
	
	case class Status(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class RetryConfig(
	  /** The number of attempts that the system will make to run a job using the exponential backoff procedure described by max_doublings. The default value of retry_count is zero. If retry_count is 0, a job attempt will not be retried if it fails. Instead the Cloud Scheduler system will wait for the next scheduled execution time. Setting retry_count to 0 does not prevent failed jobs from running according to schedule after the failure. If retry_count is set to a non-zero number then Cloud Scheduler will retry failed attempts, using exponential backoff, retry_count times, or until the next scheduled execution time, whichever comes first. Values greater than 5 and negative values are not allowed. */
		retryCount: Option[Int] = None,
	  /** The time limit for retrying a failed job, measured from time when an execution was first attempted. If specified with retry_count, the job will be retried until both limits are reached. The default value for max_retry_duration is zero, which means retry duration is unlimited. */
		maxRetryDuration: Option[String] = None,
	  /** The minimum amount of time to wait before retrying a job after it fails. The default value of this field is 5 seconds. */
		minBackoffDuration: Option[String] = None,
	  /** The maximum amount of time to wait before retrying a job after it fails. The default value of this field is 1 hour. */
		maxBackoffDuration: Option[String] = None,
	  /** The time between retries will double `max_doublings` times. A job's retry interval starts at min_backoff_duration, then doubles `max_doublings` times, then increases linearly, and finally retries at intervals of max_backoff_duration up to retry_count times. For example, if min_backoff_duration is 10s, max_backoff_duration is 300s, and `max_doublings` is 3, then the job will first be retried in 10s. The retry interval will double three times, and then increase linearly by 2^3 &#42; 10s. Finally, the job will retry at intervals of max_backoff_duration until the job has been attempted retry_count times. Thus, the requests will retry at 10s, 20s, 40s, 80s, 160s, 240s, 300s, 300s, .... The default value of this field is 5. */
		maxDoublings: Option[Int] = None
	)
	
	case class Empty(
	
	)
	
	case class PauseJobRequest(
	
	)
	
	case class ResumeJobRequest(
	
	)
	
	case class RunJobRequest(
	
	)
	
	case class ListOperationsResponse(
	  /** A list of operations that matches the specified filter in the request. */
		operations: Option[List[Schema.Operation]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class Operation(
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the `name` should be a resource name ending with `operations/{unique_id}`. */
		name: Option[String] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** If the value is `false`, it means the operation is still in progress. If `true`, the operation is completed, and either `error` or `response` is available. */
		done: Option[Boolean] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.Status] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as `Delete`, the response is `google.protobuf.Empty`. If the original method is standard `Get`/`Create`/`Update`, the response should be the resource. For other methods, the response should have the type `XxxResponse`, where `Xxx` is the original method name. For example, if the original method name is `TakeSnapshot()`, the inferred response type is `TakeSnapshotResponse`. */
		response: Option[Map[String, JsValue]] = None
	)
	
	case class CancelOperationRequest(
	
	)
	
	case class PubsubMessage(
	  /** Optional. The message data field. If this field is empty, the message must contain at least one attribute. */
		data: Option[String] = None,
	  /** Optional. Attributes for this message. If this field is empty, the message must contain non-empty data. This can be used to filter messages on the subscription. */
		attributes: Option[Map[String, String]] = None,
	  /** ID of this message, assigned by the server when the message is published. Guaranteed to be unique within the topic. This value may be read by a subscriber that receives a `PubsubMessage` via a `Pull` call or a push delivery. It must not be populated by the publisher in a `Publish` call. */
		messageId: Option[String] = None,
	  /** The time at which the message was published, populated by the server when it receives the `Publish` call. It must not be populated by the publisher in a `Publish` call. */
		publishTime: Option[String] = None,
	  /** Optional. If non-empty, identifies related messages for which publish order should be respected. If a `Subscription` has `enable_message_ordering` set to `true`, messages published with the same non-empty `ordering_key` value will be delivered to subscribers in the order in which they are received by the Pub/Sub system. All `PubsubMessage`s published in a given `PublishRequest` must specify the same `ordering_key` value. For more information, see [ordering messages](https://cloud.google.com/pubsub/docs/ordering). */
		orderingKey: Option[String] = None
	)
	
	case class OperationMetadata(
	  /** Output only. The time the operation was created. */
		createTime: Option[String] = None,
	  /** Output only. The time the operation finished running. */
		endTime: Option[String] = None,
	  /** Output only. Server-defined resource path for the target of the operation. */
		target: Option[String] = None,
	  /** Output only. Name of the verb executed by the operation. */
		verb: Option[String] = None,
	  /** Output only. Human-readable status of the operation, if any. */
		statusDetail: Option[String] = None,
	  /** Output only. Identifies whether the user has requested cancellation of the operation. Operations that have been cancelled successfully have google.longrunning.Operation.error value with a google.rpc.Status.code of `1`, corresponding to `Code.CANCELLED`. */
		cancelRequested: Option[Boolean] = None,
	  /** Output only. API version used to start the operation. */
		apiVersion: Option[String] = None
	)
}
