package com.boresjo.play.api.google.places

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleMapsPlacesV1PlacePaymentOptions(
	  /** Place accepts NFC payments. */
		acceptsNfc: Option[Boolean] = None,
	  /** Place accepts cash only as payment. Places with this attribute may still accept other payment methods. */
		acceptsCashOnly: Option[Boolean] = None,
	  /** Place accepts credit cards as payment. */
		acceptsCreditCards: Option[Boolean] = None,
	  /** Place accepts debit cards as payment. */
		acceptsDebitCards: Option[Boolean] = None
	)
	
	case class GoogleMapsPlacesV1SearchNearbyRequestLocationRestriction(
	  /** A circle defined by center point and radius. */
		circle: Option[Schema.GoogleMapsPlacesV1Circle] = None
	)
	
	case class GoogleTypeMoney(
	  /** The whole units of the amount. For example if `currencyCode` is `"USD"`, then 1 unit is one US dollar. */
		units: Option[String] = None,
	  /** Number of nano (10^-9) units of the amount. The value must be between -999,999,999 and +999,999,999 inclusive. If `units` is positive, `nanos` must be positive or zero. If `units` is zero, `nanos` can be positive, zero, or negative. If `units` is negative, `nanos` must be negative or zero. For example $-1.75 is represented as `units`=-1 and `nanos`=-750,000,000. */
		nanos: Option[Int] = None,
	  /** The three-letter currency code defined in ISO 4217. */
		currencyCode: Option[String] = None
	)
	
	object GoogleMapsPlacesV1RoutingParameters {
		enum TravelModeEnum extends Enum[TravelModeEnum] { case TRAVEL_MODE_UNSPECIFIED, DRIVE, BICYCLE, WALK, TWO_WHEELER }
		enum RoutingPreferenceEnum extends Enum[RoutingPreferenceEnum] { case ROUTING_PREFERENCE_UNSPECIFIED, TRAFFIC_UNAWARE, TRAFFIC_AWARE, TRAFFIC_AWARE_OPTIMAL }
	}
	case class GoogleMapsPlacesV1RoutingParameters(
	  /** Optional. An explicit routing origin that overrides the origin defined in the polyline. By default, the polyline origin is used. */
		origin: Option[Schema.GoogleTypeLatLng] = None,
	  /** Optional. The travel mode. */
		travelMode: Option[Schema.GoogleMapsPlacesV1RoutingParameters.TravelModeEnum] = None,
	  /** Optional. Specifies how to compute the routing summaries. The server attempts to use the selected routing preference to compute the route. The traffic aware routing preference is only available for the `DRIVE` or `TWO_WHEELER` `travelMode`. */
		routingPreference: Option[Schema.GoogleMapsPlacesV1RoutingParameters.RoutingPreferenceEnum] = None,
	  /** Optional. The route modifiers. */
		routeModifiers: Option[Schema.GoogleMapsPlacesV1RouteModifiers] = None
	)
	
	case class GoogleMapsPlacesV1Photo(
	  /** The maximum available width, in pixels. */
		widthPx: Option[Int] = None,
	  /** A link to show the photo on Google Maps. */
		googleMapsUri: Option[String] = None,
	  /** The maximum available height, in pixels. */
		heightPx: Option[Int] = None,
	  /** A link where users can flag a problem with the photo. */
		flagContentUri: Option[String] = None,
	  /** This photo's authors. */
		authorAttributions: Option[List[Schema.GoogleMapsPlacesV1AuthorAttribution]] = None,
	  /** Identifier. A reference representing this place photo which may be used to look up this place photo again (also called the API "resource" name: `places/{place_id}/photos/{photo}`). */
		name: Option[String] = None
	)
	
	case class GoogleMapsPlacesV1PlaceGenerativeSummary(
	  /** The overview of the place. */
		overview: Option[Schema.GoogleTypeLocalizedText] = None,
	  /** A link where users can flag a problem with the description summary. */
		descriptionFlagContentUri: Option[String] = None,
	  /** A link where users can flag a problem with the overview summary. */
		overviewFlagContentUri: Option[String] = None,
	  /** References that are used to generate the summary description. */
		references: Option[Schema.GoogleMapsPlacesV1References] = None,
	  /** The detailed description of the place. */
		description: Option[Schema.GoogleTypeLocalizedText] = None
	)
	
	case class GoogleMapsPlacesV1SearchTextRequestSearchAlongRouteParameters(
	  /** Required. The route polyline. */
		polyline: Option[Schema.GoogleMapsPlacesV1Polyline] = None
	)
	
	case class GoogleMapsPlacesV1AutocompletePlacesResponseSuggestionQueryPrediction(
	  /** A breakdown of the query prediction into main text containing the query and secondary text containing additional disambiguating features (such as a city or region). `structured_format` is recommended for developers who wish to show two separate, but related, UI elements. Developers who wish to show a single UI element may want to use `text` instead. They are two different ways to represent a query prediction. Users should not try to parse `structured_format` into `text` or vice versa. */
		structuredFormat: Option[Schema.GoogleMapsPlacesV1AutocompletePlacesResponseSuggestionStructuredFormat] = None,
	  /** The predicted text. This text does not represent a Place, but rather a text query that could be used in a search endpoint (for example, Text Search). `text` is recommended for developers who wish to show a single UI element. Developers who wish to show two separate, but related, UI elements may want to use `structured_format` instead. They are two different ways to represent a query prediction. Users should not try to parse `structured_format` into `text` or vice versa. May be in mixed languages if the request `input` and `language_code` are in different languages or if part of the query does not have a translation from the local language to `language_code`. */
		text: Option[Schema.GoogleMapsPlacesV1AutocompletePlacesResponseSuggestionFormattableText] = None
	)
	
	case class GoogleMapsPlacesV1AutocompletePlacesResponseSuggestionStructuredFormat(
	  /** Represents the name of the Place or query. */
		mainText: Option[Schema.GoogleMapsPlacesV1AutocompletePlacesResponseSuggestionFormattableText] = None,
	  /** Represents additional disambiguating features (such as a city or region) to further identify the Place or refine the query. */
		secondaryText: Option[Schema.GoogleMapsPlacesV1AutocompletePlacesResponseSuggestionFormattableText] = None
	)
	
	case class GoogleMapsPlacesV1ContextualContentJustificationReviewJustification(
	  /** The review that the highlighted text is generated from. */
		review: Option[Schema.GoogleMapsPlacesV1Review] = None,
		highlightedText: Option[Schema.GoogleMapsPlacesV1ContextualContentJustificationReviewJustificationHighlightedText] = None
	)
	
	case class GoogleMapsPlacesV1PlacePlusCode(
	  /** Place's compound code, such as "33GV+HQ, Ramberg, Norway", containing the suffix of the global code and replacing the prefix with a formatted name of a reference entity. */
		compoundCode: Option[String] = None,
	  /** Place's global (full) code, such as "9FWM33GV+HQ", representing an 1/8000 by 1/8000 degree area (~14 by 14 meters). */
		globalCode: Option[String] = None
	)
	
	case class GoogleMapsPlacesV1ContextualContentJustificationReviewJustificationHighlightedText(
		text: Option[String] = None,
	  /** The list of the ranges of the highlighted text. */
		highlightedTextRanges: Option[List[Schema.GoogleMapsPlacesV1ContextualContentJustificationReviewJustificationHighlightedTextHighlightedTextRange]] = None
	)
	
	case class GoogleMapsPlacesV1PlaceOpeningHoursSpecialDay(
	  /** The date of this special day. */
		date: Option[Schema.GoogleTypeDate] = None
	)
	
	case class GoogleMapsPlacesV1ContextualContentJustificationBusinessAvailabilityAttributesJustification(
	  /** If a place provides delivery. */
		delivery: Option[Boolean] = None,
	  /** If a place provides dine-in. */
		dineIn: Option[Boolean] = None,
	  /** If a place provides takeout. */
		takeout: Option[Boolean] = None
	)
	
	case class GoogleMapsPlacesV1SearchTextResponse(
	  /** A list of routing summaries where each entry associates to the corresponding place in the same index in the `places` field. If the routing summary is not available for one of the places, it will contain an empty entry. This list will have as many entries as the list of places if requested. */
		routingSummaries: Option[List[Schema.GoogleMapsPlacesV1RoutingSummary]] = None,
	  /** Experimental: See https://developers.google.com/maps/documentation/places/web-service/experimental/places-generative for more details. A list of contextual contents where each entry associates to the corresponding place in the same index in the places field. The contents that are relevant to the `text_query` in the request are preferred. If the contextual content is not available for one of the places, it will return non-contextual content. It will be empty only when the content is unavailable for this place. This list will have as many entries as the list of places if requested. */
		contextualContents: Option[List[Schema.GoogleMapsPlacesV1ContextualContent]] = None,
	  /** A link allows the user to search with the same text query as specified in the request on Google Maps. */
		searchUri: Option[String] = None,
	  /** A list of places that meet the user's text search criteria. */
		places: Option[List[Schema.GoogleMapsPlacesV1Place]] = None,
	  /** A token that can be sent as `page_token` to retrieve the next page. If this field is omitted or empty, there are no subsequent pages. */
		nextPageToken: Option[String] = None
	)
	
	case class GoogleMapsPlacesV1PlaceContainingPlace(
	  /** The place id of the place in which this place is located. */
		id: Option[String] = None,
	  /** The resource name of the place in which this place is located. */
		name: Option[String] = None
	)
	
	case class GoogleMapsPlacesV1PlaceAccessibilityOptions(
	  /** Place has wheelchair accessible restroom. */
		wheelchairAccessibleRestroom: Option[Boolean] = None,
	  /** Places has wheelchair accessible entrance. */
		wheelchairAccessibleEntrance: Option[Boolean] = None,
	  /** Place offers wheelchair accessible parking. */
		wheelchairAccessibleParking: Option[Boolean] = None,
	  /** Place has wheelchair accessible seating. */
		wheelchairAccessibleSeating: Option[Boolean] = None
	)
	
	case class GoogleMapsPlacesV1PlaceOpeningHoursPeriod(
	  /** The time that the place starts to be closed. */
		close: Option[Schema.GoogleMapsPlacesV1PlaceOpeningHoursPeriodPoint] = None,
	  /** The time that the place starts to be open. */
		open: Option[Schema.GoogleMapsPlacesV1PlaceOpeningHoursPeriodPoint] = None
	)
	
	case class GoogleMapsPlacesV1PlaceGoogleMapsLinks(
	  /** A link to show the directions to the place. The link only populates the destination location and uses the default travel mode `DRIVE`. */
		directionsUri: Option[String] = None,
	  /** A link to show photos of this place. This link is currently not supported on Google Maps Mobile and only works on the web version of Google Maps. */
		photosUri: Option[String] = None,
	  /** A link to write a review for this place. This link is currently not supported on Google Maps Mobile and only works on the web version of Google Maps. */
		writeAReviewUri: Option[String] = None,
	  /** A link to show this place. */
		placeUri: Option[String] = None,
	  /** A link to show reviews of this place. This link is currently not supported on Google Maps Mobile and only works on the web version of Google Maps. */
		reviewsUri: Option[String] = None
	)
	
	case class GoogleMapsPlacesV1SearchNearbyResponse(
	  /** A list of places that meets user's requirements like places types, number of places and specific location restriction. */
		places: Option[List[Schema.GoogleMapsPlacesV1Place]] = None,
	  /** A list of routing summaries where each entry associates to the corresponding place in the same index in the `places` field. If the routing summary is not available for one of the places, it will contain an empty entry. This list should have as many entries as the list of places if requested. */
		routingSummaries: Option[List[Schema.GoogleMapsPlacesV1RoutingSummary]] = None
	)
	
	case class GoogleMapsPlacesV1AutocompletePlacesResponseSuggestionStringRange(
	  /** Zero-based offset of the last Unicode character (exclusive). */
		endOffset: Option[Int] = None,
	  /** Zero-based offset of the first Unicode character of the string (inclusive). */
		startOffset: Option[Int] = None
	)
	
	case class GoogleMapsPlacesV1SearchTextRequestLocationBias(
	  /** A circle defined by center point and radius. */
		circle: Option[Schema.GoogleMapsPlacesV1Circle] = None,
	  /** A rectangle box defined by northeast and southwest corner. `rectangle.high()` must be the northeast point of the rectangle viewport. `rectangle.low()` must be the southwest point of the rectangle viewport. `rectangle.low().latitude()` cannot be greater than `rectangle.high().latitude()`. This will result in an empty latitude range. A rectangle viewport cannot be wider than 180 degrees. */
		rectangle: Option[Schema.GoogleGeoTypeViewport] = None
	)
	
	case class GoogleMapsPlacesV1Polyline(
	  /** An [encoded polyline](https://developers.google.com/maps/documentation/utilities/polylinealgorithm), as returned by the [Routes API by default](https://developers.google.com/maps/documentation/routes/reference/rest/v2/TopLevel/computeRoutes#polylineencoding). See the [encoder](https://developers.google.com/maps/documentation/utilities/polylineutility) and [decoder](https://developers.google.com/maps/documentation/routes/polylinedecoder) tools. */
		encodedPolyline: Option[String] = None
	)
	
	case class GoogleMapsPlacesV1AuthorAttribution(
	  /** Profile photo URI of the author of the Photo or Review. */
		photoUri: Option[String] = None,
	  /** Name of the author of the Photo or Review. */
		displayName: Option[String] = None,
	  /** URI of the author of the Photo or Review. */
		uri: Option[String] = None
	)
	
	object GoogleMapsPlacesV1Place {
		enum BusinessStatusEnum extends Enum[BusinessStatusEnum] { case BUSINESS_STATUS_UNSPECIFIED, OPERATIONAL, CLOSED_TEMPORARILY, CLOSED_PERMANENTLY }
		enum PriceLevelEnum extends Enum[PriceLevelEnum] { case PRICE_LEVEL_UNSPECIFIED, PRICE_LEVEL_FREE, PRICE_LEVEL_INEXPENSIVE, PRICE_LEVEL_MODERATE, PRICE_LEVEL_EXPENSIVE, PRICE_LEVEL_VERY_EXPENSIVE }
	}
	case class GoogleMapsPlacesV1Place(
	  /** The authoritative website for this place, e.g. a business' homepage. Note that for places that are part of a chain (e.g. an IKEA store), this will usually be the website for the individual store, not the overall chain. */
		websiteUri: Option[String] = None,
	  /** A list of sub destinations related to the place. */
		subDestinations: Option[List[Schema.GoogleMapsPlacesV1PlaceSubDestination]] = None,
	  /** Place allows dogs. */
		allowsDogs: Option[Boolean] = None,
	  /** The most recent information about fuel options in a gas station. This information is updated regularly. */
		fuelOptions: Option[Schema.GoogleMapsPlacesV1FuelOptions] = None,
	  /** Specifies if the place serves brunch. */
		servesBrunch: Option[Boolean] = None,
	  /** Payment options the place accepts. If a payment option data is not available, the payment option field will be unset. */
		paymentOptions: Option[Schema.GoogleMapsPlacesV1PlacePaymentOptions] = None,
	  /** Place serves cocktails. */
		servesCocktails: Option[Boolean] = None,
	  /** Place has restroom. */
		restroom: Option[Boolean] = None,
	  /** Place provides live music. */
		liveMusic: Option[Boolean] = None,
	  /** Plus code of the place location lat/long. */
		plusCode: Option[Schema.GoogleMapsPlacesV1PlacePlusCode] = None,
	  /** The place's address in adr microformat: http://microformats.org/wiki/adr. */
		adrFormatAddress: Option[String] = None,
	  /** Links to trigger different Google Maps actions. */
		googleMapsLinks: Option[Schema.GoogleMapsPlacesV1PlaceGoogleMapsLinks] = None,
	  /** Information about the accessibility options a place offers. */
		accessibilityOptions: Option[Schema.GoogleMapsPlacesV1PlaceAccessibilityOptions] = None,
	  /** A set of data provider that must be shown with this result. */
		attributions: Option[List[Schema.GoogleMapsPlacesV1PlaceAttribution]] = None,
	  /** Specifies if the place serves dinner. */
		servesDinner: Option[Boolean] = None,
	  /** The unique identifier of a place. */
		id: Option[String] = None,
	  /** A short, human-readable address for this place. */
		shortFormattedAddress: Option[String] = None,
	  /** Information (including references) about photos of this place. A maximum of 10 photos can be returned. */
		photos: Option[List[Schema.GoogleMapsPlacesV1Photo]] = None,
	  /** Place has a children's menu. */
		menuForChildren: Option[Boolean] = None,
	  /** The position of this place. */
		location: Option[Schema.GoogleTypeLatLng] = None,
	  /** Place serves dessert. */
		servesDessert: Option[Boolean] = None,
	  /** The business status for the place. */
		businessStatus: Option[Schema.GoogleMapsPlacesV1Place.BusinessStatusEnum] = None,
	  /** A set of type tags for this result. For example, "political" and "locality". For the complete list of possible values, see Table A and Table B at https://developers.google.com/maps/documentation/places/web-service/place-types */
		types: Option[List[String]] = None,
	  /** Specifies if the business supports curbside pickup. */
		curbsidePickup: Option[Boolean] = None,
	  /** Place provides outdoor seating. */
		outdoorSeating: Option[Boolean] = None,
	  /** The total number of reviews (with or without text) for this place. */
		userRatingCount: Option[Int] = None,
	  /** The display name of the primary type, localized to the request language if applicable. For the complete list of possible values, see Table A and Table B at https://developers.google.com/maps/documentation/places/web-service/place-types */
		primaryTypeDisplayName: Option[Schema.GoogleTypeLocalizedText] = None,
	  /** The localized name of the place, suitable as a short human-readable description. For example, "Google Sydney", "Starbucks", "Pyrmont", etc. */
		displayName: Option[Schema.GoogleTypeLocalizedText] = None,
	  /** Place is good for children. */
		goodForChildren: Option[Boolean] = None,
	  /** Specifies if the business supports indoor or outdoor seating options. */
		dineIn: Option[Boolean] = None,
	  /** Specifies if the business supports delivery. */
		delivery: Option[Boolean] = None,
	  /** A viewport suitable for displaying the place on an average-sized map. This viewport should not be used as the physical boundary or the service area of the business. */
		viewport: Option[Schema.GoogleGeoTypeViewport] = None,
	  /** Place is suitable for watching sports. */
		goodForWatchingSports: Option[Boolean] = None,
	  /** Repeated components for each locality level. Note the following facts about the address_components[] array: - The array of address components may contain more components than the formatted_address. - The array does not necessarily include all the political entities that contain an address, apart from those included in the formatted_address. To retrieve all the political entities that contain a specific address, you should use reverse geocoding, passing the latitude/longitude of the address as a parameter to the request. - The format of the response is not guaranteed to remain the same between requests. In particular, the number of address_components varies based on the address requested and can change over time for the same address. A component can change position in the array. The type of the component can change. A particular component may be missing in a later response. */
		addressComponents: Option[List[Schema.GoogleMapsPlacesV1PlaceAddressComponent]] = None,
	  /** Specifies if the place supports reservations. */
		reservable: Option[Boolean] = None,
	  /** Specifies if the place serves wine. */
		servesWine: Option[Boolean] = None,
	  /** List of places in which the current place is located. */
		containingPlaces: Option[List[Schema.GoogleMapsPlacesV1PlaceContainingPlace]] = None,
	  /** Experimental: See https://developers.google.com/maps/documentation/places/web-service/experimental/places-generative for more details. AI-generated summary of the place. */
		generativeSummary: Option[Schema.GoogleMapsPlacesV1PlaceGenerativeSummary] = None,
	  /** The hours of operation for the next seven days (including today). The time period starts at midnight on the date of the request and ends at 11:59 pm six days later. This field includes the special_days subfield of all hours, set for dates that have exceptional hours. */
		currentOpeningHours: Option[Schema.GoogleMapsPlacesV1PlaceOpeningHours] = None,
	  /** Specifies if the place serves vegetarian food. */
		servesVegetarianFood: Option[Boolean] = None,
	  /** Contains a summary of the place. A summary is comprised of a textual overview, and also includes the language code for these if applicable. Summary text must be presented as-is and can not be modified or altered. */
		editorialSummary: Option[Schema.GoogleTypeLocalizedText] = None,
	  /** A full, human-readable address for this place. */
		formattedAddress: Option[String] = None,
	  /** The address descriptor of the place. Address descriptors include additional information that help describe a location using landmarks and areas. See address descriptor regional coverage in https://developers.google.com/maps/documentation/geocoding/address-descriptors/coverage. */
		addressDescriptor: Option[Schema.GoogleMapsPlacesV1AddressDescriptor] = None,
	  /** Specifies if the place serves beer. */
		servesBeer: Option[Boolean] = None,
	  /** The regular hours of operation. */
		regularOpeningHours: Option[Schema.GoogleMapsPlacesV1PlaceOpeningHours] = None,
	  /** A rating between 1.0 and 5.0, based on user reviews of this place. */
		rating: Option[BigDecimal] = None,
	  /** The primary type of the given result. This type must one of the Places API supported types. For example, "restaurant", "cafe", "airport", etc. A place can only have a single primary type. For the complete list of possible values, see Table A and Table B at https://developers.google.com/maps/documentation/places/web-service/place-types */
		primaryType: Option[String] = None,
	  /** The price range associated with a Place. */
		priceRange: Option[Schema.GoogleMapsPlacesV1PriceRange] = None,
	  /** Indicates whether the place is a pure service area business. Pure service area business is a business that visits or delivers to customers directly but does not serve customers at their business address. For example, businesses like cleaning services or plumbers. Those businesses may not have a physical address or location on Google Maps. */
		pureServiceAreaBusiness: Option[Boolean] = None,
	  /** Options of parking provided by the place. */
		parkingOptions: Option[Schema.GoogleMapsPlacesV1PlaceParkingOptions] = None,
	  /** List of reviews about this place, sorted by relevance. A maximum of 5 reviews can be returned. */
		reviews: Option[List[Schema.GoogleMapsPlacesV1Review]] = None,
	  /** Specifies if the place serves lunch. */
		servesLunch: Option[Boolean] = None,
	  /** Specifies if the place serves breakfast. */
		servesBreakfast: Option[Boolean] = None,
	  /** A human-readable phone number for the place, in international format. */
		internationalPhoneNumber: Option[String] = None,
	  /** Number of minutes this place's timezone is currently offset from UTC. This is expressed in minutes to support timezones that are offset by fractions of an hour, e.g. X hours and 15 minutes. */
		utcOffsetMinutes: Option[Int] = None,
	  /** Specifies if the business supports takeout. */
		takeout: Option[Boolean] = None,
	  /** Contains an array of entries for information about regular secondary hours of a business. Secondary hours are different from a business's main hours. For example, a restaurant can specify drive through hours or delivery hours as its secondary hours. This field populates the type subfield, which draws from a predefined list of opening hours types (such as DRIVE_THROUGH, PICKUP, or TAKEOUT) based on the types of the place. */
		regularSecondaryOpeningHours: Option[List[Schema.GoogleMapsPlacesV1PlaceOpeningHours]] = None,
	  /** A human-readable phone number for the place, in national format. */
		nationalPhoneNumber: Option[String] = None,
	  /** Experimental: See https://developers.google.com/maps/documentation/places/web-service/experimental/places-generative for more details. AI-generated summary of the area that the place is in. */
		areaSummary: Option[Schema.GoogleMapsPlacesV1PlaceAreaSummary] = None,
	  /** A truncated URL to an icon mask. User can access different icon type by appending type suffix to the end (eg, ".svg" or ".png"). */
		iconMaskBaseUri: Option[String] = None,
	  /** Information of ev charging options. */
		evChargeOptions: Option[Schema.GoogleMapsPlacesV1EVChargeOptions] = None,
	  /** Contains an array of entries for the next seven days including information about secondary hours of a business. Secondary hours are different from a business's main hours. For example, a restaurant can specify drive through hours or delivery hours as its secondary hours. This field populates the type subfield, which draws from a predefined list of opening hours types (such as DRIVE_THROUGH, PICKUP, or TAKEOUT) based on the types of the place. This field includes the special_days subfield of all hours, set for dates that have exceptional hours. */
		currentSecondaryOpeningHours: Option[List[Schema.GoogleMapsPlacesV1PlaceOpeningHours]] = None,
	  /** This Place's resource name, in `places/{place_id}` format. Can be used to look up the Place. */
		name: Option[String] = None,
	  /** Price level of the place. */
		priceLevel: Option[Schema.GoogleMapsPlacesV1Place.PriceLevelEnum] = None,
	  /** Background color for icon_mask in hex format, e.g. #909CE1. */
		iconBackgroundColor: Option[String] = None,
	  /** Place accommodates groups. */
		goodForGroups: Option[Boolean] = None,
	  /** Place serves coffee. */
		servesCoffee: Option[Boolean] = None,
	  /** A URL providing more information about this place. */
		googleMapsUri: Option[String] = None
	)
	
	case class GoogleMapsPlacesV1AddressDescriptor(
	  /** A ranked list of nearby landmarks. The most recognizable and nearby landmarks are ranked first. */
		landmarks: Option[List[Schema.GoogleMapsPlacesV1AddressDescriptorLandmark]] = None,
	  /** A ranked list of containing or adjacent areas. The most recognizable and precise areas are ranked first. */
		areas: Option[List[Schema.GoogleMapsPlacesV1AddressDescriptorArea]] = None
	)
	
	case class GoogleMapsPlacesV1PlaceParkingOptions(
	  /** Place offers valet parking. */
		valetParking: Option[Boolean] = None,
	  /** Place offers free garage parking. */
		freeGarageParking: Option[Boolean] = None,
	  /** Place offers paid garage parking. */
		paidGarageParking: Option[Boolean] = None,
	  /** Place offers free parking lots. */
		freeParkingLot: Option[Boolean] = None,
	  /** Place offers free street parking. */
		freeStreetParking: Option[Boolean] = None,
	  /** Place offers paid street parking. */
		paidStreetParking: Option[Boolean] = None,
	  /** Place offers paid parking lots. */
		paidParkingLot: Option[Boolean] = None
	)
	
	object GoogleMapsPlacesV1SearchTextRequest {
		enum RankPreferenceEnum extends Enum[RankPreferenceEnum] { case RANK_PREFERENCE_UNSPECIFIED, DISTANCE, RELEVANCE }
		enum PriceLevelsEnum extends Enum[PriceLevelsEnum] { case PRICE_LEVEL_UNSPECIFIED, PRICE_LEVEL_FREE, PRICE_LEVEL_INEXPENSIVE, PRICE_LEVEL_MODERATE, PRICE_LEVEL_EXPENSIVE, PRICE_LEVEL_VERY_EXPENSIVE }
	}
	case class GoogleMapsPlacesV1SearchTextRequest(
	  /** Optional. Set the searchable EV options of a place search request. */
		evOptions: Option[Schema.GoogleMapsPlacesV1SearchTextRequestEVOptions] = None,
	  /** Optional. Additional parameters for routing to results. */
		routingParameters: Option[Schema.GoogleMapsPlacesV1RoutingParameters] = None,
	  /** The region to search. This location serves as a restriction which means results outside given location will not be returned. Cannot be set along with location_bias. */
		locationRestriction: Option[Schema.GoogleMapsPlacesV1SearchTextRequestLocationRestriction] = None,
	  /** Optional. Additional parameters proto for searching along a route. */
		searchAlongRouteParameters: Option[Schema.GoogleMapsPlacesV1SearchTextRequestSearchAlongRouteParameters] = None,
	  /** Used to set strict type filtering for included_type. If set to true, only results of the same type will be returned. Default to false. */
		strictTypeFiltering: Option[Boolean] = None,
	  /** The Unicode country/region code (CLDR) of the location where the request is coming from. This parameter is used to display the place details, like region-specific place name, if available. The parameter can affect results based on applicable law. For more information, see https://www.unicode.org/cldr/charts/latest/supplemental/territory_language_information.html. Note that 3-digit region codes are not currently supported. */
		regionCode: Option[String] = None,
	  /** Optional. A page token, received from a previous TextSearch call. Provide this to retrieve the subsequent page. When paginating, all parameters other than `page_token`, `page_size`, and `max_result_count` provided to TextSearch must match the initial call that provided the page token. Otherwise an INVALID_ARGUMENT error is returned. */
		pageToken: Option[String] = None,
	  /** Required. The text query for textual search. */
		textQuery: Option[String] = None,
	  /** Filter out results whose average user rating is strictly less than this limit. A valid value must be a float between 0 and 5 (inclusively) at a 0.5 cadence i.e. [0, 0.5, 1.0, ... , 5.0] inclusively. The input rating will round up to the nearest 0.5(ceiling). For instance, a rating of 0.6 will eliminate all results with a less than 1.0 rating. */
		minRating: Option[BigDecimal] = None,
	  /** Optional. The maximum number of results per page that can be returned. If the number of available results is larger than `page_size`, a `next_page_token` is returned which can be passed to `page_token` to get the next page of results in subsequent requests. If 0 or no value is provided, a default of 20 is used. The maximum value is 20; values above 20 will be set to 20. Negative values will return an INVALID_ARGUMENT error. If both `max_result_count` and `page_size` are specified, `max_result_count` will be ignored. */
		pageSize: Option[Int] = None,
	  /** The region to search. This location serves as a bias which means results around given location might be returned. Cannot be set along with location_restriction. */
		locationBias: Option[Schema.GoogleMapsPlacesV1SearchTextRequestLocationBias] = None,
	  /** The requested place type. Full list of types supported: https://developers.google.com/maps/documentation/places/web-service/place-types. Only support one included type. */
		includedType: Option[String] = None,
	  /** Used to restrict the search to places that are currently open. The default is false. */
		openNow: Option[Boolean] = None,
	  /** Deprecated: Use `page_size` instead. The maximum number of results per page that can be returned. If the number of available results is larger than `max_result_count`, a `next_page_token` is returned which can be passed to `page_token` to get the next page of results in subsequent requests. If 0 or no value is provided, a default of 20 is used. The maximum value is 20; values above 20 will be coerced to 20. Negative values will return an INVALID_ARGUMENT error. If both `max_result_count` and `page_size` are specified, `max_result_count` will be ignored. */
		maxResultCount: Option[Int] = None,
	  /** How results will be ranked in the response. */
		rankPreference: Option[Schema.GoogleMapsPlacesV1SearchTextRequest.RankPreferenceEnum] = None,
	  /** Used to restrict the search to places that are marked as certain price levels. Users can choose any combinations of price levels. Default to select all price levels. */
		priceLevels: Option[List[Schema.GoogleMapsPlacesV1SearchTextRequest.PriceLevelsEnum]] = None,
	  /** Place details will be displayed with the preferred language if available. If the language code is unspecified or unrecognized, place details of any language may be returned, with a preference for English if such details exist. Current list of supported languages: https://developers.google.com/maps/faq#languagesupport. */
		languageCode: Option[String] = None,
	  /** Optional. Include pure service area businesses if the field is set to true. Pure service area business is a business that visits or delivers to customers directly but does not serve customers at their business address. For example, businesses like cleaning services or plumbers. Those businesses do not have a physical address or location on Google Maps. Places will not return fields including `location`, `plus_code`, and other location related fields for these businesses. */
		includePureServiceAreaBusinesses: Option[Boolean] = None
	)
	
	case class GoogleMapsPlacesV1ContextualContentJustification(
	  /** Experimental: See https://developers.google.com/maps/documentation/places/web-service/experimental/places-generative for more details. */
		reviewJustification: Option[Schema.GoogleMapsPlacesV1ContextualContentJustificationReviewJustification] = None,
	  /** Experimental: See https://developers.google.com/maps/documentation/places/web-service/experimental/places-generative for more details. */
		businessAvailabilityAttributesJustification: Option[Schema.GoogleMapsPlacesV1ContextualContentJustificationBusinessAvailabilityAttributesJustification] = None
	)
	
	case class GoogleMapsPlacesV1AutocompletePlacesRequest(
	  /** Optional. Include pure service area businesses if the field is set to true. Pure service area business is a business that visits or delivers to customers directly but does not serve customers at their business address. For example, businesses like cleaning services or plumbers. Those businesses do not have a physical address or location on Google Maps. Places will not return fields including `location`, `plus_code`, and other location related fields for these businesses. */
		includePureServiceAreaBusinesses: Option[Boolean] = None,
	  /** Optional. Included primary Place type (for example, "restaurant" or "gas_station") in Place Types (https://developers.google.com/maps/documentation/places/web-service/place-types), or only `(regions)`, or only `(cities)`. A Place is only returned if its primary type is included in this list. Up to 5 values can be specified. If no types are specified, all Place types are returned. */
		includedPrimaryTypes: Option[List[String]] = None,
	  /** Optional. The region code, specified as a CLDR two-character region code. This affects address formatting, result ranking, and may influence what results are returned. This does not restrict results to the specified region. To restrict results to a region, use `region_code_restriction`. */
		regionCode: Option[String] = None,
	  /** Optional. Bias results to a specified location. At most one of `location_bias` or `location_restriction` should be set. If neither are set, the results will be biased by IP address, meaning the IP address will be mapped to an imprecise location and used as a biasing signal. */
		locationBias: Option[Schema.GoogleMapsPlacesV1AutocompletePlacesRequestLocationBias] = None,
	  /** Optional. Restrict results to a specified location. At most one of `location_bias` or `location_restriction` should be set. If neither are set, the results will be biased by IP address, meaning the IP address will be mapped to an imprecise location and used as a biasing signal. */
		locationRestriction: Option[Schema.GoogleMapsPlacesV1AutocompletePlacesRequestLocationRestriction] = None,
	  /** Optional. A zero-based Unicode character offset of `input` indicating the cursor position in `input`. The cursor position may influence what predictions are returned. If empty, defaults to the length of `input`. */
		inputOffset: Option[Int] = None,
	  /** Required. The text string on which to search. */
		input: Option[String] = None,
	  /** Optional. A string which identifies an Autocomplete session for billing purposes. Must be a URL and filename safe base64 string with at most 36 ASCII characters in length. Otherwise an INVALID_ARGUMENT error is returned. The session begins when the user starts typing a query, and concludes when they select a place and a call to Place Details or Address Validation is made. Each session can have multiple queries, followed by one Place Details or Address Validation request. The credentials used for each request within a session must belong to the same Google Cloud Console project. Once a session has concluded, the token is no longer valid; your app must generate a fresh token for each session. If the `session_token` parameter is omitted, or if you reuse a session token, the session is charged as if no session token was provided (each request is billed separately). We recommend the following guidelines: &#42; Use session tokens for all Place Autocomplete calls. &#42; Generate a fresh token for each session. Using a version 4 UUID is recommended. &#42; Ensure that the credentials used for all Place Autocomplete, Place Details, and Address Validation requests within a session belong to the same Cloud Console project. &#42; Be sure to pass a unique session token for each new session. Using the same token for more than one session will result in each request being billed individually. */
		sessionToken: Option[String] = None,
	  /** Optional. The language in which to return results. Defaults to en-US. The results may be in mixed languages if the language used in `input` is different from `language_code` or if the returned Place does not have a translation from the local language to `language_code`. */
		languageCode: Option[String] = None,
	  /** Optional. The origin point from which to calculate geodesic distance to the destination (returned as `distance_meters`). If this value is omitted, geodesic distance will not be returned. */
		origin: Option[Schema.GoogleTypeLatLng] = None,
	  /** Optional. Only include results in the specified regions, specified as up to 15 CLDR two-character region codes. An empty set will not restrict the results. If both `location_restriction` and `included_region_codes` are set, the results will be located in the area of intersection. */
		includedRegionCodes: Option[List[String]] = None,
	  /** Optional. If true, the response will include both Place and query predictions. Otherwise the response will only return Place predictions. */
		includeQueryPredictions: Option[Boolean] = None
	)
	
	case class GoogleMapsPlacesV1RoutingSummaryLeg(
	  /** The distance of this leg of the trip. */
		distanceMeters: Option[Int] = None,
	  /** The time it takes to complete this leg of the trip. */
		duration: Option[String] = None
	)
	
	object GoogleMapsPlacesV1SearchNearbyRequest {
		enum RankPreferenceEnum extends Enum[RankPreferenceEnum] { case RANK_PREFERENCE_UNSPECIFIED, DISTANCE, POPULARITY }
	}
	case class GoogleMapsPlacesV1SearchNearbyRequest(
	  /** Maximum number of results to return. It must be between 1 and 20 (default), inclusively. If the number is unset, it falls back to the upper limit. If the number is set to negative or exceeds the upper limit, an INVALID_ARGUMENT error is returned. */
		maxResultCount: Option[Int] = None,
	  /** The Unicode country/region code (CLDR) of the location where the request is coming from. This parameter is used to display the place details, like region-specific place name, if available. The parameter can affect results based on applicable law. For more information, see https://www.unicode.org/cldr/charts/latest/supplemental/territory_language_information.html. Note that 3-digit region codes are not currently supported. */
		regionCode: Option[String] = None,
	  /** Excluded primary Place type (e.g. "restaurant" or "gas_station") from https://developers.google.com/maps/documentation/places/web-service/place-types. Up to 50 types from [Table A](https://developers.google.com/maps/documentation/places/web-service/place-types#table-a) may be specified. If there are any conflicting primary types, i.e. a type appears in both included_primary_types and excluded_primary_types, an INVALID_ARGUMENT error is returned. If a Place type is specified with multiple type restrictions, only places that satisfy all of the restrictions are returned. For example, if we have {included_types = ["restaurant"], excluded_primary_types = ["restaurant"]}, the returned places provide "restaurant" related services but do not operate primarily as "restaurants". */
		excludedPrimaryTypes: Option[List[String]] = None,
	  /** How results will be ranked in the response. */
		rankPreference: Option[Schema.GoogleMapsPlacesV1SearchNearbyRequest.RankPreferenceEnum] = None,
	  /** Excluded Place type (eg, "restaurant" or "gas_station") from https://developers.google.com/maps/documentation/places/web-service/place-types. Up to 50 types from [Table A](https://developers.google.com/maps/documentation/places/web-service/place-types#table-a) may be specified. If the client provides both included_types (e.g. restaurant) and excluded_types (e.g. cafe), then the response should include places that are restaurant but not cafe. The response includes places that match at least one of the included_types and none of the excluded_types. If there are any conflicting types, i.e. a type appears in both included_types and excluded_types, an INVALID_ARGUMENT error is returned. If a Place type is specified with multiple type restrictions, only places that satisfy all of the restrictions are returned. For example, if we have {included_types = ["restaurant"], excluded_primary_types = ["restaurant"]}, the returned places provide "restaurant" related services but do not operate primarily as "restaurants". */
		excludedTypes: Option[List[String]] = None,
	  /** Included primary Place type (e.g. "restaurant" or "gas_station") from https://developers.google.com/maps/documentation/places/web-service/place-types. A place can only have a single primary type from the supported types table associated with it. Up to 50 types from [Table A](https://developers.google.com/maps/documentation/places/web-service/place-types#table-a) may be specified. If there are any conflicting primary types, i.e. a type appears in both included_primary_types and excluded_primary_types, an INVALID_ARGUMENT error is returned. If a Place type is specified with multiple type restrictions, only places that satisfy all of the restrictions are returned. For example, if we have {included_types = ["restaurant"], excluded_primary_types = ["restaurant"]}, the returned places provide "restaurant" related services but do not operate primarily as "restaurants". */
		includedPrimaryTypes: Option[List[String]] = None,
	  /** Included Place type (eg, "restaurant" or "gas_station") from https://developers.google.com/maps/documentation/places/web-service/place-types. Up to 50 types from [Table A](https://developers.google.com/maps/documentation/places/web-service/place-types#table-a) may be specified. If there are any conflicting types, i.e. a type appears in both included_types and excluded_types, an INVALID_ARGUMENT error is returned. If a Place type is specified with multiple type restrictions, only places that satisfy all of the restrictions are returned. For example, if we have {included_types = ["restaurant"], excluded_primary_types = ["restaurant"]}, the returned places provide "restaurant" related services but do not operate primarily as "restaurants". */
		includedTypes: Option[List[String]] = None,
	  /** Place details will be displayed with the preferred language if available. If the language code is unspecified or unrecognized, place details of any language may be returned, with a preference for English if such details exist. Current list of supported languages: https://developers.google.com/maps/faq#languagesupport. */
		languageCode: Option[String] = None,
	  /** Required. The region to search. */
		locationRestriction: Option[Schema.GoogleMapsPlacesV1SearchNearbyRequestLocationRestriction] = None,
	  /** Optional. Parameters that affect the routing to the search results. */
		routingParameters: Option[Schema.GoogleMapsPlacesV1RoutingParameters] = None
	)
	
	case class GoogleMapsPlacesV1AutocompletePlacesResponse(
	  /** Contains a list of suggestions, ordered in descending order of relevance. */
		suggestions: Option[List[Schema.GoogleMapsPlacesV1AutocompletePlacesResponseSuggestion]] = None
	)
	
	case class GoogleMapsPlacesV1EVChargeOptions(
	  /** A list of EV charging connector aggregations that contain connectors of the same type and same charge rate. */
		connectorAggregation: Option[List[Schema.GoogleMapsPlacesV1EVChargeOptionsConnectorAggregation]] = None,
	  /** Number of connectors at this station. However, because some ports can have multiple connectors but only be able to charge one car at a time (e.g.) the number of connectors may be greater than the total number of cars which can charge simultaneously. */
		connectorCount: Option[Int] = None
	)
	
	case class GoogleMapsPlacesV1PlaceAreaSummary(
	  /** A link where users can flag a problem with the summary. */
		flagContentUri: Option[String] = None,
	  /** Content blocks that compose the area summary. Each block has a separate topic about the area. */
		contentBlocks: Option[List[Schema.GoogleMapsPlacesV1ContentBlock]] = None
	)
	
	object GoogleMapsPlacesV1AddressDescriptorArea {
		enum ContainmentEnum extends Enum[ContainmentEnum] { case CONTAINMENT_UNSPECIFIED, WITHIN, OUTSKIRTS, NEAR }
	}
	case class GoogleMapsPlacesV1AddressDescriptorArea(
	  /** Defines the spatial relationship between the target location and the area. */
		containment: Option[Schema.GoogleMapsPlacesV1AddressDescriptorArea.ContainmentEnum] = None,
	  /** The area's display name. */
		displayName: Option[Schema.GoogleTypeLocalizedText] = None,
	  /** The area's place id. */
		placeId: Option[String] = None,
	  /** The area's resource name. */
		name: Option[String] = None
	)
	
	case class GoogleMapsPlacesV1References(
	  /** The list of resource names of the referenced places. This name can be used in other APIs that accept Place resource names. */
		places: Option[List[String]] = None,
	  /** Reviews that serve as references. */
		reviews: Option[List[Schema.GoogleMapsPlacesV1Review]] = None
	)
	
	case class GoogleMapsPlacesV1AutocompletePlacesResponseSuggestionFormattableText(
	  /** A list of string ranges identifying where the input request matched in `text`. The ranges can be used to format specific parts of `text`. The substrings may not be exact matches of `input` if the matching was determined by criteria other than string matching (for example, spell corrections or transliterations). These values are Unicode character offsets of `text`. The ranges are guaranteed to be ordered in increasing offset values. */
		matches: Option[List[Schema.GoogleMapsPlacesV1AutocompletePlacesResponseSuggestionStringRange]] = None,
	  /** Text that may be used as is or formatted with `matches`. */
		text: Option[String] = None
	)
	
	case class GoogleMapsPlacesV1FuelOptions(
	  /** The last known fuel price for each type of fuel this station has. There is one entry per fuel type this station has. Order is not important. */
		fuelPrices: Option[List[Schema.GoogleMapsPlacesV1FuelOptionsFuelPrice]] = None
	)
	
	case class GoogleMapsPlacesV1AutocompletePlacesResponseSuggestionPlacePrediction(
	  /** Contains the human-readable name for the returned result. For establishment results, this is usually the business name and address. `text` is recommended for developers who wish to show a single UI element. Developers who wish to show two separate, but related, UI elements may want to use `structured_format` instead. They are two different ways to represent a Place prediction. Users should not try to parse `structured_format` into `text` or vice versa. This text may be different from the `display_name` returned by GetPlace. May be in mixed languages if the request `input` and `language_code` are in different languages or if the Place does not have a translation from the local language to `language_code`. */
		text: Option[Schema.GoogleMapsPlacesV1AutocompletePlacesResponseSuggestionFormattableText] = None,
	  /** The resource name of the suggested Place. This name can be used in other APIs that accept Place names. */
		place: Option[String] = None,
	  /** List of types that apply to this Place from Table A or Table B in https://developers.google.com/maps/documentation/places/web-service/place-types. A type is a categorization of a Place. Places with shared types will share similar characteristics. */
		types: Option[List[String]] = None,
	  /** The length of the geodesic in meters from `origin` if `origin` is specified. Certain predictions such as routes may not populate this field. */
		distanceMeters: Option[Int] = None,
	  /** A breakdown of the Place prediction into main text containing the name of the Place and secondary text containing additional disambiguating features (such as a city or region). `structured_format` is recommended for developers who wish to show two separate, but related, UI elements. Developers who wish to show a single UI element may want to use `text` instead. They are two different ways to represent a Place prediction. Users should not try to parse `structured_format` into `text` or vice versa. */
		structuredFormat: Option[Schema.GoogleMapsPlacesV1AutocompletePlacesResponseSuggestionStructuredFormat] = None,
	  /** The unique identifier of the suggested Place. This identifier can be used in other APIs that accept Place IDs. */
		placeId: Option[String] = None
	)
	
	case class GoogleMapsPlacesV1ContentBlock(
	  /** Content related to the topic. */
		content: Option[Schema.GoogleTypeLocalizedText] = None,
	  /** The topic of the content, for example "overview" or "restaurant". */
		topic: Option[String] = None,
	  /** Experimental: See https://developers.google.com/maps/documentation/places/web-service/experimental/places-generative for more details. References that are related to this block of content. */
		references: Option[Schema.GoogleMapsPlacesV1References] = None
	)
	
	case class GoogleMapsPlacesV1ContextualContentJustificationReviewJustificationHighlightedTextHighlightedTextRange(
		startIndex: Option[Int] = None,
		endIndex: Option[Int] = None
	)
	
	case class GoogleMapsPlacesV1AutocompletePlacesRequestLocationRestriction(
	  /** A viewport defined by a northeast and a southwest corner. */
		rectangle: Option[Schema.GoogleGeoTypeViewport] = None,
	  /** A circle defined by a center point and radius. */
		circle: Option[Schema.GoogleMapsPlacesV1Circle] = None
	)
	
	case class GoogleMapsPlacesV1AutocompletePlacesRequestLocationBias(
	  /** A circle defined by a center point and radius. */
		circle: Option[Schema.GoogleMapsPlacesV1Circle] = None,
	  /** A viewport defined by a northeast and a southwest corner. */
		rectangle: Option[Schema.GoogleGeoTypeViewport] = None
	)
	
	case class GoogleTypeLatLng(
	  /** The longitude in degrees. It must be in the range [-180.0, +180.0]. */
		longitude: Option[BigDecimal] = None,
	  /** The latitude in degrees. It must be in the range [-90.0, +90.0]. */
		latitude: Option[BigDecimal] = None
	)
	
	case class GoogleTypeDate(
	  /** Day of a month. Must be from 1 to 31 and valid for the year and month, or 0 to specify a year by itself or a year and month where the day isn't significant. */
		day: Option[Int] = None,
	  /** Month of a year. Must be from 1 to 12, or 0 to specify a year without a month and day. */
		month: Option[Int] = None,
	  /** Year of the date. Must be from 1 to 9999, or 0 to specify a date without a year. */
		year: Option[Int] = None
	)
	
	case class GoogleMapsPlacesV1AutocompletePlacesResponseSuggestion(
	  /** A prediction for a Place. */
		placePrediction: Option[Schema.GoogleMapsPlacesV1AutocompletePlacesResponseSuggestionPlacePrediction] = None,
	  /** A prediction for a query. */
		queryPrediction: Option[Schema.GoogleMapsPlacesV1AutocompletePlacesResponseSuggestionQueryPrediction] = None
	)
	
	object GoogleMapsPlacesV1SearchTextRequestEVOptions {
		enum ConnectorTypesEnum extends Enum[ConnectorTypesEnum] { case EV_CONNECTOR_TYPE_UNSPECIFIED, EV_CONNECTOR_TYPE_OTHER, EV_CONNECTOR_TYPE_J1772, EV_CONNECTOR_TYPE_TYPE_2, EV_CONNECTOR_TYPE_CHADEMO, EV_CONNECTOR_TYPE_CCS_COMBO_1, EV_CONNECTOR_TYPE_CCS_COMBO_2, EV_CONNECTOR_TYPE_TESLA, EV_CONNECTOR_TYPE_UNSPECIFIED_GB_T, EV_CONNECTOR_TYPE_UNSPECIFIED_WALL_OUTLET }
	}
	case class GoogleMapsPlacesV1SearchTextRequestEVOptions(
	  /** Optional. Minimum required charging rate in kilowatts. A place with a charging rate less than the specified rate is filtered out. */
		minimumChargingRateKw: Option[BigDecimal] = None,
	  /** Optional. The list of preferred EV connector types. A place that does not support any of the listed connector types is filtered out. */
		connectorTypes: Option[List[Schema.GoogleMapsPlacesV1SearchTextRequestEVOptions.ConnectorTypesEnum]] = None
	)
	
	case class GoogleMapsPlacesV1PlaceAttribution(
	  /** URI to the Place's data provider. */
		providerUri: Option[String] = None,
	  /** Name of the Place's data provider. */
		provider: Option[String] = None
	)
	
	object GoogleMapsPlacesV1EVChargeOptionsConnectorAggregation {
		enum TypeEnum extends Enum[TypeEnum] { case EV_CONNECTOR_TYPE_UNSPECIFIED, EV_CONNECTOR_TYPE_OTHER, EV_CONNECTOR_TYPE_J1772, EV_CONNECTOR_TYPE_TYPE_2, EV_CONNECTOR_TYPE_CHADEMO, EV_CONNECTOR_TYPE_CCS_COMBO_1, EV_CONNECTOR_TYPE_CCS_COMBO_2, EV_CONNECTOR_TYPE_TESLA, EV_CONNECTOR_TYPE_UNSPECIFIED_GB_T, EV_CONNECTOR_TYPE_UNSPECIFIED_WALL_OUTLET }
	}
	case class GoogleMapsPlacesV1EVChargeOptionsConnectorAggregation(
	  /** The static max charging rate in kw of each connector in the aggregation. */
		maxChargeRateKw: Option[BigDecimal] = None,
	  /** Number of connectors in this aggregation that are currently available. */
		availableCount: Option[Int] = None,
	  /** Number of connectors in this aggregation. */
		count: Option[Int] = None,
	  /** Number of connectors in this aggregation that are currently out of service. */
		outOfServiceCount: Option[Int] = None,
	  /** The connector type of this aggregation. */
		`type`: Option[Schema.GoogleMapsPlacesV1EVChargeOptionsConnectorAggregation.TypeEnum] = None,
	  /** The timestamp when the connector availability information in this aggregation was last updated. */
		availabilityLastUpdateTime: Option[String] = None
	)
	
	case class GoogleGeoTypeViewport(
	  /** Required. The low point of the viewport. */
		low: Option[Schema.GoogleTypeLatLng] = None,
	  /** Required. The high point of the viewport. */
		high: Option[Schema.GoogleTypeLatLng] = None
	)
	
	case class GoogleMapsPlacesV1ContextualContent(
	  /** Experimental: See https://developers.google.com/maps/documentation/places/web-service/experimental/places-generative for more details. Justifications for the place. */
		justifications: Option[List[Schema.GoogleMapsPlacesV1ContextualContentJustification]] = None,
	  /** Information (including references) about photos of this place, contexual to the place query. */
		photos: Option[List[Schema.GoogleMapsPlacesV1Photo]] = None,
	  /** List of reviews about this place, contexual to the place query. */
		reviews: Option[List[Schema.GoogleMapsPlacesV1Review]] = None
	)
	
	case class GoogleMapsPlacesV1PlaceOpeningHoursPeriodPoint(
	  /** Whether or not this endpoint was truncated. Truncation occurs when the real hours are outside the times we are willing to return hours between, so we truncate the hours back to these boundaries. This ensures that at most 24 &#42; 7 hours from midnight of the day of the request are returned. */
		truncated: Option[Boolean] = None,
	  /** A day of the week, as an integer in the range 0-6. 0 is Sunday, 1 is Monday, etc. */
		day: Option[Int] = None,
	  /** The minute in 2 digits. Ranges from 00 to 59. */
		minute: Option[Int] = None,
	  /** Date in the local timezone for the place. */
		date: Option[Schema.GoogleTypeDate] = None,
	  /** The hour in 2 digits. Ranges from 00 to 23. */
		hour: Option[Int] = None
	)
	
	case class GoogleMapsPlacesV1Review(
	  /** A link to show the review on Google Maps. */
		googleMapsUri: Option[String] = None,
	  /** A string of formatted recent time, expressing the review time relative to the current time in a form appropriate for the language and country. */
		relativePublishTimeDescription: Option[String] = None,
	  /** The review text in its original language. */
		originalText: Option[Schema.GoogleTypeLocalizedText] = None,
	  /** A link where users can flag a problem with the review. */
		flagContentUri: Option[String] = None,
	  /** A number between 1.0 and 5.0, also called the number of stars. */
		rating: Option[BigDecimal] = None,
	  /** Timestamp for the review. */
		publishTime: Option[String] = None,
	  /** This review's author. */
		authorAttribution: Option[Schema.GoogleMapsPlacesV1AuthorAttribution] = None,
	  /** A reference representing this place review which may be used to look up this place review again (also called the API "resource" name: `places/{place_id}/reviews/{review}`). */
		name: Option[String] = None,
	  /** The localized text of the review. */
		text: Option[Schema.GoogleTypeLocalizedText] = None
	)
	
	object GoogleMapsPlacesV1FuelOptionsFuelPrice {
		enum TypeEnum extends Enum[TypeEnum] { case FUEL_TYPE_UNSPECIFIED, DIESEL, REGULAR_UNLEADED, MIDGRADE, PREMIUM, SP91, SP91_E10, SP92, SP95, SP95_E10, SP98, SP99, SP100, LPG, E80, E85, METHANE, BIO_DIESEL, TRUCK_DIESEL }
	}
	case class GoogleMapsPlacesV1FuelOptionsFuelPrice(
	  /** The type of fuel. */
		`type`: Option[Schema.GoogleMapsPlacesV1FuelOptionsFuelPrice.TypeEnum] = None,
	  /** The price of the fuel. */
		price: Option[Schema.GoogleTypeMoney] = None,
	  /** The time the fuel price was last updated. */
		updateTime: Option[String] = None
	)
	
	case class GoogleMapsPlacesV1Circle(
	  /** Required. Radius measured in meters. The radius must be within [0.0, 50000.0]. */
		radius: Option[BigDecimal] = None,
	  /** Required. Center latitude and longitude. The range of latitude must be within [-90.0, 90.0]. The range of the longitude must be within [-180.0, 180.0]. */
		center: Option[Schema.GoogleTypeLatLng] = None
	)
	
	case class GoogleMapsPlacesV1SearchTextRequestLocationRestriction(
	  /** A rectangle box defined by northeast and southwest corner. `rectangle.high()` must be the northeast point of the rectangle viewport. `rectangle.low()` must be the southwest point of the rectangle viewport. `rectangle.low().latitude()` cannot be greater than `rectangle.high().latitude()`. This will result in an empty latitude range. A rectangle viewport cannot be wider than 180 degrees. */
		rectangle: Option[Schema.GoogleGeoTypeViewport] = None
	)
	
	case class GoogleTypeLocalizedText(
	  /** The text's BCP-47 language code, such as "en-US" or "sr-Latn". For more information, see http://www.unicode.org/reports/tr35/#Unicode_locale_identifier. */
		languageCode: Option[String] = None,
	  /** Localized string in the language corresponding to language_code below. */
		text: Option[String] = None
	)
	
	case class GoogleMapsPlacesV1RoutingSummary(
	  /** A link to show directions on Google Maps using the waypoints from the given routing summary. The route generated by this link is not guaranteed to be the same as the route used to generate the routing summary. The link uses information provided in the request, from fields including `routingParameters` and `searchAlongRouteParameters` when applicable, to generate the directions link. */
		directionsUri: Option[String] = None,
	  /** The legs of the trip. When you calculate travel duration and distance from a set origin, `legs` contains a single leg containing the duration and distance from the origin to the destination. When you do a search along route, `legs` contains two legs: one from the origin to place, and one from the place to the destination. */
		legs: Option[List[Schema.GoogleMapsPlacesV1RoutingSummaryLeg]] = None
	)
	
	case class GoogleMapsPlacesV1RouteModifiers(
	  /** Optional. When set to true, avoids navigating indoors where reasonable, giving preference to routes not containing indoor navigation. Applies only to the `WALK` `TravelMode`. */
		avoidIndoor: Option[Boolean] = None,
	  /** Optional. When set to true, avoids toll roads where reasonable, giving preference to routes not containing toll roads. Applies only to the `DRIVE` and `TWO_WHEELER` `TravelMode`. */
		avoidTolls: Option[Boolean] = None,
	  /** Optional. When set to true, avoids ferries where reasonable, giving preference to routes not containing ferries. Applies only to the `DRIVE` and `TWO_WHEELER` `TravelMode`. */
		avoidFerries: Option[Boolean] = None,
	  /** Optional. When set to true, avoids highways where reasonable, giving preference to routes not containing highways. Applies only to the `DRIVE` and `TWO_WHEELER` `TravelMode`. */
		avoidHighways: Option[Boolean] = None
	)
	
	object GoogleMapsPlacesV1PlaceOpeningHours {
		enum SecondaryHoursTypeEnum extends Enum[SecondaryHoursTypeEnum] { case SECONDARY_HOURS_TYPE_UNSPECIFIED, DRIVE_THROUGH, HAPPY_HOUR, DELIVERY, TAKEOUT, KITCHEN, BREAKFAST, LUNCH, DINNER, BRUNCH, PICKUP, ACCESS, SENIOR_HOURS, ONLINE_SERVICE_HOURS }
	}
	case class GoogleMapsPlacesV1PlaceOpeningHours(
	  /** The next time the current opening hours period ends up to 7 days in the future. This field is only populated if the opening hours period is active at the time of serving the request. */
		nextCloseTime: Option[String] = None,
	  /** Structured information for special days that fall within the period that the returned opening hours cover. Special days are days that could impact the business hours of a place, e.g. Christmas day. Set for current_opening_hours and current_secondary_opening_hours if there are exceptional hours. */
		specialDays: Option[List[Schema.GoogleMapsPlacesV1PlaceOpeningHoursSpecialDay]] = None,
	  /** The periods that this place is open during the week. The periods are in chronological order, starting with Sunday in the place-local timezone. An empty (but not absent) value indicates a place that is never open, e.g. because it is closed temporarily for renovations. */
		periods: Option[List[Schema.GoogleMapsPlacesV1PlaceOpeningHoursPeriod]] = None,
	  /** The next time the current opening hours period starts up to 7 days in the future. This field is only populated if the opening hours period is not active at the time of serving the request. */
		nextOpenTime: Option[String] = None,
	  /** Whether the opening hours period is currently active. For regular opening hours and current opening hours, this field means whether the place is open. For secondary opening hours and current secondary opening hours, this field means whether the secondary hours of this place is active. */
		openNow: Option[Boolean] = None,
	  /** A type string used to identify the type of secondary hours. */
		secondaryHoursType: Option[Schema.GoogleMapsPlacesV1PlaceOpeningHours.SecondaryHoursTypeEnum] = None,
	  /** Localized strings describing the opening hours of this place, one string for each day of the week. Will be empty if the hours are unknown or could not be converted to localized text. Example: "Sun: 18:00–06:00" */
		weekdayDescriptions: Option[List[String]] = None
	)
	
	object GoogleMapsPlacesV1AddressDescriptorLandmark {
		enum SpatialRelationshipEnum extends Enum[SpatialRelationshipEnum] { case NEAR, WITHIN, BESIDE, ACROSS_THE_ROAD, DOWN_THE_ROAD, AROUND_THE_CORNER, BEHIND }
	}
	case class GoogleMapsPlacesV1AddressDescriptorLandmark(
	  /** The landmark's place id. */
		placeId: Option[String] = None,
	  /** Defines the spatial relationship between the target location and the landmark. */
		spatialRelationship: Option[Schema.GoogleMapsPlacesV1AddressDescriptorLandmark.SpatialRelationshipEnum] = None,
	  /** The landmark's display name. */
		displayName: Option[Schema.GoogleTypeLocalizedText] = None,
	  /** The travel distance, in meters, along the road network from the target to the landmark, if known. This value does not take into account the mode of transportation, such as walking, driving, or biking. */
		travelDistanceMeters: Option[BigDecimal] = None,
	  /** A set of type tags for this landmark. For a complete list of possible values, see https://developers.google.com/maps/documentation/places/web-service/place-types. */
		types: Option[List[String]] = None,
	  /** The landmark's resource name. */
		name: Option[String] = None,
	  /** The straight line distance, in meters, between the center point of the target and the center point of the landmark. In some situations, this value can be longer than `travel_distance_meters`. */
		straightLineDistanceMeters: Option[BigDecimal] = None
	)
	
	case class GoogleMapsPlacesV1PlaceSubDestination(
	  /** The resource name of the sub destination. */
		name: Option[String] = None,
	  /** The place id of the sub destination. */
		id: Option[String] = None
	)
	
	case class GoogleMapsPlacesV1PriceRange(
	  /** The high end of the price range (exclusive). Price should be lower than this amount. */
		endPrice: Option[Schema.GoogleTypeMoney] = None,
	  /** The low end of the price range (inclusive). Price should be at or above this amount. */
		startPrice: Option[Schema.GoogleTypeMoney] = None
	)
	
	case class GoogleMapsPlacesV1PhotoMedia(
	  /** The resource name of a photo media in the format: `places/{place_id}/photos/{photo_reference}/media`. */
		name: Option[String] = None,
	  /** A short-lived uri that can be used to render the photo. */
		photoUri: Option[String] = None
	)
	
	case class GoogleMapsPlacesV1PlaceAddressComponent(
	  /** An array indicating the type(s) of the address component. */
		types: Option[List[String]] = None,
	  /** The full text description or name of the address component. For example, an address component for the country Australia may have a long_name of "Australia". */
		longText: Option[String] = None,
	  /** The language used to format this components, in CLDR notation. */
		languageCode: Option[String] = None,
	  /** An abbreviated textual name for the address component, if available. For example, an address component for the country of Australia may have a short_name of "AU". */
		shortText: Option[String] = None
	)
}
