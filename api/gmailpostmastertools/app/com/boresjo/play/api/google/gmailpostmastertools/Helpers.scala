package com.boresjo.play.api.google.gmailpostmastertools

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putSchemaTrafficStatsDomainReputationEnum: Conversion[Schema.TrafficStats.DomainReputationEnum, Option[Schema.TrafficStats.DomainReputationEnum]] = (fun: Schema.TrafficStats.DomainReputationEnum) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putListSchemaIpReputation: Conversion[List[Schema.IpReputation], Option[List[Schema.IpReputation]]] = (fun: List[Schema.IpReputation]) => Option(fun)
		given putListSchemaDeliveryError: Conversion[List[Schema.DeliveryError], Option[List[Schema.DeliveryError]]] = (fun: List[Schema.DeliveryError]) => Option(fun)
		given putListSchemaFeedbackLoop: Conversion[List[Schema.FeedbackLoop], Option[List[Schema.FeedbackLoop]]] = (fun: List[Schema.FeedbackLoop]) => Option(fun)
		given putSchemaDeliveryErrorErrorTypeEnum: Conversion[Schema.DeliveryError.ErrorTypeEnum, Option[Schema.DeliveryError.ErrorTypeEnum]] = (fun: Schema.DeliveryError.ErrorTypeEnum) => Option(fun)
		given putSchemaDeliveryErrorErrorClassEnum: Conversion[Schema.DeliveryError.ErrorClassEnum, Option[Schema.DeliveryError.ErrorClassEnum]] = (fun: Schema.DeliveryError.ErrorClassEnum) => Option(fun)
		given putListSchemaDomain: Conversion[List[Schema.Domain], Option[List[Schema.Domain]]] = (fun: List[Schema.Domain]) => Option(fun)
		given putListSchemaTrafficStats: Conversion[List[Schema.TrafficStats], Option[List[Schema.TrafficStats]]] = (fun: List[Schema.TrafficStats]) => Option(fun)
		given putSchemaIpReputationReputationEnum: Conversion[Schema.IpReputation.ReputationEnum, Option[Schema.IpReputation.ReputationEnum]] = (fun: Schema.IpReputation.ReputationEnum) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaDomainPermissionEnum: Conversion[Schema.Domain.PermissionEnum, Option[Schema.Domain.PermissionEnum]] = (fun: Schema.Domain.PermissionEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
