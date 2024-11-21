package com.boresjo.play.api.google.connectors

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putListSchemaAction: Conversion[List[Schema.Action], Option[List[Schema.Action]]] = (fun: List[Schema.Action]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListSchemaInputParameter: Conversion[List[Schema.InputParameter], Option[List[Schema.InputParameter]]] = (fun: List[Schema.InputParameter]) => Option(fun)
		given putListSchemaResultMetadata: Conversion[List[Schema.ResultMetadata], Option[List[Schema.ResultMetadata]]] = (fun: List[Schema.ResultMetadata]) => Option(fun)
		given putSchemaJsonSchema: Conversion[Schema.JsonSchema, Option[Schema.JsonSchema]] = (fun: Schema.JsonSchema) => Option(fun)
		given putSchemaInputParameterDataTypeEnum: Conversion[Schema.InputParameter.DataTypeEnum, Option[Schema.InputParameter.DataTypeEnum]] = (fun: Schema.InputParameter.DataTypeEnum) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putJsValue: Conversion[JsValue, Option[JsValue]] = (fun: JsValue) => Option(fun)
		given putMapStringSchemaJsonSchema: Conversion[Map[String, Schema.JsonSchema], Option[Map[String, Schema.JsonSchema]]] = (fun: Map[String, Schema.JsonSchema]) => Option(fun)
		given putListJsValue: Conversion[List[JsValue], Option[List[JsValue]]] = (fun: List[JsValue]) => Option(fun)
		given putSchemaJsonSchemaJdbcTypeEnum: Conversion[Schema.JsonSchema.JdbcTypeEnum, Option[Schema.JsonSchema.JdbcTypeEnum]] = (fun: Schema.JsonSchema.JdbcTypeEnum) => Option(fun)
		given putSchemaResultMetadataDataTypeEnum: Conversion[Schema.ResultMetadata.DataTypeEnum, Option[Schema.ResultMetadata.DataTypeEnum]] = (fun: Schema.ResultMetadata.DataTypeEnum) => Option(fun)
		given putSchemaCheckStatusResponseStateEnum: Conversion[Schema.CheckStatusResponse.StateEnum, Option[Schema.CheckStatusResponse.StateEnum]] = (fun: Schema.CheckStatusResponse.StateEnum) => Option(fun)
		given putSchemaAuthCodeData: Conversion[Schema.AuthCodeData, Option[Schema.AuthCodeData]] = (fun: Schema.AuthCodeData) => Option(fun)
		given putSchemaAccessCredentials: Conversion[Schema.AccessCredentials, Option[Schema.AccessCredentials]] = (fun: Schema.AccessCredentials) => Option(fun)
		given putListSchemaField: Conversion[List[Schema.Field], Option[List[Schema.Field]]] = (fun: List[Schema.Field]) => Option(fun)
		given putListSchemaEntityTypeOperationsEnum: Conversion[List[Schema.EntityType.OperationsEnum], Option[List[Schema.EntityType.OperationsEnum]]] = (fun: List[Schema.EntityType.OperationsEnum]) => Option(fun)
		given putSchemaFieldDataTypeEnum: Conversion[Schema.Field.DataTypeEnum, Option[Schema.Field.DataTypeEnum]] = (fun: Schema.Field.DataTypeEnum) => Option(fun)
		given putSchemaReference: Conversion[Schema.Reference, Option[Schema.Reference]] = (fun: Schema.Reference) => Option(fun)
		given putListSchemaEntityType: Conversion[List[Schema.EntityType], Option[List[Schema.EntityType]]] = (fun: List[Schema.EntityType]) => Option(fun)
		given putListSchemaEntity: Conversion[List[Schema.Entity], Option[List[Schema.Entity]]] = (fun: List[Schema.Entity]) => Option(fun)
		given putSchemaQuery: Conversion[Schema.Query, Option[Schema.Query]] = (fun: Schema.Query) => Option(fun)
		given putListSchemaQueryParameter: Conversion[List[Schema.QueryParameter], Option[List[Schema.QueryParameter]]] = (fun: List[Schema.QueryParameter]) => Option(fun)
		given putSchemaQueryParameterDataTypeEnum: Conversion[Schema.QueryParameter.DataTypeEnum, Option[Schema.QueryParameter.DataTypeEnum]] = (fun: Schema.QueryParameter.DataTypeEnum) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaInstanceStateEnum: Conversion[Schema.Instance.StateEnum, Option[Schema.Instance.StateEnum]] = (fun: Schema.Instance.StateEnum) => Option(fun)
		given putListSchemaProvisionedResource: Conversion[List[Schema.ProvisionedResource], Option[List[Schema.ProvisionedResource]]] = (fun: List[Schema.ProvisionedResource]) => Option(fun)
		given putSchemaSloMetadata: Conversion[Schema.SloMetadata, Option[Schema.SloMetadata]] = (fun: Schema.SloMetadata) => Option(fun)
		given putMapStringSchemaMaintenanceSchedule: Conversion[Map[String, Schema.MaintenanceSchedule], Option[Map[String, Schema.MaintenanceSchedule]]] = (fun: Map[String, Schema.MaintenanceSchedule]) => Option(fun)
		given putSchemaMaintenanceSettings: Conversion[Schema.MaintenanceSettings, Option[Schema.MaintenanceSettings]] = (fun: Schema.MaintenanceSettings) => Option(fun)
		given putMapStringSchemaNotificationParameter: Conversion[Map[String, Schema.NotificationParameter], Option[Map[String, Schema.NotificationParameter]]] = (fun: Map[String, Schema.NotificationParameter]) => Option(fun)
		given putListSchemaNodeSloMetadata: Conversion[List[Schema.NodeSloMetadata], Option[List[Schema.NodeSloMetadata]]] = (fun: List[Schema.NodeSloMetadata]) => Option(fun)
		given putSchemaPerSliSloEligibility: Conversion[Schema.PerSliSloEligibility, Option[Schema.PerSliSloEligibility]] = (fun: Schema.PerSliSloEligibility) => Option(fun)
		given putMapStringSchemaSloEligibility: Conversion[Map[String, Schema.SloEligibility], Option[Map[String, Schema.SloEligibility]]] = (fun: Map[String, Schema.SloEligibility]) => Option(fun)
		given putMapStringSchemaMaintenancePolicy: Conversion[Map[String, Schema.MaintenancePolicy], Option[Map[String, Schema.MaintenancePolicy]]] = (fun: Map[String, Schema.MaintenancePolicy]) => Option(fun)
		given putSchemaMaintenancePolicyStateEnum: Conversion[Schema.MaintenancePolicy.StateEnum, Option[Schema.MaintenancePolicy.StateEnum]] = (fun: Schema.MaintenancePolicy.StateEnum) => Option(fun)
		given putSchemaUpdatePolicy: Conversion[Schema.UpdatePolicy, Option[Schema.UpdatePolicy]] = (fun: Schema.UpdatePolicy) => Option(fun)
		given putSchemaMaintenanceWindow: Conversion[Schema.MaintenanceWindow, Option[Schema.MaintenanceWindow]] = (fun: Schema.MaintenanceWindow) => Option(fun)
		given putSchemaUpdatePolicyChannelEnum: Conversion[Schema.UpdatePolicy.ChannelEnum, Option[Schema.UpdatePolicy.ChannelEnum]] = (fun: Schema.UpdatePolicy.ChannelEnum) => Option(fun)
		given putListSchemaDenyMaintenancePeriod: Conversion[List[Schema.DenyMaintenancePeriod], Option[List[Schema.DenyMaintenancePeriod]]] = (fun: List[Schema.DenyMaintenancePeriod]) => Option(fun)
		given putSchemaDailyCycle: Conversion[Schema.DailyCycle, Option[Schema.DailyCycle]] = (fun: Schema.DailyCycle) => Option(fun)
		given putSchemaWeeklyCycle: Conversion[Schema.WeeklyCycle, Option[Schema.WeeklyCycle]] = (fun: Schema.WeeklyCycle) => Option(fun)
		given putSchemaTimeOfDay: Conversion[Schema.TimeOfDay, Option[Schema.TimeOfDay]] = (fun: Schema.TimeOfDay) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListSchemaSchedule: Conversion[List[Schema.Schedule], Option[List[Schema.Schedule]]] = (fun: List[Schema.Schedule]) => Option(fun)
		given putSchemaScheduleDayEnum: Conversion[Schema.Schedule.DayEnum, Option[Schema.Schedule.DayEnum]] = (fun: Schema.Schedule.DayEnum) => Option(fun)
		given putSchemaDate: Conversion[Schema.Date, Option[Schema.Date]] = (fun: Schema.Date) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
