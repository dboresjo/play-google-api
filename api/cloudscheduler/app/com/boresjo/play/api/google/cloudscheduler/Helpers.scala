package com.boresjo.play.api.google.cloudscheduler

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putListSchemaJob: Conversion[List[Schema.Job], Option[List[Schema.Job]]] = (fun: List[Schema.Job]) => Option(fun)
		given putSchemaPubsubTarget: Conversion[Schema.PubsubTarget, Option[Schema.PubsubTarget]] = (fun: Schema.PubsubTarget) => Option(fun)
		given putSchemaAppEngineHttpTarget: Conversion[Schema.AppEngineHttpTarget, Option[Schema.AppEngineHttpTarget]] = (fun: Schema.AppEngineHttpTarget) => Option(fun)
		given putSchemaHttpTarget: Conversion[Schema.HttpTarget, Option[Schema.HttpTarget]] = (fun: Schema.HttpTarget) => Option(fun)
		given putSchemaJobStateEnum: Conversion[Schema.Job.StateEnum, Option[Schema.Job.StateEnum]] = (fun: Schema.Job.StateEnum) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putSchemaRetryConfig: Conversion[Schema.RetryConfig, Option[Schema.RetryConfig]] = (fun: Schema.RetryConfig) => Option(fun)
		given putSchemaAppEngineHttpTargetHttpMethodEnum: Conversion[Schema.AppEngineHttpTarget.HttpMethodEnum, Option[Schema.AppEngineHttpTarget.HttpMethodEnum]] = (fun: Schema.AppEngineHttpTarget.HttpMethodEnum) => Option(fun)
		given putSchemaAppEngineRouting: Conversion[Schema.AppEngineRouting, Option[Schema.AppEngineRouting]] = (fun: Schema.AppEngineRouting) => Option(fun)
		given putSchemaHttpTargetHttpMethodEnum: Conversion[Schema.HttpTarget.HttpMethodEnum, Option[Schema.HttpTarget.HttpMethodEnum]] = (fun: Schema.HttpTarget.HttpMethodEnum) => Option(fun)
		given putSchemaOAuthToken: Conversion[Schema.OAuthToken, Option[Schema.OAuthToken]] = (fun: Schema.OAuthToken) => Option(fun)
		given putSchemaOidcToken: Conversion[Schema.OidcToken, Option[Schema.OidcToken]] = (fun: Schema.OidcToken) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putListSchemaOperation: Conversion[List[Schema.Operation], Option[List[Schema.Operation]]] = (fun: List[Schema.Operation]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
