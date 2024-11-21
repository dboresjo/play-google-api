package com.boresjo.play.api.google.fcmdata

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtGoogleFirebaseFcmDataV1beta1Data: Format[Schema.GoogleFirebaseFcmDataV1beta1Data] = Json.format[Schema.GoogleFirebaseFcmDataV1beta1Data]
	given fmtGoogleFirebaseFcmDataV1beta1MessageOutcomePercents: Format[Schema.GoogleFirebaseFcmDataV1beta1MessageOutcomePercents] = Json.format[Schema.GoogleFirebaseFcmDataV1beta1MessageOutcomePercents]
	given fmtGoogleFirebaseFcmDataV1beta1DeliveryPerformancePercents: Format[Schema.GoogleFirebaseFcmDataV1beta1DeliveryPerformancePercents] = Json.format[Schema.GoogleFirebaseFcmDataV1beta1DeliveryPerformancePercents]
	given fmtGoogleFirebaseFcmDataV1beta1ProxyNotificationInsightPercents: Format[Schema.GoogleFirebaseFcmDataV1beta1ProxyNotificationInsightPercents] = Json.format[Schema.GoogleFirebaseFcmDataV1beta1ProxyNotificationInsightPercents]
	given fmtGoogleFirebaseFcmDataV1beta1MessageInsightPercents: Format[Schema.GoogleFirebaseFcmDataV1beta1MessageInsightPercents] = Json.format[Schema.GoogleFirebaseFcmDataV1beta1MessageInsightPercents]
	given fmtGoogleTypeDate: Format[Schema.GoogleTypeDate] = Json.format[Schema.GoogleTypeDate]
	given fmtGoogleFirebaseFcmDataV1beta1ListAndroidDeliveryDataResponse: Format[Schema.GoogleFirebaseFcmDataV1beta1ListAndroidDeliveryDataResponse] = Json.format[Schema.GoogleFirebaseFcmDataV1beta1ListAndroidDeliveryDataResponse]
	given fmtGoogleFirebaseFcmDataV1beta1AndroidDeliveryData: Format[Schema.GoogleFirebaseFcmDataV1beta1AndroidDeliveryData] = Json.format[Schema.GoogleFirebaseFcmDataV1beta1AndroidDeliveryData]
}
