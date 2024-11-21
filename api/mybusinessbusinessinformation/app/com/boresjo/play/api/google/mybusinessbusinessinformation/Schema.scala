package com.boresjo.play.api.google.mybusinessbusinessinformation

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class ListAttributeMetadataResponse(
	  /** A collection of attribute metadata for the available attributes. */
		attributeMetadata: Option[List[Schema.AttributeMetadata]] = None,
	  /** If the number of attributes exceeded the requested page size, this field will be populated with a token to fetch the next page of attributes on a subsequent call to `attributes.list`. If there are no more attributes, this field will not be present in the response. */
		nextPageToken: Option[String] = None
	)
	
	object AttributeMetadata {
		enum ValueTypeEnum extends Enum[ValueTypeEnum] { case ATTRIBUTE_VALUE_TYPE_UNSPECIFIED, BOOL, ENUM, URL, REPEATED_ENUM }
	}
	case class AttributeMetadata(
	  /** The unique identifier for the attribute. */
		parent: Option[String] = None,
	  /** The value type for the attribute. Values set and retrieved should be expected to be of this type. */
		valueType: Option[Schema.AttributeMetadata.ValueTypeEnum] = None,
	  /** The localized display name for the attribute, if available; otherwise, the English display name. */
		displayName: Option[String] = None,
	  /** The localized display name of the group that contains this attribute, if available; otherwise, the English group name. Related attributes are collected into a group and should be displayed together under the heading given here. */
		groupDisplayName: Option[String] = None,
	  /** If true, the attribute supports multiple values. If false, only a single value should be provided. */
		repeatable: Option[Boolean] = None,
	  /** For some types of attributes (for example, enums), a list of supported values and corresponding display names for those values is provided. */
		valueMetadata: Option[List[Schema.AttributeValueMetadata]] = None,
	  /** If true, the attribute is deprecated and should no longer be used. If deprecated, updating this attribute will not result in an error, but updates will not be saved. At some point after being deprecated, the attribute will be removed entirely and it will become an error. */
		deprecated: Option[Boolean] = None
	)
	
	case class AttributeValueMetadata(
	  /** The attribute value. */
		value: Option[JsValue] = None,
	  /** The display name for this value, localized where available; otherwise, in English. The value display name is intended to be used in context with the attribute display name. For example, for a "WiFi" enum attribute, this could contain "Paid" to represent paid Wi-Fi. */
		displayName: Option[String] = None
	)
	
	case class Attributes(
	  /** Required. Google identifier for this location in the form of `locations/{location_id}/attributes`. */
		name: Option[String] = None,
	  /** A collection of attributes that need to be updated. */
		attributes: Option[List[Schema.Attribute]] = None
	)
	
	object Attribute {
		enum ValueTypeEnum extends Enum[ValueTypeEnum] { case ATTRIBUTE_VALUE_TYPE_UNSPECIFIED, BOOL, ENUM, URL, REPEATED_ENUM }
	}
	case class Attribute(
	  /** Required. The resource name for this attribute. */
		name: Option[String] = None,
	  /** Output only. The type of value that this attribute contains. This should be used to determine how to interpret the value. */
		valueType: Option[Schema.Attribute.ValueTypeEnum] = None,
	  /** The values for this attribute. The type of the values supplied must match that expected for that attribute. This is a repeated field where multiple attribute values may be provided. Attribute types only support one value. */
		values: Option[List[JsValue]] = None,
	  /** When the attribute value type is REPEATED_ENUM, this contains the attribute value, and the other values fields must be empty. */
		repeatedEnumValue: Option[Schema.RepeatedEnumAttributeValue] = None,
	  /** When the attribute value type is URL, this field contains the value(s) for this attribute, and the other values fields must be empty. */
		uriValues: Option[List[Schema.UriAttributeValue]] = None
	)
	
	case class RepeatedEnumAttributeValue(
	  /** Enum values that are set. */
		setValues: Option[List[String]] = None,
	  /** Enum values that are unset. */
		unsetValues: Option[List[String]] = None
	)
	
	case class UriAttributeValue(
	  /** Required. The proposed URI value for this attribute. */
		uri: Option[String] = None
	)
	
	case class ListCategoriesResponse(
	  /** The matching categories based on the requested parameters. */
		categories: Option[List[Schema.Category]] = None,
	  /** If the number of categories exceeded the requested page size, this field will be populated with a token to fetch the next page of categories on a subsequent call to `ListCategories`. */
		nextPageToken: Option[String] = None
	)
	
	case class Category(
	  /** Required. A stable ID (provided by Google) for this category. The value must be specified when modifying the category (when creating or updating a location). */
		name: Option[String] = None,
	  /** Output only. The human-readable name of the category. This is set when reading the location. When modifying the location, `category_id` must be set. */
		displayName: Option[String] = None,
	  /** Output only. A list of all the service types that are available for this business category. */
		serviceTypes: Option[List[Schema.ServiceType]] = None,
	  /** Output only. More hours types that are available for this business category. */
		moreHoursTypes: Option[List[Schema.MoreHoursType]] = None
	)
	
	case class ServiceType(
	  /** Output only. A stable ID (provided by Google) for this service type. */
		serviceTypeId: Option[String] = None,
	  /** Output only. The human-readable display name for the service type. */
		displayName: Option[String] = None
	)
	
	case class MoreHoursType(
	  /** Output only. A stable ID provided by Google for this hours type. */
		hoursTypeId: Option[String] = None,
	  /** Output only. The human-readable English display name for the hours type. */
		displayName: Option[String] = None,
	  /** Output only. The human-readable localized display name for the hours type. */
		localizedDisplayName: Option[String] = None
	)
	
	case class BatchGetCategoriesResponse(
	  /** Categories that match the GConcept ids provided in the request. They will not come in the same order as category ids in the request. */
		categories: Option[List[Schema.Category]] = None
	)
	
	case class Chain(
	  /** Required. The chain's resource name, in the format `chains/{chain_id}`. */
		name: Option[String] = None,
	  /** Names of the chain. */
		chainNames: Option[List[Schema.ChainName]] = None,
	  /** Websites of the chain. */
		websites: Option[List[Schema.ChainUri]] = None,
	  /** Number of locations that are part of this chain. */
		locationCount: Option[Int] = None
	)
	
	case class ChainName(
	  /** The display name for this chain. */
		displayName: Option[String] = None,
	  /** The BCP 47 code of language of the name. */
		languageCode: Option[String] = None
	)
	
	case class ChainUri(
	  /** The uri for this chain. */
		uri: Option[String] = None
	)
	
	case class SearchChainsResponse(
	  /** Chains that match the queried chain_display_name in SearchChainsRequest. If there are no matches, this field will be empty. Results are listed in order of relevance. */
		chains: Option[List[Schema.Chain]] = None
	)
	
	case class SearchGoogleLocationsRequest(
	  /** Location to search for. If provided, will find locations which match the provided location details. */
		location: Option[Schema.Location] = None,
	  /** Text query to search for. The search results from a query string will be less accurate than if providing an exact location, but can provide more inexact matches. */
		query: Option[String] = None,
	  /** The number of matches to return. The default value is 3, with a maximum of 10. Note that latency may increase if more are requested. There is no pagination. */
		pageSize: Option[Int] = None
	)
	
	case class Location(
	  /** Google identifier for this location in the form: `locations/{location_id}`. */
		name: Option[String] = None,
	  /** Immutable. The language of the location. Set during creation and not updateable. */
		languageCode: Option[String] = None,
	  /** Optional. External identifier for this location, which must be unique within a given account. This is a means of associating the location with your own records. */
		storeCode: Option[String] = None,
	  /** Required. Location name should reflect your business's real-world name, as used consistently on your storefront, website, and stationery, and as known to customers. Any additional information, when relevant, can be included in other fields of the resource (for example, `Address`, `Categories`). Don't add unnecessary information to your name (for example, prefer "Google" over "Google Inc. - Mountain View Corporate Headquarters"). Don't include marketing taglines, store codes, special characters, hours or closed/open status, phone numbers, website URLs, service/product information, location/address or directions, or containment information (for example, "Chase ATM in Duane Reade"). */
		title: Option[String] = None,
	  /** Optional. The different phone numbers that customers can use to get in touch with the business. */
		phoneNumbers: Option[Schema.PhoneNumbers] = None,
	  /** Optional. The different categories that describe the business. */
		categories: Option[Schema.Categories] = None,
	  /** Optional. A precise, accurate address to describe your business location. PO boxes or mailboxes located at remote locations are not acceptable. At this time, you can specify a maximum of five `address_lines` values in the address. This field should only be set for businesses that have a storefront. This field should not be set for locations of type `CUSTOMER_LOCATION_ONLY` but if set, any value provided will be discarded. */
		storefrontAddress: Option[Schema.PostalAddress] = None,
	  /** Optional. A URL for this business. If possible, use a URL that represents this individual business location instead of a generic website/URL that represents all locations, or the brand. */
		websiteUri: Option[String] = None,
	  /** Optional. Operating hours for the business. */
		regularHours: Option[Schema.BusinessHours] = None,
	  /** Optional. Special hours for the business. This typically includes holiday hours, and other times outside of regular operating hours. These override regular business hours. This field cannot be set without regular hours. */
		specialHours: Option[Schema.SpecialHours] = None,
	  /** Optional. Service area businesses provide their service at the customer's location. If this business is a service area business, this field describes the area(s) serviced by the business. */
		serviceArea: Option[Schema.ServiceAreaBusiness] = None,
	  /** Optional. A collection of free-form strings to allow you to tag your business. These labels are NOT user facing; only you can see them. Must be between 1-255 characters per label. */
		labels: Option[List[String]] = None,
	  /** Optional. Additional information that is surfaced in AdWords. */
		adWordsLocationExtensions: Option[Schema.AdWordsLocationExtensions] = None,
	  /** Optional. User-provided latitude and longitude. When creating a location, this field is ignored if the provided address geocodes successfully. This field is only returned on get requests if the user-provided `latlng` value was accepted during create, or the `latlng` value was updated through the Google Business Profile website. This field can only be updated by approved clients. */
		latlng: Option[Schema.LatLng] = None,
	  /** Optional. A flag that indicates whether the location is currently open for business. */
		openInfo: Option[Schema.OpenInfo] = None,
	  /** Output only. Additional non-user-editable information. */
		metadata: Option[Schema.Metadata] = None,
	  /** Optional. Describes your business in your own voice and shares with users the unique story of your business and offerings. This field is required for all categories except lodging categories (e.g. hotels, motels, inns). */
		profile: Option[Schema.Profile] = None,
	  /** Optional. All locations and chain related to this one. */
		relationshipData: Option[Schema.RelationshipData] = None,
	  /** Optional. More hours for a business's different departments or specific customers. */
		moreHours: Option[List[Schema.MoreHours]] = None,
	  /** Optional. List of services supported by merchants. A service can be haircut, install water heater, etc. Duplicated service items will be removed automatically. */
		serviceItems: Option[List[Schema.ServiceItem]] = None
	)
	
	case class PhoneNumbers(
	  /** Required. A phone number that connects to your individual business location as directly as possible. Use a local phone number instead of a central, call center helpline number whenever possible. */
		primaryPhone: Option[String] = None,
	  /** Optional. Up to two phone numbers (mobile or landline, no fax) at which your business can be called, in addition to your primary phone number. */
		additionalPhones: Option[List[String]] = None
	)
	
	case class Categories(
	  /** Required. Category that best describes the core business this location engages in. */
		primaryCategory: Option[Schema.Category] = None,
	  /** Optional. Additional categories to describe your business. Categories help your customers find accurate, specific results for services they're interested in. To keep your business information accurate and live, make sure that you use as few categories as possible to describe your overall core business. Choose categories that are as specific as possible, but representative of your main business. */
		additionalCategories: Option[List[Schema.Category]] = None
	)
	
	case class PostalAddress(
	  /** The schema revision of the `PostalAddress`. This must be set to 0, which is the latest revision. All new revisions &#42;&#42;must&#42;&#42; be backward compatible with old revisions. */
		revision: Option[Int] = None,
	  /** Required. CLDR region code of the country/region of the address. This is never inferred and it is up to the user to ensure the value is correct. See https://cldr.unicode.org/ and https://www.unicode.org/cldr/charts/30/supplemental/territory_information.html for details. Example: "CH" for Switzerland. */
		regionCode: Option[String] = None,
	  /** Optional. BCP-47 language code of the contents of this address (if known). This is often the UI language of the input form or is expected to match one of the languages used in the address' country/region, or their transliterated equivalents. This can affect formatting in certain countries, but is not critical to the correctness of the data and will never affect any validation or other non-formatting related operations. If this value is not known, it should be omitted (rather than specifying a possibly incorrect default). Examples: "zh-Hant", "ja", "ja-Latn", "en". */
		languageCode: Option[String] = None,
	  /** Optional. Postal code of the address. Not all countries use or require postal codes to be present, but where they are used, they may trigger additional validation with other parts of the address (e.g. state/zip validation in the U.S.A.). */
		postalCode: Option[String] = None,
	  /** Optional. Additional, country-specific, sorting code. This is not used in most regions. Where it is used, the value is either a string like "CEDEX", optionally followed by a number (e.g. "CEDEX 7"), or just a number alone, representing the "sector code" (Jamaica), "delivery area indicator" (Malawi) or "post office indicator" (e.g. Côte d'Ivoire). */
		sortingCode: Option[String] = None,
	  /** Optional. Highest administrative subdivision which is used for postal addresses of a country or region. For example, this can be a state, a province, an oblast, or a prefecture. Specifically, for Spain this is the province and not the autonomous community (e.g. "Barcelona" and not "Catalonia"). Many countries don't use an administrative area in postal addresses. E.g. in Switzerland this should be left unpopulated. */
		administrativeArea: Option[String] = None,
	  /** Optional. Generally refers to the city/town portion of the address. Examples: US city, IT comune, UK post town. In regions of the world where localities are not well defined or do not fit into this structure well, leave locality empty and use address_lines. */
		locality: Option[String] = None,
	  /** Optional. Sublocality of the address. For example, this can be neighborhoods, boroughs, districts. */
		sublocality: Option[String] = None,
	  /** Unstructured address lines describing the lower levels of an address. Because values in address_lines do not have type information and may sometimes contain multiple values in a single field (e.g. "Austin, TX"), it is important that the line order is clear. The order of address lines should be "envelope order" for the country/region of the address. In places where this can vary (e.g. Japan), address_language is used to make it explicit (e.g. "ja" for large-to-small ordering and "ja-Latn" or "en" for small-to-large). This way, the most specific line of an address can be selected based on the language. The minimum permitted structural representation of an address consists of a region_code with all remaining information placed in the address_lines. It would be possible to format such an address very approximately without geocoding, but no semantic reasoning could be made about any of the address components until it was at least partially resolved. Creating an address only containing a region_code and address_lines, and then geocoding is the recommended way to handle completely unstructured addresses (as opposed to guessing which parts of the address should be localities or administrative areas). */
		addressLines: Option[List[String]] = None,
	  /** Optional. The recipient at the address. This field may, under certain circumstances, contain multiline information. For example, it might contain "care of" information. */
		recipients: Option[List[String]] = None,
	  /** Optional. The name of the organization at the address. */
		organization: Option[String] = None
	)
	
	case class BusinessHours(
	  /** Required. A collection of times that this location is open for business. Each period represents a range of hours when the location is open during the week. */
		periods: Option[List[Schema.TimePeriod]] = None
	)
	
	object TimePeriod {
		enum OpenDayEnum extends Enum[OpenDayEnum] { case DAY_OF_WEEK_UNSPECIFIED, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
		enum CloseDayEnum extends Enum[CloseDayEnum] { case DAY_OF_WEEK_UNSPECIFIED, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
	}
	case class TimePeriod(
	  /** Required. Indicates the day of the week this period starts on. */
		openDay: Option[Schema.TimePeriod.OpenDayEnum] = None,
	  /** Required. Valid values are 00:00-24:00, where 24:00 represents midnight at the end of the specified day field. */
		openTime: Option[Schema.TimeOfDay] = None,
	  /** Required. Indicates the day of the week this period ends on. */
		closeDay: Option[Schema.TimePeriod.CloseDayEnum] = None,
	  /** Required. Valid values are 00:00-24:00, where 24:00 represents midnight at the end of the specified day field. */
		closeTime: Option[Schema.TimeOfDay] = None
	)
	
	case class TimeOfDay(
	  /** Hours of a day in 24 hour format. Must be greater than or equal to 0 and typically must be less than or equal to 23. An API may choose to allow the value "24:00:00" for scenarios like business closing time. */
		hours: Option[Int] = None,
	  /** Minutes of an hour. Must be greater than or equal to 0 and less than or equal to 59. */
		minutes: Option[Int] = None,
	  /** Seconds of a minute. Must be greater than or equal to 0 and typically must be less than or equal to 59. An API may allow the value 60 if it allows leap-seconds. */
		seconds: Option[Int] = None,
	  /** Fractions of seconds, in nanoseconds. Must be greater than or equal to 0 and less than or equal to 999,999,999. */
		nanos: Option[Int] = None
	)
	
	case class SpecialHours(
	  /** Required. A list of exceptions to the business's regular hours. */
		specialHourPeriods: Option[List[Schema.SpecialHourPeriod]] = None
	)
	
	case class SpecialHourPeriod(
	  /** Required. The calendar date this special hour period starts on. */
		startDate: Option[Schema.Date] = None,
	  /** Optional. Valid values are 00:00-24:00 where 24:00 represents midnight at the end of the specified day field. Must be specified if `closed` is false. */
		openTime: Option[Schema.TimeOfDay] = None,
	  /** Optional. The calendar date this special hour period ends on. If `end_date` field is not set, default to the date specified in `start_date`. If set, this field must be equal to or at most 1 day after `start_date`. */
		endDate: Option[Schema.Date] = None,
	  /** Optional. Valid values are 00:00-24:00, where 24:00 represents midnight at the end of the specified day field. Must be specified if `closed` is false. */
		closeTime: Option[Schema.TimeOfDay] = None,
	  /** Optional. If true, `end_date`, `open_time`, and `close_time` are ignored, and the date specified in `start_date` is treated as the location being closed for the entire day. */
		closed: Option[Boolean] = None
	)
	
	case class Date(
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None,
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None,
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None
	)
	
	object ServiceAreaBusiness {
		enum BusinessTypeEnum extends Enum[BusinessTypeEnum] { case BUSINESS_TYPE_UNSPECIFIED, CUSTOMER_LOCATION_ONLY, CUSTOMER_AND_BUSINESS_LOCATION }
	}
	case class ServiceAreaBusiness(
	  /** Required. Indicates the type of the service area business. */
		businessType: Option[Schema.ServiceAreaBusiness.BusinessTypeEnum] = None,
	  /** The area that this business serves defined through a set of places. */
		places: Option[Schema.Places] = None,
	  /** Immutable. CLDR region code of the country/region that this service area business is based in. See http://cldr.unicode.org/ and http://www.unicode.org/cldr/charts/30/supplemental/territory_information.html for details. Example: "CH" for Switzerland. This field is required for CUSTOMER_LOCATION_ONLY businesses, and is ignored otherwise. The region specified here can be different from regions for the areas that this business serves (e.g. service area businesses that provide services in regions other than the one that they are based in). If this location requires verification after creation, the address provided for verification purposes &#42;must&#42; be located within this region, and the business owner or their authorized representative &#42;must&#42; be able to receive postal mail at the provided verification address. */
		regionCode: Option[String] = None
	)
	
	case class Places(
	  /** The areas represented by place IDs. Limited to a maximum of 20 places. */
		placeInfos: Option[List[Schema.PlaceInfo]] = None
	)
	
	case class PlaceInfo(
	  /** Required. The localized name of the place. For example, `Scottsdale, AZ`. */
		placeName: Option[String] = None,
	  /** Required. The ID of the place. Must correspond to a region. (https://developers.google.com/places/web-service/supported_types#table3) */
		placeId: Option[String] = None
	)
	
	case class AdWordsLocationExtensions(
	  /** Required. An alternate phone number to display on AdWords location extensions instead of the location's primary phone number. */
		adPhone: Option[String] = None
	)
	
	case class LatLng(
	  /** The latitude in degrees. It must be in the range [-90.0, +90.0]. */
		latitude: Option[BigDecimal] = None,
	  /** The longitude in degrees. It must be in the range [-180.0, +180.0]. */
		longitude: Option[BigDecimal] = None
	)
	
	object OpenInfo {
		enum StatusEnum extends Enum[StatusEnum] { case OPEN_FOR_BUSINESS_UNSPECIFIED, OPEN, CLOSED_PERMANENTLY, CLOSED_TEMPORARILY }
	}
	case class OpenInfo(
	  /** Required. Indicates whether or not the Location is currently open for business. All locations are open by default, unless updated to be closed. */
		status: Option[Schema.OpenInfo.StatusEnum] = None,
	  /** Output only. Indicates whether this business is eligible for re-open. */
		canReopen: Option[Boolean] = None,
	  /** Optional. The date on which the location first opened. If the exact day is not known, month and year only can be provided. The date must be in the past or be no more than one year in the future. */
		openingDate: Option[Schema.Date] = None
	)
	
	case class Metadata(
	  /** Output only. Indicates whether the place ID associated with this location has updates that need to be updated or rejected by the client. If this boolean is set, you should call the `getGoogleUpdated` method to lookup information that's needs to be verified. */
		hasGoogleUpdated: Option[Boolean] = None,
	  /** Output only. Indicates whether any of this Location's properties are in the edit pending state. */
		hasPendingEdits: Option[Boolean] = None,
	  /** Output only. Indicates whether the location can be deleted using the API. */
		canDelete: Option[Boolean] = None,
	  /** Output only. Indicates if the listing can manage local posts. */
		canOperateLocalPost: Option[Boolean] = None,
	  /** Output only. Indicates if the listing can modify the service list. */
		canModifyServiceList: Option[Boolean] = None,
	  /** Output only. Indicates if the listing is eligible for food menu. */
		canHaveFoodMenus: Option[Boolean] = None,
	  /** Output only. Indicates whether the location can operate on Health data. */
		canOperateHealthData: Option[Boolean] = None,
	  /** Output only. Indicates whether the location can operate on Lodging data. */
		canOperateLodgingData: Option[Boolean] = None,
	  /** Output only. If this locationappears on Google Maps, this field is populated with the place ID for the location. This ID can be used in various Places APIs. This field can be set during Create calls, but not for Update. */
		placeId: Option[String] = None,
	  /** Output only. The location resource that this location duplicates. */
		duplicateLocation: Option[String] = None,
	  /** Output only. A link to the location on Maps. */
		mapsUri: Option[String] = None,
	  /** Output only. A link to the page on Google Search where a customer can leave a review for the location. */
		newReviewUri: Option[String] = None,
	  /** Output only. Indicates if the listing is eligible for business calls. */
		canHaveBusinessCalls: Option[Boolean] = None,
	  /** Output only. Indicates if the listing has Voice of Merchant. If this boolean is false, you should call the locations.getVoiceOfMerchantState API to get details as to why they do not have Voice of Merchant. */
		hasVoiceOfMerchant: Option[Boolean] = None
	)
	
	case class Profile(
	  /** Required. Description of the location in your own voice, not editable by anyone else. */
		description: Option[String] = None
	)
	
	case class RelationshipData(
	  /** The parent location that this location has relations with. */
		parentLocation: Option[Schema.RelevantLocation] = None,
	  /** The list of children locations that this location has relations with. */
		childrenLocations: Option[List[Schema.RelevantLocation]] = None,
	  /** The resource name of the Chain that this location is member of. How to find Chain ID */
		parentChain: Option[String] = None
	)
	
	object RelevantLocation {
		enum RelationTypeEnum extends Enum[RelationTypeEnum] { case RELATION_TYPE_UNSPECIFIED, DEPARTMENT_OF, INDEPENDENT_ESTABLISHMENT_IN }
	}
	case class RelevantLocation(
	  /** Required. Specify the location that is on the other side of the relation by its placeID. */
		placeId: Option[String] = None,
	  /** Required. The type of the relationship. */
		relationType: Option[Schema.RelevantLocation.RelationTypeEnum] = None
	)
	
	case class MoreHours(
	  /** Required. Type of hours. Clients should call {#link businessCategories:BatchGet} to get supported hours types for categories of their locations. */
		hoursTypeId: Option[String] = None,
	  /** Required. A collection of times that this location is open. Each period represents a range of hours when the location is open during the week. */
		periods: Option[List[Schema.TimePeriod]] = None
	)
	
	case class ServiceItem(
	  /** Optional. This field will be set case of structured services data. */
		structuredServiceItem: Option[Schema.StructuredServiceItem] = None,
	  /** Optional. This field will be set case of free-form services data. */
		freeFormServiceItem: Option[Schema.FreeFormServiceItem] = None,
	  /** Optional. Represents the monetary price of the service item. We recommend that currency_code and units should be set when including a price. This will be treated as a fixed price for the service item. */
		price: Option[Schema.Money] = None
	)
	
	case class StructuredServiceItem(
	  /** Required. The `service_type_id` field is a Google provided unique ID that can be found in `ServiceType`. This information is provided by `BatchGetCategories` rpc service. */
		serviceTypeId: Option[String] = None,
	  /** Optional. Description of structured service item. The character limit is 300. */
		description: Option[String] = None
	)
	
	case class FreeFormServiceItem(
	  /** Required. This field represents the category name (i.e. the category's stable ID). The `category` and `service_type_id` should match the possible combinations provided in the `Category` message. */
		category: Option[String] = None,
	  /** Required. Language-tagged labels for the item. We recommend that item names be 140 characters or less, and descriptions 250 characters or less. This field should only be set if the input is a custom service item. Standardized service types should be updated via service_type_id. */
		label: Option[Schema.Label] = None
	)
	
	case class Label(
	  /** Required. Display name for the price list, section, or item. */
		displayName: Option[String] = None,
	  /** Optional. Description of the price list, section, or item. */
		description: Option[String] = None,
	  /** Optional. The BCP-47 language code that these strings apply for. Only one set of labels may be set per language. */
		languageCode: Option[String] = None
	)
	
	case class Money(
	  /** The three-letter currency code defined in ISO 4217. */
		currencyCode: Option[String] = None,
	  /** The whole units of the amount. For example if `currencyCode` is `"USD"`, then 1 unit is one US dollar. */
		units: Option[String] = None,
	  /** Number of nano (10^-9) units of the amount. The value must be between -999,999,999 and +999,999,999 inclusive. If `units` is positive, `nanos` must be positive or zero. If `units` is zero, `nanos` can be positive, zero, or negative. If `units` is negative, `nanos` must be negative or zero. For example $-1.75 is represented as `units`=-1 and `nanos`=-750,000,000. */
		nanos: Option[Int] = None
	)
	
	case class SearchGoogleLocationsResponse(
	  /** A collection of GoogleLocations that are potential matches to the specified request, listed in order from most to least accuracy. */
		googleLocations: Option[List[Schema.GoogleLocation]] = None
	)
	
	case class GoogleLocation(
	  /** Resource name of this GoogleLocation, in the format `googleLocations/{googleLocationId}`. */
		name: Option[String] = None,
	  /** The sparsely populated Location information. This field can be re-used in CreateLocation if it is not currently claimed by a user. */
		location: Option[Schema.Location] = None,
	  /** A URL that will redirect the user to the request admin rights UI. This field is only present if the location has already been claimed by any user, including the current user. */
		requestAdminRightsUri: Option[String] = None
	)
	
	case class ListLocationsResponse(
	  /** The locations. */
		locations: Option[List[Schema.Location]] = None,
	  /** If the number of locations exceeded the requested page size, this field is populated with a token to fetch the next page of locations on a subsequent call to `ListLocations`. If there are no more locations, this field is not present in the response. */
		nextPageToken: Option[String] = None,
	  /** The approximate number of Locations in the list irrespective of pagination. This field will only be returned if `filter` is used as a query parameter. */
		totalSize: Option[Int] = None
	)
	
	case class GoogleUpdatedLocation(
	  /** The Google-updated version of this location. */
		location: Option[Schema.Location] = None,
	  /** The fields that Google updated. */
		diffMask: Option[String] = None,
	  /** The fields that have pending edits that haven't yet been pushed to Maps and Search. */
		pendingMask: Option[String] = None
	)
	
	case class Empty(
	
	)
}
