package com.boresjo.play.api.google.localservices

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaGoogleAdsHomeservicesLocalservicesV1AggregatorInfo: Conversion[Schema.GoogleAdsHomeservicesLocalservicesV1AggregatorInfo, Option[Schema.GoogleAdsHomeservicesLocalservicesV1AggregatorInfo]] = (fun: Schema.GoogleAdsHomeservicesLocalservicesV1AggregatorInfo) => Option(fun)
		given putListSchemaGoogleAdsHomeservicesLocalservicesV1DetailedLeadReport: Conversion[List[Schema.GoogleAdsHomeservicesLocalservicesV1DetailedLeadReport], Option[List[Schema.GoogleAdsHomeservicesLocalservicesV1DetailedLeadReport]]] = (fun: List[Schema.GoogleAdsHomeservicesLocalservicesV1DetailedLeadReport]) => Option(fun)
		given putListSchemaGoogleAdsHomeservicesLocalservicesV1AccountReport: Conversion[List[Schema.GoogleAdsHomeservicesLocalservicesV1AccountReport], Option[List[Schema.GoogleAdsHomeservicesLocalservicesV1AccountReport]]] = (fun: List[Schema.GoogleAdsHomeservicesLocalservicesV1AccountReport]) => Option(fun)
		given putSchemaGoogleTypeTimeZone: Conversion[Schema.GoogleTypeTimeZone, Option[Schema.GoogleTypeTimeZone]] = (fun: Schema.GoogleTypeTimeZone) => Option(fun)
		given putSchemaGoogleAdsHomeservicesLocalservicesV1DetailedLeadReportChargeStatusEnum: Conversion[Schema.GoogleAdsHomeservicesLocalservicesV1DetailedLeadReport.ChargeStatusEnum, Option[Schema.GoogleAdsHomeservicesLocalservicesV1DetailedLeadReport.ChargeStatusEnum]] = (fun: Schema.GoogleAdsHomeservicesLocalservicesV1DetailedLeadReport.ChargeStatusEnum) => Option(fun)
		given putSchemaGoogleAdsHomeservicesLocalservicesV1MessageLead: Conversion[Schema.GoogleAdsHomeservicesLocalservicesV1MessageLead, Option[Schema.GoogleAdsHomeservicesLocalservicesV1MessageLead]] = (fun: Schema.GoogleAdsHomeservicesLocalservicesV1MessageLead) => Option(fun)
		given putSchemaGoogleAdsHomeservicesLocalservicesV1PhoneLead: Conversion[Schema.GoogleAdsHomeservicesLocalservicesV1PhoneLead, Option[Schema.GoogleAdsHomeservicesLocalservicesV1PhoneLead]] = (fun: Schema.GoogleAdsHomeservicesLocalservicesV1PhoneLead) => Option(fun)
		given putSchemaGoogleAdsHomeservicesLocalservicesV1BookingLead: Conversion[Schema.GoogleAdsHomeservicesLocalservicesV1BookingLead, Option[Schema.GoogleAdsHomeservicesLocalservicesV1BookingLead]] = (fun: Schema.GoogleAdsHomeservicesLocalservicesV1BookingLead) => Option(fun)
		given putSchemaGoogleAdsHomeservicesLocalservicesV1DetailedLeadReportLeadTypeEnum: Conversion[Schema.GoogleAdsHomeservicesLocalservicesV1DetailedLeadReport.LeadTypeEnum, Option[Schema.GoogleAdsHomeservicesLocalservicesV1DetailedLeadReport.LeadTypeEnum]] = (fun: Schema.GoogleAdsHomeservicesLocalservicesV1DetailedLeadReport.LeadTypeEnum) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
