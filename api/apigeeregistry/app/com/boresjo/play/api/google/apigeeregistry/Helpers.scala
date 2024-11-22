package com.boresjo.play.api.google.apigeeregistry

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

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
		given putSchemaInstanceStateEnum: Conversion[Schema.Instance.StateEnum, Option[Schema.Instance.StateEnum]] = (fun: Schema.Instance.StateEnum) => Option(fun)
		given putSchemaConfig: Conversion[Schema.Config, Option[Schema.Config]] = (fun: Schema.Config) => Option(fun)
		given putSchemaBuild: Conversion[Schema.Build, Option[Schema.Build]] = (fun: Schema.Build) => Option(fun)
		given putListSchemaApi: Conversion[List[Schema.Api], Option[List[Schema.Api]]] = (fun: List[Schema.Api]) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putListSchemaApiVersion: Conversion[List[Schema.ApiVersion], Option[List[Schema.ApiVersion]]] = (fun: List[Schema.ApiVersion]) => Option(fun)
		given putListSchemaApiSpec: Conversion[List[Schema.ApiSpec], Option[List[Schema.ApiSpec]]] = (fun: List[Schema.ApiSpec]) => Option(fun)
		given putListSchemaApiDeployment: Conversion[List[Schema.ApiDeployment], Option[List[Schema.ApiDeployment]]] = (fun: List[Schema.ApiDeployment]) => Option(fun)
		given putListSchemaArtifact: Conversion[List[Schema.Artifact], Option[List[Schema.Artifact]]] = (fun: List[Schema.Artifact]) => Option(fun)
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
		given putSchemaPolicy: Conversion[Schema.Policy, Option[Schema.Policy]] = (fun: Schema.Policy) => Option(fun)
		given putListSchemaBinding: Conversion[List[Schema.Binding], Option[List[Schema.Binding]]] = (fun: List[Schema.Binding]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaExpr: Conversion[Schema.Expr, Option[Schema.Expr]] = (fun: Schema.Expr) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
