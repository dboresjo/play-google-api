package com.boresjo.play.api.google.policyanalyzer

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtGoogleCloudPolicyanalyzerV1ObservationPeriod: Format[Schema.GoogleCloudPolicyanalyzerV1ObservationPeriod] = Json.format[Schema.GoogleCloudPolicyanalyzerV1ObservationPeriod]
	given fmtGoogleCloudPolicyanalyzerV1QueryActivityResponse: Format[Schema.GoogleCloudPolicyanalyzerV1QueryActivityResponse] = Json.format[Schema.GoogleCloudPolicyanalyzerV1QueryActivityResponse]
	given fmtGoogleCloudPolicyanalyzerV1Activity: Format[Schema.GoogleCloudPolicyanalyzerV1Activity] = Json.format[Schema.GoogleCloudPolicyanalyzerV1Activity]
}
