package com.boresjo.play.api.google.bigquerydatapolicy

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaDataMaskingPolicy: Conversion[Schema.DataMaskingPolicy, Option[Schema.DataMaskingPolicy]] = (fun: Schema.DataMaskingPolicy) => Option(fun)
		given putSchemaDataPolicyDataPolicyTypeEnum: Conversion[Schema.DataPolicy.DataPolicyTypeEnum, Option[Schema.DataPolicy.DataPolicyTypeEnum]] = (fun: Schema.DataPolicy.DataPolicyTypeEnum) => Option(fun)
		given putSchemaDataMaskingPolicyPredefinedExpressionEnum: Conversion[Schema.DataMaskingPolicy.PredefinedExpressionEnum, Option[Schema.DataMaskingPolicy.PredefinedExpressionEnum]] = (fun: Schema.DataMaskingPolicy.PredefinedExpressionEnum) => Option(fun)
		given putListSchemaDataPolicy: Conversion[List[Schema.DataPolicy], Option[List[Schema.DataPolicy]]] = (fun: List[Schema.DataPolicy]) => Option(fun)
		given putSchemaGetPolicyOptions: Conversion[Schema.GetPolicyOptions, Option[Schema.GetPolicyOptions]] = (fun: Schema.GetPolicyOptions) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListSchemaBinding: Conversion[List[Schema.Binding], Option[List[Schema.Binding]]] = (fun: List[Schema.Binding]) => Option(fun)
		given putListSchemaAuditConfig: Conversion[List[Schema.AuditConfig], Option[List[Schema.AuditConfig]]] = (fun: List[Schema.AuditConfig]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaExpr: Conversion[Schema.Expr, Option[Schema.Expr]] = (fun: Schema.Expr) => Option(fun)
		given putListSchemaAuditLogConfig: Conversion[List[Schema.AuditLogConfig], Option[List[Schema.AuditLogConfig]]] = (fun: List[Schema.AuditLogConfig]) => Option(fun)
		given putSchemaAuditLogConfigLogTypeEnum: Conversion[Schema.AuditLogConfig.LogTypeEnum, Option[Schema.AuditLogConfig.LogTypeEnum]] = (fun: Schema.AuditLogConfig.LogTypeEnum) => Option(fun)
		given putSchemaPolicy: Conversion[Schema.Policy, Option[Schema.Policy]] = (fun: Schema.Policy) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
