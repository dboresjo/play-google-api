package com.boresjo.play.api.google.clouderrorreporting

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtErrorGroup: Format[Schema.ErrorGroup] = Json.format[Schema.ErrorGroup]
	given fmtTrackingIssue: Format[Schema.TrackingIssue] = Json.format[Schema.TrackingIssue]
	given fmtErrorGroupResolutionStatusEnum: Format[Schema.ErrorGroup.ResolutionStatusEnum] = JsonEnumFormat[Schema.ErrorGroup.ResolutionStatusEnum]
	given fmtListGroupStatsResponse: Format[Schema.ListGroupStatsResponse] = Json.format[Schema.ListGroupStatsResponse]
	given fmtErrorGroupStats: Format[Schema.ErrorGroupStats] = Json.format[Schema.ErrorGroupStats]
	given fmtTimedCount: Format[Schema.TimedCount] = Json.format[Schema.TimedCount]
	given fmtServiceContext: Format[Schema.ServiceContext] = Json.format[Schema.ServiceContext]
	given fmtErrorEvent: Format[Schema.ErrorEvent] = Json.format[Schema.ErrorEvent]
	given fmtErrorContext: Format[Schema.ErrorContext] = Json.format[Schema.ErrorContext]
	given fmtHttpRequestContext: Format[Schema.HttpRequestContext] = Json.format[Schema.HttpRequestContext]
	given fmtSourceLocation: Format[Schema.SourceLocation] = Json.format[Schema.SourceLocation]
	given fmtSourceReference: Format[Schema.SourceReference] = Json.format[Schema.SourceReference]
	given fmtListEventsResponse: Format[Schema.ListEventsResponse] = Json.format[Schema.ListEventsResponse]
	given fmtDeleteEventsResponse: Format[Schema.DeleteEventsResponse] = Json.format[Schema.DeleteEventsResponse]
	given fmtReportedErrorEvent: Format[Schema.ReportedErrorEvent] = Json.format[Schema.ReportedErrorEvent]
	given fmtReportErrorEventResponse: Format[Schema.ReportErrorEventResponse] = Json.format[Schema.ReportErrorEventResponse]
}
