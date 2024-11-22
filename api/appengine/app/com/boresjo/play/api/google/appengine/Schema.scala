package com.boresjo.play.api.google.appengine

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ListOperationsResponse(
	  /** A list of operations that matches the specified filter in the request. */
		operations: Option[List[Schema.Operation]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class Operation(
	  /** The server-assigned name, which is only unique within the same service that originally returns it. If you use the default HTTP mapping, the name should be a resource name ending with operations/{unique_id}. */
		name: Option[String] = None,
	  /** Service-specific metadata associated with the operation. It typically contains progress information and common metadata such as create time. Some services might not provide such metadata. Any method that returns a long-running operation should document the metadata type, if any. */
		metadata: Option[Map[String, JsValue]] = None,
	  /** If the value is false, it means the operation is still in progress. If true, the operation is completed, and either error or response is available. */
		done: Option[Boolean] = None,
	  /** The error result of the operation in case of failure or cancellation. */
		error: Option[Schema.Status] = None,
	  /** The normal, successful response of the operation. If the original method returns no data on success, such as Delete, the response is google.protobuf.Empty. If the original method is standard Get/Create/Update, the response should be the resource. For other methods, the response should have the type XxxResponse, where Xxx is the original method name. For example, if the original method name is TakeSnapshot(), the inferred response type is TakeSnapshotResponse. */
		response: Option[Map[String, JsValue]] = None
	)
	
	case class Status(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	object Application {
		enum ServingStatusEnum extends Enum[ServingStatusEnum] { case UNSPECIFIED, SERVING, USER_DISABLED, SYSTEM_DISABLED }
		enum DatabaseTypeEnum extends Enum[DatabaseTypeEnum] { case DATABASE_TYPE_UNSPECIFIED, CLOUD_DATASTORE, CLOUD_FIRESTORE, CLOUD_DATASTORE_COMPATIBILITY }
	}
	case class Application(
	  /** Output only. Full path to the Application resource in the API. Example: apps/myapp.@OutputOnly */
		name: Option[String] = None,
	  /** Identifier of the Application resource. This identifier is equivalent to the project ID of the Google Cloud Platform project where you want to deploy your application. Example: myapp. */
		id: Option[String] = None,
	  /** HTTP path dispatch rules for requests to the application that do not explicitly target a service or version. Rules are order-dependent. Up to 20 dispatch rules can be supported. */
		dispatchRules: Option[List[Schema.UrlDispatchRule]] = None,
	  /** Google Apps authentication domain that controls which users can access this application.Defaults to open access for any Google Account. */
		authDomain: Option[String] = None,
	  /** Location from which this application runs. Application instances run out of the data centers in the specified location, which is also where all of the application's end user content is stored.Defaults to us-central.View the list of supported locations (https://cloud.google.com/appengine/docs/locations). */
		locationId: Option[String] = None,
	  /** Output only. Google Cloud Storage bucket that can be used for storing files associated with this application. This bucket is associated with the application and can be used by the gcloud deployment commands.@OutputOnly */
		codeBucket: Option[String] = None,
	  /** Cookie expiration policy for this application. */
		defaultCookieExpiration: Option[String] = None,
	  /** Serving status of this application. */
		servingStatus: Option[Schema.Application.ServingStatusEnum] = None,
	  /** Output only. Hostname used to reach this application, as resolved by App Engine.@OutputOnly */
		defaultHostname: Option[String] = None,
	  /** Output only. Google Cloud Storage bucket that can be used by this application to store content.@OutputOnly */
		defaultBucket: Option[String] = None,
	  /** The service account associated with the application. This is the app-level default identity. If no identity provided during create version, Admin API will fallback to this one. */
		serviceAccount: Option[String] = None,
		iap: Option[Schema.IdentityAwareProxy] = None,
	  /** Output only. The Google Container Registry domain used for storing managed build docker images for this application. */
		gcrDomain: Option[String] = None,
	  /** The type of the Cloud Firestore or Cloud Datastore database associated with this application. */
		databaseType: Option[Schema.Application.DatabaseTypeEnum] = None,
	  /** The feature specific settings to be used in the application. */
		featureSettings: Option[Schema.FeatureSettings] = None,
	  /** Additional Google Generated Customer Metadata, this field won't be provided by default and can be requested by setting the IncludeExtraData field in GetApplicationRequest */
		generatedCustomerMetadata: Option[Map[String, JsValue]] = None
	)
	
	case class UrlDispatchRule(
	  /** Domain name to match against. The wildcard "&#42;" is supported if specified before a period: "&#42;.".Defaults to matching all domains: "&#42;". */
		domain: Option[String] = None,
	  /** Pathname within the host. Must start with a "/". A single "&#42;" can be included at the end of the path.The sum of the lengths of the domain and path may not exceed 100 characters. */
		path: Option[String] = None,
	  /** Resource ID of a service in this application that should serve the matched request. The service must already exist. Example: default. */
		service: Option[String] = None
	)
	
	case class IdentityAwareProxy(
	  /** Whether the serving infrastructure will authenticate and authorize all incoming requests.If true, the oauth2_client_id and oauth2_client_secret fields must be non-empty. */
		enabled: Option[Boolean] = None,
	  /** OAuth2 client ID to use for the authentication flow. */
		oauth2ClientId: Option[String] = None,
	  /** OAuth2 client secret to use for the authentication flow.For security reasons, this value cannot be retrieved via the API. Instead, the SHA-256 hash of the value is returned in the oauth2_client_secret_sha256 field.@InputOnly */
		oauth2ClientSecret: Option[String] = None,
	  /** Output only. Hex-encoded SHA-256 hash of the client secret.@OutputOnly */
		oauth2ClientSecretSha256: Option[String] = None
	)
	
	case class FeatureSettings(
	  /** Boolean value indicating if split health checks should be used instead of the legacy health checks. At an app.yaml level, this means defaulting to 'readiness_check' and 'liveness_check' values instead of 'health_check' ones. Once the legacy 'health_check' behavior is deprecated, and this value is always true, this setting can be removed. */
		splitHealthChecks: Option[Boolean] = None,
	  /** If true, use Container-Optimized OS (https://cloud.google.com/container-optimized-os/) base image for VMs, rather than a base Debian image. */
		useContainerOptimizedOs: Option[Boolean] = None
	)
	
	case class RepairApplicationRequest(
	
	)
	
	case class ListRuntimesResponse(
	  /** The runtimes available to the requested application. */
		runtimes: Option[List[Schema.Runtime]] = None,
	  /** Continuation token for fetching the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	object Runtime {
		enum EnvironmentEnum extends Enum[EnvironmentEnum] { case ENVIRONMENT_UNSPECIFIED, STANDARD, FLEXIBLE }
		enum StageEnum extends Enum[StageEnum] { case RUNTIME_STAGE_UNSPECIFIED, DEVELOPMENT, ALPHA, BETA, GA, DEPRECATED, DECOMMISSIONED, END_OF_SUPPORT }
	}
	case class Runtime(
	  /** The name of the runtime, e.g., 'go113', 'nodejs12', etc. */
		name: Option[String] = None,
	  /** User-friendly display name, e.g. 'Node.js 12', etc. */
		displayName: Option[String] = None,
	  /** The environment of the runtime. */
		environment: Option[Schema.Runtime.EnvironmentEnum] = None,
	  /** The stage of life this runtime is in, e.g., BETA, GA, etc. */
		stage: Option[Schema.Runtime.StageEnum] = None,
	  /** Warning messages, e.g., a deprecation warning. */
		warnings: Option[List[String]] = None,
	  /** Supported operating systems for the runtime, e.g., 'ubuntu22', etc. */
		supportedOperatingSystems: Option[List[String]] = None,
	  /** Date when Runtime is end of support. */
		endOfSupportDate: Option[Schema.Date] = None,
	  /** Date when Runtime is deprecated. */
		deprecationDate: Option[Schema.Date] = None,
	  /** Date when Runtime is decommissioned. */
		decommissionedDate: Option[Schema.Date] = None
	)
	
	case class Date(
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None,
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None,
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None
	)
	
	case class ListServicesResponse(
	  /** The services belonging to the requested application. */
		services: Option[List[Schema.Service]] = None,
	  /** Continuation token for fetching the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class Service(
	  /** Output only. Full path to the Service resource in the API. Example: apps/myapp/services/default.@OutputOnly */
		name: Option[String] = None,
	  /** Output only. Relative name of the service within the application. Example: default.@OutputOnly */
		id: Option[String] = None,
	  /** Mapping that defines fractional HTTP traffic diversion to different versions within the service. */
		split: Option[Schema.TrafficSplit] = None,
	  /** A set of labels to apply to this service. Labels are key/value pairs that describe the service and all resources that belong to it (e.g., versions). The labels can be used to search and group resources, and are propagated to the usage and billing reports, enabling fine-grain analysis of costs. An example of using labels is to tag resources belonging to different environments (e.g., "env=prod", "env=qa"). Label keys and values can be no longer than 63 characters and can only contain lowercase letters, numeric characters, underscores, dashes, and international characters. Label keys must start with a lowercase letter or an international character. Each service can have at most 32 labels. */
		labels: Option[Map[String, String]] = None,
	  /** Ingress settings for this service. Will apply to all versions. */
		networkSettings: Option[Schema.NetworkSettings] = None,
	  /** Additional Google Generated Customer Metadata, this field won't be provided by default and can be requested by setting the IncludeExtraData field in GetServiceRequest */
		generatedCustomerMetadata: Option[Map[String, JsValue]] = None
	)
	
	object TrafficSplit {
		enum ShardByEnum extends Enum[ShardByEnum] { case UNSPECIFIED, COOKIE, IP, RANDOM }
	}
	case class TrafficSplit(
	  /** Mechanism used to determine which version a request is sent to. The traffic selection algorithm will be stable for either type until allocations are changed. */
		shardBy: Option[Schema.TrafficSplit.ShardByEnum] = None,
	  /** Mapping from version IDs within the service to fractional (0.000, 1] allocations of traffic for that version. Each version can be specified only once, but some versions in the service may not have any traffic allocation. Services that have traffic allocated cannot be deleted until either the service is deleted or their traffic allocation is removed. Allocations must sum to 1. Up to two decimal place precision is supported for IP-based splits and up to three decimal places is supported for cookie-based splits. */
		allocations: Option[Map[String, BigDecimal]] = None
	)
	
	object NetworkSettings {
		enum IngressTrafficAllowedEnum extends Enum[IngressTrafficAllowedEnum] { case INGRESS_TRAFFIC_ALLOWED_UNSPECIFIED, INGRESS_TRAFFIC_ALLOWED_ALL, INGRESS_TRAFFIC_ALLOWED_INTERNAL_ONLY, INGRESS_TRAFFIC_ALLOWED_INTERNAL_AND_LB }
	}
	case class NetworkSettings(
	  /** The ingress settings for version or service. */
		ingressTrafficAllowed: Option[Schema.NetworkSettings.IngressTrafficAllowedEnum] = None
	)
	
	case class ListVersionsResponse(
	  /** The versions belonging to the requested service. */
		versions: Option[List[Schema.Version]] = None,
	  /** Continuation token for fetching the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	object Version {
		enum InboundServicesEnum extends Enum[InboundServicesEnum] { case INBOUND_SERVICE_UNSPECIFIED, INBOUND_SERVICE_MAIL, INBOUND_SERVICE_MAIL_BOUNCE, INBOUND_SERVICE_XMPP_ERROR, INBOUND_SERVICE_XMPP_MESSAGE, INBOUND_SERVICE_XMPP_SUBSCRIBE, INBOUND_SERVICE_XMPP_PRESENCE, INBOUND_SERVICE_CHANNEL_PRESENCE, INBOUND_SERVICE_WARMUP }
		enum ServingStatusEnum extends Enum[ServingStatusEnum] { case SERVING_STATUS_UNSPECIFIED, SERVING, STOPPED }
	}
	case class Version(
	  /** Output only. Full path to the Version resource in the API. Example: apps/myapp/services/default/versions/v1.@OutputOnly */
		name: Option[String] = None,
	  /** Relative name of the version within the service. Example: v1. Version names can contain only lowercase letters, numbers, or hyphens. Reserved names: "default", "latest", and any name with the prefix "ah-". */
		id: Option[String] = None,
	  /** Automatic scaling is based on request rate, response latencies, and other application metrics. Instances are dynamically created and destroyed as needed in order to handle traffic. */
		automaticScaling: Option[Schema.AutomaticScaling] = None,
	  /** A service with basic scaling will create an instance when the application receives a request. The instance will be turned down when the app becomes idle. Basic scaling is ideal for work that is intermittent or driven by user activity. */
		basicScaling: Option[Schema.BasicScaling] = None,
	  /** A service with manual scaling runs continuously, allowing you to perform complex initialization and rely on the state of its memory over time. Manually scaled versions are sometimes referred to as "backends". */
		manualScaling: Option[Schema.ManualScaling] = None,
	  /** Before an application can receive email or XMPP messages, the application must be configured to enable the service. */
		inboundServices: Option[List[Schema.Version.InboundServicesEnum]] = None,
	  /** Instance class that is used to run this version. Valid values are: AutomaticScaling: F1, F2, F4, F4_1G ManualScaling or BasicScaling: B1, B2, B4, B8, B4_1GDefaults to F1 for AutomaticScaling and B1 for ManualScaling or BasicScaling. */
		instanceClass: Option[String] = None,
	  /** Extra network settings. Only applicable in the App Engine flexible environment. */
		network: Option[Schema.Network] = None,
	  /** The Google Compute Engine zones that are supported by this version in the App Engine flexible environment. Deprecated. */
		zones: Option[List[String]] = None,
	  /** Machine resources for this version. Only applicable in the App Engine flexible environment. */
		resources: Option[Schema.Resources] = None,
	  /** Desired runtime. Example: python27. */
		runtime: Option[String] = None,
	  /** The channel of the runtime to use. Only available for some runtimes. Defaults to the default channel. */
		runtimeChannel: Option[String] = None,
	  /** Whether multiple requests can be dispatched to this version at once. */
		threadsafe: Option[Boolean] = None,
	  /** Whether to deploy this version in a container on a virtual machine. */
		vm: Option[Boolean] = None,
	  /** Settings for App Engine flexible runtimes. */
		flexibleRuntimeSettings: Option[Schema.FlexibleRuntimeSettings] = None,
	  /** Allows App Engine second generation runtimes to access the legacy bundled services. */
		appEngineApis: Option[Boolean] = None,
	  /** Metadata settings that are supplied to this version to enable beta runtime features. */
		betaSettings: Option[Map[String, String]] = None,
	  /** App Engine execution environment for this version.Defaults to standard. */
		env: Option[String] = None,
	  /** Current serving status of this version. Only the versions with a SERVING status create instances and can be billed.SERVING_STATUS_UNSPECIFIED is an invalid value. Defaults to SERVING. */
		servingStatus: Option[Schema.Version.ServingStatusEnum] = None,
	  /** Output only. Email address of the user who created this version.@OutputOnly */
		createdBy: Option[String] = None,
	  /** Time that this version was created.@OutputOnly */
		createTime: Option[String] = None,
	  /** Output only. Total size in bytes of all the files that are included in this version and currently hosted on the App Engine disk.@OutputOnly */
		diskUsageBytes: Option[String] = None,
	  /** The version of the API in the given runtime environment. Please see the app.yaml reference for valid values at https://cloud.google.com/appengine/docs/standard//config/appref */
		runtimeApiVersion: Option[String] = None,
	  /** The path or name of the app's main executable. */
		runtimeMainExecutablePath: Option[String] = None,
	  /** The identity that the deployed version will run as. Admin API will use the App Engine Appspot service account as default if this field is neither provided in app.yaml file nor through CLI flag. */
		serviceAccount: Option[String] = None,
	  /** An ordered list of URL-matching patterns that should be applied to incoming requests. The first matching URL handles the request and other request handlers are not attempted.Only returned in GET requests if view=FULL is set. */
		handlers: Option[List[Schema.UrlMap]] = None,
	  /** Custom static error pages. Limited to 10KB per page.Only returned in GET requests if view=FULL is set. */
		errorHandlers: Option[List[Schema.ErrorHandler]] = None,
	  /** Configuration for third-party Python runtime libraries that are required by the application.Only returned in GET requests if view=FULL is set. */
		libraries: Option[List[Schema.Library]] = None,
	  /** Serving configuration for Google Cloud Endpoints (https://cloud.google.com/endpoints).Only returned in GET requests if view=FULL is set. */
		apiConfig: Option[Schema.ApiConfigHandler] = None,
	  /** Environment variables available to the application.Only returned in GET requests if view=FULL is set. */
		envVariables: Option[Map[String, String]] = None,
	  /** Environment variables available to the build environment.Only returned in GET requests if view=FULL is set. */
		buildEnvVariables: Option[Map[String, String]] = None,
	  /** Duration that static files should be cached by web proxies and browsers. Only applicable if the corresponding StaticFilesHandler (https://cloud.google.com/appengine/docs/admin-api/reference/rest/v1/apps.services.versions#StaticFilesHandler) does not specify its own expiration time.Only returned in GET requests if view=FULL is set. */
		defaultExpiration: Option[String] = None,
	  /** Configures health checking for instances. Unhealthy instances are stopped and replaced with new instances. Only applicable in the App Engine flexible environment. */
		healthCheck: Option[Schema.HealthCheck] = None,
	  /** Configures readiness health checking for instances. Unhealthy instances are not put into the backend traffic rotation. */
		readinessCheck: Option[Schema.ReadinessCheck] = None,
	  /** Configures liveness health checking for instances. Unhealthy instances are stopped and replaced with new instances */
		livenessCheck: Option[Schema.LivenessCheck] = None,
	  /** Files that match this pattern will not be built into this version. Only applicable for Go runtimes.Only returned in GET requests if view=FULL is set. */
		nobuildFilesRegex: Option[String] = None,
	  /** Code and application artifacts that make up this version.Only returned in GET requests if view=FULL is set. */
		deployment: Option[Schema.Deployment] = None,
	  /** Output only. Serving URL for this version. Example: "https://myversion-dot-myservice-dot-myapp.appspot.com"@OutputOnly */
		versionUrl: Option[String] = None,
	  /** Cloud Endpoints configuration.If endpoints_api_service is set, the Cloud Endpoints Extensible Service Proxy will be provided to serve the API implemented by the app. */
		endpointsApiService: Option[Schema.EndpointsApiService] = None,
	  /** The entrypoint for the application. */
		entrypoint: Option[Schema.Entrypoint] = None,
	  /** Enables VPC connectivity for standard apps. */
		vpcAccessConnector: Option[Schema.VpcAccessConnector] = None,
	  /** Additional Google Generated Customer Metadata, this field won't be provided by default and can be requested by setting the IncludeExtraData field in GetVersionRequest */
		generatedCustomerMetadata: Option[Map[String, JsValue]] = None
	)
	
	case class AutomaticScaling(
	  /** The time period that the Autoscaler (https://cloud.google.com/compute/docs/autoscaler/) should wait before it starts collecting information from a new instance. This prevents the autoscaler from collecting information when the instance is initializing, during which the collected usage would not be reliable. Only applicable in the App Engine flexible environment. */
		coolDownPeriod: Option[String] = None,
	  /** Target scaling by CPU usage. */
		cpuUtilization: Option[Schema.CpuUtilization] = None,
	  /** Number of concurrent requests an automatic scaling instance can accept before the scheduler spawns a new instance.Defaults to a runtime-specific value. */
		maxConcurrentRequests: Option[Int] = None,
	  /** Maximum number of idle instances that should be maintained for this version. */
		maxIdleInstances: Option[Int] = None,
	  /** Maximum number of instances that should be started to handle requests for this version. */
		maxTotalInstances: Option[Int] = None,
	  /** Maximum amount of time that a request should wait in the pending queue before starting a new instance to handle it. */
		maxPendingLatency: Option[String] = None,
	  /** Minimum number of idle instances that should be maintained for this version. Only applicable for the default version of a service. */
		minIdleInstances: Option[Int] = None,
	  /** Minimum number of running instances that should be maintained for this version. */
		minTotalInstances: Option[Int] = None,
	  /** Minimum amount of time a request should wait in the pending queue before starting a new instance to handle it. */
		minPendingLatency: Option[String] = None,
	  /** Target scaling by request utilization. */
		requestUtilization: Option[Schema.RequestUtilization] = None,
	  /** Target scaling by disk usage. */
		diskUtilization: Option[Schema.DiskUtilization] = None,
	  /** Target scaling by network usage. */
		networkUtilization: Option[Schema.NetworkUtilization] = None,
	  /** Scheduler settings for standard environment. */
		standardSchedulerSettings: Option[Schema.StandardSchedulerSettings] = None
	)
	
	case class CpuUtilization(
	  /** Period of time over which CPU utilization is calculated. */
		aggregationWindowLength: Option[String] = None,
	  /** Target CPU utilization ratio to maintain when scaling. Must be between 0 and 1. */
		targetUtilization: Option[BigDecimal] = None
	)
	
	case class RequestUtilization(
	  /** Target requests per second. */
		targetRequestCountPerSecond: Option[Int] = None,
	  /** Target number of concurrent requests. */
		targetConcurrentRequests: Option[Int] = None
	)
	
	case class DiskUtilization(
	  /** Target bytes written per second. */
		targetWriteBytesPerSecond: Option[Int] = None,
	  /** Target ops written per second. */
		targetWriteOpsPerSecond: Option[Int] = None,
	  /** Target bytes read per second. */
		targetReadBytesPerSecond: Option[Int] = None,
	  /** Target ops read per seconds. */
		targetReadOpsPerSecond: Option[Int] = None
	)
	
	case class NetworkUtilization(
	  /** Target bytes sent per second. */
		targetSentBytesPerSecond: Option[Int] = None,
	  /** Target packets sent per second. */
		targetSentPacketsPerSecond: Option[Int] = None,
	  /** Target bytes received per second. */
		targetReceivedBytesPerSecond: Option[Int] = None,
	  /** Target packets received per second. */
		targetReceivedPacketsPerSecond: Option[Int] = None
	)
	
	case class StandardSchedulerSettings(
	  /** Target CPU utilization ratio to maintain when scaling. */
		targetCpuUtilization: Option[BigDecimal] = None,
	  /** Target throughput utilization ratio to maintain when scaling */
		targetThroughputUtilization: Option[BigDecimal] = None,
	  /** Minimum number of instances to run for this version. Set to zero to disable min_instances configuration. */
		minInstances: Option[Int] = None,
	  /** Maximum number of instances to run for this version. Set to zero to disable max_instances configuration. */
		maxInstances: Option[Int] = None
	)
	
	case class BasicScaling(
	  /** Duration of time after the last request that an instance must wait before the instance is shut down. */
		idleTimeout: Option[String] = None,
	  /** Maximum number of instances to create for this version. */
		maxInstances: Option[Int] = None
	)
	
	case class ManualScaling(
	  /** Number of instances to assign to the service at the start. This number can later be altered by using the Modules API (https://cloud.google.com/appengine/docs/python/modules/functions) set_num_instances() function. */
		instances: Option[Int] = None
	)
	
	object Network {
		enum InstanceIpModeEnum extends Enum[InstanceIpModeEnum] { case INSTANCE_IP_MODE_UNSPECIFIED, EXTERNAL, INTERNAL }
	}
	case class Network(
	  /** List of ports, or port pairs, to forward from the virtual machine to the application container. Only applicable in the App Engine flexible environment. */
		forwardedPorts: Option[List[String]] = None,
	  /** Tag to apply to the instance during creation. Only applicable in the App Engine flexible environment. */
		instanceTag: Option[String] = None,
	  /** Google Compute Engine network where the virtual machines are created. Specify the short name, not the resource path.Defaults to default. */
		name: Option[String] = None,
	  /** Google Cloud Platform sub-network where the virtual machines are created. Specify the short name, not the resource path.If a subnetwork name is specified, a network name will also be required unless it is for the default network. If the network that the instance is being created in is a Legacy network, then the IP address is allocated from the IPv4Range. If the network that the instance is being created in is an auto Subnet Mode Network, then only network name should be specified (not the subnetwork_name) and the IP address is created from the IPCidrRange of the subnetwork that exists in that zone for that network. If the network that the instance is being created in is a custom Subnet Mode Network, then the subnetwork_name must be specified and the IP address is created from the IPCidrRange of the subnetwork.If specified, the subnetwork must exist in the same region as the App Engine flexible environment application. */
		subnetworkName: Option[String] = None,
	  /** Enable session affinity. Only applicable in the App Engine flexible environment. */
		sessionAffinity: Option[Boolean] = None,
	  /** The IP mode for instances. Only applicable in the App Engine flexible environment. */
		instanceIpMode: Option[Schema.Network.InstanceIpModeEnum] = None
	)
	
	case class Resources(
	  /** Number of CPU cores needed. */
		cpu: Option[BigDecimal] = None,
	  /** Disk size (GB) needed. */
		diskGb: Option[BigDecimal] = None,
	  /** Memory (GB) needed. */
		memoryGb: Option[BigDecimal] = None,
	  /** User specified volumes. */
		volumes: Option[List[Schema.Volume]] = None,
	  /** The name of the encryption key that is stored in Google Cloud KMS. Only should be used by Cloud Composer to encrypt the vm disk */
		kmsKeyReference: Option[String] = None
	)
	
	case class Volume(
	  /** Unique name for the volume. */
		name: Option[String] = None,
	  /** Underlying volume type, e.g. 'tmpfs'. */
		volumeType: Option[String] = None,
	  /** Volume size in gigabytes. */
		sizeGb: Option[BigDecimal] = None
	)
	
	case class FlexibleRuntimeSettings(
	  /** The operating system of the application runtime. */
		operatingSystem: Option[String] = None,
	  /** The runtime version of an App Engine flexible application. */
		runtimeVersion: Option[String] = None
	)
	
	object UrlMap {
		enum SecurityLevelEnum extends Enum[SecurityLevelEnum] { case SECURE_UNSPECIFIED, SECURE_DEFAULT, SECURE_NEVER, SECURE_OPTIONAL, SECURE_ALWAYS }
		enum LoginEnum extends Enum[LoginEnum] { case LOGIN_UNSPECIFIED, LOGIN_OPTIONAL, LOGIN_ADMIN, LOGIN_REQUIRED }
		enum AuthFailActionEnum extends Enum[AuthFailActionEnum] { case AUTH_FAIL_ACTION_UNSPECIFIED, AUTH_FAIL_ACTION_REDIRECT, AUTH_FAIL_ACTION_UNAUTHORIZED }
		enum RedirectHttpResponseCodeEnum extends Enum[RedirectHttpResponseCodeEnum] { case REDIRECT_HTTP_RESPONSE_CODE_UNSPECIFIED, REDIRECT_HTTP_RESPONSE_CODE_301, REDIRECT_HTTP_RESPONSE_CODE_302, REDIRECT_HTTP_RESPONSE_CODE_303, REDIRECT_HTTP_RESPONSE_CODE_307 }
	}
	case class UrlMap(
	  /** URL prefix. Uses regular expression syntax, which means regexp special characters must be escaped, but should not contain groupings. All URLs that begin with this prefix are handled by this handler, using the portion of the URL after the prefix as part of the file path. */
		urlRegex: Option[String] = None,
	  /** Returns the contents of a file, such as an image, as the response. */
		staticFiles: Option[Schema.StaticFilesHandler] = None,
	  /** Executes a script to handle the requests that match this URL pattern. Only the auto value is supported for Node.js in the App Engine standard environment, for example "script": "auto". */
		script: Option[Schema.ScriptHandler] = None,
	  /** Uses API Endpoints to handle requests. */
		apiEndpoint: Option[Schema.ApiEndpointHandler] = None,
	  /** Security (HTTPS) enforcement for this URL. */
		securityLevel: Option[Schema.UrlMap.SecurityLevelEnum] = None,
	  /** Level of login required to access this resource. Not supported for Node.js in the App Engine standard environment. */
		login: Option[Schema.UrlMap.LoginEnum] = None,
	  /** Action to take when users access resources that require authentication. Defaults to redirect. */
		authFailAction: Option[Schema.UrlMap.AuthFailActionEnum] = None,
	  /** 30x code to use when performing redirects for the secure field. Defaults to 302. */
		redirectHttpResponseCode: Option[Schema.UrlMap.RedirectHttpResponseCodeEnum] = None
	)
	
	case class StaticFilesHandler(
	  /** Path to the static files matched by the URL pattern, from the application root directory. The path can refer to text matched in groupings in the URL pattern. */
		path: Option[String] = None,
	  /** Regular expression that matches the file paths for all files that should be referenced by this handler. */
		uploadPathRegex: Option[String] = None,
	  /** HTTP headers to use for all responses from these URLs. */
		httpHeaders: Option[Map[String, String]] = None,
	  /** MIME type used to serve all files served by this handler.Defaults to file-specific MIME types, which are derived from each file's filename extension. */
		mimeType: Option[String] = None,
	  /** Time a static file served by this handler should be cached by web proxies and browsers. */
		expiration: Option[String] = None,
	  /** Whether this handler should match the request if the file referenced by the handler does not exist. */
		requireMatchingFile: Option[Boolean] = None,
	  /** Whether files should also be uploaded as code data. By default, files declared in static file handlers are uploaded as static data and are only served to end users; they cannot be read by the application. If enabled, uploads are charged against both your code and static data storage resource quotas. */
		applicationReadable: Option[Boolean] = None
	)
	
	case class ScriptHandler(
	  /** Path to the script from the application root directory. */
		scriptPath: Option[String] = None
	)
	
	case class ApiEndpointHandler(
	  /** Path to the script from the application root directory. */
		scriptPath: Option[String] = None
	)
	
	object ErrorHandler {
		enum ErrorCodeEnum extends Enum[ErrorCodeEnum] { case ERROR_CODE_UNSPECIFIED, ERROR_CODE_DEFAULT, ERROR_CODE_OVER_QUOTA, ERROR_CODE_DOS_API_DENIAL, ERROR_CODE_TIMEOUT }
	}
	case class ErrorHandler(
	  /** Error condition this handler applies to. */
		errorCode: Option[Schema.ErrorHandler.ErrorCodeEnum] = None,
	  /** Static file content to be served for this error. */
		staticFile: Option[String] = None,
	  /** MIME type of file. Defaults to text/html. */
		mimeType: Option[String] = None
	)
	
	case class Library(
	  /** Name of the library. Example: "django". */
		name: Option[String] = None,
	  /** Version of the library to select, or "latest". */
		version: Option[String] = None
	)
	
	object ApiConfigHandler {
		enum AuthFailActionEnum extends Enum[AuthFailActionEnum] { case AUTH_FAIL_ACTION_UNSPECIFIED, AUTH_FAIL_ACTION_REDIRECT, AUTH_FAIL_ACTION_UNAUTHORIZED }
		enum LoginEnum extends Enum[LoginEnum] { case LOGIN_UNSPECIFIED, LOGIN_OPTIONAL, LOGIN_ADMIN, LOGIN_REQUIRED }
		enum SecurityLevelEnum extends Enum[SecurityLevelEnum] { case SECURE_UNSPECIFIED, SECURE_DEFAULT, SECURE_NEVER, SECURE_OPTIONAL, SECURE_ALWAYS }
	}
	case class ApiConfigHandler(
	  /** Action to take when users access resources that require authentication. Defaults to redirect. */
		authFailAction: Option[Schema.ApiConfigHandler.AuthFailActionEnum] = None,
	  /** Level of login required to access this resource. Defaults to optional. */
		login: Option[Schema.ApiConfigHandler.LoginEnum] = None,
	  /** Path to the script from the application root directory. */
		script: Option[String] = None,
	  /** Security (HTTPS) enforcement for this URL. */
		securityLevel: Option[Schema.ApiConfigHandler.SecurityLevelEnum] = None,
	  /** URL to serve the endpoint at. */
		url: Option[String] = None
	)
	
	case class HealthCheck(
	  /** Whether to explicitly disable health checks for this instance. */
		disableHealthCheck: Option[Boolean] = None,
	  /** Host header to send when performing an HTTP health check. Example: "myapp.appspot.com" */
		host: Option[String] = None,
	  /** Number of consecutive successful health checks required before receiving traffic. */
		healthyThreshold: Option[Int] = None,
	  /** Number of consecutive failed health checks required before removing traffic. */
		unhealthyThreshold: Option[Int] = None,
	  /** Number of consecutive failed health checks required before an instance is restarted. */
		restartThreshold: Option[Int] = None,
	  /** Interval between health checks. */
		checkInterval: Option[String] = None,
	  /** Time before the health check is considered failed. */
		timeout: Option[String] = None
	)
	
	case class ReadinessCheck(
	  /** The request path. */
		path: Option[String] = None,
	  /** Host header to send when performing a HTTP Readiness check. Example: "myapp.appspot.com" */
		host: Option[String] = None,
	  /** Number of consecutive failed checks required before removing traffic. */
		failureThreshold: Option[Int] = None,
	  /** Number of consecutive successful checks required before receiving traffic. */
		successThreshold: Option[Int] = None,
	  /** Interval between health checks. */
		checkInterval: Option[String] = None,
	  /** Time before the check is considered failed. */
		timeout: Option[String] = None,
	  /** A maximum time limit on application initialization, measured from moment the application successfully replies to a healthcheck until it is ready to serve traffic. */
		appStartTimeout: Option[String] = None
	)
	
	case class LivenessCheck(
	  /** The request path. */
		path: Option[String] = None,
	  /** Host header to send when performing a HTTP Liveness check. Example: "myapp.appspot.com" */
		host: Option[String] = None,
	  /** Number of consecutive failed checks required before considering the VM unhealthy. */
		failureThreshold: Option[Int] = None,
	  /** Number of consecutive successful checks required before considering the VM healthy. */
		successThreshold: Option[Int] = None,
	  /** Interval between health checks. */
		checkInterval: Option[String] = None,
	  /** Time before the check is considered failed. */
		timeout: Option[String] = None,
	  /** The initial delay before starting to execute the checks. */
		initialDelay: Option[String] = None
	)
	
	case class Deployment(
	  /** Manifest of the files stored in Google Cloud Storage that are included as part of this version. All files must be readable using the credentials supplied with this call. */
		files: Option[Map[String, Schema.FileInfo]] = None,
	  /** The Docker image for the container that runs the version. Only applicable for instances running in the App Engine flexible environment. */
		container: Option[Schema.ContainerInfo] = None,
	  /** The zip file for this deployment, if this is a zip deployment. */
		zip: Option[Schema.ZipInfo] = None,
	  /** Options for any Google Cloud Build builds created as a part of this deployment.These options will only be used if a new build is created, such as when deploying to the App Engine flexible environment using files or zip. */
		cloudBuildOptions: Option[Schema.CloudBuildOptions] = None
	)
	
	case class FileInfo(
	  /** URL source to use to fetch this file. Must be a URL to a resource in Google Cloud Storage in the form 'http(s)://storage.googleapis.com//'. */
		sourceUrl: Option[String] = None,
	  /** The SHA1 hash of the file, in hex. */
		sha1Sum: Option[String] = None,
	  /** The MIME type of the file.Defaults to the value from Google Cloud Storage. */
		mimeType: Option[String] = None
	)
	
	case class ContainerInfo(
	  /** URI to the hosted container image in Google Container Registry. The URI must be fully qualified and include a tag or digest. Examples: "gcr.io/my-project/image:tag" or "gcr.io/my-project/image@digest" */
		image: Option[String] = None
	)
	
	case class ZipInfo(
	  /** URL of the zip file to deploy from. Must be a URL to a resource in Google Cloud Storage in the form 'http(s)://storage.googleapis.com//'. */
		sourceUrl: Option[String] = None,
	  /** An estimate of the number of files in a zip for a zip deployment. If set, must be greater than or equal to the actual number of files. Used for optimizing performance; if not provided, deployment may be slow. */
		filesCount: Option[Int] = None
	)
	
	case class CloudBuildOptions(
	  /** Path to the yaml file used in deployment, used to determine runtime configuration details.Required for flexible environment builds.See https://cloud.google.com/appengine/docs/standard/python/config/appref for more details. */
		appYamlPath: Option[String] = None,
	  /** The Cloud Build timeout used as part of any dependent builds performed by version creation. Defaults to 10 minutes. */
		cloudBuildTimeout: Option[String] = None
	)
	
	object EndpointsApiService {
		enum RolloutStrategyEnum extends Enum[RolloutStrategyEnum] { case UNSPECIFIED_ROLLOUT_STRATEGY, FIXED, MANAGED }
	}
	case class EndpointsApiService(
	  /** Endpoints service name which is the name of the "service" resource in the Service Management API. For example "myapi.endpoints.myproject.cloud.goog" */
		name: Option[String] = None,
	  /** Endpoints service configuration ID as specified by the Service Management API. For example "2016-09-19r1".By default, the rollout strategy for Endpoints is RolloutStrategy.FIXED. This means that Endpoints starts up with a particular configuration ID. When a new configuration is rolled out, Endpoints must be given the new configuration ID. The config_id field is used to give the configuration ID and is required in this case.Endpoints also has a rollout strategy called RolloutStrategy.MANAGED. When using this, Endpoints fetches the latest configuration and does not need the configuration ID. In this case, config_id must be omitted. */
		configId: Option[String] = None,
	  /** Endpoints rollout strategy. If FIXED, config_id must be specified. If MANAGED, config_id must be omitted. */
		rolloutStrategy: Option[Schema.EndpointsApiService.RolloutStrategyEnum] = None,
	  /** Enable or disable trace sampling. By default, this is set to false for enabled. */
		disableTraceSampling: Option[Boolean] = None
	)
	
	case class Entrypoint(
	  /** The format should be a shell command that can be fed to bash -c. */
		shell: Option[String] = None
	)
	
	object VpcAccessConnector {
		enum EgressSettingEnum extends Enum[EgressSettingEnum] { case EGRESS_SETTING_UNSPECIFIED, ALL_TRAFFIC, PRIVATE_IP_RANGES }
	}
	case class VpcAccessConnector(
	  /** Full Serverless VPC Access Connector name e.g. projects/my-project/locations/us-central1/connectors/c1. */
		name: Option[String] = None,
	  /** The egress setting for the connector, controlling what traffic is diverted through it. */
		egressSetting: Option[Schema.VpcAccessConnector.EgressSettingEnum] = None
	)
	
	case class ListInstancesResponse(
	  /** The instances belonging to the requested version. */
		instances: Option[List[Schema.Instance]] = None,
	  /** Continuation token for fetching the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	object Instance {
		enum AvailabilityEnum extends Enum[AvailabilityEnum] { case UNSPECIFIED, RESIDENT, DYNAMIC }
		enum VmLivenessEnum extends Enum[VmLivenessEnum] { case LIVENESS_STATE_UNSPECIFIED, UNKNOWN, HEALTHY, UNHEALTHY, DRAINING, TIMEOUT }
	}
	case class Instance(
	  /** Output only. Full path to the Instance resource in the API. Example: apps/myapp/services/default/versions/v1/instances/instance-1. */
		name: Option[String] = None,
	  /** Output only. Relative name of the instance within the version. Example: instance-1. */
		id: Option[String] = None,
	  /** Output only. App Engine release this instance is running on. */
		appEngineRelease: Option[String] = None,
	  /** Output only. Availability of the instance. */
		availability: Option[Schema.Instance.AvailabilityEnum] = None,
	  /** Output only. Name of the virtual machine where this instance lives. Only applicable for instances in App Engine flexible environment. */
		vmName: Option[String] = None,
	  /** Output only. Zone where the virtual machine is located. Only applicable for instances in App Engine flexible environment. */
		vmZoneName: Option[String] = None,
	  /** Output only. Virtual machine ID of this instance. Only applicable for instances in App Engine flexible environment. */
		vmId: Option[String] = None,
	  /** Output only. Time that this instance was started.@OutputOnly */
		startTime: Option[String] = None,
	  /** Output only. Number of requests since this instance was started. */
		requests: Option[Int] = None,
	  /** Output only. Number of errors since this instance was started. */
		errors: Option[Int] = None,
	  /** Output only. Average queries per second (QPS) over the last minute. */
		qps: Option[BigDecimal] = None,
	  /** Output only. Average latency (ms) over the last minute. */
		averageLatency: Option[Int] = None,
	  /** Output only. Total memory in use (bytes). */
		memoryUsage: Option[String] = None,
	  /** Output only. Status of the virtual machine where this instance lives. Only applicable for instances in App Engine flexible environment. */
		vmStatus: Option[String] = None,
	  /** Output only. Whether this instance is in debug mode. Only applicable for instances in App Engine flexible environment. */
		vmDebugEnabled: Option[Boolean] = None,
	  /** Output only. The IP address of this instance. Only applicable for instances in App Engine flexible environment. */
		vmIp: Option[String] = None,
	  /** Output only. The liveness health check of this instance. Only applicable for instances in App Engine flexible environment. */
		vmLiveness: Option[Schema.Instance.VmLivenessEnum] = None
	)
	
	case class DebugInstanceRequest(
	  /** Public SSH key to add to the instance. Examples: [USERNAME]:ssh-rsa [KEY_VALUE] [USERNAME] [USERNAME]:ssh-rsa [KEY_VALUE] google-ssh {"userName":"[USERNAME]","expireOn":"[EXPIRE_TIME]"}For more information, see Adding and Removing SSH Keys (https://cloud.google.com/compute/docs/instances/adding-removing-ssh-keys). */
		sshKey: Option[String] = None
	)
	
	case class ListIngressRulesResponse(
	  /** The ingress FirewallRules for this application. */
		ingressRules: Option[List[Schema.FirewallRule]] = None,
	  /** Continuation token for fetching the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	object FirewallRule {
		enum ActionEnum extends Enum[ActionEnum] { case UNSPECIFIED_ACTION, ALLOW, DENY }
	}
	case class FirewallRule(
	  /** A positive integer between 1, Int32.MaxValue-1 that defines the order of rule evaluation. Rules with the lowest priority are evaluated first.A default rule at priority Int32.MaxValue matches all IPv4 and IPv6 traffic when no previous rule matches. Only the action of this rule can be modified by the user. */
		priority: Option[Int] = None,
	  /** The action to take on matched requests. */
		action: Option[Schema.FirewallRule.ActionEnum] = None,
	  /** IP address or range, defined using CIDR notation, of requests that this rule applies to. You can use the wildcard character "&#42;" to match all IPs equivalent to "0/0" and "::/0" together. Examples: 192.168.1.1 or 192.168.0.0/16 or 2001:db8::/32 or 2001:0db8:0000:0042:0000:8a2e:0370:7334. Truncation will be silently performed on addresses which are not properly truncated. For example, 1.2.3.4/24 is accepted as the same address as 1.2.3.0/24. Similarly, for IPv6, 2001:db8::1/32 is accepted as the same address as 2001:db8::/32. */
		sourceRange: Option[String] = None,
	  /** An optional string description of this rule. This field has a maximum length of 400 characters. */
		description: Option[String] = None
	)
	
	case class BatchUpdateIngressRulesRequest(
	  /** A list of FirewallRules to replace the existing set. */
		ingressRules: Option[List[Schema.FirewallRule]] = None
	)
	
	case class BatchUpdateIngressRulesResponse(
	  /** The full list of ingress FirewallRules for this application. */
		ingressRules: Option[List[Schema.FirewallRule]] = None
	)
	
	case class Empty(
	
	)
	
	case class ListAuthorizedDomainsResponse(
	  /** The authorized domains belonging to the user. */
		domains: Option[List[Schema.AuthorizedDomain]] = None,
	  /** Continuation token for fetching the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class AuthorizedDomain(
	  /** Full path to the AuthorizedDomain resource in the API. Example: apps/myapp/authorizedDomains/example.com.@OutputOnly */
		name: Option[String] = None,
	  /** Fully qualified domain name of the domain authorized for use. Example: example.com. */
		id: Option[String] = None
	)
	
	case class ListAuthorizedCertificatesResponse(
	  /** The SSL certificates the user is authorized to administer. */
		certificates: Option[List[Schema.AuthorizedCertificate]] = None,
	  /** Continuation token for fetching the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class AuthorizedCertificate(
	  /** Full path to the AuthorizedCertificate resource in the API. Example: apps/myapp/authorizedCertificates/12345.@OutputOnly */
		name: Option[String] = None,
	  /** Relative name of the certificate. This is a unique value autogenerated on AuthorizedCertificate resource creation. Example: 12345.@OutputOnly */
		id: Option[String] = None,
	  /** The user-specified display name of the certificate. This is not guaranteed to be unique. Example: My Certificate. */
		displayName: Option[String] = None,
	  /** Topmost applicable domains of this certificate. This certificate applies to these domains and their subdomains. Example: example.com.@OutputOnly */
		domainNames: Option[List[String]] = None,
	  /** The time when this certificate expires. To update the renewal time on this certificate, upload an SSL certificate with a different expiration time using AuthorizedCertificates.UpdateAuthorizedCertificate.@OutputOnly */
		expireTime: Option[String] = None,
	  /** The SSL certificate serving the AuthorizedCertificate resource. This must be obtained independently from a certificate authority. */
		certificateRawData: Option[Schema.CertificateRawData] = None,
	  /** Only applicable if this certificate is managed by App Engine. Managed certificates are tied to the lifecycle of a DomainMapping and cannot be updated or deleted via the AuthorizedCertificates API. If this certificate is manually administered by the user, this field will be empty.@OutputOnly */
		managedCertificate: Option[Schema.ManagedCertificate] = None,
	  /** The full paths to user visible Domain Mapping resources that have this certificate mapped. Example: apps/myapp/domainMappings/example.com.This may not represent the full list of mapped domain mappings if the user does not have VIEWER permissions on all of the applications that have this certificate mapped. See domain_mappings_count for a complete count.Only returned by GET or LIST requests when specifically requested by the view=FULL_CERTIFICATE option.@OutputOnly */
		visibleDomainMappings: Option[List[String]] = None,
	  /** Aggregate count of the domain mappings with this certificate mapped. This count includes domain mappings on applications for which the user does not have VIEWER permissions.Only returned by GET or LIST requests when specifically requested by the view=FULL_CERTIFICATE option.@OutputOnly */
		domainMappingsCount: Option[Int] = None
	)
	
	case class CertificateRawData(
	  /** PEM encoded x.509 public key certificate. This field is set once on certificate creation. Must include the header and footer. Example: -----BEGIN CERTIFICATE----- -----END CERTIFICATE-----  */
		publicCertificate: Option[String] = None,
	  /** Unencrypted PEM encoded RSA private key. This field is set once on certificate creation and then encrypted. The key size must be 2048 bits or fewer. Must include the header and footer. Example: -----BEGIN RSA PRIVATE KEY----- -----END RSA PRIVATE KEY----- @InputOnly */
		privateKey: Option[String] = None
	)
	
	object ManagedCertificate {
		enum StatusEnum extends Enum[StatusEnum] { case MANAGEMENT_STATUS_UNSPECIFIED, OK, PENDING, FAILED_RETRYING_NOT_VISIBLE, FAILED_PERMANENT, FAILED_RETRYING_CAA_FORBIDDEN, FAILED_RETRYING_CAA_CHECKING }
	}
	case class ManagedCertificate(
	  /** Time at which the certificate was last renewed. The renewal process is fully managed. Certificate renewal will automatically occur before the certificate expires. Renewal errors can be tracked via ManagementStatus.@OutputOnly */
		lastRenewalTime: Option[String] = None,
	  /** Status of certificate management. Refers to the most recent certificate acquisition or renewal attempt.@OutputOnly */
		status: Option[Schema.ManagedCertificate.StatusEnum] = None
	)
	
	case class ListDomainMappingsResponse(
	  /** The domain mappings for the application. */
		domainMappings: Option[List[Schema.DomainMapping]] = None,
	  /** Continuation token for fetching the next page of results. */
		nextPageToken: Option[String] = None
	)
	
	case class DomainMapping(
	  /** Full path to the DomainMapping resource in the API. Example: apps/myapp/domainMapping/example.com.@OutputOnly */
		name: Option[String] = None,
	  /** Relative name of the domain serving the application. Example: example.com. */
		id: Option[String] = None,
	  /** SSL configuration for this domain. If unconfigured, this domain will not serve with SSL. */
		sslSettings: Option[Schema.SslSettings] = None,
	  /** The resource records required to configure this domain mapping. These records must be added to the domain's DNS configuration in order to serve the application via this domain mapping.@OutputOnly */
		resourceRecords: Option[List[Schema.ResourceRecord]] = None
	)
	
	object SslSettings {
		enum SslManagementTypeEnum extends Enum[SslManagementTypeEnum] { case SSL_MANAGEMENT_TYPE_UNSPECIFIED, AUTOMATIC, MANUAL }
	}
	case class SslSettings(
	  /** ID of the AuthorizedCertificate resource configuring SSL for the application. Clearing this field will remove SSL support.By default, a managed certificate is automatically created for every domain mapping. To omit SSL support or to configure SSL manually, specify SslManagementType.MANUAL on a CREATE or UPDATE request. You must be authorized to administer the AuthorizedCertificate resource to manually map it to a DomainMapping resource. Example: 12345. */
		certificateId: Option[String] = None,
	  /** SSL management type for this domain. If AUTOMATIC, a managed certificate is automatically provisioned. If MANUAL, certificate_id must be manually specified in order to configure SSL for this domain. */
		sslManagementType: Option[Schema.SslSettings.SslManagementTypeEnum] = None,
	  /** ID of the managed AuthorizedCertificate resource currently being provisioned, if applicable. Until the new managed certificate has been successfully provisioned, the previous SSL state will be preserved. Once the provisioning process completes, the certificate_id field will reflect the new managed certificate and this field will be left empty. To remove SSL support while there is still a pending managed certificate, clear the certificate_id field with an UpdateDomainMappingRequest.@OutputOnly */
		pendingManagedCertificateId: Option[String] = None
	)
	
	object ResourceRecord {
		enum TypeEnum extends Enum[TypeEnum] { case RECORD_TYPE_UNSPECIFIED, A, AAAA, CNAME }
	}
	case class ResourceRecord(
	  /** Relative name of the object affected by this record. Only applicable for CNAME records. Example: 'www'. */
		name: Option[String] = None,
	  /** Data for this record. Values vary by record type, as defined in RFC 1035 (section 5) and RFC 1034 (section 3.6.1). */
		rrdata: Option[String] = None,
	  /** Resource record type. Example: AAAA. */
		`type`: Option[Schema.ResourceRecord.TypeEnum] = None
	)
	
	case class ListLocationsResponse(
	  /** A list of locations that matches the specified filter in the request. */
		locations: Option[List[Schema.Location]] = None,
	  /** The standard List next-page token. */
		nextPageToken: Option[String] = None
	)
	
	case class Location(
	  /** Resource name for the location, which may vary between implementations. For example: "projects/example-project/locations/us-east1" */
		name: Option[String] = None,
	  /** The canonical id for this location. For example: "us-east1". */
		locationId: Option[String] = None,
	  /** The friendly name for this location, typically a nearby city name. For example, "Tokyo". */
		displayName: Option[String] = None,
	  /** Cross-service attributes for the location. For example {"cloud.googleapis.com/region": "us-east1"}  */
		labels: Option[Map[String, String]] = None,
	  /** Service-specific metadata. For example the available capacity at the given location. */
		metadata: Option[Map[String, JsValue]] = None
	)
	
	case class LocationMetadata(
	  /** App Engine standard environment is available in the given location.@OutputOnly */
		standardEnvironmentAvailable: Option[Boolean] = None,
	  /** App Engine flexible environment is available in the given location.@OutputOnly */
		flexibleEnvironmentAvailable: Option[Boolean] = None,
	  /** Output only. Search API (https://cloud.google.com/appengine/docs/standard/python/search) is available in the given location. */
		searchApiAvailable: Option[Boolean] = None
	)
	
	case class OperationMetadataV1(
	  /** API method that initiated this operation. Example: google.appengine.v1.Versions.CreateVersion.@OutputOnly */
		method: Option[String] = None,
	  /** Time that this operation was created.@OutputOnly */
		insertTime: Option[String] = None,
	  /** Time that this operation completed.@OutputOnly */
		endTime: Option[String] = None,
	  /** User who requested this operation.@OutputOnly */
		user: Option[String] = None,
	  /** Name of the resource that this operation is acting on. Example: apps/myapp/services/default.@OutputOnly */
		target: Option[String] = None,
	  /** Ephemeral message that may change every time the operation is polled. @OutputOnly */
		ephemeralMessage: Option[String] = None,
	  /** Durable messages that persist on every operation poll. @OutputOnly */
		warning: Option[List[String]] = None,
		createVersionMetadata: Option[Schema.CreateVersionMetadataV1] = None
	)
	
	case class CreateVersionMetadataV1(
	  /** The Cloud Build ID if one was created as part of the version create. @OutputOnly */
		cloudBuildId: Option[String] = None
	)
	
	case class OperationMetadataV1Alpha(
	  /** API method that initiated this operation. Example: google.appengine.v1alpha.Versions.CreateVersion.@OutputOnly */
		method: Option[String] = None,
	  /** Time that this operation was created.@OutputOnly */
		insertTime: Option[String] = None,
	  /** Time that this operation completed.@OutputOnly */
		endTime: Option[String] = None,
	  /** User who requested this operation.@OutputOnly */
		user: Option[String] = None,
	  /** Name of the resource that this operation is acting on. Example: apps/myapp/services/default.@OutputOnly */
		target: Option[String] = None,
	  /** Ephemeral message that may change every time the operation is polled. @OutputOnly */
		ephemeralMessage: Option[String] = None,
	  /** Durable messages that persist on every operation poll. @OutputOnly */
		warning: Option[List[String]] = None,
		createVersionMetadata: Option[Schema.CreateVersionMetadataV1Alpha] = None
	)
	
	case class CreateVersionMetadataV1Alpha(
	  /** The Cloud Build ID if one was created as part of the version create. @OutputOnly */
		cloudBuildId: Option[String] = None
	)
	
	case class GoogleAppengineV1betaLocationMetadata(
	  /** App Engine standard environment is available in the given location.@OutputOnly */
		standardEnvironmentAvailable: Option[Boolean] = None,
	  /** App Engine flexible environment is available in the given location.@OutputOnly */
		flexibleEnvironmentAvailable: Option[Boolean] = None,
	  /** Output only. Search API (https://cloud.google.com/appengine/docs/standard/python/search) is available in the given location. */
		searchApiAvailable: Option[Boolean] = None
	)
	
	case class OperationMetadataV1Beta(
	  /** API method that initiated this operation. Example: google.appengine.v1beta.Versions.CreateVersion.@OutputOnly */
		method: Option[String] = None,
	  /** Time that this operation was created.@OutputOnly */
		insertTime: Option[String] = None,
	  /** Time that this operation completed.@OutputOnly */
		endTime: Option[String] = None,
	  /** User who requested this operation.@OutputOnly */
		user: Option[String] = None,
	  /** Name of the resource that this operation is acting on. Example: apps/myapp/services/default.@OutputOnly */
		target: Option[String] = None,
	  /** Ephemeral message that may change every time the operation is polled. @OutputOnly */
		ephemeralMessage: Option[String] = None,
	  /** Durable messages that persist on every operation poll. @OutputOnly */
		warning: Option[List[String]] = None,
		createVersionMetadata: Option[Schema.CreateVersionMetadataV1Beta] = None
	)
	
	case class CreateVersionMetadataV1Beta(
	  /** The Cloud Build ID if one was created as part of the version create. @OutputOnly */
		cloudBuildId: Option[String] = None
	)
	
	object ProjectEvent {
		enum PhaseEnum extends Enum[PhaseEnum] { case CONTAINER_EVENT_PHASE_UNSPECIFIED, BEFORE_RESOURCE_HANDLING, AFTER_RESOURCE_HANDLING }
	}
	case class ProjectEvent(
	  /** The unique ID for this project event. CLHs can use this value to dedup repeated calls. required */
		eventId: Option[String] = None,
	  /** The projects metadata for this project. required */
		projectMetadata: Option[Schema.ProjectsMetadata] = None,
	  /** Phase indicates when in the container event propagation this event is being communicated. Events are sent before and after the per-resource events are propagated. required */
		phase: Option[Schema.ProjectEvent.PhaseEnum] = None,
	  /** The state of the organization that led to this event. */
		state: Option[Schema.ContainerState] = None
	)
	
	object ProjectsMetadata {
		enum ConsumerProjectStateEnum extends Enum[ConsumerProjectStateEnum] { case UNKNOWN_STATE, ON, OFF, DELETED }
	}
	case class ProjectsMetadata(
	  /** The tenant project number. */
		tenantProjectNumber: Option[String] = None,
	  /** The tenant project id. */
		tenantProjectId: Option[String] = None,
	  /** The service account authorized to operate on the consumer project. Note: CCFE only propagates P4SA with default tag to CLH. */
		p4ServiceAccount: Option[String] = None,
	  /** The producer project id. */
		producerProjectId: Option[String] = None,
	  /** The producer project number. */
		producerProjectNumber: Option[String] = None,
	  /** The consumer project id. */
		consumerProjectId: Option[String] = None,
	  /** The consumer project number. */
		consumerProjectNumber: Option[String] = None,
	  /** The CCFE state of the consumer project. It is the same state that is communicated to the CLH during project events. Notice that this field is not set in the DB, it is only set in this proto when communicated to CLH in the side channel. */
		consumerProjectState: Option[Schema.ProjectsMetadata.ConsumerProjectStateEnum] = None,
	  /** The GCE tags associated with the consumer project and those inherited due to their ancestry, if any. Not supported by CCFE. */
		gceTag: Option[List[Schema.GceTag]] = None
	)
	
	case class GceTag(
	  /** The administrative_tag name. */
		tag: Option[String] = None,
	  /** The parents(s) of the tag. Eg. projects/123, folders/456 It usually contains only one parent. But, in some corner cases, it can contain multiple parents. Currently, organizations are not supported. */
		parent: Option[List[String]] = None
	)
	
	object ContainerState {
		enum StateEnum extends Enum[StateEnum] { case UNKNOWN_STATE, ON, OFF, DELETED }
	}
	case class ContainerState(
	  /** The current state of the container. This state is the culmination of all of the opinions from external systems that CCFE knows about of the container. */
		state: Option[Schema.ContainerState.StateEnum] = None,
	  /** The previous and current reasons for a container state will be sent for a container event. CLHs that need to know the signal that caused the container event to trigger (edges) as opposed to just knowing the state can act upon differences in the previous and current reasons.Reasons will be provided for every system: service management, data governance, abuse, and billing.If this is a CCFE-triggered event used for reconciliation then the current reasons will be set to their &#42;_CONTROL_PLANE_SYNC state. The previous reasons will contain the last known set of non-unknown non-control_plane_sync reasons for the state. */
		previousReasons: Option[Schema.Reasons] = None,
		currentReasons: Option[Schema.Reasons] = None
	)
	
	object Reasons {
		enum ServiceManagementEnum extends Enum[ServiceManagementEnum] { case SERVICE_MANAGEMENT_UNKNOWN_REASON, SERVICE_MANAGEMENT_CONTROL_PLANE_SYNC, ACTIVATION, PREPARE_DEACTIVATION, ABORT_DEACTIVATION, COMMIT_DEACTIVATION }
		enum DataGovernanceEnum extends Enum[DataGovernanceEnum] { case DATA_GOVERNANCE_UNKNOWN_REASON, DATA_GOVERNANCE_CONTROL_PLANE_SYNC, HIDE, UNHIDE, PURGE }
		enum AbuseEnum extends Enum[AbuseEnum] { case ABUSE_UNKNOWN_REASON, ABUSE_CONTROL_PLANE_SYNC, SUSPEND, REINSTATE }
		enum BillingEnum extends Enum[BillingEnum] { case BILLING_UNKNOWN_REASON, BILLING_CONTROL_PLANE_SYNC, PROBATION, CLOSE, OPEN }
		enum ServiceActivationEnum extends Enum[ServiceActivationEnum] { case SERVICE_ACTIVATION_STATUS_UNSPECIFIED, SERVICE_ACTIVATION_ENABLED, SERVICE_ACTIVATION_DISABLED, SERVICE_ACTIVATION_DISABLED_FULL, SERVICE_ACTIVATION_UNKNOWN_REASON }
	}
	case class Reasons(
		serviceManagement: Option[Schema.Reasons.ServiceManagementEnum] = None,
		dataGovernance: Option[Schema.Reasons.DataGovernanceEnum] = None,
		abuse: Option[Schema.Reasons.AbuseEnum] = None,
		billing: Option[Schema.Reasons.BillingEnum] = None,
	  /** Consumer Container denotes if the service is active within a project or not. This information could be used to clean up resources in case service in DISABLED_FULL i.e. Service is inactive > 30 days. */
		serviceActivation: Option[Schema.Reasons.ServiceActivationEnum] = None
	)
}
