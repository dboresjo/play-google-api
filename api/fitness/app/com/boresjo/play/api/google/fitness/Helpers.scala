package com.boresjo.play.api.google.fitness

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaDataTypeField: Conversion[List[Schema.DataTypeField], Option[List[Schema.DataTypeField]]] = (fun: List[Schema.DataTypeField]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaBucketByActivity: Conversion[Schema.BucketByActivity, Option[Schema.BucketByActivity]] = (fun: Schema.BucketByActivity) => Option(fun)
		given putSchemaBucketBySession: Conversion[Schema.BucketBySession, Option[Schema.BucketBySession]] = (fun: Schema.BucketBySession) => Option(fun)
		given putSchemaBucketByTime: Conversion[Schema.BucketByTime, Option[Schema.BucketByTime]] = (fun: Schema.BucketByTime) => Option(fun)
		given putListSchemaAggregateBy: Conversion[List[Schema.AggregateBy], Option[List[Schema.AggregateBy]]] = (fun: List[Schema.AggregateBy]) => Option(fun)
		given putListSchemaAggregateRequestFilteredDataQualityStandardEnum: Conversion[List[Schema.AggregateRequest.FilteredDataQualityStandardEnum], Option[List[Schema.AggregateRequest.FilteredDataQualityStandardEnum]]] = (fun: List[Schema.AggregateRequest.FilteredDataQualityStandardEnum]) => Option(fun)
		given putSchemaBucketByTimePeriodTypeEnum: Conversion[Schema.BucketByTimePeriod.TypeEnum, Option[Schema.BucketByTimePeriod.TypeEnum]] = (fun: Schema.BucketByTimePeriod.TypeEnum) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListSchemaValueMapValEntry: Conversion[List[Schema.ValueMapValEntry], Option[List[Schema.ValueMapValEntry]]] = (fun: List[Schema.ValueMapValEntry]) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putListSchemaDataPoint: Conversion[List[Schema.DataPoint], Option[List[Schema.DataPoint]]] = (fun: List[Schema.DataPoint]) => Option(fun)
		given putSchemaDataTypeFieldFormatEnum: Conversion[Schema.DataTypeField.FormatEnum, Option[Schema.DataTypeField.FormatEnum]] = (fun: Schema.DataTypeField.FormatEnum) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaApplication: Conversion[Schema.Application, Option[Schema.Application]] = (fun: Schema.Application) => Option(fun)
		given putListSchemaDataSourceDataQualityStandardEnum: Conversion[List[Schema.DataSource.DataQualityStandardEnum], Option[List[Schema.DataSource.DataQualityStandardEnum]]] = (fun: List[Schema.DataSource.DataQualityStandardEnum]) => Option(fun)
		given putSchemaDataType: Conversion[Schema.DataType, Option[Schema.DataType]] = (fun: Schema.DataType) => Option(fun)
		given putSchemaDevice: Conversion[Schema.Device, Option[Schema.Device]] = (fun: Schema.Device) => Option(fun)
		given putSchemaDataSourceTypeEnum: Conversion[Schema.DataSource.TypeEnum, Option[Schema.DataSource.TypeEnum]] = (fun: Schema.DataSource.TypeEnum) => Option(fun)
		given putListSchemaValue: Conversion[List[Schema.Value], Option[List[Schema.Value]]] = (fun: List[Schema.Value]) => Option(fun)
		given putListSchemaSession: Conversion[List[Schema.Session], Option[List[Schema.Session]]] = (fun: List[Schema.Session]) => Option(fun)
		given putSchemaMapValue: Conversion[Schema.MapValue, Option[Schema.MapValue]] = (fun: Schema.MapValue) => Option(fun)
		given putListSchemaAggregateBucket: Conversion[List[Schema.AggregateBucket], Option[List[Schema.AggregateBucket]]] = (fun: List[Schema.AggregateBucket]) => Option(fun)
		given putSchemaBucketByTimePeriod: Conversion[Schema.BucketByTimePeriod, Option[Schema.BucketByTimePeriod]] = (fun: Schema.BucketByTimePeriod) => Option(fun)
		given putSchemaSession: Conversion[Schema.Session, Option[Schema.Session]] = (fun: Schema.Session) => Option(fun)
		given putListSchemaDataset: Conversion[List[Schema.Dataset], Option[List[Schema.Dataset]]] = (fun: List[Schema.Dataset]) => Option(fun)
		given putSchemaAggregateBucketTypeEnum: Conversion[Schema.AggregateBucket.TypeEnum, Option[Schema.AggregateBucket.TypeEnum]] = (fun: Schema.AggregateBucket.TypeEnum) => Option(fun)
		given putSchemaDeviceTypeEnum: Conversion[Schema.Device.TypeEnum, Option[Schema.Device.TypeEnum]] = (fun: Schema.Device.TypeEnum) => Option(fun)
		given putListSchemaDataSource: Conversion[List[Schema.DataSource], Option[List[Schema.DataSource]]] = (fun: List[Schema.DataSource]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
