package com.boresjo.play.api.google.calendar

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class Acl(
	  /** ETag of the collection. */
		etag: Option[String] = None,
	  /** List of rules on the access control list. */
		items: Option[List[Schema.AclRule]] = None,
	  /** Type of the collection ("calendar#acl"). */
		kind: Option[String] = Some("""calendar#acl"""),
	  /** Token used to access the next page of this result. Omitted if no further results are available, in which case nextSyncToken is provided. */
		nextPageToken: Option[String] = None,
	  /** Token used at a later point in time to retrieve only the entries that have changed since this result was returned. Omitted if further results are available, in which case nextPageToken is provided. */
		nextSyncToken: Option[String] = None
	)
	
	object AclRule {
		case class ScopeItem(
		  /** The type of the scope. Possible values are:  
		- "default" - The public scope. This is the default value. 
		- "user" - Limits the scope to a single user. 
		- "group" - Limits the scope to a group. 
		- "domain" - Limits the scope to a domain.  Note: The permissions granted to the "default", or public, scope apply to any user, authenticated or not. */
			`type`: Option[String] = None,
		  /** The email address of a user or group, or the name of a domain, depending on the scope type. Omitted for type "default". */
			value: Option[String] = None
		)
	}
	case class AclRule(
	  /** ETag of the resource. */
		etag: Option[String] = None,
	  /** Identifier of the Access Control List (ACL) rule. See Sharing calendars. */
		id: Option[String] = None,
	  /** Type of the resource ("calendar#aclRule"). */
		kind: Option[String] = Some("""calendar#aclRule"""),
	  /** The role assigned to the scope. Possible values are:  
	- "none" - Provides no access. 
	- "freeBusyReader" - Provides read access to free/busy information. 
	- "reader" - Provides read access to the calendar. Private events will appear to users with reader access, but event details will be hidden. 
	- "writer" - Provides read and write access to the calendar. Private events will appear to users with writer access, and event details will be visible. 
	- "owner" - Provides ownership of the calendar. This role has all of the permissions of the writer role with the additional ability to see and manipulate ACLs. */
		role: Option[String] = None,
	  /** The extent to which calendar access is granted by this ACL rule. */
		scope: Option[Schema.AclRule.ScopeItem] = None
	)
	
	case class Calendar(
	  /** Conferencing properties for this calendar, for example what types of conferences are allowed. */
		conferenceProperties: Option[Schema.ConferenceProperties] = None,
	  /** Description of the calendar. Optional. */
		description: Option[String] = None,
	  /** ETag of the resource. */
		etag: Option[String] = None,
	  /** Identifier of the calendar. To retrieve IDs call the calendarList.list() method. */
		id: Option[String] = None,
	  /** Type of the resource ("calendar#calendar"). */
		kind: Option[String] = Some("""calendar#calendar"""),
	  /** Geographic location of the calendar as free-form text. Optional. */
		location: Option[String] = None,
	  /** Title of the calendar. */
		summary: Option[String] = None,
	  /** The time zone of the calendar. (Formatted as an IANA Time Zone Database name, e.g. "Europe/Zurich".) Optional. */
		timeZone: Option[String] = None
	)
	
	case class CalendarList(
	  /** ETag of the collection. */
		etag: Option[String] = None,
	  /** Calendars that are present on the user's calendar list. */
		items: Option[List[Schema.CalendarListEntry]] = None,
	  /** Type of the collection ("calendar#calendarList"). */
		kind: Option[String] = Some("""calendar#calendarList"""),
	  /** Token used to access the next page of this result. Omitted if no further results are available, in which case nextSyncToken is provided. */
		nextPageToken: Option[String] = None,
	  /** Token used at a later point in time to retrieve only the entries that have changed since this result was returned. Omitted if further results are available, in which case nextPageToken is provided. */
		nextSyncToken: Option[String] = None
	)
	
	object CalendarListEntry {
		case class NotificationSettingsItem(
		  /** The list of notifications set for this calendar. */
			notifications: Option[List[Schema.CalendarNotification]] = None
		)
	}
	case class CalendarListEntry(
	  /** The effective access role that the authenticated user has on the calendar. Read-only. Possible values are:  
	- "freeBusyReader" - Provides read access to free/busy information. 
	- "reader" - Provides read access to the calendar. Private events will appear to users with reader access, but event details will be hidden. 
	- "writer" - Provides read and write access to the calendar. Private events will appear to users with writer access, and event details will be visible. 
	- "owner" - Provides ownership of the calendar. This role has all of the permissions of the writer role with the additional ability to see and manipulate ACLs. */
		accessRole: Option[String] = None,
	  /** The main color of the calendar in the hexadecimal format "#0088aa". This property supersedes the index-based colorId property. To set or change this property, you need to specify colorRgbFormat=true in the parameters of the insert, update and patch methods. Optional. */
		backgroundColor: Option[String] = None,
	  /** The color of the calendar. This is an ID referring to an entry in the calendar section of the colors definition (see the colors endpoint). This property is superseded by the backgroundColor and foregroundColor properties and can be ignored when using these properties. Optional. */
		colorId: Option[String] = None,
	  /** Conferencing properties for this calendar, for example what types of conferences are allowed. */
		conferenceProperties: Option[Schema.ConferenceProperties] = None,
	  /** The default reminders that the authenticated user has for this calendar. */
		defaultReminders: Option[List[Schema.EventReminder]] = None,
	  /** Whether this calendar list entry has been deleted from the calendar list. Read-only. Optional. The default is False. */
		deleted: Option[Boolean] = Some(false),
	  /** Description of the calendar. Optional. Read-only. */
		description: Option[String] = None,
	  /** ETag of the resource. */
		etag: Option[String] = None,
	  /** The foreground color of the calendar in the hexadecimal format "#ffffff". This property supersedes the index-based colorId property. To set or change this property, you need to specify colorRgbFormat=true in the parameters of the insert, update and patch methods. Optional. */
		foregroundColor: Option[String] = None,
	  /** Whether the calendar has been hidden from the list. Optional. The attribute is only returned when the calendar is hidden, in which case the value is true. */
		hidden: Option[Boolean] = Some(false),
	  /** Identifier of the calendar. */
		id: Option[String] = None,
	  /** Type of the resource ("calendar#calendarListEntry"). */
		kind: Option[String] = Some("""calendar#calendarListEntry"""),
	  /** Geographic location of the calendar as free-form text. Optional. Read-only. */
		location: Option[String] = None,
	  /** The notifications that the authenticated user is receiving for this calendar. */
		notificationSettings: Option[Schema.CalendarListEntry.NotificationSettingsItem] = None,
	  /** Whether the calendar is the primary calendar of the authenticated user. Read-only. Optional. The default is False. */
		primary: Option[Boolean] = Some(false),
	  /** Whether the calendar content shows up in the calendar UI. Optional. The default is False. */
		selected: Option[Boolean] = Some(false),
	  /** Title of the calendar. Read-only. */
		summary: Option[String] = None,
	  /** The summary that the authenticated user has set for this calendar. Optional. */
		summaryOverride: Option[String] = None,
	  /** The time zone of the calendar. Optional. Read-only. */
		timeZone: Option[String] = None
	)
	
	case class CalendarNotification(
	  /** The method used to deliver the notification. The possible value is:  
	- "email" - Notifications are sent via email.  
	Required when adding a notification. */
		method: Option[String] = None,
	  /** The type of notification. Possible values are:  
	- "eventCreation" - Notification sent when a new event is put on the calendar. 
	- "eventChange" - Notification sent when an event is changed. 
	- "eventCancellation" - Notification sent when an event is cancelled. 
	- "eventResponse" - Notification sent when an attendee responds to the event invitation. 
	- "agenda" - An agenda with the events of the day (sent out in the morning).  
	Required when adding a notification. */
		`type`: Option[String] = None
	)
	
	case class Channel(
	  /** The address where notifications are delivered for this channel. */
		address: Option[String] = None,
	  /** Date and time of notification channel expiration, expressed as a Unix timestamp, in milliseconds. Optional. */
		expiration: Option[String] = None,
	  /** A UUID or similar unique string that identifies this channel. */
		id: Option[String] = None,
	  /** Identifies this as a notification channel used to watch for changes to a resource, which is "api#channel". */
		kind: Option[String] = Some("""api#channel"""),
	  /** Additional parameters controlling delivery channel behavior. Optional. */
		params: Option[Map[String, String]] = None,
	  /** A Boolean value to indicate whether payload is wanted. Optional. */
		payload: Option[Boolean] = None,
	  /** An opaque ID that identifies the resource being watched on this channel. Stable across different API versions. */
		resourceId: Option[String] = None,
	  /** A version-specific identifier for the watched resource. */
		resourceUri: Option[String] = None,
	  /** An arbitrary string delivered to the target address with each notification delivered over this channel. Optional. */
		token: Option[String] = None,
	  /** The type of delivery mechanism used for this channel. Valid values are "web_hook" (or "webhook"). Both values refer to a channel where Http requests are used to deliver messages. */
		`type`: Option[String] = None
	)
	
	case class ColorDefinition(
	  /** The background color associated with this color definition. */
		background: Option[String] = None,
	  /** The foreground color that can be used to write on top of a background with 'background' color. */
		foreground: Option[String] = None
	)
	
	case class Colors(
	  /** A global palette of calendar colors, mapping from the color ID to its definition. A calendarListEntry resource refers to one of these color IDs in its colorId field. Read-only. */
		calendar: Option[Map[String, Schema.ColorDefinition]] = None,
	  /** A global palette of event colors, mapping from the color ID to its definition. An event resource may refer to one of these color IDs in its colorId field. Read-only. */
		event: Option[Map[String, Schema.ColorDefinition]] = None,
	  /** Type of the resource ("calendar#colors"). */
		kind: Option[String] = Some("""calendar#colors"""),
	  /** Last modification time of the color palette (as a RFC3339 timestamp). Read-only. */
		updated: Option[String] = None
	)
	
	case class ConferenceData(
	  /** The ID of the conference.
	Can be used by developers to keep track of conferences, should not be displayed to users.
	The ID value is formed differently for each conference solution type:  
	- eventHangout: ID is not set. (This conference type is deprecated.)
	- eventNamedHangout: ID is the name of the Hangout. (This conference type is deprecated.)
	- hangoutsMeet: ID is the 10-letter meeting code, for example aaa-bbbb-ccc.
	- addOn: ID is defined by the third-party provider.  Optional. */
		conferenceId: Option[String] = None,
	  /** The conference solution, such as Google Meet.
	Unset for a conference with a failed create request.
	Either conferenceSolution and at least one entryPoint, or createRequest is required. */
		conferenceSolution: Option[Schema.ConferenceSolution] = None,
	  /** A request to generate a new conference and attach it to the event. The data is generated asynchronously. To see whether the data is present check the status field.
	Either conferenceSolution and at least one entryPoint, or createRequest is required. */
		createRequest: Option[Schema.CreateConferenceRequest] = None,
	  /** Information about individual conference entry points, such as URLs or phone numbers.
	All of them must belong to the same conference.
	Either conferenceSolution and at least one entryPoint, or createRequest is required. */
		entryPoints: Option[List[Schema.EntryPoint]] = None,
	  /** Additional notes (such as instructions from the domain administrator, legal notices) to display to the user. Can contain HTML. The maximum length is 2048 characters. Optional. */
		notes: Option[String] = None,
	  /** Additional properties related to a conference. An example would be a solution-specific setting for enabling video streaming. */
		parameters: Option[Schema.ConferenceParameters] = None,
	  /** The signature of the conference data.
	Generated on server side.
	Unset for a conference with a failed create request.
	Optional for a conference with a pending create request. */
		signature: Option[String] = None
	)
	
	case class ConferenceParameters(
	  /** Additional add-on specific data. */
		addOnParameters: Option[Schema.ConferenceParametersAddOnParameters] = None
	)
	
	case class ConferenceParametersAddOnParameters(
		parameters: Option[Map[String, String]] = None
	)
	
	case class ConferenceProperties(
	  /** The types of conference solutions that are supported for this calendar.
	The possible values are:  
	- "eventHangout" 
	- "eventNamedHangout" 
	- "hangoutsMeet"  Optional. */
		allowedConferenceSolutionTypes: Option[List[String]] = None
	)
	
	case class ConferenceRequestStatus(
	  /** The current status of the conference create request. Read-only.
	The possible values are:  
	- "pending": the conference create request is still being processed.
	- "success": the conference create request succeeded, the entry points are populated.
	- "failure": the conference create request failed, there are no entry points. */
		statusCode: Option[String] = None
	)
	
	case class ConferenceSolution(
	  /** The user-visible icon for this solution. */
		iconUri: Option[String] = None,
	  /** The key which can uniquely identify the conference solution for this event. */
		key: Option[Schema.ConferenceSolutionKey] = None,
	  /** The user-visible name of this solution. Not localized. */
		name: Option[String] = None
	)
	
	case class ConferenceSolutionKey(
	  /** The conference solution type.
	If a client encounters an unfamiliar or empty type, it should still be able to display the entry points. However, it should disallow modifications.
	The possible values are:  
	- "eventHangout" for Hangouts for consumers (deprecated; existing events may show this conference solution type but new conferences cannot be created)
	- "eventNamedHangout" for classic Hangouts for Google Workspace users (deprecated; existing events may show this conference solution type but new conferences cannot be created)
	- "hangoutsMeet" for Google Meet (http://meet.google.com)
	- "addOn" for 3P conference providers */
		`type`: Option[String] = None
	)
	
	case class CreateConferenceRequest(
	  /** The conference solution, such as Hangouts or Google Meet. */
		conferenceSolutionKey: Option[Schema.ConferenceSolutionKey] = None,
	  /** The client-generated unique ID for this request.
	Clients should regenerate this ID for every new request. If an ID provided is the same as for the previous request, the request is ignored. */
		requestId: Option[String] = None,
	  /** The status of the conference create request. */
		status: Option[Schema.ConferenceRequestStatus] = None
	)
	
	case class EntryPoint(
	  /** The access code to access the conference. The maximum length is 128 characters.
	When creating new conference data, populate only the subset of {meetingCode, accessCode, passcode, password, pin} fields that match the terminology that the conference provider uses. Only the populated fields should be displayed.
	Optional. */
		accessCode: Option[String] = None,
	  /** Features of the entry point, such as being toll or toll-free. One entry point can have multiple features. However, toll and toll-free cannot be both set on the same entry point. */
		entryPointFeatures: Option[List[String]] = None,
	  /** The type of the conference entry point.
	Possible values are:  
	- "video" - joining a conference over HTTP. A conference can have zero or one video entry point.
	- "phone" - joining a conference by dialing a phone number. A conference can have zero or more phone entry points.
	- "sip" - joining a conference over SIP. A conference can have zero or one sip entry point.
	- "more" - further conference joining instructions, for example additional phone numbers. A conference can have zero or one more entry point. A conference with only a more entry point is not a valid conference. */
		entryPointType: Option[String] = None,
	  /** The label for the URI. Visible to end users. Not localized. The maximum length is 512 characters.
	Examples:  
	- for video: meet.google.com/aaa-bbbb-ccc
	- for phone: +1 123 268 2601
	- for sip: 12345678@altostrat.com
	- for more: should not be filled  
	Optional. */
		label: Option[String] = None,
	  /** The meeting code to access the conference. The maximum length is 128 characters.
	When creating new conference data, populate only the subset of {meetingCode, accessCode, passcode, password, pin} fields that match the terminology that the conference provider uses. Only the populated fields should be displayed.
	Optional. */
		meetingCode: Option[String] = None,
	  /** The passcode to access the conference. The maximum length is 128 characters.
	When creating new conference data, populate only the subset of {meetingCode, accessCode, passcode, password, pin} fields that match the terminology that the conference provider uses. Only the populated fields should be displayed. */
		passcode: Option[String] = None,
	  /** The password to access the conference. The maximum length is 128 characters.
	When creating new conference data, populate only the subset of {meetingCode, accessCode, passcode, password, pin} fields that match the terminology that the conference provider uses. Only the populated fields should be displayed.
	Optional. */
		password: Option[String] = None,
	  /** The PIN to access the conference. The maximum length is 128 characters.
	When creating new conference data, populate only the subset of {meetingCode, accessCode, passcode, password, pin} fields that match the terminology that the conference provider uses. Only the populated fields should be displayed.
	Optional. */
		pin: Option[String] = None,
	  /** The CLDR/ISO 3166 region code for the country associated with this phone access. Example: "SE" for Sweden.
	Calendar backend will populate this field only for EntryPointType.PHONE. */
		regionCode: Option[String] = None,
	  /** The URI of the entry point. The maximum length is 1300 characters.
	Format:  
	- for video, http: or https: schema is required.
	- for phone, tel: schema is required. The URI should include the entire dial sequence (e.g., tel:+12345678900,,,123456789;1234).
	- for sip, sip: schema is required, e.g., sip:12345678@myprovider.com.
	- for more, http: or https: schema is required. */
		uri: Option[String] = None
	)
	
	case class Error(
	  /** Domain, or broad category, of the error. */
		domain: Option[String] = None,
	  /** Specific reason for the error. Some of the possible values are:  
	- "groupTooBig" - The group of users requested is too large for a single query. 
	- "tooManyCalendarsRequested" - The number of calendars requested is too large for a single query. 
	- "notFound" - The requested resource was not found. 
	- "internalError" - The API service has encountered an internal error.  Additional error types may be added in the future, so clients should gracefully handle additional error statuses not included in this list. */
		reason: Option[String] = None
	)
	
	object Event {
		case class CreatorItem(
		  /** The creator's name, if available. */
			displayName: Option[String] = None,
		  /** The creator's email address, if available. */
			email: Option[String] = None,
		  /** The creator's Profile ID, if available. */
			id: Option[String] = None,
		  /** Whether the creator corresponds to the calendar on which this copy of the event appears. Read-only. The default is False. */
			self: Option[Boolean] = Some(false)
		)
		
		case class ExtendedPropertiesItem(
		  /** Properties that are private to the copy of the event that appears on this calendar. */
			`private`: Option[Map[String, String]] = None,
		  /** Properties that are shared between copies of the event on other attendees' calendars. */
			shared: Option[Map[String, String]] = None
		)
		
		case class GadgetItem(
		  /** The gadget's display mode. Deprecated. Possible values are:  
		- "icon" - The gadget displays next to the event's title in the calendar view. 
		- "chip" - The gadget displays when the event is clicked. */
			display: Option[String] = None,
		  /** The gadget's height in pixels. The height must be an integer greater than 0. Optional. Deprecated. */
			height: Option[Int] = None,
		  /** The gadget's icon URL. The URL scheme must be HTTPS. Deprecated. */
			iconLink: Option[String] = None,
		  /** The gadget's URL. The URL scheme must be HTTPS. Deprecated. */
			link: Option[String] = None,
		  /** Preferences. */
			preferences: Option[Map[String, String]] = None,
		  /** The gadget's title. Deprecated. */
			title: Option[String] = None,
		  /** The gadget's type. Deprecated. */
			`type`: Option[String] = None,
		  /** The gadget's width in pixels. The width must be an integer greater than 0. Optional. Deprecated. */
			width: Option[Int] = None
		)
		
		case class OrganizerItem(
		  /** The organizer's name, if available. */
			displayName: Option[String] = None,
		  /** The organizer's email address, if available. It must be a valid email address as per RFC5322. */
			email: Option[String] = None,
		  /** The organizer's Profile ID, if available. */
			id: Option[String] = None,
		  /** Whether the organizer corresponds to the calendar on which this copy of the event appears. Read-only. The default is False. */
			self: Option[Boolean] = Some(false)
		)
		
		case class RemindersItem(
		  /** If the event doesn't use the default reminders, this lists the reminders specific to the event, or, if not set, indicates that no reminders are set for this event. The maximum number of override reminders is 5. */
			overrides: Option[List[Schema.EventReminder]] = None,
		  /** Whether the default reminders of the calendar apply to the event. */
			useDefault: Option[Boolean] = None
		)
		
		case class SourceItem(
		  /** Title of the source; for example a title of a web page or an email subject. */
			title: Option[String] = None,
		  /** URL of the source pointing to a resource. The URL scheme must be HTTP or HTTPS. */
			url: Option[String] = None
		)
	}
	case class Event(
	  /** Whether anyone can invite themselves to the event (deprecated). Optional. The default is False. */
		anyoneCanAddSelf: Option[Boolean] = Some(false),
	  /** File attachments for the event.
	In order to modify attachments the supportsAttachments request parameter should be set to true.
	There can be at most 25 attachments per event, */
		attachments: Option[List[Schema.EventAttachment]] = None,
	  /** The attendees of the event. See the Events with attendees guide for more information on scheduling events with other calendar users. Service accounts need to use domain-wide delegation of authority to populate the attendee list. */
		attendees: Option[List[Schema.EventAttendee]] = None,
	  /** Whether attendees may have been omitted from the event's representation. When retrieving an event, this may be due to a restriction specified by the maxAttendee query parameter. When updating an event, this can be used to only update the participant's response. Optional. The default is False. */
		attendeesOmitted: Option[Boolean] = Some(false),
	  /** Birthday or special event data. Used if eventType is "birthday". Immutable. */
		birthdayProperties: Option[Schema.EventBirthdayProperties] = None,
	  /** The color of the event. This is an ID referring to an entry in the event section of the colors definition (see the  colors endpoint). Optional. */
		colorId: Option[String] = None,
	  /** The conference-related information, such as details of a Google Meet conference. To create new conference details use the createRequest field. To persist your changes, remember to set the conferenceDataVersion request parameter to 1 for all event modification requests. */
		conferenceData: Option[Schema.ConferenceData] = None,
	  /** Creation time of the event (as a RFC3339 timestamp). Read-only. */
		created: Option[String] = None,
	  /** The creator of the event. Read-only. */
		creator: Option[Schema.Event.CreatorItem] = None,
	  /** Description of the event. Can contain HTML. Optional. */
		description: Option[String] = None,
	  /** The (exclusive) end time of the event. For a recurring event, this is the end time of the first instance. */
		end: Option[Schema.EventDateTime] = None,
	  /** Whether the end time is actually unspecified. An end time is still provided for compatibility reasons, even if this attribute is set to True. The default is False. */
		endTimeUnspecified: Option[Boolean] = Some(false),
	  /** ETag of the resource. */
		etag: Option[String] = None,
	  /** Specific type of the event. This cannot be modified after the event is created. Possible values are:  
	- "birthday" - A special all-day event with an annual recurrence. 
	- "default" - A regular event or not further specified. 
	- "focusTime" - A focus-time event. 
	- "fromGmail" - An event from Gmail. This type of event cannot be created. 
	- "outOfOffice" - An out-of-office event. 
	- "workingLocation" - A working location event. */
		eventType: Option[String] = Some("""default"""),
	  /** Extended properties of the event. */
		extendedProperties: Option[Schema.Event.ExtendedPropertiesItem] = None,
	  /** Focus Time event data. Used if eventType is focusTime. */
		focusTimeProperties: Option[Schema.EventFocusTimeProperties] = None,
	  /** A gadget that extends this event. Gadgets are deprecated; this structure is instead only used for returning birthday calendar metadata. */
		gadget: Option[Schema.Event.GadgetItem] = None,
	  /** Whether attendees other than the organizer can invite others to the event. Optional. The default is True. */
		guestsCanInviteOthers: Option[Boolean] = Some(true),
	  /** Whether attendees other than the organizer can modify the event. Optional. The default is False. */
		guestsCanModify: Option[Boolean] = Some(false),
	  /** Whether attendees other than the organizer can see who the event's attendees are. Optional. The default is True. */
		guestsCanSeeOtherGuests: Option[Boolean] = Some(true),
	  /** An absolute link to the Google Hangout associated with this event. Read-only. */
		hangoutLink: Option[String] = None,
	  /** An absolute link to this event in the Google Calendar Web UI. Read-only. */
		htmlLink: Option[String] = None,
	  /** Event unique identifier as defined in RFC5545. It is used to uniquely identify events accross calendaring systems and must be supplied when importing events via the import method.
	Note that the iCalUID and the id are not identical and only one of them should be supplied at event creation time. One difference in their semantics is that in recurring events, all occurrences of one event have different ids while they all share the same iCalUIDs. To retrieve an event using its iCalUID, call the events.list method using the iCalUID parameter. To retrieve an event using its id, call the events.get method. */
		iCalUID: Option[String] = None,
	  /** Opaque identifier of the event. When creating new single or recurring events, you can specify their IDs. Provided IDs must follow these rules:  
	- characters allowed in the ID are those used in base32hex encoding, i.e. lowercase letters a-v and digits 0-9, see section 3.1.2 in RFC2938 
	- the length of the ID must be between 5 and 1024 characters 
	- the ID must be unique per calendar  Due to the globally distributed nature of the system, we cannot guarantee that ID collisions will be detected at event creation time. To minimize the risk of collisions we recommend using an established UUID algorithm such as one described in RFC4122.
	If you do not specify an ID, it will be automatically generated by the server.
	Note that the icalUID and the id are not identical and only one of them should be supplied at event creation time. One difference in their semantics is that in recurring events, all occurrences of one event have different ids while they all share the same icalUIDs. */
		id: Option[String] = None,
	  /** Type of the resource ("calendar#event"). */
		kind: Option[String] = Some("""calendar#event"""),
	  /** Geographic location of the event as free-form text. Optional. */
		location: Option[String] = None,
	  /** Whether this is a locked event copy where no changes can be made to the main event fields "summary", "description", "location", "start", "end" or "recurrence". The default is False. Read-Only. */
		locked: Option[Boolean] = Some(false),
	  /** The organizer of the event. If the organizer is also an attendee, this is indicated with a separate entry in attendees with the organizer field set to True. To change the organizer, use the move operation. Read-only, except when importing an event. */
		organizer: Option[Schema.Event.OrganizerItem] = None,
	  /** For an instance of a recurring event, this is the time at which this event would start according to the recurrence data in the recurring event identified by recurringEventId. It uniquely identifies the instance within the recurring event series even if the instance was moved to a different time. Immutable. */
		originalStartTime: Option[Schema.EventDateTime] = None,
	  /** Out of office event data. Used if eventType is outOfOffice. */
		outOfOfficeProperties: Option[Schema.EventOutOfOfficeProperties] = None,
	  /** If set to True, Event propagation is disabled. Note that it is not the same thing as Private event properties. Optional. Immutable. The default is False. */
		privateCopy: Option[Boolean] = Some(false),
	  /** List of RRULE, EXRULE, RDATE and EXDATE lines for a recurring event, as specified in RFC5545. Note that DTSTART and DTEND lines are not allowed in this field; event start and end times are specified in the start and end fields. This field is omitted for single events or instances of recurring events. */
		recurrence: Option[List[String]] = None,
	  /** For an instance of a recurring event, this is the id of the recurring event to which this instance belongs. Immutable. */
		recurringEventId: Option[String] = None,
	  /** Information about the event's reminders for the authenticated user. Note that changing reminders does not also change the updated property of the enclosing event. */
		reminders: Option[Schema.Event.RemindersItem] = None,
	  /** Sequence number as per iCalendar. */
		sequence: Option[Int] = None,
	  /** Source from which the event was created. For example, a web page, an email message or any document identifiable by an URL with HTTP or HTTPS scheme. Can only be seen or modified by the creator of the event. */
		source: Option[Schema.Event.SourceItem] = None,
	  /** The (inclusive) start time of the event. For a recurring event, this is the start time of the first instance. */
		start: Option[Schema.EventDateTime] = None,
	  /** Status of the event. Optional. Possible values are:  
	- "confirmed" - The event is confirmed. This is the default status. 
	- "tentative" - The event is tentatively confirmed. 
	- "cancelled" - The event is cancelled (deleted). The list method returns cancelled events only on incremental sync (when syncToken or updatedMin are specified) or if the showDeleted flag is set to true. The get method always returns them.
	A cancelled status represents two different states depending on the event type:  
	- Cancelled exceptions of an uncancelled recurring event indicate that this instance should no longer be presented to the user. Clients should store these events for the lifetime of the parent recurring event.
	Cancelled exceptions are only guaranteed to have values for the id, recurringEventId and originalStartTime fields populated. The other fields might be empty.  
	- All other cancelled events represent deleted events. Clients should remove their locally synced copies. Such cancelled events will eventually disappear, so do not rely on them being available indefinitely.
	Deleted events are only guaranteed to have the id field populated.   On the organizer's calendar, cancelled events continue to expose event details (summary, location, etc.) so that they can be restored (undeleted). Similarly, the events to which the user was invited and that they manually removed continue to provide details. However, incremental sync requests with showDeleted set to false will not return these details.
	If an event changes its organizer (for example via the move operation) and the original organizer is not on the attendee list, it will leave behind a cancelled event where only the id field is guaranteed to be populated. */
		status: Option[String] = None,
	  /** Title of the event. */
		summary: Option[String] = None,
	  /** Whether the event blocks time on the calendar. Optional. Possible values are:  
	- "opaque" - Default value. The event does block time on the calendar. This is equivalent to setting Show me as to Busy in the Calendar UI. 
	- "transparent" - The event does not block time on the calendar. This is equivalent to setting Show me as to Available in the Calendar UI. */
		transparency: Option[String] = Some("""opaque"""),
	  /** Last modification time of the main event data (as a RFC3339 timestamp). Updating event reminders will not cause this to change. Read-only. */
		updated: Option[String] = None,
	  /** Visibility of the event. Optional. Possible values are:  
	- "default" - Uses the default visibility for events on the calendar. This is the default value. 
	- "public" - The event is public and event details are visible to all readers of the calendar. 
	- "private" - The event is private and only event attendees may view event details. 
	- "confidential" - The event is private. This value is provided for compatibility reasons. */
		visibility: Option[String] = Some("""default"""),
	  /** Working location event data. */
		workingLocationProperties: Option[Schema.EventWorkingLocationProperties] = None
	)
	
	case class EventAttachment(
	  /** ID of the attached file. Read-only.
	For Google Drive files, this is the ID of the corresponding Files resource entry in the Drive API. */
		fileId: Option[String] = None,
	  /** URL link to the attachment.
	For adding Google Drive file attachments use the same format as in alternateLink property of the Files resource in the Drive API.
	Required when adding an attachment. */
		fileUrl: Option[String] = None,
	  /** URL link to the attachment's icon. This field can only be modified for custom third-party attachments. */
		iconLink: Option[String] = None,
	  /** Internet media type (MIME type) of the attachment. */
		mimeType: Option[String] = None,
	  /** Attachment title. */
		title: Option[String] = None
	)
	
	case class EventAttendee(
	  /** Number of additional guests. Optional. The default is 0. */
		additionalGuests: Option[Int] = Some(0),
	  /** The attendee's response comment. Optional. */
		comment: Option[String] = None,
	  /** The attendee's name, if available. Optional. */
		displayName: Option[String] = None,
	  /** The attendee's email address, if available. This field must be present when adding an attendee. It must be a valid email address as per RFC5322.
	Required when adding an attendee. */
		email: Option[String] = None,
	  /** The attendee's Profile ID, if available. */
		id: Option[String] = None,
	  /** Whether this is an optional attendee. Optional. The default is False. */
		optional: Option[Boolean] = Some(false),
	  /** Whether the attendee is the organizer of the event. Read-only. The default is False. */
		organizer: Option[Boolean] = None,
	  /** Whether the attendee is a resource. Can only be set when the attendee is added to the event for the first time. Subsequent modifications are ignored. Optional. The default is False. */
		resource: Option[Boolean] = Some(false),
	  /** The attendee's response status. Possible values are:  
	- "needsAction" - The attendee has not responded to the invitation (recommended for new events). 
	- "declined" - The attendee has declined the invitation. 
	- "tentative" - The attendee has tentatively accepted the invitation. 
	- "accepted" - The attendee has accepted the invitation.  Warning: If you add an event using the values declined, tentative, or accepted, attendees with the "Add invitations to my calendar" setting set to "When I respond to invitation in email" or "Only if the sender is known" might have their response reset to needsAction and won't see an event in their calendar unless they change their response in the event invitation email. Furthermore, if more than 200 guests are invited to the event, response status is not propagated to the guests. */
		responseStatus: Option[String] = None,
	  /** Whether this entry represents the calendar on which this copy of the event appears. Read-only. The default is False. */
		self: Option[Boolean] = Some(false)
	)
	
	case class EventBirthdayProperties(
	  /** Resource name of the contact this birthday event is linked to. This can be used to fetch contact details from People API. Format: "people/c12345". Read-only. */
		contact: Option[String] = None,
	  /** Custom type label specified for this event. This is populated if birthdayProperties.type is set to "custom". Read-only. */
		customTypeName: Option[String] = None,
	  /** Type of birthday or special event. Possible values are:  
	- "anniversary" - An anniversary other than birthday. Always has a contact. 
	- "birthday" - A birthday event. This is the default value. 
	- "custom" - A special date whose label is further specified in the customTypeName field. Always has a contact. 
	- "other" - A special date which does not fall into the other categories, and does not have a custom label. Always has a contact. 
	- "self" - Calendar owner's own birthday. Cannot have a contact.  The Calendar API only supports creating events with the type "birthday". The type cannot be changed after the event is created. */
		`type`: Option[String] = Some("""birthday""")
	)
	
	case class EventDateTime(
	  /** The date, in the format "yyyy-mm-dd", if this is an all-day event. */
		date: Option[String] = None,
	  /** The time, as a combined date-time value (formatted according to RFC3339). A time zone offset is required unless a time zone is explicitly specified in timeZone. */
		dateTime: Option[String] = None,
	  /** The time zone in which the time is specified. (Formatted as an IANA Time Zone Database name, e.g. "Europe/Zurich".) For recurring events this field is required and specifies the time zone in which the recurrence is expanded. For single events this field is optional and indicates a custom time zone for the event start/end. */
		timeZone: Option[String] = None
	)
	
	case class EventFocusTimeProperties(
	  /** Whether to decline meeting invitations which overlap Focus Time events. Valid values are declineNone, meaning that no meeting invitations are declined; declineAllConflictingInvitations, meaning that all conflicting meeting invitations that conflict with the event are declined; and declineOnlyNewConflictingInvitations, meaning that only new conflicting meeting invitations which arrive while the Focus Time event is present are to be declined. */
		autoDeclineMode: Option[String] = None,
	  /** The status to mark the user in Chat and related products. This can be available or doNotDisturb. */
		chatStatus: Option[String] = None,
	  /** Response message to set if an existing event or new invitation is automatically declined by Calendar. */
		declineMessage: Option[String] = None
	)
	
	case class EventOutOfOfficeProperties(
	  /** Whether to decline meeting invitations which overlap Out of office events. Valid values are declineNone, meaning that no meeting invitations are declined; declineAllConflictingInvitations, meaning that all conflicting meeting invitations that conflict with the event are declined; and declineOnlyNewConflictingInvitations, meaning that only new conflicting meeting invitations which arrive while the Out of office event is present are to be declined. */
		autoDeclineMode: Option[String] = None,
	  /** Response message to set if an existing event or new invitation is automatically declined by Calendar. */
		declineMessage: Option[String] = None
	)
	
	case class EventReminder(
	  /** The method used by this reminder. Possible values are:  
	- "email" - Reminders are sent via email. 
	- "popup" - Reminders are sent via a UI popup.  
	Required when adding a reminder. */
		method: Option[String] = None,
	  /** Number of minutes before the start of the event when the reminder should trigger. Valid values are between 0 and 40320 (4 weeks in minutes).
	Required when adding a reminder. */
		minutes: Option[Int] = None
	)
	
	object EventWorkingLocationProperties {
		case class CustomLocationItem(
		  /** An optional extra label for additional information. */
			label: Option[String] = None
		)
		
		case class OfficeLocationItem(
		  /** An optional building identifier. This should reference a building ID in the organization's Resources database. */
			buildingId: Option[String] = None,
		  /** An optional desk identifier. */
			deskId: Option[String] = None,
		  /** An optional floor identifier. */
			floorId: Option[String] = None,
		  /** An optional floor section identifier. */
			floorSectionId: Option[String] = None,
		  /** The office name that's displayed in Calendar Web and Mobile clients. We recommend you reference a building name in the organization's Resources database. */
			label: Option[String] = None
		)
	}
	case class EventWorkingLocationProperties(
	  /** If present, specifies that the user is working from a custom location. */
		customLocation: Option[Schema.EventWorkingLocationProperties.CustomLocationItem] = None,
	  /** If present, specifies that the user is working at home. */
		homeOffice: Option[JsValue] = None,
	  /** If present, specifies that the user is working from an office. */
		officeLocation: Option[Schema.EventWorkingLocationProperties.OfficeLocationItem] = None,
	  /** Type of the working location. Possible values are:  
	- "homeOffice" - The user is working at home. 
	- "officeLocation" - The user is working from an office. 
	- "customLocation" - The user is working from a custom location.  Any details are specified in a sub-field of the specified name, but this field may be missing if empty. Any other fields are ignored.
	Required when adding working location properties. */
		`type`: Option[String] = None
	)
	
	case class Events(
	  /** The user's access role for this calendar. Read-only. Possible values are:  
	- "none" - The user has no access. 
	- "freeBusyReader" - The user has read access to free/busy information. 
	- "reader" - The user has read access to the calendar. Private events will appear to users with reader access, but event details will be hidden. 
	- "writer" - The user has read and write access to the calendar. Private events will appear to users with writer access, and event details will be visible. 
	- "owner" - The user has ownership of the calendar. This role has all of the permissions of the writer role with the additional ability to see and manipulate ACLs. */
		accessRole: Option[String] = None,
	  /** The default reminders on the calendar for the authenticated user. These reminders apply to all events on this calendar that do not explicitly override them (i.e. do not have reminders.useDefault set to True). */
		defaultReminders: Option[List[Schema.EventReminder]] = None,
	  /** Description of the calendar. Read-only. */
		description: Option[String] = None,
	  /** ETag of the collection. */
		etag: Option[String] = None,
	  /** List of events on the calendar. */
		items: Option[List[Schema.Event]] = None,
	  /** Type of the collection ("calendar#events"). */
		kind: Option[String] = Some("""calendar#events"""),
	  /** Token used to access the next page of this result. Omitted if no further results are available, in which case nextSyncToken is provided. */
		nextPageToken: Option[String] = None,
	  /** Token used at a later point in time to retrieve only the entries that have changed since this result was returned. Omitted if further results are available, in which case nextPageToken is provided. */
		nextSyncToken: Option[String] = None,
	  /** Title of the calendar. Read-only. */
		summary: Option[String] = None,
	  /** The time zone of the calendar. Read-only. */
		timeZone: Option[String] = None,
	  /** Last modification time of the calendar (as a RFC3339 timestamp). Read-only. */
		updated: Option[String] = None
	)
	
	case class FreeBusyCalendar(
	  /** List of time ranges during which this calendar should be regarded as busy. */
		busy: Option[List[Schema.TimePeriod]] = None,
	  /** Optional error(s) (if computation for the calendar failed). */
		errors: Option[List[Schema.Error]] = None
	)
	
	case class FreeBusyGroup(
	  /** List of calendars' identifiers within a group. */
		calendars: Option[List[String]] = None,
	  /** Optional error(s) (if computation for the group failed). */
		errors: Option[List[Schema.Error]] = None
	)
	
	case class FreeBusyRequest(
	  /** Maximal number of calendars for which FreeBusy information is to be provided. Optional. Maximum value is 50. */
		calendarExpansionMax: Option[Int] = None,
	  /** Maximal number of calendar identifiers to be provided for a single group. Optional. An error is returned for a group with more members than this value. Maximum value is 100. */
		groupExpansionMax: Option[Int] = None,
	  /** List of calendars and/or groups to query. */
		items: Option[List[Schema.FreeBusyRequestItem]] = None,
	  /** The end of the interval for the query formatted as per RFC3339. */
		timeMax: Option[String] = None,
	  /** The start of the interval for the query formatted as per RFC3339. */
		timeMin: Option[String] = None,
	  /** Time zone used in the response. Optional. The default is UTC. */
		timeZone: Option[String] = Some("""UTC""")
	)
	
	case class FreeBusyRequestItem(
	  /** The identifier of a calendar or a group. */
		id: Option[String] = None
	)
	
	case class FreeBusyResponse(
	  /** List of free/busy information for calendars. */
		calendars: Option[Map[String, Schema.FreeBusyCalendar]] = None,
	  /** Expansion of groups. */
		groups: Option[Map[String, Schema.FreeBusyGroup]] = None,
	  /** Type of the resource ("calendar#freeBusy"). */
		kind: Option[String] = Some("""calendar#freeBusy"""),
	  /** The end of the interval. */
		timeMax: Option[String] = None,
	  /** The start of the interval. */
		timeMin: Option[String] = None
	)
	
	case class Setting(
	  /** ETag of the resource. */
		etag: Option[String] = None,
	  /** The id of the user setting. */
		id: Option[String] = None,
	  /** Type of the resource ("calendar#setting"). */
		kind: Option[String] = Some("""calendar#setting"""),
	  /** Value of the user setting. The format of the value depends on the ID of the setting. It must always be a UTF-8 string of length up to 1024 characters. */
		value: Option[String] = None
	)
	
	case class Settings(
	  /** Etag of the collection. */
		etag: Option[String] = None,
	  /** List of user settings. */
		items: Option[List[Schema.Setting]] = None,
	  /** Type of the collection ("calendar#settings"). */
		kind: Option[String] = Some("""calendar#settings"""),
	  /** Token used to access the next page of this result. Omitted if no further results are available, in which case nextSyncToken is provided. */
		nextPageToken: Option[String] = None,
	  /** Token used at a later point in time to retrieve only the entries that have changed since this result was returned. Omitted if further results are available, in which case nextPageToken is provided. */
		nextSyncToken: Option[String] = None
	)
	
	case class TimePeriod(
	  /** The (exclusive) end of the time period. */
		end: Option[String] = None,
	  /** The (inclusive) start of the time period. */
		start: Option[String] = None
	)
}
