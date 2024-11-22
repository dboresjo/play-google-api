package com.boresjo.play.api.google.people

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	object Person {
		enum AgeRangeEnum extends Enum[AgeRangeEnum] { case AGE_RANGE_UNSPECIFIED, LESS_THAN_EIGHTEEN, EIGHTEEN_TO_TWENTY, TWENTY_ONE_OR_OLDER }
	}
	case class Person(
	  /** The resource name for the person, assigned by the server. An ASCII string in the form of `people/{person_id}`. */
		resourceName: Option[String] = None,
	  /** The [HTTP entity tag](https://en.wikipedia.org/wiki/HTTP_ETag) of the resource. Used for web cache validation. */
		etag: Option[String] = None,
	  /** Output only. Metadata about the person. */
		metadata: Option[Schema.PersonMetadata] = None,
	  /** The person's street addresses. */
		addresses: Option[List[Schema.Address]] = None,
	  /** Output only. &#42;&#42;DEPRECATED&#42;&#42; (Please use `person.ageRanges` instead) The person's age range. */
		ageRange: Option[Schema.Person.AgeRangeEnum] = None,
	  /** Output only. The person's age ranges. */
		ageRanges: Option[List[Schema.AgeRangeType]] = None,
	  /** The person's biographies. This field is a singleton for contact sources. */
		biographies: Option[List[Schema.Biography]] = None,
	  /** The person's birthdays. This field is a singleton for contact sources. */
		birthdays: Option[List[Schema.Birthday]] = None,
	  /** &#42;&#42;DEPRECATED&#42;&#42;: No data will be returned The person's bragging rights. */
		braggingRights: Option[List[Schema.BraggingRights]] = None,
	  /** The person's calendar URLs. */
		calendarUrls: Option[List[Schema.CalendarUrl]] = None,
	  /** The person's client data. */
		clientData: Option[List[Schema.ClientData]] = None,
	  /** Output only. The person's cover photos. */
		coverPhotos: Option[List[Schema.CoverPhoto]] = None,
	  /** The person's email addresses. For `people.connections.list` and `otherContacts.list` the number of email addresses is limited to 100. If a Person has more email addresses the entire set can be obtained by calling GetPeople. */
		emailAddresses: Option[List[Schema.EmailAddress]] = None,
	  /** The person's events. */
		events: Option[List[Schema.Event]] = None,
	  /** The person's external IDs. */
		externalIds: Option[List[Schema.ExternalId]] = None,
	  /** The person's file-ases. */
		fileAses: Option[List[Schema.FileAs]] = None,
	  /** The person's genders. This field is a singleton for contact sources. */
		genders: Option[List[Schema.Gender]] = None,
	  /** The person's instant messaging clients. */
		imClients: Option[List[Schema.ImClient]] = None,
	  /** The person's interests. */
		interests: Option[List[Schema.Interest]] = None,
	  /** The person's locale preferences. */
		locales: Option[List[Schema.Locale]] = None,
	  /** The person's locations. */
		locations: Option[List[Schema.Location]] = None,
	  /** The person's group memberships. */
		memberships: Option[List[Schema.Membership]] = None,
	  /** The person's miscellaneous keywords. */
		miscKeywords: Option[List[Schema.MiscKeyword]] = None,
	  /** The person's names. This field is a singleton for contact sources. */
		names: Option[List[Schema.Name]] = None,
	  /** The person's nicknames. */
		nicknames: Option[List[Schema.Nickname]] = None,
	  /** The person's occupations. */
		occupations: Option[List[Schema.Occupation]] = None,
	  /** The person's past or current organizations. */
		organizations: Option[List[Schema.Organization]] = None,
	  /** The person's phone numbers. For `people.connections.list` and `otherContacts.list` the number of phone numbers is limited to 100. If a Person has more phone numbers the entire set can be obtained by calling GetPeople. */
		phoneNumbers: Option[List[Schema.PhoneNumber]] = None,
	  /** Output only. The person's photos. */
		photos: Option[List[Schema.Photo]] = None,
	  /** The person's relations. */
		relations: Option[List[Schema.Relation]] = None,
	  /** Output only. &#42;&#42;DEPRECATED&#42;&#42;: No data will be returned The person's relationship interests. */
		relationshipInterests: Option[List[Schema.RelationshipInterest]] = None,
	  /** Output only. &#42;&#42;DEPRECATED&#42;&#42;: No data will be returned The person's relationship statuses. */
		relationshipStatuses: Option[List[Schema.RelationshipStatus]] = None,
	  /** &#42;&#42;DEPRECATED&#42;&#42;: (Please use `person.locations` instead) The person's residences. */
		residences: Option[List[Schema.Residence]] = None,
	  /** The person's SIP addresses. */
		sipAddresses: Option[List[Schema.SipAddress]] = None,
	  /** The person's skills. */
		skills: Option[List[Schema.Skill]] = None,
	  /** Output only. &#42;&#42;DEPRECATED&#42;&#42;: No data will be returned The person's taglines. */
		taglines: Option[List[Schema.Tagline]] = None,
	  /** The person's associated URLs. */
		urls: Option[List[Schema.Url]] = None,
	  /** The person's user defined data. */
		userDefined: Option[List[Schema.UserDefined]] = None
	)
	
	object PersonMetadata {
		enum ObjectTypeEnum extends Enum[ObjectTypeEnum] { case OBJECT_TYPE_UNSPECIFIED, PERSON, PAGE }
	}
	case class PersonMetadata(
	  /** The sources of data for the person. */
		sources: Option[List[Schema.Source]] = None,
	  /** Output only. Any former resource names this person has had. Populated only for `people.connections.list` requests that include a sync token. The resource name may change when adding or removing fields that link a contact and profile such as a verified email, verified phone number, or profile URL. */
		previousResourceNames: Option[List[String]] = None,
	  /** Output only. Resource names of people linked to this resource. */
		linkedPeopleResourceNames: Option[List[String]] = None,
	  /** Output only. True if the person resource has been deleted. Populated only for `people.connections.list` and `otherContacts.list` sync requests. */
		deleted: Option[Boolean] = None,
	  /** Output only. &#42;&#42;DEPRECATED&#42;&#42; (Please use `person.metadata.sources.profileMetadata.objectType` instead) The type of the person object. */
		objectType: Option[Schema.PersonMetadata.ObjectTypeEnum] = None
	)
	
	object Source {
		enum TypeEnum extends Enum[TypeEnum] { case SOURCE_TYPE_UNSPECIFIED, ACCOUNT, PROFILE, DOMAIN_PROFILE, CONTACT, OTHER_CONTACT, DOMAIN_CONTACT }
	}
	case class Source(
	  /** The source type. */
		`type`: Option[Schema.Source.TypeEnum] = None,
	  /** The unique identifier within the source type generated by the server. */
		id: Option[String] = None,
	  /** &#42;&#42;Only populated in `person.metadata.sources`.&#42;&#42; The [HTTP entity tag](https://en.wikipedia.org/wiki/HTTP_ETag) of the source. Used for web cache validation. */
		etag: Option[String] = None,
	  /** Output only. &#42;&#42;Only populated in `person.metadata.sources`.&#42;&#42; Metadata about a source of type PROFILE. */
		profileMetadata: Option[Schema.ProfileMetadata] = None,
	  /** Output only. &#42;&#42;Only populated in `person.metadata.sources`.&#42;&#42; Last update timestamp of this source. */
		updateTime: Option[String] = None
	)
	
	object ProfileMetadata {
		enum ObjectTypeEnum extends Enum[ObjectTypeEnum] { case OBJECT_TYPE_UNSPECIFIED, PERSON, PAGE }
		enum UserTypesEnum extends Enum[UserTypesEnum] { case USER_TYPE_UNKNOWN, GOOGLE_USER, GPLUS_USER, GOOGLE_APPS_USER }
	}
	case class ProfileMetadata(
	  /** Output only. The profile object type. */
		objectType: Option[Schema.ProfileMetadata.ObjectTypeEnum] = None,
	  /** Output only. The user types. */
		userTypes: Option[List[Schema.ProfileMetadata.UserTypesEnum]] = None
	)
	
	case class Address(
	  /** Metadata about the address. */
		metadata: Option[Schema.FieldMetadata] = None,
	  /** The unstructured value of the address. If this is not set by the user it will be automatically constructed from structured values. */
		formattedValue: Option[String] = None,
	  /** The type of the address. The type can be custom or one of these predefined values: &#42; `home` &#42; `work` &#42; `other` */
		`type`: Option[String] = None,
	  /** Output only. The type of the address translated and formatted in the viewer's account locale or the `Accept-Language` HTTP header locale. */
		formattedType: Option[String] = None,
	  /** The P.O. box of the address. */
		poBox: Option[String] = None,
	  /** The street address. */
		streetAddress: Option[String] = None,
	  /** The extended address of the address; for example, the apartment number. */
		extendedAddress: Option[String] = None,
	  /** The city of the address. */
		city: Option[String] = None,
	  /** The region of the address; for example, the state or province. */
		region: Option[String] = None,
	  /** The postal code of the address. */
		postalCode: Option[String] = None,
	  /** The country of the address. */
		country: Option[String] = None,
	  /** The [ISO 3166-1 alpha-2](http://www.iso.org/iso/country_codes.htm) country code of the address. */
		countryCode: Option[String] = None
	)
	
	case class FieldMetadata(
	  /** Output only. True if the field is the primary field for all sources in the person. Each person will have at most one field with `primary` set to true. */
		primary: Option[Boolean] = None,
	  /** True if the field is the primary field for the source. Each source must have at most one field with `source_primary` set to true. */
		sourcePrimary: Option[Boolean] = None,
	  /** Output only. True if the field is verified; false if the field is unverified. A verified field is typically a name, email address, phone number, or website that has been confirmed to be owned by the person. */
		verified: Option[Boolean] = None,
	  /** The source of the field. */
		source: Option[Schema.Source] = None
	)
	
	object AgeRangeType {
		enum AgeRangeEnum extends Enum[AgeRangeEnum] { case AGE_RANGE_UNSPECIFIED, LESS_THAN_EIGHTEEN, EIGHTEEN_TO_TWENTY, TWENTY_ONE_OR_OLDER }
	}
	case class AgeRangeType(
	  /** Metadata about the age range. */
		metadata: Option[Schema.FieldMetadata] = None,
	  /** The age range. */
		ageRange: Option[Schema.AgeRangeType.AgeRangeEnum] = None
	)
	
	object Biography {
		enum ContentTypeEnum extends Enum[ContentTypeEnum] { case CONTENT_TYPE_UNSPECIFIED, TEXT_PLAIN, TEXT_HTML }
	}
	case class Biography(
	  /** Metadata about the biography. */
		metadata: Option[Schema.FieldMetadata] = None,
	  /** The short biography. */
		value: Option[String] = None,
	  /** The content type of the biography. */
		contentType: Option[Schema.Biography.ContentTypeEnum] = None
	)
	
	case class Birthday(
	  /** Metadata about the birthday. */
		metadata: Option[Schema.FieldMetadata] = None,
	  /** The structured date of the birthday. */
		date: Option[Schema.Date] = None,
	  /** Prefer to use the `date` field if set. A free-form string representing the user's birthday. This value is not validated. */
		text: Option[String] = None
	)
	
	case class Date(
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None,
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None,
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None
	)
	
	case class BraggingRights(
	  /** Metadata about the bragging rights. */
		metadata: Option[Schema.FieldMetadata] = None,
	  /** The bragging rights; for example, `climbed mount everest`. */
		value: Option[String] = None
	)
	
	case class CalendarUrl(
	  /** Metadata about the calendar URL. */
		metadata: Option[Schema.FieldMetadata] = None,
	  /** The calendar URL. */
		url: Option[String] = None,
	  /** The type of the calendar URL. The type can be custom or one of these predefined values: &#42; `home` &#42; `freeBusy` &#42; `work` */
		`type`: Option[String] = None,
	  /** Output only. The type of the calendar URL translated and formatted in the viewer's account locale or the `Accept-Language` HTTP header locale. */
		formattedType: Option[String] = None
	)
	
	case class ClientData(
	  /** Metadata about the client data. */
		metadata: Option[Schema.FieldMetadata] = None,
	  /** The client specified key of the client data. */
		key: Option[String] = None,
	  /** The client specified value of the client data. */
		value: Option[String] = None
	)
	
	case class CoverPhoto(
	  /** Metadata about the cover photo. */
		metadata: Option[Schema.FieldMetadata] = None,
	  /** The URL of the cover photo. */
		url: Option[String] = None,
	  /** True if the cover photo is the default cover photo; false if the cover photo is a user-provided cover photo. */
		default: Option[Boolean] = None
	)
	
	case class EmailAddress(
	  /** Metadata about the email address. */
		metadata: Option[Schema.FieldMetadata] = None,
	  /** The email address. */
		value: Option[String] = None,
	  /** The type of the email address. The type can be custom or one of these predefined values: &#42; `home` &#42; `work` &#42; `other` */
		`type`: Option[String] = None,
	  /** Output only. The type of the email address translated and formatted in the viewer's account locale or the `Accept-Language` HTTP header locale. */
		formattedType: Option[String] = None,
	  /** The display name of the email. */
		displayName: Option[String] = None
	)
	
	case class Event(
	  /** Metadata about the event. */
		metadata: Option[Schema.FieldMetadata] = None,
	  /** The date of the event. */
		date: Option[Schema.Date] = None,
	  /** The type of the event. The type can be custom or one of these predefined values: &#42; `anniversary` &#42; `other` */
		`type`: Option[String] = None,
	  /** Output only. The type of the event translated and formatted in the viewer's account locale or the `Accept-Language` HTTP header locale. */
		formattedType: Option[String] = None
	)
	
	case class ExternalId(
	  /** Metadata about the external ID. */
		metadata: Option[Schema.FieldMetadata] = None,
	  /** The value of the external ID. */
		value: Option[String] = None,
	  /** The type of the external ID. The type can be custom or one of these predefined values: &#42; `account` &#42; `customer` &#42; `loginId` &#42; `network` &#42; `organization` */
		`type`: Option[String] = None,
	  /** Output only. The type of the event translated and formatted in the viewer's account locale or the `Accept-Language` HTTP header locale. */
		formattedType: Option[String] = None
	)
	
	case class FileAs(
	  /** Metadata about the file-as. */
		metadata: Option[Schema.FieldMetadata] = None,
	  /** The file-as value */
		value: Option[String] = None
	)
	
	case class Gender(
	  /** Metadata about the gender. */
		metadata: Option[Schema.FieldMetadata] = None,
	  /** The gender for the person. The gender can be custom or one of these predefined values: &#42; `male` &#42; `female` &#42; `unspecified` */
		value: Option[String] = None,
	  /** Output only. The value of the gender translated and formatted in the viewer's account locale or the `Accept-Language` HTTP header locale. Unspecified or custom value are not localized. */
		formattedValue: Option[String] = None,
	  /** Free form text field for pronouns that should be used to address the person. Common values are: &#42; `he`/`him` &#42; `she`/`her` &#42; `they`/`them` */
		addressMeAs: Option[String] = None
	)
	
	case class ImClient(
	  /** Metadata about the IM client. */
		metadata: Option[Schema.FieldMetadata] = None,
	  /** The user name used in the IM client. */
		username: Option[String] = None,
	  /** The type of the IM client. The type can be custom or one of these predefined values: &#42; `home` &#42; `work` &#42; `other` */
		`type`: Option[String] = None,
	  /** Output only. The type of the IM client translated and formatted in the viewer's account locale or the `Accept-Language` HTTP header locale. */
		formattedType: Option[String] = None,
	  /** The protocol of the IM client. The protocol can be custom or one of these predefined values: &#42; `aim` &#42; `msn` &#42; `yahoo` &#42; `skype` &#42; `qq` &#42; `googleTalk` &#42; `icq` &#42; `jabber` &#42; `netMeeting` */
		protocol: Option[String] = None,
	  /** Output only. The protocol of the IM client formatted in the viewer's account locale or the `Accept-Language` HTTP header locale. */
		formattedProtocol: Option[String] = None
	)
	
	case class Interest(
	  /** Metadata about the interest. */
		metadata: Option[Schema.FieldMetadata] = None,
	  /** The interest; for example, `stargazing`. */
		value: Option[String] = None
	)
	
	case class Locale(
	  /** Metadata about the locale. */
		metadata: Option[Schema.FieldMetadata] = None,
	  /** The well-formed [IETF BCP 47](https://tools.ietf.org/html/bcp47) language tag representing the locale. */
		value: Option[String] = None
	)
	
	case class Location(
	  /** Metadata about the location. */
		metadata: Option[Schema.FieldMetadata] = None,
	  /** The free-form value of the location. */
		value: Option[String] = None,
	  /** The type of the location. The type can be custom or one of these predefined values: &#42; `desk` &#42; `grewUp` */
		`type`: Option[String] = None,
	  /** Whether the location is the current location. */
		current: Option[Boolean] = None,
	  /** The building identifier. */
		buildingId: Option[String] = None,
	  /** The floor name or number. */
		floor: Option[String] = None,
	  /** The floor section in `floor_name`. */
		floorSection: Option[String] = None,
	  /** The individual desk location. */
		deskCode: Option[String] = None
	)
	
	case class Membership(
	  /** Metadata about the membership. */
		metadata: Option[Schema.FieldMetadata] = None,
	  /** The contact group membership. */
		contactGroupMembership: Option[Schema.ContactGroupMembership] = None,
	  /** Output only. The domain membership. */
		domainMembership: Option[Schema.DomainMembership] = None
	)
	
	case class ContactGroupMembership(
	  /** Output only. The contact group ID for the contact group membership. */
		contactGroupId: Option[String] = None,
	  /** The resource name for the contact group, assigned by the server. An ASCII string, in the form of `contactGroups/{contact_group_id}`. Only contact_group_resource_name can be used for modifying memberships. Any contact group membership can be removed, but only user group or "myContacts" or "starred" system groups memberships can be added. A contact must always have at least one contact group membership. */
		contactGroupResourceName: Option[String] = None
	)
	
	case class DomainMembership(
	  /** True if the person is in the viewer's Google Workspace domain. */
		inViewerDomain: Option[Boolean] = None
	)
	
	object MiscKeyword {
		enum TypeEnum extends Enum[TypeEnum] { case TYPE_UNSPECIFIED, OUTLOOK_BILLING_INFORMATION, OUTLOOK_DIRECTORY_SERVER, OUTLOOK_KEYWORD, OUTLOOK_MILEAGE, OUTLOOK_PRIORITY, OUTLOOK_SENSITIVITY, OUTLOOK_SUBJECT, OUTLOOK_USER, HOME, WORK, OTHER }
	}
	case class MiscKeyword(
	  /** Metadata about the miscellaneous keyword. */
		metadata: Option[Schema.FieldMetadata] = None,
	  /** The value of the miscellaneous keyword. */
		value: Option[String] = None,
	  /** The miscellaneous keyword type. */
		`type`: Option[Schema.MiscKeyword.TypeEnum] = None,
	  /** Output only. The type of the miscellaneous keyword translated and formatted in the viewer's account locale or the `Accept-Language` HTTP header locale. */
		formattedType: Option[String] = None
	)
	
	case class Name(
	  /** Metadata about the name. */
		metadata: Option[Schema.FieldMetadata] = None,
	  /** Output only. The display name formatted according to the locale specified by the viewer's account or the `Accept-Language` HTTP header. */
		displayName: Option[String] = None,
	  /** Output only. The display name with the last name first formatted according to the locale specified by the viewer's account or the `Accept-Language` HTTP header. */
		displayNameLastFirst: Option[String] = None,
	  /** The free form name value. */
		unstructuredName: Option[String] = None,
	  /** The family name. */
		familyName: Option[String] = None,
	  /** The given name. */
		givenName: Option[String] = None,
	  /** The middle name(s). */
		middleName: Option[String] = None,
	  /** The honorific prefixes, such as `Mrs.` or `Dr.` */
		honorificPrefix: Option[String] = None,
	  /** The honorific suffixes, such as `Jr.` */
		honorificSuffix: Option[String] = None,
	  /** The full name spelled as it sounds. */
		phoneticFullName: Option[String] = None,
	  /** The family name spelled as it sounds. */
		phoneticFamilyName: Option[String] = None,
	  /** The given name spelled as it sounds. */
		phoneticGivenName: Option[String] = None,
	  /** The middle name(s) spelled as they sound. */
		phoneticMiddleName: Option[String] = None,
	  /** The honorific prefixes spelled as they sound. */
		phoneticHonorificPrefix: Option[String] = None,
	  /** The honorific suffixes spelled as they sound. */
		phoneticHonorificSuffix: Option[String] = None
	)
	
	object Nickname {
		enum TypeEnum extends Enum[TypeEnum] { case DEFAULT, MAIDEN_NAME, INITIALS, GPLUS, OTHER_NAME, ALTERNATE_NAME, SHORT_NAME }
	}
	case class Nickname(
	  /** Metadata about the nickname. */
		metadata: Option[Schema.FieldMetadata] = None,
	  /** The nickname. */
		value: Option[String] = None,
	  /** The type of the nickname. */
		`type`: Option[Schema.Nickname.TypeEnum] = None
	)
	
	case class Occupation(
	  /** Metadata about the occupation. */
		metadata: Option[Schema.FieldMetadata] = None,
	  /** The occupation; for example, `carpenter`. */
		value: Option[String] = None
	)
	
	case class Organization(
	  /** Metadata about the organization. */
		metadata: Option[Schema.FieldMetadata] = None,
	  /** The type of the organization. The type can be custom or one of these predefined values: &#42; `work` &#42; `school` */
		`type`: Option[String] = None,
	  /** Output only. The type of the organization translated and formatted in the viewer's account locale or the `Accept-Language` HTTP header locale. */
		formattedType: Option[String] = None,
	  /** The start date when the person joined the organization. */
		startDate: Option[Schema.Date] = None,
	  /** The end date when the person left the organization. */
		endDate: Option[Schema.Date] = None,
	  /** True if the organization is the person's current organization; false if the organization is a past organization. */
		current: Option[Boolean] = None,
	  /** The name of the organization. */
		name: Option[String] = None,
	  /** The phonetic name of the organization. */
		phoneticName: Option[String] = None,
	  /** The person's department at the organization. */
		department: Option[String] = None,
	  /** The person's job title at the organization. */
		title: Option[String] = None,
	  /** The person's job description at the organization. */
		jobDescription: Option[String] = None,
	  /** The symbol associated with the organization; for example, a stock ticker symbol, abbreviation, or acronym. */
		symbol: Option[String] = None,
	  /** The domain name associated with the organization; for example, `google.com`. */
		domain: Option[String] = None,
	  /** The location of the organization office the person works at. */
		location: Option[String] = None,
	  /** The person's cost center at the organization. */
		costCenter: Option[String] = None,
	  /** The person's full-time equivalent millipercent within the organization (100000 = 100%). */
		fullTimeEquivalentMillipercent: Option[Int] = None
	)
	
	case class PhoneNumber(
	  /** Metadata about the phone number. */
		metadata: Option[Schema.FieldMetadata] = None,
	  /** The phone number. */
		value: Option[String] = None,
	  /** Output only. The canonicalized [ITU-T E.164](https://law.resource.org/pub/us/cfr/ibr/004/itu-t.E.164.1.2008.pdf) form of the phone number. */
		canonicalForm: Option[String] = None,
	  /** The type of the phone number. The type can be custom or one of these predefined values: &#42; `home` &#42; `work` &#42; `mobile` &#42; `homeFax` &#42; `workFax` &#42; `otherFax` &#42; `pager` &#42; `workMobile` &#42; `workPager` &#42; `main` &#42; `googleVoice` &#42; `other` */
		`type`: Option[String] = None,
	  /** Output only. The type of the phone number translated and formatted in the viewer's account locale or the `Accept-Language` HTTP header locale. */
		formattedType: Option[String] = None
	)
	
	case class Photo(
	  /** Metadata about the photo. */
		metadata: Option[Schema.FieldMetadata] = None,
	  /** The URL of the photo. You can change the desired size by appending a query parameter `sz={size}` at the end of the url, where {size} is the size in pixels. Example: https://lh3.googleusercontent.com/-T_wVWLlmg7w/AAAAAAAAAAI/AAAAAAAABa8/00gzXvDBYqw/s100/photo.jpg?sz=50 */
		url: Option[String] = None,
	  /** True if the photo is a default photo; false if the photo is a user-provided photo. */
		default: Option[Boolean] = None
	)
	
	case class Relation(
	  /** Metadata about the relation. */
		metadata: Option[Schema.FieldMetadata] = None,
	  /** The name of the other person this relation refers to. */
		person: Option[String] = None,
	  /** The person's relation to the other person. The type can be custom or one of these predefined values: &#42; `spouse` &#42; `child` &#42; `mother` &#42; `father` &#42; `parent` &#42; `brother` &#42; `sister` &#42; `friend` &#42; `relative` &#42; `domesticPartner` &#42; `manager` &#42; `assistant` &#42; `referredBy` &#42; `partner` */
		`type`: Option[String] = None,
	  /** Output only. The type of the relation translated and formatted in the viewer's account locale or the locale specified in the Accept-Language HTTP header. */
		formattedType: Option[String] = None
	)
	
	case class RelationshipInterest(
	  /** Metadata about the relationship interest. */
		metadata: Option[Schema.FieldMetadata] = None,
	  /** The kind of relationship the person is looking for. The value can be custom or one of these predefined values: &#42; `friend` &#42; `date` &#42; `relationship` &#42; `networking` */
		value: Option[String] = None,
	  /** Output only. The value of the relationship interest translated and formatted in the viewer's account locale or the locale specified in the Accept-Language HTTP header. */
		formattedValue: Option[String] = None
	)
	
	case class RelationshipStatus(
	  /** Metadata about the relationship status. */
		metadata: Option[Schema.FieldMetadata] = None,
	  /** The relationship status. The value can be custom or one of these predefined values: &#42; `single` &#42; `inARelationship` &#42; `engaged` &#42; `married` &#42; `itsComplicated` &#42; `openRelationship` &#42; `widowed` &#42; `inDomesticPartnership` &#42; `inCivilUnion` */
		value: Option[String] = None,
	  /** Output only. The value of the relationship status translated and formatted in the viewer's account locale or the `Accept-Language` HTTP header locale. */
		formattedValue: Option[String] = None
	)
	
	case class Residence(
	  /** Metadata about the residence. */
		metadata: Option[Schema.FieldMetadata] = None,
	  /** The address of the residence. */
		value: Option[String] = None,
	  /** True if the residence is the person's current residence; false if the residence is a past residence. */
		current: Option[Boolean] = None
	)
	
	case class SipAddress(
	  /** Metadata about the SIP address. */
		metadata: Option[Schema.FieldMetadata] = None,
	  /** The SIP address in the [RFC 3261 19.1](https://tools.ietf.org/html/rfc3261#section-19.1) SIP URI format. */
		value: Option[String] = None,
	  /** The type of the SIP address. The type can be custom or or one of these predefined values: &#42; `home` &#42; `work` &#42; `mobile` &#42; `other` */
		`type`: Option[String] = None,
	  /** Output only. The type of the SIP address translated and formatted in the viewer's account locale or the `Accept-Language` HTTP header locale. */
		formattedType: Option[String] = None
	)
	
	case class Skill(
	  /** Metadata about the skill. */
		metadata: Option[Schema.FieldMetadata] = None,
	  /** The skill; for example, `underwater basket weaving`. */
		value: Option[String] = None
	)
	
	case class Tagline(
	  /** Metadata about the tagline. */
		metadata: Option[Schema.FieldMetadata] = None,
	  /** The tagline. */
		value: Option[String] = None
	)
	
	case class Url(
	  /** Metadata about the URL. */
		metadata: Option[Schema.FieldMetadata] = None,
	  /** The URL. */
		value: Option[String] = None,
	  /** The type of the URL. The type can be custom or one of these predefined values: &#42; `home` &#42; `work` &#42; `blog` &#42; `profile` &#42; `homePage` &#42; `ftp` &#42; `reservations` &#42; `appInstallPage`: website for a Currents application. &#42; `other` */
		`type`: Option[String] = None,
	  /** Output only. The type of the URL translated and formatted in the viewer's account locale or the `Accept-Language` HTTP header locale. */
		formattedType: Option[String] = None
	)
	
	case class UserDefined(
	  /** Metadata about the user defined data. */
		metadata: Option[Schema.FieldMetadata] = None,
	  /** The end user specified key of the user defined data. */
		key: Option[String] = None,
	  /** The end user specified value of the user defined data. */
		value: Option[String] = None
	)
	
	case class Empty(
	
	)
	
	case class DeleteContactPhotoResponse(
	  /** The updated person, if person_fields is set in the DeleteContactPhotoRequest; otherwise this will be unset. */
		person: Option[Schema.Person] = None
	)
	
	object UpdateContactPhotoRequest {
		enum SourcesEnum extends Enum[SourcesEnum] { case READ_SOURCE_TYPE_UNSPECIFIED, READ_SOURCE_TYPE_PROFILE, READ_SOURCE_TYPE_CONTACT, READ_SOURCE_TYPE_DOMAIN_CONTACT, READ_SOURCE_TYPE_OTHER_CONTACT }
	}
	case class UpdateContactPhotoRequest(
	  /** Required. Raw photo bytes */
		photoBytes: Option[String] = None,
	  /** Optional. A field mask to restrict which fields on the person are returned. Multiple fields can be specified by separating them with commas. Defaults to empty if not set, which will skip the post mutate get. Valid values are: &#42; addresses &#42; ageRanges &#42; biographies &#42; birthdays &#42; calendarUrls &#42; clientData &#42; coverPhotos &#42; emailAddresses &#42; events &#42; externalIds &#42; genders &#42; imClients &#42; interests &#42; locales &#42; locations &#42; memberships &#42; metadata &#42; miscKeywords &#42; names &#42; nicknames &#42; occupations &#42; organizations &#42; phoneNumbers &#42; photos &#42; relations &#42; sipAddresses &#42; skills &#42; urls &#42; userDefined */
		personFields: Option[String] = None,
	  /** Optional. A mask of what source types to return. Defaults to READ_SOURCE_TYPE_CONTACT and READ_SOURCE_TYPE_PROFILE if not set. */
		sources: Option[List[Schema.UpdateContactPhotoRequest.SourcesEnum]] = None
	)
	
	case class UpdateContactPhotoResponse(
	  /** The updated person, if person_fields is set in the UpdateContactPhotoRequest; otherwise this will be unset. */
		person: Option[Schema.Person] = None
	)
	
	case class SearchResponse(
	  /** The results of the request. */
		results: Option[List[Schema.SearchResult]] = None
	)
	
	case class SearchResult(
	  /** The matched Person. */
		person: Option[Schema.Person] = None
	)
	
	case class BatchDeleteContactsRequest(
	  /** Required. The resource names of the contact to delete. It's repeatable. Allows up to 500 resource names in a single request. */
		resourceNames: Option[List[String]] = None
	)
	
	object BatchCreateContactsRequest {
		enum SourcesEnum extends Enum[SourcesEnum] { case READ_SOURCE_TYPE_UNSPECIFIED, READ_SOURCE_TYPE_PROFILE, READ_SOURCE_TYPE_CONTACT, READ_SOURCE_TYPE_DOMAIN_CONTACT, READ_SOURCE_TYPE_OTHER_CONTACT }
	}
	case class BatchCreateContactsRequest(
	  /** Required. The contact to create. Allows up to 200 contacts in a single request. */
		contacts: Option[List[Schema.ContactToCreate]] = None,
	  /** Required. A field mask to restrict which fields on each person are returned in the response. Multiple fields can be specified by separating them with commas. If read mask is left empty, the post-mutate-get is skipped and no data will be returned in the response. Valid values are: &#42; addresses &#42; ageRanges &#42; biographies &#42; birthdays &#42; calendarUrls &#42; clientData &#42; coverPhotos &#42; emailAddresses &#42; events &#42; externalIds &#42; genders &#42; imClients &#42; interests &#42; locales &#42; locations &#42; memberships &#42; metadata &#42; miscKeywords &#42; names &#42; nicknames &#42; occupations &#42; organizations &#42; phoneNumbers &#42; photos &#42; relations &#42; sipAddresses &#42; skills &#42; urls &#42; userDefined */
		readMask: Option[String] = None,
	  /** Optional. A mask of what source types to return in the post mutate read. Defaults to READ_SOURCE_TYPE_CONTACT and READ_SOURCE_TYPE_PROFILE if not set. */
		sources: Option[List[Schema.BatchCreateContactsRequest.SourcesEnum]] = None
	)
	
	case class ContactToCreate(
	  /** Required. The person data to populate a newly created source. */
		contactPerson: Option[Schema.Person] = None
	)
	
	case class BatchCreateContactsResponse(
	  /** The contacts that were created, unless the request `read_mask` is empty. */
		createdPeople: Option[List[Schema.PersonResponse]] = None
	)
	
	case class PersonResponse(
	  /** &#42;&#42;DEPRECATED&#42;&#42; (Please use status instead) [HTTP 1.1 status code] (http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html). */
		httpStatusCode: Option[Int] = None,
	  /** The person. */
		person: Option[Schema.Person] = None,
	  /** The original requested resource name. May be different than the resource name on the returned person. The resource name can change when adding or removing fields that link a contact and profile such as a verified email, verified phone number, or a profile URL. */
		requestedResourceName: Option[String] = None,
	  /** The status of the response. */
		status: Option[Schema.Status] = None
	)
	
	case class Status(
	  /** The status code, which should be an enum value of google.rpc.Code. */
		code: Option[Int] = None,
	  /** A developer-facing error message, which should be in English. Any user-facing error message should be localized and sent in the google.rpc.Status.details field, or localized by the client. */
		message: Option[String] = None,
	  /** A list of messages that carry the error details. There is a common set of message types for APIs to use. */
		details: Option[List[Map[String, JsValue]]] = None
	)
	
	object BatchUpdateContactsRequest {
		enum SourcesEnum extends Enum[SourcesEnum] { case READ_SOURCE_TYPE_UNSPECIFIED, READ_SOURCE_TYPE_PROFILE, READ_SOURCE_TYPE_CONTACT, READ_SOURCE_TYPE_DOMAIN_CONTACT, READ_SOURCE_TYPE_OTHER_CONTACT }
	}
	case class BatchUpdateContactsRequest(
	  /** Required. A map of resource names to the person data to be updated. Allows up to 200 contacts in a single request. */
		contacts: Option[Map[String, Schema.Person]] = None,
	  /** Required. A field mask to restrict which fields on the person are updated. Multiple fields can be specified by separating them with commas. All specified fields will be replaced, or cleared if left empty for each person. Valid values are: &#42; addresses &#42; biographies &#42; birthdays &#42; calendarUrls &#42; clientData &#42; emailAddresses &#42; events &#42; externalIds &#42; genders &#42; imClients &#42; interests &#42; locales &#42; locations &#42; memberships &#42; miscKeywords &#42; names &#42; nicknames &#42; occupations &#42; organizations &#42; phoneNumbers &#42; relations &#42; sipAddresses &#42; urls &#42; userDefined */
		updateMask: Option[String] = None,
	  /** Required. A field mask to restrict which fields on each person are returned. Multiple fields can be specified by separating them with commas. If read mask is left empty, the post-mutate-get is skipped and no data will be returned in the response. Valid values are: &#42; addresses &#42; ageRanges &#42; biographies &#42; birthdays &#42; calendarUrls &#42; clientData &#42; coverPhotos &#42; emailAddresses &#42; events &#42; externalIds &#42; genders &#42; imClients &#42; interests &#42; locales &#42; locations &#42; memberships &#42; metadata &#42; miscKeywords &#42; names &#42; nicknames &#42; occupations &#42; organizations &#42; phoneNumbers &#42; photos &#42; relations &#42; sipAddresses &#42; skills &#42; urls &#42; userDefined */
		readMask: Option[String] = None,
	  /** Optional. A mask of what source types to return. Defaults to READ_SOURCE_TYPE_CONTACT and READ_SOURCE_TYPE_PROFILE if not set. */
		sources: Option[List[Schema.BatchUpdateContactsRequest.SourcesEnum]] = None
	)
	
	case class BatchUpdateContactsResponse(
	  /** A map of resource names to the contacts that were updated, unless the request `read_mask` is empty. */
		updateResult: Option[Map[String, Schema.PersonResponse]] = None
	)
	
	case class ListOtherContactsResponse(
	  /** The list of "Other contacts" returned as Person resources. "Other contacts" support a limited subset of fields. See ListOtherContactsRequest.request_mask for more detailed information. */
		otherContacts: Option[List[Schema.Person]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** A token, which can be sent as `sync_token` to retrieve changes since the last request. Request must set `request_sync_token` to return the sync token. */
		nextSyncToken: Option[String] = None,
	  /** The total number of other contacts in the list without pagination. */
		totalSize: Option[Int] = None
	)
	
	object CopyOtherContactToMyContactsGroupRequest {
		enum SourcesEnum extends Enum[SourcesEnum] { case READ_SOURCE_TYPE_UNSPECIFIED, READ_SOURCE_TYPE_PROFILE, READ_SOURCE_TYPE_CONTACT, READ_SOURCE_TYPE_DOMAIN_CONTACT, READ_SOURCE_TYPE_OTHER_CONTACT }
	}
	case class CopyOtherContactToMyContactsGroupRequest(
	  /** Required. A field mask to restrict which fields are copied into the new contact. Valid values are: &#42; emailAddresses &#42; names &#42; phoneNumbers */
		copyMask: Option[String] = None,
	  /** Optional. A field mask to restrict which fields on the person are returned. Multiple fields can be specified by separating them with commas. Defaults to the copy mask with metadata and membership fields if not set. Valid values are: &#42; addresses &#42; ageRanges &#42; biographies &#42; birthdays &#42; calendarUrls &#42; clientData &#42; coverPhotos &#42; emailAddresses &#42; events &#42; externalIds &#42; genders &#42; imClients &#42; interests &#42; locales &#42; locations &#42; memberships &#42; metadata &#42; miscKeywords &#42; names &#42; nicknames &#42; occupations &#42; organizations &#42; phoneNumbers &#42; photos &#42; relations &#42; sipAddresses &#42; skills &#42; urls &#42; userDefined */
		readMask: Option[String] = None,
	  /** Optional. A mask of what source types to return. Defaults to READ_SOURCE_TYPE_CONTACT and READ_SOURCE_TYPE_PROFILE if not set. */
		sources: Option[List[Schema.CopyOtherContactToMyContactsGroupRequest.SourcesEnum]] = None
	)
	
	case class GetPeopleResponse(
	  /** The response for each requested resource name. */
		responses: Option[List[Schema.PersonResponse]] = None
	)
	
	case class ListConnectionsResponse(
	  /** The list of people that the requestor is connected to. */
		connections: Option[List[Schema.Person]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** A token, which can be sent as `sync_token` to retrieve changes since the last request. Request must set `request_sync_token` to return the sync token. When the response is paginated, only the last page will contain `nextSyncToken`. */
		nextSyncToken: Option[String] = None,
	  /** &#42;&#42;DEPRECATED&#42;&#42; (Please use totalItems) The total number of people in the list without pagination. */
		totalPeople: Option[Int] = None,
	  /** The total number of items in the list without pagination. */
		totalItems: Option[Int] = None
	)
	
	case class ListDirectoryPeopleResponse(
	  /** The list of people in the domain directory. */
		people: Option[List[Schema.Person]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** A token, which can be sent as `sync_token` to retrieve changes since the last request. Request must set `request_sync_token` to return the sync token. */
		nextSyncToken: Option[String] = None
	)
	
	case class SearchDirectoryPeopleResponse(
	  /** The list of people in the domain directory that match the query. */
		people: Option[List[Schema.Person]] = None,
	  /** A token, which can be sent as `page_token` to retrieve the next page. If this field is omitted, there are no subsequent pages. */
		nextPageToken: Option[String] = None,
	  /** The total number of items in the list without pagination. */
		totalSize: Option[Int] = None
	)
	
	case class BatchGetContactGroupsResponse(
	  /** The list of responses for each requested contact group resource. */
		responses: Option[List[Schema.ContactGroupResponse]] = None
	)
	
	case class ContactGroupResponse(
	  /** The original requested resource name. */
		requestedResourceName: Option[String] = None,
	  /** The status of the response. */
		status: Option[Schema.Status] = None,
	  /** The contact group. */
		contactGroup: Option[Schema.ContactGroup] = None
	)
	
	object ContactGroup {
		enum GroupTypeEnum extends Enum[GroupTypeEnum] { case GROUP_TYPE_UNSPECIFIED, USER_CONTACT_GROUP, SYSTEM_CONTACT_GROUP }
	}
	case class ContactGroup(
	  /** The resource name for the contact group, assigned by the server. An ASCII string, in the form of `contactGroups/{contact_group_id}`. */
		resourceName: Option[String] = None,
	  /** The [HTTP entity tag](https://en.wikipedia.org/wiki/HTTP_ETag) of the resource. Used for web cache validation. */
		etag: Option[String] = None,
	  /** Output only. Metadata about the contact group. */
		metadata: Option[Schema.ContactGroupMetadata] = None,
	  /** Output only. The contact group type. */
		groupType: Option[Schema.ContactGroup.GroupTypeEnum] = None,
	  /** The contact group name set by the group owner or a system provided name for system groups. For [`contactGroups.create`](/people/api/rest/v1/contactGroups/create) or [`contactGroups.update`](/people/api/rest/v1/contactGroups/update) the name must be unique to the users contact groups. Attempting to create a group with a duplicate name will return a HTTP 409 error. */
		name: Option[String] = None,
	  /** Output only. The name translated and formatted in the viewer's account locale or the `Accept-Language` HTTP header locale for system groups names. Group names set by the owner are the same as name. */
		formattedName: Option[String] = None,
	  /** Output only. The list of contact person resource names that are members of the contact group. The field is only populated for GET requests and will only return as many members as `maxMembers` in the get request. */
		memberResourceNames: Option[List[String]] = None,
	  /** Output only. The total number of contacts in the group irrespective of max members in specified in the request. */
		memberCount: Option[Int] = None,
	  /** The group's client data. */
		clientData: Option[List[Schema.GroupClientData]] = None
	)
	
	case class ContactGroupMetadata(
	  /** Output only. The time the group was last updated. */
		updateTime: Option[String] = None,
	  /** Output only. True if the contact group resource has been deleted. Populated only for [`ListContactGroups`](/people/api/rest/v1/contactgroups/list) requests that include a sync token. */
		deleted: Option[Boolean] = None
	)
	
	case class GroupClientData(
	  /** The client specified key of the client data. */
		key: Option[String] = None,
	  /** The client specified value of the client data. */
		value: Option[String] = None
	)
	
	case class CreateContactGroupRequest(
	  /** Required. The contact group to create. */
		contactGroup: Option[Schema.ContactGroup] = None,
	  /** Optional. A field mask to restrict which fields on the group are returned. Defaults to `metadata`, `groupType`, and `name` if not set or set to empty. Valid fields are: &#42; clientData &#42; groupType &#42; metadata &#42; name */
		readGroupFields: Option[String] = None
	)
	
	case class ListContactGroupsResponse(
	  /** The list of contact groups. Members of the contact groups are not populated. */
		contactGroups: Option[List[Schema.ContactGroup]] = None,
	  /** The total number of items in the list without pagination. */
		totalItems: Option[Int] = None,
	  /** The token that can be used to retrieve the next page of results. */
		nextPageToken: Option[String] = None,
	  /** The token that can be used to retrieve changes since the last request. */
		nextSyncToken: Option[String] = None
	)
	
	case class UpdateContactGroupRequest(
	  /** Required. The contact group to update. */
		contactGroup: Option[Schema.ContactGroup] = None,
	  /** Optional. A field mask to restrict which fields on the group are updated. Multiple fields can be specified by separating them with commas. Defaults to `name` if not set or set to empty. Updated fields are replaced. Valid values are: &#42; clientData &#42; name */
		updateGroupFields: Option[String] = None,
	  /** Optional. A field mask to restrict which fields on the group are returned. Defaults to `metadata`, `groupType`, and `name` if not set or set to empty. Valid fields are: &#42; clientData &#42; groupType &#42; memberCount &#42; metadata &#42; name */
		readGroupFields: Option[String] = None
	)
	
	case class ModifyContactGroupMembersRequest(
	  /** Optional. The resource names of the contact people to add in the form of `people/{person_id}`. The total number of resource names in `resource_names_to_add` and `resource_names_to_remove` must be less than or equal to 1000. */
		resourceNamesToAdd: Option[List[String]] = None,
	  /** Optional. The resource names of the contact people to remove in the form of `people/{person_id}`. The total number of resource names in `resource_names_to_add` and `resource_names_to_remove` must be less than or equal to 1000. */
		resourceNamesToRemove: Option[List[String]] = None
	)
	
	case class ModifyContactGroupMembersResponse(
	  /** The contact people resource names that were not found. */
		notFoundResourceNames: Option[List[String]] = None,
	  /** The contact people resource names that cannot be removed from their last contact group. */
		canNotRemoveLastContactGroupResourceNames: Option[List[String]] = None
	)
}
