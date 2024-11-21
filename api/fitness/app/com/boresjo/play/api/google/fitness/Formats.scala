package com.boresjo.play.api.google.fitness

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtDataType: Format[Schema.DataType] = Json.format[Schema.DataType]
	given fmtDataTypeField: Format[Schema.DataTypeField] = Json.format[Schema.DataTypeField]
	given fmtBucketBySession: Format[Schema.BucketBySession] = Json.format[Schema.BucketBySession]
	given fmtAggregateRequest: Format[Schema.AggregateRequest] = Json.format[Schema.AggregateRequest]
	given fmtBucketByActivity: Format[Schema.BucketByActivity] = Json.format[Schema.BucketByActivity]
	given fmtBucketByTime: Format[Schema.BucketByTime] = Json.format[Schema.BucketByTime]
	given fmtAggregateBy: Format[Schema.AggregateBy] = Json.format[Schema.AggregateBy]
	given fmtAggregateRequestFilteredDataQualityStandardEnum: Format[Schema.AggregateRequest.FilteredDataQualityStandardEnum] = JsonEnumFormat[Schema.AggregateRequest.FilteredDataQualityStandardEnum]
	given fmtBucketByTimePeriod: Format[Schema.BucketByTimePeriod] = Json.format[Schema.BucketByTimePeriod]
	given fmtBucketByTimePeriodTypeEnum: Format[Schema.BucketByTimePeriod.TypeEnum] = JsonEnumFormat[Schema.BucketByTimePeriod.TypeEnum]
	given fmtValue: Format[Schema.Value] = Json.format[Schema.Value]
	given fmtValueMapValEntry: Format[Schema.ValueMapValEntry] = Json.format[Schema.ValueMapValEntry]
	given fmtDataset: Format[Schema.Dataset] = Json.format[Schema.Dataset]
	given fmtDataPoint: Format[Schema.DataPoint] = Json.format[Schema.DataPoint]
	given fmtDataTypeFieldFormatEnum: Format[Schema.DataTypeField.FormatEnum] = JsonEnumFormat[Schema.DataTypeField.FormatEnum]
	given fmtDataSource: Format[Schema.DataSource] = Json.format[Schema.DataSource]
	given fmtApplication: Format[Schema.Application] = Json.format[Schema.Application]
	given fmtDataSourceDataQualityStandardEnum: Format[Schema.DataSource.DataQualityStandardEnum] = JsonEnumFormat[Schema.DataSource.DataQualityStandardEnum]
	given fmtDevice: Format[Schema.Device] = Json.format[Schema.Device]
	given fmtDataSourceTypeEnum: Format[Schema.DataSource.TypeEnum] = JsonEnumFormat[Schema.DataSource.TypeEnum]
	given fmtListSessionsResponse: Format[Schema.ListSessionsResponse] = Json.format[Schema.ListSessionsResponse]
	given fmtSession: Format[Schema.Session] = Json.format[Schema.Session]
	given fmtMapValue: Format[Schema.MapValue] = Json.format[Schema.MapValue]
	given fmtAggregateResponse: Format[Schema.AggregateResponse] = Json.format[Schema.AggregateResponse]
	given fmtAggregateBucket: Format[Schema.AggregateBucket] = Json.format[Schema.AggregateBucket]
	given fmtAggregateBucketTypeEnum: Format[Schema.AggregateBucket.TypeEnum] = JsonEnumFormat[Schema.AggregateBucket.TypeEnum]
	given fmtListDataPointChangesResponse: Format[Schema.ListDataPointChangesResponse] = Json.format[Schema.ListDataPointChangesResponse]
	given fmtDeviceTypeEnum: Format[Schema.Device.TypeEnum] = JsonEnumFormat[Schema.Device.TypeEnum]
	given fmtListDataSourcesResponse: Format[Schema.ListDataSourcesResponse] = Json.format[Schema.ListDataSourcesResponse]
}
