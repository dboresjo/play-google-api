package com.boresjo.play.api.google.apigateway

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaApigatewayOperation: Conversion[List[Schema.ApigatewayOperation], Option[List[Schema.ApigatewayOperation]]] = (fun: List[Schema.ApigatewayOperation]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaApigatewayStatus: Conversion[Schema.ApigatewayStatus, Option[Schema.ApigatewayStatus]] = (fun: Schema.ApigatewayStatus) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putListSchemaApigatewayGateway: Conversion[List[Schema.ApigatewayGateway], Option[List[Schema.ApigatewayGateway]]] = (fun: List[Schema.ApigatewayGateway]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putSchemaApigatewayGatewayStateEnum: Conversion[Schema.ApigatewayGateway.StateEnum, Option[Schema.ApigatewayGateway.StateEnum]] = (fun: Schema.ApigatewayGateway.StateEnum) => Option(fun)
		given putListSchemaApigatewayApi: Conversion[List[Schema.ApigatewayApi], Option[List[Schema.ApigatewayApi]]] = (fun: List[Schema.ApigatewayApi]) => Option(fun)
		given putSchemaApigatewayApiStateEnum: Conversion[Schema.ApigatewayApi.StateEnum, Option[Schema.ApigatewayApi.StateEnum]] = (fun: Schema.ApigatewayApi.StateEnum) => Option(fun)
		given putListSchemaApigatewayApiConfig: Conversion[List[Schema.ApigatewayApiConfig], Option[List[Schema.ApigatewayApiConfig]]] = (fun: List[Schema.ApigatewayApiConfig]) => Option(fun)
		given putSchemaApigatewayApiConfigStateEnum: Conversion[Schema.ApigatewayApiConfig.StateEnum, Option[Schema.ApigatewayApiConfig.StateEnum]] = (fun: Schema.ApigatewayApiConfig.StateEnum) => Option(fun)
		given putListSchemaApigatewayApiConfigOpenApiDocument: Conversion[List[Schema.ApigatewayApiConfigOpenApiDocument], Option[List[Schema.ApigatewayApiConfigOpenApiDocument]]] = (fun: List[Schema.ApigatewayApiConfigOpenApiDocument]) => Option(fun)
		given putListSchemaApigatewayApiConfigGrpcServiceDefinition: Conversion[List[Schema.ApigatewayApiConfigGrpcServiceDefinition], Option[List[Schema.ApigatewayApiConfigGrpcServiceDefinition]]] = (fun: List[Schema.ApigatewayApiConfigGrpcServiceDefinition]) => Option(fun)
		given putListSchemaApigatewayApiConfigFile: Conversion[List[Schema.ApigatewayApiConfigFile], Option[List[Schema.ApigatewayApiConfigFile]]] = (fun: List[Schema.ApigatewayApiConfigFile]) => Option(fun)
		given putSchemaApigatewayApiConfigFile: Conversion[Schema.ApigatewayApiConfigFile, Option[Schema.ApigatewayApiConfigFile]] = (fun: Schema.ApigatewayApiConfigFile) => Option(fun)
		given putListSchemaApigatewayLocation: Conversion[List[Schema.ApigatewayLocation], Option[List[Schema.ApigatewayLocation]]] = (fun: List[Schema.ApigatewayLocation]) => Option(fun)
		given putSchemaApigatewayPolicy: Conversion[Schema.ApigatewayPolicy, Option[Schema.ApigatewayPolicy]] = (fun: Schema.ApigatewayPolicy) => Option(fun)
		given putListSchemaApigatewayBinding: Conversion[List[Schema.ApigatewayBinding], Option[List[Schema.ApigatewayBinding]]] = (fun: List[Schema.ApigatewayBinding]) => Option(fun)
		given putListSchemaApigatewayAuditConfig: Conversion[List[Schema.ApigatewayAuditConfig], Option[List[Schema.ApigatewayAuditConfig]]] = (fun: List[Schema.ApigatewayAuditConfig]) => Option(fun)
		given putSchemaApigatewayExpr: Conversion[Schema.ApigatewayExpr, Option[Schema.ApigatewayExpr]] = (fun: Schema.ApigatewayExpr) => Option(fun)
		given putListSchemaApigatewayAuditLogConfig: Conversion[List[Schema.ApigatewayAuditLogConfig], Option[List[Schema.ApigatewayAuditLogConfig]]] = (fun: List[Schema.ApigatewayAuditLogConfig]) => Option(fun)
		given putSchemaApigatewayAuditLogConfigLogTypeEnum: Conversion[Schema.ApigatewayAuditLogConfig.LogTypeEnum, Option[Schema.ApigatewayAuditLogConfig.LogTypeEnum]] = (fun: Schema.ApigatewayAuditLogConfig.LogTypeEnum) => Option(fun)
		given putListSchemaApigatewayOperationMetadataDiagnostic: Conversion[List[Schema.ApigatewayOperationMetadataDiagnostic], Option[List[Schema.ApigatewayOperationMetadataDiagnostic]]] = (fun: List[Schema.ApigatewayOperationMetadataDiagnostic]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
