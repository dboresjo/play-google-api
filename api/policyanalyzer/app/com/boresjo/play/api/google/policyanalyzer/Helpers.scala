package com.boresjo.play.api.google.policyanalyzer

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListSchemaGoogleCloudPolicyanalyzerV1Activity: Conversion[List[Schema.GoogleCloudPolicyanalyzerV1Activity], Option[List[Schema.GoogleCloudPolicyanalyzerV1Activity]]] = (fun: List[Schema.GoogleCloudPolicyanalyzerV1Activity]) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putSchemaGoogleCloudPolicyanalyzerV1ObservationPeriod: Conversion[Schema.GoogleCloudPolicyanalyzerV1ObservationPeriod, Option[Schema.GoogleCloudPolicyanalyzerV1ObservationPeriod]] = (fun: Schema.GoogleCloudPolicyanalyzerV1ObservationPeriod) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
