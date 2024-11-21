package com.boresjo.play.api.google.fitness

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class DataType(
	  /** A field represents one dimension of a data type. */
		field: Option[List[Schema.DataTypeField]] = None,
	  /** Each data type has a unique, namespaced, name. All data types in the com.google namespace are shared as part of the platform. */
		name: Option[String] = None
	)
	
	case class BucketBySession(
	  /** Specifies that only sessions of duration longer than minDurationMillis are considered and used as a container for aggregated data. */
		minDurationMillis: Option[String] = None
	)
	
	object AggregateRequest {
		enum FilteredDataQualityStandardEnum extends Enum[FilteredDataQualityStandardEnum] { case dataQualityUnknown, dataQualityBloodPressureEsh2002, dataQualityBloodPressureEsh2010, dataQualityBloodPressureAami, dataQualityBloodPressureBhsAA, dataQualityBloodPressureBhsAB, dataQualityBloodPressureBhsBA, dataQualityBloodPressureBhsBB, dataQualityBloodGlucoseIso151972003, dataQualityBloodGlucoseIso151972013 }
	}
	case class AggregateRequest(
	  /** Specifies that data be aggregated by the type of activity being performed when the data was recorded. All data that was recorded during a certain activity type (.for the given time range) will be aggregated into the same bucket. Data that was recorded while the user was not active will not be included in the response. Mutually exclusive of other bucketing specifications. */
		bucketByActivityType: Option[Schema.BucketByActivity] = None,
	  /** Specifies that data be aggregated by user sessions. Data that does not fall within the time range of a session will not be included in the response. Mutually exclusive of other bucketing specifications. */
		bucketBySession: Option[Schema.BucketBySession] = None,
	  /** The end of a window of time. Data that intersects with this time window will be aggregated. The time is in milliseconds since epoch, inclusive. The maximum allowed difference between start_time_millis // and end_time_millis is 7776000000 (roughly 90 days). */
		endTimeMillis: Option[String] = None,
	  /** Specifies that data be aggregated each activity segment recorded for a user. Similar to bucketByActivitySegment, but bucketing is done for each activity segment rather than all segments of the same type. Mutually exclusive of other bucketing specifications. */
		bucketByActivitySegment: Option[Schema.BucketByActivity] = None,
	  /** Specifies that data be aggregated by a single time interval. Mutually exclusive of other bucketing specifications. */
		bucketByTime: Option[Schema.BucketByTime] = None,
	  /** The start of a window of time. Data that intersects with this time window will be aggregated. The time is in milliseconds since epoch, inclusive. */
		startTimeMillis: Option[String] = None,
	  /** The specification of data to be aggregated. At least one aggregateBy spec must be provided. All data that is specified will be aggregated using the same bucketing criteria. There will be one dataset in the response for every aggregateBy spec. */
		aggregateBy: Option[List[Schema.AggregateBy]] = None,
	  /** DO NOT POPULATE THIS FIELD. It is ignored. */
		filteredDataQualityStandard: Option[List[Schema.AggregateRequest.FilteredDataQualityStandardEnum]] = None
	)
	
	object BucketByTimePeriod {
		enum TypeEnum extends Enum[TypeEnum] { case day, week, month }
	}
	case class BucketByTimePeriod(
		`type`: Option[Schema.BucketByTimePeriod.TypeEnum] = None,
	  /** org.joda.timezone.DateTimeZone */
		timeZoneId: Option[String] = None,
		value: Option[Int] = None
	)
	
	case class Value(
	  /** Map value. The valid key space and units for the corresponding value of each entry should be documented as part of the data type definition. Keys should be kept small whenever possible. Data streams with large keys and high data frequency may be down sampled. */
		mapVal: Option[List[Schema.ValueMapValEntry]] = None,
	  /** Floating point value. When this is set, other values must not be set. */
		fpVal: Option[BigDecimal] = None,
	  /** String value. When this is set, other values must not be set. Strings should be kept small whenever possible. Data streams with large string values and high data frequency may be down sampled. */
		stringVal: Option[String] = None,
	  /** Integer value. When this is set, other values must not be set. */
		intVal: Option[Int] = None
	)
	
	case class Dataset(
	  /** The largest end time of all data points in this possibly partial representation of the dataset. Time is in nanoseconds from epoch. This should also match the second part of the dataset identifier. */
		maxEndTimeNs: Option[String] = None,
	  /** A partial list of data points contained in the dataset, ordered by endTimeNanos. This list is considered complete when retrieving a small dataset and partial when patching a dataset or retrieving a dataset that is too large to include in a single response. */
		point: Option[List[Schema.DataPoint]] = None,
	  /** This token will be set when a dataset is received in response to a GET request and the dataset is too large to be included in a single response. Provide this value in a subsequent GET request to return the next page of data points within this dataset. */
		nextPageToken: Option[String] = None,
	  /** The smallest start time of all data points in this possibly partial representation of the dataset. Time is in nanoseconds from epoch. This should also match the first part of the dataset identifier. */
		minStartTimeNs: Option[String] = None,
	  /** The data stream ID of the data source that created the points in this dataset. */
		dataSourceId: Option[String] = None
	)
	
	object DataTypeField {
		enum FormatEnum extends Enum[FormatEnum] { case integer, floatPoint, string, map, integerList, floatList, blob }
	}
	case class DataTypeField(
	  /** The different supported formats for each field in a data type. */
		format: Option[Schema.DataTypeField.FormatEnum] = None,
		optional: Option[Boolean] = None,
	  /** Defines the name and format of data. Unlike data type names, field names are not namespaced, and only need to be unique within the data type. */
		name: Option[String] = None
	)
	
	object DataSource {
		enum DataQualityStandardEnum extends Enum[DataQualityStandardEnum] { case dataQualityUnknown, dataQualityBloodPressureEsh2002, dataQualityBloodPressureEsh2010, dataQualityBloodPressureAami, dataQualityBloodPressureBhsAA, dataQualityBloodPressureBhsAB, dataQualityBloodPressureBhsBA, dataQualityBloodPressureBhsBB, dataQualityBloodGlucoseIso151972003, dataQualityBloodGlucoseIso151972013 }
		enum TypeEnum extends Enum[TypeEnum] { case raw, derived }
	}
	case class DataSource(
	  /** Information about an application which feeds sensor data into the platform. */
		application: Option[Schema.Application] = None,
	  /** An end-user visible name for this data source. */
		name: Option[String] = None,
	  /** DO NOT POPULATE THIS FIELD. It is never populated in responses from the platform, and is ignored in queries. It will be removed in a future version entirely. */
		dataQualityStandard: Option[List[Schema.DataSource.DataQualityStandardEnum]] = None,
	  /** The data type defines the schema for a stream of data being collected by, inserted into, or queried from the Fitness API. */
		dataType: Option[Schema.DataType] = None,
	  /** A unique identifier for the data stream produced by this data source. The identifier includes: - The physical device's manufacturer, model, and serial number (UID). - The application's package name or name. Package name is used when the data source was created by an Android application. The developer project number is used when the data source was created by a REST client. - The data source's type. - The data source's stream name. Note that not all attributes of the data source are used as part of the stream identifier. In particular, the version of the hardware/the application isn't used. This allows us to preserve the same stream through version updates. This also means that two DataSource objects may represent the same data stream even if they're not equal. The exact format of the data stream ID created by an Android application is: type:dataType.name:application.packageName:device.manufacturer:device.model:device.uid:dataStreamName The exact format of the data stream ID created by a REST client is: type:dataType.name:developer project number:device.manufacturer:device.model:device.uid:dataStreamName When any of the optional fields that make up the data stream ID are absent, they will be omitted from the data stream ID. The minimum viable data stream ID would be: type:dataType.name:developer project number Finally, the developer project number and device UID are obfuscated when read by any REST or Android client that did not create the data source. Only the data source creator will see the developer project number in clear and normal form. This means a client will see a different set of data_stream_ids than another client with different credentials. */
		dataStreamId: Option[String] = None,
	  /** The stream name uniquely identifies this particular data source among other data sources of the same type from the same underlying producer. Setting the stream name is optional, but should be done whenever an application exposes two streams for the same data type, or when a device has two equivalent sensors. */
		dataStreamName: Option[String] = None,
	  /** Representation of an integrated device (such as a phone or a wearable) that can hold sensors. */
		device: Option[Schema.Device] = None,
	  /** A constant describing the type of this data source. Indicates whether this data source produces raw or derived data. */
		`type`: Option[Schema.DataSource.TypeEnum] = None
	)
	
	case class DataPoint(
	  /** The raw timestamp from the original SensorEvent. */
		rawTimestampNanos: Option[String] = None,
	  /** Values of each data type field for the data point. It is expected that each value corresponding to a data type field will occur in the same order that the field is listed with in the data type specified in a data source. Only one of integer and floating point fields will be populated, depending on the format enum value within data source's type field. */
		value: Option[List[Schema.Value]] = None,
	  /** Indicates the last time this data point was modified. Useful only in contexts where we are listing the data changes, rather than representing the current state of the data. */
		modifiedTimeMillis: Option[String] = None,
	  /** DO NOT USE THIS FIELD. It is ignored, and not stored. */
		computationTimeMillis: Option[String] = None,
	  /** The start time of the interval represented by this data point, in nanoseconds since epoch. */
		startTimeNanos: Option[String] = None,
	  /** If the data point is contained in a dataset for a derived data source, this field will be populated with the data source stream ID that created the data point originally. WARNING: do not rely on this field for anything other than debugging. The value of this field, if it is set at all, is an implementation detail and is not guaranteed to remain consistent. */
		originDataSourceId: Option[String] = None,
	  /** The end time of the interval represented by this data point, in nanoseconds since epoch. */
		endTimeNanos: Option[String] = None,
	  /** The data type defining the format of the values in this data point. */
		dataTypeName: Option[String] = None
	)
	
	case class ListSessionsResponse(
	  /** The sync token which is used to sync further changes. This will only be provided if both startTime and endTime are omitted from the request. */
		nextPageToken: Option[String] = None,
	  /** Flag to indicate server has more data to transfer. DO NOT USE THIS FIELD. It is never populated in responses from the server. */
		hasMoreData: Option[Boolean] = None,
	  /** If includeDeleted is set to true in the request, and startTime and endTime are omitted, this will include sessions which were deleted since the last sync. */
		deletedSession: Option[List[Schema.Session]] = None,
	  /** Sessions with an end time that is between startTime and endTime of the request. */
		session: Option[List[Schema.Session]] = None
	)
	
	case class Application(
	  /** An optional URI that can be used to link back to the application. */
		detailsUrl: Option[String] = None,
	  /** Package name for this application. This is used as a unique identifier when created by Android applications, but cannot be specified by REST clients. REST clients will have their developer project number reflected into the Data Source data stream IDs, instead of the packageName. */
		packageName: Option[String] = None,
	  /** The name of this application. This is required for REST clients, but we do not enforce uniqueness of this name. It is provided as a matter of convenience for other developers who would like to identify which REST created an Application or Data Source. */
		name: Option[String] = None,
	  /** Version of the application. You should update this field whenever the application changes in a way that affects the computation of the data. */
		version: Option[String] = None
	)
	
	case class ValueMapValEntry(
		key: Option[String] = None,
		value: Option[Schema.MapValue] = None
	)
	
	case class MapValue(
	  /** Floating point value. */
		fpVal: Option[BigDecimal] = None
	)
	
	case class Session(
	  /** A human readable name of the session. */
		name: Option[String] = None,
	  /** An end time, in milliseconds since epoch, inclusive. */
		endTimeMillis: Option[String] = None,
	  /** Session active time. While start_time_millis and end_time_millis define the full session time, the active time can be shorter and specified by active_time_millis. If the inactive time during the session is known, it should also be inserted via a com.google.activity.segment data point with a STILL activity value */
		activeTimeMillis: Option[String] = None,
	  /** A start time, in milliseconds since epoch, inclusive. */
		startTimeMillis: Option[String] = None,
	  /** The application that created the session. */
		application: Option[Schema.Application] = None,
	  /** A timestamp that indicates when the session was last modified. */
		modifiedTimeMillis: Option[String] = None,
	  /** A description for this session. */
		description: Option[String] = None,
	  /** The type of activity this session represents. */
		activityType: Option[Int] = None,
	  /** A client-generated identifier that is unique across all sessions owned by this particular user. */
		id: Option[String] = None
	)
	
	case class AggregateBy(
	  /** The data type to aggregate. All data sources providing this data type will contribute data to the aggregation. The response will contain a single dataset for this data type name. The dataset will have a data source ID of derived::com.google.android.gms:aggregated. If the user has no data for this data type, an empty data set will be returned. Note: Data can be aggregated by either the dataTypeName or the dataSourceId, not both. */
		dataTypeName: Option[String] = None,
	  /** A data source ID to aggregate. Only data from the specified data source ID will be included in the aggregation. If specified, this data source must exist; the OAuth scopes in the supplied credentials must grant read access to this data type. The dataset in the response will have the same data source ID. Note: Data can be aggregated by either the dataTypeName or the dataSourceId, not both. */
		dataSourceId: Option[String] = None
	)
	
	case class AggregateResponse(
	  /** A list of buckets containing the aggregated data. */
		bucket: Option[List[Schema.AggregateBucket]] = None
	)
	
	case class BucketByTime(
		period: Option[Schema.BucketByTimePeriod] = None,
	  /** Specifies that result buckets aggregate data by exactly durationMillis time frames. Time frames that contain no data will be included in the response with an empty dataset. */
		durationMillis: Option[String] = None
	)
	
	object AggregateBucket {
		enum TypeEnum extends Enum[TypeEnum] { case unknown, time, session, activityType, activitySegment }
	}
	case class AggregateBucket(
	  /** Available for Bucket.Type.ACTIVITY_TYPE, Bucket.Type.ACTIVITY_SEGMENT */
		activity: Option[Int] = None,
	  /** The end time for the aggregated data, in milliseconds since epoch, inclusive. */
		endTimeMillis: Option[String] = None,
	  /** Available for Bucket.Type.SESSION */
		session: Option[Schema.Session] = None,
	  /** There will be one dataset per AggregateBy in the request. */
		dataset: Option[List[Schema.Dataset]] = None,
	  /** The start time for the aggregated data, in milliseconds since epoch, inclusive. */
		startTimeMillis: Option[String] = None,
	  /** The type of a bucket signifies how the data aggregation is performed in the bucket. */
		`type`: Option[Schema.AggregateBucket.TypeEnum] = None
	)
	
	case class ListDataPointChangesResponse(
	  /** Inserted data points for the user. */
		insertedDataPoint: Option[List[Schema.DataPoint]] = None,
	  /** The continuation token, which is used to page through large result sets. Provide this value in a subsequent request to return the next page of results. */
		nextPageToken: Option[String] = None,
	  /** Deleted data points for the user. Note, for modifications this should be parsed before handling insertions. */
		deletedDataPoint: Option[List[Schema.DataPoint]] = None,
	  /** The data stream ID of the data source with data point changes. */
		dataSourceId: Option[String] = None
	)
	
	object Device {
		enum TypeEnum extends Enum[TypeEnum] { case unknown, phone, tablet, watch, chestStrap, scale, headMounted, smartDisplay }
	}
	case class Device(
	  /** Version string for the device hardware/software. */
		version: Option[String] = None,
	  /** Manufacturer of the product/hardware. */
		manufacturer: Option[String] = None,
	  /** End-user visible model name for the device. */
		model: Option[String] = None,
	  /** A constant representing the type of the device. */
		`type`: Option[Schema.Device.TypeEnum] = None,
	  /** The serial number or other unique ID for the hardware. This field is obfuscated when read by any REST or Android client that did not create the data source. Only the data source creator will see the uid field in clear and normal form. The obfuscation preserves equality; that is, given two IDs, if id1 == id2, obfuscated(id1) == obfuscated(id2). */
		uid: Option[String] = None
	)
	
	case class ListDataSourcesResponse(
	  /** A previously created data source. */
		dataSource: Option[List[Schema.DataSource]] = None
	)
	
	case class BucketByActivity(
	  /** Specifies that only activity segments of duration longer than minDurationMillis are considered and used as a container for aggregated data. */
		minDurationMillis: Option[String] = None,
	  /** The default activity stream will be used if a specific activityDataSourceId is not specified. */
		activityDataSourceId: Option[String] = None
	)
}
