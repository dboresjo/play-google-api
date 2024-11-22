package com.boresjo.play.api.google.cloudtasks

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaLocation: Conversion[List[Schema.Location], Option[List[Schema.Location]]] = (fun: List[Schema.Location]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putMapStringJsValue: Conversion[Map[String, JsValue], Option[Map[String, JsValue]]] = (fun: Map[String, JsValue]) => Option(fun)
		given putListSchemaQueue: Conversion[List[Schema.Queue], Option[List[Schema.Queue]]] = (fun: List[Schema.Queue]) => Option(fun)
		given putSchemaAppEngineRouting: Conversion[Schema.AppEngineRouting, Option[Schema.AppEngineRouting]] = (fun: Schema.AppEngineRouting) => Option(fun)
		given putSchemaHttpTarget: Conversion[Schema.HttpTarget, Option[Schema.HttpTarget]] = (fun: Schema.HttpTarget) => Option(fun)
		given putSchemaRateLimits: Conversion[Schema.RateLimits, Option[Schema.RateLimits]] = (fun: Schema.RateLimits) => Option(fun)
		given putSchemaRetryConfig: Conversion[Schema.RetryConfig, Option[Schema.RetryConfig]] = (fun: Schema.RetryConfig) => Option(fun)
		given putSchemaQueueStateEnum: Conversion[Schema.Queue.StateEnum, Option[Schema.Queue.StateEnum]] = (fun: Schema.Queue.StateEnum) => Option(fun)
		given putSchemaStackdriverLoggingConfig: Conversion[Schema.StackdriverLoggingConfig, Option[Schema.StackdriverLoggingConfig]] = (fun: Schema.StackdriverLoggingConfig) => Option(fun)
		given putSchemaUriOverride: Conversion[Schema.UriOverride, Option[Schema.UriOverride]] = (fun: Schema.UriOverride) => Option(fun)
		given putSchemaHttpTargetHttpMethodEnum: Conversion[Schema.HttpTarget.HttpMethodEnum, Option[Schema.HttpTarget.HttpMethodEnum]] = (fun: Schema.HttpTarget.HttpMethodEnum) => Option(fun)
		given putListSchemaHeaderOverride: Conversion[List[Schema.HeaderOverride], Option[List[Schema.HeaderOverride]]] = (fun: List[Schema.HeaderOverride]) => Option(fun)
		given putSchemaOAuthToken: Conversion[Schema.OAuthToken, Option[Schema.OAuthToken]] = (fun: Schema.OAuthToken) => Option(fun)
		given putSchemaOidcToken: Conversion[Schema.OidcToken, Option[Schema.OidcToken]] = (fun: Schema.OidcToken) => Option(fun)
		given putSchemaUriOverrideSchemeEnum: Conversion[Schema.UriOverride.SchemeEnum, Option[Schema.UriOverride.SchemeEnum]] = (fun: Schema.UriOverride.SchemeEnum) => Option(fun)
		given putSchemaPathOverride: Conversion[Schema.PathOverride, Option[Schema.PathOverride]] = (fun: Schema.PathOverride) => Option(fun)
		given putSchemaQueryOverride: Conversion[Schema.QueryOverride, Option[Schema.QueryOverride]] = (fun: Schema.QueryOverride) => Option(fun)
		given putSchemaUriOverrideUriOverrideEnforceModeEnum: Conversion[Schema.UriOverride.UriOverrideEnforceModeEnum, Option[Schema.UriOverride.UriOverrideEnforceModeEnum]] = (fun: Schema.UriOverride.UriOverrideEnforceModeEnum) => Option(fun)
		given putSchemaHeader: Conversion[Schema.Header, Option[Schema.Header]] = (fun: Schema.Header) => Option(fun)
		given putBigDecimal: Conversion[BigDecimal, Option[BigDecimal]] = (fun: BigDecimal) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaGetPolicyOptions: Conversion[Schema.GetPolicyOptions, Option[Schema.GetPolicyOptions]] = (fun: Schema.GetPolicyOptions) => Option(fun)
		given putListSchemaBinding: Conversion[List[Schema.Binding], Option[List[Schema.Binding]]] = (fun: List[Schema.Binding]) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaExpr: Conversion[Schema.Expr, Option[Schema.Expr]] = (fun: Schema.Expr) => Option(fun)
		given putSchemaPolicy: Conversion[Schema.Policy, Option[Schema.Policy]] = (fun: Schema.Policy) => Option(fun)
		given putListSchemaTask: Conversion[List[Schema.Task], Option[List[Schema.Task]]] = (fun: List[Schema.Task]) => Option(fun)
		given putSchemaAppEngineHttpRequest: Conversion[Schema.AppEngineHttpRequest, Option[Schema.AppEngineHttpRequest]] = (fun: Schema.AppEngineHttpRequest) => Option(fun)
		given putSchemaHttpRequest: Conversion[Schema.HttpRequest, Option[Schema.HttpRequest]] = (fun: Schema.HttpRequest) => Option(fun)
		given putSchemaAttempt: Conversion[Schema.Attempt, Option[Schema.Attempt]] = (fun: Schema.Attempt) => Option(fun)
		given putSchemaTaskViewEnum: Conversion[Schema.Task.ViewEnum, Option[Schema.Task.ViewEnum]] = (fun: Schema.Task.ViewEnum) => Option(fun)
		given putSchemaAppEngineHttpRequestHttpMethodEnum: Conversion[Schema.AppEngineHttpRequest.HttpMethodEnum, Option[Schema.AppEngineHttpRequest.HttpMethodEnum]] = (fun: Schema.AppEngineHttpRequest.HttpMethodEnum) => Option(fun)
		given putSchemaHttpRequestHttpMethodEnum: Conversion[Schema.HttpRequest.HttpMethodEnum, Option[Schema.HttpRequest.HttpMethodEnum]] = (fun: Schema.HttpRequest.HttpMethodEnum) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
		given putSchemaTask: Conversion[Schema.Task, Option[Schema.Task]] = (fun: Schema.Task) => Option(fun)
		given putSchemaCreateTaskRequestResponseViewEnum: Conversion[Schema.CreateTaskRequest.ResponseViewEnum, Option[Schema.CreateTaskRequest.ResponseViewEnum]] = (fun: Schema.CreateTaskRequest.ResponseViewEnum) => Option(fun)
		given putSchemaRunTaskRequestResponseViewEnum: Conversion[Schema.RunTaskRequest.ResponseViewEnum, Option[Schema.RunTaskRequest.ResponseViewEnum]] = (fun: Schema.RunTaskRequest.ResponseViewEnum) => Option(fun)
		given putSchemaHttpBody: Conversion[Schema.HttpBody, Option[Schema.HttpBody]] = (fun: Schema.HttpBody) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
