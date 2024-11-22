package com.boresjo.play.api.google.clouderrorreporting

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListSchemaTrackingIssue: Conversion[List[Schema.TrackingIssue], Option[List[Schema.TrackingIssue]]] = (fun: List[Schema.TrackingIssue]) => Option(fun)
		given putSchemaErrorGroupResolutionStatusEnum: Conversion[Schema.ErrorGroup.ResolutionStatusEnum, Option[Schema.ErrorGroup.ResolutionStatusEnum]] = (fun: Schema.ErrorGroup.ResolutionStatusEnum) => Option(fun)
		given putListSchemaErrorGroupStats: Conversion[List[Schema.ErrorGroupStats], Option[List[Schema.ErrorGroupStats]]] = (fun: List[Schema.ErrorGroupStats]) => Option(fun)
		given putSchemaErrorGroup: Conversion[Schema.ErrorGroup, Option[Schema.ErrorGroup]] = (fun: Schema.ErrorGroup) => Option(fun)
		given putListSchemaTimedCount: Conversion[List[Schema.TimedCount], Option[List[Schema.TimedCount]]] = (fun: List[Schema.TimedCount]) => Option(fun)
		given putListSchemaServiceContext: Conversion[List[Schema.ServiceContext], Option[List[Schema.ServiceContext]]] = (fun: List[Schema.ServiceContext]) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaErrorEvent: Conversion[Schema.ErrorEvent, Option[Schema.ErrorEvent]] = (fun: Schema.ErrorEvent) => Option(fun)
		given putSchemaServiceContext: Conversion[Schema.ServiceContext, Option[Schema.ServiceContext]] = (fun: Schema.ServiceContext) => Option(fun)
		given putSchemaErrorContext: Conversion[Schema.ErrorContext, Option[Schema.ErrorContext]] = (fun: Schema.ErrorContext) => Option(fun)
		given putSchemaHttpRequestContext: Conversion[Schema.HttpRequestContext, Option[Schema.HttpRequestContext]] = (fun: Schema.HttpRequestContext) => Option(fun)
		given putSchemaSourceLocation: Conversion[Schema.SourceLocation, Option[Schema.SourceLocation]] = (fun: Schema.SourceLocation) => Option(fun)
		given putListSchemaSourceReference: Conversion[List[Schema.SourceReference], Option[List[Schema.SourceReference]]] = (fun: List[Schema.SourceReference]) => Option(fun)
		given putListSchemaErrorEvent: Conversion[List[Schema.ErrorEvent], Option[List[Schema.ErrorEvent]]] = (fun: List[Schema.ErrorEvent]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
