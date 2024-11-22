package com.boresjo.play.api.google.fcmdata

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putSchemaGoogleFirebaseFcmDataV1beta1MessageOutcomePercents: Conversion[Schema.GoogleFirebaseFcmDataV1beta1MessageOutcomePercents, Option[Schema.GoogleFirebaseFcmDataV1beta1MessageOutcomePercents]] = (fun: Schema.GoogleFirebaseFcmDataV1beta1MessageOutcomePercents) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaGoogleFirebaseFcmDataV1beta1DeliveryPerformancePercents: Conversion[Schema.GoogleFirebaseFcmDataV1beta1DeliveryPerformancePercents, Option[Schema.GoogleFirebaseFcmDataV1beta1DeliveryPerformancePercents]] = (fun: Schema.GoogleFirebaseFcmDataV1beta1DeliveryPerformancePercents) => Option(fun)
		given putSchemaGoogleFirebaseFcmDataV1beta1ProxyNotificationInsightPercents: Conversion[Schema.GoogleFirebaseFcmDataV1beta1ProxyNotificationInsightPercents, Option[Schema.GoogleFirebaseFcmDataV1beta1ProxyNotificationInsightPercents]] = (fun: Schema.GoogleFirebaseFcmDataV1beta1ProxyNotificationInsightPercents) => Option(fun)
		given putSchemaGoogleFirebaseFcmDataV1beta1MessageInsightPercents: Conversion[Schema.GoogleFirebaseFcmDataV1beta1MessageInsightPercents, Option[Schema.GoogleFirebaseFcmDataV1beta1MessageInsightPercents]] = (fun: Schema.GoogleFirebaseFcmDataV1beta1MessageInsightPercents) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListSchemaGoogleFirebaseFcmDataV1beta1AndroidDeliveryData: Conversion[List[Schema.GoogleFirebaseFcmDataV1beta1AndroidDeliveryData], Option[List[Schema.GoogleFirebaseFcmDataV1beta1AndroidDeliveryData]]] = (fun: List[Schema.GoogleFirebaseFcmDataV1beta1AndroidDeliveryData]) => Option(fun)
		given putSchemaGoogleFirebaseFcmDataV1beta1Data: Conversion[Schema.GoogleFirebaseFcmDataV1beta1Data, Option[Schema.GoogleFirebaseFcmDataV1beta1Data]] = (fun: Schema.GoogleFirebaseFcmDataV1beta1Data) => Option(fun)
		given putSchemaGoogleTypeDate: Conversion[Schema.GoogleTypeDate, Option[Schema.GoogleTypeDate]] = (fun: Schema.GoogleTypeDate) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
