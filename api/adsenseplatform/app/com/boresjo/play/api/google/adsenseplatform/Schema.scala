package com.boresjo.play.api.google.adsenseplatform

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object Account {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, UNCHECKED, APPROVED, DISAPPROVED }
	}
	case class Account(
	  /** Output only. Resource name of the account. Format: platforms/pub-[0-9]+/accounts/pub-[0-9]+ */
		name: Option[String] = None,
	  /** Display name of this account. */
		displayName: Option[String] = None,
	  /** Output only. Approval state of the account. */
		state: Option[Schema.Account.StateEnum] = None,
	  /** Required. The IANA TZ timezone code of this account. For more information, see https://en.wikipedia.org/wiki/List_of_tz_database_time_zones. This field is used for reporting. It is recommended to set it to the same value for all child accounts. */
		timeZone: Option[Schema.TimeZone] = None,
	  /** Output only. Creation time of the account. */
		createTime: Option[String] = None,
	  /** Required. Input only. CLDR region code of the country/region of the address. Set this to country code of the child account if known, otherwise to your own country code. */
		regionCode: Option[String] = None,
	  /** Required. An opaque token that uniquely identifies the account among all the platform's accounts. This string may contain at most 64 non-whitespace ASCII characters, but otherwise has no predefined structure. However, it is expected to be a platform-specific identifier for the user creating the account, so that only a single account can be created for any given user. This field must not contain any information that is recognizable as personally identifiable information. e.g. it should not be an email address or login name. Once an account has been created, a second attempt to create an account using the same creation_request_id will result in an ALREADY_EXISTS error. */
		creationRequestId: Option[String] = None
	)
	
	case class TimeZone(
	  /** IANA Time Zone Database time zone, e.g. "America/New_York". */
		id: Option[String] = None,
	  /** Optional. IANA Time Zone Database version number, e.g. "2019a". */
		version: Option[String] = None
	)
	
	case class LookupAccountResponse(
	  /** The name of the Account Format: platforms/{platform}/accounts/{account_id} */
		name: Option[String] = None
	)
	
	case class ListAccountsResponse(
	  /** The Accounts returned in the list response. Represented by a partial view of the Account resource, populating `name` and `creation_request_id`. */
		accounts: Option[List[Schema.Account]] = None,
	  /** Continuation token used to page through accounts. To retrieve the next page of the results, set the next request's "page_token" value to this. */
		nextPageToken: Option[String] = None
	)
	
	case class CloseAccountRequest(
	
	)
	
	case class CloseAccountResponse(
	
	)
	
	object Event {
		enum EventTypeEnum extends Enum[EventTypeEnum] { case EVENT_TYPE_UNSPECIFIED, LOG_IN_VIA_PLATFORM, SIGN_UP_VIA_PLATFORM }
	}
	case class Event(
	  /** Required. Event type. */
		eventType: Option[Schema.Event.EventTypeEnum] = None,
	  /** Required. Information associated with the event. */
		eventInfo: Option[Schema.EventInfo] = None,
	  /** Required. Event timestamp. */
		eventTime: Option[String] = None
	)
	
	case class EventInfo(
	  /** Required. The email address that is associated with the publisher when performing the event. */
		email: Option[String] = None,
	  /** The billing address of the publisher associated with this event, if available. */
		billingAddress: Option[Schema.Address] = None
	)
	
	case class Address(
	  /** First line of address. Max length 64 bytes or 30 characters. */
		address1: Option[String] = None,
	  /** Second line of address. Max length 64 bytes or 30 characters. */
		address2: Option[String] = None,
	  /** City. Max length 60 bytes or 30 characters. */
		city: Option[String] = None,
	  /** State. Max length 60 bytes or 30 characters. */
		state: Option[String] = None,
	  /** Zip/post code. Max length 10 bytes or 10 characters. */
		zip: Option[String] = None,
	  /** Name of the company. Max length 255 bytes or 34 characters. */
		company: Option[String] = None,
	  /** Contact name of the company. Max length 128 bytes or 34 characters. */
		contact: Option[String] = None,
	  /** Phone number with international code (i.e. +441234567890). */
		phone: Option[String] = None,
	  /** Fax number with international code (i.e. +441234567890). */
		fax: Option[String] = None,
	  /** Country/Region code. The region is specified as a CLDR region code (e.g. "US", "FR"). */
		regionCode: Option[String] = None
	)
	
	object Site {
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, REQUIRES_REVIEW, GETTING_READY, READY, NEEDS_ATTENTION }
	}
	case class Site(
	  /** Output only. Resource name of a site. Format: platforms/{platform}/accounts/{account}/sites/{site} */
		name: Option[String] = None,
	  /** Domain/sub-domain of the site. Must be a valid domain complying with [RFC 1035](https://www.ietf.org/rfc/rfc1035.txt) and formatted as punycode [RFC 3492](https://www.ietf.org/rfc/rfc3492.txt) in case the domain contains unicode characters. */
		domain: Option[String] = None,
	  /** Output only. State of a site. */
		state: Option[Schema.Site.StateEnum] = None
	)
	
	case class ListSitesResponse(
	  /** The sites returned in this list response. */
		sites: Option[List[Schema.Site]] = None,
	  /** Continuation token used to page through sites. To retrieve the next page of the results, set the next request's "page_token" value to this. */
		nextPageToken: Option[String] = None
	)
	
	case class RequestSiteReviewResponse(
	
	)
	
	case class Empty(
	
	)
}
