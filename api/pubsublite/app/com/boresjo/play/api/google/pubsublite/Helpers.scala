package com.boresjo.play.api.google.pubsublite

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaOperation: Conversion[List[Schema.Operation], Option[List[Schema.Operation]]] = (fun: List[Schema.Operation]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putSchemaPartitionConfig: Conversion[Schema.PartitionConfig, Option[Schema.PartitionConfig]] = (fun: Schema.PartitionConfig) => Option(fun)
		given putSchemaRetentionConfig: Conversion[Schema.RetentionConfig, Option[Schema.RetentionConfig]] = (fun: Schema.RetentionConfig) => Option(fun)
		given putSchemaReservationConfig: Conversion[Schema.ReservationConfig, Option[Schema.ReservationConfig]] = (fun: Schema.ReservationConfig) => Option(fun)
		given putSchemaCapacity: Conversion[Schema.Capacity, Option[Schema.Capacity]] = (fun: Schema.Capacity) => Option(fun)
		given putListSchemaTopic: Conversion[List[Schema.Topic], Option[List[Schema.Topic]]] = (fun: List[Schema.Topic]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaDeliveryConfig: Conversion[Schema.DeliveryConfig, Option[Schema.DeliveryConfig]] = (fun: Schema.DeliveryConfig) => Option(fun)
		given putSchemaExportConfig: Conversion[Schema.ExportConfig, Option[Schema.ExportConfig]] = (fun: Schema.ExportConfig) => Option(fun)
		given putSchemaDeliveryConfigDeliveryRequirementEnum: Conversion[Schema.DeliveryConfig.DeliveryRequirementEnum, Option[Schema.DeliveryConfig.DeliveryRequirementEnum]] = (fun: Schema.DeliveryConfig.DeliveryRequirementEnum) => Option(fun)
		given putSchemaExportConfigDesiredStateEnum: Conversion[Schema.ExportConfig.DesiredStateEnum, Option[Schema.ExportConfig.DesiredStateEnum]] = (fun: Schema.ExportConfig.DesiredStateEnum) => Option(fun)
		given putSchemaExportConfigCurrentStateEnum: Conversion[Schema.ExportConfig.CurrentStateEnum, Option[Schema.ExportConfig.CurrentStateEnum]] = (fun: Schema.ExportConfig.CurrentStateEnum) => Option(fun)
		given putSchemaPubSubConfig: Conversion[Schema.PubSubConfig, Option[Schema.PubSubConfig]] = (fun: Schema.PubSubConfig) => Option(fun)
		given putListSchemaSubscription: Conversion[List[Schema.Subscription], Option[List[Schema.Subscription]]] = (fun: List[Schema.Subscription]) => Option(fun)
		given putSchemaSeekSubscriptionRequestNamedTargetEnum: Conversion[Schema.SeekSubscriptionRequest.NamedTargetEnum, Option[Schema.SeekSubscriptionRequest.NamedTargetEnum]] = (fun: Schema.SeekSubscriptionRequest.NamedTargetEnum) => Option(fun)
		given putSchemaTimeTarget: Conversion[Schema.TimeTarget, Option[Schema.TimeTarget]] = (fun: Schema.TimeTarget) => Option(fun)
		given putListSchemaReservation: Conversion[List[Schema.Reservation], Option[List[Schema.Reservation]]] = (fun: List[Schema.Reservation]) => Option(fun)
		given putSchemaCursor: Conversion[Schema.Cursor, Option[Schema.Cursor]] = (fun: Schema.Cursor) => Option(fun)
		given putListSchemaPartitionCursor: Conversion[List[Schema.PartitionCursor], Option[List[Schema.PartitionCursor]]] = (fun: List[Schema.PartitionCursor]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
