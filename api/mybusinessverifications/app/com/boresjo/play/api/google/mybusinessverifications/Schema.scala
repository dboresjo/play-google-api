package com.boresjo.play.api.google.mybusinessverifications

import play.api.libs.json.*
import play.api.libs.ws.{WSClient, WSRequest}
import com.boresjo.play.api.{PlayApi, AuthToken, JsonEnumFormat}

import javax.inject.*
import scala.concurrent.{ExecutionContext, Future}

object Schema {
	case class VoiceOfMerchantState(
	  /** Indicates whether the location is in good standing and has control over the business on Google. Any edits made to the location will propagate to Maps after passing the review phase. */
		hasVoiceOfMerchant: Option[Boolean] = None,
	  /** Indicates whether the location has the authority (ownership) over the business on Google. If true, another location cannot take over and become the dominant listing on Maps. However, edits will not become live unless Voice of Merchant is gained (i.e. has_voice_of_merchant is true). */
		hasBusinessAuthority: Option[Boolean] = None,
	  /** Wait to gain Voice of Merchant. The location is under review for quality purposes. */
		waitForVoiceOfMerchant: Option[Schema.WaitForVoiceOfMerchant] = None,
	  /** Start or continue the verification process. */
		verify: Option[Schema.Verify] = None,
	  /** This location duplicates another location that is in good standing. If you have access to the location in good standing, use that location's id to perform operations. Otherwise, request access from the current owner. */
		resolveOwnershipConflict: Option[Schema.ResolveOwnershipConflict] = None,
	  /** The location fails to comply with our [guidelines](https://support.google.com/business/answer/3038177) and requires additional steps for reinstatement. To fix this issue, consult the [Help Center Article](https://support.google.com/business/answer/4569145). */
		complyWithGuidelines: Option[Schema.ComplyWithGuidelines] = None
	)
	
	case class WaitForVoiceOfMerchant(
	
	)
	
	case class Verify(
	  /** Indicates whether a verification process has already started, and can be completed by the location. */
		hasPendingVerification: Option[Boolean] = None
	)
	
	case class ResolveOwnershipConflict(
	
	)
	
	object ComplyWithGuidelines {
		enum RecommendationReasonEnum extends Enum[RecommendationReasonEnum] { case RECOMMENDATION_REASON_UNSPECIFIED, BUSINESS_LOCATION_SUSPENDED, BUSINESS_LOCATION_DISABLED }
	}
	case class ComplyWithGuidelines(
	  /** The reason why the location is being recommended to comply with guidelines. */
		recommendationReason: Option[Schema.ComplyWithGuidelines.RecommendationReasonEnum] = None
	)
	
	case class CompleteVerificationRequest(
	  /** Required. PIN code received by the merchant to complete the verification. */
		pin: Option[String] = None
	)
	
	case class CompleteVerificationResponse(
	  /** The completed verification. */
		verification: Option[Schema.Verification] = None
	)
	
	object Verification {
		enum MethodEnum extends Enum[MethodEnum] { case VERIFICATION_METHOD_UNSPECIFIED, ADDRESS, EMAIL, PHONE_CALL, SMS, AUTO, VETTED_PARTNER }
		enum StateEnum extends Enum[StateEnum] { case STATE_UNSPECIFIED, PENDING, COMPLETED, FAILED }
	}
	case class Verification(
	  /** Resource name of the verification. */
		name: Option[String] = None,
	  /** The method of the verification. */
		method: Option[Schema.Verification.MethodEnum] = None,
	  /** The state of the verification. */
		state: Option[Schema.Verification.StateEnum] = None,
	  /** The timestamp when the verification is requested. */
		createTime: Option[String] = None,
	  /** Optional. Response announcement set only if the method is VETTED_PARTNER. */
		announcement: Option[String] = None
	)
	
	case class ListVerificationsResponse(
	  /** List of the verifications. */
		verifications: Option[List[Schema.Verification]] = None,
	  /** If the number of verifications exceeded the requested page size, this field will be populated with a token to fetch the next page of verification on a subsequent call. If there are no more attributes, this field will not be present in the response. */
		nextPageToken: Option[String] = None
	)
	
	case class FetchVerificationOptionsRequest(
	  /** Required. The BCP 47 language code representing the language that is to be used for the verification process. Available options vary by language. */
		languageCode: Option[String] = None,
	  /** Optional. Extra context information for the verification of service businesses. Can only be applied to the locations whose business type is CUSTOMER_LOCATION_ONLY. Specifying an accurate address could enable more options. INVALID_ARGUMENT will be thrown if it is set for other business types of locations. */
		context: Option[Schema.ServiceBusinessContext] = None
	)
	
	case class ServiceBusinessContext(
	  /** The verification address of the location. It is used to either enable more verification options or send a postcard. */
		address: Option[Schema.PostalAddress] = None
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
	
	case class FetchVerificationOptionsResponse(
	  /** The available verification options. */
		options: Option[List[Schema.VerificationOption]] = None
	)
	
	object VerificationOption {
		enum VerificationMethodEnum extends Enum[VerificationMethodEnum] { case VERIFICATION_METHOD_UNSPECIFIED, ADDRESS, EMAIL, PHONE_CALL, SMS, AUTO, VETTED_PARTNER }
	}
	case class VerificationOption(
	  /** Method to verify the location. */
		verificationMethod: Option[Schema.VerificationOption.VerificationMethodEnum] = None,
	  /** Set only if the method is PHONE_CALL or SMS. Phone number that the PIN will be sent to. */
		phoneNumber: Option[String] = None,
	  /** Set only if the method is MAIL. */
		addressData: Option[Schema.AddressVerificationData] = None,
	  /** Set only if the method is EMAIL. */
		emailData: Option[Schema.EmailVerificationData] = None,
	  /** Set only if the method is VETTED_PARTNER. */
		announcement: Option[String] = None
	)
	
	case class AddressVerificationData(
	  /** Merchant's business name. */
		business: Option[String] = None,
	  /** Address that a postcard can be sent to. */
		address: Option[Schema.PostalAddress] = None,
	  /** Expected number of days it takes to deliver a postcard to the address's region. */
		expectedDeliveryDaysRegion: Option[Int] = None
	)
	
	case class EmailVerificationData(
	  /** Domain name in the email address. e.g. "gmail.com" in foo@gmail.com */
		domain: Option[String] = None,
	  /** User name in the email address. e.g. "foo" in foo@gmail.com */
		user: Option[String] = None,
	  /** Whether client is allowed to provide a different user name. */
		isUserNameEditable: Option[Boolean] = None
	)
	
	object VerifyLocationRequest {
		enum MethodEnum extends Enum[MethodEnum] { case VERIFICATION_METHOD_UNSPECIFIED, ADDRESS, EMAIL, PHONE_CALL, SMS, AUTO, VETTED_PARTNER }
	}
	case class VerifyLocationRequest(
	  /** Required. Verification method. */
		method: Option[Schema.VerifyLocationRequest.MethodEnum] = None,
	  /** Optional. The BCP 47 language code representing the language that is to be used for the verification process. */
		languageCode: Option[String] = None,
	  /** Optional. The input for EMAIL method. Email address where the PIN should be sent to. An email address is accepted only if it is one of the addresses provided by FetchVerificationOptions. If the EmailVerificationData has is_user_name_editable set to true, the client may specify a different user name (local-part) but must match the domain name. */
		emailAddress: Option[String] = None,
	  /** Optional. The input for ADDRESS method. Contact name the mail should be sent to. */
		mailerContact: Option[String] = None,
	  /** Optional. The input for PHONE_CALL/SMS method The phone number that should be called or be sent SMS to. It must be one of the phone numbers in the eligible options. */
		phoneNumber: Option[String] = None,
	  /** Optional. The input for VETTED_PARTNER method available to select [partners.](https://support.google.com/business/answer/7674102) The input is not needed for a vetted account. Token that is associated to the location. Token that is associated to the location. */
		token: Option[Schema.VerificationToken] = None,
	  /** Optional. Extra context information for the verification of service businesses. It is only required for the locations whose business type is CUSTOMER_LOCATION_ONLY. For ADDRESS verification, the address will be used to send out postcard. For other methods, it should be the same as the one that is passed to GetVerificationOptions. INVALID_ARGUMENT will be thrown if it is set for other types of business locations. */
		context: Option[Schema.ServiceBusinessContext] = None
	)
	
	case class VerificationToken(
	  /** The token string. */
		tokenString: Option[String] = None
	)
	
	case class VerifyLocationResponse(
	  /** The created verification request. */
		verification: Option[Schema.Verification] = None
	)
}
