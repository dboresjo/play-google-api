package com.boresjo.play.api.google.bigqueryreservation

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaAutoscale: Conversion[Schema.Autoscale, Option[Schema.Autoscale]] = (fun: Schema.Autoscale) => Option(fun)
		given putSchemaReservationEditionEnum: Conversion[Schema.Reservation.EditionEnum, Option[Schema.Reservation.EditionEnum]] = (fun: Schema.Reservation.EditionEnum) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putListSchemaReservation: Conversion[List[Schema.Reservation], Option[List[Schema.Reservation]]] = (fun: List[Schema.Reservation]) => Option(fun)
		given putSchemaCapacityCommitmentPlanEnum: Conversion[Schema.CapacityCommitment.PlanEnum, Option[Schema.CapacityCommitment.PlanEnum]] = (fun: Schema.CapacityCommitment.PlanEnum) => Option(fun)
		given putSchemaCapacityCommitmentStateEnum: Conversion[Schema.CapacityCommitment.StateEnum, Option[Schema.CapacityCommitment.StateEnum]] = (fun: Schema.CapacityCommitment.StateEnum) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putSchemaCapacityCommitmentRenewalPlanEnum: Conversion[Schema.CapacityCommitment.RenewalPlanEnum, Option[Schema.CapacityCommitment.RenewalPlanEnum]] = (fun: Schema.CapacityCommitment.RenewalPlanEnum) => Option(fun)
		given putSchemaCapacityCommitmentEditionEnum: Conversion[Schema.CapacityCommitment.EditionEnum, Option[Schema.CapacityCommitment.EditionEnum]] = (fun: Schema.CapacityCommitment.EditionEnum) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putListSchemaCapacityCommitment: Conversion[List[Schema.CapacityCommitment], Option[List[Schema.CapacityCommitment]]] = (fun: List[Schema.CapacityCommitment]) => Option(fun)
		given putSchemaCapacityCommitment: Conversion[Schema.CapacityCommitment, Option[Schema.CapacityCommitment]] = (fun: Schema.CapacityCommitment) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaAssignmentJobTypeEnum: Conversion[Schema.Assignment.JobTypeEnum, Option[Schema.Assignment.JobTypeEnum]] = (fun: Schema.Assignment.JobTypeEnum) => Option(fun)
		given putSchemaAssignmentStateEnum: Conversion[Schema.Assignment.StateEnum, Option[Schema.Assignment.StateEnum]] = (fun: Schema.Assignment.StateEnum) => Option(fun)
		given putListSchemaAssignment: Conversion[List[Schema.Assignment], Option[List[Schema.Assignment]]] = (fun: List[Schema.Assignment]) => Option(fun)
		given putListSchemaTableReference: Conversion[List[Schema.TableReference], Option[List[Schema.TableReference]]] = (fun: List[Schema.TableReference]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
