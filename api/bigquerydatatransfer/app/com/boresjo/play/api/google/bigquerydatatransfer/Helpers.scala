package com.boresjo.play.api.google.bigquerydatatransfer

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaDataSourceTransferTypeEnum: Conversion[Schema.DataSource.TransferTypeEnum, Option[Schema.DataSource.TransferTypeEnum]] = (fun: Schema.DataSource.TransferTypeEnum) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListSchemaDataSourceParameter: Conversion[List[Schema.DataSourceParameter], Option[List[Schema.DataSourceParameter]]] = (fun: List[Schema.DataSourceParameter]) => Option(fun)
		given putSchemaDataSourceAuthorizationTypeEnum: Conversion[Schema.DataSource.AuthorizationTypeEnum, Option[Schema.DataSource.AuthorizationTypeEnum]] = (fun: Schema.DataSource.AuthorizationTypeEnum) => Option(fun)
		given putSchemaDataSourceDataRefreshTypeEnum: Conversion[Schema.DataSource.DataRefreshTypeEnum, Option[Schema.DataSource.DataRefreshTypeEnum]] = (fun: Schema.DataSource.DataRefreshTypeEnum) => Option(fun)
		given putSchemaDataSourceParameterTypeEnum: Conversion[Schema.DataSourceParameter.TypeEnum, Option[Schema.DataSourceParameter.TypeEnum]] = (fun: Schema.DataSourceParameter.TypeEnum) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putListSchemaDataSource: Conversion[List[Schema.DataSource], Option[List[Schema.DataSource]]] = (fun: List[Schema.DataSource]) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putSchemaScheduleOptions: Conversion[Schema.ScheduleOptions, Option[Schema.ScheduleOptions]] = (fun: Schema.ScheduleOptions) => Option(fun)
		given putSchemaScheduleOptionsV2: Conversion[Schema.ScheduleOptionsV2, Option[Schema.ScheduleOptionsV2]] = (fun: Schema.ScheduleOptionsV2) => Option(fun)
		given putSchemaTransferConfigStateEnum: Conversion[Schema.TransferConfig.StateEnum, Option[Schema.TransferConfig.StateEnum]] = (fun: Schema.TransferConfig.StateEnum) => Option(fun)
		given putSchemaEmailPreferences: Conversion[Schema.EmailPreferences, Option[Schema.EmailPreferences]] = (fun: Schema.EmailPreferences) => Option(fun)
		given putSchemaUserInfo: Conversion[Schema.UserInfo, Option[Schema.UserInfo]] = (fun: Schema.UserInfo) => Option(fun)
		given putSchemaEncryptionConfiguration: Conversion[Schema.EncryptionConfiguration, Option[Schema.EncryptionConfiguration]] = (fun: Schema.EncryptionConfiguration) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putSchemaTimeBasedSchedule: Conversion[Schema.TimeBasedSchedule, Option[Schema.TimeBasedSchedule]] = (fun: Schema.TimeBasedSchedule) => Option(fun)
		given putSchemaManualSchedule: Conversion[Schema.ManualSchedule, Option[Schema.ManualSchedule]] = (fun: Schema.ManualSchedule) => Option(fun)
		given putSchemaEventDrivenSchedule: Conversion[Schema.EventDrivenSchedule, Option[Schema.EventDrivenSchedule]] = (fun: Schema.EventDrivenSchedule) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putListSchemaTransferConfig: Conversion[List[Schema.TransferConfig], Option[List[Schema.TransferConfig]]] = (fun: List[Schema.TransferConfig]) => Option(fun)
		given putListSchemaTransferRun: Conversion[List[Schema.TransferRun], Option[List[Schema.TransferRun]]] = (fun: List[Schema.TransferRun]) => Option(fun)
		given putSchemaTransferRunStateEnum: Conversion[Schema.TransferRun.StateEnum, Option[Schema.TransferRun.StateEnum]] = (fun: Schema.TransferRun.StateEnum) => Option(fun)
		given putSchemaTimeRange: Conversion[Schema.TimeRange, Option[Schema.TimeRange]] = (fun: Schema.TimeRange) => Option(fun)
		given putListSchemaTransferMessage: Conversion[List[Schema.TransferMessage], Option[List[Schema.TransferMessage]]] = (fun: List[Schema.TransferMessage]) => Option(fun)
		given putSchemaTransferMessageSeverityEnum: Conversion[Schema.TransferMessage.SeverityEnum, Option[Schema.TransferMessage.SeverityEnum]] = (fun: Schema.TransferMessage.SeverityEnum) => Option(fun)
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
