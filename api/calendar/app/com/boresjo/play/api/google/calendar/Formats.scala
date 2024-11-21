package com.boresjo.play.api.google.calendar

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Formats {
	given fmtAcl: Format[Schema.Acl] = Json.format[Schema.Acl]
	given fmtAclRule: Format[Schema.AclRule] = Json.format[Schema.AclRule]
	given fmtAclRuleScopeItem: Format[Schema.AclRule.ScopeItem] = Json.format[Schema.AclRule.ScopeItem]
	given fmtCalendar: Format[Schema.Calendar] = Json.format[Schema.Calendar]
	given fmtConferenceProperties: Format[Schema.ConferenceProperties] = Json.format[Schema.ConferenceProperties]
	given fmtCalendarList: Format[Schema.CalendarList] = Json.format[Schema.CalendarList]
	given fmtCalendarListEntry: Format[Schema.CalendarListEntry] = Json.format[Schema.CalendarListEntry]
	given fmtEventReminder: Format[Schema.EventReminder] = Json.format[Schema.EventReminder]
	given fmtCalendarListEntryNotificationSettingsItem: Format[Schema.CalendarListEntry.NotificationSettingsItem] = Json.format[Schema.CalendarListEntry.NotificationSettingsItem]
	given fmtCalendarNotification: Format[Schema.CalendarNotification] = Json.format[Schema.CalendarNotification]
	given fmtChannel: Format[Schema.Channel] = Json.format[Schema.Channel]
	given fmtColorDefinition: Format[Schema.ColorDefinition] = Json.format[Schema.ColorDefinition]
	given fmtColors: Format[Schema.Colors] = Json.format[Schema.Colors]
	given fmtConferenceData: Format[Schema.ConferenceData] = Json.format[Schema.ConferenceData]
	given fmtConferenceSolution: Format[Schema.ConferenceSolution] = Json.format[Schema.ConferenceSolution]
	given fmtCreateConferenceRequest: Format[Schema.CreateConferenceRequest] = Json.format[Schema.CreateConferenceRequest]
	given fmtEntryPoint: Format[Schema.EntryPoint] = Json.format[Schema.EntryPoint]
	given fmtConferenceParameters: Format[Schema.ConferenceParameters] = Json.format[Schema.ConferenceParameters]
	given fmtConferenceParametersAddOnParameters: Format[Schema.ConferenceParametersAddOnParameters] = Json.format[Schema.ConferenceParametersAddOnParameters]
	given fmtConferenceRequestStatus: Format[Schema.ConferenceRequestStatus] = Json.format[Schema.ConferenceRequestStatus]
	given fmtConferenceSolutionKey: Format[Schema.ConferenceSolutionKey] = Json.format[Schema.ConferenceSolutionKey]
	given fmtError: Format[Schema.Error] = Json.format[Schema.Error]
	given fmtEvent: Format[Schema.Event] = Json.format[Schema.Event]
	given fmtEventAttachment: Format[Schema.EventAttachment] = Json.format[Schema.EventAttachment]
	given fmtEventAttendee: Format[Schema.EventAttendee] = Json.format[Schema.EventAttendee]
	given fmtEventBirthdayProperties: Format[Schema.EventBirthdayProperties] = Json.format[Schema.EventBirthdayProperties]
	given fmtEventCreatorItem: Format[Schema.Event.CreatorItem] = Json.format[Schema.Event.CreatorItem]
	given fmtEventDateTime: Format[Schema.EventDateTime] = Json.format[Schema.EventDateTime]
	given fmtEventExtendedPropertiesItem: Format[Schema.Event.ExtendedPropertiesItem] = Json.format[Schema.Event.ExtendedPropertiesItem]
	given fmtEventFocusTimeProperties: Format[Schema.EventFocusTimeProperties] = Json.format[Schema.EventFocusTimeProperties]
	given fmtEventGadgetItem: Format[Schema.Event.GadgetItem] = Json.format[Schema.Event.GadgetItem]
	given fmtEventOrganizerItem: Format[Schema.Event.OrganizerItem] = Json.format[Schema.Event.OrganizerItem]
	given fmtEventOutOfOfficeProperties: Format[Schema.EventOutOfOfficeProperties] = Json.format[Schema.EventOutOfOfficeProperties]
	given fmtEventRemindersItem: Format[Schema.Event.RemindersItem] = Json.format[Schema.Event.RemindersItem]
	given fmtEventSourceItem: Format[Schema.Event.SourceItem] = Json.format[Schema.Event.SourceItem]
	given fmtEventWorkingLocationProperties: Format[Schema.EventWorkingLocationProperties] = Json.format[Schema.EventWorkingLocationProperties]
	given fmtEventWorkingLocationPropertiesCustomLocationItem: Format[Schema.EventWorkingLocationProperties.CustomLocationItem] = Json.format[Schema.EventWorkingLocationProperties.CustomLocationItem]
	given fmtEventWorkingLocationPropertiesOfficeLocationItem: Format[Schema.EventWorkingLocationProperties.OfficeLocationItem] = Json.format[Schema.EventWorkingLocationProperties.OfficeLocationItem]
	given fmtEvents: Format[Schema.Events] = Json.format[Schema.Events]
	given fmtFreeBusyCalendar: Format[Schema.FreeBusyCalendar] = Json.format[Schema.FreeBusyCalendar]
	given fmtTimePeriod: Format[Schema.TimePeriod] = Json.format[Schema.TimePeriod]
	given fmtFreeBusyGroup: Format[Schema.FreeBusyGroup] = Json.format[Schema.FreeBusyGroup]
	given fmtFreeBusyRequest: Format[Schema.FreeBusyRequest] = Json.format[Schema.FreeBusyRequest]
	given fmtFreeBusyRequestItem: Format[Schema.FreeBusyRequestItem] = Json.format[Schema.FreeBusyRequestItem]
	given fmtFreeBusyResponse: Format[Schema.FreeBusyResponse] = Json.format[Schema.FreeBusyResponse]
	given fmtSetting: Format[Schema.Setting] = Json.format[Schema.Setting]
	given fmtSettings: Format[Schema.Settings] = Json.format[Schema.Settings]
}
