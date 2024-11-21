package com.boresjo.play.api.google.cloudtrace

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putListSchemaSpan: Conversion[List[Schema.Span], Option[List[Schema.Span]]] = (fun: List[Schema.Span]) => Option(fun)
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putSchemaTruncatableString: Conversion[Schema.TruncatableString, Option[Schema.TruncatableString]] = (fun: Schema.TruncatableString) => Option(fun)
		given putSchemaAttributes: Conversion[Schema.Attributes, Option[Schema.Attributes]] = (fun: Schema.Attributes) => Option(fun)
		given putSchemaStackTrace: Conversion[Schema.StackTrace, Option[Schema.StackTrace]] = (fun: Schema.StackTrace) => Option(fun)
		given putSchemaTimeEvents: Conversion[Schema.TimeEvents, Option[Schema.TimeEvents]] = (fun: Schema.TimeEvents) => Option(fun)
		given putSchemaLinks: Conversion[Schema.Links, Option[Schema.Links]] = (fun: Schema.Links) => Option(fun)
		given putSchemaStatus: Conversion[Schema.Status, Option[Schema.Status]] = (fun: Schema.Status) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaSpanSpanKindEnum: Conversion[Schema.Span.SpanKindEnum, Option[Schema.Span.SpanKindEnum]] = (fun: Schema.Span.SpanKindEnum) => Option(fun)
		given putMapStringSchemaAttributeValue: Conversion[Map[String, Schema.AttributeValue], Option[Map[String, Schema.AttributeValue]]] = (fun: Map[String, Schema.AttributeValue]) => Option(fun)
		given putSchemaStackFrames: Conversion[Schema.StackFrames, Option[Schema.StackFrames]] = (fun: Schema.StackFrames) => Option(fun)
		given putListSchemaStackFrame: Conversion[List[Schema.StackFrame], Option[List[Schema.StackFrame]]] = (fun: List[Schema.StackFrame]) => Option(fun)
		given putSchemaModule: Conversion[Schema.Module, Option[Schema.Module]] = (fun: Schema.Module) => Option(fun)
		given putListSchemaTimeEvent: Conversion[List[Schema.TimeEvent], Option[List[Schema.TimeEvent]]] = (fun: List[Schema.TimeEvent]) => Option(fun)
		given putSchemaAnnotation: Conversion[Schema.Annotation, Option[Schema.Annotation]] = (fun: Schema.Annotation) => Option(fun)
		given putSchemaMessageEvent: Conversion[Schema.MessageEvent, Option[Schema.MessageEvent]] = (fun: Schema.MessageEvent) => Option(fun)
		given putSchemaMessageEventTypeEnum: Conversion[Schema.MessageEvent.TypeEnum, Option[Schema.MessageEvent.TypeEnum]] = (fun: Schema.MessageEvent.TypeEnum) => Option(fun)
		given putListSchemaLink: Conversion[List[Schema.Link], Option[List[Schema.Link]]] = (fun: List[Schema.Link]) => Option(fun)
		given putSchemaLinkTypeEnum: Conversion[Schema.Link.TypeEnum, Option[Schema.Link.TypeEnum]] = (fun: Schema.Link.TypeEnum) => Option(fun)
		given putListMapStringJsValue: Conversion[List[Map[String, JsValue]], Option[List[Map[String, JsValue]]]] = (fun: List[Map[String, JsValue]]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
