package com.boresjo.play.api.google.addressvalidation

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, RequestSigner, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class GoogleMapsAddressvalidationV1PlusCode(
	  /** Place's compound code, such as "33GV+HQ, Ramberg, Norway", containing the suffix of the global code and replacing the prefix with a formatted name of a reference entity. */
		compoundCode: Option[String] = None,
	  /** Place's global (full) code, such as "9FWM33GV+HQ", representing an 1/8000 by 1/8000 degree area (~14 by 14 meters). */
		globalCode: Option[String] = None
	)
	
	case class GoogleMapsAddressvalidationV1LanguageOptions(
	  /** Preview: Return a [google.maps.addressvalidation.v1.Address] in English. See [google.maps.addressvalidation.v1.ValidationResult.english_latin_address] for details. */
		returnEnglishLatinAddress: Option[Boolean] = None
	)
	
	case class GoogleMapsAddressvalidationV1ValidateAddressResponse(
	  /** The UUID that identifies this response. If the address needs to be re-validated, this UUID &#42;must&#42; accompany the new request. */
		responseId: Option[String] = None,
	  /** The result of the address validation. */
		result: Option[Schema.GoogleMapsAddressvalidationV1ValidationResult] = None
	)
	
	case class GoogleTypePostalAddress(
	  /** Optional. BCP-47 language code of the contents of this address (if known). This is often the UI language of the input form or is expected to match one of the languages used in the address' country/region, or their transliterated equivalents. This can affect formatting in certain countries, but is not critical to the correctness of the data and will never affect any validation or other non-formatting related operations. If this value is not known, it should be omitted (rather than specifying a possibly incorrect default). Examples: "zh-Hant", "ja", "ja-Latn", "en". */
		languageCode: Option[String] = None,
	  /** Required. CLDR region code of the country/region of the address. This is never inferred and it is up to the user to ensure the value is correct. See https://cldr.unicode.org/ and https://www.unicode.org/cldr/charts/30/supplemental/territory_information.html for details. Example: "CH" for Switzerland. */
		regionCode: Option[String] = None,
	  /** Optional. Sublocality of the address. For example, this can be neighborhoods, boroughs, districts. */
		sublocality: Option[String] = None,
	  /** Optional. Generally refers to the city/town portion of the address. Examples: US city, IT comune, UK post town. In regions of the world where localities are not well defined or do not fit into this structure well, leave locality empty and use address_lines. */
		locality: Option[String] = None,
	  /** Optional. Postal code of the address. Not all countries use or require postal codes to be present, but where they are used, they may trigger additional validation with other parts of the address (e.g. state/zip validation in the U.S.A.). */
		postalCode: Option[String] = None,
	  /** Optional. The recipient at the address. This field may, under certain circumstances, contain multiline information. For example, it might contain "care of" information. */
		recipients: Option[List[String]] = None,
	  /** Unstructured address lines describing the lower levels of an address. Because values in address_lines do not have type information and may sometimes contain multiple values in a single field (e.g. "Austin, TX"), it is important that the line order is clear. The order of address lines should be "envelope order" for the country/region of the address. In places where this can vary (e.g. Japan), address_language is used to make it explicit (e.g. "ja" for large-to-small ordering and "ja-Latn" or "en" for small-to-large). This way, the most specific line of an address can be selected based on the language. The minimum permitted structural representation of an address consists of a region_code with all remaining information placed in the address_lines. It would be possible to format such an address very approximately without geocoding, but no semantic reasoning could be made about any of the address components until it was at least partially resolved. Creating an address only containing a region_code and address_lines, and then geocoding is the recommended way to handle completely unstructured addresses (as opposed to guessing which parts of the address should be localities or administrative areas). */
		addressLines: Option[List[String]] = None,
	  /** Optional. Highest administrative subdivision which is used for postal addresses of a country or region. For example, this can be a state, a province, an oblast, or a prefecture. Specifically, for Spain this is the province and not the autonomous community (e.g. "Barcelona" and not "Catalonia"). Many countries don't use an administrative area in postal addresses. E.g. in Switzerland this should be left unpopulated. */
		administrativeArea: Option[String] = None,
	  /** Optional. Additional, country-specific, sorting code. This is not used in most regions. Where it is used, the value is either a string like "CEDEX", optionally followed by a number (e.g. "CEDEX 7"), or just a number alone, representing the "sector code" (Jamaica), "delivery area indicator" (Malawi) or "post office indicator" (e.g. Côte d'Ivoire). */
		sortingCode: Option[String] = None,
	  /** Optional. The name of the organization at the address. */
		organization: Option[String] = None,
	  /** The schema revision of the `PostalAddress`. This must be set to 0, which is the latest revision. All new revisions &#42;&#42;must&#42;&#42; be backward compatible with old revisions. */
		revision: Option[Int] = None
	)
	
	case class GoogleMapsAddressvalidationV1ValidationResult(
	  /** Overall verdict flags */
		verdict: Option[Schema.GoogleMapsAddressvalidationV1Verdict] = None,
	  /** Preview: This feature is in Preview (pre-GA). Pre-GA products and features might have limited support, and changes to pre-GA products and features might not be compatible with other pre-GA versions. Pre-GA Offerings are covered by the [Google Maps Platform Service Specific Terms](https://cloud.google.com/maps-platform/terms/maps-service-terms). For more information, see the [launch stage descriptions](https://developers.google.com/maps/launch-stages). The address translated to English. Translated addresses are not reusable as API input. The service provides them so that the user can use their native language to confirm or deny the validation of the originally-provided address. If part of the address doesn't have an English translation, the service returns that part in an alternate language that uses a Latin script. See [here](https://developers.google.com/maps/documentation/address-validation/convert-addresses-english) for an explanation of how the alternate language is selected. If part of the address doesn't have any translations or transliterations in a language that uses a Latin script, the service returns that part in the local language associated with the address. Enable this output by using the [google.maps.addressvalidation.v1.LanguageOptions.return_english_latin_address] flag. Note: the [google.maps.addressvalidation.v1.Address.unconfirmed_component_types] field in the `english_latin_address` and the [google.maps.addressvalidation.v1.AddressComponent.confirmation_level] fields in `english_latin_address.address_components` are not populated. */
		englishLatinAddress: Option[Schema.GoogleMapsAddressvalidationV1Address] = None,
	  /** Information about the location and place that the address geocoded to. */
		geocode: Option[Schema.GoogleMapsAddressvalidationV1Geocode] = None,
	  /** Other information relevant to deliverability. `metadata` is not guaranteed to be fully populated for every address sent to the Address Validation API. */
		metadata: Option[Schema.GoogleMapsAddressvalidationV1AddressMetadata] = None,
	  /** Information about the address itself as opposed to the geocode. */
		address: Option[Schema.GoogleMapsAddressvalidationV1Address] = None,
	  /** Extra deliverability flags provided by USPS. Only provided in region `US` and `PR`. */
		uspsData: Option[Schema.GoogleMapsAddressvalidationV1UspsData] = None
	)
	
	case class GoogleTypeLatLng(
	  /** The longitude in degrees. It must be in the range [-180.0, +180.0]. */
		longitude: Option[BigDecimal] = None,
	  /** The latitude in degrees. It must be in the range [-90.0, +90.0]. */
		latitude: Option[BigDecimal] = None
	)
	
	case class GoogleGeoTypeViewport(
	  /** Required. The low point of the viewport. */
		low: Option[Schema.GoogleTypeLatLng] = None,
	  /** Required. The high point of the viewport. */
		high: Option[Schema.GoogleTypeLatLng] = None
	)
	
	case class GoogleMapsAddressvalidationV1ComponentName(
	  /** The name text. For example, "5th Avenue" for a street name or "1253" for a street number. */
		text: Option[String] = None,
	  /** The BCP-47 language code. This will not be present if the component name is not associated with a language, such as a street number. */
		languageCode: Option[String] = None
	)
	
	object GoogleMapsAddressvalidationV1AddressComponent {
		enum ConfirmationLevelEnum extends Enum[ConfirmationLevelEnum] { case CONFIRMATION_LEVEL_UNSPECIFIED, CONFIRMED, UNCONFIRMED_BUT_PLAUSIBLE, UNCONFIRMED_AND_SUSPICIOUS }
	}
	case class GoogleMapsAddressvalidationV1AddressComponent(
	  /** The type of the address component. See [Table 2: Additional types returned by the Places service](https://developers.google.com/places/web-service/supported_types#table2) for a list of possible types. */
		componentType: Option[String] = None,
	  /** Indicates the name of the component was replaced with a completely different one, for example a wrong postal code being replaced with one that is correct for the address. This is not a cosmetic change, the input component has been changed to a different one. */
		replaced: Option[Boolean] = None,
	  /** Indicates an address component that is not expected to be present in a postal address for the given region. We have retained it only because it was part of the input. */
		unexpected: Option[Boolean] = None,
	  /** Indicates the level of certainty that we have that the component is correct. */
		confirmationLevel: Option[Schema.GoogleMapsAddressvalidationV1AddressComponent.ConfirmationLevelEnum] = None,
	  /** Indicates a correction to a misspelling in the component name. The API does not always flag changes from one spelling variant to another, such as when changing "centre" to "center". It also does not always flag common misspellings, such as when changing "Amphitheater Pkwy" to "Amphitheatre Pkwy". */
		spellCorrected: Option[Boolean] = None,
	  /** The name for this component. */
		componentName: Option[Schema.GoogleMapsAddressvalidationV1ComponentName] = None,
	  /** Indicates that the component was not part of the input, but we inferred it for the address location and believe it should be provided for a complete address. */
		inferred: Option[Boolean] = None
	)
	
	case class GoogleMapsAddressvalidationV1ValidateAddressRequest(
	  /** Required. The address being validated. Unformatted addresses should be submitted via `address_lines`. The total length of the fields in this input must not exceed 280 characters. Supported regions can be found [here](https://developers.google.com/maps/documentation/address-validation/coverage). The language_code value in the input address is reserved for future uses and is ignored today. The validated address result will be populated based on the preferred language for the given address, as identified by the system. The Address Validation API ignores the values in recipients and organization. Any values in those fields will be discarded and not returned. Please do not set them. */
		address: Option[Schema.GoogleTypePostalAddress] = None,
	  /** Enables USPS CASS compatible mode. This affects _only_ the [google.maps.addressvalidation.v1.ValidationResult.usps_data] field of [google.maps.addressvalidation.v1.ValidationResult]. Note: for USPS CASS enabled requests for addresses in Puerto Rico, a [google.type.PostalAddress.region_code] of the `address` must be provided as "PR", or an [google.type.PostalAddress.administrative_area] of the `address` must be provided as "Puerto Rico" (case-insensitive) or "PR". It's recommended to use a componentized `address`, or alternatively specify at least two [google.type.PostalAddress.address_lines] where the first line contains the street number and name and the second line contains the city, state, and zip code. */
		enableUspsCass: Option[Boolean] = None,
	  /** Optional. A string which identifies an Autocomplete session for billing purposes. Must be a URL and filename safe base64 string with at most 36 ASCII characters in length. Otherwise an INVALID_ARGUMENT error is returned. The session begins when the user makes an Autocomplete query, and concludes when they select a place and a call to Place Details or Address Validation is made. Each session can have multiple Autocomplete queries, followed by one Place Details or Address Validation request. The credentials used for each request within a session must belong to the same Google Cloud Console project. Once a session has concluded, the token is no longer valid; your app must generate a fresh token for each session. If the `sessionToken` parameter is omitted, or if you reuse a session token, the session is charged as if no session token was provided (each request is billed separately). Note: Address Validation can only be used in sessions with the Autocomplete (New) API, not the Autocomplete API. See https://developers.google.com/maps/documentation/places/web-service/session-pricing for more details. */
		sessionToken: Option[String] = None,
	  /** This field must be empty for the first address validation request. If more requests are necessary to fully validate a single address (for example if the changes the user makes after the initial validation need to be re-validated), then each followup request must populate this field with the response_id from the very first response in the validation sequence. */
		previousResponseId: Option[String] = None,
	  /** Optional. Preview: This feature is in Preview (pre-GA). Pre-GA products and features might have limited support, and changes to pre-GA products and features might not be compatible with other pre-GA versions. Pre-GA Offerings are covered by the [Google Maps Platform Service Specific Terms](https://cloud.google.com/maps-platform/terms/maps-service-terms). For more information, see the [launch stage descriptions](https://developers.google.com/maps/launch-stages). Enables the Address Validation API to include additional information in the response. */
		languageOptions: Option[Schema.GoogleMapsAddressvalidationV1LanguageOptions] = None
	)
	
	case class GoogleMapsAddressvalidationV1Address(
	  /** The post-processed address, formatted as a single-line address following the address formatting rules of the region where the address is located. Note: the format of this address may not match the format of the address in the `postal_address` field. For example, the `postal_address` always represents the country as a 2 letter `region_code`, such as "US" or "NZ". By contrast, this field uses a longer form of the country name, such as "USA" or "New Zealand". */
		formattedAddress: Option[String] = None,
	  /** Unordered list. The individual address components of the formatted and corrected address, along with validation information. This provides information on the validation status of the individual components. Address components are not ordered in a particular way. Do not make any assumptions on the ordering of the address components in the list. */
		addressComponents: Option[List[Schema.GoogleMapsAddressvalidationV1AddressComponent]] = None,
	  /** Any tokens in the input that could not be resolved. This might be an input that was not recognized as a valid part of an address. For example, for an input such as "Parcel 0000123123 & 0000456456 Str # Guthrie Center IA 50115 US", the unresolved tokens might look like `["Parcel", "0000123123", "&", "0000456456"]`. */
		unresolvedTokens: Option[List[String]] = None,
	  /** The post-processed address represented as a postal address. */
		postalAddress: Option[Schema.GoogleTypePostalAddress] = None,
	  /** The types of the components that are present in the `address_components` but could not be confirmed to be correct. This field is provided for the sake of convenience: its contents are equivalent to iterating through the `address_components` to find the types of all the components where the confirmation_level is not CONFIRMED or the inferred flag is not set to `true`. The list of possible types can be found [here](https://developers.google.com/maps/documentation/geocoding/requests-geocoding#Types). */
		unconfirmedComponentTypes: Option[List[String]] = None,
	  /** The types of components that were expected to be present in a correctly formatted mailing address but were not found in the input AND could not be inferred. Components of this type are not present in `formatted_address`, `postal_address`, or `address_components`. An example might be `['street_number', 'route']` for an input like "Boulder, Colorado, 80301, USA". The list of possible types can be found [here](https://developers.google.com/maps/documentation/geocoding/requests-geocoding#Types). */
		missingComponentTypes: Option[List[String]] = None
	)
	
	case class GoogleMapsAddressvalidationV1Geocode(
	  /** The size of the geocoded place, in meters. This is another measure of the coarseness of the geocoded location, but in physical size rather than in semantic meaning. */
		featureSizeMeters: Option[BigDecimal] = None,
	  /** The plus code corresponding to the `location`. */
		plusCode: Option[Schema.GoogleMapsAddressvalidationV1PlusCode] = None,
	  /** The PlaceID of the place this input geocodes to. For more information about Place IDs see [here](https://developers.google.com/maps/documentation/places/web-service/place-id). */
		placeId: Option[String] = None,
	  /** The bounds of the geocoded place. */
		bounds: Option[Schema.GoogleGeoTypeViewport] = None,
	  /** The type(s) of place that the input geocoded to. For example, `['locality', 'political']`. The full list of types can be found [here](https://developers.google.com/maps/documentation/geocoding/requests-geocoding#Types). */
		placeTypes: Option[List[String]] = None,
	  /** The geocoded location of the input. Using place IDs is preferred over using addresses, latitude/longitude coordinates, or plus codes. Using coordinates when routing or calculating driving directions will always result in the point being snapped to the road nearest to those coordinates. This may not be a road that will quickly or safely lead to the destination and may not be near an access point to the property. Additionally, when a location is reverse geocoded, there is no guarantee that the returned address will match the original. */
		location: Option[Schema.GoogleTypeLatLng] = None
	)
	
	object GoogleMapsAddressvalidationV1ProvideValidationFeedbackRequest {
		enum ConclusionEnum extends Enum[ConclusionEnum] { case VALIDATION_CONCLUSION_UNSPECIFIED, VALIDATED_VERSION_USED, USER_VERSION_USED, UNVALIDATED_VERSION_USED, UNUSED }
	}
	case class GoogleMapsAddressvalidationV1ProvideValidationFeedbackRequest(
	  /** Required. The outcome of the sequence of validation attempts. If this field is set to `VALIDATION_CONCLUSION_UNSPECIFIED`, an `INVALID_ARGUMENT` error will be returned. */
		conclusion: Option[Schema.GoogleMapsAddressvalidationV1ProvideValidationFeedbackRequest.ConclusionEnum] = None,
	  /** Required. The ID of the response that this feedback is for. This should be the response_id from the first response in a series of address validation attempts. */
		responseId: Option[String] = None
	)
	
	case class GoogleMapsAddressvalidationV1AddressMetadata(
	  /** Indicates that this is the address of a business. If unset, indicates that the value is unknown. */
		business: Option[Boolean] = None,
	  /** Indicates that this is the address of a residence. If unset, indicates that the value is unknown. */
		residential: Option[Boolean] = None,
	  /** Indicates that the address of a PO box. If unset, indicates that the value is unknown. */
		poBox: Option[Boolean] = None
	)
	
	object GoogleMapsAddressvalidationV1Verdict {
		enum GeocodeGranularityEnum extends Enum[GeocodeGranularityEnum] { case GRANULARITY_UNSPECIFIED, SUB_PREMISE, PREMISE, PREMISE_PROXIMITY, BLOCK, ROUTE, OTHER }
		enum ValidationGranularityEnum extends Enum[ValidationGranularityEnum] { case GRANULARITY_UNSPECIFIED, SUB_PREMISE, PREMISE, PREMISE_PROXIMITY, BLOCK, ROUTE, OTHER }
		enum InputGranularityEnum extends Enum[InputGranularityEnum] { case GRANULARITY_UNSPECIFIED, SUB_PREMISE, PREMISE, PREMISE_PROXIMITY, BLOCK, ROUTE, OTHER }
	}
	case class GoogleMapsAddressvalidationV1Verdict(
	  /** Information about the granularity of the `geocode`. This can be understood as the semantic meaning of how coarse or fine the geocoded location is. This can differ from the `validation_granularity` above occasionally. For example, our database might record the existence of an apartment number but do not have a precise location for the apartment within a big apartment complex. In that case, the `validation_granularity` will be `SUB_PREMISE` but the `geocode_granularity` will be `PREMISE`. */
		geocodeGranularity: Option[Schema.GoogleMapsAddressvalidationV1Verdict.GeocodeGranularityEnum] = None,
	  /** The address is considered complete if there are no unresolved tokens, no unexpected or missing address components. If unset, indicates that the value is `false`. See `missing_component_types`, `unresolved_tokens` or `unexpected` fields for more details. */
		addressComplete: Option[Boolean] = None,
	  /** At least one address component cannot be categorized or validated, see [google.maps.addressvalidation.v1.Address.address_components] for details. */
		hasUnconfirmedComponents: Option[Boolean] = None,
	  /** The granularity level that the API can fully &#42;&#42;validate&#42;&#42; the address to. For example, an `validation_granularity` of `PREMISE` indicates all address components at the level of `PREMISE` or more coarse can be validated. Per address component validation result can be found in [google.maps.addressvalidation.v1.Address.address_components]. */
		validationGranularity: Option[Schema.GoogleMapsAddressvalidationV1Verdict.ValidationGranularityEnum] = None,
	  /** At least one address component was replaced, see [google.maps.addressvalidation.v1.Address.address_components] for details. */
		hasReplacedComponents: Option[Boolean] = None,
	  /** The granularity of the &#42;&#42;input&#42;&#42; address. This is the result of parsing the input address and does not give any validation signals. For validation signals, refer to `validation_granularity` below. For example, if the input address includes a specific apartment number, then the `input_granularity` here will be `SUB_PREMISE`. If we cannot match the apartment number in the databases or the apartment number is invalid, the `validation_granularity` will likely be `PREMISE` or below. */
		inputGranularity: Option[Schema.GoogleMapsAddressvalidationV1Verdict.InputGranularityEnum] = None,
	  /** At least one address component was inferred (added) that wasn't in the input, see [google.maps.addressvalidation.v1.Address.address_components] for details. */
		hasInferredComponents: Option[Boolean] = None
	)
	
	case class GoogleMapsAddressvalidationV1ProvideValidationFeedbackResponse(
	
	)
	
	case class GoogleMapsAddressvalidationV1UspsAddress(
	  /** City + state + postal code. */
		cityStateZipAddressLine: Option[String] = None,
	  /** Second address line. */
		secondAddressLine: Option[String] = None,
	  /** Postal code e.g. 10009. */
		zipCode: Option[String] = None,
	  /** 2 letter state code. */
		state: Option[String] = None,
	  /** Puerto Rican urbanization name. */
		urbanization: Option[String] = None,
	  /** First address line. */
		firstAddressLine: Option[String] = None,
	  /** Firm name. */
		firm: Option[String] = None,
	  /** 4-digit postal code extension e.g. 5023. */
		zipCodeExtension: Option[String] = None,
	  /** City name. */
		city: Option[String] = None
	)
	
	case class GoogleMapsAddressvalidationV1UspsData(
	  /** Is this place vacant? Returns a single character. &#42; `Y`: The address is vacant &#42; `N`: The address is not vacant */
		dpvVacant: Option[String] = None,
	  /** Indicates the NoStat type. Returns a reason code as int. &#42; `1`: IDA (Internal Drop Address) – Addresses that do not receive mail directly from the USPS but are delivered to a drop address that services them. &#42; `2`: CDS - Addresses that have not yet become deliverable. For example, a new subdivision where lots and primary numbers have been determined, but no structure exists yet for occupancy. &#42; `3`: Collision - Addresses that do not actually DPV confirm. &#42; `4`: CMZ (College, Military and Other Types) - ZIP + 4 records USPS has incorporated into the data. &#42; `5`: Regular - Indicates addresses not receiving delivery and the addresses are not counted as possible deliveries. &#42; `6`: Secondary Required - The address requires secondary information. */
		dpvNoStatReasonCode: Option[Int] = None,
	  /** Indicator that a default address was found, but more specific addresses exists. */
		defaultAddress: Option[Boolean] = None,
	  /** The delivery point check digit. This number is added to the end of the delivery_point_barcode for mechanically scanned mail. Adding all the digits of the delivery_point_barcode, delivery_point_check_digit, postal code, and ZIP+4 together should yield a number divisible by 10. */
		deliveryPointCheckDigit: Option[String] = None,
	  /** Indicator that the request has been CASS processed. */
		cassProcessed: Option[Boolean] = None,
	  /** Flag indicates mail delivery is not performed every day of the week. Returns a single character. &#42; `Y`: The mail delivery is not performed every day of the week. &#42; `N`: No indication the mail delivery is not performed every day of the week. */
		dpvNonDeliveryDays: Option[String] = None,
	  /** Flag indicates door is accessible, but package will not be left due to security concerns. Returns a single character. &#42; `Y`: The package will not be left due to security concerns. &#42; `N`: No indication the package will not be left due to security concerns. */
		dpvNoSecureLocation: Option[String] = None,
	  /** Indicates that more than one DPV return code is valid for the address. Returns a single character. &#42; `Y`: Address was DPV confirmed for primary and any secondary numbers. &#42; `N`: Primary and any secondary number information failed to DPV confirm. &#42; `S`: Address was DPV confirmed for the primary number only, and the secondary number information was present but not confirmed, or a single trailing alpha on a primary number was dropped to make a DPV match and secondary information required. &#42; `D`: Address was DPV confirmed for the primary number only, and the secondary number information was missing. &#42; `R`: Address confirmed but assigned to phantom route R777 and R779 and USPS delivery is not provided. */
		dpvEnhancedDeliveryCode: Option[String] = None,
	  /** The possible values for DPV confirmation. Returns a single character or returns no value. &#42; `N`: Primary and any secondary number information failed to DPV confirm. &#42; `D`: Address was DPV confirmed for the primary number only, and the secondary number information was missing. &#42; `S`: Address was DPV confirmed for the primary number only, and the secondary number information was present but not confirmed. &#42; `Y`: Address was DPV confirmed for primary and any secondary numbers. &#42; Empty: If the response does not contain a `dpv_confirmation` value, the address was not submitted for DPV confirmation. */
		dpvConfirmation: Option[String] = None,
	  /** The footnotes from delivery point validation. Multiple footnotes may be strung together in the same string. &#42; `AA`: Input address matched to the ZIP+4 file &#42; `A1`: Input address was not matched to the ZIP+4 file &#42; `BB`: Matched to DPV (all components) &#42; `CC`: Secondary number not matched and not required &#42; `C1`: Secondary number not matched but required &#42; `N1`: High-rise address missing secondary number &#42; `M1`: Primary number missing &#42; `M3`: Primary number invalid &#42; `P1`: Input address PO, RR or HC box number missing &#42; `P3`: Input address PO, RR, or HC Box number invalid &#42; `F1`: Input address matched to a military address &#42; `G1`: Input address matched to a general delivery address &#42; `U1`: Input address matched to a unique ZIP code &#42; `PB`: Input address matched to PBSA record &#42; `RR`: DPV confirmed address with PMB information &#42; `R1`: DPV confirmed address without PMB information &#42; `R7`: Carrier Route R777 or R779 record &#42; `IA`: Informed Address identified &#42; `TA`: Primary number matched by dropping a trailing alpha */
		dpvFootnote: Option[String] = None,
	  /** Integer identifying non-delivery days. It can be interrogated using bit flags: 0x40 – Sunday is a non-delivery day 0x20 – Monday is a non-delivery day 0x10 – Tuesday is a non-delivery day 0x08 – Wednesday is a non-delivery day 0x04 – Thursday is a non-delivery day 0x02 – Friday is a non-delivery day 0x01 – Saturday is a non-delivery day */
		dpvNonDeliveryDaysValues: Option[Int] = None,
	  /** PMB (Private Mail Box) unit designator. */
		pmbDesignator: Option[String] = None,
	  /** Flag indicates mail is delivered to a single receptable at a site. Returns a single character. &#42; `Y`: The mail is delivered to a single receptable at a site. &#42; `N`: The mail is not delivered to a single receptable at a site. */
		dpvDrop: Option[String] = None,
	  /** Main post office state. */
		postOfficeState: Option[String] = None,
	  /** Carrier route rate sort indicator. */
		carrierRouteIndicator: Option[String] = None,
	  /** Main post office city. */
		postOfficeCity: Option[String] = None,
	  /** Type of the address record that matches the input address. &#42; `F`: FIRM. This is a match to a Firm Record, which is the finest level of match available for an address. &#42; `G`: GENERAL DELIVERY. This is a match to a General Delivery record. &#42; `H`: BUILDING / APARTMENT. This is a match to a Building or Apartment record. &#42; `P`: POST OFFICE BOX. This is a match to a Post Office Box. &#42; `R`: RURAL ROUTE or HIGHWAY CONTRACT: This is a match to either a Rural Route or a Highway Contract record, both of which may have associated Box Number ranges. &#42; `S`: STREET RECORD: This is a match to a Street record containing a valid primary number range. */
		addressRecordType: Option[String] = None,
	  /** USPS standardized address. */
		standardizedAddress: Option[Schema.GoogleMapsAddressvalidationV1UspsAddress] = None,
	  /** Flag indicates addresses where USPS cannot knock on a door to deliver mail. Returns a single character. &#42; `Y`: The door is not accessible. &#42; `N`: No indication the door is not accessible. */
		dpvDoorNotAccessible: Option[String] = None,
	  /** Error message for USPS data retrieval. This is populated when USPS processing is suspended because of the detection of artificially created addresses. The USPS data fields might not be populated when this error is present. */
		errorMessage: Option[String] = None,
	  /** County name. */
		county: Option[String] = None,
	  /** The delivery address is matchable, but the EWS file indicates that an exact match will be available soon. */
		ewsNoMatch: Option[Boolean] = None,
	  /** LACSLink indicator. */
		lacsLinkIndicator: Option[String] = None,
	  /** Enhanced Line of Travel (eLOT) number. */
		elotNumber: Option[String] = None,
	  /** Is this a no stat address or an active address? No stat addresses are ones which are not continuously occupied or addresses that the USPS does not service. Returns a single character. &#42; `Y`: The address is not active &#42; `N`: The address is active */
		dpvNoStat: Option[String] = None,
	  /** Indicates that mail is not delivered to the street address. Returns a single character. &#42; `Y`: The mail is not delivered to the street address. &#42; `N`: The mail is delivered to the street address. */
		dpvThrowback: Option[String] = None,
	  /** 2 digit delivery point code */
		deliveryPointCode: Option[String] = None,
	  /** PMB (Private Mail Box) number; */
		pmbNumber: Option[String] = None,
	  /** The carrier route code. A four character code consisting of a one letter prefix and a three digit route designator. Prefixes: &#42; `C`: Carrier route (or city route) &#42; `R`: Rural route &#42; `H`: Highway Contract Route &#42; `B`: Post Office Box Section &#42; `G`: General delivery unit */
		carrierRoute: Option[String] = None,
	  /** eLOT Ascending/Descending Flag (A/D). */
		elotFlag: Option[String] = None,
	  /** Footnotes from matching a street or highrise record to suite information. If business name match is found, the secondary number is returned. &#42; `A`: SuiteLink record match, business address improved. &#42; `00`: No match, business address is not improved. */
		suitelinkFootnote: Option[String] = None,
	  /** FIPS county code. */
		fipsCountyCode: Option[String] = None,
	  /** Indicates the address was matched to PBSA record. Returns a single character. &#42; `Y`: The address was matched to PBSA record. &#42; `N`: The address was not matched to PBSA record. */
		dpvPbsa: Option[String] = None,
	  /** PO Box only postal code. */
		poBoxOnlyPostalCode: Option[Boolean] = None,
	  /** LACSLink return code. */
		lacsLinkReturnCode: Option[String] = None,
	  /** Abbreviated city. */
		abbreviatedCity: Option[String] = None,
	  /** Indicates if the address is a CMRA (Commercial Mail Receiving Agency)--a private business receiving mail for clients. Returns a single character. &#42; `Y`: The address is a CMRA &#42; `N`: The address is not a CMRA */
		dpvCmra: Option[String] = None
	)
}
