package com.boresjo.play.api.google.admin

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class Activities(
	  /** The type of API resource. For an activity report, the value is `reports#activities`. */
		kind: Option[String] = Some("""admin#reports#activities"""),
	  /** ETag of the resource. */
		etag: Option[String] = None,
	  /** Each activity record in the response. */
		items: Option[List[Schema.Activity]] = None,
	  /** Token for retrieving the follow-on next page of the report. The `nextPageToken` value is used in the request's `pageToken` query string. */
		nextPageToken: Option[String] = None
	)
	
	object Activity {
		object EventsItem {
			object ParametersItem {
				case class MessageValueItem(
				  /** Parameter values */
					parameter: Option[List[Schema.NestedParameter]] = None
				)
				
				case class MultiMessageValueItem(
				  /** Parameter values */
					parameter: Option[List[Schema.NestedParameter]] = None
				)
			}
			case class ParametersItem(
			  /** Nested parameter value pairs associated with this parameter. Complex value type for a parameter are returned as a list of parameter values. For example, the address parameter may have a value as `[{parameter: [{name: city, value: abc}]}]` */
				messageValue: Option[Schema.Activity.EventsItem.ParametersItem.MessageValueItem] = None,
			  /** The name of the parameter. */
				name: Option[String] = None,
			  /** String value of the parameter. */
				value: Option[String] = None,
			  /** String values of the parameter. */
				multiValue: Option[List[String]] = None,
			  /** Integer value of the parameter. */
				intValue: Option[String] = None,
			  /** Integer values of the parameter. */
				multiIntValue: Option[List[String]] = None,
			  /** Boolean value of the parameter. */
				boolValue: Option[Boolean] = None,
			  /** List of `messageValue` objects. */
				multiMessageValue: Option[List[Schema.Activity.EventsItem.ParametersItem.MultiMessageValueItem]] = None
			)
		}
		case class EventsItem(
		  /** Type of event. The Google Workspace service or feature that an administrator changes is identified in the `type` property which identifies an event using the `eventName` property. For a full list of the API's `type` categories, see the list of event names for various applications above in `applicationName`. */
			`type`: Option[String] = None,
		  /** Name of the event. This is the specific name of the activity reported by the API. And each `eventName` is related to a specific Google Workspace service or feature which the API organizes into types of events. For `eventName` request parameters in general: - If no `eventName` is given, the report returns all possible instances of an `eventName`. - When you request an `eventName`, the API's response returns all activities which contain that `eventName`. For more information about `eventName` properties, see the list of event names for various applications above in `applicationName`. */
			name: Option[String] = None,
		  /** Parameter value pairs for various applications. For more information about `eventName` parameters, see the list of event names for various applications above in `applicationName`. */
			parameters: Option[List[Schema.Activity.EventsItem.ParametersItem]] = None
		)
		
		case class IdItem(
		  /** Time of occurrence of the activity. This is in UNIX epoch time in seconds. */
			time: Option[String] = None,
		  /** Unique qualifier if multiple events have the same time. */
			uniqueQualifier: Option[String] = None,
		  /** Application name to which the event belongs. For possible values see the list of applications above in `applicationName`. */
			applicationName: Option[String] = None,
		  /** The unique identifier for a Google Workspace account. */
			customerId: Option[String] = None
		)
		
		case class ActorItem(
		  /** The unique Google Workspace profile ID of the actor. This value might be absent if the actor is not a Google Workspace user, or may be the number 105250506097979753968 which acts as a placeholder ID. */
			profileId: Option[String] = None,
		  /** The primary email address of the actor. May be absent if there is no email address associated with the actor. */
			email: Option[String] = None,
		  /** The type of actor. */
			callerType: Option[String] = None,
		  /** Only present when `callerType` is `KEY`. Can be the `consumer_key` of the requestor for OAuth 2LO API requests or an identifier for robot accounts. */
			key: Option[String] = None
		)
	}
	case class Activity(
	  /** The type of API resource. For an activity report, the value is `audit#activity`. */
		kind: Option[String] = Some("""admin#reports#activity"""),
	  /** ETag of the entry. */
		etag: Option[String] = None,
	  /** This is the domain that is affected by the report's event. For example domain of Admin console or the Drive application's document owner. */
		ownerDomain: Option[String] = None,
	  /** IP address of the user doing the action. This is the Internet Protocol (IP) address of the user when logging into Google Workspace, which may or may not reflect the user's physical location. For example, the IP address can be the user's proxy server's address or a virtual private network (VPN) address. The API supports IPv4 and IPv6. */
		ipAddress: Option[String] = None,
	  /** Activity events in the report. */
		events: Option[List[Schema.Activity.EventsItem]] = None,
	  /** Unique identifier for each activity record. */
		id: Option[Schema.Activity.IdItem] = None,
	  /** User doing the action. */
		actor: Option[Schema.Activity.ActorItem] = None
	)
	
	case class NestedParameter(
	  /** The name of the parameter. */
		name: Option[String] = None,
	  /** String value of the parameter. */
		value: Option[String] = None,
	  /** Multiple string values of the parameter. */
		multiValue: Option[List[String]] = None,
	  /** Integer value of the parameter. */
		intValue: Option[String] = None,
	  /** Multiple integer values of the parameter. */
		multiIntValue: Option[List[String]] = None,
	  /** Boolean value of the parameter. */
		boolValue: Option[Boolean] = None,
	  /** Multiple boolean values of the parameter. */
		multiBoolValue: Option[List[Boolean]] = None
	)
	
	case class Channel(
	  /** A UUID or similar unique string that identifies this channel. */
		id: Option[String] = None,
	  /** An arbitrary string delivered to the target address with each notification delivered over this channel. Optional. */
		token: Option[String] = None,
	  /** Date and time of notification channel expiration, expressed as a Unix timestamp, in milliseconds. Optional. */
		expiration: Option[String] = None,
	  /** The type of delivery mechanism used for this channel. The value should be set to `"web_hook"`. */
		`type`: Option[String] = None,
	  /** The address where notifications are delivered for this channel. */
		address: Option[String] = None,
	  /** A Boolean value to indicate whether payload is wanted. A payload is data that is sent in the body of an HTTP POST, PUT, or PATCH message and contains important information about the request. Optional. */
		payload: Option[Boolean] = None,
	  /** Additional parameters controlling delivery channel behavior. Optional. */
		params: Option[Map[String, String]] = None,
	  /** An opaque ID that identifies the resource being watched on this channel. Stable across different API versions. */
		resourceId: Option[String] = None,
	  /** A version-specific identifier for the watched resource. */
		resourceUri: Option[String] = None,
	  /** Identifies this as a notification channel used to watch for changes to a resource, which is "`api#channel`". */
		kind: Option[String] = Some("""api#channel""")
	)
	
	object UsageReports {
		object WarningsItem {
			case class DataItem(
			  /** Key associated with a key-value pair to give detailed information on the warning. */
				key: Option[String] = None,
			  /** Value associated with a key-value pair to give detailed information on the warning. */
				value: Option[String] = None
			)
		}
		case class WarningsItem(
		  /** Machine readable code or warning type. The warning code value is `200`. */
			code: Option[String] = None,
		  /** The human readable messages for a warning are: - Data is not available warning - Sorry, data for date yyyy-mm-dd for application "`application name`" is not available. - Partial data is available warning - Data for date yyyy-mm-dd for application "`application name`" is not available right now, please try again after a few hours.  */
			message: Option[String] = None,
		  /** Key-value pairs to give detailed information on the warning. */
			data: Option[List[Schema.UsageReports.WarningsItem.DataItem]] = None
		)
	}
	case class UsageReports(
	  /** The type of API resource. For a usage report, the value is `admin#reports#usageReports`. */
		kind: Option[String] = Some("""admin#reports#usageReports"""),
	  /** ETag of the resource. */
		etag: Option[String] = None,
	  /** Warnings, if any. */
		warnings: Option[List[Schema.UsageReports.WarningsItem]] = None,
	  /** Various application parameter records. */
		usageReports: Option[List[Schema.UsageReport]] = None,
	  /** Token to specify next page. A report with multiple pages has a `nextPageToken` property in the response. For your follow-on requests getting all of the report's pages, enter the `nextPageToken` value in the `pageToken` query string. */
		nextPageToken: Option[String] = None
	)
	
	object UsageReport {
		case class ParametersItem(
		  /** Output only. Integer value of the parameter. */
			intValue: Option[String] = None,
		  /** The name of the parameter. For the User Usage Report parameter names, see the User Usage parameters reference. */
			name: Option[String] = None,
		  /** Output only. String value of the parameter. */
			stringValue: Option[String] = None,
		  /** The RFC 3339 formatted value of the parameter, for example 2010-10-28T10:26:35.000Z. */
			datetimeValue: Option[String] = None,
		  /** Output only. Boolean value of the parameter. */
			boolValue: Option[Boolean] = None,
		  /** Output only. Nested message value of the parameter. */
			msgValue: Option[List[Map[String, JsValue]]] = None
		)
		
		case class EntityItem(
		  /** Output only. The unique identifier of the customer's account. */
			customerId: Option[String] = None,
		  /** Output only. The user's email address. Only relevant if entity.type = "USER" */
			userEmail: Option[String] = None,
		  /** Output only. The user's immutable Google Workspace profile identifier. */
			profileId: Option[String] = None,
		  /** Output only. Object key. Only relevant if entity.type = "OBJECT" Note: external-facing name of report is "Entities" rather than "Objects". */
			entityId: Option[String] = None,
		  /** Output only. The type of item. The value is `user`. */
			`type`: Option[String] = None
		)
	}
	case class UsageReport(
	  /** The type of API resource. For a usage report, the value is `admin#reports#usageReport`. */
		kind: Option[String] = Some("""admin#reports#usageReport"""),
	  /** Output only. The date of the report request. */
		date: Option[String] = None,
	  /** Output only. Parameter value pairs for various applications. For the Entity Usage Report parameters and values, see [the Entity Usage parameters reference](/admin-sdk/reports/v1/reference/usage-ref-appendix-a/entities). */
		parameters: Option[List[Schema.UsageReport.ParametersItem]] = None,
	  /** ETag of the resource. */
		etag: Option[String] = None,
	  /** Output only. Information about the type of the item. */
		entity: Option[Schema.UsageReport.EntityItem] = None
	)
}
