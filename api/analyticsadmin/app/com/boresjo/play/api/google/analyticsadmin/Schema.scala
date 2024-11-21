package com.boresjo.play.api.google.analyticsadmin

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object GoogleAnalyticsAdminV1betaAccessNumericFilter {
		enum OperationEnum extends Enum[OperationEnum] { case OPERATION_UNSPECIFIED, EQUAL, LESS_THAN, LESS_THAN_OR_EQUAL, GREATER_THAN, GREATER_THAN_OR_EQUAL }
	}
	case class GoogleAnalyticsAdminV1betaAccessNumericFilter(
	  /** A numeric value or a date value. */
		value: Option[Schema.GoogleAnalyticsAdminV1betaNumericValue] = None,
	  /** The operation type for this filter. */
		operation: Option[Schema.GoogleAnalyticsAdminV1betaAccessNumericFilter.OperationEnum] = None
	)
	
	case class GoogleAnalyticsAdminV1betaAccessFilter(
	  /** A filter for two values. */
		betweenFilter: Option[Schema.GoogleAnalyticsAdminV1betaAccessBetweenFilter] = None,
	  /** A filter for numeric or date values. */
		numericFilter: Option[Schema.GoogleAnalyticsAdminV1betaAccessNumericFilter] = None,
	  /** A filter for in list values. */
		inListFilter: Option[Schema.GoogleAnalyticsAdminV1betaAccessInListFilter] = None,
	  /** Strings related filter. */
		stringFilter: Option[Schema.GoogleAnalyticsAdminV1betaAccessStringFilter] = None,
	  /** The dimension name or metric name. */
		fieldName: Option[String] = None
	)
	
	case class GoogleAnalyticsAdminV1betaProvisionAccountTicketRequest(
	  /** The account to create. */
		account: Option[Schema.GoogleAnalyticsAdminV1betaAccount] = None,
	  /** Redirect URI where the user will be sent after accepting Terms of Service. Must be configured in Cloud Console as a Redirect URI. */
		redirectUri: Option[String] = None
	)
	
	case class GoogleAnalyticsAdminV1betaAccessMetricHeader(
	  /** The metric's name; for example 'accessCount'. */
		metricName: Option[String] = None
	)
	
	object GoogleAnalyticsAdminV1betaAccessOrderByDimensionOrderBy {
		enum OrderTypeEnum extends Enum[OrderTypeEnum] { case ORDER_TYPE_UNSPECIFIED, ALPHANUMERIC, CASE_INSENSITIVE_ALPHANUMERIC, NUMERIC }
	}
	case class GoogleAnalyticsAdminV1betaAccessOrderByDimensionOrderBy(
	  /** Controls the rule for dimension value ordering. */
		orderType: Option[Schema.GoogleAnalyticsAdminV1betaAccessOrderByDimensionOrderBy.OrderTypeEnum] = None,
	  /** A dimension name in the request to order by. */
		dimensionName: Option[String] = None
	)
	
	case class GoogleAnalyticsAdminV1betaDataStreamWebStreamData(
	  /** Output only. ID of the corresponding web app in Firebase, if any. This ID can change if the web app is deleted and recreated. */
		firebaseAppId: Option[String] = None,
	  /** Domain name of the web app being measured, or empty. Example: "http://www.google.com", "https://www.google.com" */
		defaultUri: Option[String] = None,
	  /** Output only. Analytics Measurement ID. Example: "G-1A2BCD345E" */
		measurementId: Option[String] = None
	)
	
	case class GoogleProtobufEmpty(
	
	)
	
	case class GoogleAnalyticsAdminV1betaProvisionAccountTicketResponse(
	  /** The param to be passed in the ToS link. */
		accountTicketId: Option[String] = None
	)
	
	object GoogleAnalyticsAdminV1betaDataRetentionSettings {
		enum EventDataRetentionEnum extends Enum[EventDataRetentionEnum] { case RETENTION_DURATION_UNSPECIFIED, TWO_MONTHS, FOURTEEN_MONTHS, TWENTY_SIX_MONTHS, THIRTY_EIGHT_MONTHS, FIFTY_MONTHS }
	}
	case class GoogleAnalyticsAdminV1betaDataRetentionSettings(
	  /** The length of time that event-level data is retained. */
		eventDataRetention: Option[Schema.GoogleAnalyticsAdminV1betaDataRetentionSettings.EventDataRetentionEnum] = None,
	  /** Output only. Resource name for this DataRetentionSetting resource. Format: properties/{property}/dataRetentionSettings */
		name: Option[String] = None,
	  /** If true, reset the retention period for the user identifier with every event from that user. */
		resetUserDataOnNewActivity: Option[Boolean] = None
	)
	
	object GoogleAnalyticsAdminV1betaAccessStringFilter {
		enum MatchTypeEnum extends Enum[MatchTypeEnum] { case MATCH_TYPE_UNSPECIFIED, EXACT, BEGINS_WITH, ENDS_WITH, CONTAINS, FULL_REGEXP, PARTIAL_REGEXP }
	}
	case class GoogleAnalyticsAdminV1betaAccessStringFilter(
	  /** The match type for this filter. */
		matchType: Option[Schema.GoogleAnalyticsAdminV1betaAccessStringFilter.MatchTypeEnum] = None,
	  /** The string value used for the matching. */
		value: Option[String] = None,
	  /** If true, the string value is case sensitive. */
		caseSensitive: Option[Boolean] = None
	)
	
	case class GoogleAnalyticsAdminV1betaRunAccessReportResponse(
	  /** The header for a column in the report that corresponds to a specific metric. The number of MetricHeaders and ordering of MetricHeaders matches the metrics present in rows. */
		metricHeaders: Option[List[Schema.GoogleAnalyticsAdminV1betaAccessMetricHeader]] = None,
	  /** The header for a column in the report that corresponds to a specific dimension. The number of DimensionHeaders and ordering of DimensionHeaders matches the dimensions present in rows. */
		dimensionHeaders: Option[List[Schema.GoogleAnalyticsAdminV1betaAccessDimensionHeader]] = None,
	  /** The total number of rows in the query result. `rowCount` is independent of the number of rows returned in the response, the `limit` request parameter, and the `offset` request parameter. For example if a query returns 175 rows and includes `limit` of 50 in the API request, the response will contain `rowCount` of 175 but only 50 rows. To learn more about this pagination parameter, see [Pagination](https://developers.google.com/analytics/devguides/reporting/data/v1/basics#pagination). */
		rowCount: Option[Int] = None,
	  /** Rows of dimension value combinations and metric values in the report. */
		rows: Option[List[Schema.GoogleAnalyticsAdminV1betaAccessRow]] = None,
	  /** The quota state for this Analytics property including this request. This field doesn't work with account-level requests. */
		quota: Option[Schema.GoogleAnalyticsAdminV1betaAccessQuota] = None
	)
	
	case class GoogleAnalyticsAdminV1betaArchiveCustomDimensionRequest(
	
	)
	
	case class GoogleAnalyticsAdminV1betaAccessDimensionValue(
	  /** The dimension value. For example, this value may be 'France' for the 'country' dimension. */
		value: Option[String] = None
	)
	
	object GoogleAnalyticsAdminV1betaChangeHistoryEvent {
		enum ActorTypeEnum extends Enum[ActorTypeEnum] { case ACTOR_TYPE_UNSPECIFIED, USER, SYSTEM, SUPPORT }
	}
	case class GoogleAnalyticsAdminV1betaChangeHistoryEvent(
	  /** A list of changes made in this change history event that fit the filters specified in SearchChangeHistoryEventsRequest. */
		changes: Option[List[Schema.GoogleAnalyticsAdminV1betaChangeHistoryChange]] = None,
	  /** ID of this change history event. This ID is unique across Google Analytics. */
		id: Option[String] = None,
	  /** Time when change was made. */
		changeTime: Option[String] = None,
	  /** Email address of the Google account that made the change. This will be a valid email address if the actor field is set to USER, and empty otherwise. Google accounts that have been deleted will cause an error. */
		userActorEmail: Option[String] = None,
	  /** The type of actor that made this change. */
		actorType: Option[Schema.GoogleAnalyticsAdminV1betaChangeHistoryEvent.ActorTypeEnum] = None,
	  /** If true, then the list of changes returned was filtered, and does not represent all changes that occurred in this event. */
		changesFiltered: Option[Boolean] = None
	)
	
	case class GoogleAnalyticsAdminV1betaAccount(
	  /** Required. Human-readable display name for this account. */
		displayName: Option[String] = None,
	  /** Country of business. Must be a Unicode CLDR region code. */
		regionCode: Option[String] = None,
	  /** Output only. Resource name of this account. Format: accounts/{account} Example: "accounts/100" */
		name: Option[String] = None,
	  /** Output only. Indicates whether this Account is soft-deleted or not. Deleted accounts are excluded from List results unless specifically requested. */
		deleted: Option[Boolean] = None,
	  /** Output only. Time when account payload fields were last updated. */
		updateTime: Option[String] = None,
	  /** Output only. Time when this account was originally created. */
		createTime: Option[String] = None,
	  /** Output only. The URI for a Google Marketing Platform organization resource. Only set when this account is connected to a GMP organization. Format: marketingplatformadmin.googleapis.com/organizations/{org_id} */
		gmpOrganization: Option[String] = None
	)
	
	case class GoogleAnalyticsAdminV1betaMeasurementProtocolSecret(
	  /** Required. Human-readable display name for this secret. */
		displayName: Option[String] = None,
	  /** Output only. Resource name of this secret. This secret may be a child of any type of stream. Format: properties/{property}/dataStreams/{dataStream}/measurementProtocolSecrets/{measurementProtocolSecret} */
		name: Option[String] = None,
	  /** Output only. The measurement protocol secret value. Pass this value to the api_secret field of the Measurement Protocol API when sending hits to this secret's parent property. */
		secretValue: Option[String] = None
	)
	
	case class GoogleAnalyticsAdminV1betaSearchChangeHistoryEventsResponse(
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Results that were accessible to the caller. */
		changeHistoryEvents: Option[List[Schema.GoogleAnalyticsAdminV1betaChangeHistoryEvent]] = None
	)
	
	object GoogleAnalyticsAdminV1betaChangeHistoryChange {
		enum ActionEnum extends Enum[ActionEnum] { case ACTION_TYPE_UNSPECIFIED, CREATED, UPDATED, DELETED }
	}
	case class GoogleAnalyticsAdminV1betaChangeHistoryChange(
	  /** Resource contents from after the change was made. If this resource was deleted in this change, this field will be missing. */
		resourceAfterChange: Option[Schema.GoogleAnalyticsAdminV1betaChangeHistoryChangeChangeHistoryResource] = None,
	  /** The type of action that changed this resource. */
		action: Option[Schema.GoogleAnalyticsAdminV1betaChangeHistoryChange.ActionEnum] = None,
	  /** Resource contents from before the change was made. If this resource was created in this change, this field will be missing. */
		resourceBeforeChange: Option[Schema.GoogleAnalyticsAdminV1betaChangeHistoryChangeChangeHistoryResource] = None,
	  /** Resource name of the resource whose changes are described by this entry. */
		resource: Option[String] = None
	)
	
	case class GoogleAnalyticsAdminV1betaAccessDateRange(
	  /** The inclusive start date for the query in the format `YYYY-MM-DD`. Cannot be after `endDate`. The format `NdaysAgo`, `yesterday`, or `today` is also accepted, and in that case, the date is inferred based on the current time in the request's time zone. */
		startDate: Option[String] = None,
	  /** The inclusive end date for the query in the format `YYYY-MM-DD`. Cannot be before `startDate`. The format `NdaysAgo`, `yesterday`, or `today` is also accepted, and in that case, the date is inferred based on the current time in the request's time zone. */
		endDate: Option[String] = None
	)
	
	object GoogleAnalyticsAdminV1betaDataStream {
		enum TypeEnum extends Enum[TypeEnum] { case DATA_STREAM_TYPE_UNSPECIFIED, WEB_DATA_STREAM, ANDROID_APP_DATA_STREAM, IOS_APP_DATA_STREAM }
	}
	case class GoogleAnalyticsAdminV1betaDataStream(
	  /** Output only. Time when this stream was originally created. */
		createTime: Option[String] = None,
	  /** Data specific to web streams. Must be populated if type is WEB_DATA_STREAM. */
		webStreamData: Option[Schema.GoogleAnalyticsAdminV1betaDataStreamWebStreamData] = None,
	  /** Required. Immutable. The type of this DataStream resource. */
		`type`: Option[Schema.GoogleAnalyticsAdminV1betaDataStream.TypeEnum] = None,
	  /** Output only. Resource name of this Data Stream. Format: properties/{property_id}/dataStreams/{stream_id} Example: "properties/1000/dataStreams/2000" */
		name: Option[String] = None,
	  /** Human-readable display name for the Data Stream. Required for web data streams. The max allowed display name length is 255 UTF-16 code units. */
		displayName: Option[String] = None,
	  /** Data specific to Android app streams. Must be populated if type is ANDROID_APP_DATA_STREAM. */
		androidAppStreamData: Option[Schema.GoogleAnalyticsAdminV1betaDataStreamAndroidAppStreamData] = None,
	  /** Data specific to iOS app streams. Must be populated if type is IOS_APP_DATA_STREAM. */
		iosAppStreamData: Option[Schema.GoogleAnalyticsAdminV1betaDataStreamIosAppStreamData] = None,
	  /** Output only. Time when stream payload fields were last updated. */
		updateTime: Option[String] = None
	)
	
	case class GoogleAnalyticsAdminV1betaListConversionEventsResponse(
	  /** The requested conversion events */
		conversionEvents: Option[List[Schema.GoogleAnalyticsAdminV1betaConversionEvent]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleAnalyticsAdminV1betaAccessBetweenFilter(
	  /** Ends with this number. */
		toValue: Option[Schema.GoogleAnalyticsAdminV1betaNumericValue] = None,
	  /** Begins with this number. */
		fromValue: Option[Schema.GoogleAnalyticsAdminV1betaNumericValue] = None
	)
	
	case class GoogleAnalyticsAdminV1betaFirebaseLink(
	  /** Output only. Example format: properties/1234/firebaseLinks/5678 */
		name: Option[String] = None,
	  /** Output only. Time when this FirebaseLink was originally created. */
		createTime: Option[String] = None,
	  /** Immutable. Firebase project resource name. When creating a FirebaseLink, you may provide this resource name using either a project number or project ID. Once this resource has been created, returned FirebaseLinks will always have a project_name that contains a project number. Format: 'projects/{project number}' Example: 'projects/1234' */
		project: Option[String] = None
	)
	
	case class GoogleAnalyticsAdminV1betaArchiveCustomMetricRequest(
	
	)
	
	case class GoogleAnalyticsAdminV1betaRunAccessReportRequest(
	  /** Metric filters allow you to restrict report response to specific metric values which match the filter. Metric filters are applied after aggregating the report's rows, similar to SQL having-clause. Dimensions cannot be used in this filter. */
		metricFilter: Option[Schema.GoogleAnalyticsAdminV1betaAccessFilterExpression] = None,
	  /** Optional. Decides whether to return the users within user groups. This field works only when include_all_users is set to true. If true, it will return all users with access to the specified property or account. If false, only the users with direct access will be returned. */
		expandGroups: Option[Boolean] = None,
	  /** Dimension filters let you restrict report response to specific dimension values which match the filter. For example, filtering on access records of a single user. To learn more, see [Fundamentals of Dimension Filters](https://developers.google.com/analytics/devguides/reporting/data/v1/basics#dimension_filters) for examples. Metrics cannot be used in this filter. */
		dimensionFilter: Option[Schema.GoogleAnalyticsAdminV1betaAccessFilterExpression] = None,
	  /** This request's time zone if specified. If unspecified, the property's time zone is used. The request's time zone is used to interpret the start & end dates of the report. Formatted as strings from the IANA Time Zone database (https://www.iana.org/time-zones); for example "America/New_York" or "Asia/Tokyo". */
		timeZone: Option[String] = None,
	  /** The metrics requested and displayed in the response. Requests are allowed up to 10 metrics. */
		metrics: Option[List[Schema.GoogleAnalyticsAdminV1betaAccessMetric]] = None,
	  /** The number of rows to return. If unspecified, 10,000 rows are returned. The API returns a maximum of 100,000 rows per request, no matter how many you ask for. `limit` must be positive. The API may return fewer rows than the requested `limit`, if there aren't as many remaining rows as the `limit`. For instance, there are fewer than 300 possible values for the dimension `country`, so when reporting on only `country`, you can't get more than 300 rows, even if you set `limit` to a higher value. To learn more about this pagination parameter, see [Pagination](https://developers.google.com/analytics/devguides/reporting/data/v1/basics#pagination). */
		limit: Option[String] = None,
	  /** The row count of the start row. The first row is counted as row 0. If offset is unspecified, it is treated as 0. If offset is zero, then this method will return the first page of results with `limit` entries. To learn more about this pagination parameter, see [Pagination](https://developers.google.com/analytics/devguides/reporting/data/v1/basics#pagination). */
		offset: Option[String] = None,
	  /** Date ranges of access records to read. If multiple date ranges are requested, each response row will contain a zero based date range index. If two date ranges overlap, the access records for the overlapping days is included in the response rows for both date ranges. Requests are allowed up to 2 date ranges. */
		dateRanges: Option[List[Schema.GoogleAnalyticsAdminV1betaAccessDateRange]] = None,
	  /** Optional. Determines whether to include users who have never made an API call in the response. If true, all users with access to the specified property or account are included in the response, regardless of whether they have made an API call or not. If false, only the users who have made an API call will be included. */
		includeAllUsers: Option[Boolean] = None,
	  /** Specifies how rows are ordered in the response. */
		orderBys: Option[List[Schema.GoogleAnalyticsAdminV1betaAccessOrderBy]] = None,
	  /** Toggles whether to return the current state of this Analytics Property's quota. Quota is returned in [AccessQuota](#AccessQuota). For account-level requests, this field must be false. */
		returnEntityQuota: Option[Boolean] = None,
	  /** The dimensions requested and displayed in the response. Requests are allowed up to 9 dimensions. */
		dimensions: Option[List[Schema.GoogleAnalyticsAdminV1betaAccessDimension]] = None
	)
	
	case class GoogleAnalyticsAdminV1betaAccessQuota(
	  /** Properties can use 50,000 tokens per hour. An API request consumes a single number of tokens, and that number is deducted from all of the hourly, daily, and per project hourly quotas. */
		tokensPerHour: Option[Schema.GoogleAnalyticsAdminV1betaAccessQuotaStatus] = None,
	  /** Properties can use up to 25% of their tokens per project per hour. This amounts to Analytics 360 Properties can use 12,500 tokens per project per hour. An API request consumes a single number of tokens, and that number is deducted from all of the hourly, daily, and per project hourly quotas. */
		tokensPerProjectPerHour: Option[Schema.GoogleAnalyticsAdminV1betaAccessQuotaStatus] = None,
	  /** Properties can use up to 50 concurrent requests. */
		concurrentRequests: Option[Schema.GoogleAnalyticsAdminV1betaAccessQuotaStatus] = None,
	  /** Properties can use 250,000 tokens per day. Most requests consume fewer than 10 tokens. */
		tokensPerDay: Option[Schema.GoogleAnalyticsAdminV1betaAccessQuotaStatus] = None,
	  /** Properties and cloud project pairs can have up to 50 server errors per hour. */
		serverErrorsPerProjectPerHour: Option[Schema.GoogleAnalyticsAdminV1betaAccessQuotaStatus] = None
	)
	
	object GoogleAnalyticsAdminV1betaConversionEvent {
		enum CountingMethodEnum extends Enum[CountingMethodEnum] { case CONVERSION_COUNTING_METHOD_UNSPECIFIED, ONCE_PER_EVENT, ONCE_PER_SESSION }
	}
	case class GoogleAnalyticsAdminV1betaConversionEvent(
	  /** Output only. If set, this event can currently be deleted with DeleteConversionEvent. */
		deletable: Option[Boolean] = None,
	  /** Optional. The method by which conversions will be counted across multiple events within a session. If this value is not provided, it will be set to `ONCE_PER_EVENT`. */
		countingMethod: Option[Schema.GoogleAnalyticsAdminV1betaConversionEvent.CountingMethodEnum] = None,
	  /** Output only. Time when this conversion event was created in the property. */
		createTime: Option[String] = None,
	  /** Immutable. The event name for this conversion event. Examples: 'click', 'purchase' */
		eventName: Option[String] = None,
	  /** Output only. If set to true, this conversion event refers to a custom event. If set to false, this conversion event refers to a default event in GA. Default events typically have special meaning in GA. Default events are usually created for you by the GA system, but in some cases can be created by property admins. Custom events count towards the maximum number of custom conversion events that may be created per property. */
		custom: Option[Boolean] = None,
	  /** Optional. Defines a default value/currency for a conversion event. */
		defaultConversionValue: Option[Schema.GoogleAnalyticsAdminV1betaConversionEventDefaultConversionValue] = None,
	  /** Output only. Resource name of this conversion event. Format: properties/{property}/conversionEvents/{conversion_event} */
		name: Option[String] = None
	)
	
	case class GoogleAnalyticsAdminV1betaAccessDimensionHeader(
	  /** The dimension's name; for example 'userEmail'. */
		dimensionName: Option[String] = None
	)
	
	case class GoogleAnalyticsAdminV1betaListFirebaseLinksResponse(
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. Currently, Google Analytics supports only one FirebaseLink per property, so this will never be populated. */
		nextPageToken: Option[String] = None,
	  /** List of FirebaseLinks. This will have at most one value. */
		firebaseLinks: Option[List[Schema.GoogleAnalyticsAdminV1betaFirebaseLink]] = None
	)
	
	case class GoogleAnalyticsAdminV1betaAccountSummary(
	  /** Resource name of account referred to by this account summary Format: accounts/{account_id} Example: "accounts/1000" */
		account: Option[String] = None,
	  /** List of summaries for child accounts of this account. */
		propertySummaries: Option[List[Schema.GoogleAnalyticsAdminV1betaPropertySummary]] = None,
	  /** Resource name for this account summary. Format: accountSummaries/{account_id} Example: "accountSummaries/1000" */
		name: Option[String] = None,
	  /** Display name for the account referred to in this account summary. */
		displayName: Option[String] = None
	)
	
	case class GoogleAnalyticsAdminV1betaAccessMetric(
	  /** The API name of the metric. See [Data Access Schema](https://developers.google.com/analytics/devguides/config/admin/v1/access-api-schema) for the list of metrics supported in this API. Metrics are referenced by name in `metricFilter` & `orderBys`. */
		metricName: Option[String] = None
	)
	
	case class GoogleAnalyticsAdminV1betaGoogleAdsLink(
	  /** Enable personalized advertising features with this integration. Automatically publish my Google Analytics audience lists and Google Analytics remarketing events/parameters to the linked Google Ads account. If this field is not set on create/update, it will be defaulted to true. */
		adsPersonalizationEnabled: Option[Boolean] = None,
	  /** Output only. Format: properties/{propertyId}/googleAdsLinks/{googleAdsLinkId} Note: googleAdsLinkId is not the Google Ads customer ID. */
		name: Option[String] = None,
	  /** Output only. If true, this link is for a Google Ads manager account. */
		canManageClients: Option[Boolean] = None,
	  /** Output only. Time when this link was originally created. */
		createTime: Option[String] = None,
	  /** Immutable. Google Ads customer ID. */
		customerId: Option[String] = None,
	  /** Output only. Time when this link was last updated. */
		updateTime: Option[String] = None,
	  /** Output only. Email address of the user that created the link. An empty string will be returned if the email address can't be retrieved. */
		creatorEmailAddress: Option[String] = None
	)
	
	case class GoogleAnalyticsAdminV1betaListPropertiesResponse(
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Results that matched the filter criteria and were accessible to the caller. */
		properties: Option[List[Schema.GoogleAnalyticsAdminV1betaProperty]] = None
	)
	
	case class GoogleAnalyticsAdminV1betaAccessFilterExpression(
	  /** The FilterExpression is NOT of not_expression. */
		notExpression: Option[Schema.GoogleAnalyticsAdminV1betaAccessFilterExpression] = None,
	  /** Each of the FilterExpressions in the and_group has an AND relationship. */
		andGroup: Option[Schema.GoogleAnalyticsAdminV1betaAccessFilterExpressionList] = None,
	  /** A primitive filter. In the same FilterExpression, all of the filter's field names need to be either all dimensions or all metrics. */
		accessFilter: Option[Schema.GoogleAnalyticsAdminV1betaAccessFilter] = None,
	  /** Each of the FilterExpressions in the or_group has an OR relationship. */
		orGroup: Option[Schema.GoogleAnalyticsAdminV1betaAccessFilterExpressionList] = None
	)
	
	object GoogleAnalyticsAdminV1betaProperty {
		enum ServiceLevelEnum extends Enum[ServiceLevelEnum] { case SERVICE_LEVEL_UNSPECIFIED, GOOGLE_ANALYTICS_STANDARD, GOOGLE_ANALYTICS_360 }
		enum PropertyTypeEnum extends Enum[PropertyTypeEnum] { case PROPERTY_TYPE_UNSPECIFIED, PROPERTY_TYPE_ORDINARY, PROPERTY_TYPE_SUBPROPERTY, PROPERTY_TYPE_ROLLUP }
		enum IndustryCategoryEnum extends Enum[IndustryCategoryEnum] { case INDUSTRY_CATEGORY_UNSPECIFIED, AUTOMOTIVE, BUSINESS_AND_INDUSTRIAL_MARKETS, FINANCE, HEALTHCARE, TECHNOLOGY, TRAVEL, OTHER, ARTS_AND_ENTERTAINMENT, BEAUTY_AND_FITNESS, BOOKS_AND_LITERATURE, FOOD_AND_DRINK, GAMES, HOBBIES_AND_LEISURE, HOME_AND_GARDEN, INTERNET_AND_TELECOM, LAW_AND_GOVERNMENT, NEWS, ONLINE_COMMUNITIES, PEOPLE_AND_SOCIETY, PETS_AND_ANIMALS, REAL_ESTATE, REFERENCE, SCIENCE, SPORTS, JOBS_AND_EDUCATION, SHOPPING }
	}
	case class GoogleAnalyticsAdminV1betaProperty(
	  /** Output only. If set, the time at which this trashed property will be permanently deleted. If not set, then this property is not currently in the trash can and is not slated to be deleted. */
		expireTime: Option[String] = None,
	  /** Required. Reporting Time Zone, used as the day boundary for reports, regardless of where the data originates. If the time zone honors DST, Analytics will automatically adjust for the changes. NOTE: Changing the time zone only affects data going forward, and is not applied retroactively. Format: https://www.iana.org/time-zones Example: "America/Los_Angeles" */
		timeZone: Option[String] = None,
	  /** Output only. If set, the time at which this property was trashed. If not set, then this property is not currently in the trash can. */
		deleteTime: Option[String] = None,
	  /** Immutable. The resource name of the parent account Format: accounts/{account_id} Example: "accounts/123" */
		account: Option[String] = None,
	  /** The currency type used in reports involving monetary values. Format: https://en.wikipedia.org/wiki/ISO_4217 Examples: "USD", "EUR", "JPY" */
		currencyCode: Option[String] = None,
	  /** Output only. The Google Analytics service level that applies to this property. */
		serviceLevel: Option[Schema.GoogleAnalyticsAdminV1betaProperty.ServiceLevelEnum] = None,
	  /** Output only. Time when entity payload fields were last updated. */
		updateTime: Option[String] = None,
	  /** Immutable. Resource name of this property's logical parent. Note: The Property-Moving UI can be used to change the parent. Format: accounts/{account}, properties/{property} Example: "accounts/100", "properties/101" */
		parent: Option[String] = None,
	  /** Required. Human-readable display name for this property. The max allowed display name length is 100 UTF-16 code units. */
		displayName: Option[String] = None,
	  /** Immutable. The property type for this Property resource. When creating a property, if the type is "PROPERTY_TYPE_UNSPECIFIED", then "ORDINARY_PROPERTY" will be implied. */
		propertyType: Option[Schema.GoogleAnalyticsAdminV1betaProperty.PropertyTypeEnum] = None,
	  /** Output only. Time when the entity was originally created. */
		createTime: Option[String] = None,
	  /** Output only. Resource name of this property. Format: properties/{property_id} Example: "properties/1000" */
		name: Option[String] = None,
	  /** Industry associated with this property Example: AUTOMOTIVE, FOOD_AND_DRINK */
		industryCategory: Option[Schema.GoogleAnalyticsAdminV1betaProperty.IndustryCategoryEnum] = None
	)
	
	case class GoogleAnalyticsAdminV1betaDataStreamAndroidAppStreamData(
	  /** Immutable. The package name for the app being measured. Example: "com.example.myandroidapp" */
		packageName: Option[String] = None,
	  /** Output only. ID of the corresponding Android app in Firebase, if any. This ID can change if the Android app is deleted and recreated. */
		firebaseAppId: Option[String] = None
	)
	
	case class GoogleAnalyticsAdminV1betaAccessRow(
	  /** List of dimension values. These values are in the same order as specified in the request. */
		dimensionValues: Option[List[Schema.GoogleAnalyticsAdminV1betaAccessDimensionValue]] = None,
	  /** List of metric values. These values are in the same order as specified in the request. */
		metricValues: Option[List[Schema.GoogleAnalyticsAdminV1betaAccessMetricValue]] = None
	)
	
	case class GoogleAnalyticsAdminV1betaListDataStreamsResponse(
	  /** List of DataStreams. */
		dataStreams: Option[List[Schema.GoogleAnalyticsAdminV1betaDataStream]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleAnalyticsAdminV1betaAccessQuotaStatus(
	  /** Quota remaining after this request. */
		remaining: Option[Int] = None,
	  /** Quota consumed by this request. */
		consumed: Option[Int] = None
	)
	
	case class GoogleAnalyticsAdminV1betaNumericValue(
	  /** Double value */
		doubleValue: Option[BigDecimal] = None,
	  /** Integer value */
		int64Value: Option[String] = None
	)
	
	case class GoogleAnalyticsAdminV1betaAccessDimension(
	  /** The API name of the dimension. See [Data Access Schema](https://developers.google.com/analytics/devguides/config/admin/v1/access-api-schema) for the list of dimensions supported in this API. Dimensions are referenced by name in `dimensionFilter` and `orderBys`. */
		dimensionName: Option[String] = None
	)
	
	case class GoogleAnalyticsAdminV1betaAccessOrderBy(
	  /** Sorts results by a metric's values. */
		metric: Option[Schema.GoogleAnalyticsAdminV1betaAccessOrderByMetricOrderBy] = None,
	  /** If true, sorts by descending order. If false or unspecified, sorts in ascending order. */
		desc: Option[Boolean] = None,
	  /** Sorts results by a dimension's values. */
		dimension: Option[Schema.GoogleAnalyticsAdminV1betaAccessOrderByDimensionOrderBy] = None
	)
	
	case class GoogleAnalyticsAdminV1betaKeyEventDefaultValue(
	  /** Required. When an occurrence of this Key Event (specified by event_name) has no set currency this currency will be applied as the default. Must be in ISO 4217 currency code format. See https://en.wikipedia.org/wiki/ISO_4217 for more information. */
		currencyCode: Option[String] = None,
	  /** Required. This will be used to populate the "value" parameter for all occurrences of this Key Event (specified by event_name) where that parameter is unset. */
		numericValue: Option[BigDecimal] = None
	)
	
	object GoogleAnalyticsAdminV1betaPropertySummary {
		enum PropertyTypeEnum extends Enum[PropertyTypeEnum] { case PROPERTY_TYPE_UNSPECIFIED, PROPERTY_TYPE_ORDINARY, PROPERTY_TYPE_SUBPROPERTY, PROPERTY_TYPE_ROLLUP }
	}
	case class GoogleAnalyticsAdminV1betaPropertySummary(
	  /** Resource name of this property's logical parent. Note: The Property-Moving UI can be used to change the parent. Format: accounts/{account}, properties/{property} Example: "accounts/100", "properties/200" */
		parent: Option[String] = None,
	  /** The property's property type. */
		propertyType: Option[Schema.GoogleAnalyticsAdminV1betaPropertySummary.PropertyTypeEnum] = None,
	  /** Display name for the property referred to in this property summary. */
		displayName: Option[String] = None,
	  /** Resource name of property referred to by this property summary Format: properties/{property_id} Example: "properties/1000" */
		property: Option[String] = None
	)
	
	case class GoogleAnalyticsAdminV1betaListCustomDimensionsResponse(
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** List of CustomDimensions. */
		customDimensions: Option[List[Schema.GoogleAnalyticsAdminV1betaCustomDimension]] = None
	)
	
	case class GoogleAnalyticsAdminV1betaDataSharingSettings(
	  /** Allows any of Google sales to access the data in order to suggest configuration changes to improve results. */
		sharingWithGoogleAnySalesEnabled: Option[Boolean] = None,
	  /** Allows Google to share the data anonymously in aggregate form with others. */
		sharingWithOthersEnabled: Option[Boolean] = None,
	  /** Allows Google to use the data to improve other Google products or services. */
		sharingWithGoogleProductsEnabled: Option[Boolean] = None,
	  /** Allows Google support to access the data in order to help troubleshoot issues. */
		sharingWithGoogleSupportEnabled: Option[Boolean] = None,
	  /** Output only. Resource name. Format: accounts/{account}/dataSharingSettings Example: "accounts/1000/dataSharingSettings" */
		name: Option[String] = None,
	  /** Allows Google sales teams that are assigned to the customer to access the data in order to suggest configuration changes to improve results. Sales team restrictions still apply when enabled. */
		sharingWithGoogleAssignedSalesEnabled: Option[Boolean] = None
	)
	
	case class GoogleAnalyticsAdminV1betaListAccountsResponse(
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** Results that were accessible to the caller. */
		accounts: Option[List[Schema.GoogleAnalyticsAdminV1betaAccount]] = None
	)
	
	case class GoogleAnalyticsAdminV1betaAccessMetricValue(
	  /** The measurement value. For example, this value may be '13'. */
		value: Option[String] = None
	)
	
	case class GoogleAnalyticsAdminV1betaConversionEventDefaultConversionValue(
	  /** This value will be used to populate the value for all conversions of the specified event_name where the event "value" parameter is unset. */
		value: Option[BigDecimal] = None,
	  /** When a conversion event for this event_name has no set currency, this currency will be applied as the default. Must be in ISO 4217 currency code format. See https://en.wikipedia.org/wiki/ISO_4217 for more information. */
		currencyCode: Option[String] = None
	)
	
	case class GoogleAnalyticsAdminV1betaListGoogleAdsLinksResponse(
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** List of GoogleAdsLinks. */
		googleAdsLinks: Option[List[Schema.GoogleAnalyticsAdminV1betaGoogleAdsLink]] = None
	)
	
	case class GoogleAnalyticsAdminV1betaDataStreamIosAppStreamData(
	  /** Required. Immutable. The Apple App Store Bundle ID for the app Example: "com.example.myiosapp" */
		bundleId: Option[String] = None,
	  /** Output only. ID of the corresponding iOS app in Firebase, if any. This ID can change if the iOS app is deleted and recreated. */
		firebaseAppId: Option[String] = None
	)
	
	case class GoogleAnalyticsAdminV1betaAcknowledgeUserDataCollectionRequest(
	  /** Required. An acknowledgement that the caller of this method understands the terms of user data collection. This field must contain the exact value: "I acknowledge that I have the necessary privacy disclosures and rights from my end users for the collection and processing of their data, including the association of such data with the visitation information Google Analytics collects from my site and/or app property." */
		acknowledgement: Option[String] = None
	)
	
	case class GoogleAnalyticsAdminV1betaAccessFilterExpressionList(
	  /** A list of filter expressions. */
		expressions: Option[List[Schema.GoogleAnalyticsAdminV1betaAccessFilterExpression]] = None
	)
	
	case class GoogleAnalyticsAdminV1betaListAccountSummariesResponse(
	  /** Account summaries of all accounts the caller has access to. */
		accountSummaries: Option[List[Schema.GoogleAnalyticsAdminV1betaAccountSummary]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleAnalyticsAdminV1betaListMeasurementProtocolSecretsResponse(
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** A list of secrets for the parent stream specified in the request. */
		measurementProtocolSecrets: Option[List[Schema.GoogleAnalyticsAdminV1betaMeasurementProtocolSecret]] = None
	)
	
	case class GoogleAnalyticsAdminV1betaListKeyEventsResponse(
	  /** The requested Key Events */
		keyEvents: Option[List[Schema.GoogleAnalyticsAdminV1betaKeyEvent]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	object GoogleAnalyticsAdminV1betaKeyEvent {
		enum CountingMethodEnum extends Enum[CountingMethodEnum] { case COUNTING_METHOD_UNSPECIFIED, ONCE_PER_EVENT, ONCE_PER_SESSION }
	}
	case class GoogleAnalyticsAdminV1betaKeyEvent(
	  /** Output only. Time when this key event was created in the property. */
		createTime: Option[String] = None,
	  /** Output only. If set to true, this key event refers to a custom event. If set to false, this key event refers to a default event in GA. Default events typically have special meaning in GA. Default events are usually created for you by the GA system, but in some cases can be created by property admins. Custom events count towards the maximum number of custom key events that may be created per property. */
		custom: Option[Boolean] = None,
	  /** Output only. If set to true, this event can be deleted. */
		deletable: Option[Boolean] = None,
	  /** Optional. Defines a default value/currency for a key event. */
		defaultValue: Option[Schema.GoogleAnalyticsAdminV1betaKeyEventDefaultValue] = None,
	  /** Immutable. The event name for this key event. Examples: 'click', 'purchase' */
		eventName: Option[String] = None,
	  /** Required. The method by which Key Events will be counted across multiple events within a session. */
		countingMethod: Option[Schema.GoogleAnalyticsAdminV1betaKeyEvent.CountingMethodEnum] = None,
	  /** Output only. Resource name of this key event. Format: properties/{property}/keyEvents/{key_event} */
		name: Option[String] = None
	)
	
	case class GoogleAnalyticsAdminV1betaAccessInListFilter(
	  /** The list of string values. Must be non-empty. */
		values: Option[List[String]] = None,
	  /** If true, the string value is case sensitive. */
		caseSensitive: Option[Boolean] = None
	)
	
	object GoogleAnalyticsAdminV1betaCustomMetric {
		enum MeasurementUnitEnum extends Enum[MeasurementUnitEnum] { case MEASUREMENT_UNIT_UNSPECIFIED, STANDARD, CURRENCY, FEET, METERS, KILOMETERS, MILES, MILLISECONDS, SECONDS, MINUTES, HOURS }
		enum ScopeEnum extends Enum[ScopeEnum] { case METRIC_SCOPE_UNSPECIFIED, EVENT }
		enum RestrictedMetricTypeEnum extends Enum[RestrictedMetricTypeEnum] { case RESTRICTED_METRIC_TYPE_UNSPECIFIED, COST_DATA, REVENUE_DATA }
	}
	case class GoogleAnalyticsAdminV1betaCustomMetric(
	  /** Required. Display name for this custom metric as shown in the Analytics UI. Max length of 82 characters, alphanumeric plus space and underscore starting with a letter. Legacy system-generated display names may contain square brackets, but updates to this field will never permit square brackets. */
		displayName: Option[String] = None,
	  /** Output only. Resource name for this CustomMetric resource. Format: properties/{property}/customMetrics/{customMetric} */
		name: Option[String] = None,
	  /** Required. Immutable. Tagging name for this custom metric. If this is an event-scoped metric, then this is the event parameter name. May only contain alphanumeric and underscore charactes, starting with a letter. Max length of 40 characters for event-scoped metrics. */
		parameterName: Option[String] = None,
	  /** Required. The type for the custom metric's value. */
		measurementUnit: Option[Schema.GoogleAnalyticsAdminV1betaCustomMetric.MeasurementUnitEnum] = None,
	  /** Required. Immutable. The scope of this custom metric. */
		scope: Option[Schema.GoogleAnalyticsAdminV1betaCustomMetric.ScopeEnum] = None,
	  /** Optional. Description for this custom dimension. Max length of 150 characters. */
		description: Option[String] = None,
	  /** Optional. Types of restricted data that this metric may contain. Required for metrics with CURRENCY measurement unit. Must be empty for metrics with a non-CURRENCY measurement unit. */
		restrictedMetricType: Option[List[Schema.GoogleAnalyticsAdminV1betaCustomMetric.RestrictedMetricTypeEnum]] = None
	)
	
	case class GoogleAnalyticsAdminV1betaAcknowledgeUserDataCollectionResponse(
	
	)
	
	object GoogleAnalyticsAdminV1betaCustomDimension {
		enum ScopeEnum extends Enum[ScopeEnum] { case DIMENSION_SCOPE_UNSPECIFIED, EVENT, USER, ITEM }
	}
	case class GoogleAnalyticsAdminV1betaCustomDimension(
	  /** Required. Display name for this custom dimension as shown in the Analytics UI. Max length of 82 characters, alphanumeric plus space and underscore starting with a letter. Legacy system-generated display names may contain square brackets, but updates to this field will never permit square brackets. */
		displayName: Option[String] = None,
	  /** Required. Immutable. Tagging parameter name for this custom dimension. If this is a user-scoped dimension, then this is the user property name. If this is an event-scoped dimension, then this is the event parameter name. If this is an item-scoped dimension, then this is the parameter name found in the eCommerce items array. May only contain alphanumeric and underscore characters, starting with a letter. Max length of 24 characters for user-scoped dimensions, 40 characters for event-scoped dimensions. */
		parameterName: Option[String] = None,
	  /** Required. Immutable. The scope of this dimension. */
		scope: Option[Schema.GoogleAnalyticsAdminV1betaCustomDimension.ScopeEnum] = None,
	  /** Optional. If set to true, sets this dimension as NPA and excludes it from ads personalization. This is currently only supported by user-scoped custom dimensions. */
		disallowAdsPersonalization: Option[Boolean] = None,
	  /** Optional. Description for this custom dimension. Max length of 150 characters. */
		description: Option[String] = None,
	  /** Output only. Resource name for this CustomDimension resource. Format: properties/{property}/customDimensions/{customDimension} */
		name: Option[String] = None
	)
	
	case class GoogleAnalyticsAdminV1betaChangeHistoryChangeChangeHistoryResource(
	  /** A snapshot of a Property resource in change history. */
		property: Option[Schema.GoogleAnalyticsAdminV1betaProperty] = None,
	  /** A snapshot of an Account resource in change history. */
		account: Option[Schema.GoogleAnalyticsAdminV1betaAccount] = None,
	  /** A snapshot of a DataStream resource in change history. */
		dataStream: Option[Schema.GoogleAnalyticsAdminV1betaDataStream] = None,
	  /** A snapshot of a data retention settings resource in change history. */
		dataRetentionSettings: Option[Schema.GoogleAnalyticsAdminV1betaDataRetentionSettings] = None,
	  /** A snapshot of a ConversionEvent resource in change history. */
		conversionEvent: Option[Schema.GoogleAnalyticsAdminV1betaConversionEvent] = None,
	  /** A snapshot of a MeasurementProtocolSecret resource in change history. */
		measurementProtocolSecret: Option[Schema.GoogleAnalyticsAdminV1betaMeasurementProtocolSecret] = None,
	  /** A snapshot of a GoogleAdsLink resource in change history. */
		googleAdsLink: Option[Schema.GoogleAnalyticsAdminV1betaGoogleAdsLink] = None,
	  /** A snapshot of a FirebaseLink resource in change history. */
		firebaseLink: Option[Schema.GoogleAnalyticsAdminV1betaFirebaseLink] = None
	)
	
	case class GoogleAnalyticsAdminV1betaListCustomMetricsResponse(
	  /** List of CustomMetrics. */
		customMetrics: Option[List[Schema.GoogleAnalyticsAdminV1betaCustomMetric]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	object GoogleAnalyticsAdminV1betaSearchChangeHistoryEventsRequest {
		enum ActionEnum extends Enum[ActionEnum] { case ACTION_TYPE_UNSPECIFIED, CREATED, UPDATED, DELETED }
		enum ResourceTypeEnum extends Enum[ResourceTypeEnum] { case CHANGE_HISTORY_RESOURCE_TYPE_UNSPECIFIED, ACCOUNT, PROPERTY, FIREBASE_LINK, GOOGLE_ADS_LINK, GOOGLE_SIGNALS_SETTINGS, CONVERSION_EVENT, MEASUREMENT_PROTOCOL_SECRET, CUSTOM_DIMENSION, CUSTOM_METRIC, DATA_RETENTION_SETTINGS, DISPLAY_VIDEO_360_ADVERTISER_LINK, DISPLAY_VIDEO_360_ADVERTISER_LINK_PROPOSAL, DATA_STREAM, ATTRIBUTION_SETTINGS }
	}
	case class GoogleAnalyticsAdminV1betaSearchChangeHistoryEventsRequest(
	  /** Optional. If set, only return changes made before this time (inclusive). */
		latestChangeTime: Option[String] = None,
	  /** Optional. If set, only return changes if they are made by a user in this list. */
		actorEmail: Option[List[String]] = None,
	  /** Optional. If set, only return changes that match one or more of these types of actions. */
		action: Option[List[Schema.GoogleAnalyticsAdminV1betaSearchChangeHistoryEventsRequest.ActionEnum]] = None,
	  /** Optional. If set, only return changes made after this time (inclusive). */
		earliestChangeTime: Option[String] = None,
	  /** Optional. If set, only return changes if they are for a resource that matches at least one of these types. */
		resourceType: Option[List[Schema.GoogleAnalyticsAdminV1betaSearchChangeHistoryEventsRequest.ResourceTypeEnum]] = None,
	  /** Optional. Resource name for a child property. If set, only return changes made to this property or its child resources. Format: properties/{propertyId} Example: `properties/100` */
		property: Option[String] = None,
	  /** Optional. The maximum number of ChangeHistoryEvent items to return. The service may return fewer than this value, even if there are additional pages. If unspecified, at most 50 items will be returned. The maximum value is 200 (higher values will be coerced to the maximum). */
		pageSize: Option[Int] = None,
	  /** Optional. A page token, received from a previous `SearchChangeHistoryEvents` call. Provide this to retrieve the subsequent page. When paginating, all other parameters provided to `SearchChangeHistoryEvents` must match the call that provided the page token. */
		pageToken: Option[String] = None
	)
	
	case class GoogleAnalyticsAdminV1betaAccessOrderByMetricOrderBy(
	  /** A metric name in the request to order by. */
		metricName: Option[String] = None
	)
}
