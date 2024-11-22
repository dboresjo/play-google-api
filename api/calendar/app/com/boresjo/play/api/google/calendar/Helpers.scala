package com.boresjo.play.api.google.calendar

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Helpers {
	import Schema.*
	object AutoOption {
		given putString: Conversion[String, Option[String]] = (fun: String) => Option(fun)
		given putListSchemaAclRule: Conversion[List[Schema.AclRule], Option[List[Schema.AclRule]]] = (fun: List[Schema.AclRule]) => Option(fun)
		given putSchemaAclRuleScopeItem: Conversion[Schema.AclRule.ScopeItem, Option[Schema.AclRule.ScopeItem]] = (fun: Schema.AclRule.ScopeItem) => Option(fun)
		given putSchemaConferenceProperties: Conversion[Schema.ConferenceProperties, Option[Schema.ConferenceProperties]] = (fun: Schema.ConferenceProperties) => Option(fun)
		given putListSchemaCalendarListEntry: Conversion[List[Schema.CalendarListEntry], Option[List[Schema.CalendarListEntry]]] = (fun: List[Schema.CalendarListEntry]) => Option(fun)
		given putListSchemaEventReminder: Conversion[List[Schema.EventReminder], Option[List[Schema.EventReminder]]] = (fun: List[Schema.EventReminder]) => Option(fun)
		given putBoolean: Conversion[Boolean, Option[Boolean]] = (fun: Boolean) => Option(fun)
		given putSchemaCalendarListEntryNotificationSettingsItem: Conversion[Schema.CalendarListEntry.NotificationSettingsItem, Option[Schema.CalendarListEntry.NotificationSettingsItem]] = (fun: Schema.CalendarListEntry.NotificationSettingsItem) => Option(fun)
		given putMapStringString: Conversion[Map[String, String], Option[Map[String, String]]] = (fun: Map[String, String]) => Option(fun)
		given putMapStringSchemaColorDefinition: Conversion[Map[String, Schema.ColorDefinition], Option[Map[String, Schema.ColorDefinition]]] = (fun: Map[String, Schema.ColorDefinition]) => Option(fun)
		given putSchemaConferenceSolution: Conversion[Schema.ConferenceSolution, Option[Schema.ConferenceSolution]] = (fun: Schema.ConferenceSolution) => Option(fun)
		given putSchemaCreateConferenceRequest: Conversion[Schema.CreateConferenceRequest, Option[Schema.CreateConferenceRequest]] = (fun: Schema.CreateConferenceRequest) => Option(fun)
		given putListSchemaEntryPoint: Conversion[List[Schema.EntryPoint], Option[List[Schema.EntryPoint]]] = (fun: List[Schema.EntryPoint]) => Option(fun)
		given putSchemaConferenceParameters: Conversion[Schema.ConferenceParameters, Option[Schema.ConferenceParameters]] = (fun: Schema.ConferenceParameters) => Option(fun)
		given putSchemaConferenceParametersAddOnParameters: Conversion[Schema.ConferenceParametersAddOnParameters, Option[Schema.ConferenceParametersAddOnParameters]] = (fun: Schema.ConferenceParametersAddOnParameters) => Option(fun)
		given putListString: Conversion[List[String], Option[List[String]]] = (fun: List[String]) => Option(fun)
		given putSchemaConferenceSolutionKey: Conversion[Schema.ConferenceSolutionKey, Option[Schema.ConferenceSolutionKey]] = (fun: Schema.ConferenceSolutionKey) => Option(fun)
		given putSchemaConferenceRequestStatus: Conversion[Schema.ConferenceRequestStatus, Option[Schema.ConferenceRequestStatus]] = (fun: Schema.ConferenceRequestStatus) => Option(fun)
		given putListSchemaEventAttachment: Conversion[List[Schema.EventAttachment], Option[List[Schema.EventAttachment]]] = (fun: List[Schema.EventAttachment]) => Option(fun)
		given putListSchemaEventAttendee: Conversion[List[Schema.EventAttendee], Option[List[Schema.EventAttendee]]] = (fun: List[Schema.EventAttendee]) => Option(fun)
		given putSchemaEventBirthdayProperties: Conversion[Schema.EventBirthdayProperties, Option[Schema.EventBirthdayProperties]] = (fun: Schema.EventBirthdayProperties) => Option(fun)
		given putSchemaConferenceData: Conversion[Schema.ConferenceData, Option[Schema.ConferenceData]] = (fun: Schema.ConferenceData) => Option(fun)
		given putSchemaEventCreatorItem: Conversion[Schema.Event.CreatorItem, Option[Schema.Event.CreatorItem]] = (fun: Schema.Event.CreatorItem) => Option(fun)
		given putSchemaEventDateTime: Conversion[Schema.EventDateTime, Option[Schema.EventDateTime]] = (fun: Schema.EventDateTime) => Option(fun)
		given putSchemaEventExtendedPropertiesItem: Conversion[Schema.Event.ExtendedPropertiesItem, Option[Schema.Event.ExtendedPropertiesItem]] = (fun: Schema.Event.ExtendedPropertiesItem) => Option(fun)
		given putSchemaEventFocusTimeProperties: Conversion[Schema.EventFocusTimeProperties, Option[Schema.EventFocusTimeProperties]] = (fun: Schema.EventFocusTimeProperties) => Option(fun)
		given putSchemaEventGadgetItem: Conversion[Schema.Event.GadgetItem, Option[Schema.Event.GadgetItem]] = (fun: Schema.Event.GadgetItem) => Option(fun)
		given putSchemaEventOrganizerItem: Conversion[Schema.Event.OrganizerItem, Option[Schema.Event.OrganizerItem]] = (fun: Schema.Event.OrganizerItem) => Option(fun)
		given putSchemaEventOutOfOfficeProperties: Conversion[Schema.EventOutOfOfficeProperties, Option[Schema.EventOutOfOfficeProperties]] = (fun: Schema.EventOutOfOfficeProperties) => Option(fun)
		given putSchemaEventRemindersItem: Conversion[Schema.Event.RemindersItem, Option[Schema.Event.RemindersItem]] = (fun: Schema.Event.RemindersItem) => Option(fun)
		given putInt: Conversion[Int, Option[Int]] = (fun: Int) => Option(fun)
		given putSchemaEventSourceItem: Conversion[Schema.Event.SourceItem, Option[Schema.Event.SourceItem]] = (fun: Schema.Event.SourceItem) => Option(fun)
		given putSchemaEventWorkingLocationProperties: Conversion[Schema.EventWorkingLocationProperties, Option[Schema.EventWorkingLocationProperties]] = (fun: Schema.EventWorkingLocationProperties) => Option(fun)
		given putSchemaEventWorkingLocationPropertiesCustomLocationItem: Conversion[Schema.EventWorkingLocationProperties.CustomLocationItem, Option[Schema.EventWorkingLocationProperties.CustomLocationItem]] = (fun: Schema.EventWorkingLocationProperties.CustomLocationItem) => Option(fun)
		given putJsValue: Conversion[JsValue, Option[JsValue]] = (fun: JsValue) => Option(fun)
		given putSchemaEventWorkingLocationPropertiesOfficeLocationItem: Conversion[Schema.EventWorkingLocationProperties.OfficeLocationItem, Option[Schema.EventWorkingLocationProperties.OfficeLocationItem]] = (fun: Schema.EventWorkingLocationProperties.OfficeLocationItem) => Option(fun)
		given putListSchemaEvent: Conversion[List[Schema.Event], Option[List[Schema.Event]]] = (fun: List[Schema.Event]) => Option(fun)
		given putListSchemaTimePeriod: Conversion[List[Schema.TimePeriod], Option[List[Schema.TimePeriod]]] = (fun: List[Schema.TimePeriod]) => Option(fun)
		given putListSchemaError: Conversion[List[Schema.Error], Option[List[Schema.Error]]] = (fun: List[Schema.Error]) => Option(fun)
		given putListSchemaFreeBusyRequestItem: Conversion[List[Schema.FreeBusyRequestItem], Option[List[Schema.FreeBusyRequestItem]]] = (fun: List[Schema.FreeBusyRequestItem]) => Option(fun)
		given putMapStringSchemaFreeBusyCalendar: Conversion[Map[String, Schema.FreeBusyCalendar], Option[Map[String, Schema.FreeBusyCalendar]]] = (fun: Map[String, Schema.FreeBusyCalendar]) => Option(fun)
		given putMapStringSchemaFreeBusyGroup: Conversion[Map[String, Schema.FreeBusyGroup], Option[Map[String, Schema.FreeBusyGroup]]] = (fun: Map[String, Schema.FreeBusyGroup]) => Option(fun)
		given putListSchemaSetting: Conversion[List[Schema.Setting], Option[List[Schema.Setting]]] = (fun: List[Schema.Setting]) => Option(fun)
	}
	object OptionDefault {
		given getString: Conversion[Option[String], String] = (fun: Option[String]) => fun.getOrElse("")
	}
}
