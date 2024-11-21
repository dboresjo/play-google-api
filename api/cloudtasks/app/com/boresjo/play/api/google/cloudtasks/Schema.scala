package com.boresjo.play.api.google.cloudtasks

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

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
	
	case class ListQueuesResponse(
	  /** The list of queues. */
		queues: Option[List[Schema.Queue]] = None,
	  /** A token to retrieve next page of results. To return the next page of results, call ListQueues with this value as the page_token. If the next_page_token is empty, there are no more results. The page token is valid for only 2 hours. */
		nextPageToken: Option[String] = None
	)
	
	object Queue {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, RUNNING, PAUSED, DISABLED }
	}
	case class Queue(
	  /** Caller-specified and required in CreateQueue, after which it becomes output only. The queue name. The queue name must have the following format: `projects/PROJECT_ID/locations/LOCATION_ID/queues/QUEUE_ID` &#42; `PROJECT_ID` can contain letters ([A-Za-z]), numbers ([0-9]), hyphens (-), colons (:), or periods (.). For more information, see [Identifying projects](https://cloud.google.com/resource-manager/docs/creating-managing-projects#identifying_projects) &#42; `LOCATION_ID` is the canonical ID for the queue's location. The list of available locations can be obtained by calling ListLocations. For more information, see https://cloud.google.com/about/locations/. &#42; `QUEUE_ID` can contain letters ([A-Za-z]), numbers ([0-9]), or hyphens (-). The maximum length is 100 characters. */
		name: Option[String] = None,
	  /** Overrides for task-level app_engine_routing. These settings apply only to App Engine tasks in this queue. Http tasks are not affected. If set, `app_engine_routing_override` is used for all App Engine tasks in the queue, no matter what the setting is for the task-level app_engine_routing. */
		appEngineRoutingOverride: Option[Schema.AppEngineRouting] = None,
	  /** Modifies HTTP target for HTTP tasks. */
		httpTarget: Option[Schema.HttpTarget] = None,
	  /** Rate limits for task dispatches. rate_limits and retry_config are related because they both control task attempts. However they control task attempts in different ways: &#42; rate_limits controls the total rate of dispatches from a queue (i.e. all traffic dispatched from the queue, regardless of whether the dispatch is from a first attempt or a retry). &#42; retry_config controls what happens to particular a task after its first attempt fails. That is, retry_config controls task retries (the second attempt, third attempt, etc). The queue's actual dispatch rate is the result of: &#42; Number of tasks in the queue &#42; User-specified throttling: rate_limits, retry_config, and the queue's state. &#42; System throttling due to `429` (Too Many Requests) or `503` (Service Unavailable) responses from the worker, high error rates, or to smooth sudden large traffic spikes. */
		rateLimits: Option[Schema.RateLimits] = None,
	  /** Settings that determine the retry behavior. &#42; For tasks created using Cloud Tasks: the queue-level retry settings apply to all tasks in the queue that were created using Cloud Tasks. Retry settings cannot be set on individual tasks. &#42; For tasks created using the App Engine SDK: the queue-level retry settings apply to all tasks in the queue which do not have retry settings explicitly set on the task and were created by the App Engine SDK. See [App Engine documentation](https://cloud.google.com/appengine/docs/standard/python/taskqueue/push/retrying-tasks). */
		retryConfig: Option[Schema.RetryConfig] = None,
	  /** Output only. The state of the queue. `state` can only be changed by calling PauseQueue, ResumeQueue, or uploading [queue.yaml/xml](https://cloud.google.com/appengine/docs/python/config/queueref). UpdateQueue cannot be used to change `state`. */
		state: Option[Schema.Queue.StateEnum] = None,
	  /** Output only. The last time this queue was purged. All tasks that were created before this time were purged. A queue can be purged using PurgeQueue, the [App Engine Task Queue SDK, or the Cloud Console](https://cloud.google.com/appengine/docs/standard/python/taskqueue/push/deleting-tasks-and-queues#purging_all_tasks_from_a_queue). Purge time will be truncated to the nearest microsecond. Purge time will be unset if the queue has never been purged. */
		purgeTime: Option[String] = None,
	  /** Configuration options for writing logs to [Stackdriver Logging](https://cloud.google.com/logging/docs/). If this field is unset, then no logs are written. */
		stackdriverLoggingConfig: Option[Schema.StackdriverLoggingConfig] = None
	)
	
	case class AppEngineRouting(
	  /** App service. By default, the task is sent to the service which is the default service when the task is attempted. For some queues or tasks which were created using the App Engine Task Queue API, host is not parsable into service, version, and instance. For example, some tasks which were created using the App Engine SDK use a custom domain name; custom domains are not parsed by Cloud Tasks. If host is not parsable, then service, version, and instance are the empty string. */
		service: Option[String] = None,
	  /** App version. By default, the task is sent to the version which is the default version when the task is attempted. For some queues or tasks which were created using the App Engine Task Queue API, host is not parsable into service, version, and instance. For example, some tasks which were created using the App Engine SDK use a custom domain name; custom domains are not parsed by Cloud Tasks. If host is not parsable, then service, version, and instance are the empty string. */
		version: Option[String] = None,
	  /** App instance. By default, the task is sent to an instance which is available when the task is attempted. Requests can only be sent to a specific instance if [manual scaling is used in App Engine Standard](https://cloud.google.com/appengine/docs/python/an-overview-of-app-engine?hl=en_US#scaling_types_and_instance_classes). App Engine Flex does not support instances. For more information, see [App Engine Standard request routing](https://cloud.google.com/appengine/docs/standard/python/how-requests-are-routed) and [App Engine Flex request routing](https://cloud.google.com/appengine/docs/flexible/python/how-requests-are-routed). */
		instance: Option[String] = None,
	  /** Output only. The host that the task is sent to. The host is constructed from the domain name of the app associated with the queue's project ID (for example .appspot.com), and the service, version, and instance. Tasks which were created using the App Engine SDK might have a custom domain name. For more information, see [How Requests are Routed](https://cloud.google.com/appengine/docs/standard/python/how-requests-are-routed). */
		host: Option[String] = None
	)
	
	object HttpTarget {
		enum HttpMethodEnum extends Enum[HttpMethodEnum] { case HTTP_METHOD_UNSPECIFIED, POST, GET, HEAD, PUT, DELETE, PATCH, OPTIONS }
	}
	case class HttpTarget(
	  /** URI override. When specified, overrides the execution URI for all the tasks in the queue. */
		uriOverride: Option[Schema.UriOverride] = None,
	  /** The HTTP method to use for the request. When specified, it overrides HttpRequest for the task. Note that if the value is set to HttpMethod the HttpRequest of the task will be ignored at execution time. */
		httpMethod: Option[Schema.HttpTarget.HttpMethodEnum] = None,
	  /** HTTP target headers. This map contains the header field names and values. Headers will be set when running the CreateTask and/or BufferTask. These headers represent a subset of the headers that will be configured for the task's HTTP request. Some HTTP request headers will be ignored or replaced. A partial list of headers that will be ignored or replaced is: &#42; Several predefined headers, prefixed with "X-CloudTasks-", can be used to define properties of the task. &#42; Host: This will be computed by Cloud Tasks and derived from HttpRequest.url. &#42; Content-Length: This will be computed by Cloud Tasks. `Content-Type` won't be set by Cloud Tasks. You can explicitly set `Content-Type` to a media type when the task is created. For example,`Content-Type` can be set to `"application/octet-stream"` or `"application/json"`. The default value is set to "application/json"`. &#42; User-Agent: This will be set to `"Google-Cloud-Tasks"`. Headers which can have multiple values (according to RFC2616) can be specified using comma-separated values. The size of the headers must be less than 80KB. Queue-level headers to override headers of all the tasks in the queue. Do not put business sensitive or personally identifying data in the HTTP Header Override Configuration or other similar fields in accordance with Section 12 (Resource Fields) of the [Service Specific Terms](https://cloud.google.com/terms/service-terms). */
		headerOverrides: Option[List[Schema.HeaderOverride]] = None,
	  /** If specified, an [OAuth token](https://developers.google.com/identity/protocols/OAuth2) is generated and attached as the `Authorization` header in the HTTP request. This type of authorization should generally be used only when calling Google APIs hosted on &#42;.googleapis.com. Note that both the service account email and the scope MUST be specified when using the queue-level authorization override. */
		oauthToken: Option[Schema.OAuthToken] = None,
	  /** If specified, an [OIDC](https://developers.google.com/identity/protocols/OpenIDConnect) token is generated and attached as an `Authorization` header in the HTTP request. This type of authorization can be used for many scenarios, including calling Cloud Run, or endpoints where you intend to validate the token yourself. Note that both the service account email and the audience MUST be specified when using the queue-level authorization override. */
		oidcToken: Option[Schema.OidcToken] = None
	)
	
	object UriOverride {
		enum SchemeEnum extends Enum[SchemeEnum] { case SCHEME_UNSPECIFIED, HTTP, HTTPS }
		enum UriOverrideEnforceModeEnum extends Enum[UriOverrideEnforceModeEnum] { case URI_OVERRIDE_ENFORCE_MODE_UNSPECIFIED, IF_NOT_EXISTS, ALWAYS }
	}
	case class UriOverride(
	  /** Scheme override. When specified, the task URI scheme is replaced by the provided value (HTTP or HTTPS). */
		scheme: Option[Schema.UriOverride.SchemeEnum] = None,
	  /** Host override. When specified, replaces the host part of the task URL. For example, if the task URL is "https://www.google.com," and host value is set to "example.net", the overridden URI will be changed to "https://example.net." Host value cannot be an empty string (INVALID_ARGUMENT). */
		host: Option[String] = None,
	  /** Port override. When specified, replaces the port part of the task URI. For instance, for a URI http://www.google.com/foo and port=123, the overridden URI becomes http://www.google.com:123/foo. Note that the port value must be a positive integer. Setting the port to 0 (Zero) clears the URI port. */
		port: Option[String] = None,
	  /** URI path. When specified, replaces the existing path of the task URL. Setting the path value to an empty string clears the URI path segment. */
		pathOverride: Option[Schema.PathOverride] = None,
	  /** URI query. When specified, replaces the query part of the task URI. Setting the query value to an empty string clears the URI query segment. */
		queryOverride: Option[Schema.QueryOverride] = None,
	  /** URI Override Enforce Mode When specified, determines the Target UriOverride mode. If not specified, it defaults to ALWAYS. */
		uriOverrideEnforceMode: Option[Schema.UriOverride.UriOverrideEnforceModeEnum] = None
	)
	
	case class PathOverride(
	  /** The URI path (e.g., /users/1234). Default is an empty string. */
		path: Option[String] = None
	)
	
	case class QueryOverride(
	  /** The query parameters (e.g., qparam1=123&qparam2=456). Default is an empty string. */
		queryParams: Option[String] = None
	)
	
	case class HeaderOverride(
	  /** Header embodying a key and a value. Do not put business sensitive or personally identifying data in the HTTP Header Override Configuration or other similar fields in accordance with Section 12 (Resource Fields) of the [Service Specific Terms](https://cloud.google.com/terms/service-terms). */
		header: Option[Schema.Header] = None
	)
	
	case class Header(
	  /** The Key of the header. */
		key: Option[String] = None,
	  /** The Value of the header. */
		value: Option[String] = None
	)
	
	case class OAuthToken(
	  /** [Service account email](https://cloud.google.com/iam/docs/service-accounts) to be used for generating OAuth token. The service account must be within the same project as the queue. The caller must have iam.serviceAccounts.actAs permission for the service account. */
		serviceAccountEmail: Option[String] = None,
	  /** OAuth scope to be used for generating OAuth access token. If not specified, "https://www.googleapis.com/auth/cloud-platform" will be used. */
		scope: Option[String] = None
	)
	
	case class OidcToken(
	  /** [Service account email](https://cloud.google.com/iam/docs/service-accounts) to be used for generating OIDC token. The service account must be within the same project as the queue. The caller must have iam.serviceAccounts.actAs permission for the service account. */
		serviceAccountEmail: Option[String] = None,
	  /** Audience to be used when generating OIDC token. If not specified, the URI specified in target will be used. */
		audience: Option[String] = None
	)
	
	case class RateLimits(
	  /** The maximum rate at which tasks are dispatched from this queue. If unspecified when the queue is created, Cloud Tasks will pick the default. &#42; The maximum allowed value is 500. This field has the same meaning as [rate in queue.yaml/xml](https://cloud.google.com/appengine/docs/standard/python/config/queueref#rate). */
		maxDispatchesPerSecond: Option[BigDecimal] = None,
	  /** Output only. The max burst size. Max burst size limits how fast tasks in queue are processed when many tasks are in the queue and the rate is high. This field allows the queue to have a high rate so processing starts shortly after a task is enqueued, but still limits resource usage when many tasks are enqueued in a short period of time. The [token bucket](https://wikipedia.org/wiki/Token_Bucket) algorithm is used to control the rate of task dispatches. Each queue has a token bucket that holds tokens, up to the maximum specified by `max_burst_size`. Each time a task is dispatched, a token is removed from the bucket. Tasks will be dispatched until the queue's bucket runs out of tokens. The bucket will be continuously refilled with new tokens based on max_dispatches_per_second. Cloud Tasks will pick the value of `max_burst_size` based on the value of max_dispatches_per_second. For queues that were created or updated using `queue.yaml/xml`, `max_burst_size` is equal to [bucket_size](https://cloud.google.com/appengine/docs/standard/python/config/queueref#bucket_size). Since `max_burst_size` is output only, if UpdateQueue is called on a queue created by `queue.yaml/xml`, `max_burst_size` will be reset based on the value of max_dispatches_per_second, regardless of whether max_dispatches_per_second is updated.  */
		maxBurstSize: Option[Int] = None,
	  /** The maximum number of concurrent tasks that Cloud Tasks allows to be dispatched for this queue. After this threshold has been reached, Cloud Tasks stops dispatching tasks until the number of concurrent requests decreases. If unspecified when the queue is created, Cloud Tasks will pick the default. The maximum allowed value is 5,000. This field has the same meaning as [max_concurrent_requests in queue.yaml/xml](https://cloud.google.com/appengine/docs/standard/python/config/queueref#max_concurrent_requests). */
		maxConcurrentDispatches: Option[Int] = None
	)
	
	case class RetryConfig(
	  /** Number of attempts per task. Cloud Tasks will attempt the task `max_attempts` times (that is, if the first attempt fails, then there will be `max_attempts - 1` retries). Must be >= -1. If unspecified when the queue is created, Cloud Tasks will pick the default. -1 indicates unlimited attempts. This field has the same meaning as [task_retry_limit in queue.yaml/xml](https://cloud.google.com/appengine/docs/standard/python/config/queueref#retry_parameters). */
		maxAttempts: Option[Int] = None,
	  /** If positive, `max_retry_duration` specifies the time limit for retrying a failed task, measured from when the task was first attempted. Once `max_retry_duration` time has passed &#42;and&#42; the task has been attempted max_attempts times, no further attempts will be made and the task will be deleted. If zero, then the task age is unlimited. If unspecified when the queue is created, Cloud Tasks will pick the default. The value must be given as a string that indicates the length of time (in seconds) followed by `s` (for "seconds"). For the maximum possible value or the format, see the documentation for [Duration](https://protobuf.dev/reference/protobuf/google.protobuf/#duration). `max_retry_duration` will be truncated to the nearest second. This field has the same meaning as [task_age_limit in queue.yaml/xml](https://cloud.google.com/appengine/docs/standard/python/config/queueref#retry_parameters). */
		maxRetryDuration: Option[String] = None,
	  /** A task will be scheduled for retry between min_backoff and max_backoff duration after it fails, if the queue's RetryConfig specifies that the task should be retried. If unspecified when the queue is created, Cloud Tasks will pick the default. The value must be given as a string that indicates the length of time (in seconds) followed by `s` (for "seconds"). For more information on the format, see the documentation for [Duration](https://protobuf.dev/reference/protobuf/google.protobuf/#duration). `min_backoff` will be truncated to the nearest second. This field has the same meaning as [min_backoff_seconds in queue.yaml/xml](https://cloud.google.com/appengine/docs/standard/python/config/queueref#retry_parameters). */
		minBackoff: Option[String] = None,
	  /** A task will be scheduled for retry between min_backoff and max_backoff duration after it fails, if the queue's RetryConfig specifies that the task should be retried. If unspecified when the queue is created, Cloud Tasks will pick the default. The value must be given as a string that indicates the length of time (in seconds) followed by `s` (for "seconds"). For more information on the format, see the documentation for [Duration](https://protobuf.dev/reference/protobuf/google.protobuf/#duration). `max_backoff` will be truncated to the nearest second. This field has the same meaning as [max_backoff_seconds in queue.yaml/xml](https://cloud.google.com/appengine/docs/standard/python/config/queueref#retry_parameters). */
		maxBackoff: Option[String] = None,
	  /** The time between retries will double `max_doublings` times. A task's retry interval starts at min_backoff, then doubles `max_doublings` times, then increases linearly, and finally retries at intervals of max_backoff up to max_attempts times. For example, if min_backoff is 10s, max_backoff is 300s, and `max_doublings` is 3, then the a task will first be retried in 10s. The retry interval will double three times, and then increase linearly by 2^3 &#42; 10s. Finally, the task will retry at intervals of max_backoff until the task has been attempted max_attempts times. Thus, the requests will retry at 10s, 20s, 40s, 80s, 160s, 240s, 300s, 300s, .... If unspecified when the queue is created, Cloud Tasks will pick the default. This field has the same meaning as [max_doublings in queue.yaml/xml](https://cloud.google.com/appengine/docs/standard/python/config/queueref#retry_parameters). */
		maxDoublings: Option[Int] = None
	)
	
	case class StackdriverLoggingConfig(
	  /** Specifies the fraction of operations to write to [Stackdriver Logging](https://cloud.google.com/logging/docs/). This field may contain any value between 0.0 and 1.0, inclusive. 0.0 is the default and means that no operations are logged. */
		samplingRatio: Option[BigDecimal] = None
	)
	
	case class Empty(
	
	)
	
	case class PurgeQueueRequest(
	
	)
	
	case class PauseQueueRequest(
	
	)
	
	case class ResumeQueueRequest(
	
	)
	
	case class GetIamPolicyRequest(
	  /** OPTIONAL: A `GetPolicyOptions` object for specifying options to `GetIamPolicy`. */
		options: Option[Schema.GetPolicyOptions] = None
	)
	
	case class GetPolicyOptions(
	  /** Optional. The maximum policy version that will be used to format the policy. Valid values are 0, 1, and 3. Requests specifying an invalid value will be rejected. Requests for policies with any conditional role bindings must specify version 3. Policies with no conditional role bindings may specify any valid value or leave the field unset. The policy in the response might use the policy version that you specified, or it might use a lower policy version. For example, if you specify version 3, but the policy has no conditional role bindings, the response uses version 1. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		requestedPolicyVersion: Option[Int] = None
	)
	
	case class Policy(
	  /** Specifies the format of the policy. Valid values are `0`, `1`, and `3`. Requests that specify an invalid value are rejected. Any operation that affects conditional role bindings must specify version `3`. This requirement applies to the following operations: &#42; Getting a policy that includes a conditional role binding &#42; Adding a conditional role binding to a policy &#42; Changing a conditional role binding in a policy &#42; Removing any role binding, with or without a condition, from a policy that includes conditions &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. If a policy does not include any conditions, operations on that policy may specify any valid version or leave the field unset. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		version: Option[Int] = None,
	  /** Associates a list of `members`, or principals, with a `role`. Optionally, may specify a `condition` that determines how and when the `bindings` are applied. Each of the `bindings` must contain at least one principal. The `bindings` in a `Policy` can refer to up to 1,500 principals; up to 250 of these principals can be Google groups. Each occurrence of a principal counts towards these limits. For example, if the `bindings` grant 50 different roles to `user:alice@example.com`, and not to any other principal, then you can add another 1,450 principals to the `bindings` in the `Policy`. */
		bindings: Option[List[Schema.Binding]] = None,
	  /** `etag` is used for optimistic concurrency control as a way to help prevent simultaneous updates of a policy from overwriting each other. It is strongly suggested that systems make use of the `etag` in the read-modify-write cycle to perform policy updates in order to avoid race conditions: An `etag` is returned in the response to `getIamPolicy`, and systems are expected to put that etag in the request to `setIamPolicy` to ensure that their change will be applied to the same version of the policy. &#42;&#42;Important:&#42;&#42; If you use IAM Conditions, you must include the `etag` field whenever you call `setIamPolicy`. If you omit this field, then IAM allows you to overwrite a version `3` policy with a version `1` policy, and all of the conditions in the version `3` policy are lost. */
		etag: Option[String] = None
	)
	
	case class Binding(
	  /** Role that is assigned to the list of `members`, or principals. For example, `roles/viewer`, `roles/editor`, or `roles/owner`. For an overview of the IAM roles and permissions, see the [IAM documentation](https://cloud.google.com/iam/docs/roles-overview). For a list of the available pre-defined roles, see [here](https://cloud.google.com/iam/docs/understanding-roles). */
		role: Option[String] = None,
	  /** Specifies the principals requesting access for a Google Cloud resource. `members` can have the following values: &#42; `allUsers`: A special identifier that represents anyone who is on the internet; with or without a Google account. &#42; `allAuthenticatedUsers`: A special identifier that represents anyone who is authenticated with a Google account or a service account. Does not include identities that come from external identity providers (IdPs) through identity federation. &#42; `user:{emailid}`: An email address that represents a specific Google account. For example, `alice@example.com` . &#42; `serviceAccount:{emailid}`: An email address that represents a Google service account. For example, `my-other-app@appspot.gserviceaccount.com`. &#42; `serviceAccount:{projectid}.svc.id.goog[{namespace}/{kubernetes-sa}]`: An identifier for a [Kubernetes service account](https://cloud.google.com/kubernetes-engine/docs/how-to/kubernetes-service-accounts). For example, `my-project.svc.id.goog[my-namespace/my-kubernetes-sa]`. &#42; `group:{emailid}`: An email address that represents a Google group. For example, `admins@example.com`. &#42; `domain:{domain}`: The G Suite domain (primary) that represents all the users of that domain. For example, `google.com` or `example.com`. &#42; `principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workforce identity pool. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/group/{group_id}`: All workforce identities in a group. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All workforce identities with a specific attribute value. &#42; `principalSet://iam.googleapis.com/locations/global/workforcePools/{pool_id}/&#42;`: All identities in a workforce identity pool. &#42; `principal://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/subject/{subject_attribute_value}`: A single identity in a workload identity pool. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/group/{group_id}`: A workload identity pool group. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/attribute.{attribute_name}/{attribute_value}`: All identities in a workload identity pool with a certain attribute. &#42; `principalSet://iam.googleapis.com/projects/{project_number}/locations/global/workloadIdentityPools/{pool_id}/&#42;`: All identities in a workload identity pool. &#42; `deleted:user:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a user that has been recently deleted. For example, `alice@example.com?uid=123456789012345678901`. If the user is recovered, this value reverts to `user:{emailid}` and the recovered user retains the role in the binding. &#42; `deleted:serviceAccount:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a service account that has been recently deleted. For example, `my-other-app@appspot.gserviceaccount.com?uid=123456789012345678901`. If the service account is undeleted, this value reverts to `serviceAccount:{emailid}` and the undeleted service account retains the role in the binding. &#42; `deleted:group:{emailid}?uid={uniqueid}`: An email address (plus unique identifier) representing a Google group that has been recently deleted. For example, `admins@example.com?uid=123456789012345678901`. If the group is recovered, this value reverts to `group:{emailid}` and the recovered group retains the role in the binding. &#42; `deleted:principal://iam.googleapis.com/locations/global/workforcePools/{pool_id}/subject/{subject_attribute_value}`: Deleted single identity in a workforce identity pool. For example, `deleted:principal://iam.googleapis.com/locations/global/workforcePools/my-pool-id/subject/my-subject-attribute-value`. */
		members: Option[List[String]] = None,
	  /** The condition that is associated with this binding. If the condition evaluates to `true`, then this binding applies to the current request. If the condition evaluates to `false`, then this binding does not apply to the current request. However, a different role binding might grant the same role to one or more of the principals in this binding. To learn which resources support conditions in their IAM policies, see the [IAM documentation](https://cloud.google.com/iam/help/conditions/resource-policies). */
		condition: Option[Schema.Expr] = None
	)
	
	case class Expr(
	  /** Textual representation of an expression in Common Expression Language syntax. */
		expression: Option[String] = None,
	  /** Optional. Title for the expression, i.e. a short string describing its purpose. This can be used e.g. in UIs which allow to enter the expression. */
		title: Option[String] = None,
	  /** Optional. Description of the expression. This is a longer text which describes the expression, e.g. when hovered over it in a UI. */
		description: Option[String] = None,
	  /** Optional. String indicating the location of the expression for error reporting, e.g. a file name and a position in the file. */
		location: Option[String] = None
	)
	
	case class SetIamPolicyRequest(
	  /** REQUIRED: The complete policy to be applied to the `resource`. The size of the policy is limited to a few 10s of KB. An empty policy is a valid policy but certain Google Cloud services (such as Projects) might reject them. */
		policy: Option[Schema.Policy] = None
	)
	
	case class TestIamPermissionsRequest(
	  /** The set of permissions to check for the `resource`. Permissions with wildcards (such as `&#42;` or `storage.&#42;`) are not allowed. For more information see [IAM Overview](https://cloud.google.com/iam/docs/overview#permissions). */
		permissions: Option[List[String]] = None
	)
	
	case class TestIamPermissionsResponse(
	  /** A subset of `TestPermissionsRequest.permissions` that the caller is allowed. */
		permissions: Option[List[String]] = None
	)
	
	case class ListTasksResponse(
	  /** The list of tasks. */
		tasks: Option[List[Schema.Task]] = None,
	  /** A token to retrieve next page of results. To return the next page of results, call ListTasks with this value as the page_token. If the next_page_token is empty, there are no more results. */
		nextPageToken: Option[String] = None
	)
	
	object Task {
		enum ViewEnum extends Enum[ViewEnum] { case VIEW_UNSPECIFIED, BASIC, FULL }
	}
	case class Task(
	  /** Optionally caller-specified in CreateTask. The task name. The task name must have the following format: `projects/PROJECT_ID/locations/LOCATION_ID/queues/QUEUE_ID/tasks/TASK_ID` &#42; `PROJECT_ID` can contain letters ([A-Za-z]), numbers ([0-9]), hyphens (-), colons (:), or periods (.). For more information, see [Identifying projects](https://cloud.google.com/resource-manager/docs/creating-managing-projects#identifying_projects) &#42; `LOCATION_ID` is the canonical ID for the task's location. The list of available locations can be obtained by calling ListLocations. For more information, see https://cloud.google.com/about/locations/. &#42; `QUEUE_ID` can contain letters ([A-Za-z]), numbers ([0-9]), or hyphens (-). The maximum length is 100 characters. &#42; `TASK_ID` can contain only letters ([A-Za-z]), numbers ([0-9]), hyphens (-), or underscores (_). The maximum length is 500 characters. */
		name: Option[String] = None,
	  /** HTTP request that is sent to the App Engine app handler. An App Engine task is a task that has AppEngineHttpRequest set. */
		appEngineHttpRequest: Option[Schema.AppEngineHttpRequest] = None,
	  /** HTTP request that is sent to the worker. An HTTP task is a task that has HttpRequest set. */
		httpRequest: Option[Schema.HttpRequest] = None,
	  /** The time when the task is scheduled to be attempted or retried. `schedule_time` will be truncated to the nearest microsecond. */
		scheduleTime: Option[String] = None,
	  /** Output only. The time that the task was created. `create_time` will be truncated to the nearest second. */
		createTime: Option[String] = None,
	  /** The deadline for requests sent to the worker. If the worker does not respond by this deadline then the request is cancelled and the attempt is marked as a `DEADLINE_EXCEEDED` failure. Cloud Tasks will retry the task according to the RetryConfig. Note that when the request is cancelled, Cloud Tasks will stop listening for the response, but whether the worker stops processing depends on the worker. For example, if the worker is stuck, it may not react to cancelled requests. The default and maximum values depend on the type of request: &#42; For HTTP tasks, the default is 10 minutes. The deadline must be in the interval [15 seconds, 30 minutes]. &#42; For App Engine tasks, 0 indicates that the request has the default deadline. The default deadline depends on the [scaling type](https://cloud.google.com/appengine/docs/standard/go/how-instances-are-managed#instance_scaling) of the service: 10 minutes for standard apps with automatic scaling, 24 hours for standard apps with manual and basic scaling, and 60 minutes for flex apps. If the request deadline is set, it must be in the interval [15 seconds, 24 hours 15 seconds]. Regardless of the task's `dispatch_deadline`, the app handler will not run for longer than than the service's timeout. We recommend setting the `dispatch_deadline` to at most a few seconds more than the app handler's timeout. For more information see [Timeouts](https://cloud.google.com/tasks/docs/creating-appengine-handlers#timeouts). The value must be given as a string that indicates the length of time (in seconds) followed by `s` (for "seconds"). For more information on the format, see the documentation for [Duration](https://protobuf.dev/reference/protobuf/google.protobuf/#duration). `dispatch_deadline` will be truncated to the nearest millisecond. The deadline is an approximate deadline. */
		dispatchDeadline: Option[String] = None,
	  /** Output only. The number of attempts dispatched. This count includes attempts which have been dispatched but haven't received a response. */
		dispatchCount: Option[Int] = None,
	  /** Output only. The number of attempts which have received a response. */
		responseCount: Option[Int] = None,
	  /** Output only. The status of the task's first attempt. Only dispatch_time will be set. The other Attempt information is not retained by Cloud Tasks. */
		firstAttempt: Option[Schema.Attempt] = None,
	  /** Output only. The status of the task's last attempt. */
		lastAttempt: Option[Schema.Attempt] = None,
	  /** Output only. The view specifies which subset of the Task has been returned. */
		view: Option[Schema.Task.ViewEnum] = None
	)
	
	object AppEngineHttpRequest {
		enum HttpMethodEnum extends Enum[HttpMethodEnum] { case HTTP_METHOD_UNSPECIFIED, POST, GET, HEAD, PUT, DELETE, PATCH, OPTIONS }
	}
	case class AppEngineHttpRequest(
	  /** The HTTP method to use for the request. The default is POST. The app's request handler for the task's target URL must be able to handle HTTP requests with this http_method, otherwise the task attempt fails with error code 405 (Method Not Allowed). See [Writing a push task request handler](https://cloud.google.com/appengine/docs/java/taskqueue/push/creating-handlers#writing_a_push_task_request_handler) and the App Engine documentation for your runtime on [How Requests are Handled](https://cloud.google.com/appengine/docs/standard/python3/how-requests-are-handled). */
		httpMethod: Option[Schema.AppEngineHttpRequest.HttpMethodEnum] = None,
	  /** Task-level setting for App Engine routing. &#42; If app_engine_routing_override is set on the queue, this value is used for all tasks in the queue, no matter what the setting is for the task-level app_engine_routing. */
		appEngineRouting: Option[Schema.AppEngineRouting] = None,
	  /** The relative URI. The relative URI must begin with "/" and must be a valid HTTP relative URI. It can contain a path and query string arguments. If the relative URI is empty, then the root path "/" will be used. No spaces are allowed, and the maximum length allowed is 2083 characters. */
		relativeUri: Option[String] = None,
	  /** HTTP request headers. This map contains the header field names and values. Headers can be set when the task is created. Repeated headers are not supported but a header value can contain commas. Cloud Tasks sets some headers to default values: &#42; `User-Agent`: By default, this header is `"AppEngine-Google; (+http://code.google.com/appengine)"`. This header can be modified, but Cloud Tasks will append `"AppEngine-Google; (+http://code.google.com/appengine)"` to the modified `User-Agent`. If the task has a body, Cloud Tasks sets the following headers: &#42; `Content-Type`: By default, the `Content-Type` header is set to `"application/octet-stream"`. The default can be overridden by explicitly setting `Content-Type` to a particular media type when the task is created. For example, `Content-Type` can be set to `"application/json"`. &#42; `Content-Length`: This is computed by Cloud Tasks. This value is output only. It cannot be changed. The headers below cannot be set or overridden: &#42; `Host` &#42; `X-Google-&#42;` &#42; `X-AppEngine-&#42;` In addition, Cloud Tasks sets some headers when the task is dispatched, such as headers containing information about the task; see [request headers](https://cloud.google.com/tasks/docs/creating-appengine-handlers#reading_request_headers). These headers are set only when the task is dispatched, so they are not visible when the task is returned in a Cloud Tasks response. Although there is no specific limit for the maximum number of headers or the size, there is a limit on the maximum size of the Task. For more information, see the CreateTask documentation. */
		headers: Option[Map[String, String]] = None,
	  /** HTTP request body. A request body is allowed only if the HTTP method is POST or PUT. It is an error to set a body on a task with an incompatible HttpMethod. */
		body: Option[String] = None
	)
	
	object HttpRequest {
		enum HttpMethodEnum extends Enum[HttpMethodEnum] { case HTTP_METHOD_UNSPECIFIED, POST, GET, HEAD, PUT, DELETE, PATCH, OPTIONS }
	}
	case class HttpRequest(
	  /** Required. The full url path that the request will be sent to. This string must begin with either "http://" or "https://". Some examples are: `http://acme.com` and `https://acme.com/sales:8080`. Cloud Tasks will encode some characters for safety and compatibility. The maximum allowed URL length is 2083 characters after encoding. The `Location` header response from a redirect response [`300` - `399`] may be followed. The redirect is not counted as a separate attempt. */
		url: Option[String] = None,
	  /** The HTTP method to use for the request. The default is POST. */
		httpMethod: Option[Schema.HttpRequest.HttpMethodEnum] = None,
	  /** HTTP request headers. This map contains the header field names and values. Headers can be set when the task is created. These headers represent a subset of the headers that will accompany the task's HTTP request. Some HTTP request headers will be ignored or replaced. A partial list of headers that will be ignored or replaced is: &#42; Host: This will be computed by Cloud Tasks and derived from HttpRequest.url. &#42; Content-Length: This will be computed by Cloud Tasks. &#42; User-Agent: This will be set to `"Google-Cloud-Tasks"`. &#42; `X-Google-&#42;`: Google use only. &#42; `X-AppEngine-&#42;`: Google use only. `Content-Type` won't be set by Cloud Tasks. You can explicitly set `Content-Type` to a media type when the task is created. For example, `Content-Type` can be set to `"application/octet-stream"` or `"application/json"`. Headers which can have multiple values (according to RFC2616) can be specified using comma-separated values. The size of the headers must be less than 80KB. */
		headers: Option[Map[String, String]] = None,
	  /** HTTP request body. A request body is allowed only if the HTTP method is POST, PUT, or PATCH. It is an error to set body on a task with an incompatible HttpMethod. */
		body: Option[String] = None,
	  /** If specified, an [OAuth token](https://developers.google.com/identity/protocols/OAuth2) will be generated and attached as an `Authorization` header in the HTTP request. This type of authorization should generally only be used when calling Google APIs hosted on &#42;.googleapis.com. */
		oauthToken: Option[Schema.OAuthToken] = None,
	  /** If specified, an [OIDC](https://developers.google.com/identity/protocols/OpenIDConnect) token will be generated and attached as an `Authorization` header in the HTTP request. This type of authorization can be used for many scenarios, including calling Cloud Run, or endpoints where you intend to validate the token yourself. */
		oidcToken: Option[Schema.OidcToken] = None
	)
	
	case class Attempt(
	  /** Output only. The time that this attempt was scheduled. `schedule_time` will be truncated to the nearest microsecond. */
		scheduleTime: Option[String] = None,
	  /** Output only. The time that this attempt was dispatched. `dispatch_time` will be truncated to the nearest microsecond. */
		dispatchTime: Option[String] = None,
	  /** Output only. The time that this attempt response was received. `response_time` will be truncated to the nearest microsecond. */
		responseTime: Option[String] = None,
	  /** Output only. The response from the worker for this attempt. If `response_time` is unset, then the task has not been attempted or is currently running and the `response_status` field is meaningless. */
		responseStatus: Option[Schema.Status] = None
	)
	
	case class Status(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	object CreateTaskRequest {
		enum ResponseViewEnum extends Enum[ResponseViewEnum] { case VIEW_UNSPECIFIED, BASIC, FULL }
	}
	case class CreateTaskRequest(
	  /** Required. The task to add. Task names have the following format: `projects/PROJECT_ID/locations/LOCATION_ID/queues/QUEUE_ID/tasks/TASK_ID`. The user can optionally specify a task name. If a name is not specified then the system will generate a random unique task id, which will be set in the task returned in the response. If schedule_time is not set or is in the past then Cloud Tasks will set it to the current time. Task De-duplication: Explicitly specifying a task ID enables task de-duplication. If a task's ID is identical to that of an existing task or a task that was deleted or executed recently then the call will fail with ALREADY_EXISTS. The IDs of deleted tasks are not immediately available for reuse. It can take up to 4 hours (or 9 days if the task's queue was created using a queue.yaml or queue.xml) for the task ID to be released and made available again. Because there is an extra lookup cost to identify duplicate task names, these CreateTask calls have significantly increased latency. Using hashed strings for the task id or for the prefix of the task id is recommended. Choosing task ids that are sequential or have sequential prefixes, for example using a timestamp, causes an increase in latency and error rates in all task commands. The infrastructure relies on an approximately uniform distribution of task ids to store and serve tasks efficiently. */
		task: Option[Schema.Task] = None,
	  /** The response_view specifies which subset of the Task will be returned. By default response_view is BASIC; not all information is retrieved by default because some data, such as payloads, might be desirable to return only when needed because of its large size or because of the sensitivity of data that it contains. Authorization for FULL requires `cloudtasks.tasks.fullView` [Google IAM](https://cloud.google.com/iam/) permission on the Task resource. */
		responseView: Option[Schema.CreateTaskRequest.ResponseViewEnum] = None
	)
	
	object RunTaskRequest {
		enum ResponseViewEnum extends Enum[ResponseViewEnum] { case VIEW_UNSPECIFIED, BASIC, FULL }
	}
	case class RunTaskRequest(
	  /** The response_view specifies which subset of the Task will be returned. By default response_view is BASIC; not all information is retrieved by default because some data, such as payloads, might be desirable to return only when needed because of its large size or because of the sensitivity of data that it contains. Authorization for FULL requires `cloudtasks.tasks.fullView` [Google IAM](https://cloud.google.com/iam/) permission on the Task resource. */
		responseView: Option[Schema.RunTaskRequest.ResponseViewEnum] = None
	)
	
	case class CmekConfig(
	  /** Output only. The config resource name which includes the project and location and must end in 'cmekConfig', in the format projects/PROJECT_ID/locations/LOCATION_ID/cmekConfig` */
		name: Option[String] = None,
	  /** Resource name of the Cloud KMS key, of the form `projects/PROJECT_ID/locations/LOCATION_ID/keyRings/KEY_RING_ID/cryptoKeys/KEY_ID`, that will be used to encrypt the Queues & Tasks in the region. Setting this as blank will turn off CMEK encryption. */
		kmsKey: Option[String] = None
	)
	
	case class BufferTaskRequest(
	  /** Optional. Body of the HTTP request. The body can take any generic value. The value is written to the HttpRequest of the [Task]. */
		body: Option[Schema.HttpBody] = None
	)
	
	case class HttpBody(
	  /** The HTTP Content-Type header value specifying the content type of the body. */
		contentType: Option[String] = None,
	  /** The HTTP request/response body as raw binary. */
		data: Option[String] = None,
	  /** Application specific response metadata. Must be set in the first response for streaming APIs. */
		extensions: Option[List[Map[String, JsValue]]] = None
	)
	
	case class BufferTaskResponse(
	  /** The created task. */
		task: Option[Schema.Task] = None
	)
}
