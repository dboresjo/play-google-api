package com.boresjo.play.api.google.cloudtrace

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class BatchWriteSpansRequest(
	  /** Required. A list of new spans. The span names must not match existing spans, otherwise the results are undefined. */
		spans: Option[List[Schema.Span]] = None
	)
	
	object Span {
		enum SpanKindEnum extends Enum[SpanKindEnum] { case SPAN_KIND_UNSPECIFIED, INTERNAL, SERVER, CLIENT, PRODUCER, CONSUMER }
	}
	case class Span(
	  /** Required. The resource name of the span in the following format: &#42; `projects/[PROJECT_ID]/traces/[TRACE_ID]/spans/[SPAN_ID]` `[TRACE_ID]` is a unique identifier for a trace within a project; it is a 32-character hexadecimal encoding of a 16-byte array. It should not be zero. `[SPAN_ID]` is a unique identifier for a span within a trace; it is a 16-character hexadecimal encoding of an 8-byte array. It should not be zero. . */
		name: Option[String] = None,
	  /** Required. The `[SPAN_ID]` portion of the span's resource name. */
		spanId: Option[String] = None,
	  /** The `[SPAN_ID]` of this span's parent span. If this is a root span, then this field must be empty. */
		parentSpanId: Option[String] = None,
	  /** Required. A description of the span's operation (up to 128 bytes). Cloud Trace displays the description in the Cloud console. For example, the display name can be a qualified method name or a file name and a line number where the operation is called. A best practice is to use the same display name within an application and at the same call point. This makes it easier to correlate spans in different traces. */
		displayName: Option[Schema.TruncatableString] = None,
	  /** Required. The start time of the span. On the client side, this is the time kept by the local machine where the span execution starts. On the server side, this is the time when the server's application handler starts running. */
		startTime: Option[String] = None,
	  /** Required. The end time of the span. On the client side, this is the time kept by the local machine where the span execution ends. On the server side, this is the time when the server application handler stops running. */
		endTime: Option[String] = None,
	  /** A set of attributes on the span. You can have up to 32 attributes per span. */
		attributes: Option[Schema.Attributes] = None,
	  /** Stack trace captured at the start of the span. */
		stackTrace: Option[Schema.StackTrace] = None,
	  /** A set of time events. You can have up to 32 annotations and 128 message events per span. */
		timeEvents: Option[Schema.TimeEvents] = None,
	  /** Links associated with the span. You can have up to 128 links per Span. */
		links: Option[Schema.Links] = None,
	  /** Optional. The final status for this span. */
		status: Option[Schema.Status] = None,
	  /** Optional. Set this parameter to indicate whether this span is in the same process as its parent. If you do not set this parameter, Trace is unable to take advantage of this helpful information. */
		sameProcessAsParentSpan: Option[Boolean] = None,
	  /** Optional. The number of child spans that were generated while this span was active. If set, allows implementation to detect missing child spans. */
		childSpanCount: Option[Int] = None,
	  /** Optional. Distinguishes between spans generated in a particular context. For example, two spans with the same name may be distinguished using `CLIENT` (caller) and `SERVER` (callee) to identify an RPC call. */
		spanKind: Option[Schema.Span.SpanKindEnum] = None
	)
	
	case class TruncatableString(
	  /** The shortened string. For example, if the original string is 500 bytes long and the limit of the string is 128 bytes, then `value` contains the first 128 bytes of the 500-byte string. Truncation always happens on a UTF8 character boundary. If there are multi-byte characters in the string, then the length of the shortened string might be less than the size limit. */
		value: Option[String] = None,
	  /** The number of bytes removed from the original string. If this value is 0, then the string was not shortened. */
		truncatedByteCount: Option[Int] = None
	)
	
	case class Attributes(
	  /** A set of attributes. Each attribute's key can be up to 128 bytes long. The value can be a string up to 256 bytes, a signed 64-bit integer, or the boolean values `true` or `false`. For example: "/instance_id": { "string_value": { "value": "my-instance" } } "/http/request_bytes": { "int_value": 300 } "example.com/myattribute": { "bool_value": false } */
		attributeMap: Option[Map[String, Schema.AttributeValue]] = None,
	  /** The number of attributes that were discarded. Attributes can be discarded because their keys are too long or because there are too many attributes. If this value is 0 then all attributes are valid. */
		droppedAttributesCount: Option[Int] = None
	)
	
	case class AttributeValue(
	  /** A string up to 256 bytes long. */
		stringValue: Option[Schema.TruncatableString] = None,
	  /** A 64-bit signed integer. */
		intValue: Option[String] = None,
	  /** A Boolean value represented by `true` or `false`. */
		boolValue: Option[Boolean] = None
	)
	
	case class StackTrace(
	  /** Stack frames in this stack trace. A maximum of 128 frames are allowed. */
		stackFrames: Option[Schema.StackFrames] = None,
	  /** The hash ID is used to conserve network bandwidth for duplicate stack traces within a single trace. Often multiple spans will have identical stack traces. The first occurrence of a stack trace should contain both the `stackFrame` content and a value in `stackTraceHashId`. Subsequent spans within the same request can refer to that stack trace by only setting `stackTraceHashId`. */
		stackTraceHashId: Option[String] = None
	)
	
	case class StackFrames(
	  /** Stack frames in this call stack. */
		frame: Option[List[Schema.StackFrame]] = None,
	  /** The number of stack frames that were dropped because there were too many stack frames. If this value is 0, then no stack frames were dropped. */
		droppedFramesCount: Option[Int] = None
	)
	
	case class StackFrame(
	  /** The fully-qualified name that uniquely identifies the function or method that is active in this frame (up to 1024 bytes). */
		functionName: Option[Schema.TruncatableString] = None,
	  /** An un-mangled function name, if `function_name` is mangled. To get information about name mangling, run [this search](https://www.google.com/search?q=cxx+name+mangling). The name can be fully-qualified (up to 1024 bytes). */
		originalFunctionName: Option[Schema.TruncatableString] = None,
	  /** The name of the source file where the function call appears (up to 256 bytes). */
		fileName: Option[Schema.TruncatableString] = None,
	  /** The line number in `file_name` where the function call appears. */
		lineNumber: Option[String] = None,
	  /** The column number where the function call appears, if available. This is important in JavaScript because of its anonymous functions. */
		columnNumber: Option[String] = None,
	  /** The binary module from where the code was loaded. */
		loadModule: Option[Schema.Module] = None,
	  /** The version of the deployed source code (up to 128 bytes). */
		sourceVersion: Option[Schema.TruncatableString] = None
	)
	
	case class Module(
	  /** For example: main binary, kernel modules, and dynamic libraries such as libc.so, sharedlib.so (up to 256 bytes). */
		module: Option[Schema.TruncatableString] = None,
	  /** A unique identifier for the module, usually a hash of its contents (up to 128 bytes). */
		buildId: Option[Schema.TruncatableString] = None
	)
	
	case class TimeEvents(
	  /** A collection of `TimeEvent`s. */
		timeEvent: Option[List[Schema.TimeEvent]] = None,
	  /** The number of dropped annotations in all the included time events. If the value is 0, then no annotations were dropped. */
		droppedAnnotationsCount: Option[Int] = None,
	  /** The number of dropped message events in all the included time events. If the value is 0, then no message events were dropped. */
		droppedMessageEventsCount: Option[Int] = None
	)
	
	case class TimeEvent(
	  /** The timestamp indicating the time the event occurred. */
		time: Option[String] = None,
	  /** Text annotation with a set of attributes. */
		annotation: Option[Schema.Annotation] = None,
	  /** An event describing a message sent/received between Spans. */
		messageEvent: Option[Schema.MessageEvent] = None
	)
	
	case class Annotation(
	  /** A user-supplied message describing the event. The maximum length for the description is 256 bytes. */
		description: Option[Schema.TruncatableString] = None,
	  /** A set of attributes on the annotation. You can have up to 4 attributes per Annotation. */
		attributes: Option[Schema.Attributes] = None
	)
	
	object MessageEvent {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, SENT, RECEIVED }
	}
	case class MessageEvent(
	  /** Type of MessageEvent. Indicates whether the message was sent or received. */
		`type`: Option[Schema.MessageEvent.TypeEnum] = None,
	  /** An identifier for the MessageEvent's message that can be used to match `SENT` and `RECEIVED` MessageEvents. */
		id: Option[String] = None,
	  /** The number of uncompressed bytes sent or received. */
		uncompressedSizeBytes: Option[String] = None,
	  /** The number of compressed bytes sent or received. If missing, the compressed size is assumed to be the same size as the uncompressed size. */
		compressedSizeBytes: Option[String] = None
	)
	
	case class Links(
	  /** A collection of links. */
		link: Option[List[Schema.Link]] = None,
	  /** The number of dropped links after the maximum size was enforced. If this value is 0, then no links were dropped. */
		droppedLinksCount: Option[Int] = None
	)
	
	object Link {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, CHILD_LINKED_SPAN, PARENT_LINKED_SPAN }
	}
	case class Link(
	  /** The `[TRACE_ID]` for a trace within a project. */
		traceId: Option[String] = None,
	  /** The `[SPAN_ID]` for a span within a trace. */
		spanId: Option[String] = None,
	  /** The relationship of the current span relative to the linked span. */
		`type`: Option[Schema.Link.TypeEnum] = None,
	  /** A set of attributes on the link. Up to 32 attributes can be specified per link. */
		attributes: Option[Schema.Attributes] = None
	)
	
	case class Status(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	case class Empty(
	
	)
}
