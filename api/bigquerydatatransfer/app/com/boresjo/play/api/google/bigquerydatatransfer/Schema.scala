package com.boresjo.play.api.google.bigquerydatatransfer

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object DataSource {
		enum TransferTypeEnum extends Enum[TransferTypeEnum] { case TRANSFER_TYPE_UNSPECIFIED, BATCH, STREAMING }
		enum AuthorizationTypeEnum extends Enum[AuthorizationTypeEnum] { case AUTHORIZATION_TYPE_UNSPECIFIED, AUTHORIZATION_CODE, GOOGLE_PLUS_AUTHORIZATION_CODE, FIRST_PARTY_OAUTH }
		enum DataRefreshTypeEnum extends Enum[DataRefreshTypeEnum] { case DATA_REFRESH_TYPE_UNSPECIFIED, SLIDING_WINDOW, CUSTOM_SLIDING_WINDOW }
	}
	case class DataSource(
	  /** Output only. Data source resource name. */
		name: Option[String] = None,
	  /** Data source id. */
		dataSourceId: Option[String] = None,
	  /** User friendly data source name. */
		displayName: Option[String] = None,
	  /** User friendly data source description string. */
		description: Option[String] = None,
	  /** Data source client id which should be used to receive refresh token. */
		clientId: Option[String] = None,
	  /** Api auth scopes for which refresh token needs to be obtained. These are scopes needed by a data source to prepare data and ingest them into BigQuery, e.g., https://www.googleapis.com/auth/bigquery */
		scopes: Option[List[String]] = None,
	  /** Deprecated. This field has no effect. */
		transferType: Option[Schema.DataSource.TransferTypeEnum] = None,
	  /** Deprecated. This field has no effect. */
		supportsMultipleTransfers: Option[Boolean] = None,
	  /** The number of seconds to wait for an update from the data source before the Data Transfer Service marks the transfer as FAILED. */
		updateDeadlineSeconds: Option[Int] = None,
	  /** Default data transfer schedule. Examples of valid schedules include: `1st,3rd monday of month 15:30`, `every wed,fri of jan,jun 13:15`, and `first sunday of quarter 00:00`. */
		defaultSchedule: Option[String] = None,
	  /** Specifies whether the data source supports a user defined schedule, or operates on the default schedule. When set to `true`, user can override default schedule. */
		supportsCustomSchedule: Option[Boolean] = None,
	  /** Data source parameters. */
		parameters: Option[List[Schema.DataSourceParameter]] = None,
	  /** Url for the help document for this data source. */
		helpUrl: Option[String] = None,
	  /** Indicates the type of authorization. */
		authorizationType: Option[Schema.DataSource.AuthorizationTypeEnum] = None,
	  /** Specifies whether the data source supports automatic data refresh for the past few days, and how it's supported. For some data sources, data might not be complete until a few days later, so it's useful to refresh data automatically. */
		dataRefreshType: Option[Schema.DataSource.DataRefreshTypeEnum] = None,
	  /** Default data refresh window on days. Only meaningful when `data_refresh_type` = `SLIDING_WINDOW`. */
		defaultDataRefreshWindowDays: Option[Int] = None,
	  /** Disables backfilling and manual run scheduling for the data source. */
		manualRunsDisabled: Option[Boolean] = None,
	  /** The minimum interval for scheduler to schedule runs. */
		minimumScheduleInterval: Option[String] = None
	)
	
	object DataSourceParameter {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, STRING, INTEGER, DOUBLE, BOOLEAN, RECORD, PLUS_PAGE, LIST }
	}
	case class DataSourceParameter(
	  /** Parameter identifier. */
		paramId: Option[String] = None,
	  /** Parameter display name in the user interface. */
		displayName: Option[String] = None,
	  /** Parameter description. */
		description: Option[String] = None,
	  /** Parameter type. */
		`type`: Option[Schema.DataSourceParameter.TypeEnum] = None,
	  /** Is parameter required. */
		required: Option[Boolean] = None,
	  /** Deprecated. This field has no effect. */
		repeated: Option[Boolean] = None,
	  /** Regular expression which can be used for parameter validation. */
		validationRegex: Option[String] = None,
	  /** All possible values for the parameter. */
		allowedValues: Option[List[String]] = None,
	  /** For integer and double values specifies minimum allowed value. */
		minValue: Option[BigDecimal] = None,
	  /** For integer and double values specifies maximum allowed value. */
		maxValue: Option[BigDecimal] = None,
	  /** Deprecated. This field has no effect. */
		fields: Option[List[Schema.DataSourceParameter]] = None,
	  /** Description of the requirements for this field, in case the user input does not fulfill the regex pattern or min/max values. */
		validationDescription: Option[String] = None,
	  /** URL to a help document to further explain the naming requirements. */
		validationHelpUrl: Option[String] = None,
	  /** Cannot be changed after initial creation. */
		immutable: Option[Boolean] = None,
	  /** Deprecated. This field has no effect. */
		recurse: Option[Boolean] = None,
	  /** If true, it should not be used in new transfers, and it should not be visible to users. */
		deprecated: Option[Boolean] = None
	)
	
	case class ListDataSourcesResponse(
	  /** List of supported data sources and their transfer settings. */
		dataSources: Option[List[Schema.DataSource]] = None,
	  /** Output only. The next-pagination token. For multiple-page list results, this token can be used as the `ListDataSourcesRequest.page_token` to request the next page of list results. */
		nextPageToken: Option[String] = None
	)
	
	object TransferConfig {
		enum StateEnum extends Enum[StateEnum] { case TRANSFER_STATE_UNSPECIFIED, PENDING, RUNNING, SUCCEEDED, FAILED, CANCELLED }
	}
	case class TransferConfig(
	  /** Identifier. The resource name of the transfer config. Transfer config names have the form either `projects/{project_id}/locations/{region}/transferConfigs/{config_id}` or `projects/{project_id}/transferConfigs/{config_id}`, where `config_id` is usually a UUID, even though it is not guaranteed or required. The name is ignored when creating a transfer config. */
		name: Option[String] = None,
	  /** The BigQuery target dataset id. */
		destinationDatasetId: Option[String] = None,
	  /** User specified display name for the data transfer. */
		displayName: Option[String] = None,
	  /** Data source ID. This cannot be changed once data transfer is created. The full list of available data source IDs can be returned through an API call: https://cloud.google.com/bigquery-transfer/docs/reference/datatransfer/rest/v1/projects.locations.dataSources/list */
		dataSourceId: Option[String] = None,
	  /** Parameters specific to each data source. For more information see the bq tab in the 'Setting up a data transfer' section for each data source. For example the parameters for Cloud Storage transfers are listed here: https://cloud.google.com/bigquery-transfer/docs/cloud-storage-transfer#bq */
		params: Option[Map[String, JsValue]] = None,
	  /** Data transfer schedule. If the data source does not support a custom schedule, this should be empty. If it is empty, the default value for the data source will be used. The specified times are in UTC. Examples of valid format: `1st,3rd monday of month 15:30`, `every wed,fri of jan,jun 13:15`, and `first sunday of quarter 00:00`. See more explanation about the format here: https://cloud.google.com/appengine/docs/flexible/python/scheduling-jobs-with-cron-yaml#the_schedule_format NOTE: The minimum interval time between recurring transfers depends on the data source; refer to the documentation for your data source. */
		schedule: Option[String] = None,
	  /** Options customizing the data transfer schedule. */
		scheduleOptions: Option[Schema.ScheduleOptions] = None,
	  /** Options customizing different types of data transfer schedule. This field replaces "schedule" and "schedule_options" fields. ScheduleOptionsV2 cannot be used together with ScheduleOptions/Schedule. */
		scheduleOptionsV2: Option[Schema.ScheduleOptionsV2] = None,
	  /** The number of days to look back to automatically refresh the data. For example, if `data_refresh_window_days = 10`, then every day BigQuery reingests data for [today-10, today-1], rather than ingesting data for just [today-1]. Only valid if the data source supports the feature. Set the value to 0 to use the default value. */
		dataRefreshWindowDays: Option[Int] = None,
	  /** Is this config disabled. When set to true, no runs will be scheduled for this transfer config. */
		disabled: Option[Boolean] = None,
	  /** Output only. Data transfer modification time. Ignored by server on input. */
		updateTime: Option[String] = None,
	  /** Output only. Next time when data transfer will run. */
		nextRunTime: Option[String] = None,
	  /** Output only. State of the most recently updated transfer run. */
		state: Option[Schema.TransferConfig.StateEnum] = None,
	  /** Deprecated. Unique ID of the user on whose behalf transfer is done. */
		userId: Option[String] = None,
	  /** Output only. Region in which BigQuery dataset is located. */
		datasetRegion: Option[String] = None,
	  /** Pub/Sub topic where notifications will be sent after transfer runs associated with this transfer config finish. The format for specifying a pubsub topic is: `projects/{project_id}/topics/{topic_id}` */
		notificationPubsubTopic: Option[String] = None,
	  /** Email notifications will be sent according to these preferences to the email address of the user who owns this transfer config. */
		emailPreferences: Option[Schema.EmailPreferences] = None,
	  /** Output only. Information about the user whose credentials are used to transfer data. Populated only for `transferConfigs.get` requests. In case the user information is not available, this field will not be populated. */
		ownerInfo: Option[Schema.UserInfo] = None,
	  /** The encryption configuration part. Currently, it is only used for the optional KMS key name. The BigQuery service account of your project must be granted permissions to use the key. Read methods will return the key name applied in effect. Write methods will apply the key if it is present, or otherwise try to apply project default keys if it is absent. */
		encryptionConfiguration: Option[Schema.EncryptionConfiguration] = None,
	  /** Output only. Error code with detailed information about reason of the latest config failure. */
		error: Option[Schema.Status] = None
	)
	
	case class ScheduleOptions(
	  /** If true, automatic scheduling of data transfer runs for this configuration will be disabled. The runs can be started on ad-hoc basis using StartManualTransferRuns API. When automatic scheduling is disabled, the TransferConfig.schedule field will be ignored. */
		disableAutoScheduling: Option[Boolean] = None,
	  /** Specifies time to start scheduling transfer runs. The first run will be scheduled at or after the start time according to a recurrence pattern defined in the schedule string. The start time can be changed at any moment. The time when a data transfer can be triggered manually is not limited by this option. */
		startTime: Option[String] = None,
	  /** Defines time to stop scheduling transfer runs. A transfer run cannot be scheduled at or after the end time. The end time can be changed at any moment. The time when a data transfer can be triggered manually is not limited by this option. */
		endTime: Option[String] = None
	)
	
	case class ScheduleOptionsV2(
	  /** Time based transfer schedule options. This is the default schedule option. */
		timeBasedSchedule: Option[Schema.TimeBasedSchedule] = None,
	  /** Manual transfer schedule. If set, the transfer run will not be auto-scheduled by the system, unless the client invokes StartManualTransferRuns. This is equivalent to disable_auto_scheduling = true. */
		manualSchedule: Option[Schema.ManualSchedule] = None,
	  /** Event driven transfer schedule options. If set, the transfer will be scheduled upon events arrial. */
		eventDrivenSchedule: Option[Schema.EventDrivenSchedule] = None
	)
	
	case class TimeBasedSchedule(
	  /** Data transfer schedule. If the data source does not support a custom schedule, this should be empty. If it is empty, the default value for the data source will be used. The specified times are in UTC. Examples of valid format: `1st,3rd monday of month 15:30`, `every wed,fri of jan,jun 13:15`, and `first sunday of quarter 00:00`. See more explanation about the format here: https://cloud.google.com/appengine/docs/flexible/python/scheduling-jobs-with-cron-yaml#the_schedule_format NOTE: The minimum interval time between recurring transfers depends on the data source; refer to the documentation for your data source. */
		schedule: Option[String] = None,
	  /** Specifies time to start scheduling transfer runs. The first run will be scheduled at or after the start time according to a recurrence pattern defined in the schedule string. The start time can be changed at any moment. */
		startTime: Option[String] = None,
	  /** Defines time to stop scheduling transfer runs. A transfer run cannot be scheduled at or after the end time. The end time can be changed at any moment. */
		endTime: Option[String] = None
	)
	
	case class ManualSchedule(
	
	)
	
	case class EventDrivenSchedule(
	  /** Pub/Sub subscription name used to receive events. Only Google Cloud Storage data source support this option. Format: projects/{project}/subscriptions/{subscription} */
		pubsubSubscription: Option[String] = None
	)
	
	case class EmailPreferences(
	  /** If true, email notifications will be sent on transfer run failures. */
		enableFailureEmail: Option[Boolean] = None
	)
	
	case class UserInfo(
	  /** E-mail address of the user. */
		email: Option[String] = None
	)
	
	case class EncryptionConfiguration(
	  /** The name of the KMS key used for encrypting BigQuery data. */
		kmsKeyName: Option[String] = None
	)
	
	case class Status(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class Empty(
	
	)
	
	case class ListTransferConfigsResponse(
	  /** Output only. The stored pipeline transfer configurations. */
		transferConfigs: Option[List[Schema.TransferConfig]] = None,
	  /** Output only. The next-pagination token. For multiple-page list results, this token can be used as the `ListTransferConfigsRequest.page_token` to request the next page of list results. */
		nextPageToken: Option[String] = None
	)
	
	case class ScheduleTransferRunsRequest(
	  /** Required. Start time of the range of transfer runs. For example, `"2017-05-25T00:00:00+00:00"`. */
		startTime: Option[String] = None,
	  /** Required. End time of the range of transfer runs. For example, `"2017-05-30T00:00:00+00:00"`. */
		endTime: Option[String] = None
	)
	
	case class ScheduleTransferRunsResponse(
	  /** The transfer runs that were scheduled. */
		runs: Option[List[Schema.TransferRun]] = None
	)
	
	object TransferRun {
		enum StateEnum extends Enum[StateEnum] { case TRANSFER_STATE_UNSPECIFIED, PENDING, RUNNING, SUCCEEDED, FAILED, CANCELLED }
	}
	case class TransferRun(
	  /** Identifier. The resource name of the transfer run. Transfer run names have the form `projects/{project_id}/locations/{location}/transferConfigs/{config_id}/runs/{run_id}`. The name is ignored when creating a transfer run. */
		name: Option[String] = None,
	  /** Minimum time after which a transfer run can be started. */
		scheduleTime: Option[String] = None,
	  /** For batch transfer runs, specifies the date and time of the data should be ingested. */
		runTime: Option[String] = None,
	  /** Status of the transfer run. */
		errorStatus: Option[Schema.Status] = None,
	  /** Output only. Time when transfer run was started. Parameter ignored by server for input requests. */
		startTime: Option[String] = None,
	  /** Output only. Time when transfer run ended. Parameter ignored by server for input requests. */
		endTime: Option[String] = None,
	  /** Output only. Last time the data transfer run state was updated. */
		updateTime: Option[String] = None,
	  /** Output only. Parameters specific to each data source. For more information see the bq tab in the 'Setting up a data transfer' section for each data source. For example the parameters for Cloud Storage transfers are listed here: https://cloud.google.com/bigquery-transfer/docs/cloud-storage-transfer#bq */
		params: Option[Map[String, JsValue]] = None,
	  /** Output only. The BigQuery target dataset id. */
		destinationDatasetId: Option[String] = None,
	  /** Output only. Data source id. */
		dataSourceId: Option[String] = None,
	  /** Data transfer run state. Ignored for input requests. */
		state: Option[Schema.TransferRun.StateEnum] = None,
	  /** Deprecated. Unique ID of the user on whose behalf transfer is done. */
		userId: Option[String] = None,
	  /** Output only. Describes the schedule of this transfer run if it was created as part of a regular schedule. For batch transfer runs that are scheduled manually, this is empty. NOTE: the system might choose to delay the schedule depending on the current load, so `schedule_time` doesn't always match this. */
		schedule: Option[String] = None,
	  /** Output only. Pub/Sub topic where a notification will be sent after this transfer run finishes. The format for specifying a pubsub topic is: `projects/{project_id}/topics/{topic_id}` */
		notificationPubsubTopic: Option[String] = None,
	  /** Output only. Email notifications will be sent according to these preferences to the email address of the user who owns the transfer config this run was derived from. */
		emailPreferences: Option[Schema.EmailPreferences] = None
	)
	
	case class StartManualTransferRunsRequest(
	  /** A time_range start and end timestamp for historical data files or reports that are scheduled to be transferred by the scheduled transfer run. requested_time_range must be a past time and cannot include future time values. */
		requestedTimeRange: Option[Schema.TimeRange] = None,
	  /** A run_time timestamp for historical data files or reports that are scheduled to be transferred by the scheduled transfer run. requested_run_time must be a past time and cannot include future time values. */
		requestedRunTime: Option[String] = None
	)
	
	case class TimeRange(
	  /** Start time of the range of transfer runs. For example, `"2017-05-25T00:00:00+00:00"`. The start_time must be strictly less than the end_time. Creates transfer runs where run_time is in the range between start_time (inclusive) and end_time (exclusive). */
		startTime: Option[String] = None,
	  /** End time of the range of transfer runs. For example, `"2017-05-30T00:00:00+00:00"`. The end_time must not be in the future. Creates transfer runs where run_time is in the range between start_time (inclusive) and end_time (exclusive). */
		endTime: Option[String] = None
	)
	
	case class StartManualTransferRunsResponse(
	  /** The transfer runs that were created. */
		runs: Option[List[Schema.TransferRun]] = None
	)
	
	case class ListTransferRunsResponse(
	  /** Output only. The stored pipeline transfer runs. */
		transferRuns: Option[List[Schema.TransferRun]] = None,
	  /** Output only. The next-pagination token. For multiple-page list results, this token can be used as the `ListTransferRunsRequest.page_token` to request the next page of list results. */
		nextPageToken: Option[String] = None
	)
	
	case class ListTransferLogsResponse(
	  /** Output only. The stored pipeline transfer messages. */
		transferMessages: Option[List[Schema.TransferMessage]] = None,
	  /** Output only. The next-pagination token. For multiple-page list results, this token can be used as the `GetTransferRunLogRequest.page_token` to request the next page of list results. */
		nextPageToken: Option[String] = None
	)
	
	object TransferMessage {
		enum SeverityEnum extends Enum[SeverityEnum] { case MESSAGE_SEVERITY_UNSPECIFIED, INFO, WARNING, ERROR }
	}
	case class TransferMessage(
	  /** Time when message was logged. */
		messageTime: Option[String] = None,
	  /** Message severity. */
		severity: Option[Schema.TransferMessage.SeverityEnum] = None,
	  /** Message text. */
		messageText: Option[String] = None
	)
	
	case class CheckValidCredsRequest(
	
	)
	
	case class CheckValidCredsResponse(
	  /** If set to `true`, the credentials exist and are valid. */
		hasValidCreds: Option[Boolean] = None
	)
	
	case class EnrollDataSourcesRequest(
	  /** Data sources that are enrolled. It is required to provide at least one data source id. */
		dataSourceIds: Option[List[String]] = None
	)
	
	case class UnenrollDataSourcesRequest(
	  /** Data sources that are unenrolled. It is required to provide at least one data source id. */
		dataSourceIds: Option[List[String]] = None
	)
	
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
}
