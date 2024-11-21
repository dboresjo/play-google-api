package com.boresjo.play.api.google.cloudtrace

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtBatchWriteSpansRequest: Format[Schema.BatchWriteSpansRequest] = Json.format[Schema.BatchWriteSpansRequest]
	given fmtSpan: Format[Schema.Span] = Json.format[Schema.Span]
	given fmtTruncatableString: Format[Schema.TruncatableString] = Json.format[Schema.TruncatableString]
	given fmtAttributes: Format[Schema.Attributes] = Json.format[Schema.Attributes]
	given fmtStackTrace: Format[Schema.StackTrace] = Json.format[Schema.StackTrace]
	given fmtTimeEvents: Format[Schema.TimeEvents] = Json.format[Schema.TimeEvents]
	given fmtLinks: Format[Schema.Links] = Json.format[Schema.Links]
	given fmtStatus: Format[Schema.Status] = Json.format[Schema.Status]
	given fmtSpanSpanKindEnum: Format[Schema.Span.SpanKindEnum] = JsonEnumFormat[Schema.Span.SpanKindEnum]
	given fmtAttributeValue: Format[Schema.AttributeValue] = Json.format[Schema.AttributeValue]
	given fmtStackFrames: Format[Schema.StackFrames] = Json.format[Schema.StackFrames]
	given fmtStackFrame: Format[Schema.StackFrame] = Json.format[Schema.StackFrame]
	given fmtModule: Format[Schema.Module] = Json.format[Schema.Module]
	given fmtTimeEvent: Format[Schema.TimeEvent] = Json.format[Schema.TimeEvent]
	given fmtAnnotation: Format[Schema.Annotation] = Json.format[Schema.Annotation]
	given fmtMessageEvent: Format[Schema.MessageEvent] = Json.format[Schema.MessageEvent]
	given fmtMessageEventTypeEnum: Format[Schema.MessageEvent.TypeEnum] = JsonEnumFormat[Schema.MessageEvent.TypeEnum]
	given fmtLink: Format[Schema.Link] = Json.format[Schema.Link]
	given fmtLinkTypeEnum: Format[Schema.Link.TypeEnum] = JsonEnumFormat[Schema.Link.TypeEnum]
	given fmtEmpty: Format[Schema.Empty] = Json.format[Schema.Empty]
}
