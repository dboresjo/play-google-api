package com.boresjo.play.api.google.admin

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtActivities: Format[Schema.Activities] = Json.format[Schema.Activities]
	given fmtActivity: Format[Schema.Activity] = Json.format[Schema.Activity]
	given fmtActivityEventsItem: Format[Schema.Activity.EventsItem] = Json.format[Schema.Activity.EventsItem]
	given fmtActivityEventsItemParametersItem: Format[Schema.Activity.EventsItem.ParametersItem] = Json.format[Schema.Activity.EventsItem.ParametersItem]
	given fmtActivityEventsItemParametersItemMessageValueItem: Format[Schema.Activity.EventsItem.ParametersItem.MessageValueItem] = Json.format[Schema.Activity.EventsItem.ParametersItem.MessageValueItem]
	given fmtNestedParameter: Format[Schema.NestedParameter] = Json.format[Schema.NestedParameter]
	given fmtActivityEventsItemParametersItemMultiMessageValueItem: Format[Schema.Activity.EventsItem.ParametersItem.MultiMessageValueItem] = Json.format[Schema.Activity.EventsItem.ParametersItem.MultiMessageValueItem]
	given fmtActivityIdItem: Format[Schema.Activity.IdItem] = Json.format[Schema.Activity.IdItem]
	given fmtActivityActorItem: Format[Schema.Activity.ActorItem] = Json.format[Schema.Activity.ActorItem]
	given fmtChannel: Format[Schema.Channel] = Json.format[Schema.Channel]
	given fmtUsageReports: Format[Schema.UsageReports] = Json.format[Schema.UsageReports]
	given fmtUsageReportsWarningsItem: Format[Schema.UsageReports.WarningsItem] = Json.format[Schema.UsageReports.WarningsItem]
	given fmtUsageReportsWarningsItemDataItem: Format[Schema.UsageReports.WarningsItem.DataItem] = Json.format[Schema.UsageReports.WarningsItem.DataItem]
	given fmtUsageReport: Format[Schema.UsageReport] = Json.format[Schema.UsageReport]
	given fmtUsageReportParametersItem: Format[Schema.UsageReport.ParametersItem] = Json.format[Schema.UsageReport.ParametersItem]
	given fmtUsageReportEntityItem: Format[Schema.UsageReport.EntityItem] = Json.format[Schema.UsageReport.EntityItem]
}
